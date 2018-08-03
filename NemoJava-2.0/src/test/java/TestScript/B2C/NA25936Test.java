package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA25936Test extends SuperTestClass {

	B2CPage b2cPage = null;
	String orderNum = null;
	boolean isNewUI = false;

	public NA25936Test(String store) {
		this.Store = store;
		this.testName = "NA-25936";
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void NA25936(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// Quick order

			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			Common.sleep(1000);
			b2cPage.Cart_AddButton.click();
			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
				b2cPage.Cart_QuickOrderTextBox.sendKeys("06P4069");
				b2cPage.Cart_AddButton.click();
			}
			// Amazon has special process
			Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);
			// Click on guest checkout button if exists
			if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				Dailylog.logInfoDB(1, "OLD UI", this.Store, this.testName);
				b2cPage.Checkout_LoginEmail.sendKeys(testData.B2C.getLoginID());
				b2cPage.Checkout_LoginPassword.sendKeys(testData.B2C.getLoginPassword());
				b2cPage.Checkout_LoginSignInButton_oldUI.click();

			} else {
				isNewUI = true;
				Dailylog.logInfoDB(1, "new UI", this.Store, this.testName);
				loginWhenCheckout(b2cPage);
			}
			Thread.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("address"));

			// Fill default shipping address
			if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel,5)) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
				
			}else if(Common.isElementExist(driver, By
					.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			if (Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				try {
					driver.findElement(By.xpath("//*[@id='resellerID']")).clear();
					driver.findElement(By.xpath("//*[@id='resellerID']")).sendKeys("1234567890");
				} catch (Exception e) {
					System.out.println("reseller id is not available");
				}
			}
			// Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			// Get Order number
			orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);

			driver.get(testData.B2C.getHomePageUrl() + "/my-account");

			b2cPage.MyAccount_ViewOrderHistoryLink.click();
			if (!B2CCommon.checkOrderInHistroyPage(b2cPage, orderNum))
				Assert.fail(this.Store + " order " + orderNum + " doesn't exist!");

			// Login in review page
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// Quick order
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			Common.sleep(1000);
			b2cPage.Cart_AddButton.click();
			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
				b2cPage.Cart_QuickOrderTextBox.sendKeys("06P4069");
				b2cPage.Cart_AddButton.click();
			}
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
			if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel,5)) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
				
			}else if(Common.isElementExist(driver, By
					.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			if (Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				try {
					driver.findElement(By.xpath("//*[@id='resellerID']")).clear();
					driver.findElement(By.xpath("//*[@id='resellerID']")).sendKeys("1234567890");
				} catch (Exception e) {
					System.out.println("reseller id is not available");
				}
			}
			// Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			// Get Order number
			orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			if (Common.checkElementExists(driver, b2cPage.order_Singin, 5)) {
				b2cPage.order_Singin.click();
				b2cPage.Checkout_LoginEmail.clear();
				b2cPage.Checkout_LoginEmail.sendKeys(testData.B2C.getLoginID());
				b2cPage.Checkout_LoginPassword.sendKeys(testData.B2C.getLoginPassword());
				b2cPage.order_Singin_button.click();

			} else {
				isNewUI = true;

				loginWhenCheckout(b2cPage);
			}

			Dailylog.logInfoDB(3, "Order Number is: " + orderNum, this.Store, this.testName);
			
		
			if (!driver
					.findElement(By.xpath("//tbody[@class='checkout-confirm-orderSummary-table-content']/tr[1]/td[1]"))
					.getText().equals(orderNum))
				Assert.fail("Order is not under the account!");

			if (isNewUI) {
				driver.manage().deleteAllCookies();
				driver.get(testData.B2C.getHomePageUrl() + "/cart");
				B2CCommon.handleGateKeeper(b2cPage, testData);
				// Quick order
				b2cPage.Cart_QuickOrderTextBox.clear();
				b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
				Common.sleep(1000);
				b2cPage.Cart_AddButton.click();
				if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
					b2cPage.Cart_QuickOrderTextBox.sendKeys("06P4069");
					b2cPage.Cart_AddButton.click();
				}
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
				if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel,5)) {
					Common.javascriptClick(driver, driver.findElement(By
							.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
					
				}else if(Common.isElementExist(driver, By
						.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
					// Fill payment info
					Common.javascriptClick(driver, driver.findElement(By
							.xpath("//*[@id='PaymentTypeSelection_CARD']")));
					B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
				}
				Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
				if (Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
					try {
						driver.findElement(By.xpath("//*[@id='resellerID']")).clear();
						driver.findElement(By.xpath("//*[@id='resellerID']")).sendKeys("1234567890");
					} catch (Exception e) {
						System.out.println("reseller id is not available");
					}
				}
				// Place Order
				Common.checkElementDisplays(driver, b2cPage.OrderSummary_AcceptTermsCheckBox, 10);

				loginWhenCheckout(b2cPage);

				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);
				// Get Order number
				orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
				Dailylog.logInfoDB(2, "Order Number is: " + orderNum, this.Store, this.testName);

				driver.get(testData.B2C.getHomePageUrl() + "/my-account");

				b2cPage.MyAccount_ViewOrderHistoryLink.click();
				if (!B2CCommon.checkOrderInHistroyPage(b2cPage, orderNum))
					Assert.fail(this.Store + " order " + orderNum + " doesn't exist!");
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void loginWhenCheckout(B2CPage b2cPage) throws InterruptedException {
		Common.javascriptClick(driver, b2cPage.Checkout_LoginLink);
		b2cPage.Checkout_LoginEmail.sendKeys(testData.B2C.getLoginID());
		b2cPage.Checkout_LoginPassword.sendKeys(testData.B2C.getLoginPassword());
		b2cPage.Checkout_LoginSignInButton.click();
		Thread.sleep(5000);
	}

}
