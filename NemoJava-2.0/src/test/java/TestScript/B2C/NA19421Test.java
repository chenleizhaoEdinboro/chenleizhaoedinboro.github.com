package TestScript.B2C;

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
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA19421Test extends SuperTestClass {

	public NA19421Test(String store) {
		this.Store = store;
		this.testName = "NA-19421";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void NA19421(ITestContext ctx) {
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
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Boleto_B2C)) {

				Payment.payAndContinue(b2cPage, PaymentType.Boleto_B2C, testData);

				// Place Order
				b2cPage.OrderSummary_AcceptTermsCheckBox.click();
				B2CCommon.clickPlaceOrder(b2cPage);

				// Get Order number
				String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
				
				Payment.payWithBoletoAfterPlaceOrder(b2cPage, testData);
				
				// Verify HMC value
				driver.get(testData.HMC.getHomePageUrl());
				HMCPage hmcPage = new HMCPage(driver);
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);
				
				// Validate YB06 in xml
				if(!HMCCommon.GetYB06Value(hmcPage).equals("8"))
					Assert.fail("YB06 value is wrong");

				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
			} else {
				Assert.fail("Boleto is not configured yet!");
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
