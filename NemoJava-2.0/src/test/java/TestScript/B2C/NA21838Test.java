package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class NA21838Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String Country;
	private Double minthresholds;
	private Double maxthresholds;
	private String laUrl = "https://cn.bing.com/";
	private String billTo = "1234567890";
	private String isNaLeasingOptionText = "Yes";
	private String testProduct;
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

	
	public NA21838Test(String store, String country, String billNumber) {
		this.Store = store;
		this.Country = country;
		this.testName = "NA-21838";
		this.BillNumber = billNumber;
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21838(ITestContext ctx) {
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
			
			
			billTo = "21838" + BillNumber;
			Dailylog.logInfoDB(1, "BillTo: " + billTo, Store, testName);

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
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);

			// go to payment page to check the order total price
			checkout();
	
			String price = "";
			
			if(Common.isElementExist(driver, By.xpath("//span[contains(@class,'totalPriceWithTax')]"))){
				WebElement totalPriceWithTax = driver.findElement(By.xpath("//span[contains(@class,'totalPriceWithTax')]"));
				price = totalPriceWithTax.getText();
			}else{
				WebElement totalPriceWithTax_new = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]"));
				price = totalPriceWithTax_new.getText();
			}
			Dailylog.logInfoDB(1, "Total Price: " + price, Store, testName);

			// Set min price to price *2
			minthresholds = getDoublePrice(price) * 2;
			// Set max price to price *2+1
			maxthresholds = getDoublePrice(price) * 2 + 1;
			Dailylog.logInfoDB(1, "minthresholds: " + minthresholds, Store, testName);
			Dailylog.logInfoDB(1, "maxthresholds: " + maxthresholds, Store, testName);

			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);

			// Payment leasing setting
			paymentLeasingSetting(minthresholds + "", maxthresholds + "", laUrl, Country);
			Dailylog.logInfoDB(2, "payment leasing setting done", Store, testName);
			// B2C leasing setting: isNaLeasing: Yes
			b2cLeasingSetting(billTo, isNaLeasingOptionText, Country);
			Dailylog.logInfoDB(6, "b2c leasing setting done", Store, testName);
			// B2C unit setting
			b2cUnitSetting(billTo, isNaLeasingOptionText);
			Dailylog.logInfoDB(8, "b2c unit setting done", Store, testName);

			// step 9
			loginB2C();
			driver.get(ordinary_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			// add product price < min
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			checkout();
			// Assert not shown, isNALeasing: Yes, total price<min
			Assert.assertFalse(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"step 9 LFS payment should not display: isNALeasing: Yes, total price < min, ordinary user");

			// step 10
			driver.get(ordinary_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			// add product price > max
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);

			checkout();
			// Assert not shown, isNALeasing: Yes, total price>max
			Assert.assertFalse(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"step 10 LFS payment should not display: isNALeasing: Yes, total price > max, ordinary user");

			// step 11
			teleSalesLogin();
			Dailylog.logInfoDB(11, "login with telesales account", Store, testName);

			// step 12
			driver.get(tele_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			// add product price < min
			teleQuickOrder(testProduct);
			checkout();
			// Assert not shown, isNALeasing: Yes, total price<min
			Assert.assertFalse(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"step 12 LFS payment should not display: isNALeasing: Yes, total price < min, tele user");

			// step 13
			driver.get(tele_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			// add product price > max
			teleQuickOrder(testProduct);
			teleQuickOrder(testProduct);
			teleQuickOrder(testProduct);

			checkout();
			// Assert not shown, isNALeasing: Yes, total price>max
			Assert.assertFalse(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"step 13 LFS payment should not display: isNALeasing: Yes, total price > max, tele user");
			// logoutB2C();

			// step 14~15
			isNaLeasingOptionText = "No";
			driver.get(hmcHomePageUrl);
			hmcPage.Home_EndSessionLink.click();
			HMCCommon.Login(hmcPage, testData);
			b2cLeasingSetting(billTo, isNaLeasingOptionText, Country);

			// step 16
			driver.get(tele_homePageUrl);
			driver.manage().deleteAllCookies();
			loginB2C_tele();
			driver.get(tele_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			// add product price < min
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			checkout();
			// Assert not shown, isNALeasing: Yes, total price<min
			Assert.assertFalse(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"step 16 LFS payment should not display: isNALeasing: No, total price < min, ordinary user");

			// step 17
			teleSalesLogin();

			// step 18
			driver.get(tele_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			// add product price < min
			teleQuickOrder(testProduct);
			checkout();
			// Assert not shown, isNALeasing: Yes, total price<min
			Assert.assertFalse(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"step 12 LFS payment should not display: isNALeasing: No, total price < min, tele user");

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
			hmcPage.Home_paymentLeasingB2C.click();
			Common.sleep(500);
			try {
				hmcPage.B2CLeasing_billToNumber.clear();
				hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
			} catch (NoSuchElementException e) {
				System.out.println("handle NoSuchElementException");
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
				if (isAlertPresent(driver))
					driver.switchTo().alert().accept();
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void paymentLeasingSetting(String minthresholds, String maxthresholds, String laUrl, String country)
			throws InterruptedException {
		// Go to HMC-> Nemo -> Payment -> Payment Leasing Setting->search
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentLeasingSetting.click();
		Select dropdown = new Select(hmcPage.PaymentLeasing_leasingType);
		dropdown.selectByVisibleText("LFS");
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
		dropdown.selectByVisibleText("LFS");
		// Country:
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		dropdown.selectByVisibleText(country);
		// Application URL :http://cn.bing.com/
		hmcPage.PaymentLeasing_laUrl.clear();
		hmcPage.PaymentLeasing_laUrl.sendKeys(laUrl);
		// Minimum thresholds：1
		hmcPage.PaymentLeasing_minimum.clear();
		hmcPage.PaymentLeasing_minimum.sendKeys(minthresholds);
		// Maximum thresholds：10000
		hmcPage.PaymentLeasing_maximum.clear();
		hmcPage.PaymentLeasing_maximum.sendKeys(maxthresholds);
		// Save
		hmcPage.PaymentLeasing_saveAndCreate.click();

		Thread.sleep(10000);
		hmcPage.Home_payment.click();
		hmcPage.Home_Nemo.click();
	}

	public void b2cLeasingSetting(String billTo, String isNaLeasingOptionText, String country)
			throws InterruptedException {
		// Go to HMC -> Nemo -> Payment -> B2C Leasing Setting->Search
		hmcPage.Home_Nemo.click();
		Common.waitElementClickable(driver, hmcPage.Home_payment, 5);
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentLeasingB2C.click();
		Common.sleep(500);
		try {
			hmcPage.B2CLeasing_billToNumber.clear();
			hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		} catch (NoSuchElementException e) {
			System.out.println("handle NoSuchElementException");
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
		dropdown.selectByVisibleText("LFS");
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
			if (isAlertPresent())
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
		dropdown.selectByVisibleText(Country);
		// if (Store.equals("US")) {
		// dropdown.selectByVisibleText("United States");
		// } else if (Store.equals("AU")) {
		// dropdown.selectByVisibleText("Australia");
		// }
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

	public void b2cUnitSetting(String billTo, String isNaLeasingOptionText) {
		// HMC->B2C Commerce->B2C unit->search the B2C unit-> Site Attribute
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		// Payment Type: --> Add LFS to Payment Type
		Common.rightClick(driver, hmcPage.B2CUnit_paymentTypeTable);
		hmcPage.B2CLeasing_add.click();
		switchToWindow(1);
		hmcPage.B2CUnit_paymentTypeInput.sendKeys("LFS");
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
			if (isAlertPresent(driver))
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
			System.out.println("Swith to window timeout, window: " + windowNo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void fillPaymentAddressInfo(B2CPage b2cPage, String firstName, String lastName, String phone)
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
	}

	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void loginB2C() {
		driver.get(ordinary_homePageUrl);
		if (Common.checkElementExists(driver, b2cPage.Close_PopUpJP, 10)) {
			Common.sleep(5000);
			b2cPage.Close_PopUpJP.click();
		}
		if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
			B2CCommon.handleGateKeeper(b2cPage, testData);
		} else {
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// login B2C
			driver.get(ordinary_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
		}
	}
	
	public void loginB2C_tele() {
		driver.get(tele_homePageUrl);
		if (Common.checkElementExists(driver, b2cPage.Close_PopUpJP, 10)) {
			Common.sleep(5000);
			b2cPage.Close_PopUpJP.click();
		}
		if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
			B2CCommon.handleGateKeeper(b2cPage, testData);
		} else {
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// login B2C
			driver.get(tele_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
		}
	}

	public void checkout() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].click();",
		// b2cPage.Cart_CheckoutButton);
		// Thread.sleep(1000);
		b2cPage.Cart_CheckoutButton.click();
		if (Common.isElementExist(driver, By.xpath(".//*[@id='guestForm']/button")))
			b2cPage.Checkout_StartCheckoutButton.click();
		if (Common.isElementExist(driver, By.xpath("//*[@id='addressForm']/fieldset[1]/legend/a")) && b2cPage.shippingEdit.isDisplayed())
			b2cPage.shippingEdit.click();
		B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
				"Auto19782@sharklasers.com");
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
		Common.sleep(10000);
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
		// Start Assisted Service Session
		Thread.sleep(1000);
		b2cPage.MyAccount_Telesales.click();
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.HomePage_CloseAdvButton, 5))
			b2cPage.HomePage_CloseAdvButton.click();
		Common.waitElementClickable(driver, b2cPage.Tele_CustomerSearch, 5);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Tele_CustomerSearch);
		b2cPage.Tele_CustomerSearch_customerID.sendKeys(testData.B2C.getLoginID());
		b2cPage.Tele_CustomerSearch_Search.click();
		Common.sleep(500);
		b2cPage.Tele_CustomerSearch_SearchResult.click();
		b2cPage.Tele_StartSession.click();
		Dailylog.logInfoDB(6, testData.B2C.getTelesalesAccount() + " start session", Store, testName);
	}

	public double getDoublePrice(String price) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		price = price.replace("[", "").replace("]", "").replace("add", "").replace("$", "").replace(",", "")
				.replace("￥", "").replace("HK", "").replace("SG", "").trim().toString();
		double price_1 = Double.parseDouble(price);
		String price_2 = decimalFormat.format(price_1);
		Double doublePrice = Double.parseDouble(price_2);
		return doublePrice;
	}

	public void teleQuickOrder(String testProduct) {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(testProduct);
		b2cPage.Cart_AddButton.click();
	}

	public boolean isAlertPresent(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
}
