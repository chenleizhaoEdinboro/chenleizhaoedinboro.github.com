package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18405Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;

	public NA18405Test(String Store) {
		this.Store = Store;
		this.testName = "NA-18405";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2b"})
	public void NA18405(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			// step1 Open one website
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			Dailylog.logInfoDB(1, "Open one website", Store, testName);

			// step2 click Accessories & Upgrades
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2bPage.HomePage_AccessoriesLink, 5)) {
				b2bPage.HomePage_AccessoriesLink.click();
				Assert.assertTrue(driver.getCurrentUrl().contains("Accessories_Upgrades"));
				Dailylog.logInfoDB(2, "click Accessories & Upgrades", Store, testName);
			}else {
				this.setNoDataLog("Accessories & Upgrades link is not shown");
			}
			
			String url  = driver.getCurrentUrl();
			List<WebElement> categoryList = driver.findElements(By.xpath("//div[@class='yCmsComponent']/div[1]//h2/a"));
			for(int i = 1;i<=categoryList.size();i++){
				driver.get(url);
				driver.findElement(By.xpath("(//div[@class='yCmsComponent']/div[1]//h2/a)["+i+"]")).click();
				Common.sleep(3000);
				Assert.assertTrue(driver.getCurrentUrl().contains(testData.B2B.getHomePageUrl().replace("https://LIeCommerce:M0C0v0n3L!@", "")));
				Assert.assertTrue(Common.checkElementExists(driver,b2bPage.productPage_AddToCart, 3), "check accessories list");
			}
			

/*			// step3 In the select list, click all options iteratelly
			Common.sleep(3000);
			b2bPage.Product_cables.click();
			Common.sleep(3000);
			List<WebElement> productList = driver.findElements(By.xpath("//h3[@class='accessoriesListing-title']/a"));
			for (int i = 0; i < productList.size(); i++) {
				Assert.assertTrue(productList.get(i).getAttribute("href").contains(category));
			}
			Dailylog.logInfoDB(3, "In the select list, click all options iteratelly", Store, testName);

			// step4 select the option with products , and add one product to cart
			String[] split = b2bPage.Product_partnumber.getText().split(":");
			B2BCommon.addProduct(driver, b2bPage, split[1]);
			Dailylog.logInfoDB(4, " add one product to cart", Store, testName);
*/
			// step5 checkout the product
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultAccessoryPN1());
			b2bPage.lenovoCheckout.click();
			String placeAnOrder = B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
			Dailylog.logInfoDB(5, " checkout the product", Store, testName);

			// step6 go to HMC and check the order status
			Common.sleep(3000);
			((JavascriptExecutor) driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(placeAnOrder);
			hmcPage.Home_Order_OrderSearch.click();
			Common.sleep(3000);
			if (hmcPage.Home_Order_OrderStatus.getText().equals("fraud")) {
				Common.sleep(3000);
				hmcPage.Home_Order_OrderSearch.click();
			}
			Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"));
			Dailylog.logInfoDB(6, "Login in HMC , click Order -->Orders: "+placeAnOrder, Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
