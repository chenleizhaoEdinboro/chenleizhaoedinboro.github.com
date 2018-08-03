package CommonFunction.DesignHandler;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Pages.B2BPage;
import Pages.B2CPage;
import TestData.TestData;
import junit.framework.Assert;

public class Payment {

	public static boolean isPaymentMethodExists(B2CPage b2cPage, PaymentType type) throws InterruptedException {
		Common.checkElementInvisible(b2cPage.PageDriver, b2cPage.Shipping_ContinueButton, 10);
		switch (type) {
		case Visa_B2C:
			return isCreditCardPaymentExists(b2cPage, "Visa");
		case VISA_B2B:
			return isCreditCardPaymentExists(b2cPage, "Visa");
		case AmericanExpress_B2C:
			return isCreditCardPaymentExists(b2cPage, "American Express");
		case AMEX_B2B:
			return isCreditCardPaymentExists(b2cPage, "American Express");
		case Master_B2C:
			return isCreditCardPaymentExists(b2cPage, "Mastercard");
		case Master_B2B:
			return isCreditCardPaymentExists(b2cPage, "Mastercard");
		case JCB_B2C:
			return isCreditCardPaymentExists(b2cPage, "JCB");
		case Discover_B2C:
			return isCreditCardPaymentExists(b2cPage, "Discover");
		case Discover_B2B:
			return isCreditCardPaymentExists(b2cPage, "Discover");
		case TwoCards_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.TwoCardRadioButton, 3);
		case Klarna_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Klarna_ApplyNow, 3);
		case Zibby_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_ZibbyRadioButton, 3);
		case Deposit_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_bankDeposit, 3);
		case Deposit_B2B:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_bankDeposit, 3);
		case PurchaseOrder_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder, 3);
		case PurchaseOrder_B2B:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder, 3);
		case PayU_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.paymentPage_payU_Method, 3);
		case JACCS_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Jaccs_PaymentMode, 3);
		case Visa3DS_B2C:
			return isCreditCardPaymentExists(b2cPage, "Visa");
		case Master3DS_B2C:
			return isCreditCardPaymentExists(b2cPage, "Mastercard");
		case Boleto_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_Boleto, 3);
		case Wire_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Wire, 3);
		case Mercado_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_Mercado, 3);
		case Amazon_B2C:
			return isAmazonPaymentExists(b2cPage);
		case Amazon_B2B:
			return isAmazonPaymentExists(b2cPage);
		case LFS_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_LFS, 3);
		case LFS_B2B:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_LFS, 3);
		case IGF_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_IGF, 3);
		case IGF_B2B:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_IGF, 3);
		case Party_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_Party, 3);
		case Party_B2B:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.payment_Party, 3);
		case Paypal_B2C:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.PaypalButton, 3);
		case PayPal_B2B:
			return Common.checkElementExists(b2cPage.PageDriver, b2cPage.PaypalButton, 3);
		default:
			return false;
		}
	}

	public static void payAndContinue(B2CPage b2cPage, PaymentType type, TestData testData)
			throws InterruptedException {
		switch (type) {
		case Visa_B2C:
			payWithVisa(b2cPage, testData);
			break;
		case VISA_B2B:
			payWithVisa(b2cPage, testData);
			break;
		case AmericanExpress_B2C:
			payWithAmericanExpress(b2cPage, testData);
			break;
		case AMEX_B2B:
			payWithAmericanExpress(b2cPage, testData);
			break;
		case Master_B2C:
			payWithMasterCard(b2cPage, testData);
			break;
		case Master_B2B:
			payWithMasterCard(b2cPage, testData);
			break;
		case JCB_B2C:
			payWithJCB(b2cPage, testData);
			break;
		case Discover_B2C:
			payWithDiscover(b2cPage, testData);
			break;
		case Discover_B2B:
			payWithDiscover(b2cPage, testData);
			break;
		case TwoCards_B2C:
			payWithTwoCards(b2cPage, testData);
			break;
		case Klarna_B2C:
			payWithKlarna(b2cPage, testData);
			break;
		case Zibby_B2C:
			payWithZibby(b2cPage, testData);
			break;
		case Deposit_B2C:
			payWithDeposit(b2cPage, testData);
			break;
		case Deposit_B2B:
			payWithDeposit(b2cPage, testData);
			break;
		case PurchaseOrder_B2C:
			payWithPurchaseOrder(b2cPage, testData);
			break;
		case PurchaseOrder_B2B:
			payWithPurchaseOrder(b2cPage, testData);
			break;
		case PayU_B2C:
			payWithPayU(b2cPage, testData);
			break;
		case JACCS_B2C:
			payWithJACCS(b2cPage, testData);
			break;
		case Visa3DS_B2C:
			payWith3DSVisa(b2cPage, testData);
			break;
		case Master3DS_B2C:
			payWith3DSMasterCard(b2cPage, testData);
			break;
		case Boleto_B2C:
			payWithBoleto(b2cPage, testData);
			break;
		case Wire_B2C:
			payWithWire(b2cPage, testData);
			break;
		case Mercado_B2C:
			payWithMercado(b2cPage, testData);
			break;
		case Amazon_B2C:
			payWithAmazonFromCart(b2cPage);
			break;
		case Amazon_B2B:
			payWithAmazonFromCart(b2cPage);
			break;
		case LFS_B2C:
			payWithLFSLeasing(b2cPage, testData);
			break;
		case LFS_B2B:
			payWithLFSLeasing(b2cPage, testData);
			break;
		case IGF_B2C:
			payWithIGFLeasing(b2cPage, testData);
			break;
		case IGF_B2B:
			payWithIGFLeasing(b2cPage, testData);
			break;
		case Party_B2C:
			payWithPartyLeasing(b2cPage, testData);
			break;
		case Party_B2B:
			payWithPartyLeasing(b2cPage, testData);
			break;
		case Paypal_B2C:
			payWithPaypal(b2cPage);
			break;
		case PayPal_B2B:
			payWithPaypal(b2cPage);
			break;
		default:
			return;
		}
	}

	private static boolean isCreditCardPaymentExists(B2CPage b2cPage, String type) throws InterruptedException {
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_CreditCardRadioButton, 3)) {
			Thread.sleep(2000);
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_CreditCardRadioButton);
			b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
			Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
			boolean result = false;

			List<WebElement> allOptions = dropdown.getOptions();
			for (int i = 0; i < allOptions.size(); i++) {
				if (allOptions.get(i).getText().equals(type)) {
					result = true;
					break;
				}
			}

			b2cPage.PageDriver.switchTo().defaultContent();
			return result;
		}
		return false;
	}

	public static void payWithPartyLeasing(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_Party);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithIGFLeasing(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_IGF);
		fillOtherPaymentFields(b2cPage, testData);
		if(testData.B2B == null)
			Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithLFSLeasing(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_LFS);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithMercado(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_Mercado);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithMercadoAfterPlaceOrder(B2CPage b2cPage, TestData testData) throws InterruptedException {
		b2cPage.PageDriver.switchTo().frame(b2cPage.MercadoFrame);
		b2cPage.Mercado_UseAccount.click();
		Thread.sleep(3000);
		b2cPage.Mercado_UserID.sendKeys("test_user_57127516@testuser.com");
		b2cPage.Mercado_Password.sendKeys("qatest1659");
		b2cPage.Mercado_LoginButton.click();
		Thread.sleep(3000);
		b2cPage.Mercado_Card.click();
		Thread.sleep(3000);
		b2cPage.Mercado_CreditCard.click();
		Thread.sleep(3000);
		b2cPage.Mercado_CardNumber.sendKeys("4075595716483764");
		b2cPage.Mercado_CardExpiration.sendKeys("1220");
		b2cPage.Mercado_SecurityCode.sendKeys("881");
		b2cPage.Mercado_CardName.clear();
		b2cPage.Mercado_CardName.sendKeys("APRO");

		b2cPage.PageDriver.findElement(By.xpath("//label[@for='installments']//div")).click();
		Thread.sleep(3000);
		b2cPage.PageDriver.findElement(By.xpath("//label[@for='installments']//ul/li[2]")).click();
		Thread.sleep(3000);

		b2cPage.Mercado_SubmitButton.click();
		Thread.sleep(3000);
		b2cPage.Mercado_NextButton.click();
		Thread.sleep(3000);
		b2cPage.PageDriver.switchTo().defaultContent();

		Thread.sleep(20000);
	}

	public static void payWithMercadoAfterPlaceOrder_Mobile(B2CPage b2cPage, TestData testData)
			throws InterruptedException {
		b2cPage.Mercado_UseAccount.click();
		b2cPage.Mercado_UserID.sendKeys("test_user_57127516@testuser.com");
		b2cPage.Mercado_Password.sendKeys("qatest1659");
		b2cPage.Mercado_LoginButton.click();
		b2cPage.Mercado_Card.click();
		b2cPage.Mercado_CreditCard.click();
		b2cPage.Mercado_CardNumber.sendKeys("4075595716483764");
		Thread.sleep(5000);
		b2cPage.Mercado_CardName.clear();
		b2cPage.Mercado_CardName.sendKeys("APRO");
		b2cPage.PageDriver.findElement(By.id("mp-form__field-next")).click();
		Thread.sleep(5000);
		b2cPage.Mercado_CardExpiration.sendKeys("1220");
		Thread.sleep(3000);
		b2cPage.Mercado_SecurityCode.sendKeys("881");
		Thread.sleep(3000);
		b2cPage.Mercado_SubmitButton.click();

		Thread.sleep(3000);
		b2cPage.PageDriver.findElement(By.xpath("//label[@for='mp-form__installment-1']")).click();
		b2cPage.Mercado_NextButton.click();

		Thread.sleep(20000);
	}

	public static void payWithWire(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Wire);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithBoletoAfterPlaceOrder(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.BoletoForm);
		Common.switchToWindow(b2cPage.PageDriver, 1);
		if (!b2cPage.PageDriver.getTitle().contains("Pagamentos")) {
			Assert.fail("Boleto form is not opened!");
		}
	}

	public static void payWithBoleto(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_Boleto);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithJACCS(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Jaccs_PaymentMode);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithPayU(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.paymentPage_payU_Method);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithPayUAfterPlaceOrder(B2CPage b2cPage, TestData testData) throws InterruptedException {

		fillPayUInfo(b2cPage.PageDriver, testData, "APPROVED");

		Common.javascriptClick(b2cPage.PageDriver,
				b2cPage.PageDriver.findElement(By.xpath(".//*[@id='response_button_continue']/span")));
		// Need to wait PayU refresh page
		Thread.sleep(60000);
	}

	public static void fillPayUInfo(WebDriver driver, TestData testData, String fullName) throws InterruptedException {
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(//*[@id='pm-VISA'])[1]")), 20);
		driver.findElement(By.xpath("(//*[@id='pm-VISA'])[1]")).click();

		// enter the necessary information
		Common.waitElementClickable(driver, driver.findElement(By.xpath("//*[@id='cc_fullName']")), 10);
		driver.findElement(By.xpath("//*[@id='cc_fullName']")).clear();
		driver.findElement(By.xpath("//*[@id='cc_fullName']")).sendKeys(fullName);

		if (Common.checkElementDisplays(driver, By.xpath("//*[@id='cc_dniNumber']"), 1)) {
			driver.findElement(By.xpath("//*[@id='cc_dniNumber']")).clear();
			driver.findElement(By.xpath("//*[@id='cc_dniNumber']")).sendKeys("123456789");
		}

		driver.findElement(By.xpath("//*[@id='ccNumber']")).clear();
		driver.findElement(By.xpath("//*[@id='ccNumber']")).sendKeys("4111111111111111");

		if (Common.checkElementDisplays(driver, By.xpath("//*[@id='securityCodeAux_']"), 1)) {
			driver.findElement(By.xpath("//*[@id='securityCodeAux_']")).clear();
			driver.findElement(By.xpath("//*[@id='securityCodeAux_']")).sendKeys("211");
		} else {
			driver.findElement(By.xpath("//*[@id='securityCode']")).clear();
			driver.findElement(By.xpath("//*[@id='securityCode']")).sendKeys("211");
		}

		Select select = new Select(driver.findElement(By.xpath("//*[@id='expirationDateMonth']")));
		select.selectByValue("10");

		select = new Select(driver.findElement(By.xpath("//*[@id='expirationDateYear']")));
		select.selectByValue("30");

		if (driver.findElement(By.xpath("//*[@id='installments']")).isEnabled()) {
			select = new Select(driver.findElement(By.xpath("//*[@id='installments']")));
			select.selectByIndex(0);
		}

		if (Common.checkElementDisplays(driver, By.xpath("//select[@ng-model='selectedBank']"), 10)) {
			select = new Select(driver.findElement(By.xpath("//select[@ng-model='selectedBank']")));
			select.selectByVisibleText("Banorte");
		}

		if (Common.checkElementDisplays(driver, By.id("birthDateYear"), 1)) {
			select = new Select(driver.findElement(By.id("birthDateYear")));
			select.selectByVisibleText("1983");
			Thread.sleep(2000);
			select = new Select(driver.findElement(By.id("birthDateMonth")));
			select.selectByVisibleText("6");
			Thread.sleep(2000);
			select = new Select(driver.findElement(By.id("birthDateDay")));
			select.selectByVisibleText("25");
		}

		driver.findElement(By.xpath("//*[@id='contactPhone']")).clear();
		driver.findElement(By.xpath("//*[@id='contactPhone']")).sendKeys("311 111 1111");
		if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cc_street1']"), 1)) {
			driver.findElement(By.xpath(".//*[@id='cc_street1']")).clear();
			driver.findElement(By.xpath(".//*[@id='cc_street1']")).sendKeys(testData.B2C.getDefaultAddressLine1());
		}
		if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cc_street2']"), 1)) {
			driver.findElement(By.xpath("//*[@id='cc_street2']")).clear();
			driver.findElement(By.xpath("//*[@id='cc_street2']")).sendKeys("Co street1");
		}
		if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cc_city']"), 1)) {
			driver.findElement(By.xpath("//*[@id='cc_city']")).clear();
			driver.findElement(By.xpath("//*[@id='cc_city']")).sendKeys(testData.B2C.getDefaultAddressCity());
		}
		if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cpfNumber']"), 1)) {
			driver.findElement(By.xpath("//*[@id='cpfNumber']")).clear();
			driver.findElement(By.xpath("//*[@id='cpfNumber']")).sendKeys(testData.B2C.getConsumerTaxNumber());
		}

		if (Common.checkElementDisplays(driver,
				By.xpath("//select[@ng-model='transaction.payer.billingAddress.state']"), 1)) {
			select = new Select(
					driver.findElement(By.xpath("//select[@ng-model='transaction.payer.billingAddress.state']")));
			select.selectByValue("Colima");
		}
		driver.findElement(By.xpath(".//*[@id='buyer_data_button_pay']")).click();
	}

	public static void payWithPurchaseOrder(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithDeposit(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_bankDeposit);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithZibby(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_ZibbyRadioButton);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
		fillZibbyInfoAndContinue(b2cPage);
	}

	public static void fillZibbyInfoAndContinue(B2CPage b2cPage) throws InterruptedException {
		Thread.sleep(5000);
		b2cPage.PageDriver.switchTo().frame(b2cPage.Zibby_Iframe);
		Thread.sleep(3000);
		b2cPage.Zibby_Phone.sendKeys("2");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("0");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("2");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("5");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("5");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("5");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("0");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("1");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("8");
		Thread.sleep(1000);
		b2cPage.Zibby_Phone.sendKeys("0");
		b2cPage.Zibby_Terms.click();
		b2cPage.Zibby_Terms2.click();
		b2cPage.Zibby_Continue.click();

		Thread.sleep(3000);
		b2cPage.Zibby_DigitalCode.sendKeys("123456");
		b2cPage.Zibby_DigitalCode2.sendKeys("1234");
		b2cPage.Zibby_Continue.click();

		Thread.sleep(5000);
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Zibby_SelectAddressButton, 5)) {
			b2cPage.Zibby_SelectAddressButton.click();
			Thread.sleep(10000);
		}
		b2cPage.Zibby_CardNumber.sendKeys("4111111111111111");
		b2cPage.Zibby_CardExpireDate.sendKeys("12");
		Thread.sleep(1000);
		b2cPage.Zibby_CardExpireDate.sendKeys("20");
		b2cPage.Zibby_CardSecurityCode.sendKeys("123");
		b2cPage.Zibby_CardTerms.click();
		b2cPage.Zibby_CardTerms2.click();
		b2cPage.Zibby_AgreementLink.click();

		Thread.sleep(3000);
		Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Zibby_Contract, 30);
		b2cPage.Zibby_Contract.click();
		Common.scrollToElement(b2cPage.PageDriver, b2cPage.PageDriver.findElement(By.xpath("//ol/li[last()]")));// ol[@class='ng-scope']
		Thread.sleep(3000);
		b2cPage.Zibby_IAgreeButton.click();

		Thread.sleep(3000);
		b2cPage.Zibby_MakePaymentButton.click();
		Thread.sleep(30000);
		b2cPage.PageDriver.switchTo().defaultContent();
	}

	public static void payWithKlarnaFinancing(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_KlarnaRadioButton);
		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithKlarna(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Klarna_ApplyNow);
		Thread.sleep(3000);
		Common.scrollToElement(b2cPage.PageDriver, b2cPage.Klarna_Frame);
		b2cPage.PageDriver.switchTo().frame(b2cPage.Klarna_Frame);
		b2cPage.Klarna_ContinueButton.click();
		Thread.sleep(5000);
		b2cPage.Klarna_ContinueButton.click();
		Thread.sleep(5000);
		String cardNum = b2cPage.Klarna_CardNum.getText();
		b2cPage.PageDriver.switchTo().defaultContent();

		b2cPage.PageDriver.switchTo().frame(b2cPage.Payment_CreditCardFrame);
		Common.scrollToElement(b2cPage.PageDriver, b2cPage.Payment_CardTypeDropdown);
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText("Mastercard");
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys(cardNum);

		if (!b2cPage.Payment_CardMonthTextBox.getTagName().toLowerCase().equals("select")) {
			b2cPage.Payment_CardMonthTextBox.clear();
			b2cPage.Payment_CardMonthTextBox.sendKeys("12");
			b2cPage.Payment_CardYearTextBox.clear();
			b2cPage.Payment_CardYearTextBox.sendKeys("20");
		} else {
			// Dropdown month and year
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

		fillOtherPaymentFields(b2cPage, testData);
		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void fillCardInfo(B2CPage b2cPage, String cardType, String cardNum) throws InterruptedException {
		Thread.sleep(2000);
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_CreditCardRadioButton);
		b2cPage.PageDriver.switchTo().frame(b2cPage.Payment_CreditCardFrame);
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText(cardType);
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys(cardNum);

		if (!b2cPage.Payment_CardMonthTextBox.getTagName().toLowerCase().equals("select")) {
			b2cPage.Payment_CardMonthTextBox.clear();
			b2cPage.Payment_CardMonthTextBox.sendKeys("12");
			b2cPage.Payment_CardYearTextBox.clear();
			b2cPage.Payment_CardYearTextBox.sendKeys("20");
		} else {
			// Dropdown month and year
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
	}

	public static void fillOtherPaymentFields(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// TW invoice
		if (Common.isElementExist(b2cPage.PageDriver, By.xpath(".//select[contains(@id,'invoiceTypeTW')]"), 1)) {
			b2cPage.PageDriver
					.findElement(By.xpath(".//select[contains(@id,'invoiceTypeTW')]/option[contains(.,'紙本發票')]"))
					.click();
		}
		// if PO is mandatory
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.payment_purchaseNum, 1)) {
			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");
			Thread.sleep(2000);
			b2cPage.payment_purchaseDate.click();
			Thread.sleep(3000);
			b2cPage.PageDriver.findElement(By.xpath("//td[contains(@class,'ui-datepicker-today')]/a")).click();
		}

		if (testData.B2B == null) {
			B2CCommon.fillPaymentAddressInfo(b2cPage, "autoFirstName", "autoLastName",
					testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(),
					testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(),
					testData.B2C.getDefaultAddressPhone());
		} else {
			B2BCommon.fillDefaultB2bBillingAddress(b2cPage.PageDriver, new B2BPage(b2cPage.PageDriver), testData);;
		}
	}

	public static void payWithVisa(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Visa", "4111111111111111");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithAmericanExpress(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "American Express", "371449635398431");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithMasterCard(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Mastercard", "5555555555554444");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWith3DSMasterCard(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Mastercard", "5200000000000007");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);

		fill3DS(b2cPage);
	}

	public static void payWith3DSVisa(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Visa", "4000000000000002");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);

		fill3DS(b2cPage);
	}

	/**
	 * @Owner Song Li
	 * @Parameter
	 * @Usage Handle 3DS dialogs
	 */
	public static void fill3DS(B2CPage b2cPage) throws InterruptedException {

		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("hidden3DSFrame0")));
		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("authWindow")));
		Common.scrollToElement(b2cPage.PageDriver, b2cPage.Payment_VisaVerifiedSubmitButton);
		b2cPage.Payment_VisaVerifiedTextBox.sendKeys("1234");
		b2cPage.Payment_VisaVerifiedSubmitButton.click();
		b2cPage.PageDriver.switchTo().defaultContent();

	}

	public static void payWithDiscover(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Discover", "6011111111111117");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithJCB(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "JCB", "3530111333300000");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void payWithTwoCards(B2CPage b2cPage, TestData testData) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.TwoCardRadioButton);

		b2cPage.FirstCardAmount.clear();
		b2cPage.FirstCardAmount.sendKeys("10.00");
		Thread.sleep(3000);
		b2cPage.PaymentIndicator.click();

		b2cPage.TwocardsNameOnCard1.sendKeys("LIXE");
		b2cPage.TwocardsNameOnCard2.sendKeys("LIXE");
		Thread.sleep(2000);
		b2cPage.PageDriver.switchTo().frame(b2cPage.FirstCardIframe);

		Thread.sleep(2000);
		b2cPage.TwoCardsType.click();

		b2cPage.TwoCardsNumber.sendKeys("4111111111111111");

		if (!b2cPage.TwocardsMonth.getTagName().toLowerCase().equals("select")) {
			b2cPage.TwocardsMonth.clear();
			b2cPage.TwocardsMonth.sendKeys("12");
			b2cPage.TwocardsYear.clear();
			b2cPage.TwocardsYear.sendKeys("20");
		} else {
			// Dropdown month and year
			Select dropdown = new Select(b2cPage.TwocardsMonth);
			dropdown.selectByVisibleText("12");
			dropdown = new Select(b2cPage.TwocardsYear);
			dropdown.selectByVisibleText("2020");
		}

		b2cPage.TwocardsCV.sendKeys("123");

		b2cPage.PageDriver.switchTo().defaultContent();
		b2cPage.PageDriver.switchTo().frame(b2cPage.SecondCardIframe);

		b2cPage.TwoCardsType.click();
		b2cPage.TwoCardsNumber.sendKeys("4222222222222");
		if (!b2cPage.TwocardsMonth.getTagName().toLowerCase().equals("select")) {
			b2cPage.TwocardsMonth.clear();
			b2cPage.TwocardsMonth.sendKeys("12");
			b2cPage.TwocardsYear.clear();
			b2cPage.TwocardsYear.sendKeys("20");
		} else {
			// Dropdown month and year
			Select dropdown = new Select(b2cPage.TwocardsMonth);
			dropdown.selectByVisibleText("12");
			dropdown = new Select(b2cPage.TwocardsYear);
			dropdown.selectByVisibleText("2020");
		}

		b2cPage.TwocardsCV.sendKeys("124");

		b2cPage.PageDriver.switchTo().defaultContent();
		Thread.sleep(2000);

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
	}

	public static void clickPaymentContinueButton(B2CPage b2cPage) {
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_ContinueButonNew, 1))
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_ContinueButonNew);
		else
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_ContinueButton);
	}

	public static void payWithPaypal(B2CPage b2cPage) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_paypalRadioBox);

		clickPaymentContinueButton(b2cPage);
		Thread.sleep(15000);

		// b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.name("injectedUl")));
		b2cPage.Payment_paypalLoginID.clear();
		b2cPage.Payment_paypalLoginID.sendKeys("accept@lenovo.com");
		if (!Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_paypalLoginPwd, 1)) {
			b2cPage.Payment_paypalNextBtn.click();
			Thread.sleep(10000);
		}
		b2cPage.Payment_paypalLoginPwd.clear();
		b2cPage.Payment_paypalLoginPwd.sendKeys("Hybris@sap");
		b2cPage.Payment_paypalSignBtn.click();

		// b2cPage.PageDriver.switchTo().defaultContent();
		Thread.sleep(15000);
		Common.waitElementClickable(b2cPage.PageDriver, b2cPage.Payment_paypalContinueBtn, 3);
		Common.sleep(3000);
		b2cPage.Payment_paypalContinueBtn.click();
	}

	public static boolean isAmazonPaymentExists(B2CPage b2cPage) {
		return Common.checkElementExists(b2cPage.PageDriver, b2cPage.Payment_Amazon, 3);
	}

	public static void payWithAmazonFromCart(B2CPage b2cPage) throws InterruptedException {
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_Amazon);

		Thread.sleep(2000);
		Common.switchToWindow_Title(b2cPage.PageDriver, "Amazon.com");
		b2cPage.Amazon_Email.sendKeys("huakx1@lenovo.com");
		b2cPage.Amazon_Password.sendKeys("1234qwer");
		b2cPage.Amazon_SigninButton.click();
		Common.switchToWindow(b2cPage.PageDriver, 0);
		b2cPage.Shipping_ContinueButton.click();

		clickPaymentContinueButton(b2cPage);
	}
}
