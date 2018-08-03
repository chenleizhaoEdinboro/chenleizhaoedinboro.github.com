package TestScript.B2C;
import junit.framework.Assert;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;
public class NA18817Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	public NA18817Test(String store) {
		this.Store = store;
		this.testName = "NA-18817";
	}	

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2c"})
	public void NA18817(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			// step~1 : Login into HMC -->Enable country seal for JP
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Logged in into HMC",Store,testName);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			// step~2 : IsQuoteDisplayed is enabled and set Country Seal
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.HMC_IsQuoteDisplay.click();
			setCountrySeal(true);		
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);		
			// +=+=+=+=+=+Step~3 : login into JP B2C web site as an ASM
			// user+=+=+=+=+=+//
			driver.get(homePageUrl);
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			Dailylog.logInfoDB(3,"Logged in into B2C as Telesales User",Store,testName);
			// Start Session
			//3-2, Clicking Start Assisted Service Session line 
			Dailylog.logInfoDB(5, "Clicking Start Assisted Service Session line ", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			//3-3, Assign customer into ASM, then start session. Add product into cart.
			Dailylog.logInfoDB(6, "Assign customer into ASM, then start session. Add product into cart.", Store, testName);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			
			Common.sleep(2000);
			// step~4 : Add a product		
			driver.get(cartUrl);			
			B2CCommon.emptyCart(driver, b2cPage);;	
			//Common.sendFieldValue(b2cPage.Cart_QuickOrderTextBox, testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice' and @name = 'price']")).isDisplayed());
			Dailylog.logInfoDB(4,"Added 1 product using Quick order",Store,testName);
			Common.sleep(10000);
			// step~5 : Request quote and check country seal
			//QuoteAndCountrySeal(true);
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
			Dailylog.logInfoDB(5,"Checkout and type shipping information then continue.", Store,testName);
			Assert.assertTrue("Don't show the thankyou page!", Common.checkElementExists(driver, b2cPage.QuoteConfirmPage_QuoteNo, 60));
			String quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			Dailylog.logInfoDB(9, "Quote number is: " + quoteNum, Store, testName);
			Assert.assertTrue("Don't show the seal toggle!", Common.checkElementExists(driver, b2cPage.CountrySeal, 10));
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			
			// step~6 : Check quote confirmation email
			EMailCommon.createEmail(driver,mailPage,testData.B2C.getLoginID());
			//Boolean flag = checkEmail(driver, mailPage, "Gracias por tu orden "+orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			Thread.sleep(10000);
			Boolean flag = EMailCommon.checkEmailCountrySeal(driver, mailPage, "お見積", testData.B2C.getLoginID(), quoteNum, true);
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ testData.B2C.getLoginID() +", and check email: "+ "Gracias por tu orden "+quoteNum);		
			}
			// +=+=+=+=+=+=step~7 : Go to HMC and disable country
			// seal+=+=+=+=+=+=//
			Dailylog.logInfoDB(11,"Enter into HMC, open the toggle for country seal.", Store,testName);		
			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			Common.sleep(10000);
			driver.get(testData.HMC.getHomePageUrl());
			Dailylog.logInfoDB(7,"Logged in into HMC",Store,testName);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			setCountrySeal(false);		
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);
			// +=+=+=+=+=+=step~8 : Check country seal is not present on JP
			// web site+=+=+=+=//
			driver.get(homePageUrl);
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			Dailylog.logInfoDB(3,"Logged in into B2C as Telesales User",Store,testName);
			// Start Session
			//8-2, Clicking Start Assisted Service Session line 
			Dailylog.logInfoDB(5, "Clicking Start Assisted Service Session line ", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			//8-3, Assign customer into ASM, then start session. Add product into cart.
			Dailylog.logInfoDB(6, "Assign customer into ASM, then start session. Add product into cart.", Store, testName);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			
			Common.sleep(2000);
			// step~8-4 : Add a product		
			driver.get(cartUrl);			
			B2CCommon.emptyCart(driver, b2cPage);;	
			//Common.sendFieldValue(b2cPage.Cart_QuickOrderTextBox, testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice' and @name = 'price']")).isDisplayed());
			Dailylog.logInfoDB(4,"Added 1 product using Quick order",Store,testName);
			Common.sleep(10000);
			// step~8-5 : Request quote and check country seal
			//QuoteAndCountrySeal(true);
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
			Dailylog.logInfoDB(5,"Checkout and type shipping information then continue.", Store,testName);
			Assert.assertTrue("Don't show the thankyou page!", Common.checkElementExists(driver, b2cPage.QuoteConfirmPage_QuoteNo, 60));
			// Get Quote number
			quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			Dailylog.logInfoDB(9, "Order number is: " + quoteNum, Store, testName);
			Assert.assertTrue("Don't show the seal toggle!", !Common.checkElementExists(driver, b2cPage.CountrySeal, 10));
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			//driver.manage().deleteAllCookies();
			// step~6 : Check quote confirmation email
			EMailCommon.createEmail(driver,mailPage,testData.B2C.getLoginID());
			flag = EMailCommon.checkEmailCountrySeal(driver, mailPage, "お見積", testData.B2C.getLoginID(), quoteNum, false);
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ testData.B2C.getLoginID() +", and check email: "+ "Gracias por tu orden "+quoteNum);		
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
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
	
}