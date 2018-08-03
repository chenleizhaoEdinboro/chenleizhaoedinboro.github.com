package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
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


public class NA27829Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	
	
	
	public NA27829Test(String store) {
		this.Store = store;
		this.testName = "NA-27829";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c"})
	public void NA27829(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			
			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, Go to HMC -> User -> Customers -> Search telesale rep  account
			
			Dailylog.logInfoDB(1, "Go to HMC -> User -> Customers -> Search telesale rep  account", Store, testName);
			
			driver.get(hmcLoginUrl);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.home_userTab.click();
			hmcPage.userTab_customerTab.click();
			hmcPage.customer_customerIDTextBox.clear();
			hmcPage.customer_customerIDTextBox.sendKeys(testData.B2C.getTelesalesAccount());
			
			hmcPage.customer_customerSearchButtonn.click();
			Thread.sleep(8000);
			
			
			//2, Under tab "Administration" , set the testing email address to "Telesales user email" 
			
			Dailylog.logInfoDB(2, "Under tab Administration , set the testing email address to Telesales user email", Store, testName);
			Dailylog.logInfoDB(2,"Tele user email is : mengxy5@lenovo.com", Store, testName );
			
			driver.findElement(By.xpath("//span[contains(@id,'OrganizerListEntry')]/img")).click();
			
			hmcPage.customerSearch_administrationTab.click();
			
			driver.findElement(By.xpath("//input[contains(@id,'Customer.zTeleUserEmail')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'Customer.zTeleUserEmail')]")).sendKeys("mengxy5@lenovo.com");

			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			hmcPage.Home_closeSession.click();
			
			//3, Go to PREC US B2C website. Login with Telesale Account youngmeng_n/Lenovo-1
			
			Dailylog.logInfoDB(3, "Go to PREC US B2C website. Login with Telesale Account youngmeng_n/Lenovo-1", Store, testName);
			Dailylog.logInfoDB(3, "the loginID is :" + testData.B2C.getLoginID(), Store, testName);
			
			driver.get(tele_loginUrl);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			//4,Start Assisted Service Session. Select the testing customer account in "Customer ID" field. then Start Session
			Dailylog.logInfoDB(4, "Start Assisted Service Session. Select the testing customer account in Customer ID field. then Start Session", Store, testName);
			
			B2CCommon.loginASMAndStartSession(driver, b2cPage, "customer", testData.B2C.getLoginID());
			
			//5, Add one product into cart then request quote.
			Dailylog.logInfoDB(5, "Add one product into cart then request quote.", Store, testName);
			
			Thread.sleep(6000);
			
			driver.get(tele_cartUrl);
			B2CCommon.addPartNumberToCartTele(b2cPage, testData.B2C.getDefaultMTMPN());
			
			b2cPage.RequestQuoteBtn.click();
			Thread.sleep(5000);
			b2cPage.Quote_RepIDTextBox.clear();
			b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());
			Thread.sleep(3000);
			b2cPage.Quote_submitQuoteBtn.click();
			Thread.sleep(4000);
			
			String quoteNumber = b2cPage.QuoteConfirmPage_QuoteNo.getText().trim();
			Dailylog.logInfoDB(5, "created quote number is :" + quoteNumber, Store, testName);
			
			
			//6, Check the quote confirmation email in  testing customer's email box.'
			Dailylog.logInfoDB(6, "Check the quote confirmation email in  testing customer's email box.'", Store, testName);
			
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, testData.B2C.getLoginID());
			
			String emailSubject = "quote";
			String contentXpath = "//td[text()='"+quoteNumber+"']";
			
			EMailCommon.checkEmailContentinAllEmail(driver, mailPage, emailSubject, contentXpath);
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='"+quoteNumber+"']")) && 
					Common.isElementExist(driver, By.xpath("//a[contains(.,'mengxy5@lenovo.com')]")),
					"the message does not contians the necessary message");
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	

}
	

