package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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
import Pages.MailPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA17696Test extends SuperTestClass {
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private MailPage mailPage;
	private final String RESELLER_ID = "123456";
	private final static String EMAIL_ID = "testbpcto@sharklasers.com";

	public NA17696Test(String store) {
		this.Store = store;
		this.testName = "NA-17696";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA17696(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);

			// =========== Step:1 Accessing HMC URL ===========//

			Dailylog.logInfoDB(1, "Logging into HMC", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			Dailylog.logInfoDB(1, "Logged into HMC successfully!!!", Store, testName);
			Common.sleep(3000);

			Dailylog.logInfoDB(2, "Clicked on B2C Commerce link -> B2C unit ", Store, testName);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();

			Dailylog.logInfoDB(3, "Search B2C unit id : bpcto_us_softmart_unit ", Store, testName);
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			Dailylog.logInfoDB(4, "Clicking on search button", Store, testName);
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Dailylog.logInfoDB(4, "Clicked search results", Store, testName);
			Common.sleep(2000);

			// =========== Step:4 Go to Site Attribute Tab ===========//
			Dailylog.logInfoDB(4, "Clicked on site attribute", Store, testName);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(5, "Enabled Quote Id.", Store, testName);
			hmcPage.B2BUnit_enableQuoteResId.click();
			Dailylog.logInfoDB(5, "Saved setting.", Store, testName);
			Common.javascriptClick(driver, hmcPage.HMC_Save);
			Common.sleep(2000);
			hmcPage.HMC_Logout.click();
			Common.sleep(2000);

			// =========== Step:7 Accessing B2C URL ===========//

			Dailylog.logInfoDB(7, "Login to B2C site.", Store, testName);
			driver.get(testData.B2C.getloginPageUrl());
			Thread.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);

			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			Thread.sleep(2000);
			Dailylog.logInfoDB(7, "Logged into B2C site successfully.", Store, testName);
			Dailylog.logInfoDB(7, "Open cart page...", Store, testName);
			Thread.sleep(2000);
			Dailylog.logInfoDB(7, "Clearing the cart.", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Thread.sleep(2000);
			//String testProduct = testData.B2C.getDefaultMTMPN();
			String testProduct = testData.B2C.getDefaultMTMPN2();
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			// 60DFAAR1US "80NV00W6US" testData.B2C.getDefaultMTMPN()
			Thread.sleep(2000);
			Dailylog.logInfoDB(7, "Checking out the product.", Store, testName);
			b2cPage.lenovo_checkout.click();

			Thread.sleep(2000);
			if (Common.checkElementDisplays(driver, By.xpath("//*[@id='addressForm']/fieldset[1]/legend/a"), 5))
				b2cPage.shippingEdit.click();

			Dailylog.logInfoDB(7, "Filling shipping details.", Store, testName);
			fillDefaultShippingInfo(b2cPage, testData);

			if (Common.checkElementExists(driver, b2cPage.Shipping_copyAddressToBilling, 3)) {
				Common.javascriptClick(driver, b2cPage.Shipping_copyAddressToBilling);
			}

			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Dailylog.logInfoDB(7, "Clicked on continue button.", Store, testName);

			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_validateAddress, 1)) {
				Dailylog.logInfoDB(7, "Verifying the pop up.", Store, testName);
				b2cPage.Shipping_validateAddress.click();
			}

			Dailylog.logInfoDB(7, "Filling payment details.", Store, testName);
			try{
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}catch(NoSuchElementException e) {
				if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.payment_purchaseNum, 2)) {
					b2cPage.payment_purchaseNum.clear();
					b2cPage.payment_purchaseNum.sendKeys("1234567890");
					Thread.sleep(2000);
					b2cPage.payment_purchaseDate.click();
					Thread.sleep(3000);
					b2cPage.PageDriver.findElement(By.xpath("//td[contains(@class,'ui-datepicker-today')]/a")).click();
				}
			}
			
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
//			b2cPage.Payment_ContinueButton.click();
			Dailylog.logInfoDB(7, "Clicked on continue button.", Store, testName);
			Thread.sleep(2000);
			Dailylog.logInfoDB(8, "Checking Reseller Id text box.", Store, testName);
			if (Common.isElementExist(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.B2C_SummaryPage_ResellerId))))
				System.out.println("yes");
			System.out.println(Common.convertWebElementToString(b2cPage.B2C_SummaryPage_ResellerId));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.B2C_SummaryPage_ResellerId))));
			Thread.sleep(2000);
			Dailylog.logInfoDB(8, "Providing Reseller Id.", Store, testName);
			b2cPage.B2C_SummaryPage_ResellerIdTextBox.clear();
			Thread.sleep(1000);
			b2cPage.B2C_SummaryPage_ResellerIdTextBox.sendKeys(RESELLER_ID);
			Thread.sleep(2000);

			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(8, "Clicked on order button.", Store, testName);
			Assert.assertTrue(
					Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.ThankyouMessage))));

			Dailylog.logInfoDB(9, "Order placed successfully.", Store, testName);
			Common.sleep(1000);
			String ordernumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(9, "Order number is : " + ordernumber, Store, testName);
			Thread.sleep(2000);

			Dailylog.logInfoDB(10, "Login to Guerrilla Mail box.", Store, testName);
			EMailCommon.createEmail(driver, mailPage, EMAIL_ID);
			Thread.sleep(5000);
			checkEmail(driver, mailPage, ordernumber, RESELLER_ID, true);

			//// ====================*******************===================////

			Dailylog.logInfoDB(11, "Logging into HMC", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			Dailylog.logInfoDB(11, "Logged into HMC successfully!!!", Store, testName);
			Common.sleep(3000);

			Dailylog.logInfoDB(11, "Clicked on B2C Commerce link -> B2C unit ", Store, testName);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();

			Dailylog.logInfoDB(11, "Search B2C unit id : bpcto_us_softmart_unit ", Store, testName);
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			Dailylog.logInfoDB(11, "Clicking on search button", Store, testName);
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Dailylog.logInfoDB(11, "Clicked search results", Store, testName);
			Common.sleep(2000);

			// =========== Step:4 Go to Site Attribute Tab ===========//
			Dailylog.logInfoDB(11, "Clicked on site attribute", Store, testName);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(11, "Disable Quote Id.", Store, testName);
			hmcPage.B2BUnit_disableQuoteResId.click();
			Dailylog.logInfoDB(11, "Saved setting.", Store, testName);
			Common.javascriptClick(driver, hmcPage.HMC_Save);
			Common.sleep(2000);
			hmcPage.HMC_Logout.click();
			Common.sleep(2000);

			// =========== Step:12 Accessing B2C URL ===========//
			Dailylog.logInfoDB(12, "Login to B2C site.", Store, testName);
			driver.get(testData.B2C.getloginPageUrl());
			Thread.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);

			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			Thread.sleep(2000);
			Dailylog.logInfoDB(12, "Logged into B2C site successfully.", Store, testName);
			Dailylog.logInfoDB(12, "Open cart page...", Store, testName);
			// driver.get(testData.B2C.getHomePageUrl() + "/cart");
			Thread.sleep(2000);
			Dailylog.logInfoDB(12, "Clearing the cart.", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Thread.sleep(2000);
			B2CCommon.addPartNumberToCart(b2cPage, "60DFAAR1US");
			// 60DFAAR1US testData.B2C.getDefaultMTMPN()
			Thread.sleep(2000);
			Dailylog.logInfoDB(12, "Checking out the product.", Store, testName);
			b2cPage.lenovo_checkout.click();
			Thread.sleep(2000);
			if (Common.checkElementDisplays(driver, By.xpath("//*[@id='addressForm']/fieldset[1]/legend/a"), 5))
				b2cPage.shippingEdit.click();
			Thread.sleep(1000);

			Dailylog.logInfoDB(12, "Filling shipping details.", Store, testName);
			fillDefaultShippingInfo(b2cPage, testData);

			if (Common.checkElementExists(driver, b2cPage.Shipping_copyAddressToBilling, 3)) {
				Common.javascriptClick(driver, b2cPage.Shipping_copyAddressToBilling);
			}

			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Dailylog.logInfoDB(12, "Clicked on continue button.", Store, testName);

			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_validateAddress, 1)) {
				Dailylog.logInfoDB(12, "Verifying the pop up.", Store, testName);
				b2cPage.Shipping_validateAddress.click();
			}

			Dailylog.logInfoDB(12, "Filling payment details.", Store, testName);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);

			b2cPage.Payment_ContinueButton.click();
			Dailylog.logInfoDB(12, "Clicked on continue button.", Store, testName);
			Common.sleep(12000);
			Dailylog.logInfoDB(12, "Checking Reseller Id text box.", Store, testName);
			Assert.assertTrue(!Common.isElementExist(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.B2C_SummaryPage_ResellerId))));

			try {
				b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			} catch (ElementNotVisibleException e) {
				driver.findElement(By.xpath("//label[@for='Terms1']")).click();
			}

			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(13, "Clicked on order button.", Store, testName);
			Common.sleep(5000);
			Assert.assertTrue(
					Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.ThankyouMessage))));
			Dailylog.logInfoDB(13, "Order placed successfully.", Store, testName);
			Common.sleep(1000);
			ordernumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(13, "Order number is : " + ordernumber, Store, testName);
			Thread.sleep(2000);
			Dailylog.logInfoDB(13, "Login to Guerrilla Mail box.", Store, testName);
			EMailCommon.createEmail(driver, mailPage, EMAIL_ID);
			Thread.sleep(5000);
			checkEmail(driver, mailPage, ordernumber, RESELLER_ID, false);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public static void fillDefaultShippingInfo(B2CPage b2cPage, TestData testData) {
		fillShippingInfo(b2cPage, "John", "Cena", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), EMAIL_ID,
				testData.B2C.getConsumerTaxNumber());
	}

	public static void fillShippingInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String phone, String mail, String... consumerTaxNumber) {
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_FirstNameTextBox, 5)) {
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys(firstName);
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(addressline1);
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_CityTextBox, 1)) {
				b2cPage.Shipping_CityTextBox.clear();
				b2cPage.Shipping_CityTextBox.sendKeys(city);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_AddressLine3TextBox, 1)) {
				b2cPage.Shipping_AddressLine3TextBox.clear();
				b2cPage.Shipping_AddressLine3TextBox.sendKeys(city);
			}
			Select stateDropdown = new Select(b2cPage.Shipping_StateDropdown);
			stateDropdown.selectByVisibleText(state);
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_PostCodeTextBox, 1)) {
				b2cPage.Shipping_PostCodeTextBox.clear();
				b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_consumerTaxNumber, 1)) {
				b2cPage.Shipping_consumerTaxNumber.clear();
				b2cPage.Shipping_consumerTaxNumber.sendKeys(consumerTaxNumber);
			}
			b2cPage.Shipping_ContactNumTextBox.clear();
			b2cPage.Shipping_ContactNumTextBox.sendKeys(phone);
			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys(mail);
		}
	}

	public void checkEmail(WebDriver driver, MailPage mailPage, String emailSubject, String resellerId, boolean flag) {
		String subject = null;
		for (int i = 1; i <= 5; i++) {
			if (i == 5) {
				// System.out.println("ERROR !!!! Email is not received.....!");
				System.out.println("Need Manual Validate in email " + resellerId + ", and check email: ");
			} else if (Common.checkElementExists(driver, mailPage.Inbox_EmailSubject, 5) == true) {
				subject = mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: " + subject);
				if (subject.contains(emailSubject)) {
					i = 6;
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					mailPage.Inbox_EmailSubject.click();
					Common.sleep(1000);
					System.out.println("Clicked on email containing :" + emailSubject);

					Assert.assertEquals(flag, Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(mailPage.Mailbody_ResellerText))));
					if (flag) {
						Dailylog.logInfoDB(10, "Reseller Id is present in mail.", Store, testName);
						String ResellerValue = driver.findElement(By.xpath(
								".//*[@id='display_email']//td[@valign='bottom'][contains(.," + resellerId + ")]"))
								.getText();
						Dailylog.logInfoDB(10, "Reseller Id : " + ResellerValue, Store, testName);
					} else {
						Dailylog.logInfoDB(13, "Reseller Id is NOT present in mail.", Store, testName);
					}
					Common.sleep(10000);
				} else {
					System.out.println(
							"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
					Common.sleep(10000);
				}
			}
		}
	}
}
