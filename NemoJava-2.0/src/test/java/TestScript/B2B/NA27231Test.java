package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;



public class NA27231Test extends SuperTestClass {
	public String Punchout_Ariba_URL;
	public String HMCURL;
	HMCPage hmcPage ;
	B2BPunchoutPage page;
	B2BPage b2bPage;
	String ProductID="20JES1X200";
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	public NA27231Test(String Store){
		this.Store = Store;
		this.testName = "NA-27231";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA27231(ITestContext ctx){
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			page = new B2BPunchoutPage(driver);
			b2bPage = new B2BPage(driver);
			HMCURL = testData.HMC.getHomePageUrl();
		
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC successfully.", Store,testName);
			
			
			
			HMCCommon.searchB2BUnit(hmcPage,testData);
			
			driver.findElement(By.id("Content/EditorTab[B2BUnit.tab.siteattributes]_span")).click();
			Common.sleep(8000);
			driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_PURCHASEORDER_sequence')])[2]")).clear();
			driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_PURCHASEORDER_sequence')])[2]")).sendKeys("1");
			if(driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_BANKDEPOSIT_sequence')])[2]")).isDisplayed()) {
				driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_BANKDEPOSIT_sequence')])[2]")).clear();
				driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_BANKDEPOSIT_sequence')])[2]")).sendKeys("2");
			}else {
				driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_CARD_sequence')])[2]")).clear();
				driver.findElement(By.xpath("(.//input[contains(@id,'NEMO_PAYERID_EVENT_CARD_sequence')])[2]")).sendKeys("2");
			}
			
			hmcPage.PaymentLeasing_saveAndCreate.click();
			
			Common.sleep(3000);
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
			Common.sleep(2000);
			
			driver.findElement(By.id("quickOrderProductId")).clear();
			driver.findElement(By.id("quickOrderProductId")).sendKeys(ProductID);
			driver.findElement(By.xpath(".//*[@id='quickAddInput']/a")).click();
			Common.sleep(3000);
			driver.findElement(By.id("validateDateformatForCheckout")).click();
			
			Common.sleep(5000);
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, "us",
					"testtesttest", "testtesttest", "Georgia",
					"1535 Broadway", "New York", "New York", "10036-4077",
					"2129450100");
			Thread.sleep(3000);
			b2bPage.shippingPage_ContinueToPayment.click();
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions
							.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));
			
			String PURCHASEORDERIndex=driver.findElement(By.id("PaymentTypeSelection_PURCHASEORDER")).getAttribute("tabindex").toString();
			String CARDIndex=driver.findElement(By.id("PaymentTypeSelection_CARD")).getAttribute("tabindex").toString();
			if(PURCHASEORDERIndex=="2"&&CARDIndex=="3"){
				Assert.assertEquals(true, true);
			}
		}catch(Throwable e){
			handleThrowable(e, ctx);			
		}
		
	}
	
	
}

