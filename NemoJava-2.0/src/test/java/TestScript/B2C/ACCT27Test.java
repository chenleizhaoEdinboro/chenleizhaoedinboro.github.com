package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class ACCT27Test extends SuperTestClass {

	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public String cartUrl;
	public String myAccountUrl;
	
	public ACCT27Test(String store) {
		this.Store = store;
		this.testName = "ACCT-27";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void ACCT27(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//before test. we need to configure in hmc
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			Select select_saleType = new Select(driver.findElement(By.xpath("//select[contains(@id,'dynamicSaleType')]")));
			select_saleType.selectByVisibleText("DIRECT");
			
			Select select_FulfillmentType = new Select(driver.findElement(By.xpath("//select[contains(@id,'dynamicfulfillmentType')]")));
			select_FulfillmentType.selectByVisibleText("Vinculum_CRM");
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			hmcPage.Home_closeSession.click();
			
			//1, login store with a general customer
			Dailylog.logInfoDB(1, "login store with a general customer", Store, testName);
			
			driver.get(testData.B2C.getloginPageUrl());
			
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());

			//2, select one product and drop an order
			Dailylog.logInfoDB(2, "select one product and drop an order", Store, testName);
			
			cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			
			driver.get(cartUrl);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			B2CCommon.addPartNumberToCart(b2cPage,testData.B2C.getDefaultMTMPN());
			
			String orderNumber = placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData, Store);
			
			Dailylog.logInfoDB(2, "order number is :" + orderNumber, Store, testName);	
			
			//3, go to hmc to make sure the order status is complete and then go to myaccount -- view order history link
			Dailylog.logInfoDB(3, "go to hmc to make sure the order status is complete and then go to myaccount -- view order history link", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(hmcPage, orderNumber);
			
			myAccountUrl = testData.B2C.getHomePageUrl() + "/my-account";
			
			driver.get(myAccountUrl);
			
			b2cPage.MyAccount_ViewOrderHistoryLink.click();
			
			//4, click view order status link
			Dailylog.logInfoDB(4, "click view order status link", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='accountOrderHistory']//a[contains(@href,'orderStatus') and contains(@href,'"+orderNumber+"')]")).click();
			
			String orderStatus = driver.findElement(By.xpath("//div[@class='orderStatus']")).getText().split(":")[1];
			Assert.assertTrue(!orderStatus.equals(""), "order status is empty");
			
			String orderNumber_1 = driver.findElement(By.xpath("//label[contains(.,'Number')]/../span[@class='item-value']")).getText().toString();
			String shippingAndHandling = driver.findElement(By.xpath("//label[contains(.,'Shipping')]/../span[@class='item-value']")).getText().toString();
			String date = driver.findElement(By.xpath("//label[contains(.,'Date')]/../span[@class='item-value']")).getText().toString();
			String estimatedTotal = driver.findElement(By.xpath("//label[contains(.,'Estimated')]/../span[contains(@class,'item-value')]")).getText().toString();
			String soldTo = driver.findElement(By.xpath("//label[contains(.,'Sold')]/../span[@class='item-value']")).getText().toString();
			
			String productID = driver.findElement(By.xpath("//label[contains(.,'Product')]/../span[@class='productBasicDetail-value']")).getText().toString();
			String quantity = driver.findElement(By.xpath("//label[contains(.,'Quantity')]/../span[@class='productBasicDetail-value']")).getText().toString();
			
			Dailylog.logInfoDB(4, "orderStatus is :" + orderStatus, Store, testName);
			Dailylog.logInfoDB(4, "orderNumber_1 is :" + orderNumber_1, Store, testName);
			Dailylog.logInfoDB(4, "shippingAndHandling is :" + shippingAndHandling, Store, testName);
			Dailylog.logInfoDB(4, "date is :" + date, Store, testName);
			Dailylog.logInfoDB(4, "estimatedTotal is :" + estimatedTotal, Store, testName);
			Dailylog.logInfoDB(4, "soldTo is :" + soldTo, Store, testName);
			Dailylog.logInfoDB(4, "productID is :" + productID, Store, testName);
			Dailylog.logInfoDB(4, "quantity is :" + quantity, Store, testName);
			
			
			//5, logout website , click "customer support-->order status" link which can find in the bottom of home page 
			Dailylog.logInfoDB(5, "logout website , click customer support-->order status link which can find in the bottom of home page ", Store, testName);
			
			// 4291282336
			driver.manage().deleteAllCookies();
			
			driver.get(testData.B2C.getHomePageUrl());
			
			Common.javascriptClick(driver, driver.findElement(By.xpath("//a[contains(@href,'orderStatus')]")));;
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//input[@name='orderNumber']")).clear();
			driver.findElement(By.xpath("//input[@name='orderNumber']")).sendKeys(orderNumber);
			
			driver.findElement(By.xpath("//input[@name='guestEmail']")).clear();
			driver.findElement(By.xpath("//input[@name='guestEmail']")).sendKeys(testData.B2C.getLoginID());
			driver.findElement(By.xpath("//button[contains(@class,'checkStatus')]")).click();
			
			Thread.sleep(4000);
			
			Common.switchToWindow(driver, 1);
			
			String orderStatus_popUp = driver.findElement(By.xpath("//div[@class='orderStatus']")).getText().split(":")[1];
			Assert.assertTrue(!orderStatus_popUp.equals(""), "order status is empty");
			
			String orderNumber_1_popUp = driver.findElement(By.xpath("//label[contains(.,'Number')]/../span[@class='item-value']")).getText().toString();
			String shippingAndHandling_popUp = driver.findElement(By.xpath("//label[contains(.,'Shipping')]/../span[@class='item-value']")).getText().toString();
			String date_popUp = driver.findElement(By.xpath("//label[contains(.,'Date')]/../span[@class='item-value']")).getText().toString();
			String estimatedTotal_popUp = driver.findElement(By.xpath("//label[contains(.,'Estimated')]/../span[contains(@class,'item-value')]")).getText().toString();
			String soldTo_popUp = driver.findElement(By.xpath("//label[contains(.,'Sold')]/../span[@class='item-value']")).getText().toString();
			
			String productID_popUp = driver.findElement(By.xpath("//label[contains(.,'Product')]/../span[@class='productBasicDetail-value']")).getText().toString();
			String quantity_popUp = driver.findElement(By.xpath("//label[contains(.,'Quantity')]/../span[@class='productBasicDetail-value']")).getText().toString();
			
			Dailylog.logInfoDB(5, "orderStatus_popUp is :" + orderStatus_popUp, Store, testName);
			Dailylog.logInfoDB(5, "orderNumber_1_popUp is :" + orderNumber_1_popUp, Store, testName);
			Dailylog.logInfoDB(5, "shippingAndHandling_popUp is :" + shippingAndHandling_popUp, Store, testName);
			Dailylog.logInfoDB(5, "date_popUp is :" + date_popUp, Store, testName);
			Dailylog.logInfoDB(5, "estimatedTotal_popUp is :" + estimatedTotal_popUp, Store, testName);
			Dailylog.logInfoDB(5, "soldTo_popUp is :" + soldTo_popUp, Store, testName);
			Dailylog.logInfoDB(5, "productID_popUp is :" + productID_popUp, Store, testName);
			Dailylog.logInfoDB(5, "quantity_popUp is :" + quantity_popUp, Store, testName);
			
			
			Assert.assertTrue(orderStatus.equals(orderStatus_popUp), "order status on popup page does not equals with order history page");
			Assert.assertTrue(orderNumber_1.equals(orderNumber_1_popUp), "order number on popup page does not equals with order history page");
			Assert.assertTrue(shippingAndHandling.equals(shippingAndHandling_popUp), "shippingAndHandling on popup page does not equals with order history page");
			Assert.assertTrue(date.equals(date_popUp), "date on popup page does not equals with order history page");
			Assert.assertTrue(estimatedTotal.equals(estimatedTotal_popUp), "estimatedTotal on popup page does not equals with order history page");
			Assert.assertTrue(soldTo.equals(soldTo_popUp), "soldTo on popup page does not equals with order history page");
			Assert.assertTrue(productID.equals(productID_popUp), "productID on popup page does not equals with order history page");
			Assert.assertTrue(quantity.equals(quantity_popUp), "quantity on popup page does not equals with order history page");
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public String placeOrderFromClickingStartCheckoutButtonInCart(WebDriver driver, B2CPage b2cPage,
			TestData testData, String... store) throws Exception {
		
		b2cPage.Cart_CheckoutButton.click();
		Thread.sleep(3000);
		// If already login in register gate keeper, then no
		// StartCheckout button and ShippingInfo is pre-filled
		String tempEmail = testData.B2C.getLoginID();
		String firstName = Common.getDateTimeString();
		String lastName = Common.getDateTimeString();
		if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
			b2cPage.Checkout_StartCheckoutButton.click();
		}
		// Fill shipping info
		if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
			B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail,
					testData.B2C.getConsumerTaxNumber());
		}
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		// b2cPage.Shipping_ContinueButton.click();
		B2CCommon.handleAddressVerify(driver, b2cPage);
		// Fill payment info
		if(Common.isElementExist(driver, By.xpath("//input[@id='PaymentTypeSelection_COD']"))){
			driver.findElement(By.xpath("//input[@id='PaymentTypeSelection_COD']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='codTermsCheckBox']")).click();
			Thread.sleep(2000);
			Common.javascriptClick(driver, driver.findElement(By.xpath("//input[@id='cod_payment_pop_confirm_button']")));
			Thread.sleep(2000);
		}else{
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
		}
		B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
//			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
		Payment.clickPaymentContinueButton(b2cPage);

		B2CCommon.handleVisaVerify(b2cPage);
		// Place Order
		Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
		B2CCommon.clickPlaceOrder(b2cPage);
		// Get Order number
		return B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
	}
	
	
	
}

