package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM487Test extends SuperTestClass {

	private B2BPage b2bPage;
	public HMCPage hmcPage;

	public String Url;

	private String subscription = "RR00000003";
	private String dcgproNUM = "4XB0G88776";
	private Boolean cardPayment = false;
	private String mtmproNUM;
	private boolean isDisplayed;
	private String price_PCG;
	private String price_SUB;;
	private String price_DCG;;
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";

	public COMM487Test(String Store) {
		this.Store = Store;

		this.testName = "COMM-487";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	private String GetPriceValue(String Price) {

		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\￥", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("₹", "");
		Price = Price.replaceAll("/yer", "");
		Price = Price.replaceAll("/mon", "");
		Price = Price.replaceAll("/syer", "");
		Price = Price.replaceAll("/que", "");
		Price = Price.trim();
		
		return Price;
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void COMM487(ITestContext ctx) {

		try {
			this.prepareTest();

			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			driver.get(testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			b2bPage.HomePage_CartIcon.click();
			Dailylog.logInfoDB(1, "add subscription product to cart", Store, testName);
			Common.sleep(1500);
			if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
				driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
			}
			B2BCommon.addProduct(driver, b2bPage, subscription);
			price_SUB = b2bPage.cartPage_TotalPrice.getText().toString();
			price_SUB = GetPriceValue(price_SUB);		
			Dailylog.logInfoDB(1, "subscription unitPrice is :"+ price_SUB, Store, testName);
			
			Dailylog.logInfoDB(1, "udpate subscription quantity", Store, testName);
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("2");
			b2bPage.cartPage_QuantityUpdate.click();
			
			Dailylog.logInfoDB(2, "add mtm product to cart", Store, testName);
			mtmproNUM = testData.B2B.getDefaultMTMPN1();
			B2BCommon.addProduct(driver, b2bPage, mtmproNUM);
			price_PCG = b2bPage.CartPage_SecondPrice.getText().toString();
			price_PCG = GetPriceValue(price_PCG);		
			Dailylog.logInfoDB(2, "mtm unitPrice is :"+ price_PCG, Store, testName);
			
			Dailylog.logInfoDB(3, "udpate mtm quantity", Store, testName);
			b2bPage.cartPage_Quantity2.clear();
			b2bPage.cartPage_Quantity2.sendKeys("3");
			b2bPage.cartPage_QuantityUpdate2.click();

			Dailylog.logInfoDB(4, "add DCG product to cart", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, dcgproNUM);
			price_DCG = b2bPage.CartPage_ThirdPrice.getText().toString();
			price_DCG = GetPriceValue(price_DCG);		
			Dailylog.logInfoDB(4, "DCG unitPrice is :"+ price_DCG, Store, testName);
			Dailylog.logInfoDB(5, "udpate DCG quantity", Store, testName);
			b2bPage.cartPage_Quantity3.clear();
			b2bPage.cartPage_Quantity3.sendKeys("4");
			b2bPage.cartPage_QuantityUpdate3.click();

			Dailylog.logInfoDB(6, "checkout", Store, testName);
			b2bPage.cartPage_LenovoCheckout.click();

			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());

			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}

			Thread.sleep(3000);

			Dailylog.logInfoDB(6, " select payment", Store, testName);
			Actions actions = new Actions(driver);
			By Card = By.xpath("//input[@id='PaymentTypeSelection_CARD']");

			By Purchase_Order = By.xpath(".//*[@id='PaymentTypeSelection_PURCHASEORDER']");
			if (Common.isElementExist(driver, Card)) {
				cardPayment = true;
				actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click().perform();
				driver.switchTo().frame(b2bPage.creditCardFrame);
				b2bPage.paymentPage_Visa.click();
				b2bPage.paymentPage_CardNumber.sendKeys("4111111111111111");
				b2bPage.paymentPage_ExpiryMonth.sendKeys("06");
				b2bPage.paymentPage_ExpiryYear.sendKeys("20");
				b2bPage.paymentPage_SecurityCode.sendKeys("132");
				driver.switchTo().defaultContent();
				b2bPage.paymentPage_NameonCard.sendKeys("LIXE");
			} else if (Common.isElementExist(driver, Purchase_Order)) {
				actions.moveToElement(b2bPage.paymentPage_PurchaseOrder).click().perform();
				b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
				b2bPage.paymentPage_purchaseDate.click();
				b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			}

			B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
			Dailylog.logInfoDB(6, "payment continue", Store, testName);

			if (Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");

			}

			Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
			
			Common.javascriptClick(driver, b2bPage.placeOrderPage_PlaceOrder);
			Common.sleep(5000);
			
			String orderNum =  driver.getCurrentUrl().substring(driver.getCurrentUrl().lastIndexOf("/")+1, driver.getCurrentUrl().length());
			long orderNum_DCG = Long.parseLong(orderNum)+1l;
			String  orderNumDCG = Long.toString(orderNum_DCG);
			
			
			Dailylog.logInfoDB(6, "Order DCG Number is: " + orderNumDCG, Store, testName);
			Dailylog.logInfoDB(6, "Order Number is: " + orderNum, Store, testName);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			String XMLContentDCG = HMCOrderCheck(driver, hmcPage, orderNumDCG);
			Assert.assertTrue( "DCG display in order XML",XMLContentDCG.contains(dcgproNUM));
			Assert.assertTrue("SUB does not display in order XML",!XMLContentDCG.contains(subscription));
			
			driver.findElement(By.xpath("//*[contains(@id,'[organizersearch][Order]_togglearrow')]")).click();
			Common.sleep(2000);
			String XMLContentPCG = HMCOrderCheck(driver, hmcPage, orderNum);
			Assert.assertTrue( "PCG does display in order XML",XMLContentPCG.contains(mtmproNUM));
			Assert.assertTrue( "SUB does not display in order XML",!XMLContentPCG.contains(subscription));
			
			// step~8
			Dailylog.logInfoDB(8, "check the value of zPayload", Store, testName);
			Common.scrollToElement(driver, hmcPage.order_Administration_zPayload);
			Common.rightClick(driver, hmcPage.order_Administration_zPayload);
			Common.sleep(2000);
			hmcPage.products_PB_editInNewWindow.click();
			Common.switchToWindow(driver, 1);

			String valuePayload = hmcPage.order_Administration_zPayloadContent.getAttribute("value");

			JSONObject obj = new JSONObject(valuePayload);
			JSONObject obj1 = obj.getJSONObject("request");

			Assert.assertEquals("card payment is woring!", obj1.getString("segment"), "Business");
			if (cardPayment) {
				Assert.assertEquals("card payment is woring!", obj1.getString("payment_method"), "CreditCard");
			} else {
				Assert.assertEquals("PO payment is woring!", obj1.getString("payment_method"), "PO");

			}

			JSONArray jArr = obj1.getJSONArray("items");
			JSONObject obj2 = jArr.getJSONObject(0);

			Assert.assertEquals("product No is wrong!", obj2.getString("lenovo_sku"), subscription);
			Assert.assertEquals("quantity is wrong!", obj2.getString("quantity"), "2");
			
			Assert.assertTrue("PO payment is woring!",obj2.getString("unit_price").contains(price_SUB) );

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public static String HMCOrderCheck(WebDriver driver, HMCPage page1, String OrderNumber){
		page1.Home_Order_OrderID.clear();
		page1.Home_Order_OrderID.sendKeys(OrderNumber);
		page1.Home_Order_OrderSearch.click();
		Common.sleep(5000);
		if (!page1.Home_Order_OrderStatus.getText().contains("Completed")) {
			Common.sleep(5000);
			page1.Home_Order_OrderSearch.click();
		}
		if(!page1.Home_Order_OrderStatus.getText().contains("Completed"))
			Assert.fail("Order status is not completed!");
		page1.Home_Order_OrderDetail.click();
		// page1.OrderReload.click();
		Common.sleep(5000);
		page1.Home_Order_OrderAdmin.click();
		Common.sleep(5000);
		return page1.Orders_OrderXML.getText();
		
	}
	
	

	

	public void AddPaymentType(String paymentType) {
		Dailylog.logInfoDB(1, "ADD Payment type : " + paymentType, Store, testName);
		Common.rightClick(driver, hmcPage.paymentProfileGlobalTab_paymentTypeAndPayerIDTop);
		Common.sleep(1000);
		hmcPage.b2bUnitSiteAttribute_addPaymentTypePayerID.click();
		Common.switchToWindow(driver, 1);
		Common.sleep(1000);
		hmcPage.checkoutPaymentType_identifierTxtBox.clear();
		hmcPage.checkoutPaymentType_identifierTxtBox.sendKeys(paymentType);
		hmcPage.B2BUnit_SearchButton.click();
		Common.sleep(5000);
		hmcPage.checkoutPaymentType_searchedResult.click();
		hmcPage.checkoutPaymentType_useButton.click();
		Common.sleep(1000);
		Common.switchToWindow(driver, 0);
		hmcPage.B2BUnit_Save.click();

		Common.sleep(5000);

	}


	
}
