package TestScript.B2C;

import java.util.List;

import org.omg.CORBA.COMM_FAILURE;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HACPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18378Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HACPage hacPage;
	public B2BPage b2bPage;
	private HMCPage hmcPage;
	private String catory = "/accessories-and-monitors/c/ACCESSORY?menu-id=Monitors%2C_Accessories_Upgrades";
	private String placeOrderFromClickingStartCheckoutButtonInCart;

	public NA18378Test(String store) {
		this.Store = store;
		this.testName = "NA-18378";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18378(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			// Step~1 : open the website
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			Common.sleep(3000);
			Dailylog.logInfoDB(1, "load b2c: ", Store, testName);

			// step~2 : click "PRODUCTS" AND then click " "Accessories & Upgrades"
			b2cPage.Navigation_ProductsLink.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_Accessories, 5)) {
				driver.get(testData.B2C.getHomePageUrl() + catory);
				Dailylog.logInfoDB(2, "click PRODUCTS  ", Store, testName);
			} else {
				driver.get(testData.B2C.getHomePageUrl() + catory);
			}

			// Step~3 : Click "Browse All Categories"
			if (Common.checkElementExists(driver, b2cPage.Browse_category, 5)) {
				b2cPage.Browse_category.click();
				Common.sleep(2000);
				String text = b2cPage.Browse_category_select.getText();
				Dailylog.logInfoDB(3, text, Store, testName);
				Common.javascriptClick(driver, b2cPage.Browse_category_select);
//				b2cPage.Browse_category_select.click();
				Dailylog.logInfoDB(3, "Click Select a Category and choose one category ", Store, testName);
				// Step~4 : check whether the page title is right whether the "New Search"
				// button exists
				Common.sleep(2000);
				System.out.println(b2cPage.Browse_category_title.getText());
				String title = b2cPage.Browse_category_title.getText().split("\\:")[1].split("\\(")[0];
				
				Dailylog.logInfoDB(3, title.trim().toLowerCase(), Store, testName);
				Assert.assertTrue(title.toLowerCase().trim().equals(text.toLowerCase()));
				//Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Search_button, 5));
				Dailylog.logInfoDB(4, "check whether the page title is right", Store, testName);
			}

			// Step~5 : then choose one product add the product to cart , if all category
			// don't have products , so ignore this step
			if (Common.checkElementExists(driver, b2cPage.Servers_Customise, 5)) {
				List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='addToCartButtonTop']"));
				for (int i = 0; i < findElements.size(); i++) {
					findElements.get(i).click();
					Common.sleep(3000);
				}
				driver.get(testData.B2C.getHomePageUrl()+"/cart");
				Dailylog.logInfoDB(5, "add the product to cart ", Store, testName);
				Common.sleep(2000);
				placeOrderFromClickingStartCheckoutButtonInCart = B2CCommon
						.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData, Store);
				Dailylog.logInfoDB(6, "checkout and place an order for the product ", Store, testName);
			}

			// step~7 Login in HMC , click "Order" -->"Orders" , then input the order number
			((JavascriptExecutor) driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			hmcPage.Home_Order.click();
			Common.sleep(3000);
			hmcPage.Home_Order_Orders.click();
			Common.sleep(2000);
			hmcPage.Home_Order_OrderID.sendKeys(placeOrderFromClickingStartCheckoutButtonInCart);
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