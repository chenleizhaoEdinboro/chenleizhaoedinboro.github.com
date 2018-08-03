package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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
import TestData.TestData;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA23085Test extends SuperTestClass {

	private Actions act;
	public NA23085Test(String store) {
		this.Store = store;
		this.testName = "NA-23085";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2c"})
	public void NA23085(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.handleGateKeeper(b2cPage, testData);

			// Quick order
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			// Amazon has special process
			b2cPage.Cart_CheckoutButton.click();
			Common.sleep(5000);

			// Click on guest checkout button if exists
			act = new Actions(driver);
			Common.scrollToElement(driver, b2cPage.Checkout_StartCheckoutButton);
			act.moveToElement(b2cPage.Checkout_StartCheckoutButton).click().perform();

			// Fill default shipping address
			if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			
			// Payment
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.Mercado_B2C)) {

				Payment.payAndContinue(b2cPage, PaymentType.Mercado_B2C, testData);

				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				// Pay with APRO
				payWithMercadoAfterPlaceOrder(b2cPage, testData);
				// Get Order number
				String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();

				
				// Pay with OTHE
				driver.manage().deleteAllCookies();
				Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
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

				// Place Order
				b2cPage.OrderSummary_AcceptTermsCheckBox.click();
				B2CCommon.clickPlaceOrder(b2cPage);
				
				// Pay with OTHE
				Thread.sleep(3000);
				b2cPage.PageDriver.switchTo().frame(b2cPage.MercadoFrame);
				b2cPage.Mercado_Card.click();
				Thread.sleep(3000);
				b2cPage.Mercado_CreditCard.click();
				Thread.sleep(3000);
				b2cPage.Mercado_CardNumber.sendKeys("4075595716483764");
				Thread.sleep(3000);
				b2cPage.Mercado_CardExpiration.sendKeys("1220");
				Thread.sleep(3000);
				b2cPage.Mercado_SecurityCode.sendKeys("881");
				b2cPage.Mercado_CardName.clear();
				b2cPage.Mercado_CardName.sendKeys("OTHE");
				
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
				
				if(!Common.checkElementDisplays(driver, b2cPage.Mercado_BackToPayment, 60))
				{
					Assert.fail("Back to payment link is not shown!");
				}
				Common.javascriptClick(driver, b2cPage.Mercado_BackToPayment);
				
//				 Pay with CONT
//				driver.manage().deleteAllCookies();
//				driver.get(testData.B2C.getHomePageUrl() + "/cart");
//
//				B2CCommon.handleGateKeeper(b2cPage, testData);
//
//				// Quick order
//				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
//

//				b2cPage.Cart_CheckoutButton.click();
//				Thread.sleep(2000);
//
//				// Click on guest checkout button if exists
//				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
//					b2cPage.Checkout_StartCheckoutButton.click();
//				}
//
//				// Fill default shipping address
//				if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
//					B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
//				}
//				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
//				B2CCommon.handleAddressVerify(driver, b2cPage);
				
				Payment.payAndContinue(b2cPage, PaymentType.Mercado_B2C, testData);

				// Place Order
				b2cPage.OrderSummary_AcceptTermsCheckBox.click();
				B2CCommon.clickPlaceOrder(b2cPage);
				
				// Pay with CONT
				Thread.sleep(3000);
				b2cPage.PageDriver.switchTo().frame(b2cPage.MercadoFrame);
				b2cPage.Mercado_Card.click();
				Thread.sleep(3000);
				b2cPage.Mercado_CreditCard.click();
				Thread.sleep(3000);
				b2cPage.Mercado_CardNumber.sendKeys("4075595716483764");
				Thread.sleep(3000);
				b2cPage.Mercado_CardExpiration.sendKeys("1220");
				Thread.sleep(3000);
				b2cPage.Mercado_SecurityCode.sendKeys("881");
				b2cPage.Mercado_CardName.clear();
				b2cPage.Mercado_CardName.sendKeys("CONT");
				
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
				// Get order number
				orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
				
				// Verify HMC value
				Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
				HMCPage hmcPage = new HMCPage(driver);
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);
				
				// Validate YB06 in xml
				if(!HMCCommon.GetYB06Value(hmcPage).equals("3"))
					Assert.fail("YB06 value is wrong");

				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
			} else {
				Assert.fail("Mercado is not configured yet!");
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void payWithMercadoAfterPlaceOrder(B2CPage b2cPage, TestData testData) throws InterruptedException {
		b2cPage.PageDriver.switchTo().frame(b2cPage.MercadoFrame);
		Common.sleep(5000);
		Actions act = new Actions(driver);
//		WebElement Mercado_UseAccount = driver.findElement(By.xpath("//a[contains(@id,'use_account')]/div/div[@class='cho-media__body']/span"));
		Common.scrollToElement(driver, b2cPage.Mercado_UseAccount);
		act.moveToElement(b2cPage.Mercado_UseAccount).click().perform();
		Thread.sleep(5000);
		act.sendKeys(Keys.PAGE_UP);
		b2cPage.Mercado_UserID.sendKeys("test_user_57127516@testuser.com");
		b2cPage.Mercado_Password.sendKeys("qatest1659");
		Common.javascriptClick(driver, b2cPage.Mercado_LoginButton);
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
		Common.javascriptClick(driver, b2cPage.Mercado_SubmitButton);
		Thread.sleep(3000);
		Common.javascriptClick(driver, b2cPage.Mercado_NextButton);
		Thread.sleep(3000);
		b2cPage.PageDriver.switchTo().defaultContent();

		Thread.sleep(20000);
	}
}
