package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA26500Test extends SuperTestClass {

	public NA26500Test(String store) {
		this.Store = store;
		this.testName = "NA-26500";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void NA26500(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");

			B2CCommon.handleGateKeeper(b2cPage, testData);

			// Quick order
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			// Amazon has special process
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);

			// Click on guest checkout button if exists
			if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}

			// Fill default shipping address
			if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);

			// Payment
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Visa3DS_B2C)) {
				
				payAndContinue(b2cPage, PaymentType.Visa3DS_B2C, testData);
				

				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				// Get Order number
				String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
				
				// Verify HMC value
				driver.get(testData.HMC.getHomePageUrl());
				HMCPage hmcPage = new HMCPage(driver);
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);

				// Validate YB06 in xml
				if (!HMCCommon.GetYB06Value(hmcPage).equals("3"))
					Assert.fail("YB06 value is wrong");

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

				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
			} else {
				Assert.fail("Visa 3DS is not configured yet!");
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void payAndContinue(B2CPage b2cPage, PaymentType type, TestData testData)
			throws InterruptedException {
		switch (type) {
		case Visa3DS_B2C:
			payWith3DSVisa(b2cPage, testData);
			break;
		default:
			return;
		}
	}
	
	public void payWith3DSMasterCard(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Mastercard", "5200000000000007");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
		fill3DS(b2cPage);
	}
	
	public void payWith3DSVisa(B2CPage b2cPage, TestData testData) throws InterruptedException {
		// Fill card info
		fillCardInfo(b2cPage, "Visa", "4000000000000002");

		// Fill other payment info
		fillOtherPaymentFields(b2cPage, testData);

		Payment.clickPaymentContinueButton(b2cPage);
		
		if(Common.checkElementDisplays(driver, By.id("hidden3DSFrame0"), 5)){
			fill3DS(b2cPage);
		}
	}
	
	public void fillCardInfo(B2CPage b2cPage, String cardType, String cardNum) throws InterruptedException {
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
	
	public void fill3DS(B2CPage b2cPage) throws InterruptedException {

		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("hidden3DSFrame0")));
		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("authWindow")));
		Common.scrollToElement(b2cPage.PageDriver, b2cPage.Payment_VisaVerifiedSubmitButton);
		b2cPage.Payment_VisaVerifiedTextBox.sendKeys("1234");
		b2cPage.Payment_VisaVerifiedSubmitButton.click();
		b2cPage.PageDriver.switchTo().defaultContent();

	}
	
	public void fillOtherPaymentFields(B2CPage b2cPage, TestData testData) throws InterruptedException {
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
			B2CCommon.fillPaymentAddressInfo(b2cPage, "autoFirstName", "autoLastName", testData.B2B.getAddressLine1(),
					testData.B2B.getAddressCity(), testData.B2B.getAddressState(), testData.B2B.getPostCode(),
					testData.B2B.getPhoneNum());
		}
	}
	
}
