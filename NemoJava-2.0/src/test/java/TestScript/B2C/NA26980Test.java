package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
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

public class NA26980Test extends SuperTestClass {

	B2CPage b2cPage = null;
	String orderNum = null;
	PaymentType paymentType = null;

	public NA26980Test(String store) {
		this.Store = store;
		this.testName = "NA-26980";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void NA26980(ITestContext ctx) {
		try {
			this.prepareTest();
			driver.quit();
			this.setupMobileBrowser();
			b2cPage = new B2CPage(driver);
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

			Payment.payAndContinue(b2cPage, PaymentType.Mercado_B2C, testData);

			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			Thread.sleep(5000);
			if(!driver.getCurrentUrl().contains("www.mercadopago.com"))
				Assert.fail("Url is not changed to Mercardo page!");
			
			Payment.payWithMercadoAfterPlaceOrder_Mobile(b2cPage, testData);

			// Get Order number
			orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
			
			
			// CONT & OTHE
			driver.quit();
			this.setupMobileBrowser();
			b2cPage = new B2CPage(driver);
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

			Payment.payAndContinue(b2cPage, PaymentType.Mercado_B2C, testData);

			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			Thread.sleep(5000);
			if(!driver.getCurrentUrl().contains("www.mercadopago.com"))
				Assert.fail("Url is not changed to Mercardo page!");
			
			b2cPage.Mercado_UseAccount.click();
			b2cPage.Mercado_UserID.sendKeys("test_user_57127516@testuser.com");
			b2cPage.Mercado_Password.sendKeys("qatest1659");
			b2cPage.Mercado_LoginButton.click();
			b2cPage.Mercado_Card.click();
			b2cPage.Mercado_CreditCard.click();
			b2cPage.Mercado_CardNumber.sendKeys("4075595716483764");
			Thread.sleep(5000);
			b2cPage.Mercado_CardName.clear();
			b2cPage.Mercado_CardName.sendKeys("OTHE");
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
			
			driver.findElement(By.id("_eventId_changePMT")).click();
			
			b2cPage.Mercado_Card.click();
			b2cPage.Mercado_CreditCard.click();
			b2cPage.Mercado_CardNumber.sendKeys("4075595716483764");
			Thread.sleep(5000);
			b2cPage.Mercado_CardName.clear();
			b2cPage.Mercado_CardName.sendKeys("CONT");
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
			
			if(!Common.checkElementDisplays(driver, By.xpath("//h1[@class='pendingStep__title']"), 20))
				Assert.fail("Pay fail or not stay in MP page!");
			
			driver.quit();
			this.SetupBrowser();
			driver.get(testData.HMC.getHomePageUrl());
			HMCPage hmcPage = new HMCPage(driver);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);
			if(!HMCCommon.GetYB06Value(hmcPage).equals("3"))
				Assert.fail("YB06 value is wrong!");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
