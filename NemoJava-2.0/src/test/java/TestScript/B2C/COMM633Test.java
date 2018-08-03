package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM633Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String bundle1;
	private String bundle2;
	private String bundle3;
	public String Url;


	public COMM633Test(String store,String bundle1,String bundle2,String bundle3) {
		this.Store = store;
		this.bundle1 = bundle1;
		this.bundle2 = bundle2;
		this.bundle3 = bundle3;
		this.testName = "COMM-633";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage, String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void COMM633(ITestContext ctx) {

		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
//			Dailylog.logInfoDB(3, "set Line Item Quantity ", Store, testName);
//			driver.get(testData.HMC.getHomePageUrl());
//			HMCCommon.Login(hmcPage, testData);
//			HMCCommon.searchB2CUnit(hmcPage, testData);
//			hmcPage.B2CUnit_FirstSearchResultItem.click();
//			
//			hmcPage.B2CUnit_SiteAttributeTab.click();
//		
//			hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.clear();
//			hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.sendKeys("5");
//			hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.clear();
//			hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.sendKeys("10");
//			hmcPage.baseStore_save.click();
//			Common.sleep(2000);
//			Dailylog.logInfoDB(3, "hmc save ", Store, testName);
//			hmcPage.Home_EndSessionLink.click();
			Dailylog.logInfoDB(4, "login b2c website ", Store, testName);
//			driver.get(testData.B2C.getloginPageUrl());
//
//			Common.sleep(2500);
//
//			B2CCommon.handleGateKeeper(b2cPage, testData);
//			String loginID = testData.B2C.getLoginID();
//		
//			String pwd = testData.B2C.getLoginPassword();
//			B2CCommon.login(b2cPage, loginID, pwd);
//			b2cPage.Navigation_CartIcon.click();
//			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
//				b2cPage.Navigation_ViewCartButton.click();
//			Common.sleep(2000);
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			
			Dailylog.logInfoDB(4, "clear cart", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			
			Dailylog.logInfoDB(4, "open bundle 1 ", Store, testName);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundle1);

			if (Common.checkElementDisplays(driver, b2cPage.ConvenienceBundle_Add, 3)) {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.ConvenienceBundle_Add);
				if(Common.checkElementExists(driver, b2cPage.cto_AddToCartButton, 3)){
					b2cPage.cto_AddToCartButton.click();
				}
				
			}else{
				Assert.assertTrue("bundle1 cannot add to cart",false);
			}
			Dailylog.logInfoDB(5, "quantity to 5 ", Store, testName);
	
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("5");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		
			Dailylog.logInfoDB(6, "open bundle 2 ", Store, testName);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundle2);

			if (Common.checkElementDisplays(driver, b2cPage.ConvenienceBundle_Add, 3)) {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.ConvenienceBundle_Add);
				if(Common.checkElementExists(driver, b2cPage.cto_AddToCartButton, 3)){
					b2cPage.cto_AddToCartButton.click();
				}
			}else{
				Assert.assertTrue("bundle2 cannot add to cart",false);
			}
		
			
			Dailylog.logInfoDB(7, "quantity to 6 ", Store, testName);	
			Common.scrollToElement(driver, b2cPage.cartPage_Quantity1);
			Common.sleep(5000);
			b2cPage.cartPage_Quantity1.clear();
			b2cPage.cartPage_Quantity1.sendKeys("6");		
			Assert.assertTrue("Line item limitation no effect.", Common.checkElementDisplays(driver, b2cPage.CART_popup_itemlimit, 10));
			
			Dailylog.logInfoDB(8, "quantity to 2 ", Store, testName);
			b2cPage.cartPage_Quantity1.clear();
			b2cPage.cartPage_Quantity1.sendKeys("2");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update1);
			
			
			Dailylog.logInfoDB(9, "open bundle 3 ", Store, testName);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundle3);

			if (Common.checkElementDisplays(driver, b2cPage.ConvenienceBundle_Add, 3)) {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.ConvenienceBundle_Add);
				if(Common.checkElementExists(driver, b2cPage.cto_AddToCartButton, 3)){
					b2cPage.cto_AddToCartButton.click();
				}
			}else{
				Assert.assertTrue("bundle3 cannot add to cart",false);
			}
			Dailylog.logInfoDB(10, "quantity to 5 ", Store, testName);
			Common.scrollToElement(driver, b2cPage.cartPage_Quantity2);
			b2cPage.cartPage_Quantity2.clear();
			b2cPage.cartPage_Quantity2.sendKeys("5");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update2);
			
			Assert.assertTrue("Line item limitation no effect.", Common.checkElementExists(driver, b2cPage.CART_alert, 8));
			Common.scrollToElement(driver, b2cPage.cartPage_Quantity2);
			b2cPage.cartPage_Quantity2.clear();
			b2cPage.cartPage_Quantity2.sendKeys("1");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update2);
			
			
			Dailylog.logInfoDB(11, "Click edit button ", Store, testName);
			Common.scrollAndClick(driver, driver.findElement(By
					.xpath("(//a[contains(text(),'Edit')])[3]")));
			
			Dailylog.logInfoDB(12, "Click yes button ", Store, testName);			
			b2cPage.CART_edit_yes.click();
			
			Dailylog.logInfoDB(13, "add 3 option or software ", Store, testName);
			Common.javascriptClick(driver,b2cPage.PBP_SoftwareTag);
			Common.javascriptClick(driver, driver.findElement(By
					.xpath("(//div[contains(@class,'AddButton') ])[1]")));
			Common.javascriptClick(driver, driver.findElement(By
					.xpath("(//div[contains(@class,'AddButton') ])[2]")));
			Common.javascriptClick(driver, driver.findElement(By
					.xpath("(//div[contains(@class,'AddButton') ])[3]")));
			b2cPage.B2C_PDP_AddToCart.click();
			
			Assert.assertTrue("Line item limitation no effect.", Common.checkElementExists(driver, b2cPage.CART_alert, 8));
			
			//remove cart
			Common.scrollAndClick(driver, driver.findElement(By
					.xpath("(//a[contains(@class,'Remove')])[3]")));
			Common.scrollAndClick(driver, driver.findElement(By
					.xpath("(//a[contains(@class,'Remove')])[4]")));
					
					
			Dailylog.logInfoDB(15, "checkout", Store, testName);
			
			Common.scrollAndClick(b2cPage.PageDriver, b2cPage.lenovo_checkout);
			

			Dailylog.logInfoDB(15, "Shipping Address", Store, testName);
			if (Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)) {
				b2cPage.ASM_EditAddress.click();
			}
			B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());

			Thread.sleep(3000);

			Dailylog.logInfoDB(15, "shipping continue", Store, testName);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);

			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();

			
			Thread.sleep(3000);
			

			Dailylog.logInfoDB(15, "Select  payment.", Store, testName);

			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);

			Common.sleep(2000);
			if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel,5)) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
				
			}else if(Common.isElementExist(driver, By
					.xpath("//*[@id='PaymentTypeSelection_CARD']"))){
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}else if(Common.checkElementExists(driver, b2cPage.payment_PurchaseOrder,5)){
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);

				b2cPage.payment_purchaseNum.clear();
				b2cPage.payment_purchaseNum.sendKeys("1234567890");
			}
			

			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {

				b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);

			}

			Dailylog.logInfoDB(15, "payment continue", Store, testName);

			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			

			if (Common
					.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				try {
					driver.findElement(By.xpath("//*[@id='resellerID']"))
							.clear();
					driver.findElement(By.xpath("//*[@id='resellerID']"))
							.sendKeys("1234567890");
				} catch (Exception e) {
					System.out.println("reseller id is not available");
				}
			}

			
			

			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			Dailylog.logInfoDB(15, "Drop order", Store, testName);
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(15, " order number : " + orderNum, Store, testName);
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
