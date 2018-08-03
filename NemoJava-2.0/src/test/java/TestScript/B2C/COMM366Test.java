package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM366Test extends SuperTestClass {

	B2CPage b2cPage = null;
	private String testProduct;
	private String loginID;
	private String pwd;

	public COMM366Test(String store) {
		this.Store = store;
		this.testName = "COMM-366";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage, String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "payment", "p2", "b2c" })
	public void COMM366(ITestContext ctx) {
		try {
			this.prepareTest();

			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);

			hmcPage.enable3DSecure_yes.click();
			hmcPage.bypass3DSecure_no.click();
			hmcPage.Common_SaveButton.click();

			Common.sleep(5000);
			hmcPage.HMC_Logout.click();

			b2cPage = new B2CPage(driver);
			gotoPayment();
			Dailylog.logInfoDB(6, "Clicked Payment_ContinueButton.", Store, testName);

			Assert.assertTrue("Bypass Active 3DS for ASM set no ifram not show",
					Common.checkElementExists(driver, b2cPage.PageDriver.findElement(By.id("hidden3DSFrame0")), 5));
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);

			hmcPage.bypass3DSecure_yes.click();
			hmcPage.Common_SaveButton.click();

			Common.sleep(5000);
			hmcPage.HMC_Logout.click();
			gotoPayment();
			Dailylog.logInfoDB(9, "Clicked Payment_ContinueButton.", Store, testName);
			Assert.assertTrue("Bypass Active 3DS for ASM set no ifram not show",
					Common.checkElementExists(driver, b2cPage.PageDriver.findElement(By.id("hidden3DSFrame0")), 5));

			b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("hidden3DSFrame0")));
//			b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("authWindow")));
			b2cPage.threeDSpassword.sendKeys("1234");
			b2cPage.threeDSsubmit.click();
			Dailylog.logInfoDB(9, "3dS Submit .", Store, testName);
			// Place Order
			b2cPage.PageDriver.switchTo().defaultContent();
			Thread.sleep(2000);
			Dailylog.logInfoDB(9, "place order .", Store, testName);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			String orderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(10, " order number : " + orderNumber, Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(driver, hmcPage, orderNumber);
			hmcPage.PaymentAndDeliveryTab.click();
			Common.rightClick(driver, hmcPage.PaymentInfoLabel);
			driver.findElement(By.id(
					"Content/GenericReferenceEditor[in Content/Attribute[Order.paymentInfo]]_!open_editor_internal_true_label"))
					.click();
			driver.findElement(By.id("Content/EditorTab[CreditCardPaymentInfo.administration]_span")).click();
			if (!driver
					.findElement(By.id(
							"Content/BooleanEditor[in Content/Attribute[CreditCardPaymentInfo.is3DSecure]]_true"))
					.isSelected())
				Assert.fail("is3DSecure is not checked in HMC order!");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public boolean isElementExsit(WebDriver driver, By locator) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(locator);
			flag = null != element;
		} catch (NoSuchElementException e) {
			// System.out.println( "this category has only one page");
		}
		return flag;
	}

	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(date);
		return dateString;
	}

	private void gotoPayment() {
		driver.get(testData.B2C.getloginPageUrl());
		// testProduct = testData.B2C.getDefaultMTMPN();
		testProduct = "4X40K09936";
		Common.sleep(2500);

		B2CCommon.handleGateKeeper(b2cPage, testData);
		loginID = testData.B2C.getLoginID();
		pwd = testData.B2C.getLoginPassword();
		//Handle advertisement
		if(Common.checkElementDisplays(driver, By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']"), 3)){
			driver.findElement(By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']")).click();
		}
		B2CCommon.login(b2cPage, loginID, pwd);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		HandleJSpring(driver, b2cPage, loginID, pwd);

		Common.sleep(2000);
		b2cPage.Navigation_CartIcon.click();
		if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
			b2cPage.Navigation_ViewCartButton.click();
		Common.sleep(2000);
		if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testProduct);
			b2cPage.Cart_AddButton.click();

		}

		Common.sleep(2000);
		Dailylog.logInfoDB(2, "Clicked checkout.", Store, testName);
		if(Common.checkElementExists(driver, b2cPage.PageDriver.findElement(By.xpath("//iframe[@data-test-id='ChatWidgetWindow-iframe']")), 5)){
			
			b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.xpath("//iframe[@data-test-id='ChatWidgetWindow-iframe']")));
			driver.findElement(By.xpath("(//div[contains(@class,'MinimizeButton')]/div)[last()]")).click();
			
			b2cPage.PageDriver.switchTo().defaultContent();
			Common.sleep(2000);
		}
		
		b2cPage.Cart_CheckoutButton.click();
		
		String tempEmail = EMailCommon.getRandomEmailAddress();
		String firstName = Common.getDateTimeString();
		String lastName = Common.getDateTimeString();

		// Fill shipping info
		Actions actions = new Actions(driver);

		if (isElementExsit(driver,
				By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]"))) {
			actions.moveToElement(b2cPage.EditAddress).click().perform();
		}

		B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail);
		Dailylog.logInfoDB(2, "Clicked Shipping_ContinueButton.", Store, testName);
		Common.sleep(5000);
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		// if (Common.checkElementExists(driver,
		// b2cPage.Shipping_AddressMatchOKButton, 10)) {
		// b2cPage.Shipping_AddressMatchOKButton.click();
		//
		// }
		if (Common.isElementExist(driver, By.id("address_sel"))) {
			// US
			Select dropdown = new Select(driver.findElement(By.id("address_sel")));
			dropdown.selectByIndex(1);
			b2cPage.Shipping_AddressMatchOKButton.click();
		} else if (Common.isElementExist(driver, By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
			if (b2cPage.Shipping_validateAddressItem.isDisplayed()) {
				// USEPP
				b2cPage.Shipping_validateAddressItem.click();
			}
			// USBPCTO
			b2cPage.Shipping_AddressMatchOKButton.click();
		}
		if (Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']"))) {
			// Fill payment info
			Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_CARD']")));
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_CreditCardRadioButton);
			b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
			try {
				Common.waitElementVisible(b2cPage.PageDriver, b2cPage.Payment_CardTypeDropdown);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
			dropdown.selectByVisibleText("Visa");
			b2cPage.Payment_CardNumberTextBox.clear();
			b2cPage.Payment_CardNumberTextBox.sendKeys("4000000000000002");

			if (!b2cPage.Payment_CardMonthTextBox.getTagName().toLowerCase().equals("select")) {
				b2cPage.Payment_CardMonthTextBox.clear();
				b2cPage.Payment_CardMonthTextBox.sendKeys("12");
				b2cPage.Payment_CardYearTextBox.clear();
				b2cPage.Payment_CardYearTextBox.sendKeys("20");
			} else {
				// dropdown month and year
				dropdown = new Select(b2cPage.Payment_CardMonthTextBox);
				dropdown.selectByVisibleText("12");
				dropdown = new Select(b2cPage.Payment_CardYearTextBox);
				dropdown.selectByVisibleText("2020");
			}

			b2cPage.Payment_SecurityCodeTextBox.clear();
			b2cPage.Payment_SecurityCodeTextBox.sendKeys("123");
			b2cPage.PageDriver.switchTo().defaultContent();
			Common.sleep(2000);
			b2cPage.Payment_CardHolderNameTextBox.clear();
			b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
			
			
		} else {
			Assert.fail("no card payment");
		}

		Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
	}
}
