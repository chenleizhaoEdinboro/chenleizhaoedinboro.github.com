package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA17938Test extends SuperTestClass {

	private static HMCPage hmcPage;
	private static B2CPage b2cPage;
	private static MailPage mailPage;

	public NA17938Test(String store) {
		this.Store = store;
		this.testName = "NA-17938";
	}

	public void setQuoteToggle(WebElement toggleLocator)
			throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		Thread.sleep(1000);
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		Thread.sleep(5000);
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Thread.sleep(8000);
		toggleLocator.click();
		hmcPage.Types_SaveBtn.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	public void logout() {
		Common.mouseHover(driver, b2cPage.Navigation_MyAccountIcon);
		new WebDriverWait(driver, 500)
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")));
		b2cPage.Navigation_SignOutLink.click();
	}

	public void quickAdd() throws InterruptedException {
		driver.get(testData.B2C.getHomePageUrl() + "/cart");
		
		B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
	}

	public void submitQuote(int quoteCount) throws InterruptedException {
		Common.javascriptClick(driver, b2cPage.Override_SubmitQuote);
		// b2cPage.Override_SubmitQuote.click();
		System.out.println("Quote " + quoteCount + "submitted.");
		System.out.println("Quote number " + quoteCount + "is: "
				+ b2cPage.Quote_quoteNum.getText());
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17938(ITestContext ctx) {
		try {
			this.prepareTest();
			// emailComm = new EMailCommon();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);

			// creating email
			String createdEmail = EMailCommon.getRandomEmailAddress();

			// Step 1:
			Dailylog.logInfoDB(
					1,
					"logon hmc. B2C Commerce--> B2CUnit. Find the B2Cunit, "
							+ "set EnabaleGuestQuote as False. set is quote available as yes for base store jpweb",
					Store, "NA-17938");
			setQuoteToggle(hmcPage.B2CUnit_enableGuestQuoteNo);

			// Step 2:
			Dailylog.logInfoDB(
					1,
					"go to store front, go to store front, add product into cart by quick order.",
					Store, "NA-17938");
			quickAdd();
			Thread.sleep(1000);
			
			Assert.assertFalse(b2cPage.Override_RequestQuote.isEnabled(),
					"Failed! Request quote button is not disabled.");

			// Step 3:
			Dailylog.logInfoDB(1, "logon site, and go to shopping cart.",
					Store, "NA-17938");
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			quickAdd();

			// Step 4:
			Dailylog.logInfoDB(1, "click on request quote button.", Store,
					"NA-17938");
			Assert.assertTrue(b2cPage.Override_RequestQuote.isEnabled(),
					"Failed! Request quote button is disabled for logged in user.");
			((JavascriptExecutor) driver).executeScript("scroll(0,500)");
			b2cPage.Override_RequestQuote.click();

			// Step 5:
			Dailylog.logInfoDB(5, "click on ok.", Store, "NA-17938");
			submitQuote(1);

			// Step 6:
			Dailylog.logInfoDB(
					6,
					"logon hmc. B2C Commerce--> B2CUnit. Find the B2Cunit, set EnabaleGuestQuote as true.",
					Store, "NA-17938");
			driver.manage().deleteAllCookies();
			setQuoteToggle(hmcPage.B2CUnit_enableGuestQuoteYes);

			// Step 7:
			Dailylog.logInfoDB(7, "click on ok.", Store, "NA-17938");
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			driver.manage().deleteAllCookies();
			
			quickAdd();

			// Step 8:
			Dailylog.logInfoDB(8, "click on ok.", Store, "NA-17938");
			Assert.assertTrue(b2cPage.Override_RequestQuote.isEnabled(),
					"Failed! Request quote button is disabled for logged in user.");
			b2cPage.Override_RequestQuote.click();

			// Step 9:
			Dailylog.logInfoDB(9, "click on save a one time quote.", Store,
					"NA-17938");
			
			Thread.sleep(5000);	
			Assert.assertTrue(b2cPage.Quote_createOneTimeQuote.isDisplayed());
			b2cPage.Quote_createOneTimeQuote.click();

			// Step 10:
			Dailylog.logInfoDB(10, "do not input contact email, click on yes.",
					Store, "NA-17938");
			Common.javascriptClick(driver, b2cPage.Quote_SubmitQuoteBtn);
			// b2cPage.Quote_SubmitQuoteBtn.click();
			Thread.sleep(5000);
			Assert.assertTrue(
					b2cPage.cartPage_emailIncorrectError.isDisplayed(),
					"EMail incorrect error is not displayed.");
			Assert.assertEquals(b2cPage.cartPage_emailIncorrectError.getText(),
					"Eメールアドレスを入力してください。");

			// Step 11:
			Dailylog.logInfoDB(11,
					"input a valid email address, click on yes.", Store,
					"NA-17938");
			b2cPage.cartPage_quoteContactEmail.clear();
			b2cPage.cartPage_quoteContactEmail.sendKeys(createdEmail);
			submitQuote(2);

			// Step 12:
			Dailylog.logInfoDB(
					12,
					"go to shopping cart, add product into cart by quick order.",
					Store, "NA-17938");
			quickAdd();

			// Step 13:
			Dailylog.logInfoDB(13, "click on request quote button.", Store,
					"NA-17938");
			b2cPage.Override_RequestQuote.click();

			// Step 14:
			Dailylog.logInfoDB(14, "click on login", Store, "NA-17938");
			Thread.sleep(5000);
			b2cPage.cartPage_quoteLoginButton.click();
			driver.get(testData.B2C.getloginPageUrl());
			Thread.sleep(1000);

			// Step 15:
			Dailylog.logInfoDB(15, "input account, and logon.", Store,
					"NA-17938");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			quickAdd();

			// Step 16:
			Dailylog.logInfoDB(16, "click on YES.", Store, "NA-17938");
			b2cPage.Override_RequestQuote.click();
			submitQuote(3);

			// Step 17:
			Dailylog.logInfoDB(
					17,
					"go to shopping cart, add product into cart by quick order.",
					Store, "NA-17938");
			driver.manage().deleteAllCookies();
			quickAdd();

			// Step 18:
			Dailylog.logInfoDB(
					18,
					"click on Lenovo checkout, on the login page, click on start checkout",
					Store, "NA-17938");
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(5000);
			try{
			b2cPage.Checkout_StartCheckoutButton.click();}catch(Exception e){System.out.println("start checkout not exist");}
			// Step 19:
			Dailylog.logInfoDB(
					19,
					"input delivery address, select shipping method, click on Continue",
					Store, "NA-17938");
			B2CCommon.fillShippingInfo(b2cPage, "Batman", "Begins",
					testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(),
					testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(),
					testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(),
					testData.B2C.getConsumerTaxNumber());
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);

			// Step 20:
			Dailylog.logInfoDB(20, "click on request quote button.", Store,
					"NA-17938");
			Common.javascriptClick(driver, b2cPage.Override_RequestQuote);
			// b2cPage.Override_RequestQuote.click();

			// Step 21:
			Dailylog.logInfoDB(21, "click on save a one time quote.", Store,
					"NA-17938");
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Quote_createOneTimeQuote);
			//b2cPage.Quote_createOneTimeQuote.click();

			// Step 22:
			Dailylog.logInfoDB(22,
					"input a valid email address, click on yes.", Store,
					"NA-17938");
			Thread.sleep(5000);
			b2cPage.cartPage_quoteContactEmail.clear();
			b2cPage.cartPage_quoteContactEmail.sendKeys(createdEmail);
			submitQuote(4);
			EMailCommon.createEmail(driver, mailPage, createdEmail);
			EMailCommon.checkIfEmailReceived(driver, mailPage,
					"お見積");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
