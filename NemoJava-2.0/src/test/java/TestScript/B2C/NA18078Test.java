package TestScript.B2C;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18078Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String laptopPageURL;
	private String product1 = "";
	String CartName = Common.getDateTimeString();
	String StockChangeMessage = "Out of stock";
	String product2;
	String WarehouseData = "";

	public NA18078Test(String store, String baseStore) {
		this.Store = store;
//		this.product1 = accessory;
		this.testName = "NA-18078";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA18078(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			product1 = testData.B2C.getDefaultMTMPN();
			product2 = testData.B2C.getDefaultAccessoryPN();
			
//			product2 = "65C5KAC1AU";
//			product1 = "81A400AGAU";
			// step~1
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(5000);
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			hmcPage.BaseStore_SearchResult.click();
			Dailylog.logInfoDB(1, "Clicked on search result", Store, testName);
			// click on Administration
			hmcPage.baseStore_administration.click();
			hmcPage.BaseStoreAdministrator_StockMessage.clear();
			hmcPage.BaseStoreAdministrator_StockMessage.sendKeys(StockChangeMessage);
			hmcPage.Common_SaveButton.click();
			hmcPage.BaseStore_PropertiesTab.click();
			WarehouseData = hmcPage.BaseStoreProperties_WarehouseData.getText();
			Dailylog.logInfoDB(1, "Data in Warehouse table is :" + WarehouseData, Store, testName);
			hmcPage.HMC_Logout.click();
			// step~2		
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			if(!driver.getCurrentUrl().contains("account")) {
				driver.get(testData.B2C.getloginPageUrl());
				Common.sleep(2500);
				B2CCommon.handleGateKeeper(b2cPage, testData);
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			}
			Dailylog.logInfoDB(3, "Logged in into B2C as a Buyer", Store, testName);
//			Common.sleep(2000);
//			Assert.assertTrue(driver.getCurrentUrl().contains("my-account"));
			// step~3 : Add 2 product in cart
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			B2CCommon.emptyCart(driver, b2cPage);
			Common.sleep(3000);
			// Adding First Product
			B2CCommon.addPartNumberToCart(b2cPage, product1);
			Dailylog.logInfoDB(3, "Added product1: " + product1, Store, testName);
			// Adding second Product
			B2CCommon.addPartNumberToCart(b2cPage, product2);
			Dailylog.logInfoDB(3, "Added product2: " + product2, Store, testName);

			// get products details
			String Cart_FirstProdPrice = b2cPage.Cart_itemPriceList.get(0).getText();
			String Cart_FirstProdNo = b2cPage.Cart_itemNumberList.get(0).getText();

			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
			String Cart_SecondProdPrice = b2cPage.Cart_itemPriceList.get(1).getText();
			String Cart_SecondProdNo = b2cPage.Cart_itemNumberList.get(1).getText();

			// step~4 : Enter cart name and detail --> save cart
			Common.javascriptClick(driver, b2cPage.Cart_saveCart);
			Common.sleep(5000);
			boolean isDisplayed = Common.checkElementDisplays(driver, By.id("realsavecartname"), 5);
			Assert.assertTrue(isDisplayed, "Save Cart Text box is not present");

			// step~5: Enter detail
			b2cPage.Cart_nameTextBox.clear();
			b2cPage.Cart_nameTextBox.sendKeys("Save_" + CartName);
			b2cPage.Cart_saveCartBtn.click();
			System.out.println("Save_" + CartName);
			Dailylog.logInfoDB(5, "Saved Cart button is clicked", Store, testName);

			// step~6&7
			ViewSavedCart();

			// verify product
			String SavedCart_ProdNo1 = b2cPage.Cart_itemNumberList.get(0).getText();
			String SavedCart_ProdNo2 = b2cPage.Cart_itemNumberList.get(1).getText();
			String SavedCart_ProdPrice1 = b2cPage.Cart_itemPriceList.get(0).getText();
			String SavedCart_ProdPrice2 = b2cPage.Cart_itemPriceList.get(1).getText();
			Assert.assertEquals(Cart_FirstProdNo, SavedCart_ProdNo1);
			Assert.assertEquals(Cart_SecondProdNo, SavedCart_ProdNo2);
			Assert.assertEquals(Cart_FirstProdPrice, SavedCart_ProdPrice1);
			Assert.assertEquals(Cart_SecondProdPrice, SavedCart_ProdPrice2);

			String SubTotal = b2cPage.SavedCart_SubTotal.getText();
			String SubTotalAfterSplit = (SubTotal.split(":          "))[1];
			Dailylog.logInfoDB(9, "Subtotal value : " + (SubTotal.split(":          "))[1], Store, testName);

			// step~8
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(5000);
			hmcPage.Home_baseCommerce.click();
			hmcPage.BaseCommerce_StockLevel.click();
			hmcPage.StockLevel_ProductCode.click();
			hmcPage.StockLevel_ProductCode.sendKeys(product1);
			hmcPage.WarehouseIcon.click();
			Common.switchToWindow(driver, 1);
			Common.sendFieldValue(hmcPage.WarehouseIcon_Warehousecode, WarehouseData);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
			driver.findElement(By.xpath("//div/div[contains(@id,'" + WarehouseData + "')]")).click();
			hmcPage.AddCountry_Use.click();
			Common.switchToWindow(driver, 0);
			hmcPage.HMCBaseCommerce_searchButton.click();
			try {
				Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
			} catch (NoSuchElementException e) {
				hmcPage.hmcHome_hmcSignOut.click();
				HMCCommon.Login(hmcPage, testData);
				hmcPage.Home_baseCommerce.click();
				Common.sleep(5000);
				Common.rightClick(driver, hmcPage.HMCBaseCommerce_stockLevel);
				Common.javascriptClick(driver, hmcPage.BaseCommerce_CreateStockLevel);
				Common.sendFieldValue(hmcPage.StockLevel_ProductCode, Cart_FirstProdNo);
				hmcPage.StockLevel_WarehouseIcon.click();
				Common.switchToWindow(driver, 1);
				Common.sendFieldValue(hmcPage.WarehouseIcon_Warehousecode, WarehouseData);
				Thread.sleep(5000);
				driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
				driver.findElement(By.xpath("//div/div[contains(@id,'" + WarehouseData + "')]")).click();
				Dailylog.logInfoDB(8, "Clicked on search result", Store, testName);
				hmcPage.AddCountry_Use.click();
				Common.switchToWindow(driver, 0);
				hmcPage.HMC_CreateAndSave.click();
			}
			hmcPage.StockLevel_InStockStatus.click();
			hmcPage.InStockStatus_ForceOutOFStock.click();
			hmcPage.Types_SaveBtn.click();
			Thread.sleep(6000);
			Dailylog.logInfoDB(8, "update stock status", Store, testName);
			hmcPage.HMC_Logout.click();

			// step~9
			driver.get(testData.B2C.getloginPageUrl());
			Common.sleep(2500);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			ViewSavedCart();
			Assert.assertTrue(driver.getCurrentUrl().contains("save-cart"), "we are not on save cart detail page.");
			Assert.assertTrue(b2cPage.SavedCart_GrayOutProduct.getText().contains(product1), "Item line is not Grey");

			String OutOfStockMesg = b2cPage.SavedCart_OutOfStockMesg.getText();
			Assert.assertEquals(OutOfStockMesg, StockChangeMessage);

			String SubTotal2 = b2cPage.SavedCart_SubTotal.getText();

			String SubTotal2AfterSplit = (SubTotal2.split(":          "))[1];
			Dailylog.logInfoDB(9, "Subtotal value : " + (SubTotal2.split(":          "))[1], Store, testName);

			assert SubTotalAfterSplit.equals(SubTotal2AfterSplit) == false;

			// Step~10
			b2cPage.SavedCart_OpenCart.click();
			Dailylog.logInfoDB(9, "Clicked on Open Cart", Store, testName);
			// step~11
			Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Not on Cart Page");

			Assert.assertFalse(b2cPage.Cart_itemNumberList.get(0).getText().contains(Cart_FirstProdNo),
					"Part No is still present");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	// +=+=+=+=+=+=+Step~12 : Roll back by deleting step 8 for stock
	// level+=+=+=+=+=+=+=//
	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		Dailylog.logInfoDB(12, "rollback", Store, testName); // roll back
		SetupBrowser();
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		Dailylog.logInfoDB(12, "Logged in into HMC", Store, testName);
		Common.sleep(5000);
		hmcPage.Home_baseCommerce.click();
		hmcPage.BaseCommerce_StockLevel.click();
		WebElement mtmProduct = driver.findElement(By
				.xpath(".//*[@id='Content/StringEditor[in Content/GenericCondition[StockLevel.productCode]]_input']"));
		mtmProduct.clear();
		mtmProduct.sendKeys(product1);
		hmcPage.WarehouseIcon.click();
		Common.switchToWindow(driver, 1);
		Common.sendFieldValue(hmcPage.WarehouseIcon_Warehousecode, WarehouseData);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
		driver.findElement(By.xpath("//div/div[contains(@id,'" + WarehouseData + "')]")).click();
		hmcPage.AddCountry_Use.click();
		Common.switchToWindow(driver, 0);

		hmcPage.HMCBaseCommerce_searchButton.click();
		Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		hmcPage.StockLevel_InStockStatus.click();

		driver.findElement(By.xpath("//select[contains(@id,'inStockStatus')]/option[contains(.,'forceInStock')]"))
				.click();
		// hmcPage.InStockStatus_ForceOutOFStock.click();
		// hmcPage.HMC_CreateAndSave.click();
		hmcPage.Types_SaveBtn.click();
		Thread.sleep(6000);
		/*
		 * Common.sendFieldValue(hmcPage.B2BUnit_SearchIDTextBox, "60DFAAR1AU");
		 * hmcPage.B2BUnit_SearchButton.click(); Common.sleep(3000);
		 * Common.rightClick(driver, hmcPage.StockLevel_Result);
		 * hmcPage.HMC_RemoveResult.click();
		 */
		hmcPage.HMC_Logout.click();
	}

	public void ViewSavedCart() {
		driver.get(testData.B2C.getHomePageUrl()+"/my-account");
		b2cPage.MyAccount_ViewSavedCartHistory.click();
		Common.sleep(2000);
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(.,'" + "Save_" + CartName + "')]")).isDisplayed(),
				"saved cart is not present");
		driver.findElement(By.xpath("//td[contains(.,'" + "Save_" + CartName + "')]/../td/a/div")).click();
		Dailylog.logInfoDB(6, "clicked on view link for save cart " + "Save_" + CartName, Store, testName);
		Common.sleep(3500);
	}

}