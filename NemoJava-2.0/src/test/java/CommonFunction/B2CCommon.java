package CommonFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import CommonFunction.DesignHandler.CTOandPB;
import CommonFunction.DesignHandler.Payment;
import Pages.B2CPage;
import TestData.TestData;
import junit.framework.Assert;

public class B2CCommon {

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Handle all kinds of gate keeper if exist
	 */
	public static boolean handleGateKeeper(B2CPage b2cPage, TestData testData) {
		boolean isGateKeeperAppeared = false;
		if (b2cPage.PageDriver.getCurrentUrl().endsWith("PasscodeGatekeeper")) {
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.clear();
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.sendKeys(testData.B2C.getGateKeeperPassCode());
			b2cPage.GateKeeper_LoginButton.click();
			isGateKeeperAppeared = true;
		} else if (b2cPage.PageDriver.getCurrentUrl().endsWith("SerialNumberGatekeeper")) {
			b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.clear();
			b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.sendKeys(testData.B2C.getGateKeeperSerialNmuber());
			b2cPage.SerialNumberGateKeeper_LastNameTextBox.clear();
			b2cPage.SerialNumberGateKeeper_LastNameTextBox.sendKeys(testData.B2C.getGateKeeperSerialLastName());
			b2cPage.GateKeeper_LoginButton.click();
			isGateKeeperAppeared = true;
		} else if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys(testData.B2C.getGateKeeperRegistrationLenovoID());
			b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
			b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys(testData.B2C.getGateKeeperRegistrationPassword());
			b2cPage.RegisterGateKeeper_LoginButton.click();
			isGateKeeperAppeared = true;
		}
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.HomePage_CloseAdvButton, 5))
			b2cPage.HomePage_CloseAdvButton.click();
		return isGateKeeperAppeared;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Handle all kinds of gate keeper if exist
	 */
	public static void handleTeleGateKeeper(B2CPage b2cPage, TestData testData) {
		if (b2cPage.PageDriver.getCurrentUrl().endsWith("PasscodeGatekeeper")) {
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.clear();
			b2cPage.PasscodeGateKeeper_PasscodeTextBox.sendKeys(testData.B2C.getGateKeeperPassCode());
			b2cPage.GateKeeper_LoginButton.click();
		} else if (b2cPage.PageDriver.getCurrentUrl().endsWith("SerialNumberGatekeeper")) {
			b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.clear();
			b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.sendKeys(testData.B2C.getGateKeeperSerialNmuber());
			b2cPage.SerialNumberGateKeeper_LastNameTextBox.clear();
			b2cPage.SerialNumberGateKeeper_LastNameTextBox.sendKeys(testData.B2C.getGateKeeperSerialLastName());
			b2cPage.GateKeeper_LoginButton.click();
		}
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.HomePage_CloseAdvButton, 5))
			b2cPage.HomePage_CloseAdvButton.click();
	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void login(B2CPage b2cPage, String emailId, String password) {
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.SMB_LoginButton, 1)) {
			b2cPage.SMB_LoginButton.click();
		}
		b2cPage.Login_LenovoIDTextBox.clear();
		b2cPage.Login_LenovoIDTextBox.sendKeys(emailId);
		b2cPage.Login_LenovoPasswordTextBox.clear();
		b2cPage.Login_LenovoPasswordTextBox.sendKeys(password);
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Login_LogInButton);
		// b2cPage.Login_LogInButton.click();
	}

	

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
		try {
			Common.waitElementClickable(b2cPage.PageDriver,
					b2cPage.PageDriver.findElement(By.xpath("//div[@data-p-code='" + partNum + "']")), 10);
		} catch (NoSuchElementException e) {
			Assert.fail("PartNumber(" + partNum + ") is invalid, need to update test data!");
		}
	}

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void addPartNumberToCartTele(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		try {
			Common.waitElementClickable(b2cPage.PageDriver,
					b2cPage.PageDriver.findElement(By.xpath("//*[text()='" + partNum + "']")), 5);
		} catch (NoSuchElementException e) {
			Assert.fail("PartNumber(" + partNum + ") is invalid, need to update test data!");
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage, TestData
	 * @Usage Fill shipping info with default address from TestData
	 */
	public static void fillDefaultShippingInfo(B2CPage b2cPage, TestData testData) {
		fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
				testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Fill shipping info with parameters
	 */
	public static void fillShippingInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String phone, String mail, String... consumerTaxNumber) {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Shipping_FirstNameTextBox, 3)) {
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

			// BR
			if (Common.isElementExist(b2cPage.PageDriver, By.id("building"))) {
				b2cPage.PageDriver.findElement(By.id("building")).clear();
				b2cPage.PageDriver.findElement(By.id("building")).sendKeys(city);
			}
			if (Common.isElementExist(b2cPage.PageDriver, By.id("line2"))) {
				b2cPage.PageDriver.findElement(By.id("line2")).clear();
				b2cPage.PageDriver.findElement(By.id("line2")).sendKeys(addressline1);
			}
			if (Common.isElementExist(b2cPage.PageDriver, By.id("district"))) {
				b2cPage.PageDriver.findElement(By.id("district")).clear();
				b2cPage.PageDriver.findElement(By.id("district")).sendKeys("São João Batista");
			}
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Fill default payment info (Visa 41111111) work for both old and
	 *        new UI
	 */
	public static void fillDefaultPaymentInfo(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Thread.sleep(2000);
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_CreditCardRadioButton);
		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		Common.waitElementVisible(b2cPage.PageDriver, b2cPage.Payment_CardTypeDropdown);
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText("Visa");
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys("4111111111111111");

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
		Thread.sleep(2000);
		b2cPage.Payment_CardHolderNameTextBox.clear();
		b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
		// TW invoice
		if (Common.isElementExist(b2cPage.PageDriver, By.xpath(".//select[contains(@id,'invoiceTypeTW')]"))) {
			b2cPage.PageDriver
					.findElement(By.xpath(".//select[contains(@id,'invoiceTypeTW')]/option[contains(.,'紙本發票')]"))
					.click();
		}
		// if PO is mandatory
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.payment_purchaseNum, 2)) {
			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");
			Thread.sleep(2000);
			b2cPage.payment_purchaseDate.click();
			Thread.sleep(3000);
			b2cPage.PageDriver.findElement(By.xpath("//td[contains(@class,'ui-datepicker-today')]/a")).click();
		}
	}

	/**
	 * @throws InterruptedException
	 * @Owner Songli
	 * @Parameter
	 * @Usage Fill payment address info with parameters
	 */
	public static void fillPaymentAddressInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String phone) throws InterruptedException {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_FirstNameTextBox, 3)) {
			if (Common.isEditable(b2cPage.Payment_FirstNameTextBox)) {
				b2cPage.Payment_FirstNameTextBox.clear();
				b2cPage.Payment_FirstNameTextBox.sendKeys(firstName);
			}
			if (Common.isEditable(b2cPage.Payment_LastNameTextBox)) {
				b2cPage.Payment_LastNameTextBox.clear();
				b2cPage.Payment_LastNameTextBox.sendKeys(lastName);
			}
			if (Common.isEditable(b2cPage.Payment_AddressLine1TextBox)) {
				b2cPage.Payment_AddressLine1TextBox.clear();
				b2cPage.Payment_AddressLine1TextBox.sendKeys(addressline1);
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_CityTextBox, 1)) {
				if (Common.isEditable(b2cPage.Payment_CityTextBox)) {
					b2cPage.Payment_CityTextBox.clear();
					b2cPage.Payment_CityTextBox.sendKeys(city);
				}
			} else if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_City2TextBox, 1)) {
				if (Common.isEditable(b2cPage.Payment_City2TextBox)) {
					b2cPage.Payment_City2TextBox.clear();
					b2cPage.Payment_City2TextBox.sendKeys(city);
				}
			}
			if (Common.isEditable(b2cPage.Payment_StateDropdown)) {
				Select dropdown = new Select(b2cPage.Payment_StateDropdown);
				dropdown.selectByVisibleText(state);
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_PostCodeTextBox, 1)) {
				if (Common.isEditable(b2cPage.Payment_PostCodeTextBox)) {
					b2cPage.Payment_PostCodeTextBox.clear();
					b2cPage.Payment_PostCodeTextBox.sendKeys(postCode);
				}
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_ContactNumTextBox, 1)) {
				if (Common.isEditable(b2cPage.Payment_ContactNumTextBox)) {
					b2cPage.Payment_ContactNumTextBox.clear();
					b2cPage.Payment_ContactNumTextBox.sendKeys(phone);
				}
			}
		}
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void clickPlaceOrder(B2CPage b2cPage) {
		if (!b2cPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com"))
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.OrderSummary_PlaceOrderButton);
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void clickRequestQuote(B2CPage b2cPage) {
		if (!b2cPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com"))

			b2cPage.Quote_requestBtn.click();
	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static boolean checkOrderInHistroyPage(B2CPage b2cPage, String orderNum) {
		return Common.checkElementExists(b2cPage.PageDriver,
				b2cPage.PageDriver.findElement(By.xpath(".//a[contains(@href,'my-account/order/" + orderNum + "')]")),
				60);
	}

	/**
	 * @Owner Songli
	 * @Parameter:
	 * @Usage:
	 */
	public static String getOrderNumberFromThankyouPage(B2CPage b2cPage) {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.OrderThankyou_OrderNumberLabel, 5))
			return b2cPage.OrderThankyou_OrderNumberLabel.getText(); // For
																		// normal
																		// payment
																		// methods
		else if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.OrderThankyou_OrderNumberLabelNew, 5))
			return b2cPage.OrderThankyou_OrderNumberLabelNew.getText(); // For
																		// JACCS?
		else
			return b2cPage.PageDriver.findElement(By.xpath("(//span[contains(text(),'ご注文番号')]/../span)[2]")).getText();
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Place order from cart page with default shipping and payment (Visa
	 *        41111111111) method
	 */
	public static String placeOrderFromClickingStartCheckoutButtonInCart(WebDriver driver, B2CPage b2cPage,
			TestData testData, String... store) throws Exception {
		b2cPage.Cart_CheckoutButton.click();
		Thread.sleep(3000);
		if (store != null && store.length > 0 && (store[0] == "FR" || store[0] == "IE" || store[0] == "GB")) {
			if (driver.getCurrentUrl().contains("digitalriver.com")) {
				// DR checkout process
				driver.findElement(By.id("email")).sendKeys("auto738@yopmail.com");
				driver.findElement(By.id("billingName1")).sendKeys("autofirstname");
				driver.findElement(By.id("billingName2")).sendKeys("autolastname");
				driver.findElement(By.id("billingAddress1")).sendKeys("autoaddress");
				driver.findElement(By.id("billingCity")).sendKeys("autocity");
				driver.findElement(By.id("billingPostalCode")).sendKeys("1234");
				driver.findElement(By.id("billingPhoneNumber")).sendKeys("1234");
				driver.findElement(By.id("ccNum")).sendKeys("4012888888881881");
				driver.findElement(By.id("ccMonth")).sendKeys("February");
				driver.findElement(By.id("ccYear")).sendKeys("2022");
				driver.findElement(By.id("cardSecurityCode")).sendKeys("881");
				driver.findElement(By.id("checkoutButton")).click();
				driver.findElement(By.id("tosAccepted")).click();
				driver.findElement(By.id("submitBottom")).click();
				return driver.findElement(By.id("dr_orderNumber")).getText();
			} else {
				throw new Exception("DR payment does not exist! ");
				// return null;
			}
		} else if (driver.getCurrentUrl().contains("digitalriver.com")) {
			driver.findElement(By.id("email")).sendKeys("auto738@yopmail.com");
			driver.findElement(By.id("billingName1")).sendKeys("autofirstname");
			driver.findElement(By.id("billingName2")).sendKeys("autolastname");
			driver.findElement(By.id("billingAddress1")).sendKeys("autoaddress");
			driver.findElement(By.id("billingCity")).sendKeys("autocity");
			driver.findElement(By.id("billingPostalCode")).sendKeys("1234");
			driver.findElement(By.id("billingPhoneNumber")).sendKeys("1234");
			driver.findElement(By.id("ccNum")).sendKeys("4111111111111111");
			driver.findElement(By.id("ccMonth")).sendKeys("February");
			driver.findElement(By.id("ccYear")).sendKeys("2022");
			driver.findElement(By.id("cardSecurityCode")).sendKeys("123");
			driver.findElement(By.id("checkoutButton")).click();
			driver.findElement(By.id("tosAccepted")).click();
			driver.findElement(By.id("submitBottom")).click();
			return driver.findElement(By.id("dr_orderNumber")).getText();
		} else {
			// If already login in register gate keeper, then no
			// StartCheckout button and ShippingInfo is pre-filled
			String tempEmail = EMailCommon.getRandomEmailAddress();
			String firstName = Common.getDateTimeString();
			String lastName = Common.getDateTimeString();
			if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			// Fill shipping info
			if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail,
						testData.B2C.getConsumerTaxNumber());
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			// b2cPage.Shipping_ContinueButton.click();
			handleAddressVerify(driver, b2cPage);
			// Fill payment info
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			// Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			Payment.clickPaymentContinueButton(b2cPage);

			handleVisaVerify(b2cPage);
			// Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			// Get Order number
			return getOrderNumberFromThankyouPage(b2cPage);
		}
	}

	public static void handleAddressVerify(WebDriver driver, B2CPage b2cPage) {
		if (Common.checkElementDisplays(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
			b2cPage.Shipping_AddressMatchOKButton.click();
		if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 1))
			b2cPage.ValidateInfo_SkipButton.click();
		if (!Common.checkElementInvisible(driver, b2cPage.ValidateInfo_UseSuggestButton, 1)) {

			if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_customertype, 5)) {
				b2cPage.ValidateInfo_customertype.click();
			}
			b2cPage.ValidateInfo_UseSuggestButton.click();
		}

	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void handleVisaVerify(B2CPage b2cPage) throws InterruptedException {
		if (Common.isElementExist(b2cPage.PageDriver, By.id("hidden3DSFrame0"))) {
			Payment.fill3DS(b2cPage);
		}
	}

	/**
	 * @Owner Zhongxu
	 * @Parameter:
	 * @Usage:
	 */
	public static String getCTOProduct(String pageUrl, String store) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("AU", pageUrl + "/p/20L5CTO1WWENAU1");
		map.put("NZ", pageUrl + "/p/20ENCTO1WWENNZ0");
		map.put("US", pageUrl + "/p/20ERCTO1WWENUS0");
		map.put("USEPP", pageUrl + "/p/20HDCTO1WWENUS2");
		map.put("CA", pageUrl + "/p/20H9CTO1WWENCA0");
		map.put("CA_AFFINITY", pageUrl + "/p/20GQCTO1WWENCA0");
		map.put("US_BPCTO", pageUrl + "/p/20GQCTO1WWENUS1");
		map.put("HK", pageUrl + "/p/20HHCTO1WWENHK0");
		map.put("HKZF", pageUrl + "/p/20HHCTO1WWENHK0");
		map.put("JP", pageUrl + "/p/20M5CTO1WWJAJP1");
		map.put("SG", pageUrl + "/p/20HDCTO1WWENSG0");
		map.put("GB", pageUrl + "/p/20KHCTO1WWENGB0");
		map.put("IE", pageUrl + "/p/20J6CTO1WWENIE0");
		map.put("US_OUTLET",
				pageUrl + "/commercial-notebook/thinkpad-classic/thinkpad-x-series-2nd-gen/11e-3rd-Gen/p/20GFX004US");
		map.put("TW", pageUrl + "/p/20HDCTO1WWZHTW0");
		map.put("CO", pageUrl + "/laptops/thinkpad/serie-e/ThinkPad-E470/p/22TP2TEE470");
		map.put("BR", pageUrl + "/laptops/lenovo/serie-b/Lenovo-B320-14IKB/p/88LG80B0951");
		map.put("US_SMB", pageUrl + "/p/20HDCTO1WWENUS2");
		return map.get(store);
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Add a product (work for both CTO and MTM product) into cart from
	 *        PDP page
	 */
	public static void addProductToCartFromPDPPage(WebDriver driver) throws InterruptedException {

		clickAddtocartOrCustomizeOnPDP(driver);

		if (Common.isElementExist(driver, By.xpath(".//*[contains(@class,'stepsItem-wrapper')]"))) {
			// New CTO PB

			CTOandPB.addToCartFromCTONew(driver);
		} else {
			// Old CTO PB

			CTOandPB.addToCartFromCTOOld(driver);
		}

	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Add a product (work for both CTO and MTM product) into cart from
	 *        PDP page
	 */
	public static String addProductToCartFromPDPPage(WebDriver driver, boolean ifChangeCV, boolean ifAddWarranty,
			boolean ifAddSoftware, boolean ifAddAccesory) throws InterruptedException {

		clickAddtocartOrCustomizeOnPDP(driver);
		if (Common.isElementExist(driver, By.xpath(".//*[contains(@class,'stepsItem-wrapper')]"))) {
			// New CTO PB
			return CTOandPB.addToCartFromCTONew(driver, ifChangeCV, ifAddWarranty, ifAddSoftware, ifAddAccesory);
		} else {
			// Old CTO PB
			return CTOandPB.addToCartFromCTOOld(driver, ifChangeCV, ifAddWarranty, ifAddSoftware, ifAddAccesory);
		}
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void clearTheCart(WebDriver driver, B2CPage b2cPage, TestData testData) {
		String HomePage_ViewCartLink = "//div/a[contains(@id,'ViewCart')]";
		String CartPage_EmpCart = "//a[contains(@href,'emptyCart')]";
		String CartPage_EmpCart_NewUI = "//form[contains(@action,'emptyCart')]/a";
		String deleteCart = "//input[contains(@onclick,'emptyCart')]";

		String pageURL = driver.getCurrentUrl();
		if (!pageURL.contains("cart")) {
			b2cPage.HomePage_CartIcon.click();
			if (Common.isElementExist(driver, By.xpath(HomePage_ViewCartLink))) {
				driver.findElement(By.xpath(HomePage_ViewCartLink)).click();
			}
		}
		Common.sleep(3000);
		if (Common.isElementExist(driver, By.xpath(CartPage_EmpCart))) {
			Common.javascriptClick(driver, driver.findElement(By.xpath(CartPage_EmpCart)));
			// driver.findElement(By.xpath(CartPage_EmpCart)).click();
			System.out.println("Cart is cleared.");
			Common.sleep(2000);
		} else if (Common.isElementExist(driver, By.xpath(CartPage_EmpCart_NewUI))) {
			Common.javascriptClick(driver, driver.findElement(By.xpath(CartPage_EmpCart_NewUI)));
			if (Common.checkElementExists(driver, driver.findElement(By.xpath(deleteCart)), 60)) {
				Common.javascriptClick(driver, driver.findElement(By.xpath(deleteCart)));
			}
			System.out.println("Cart is cleared.");
			Common.sleep(2000);
		} else {
			System.out.println("Cart is empty");
		}
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void addToCartB2C(int laptopCounter, WebDriver driver, String URL, String ButtonLocator)
			throws InterruptedException {
		String LaptopsPage_LaptopTitle = "(//div/h1[@class='seriesPreview-title'])";
		String LaptopsSubSeriesPage_ModalName = "(//div/div/h3/a)";
		String PDPPage_AddToCart = "(//button/span[contains(@id,'addToCart')])[1]";
		String ConfigurationPage_ContinueButton = "//*[@id='product-builder-form']/descendant::div/button//span[1]";
		String CartPage_AddToCartBtn = "//span[contains(@class,'button-text')]";
		String PLPPage_ViewCurrentModels = ".//*[@id='tab-a-nav-currentmodels']/span";
		String LaptopsSubSeriesPage_pickALaptopBrand = ".//*[@id='viewmodel-container']/h3[contains(@class,'viewmodel')][2]";
		String NewUI_LaptopSubSeriesPage_ModelName = "(//div[@class='model']//div[contains(@class,'product')]/a)";
		String differentUI_ModelDetails = "(//div[@class='product-detailslink']/a)[1]";
		String differentUI_viewProduct = "(//div[contains(@class,'facetedResults')]/a[contains(@class,'button')])";
		final int totalNoOfLaptops;

		// For New UI
		if (Common.checkElementDisplays(driver, By.xpath(LaptopsSubSeriesPage_pickALaptopBrand), 4)) {
			totalNoOfLaptops = driver.findElements(By.xpath(NewUI_LaptopSubSeriesPage_ModelName)).size();
			WebElement clickProduct = driver
					.findElement(By.xpath(NewUI_LaptopSubSeriesPage_ModelName + "[" + laptopCounter + "]"));
			Common.javascriptClick(driver, clickProduct);
		}
		// For US OUTLET
		else if (Common.checkElementDisplays(driver, By.xpath(differentUI_ModelDetails), 4)) {
			totalNoOfLaptops = driver.findElements(By.xpath(differentUI_viewProduct)).size();
			WebElement clickProduct = driver.findElement(By.xpath(differentUI_viewProduct + "[" + laptopCounter + "]"));
			Common.javascriptClick(driver, clickProduct);
		}
		// For Old UI
		else {
			totalNoOfLaptops = driver.findElements(By.xpath(LaptopsPage_LaptopTitle)).size();
			Common.javascriptClick(driver,
					driver.findElement(By.xpath(LaptopsPage_LaptopTitle + "[" + laptopCounter + "]")));
			// driver.findElement(By.xpath(LaptopsPage_LaptopTitle + "[" +
			// laptopCounter +
			// "]")).click();
			Common.sleep(4000);
			if (Common.checkElementDisplays(driver, By.xpath(LaptopsSubSeriesPage_ModalName + "[1]"), 4)) {
				Common.javascriptClick(driver, driver.findElement(By.xpath(LaptopsSubSeriesPage_ModalName + "[1]")));
				// driver.findElement(By.xpath(LaptopsSubSeriesPage_ModalName +
				// "[1]")).click();
			} else if (laptopCounter < totalNoOfLaptops) {
				++laptopCounter;
				driver.findElement(By.xpath(LaptopsPage_LaptopTitle + "[" + laptopCounter + "]")).click();
			}
		}
		Common.sleep(5000);
		if (Common.checkElementDisplays(driver, By.xpath(ButtonLocator), 4)) {
			// Common.scrollToElement(driver,
			// driver.findElement(By.xpath(ButtonLocator)));
			Common.javascriptClick(driver, driver.findElement(By.xpath(PLPPage_ViewCurrentModels)));

			Common.sleep(1000);
			Common.javascriptClick(driver, driver.findElement(By.xpath(ButtonLocator)));
			// driver.findElement(By.xpath(ButtonLocator)).click();
			System.out.println("Add to Cart button is clicked on PLP page.");
			Common.sleep(4000);
			Common.WaitUntilSpinner(driver);
			if (Common.checkElementDisplays(driver, By.xpath(PDPPage_AddToCart), 5)) {
				driver.findElement(By.xpath(PDPPage_AddToCart)).click();
				System.out.println("Add to Cart button is clicked on PDP Page.");
			}
			Common.sleep(3000);
			Common.WaitUntilSpinner(driver);
			if (Common.checkElementDisplays(driver, By.xpath(ConfigurationPage_ContinueButton), 5)) {
				driver.findElement(By.xpath(ConfigurationPage_ContinueButton)).click();
				System.out.println("Continue button is clicked on Configuration Page.");
			}
			if (Common.checkElementDisplays(driver, By.xpath(ConfigurationPage_ContinueButton), 5)) {
				Common.javascriptClick(driver, driver.findElement(By.xpath(ConfigurationPage_ContinueButton)));
				// driver.findElement(By.xpath(ConfigurationPage_ContinueButton)).click();
				System.out.println("Continue button is clicked on Configuration Page.");
			}
			Common.sleep(3000);
			Common.WaitUntilSpinner(driver);
			if (Common.checkElementDisplays(driver, By.xpath(CartPage_AddToCartBtn), 5)) {
				driver.findElement(By.xpath(CartPage_AddToCartBtn)).click();
				System.out.println("Add to Cart button is clicked on Configuration Page.");
			}
			Common.sleep(3000);
			Common.WaitUntilSpinner(driver);
			if (Common.checkElementDisplays(driver, By.xpath(CartPage_AddToCartBtn), 5)) {
				Common.sleep(3000);
				Common.javascriptClick(driver, driver.findElement(By.xpath(CartPage_AddToCartBtn)));
				// driver.findElement(By.xpath(CartPage_AddToCartBtn)).click();
				System.out.println("Add to Cart button is clicked on Cart Page.");
			}
		} else if (laptopCounter < totalNoOfLaptops) {
			driver.get(URL);
			Common.sleep(3000);
			addToCartB2C(++laptopCounter, driver, URL, ButtonLocator);
		}
	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void NewUserRegistration(String store, WebDriver driver, B2CPage b2cPage, TestData testData,
			String email, String browser) {
		Common.NavigateToUrl(driver, browser, testData.B2C.getloginPageUrl());
		Common.sleep(2500);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		Common.sleep(1000);
		if (store.equals("US_BPCTO")) {
			driver.findElement(By.xpath("//a[contains(@class,'register-button')]")).click();
		} else {
			b2cPage.Login_CreateAnAccountButton.click();
		}
		// Enter ID
		Common.sendFieldValue(b2cPage.RegistrateAccount_EmailIdTextBox, email + "@sharklasers.com");
		// Enter Confirm Email
		Common.sendFieldValue(b2cPage.RegistrateAccount_ConfirmEmailTextBox, email + "@sharklasers.com");
		// Enter Password
		Common.sendFieldValue(b2cPage.RegistrateAccount_FirstNameTextBox, "First_Nmae");
		// Enter First Name
		Common.sendFieldValue(b2cPage.RegistrateAccount_LastNameTextBox, "Last_Name");
		// Enter Last Name
		Common.sendFieldValue(b2cPage.RegistrateAccount_PasswordTextBox, "1q2w3e4r");
		// Enter confirm password
		Common.sendFieldValue(b2cPage.RegistrateAccount_ConfirmPasswordTextBox, "1q2w3e4r");
		// Check the Terms & Conditions check box
		b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
		b2cPage.RegistrateAccount_CreateAccountButton.click();
		Common.sleep(3000);
		if (!Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.RegistrateAccount_ThankYouMessage, 10))
			Assert.fail("New Account is not created successfully!");
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Click on AddToCart/Customize button on PDP page
	 */
	public static void clickAddtocartOrCustomizeOnPDP(WebDriver driver) throws InterruptedException {
		B2CPage b2cPage = new B2CPage(driver);
		if (Common.isElementExist(driver, By.xpath(".//*[@id='addToCartButtonTop']"))) {
			b2cPage.Product_viewModel.click();
			Thread.sleep(5000);
			List<WebElement> buttons = driver.findElements(By.xpath("//button[@id='addToCartButtonTop']"));
			Common.javascriptClick(driver, buttons.get(buttons.size() - 1));
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Close home page pop up
	 */
	public static void closeHomePagePopUP(WebDriver driver) {
		String closeHomePagePopUp = "//div//button[@title='Close (Esc)']";
		String closeLeftSidePopUp = "//div/button[contains(.,'X')]";
		String SlidePopUp = ".//*[@id='full-sc']/a";
		String TransparentPopUp = ".//*[@id='content-sc']/a";
		String FlashSalePopUp = "html/body/div[2]/div/div[1]/dl/button";
		String blackFridayPopUp = "//a[@title='Close']";
		String productNumberPopUp = "//span[@class='numberclose']/img";

		if (Common.checkElementDisplays(driver, By.xpath(closeHomePagePopUp), 4)) {
			driver.findElement(By.xpath(closeHomePagePopUp)).click();
		} else if (Common.checkElementDisplays(driver, By.xpath(closeLeftSidePopUp), 4)) {
			driver.findElement(By.xpath(closeLeftSidePopUp)).click();
		} else if (Common.checkElementDisplays(driver, By.xpath(SlidePopUp), 4)) {
			driver.findElement(By.xpath(SlidePopUp)).click();
		} else if (Common.checkElementDisplays(driver, By.xpath(TransparentPopUp), 4)) {
			driver.findElement(By.xpath(TransparentPopUp)).click();
		} else if (Common.checkElementDisplays(driver, By.xpath(FlashSalePopUp), 4)) {
			driver.findElement(By.xpath(FlashSalePopUp)).click();
		} else if (Common.checkElementDisplays(driver, By.xpath(blackFridayPopUp), 4)) {
			driver.findElement(By.xpath(blackFridayPopUp)).click();
		} else if (Common.checkElementDisplays(driver, By.xpath(productNumberPopUp), 4)) {
			driver.findElement(By.xpath(productNumberPopUp)).click();
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Handle JP pop up
	 */
	public static void PopUpJP(WebDriver driver) {
		String CloseIcon = ".//*[@id='window-close']/img";
		// close login page pop-up
		if (driver.findElement(By.xpath(CloseIcon)).isDisplayed()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(CloseIcon)));
			driver.findElement(By.xpath(CloseIcon)).click();
		}
	}

	/**
	 * @Owner Pan
	 * @Parameter
	 * @Usage
	 */
	public static void loginASMAndStartSession(WebDriver driver, B2CPage b2cPage, String category,
			String customerOrQuoteNo) throws InterruptedException {
		b2cPage.myAccountPage_startAssistedServiceSession.click();
		Thread.sleep(7000);
		B2CCommon.closeHomePagePopUP(driver);

		if (category.equals("customer")) {
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(customerOrQuoteNo);
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
		} else {
			b2cPage.TransactionID.sendKeys(customerOrQuoteNo);
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='Q']>a")));
			b2cPage.ASM_QuoteResult.click();
			Thread.sleep(3000);
		}
		b2cPage.Tele_StartSession.click();
	}

	/**
	 * @author gaopan2
	 * @param
	 * @throws Exception
	 * @Usage
	 */
	public static void emptyCart(WebDriver driver, B2CPage b2cPage) throws Exception {
		if (Common.isElementExist(driver, By.xpath("//form[@id='emptyCartItemsForm']/a"))) {
			b2cPage.Cart_emptyCart.click();
			Thread.sleep(4000);
			if (Common.isElementExist(driver,
					By.xpath("//div[@class='popup_arrow_box']/div/input[contains(@onclick,'submit')]"))) {
				b2cPage.NewCart_ConfirmDelete.click();
			}
		}
	}

	/**
	 * @author gaopan2
	 * @param
	 * @return
	 * @Usage
	 */
	public static String getCountry(String store) {

		String country = "";
		switch (store) {
		case "AU":
			country = "Australia";
			break;
		case "BR":
			country = "Brazil";
			break;
		case "CA":
			country = "Canada";
			break;
		case "CA_AFFINITY":
			country = "Canada";
			break;
		case "CO":
			country = "Columbia";
			break;
		case "FR":
			country = "France";
			break;
		case "GB":
			country = "United Kingdom";
			break;
		case "HK":
			country = "Hong Kong";
			break;
		case "HKZF":
			country = "Hong Kong";
			break;
		case "IE":
			country = "Ireland";
			break;
		case "JP":
			country = "Japan";
			break;
		case "MY":
			country = "Malaysia";
			break;
		case "NZ":
			country = "New Zealand";
			break;
		case "SG":
			country = "Singapore";
			break;
		case "TW":
			country = "Taiwan";
			break;
		case "US":
			country = "United States";
			break;
		case "USEPP":
			country = "United States";
			break;
		case "US_OUTLET":
			country = "United States";
			break;
		case "US_BPCTO":
			country = "United States";
			break;
		}
		return country;
	}

	/**
	 * @author Songli
	 * @param
	 * @Usage Go to cart page by clicking on icon in navigation bar (handle
	 *        'View Cart' button as well)
	 */
	public static void ClickToCartPage(B2CPage b2cPage) {
		b2cPage.Navigation_CartIcon.click();
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Navigation_ViewCartButton, 5))
			b2cPage.Navigation_ViewCartButton.click();
		if (Common.isElementExist(b2cPage.PageDriver, By.xpath("//form[@id='emptyCartItemsForm']/a"))) {
			b2cPage.PageDriver.findElement(By.xpath("//form[@id='emptyCartItemsForm']/a")).click();
		}
	}

	/****
	 * @auther Hao
	 * @param
	 * @usage Check if checkout need user signing in, then sign in
	 */
	public static void HandleCheckoutLogin(B2CPage b2cPage, String emailId, String password) {
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.JPLogin_UserName, 3)) {
			b2cPage.JPLogin_UserName.clear();
			b2cPage.JPLogin_UserName.sendKeys(emailId);
			b2cPage.JPLogin_Password.clear();
			b2cPage.JPLogin_Password.sendKeys(password);
			b2cPage.Login_LogInButton.click();
		}
	}

	/****
	 * @auther Pan
	 * @param
	 * @usage to handle the spring check for the telesales testing
	 */
	public static void DoubleLogin(WebDriver driver, TestData testData, B2CPage b2cPage, String url, String account,
			String password, String browser) {

		// the first time login
		Common.NavigateToUrl(driver, browser, url);
		B2CCommon.login(b2cPage, account, password);
		B2CCommon.handleTeleGateKeeper(b2cPage, testData);

		if (!Common.isElementExist(driver,
				By.xpath("//a[contains(@href,'logout')]/div[contains(@class,'link_text')]"))) {
			Common.NavigateToUrl(driver, browser, url);
			B2CCommon.login(b2cPage, account, password);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
		}
	}

	/****
	 * @auther Pan
	 * @param
	 * @usage to logout the B2C
	 */

	public static void javascript_logout(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]")));
	}

	/****
	 * @auther Pan
	 * @param
	 * @usage
	 */

	public static float GetPriceValue(String Price) {

		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\￥", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("₹", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

}
