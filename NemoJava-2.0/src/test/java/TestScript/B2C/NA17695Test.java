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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.CTOCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.CTOPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17695Test extends SuperTestClass {
	private String ctoProduct = "";
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	public CTOPage ctoPage;

	public NA17695Test(String store) {
		this.Store = store;
		this.testName = "NA-17695";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void NA17695(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			ctoPage = new CTOPage(driver);

			if (Store.equals("US"))
				ctoProduct = "20KSCTO1WWENUS0"; // ThinkPad-E580

			// 1 get CTO product
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			String pdpUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?";
			driver.get(pdpUrl);
			Common.sleep(5000);
			if (Common.isAlertPresent(driver)) {
				driver.switchTo().alert().accept();
				Assert.fail("test product is invalid: " + ctoProduct);
			}
			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 10))
				Assert.fail("CTO_addToCart does not exist, please update test product");
			boolean isNewUI;
			isNewUI = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_newconfiguratorHeader)));
			Dailylog.logInfoDB(1, "ctoProduct: " + ctoProduct, Store, testName);
			Dailylog.logInfoDB(1, "isNewUI: " + isNewUI, Store, testName);

			// 2 get 6 cv and 6 options
			ArrayList<String> unSelectedCV = getCV(isNewUI, false);
			for (String cv : unSelectedCV)
				Dailylog.logInfoDB(2, "unSelectedCV: " + cv, Store, testName);
			ArrayList<String> selectedCV = getCV(isNewUI, true);
			for (String cv : selectedCV)
				Dailylog.logInfoDB(2, "selectedCV: " + cv, Store, testName);
			ArrayList<String> option = getAccessory(isNewUI);
			for (String accessory : option)
				Dailylog.logInfoDB(3, "option: " + accessory, Store, testName);

			// 3 get baseProduct plp url and bundle url
			String baseProduct = getBaseProduct("online", ctoProduct);
			Dailylog.logInfoDB(4, "baseProduct: " + baseProduct, Store, testName);
			// 22TP2TEE57020H5
			String plpUrl = testData.B2C.getHomePageUrl() + "/p/" + baseProduct.substring(0, 11);
			Dailylog.logInfoDB(4, "plpUrl: " + plpUrl, Store, testName);
			String bundleUrl = testData.CTO.getHomePageUrl() + "ca/nomenclature=" + baseProduct + ctoProduct + "~~~";
			Dailylog.logInfoDB(4, "bundleUrl: " + bundleUrl, Store, testName);

			// 4 Open CTO engine suggest all 6 values
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(3000);
			CTOCommon.Login(ctoPage, testData);
			Common.sleep(5000);
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
			navigateToAndRetry(bundleUrl);
			for (String data : unSelectedCV)
				suggestOrCancelV(getC(data), getV(data), true);

			// 5 save and publish
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_Save);
			Thread.sleep(5000);
			Common.scrollToElement(driver, ctoPage.BundlePage_Nomenclature);
			// ctoPage.BundlePage_Publish.click();
			Common.javascriptClick(driver, ctoPage.BundlePage_Publish);
			Thread.sleep(1000);
			Common.javascriptClick(driver, ctoPage.BundlePage_PublishConfirm);
			// ctoPage.BundlePage_PublishConfirm.click();
			Dailylog.logInfoDB(5, "BundlePage_PublishConfirm", Store, testName);
			try {
				new WebDriverWait(driver, 1800).until(ExpectedConditions.visibilityOf(ctoPage.BundlePage_PublishSuccMsg));
				Dailylog.logInfoDB(5, "BundlePage_PublishConfirm success", Store, testName);
			} catch (Exception e) {
				Dailylog.logInfoDB(5, "BundlePage_PublishConfirm failure", Store, testName);
				// assert false;
			}

			// 6 HMC Sync product in HMC
			syncProduct(ctoProduct);

			// 7 HMC show promoted items
			ArrayList<String> promotedCVs = showPromotedItems(ctoProduct, "B2C", "cv");
			for (String text : promotedCVs)
				Dailylog.logInfoDB(7, "promotedCVs: " + text, Store, testName);
			for (String text : unSelectedCV)
				Assert.assertTrue(promotedCVs.contains(text), text + " is not displayed in show promoted items");

			// 8 create promoted options
			String proOptName = testName.replace("NA-", "AutoTest") + Store;
			boolean isCreated = createPromotedOption(option, proOptName);
			Assert.assertFalse(isCreated);
			Dailylog.logInfoDB(8, "Promoted options created false pass", Store, testName);
			option.remove(0);
			isCreated = createPromotedOption(option, proOptName);
			Assert.assertTrue(isCreated);
			Dailylog.logInfoDB(8, "Promoted options created success pass", Store, testName);

			// 9 use promoted options
			String region = Store.substring(0, 2).toUpperCase();
			addProOptToProduct(baseProduct, "online", "B2C", region, proOptName);
			Dailylog.logInfoDB(9, "Added promoted options to product builder container", Store, testName);

			// 10 check in PLP
			driver.get(plpUrl);
			Common.sleep(5000);
			List<WebElement> promoptedInput = driver.findElements(By.xpath("//input[@class='" + ctoProduct + "']"));
			Assert.assertEquals(promoptedInput.size(), 5);
			ArrayList<String> promotedB2C = new ArrayList<String>();
			for (WebElement ele : promoptedInput)
				promotedB2C.add(ele.getAttribute("value"));
			for (String text : promotedB2C)
				Assert.assertTrue(promotedCVs.contains(text));

			// 11 cto only left 3 suggest cvs, cancel others
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(3000);
			if (Common.checkElementDisplays(driver, By.xpath("login-username"), 5)) {
				CTOCommon.Login(ctoPage, testData);
				Common.sleep(2000);
			}
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
			navigateToAndRetry(bundleUrl);
			ArrayList<String> expectedPrmotedCVs = new ArrayList<String>();
			while (expectedPrmotedCVs.size() != 3 && promotedCVs.size() > 1) {
				expectedPrmotedCVs.add(promotedCVs.get(0));
				promotedCVs.remove(0);
			}
			for (String data : promotedCVs)
				suggestOrCancelV(getC(data), getV(data), false);
			Dailylog.logInfoDB(11, "cto only left 3 suggest cvs, cancel others", Store, testName);

			// 12 save and publish
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_Save);
			Thread.sleep(5000);
			Common.scrollToElement(driver, ctoPage.BundlePage_Nomenclature);
			ctoPage.BundlePage_Publish.click();
			Thread.sleep(1000);
			ctoPage.BundlePage_PublishConfirm.click();
			Dailylog.logInfoDB(12, "BundlePage_PublishConfirm", Store, testName);
			try {
				new WebDriverWait(driver, 1800).until(ExpectedConditions.visibilityOf(ctoPage.BundlePage_PublishSuccMsg));
				Dailylog.logInfoDB(12, "BundlePage_PublishConfirm success", Store, testName);
			} catch (Exception e) {
				Dailylog.logInfoDB(12, "BundlePage_PublishConfirm failure", Store, testName);
				// assert false;
			}

			// 13 sync in HMC
			syncProduct(ctoProduct);
			Dailylog.logInfoDB(13, "sync product in HMC", Store, testName);

			// 14 check the promoted items in HMC
			ArrayList<String> promotedCVs2 = showPromotedItems(ctoProduct, "B2C", "cv");
			Assert.assertEquals(promotedCVs2.size(), expectedPrmotedCVs.size());
			for (String text : promotedCVs2) {
				Dailylog.logInfoDB(14, "promotedCVs2: " + text, Store, testName);
				Assert.assertTrue(expectedPrmotedCVs.contains(text), text + " not found in expectedPrmotedCVs");
			}

			ArrayList<String> promotedOptions = showPromotedItems(ctoProduct, "B2C", "option");
			Assert.assertEquals(option.size(), promotedOptions.size());
			for (String text : promotedOptions) {
				Dailylog.logInfoDB(14, "promotedOptions: " + text, Store, testName);
				Assert.assertTrue(option.contains(text), text + " not found in option");
			}

			// 15 change the sort of promoted CVs
			ArrayList<String> sortedCVs = chagneSort(ctoProduct, "B2C");
			ArrayList<String> expected = sortedCVs;
			expected.add(promotedOptions.get(0));
			expected.add(promotedOptions.get(1));
			for (String text : expected)
				Dailylog.logInfoDB(15, "sorted: " + text, Store, testName);

			// 16 check in B2C
			Common.sleep(12000);
			driver.get(plpUrl);
			Common.sleep(2000);
			driver.navigate().refresh();
			promoptedInput = driver.findElements(By.xpath("//input[@class='" + ctoProduct + "']"));
			Common.scrollToElement(driver, promoptedInput.get(0));
			Assert.assertEquals(promoptedInput.size(), 5);
			promotedB2C = new ArrayList<String>();
			for (WebElement ele : promoptedInput)
				promotedB2C.add(ele.getAttribute("value"));
			for (String text : promotedB2C)
				Dailylog.logInfoDB(16, "promotedB2C: " + text, Store, testName);
			Assert.assertEquals(promotedB2C, expected);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private ArrayList<String> getCV(boolean isNewUI, boolean isSelectedCV) {
		ArrayList<String> returnCV = new ArrayList<String>();
		By CTO_CV;
		List<WebElement> targetCV;
		if (isSelectedCV) {
			targetCV = b2cPage.CTO_selectedCV;
			CTO_CV = By.xpath("//div[contains(@class,'group-item') and contains(@class,'visible') and not(contains(@class,'active'))]//span[@class='label-text']/../span[@class='debug-id']");
		} else {
			targetCV = b2cPage.CTO_unselectedCV;
			CTO_CV = By.xpath("//div[contains(@class,'group-item') and contains(@class,'visible') and not(contains(@class,'active'))]//span[@class='label-text']/../span[@class='debug-id']");
		}
		if (isNewUI) {
			By expandCategoriesX = By.xpath(Common.convertWebElementToString(b2cPage.cto_expandCategories));
			if (Common.checkElementDisplays(driver, expandCategoriesX, 3))
				Common.javascriptClick(driver, b2cPage.cto_expandCategories);
			if (Common.checkElementExists(driver, driver.findElement(CTO_CV), 3)) {
				for (int i = 0; i < 6; i++)
					returnCV.add(targetCV.get(i).getAttribute("textContent").trim().replace("[", "").replace("]:", "").replace("]", ""));
			} else {
				Dailylog.logInfoDB(1, "record CV failure, no CV to record", Store, testName);
			}
		} else {
			if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld)), 3)) {
				// TODO xpath refer to NA17455
				Assert.fail("record CV failure,old UI");
			} else {
				Dailylog.logInfoDB(1, "record CV failure, no CV to record", Store, testName);
			}
		}
		return returnCV;
	}

	public void navigateToAndRetry(String url) {
		try {
			driver.get(url);
		} catch (TimeoutException exception) {
			System.out.println("driver.get timeout");
			driver.navigate().refresh();
		}

	}

	public ArrayList<String> getAccessory(boolean isNewUI) {
		ArrayList<String> optionID = new ArrayList<String>();
		String url = driver.getCurrentUrl();
		if (url.contains("cart?")) {
			Dailylog.logInfoDB(0, "url: " + url, Store, testName);
			Assert.fail("please update test product, no product builder page for this product");
		}
		// Desculpe
		By noAccMsg = By.xpath("//div[@id='Accessories' or @data-tabcode='Accessories']//div[contains(text(),'Desculpe') or contains(text(),'sorry') or contains(.,'Desculpe') or contains(.,'sorry')]");
		if (Common.checkElementDisplays(driver, noAccMsg, 3)) {
			Dailylog.logInfoDB(1, driver.findElement(noAccMsg).getText(), Store, testName);
			Assert.fail("please update test product, no accessory for this product");
		}
		if (isNewUI) {
			Common.javascriptClick(driver, b2cPage.PB_accessoriesTab);
			for (WebElement ele : b2cPage.PB_expandableSectionHeader)
				Common.javascriptClick(driver, ele);
			int size = 6;
			for (int i = 0; i < size; i++) {
				Common.javascriptClick(driver, b2cPage.PB_optionText.get(i));
				Common.sleep(3000);
				String currentOption = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];
				if (currentOption.substring(currentOption.length() - 2).equals(Store.substring(0, 2))) {
					System.out.println("mtm found instead of acc");
					size++;
				} else
					optionID.add(currentOption);
			}

		} else {
			// TODO xpath refer to NA16687
			Assert.fail("record CV failure,old UI");
		}
		return optionID;
	}

	// [NB_CPU:INTEL_CORE_I5_7200U_MB]:
	public String getC(String text) {
		String c;
		c = text.split(":")[0].replace("[", "");
		Dailylog.logInfoDB(0, "c: " + c, Store, testName);
		return c;
	}

	public String getV(String text) {
		String c;
		c = text.split(":")[1].replace("]", "");
		Dailylog.logInfoDB(0, "v: " + c, Store, testName);
		return c;
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

	private void suggestOrCancelV(String c, String v, boolean isSuggest) {
		WebElement openC = driver.findElement(By.xpath("//tr[@groupid='" + c + "']//span[@class='icon fa fa-caret-square-o-down']"));
		Common.javascriptClick(driver, openC);
		WebElement suggestV = driver.findElement(By.xpath("//tbody[@componentid='" + v + "']//td[@property='suggest']"));
		if (isSuggest) {
			if (!suggestV.getAttribute("class").contains("clickable yes"))
				Common.javascriptClick(driver, suggestV);
		} else {
			if (suggestV.getAttribute("class").contains("clickable yes"))
				Common.javascriptClick(driver, suggestV);
		}
	}

	private void syncProduct(String ProductNo) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		Common.sleep(5000);
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(ProductNo);
		hmcPage.Cataglog_CatalogVersionSel.click();
		Common.waitElementClickable(driver, hmcPage.Cataglog_CatalogVersionStaged, 60);
		hmcPage.Cataglog_CatalogVersionStaged.click();
		hmcPage.B2BUnit_SearchButton.click();

		By byLocator3 = By.id("Content/StringDisplay[" + ProductNo + "]_span");
		Common.waitElementClickable(driver, driver.findElement(byLocator3), 60);
		Common.doubleClick(driver, driver.findElement(byLocator3));
		// hmcPage.Product_DisplayTo.sendKeys(testData.B2C.getUnit());
		// driver.findElement(By.xpath("//td[contains(text(),'" + testData.B2C.getUnit()
		// + "')]")).click();
		// hmcPage.Types_SaveBtn.click();

		hmcPage.Product_SynchronizationBtn.click();
		Dailylog.logInfoDB(0, "syn product No : " + ProductNo, Store, testName);

		Common.sleep(5000);
		switchToWindow(1);
		try {
			hmcPage.Product_SyncTargetBtn.click();
		} catch (NoSuchElementException e) {
			switchToWindow(0);
			hmcPage.Product_SynchronizationBtn.click();
			Common.sleep(8000);
			switchToWindow(1);
			hmcPage.Product_SyncTargetBtn.click();
		}
		if (Store.contains("AU") || Store.contains("NZ")) {
			hmcPage.Product_SyncExeJobANZ.click();
		} else if (Store.contains("US") || Store.contains("CA")) {
			hmcPage.Product_SyncExeJobNA.click();
		} else if (Store.contains("GB") || Store.contains("FR") || Store.contains("IE")) {
			hmcPage.Product_SyncExeJobEMEA.click();
		} else if (Store.contains("BR") || Store.contains("MX")) {
			hmcPage.Product_SyncExeJobLA.click();
		} else {
			hmcPage.Product_SyncExeJobAP2.click();
		}
		hmcPage.Product_NeedRunMapping.click();
		Common.waitElementClickable(driver, hmcPage.Product_SyncStart, 30);
		try {
			Common.javascriptClick(driver, hmcPage.Product_SyncStart);
		} catch (TimeoutException e) {
			Common.sleep(12000);
		}
		new WebDriverWait(driver, 400).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncFinishMsg));
		Common.sleep(5000);
		Dailylog.logInfoDB(0, "Product_SyncStatus: " + hmcPage.Product_SyncStatus.getText(), Store, testName);
		Dailylog.logInfoDB(0, "Product_SyncResult : " + hmcPage.Product_SyncResult.getText(), Store, testName);
		Assert.assertTrue(hmcPage.Product_SyncStatus.getText().contains("FINISHED"), "Product_SyncStatus should be FINISHED");
		Assert.assertTrue(hmcPage.Product_SyncResult.getText().contains("SUCCESS"), "Product_SyncResult should be SUCCESS");
		driver.close();
		switchToWindow(0);

		// Sign-out
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
			System.out.println("Swith to window timeout, window: " + windowNo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private ArrayList<String> showPromotedItems(String productCode, String channel, String type) {
		ArrayList<String> promotedItems = new ArrayList<String>();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.PB_showPromotedOptions.click();
		driver.switchTo().frame(hmcPage.ShowPromotedOptions_iframe);
		Select catalogVersionSel = new Select(hmcPage.ShowPromotedOptions_catalogVersionSel);
		catalogVersionSel.selectByValue("masterMultiCountryProductCatalog-Online");
		hmcPage.ShowPromotedOptions_productCode.clear();
		hmcPage.ShowPromotedOptions_productCode.sendKeys(productCode);
		WebElement countrySel = driver.findElement(By.xpath("//select[@id='countryCode']//option[@value='" + Store.substring(0, 2) + "']"));
		countrySel.click();
		WebElement channelSel = driver.findElement(By.xpath("//select[@id='channelCode']//option[@value='" + channel + "']"));
		channelSel.click();
		hmcPage.ShowPromotedOptions_search.click();
		if (type.toLowerCase().equals("cv"))
			for (WebElement ele : hmcPage.ShowPromotedOptions_promotedCV)
				promotedItems.add(ele.getText());
		else
			for (WebElement ele : hmcPage.ShowPromotedOptions_promotedOptions)
				promotedItems.add(ele.getText());
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		return promotedItems;
	}

	private boolean createPromotedOption(ArrayList<String> options, String proOptName) {
		boolean isCreated = false;
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.ProductBuidler_promotedProductOptions.click();
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();

		hmcPage.ProductBuidler_proOptName.sendKeys(proOptName);
		hmcPage.ProductBuidler_searchBtn.click();
		By targetResult = By.xpath("//div[contains(@id,'" + proOptName + "')]");
		if (Common.checkElementDisplays(driver, targetResult, 3)) {
			Common.doubleClick(driver, driver.findElement(targetResult));
			hmcPage.ProductBuidler_deleteBtn.click();
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
		}
		Common.rightClick(driver, hmcPage.ProductBuidler_promotedProductOptions);
		Common.waitElementClickable(driver, hmcPage.ProductBuidler_createProOpt, 3);
		hmcPage.ProductBuidler_createProOpt.click();
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		hmcPage.ProductBuidler_proOptName.sendKeys(proOptName);
		for (String data : options) {
			Common.rightClick(driver, hmcPage.ProductBuidler_optionsTable);
			Common.waitElementClickable(driver, hmcPage.HMC_add, 3);
			hmcPage.HMC_add.click();
			Common.switchToWindow(driver, 1);
			hmcPage.Template_codeInput.sendKeys(data);
			Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
			hmcPage.Template_selCatalogVersionOnline.click();
			hmcPage.Template_searchBtn.click();
			hmcPage.Template_searchedHierarchy.click();
			hmcPage.Template_useBtn.click();
			Common.switchToWindow(driver, 0);
		}
		hmcPage.Nemo_InstallmentsCreate.click();
		Common.sleep(5000);
		isCreated = !Common.checkElementDisplays(driver, By.id("y_popupmessage_ok_label"), 15);
		if (!isCreated)
			Assert.assertTrue(hmcPage.CreatePromotedOptions_popText.getText().contains("exceeds the limit of 5"), "error message not correct: " + hmcPage.CreatePromotedOptions_popText.getText());
		Common.javascriptClick(driver, hmcPage.hmcHome_hmcSignOut);
		// hmcPage.hmcHome_hmcSignOut.click();
		return isCreated;
	}

	private void addProOptToProduct(String productID, String category, String channel, String region, String proOptName) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(category, productID);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[" + channel + "]')][contains(.,'" + channel + "')]/../../td/div[contains(@id,'[" + region + "]')]"));
		Common.doubleClick(driver, targetProductBuilder);
		Common.switchToWindow(driver, 1);
		Common.sleep(5000);
		hmcPage.Product_promotedOptions.sendKeys(proOptName);
		Common.sleep(5000);
		hmcPage.Product_promotedOptions.sendKeys(Keys.ENTER);
		hmcPage.ProductBuidler_saveBtn.click();
		Common.sleep(5000);
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.ProductBuidler_saveBtn.click();
		Common.sleep(5000);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private ArrayList<String> chagneSort(String productCode, String channel) {
		ArrayList<String> sortedCVs = new ArrayList<String>();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.PB_showPromotedOptions.click();
		driver.switchTo().frame(hmcPage.ShowPromotedOptions_iframe);
		Common.waitElementClickable(driver, hmcPage.ShowPromotedOptions_catalogVersionOnline, 5);
		hmcPage.ShowPromotedOptions_catalogVersionOnline.click();
		hmcPage.ShowPromotedOptions_productCode.clear();
		hmcPage.ShowPromotedOptions_productCode.sendKeys(productCode);
		WebElement countrySel = driver.findElement(By.xpath("//select[@id='countryCode']//option[@value='" + Store.substring(0, 2) + "']"));
		countrySel.click();
		WebElement channelSel = driver.findElement(By.xpath("//select[@id='channelCode']//option[@value='" + channel + "']"));
		channelSel.click();
		hmcPage.ShowPromotedOptions_search.click();
		String lastBefore = hmcPage.ShowPromotedOptions_promotedCV.get(hmcPage.ShowPromotedOptions_promotedCV.size() - 1).getText();
		WebElement element = hmcPage.ShowPromotedOptions_promotedCV.get(hmcPage.ShowPromotedOptions_promotedCV.size() - 1);
		WebElement target = hmcPage.ShowPromotedOptions_promotedCV.get(0);
		(new Actions(driver)).dragAndDrop(element, target).perform();
		hmcPage.ShowPromotedOptions_save.click();
		driver.findElement(By.id("reset-frame")).click();
		// hmcPage.CronJob_reloadButton.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		Assert.assertEquals(hmcPage.ShowPromotedOptions_promotedCV.get(0).getText().trim(), lastBefore);
		for (WebElement ele : hmcPage.ShowPromotedOptions_promotedCV)
			sortedCVs.add(ele.getText());
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		return sortedCVs;
	}
}
