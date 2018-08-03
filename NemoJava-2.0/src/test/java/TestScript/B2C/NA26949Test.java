package TestScript.B2C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA26949Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	private String country1;

	public NA26949Test(String store, String country1) {
		this.Store = store;
		this.country1 = country1;
		this.testName = "NA-26949";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2c"})
	public void NA26949(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			// Step 1 Find one machine type with PB
			String product;
			if (Store.equals("GB"))
				product = "20KSCTO1WWENGB0";
			else
				product = testData.B2C.getDefaultCTOPN();
			String pdpUrl = testData.B2C.getHomePageUrl() + "/p/" + product + "/customize?";
			String productType = "cto";
			driver.get(pdpUrl);
			Dailylog.logInfoDB(1, "ctoProduct: " + product, Store, testName);
			Dailylog.logInfoDB(1, "pdpUrl: " + pdpUrl, Store, testName);
			boolean isValid = productValidation(product, productType);
			if (!isValid) {
				product = testData.B2C.getDefaultMTMPN();
				pdpUrl = testData.B2C.getHomePageUrl() + "/p/" + product;
				productType = "mtm";
				driver.get(pdpUrl);
				Dailylog.logInfoDB(1, "mtmProduct: " + product, Store, testName);
				Dailylog.logInfoDB(1, "pdpUrl: " + pdpUrl, Store, testName);
				isValid = productValidation(product, productType);
				if (!isValid)
					Assert.fail("invalid default product: " + product);
			}
			boolean isPBExist = checkIsPBExist(pdpUrl, productType);
			Dailylog.logInfoDB(1, "isPBExist: " + isPBExist, Store, testName);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('" + testData.HMC.getHomePageUrl() + "')");
			Common.switchToWindow(driver, 1);
			String baseProduct = getBaseProduct("online", product);
			Dailylog.logInfoDB(1, "baseProduct: " + baseProduct, Store, testName);
			if (!isPBExist) {
				List<String> countries = new ArrayList<String>();
				countries.add(country1);
				batchAssingByMT(true, baseProduct, countries, "B2C");
				Common.switchToWindow(driver, 0);
				isPBExist = checkIsPBExist(pdpUrl, productType);
				Assert.assertTrue(isPBExist, "isPBExist after batch assign by MT");
			}

			String groupItemName = "Power accessories";
			String groupItemNameJP = "電源関係";
			String sortName = " ";
			int windowNumber = 1;

			// Step 2~3: sort type blank
			changeSortType(baseProduct, groupItemName, windowNumber, sortName);
			String type = "price";
			List<String> sortedItems = getSortedPriceOrName(pdpUrl, productType, groupItemName, groupItemNameJP, type);
			List<Double> sortedItems1 = new ArrayList<Double>();
			for (String item : sortedItems)
				Dailylog.logInfoDB(3, "sortedItems: " + item, Store, testName);
			for (String item : sortedItems) {
				if (!item.equals(null) && !item.equals(""))
					sortedItems1.add(Double.parseDouble(item));
			}
			List<Double> sortedItems2 = sortedItems1;
			Collections.sort(sortedItems2);
			for (Double item : sortedItems1)
				Dailylog.logInfoDB(3, "actual sortedItems1: " + item, Store, testName);
			for (Double item : sortedItems2)
				Dailylog.logInfoDB(3, "expected sortedItems2: " + item, Store, testName);
			Assert.assertEquals(sortedItems1, sortedItems2);

			// Step 4: priceHighToLow
			sortName = "priceHighToLow";
			changeSortType(baseProduct, groupItemName, windowNumber, sortName);
			type = "price";
			sortedItems = getSortedPriceOrName(pdpUrl, productType, groupItemName, groupItemNameJP, type);
			sortedItems1 = new ArrayList<Double>();
			for (String item : sortedItems) {
				if (!item.equals(null) && !item.equals(""))
					sortedItems1.add(Double.parseDouble(item));
			}
			sortedItems2 = sortedItems1;
			Collections.sort(sortedItems2, Collections.reverseOrder());
			for (Double item : sortedItems1)
				Dailylog.logInfoDB(4, "actual sortedItems1: " + item, Store, testName);
			for (Double item : sortedItems2)
				Dailylog.logInfoDB(4, "expected sortedItems2: " + item, Store, testName);
			Assert.assertEquals(sortedItems1, sortedItems2);

			// Step 5: alphabeticalAToZ
			sortName = "alphabeticalAToZ";
			changeSortType(baseProduct, groupItemName, windowNumber, sortName);
			type = "name";
			List<String> sortedItems3 = getSortedPriceOrName(pdpUrl, productType, groupItemName, groupItemNameJP, type);
			List<String> sortedItems4 = sortedItems3;
			Collections.sort(sortedItems4);
			for (String item : sortedItems3)
				Dailylog.logInfoDB(5, "actual sortedItems3: " + item, Store, testName);
			for (String item : sortedItems4)
				Dailylog.logInfoDB(5, "expected sortedItems4: " + item, Store, testName);
			Assert.assertEquals(sortedItems3, sortedItems4);

			// Step 6: alphabeticalZToA
			sortName = "alphabeticalZToA";
			changeSortType(baseProduct, groupItemName, windowNumber, sortName);
			type = "name";
			sortedItems3 = getSortedPriceOrName(pdpUrl, productType, groupItemName, groupItemNameJP, type);
			sortedItems4 = sortedItems3;
			Collections.sort(sortedItems4, Collections.reverseOrder());
			for (String item : sortedItems3)
				Dailylog.logInfoDB(6, "actual sortedItems3: " + item, Store, testName);
			for (String item : sortedItems4)
				Dailylog.logInfoDB(6, "expected sortedItems4: " + item, Store, testName);
			Assert.assertEquals(sortedItems3, sortedItems4);

			// Step 7: customize
			type = "productNumber";
			List<String> itemsBeforeCus = new ArrayList<String>();
			itemsBeforeCus = getSortedPriceOrName(pdpUrl, productType, groupItemName, groupItemNameJP, type);
			for (String item : itemsBeforeCus)
				Dailylog.logInfoDB(7, "itemsBeforeCus: " + item, Store, testName);
			sortName = "customize";

			sortedItems4 = changeSortType(baseProduct, groupItemName, windowNumber, sortName, itemsBeforeCus);
			sortedItems3 = getSortedPriceOrName(pdpUrl, productType, groupItemName, groupItemNameJP, type);
			for (String item : sortedItems3)
				Dailylog.logInfoDB(7, "actual sortedItems3: " + item, Store, testName);
			for (String item : sortedItems4)
				Dailylog.logInfoDB(7, "expected sortedItems4: " + item, Store, testName);
			Assert.assertEquals(sortedItems3, sortedItems4);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private boolean productValidation(String product, String productType) {
		boolean isValid = false;
		if (Common.isAlertPresent(driver)) {
			driver.switchTo().alert().accept();
			Dailylog.logInfoDB(0, "product invalid alert popup: " + product, Store, testName);
			return isValid;
		}
		if (productType.equals("cto")) {
			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 5))
				Dailylog.logInfoDB(0, "continue customising is not valid on page: " + product, Store, testName);
			else
				isValid = true;

		} else if (productType.equals("mtm")) {
			if (!Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 5))
				Dailylog.logInfoDB(0, "addToCart is not valid on page: " + product, Store, testName);
			else
				isValid = true;
		}
		return isValid;
	}

	boolean checkIsPBExist(String b2cProductUrl, String productType) {
		boolean isPBExist;
		driver.get(b2cProductUrl);
		if (productType.equals("cto"))
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

	private String getBaseProduct(String category, String ProductID) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(category, ProductID);
		Common.waitElementVisible(driver, hmcPage.Products_baseProduct);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "baseProduct:" + baseProduct, Store, testName);
		return baseProduct;
	}

	private void searchProduct(String category, String productID) {
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

	private void batchAssingByMT(Boolean catalogFlag, String mt, List<String> countries, String channel)
			throws InterruptedException {
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
				driver.findElement(
						By.xpath("//span[contains(text(),'" + coutry + "') and @class ='filter-option pull-left']"));
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

	private void changeSortType(String baseProduct, String groupItemName, int i, String sortName) {
		Common.switchToWindow(driver, i);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online", baseProduct);
		hmcPage.Catalog_multiCountry.click();
		String region = Store.substring(0, 2).toUpperCase();
		System.out.println("region: " + region);
		// open product builder
		WebElement targetProductBuilder = driver.findElement(By.xpath(
				"//div[contains(@id,'ItemDisplay[B2C]')][contains(.,'B2C')]/../../td/div[contains(@id,'[" + region + "]')]"));
		Common.doubleClick(driver, targetProductBuilder);
		Common.switchToWindow(driver, ++i);
		// open group items
		Common.sleep(1000);
		WebElement targetGroupItem = driver
				.findElement(By.xpath("//div[contains(@id,'" + groupItemName + "') and @class='gilcEntry']"));
		Common.doubleClick(driver, targetGroupItem);
		Common.switchToWindow(driver, ++i);
		Select sortType = new Select(hmcPage.Products_sortTypeSel);
		sortType.selectByVisibleText(sortName);
		hmcPage.HMC_Save.click();
		driver.close();
		Common.switchToWindow(driver, --i);
		hmcPage.HMC_Save.click();
		driver.close();
		Common.switchToWindow(driver, --i);
		Common.sleep(1000);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	// for customize
	private List<String> changeSortType(String baseProduct, String groupItemName, int i, String sortName,
			List<String> itemsBeforeCus) {
		Common.switchToWindow(driver, i);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online", baseProduct);
		hmcPage.Catalog_multiCountry.click();
		String region = Store.substring(0, 2).toUpperCase();
		// open product builder
		WebElement targetProductBuilder = driver.findElement(By.xpath(
				"//div[contains(@id,'ItemDisplay[B2C]')][contains(.,'B2C')]/../../td/div[contains(@id,'[" + region + "]')]"));
		Common.doubleClick(driver, targetProductBuilder);
		Common.switchToWindow(driver, ++i);
		// open group items
		Common.sleep(1000);
		WebElement targetGroupItem = driver
				.findElement(By.xpath("//div[contains(@id,'" + groupItemName + "') and @class='gilcEntry']"));
		Common.doubleClick(driver, targetGroupItem);
		Common.switchToWindow(driver, ++i);
		Select sortType = new Select(hmcPage.Products_sortTypeSel);
		sortType.selectByVisibleText(sortName);
		Common.sleep(5000);
		String lastBefore = itemsBeforeCus.get(itemsBeforeCus.size() - 1);
		WebElement element = driver
				.findElement(By.xpath("//div[contains(@id,'" + lastBefore + "') and @class='gilcEntry']"));
		WebElement target = hmcPage.Products_optionsFirst;
		(new Actions(driver)).dragAndDrop(element, target).perform();
		Common.sleep(5000);
		hmcPage.HMC_Save.click();
		hmcPage.CronJob_reloadButton.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		Assert.assertEquals(hmcPage.Products_optionsAll.get(0).getText().trim(), lastBefore);
		List<String> itemsAfterCus = new ArrayList<String>();
		for (WebElement ele : hmcPage.Products_optionsAll) {
			String text = ele.getText().trim();
			if (itemsBeforeCus.contains(text))
				itemsAfterCus.add(text);
		}
		driver.close();
		Common.switchToWindow(driver, --i);
		hmcPage.HMC_Save.click();
		driver.close();
		Common.switchToWindow(driver, --i);
		Common.sleep(1000);
		hmcPage.hmcHome_hmcSignOut.click();
		return itemsAfterCus;
	}

	// power accessories
	private List<String> getSortedPriceOrName(String b2cProductUrl, String productType, String groupItemName,
			String groupItemNameJP, String type) {
		Common.switchToWindow(driver, 0);
		driver.get(b2cProductUrl);
		if (productType.equals("cto"))
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		else
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
		b2cPage.PB_accessoriesTab.click();
		WebElement targetSection = driver.findElement(By.xpath(
				"//span[contains(text(),'" + groupItemNameJP + "') or contains(text(),'" + groupItemName + "')]/.."));
		String sectionID = targetSection.getAttribute("id");
		List<String> returns = new ArrayList<String>();
		if (type.equals("price")) {
			List<WebElement> prices = driver.findElements(By
					.xpath("//div[@data-source-id='" + sectionID + "']//span[@class='option-price option-price-opt']"));
			for (WebElement price : prices) {
				String text = price.getAttribute("data-price");
				// System.out.println(text);
				if (!text.equals(null) && !text.equals(""))
					returns.add(text.replace(",", ""));
			}

		} else if (type.equals("name")) {
			List<WebElement> names = driver.findElements(
					By.xpath("//div[@data-source-id='" + sectionID + "']//div[contains(@class,'option-text ')]"));
			for (WebElement name : names) {
				String text = name.getAttribute("textContent").trim();
				if (!text.equals(null) && !text.equals(""))
					returns.add(text);
			}
		} else {
			List<WebElement> productNumbers = driver
					.findElements(By.xpath("//div[@data-source-id='" + sectionID + "']//input"));
			for (WebElement productNumber : productNumbers) {
				String text = productNumber.getAttribute("value").trim();
				if (!text.equals(null) && !text.equals(""))
					returns.add(text);
			}
		}
		return returns;
	}

}
