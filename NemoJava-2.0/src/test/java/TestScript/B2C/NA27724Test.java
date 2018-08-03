package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HACPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27724Test extends SuperTestClass {
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	public HACPage hacPage;
	private String country1;
	private String country2;
	private String product1;
	private String product2;
	private String optionID;

	public NA27724Test(String store, String country1, String country2, String product1, String product2,
			String optionID) {
		this.Store = store;
		this.country1 = country1;
		this.country2 = country2;
		this.product1 = product1;
		this.product2 = product2;
		this.optionID = optionID;
		this.testName = "NA-27724";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void NA27724(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			hacPage = new HACPage(driver);
//			String product1 = "20KH002KUS";
//			String product2 = "20KH002KUS";
//			String mt = "22TP2TXX16G20KH";
//			String optionID = "4X40N18009";
			
			// HMC Get base product
			String mt = getBaseProduct(product1);
			Dailylog.logInfoDB(0, "baseProduct: " + mt, Store, testName);

			String url1 = testData.B2C.getHomePageUrl() + "/p/" + product1;
			String url2 = testData.B2C.getHomePageUrl().replace("us/en", "ca/en").replace("usweb", "webca") + "/p/"
					+ product2;
			List<String> countries = new ArrayList<String>();
			countries.add(country1);
			countries.add(country2);
			if (!isOptionShownOnPB(url1, optionID) || !isOptionShownOnPB(url2, optionID))
				batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 1. Has machine type, country and channel in impex
			String region = this.Store;
			String channel = "B2B";
			boolean isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(isOptionShownOnPB(url1, optionID), optionID + " " + url1);

			region = this.Store;
			channel = "B2C";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertTrue(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 2. Has only machine type and country in impex
			mt = "22TP2TXX16G20KH";
			region = this.Store;
			channel = "NULL";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertTrue(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 3. Has only machine type and channel in impex
			mt = "22TP2TXX16G20KH";
			region = "NULL";
			channel = "B2B";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(isOptionShownOnPB(url1, optionID), optionID + " " + url1);

			mt = "22TP2TXX16G20KH";
			region = "NULL";
			channel = "B2C";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertFalse(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 4. Has only country and channel in impex
			mt = "NULL";
			region = this.Store;
			channel = "B2B";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(isOptionShownOnPB(url1, optionID), optionID + " " + url1);

			mt = "NULL";
			region = this.Store;
			channel = "B2C";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertTrue(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			mt = "22TP2TXX16G20KH";
			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 5. Has only country in impex
			mt = "NULL";
			region = this.Store;
			channel = "NULL";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertTrue(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			mt = "22TP2TXX16G20KH";
			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 6. Has only channel in impex
			mt = "NULL";
			region = "NULL";
			channel = "B2B";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(isOptionShownOnPB(url1, optionID), optionID + " " + url1);

			mt = "NULL";
			region = "NULL";
			channel = "B2C";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertFalse(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			mt = "22TP2TXX16G20KH";
			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

			// 7.Has only option id in impex
			mt = "NULL";
			region = "NULL";
			channel = "NULL";
			isImported = impexImport(optionID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(isOptionShownOnPB(url1, optionID), optionID + " " + url1);
			Assert.assertFalse(isOptionShownOnPB(url2, optionID), optionID + " " + url2);

			mt = "22TP2TXX16G20KH";
			batchAssingAndUnassign(true, true, optionID, mt, countries, "B2C");

		} catch (

		Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private boolean impexImport(String optionID, String mt, String region, String channel) throws InterruptedException {
		System.out.println(testData.HAC.getHomePageUrl());
		driver.get(testData.HAC.getHomePageUrl());
		Common.waitElementClickable(driver, hacPage.HacLogin_UserName, 20);
		Common.sendFieldValue(hacPage.HacLogin_UserName, testData.HAC.getLoginId());
		Common.sendFieldValue(hacPage.HacLogin_UserPassword, testData.HAC.getPassword());
		hacPage.HacLogin_LoginButton.click();

		hacPage.HacHome_console.click();
		Common.javascriptClick(driver, hacPage.HacHome_impexImport);
		String command1 = "\"$catalogVersion=catalogVersion(catalog(id[default='masterMultiCountryProductCatalog']), version[default='Online'])[unique=true,default='masterMultiCountryProductCatalog:Online']\"";
		String command2 = "INSERT_UPDATE ProductOption;code[unique=true];$catalogVersion;name[translator=com.nemo.b2b.core.translator.ProductOptionGroupItemsUnassignTranslator]";
		String command3 = ";" + optionID + ";;" + mt + ":" + region + ":" + channel;
		Dailylog.logInfoDB(0, command3, Store, testName);

		Actions action = new Actions(driver);
		Common.sleep(1000);
		action.sendKeys(hacPage.ImpexImport_content1, Keys.ENTER).build().perform();
		action.sendKeys(hacPage.ImpexImport_content2, Keys.ENTER).build().perform();
		action.sendKeys(hacPage.ImpexImport_content3, command3).build().perform();
		action.sendKeys(hacPage.ImpexImport_content2, command2).build().perform();
		action.sendKeys(hacPage.ImpexImport_content1, command1).build().perform();

		Common.sleep(1000);
		hacPage.ImpexImport_importBtn.click();

		Common.waitElementVisible(driver, hacPage.ImpexImport_notificationMsg);
		String notification = hacPage.ImpexImport_notificationMsg.getText();
		hacPage.HacHome_Logout.click();
		return notification.contains("成功") || notification.contains("successfully");

	}

	private boolean isOptionShownOnPB(String url, String optionID) {
		try {
			driver.get(url);
		} catch (TimeoutException e) {
			driver.navigate().refresh();
		}

		if (!url.contains("CTO")) {
			if (!Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 15))
				Assert.fail("addToCart is not valid on page, please update test product");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
		} else {
			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 15))
				Assert.fail("continue customising is not valid on page, please update test product");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		}
		b2cPage.PB_accessoriesTab.click();
		By targetOption = By.xpath("//input[@value='" + optionID + "']");
		return Common.isElementExist(driver, targetOption);
	}

	private void batchAssingAndUnassign(Boolean batchAssignFlag, Boolean catalogFlag, String optionID, String mt,
			List<String> countries, String channel) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		if (batchAssignFlag)
			hmcPage.ProductBuilder_batchAssign.click();
		else
			hmcPage.ProductBuilder_batchUnassign.click();
		// select catalog version
		driver.switchTo().frame(0);
		hmcPage.ProductBuilder_clickSelCatalogVersion.click();
		if (catalogFlag)
			hmcPage.ProductBuilder_selCatalogVersionOnline.click();
		else
			hmcPage.ProductBuilder_selCatalogVersionStaged.click();
		// select country
		for (String coutry : countries) {
			WebElement targetCountry = driver.findElement(By.xpath("//span[text()='" + coutry + "']"));
			Common.javascriptClick(driver, targetCountry);
			try {
				driver.findElement(
						By.xpath("//span[contains(text(),'" + coutry + "') and @class ='filter-option pull-left']"));
			} catch (NoSuchElementException e) {
				Assert.fail("batch assign by MT: country added failure: " + coutry);
			}
		}
		// search channel
		Select channel1 = new Select(hmcPage.ProductBuidler_channelCode);
		channel1.selectByVisibleText(channel);
		// search option
		hmcPage.ProductBuilder_showSearchOptionDialog.click();
		hmcPage.ProductBuilder_optionCodeInput.sendKeys(optionID);
		hmcPage.ProductBuilder_searchOptionBtn2.click();
		By targetOptionX = By.xpath(".//*[@id='optionTable']//*[text()='" + optionID + "']");
		Common.waitElementClickable(driver, driver.findElement(targetOptionX), 30);
		driver.findElement(targetOptionX).click();
		hmcPage.ProductBuilder_selectOptionBtn.click();
		// Select Machine Type
		try {
			Common.sleep(8000);
			Common.waitElementVisible(driver, hmcPage.ProductBuilder_mtList);
		} catch (TimeoutException e) {
			hmcPage.ProductBuilder_showSearchOptionDialog.click();
			hmcPage.ProductBuilder_optionCodeInput.sendKeys(optionID);
			hmcPage.ProductBuilder_searchOptionBtn2.click();
			Common.waitElementClickable(driver, driver.findElement(targetOptionX), 30);
			driver.findElement(targetOptionX).click();
			hmcPage.ProductBuilder_selectOptionBtn.click();
			Common.sleep(8000);
		}
		hmcPage.ProductBuilder_searchInput.sendKeys(mt);
		Common.sleep(5000);
		By targetMTX = By.xpath("//*[contains(@id,'machineTypesSelectNamems')]/option[contains(text(),'" + mt + "')]");
		if (Common.isElementExist(driver, targetMTX)) {
			Common.waitElementClickable(driver, driver.findElement(targetMTX), 30);
			driver.findElement(targetMTX).click();
			hmcPage.ProductBuilder_addSelected.click();
			hmcPage.ProductBuilder_submit.click();
			Common.waitAlertPresent(driver, 30);
			driver.switchTo().alert().accept();
			if (batchAssignFlag)
				Dailylog.logInfo("...Batch Assign Option success...");
			else
				Dailylog.logInfo("...Batch UNassign Option success...");
		} else {
			if (batchAssignFlag)
				Dailylog.logInfo("...Batch Assign No MT searched out...");
			else
				Dailylog.logInfo("...Batch UNassign No MT searched out...");
		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private String getBaseProduct(String product) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(product);
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		Common.sleep(1000);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(2000);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
		return baseProduct;
	}
}
