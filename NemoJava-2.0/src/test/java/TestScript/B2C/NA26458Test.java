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

public class NA26458Test extends SuperTestClass {

	public NA26458Test(String store) {
		this.Store = store;
		this.testName = "NA-26458";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","product",  "p1", "b2c"})
	public void NA26458(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);

			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultMTMPN());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			String description = b2cPage.PDP_Title.getText();
			// Quick order
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			// Amazon has special process
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);

			// Click on guest checkout button if exists
			if (Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}

			// Fill default shipping address
			if (Common.checkElementDisplays(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);

			// Payment
			Payment.payAndContinue(b2cPage, PaymentType.Visa_B2C, testData);

			// Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			// Get Order number
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			// Verify HMC value
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);

			// Validate in xml
			if (!hmcPage.Orders_OrderXML.getText().contains("<product_description_1>" + description + "</product_description_1>"))
				Assert.fail("Product description is wrong!");
			if (!hmcPage.Orders_OrderXML.getText().contains("<product_id>" + testData.B2C.getDefaultMTMPN() + "</product_id>"))
				Assert.fail("Product product id is wrong!");

			Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
