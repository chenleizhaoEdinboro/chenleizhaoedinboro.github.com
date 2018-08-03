package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17126Test extends SuperTestClass {

	B2CPage b2cPage = null;

	Actions actions = null;
	String orderNum = null;
	private String customer;

	private String addressline;
	private String county;
	private String zipcode;
	private String WebsitesSearchID;
	private boolean flag;
	private String tempBrowser;
	String WarehouseName;

	public NA17126Test(String store, String WebsitesID) {
		this.Store = store;
		this.testName = "NA-17126";
		this.WebsitesSearchID = WebsitesID;
	}

	public void findBaseProduct(HMCPage hmcPage) {
		hmcPage.HMCHome_Catalog.click();
		hmcPage.HMCCatalog_products.click();
		hmcPage.HMCB2CUnitSearch_IdValue.clear();
		hmcPage.HMCB2CUnitSearch_IdValue.sendKeys(testData.B2C
				.getDefaultMTMPN());

		hmcPage.HMCCatalog_catalogVersion.click();
		hmcPage.HMCCatalog_masterMultiCountryProductCatalog.click();
		hmcPage.HMCBaseCommerce_searchButton.click();
		Common.doubleClick(driver, hmcPage.HMCBaseCommerce_searchResult);
		System.out.println(hmcPage.HMCCatalog_getMT.getText().split("-")[0]);
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17126(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			actions = new Actions(driver);
			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.HMCBaseCommerce_baseCommerce.click();
			hmcPage.HMCBaseCommerce_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(WebsitesSearchID);

			hmcPage.baseStore_search.click();
			Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
			// case was previously looking at this value now changing it
			// WarehouseName = hmcPage.baseStore_DeliveryOrigin.getText();
			hmcPage.BaseStore_PropertiesTab.click();
			Thread.sleep(3000);
			WarehouseName = driver
					.findElement(
							By.xpath("((//div[contains(text(),'Warehouses')])[2]/../..//*[contains(text(),'Name')]/../../../..//div)[11]"))
					.getText();

			// force Out of stock
			hmcPage.BaseCommerce_StockLevel.click();
			WebElement mtmProduct = driver
					.findElement(By
							.xpath(".//*[@id='Content/StringEditor[in Content/GenericCondition[StockLevel.productCode]]_input']"));
			mtmProduct.clear();
			mtmProduct.sendKeys(testData.B2C.getDefaultMTMPN());

			driver.findElement(
					By.xpath(".//*[@id='Content/NemoSearchConfigurable[StockLevel]_searchbutton']"))
					.click();
			if (Common.isElementExist(
					driver,
					By.xpath("(//div[contains(@id,'" + WarehouseName
							+ "')])[2]"))) {

				Common.doubleClick(
						driver,
						driver.findElement(By.xpath("(//div[contains(@id,'"
								+ WarehouseName + "')])[2]")));
				Thread.sleep(3000);
				hmcPage.StockLevel_InStockStatus.click();
				hmcPage.InStockStatus_ForceOutOFStock.click();
				hmcPage.Types_SaveBtn.click();
				Thread.sleep(6000);
			} else {
				Common.rightClick(driver, hmcPage.BaseCommerce_StockLevel);
				hmcPage.HMCBaseCommerce_createStockLevel.click();
				hmcPage.StockLevel_ProductCode.clear();
				hmcPage.StockLevel_ProductCode.sendKeys(testData.B2C
						.getDefaultMTMPN());
				hmcPage.StockLevel_WarehouseIcon.click();
				Thread.sleep(5000);
				Set<String> winHandels = driver.getWindowHandles();
				List<String> popupWindows = new ArrayList<String>(winHandels);
				Thread.sleep(3000);
				driver.switchTo().window(popupWindows.get(1));
				hmcPage.WarehouseIcon_Warehousecode.clear();
				hmcPage.WarehouseIcon_Warehousecode.sendKeys(WarehouseName);// disable
																			// to
																			// input
																			// the
																			// warehousecode.
				hmcPage.stockLevel_searchWareHouse.click();
				hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
				driver.switchTo().window(popupWindows.get(0));
				hmcPage.StockLevel_InStockStatus.click();
				hmcPage.InStockStatus_ForceOutOFStock.click();
				WebElement wareHouseAmount = driver
						.findElement(By
								.xpath(".//*[@id='Content/IntegerEditor[in Content/Attribute[.available]]_input']"));
				Thread.sleep(3000);
				wareHouseAmount.clear();
				wareHouseAmount.sendKeys("1000");
				/*
				 * hmcPage.stockLevel_availableAmount.clear();
				 * hmcPage.stockLevel_availableAmount.sendKeys("1000");
				 */
				driver.findElement(
						By.id("Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate]_label"))
						.click();
				Thread.sleep(6000);

			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open('about:blank','_blank');", "");
			Set<String> winHandels = driver.getWindowHandles();
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(1));
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			if (b2cPage.PageDriver.getCurrentUrl().endsWith(
					"RegistrationGatekeeper")) {
				B2CCommon.handleGateKeeper(b2cPage, testData);
			}
			// add product to cart
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.clear();
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.sendKeys(testData.B2C.getDefaultMTMPN());
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quickAddInput']/a"))) {
				driver.findElement(By.xpath("//*[@id='quickAddInput']/a"))
						.click();
			} else {
				driver.findElement(By.xpath(".//*[@id='quickAddInput']/button"))
						.click();
			}
			Assert.assertFalse(Common.isElementExist(driver,
					By.xpath(".//*[@id='quantity0']")));

			// set to instock
			driver.switchTo().window(it.get(0));
			hmcPage.StockLevel_InStockStatus.click();
			hmcPage.InStockStatus_ForceInStock.click();
			hmcPage.Types_SaveBtn.click();
			Thread.sleep(6000);
			// add product again
			driver.switchTo().window(it.get(1));
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.clear();
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.sendKeys(testData.B2C.getDefaultMTMPN());
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quickAddInput']/a"))) {
				driver.findElement(By.xpath("//*[@id='quickAddInput']/a"))
						.click();
			} else {
				driver.findElement(By.xpath(".//*[@id='quickAddInput']/button"))
						.click();
			}

			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath(".//*[@id='quantity0']")));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
