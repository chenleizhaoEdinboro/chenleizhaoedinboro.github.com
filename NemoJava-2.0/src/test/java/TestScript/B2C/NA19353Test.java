package TestScript.B2C;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA19353Test extends SuperTestClass {

	B2CPage b2cPage = null;
	String orderNum = null;
	PaymentType paymentType = null;

	public NA19353Test(String store, PaymentType Payment) {
		this.Store = store;
		this.testName = Payment.name();
		paymentType = Payment;
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2c", "compatibility"})
	public void NA19353(ITestContext ctx) {
		try {
			if ((paymentType == PaymentType.Master3DS_B2C || paymentType == PaymentType.Visa3DS_B2C)
					&& !Store.equals("US")) {
				// non-US no need to test 3DS
				markNAforPayment();
			} else if ((paymentType == PaymentType.Master3DS_B2C || paymentType == PaymentType.Visa3DS_B2C
					|| paymentType == PaymentType.Master_B2C || paymentType == PaymentType.Visa_B2C
					|| paymentType == PaymentType.AmericanExpress_B2C)
					&& (Store.equals("CO") || Store.equals("MX"))) {
				// CO MX no need to test card payment
				markNAforPayment();
			} else {
				this.prepareTest();
				b2cPage = new B2CPage(driver);
//				driver.get(testData.B2C.getHomePageUrl() + "/cart");
				Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
				
				B2CCommon.handleGateKeeper(b2cPage, testData);

				// // Go to cart page
				// B2CCommon.ClickToCartPage(b2cPage);

				// Quick order
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

				if (paymentType != PaymentType.Amazon_B2C) {
					// Amazon has special process
					Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
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
				}
				// Payment
				if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, paymentType)) {

					Payment.payAndContinue(b2cPage, paymentType, testData);

					if (paymentType != PaymentType.Zibby_B2C) {
						// Place Order
						Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
						B2CCommon.clickPlaceOrder(b2cPage);
					}

					if (paymentType == PaymentType.Mercado_B2C) {
						Payment.payWithMercadoAfterPlaceOrder(b2cPage, testData);
					} else if (paymentType == PaymentType.PayU_B2C) {
						Payment.payWithPayUAfterPlaceOrder(b2cPage, testData);
					}

					// Get Order number
					orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

					Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
				} else {
					markNAforPayment();
				}
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
