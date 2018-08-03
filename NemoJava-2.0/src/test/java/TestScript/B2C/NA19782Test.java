package TestScript.B2C;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19782Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String Country;
	private Double min;
	private Double max;
	private String laUrl = "https://cn.bing.com/";
	private String billTo;// "1234567890";
	private String isNaLeasingOptionText = "No";
	private String testProduct;
	private String cartName = "";
	private String payment = "LFS";
	private String BillNumber;
	String homeurl = "https://pre-c-hybris.lenovo.com/us/en/usweb";
    String hmcurl = "http://admin-pre-c-hybris.lenovo.com/hmc/hybris?";
	By emptyCart = By.xpath(
			"//*[contains(text(),'Empty cart') or contains(text(),'カートを空にする') or contains(@alt,'カートを空にする') or contains(@alt,'Empty cart')]");
	By confirmEmpty = By.xpath("//input[contains(@onclick, '.submit')]");

	public NA19782Test(String store, String country, String billNumber) {
		this.Store = store;
		this.Country = country;
		this.BillNumber = billNumber;
		this.testName = "NA-19782";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2c","compatibility"})
	public void NA19782(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			billTo = "19782" + BillNumber;
			JavascriptExecutor executor = (JavascriptExecutor) driver;

			// Check the price of default product
			// testProduct = "80WK002TJE";
			if (Store.indexOf("US") >= 0)
				testProduct = "40A40090US";
			else
				testProduct = testData.B2C.getDefaultMTMPN();
			Common.NavigateToUrl(driver, Browser,testData.B2C.getHomePageUrl());
//			Common.NavigateToUrl(homeurl);
			Common.sleep(5000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "Product: " + testProduct, Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			String price = b2cPage.CartPage_totalPrice.getText();
			Dailylog.logInfoDB(1, "total price: " + price, Store, testName);

			// Set min price to price - 1
			min = getDoublePrice(price) - 1.0;
			// Set max price to price * 10
			max = getDoublePrice(price) * 10;
			Dailylog.logInfoDB(1, "min: " + min, Store, testName);
			Dailylog.logInfoDB(1, "max: " + max, Store, testName);

			// Step1~2 Payment Leasing setting for LFS
//			Common.NavigateToUrl(testData.HMC.getHomePageUrl().replace("origin-", ""));
			Common.NavigateToUrl(driver, price, testData.HMC.getHomePageUrl());
			Common.sleep(3000);
			HMCCommon.Login(hmcPage, testData);
			paymentLeasingSetting(min + "", max + "", laUrl, Country, payment);
			Dailylog.logInfoDB(2, "payment leasing done", Store, testName);

			// Step3~6 B2C leasing setting, isNALeasing:No
			b2cLeasingSetting(billTo, isNaLeasingOptionText, Country, payment);
			Dailylog.logInfoDB(6, "b2c leasing done", Store, testName);

			// Step7~8 B2C unit setting add PARTYLEASING payment
			b2cUnitSetting(billTo, isNaLeasingOptionText, payment);
			Dailylog.logInfoDB(8, "b2c unit setting done", Store, testName);

			// Step9 Go to website, add item to cart. then checkout this product
			// gate keeper
			Common.NavigateToUrl(driver, Browser,testData.B2C.getHomePageUrl());
//			Common.NavigateToUrl(homeurl);
			if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
				B2CCommon.handleGateKeeper(b2cPage, testData);
			} else {
				B2CCommon.handleGateKeeper(b2cPage, testData);
				// login B2C
				Common.NavigateToUrl(driver, Browser,testData.B2C.getloginPageUrl());
//				Common.NavigateToUrl(homeurl+"/login");
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}

			// empty cart
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			// add product to cart
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);
			Dailylog.logInfoDB(9, "Added product to cart", Store, testName);

			// lenovo checkout			
			b2cPage.lenovo_checkout.click();
			if(Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)){
				b2cPage.ASM_EditAddress.click();
			}

			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					"Auto19782@sharklasers.com");
			Thread.sleep(3000);
			// continue
			WebElement continueButton = driver.findElement(By.id("checkoutForm-shippingContinueButton"));
			// executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", continueButton);
//			// UI of validate info is different in each region
//			if (Common.isElementExist(driver, By.id("address_sel"))) {
//				// US
//				Select dropdown = new Select(driver.findElement(By.id("address_sel")));
//				dropdown.selectByIndex(1);
//				b2cPage.Shipping_AddressMatchOKButton.click();
//			} else if (Common.isElementExist(driver, By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
//				if (driver.findElement(By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]"))
//						.isDisplayed()) {
//					// USEPP
//					driver.findElement(By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]")).click();
//				}
//				// USBPCTO
//				b2cPage.Shipping_AddressMatchOKButton.click();
//			}
			handleAddressVerify(driver, b2cPage);
//			B2CCommon.handleAddressVerify(driver, b2cPage);
			Dailylog.logInfoDB(9, "check out to payment", Store, testName);
			//span[@class='price-calculator-order-summary-subTotalWithoutCoupon']
			String totalPrice_Checkout;
			if(Common.isElementExist(driver, By.xpath("//span[contains(@class,'totalPriceWithTax')]"))){
				WebElement totalPriceWithTax = driver.findElement(By.xpath("//span[contains(@class,'totalPriceWithTax')]"));
				totalPrice_Checkout = totalPriceWithTax.getText();
			}else{
				WebElement totalPriceWithTax_new = driver.findElement(By.xpath("//div[@id='summaryTotalPriceDiv']/div[@class='right']/span[@id='withTaxTotal']"));
				totalPrice_Checkout = totalPriceWithTax_new.getText();
			}
			
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_LFS")),
					"LFS payment should display");
			Dailylog.logInfoDB(10, "On Payment page, available leasing payments are showing up", Store, testName);

			// Leasing payment
//			holdButton(b2cPage.payment_LFS);
			b2cPage.payment_LFS.click();
			// Check if purchase order payment display
			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {
				if (b2cPage.payment_purchaseNum.isDisplayed()) {
					b2cPage.payment_purchaseNum.sendKeys("1234567890");
					b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);
				}
			}
			
			

			// fill payment info, only need to fill name and phone
//			fillPaymentAddressInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressPhone());
			// continue
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			
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
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			// check price in order summary review page
			String totalPrice_Review ;
			if(Common.isElementExist(driver, By.xpath("//dd[contains(@class,'totalValue')]"))){
				WebElement totalPriceWithTax = driver.findElement(By.xpath("//dd[contains(@class,'totalValue')]"));
				totalPrice_Review = totalPriceWithTax.getText().toString();
			}else{
				WebElement totalPriceWithTax_new = driver.findElement(By.xpath("//div[@class='summary-item total']/div[@class='right']"));
				totalPrice_Review = totalPriceWithTax_new.getText().toString();
			}
			
			Assert.assertEquals(totalPrice_Review, totalPrice_Checkout,
					"Price in review page should be the same as checkout page");
			// place order
			Thread.sleep(500);
			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(11, "Continue to place order", Store, testName);
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			System.out.println("orderNum is :" + orderNum);

		

			// The Application URL ON Step 2 is show on this page
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//a[text()='" + laUrl + "']")),
					"Application URL should display");
			Dailylog.logInfoDB(12, "check url on Thank you page", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@AfterTest
	public void rollback(ITestContext ctx) {
		try {

			System.out.println("rollback"); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
//			Common.NavigateToUrl(testData.HMC.getHomePageUrl());
			Common.NavigateToUrl(driver, BillNumber,testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 3)) {
				hmcPage.Home_EndSessionLink.click();
			}
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentLeasingB2C.click();
			Common.sleep(500);
			hmcPage.B2CLeasing_billToNumber.clear();
			hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
			hmcPage.PaymentLeasing_searchbutton.click();
			if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'No results')]"))) {
				// delete b2c leasing setting
				Common.scrollToElement(driver, hmcPage.PaymentLeasing_openEditor1);
				hmcPage.PaymentLeasing_openEditor1.click();
				hmcPage.PaymentLeasing_delete.click();
				if (isAlertPresent(driver))
					driver.switchTo().alert().accept();
			}
			holdButton(hmcPage.HMC_Logout);
			driver.close();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void paymentLeasingSetting(String min, String max, String laUrl, String country, String payment)
			throws InterruptedException {
		// Go to HMC-> Nemo -> Payment -> Payment Leasing Setting->search
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		Common.javascriptClick(driver, hmcPage.Home_paymentLeasingSetting);
		Select dropdown = new Select(hmcPage.PaymentLeasing_leasingType);
		dropdown.selectByVisibleText(payment);
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		dropdown.selectByVisibleText(Country);
		if(Browser.equals("ie")) {
			Common.doubleClick(driver, hmcPage.PaymentLeasing_searchbutton);
			hmcPage.PaymentLeasing_searchbutton.click();
		}else {
			hmcPage.PaymentLeasing_searchbutton.click();
		}
		
	

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

		Thread.sleep(6000);
		hmcPage.Home_payment.click();
		hmcPage.Home_Nemo.click();
	}

	public void b2cLeasingSetting(String billTo, String isNaLeasingOptionText, String country, String payment)
			throws InterruptedException {
		// Go to HMC -> Nemo -> Payment -> B2C Leasing Setting->Search
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		Common.javascriptClick(driver, hmcPage.Home_paymentLeasingB2C);
		Common.sleep(500);
		hmcPage.B2CLeasing_billToNumber.clear();
		hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		// Select dropdown = new Select(hmcPage.B2CLeasing_NALeasing);
		// dropdown.selectByVisibleText(isNaLeasingOptionText);
		hmcPage.PaymentLeasing_searchbutton.click();

		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'No results')]"))) {
			// If no result: Create B2C Leasing Setting
			Common.scrollToElement(driver, hmcPage.Home_paymentLeasingB2C);
			Common.rightClick(driver, hmcPage.Home_paymentLeasingB2C);
			Common.waitElementClickable(driver, hmcPage.B2CLeasing_createB2CLeasing, 8000);
			hmcPage.B2CLeasing_createB2CLeasing.click();

		} else {
			// If searched out one result: Open this Leasing setting to edit
			// Common.doubleClick(driver,hmcPage.PaymentLeasing_searchedResults);
			hmcPage.PaymentLeasing_openEditor1.click();
		}
		// Edit or Create data - Leasing Setting
		if(Browser.equals("ie")) {
			Common.rightClick(driver, hmcPage.B2CLeasing_leasingSetting);
			driver.findElement(By.xpath("//tr[@id='Content/GenericReferenceEditor[in Content/Attribute[.paymentLeasingSetting]]_open_search_true_tr']/td[contains(.,'Search')]")).click();
		}else {
			hmcPage.B2CLeasing_leasingSetting.click();
		}
		switchToWindow(1);
		Select dropdown = new Select(hmcPage.PaymentLeasing_leasingType);
		dropdown.selectByVisibleText(payment);
		dropdown = new Select(hmcPage.PaymentLeasing_country);
		dropdown.selectByVisibleText(country);
		if(Browser.equals("ie")) {
			Common.doubleClick(driver, hmcPage.PaymentLeasing_searchbutton);
			hmcPage.PaymentLeasing_searchbutton.click();
		}else {
			hmcPage.PaymentLeasing_searchbutton.click();
		}
		Common.sleep(3000);
		holdButton(hmcPage.B2CLeasing_searchedResult1);
//		hmcPage.B2CLeasing_searchedResult1.click();
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
			if (isAlertPresent(driver))
				driver.switchTo().alert().accept();
		}
		Common.sleep(3000);
		Common.rightClick(driver, hmcPage.B2CLeasing_billtoList);
		holdButton(hmcPage.B2CLeasing_add);
//		hmcPage.B2CLeasing_add.click();
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
		holdButton(hmcPage.B2CLeasing_searchedResult1);
//		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();
		// Save
		switchToWindow(0);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Common.sleep(6000);
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
		Common.scrollToElement(driver, hmcPage.B2CUnit_paymentTypeTable);
		Common.rightClick(driver, hmcPage.B2CUnit_paymentTypeTable);
		holdButton(hmcPage.B2CLeasing_add);
//		hmcPage.B2CLeasing_add.click();
		switchToWindow(1);
		hmcPage.B2CUnit_paymentTypeInput.sendKeys(payment);
		hmcPage.PaymentLeasing_searchbutton.click();
		holdButton(hmcPage.B2CLeasing_searchedResult1);
//		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();

		// B2C Leasing Settings: --> add LFS
		switchToWindow(0);
		dLeasingItems();
		Common.sleep(3000);
		Common.scrollToElement(driver, driver.findElement(By.xpath("//*[contains(text(),'B2C Leasing Settings:')]")));
		Common.rightClick(driver, hmcPage.B2CUnit_B2CLeasingTable);
		holdButton(hmcPage.B2CLeasing_add);
//		hmcPage.B2CLeasing_add.click();
		switchToWindow(1);
		hmcPage.B2CLeasing_billToNumber.clear();
		hmcPage.B2CLeasing_billToNumber.sendKeys(billTo);
		Select dropdown = new Select(hmcPage.B2CLeasing_NALeasing);
		dropdown.selectByVisibleText(isNaLeasingOptionText);
		hmcPage.PaymentLeasing_searchbutton.click();
		Common.sleep(3000);
		holdButton(hmcPage.B2CLeasing_searchedResult1);
//		hmcPage.B2CLeasing_searchedResult1.click();
		hmcPage.B2CLeasing_use.click();
		// Save
		switchToWindow(0);
		holdButton(hmcPage.PaymentLeasing_saveAndCreate);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Common.sleep(18000);
		hmcPage.Home_B2CCommercelink.click();
	}

	private void dLeasingItems() throws InterruptedException {
		String leasingItem = "//*[contains(text(),'B2C Leasing Settings:')]/../..//table[@class='genericItemListChip']/tbody//tbody//tr[contains(.,'empty')]";
		if (!Common.isElementExist(driver, By.xpath(leasingItem))) {
			System.out.println("delete previous leasing settings");
			Common.sleep(3000);
			Common.scrollToElement(driver, driver.findElement(By.xpath("//*[contains(text(),'B2C Leasing Settings:')]")));
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
			Common.sleep(1000);
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			System.out.println("Swith to window timeout, windowNo: " + windowNo);
		} catch (InterruptedException e) {

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

	public boolean isAlertPresent(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	



	public void checkout() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].click();",
		// b2cPage.Cart_CheckoutButton);
		Thread.sleep(1000);
		b2cPage.Cart_CheckoutButton.click();
		if (Common.checkElementExists(driver, b2cPage.Shipping_editAddress, 5))
			b2cPage.Shipping_editAddress.click();
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
		Common.sleep(500);
		System.out.println(driver.getCurrentUrl() + "    " + Store);
		Assert.assertTrue(driver.getCurrentUrl().indexOf("payment") >= 0, "go to payment page successfully " + Store);
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


	public void holdButton(WebElement webElement) {
		Actions action = new Actions(driver);
		action.moveToElement(webElement).clickAndHold(webElement).click().build().perform();
	}
	
	public  void handleAddressVerify(WebDriver driver, B2CPage b2cPage)  {
		if (Common.checkElementDisplays(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
			b2cPage.Shipping_AddressMatchOKButton.click();
		if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 1))
			b2cPage.ValidateInfo_SkipButton.click();
		if (!Common.checkElementInvisible(driver, b2cPage.ValidateInfo_UseSuggestButton, 1)){
			
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_customertype, 5)){
				b2cPage.ValidateInfo_customertype.click();
			}
			holdButton(b2cPage.ValidateInfo_UseSuggestButton);
		}
			
	}
}
