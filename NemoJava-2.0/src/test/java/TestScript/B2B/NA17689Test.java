package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class NA17689Test extends SuperTestClass {
	private String ProductID = "";
	private String baseProduct = "";
	private String accesoryID = "";
	private String accessoriesText;
	private String b2bProductUrl;
	private String Unit;
	private B2BPage b2bPage;
	private HMCPage hmcPage;

	public NA17689Test(String store) {
		this.Store = store;
		this.testName = "NA-17689";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p1", "b2b"})
	public void NA17689(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			driver.get(testData.B2B.getLoginUrl());
			Unit = testData.B2B.getLoginUrl();
			Unit = Unit.split("/")[7];
			Dailylog.logInfoDB(0, "Unit: " + Unit, Store, testName);
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());

			if (Common.isElementExist(driver, By.xpath("//a[contains(@href,'Accessories_Upgrades')]"))) {
				b2bPage.HomePage_productsLink.click();
				b2bPage.HomePage_Laptop.click();

				// part number
				By partNumberX = By.xpath("//*[@id='resultList']/div//dt[contains(.,'Part Number:')]/../dd[1]");
				List<WebElement> partNumber = driver.findElements(partNumberX);
				int partNumberSize = partNumber.size();
				Dailylog.logInfoDB(0, "partNumberSize: " + partNumberSize, Store, testName);
				Assert.assertTrue(partNumberSize > 0, "No avalibale product");
				if (partNumberSize > 1)
					ProductID = partNumber.get(1).getText();
				else
					ProductID = partNumber.get(0).getText();
				Dailylog.logInfoDB(0, "ProductID: " + ProductID, Store, testName);

				// get Accessories
				b2bProductUrl = driver.getCurrentUrl();
				Dailylog.logInfoDB(0, "b2bProductUrl:" + b2bProductUrl, Store, testName);
				driver.findElement(By.xpath(".//*[@id='addToAccessorisForm" + ProductID + "']/button")).click();
				Common.sleep(5000);
				b2bPage.PB_expandableSection.click();
				Common.waitElementClickable(driver, b2bPage.PB_firstOption, 5);
				accessoriesText = b2bPage.PB_firstOption.getText();
				Dailylog.logInfoDB(0, "accessoriesText:" + accessoriesText, Store, testName);
				Common.javascriptClick(driver, b2bPage.PB_firstOption);
				//b2bPage.PB_firstOption.click();
				accesoryID = b2bPage.PB_optionID.getText();
				Dailylog.logInfoDB(0, "accesoryID:" + accesoryID, Store, testName);

				// Get baseProduct
				baseProduct = getBaseProduct("staged", ProductID);
				// add unt id for staged product
				setMT_unit("staged", Unit, accesoryID);
				// add unt id for online product
				setMT_unit("online", Unit, accesoryID);
				// Batch Unassign staged option
				batchAssingAndUnassign(false, false, accesoryID, baseProduct, Unit);
				// Batch Unassign online option
				batchAssingAndUnassign(false, true, accesoryID, baseProduct, Unit);
				// check in B2C
				boolean isDisplayed = checkAccessory(accesoryID);
				Assert.assertTrue(!isDisplayed, "batch unassigned option is still displayed");

				// Batch Assign staged Option
				batchAssingAndUnassign(true, false, accesoryID, baseProduct, Unit);
				// Sync product
				syncProduct(baseProduct);
				// Check in B2C
				isDisplayed = checkAccessory(accesoryID);
				Assert.assertTrue(isDisplayed, "batch assigned option is not displayed");

			}else {
				Dailylog.logInfoDB(0, "No available product to test", Store, testName);
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private String getBaseProduct(String category, String ProductID) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		serachProduct(category);
		Common.waitElementVisible(driver, hmcPage.Products_baseProduct);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "baseProduct:" + baseProduct, Store, testName);
		return baseProduct;
	}

	private void serachProduct(String category) {
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(ProductID);
		hmcPage.Catalog_CatalogVersion.click();
		if (category.equals("staged")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogStaged.click();
		} else if (category.equals("online")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		}
		hmcPage.Catalog_SearchButton.click();
		hmcPage.products_resultItem.click();
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

	private void setMT_unit(String category, String unit, String accesoryID) throws Exception {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		serachProduct(category);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		hmcPage.Products_edit.click();
		Common.waitElementClickable(driver, hmcPage.Catalog_multiCountry, 5);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath(
				"//div[contains(@id,'ItemDisplay[B2B]')][contains(.,'B2B')]/../../td[contains(.,'" + Store + "')]"));
		Common.doubleClick(driver, targetProductBuilder);
		switchToWindow(1);
		WebElement targetGroupItem = driver.findElement(
				By.xpath("//*[contains(@id,'ProductOptionsDisplay')]//td[contains(.,'" + accesoryID + "')]"));
		Common.doubleClick(driver, targetGroupItem);
		switchToWindow(2);
		Common.waitElementClickable(driver, hmcPage.Products_groupUnit, 5);
		hmcPage.Products_groupUnit.clear();
		hmcPage.Products_groupUnit.sendKeys(unit);
		Common.waitElementClickable(driver, hmcPage.Products_searchedUnit, 5);
		hmcPage.Products_searchedUnit.click();
		Common.sleep(1000);
		hmcPage.Catalog_save.click();
		driver.close();
		switchToWindow(1);
		hmcPage.Catalog_save.click();
		driver.close();
		switchToWindow(0);
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "added unit to group item", Store, testName);
	}

	private boolean checkAccessory(String accesoryID) throws InterruptedException {
		driver.get(b2bProductUrl);
		driver.findElement(By.xpath(".//*[@id='addToAccessorisForm" + ProductID + "']/button")).click();
		Common.waitElementVisible(driver, b2bPage.PB_expandableSection);
		Common.sleep(1000);
		b2bPage.PB_expandableSection.click();
		Common.waitElementVisible(driver, b2bPage.PB_firstOption);
		By unassignedOption = By.xpath("//input[@value='" + accesoryID + "']");
		return Common.checkElementDisplays(driver, unassignedOption, 5);

	}

	private void batchAssingAndUnassign(Boolean batchAssignFlag, Boolean catalogFlag, String optionID, String mt,
			String unit) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		if (batchAssignFlag) {
			hmcPage.ProductBuilder_batchAssign.click();
			Dailylog.logInfo("...Batch Assign Option...");
		} else {
			hmcPage.ProductBuilder_batchUnassign.click();
			Dailylog.logInfo("...Batch Unassign Option...");
		}
		// select catlog version
		driver.switchTo().frame(0);
		hmcPage.ProductBuilder_clickSelCatalogVersion.click();
		if (catalogFlag) {
			hmcPage.ProductBuilder_selCatalogVersionOnline.click();
			Dailylog.logInfo("...ProductBuilder_selCatalogVersionOnline...");
		} else {
			hmcPage.ProductBuilder_selCatalogVersionStaged.click();
			Dailylog.logInfo("...ProductBuilder_selCatalogVersionStaged...");
		}
		// select unit
		hmcPage.batchAssignOption_searchUnit.click();
		hmcPage.batchAssignOption_unitCodeInput.sendKeys(unit);
		hmcPage.batchAssignOption_searchUnitBtn2.click();
		Common.sleep(3000);
		driver.findElement(By.xpath("//td[text()='" + unit + "']")).click();
		Thread.sleep(2000);
		hmcPage.batchAssignOption_selectUnitBtn.click();
		Thread.sleep(2000);
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

	private void syncProduct(String ProductNo) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
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

		hmcPage.Product_SynchronizationBtn.click();
		Dailylog.logInfoDB(0, "syn product No : " + ProductNo, Store, testName);

		Common.sleep(5000);
		switchToWindow(1);
		hmcPage.Product_SyncTargetBtn.click();
		if (Store.contains("AU") || Store.contains("NZ")) {
			hmcPage.Product_SyncExeJobANZ.click();
		} else if (Store.contains("US") || Store.contains("CA")) {
			hmcPage.Product_SyncExeJobNA.click();
		} else if (Store.contains("GB") || Store.contains("FR")) {
			hmcPage.Product_SyncExeJobEMEA.click();
		} else {
			hmcPage.Product_SyncExeJobAP2.click();
		}
		hmcPage.Product_NeedRunMapping.click();
		new WebDriverWait(driver, 30000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncStart));
		hmcPage.Product_SyncStart.click();
		new WebDriverWait(driver, 3000000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncFinishMsg));
		Common.sleep(3000);
		Dailylog.logInfoDB(0, "Product_SyncStatus: " + hmcPage.Product_SyncStatus.getText(), Store, testName);
		Dailylog.logInfoDB(0, "Product_SyncResult : " + hmcPage.Product_SyncResult.getText(), Store, testName);
		Assert.assertTrue(hmcPage.Product_SyncStatus.getText().contains("FINISHED"),
				"Product_SyncStatus should be FINISHED");
//		Assert.assertTrue(hmcPage.Product_SyncResult.getText().contains("SUCCESS"),
//				"Product_SyncResult should be SUCCESS");
		driver.close();
		switchToWindow(0);

		// Sign-out
		hmcPage.hmcHome_hmcSignOut.click();
	}
}
