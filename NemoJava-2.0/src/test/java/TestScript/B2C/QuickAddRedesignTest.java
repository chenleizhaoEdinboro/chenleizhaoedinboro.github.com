package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class QuickAddRedesignTest extends SuperTestClass {

	B2CPage b2cPage = null;

	Actions actions = null;
	String orderNum = null;
	
	String productName = null;	
	float webPrice = 0;
	float cartWebPrice = 0;
	private String productNOMTM;
	private String productNOCTO;
	private String productNOAccessory;
	private String PaymentExistence = "Y";
	private String paymentMethod="VISA";
	private String paymentElement;
	private boolean flag;

	public QuickAddRedesignTest(String store, String accessoryNumber,String payment) {
		this.Store = store;
		this.testName = "CartCheckoutTest";

		this.productNOAccessory = accessoryNumber;
		this.paymentElement=payment;
	}

	@Test(alwaysRun= true)
	public void CartCheckout(ITestContext ctx) {
		try {
			By InvalidMessage = By.xpath("//div[contains(text(),'The input product code is invalid')]");
			By CTOInvalidMessage=By.xpath("//div[contains(text(),'Currently CTO product cannot be added')]");
			By CartItems=By.xpath("//div[@class='cart-items-wrapper']/div");
			By GuestCheckout = By.xpath(".//*[@id='guestForm']/button");
			By invoiceIndicator = By.xpath("//input[@id='carrierCodeTW']");
			By PaymentInfo_Continue = By.xpath(".//*[@id='add-payment-method-continue']");
			By OrderNumber = By.cssSelector("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)");
			By PaymentXPATH = By.xpath(paymentElement);
			Dailylog.logInfo("Test run for " + Store);

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			actions = new Actions(driver);
			
				productNOMTM = testData.B2C.getDefaultMTMPN();
			
				productNOCTO = testData.B2C.getDefaultCTOPN();
		
		
		
			
				
			

			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			closePromotion(driver, b2cPage);
			
			// MTM
			
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_AddButton));
			b2cPage.Payment_QuickAddBox.sendKeys(productNOMTM);
			b2cPage.Cart_AddButton.click();
			List<WebElement> CartElements = driver.findElements(CartItems);
			assert CartElements.size()==1:"Assertion point1 failed";
			// CTO
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_AddButton));
			b2cPage.Payment_QuickAddBox.sendKeys(productNOCTO);
			b2cPage.Cart_AddButton.click();
			assert Common.isElementExist(driver, CTOInvalidMessage)||Common.isElementExist(driver, InvalidMessage):"Assertion point2 failed";
			//accessory
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_AddButton));
			b2cPage.Payment_QuickAddBox.sendKeys(productNOAccessory);
			b2cPage.Cart_AddButton.click();
			CartElements = driver.findElements(CartItems);
			assert CartElements.size()==2:"Assertion point3 failed";
			//dummy code
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_AddButton));
			b2cPage.Payment_QuickAddBox.sendKeys("111111111");
			b2cPage.Cart_AddButton.click();
			assert Common.isElementExist(driver, InvalidMessage):"Assertion point4 failed";
			//empty code
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_AddButton));
			b2cPage.Cart_AddButton.click();
			assert Common.isElementExist(driver, InvalidMessage):"Assertion point5 failed";
			//repeated code
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_AddButton));
			b2cPage.Payment_QuickAddBox.sendKeys(productNOMTM);
			b2cPage.Cart_AddButton.click();
			CartElements = driver.findElements(CartItems);
			assert CartElements.size()==3:"Assertion point6 failed";
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.Cart_CheckoutButton));
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);
			if (Common.isElementExist(driver, GuestCheckout)) {
				new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.GuestCheckout));
				b2cPage.GuestCheckout.click();
				Thread.sleep(2000);

			}
			ShippingInfo(driver, b2cPage, testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(),
					".//*[@id='state']/option[contains(text(),'"+testData.B2C.getDefaultAddressState()+"')]", testData.B2C.getDefaultAddressPostCode(), "Standard", "lixe1@lenovo.com", this.Store, paymentMethod);

			if (Common.isElementExist(driver, PaymentXPATH) || isCreditCardMethodExist(driver, b2cPage, PaymentXPATH)) {
				Payment(driver, b2cPage, paymentMethod);
				if (Common.isElementExist(driver, invoiceIndicator)) {
					b2cPage.Payment_invoiceType.click();

				}
				if (Common.isElementExist(driver, PaymentInfo_Continue)) {
					b2cPage.ContinueforPayment.click();
				}
				Thread.sleep(2000);

				if (Common.isElementExist(b2cPage.PageDriver, By.id("hidden3DSFrame0"))) {
					B2CCommon.handleVisaVerify(b2cPage);

				}
				Thread.sleep(2000);

				new WebDriverWait(driver, 500)
						.until(ExpectedConditions.elementToBeClickable(b2cPage.OrderSummary_AcceptTermsCheckBox));

				if (!Environment.equals("PreC")&&!Environment.equals("Crt")) {
					assert b2cPage.OrderSummary_PlaceOrderButton
							.isDisplayed() : "System failed to navigate to Review page";
				} else {

					b2cPage.OrderSummary_AcceptTermsCheckBox.click();

					B2CCommon.clickPlaceOrder(b2cPage);

					if (!driver.getCurrentUrl().contains("www3.lenovo.com")) {
						assert Common.isElementExist(driver,
								OrderNumber) : "System failed to navigate to Order Confirmation page";
						orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText().toString();
					}
				}
			} else {
				PaymentExistence = "N";
				// Common.addPassContent(country + "does not have "
				// + paymentMethod, reportLocation);
			}
			if (PaymentExistence.equals("N")) {

				markNAforPayment();
				System.out.println("does not exist but previous steps are fine");
			} else {
				System.out.println(this.Store + " " + paymentMethod + " " + orderNum);

			}
			
			
			
			
			
			
			

		} catch (Throwable e) {
			System.out
					.println(Store + " " + e.getMessage());
			handleThrowable(e, ctx);
		}
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public void ShippingInfo(WebDriver driver, B2CPage page, String addressline, String county, String statelocator,
			String zipcode, String shipping, String email, String country, String payment) throws InterruptedException {
		By locator1 = By.xpath(".//*[@id='checkoutForm-shippingContinueButton']");
		By locator2 = By.xpath("//input[@value='ok']");
		By locator3 = By.xpath("//input[@id='copyAddressToBilling']");
		By locator4 = By.xpath("//input[@value='expedite-shipping-gross']");
		// By editButton = By.xpath("//a[contains(text(),'Edit')]");
		By editButton = By.xpath("//form[@id='addressForm']/fieldset/legend/a");
		By addressOptions = By.xpath("//input[@name='address']");
		By suggestedAddressOption = By.xpath(".//*[@id='checkout_validateFrom_ok']");
		Actions actions = new Actions(driver);
		Thread.sleep(5000);
		if (!payment.equals("LFS") && !payment.equals("IGF")) {
			if (Common.isElementExist(driver, locator3)) {
				actions.moveToElement(page.CopyAddress).click().perform();
			}
		}
		if (Common.isElementExist(driver, editButton)) {
			driver.findElement(editButton).click();

			Thread.sleep(5000);
		}
		page.Shipping_FirstNameTextBox.clear();
		page.Shipping_FirstNameTextBox.sendKeys("Shane88888888");
		page.Shipping_LastNameTextBox.clear();
		page.Shipping_LastNameTextBox.sendKeys("Li88888888");
		page.Shipping_AddressLine1TextBox.clear();
		page.Shipping_AddressLine1TextBox.sendKeys(testData.B2C.getDefaultAddressLine1());
		if (country.equals("HK")) {
			page.Shipping_AddressLine3TextBox.clear();
			page.Shipping_AddressLine3TextBox.sendKeys(testData.B2C.getDefaultAddressCity());
		} else if (!country.equals("KR")) {
			page.ASM_City.clear();
			page.ASM_City.sendKeys(testData.B2C.getDefaultAddressCity());
			page.Shipping_PostCodeTextBox.clear();
			page.Shipping_PostCodeTextBox.sendKeys(testData.B2C.getDefaultAddressPostCode());
		} else {
			page.Shipping_PostCodeTextBox.clear();
			page.Shipping_PostCodeTextBox.sendKeys(testData.B2C.getDefaultAddressPostCode());
		}
		WebElement state = driver.findElement(By.xpath(statelocator));
		state.click();
		// b2cPage.State.click();

		page.Mobile.clear();
		page.Mobile.sendKeys("2022022020");

		page.Shipping_EmailTextBox.clear();
		page.Shipping_EmailTextBox.sendKeys("19353PreC@yopmail.com");
		// addPassContent("Enter shipping address successfully",
		// reportLocation);
		Thread.sleep(3000);
		((JavascriptExecutor) driver).executeScript("scroll(0,300)");
		if (shipping.equals("Expedited")) {
			if (Common.isElementExist(driver, locator4)) {
				actions.moveToElement(page.Payment_ExpeditedGrossShipping).click().perform();
			} else {
				actions.moveToElement(page.Payment_ExpeditedNetShipping).click().perform();
			}

			Thread.sleep(3000);

		} else if (shipping.equals("Standard")) {
			actions.moveToElement(page.standardShipping).click().perform();

			Thread.sleep(3000);
		}

		actions.moveToElement(page.Shipping_ContinueButton).click().perform();
		Thread.sleep(10000);
		if (Common.isElementExist(driver, locator2)) {

			if (Common.isElementExist(driver, addressOptions)) {
				page.Shipping_AddressOptionsList.click();
			}
			page.addressValidation.click();
			Thread.sleep(3000);
		}
		if (Common.isElementExist(driver, suggestedAddressOption)) {
			page.Shipping_SuggestedAddress.click();
			Thread.sleep(3000);
		}
		while (Common.isElementExist(driver, locator1)) {
			actions.moveToElement(page.Shipping_ContinueButton).click().perform();

			Thread.sleep(5000);
		}
		Thread.sleep(5000);
	}

	public void Payment(WebDriver driver, B2CPage page, String paymentMethod) throws InterruptedException {
		Thread.sleep(5000);
		By locator4 = By.xpath("//input[@name='external.field.password']");
		By LocatorDatePicker = By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
		Actions actions = new Actions(driver);

		if (paymentMethod.equals("VISA")) {
			actions.moveToElement(page.Payment_CreditCard).click().perform();

			driver.switchTo().frame(page.Payment_CreditCardFrame);
			page.Visa.click();
			page.Payment_CardNumberTextBox.sendKeys("4111111111111111");
			page.Payment_CardMonthTextBox.sendKeys("06");
			page.Payment_CardYearTextBox.sendKeys("20");
			page.Payment_SecurityCodeTextBox.sendKeys("132");
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			page.Payment_CardHolderNameTextBox.sendKeys("LIXE");

			if (Common.isElementExist(driver, locator4)) {
				page.VisaPassword.sendKeys("1234");
				page.VisaSubmit.click();
			}
			Thread.sleep(5000);

		} else if (paymentMethod.equals("AMEX_B2C")) {
			actions.moveToElement(page.Payment_CreditCard).click().perform();

			driver.switchTo().frame(page.Payment_CreditCardFrame);
			page.AmericaExpress.click();
			page.Payment_CardNumberTextBox.sendKeys("378282246310005");
			page.Payment_CardMonthTextBox.sendKeys("06");
			page.Payment_CardYearTextBox.sendKeys("20");
			page.Payment_SecurityCodeTextBox.sendKeys("132");
			driver.switchTo().defaultContent();
			page.Payment_CardHolderNameTextBox.sendKeys("LIXE");

		} else if (paymentMethod.equals("Master_B2C")) {
			actions.moveToElement(page.Payment_CreditCard).click().perform();

			driver.switchTo().frame(page.Payment_CreditCardFrame);
			page.MasterCard.click();
			page.Payment_CardNumberTextBox.sendKeys("5555555555554444");
			page.Payment_CardMonthTextBox.sendKeys("06");
			page.Payment_CardYearTextBox.sendKeys("20");
			page.Payment_SecurityCodeTextBox.sendKeys("132");
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			page.Payment_CardHolderNameTextBox.sendKeys("LIXE");

		} else if (paymentMethod.equals("Discover_B2C")) {
			actions.moveToElement(page.Payment_CreditCard).click().perform();

			driver.switchTo().frame(page.Payment_CreditCardFrame);
			page.Discover.click();
			page.Payment_CardNumberTextBox.sendKeys("6011111111111117");
			page.Payment_CardMonthTextBox.sendKeys("06");
			page.Payment_CardYearTextBox.sendKeys("20");
			page.Payment_SecurityCodeTextBox.sendKeys("132");
			driver.switchTo().defaultContent();
			page.Payment_CardHolderNameTextBox.sendKeys("LIXE");

		} else if (paymentMethod.equals("IGF_B2C") || paymentMethod.equals("LFS_B2C")) {
			actions.moveToElement(page.Leasing).click().perform();
			Thread.sleep(8000);

		} else if (paymentMethod.equals("TwoCards_B2C")) {

			actions.moveToElement(page.TwoCardRadioButton).click().perform();

			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(page.FirstCardAmount));
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

		} else if (paymentMethod.equals("Paypal_B2C")) {
			actions.moveToElement(page.PaypalButton).click().perform();

		} else if (paymentMethod.equals("Check_B2C")) {
			actions.moveToElement(page.PayByCheck).click().perform();
			Thread.sleep(2000);

		} else if (paymentMethod.equals("Wire_B2C")) {
			actions.moveToElement(page.Wire).click().perform();
			Thread.sleep(2000);

		} else if (paymentMethod.equals("KCP_B2C")) {
			actions.moveToElement(page.KCPButton).click().perform();
			page.KCPOption.click();
		} else if (paymentMethod.equals("Deposit_B2C")) {
			Thread.sleep(2000);
			actions.moveToElement(page.DirectDeposit).click().perform();
			Thread.sleep(2000);

		} else if (paymentMethod.equals("LenovoCard_B2C")) {
			actions.moveToElement(page.LenovoCard).click().perform();
			Thread.sleep(2000);
			page.LenovoCardNumber.sendKeys("7788401125064840");
			actions.moveToElement(page.LenovoCardResolving).click().perform();
			page.LenovoCardHolderName.sendKeys("BLISS BARBARA");

		}
		if (page.purchaseNum.isDisplayed() && page.purchaseNum.isEnabled()) {
			page.purchaseNum.sendKeys("1234567654");
			SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
			page.purchaseDate.sendKeys(dataFormat.format(new Date()).toString());
			if (Common.isElementExist(driver, By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))) {
				driver.findElements(LocatorDatePicker).get(driver.findElements(LocatorDatePicker).size() - 1).click();
			}
		}

		page.ContinueforPayment.click();
		Thread.sleep(5000);
		if (paymentMethod.equals("Paypal_B2C")) {

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
	public boolean isCreditCardMethodExist(WebDriver driver, B2CPage page, By payment) throws InterruptedException {
		boolean result = false;
		By Card = By.xpath("//input[@value='CREDIT_CARD']");
		if (Common.isElementExist(driver, Card)) {
			Actions actions = new Actions(driver);

			actions.moveToElement(page.Payment_CreditCard).click().perform();

			Thread.sleep(3000);
			driver.switchTo().frame(page.Payment_CreditCardFrame);
			result = Common.isElementExist(driver, payment);
			driver.switchTo().defaultContent();
		}

		return result;
	}
	
}
