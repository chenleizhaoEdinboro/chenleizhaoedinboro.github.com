package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21948Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String mtmPN;
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	

	public NA21948Test(String store) {
		this.Store = store;
		this.testName = "NA-21948";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21948(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			driver.get(ordinary_homePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(ordinary_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());

			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);

			// product number 80U10066US
			mtmPN = testData.B2C.getDefaultMTMPN();
			B2CCommon.addPartNumberToCart(b2cPage, mtmPN);
			Thread.sleep(10000);
			b2cPage.Cart_CheckoutButton.click();

			// Fill shipping info
			Actions actions = new Actions(driver);

			if (Common.checkElementDisplays(driver, b2cPage.CopyAddress, 4)) {
				actions.moveToElement(b2cPage.CopyAddress).click().perform();
			}

			if(Common.isElementExist(driver, By.xpath(".//*[@id='addressForm']/fieldset/legend/a")) && driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).isDisplayed()){
				driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).click();
			}
			
			B2CCommon.fillShippingInfo(b2cPage, "Alberto", "Costantini", "620 Broadway ", "Everett", "Massachusetts",
					"02149", "8572779690", testData.B2C.getLoginID());
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
			
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10)){
				b2cPage.Shipping_AddressMatchOKButton.click();
			}

			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);

			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");

			Assert.assertTrue(
					driver.findElement(By.xpath("//*[contains(text(),'Your order needs to be further reviewed.')]"))
							.isDisplayed(),
					"Check Review&Purchase page: is split-order-remind Displayed");

			String cardId = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			System.out.println("cardId is :" + cardId);

			driver.get(ordinary_homePageUrl);
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]")));
	
			driver.get(tele_homePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(tele_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			Thread.sleep(10000);
			
			
			b2cPage.MyAccount_Telesales.click();
			Dailylog.logInfoDB(7, "Sign out and input the telesales user name and password to login ASM", Store,
					testName);

			Thread.sleep(5000);
			b2cPage.Tele_DPLReport.click();
			Dailylog.logInfoDB(8, "Click DPL Report", Store, testName);

			Thread.sleep(20000);
			b2cPage.Tele_DPLReport_ID.sendKeys(cardId);
			b2cPage.Tele_DPLReport_StoreID.sendKeys(testData.B2C.getStore());
			b2cPage.Tele_DPLReport_Search.click();
			Thread.sleep(10000);
			List<WebElement> minPrices = driver
					.findElements(By.xpath("//tbody[@id='dplReportTableBody']/tr[contains(@id,'row')]"));
			Assert.assertEquals(minPrices.size(), 1);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='PENDING']")),
					"Check OCR status should be pending");

			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-ok')]")),
					"correct icon should be display");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-remove')]")),
					"x icon should be display");

			Dailylog.logInfoDB(9, "Serach by Cart ID and store ID", Store, testName);

			b2cPage.Tele_DPLReport_Reject.click();
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='REJECTED']")),
					"OCR status should change to Reject from pending");
			b2cPage.Tele_DPLReport_Search.click();
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[contains(@class,'glyphicon-ok')]")),
					"correct icon should be display");
			Dailylog.logInfoDB(10, "Click reject icon in check column", Store, testName);

			b2cPage.Tele_DPLReport_Approve.click();
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//td[text()='APPROVED']")),
					"OCR status should be Approved");
			Dailylog.logInfoDB(11, "Click Approve  icon in check column", Store, testName);

			b2cPage.Tele_DPLReport_Close.click();
			Dailylog.logInfoDB(12, "Close DPL Report Dailog and go back ASM function board", Store, testName);
			Thread.sleep(5000);

			b2cPage.Tele_TransactionSearch.click();
			Thread.sleep(20000);

			b2cPage.Tele_TransactionSearch_TransactionId.sendKeys(cardId);
			b2cPage.Tele_TransactionSearch_Search.click();
			Thread.sleep(5000);
			b2cPage.Tele_TransactionSearch_SearchResult.click();
			Dailylog.logInfoDB(13, "Copy cart ID to transaction ID on ASM broad", Store, testName);

			Thread.sleep(10000);
			// b2cPage.Tele_StartSession.click();
			executor.executeScript("arguments[0].click();", b2cPage.Tele_StartSession);
			Thread.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("save-cart"));
			Dailylog.logInfoDB(14, "Start Session", Store, testName);

			b2cPage.Cart_openCart.click();
			Thread.sleep(10000);
			b2cPage.Cart_CheckoutButton.click();
			// executor.executeScript("arguments[0].click();",
			// b2cPage.Cart_CheckoutButton);
			Thread.sleep(5000);
			WebElement continueButton = driver.findElement(By.id("checkoutForm-shippingContinueButton"));
			executor.executeScript("arguments[0].click();", continueButton);
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();

			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "Alberto", "Costantini", "620 Broadway ", "Everett",
					"Massachusetts", "02149", "8572779690");
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);

			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");
			
			
			String orderNum = b2cPage.OrderThankyou_OrderNumberLabelNew.getText();
			System.out.println("orderNum is :" + orderNum);
			Dailylog.logInfoDB(15, "Place Order", Store, testName);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			// to avoid mail status delay in HMC
			Thread.sleep(10000);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(orderNum);
			hmcPage.Home_Order_OrderSearch.click();
			System.out.println("Order status in HMC: " + hmcPage.Home_Order_OrderStatus.getText());
			Assert.assertEquals(hmcPage.Home_Order_OrderStatus.getText(), "Completed",
					"Order Status in HMC: " + orderNum);
			Dailylog.logInfoDB(16, "Check the order statues in HMC", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
