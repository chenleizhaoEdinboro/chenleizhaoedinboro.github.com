package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA22046Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	// String productNo;
	String orderNo;
	String invoiceType = "<text_id>YW30</text_id>";
	String eInvoice = "<text>A01</text>";
	String donation = "<text>A02</text>";
	String mobileCode = "<text>A03</text>";
	String citizenCertificate = "<text>A04</text>";
	String paperInvoice = "<text>A05</text>";
	String donationCode = "<text_id>YW31</text_id>";
	String carrierCode = "<text_id>YW32</text_id>";
	String vat = "<text_id>YW33</text_id>";

	public NA22046Test(String store) {
		this.Store = store;
		this.testName = "NA-22046";
		// this.productNo = productNo;
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA22046(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

//			Dailylog.logInfoDB(1, "Set [invoiceToggle] as no in HMC.", Store,
//					testName);
//			setInvoiceToggleInHMC(false);
//			Dailylog.logInfoDB(2, "Add product into cart.", Store, testName);
//			addProductNO();
//			Dailylog.logInfoDB(
//					3,
//					"Place order, and check: there is no invoice information showed under the billing address fields.",
//					Store, testName);
//			orderNo = placeOrder(false, "");
//			Dailylog.logInfoDB(
//					4,
//					"Check the order xml in HMC,and there is no invoice fields generated in the xml.",
//					Store, testName);
//			checkOrderXml(orderNo, "", "", false);

			Dailylog.logInfoDB(5, "Set [invoiceToggle] as yes. in HMC.", Store,
					testName);
			setInvoiceToggleInHMC(true);

			Dailylog.logInfoDB(6,
					"Add product, choose invoice type: PaperInvoice.", Store,
					testName);
			addProductNO();
			orderNo = placeOrder(true, "PaperInvoice");
			checkOrderXml(orderNo, paperInvoice, "", true);

			Dailylog.logInfoDB(11,
					"Add product, choose invoice type: MobileCode.", Store,
					testName);
			addProductNO();
			orderNo = placeOrder(true, "MobileCode");
			checkOrderXml(orderNo, mobileCode, carrierCode, true);

			Dailylog.logInfoDB(12,
					"Add product, choose invoice type: CitizenCertificate.",
					Store, testName);
			addProductNO();
			orderNo = placeOrder(true, "CitizenCertificate");
			checkOrderXml(orderNo, citizenCertificate, carrierCode, true);

			Dailylog.logInfoDB(13,
					"Add product, choose invoice type: Donation.", Store,
					testName);
			addProductNO();
			orderNo = placeOrder(true, "Donation");
			checkOrderXml(orderNo, donation, donationCode, true);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void checkOrderXml(String orderNo, String invoiceTypeValue,
			String barCodeMessage, Boolean whetherInvoice) {
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(orderNo);
		hmcPage.Home_Order_OrderSearch.click();
		System.out.println("Search the order.");
		for (int i = 0; i <= 20; i++) {
			String orderStatus = hmcPage.Home_Order_OrderStatus.getText();
			if (!orderStatus.equals("Completed")) {
				System.out.println("The order status is: " + orderStatus
						+ ", wait 30 seconds and then check out!");
				if (i == 20) {
					Assert.assertEquals(orderStatus, "Completed",
							"The order statsu is still not completed, check whether this is a bug!");
				} else {
					Common.sleep(30000);
					driver.navigate().refresh();
				}
			} else
				break;
		}
		hmcPage.Home_Order_OrderDetail.click();
		hmcPage.Order_ordersAdministration.click();
		System.out.println("Click anministration.");
		String xmlContent = hmcPage.Order_textArea.getText();
		// System.out.println("The invoice information is: "+xmlContent);
		if (whetherInvoice == false) {
			Boolean invoiceInfoDisplay = xmlContent.contains(invoiceType)
					&& xmlContent.contains(vat);
			Assert.assertFalse(invoiceInfoDisplay,
					"It's error the invoice information display in the xml.");
			// Assert.assertFalse(xmlContent.contains(invoiceType),
			// "It's error the invoice information display in the xml.");
			System.out
					.println("The invoice information don't display in the xml.");
		} else {
			Assert.assertTrue(xmlContent.contains(invoiceType),
					"The invoice type don't diaplay in the XML.");
			Assert.assertTrue(xmlContent.contains(invoiceTypeValue),
					"The invoice type value don't diaplay in the XML.");
			Assert.assertTrue(xmlContent.contains(barCodeMessage),
					"The barCode don't diaplay in the XML.");
			Assert.assertTrue(xmlContent.contains(vat),
					"The vat don't diaplay in the XML.");
			System.out.println("The invoice information display in the xml.");
		}
	}

	public String placeOrder(Boolean whetherInvoice, String invoiceType)
			throws InterruptedException {
		b2cPage.Cart_CheckoutButton.click();
		b2cPage.Checkout_StartCheckoutButton.click();
		System.out.println("Go to shipping page.");
		// Fill shipping info
		B2CCommon.fillShippingInfo(b2cPage, "Wang", "DaLu",
				testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(),
				testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(),
				testData.B2C.getDefaultAddressPhone(),
				testData.B2C.getLoginID());
		Thread.sleep(5000);
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		//b2cPage.Shipping_ContinueButton.click();
		System.out.println("Go to payment page.");
		// Fill payment info
		Boolean invoiceDisplay;
		Boolean isCard = true;
		if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel,5)) {
			Common.javascriptClick(driver, driver.findElement(By
					.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
			isCard = false;
		}else if(Common.isElementExist(driver, By
				.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
			// Fill payment info
			Common.javascriptClick(driver, driver.findElement(By
					.xpath("//*[@id='PaymentTypeSelection_CARD']")));
			fillDefaultPaymentInfo(b2cPage, testData);
		}
		
		
		
		
		B2CCommon.fillPaymentAddressInfo(b2cPage, "Wang", "DaLu",
				testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(),
				testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(),
				testData.B2C.getDefaultAddressPhone());
		if (whetherInvoice == true) {
			// invoiceDisplay = Common.isElementExist(b2cPage.PageDriver,
			// By.xpath("//select[contains(@id,'invoiceTypeTW')]"));
			invoiceDisplay = Common.checkElementExists(driver,
					b2cPage.Payment_selectInvoice, 10);
			Assert.assertTrue(invoiceDisplay, "The invoice don't display!");
			System.out.println("Display the invoice info.");
			b2cPage.Payment_ContinueButton.click();
			// Boolean errorDisplay = Common.isElementExist(b2cPage.PageDriver,
			// By.xpath("//div[@id='error_payment.set.paymentDetail.needInvoiceTypeTW']/p"));
			Boolean errorDisplay = Common.checkElementExists(driver,
					b2cPage.Payment_invoiceErrorMessage, 10);
			Assert.assertTrue(errorDisplay, "The error message don't display!");
			System.out
					.println("Display the error message, and the message is: "
							+ b2cPage.Payment_invoiceErrorMessage.getText());
			if(isCard){
				b2cPage.Payment_CardHolderNameTextBox.clear();
				Thread.sleep(2000);
				b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
			}
			
			Select sel = new Select(b2cPage.Payment_selectInvoice);
			sel.selectByValue(invoiceType);
			System.out.println("Select the invoice type: " + invoiceType);
			if (Common.checkElementExists(driver, b2cPage.Payment_invoiceCode,
					10)) {
				try {
					b2cPage.Payment_invoiceCode.sendKeys("12345678");
				} catch (Exception e) {
					driver.findElement(By.xpath(".//*[@id='vatCodeTW']"))
							.sendKeys("12345678");
				}
			} 
			if (Common.checkElementDisplays(driver,
					b2cPage.Payment_invoiceDonationCode, 10)) {
				
				b2cPage.Payment_invoiceDonationCode.sendKeys("12345678");

			}
			// b2cPage.PageDriver.findElement(By.xpath(".//select[contains(@id,'invoiceTypeTW')]/option[contains(.,'紙本發票')]")).click();
		} else if (whetherInvoice == false) {
			// invoiceDisplay = Common.isElementExist(b2cPage.PageDriver,
			// By.xpath(".//select[contains(@id,'invoiceTypeTW')]"));
			invoiceDisplay = Common.checkElementExists(driver,
					b2cPage.Payment_selectInvoice, 10);
			Assert.assertFalse(invoiceDisplay,
					"Should not display the invoice info!");
			System.out.println("Don't display the invoice info.");
		}
		
		Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
		// b2cPage.Payment_ContinueButton.click();
		System.out.println("Go to summary page.");
		// Place Order
		Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
		//b2cPage.OrderSummary_AcceptTermsCheckBox.click();
		B2CCommon.clickPlaceOrder(b2cPage);

		// Get Order number
		String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
		System.out.println("The orderNum is:" + orderNum);
		Dailylog.logInfoDB(1, "Order number is: " + orderNum, Store, testName);
		return orderNum;
	}

	public static void fillDefaultPaymentInfo(B2CPage b2cPage, TestData testData)
			throws InterruptedException {
		b2cPage.Payment_CreditCardRadioButton.click();
		b2cPage.PageDriver.switchTo().frame(
				b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText("Visa");
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys("4111111111111111");
		b2cPage.Payment_CardMonthTextBox.clear();
		b2cPage.Payment_CardMonthTextBox.sendKeys("12");
		b2cPage.Payment_CardYearTextBox.clear();
		b2cPage.Payment_CardYearTextBox.sendKeys("20");
		b2cPage.Payment_SecurityCodeTextBox.clear();
		b2cPage.Payment_SecurityCodeTextBox.sendKeys("123");
		b2cPage.PageDriver.switchTo().defaultContent();
		b2cPage.Payment_CardHolderNameTextBox.clear();
		Thread.sleep(2000);
		b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
	}

	public void addProductNO() {
		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(30);
		b2cPage.Navigation_CartIcon.click();
		if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 10)) {
			b2cPage.Navigation_ViewCartButton.click();
		}
		
		if (Common.checkElementExists(driver, b2cPage.Cart_emptyCart, 10)) {
			b2cPage.Cart_emptyCart.click();
			System.out.println("Empty cart.");
		}
		// b2cPage.Cart_QuickOrderTextBox.sendKeys(productNo);
		b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
		b2cPage.Cart_AddButton.click();
		if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
			b2cPage.Cart_QuickOrderTextBox.sendKeys("0C52863");
			b2cPage.Cart_AddButton.click();
		}
		System.out.println("Add product to cart successfully.");
	}

	public void setInvoiceToggleInHMC(Boolean whetherInvoice) {
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_administration.click();
		if (whetherInvoice == false) {
			hmcPage.B2CUnit_administration_invoiceToggle_false.click();
		} else {
			hmcPage.B2CUnit_administration_invoiceToggle_true.click();
		}
		hmcPage.Common_SaveButton.click();
		System.out.println("Set [invoiceToggle] as " + whetherInvoice
				+ " is successful.");
	}

}
