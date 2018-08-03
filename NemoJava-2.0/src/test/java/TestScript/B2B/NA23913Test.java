package TestScript.B2B;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA23913Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	
	public String storeID = "";
	
    public NA23913Test(String store) {
		this.Store = store;
		this.testName = "NA-23913";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA23913(ITestContext ctx) {
		
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			
			
			//1~2 open the website and login in with telesales
			Dailylog.logInfoDB(1, "open the website and login in with telesales", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			
			//3, go to "My Account" page and click "Start Assisted Service Session"
			//Expect : Assisted Service Mode can appear at the top of the page
			
			Dailylog.logInfoDB(3, "click \"Start Assisted Service Session\"", Store, testName);
			
			b2bPage.homepage_MyAccount.click();
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			//Assert.assertTrue("Assisted Service Session Mode does not appeared !!!", b2bPage.asmMode_top.isDisplayed());
			
			//4,login in the ASM mode with the login user password
			//Expect : the ASM Mode can be login successfully
			Dailylog.logInfoDB(4, "login ASM", Store, testName);
		
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			Thread.sleep(5000);
			
			Assert.assertTrue("ASM login failed !!!", b2bPage.asmMode_signOutButton.isDisplayed());
			
			//5, the default Store Type is LE, If not ,please set the Store Type as LE
			Dailylog.logInfoDB(5, "Set the store type as LE", Store, testName);
			
			String defaultStoreType = driver.findElement(By.xpath("//select[@name='storeType']/option[@selected='selected']")).getText().toString();
			System.out.println("defaultStoreType is: " + defaultStoreType);
			
			if(!defaultStoreType.equals("LE")){
				Select select = new Select(driver.findElement(By.xpath("//select[@name='storeType']")));
				select.selectByValue("LE");
			}
			String defaultStoreType1 = driver.findElement(By.xpath("//select[@name='storeType']/option[@selected='selected']")).getText().toString();
			
			Assert.assertTrue("default store type is not LE", defaultStoreType1.equals("LE"));
			
			//6, click Customer ID search icon 
			//Expect : Advanced Customer Search dialogue will appear 
			Dailylog.logInfoDB(6, "click Customer ID search icon ", Store, testName);
			
			B2CCommon.closeHomePagePopUP(driver);
			
			b2bPage.asmMode_customerSearchIcon.click();
			
			Assert.assertTrue(" Advanced Customer Search dialogue does not appear ", b2bPage.asmMode_advancedCustomerSearchButton.isDisplayed());
			
			//7, click Search button on the Advanced Customer Search dialogue .
			//Expect : the result is not null , some results can be searched out.
			Dailylog.logInfoDB(7, "click Search button on the Advanced Customer Search dialogue", Store, testName);
			
			b2bPage.advancedSearch_FirstName.clear();
			b2bPage.advancedSearch_FirstName.sendKeys("A");
			
			Thread.sleep(3000);
			b2bPage.asmMode_advancedCustomerSearchButton.click();
			Thread.sleep(60000);
			
			boolean b = Common.isElementExist(driver, By.xpath(".//*[@id='customerPopupContent']/div/table/tbody/tr[@class='b2bCustomer']"));
			System.out.println("b is :" + b);
			
			if(Common.isElementExist(driver, By.xpath(".//*[@id='customerPopupContent']/div/table/tbody/tr[@class='b2bCustomer']"))){
				List<WebElement> customerList = driver.findElements(By.xpath(".//*[@id='customerPopupContent']/div/table/tbody/tr[@class='b2bCustomer']"));
				System.out.println("-----------");
				Assert.assertTrue("After click Advanced customer search button , the customer can not be searched out", customerList.size() > 0);
				
			}else{
				System.out.println("++++++++++++");
				Assert.assertTrue("After click Advanced customer search button , the customer can not be searched out",false);
			}
			
			System.out.println("?????????????");
			//8, close the Advanced Customer Search dialogue
			Dailylog.logInfoDB(8, "close the Advanced Customer Search dialogue", Store, testName);
			
			Common.javascriptClick(driver, driver.findElement(By.xpath(".//*[@id='cboxClose']")));
//			driver.findElement(By.xpath(".//*[@id='cboxClose']")).click();
			System.out.println("1111111111");
			Thread.sleep(10000);
			//9 ,click Store ID search icon
			//Expect : Advanced Store Search dialogue will appear 
			Dailylog.logInfoDB(9, "click Store ID search icon", Store, testName);
			
			
			b2bPage.asmMode_storeIDSearchIcon.click();
			Thread.sleep(10000);
			Assert.assertTrue("Advanced Store Search dialogue does not appear",b2bPage.asmMode_advancedStoreSearchButton.isDisplayed());
			
			//10, click Search button on the Advanced Store Search dialogue
			//Expect : the results is not null ,some results can be searched out 
			Dailylog.logInfoDB(10, "click Search button on the Advanced Store Search dialogue", Store, testName);
			
			Select select_advancedStoreType = new Select(driver.findElement(By.xpath("//select[@name='advStoreType']")));
			select_advancedStoreType.selectByValue("LE");
			Thread.sleep(4000);
			b2bPage.advancedStore_FirstName.clear();
			b2bPage.advancedStore_FirstName.sendKeys("A");
			
			b2bPage.asmMode_advancedStoreSearchButton.click();
			Thread.sleep(90000);
			
			if(Common.isElementExist(driver, By.xpath("//*[@id='advStoreSearchTable']/tr[contains(@onclick,'changeStore')]"))){
				List<WebElement> list_advancedStoreSearch = driver.findElements(By.xpath("//*[@id='advStoreSearchTable']/tr[contains(@onclick,'changeStore')]"));
				
				Assert.assertTrue("After click advanced search button , the result can not be searched out !!!", list_advancedStoreSearch.size() != 0);
				
			}else{
				Assert.assertTrue("After click advanced search button , the result can not be searched out !!!", false);
			}
			
			
			//11 , close the Advanced Store Search dialogue
			Dailylog.logInfoDB(11, "close the Advanced Store Search dialogue", Store, testName);
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath(".//*[@id='cboxClose']")));
			
			//12 ,click Transaction ID search icon 
			// Expect : Advanced Transaction Search dialogue will appear 
			Dailylog.logInfoDB(12, "click Transaction ID search icon ", Store, testName);
			
			Thread.sleep(10000);
			b2bPage.asmMode_TransactionID.click();
			Thread.sleep(5000);
			Assert.assertTrue("After click Transaction ID search icon , the Advanced Transaction Search dialogue does not appear !!", b2bPage.asmMode_SearchButton.isDisplayed());
			Thread.sleep(5000);
			//13, click search button on the Advanced Transaction Search dialogue
			Dailylog.logInfoDB(13, "click search button on the Advanced Transaction Search dialogue ", Store, testName);
			
			b2bPage.asmMode_SearchButton.click();
			Thread.sleep(5000);
			
			if(Common.isElementExist(driver, By.xpath(".//*[@id='advTransactionSearchTable']/tr[contains(@onclick,'selectTransaction')]"))){
				List<WebElement> list_transaction = driver.findElements(By.xpath(".//*[@id='advTransactionSearchTable']/tr[contains(@onclick,'selectTransaction')]"));
				
				Assert.assertTrue("After click Advanced Trasncton Search button , the results can not be searched out !!", list_transaction.size() != 0);
			}else{
				Assert.assertTrue("After click Advanced Trasncton Search button , the results can not be searched out !!", false);
			}
			
			//14, close the Transaction Search dialogue
			Dailylog.logInfoDB(14, "close the Transaction Search dialogue", Store, testName);
			
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("arguments[0].click();", driver.findElement(By.xpath(".//*[@id='cboxClose']")));
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	
	

	
	

}
