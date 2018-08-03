package TestScript.B2C;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19104Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	boolean flag = false;

	public NA19104Test(String store) {
		this.Store = store;
		this.testName = "NA-19104";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p1", "b2c","compatibility "})
	public void NA19104(ITestContext ctx) {
		try {
			this.prepareTest();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			// 1.login b2c site
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());;
		
			// 2, click Deals
			if (!Common.checkElementExists(driver, b2cPage.Navigation_DEALS, 5))
				this.setNoDataLog("Deals link is not shown");
			NavigationBar.goToDealsFirstLink(b2cPage);

			// 3, Check Product display
			System.out.println(""+Common.checkElementExists(driver, b2cPage.Deals_shopnowbutton, 5)+Common.checkElementExists(driver, b2cPage.Deals_price, 5) + Common.checkElementExists(driver, b2cPage.Deals_usprice, 5)+Common.checkElementExists(driver, b2cPage.Deals_nzprice, 5));
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Deals_price, 5) || Common.checkElementExists(driver, b2cPage.Deals_usprice, 5)||Common.checkElementExists(driver, b2cPage.Deals_nzprice, 5));
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Deals_shopnowbutton, 5));
			
			// 5,Click Shop Now under one Product			
			//Add current product to cart from PDP page
			String currentUrl = driver.getCurrentUrl();
			System.out.println("URL is :" + currentUrl);
			List<WebElement> shopnowbutton_list = driver.findElements(By.xpath("//a[contains(text(),'Shop Now')]"));
			for(int i = 1;i<=shopnowbutton_list.size();i++) {
				Common.NavigateToUrl(driver, Browser, currentUrl);
				Common.javascriptClick(driver, driver.findElement(By.xpath("(//a[contains(text(),'Shop Now')])["+i+"]")));
				Common.waitElementClickable(driver, b2cPage.PDP_ViewCurrentModelTab, 5);
				Common.scrollAndClick(driver, b2cPage.PDP_ViewCurrentModelTab);
				//b2cPage.PDP_ViewCurrentModelTab.click();
				if(Common.checkElementExists(driver, b2cPage.B2C_PDP_CustomizeButton, 3)) {
					b2cPage.B2C_PDP_CustomizeButton.click();
					Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 5);
					b2cPage.Product_AddToCartBtn.click();
					break;
				} else if(Common.checkElementExists(driver, b2cPage.B2C_PDP_MTMAddToCartButton, 3)) {
					b2cPage.B2C_PDP_MTMAddToCartButton.click();
					break;
				}else {
					continue;
				}
			}
			
			//Add current product to cart from PDP page
			
			//check if cto exist and add to cart
			//then check the mtm exist
			// if all not exist then change to another part
			
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()+"/cart");
//			b2cPage.CartPage_icon.click();
			Common.sleep(3000);
			String orderNum = B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData);
			System.out.println("orderNum is :" + orderNum);
			Dailylog.logInfoDB(1, orderNum,Store,testName);
					
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			hmcPage.Home_Order.click();
			Common.sleep(3000);
			hmcPage.Home_Order_Orders.click();
			Common.sleep(2000);
			hmcPage.Home_Order_OrderID.sendKeys(orderNum);
			hmcPage.Home_Order_OrderSearch.click();
			if (hmcPage.Home_Order_OrderStatus.getText().toLowerCase().equals("fraud")) {
				Common.sleep(3000);
				hmcPage.Home_Order_OrderSearch.click();
			}
			Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"));
			Dailylog.logInfoDB(7, "Login in HMC , click Order -->Orders", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}