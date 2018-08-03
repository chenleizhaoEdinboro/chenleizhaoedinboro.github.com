package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
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
import TestScript.SuperTestClass;

public class NA21832Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;


	public NA21832Test(String store) {
		this.Store = store;
		this.testName = "NA-21832";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21832(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			// Step 1 Login HMC
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Login HMC success!", Store, testName);

			// Step 2 B2C Commerce -- B2C Unit --- Site Attributes --show
			// language
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();

			hmcPage.B2CUnit_ShowLanguage.click();
			hmcPage.B2BUnit_Save.click();
			Dailylog.logInfoDB(2, "show languange choose yes", Store, testName);

			// Step 3 Login B2C site (hk Telesales account)
			driver.get(homePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			B2CCommon.DoubleLogin(driver, testData, b2cPage, loginUrl, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			
			
			Dailylog.logInfoDB(3, "Login B2C site (hk Telesales account)", Store, testName);

			// Step 4 Go to My Account page and click on 'Start Assisted Service
			// Session' link		
			b2cPage.MyAccount_Telesales.click();
			Dailylog.logInfoDB(4, "My Account Start Assisted Service Session", Store, testName);

			// Step 5 On assisted Service Mode ,fill in Customer ID use ordinary
			// user
			Thread.sleep(5000);
			b2cPage.Tele_CustomerSearch.click();
			b2cPage.Tele_CustomerSearch_customerID.sendKeys(testData.B2C.getLoginID());
			b2cPage.Tele_CustomerSearch_Search.click();
			b2cPage.Tele_CustomerSearch_SearchResult.click();

			// Step 6 Click "Start Session"
			b2cPage.Tele_StartSession.click();
			Dailylog.logInfoDB(6, "Start Session", Store, testName);

			// Step 7 choose one product to add to cart
			Thread.sleep(5000);
			driver.get(cartUrl);
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				driver.findElement(By.xpath("//a[contains(text(),'Empty cart')]")).click();
			}
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();

			Thread.sleep(5000);
			Dailylog.logInfoDB(7, "add to cart", Store, testName);

			// Step 8 Click "Lenovo Checkout"
			// JavascriptExecutor executor = (JavascriptExecutor) driver;
			b2cPage.Cart_CheckoutButton.click();
			// executor.executeScript("arguments[0].click();",
			// b2cPage.Cart_CheckoutButton);
			Dailylog.logInfoDB(8, "Lenovo Checkout", Store, testName);

			// Step 9 Fill in the shipping information, select one shipping
			// method. Click continue
			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID());
			Thread.sleep(3000);

			// continue
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
	
			// WebElement continueButton =
			// driver.findElement(By.id("checkoutForm-shippingContinueButton"));
			// executor.executeScript("arguments[0].click();", continueButton);
			Dailylog.logInfoDB(9, "Fill in the shipping information", Store, testName);
			// Step 10 Select one payment method, Type date ,then click
			// continue.
			// b2cPage.Payment_bankDeposit.click();
			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			// B2CCommon.fillPaymentAddressInfo(b2cPage, "test", "test",
			// testData.B2C.getDefaultAddressLine1(),
			// testData.B2C.getDefaultAddressCity(),
			// testData.B2C.getDefaultAddressState(),
			// testData.B2C.getDefaultAddressPostCode(),
			// testData.B2C.getDefaultAddressPhone());
			Thread.sleep(5000);
			
			System.out.println("------------------------");
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
		
			System.out.println("------------------------");
			// executor.executeScript("arguments[0].click();",
			// b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(10, "Select one payment method", Store, testName);

			// Step 11 choose email language ,need be different from the
			// language of the web site
			
			Common.scrollToElement(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			Thread.sleep(4000);
			
			Select select = new Select(b2cPage.OrderSummary_emailLanguage);
			select.selectByValue("fr");
			
			Dailylog.logInfoDB(11, "choose email language : French", Store, testName);

			// Step 12 Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");
			String orderNum = b2cPage.OrderThankyou_OrderNumberLabelNew.getText();
			System.out.println("orderNum is :" + orderNum);
			Dailylog.logInfoDB(12, "Place Order", Store, testName);

			// Step 13
			b2cPage.Thankyou_email.sendKeys(testData.B2C.getLoginID());
			b2cPage.Thankyou_sendButton.click();
			String sendMessage = b2cPage.Thankyou_sendMessage.getText();
			System.out.println("sendMessage : " + sendMessage);

			// Step 13 Check the email
			System.out.println("Checking email...");
			EMailCommon.goToMailHomepage(driver);
			driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
			driver.findElement(By.xpath(".//*[@id='inbox-id']/input"))
					.sendKeys(testData.B2C.getLoginID().replace("@sharklasers.com", ""));
			driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
			if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
				for (int i = 0; i < 6; i++) {
					System.out.println("Email not received, will check 10s later");
					Thread.sleep(10000);
					if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")))
						break;
				}
			}
			System.out.println("Order Email: "
					+ Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")));

			if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
				setManualValidateLog("Order Number: " + orderNum);
				setManualValidateLog("Mailbox: " + testData.B2C.getLoginID());

			} else {
				driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).click();

				Assert.assertFalse(Common.isElementExist(driver, By.xpath("//h3[contains(text(),'order')]")),
						"isFranch email");
				Assert.assertFalse(Common.isElementExist(driver, By.xpath("//td[contains(text(),'number')]")),
						"isFranch email");
			}

			Dailylog.logInfoDB(13, "Check the email", Store, testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
