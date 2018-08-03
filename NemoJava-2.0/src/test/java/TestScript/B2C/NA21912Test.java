package TestScript.B2C;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21912Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String Country;
	private Double min;
	private Double max;
	private String laUrl = "https://cn.bing.com/";
	private String billTo = "1234567890";
	private String isNaLeasingOptionText = "No";
	private String testProduct;
	private String cartName = "";
	private String orderNum = null;
	private String payment = "IGF";
	private String BillNumber;
	By emptyCart = By.xpath(
			"//*[contains(text(),'Empty cart') or contains(text(),'カートを空にする') or contains(@alt,'カートを空にする') or contains(@alt,'Empty cart')]");
	By confirmEmpty = By.xpath("//input[contains(@onclick, '.submit')]");
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;


	public NA21912Test(String store, String country, String billNumber) {
		this.Store = store;
		this.Country = country;
		this.BillNumber = billNumber;
		this.testName = "NA-21912";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21912(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();

			billTo = "21912" + BillNumber;
			Dailylog.logInfoDB(0,
					Store + "  NA21912  IGF  isNALeasing:No  price>max  ordinary:save cart  Tele:place order", Store,
					testName);

			// Check the price of default product
			if (Store.indexOf("US") >= 0)
				testProduct = "40A40090US";
			else if (Store.indexOf("JP") >= 0)
				testProduct = "80YL00N4JP";
			else if (Store.indexOf("AU") >= 0)
				testProduct = "65C5KAC1AU";
			else
				testProduct = testData.B2C.getDefaultMTMPN();

			driver.get(ordinary_homePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "Product: " + testProduct, Store, testName);
			driver.get(ordinary_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			String price = b2cPage.CartPage_totalPrice.getText();
			Dailylog.logInfoDB(1, "total price: " + price, Store, testName);

			// Set min price to price - 2
			min = getDoublePrice(price) / 100;
			// Set max price to price - 1
			max = getDoublePrice(price) / 10;
			Dailylog.logInfoDB(1, "min: " + min, Store, testName);
			Dailylog.logInfoDB(1, "max: " + max, Store, testName);

			// Step1~2 Payment Leasing setting for IGF
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			paymentLeasingSetting(min + "", max + "", laUrl, Country, payment);
			Dailylog.logInfoDB(2, "payment leasing done", Store, testName);

			// Step3~6 B2C leasing setting, isNALeasing:No
			b2cLeasingSetting(billTo, isNaLeasingOptionText, Country, payment);
			Dailylog.logInfoDB(6, "b2c leasing done", Store, testName);

			// Step7~8 B2C unit setting add IGF payment
			b2cUnitSetting(billTo, isNaLeasingOptionText, payment);
			Dailylog.logInfoDB(8, "b2c unit setting done", Store, testName);

			// Step9 Login and add item to cart, total price>max
			gateKeeper();
			driver.get(ordinary_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			Dailylog.logInfoDB(9, "add item to cart", Store, testName);

			// Step10 Checkout and try to place order with IGF payment
			checkout();
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_IGF")),
					"step 10 IGF payment should display: isNALeasing: No, total price > max, ordinary user");
			Dailylog.logInfoDB(10, "checkout", Store, testName);

			// Step10 Leasing payment
			b2cPage.payment_IGF.click();

			// Step10 Check if purchase order payment display
			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {
				if (b2cPage.payment_purchaseNum.isDisplayed()) {
					b2cPage.payment_purchaseNum.sendKeys(billTo);
					b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);
				}
			}

			// Step 10 fill payment info, only need to fill name and phone
			fillPaymentAddressInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getDefaultAddressState());

			// Step 10 continue
			Thread.sleep(5000);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(10, "click continue in payment page", Store, testName);
			Thread.sleep(5000);
			
			boolean b = Common.isAlertPresent(driver);
			
			System.out.println("b is :" + b);
			
			if (!Common.isAlertPresent(driver)){
				if (Common.checkElementExists(driver, b2cPage.Payment_ContinueButton, 15)) {
					executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
					Dailylog.logInfoDB(10, "click continue in payment page again", Store, testName);
				}
			}
			// Assert: save cart prompt is shown.
			
			boolean b1 = Common.isAlertPresent(driver);
			
			System.out.println("b1 is :" + b1);
			
			Assert.assertTrue(Common.isAlertPresent(driver), "alert should be displayed");
			String alertText = driver.switchTo().alert().getText();
			Dailylog.logInfoDB(10, alertText, Store, testName);

			// Step 11 Close save cart popup
			driver.switchTo().alert().accept();

			// Step 12 Click save cart, record cart name
			saveCart();
			Dailylog.logInfoDB(12, cartName, Store, testName);

			// Step 13 Re-login with telesales,transaction name: cart name
			teleSalesLogin();
			Dailylog.logInfoDB(13, "login with telesales account", Store, testName);

			startSession(cartName, "name");
			Dailylog.logInfoDB(13, "Start session", Store, testName);

			// Step 14 Checkout with telesales
			Thread.sleep(1000);
			Common.waitElementClickable(driver, b2cPage.Cart_openCart, 20);
			b2cPage.Cart_openCart.click();
			Thread.sleep(1000);
			checkout();
			Dailylog.logInfoDB(14, "Checkout with telesales", Store, testName);

			// Step 15 Place order with IGF payment
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_IGF")),
					"step 15 IGF payment should display: isNALeasing: No, total price > max, telesales user");

			// Step15 Leasing payment
			b2cPage.payment_IGF.click();

			// Step15 Check if purchase order payment display
			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {
				if (b2cPage.payment_purchaseNum.isDisplayed()) {
					b2cPage.payment_purchaseNum.sendKeys(billTo);
					b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);
				}
			}

			// Step 15 fill payment info, only need to fill name and phone
			fillPaymentAddressInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getDefaultAddressState());

			// Step 15 continue
			Thread.sleep(5000);
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
			Thread.sleep(5000);
			if (Common.checkElementExists(driver, b2cPage.Payment_ContinueButton, 15)) {
				executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
				Dailylog.logInfoDB(10, "click continue in payment page again", Store, testName);
			}

			try {
				if (Common.isElementExist(driver, By.xpath("//*[@id='repId']"))) {
					// driver.findElement(By.xpath("//*[@id='repId']")).clear();
					driver.findElement(By.xpath("//*[@id='repId']")).sendKeys(testData.B2C.getRepID());
				}
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {
				System.out.println("StaleElementReferenceException");
				if (Common.isElementExist(driver, By.xpath("//*[@id='repId']"))) {
					// driver.findElement(By.xpath("//*[@id='repId']")).clear();
					driver.findElement(By.xpath("//*[@id='repId']")).sendKeys(testData.B2C.getRepID());
				}
			}

			b2cPage.OrderSummary_AcceptTermsCheckBox.click();

			// place order
			Thread.sleep(500);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");
			orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			System.out.println("orderNum is :" + orderNum);

			Assert.assertNotNull(orderNum);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test
	public void rollback(ITestContext ctx) {
		try {

			System.out.println("rollback"); // roll back

			SetupBrowser();
			hmcPage = new HMCPage(driver);

			driver.get(hmcHomePageUrl);
			if (Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 3)) {
				hmcPage.Home_EndSessionLink.click();
			}

			HMCCommon.Login(hmcPage, testData);

			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			Common.sleep(500);
			hmcPage.Home_paymentLeasingB2C.click();
			Common.sleep(500);
			try {
				hmcPage.B2CLeasing_billToNumber.clear();
				hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
			} catch (NoSuchElementException e) {
				hmcPage.Home_paymentLeasingB2C.click();
				Common.sleep(1000);
				hmcPage.B2CLeasing_billToNumber.clear();
				hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
			}
			hmcPage.PaymentLeasing_searchbutton.click();

			if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'No results')]"))) {
				// delete b2c leasing setting
				hmcPage.PaymentLeasing_openEditor1.click();
				hmcPage.PaymentLeasing_delete.click();
				if (Common.isAlertPresent(driver))
					driver.switchTo().alert().accept();
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void paymentLeasingSetting(String min, String max, String laUrl, String country, String payment)
			throws InterruptedException {
		// Go to HMC-> Nemo -> Payment -> Payment Leasing Setting->search
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentLeasingSetting.click();
		Select dropdown = new Select(hmcPage.PaymentLeasing_leasingType);
		dropdown.selectByVisibleText(payment);
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		dropdown.selectByVisibleText(Country);
		hmcPage.PaymentLeasing_searchbutton.click();

		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'No results')]"))) {
			// If no result: create Payment Leasing Setting
			Common.rightClick(driver, hmcPage.Home_paymentLeasingSetting);
			Common.waitElementClickable(driver, hmcPage.PaymentLeasing_create, 8);
			hmcPage.PaymentLeasing_create.click();

		} else {
			// If searched out one result: Open this Leasing setting to edit
			// Common.doubleClick(driver,hmcPage.PaymentLeasing_searchedResults);
			hmcPage.PaymentLeasing_openEditor1.click();
		}
		// Edit or Create data - Leasing type: LFS
		dropdown = new Select(hmcPage.PaymentLeasing_leasingType);
		dropdown.selectByVisibleText(payment);
		// Country:
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		dropdown.selectByVisibleText(country);
		// Application URL :http://cn.bing.com/
		hmcPage.PaymentLeasing_laUrl.clear();
		hmcPage.PaymentLeasing_laUrl.sendKeys(laUrl);
		// Minimum thresholds：1
		hmcPage.PaymentLeasing_minimum.clear();
		hmcPage.PaymentLeasing_minimum.sendKeys(min);
		// Maximum thresholds：10000
		hmcPage.PaymentLeasing_maximum.clear();
		hmcPage.PaymentLeasing_maximum.sendKeys(max);
		// Save
		hmcPage.PaymentLeasing_saveAndCreate.click();

		Thread.sleep(10000);
		hmcPage.Home_payment.click();
		hmcPage.Home_Nemo.click();
	}

	public void b2cLeasingSetting(String billTo, String isNaLeasingOptionText, String country, String payment)
			throws InterruptedException {
		// Go to HMC -> Nemo -> Payment -> B2C Leasing Setting->Search
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		Common.sleep(500);
		hmcPage.Home_paymentLeasingB2C.click();
		Common.sleep(500);
		try {
			hmcPage.B2CLeasing_billToNumber.clear();
			hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		} catch (NoSuchElementException e) {
			hmcPage.Home_paymentLeasingB2C.click();
			Common.sleep(1000);
			hmcPage.B2CLeasing_billToNumber.clear();
			hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		}
		// Select dropdown = new Select(hmcPage.B2CLeasing_NALeasing);
		// dropdown.selectByVisibleText(isNaLeasingOptionText);
		hmcPage.PaymentLeasing_searchbutton.click();

		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'No results')]"))) {
			// If no result: Create B2C Leasing Setting
			Common.rightClick(driver, hmcPage.Home_paymentLeasingB2C);
			Common.waitElementClickable(driver, hmcPage.B2CLeasing_createB2CLeasing, 8000);
			hmcPage.B2CLeasing_createB2CLeasing.click();

		} else {
			// If searched out one result: Open this Leasing setting to edit
			// Common.doubleClick(driver,hmcPage.PaymentLeasing_searchedResults);
			hmcPage.PaymentLeasing_openEditor1.click();
		}
		// Edit or Create data - Leasing Setting
		hmcPage.B2CLeasing_leasingSetting.click();
		switchToWindow(1);
		Select dropdown = new Select(hmcPage.PaymentLeasing_leasingType);
		dropdown.selectByVisibleText(payment);
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		dropdown.selectByVisibleText(country);
		hmcPage.PaymentLeasing_searchbutton.click();
		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();
		switchToWindow(0);
		// BillTo Number：1234567890
		hmcPage.B2CLeasing_billToNumber.clear();
		hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		// is NA Leasing:
		if (isNaLeasingOptionText.equals("No"))
			hmcPage.B2CLeasing_NALeasingNo.click();
		else
			hmcPage.B2CLeasing_NALeasingYes.click();
		// BilltoAddress :
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'The list is empty.')]"))) {
			Common.rightClick(driver, hmcPage.B2CLeasing_billtoList);
			hmcPage.B2CLeasing_selectAll.click();
			Thread.sleep(2000);
			Common.rightClick(driver, hmcPage.B2CLeasing_billtoList);
			hmcPage.B2CLeasing_remove.click();
			Thread.sleep(2000);
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
		}
		Common.rightClick(driver, hmcPage.B2CLeasing_billtoList);
		hmcPage.B2CLeasing_add.click();
		switchToWindow(1);
		// Is billing address : Yes,
		dropdown = new Select(hmcPage.B2CLeasing_billingAddress);
		dropdown.selectByVisibleText("Yes");
		// Country:
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		if (Store.equals("US")) {
			dropdown.selectByVisibleText("United States");
		} else if (Store.equals("AU")) {
			dropdown.selectByVisibleText("Australia");
		}
		// Street Name : Starts with 1，Then use the first result
		hmcPage.B2CLeasing_streetname.sendKeys("1");
		hmcPage.PaymentLeasing_searchbutton.click();
		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();
		// Save
		switchToWindow(0);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_Nemo.click();

	}

	public void b2cUnitSetting(String billTo, String isNaLeasingOptionText, String payment)
			throws InterruptedException {
		// HMC->B2C Commerce->B2C unit->search the B2C unit-> Site Attribute
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		// Payment Type: --> Add LFS to Payment Type
		Common.rightClick(driver, hmcPage.B2CUnit_paymentTypeTable);
		hmcPage.B2CLeasing_add.click();
		switchToWindow(1);
		hmcPage.B2CUnit_paymentTypeInput.sendKeys(payment);
		hmcPage.PaymentLeasing_searchbutton.click();
		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();
		// B2C Leasing Settings: --> add LFS
		switchToWindow(0);
		dLeasingItems();
		Common.rightClick(driver, hmcPage.B2CUnit_B2CLeasingTable);
		hmcPage.B2CLeasing_add.click();
		switchToWindow(1);
		hmcPage.B2CLeasing_billToNumber.clear();
		hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		Select dropdown = new Select(hmcPage.B2CLeasing_NALeasing);
		dropdown.selectByVisibleText(isNaLeasingOptionText);
		hmcPage.PaymentLeasing_searchbutton.click();
		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();
		// Save
		switchToWindow(0);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Thread.sleep(12000);
		hmcPage.Home_B2CCommercelink.click();
	}

	private void dLeasingItems() {
		String leasingItem = "//*[contains(text(),'B2C Leasing Settings:')]/../..//table[@class='genericItemListChip']/tbody//tbody//tr[contains(.,'empty')]";
		if (!Common.isElementExist(driver, By.xpath(leasingItem))) {
			System.out.println("delete previous leasing settings");
			Common.rightClick(driver, hmcPage.B2CUnit_B2CLeasingTable);
			hmcPage.B2CLeasing_selectAll.click();
			Common.sleep(2000);
			Common.rightClick(driver, hmcPage.B2CUnit_B2CLeasingTable);
			hmcPage.B2CLeasing_remove.click();
			Common.sleep(2000);
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();

		}

	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			System.out.println("Swith to window timeout, windowNo: " + windowNo);
		} catch (InterruptedException e) {

		}
	}

	public void fillPaymentAddressInfo(B2CPage b2cPage, String firstName, String lastName, String phone, String state)
			throws InterruptedException {
		if (b2cPage.Payment_FirstNameTextBox.isEnabled()) {
			b2cPage.Payment_FirstNameTextBox.clear();
			Thread.sleep(1000);
			b2cPage.Payment_FirstNameTextBox.sendKeys(firstName);
		}
		if (b2cPage.Payment_LastNameTextBox.isEnabled()) {
			b2cPage.Payment_LastNameTextBox.clear();
			Thread.sleep(1000);
			b2cPage.Payment_LastNameTextBox.sendKeys(lastName);
		}

		if (b2cPage.Payment_AddressLine1TextBox.isEnabled()) {
			b2cPage.Payment_ContactNumTextBox.clear();
			Thread.sleep(1000);
			b2cPage.Payment_ContactNumTextBox.sendKeys(phone);
		}
		if (b2cPage.Payment_StateDropdown.isEnabled()) {
			Select dropdown = new Select(b2cPage.Payment_StateDropdown);
			dropdown.selectByVisibleText(state);
		}
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_PostCodeTextBox, 1)) {
			if (b2cPage.Payment_PostCodeTextBox.isEnabled()) {
				b2cPage.Payment_PostCodeTextBox.clear();
				b2cPage.Payment_PostCodeTextBox.sendKeys(testData.B2C.getDefaultAddressPostCode());
			}
		}
	}

	

	
	public void gateKeeper() {
		driver.get(ordinary_homePageUrl);
		if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
			B2CCommon.handleGateKeeper(b2cPage, testData);
		} else {
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// login B2C
			driver.get(ordinary_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
		}
	}

	public void checkout() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].click();",
		// b2cPage.Cart_CheckoutButton);
		Thread.sleep(1000);
		b2cPage.Cart_CheckoutButton.click();
		if (Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5))
			b2cPage.Shipping_editAddress.click();
		B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
				"Auto21912@sharklasers.com");
		Thread.sleep(3000);

		// continue
		WebElement continueButton = driver.findElement(By.id("checkoutForm-shippingContinueButton"));
		executor.executeScript("arguments[0].click();", continueButton);

		// UI of validate info is different in each region
		if (Common.isElementExist(driver, By.id("address_sel"))) {
			// US
			Select dropdown = new Select(driver.findElement(By.id("address_sel")));
			dropdown.selectByIndex(1);
			b2cPage.Shipping_AddressMatchOKButton.click();
		} else if (Common.isElementExist(driver, By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
			if (driver.findElement(By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]"))
					.isDisplayed()) {
				// USEPP
				driver.findElement(By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]")).click();
			}
			// USBPCTO
			b2cPage.Shipping_AddressMatchOKButton.click();
		}
		Common.sleep(500);
		
		if(Common.checkElementDisplays(driver, By.id("checkout_validateFrom_skip"), 5)){
			driver.findElement(By.id("checkout_validateFrom_skip")).click();
		}
		
		System.out.println(driver.getCurrentUrl() + "    " + Store);
//		Assert.assertTrue(driver.getCurrentUrl().indexOf("payment") >= 0, "go to payment page successfully " + Store);
	}

	public void teleSalesLogin() throws InterruptedException {
		driver.get(tele_loginUrl);
		if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys(testData.B2C.getTelesalesAccount());
			b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
			b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys(testData.B2C.getTelesalesPassword());
			b2cPage.RegisterGateKeeper_LoginButton.click();
		} else {
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
		}
		if (Common.checkElementExists(driver, b2cPage.Close_PopUpJP, 10)) {
			Common.sleep(5000);
			b2cPage.Close_PopUpJP.click();
		}
	}

	public void startSession(String cartName, String type) throws InterruptedException {
		// Start Assisted Service Session
		Thread.sleep(1000);
		b2cPage.MyAccount_Telesales.click();
		Common.sleep(5000);
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.HomePage_CloseAdvButton, 5))
			b2cPage.HomePage_CloseAdvButton.click();
		try {
			b2cPage.Tele_TransactionSearch.click();
		} catch (NoSuchElementException e) {
			driver.navigate().back();
			teleSalesLogin();
			b2cPage.Tele_TransactionSearch.click();
		}
		Thread.sleep(20000);
		if (type.equals("name"))
			b2cPage.Tele_TransactionSearch_TransactionName.sendKeys(cartName);
		else
			b2cPage.Tele_TransactionSearch_TransactionId.sendKeys(cartName);
		b2cPage.Tele_TransactionSearch_Search.click();
		Thread.sleep(5000);

		WebElement searchResult = driver
				.findElement(By.xpath("//tbody[@id='advTransactionSearchTable']/tr[contains(.,'" + cartName + "')]"));
		Common.waitElementClickable(driver, searchResult, 20);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", searchResult);

		Common.waitElementClickable(driver, b2cPage.Tele_StartSession, 20);
		Thread.sleep(5000);
		b2cPage.Tele_StartSession.click();
	}

	public double getDoublePrice(String price) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		price = price.replace("[", "").replace("]", "").replace("add", "").replace("$", "").replace(",", "")
				.replace("HK", "").replace("SG", "").replace("￥", "").trim().toString();
		double price_1 = Double.parseDouble(price);
		String price_2 = decimalFormat.format(price_1);
		Double doublePrice = Double.parseDouble(price_2);
		return doublePrice;
	}

	public void saveCart() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cartName = sdf.format(date);
		b2cPage.checkout_saveCart.click();
		b2cPage.Cart_nameTextBox.clear();
		b2cPage.Cart_nameTextBox.sendKeys(cartName);
		b2cPage.Cart_saveCartBtn.click();
	}
}
