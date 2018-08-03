package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA18479Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String email = "testjp";
	String emailAddress = "testjp@sharklasers.com";
	String totalPrice;
	
	public NA18479Test(String store, String productNo) {
		this.Store = store;
		this.testName = "NA-18479";
		this.productNo = productNo;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c"})
	public void NA18479(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			
			Dailylog.logInfoDB(1,"Enter into HMC, open the toggle for country seal.", Store,testName);		
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.HMC_IsQuoteDisplay.click();
			setCountrySeal(true);		
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);
			requestQuote();
			Assert.assertTrue("Don't show the thankyou page!", Common.checkElementExists(driver, b2cPage.QuoteConfirmPage_QuoteNo, 60));
			// Get Quote number
			String quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			Dailylog.logInfoDB(9, "Quote number is: " + quoteNum, Store, testName);
			Assert.assertTrue("Don't show the seal toggle!", Common.checkElementExists(driver, b2cPage.CountrySeal, 10));
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			//driver.manage().deleteAllCookies();
			EMailCommon.createEmail(driver,mailPage,email);
			//Boolean flag = checkEmail(driver, mailPage, "Gracias por tu orden "+orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			Thread.sleep(10000);
			Boolean flag = EMailCommon.checkEmailCountrySeal(driver, mailPage, "お見積", email+"@sharklasers.com", quoteNum, true);
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ email+"@sharklasers.com" +", and check email: "+ "Gracias por tu orden "+quoteNum);		
			}
			
			Dailylog.logInfoDB(11,"Enter into HMC, open the toggle for country seal.", Store,testName);		
			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			Common.sleep(10000);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			setCountrySeal(false);		
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);		
			requestQuote();
			Assert.assertTrue("Don't show the thankyou page!", Common.checkElementExists(driver, b2cPage.QuoteConfirmPage_QuoteNo, 60));
			// Get Quote number
			quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			Dailylog.logInfoDB(9, "Order number is: " + quoteNum, Store, testName);
			Assert.assertTrue("Don't show the seal toggle!", !Common.checkElementExists(driver, b2cPage.CountrySeal, 10));
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			//driver.manage().deleteAllCookies();
			EMailCommon.createEmail(driver,mailPage,email);
			flag = EMailCommon.checkEmailCountrySeal(driver, mailPage, "お見積", email+"@sharklasers.com", quoteNum, false);
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ email+"@sharklasers.com" +", and check email: "+ "Gracias por tu orden "+quoteNum);		
			}
			}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}	
	
	public void requestQuote() throws Exception{
		Dailylog.logInfoDB(2,"Login JP public, and product", Store,testName);
		//driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getHomePageUrl());
		System.out.println(driver.getCurrentUrl());
		B2CCommon.handleGateKeeper(b2cPage, testData);
		driver.get(testData.B2C.getloginPageUrl());
		B2CCommon.login(b2cPage, emailAddress, "1q2w3e4r");
		//b2cPage.Navigation_CartIcon.click();
		driver.get(testData.B2C.getHomePageUrl() + "/cart");
		B2CCommon.emptyCart(driver, b2cPage);
		//B2CCommon.addPartNumberToCart(b2cPage,"65CCAAC6JP");
		B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
		B2CCommon.clickRequestQuote(b2cPage);
		Common.sleep(3000);
		try {
			b2cPage.Quote_RepIDTextBox.clear();
			b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());
		} catch (Exception e3) {
			Dailylog.logInfo("Dont sendkeys requestQuote");
		}
		Common.waitElementClickable(driver, b2cPage.Quote_submitQuoteBtn, 5);		
		b2cPage.Quote_submitQuoteBtn.click();		
		Dailylog.logInfoDB(5,"Request Quote Successfully", Store,testName);
	}
	
	public void setCountrySeal(boolean enable){
		// Set Country Seal
		if (enable) {
			By enableCountrySeal = By
					.xpath("//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.enableCountrySeal]]_true']");

			if (!driver.findElement(enableCountrySeal).isSelected()) {
				driver.findElement(enableCountrySeal).click();
			}
		}else {
			By enableCountrySeal = By
					.xpath("//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.enableCountrySeal]]_false']");

			if (!driver.findElement(enableCountrySeal).isSelected()) {
				driver.findElement(enableCountrySeal).click();
			}
		}
	
	}

	

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}