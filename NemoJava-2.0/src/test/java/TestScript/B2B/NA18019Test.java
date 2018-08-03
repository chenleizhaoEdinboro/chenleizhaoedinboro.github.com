package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA18019Test extends SuperTestClass{
	private static B2BPage b2bPage;

	public NA18019Test(String store) {
		this.Store = store;
		this.testName = "NA-18019";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18019(ITestContext ctx) {
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);

			//Step 1:
			Dailylog.logInfoDB(1, "login the B2B website", Store, testName);
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			Thread.sleep(2000);
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
			Thread.sleep(2000);
			if(Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))){
				b2bPage.cartPage_emptyCartButton.click();
				Dailylog.logInfoDB(1, "Cleared the cart. Cart is empty now.", Store, testName);
			} else{
				Dailylog.logInfoDB(1, "Cart is already empty. No need to clear cart.", Store, testName);
			}

			//Step 2:
			Dailylog.logInfoDB(2, "add one product to cart", Store, testName);
			addProductToCart(testData.B2B.getDefaultMTMPN1(), "(.//img[contains(@class,'cart-item-image')])[1]", b2bPage.cartPage_Quantity, "1");

			//Step 3:
			Dailylog.logInfoDB(3, "add the same product to the cart again", Store, testName);
			addProductToCart(testData.B2B.getDefaultMTMPN1(), "(.//img[contains(@class,'cart-item-image')])[2]", b2bPage.cartPage_Quantity, "1");

			//Step 4:
			//*be aware accessory could not exist
			Dailylog.logInfoDB(4, "add one accessory product to cart", Store, testName);
//			addProductToCart(testData.B2B.getDefaultAccessoryPN1(), "(.//img[contains(@class,'cart-item-image')])[3]", b2bPage.b2bCart_cartItem3Quantity, "1");
			addProductToCart(testData.B2B.getDefaultAccessoryPN1(), "(.//img[contains(@class,'cart-item-image')])[3]", b2bPage.b2bCart_cartItem3Quantity, "1");

			//Step 5:
			Dailylog.logInfoDB(4, "add the same accessory product to cart", Store, testName);
//			addProductToCart(testData.B2B.getDefaultAccessoryPN1(), "(.//img[contains(@class,'cart-item-image')])[3]", b2bPage.b2bCart_cartItem3Quantity, "1");			
			addProductToCart(testData.B2B.getDefaultAccessoryPN1(), "(.//img[contains(@class,'cart-item-image')])[3]", b2bPage.b2bCart_cartItem3Quantity, "2");
			
			
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}

	public void addProductToCart(String partNumber, String cartItemImageLocator, WebElement cartItemQuantityLocator, String cartItemQuantity) throws InterruptedException{
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(partNumber);
		b2bPage.cartPage_addButton.click();
		Thread.sleep(2000);
		System.out.println(Common.isElementExist(driver, By.xpath(cartItemImageLocator)));
		Assert.assertEquals(Common.isElementExist(driver, By.xpath(cartItemImageLocator)), true);
		System.out.println(cartItemQuantityLocator.getAttribute("value")+" "+cartItemQuantity);
		Assert.assertEquals(cartItemQuantityLocator.getAttribute("value"), cartItemQuantity);
	}
}
