package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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

public class NA23005Test extends SuperTestClass {

	public NA23005Test(String store) {
		this.Store = store;
		this.testName = "NA-23005";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2c","compatibility"})
	public void NA23005(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			Common.NavigateToUrl(driver, Browser,testData.B2C.getHomePageUrl() + "/cart");

			B2CCommon.handleGateKeeper(b2cPage, testData);

			// Quick order
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			b2cPage.Quote_quantity0.clear();
			b2cPage.Quote_quantity0.sendKeys("5");
			b2cPage.Quote_update.click();
			Thread.sleep(5000);
			
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
			
			Thread.sleep(3000);
			// Payment
//			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Klarna_B2C)) {

//				Payment.payAndContinue(b2cPage, PaymentType.Klarna_B2C, testData);
				
				// This case need cart > 1200, and use Klarna radio button as payment method
//				Common.javascriptClick(driver, b2cPage.KlarnaRadioButton);
				Actions actions = new Actions(driver);
				Common.scrollToElement(driver, b2cPage.KlarnaRadioButton);
				actions.moveToElement(b2cPage.KlarnaRadioButton).click().perform();
				Thread.sleep(10000);
//				Payment.clickPaymentContinueButton(b2cPage);
				if(this.Store.equals("US_BPCTO")){
					b2cPage.payment_purchaseNum.clear();
					b2cPage.payment_purchaseNum.sendKeys("1234567890");
					Thread.sleep(2000);
					b2cPage.payment_purchaseDate.click();
					Thread.sleep(3000);
					b2cPage.PageDriver.findElement(By.xpath("//td[contains(@class,'ui-datepicker-today')]/a")).click();
				}
				b2cPage.Payment_ContinueButton.click();
				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				// Get Order number
				String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

				// Verify HMC value
				Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
				HMCPage hmcPage = new HMCPage(driver);
				HMCCommon.Login(hmcPage, testData);
				HMCOrderCheck(driver, hmcPage, orderNum);
				
				// Validate YB06 in xml
				if(!HMCCommon.GetYB06Value(hmcPage).equals("K"))
					Assert.fail("YB06 value is wrong");

				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
//			} else {
//				Assert.fail("Klarna is not configured yet!");
//			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public static void HMCOrderCheck(WebDriver driver, HMCPage page1, String OrderNumber) throws Exception {
		page1.Home_Order.click();
		page1.Home_Order_Orders.click();
		page1.Home_Order_OrderID.clear();
		page1.Home_Order_OrderID.sendKeys(OrderNumber);
		page1.Home_Order_OrderSearch.click();
		Thread.sleep(5000);
		if (!page1.Home_Order_OrderStatus.getText().contains("Completed")) {
			Thread.sleep(5000);
			page1.Home_Order_OrderSearch.click();
		}
		if(page1.Home_Order_OrderStatus.getText().contains("BPCTO Holding"))
			Assert.fail("BPCTO Holding,order number is:" + OrderNumber);
		page1.Home_Order_OrderDetail.click();
		// page1.OrderReload.click();
		Thread.sleep(5000);
		page1.Home_Order_OrderAdmin.click();
		Thread.sleep(5000);
		if(!page1.Orders_OrderXML.getText().contains("xml"))
			Assert.fail("XML is not generated!");
	}
}
