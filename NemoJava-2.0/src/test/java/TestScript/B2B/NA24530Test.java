package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA24530Test extends SuperTestClass{
	private B2BPage b2bPage;
	private HMCPage hmcPage;	
	public String homePageUrl;
	private String AcslProjectId;
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";

	public NA24530Test(String Store){
		this.Store = Store;
		this.testName = "NA-24530";
	}
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA24530(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			String hmcLoginURL = testData.HMC.getHomePageUrl();
		
			//Step 1 : HMC->B2B Unit->Site Attribute Tab->ACSL Project ID Input vaule:96
			Dailylog.logInfoDB(1, "ACSL Project ID Input vaule:96", Store, testName);
			fulfillmentType("ACSL");
			Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);
		    AcslProjectId = driver.findElement(By.xpath("//input[contains(@id,'B2BUnit.AcslProjectId') and contains(@id,'input')]")).getAttribute("value");
			System.out.println("after change , the AcslProjectId is :" + AcslProjectId);		
			Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'B2BUnit.AcslProjectId') and contains(@id,'input')]")).getAttribute("value").equals("96"));		

			
			checkAdvancedShippingOptionInB2B();

			//=============== Step:6 & 7 & 8 Fill Shipping Details and paymet page details =================//
			String orderNum = B2BCommon.placeAnOrder(driver, this.Store, b2bPage, testData);
			Dailylog.logInfoDB(8, "Order Num" + orderNum, Store, testName);
			
			//Step 9 check order status in HMC
			driver.get(hmcLoginURL);
			HMCCommon.HMCOrderCheck(hmcPage, orderNum);


		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	private void fulfillmentType(String type){
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
		if(type=="ACSL"){
			AcslProjectId = driver.findElement(By.xpath("//input[contains(@id,'B2BUnit.AcslProjectId') and contains(@id,'input')]")).getAttribute("value");
			System.out.println("AcslProjectId is :" + AcslProjectId);
			if(!AcslProjectId.equals("96")){
				hmcPage.AcslProjectId.clear();
				hmcPage.AcslProjectId.click();
				hmcPage.AcslProjectId.sendKeys("96");
			}
			//Step 2: Under Site Attribute Tab, Set Fulfillment Type:ACSL
			Dailylog.logInfoDB(2, "Set Fulfillment Type:ACSL", Store, testName);
			hmcPage.fulfillmentType.click();
			hmcPage.fulfillmentType_ACSL.click();
		}else{
			hmcPage.fulfillmentType.click();
			hmcPage.fulfillmentType_CRM.click();
		}
		
		Common.sleep(5000);
		hmcPage.B2BUnit_Save.click();
	}
	
	
	private void checkAdvancedShippingOptionInB2B(){
		String b2bLoginURL = testData.B2B.getLoginUrl();
		driver.get(b2bLoginURL);
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		Common.sleep(3000);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(1500);
		if(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))){
			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
		}
		B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
		
		Dailylog.logInfoDB(4, "Added a product into Cart", Store, testName);
		Common.sleep(2000);
		b2bPage.cartPage_LenovoCheckout.click();
		Common.sleep(3000);
		((JavascriptExecutor) driver).executeScript("scroll(0,500)");
		
		Common.sleep(1000);
		

	}
	
	

	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			Dailylog.logInfoDB(10, "rollback", Store, testName); // roll back
			SetupBrowser();
			fulfillmentType("CRM");
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
