package TestScript.B2B;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19422Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String homepageUrl;
	public String cartUrl;
	Actions actions = null;
	String orderNum = null;

	private PaymentType paymentType;

	public NA19422Test(String store, PaymentType type) {
		this.Store = store;
		this.testName = type.toString();
		this.paymentType = type;
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2b"})
	public void NA19422(ITestContext ctx) {
		try {
			this.prepareTest();
			By CustomiseAdd = By.xpath(".//*[@id='resultList']/div/div[4]/a");
			By customiseAddtoCart = By.xpath("//button[@class='add-to-cart']");
			// By AddtoCartStatus = By
			// .xpath("//h2[contains(text(),'Adding to Cart')]");
			By AddressValidation = By.xpath("//input[@value='ok']");
			By closeBundle = By.xpath(".//*[@id='bundleAlert']/div/div/div[1]/button[@aria-hidden='false']");
			By warningAdd = By.xpath("//button[@id='b_alert_add_to_cart']");
			By configWarning = By.xpath(
					"//div[@id='bundleAlert'  and @style='display: block;']//button[contains(text(),'Customize')]");

			b2bPage = new B2BPage(driver);
			actions = new Actions(driver);
			Common.NavigateToUrl(driver, Browser, testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), "1q2w3e4r");
			b2bPage.Homepage_ClickCart.click();

			if (Common.checkElementDisplays(driver, b2bPage.CartPage_EmptyCart, 1)) {
				b2bPage.CartPage_EmptyCart.click();
			}
			new WebDriverWait(driver, 50)
					.until(ExpectedConditions.elementToBeClickable(b2bPage.HomePage_productsLink));

			b2bPage.HomePage_productsLink.click();
			Thread.sleep(2000);
			b2bPage.HomePage_Laptop.click();
			if (Common.isElementExist(driver, CustomiseAdd)) {

				b2bPage.Payment_Customize.click();
				Thread.sleep(3000);
				if (Common.isElementExist(driver, configWarning)) {
					b2bPage.Config_BundleAlert.click();
				}

				if (Common.isElementExist(driver, warningAdd) && b2bPage.warningAdd.isDisplayed()) {

					driver.findElement(By.xpath("//button[@id='b_alert_add_to_cart']")).click();
					Thread.sleep(3000);
					try {
						if (b2bPage.getAddtoCartPB.isDisplayed() && b2bPage.getAddtoCartPB.isEnabled()) {
							b2bPage.getAddtoCartPB.click();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					if (Common.isElementExist(driver, customiseAddtoCart)) {

						((JavascriptExecutor) driver).executeScript("scroll(0,250)");
						new WebDriverWait(driver, 300)
								.until(ExpectedConditions.elementToBeClickable(b2bPage.agreementsAddToCart));
						b2bPage.agreementsAddToCart.click();

						Thread.sleep(3000);
						try {
							if (b2bPage.accessoryTabAdd.isEnabled()) {
								b2bPage.accessoryTabAdd.click();
							}
						} catch (Exception e) {
							System.out.println("accessoryTabAdd not exist");
						}

					} else {
						Thread.sleep(3000);
						if (Common.isElementExist(driver, closeBundle)) {
							driver.findElement(By.xpath(".//*[@id='bundleAlert']/div/div/div[1]/button")).click();
						}
						((JavascriptExecutor) driver).executeScript("scroll(0,350)");
						// actions.moveToElement(b2bPage.CTOAddtoCartPDP).build()
						// .perform();
						b2bPage.CTOAddtoCartPDP.click();

						if (b2bPage.getAddtoCartPB.isEnabled()) {
							b2bPage.getAddtoCartPB.click();
						}

					}
				}
			} else {
				Thread.sleep(3000);
				b2bPage.AddForm_AddToCartBtn.click();
				b2bPage.addtoCartPOP.click();
				Common.waitElementVisible(driver,
						driver.findElement(By.xpath("//div[@id='cboxContent']//h2[text()='Added to Cart']")));
				Thread.sleep(3000);
				b2bPage.goToCartPop.click();
			}

			new WebDriverWait(driver, 50).until(ExpectedConditions.elementToBeClickable(b2bPage.lenovoCheckout));
			if (paymentType != PaymentType.Amazon_B2B) {

				b2bPage.lenovoCheckout.click();
				if (Common.checkElementExists(driver, b2bPage.shippingPage_EditAddress, 1)) {

					b2bPage.shippingPage_EditAddress.click();
				}
				b2bPage.shippingPage_CompanyName.clear();
				b2bPage.shippingPage_CompanyName.sendKeys("Company");
				b2bPage.shippingPage_AddressLine1.clear();
				b2bPage.shippingPage_AddressLine1.sendKeys(testData.B2B.getAddressLine1());
				b2bPage.shippingPage_CityOrSuburb.clear();
				b2bPage.shippingPage_CityOrSuburb.sendKeys(testData.B2B.getAddressCity());
				b2bPage.shippingPage_PostCode.clear();
				b2bPage.shippingPage_PostCode.sendKeys(testData.B2B.getPostCode());

				Select state = new Select(b2bPage.shippingPage_State);
				state.selectByVisibleText(testData.B2B.getAddressState());

				b2bPage.ShippingEmail.clear();
				b2bPage.ShippingEmail.sendKeys("testB2BPayement@yopmail.com");
				b2bPage.FirstName.clear();
				b2bPage.FirstName.sendKeys("Test1234567");
				b2bPage.LastName.clear();
				b2bPage.LastName.sendKeys("Test1234567");
				b2bPage.Mobile.clear();
				b2bPage.Mobile.sendKeys("1234567890");
				b2bPage.shippingPage_ContinueToPayment.click();
				if (Common.isElementExist(driver, AddressValidation)) {

					b2bPage.shippingPage_Addressvalidation.click();
					Thread.sleep(3000);

				}
				new WebDriverWait(driver, 50).until(ExpectedConditions.elementToBeClickable(b2bPage.addressFirstName));
//				B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
			}
			
			B2CPage b2cPage = new B2CPage(driver);
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, paymentType)) {
				CommonFunction.DesignHandler.Payment.payAndContinue(b2cPage, paymentType, testData);

				if (!Environment.equals("PreC")) {
					Thread.sleep(5000);
					String currentURL = driver.getCurrentUrl();

					assert currentURL.contains("summary") : Store + "  " + paymentType + "  "
							+ "System failed to navigate to Review page";
				} else {
					Thread.sleep(5000);
					if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)) {
						b2bPage.placeOrderPage_ResellerID.clear();
						b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
					}
					Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
					Common.javascriptClick(driver, b2bPage.placeOrderPage_PlaceOrder);
					Thread.sleep(3000);
					assert Common.checkElementDisplays(driver, b2bPage.orderPage_orderNumber, 1) : Store + "  "
							+ paymentType + "  " + "System failed to navigate to Order Confirmation page";
					Dailylog.logInfoDB(9, "Order Number is: " + b2bPage.orderPage_orderNumber.getText(), this.Store,
							paymentType.toString());
				}
			} else {
				this.markNAforPayment();
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public boolean isCreditCardMethodExist(WebDriver driver, B2BPage page, By payment) throws InterruptedException {
		boolean result = false;
		By Card = By.xpath("//input[@id='PaymentTypeSelection_CARD']");

		if (Common.isElementExist(driver, Card)) {
			page.credit_cardPayment.click();

			Thread.sleep(3000);

			driver.switchTo().frame(page.creditCardFrame);
			result = Common.isElementExist(driver, payment);
			driver.switchTo().defaultContent();
		}

		return result;
	}

	public void Payment(WebDriver driver, B2BPage page, String paymentMethod, String addressline, String county,
			String statelocator, String zipcode) throws InterruptedException {
		Thread.sleep(6000);
		By locator4 = By.xpath("//input[@name='external.field.password']");
		By LocatorDatePicker = By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
		// By PurchaseOrder = By.xpath("//input[@id='purchase_orderNumber']");
		Actions actions = new Actions(driver);

		if (paymentMethod.equals("VISA_B2B")) {
			actions.moveToElement(page.credit_cardPayment).click().perform();

			driver.switchTo().frame(page.creditCardFrame);
			page.Visa.click();
			page.CardNumber.sendKeys("4111111111111111");
			page.ExpiryMonth.sendKeys("06");
			page.ExpiryYear.sendKeys("20");
			page.SecurityCode.sendKeys("132");
			driver.switchTo().defaultContent();
			page.NameonCard.sendKeys("LIXE");

			if (Common.isElementExist(driver, locator4)) {
				page.VisaPassword.sendKeys("1234");
				page.VisaSubmit.click();
			}
			Thread.sleep(6000);

		} else if (paymentMethod.equals("AMEX_B2B")) {
			actions.moveToElement(page.credit_cardPayment).click().perform();

			driver.switchTo().frame(page.creditCardFrame);
			page.AmericaExpress.click();
			page.CardNumber.sendKeys("378282246310005");
			page.ExpiryMonth.sendKeys("06");
			page.ExpiryYear.sendKeys("20");
			page.SecurityCode.sendKeys("132");
			driver.switchTo().defaultContent();
			page.NameonCard.sendKeys("LIXE");

		} else if (paymentMethod.equals("Master_B2B")) {
			actions.moveToElement(page.credit_cardPayment).click().perform();

			driver.switchTo().frame(page.creditCardFrame);
			page.MasterCard.click();
			page.CardNumber.sendKeys("5555555555554444");
			page.ExpiryMonth.sendKeys("06");
			page.ExpiryYear.sendKeys("20");
			page.SecurityCode.sendKeys("132");
			driver.switchTo().defaultContent();
			page.NameonCard.sendKeys("LIXE");

		} else if (paymentMethod.equals("Discover_B2B")) {
			actions.moveToElement(page.credit_cardPayment).click().perform();

			driver.switchTo().frame(page.creditCardFrame);
			page.Discover.click();
			page.CardNumber.sendKeys("6011111111111117");
			page.ExpiryMonth.sendKeys("06");
			page.ExpiryYear.sendKeys("20");
			page.SecurityCode.sendKeys("132");
			driver.switchTo().defaultContent();
			page.NameonCard.sendKeys("LIXE");

		} else if (paymentMethod.equals("IGF_B2B")) {
			actions.moveToElement(page.leasing_IGF_payment).click().perform();
			Thread.sleep(8000);

		} else if (paymentMethod.equals("LFS_B2B")) {
			actions.moveToElement(page.leasing_LFS_payment).click().perform();
			Thread.sleep(8000);
		} else if (paymentMethod.equals("TwoCards_B2B")) {

			actions.moveToElement(page.TwoCardRadioButton).click().perform();

			new WebDriverWait(driver, 50).until(ExpectedConditions.elementToBeClickable(page.FirstCardAmount));
			page.FirstCardAmount.clear();
			page.FirstCardAmount.sendKeys("10.00");
			Thread.sleep(3000);
			page.PaymentIndicator.click();

			page.TwocardsNameOnCard1.sendKeys("LIXE");
			page.TwocardsNameOnCard2.sendKeys("LIXE");
			Thread.sleep(2000);
			driver.switchTo().frame(page.FirstCardIframe);

			Thread.sleep(2000);
			page.TwoCardsType.click();

			page.TwoCardsNumber.sendKeys("4111111111111111");

			page.TwocardsMonth.sendKeys("06");
			page.TwocardsYear.sendKeys("20");
			page.TwocardsCV.sendKeys("123");

			driver.switchTo().defaultContent();
			driver.switchTo().frame(page.SecondCardIframe);

			page.TwoCardsType.click();
			page.TwoCardsNumber.sendKeys("4222222222222");
			page.TwocardsMonth.sendKeys("07");
			page.TwocardsYear.sendKeys("21");
			page.TwocardsCV.sendKeys("124");

			driver.switchTo().defaultContent();
			Thread.sleep(5000);

		} else if (paymentMethod.equals("Paypal_B2B")) {
			actions.moveToElement(page.PaypalButton).click().perform();

		} else if (paymentMethod.equals("Deposit_B2B")) {
			actions.moveToElement(page.DirectDeposit).click().perform();
			Thread.sleep(2000);

		} else if (paymentMethod.equals("PurchaseOrder_B2B")) {
			actions.moveToElement(page.purchaseOrder).click().perform();
			Thread.sleep(2000);
			page.purchaseNum.clear();
			page.purchaseNum.sendKeys("1234563242");
			SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
			page.purchaseDate.sendKeys(dataFormat.format(new Date()).toString());
			if (Common.isElementExist(driver, By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))) {
				driver.findElements(LocatorDatePicker).get(driver.findElements(LocatorDatePicker).size() - 1).click();
			}

		}

		if (page.purchaseNum.isDisplayed() && page.purchaseNum.isEnabled()) {
			page.purchaseNum.sendKeys("1234568765");
			SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
			page.purchaseDate.sendKeys(dataFormat.format(new Date()).toString());
			if (Common.isElementExist(driver, By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))) {
				driver.findElements(LocatorDatePicker).get(driver.findElements(LocatorDatePicker).size() - 1).click();
			}
		}
		page.paymentPage_FirstName.clear();
		page.paymentPage_FirstName.sendKeys("Test1234567");
		page.paymentPage_LastName.clear();
		page.paymentPage_LastName.sendKeys("Test1234567");
		page.paymentPage_Phone.clear();
		page.paymentPage_Phone.sendKeys("1234567654");
		if ("true".equals(page.paymentPage_addressLine1.getAttribute("editable"))) {
			page.paymentPage_addressLine1.clear();
			page.paymentPage_addressLine1.sendKeys(addressline);
			page.paymentPage_cityOrSuburb.clear();
			page.paymentPage_cityOrSuburb.sendKeys(county);
			WebElement paymentstate = driver.findElement(By.xpath(statelocator.replace("state", "address.region")));
			paymentstate.click();
			page.paymentPage_addressPostcode.clear();
			page.paymentPage_addressPostcode.sendKeys(zipcode);
		}
		page.ContinueforPayment.click();
		Thread.sleep(5000);
		if (paymentMethod.equals("Paypal")) {

			Thread.sleep(8000);
			driver.switchTo().frame(page.PaypalLoginFrame);
			page.PaypalEmail.sendKeys("accept@lenovo.com");

			page.PaypalPassword.sendKeys("Hybris@sap");
			page.PaypalSignin.click();

			Thread.sleep(15000);
			page.PaypalContinue.click();
			Thread.sleep(10000);
		}

	}

	@SuppressWarnings("static-access")
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

	public int getOrderRecordsNumber(WebDriver driver, HMCPage page, String url, String date, String customer)
			throws Exception {
		int currentRecords = 0;
		String customerXpath = ".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[Order.user]]_ajaxselect_"
				+ customer + "']";
		String totalRecords;
		Common.NavigateToUrl(driver, Browser, url);
		HMCCommon.Login(page, testData);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		Thread.sleep(5000);
		page.Order.click();
		page.Orders.click();
		page.OrderDate.clear();
		page.OrderDate.sendKeys(date);
		page.CustomerID.clear();
		page.CustomerID.sendKeys(customer);
		Thread.sleep(2000);
		WebElement CustomerOption = driver.findElement(By.xpath(customerXpath));
		CustomerOption.click();

		page.OrderSearch.click();
		Thread.sleep(5000);
		totalRecords = page.NumberofOrders.getText().toString().replaceAll(" ", "");
		String[] temp = totalRecords.split("of");
		currentRecords = Integer.parseInt(temp[temp.length - 1]);
		return currentRecords;

	}

	public void isOrderGenerated(WebDriver driver, HMCPage page, int previousRecord) throws Exception {
		String totalRecords;
		int currentRecords = 0;
		page.OrderSearch.click();
		Thread.sleep(5000);
		totalRecords = page.NumberofOrders.getText().toString().replaceAll(" ", "");
		String[] temp = totalRecords.split("of");
		currentRecords = Integer.parseInt(temp[temp.length - 1]);
		assert previousRecord == currentRecords : "A new order has been generated in HMC!";

	}

}
