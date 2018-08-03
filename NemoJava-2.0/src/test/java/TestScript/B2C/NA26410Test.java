package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class NA26410Test extends SuperTestClass {
	private String productID = "";
	private String baseProduct = "";
	private String accesoryID = "";
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	private String country1;
	private String country2;

	public NA26410Test(String store, String country1, String country2) {
		this.Store = store;
		this.country1 = country1;
		this.country2 = country2;
		this.testName = "NA-26410";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2c"})
	public void NA26410(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);

			// Step 1. Find one machine type which has no product builder container
			productID = testData.B2C.getDefaultCTOPN();
			String b2cProductUrl;

			if (!Store.contains("CO")) {
				Dailylog.logInfoDB(1, "productID: " + productID, Store, testName);
				b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + productID + "/customize?";
				Dailylog.logInfoDB(1, "b2cProductUrl: " + b2cProductUrl, Store, testName);
				driver.get(b2cProductUrl);
				Dailylog.logInfoDB(1, "currentUrl: " + driver.getCurrentUrl(), Store, testName);

			} else {
				productID = testData.B2C.getDefaultMTMPN();
				//productID = "20H1000400";
				b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + productID;
				driver.get(b2cProductUrl);
				Dailylog.logInfoDB(1, "productID: " + productID, Store, testName);
				Dailylog.logInfoDB(1, "b2cProductUrl: " + b2cProductUrl, Store, testName);
			}
			// check if product is valid
			if (isAlertPresent()) {
				driver.switchTo().alert().accept();
				Assert.fail("getDefaultCTOPN is invalid: " + productID);
			}
			if (!Store.contains("CO")) {
				if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 30))
					Assert.fail("continue customising is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			} else {
				if (!Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 30))
					Assert.fail("addToCart is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
			}
			boolean isPBExist = checkIsPBExist(b2cProductUrl);
			Dailylog.logInfoDB(1, "isPBExist: " + isPBExist, Store, testName);
			// get baseProduct
			baseProduct = getBaseProduct("online", productID);
			Dailylog.logInfoDB(1, "baseProduct: " + baseProduct, Store, testName);
			// delete PB if exist
			String region = Store.substring(0, 2).toUpperCase();
			if (isPBExist) {
				deletePB("online", baseProduct, "B2C", region);
				isPBExist = checkIsPBExist(b2cProductUrl);
				Assert.assertTrue(!isPBExist, "PB still exists after deleting product builder container");
			}

			// Step2~4. Go to Nemo - Product Builder - Batch assign by MT
			List<String> countries = new ArrayList<String>();
			countries.add(country1);
			countries.add(country2);
			batchAssingByMT(true, baseProduct, countries, "B2C");

			// Step 5.Go the Machine type, check in HMC and B2C
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			serachProduct("online", baseProduct);
			hmcPage.Catalog_multiCountry.click();
			try {
				WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[B2C]')][contains(.,'B2C')]/../../td/div[contains(@id,'[" + region + "]')]"));
				Common.doubleClick(driver, targetProductBuilder);
			} catch (NoSuchElementException e) {
				Assert.fail("no pb container after batch assign by MT");
			}
			switchToWindow(1);
			int groupItemsSize = hmcPage.ProductBuidler_groupItems.size();
			int groupItemsDetailSize = hmcPage.ProductBuidler_groupItemsDetail.size();
			Assert.assertTrue(groupItemsSize > 10, "groupItemsSize");
			Assert.assertTrue(groupItemsDetailSize > 10, "groupItemsDetailSize");
			driver.close();
			switchToWindow(0);
			hmcPage.hmcHome_hmcSignOut.click();
			isPBExist = checkIsPBExist(b2cProductUrl);
			Assert.assertTrue(isPBExist, "isPBExist after batch assign by MT");

			// Step 6.Find one option, do batch unassign for multiple country in one channel
			accesoryID = findAccessory();
			Dailylog.logInfoDB(0, "accesoryID: " + accesoryID, Store, testName);
			// Assign first to make sure unassign success
			batchAssingAndUnassign(true, true, accesoryID, baseProduct, countries, "B2C");
			batchAssingAndUnassign(false, true, accesoryID, baseProduct, countries, "B2C");
			driver.get(b2cProductUrl);
			if (!Store.contains("CO")) {
				if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 5))
					Assert.fail("continue customising is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			} else {
				if (!Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 5))
					Assert.fail("addToCart is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
			}
			String accesoryID2 = findAccessory();
			Dailylog.logInfoDB(0, "accesoryID2: " + accesoryID2, Store, testName);
			Assert.assertNotEquals(accesoryID2, accesoryID, "accesoryID2: " + accesoryID2);

			// Step 7.Find one option, do batch assign for multiple country in one channel
			batchAssingAndUnassign(true, true, accesoryID, baseProduct, countries, "B2C");
			driver.get(b2cProductUrl);
			driver.manage().deleteAllCookies();
			driver.get(b2cProductUrl);
			if (!Store.contains("CO")) {
				if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 5))
					Assert.fail("continue customising is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			} else {
				if (!Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 5))
					Assert.fail("addToCart is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
			}
			String accesoryID3 = findAccessory();
			Dailylog.logInfoDB(0, "accesoryID3: " + accesoryID3, Store, testName);
			Assert.assertEquals(accesoryID3, accesoryID, "accesoryID3: " + accesoryID3);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void clickContinue_1() {
		List<WebElement> list = driver.findElements(By.xpath("//*[@id='product-builder-form']//ol[@class='stepsItem']/li"));

		int count = list.size() - 3;
		for (int x = 1; x <= count; x++) {
			// b2cPage.Product_Continue.click();
			int y = x + 1;
			String tab_name = driver.findElement(By.xpath("(//*[@id='product-builder-form']//ol[@class='stepsItem']/li/a/span)[" + y + "]")).getText().toString();
			if ((tab_name.equals("Accessories")) || (tab_name.equals("周辺機器")) || (tab_name.equals("配件"))) {
				break;
			}
			driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")).click();
		}
	}

	private boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String findAccessory() {
		String accessoriesText;
		String accesoryID;
		String url = driver.getCurrentUrl();
		if (url.contains("cart?")) {
			Dailylog.logInfoDB(0, "url: " + url, Store, testName);
			Assert.fail("please update test product, no product builder page for this product");
		}
		// Desculpe
		By noAccMsg = By.xpath("//div[@id='Accessories' or @data-tabcode='Accessories']//div[contains(text(),'Desculpe') or contains(text(),'sorry') or contains(.,'Desculpe') or contains(.,'sorry')]");
		if (Common.checkElementDisplays(driver, noAccMsg, 3)) {
			Dailylog.logInfoDB(0, driver.findElement(noAccMsg).getText(), Store, testName);
			Assert.fail("please update test product, no accessory for this product");
		}
		if (Common.checkElementDisplays(driver, By.xpath("//*[contains(@class,'configuratorHeader') or contains(@class,'configuratorItem-accordion-header')]"), 5)) {
			Dailylog.logInfoDB(0, "new product builder", Store, testName);
			Common.javascriptClick(driver, b2cPage.PB_accessoriesTab);
			By option = By.xpath("(.//*[@id='Accessories']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//div[contains(@class,'option-text ')]");
			if (!driver.findElement(option).isDisplayed()) {
				Common.sleep(12000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PB_firstExpandAccessory);
			}
			Common.sleep(3000);
			accessoriesText = b2cPage.newPDP_firstAccessories.getText();
			Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
			Common.javascriptClick(driver, b2cPage.newPDP_firstAccessories);
			Common.sleep(3000);
			accesoryID = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];
		} else {
			Dailylog.logInfoDB(0, "old product builder", Store, testName);
			clickContinue_1();
			try {
				b2cPage.PDP_firstConfigraution.click();
			} catch (Exception e) {
				Dailylog.logInfo("no need to expand accessory");
			}
			driver.findElement(By.xpath("(//header[contains(@id,'ccessories')]/..//div[contains(@class,'show-detail')])[1]")).click();
			Common.sleep(5000);
			// accessoriesText =
			// driver.findElement(By.xpath("(//div[@class='lnvmodal-tail']//span)[4]")).getText();
			// Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
			accesoryID = driver.findElement(By.xpath("(//div[@class='lnvmodal-tail']//span)[2]")).getText();
		}
		return accesoryID;
	}

	// batchAssignFlag true: batch assign catalogFlag true online
	private void batchAssingAndUnassign(Boolean batchAssignFlag, Boolean catalogFlag, String optionID, String mt, List<String> countries, String channel) throws InterruptedException {
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
				driver.findElement(By.xpath("//span[contains(text(),'" + coutry + "') and @class ='filter-option pull-left']"));
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

	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			// System.out.println(windows.size());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			Dailylog.logInfoDB(0, "Swith to window timeout, window: " + windowNo, Store, testName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private String getBaseProduct(String category, String ProductID) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		serachProduct(category, ProductID);
		Common.waitElementVisible(driver, hmcPage.Products_baseProduct);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "baseProduct:" + baseProduct, Store, testName);
		return baseProduct;
	}

	private void serachProduct(String category, String productID) {
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productID);
		hmcPage.Catalog_CatalogVersion.click();
		if (category.equals("staged")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogStaged.click();
		} else if (category.equals("online")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		}
		hmcPage.Catalog_SearchButton.click();
		hmcPage.products_resultItem.click();
	}

	private void deletePB(String category, String baseProduct, String channel, String region) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		serachProduct(category, baseProduct);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[" + channel + "]')][contains(.,'" + channel + "')]/../../td[contains(.,'" + region + "')]"));
		Common.rightClick(driver, targetProductBuilder);
		Common.waitElementClickable(driver, hmcPage.Catalog_RemoveMenu, 3);
		hmcPage.Catalog_RemoveMenu.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		hmcPage.Catalog_save.click();
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "product builder container deleted", Store, testName);
	}

	private void batchAssingByMT(Boolean catalogFlag, String mt, List<String> countries, String channel) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.ProductBuidler_batchAssignByMT.click();
		// select catlog version
		driver.switchTo().frame(0);
		hmcPage.ProductBuilder_clickSelCatalogVersion.click();
		if (catalogFlag)
			hmcPage.ProductBuilder_selCatalogVersionOnline.click();
		else
			hmcPage.ProductBuilder_selCatalogVersionStaged.click();
		// select machine type
		hmcPage.ProductBuidler_selectMachineType.click();
		hmcPage.ProductBuidler_machineTypeCodeInput.sendKeys(mt);
		hmcPage.ProductBuidler_searchMachineTypeBtn2.click();
		Common.sleep(5000);
		By targetOptionX = By.xpath(".//*[@id='machineTypeTable']//*[text()='" + mt + "']");
		Common.waitElementClickable(driver, driver.findElement(targetOptionX), 30);
		driver.findElement(targetOptionX).click();
		hmcPage.ProductBuidler_selectMachineTypeBtn.click();
		// select country
		for (String coutry : countries) {
			WebElement targetCountry = driver.findElement(By.xpath("//span[text()='" + coutry + "']"));
			Common.javascriptClick(driver, targetCountry);
			try {
				driver.findElement(By.xpath("//span[contains(text(),'" + coutry + "') and @class ='filter-option pull-left']"));
			} catch (NoSuchElementException e) {
				Assert.fail("batch assign by MT: country added failure: " + coutry);
			}
		}
		// search channel
		Select channel1 = new Select(hmcPage.ProductBuidler_channelCode);
		channel1.selectByVisibleText(channel);
		// Assign
		Common.sleep(5000);
		hmcPage.ProductBuilder_submit.click();
		for (int i = 0; i < 3; i++) {
			try {
				Common.waitAlertPresent(driver, 120);
				if (Common.isAlertPresent(driver)) {
					String alertText = driver.switchTo().alert().getText();
					driver.switchTo().alert().accept();
					Dailylog.logInfoDB(0, alertText, Store, testName);
					if (alertText.equals("success"))
						break;
				} else {
					Dailylog.logInfoDB(0, "no batch assign by mt alert", Store, testName);
				}
			} catch (TimeoutException e) {
				Assert.fail("assign MT time>120 seconds");
			}
		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "Batch Asign by MT success", Store, testName);
	}

	boolean checkIsPBExist(String b2cProductUrl) {
		boolean isPBExist;
		driver.get(b2cProductUrl);
		if (!Store.contains("CO"))
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		else
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
		Common.sleep(5000);
		String url = driver.getCurrentUrl();
		if (url.contains("?activeTabName=&transMessageFlag=true"))
			isPBExist = true;
		else
			isPBExist = false;
		return isPBExist;
	}
}
