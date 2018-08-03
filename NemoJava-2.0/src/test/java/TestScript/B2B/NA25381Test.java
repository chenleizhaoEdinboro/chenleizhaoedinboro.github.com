package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class NA25381Test extends SuperTestClass {
	private String ProductID = "";
	private String baseProduct = "";
	private String accesoryID = "";
	private String Unit;
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	private String country1;
	private String country2;

	public NA25381Test(String store, String country1, String country2) {
		this.Store = store;
		this.testName = "NA-25381";
		this.country1 = country1;
		this.country2 = country2;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2b"})
	public void NA25381(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			driver.get(testData.B2B.getLoginUrl());
			Unit = testData.B2B.getLoginUrl();
			Unit = Unit.split("/")[7];
			Dailylog.logInfoDB(0, "Unit: " + Unit, Store, testName);
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();

			// Step 1. Find one machine type which has no product builder container
			By partNumberX = By.xpath("//*[@id='resultList']/div//dt[contains(.,'Part Number:')]/../dd[1]");
			List<WebElement> partNumber = driver.findElements(partNumberX);
			int partNumberSize = partNumber.size();
			Dailylog.logInfoDB(1, "partNumberSize: " + partNumberSize, Store, testName);
			Assert.assertTrue(partNumberSize > 0, "No avalibale product");
			if (partNumberSize > 1) {
				ProductID = partNumber.get(1).getText();
				driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[2]")).click();
			} else {
				ProductID = partNumber.get(0).getText();
				driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[1]")).click();
			}
			// check product builder
			String pdpURL = driver.getCurrentUrl();
			String addAccessoryXpath = "//form[contains(@id,'addToAccessorisForm')]/button";
			if (Common.checkElementDisplays(driver, By.xpath(addAccessoryXpath), 3))
				b2bPage.PDPPage_AddAccessories.click();
			else {
				ProductID = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
				b2bPage.Agreement_agreementsAddToCart.click();
			}

			Dailylog.logInfoDB(1, "ProductID: " + ProductID, Store, testName);
			boolean isPBExist = Common.checkElementDisplays(driver, By.xpath("//span[contains(text(),'Product Builder')]"), 3);
			Dailylog.logInfoDB(1, "isPBExist: " + isPBExist, Store, testName);

			// get baseProduct
			baseProduct = getBaseProduct("online", ProductID);

			// delete PB if exist
			String region = Store.substring(0, 2).toUpperCase();
			if (isPBExist) {
				deletePB("online", baseProduct, "B2B", region);
				driver.get(testData.B2B.getLoginUrl());
				B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
				driver.get(pdpURL);
				if (Common.checkElementDisplays(driver, By.xpath(addAccessoryXpath), 3))
					b2bPage.PDPPage_AddAccessories.click();
				else
					b2bPage.Agreement_agreementsAddToCart.click();
				isPBExist = Common.checkElementDisplays(driver, By.xpath("//span[contains(text(),'Product Builder')]"), 3);
				Assert.assertTrue(!isPBExist, "PB still exists after deleting product builder container");
			}

			// Step2~4. Go to Nemo - Product Builder - Batch assign by MT
			List<String> countries = new ArrayList<String>();
			countries.add(country1);
			countries.add(country2);
			batchAssingByMT(true, baseProduct, countries, "B2B");

			// Step 5.Go the Machine type, check in HMC and B2C
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			serachProduct("online", baseProduct);
			hmcPage.Catalog_multiCountry.click();
			try {
				WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[B2B]')][contains(.,'B2B')]/../../td[contains(.,'" + region + "')]"));
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
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			driver.get(pdpURL);
			if (Common.checkElementDisplays(driver, By.xpath(addAccessoryXpath), 3))
				b2bPage.PDPPage_AddAccessories.click();
			else {
				ProductID = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
				b2bPage.Agreement_agreementsAddToCart.click();
			}
			isPBExist = Common.checkElementDisplays(driver, By.xpath("//span[contains(text(),'Product Builder')]"), 3);
			Assert.assertTrue(isPBExist, "isPBExist after batch assign by MT");

			// Step 6.Find one option, do batch unassign for multiple country in one channel
			By PB_firstUnselectedOption = By.xpath("(//div[@class='option-textFrame']//div[text()]/../..//input[@value!=''])[1]");
			if (!Common.checkElementDisplays(driver, PB_firstUnselectedOption, 3))
				b2bPage.PB_expandableSection.click();
			Common.waitElementClickable(driver, b2bPage.PB_firstUnselectedOption, 5);
			b2bPage.PB_firstUnselectedOption.click();
			accesoryID = b2bPage.PB_firstUnselectedOption.getAttribute("value");
			Dailylog.logInfoDB(0, "accesoryID:" + accesoryID, Store, testName);
			setMT_unit("online", Unit, accesoryID);
			Common.sleep(5000);
			batchAssingAndUnassign(true, true, accesoryID, baseProduct, countries, "B2B");
			batchAssingAndUnassign(false, true, accesoryID, baseProduct, countries, "B2B");
			boolean isDisplayed = checkAccessory(accesoryID, pdpURL);
			Assert.assertTrue(!isDisplayed, "batch unassigned option is still displayed");
			
			// Step 7.Find one option, do batch assign for multiple country in one channel
			batchAssingAndUnassign(true, true, accesoryID, baseProduct, countries, "B2B");
			isDisplayed = checkAccessory(accesoryID, pdpURL);
			Assert.assertTrue(isDisplayed, "batch assigned option is not displayed");
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
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

	private void deletePB(String category, String baseProduct, String channel, String region) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		serachProduct(category, baseProduct);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[B2B]')][contains(.,'" + channel + "')]/../../td[contains(.,'" + region + "')]"));
		Common.rightClick(driver, targetProductBuilder);
		Common.waitElementClickable(driver, hmcPage.Catalog_RemoveMenu, 3);
		hmcPage.Catalog_RemoveMenu.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		hmcPage.Catalog_save.click();
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "product builder container deleted", Store, testName);
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
		serachProduct(category, ProductID);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		hmcPage.Products_edit.click();
		Common.waitElementClickable(driver, hmcPage.Catalog_multiCountry, 5);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[B2B]')][contains(.,'B2B')]/../../td[contains(.,'" + Store + "')]"));

		Common.doubleClick(driver, targetProductBuilder);
		switchToWindow(1);
		WebElement targetGroupItem = driver.findElement(By.xpath("//*[contains(@id,'ProductOptionsDisplay')]//td[contains(.,'" + accesoryID + "')]"));
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

	private boolean checkAccessory(String accesoryID, String url) throws InterruptedException {
		driver.get(url);
		String addAccessoryXpath = "//form[contains(@id,'addToAccessorisForm')]/button";
		if (Common.checkElementDisplays(driver, By.xpath(addAccessoryXpath), 3))
			b2bPage.PDPPage_AddAccessories.click();
		else {
			ProductID = url.substring(url.lastIndexOf('/') + 1, url.length());
			b2bPage.Agreement_agreementsAddToCart.click();
		}
		By PB_firstUnselectedOption = By.xpath("(//div[@class='option-textFrame']//div[text()]/../..//input[@value!=''])[1]");
		if (!Common.checkElementDisplays(driver, PB_firstUnselectedOption, 3))
			b2bPage.PB_expandableSection.click();
		Common.waitElementVisible(driver, b2bPage.PB_firstOption);
		By unassignedOption = By.xpath("//input[@value='" + accesoryID + "']");
		return Common.checkElementDisplays(driver, unassignedOption, 5);

	}

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

//	private void syncProduct(String ProductNo) {
//		driver.get(testData.HMC.getHomePageUrl());
//		HMCCommon.Login(hmcPage, testData);
//		hmcPage.Home_CatalogLink.click();
//		hmcPage.Home_ProductsLink.click();
//		Common.sleep(5000);
//		hmcPage.Catalog_ArticleNumberTextBox.clear();
//		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(ProductNo);
//		hmcPage.Cataglog_CatalogVersionSel.click();
//		Common.waitElementClickable(driver, hmcPage.Cataglog_CatalogVersionStaged, 60);
//		hmcPage.Cataglog_CatalogVersionStaged.click();
//		hmcPage.B2BUnit_SearchButton.click();
//
//		By byLocator3 = By.id("Content/StringDisplay[" + ProductNo + "]_span");
//		Common.waitElementClickable(driver, driver.findElement(byLocator3), 60);
//		Common.doubleClick(driver, driver.findElement(byLocator3));
//
//		hmcPage.Product_SynchronizationBtn.click();
//		Dailylog.logInfoDB(0, "syn product No : " + ProductNo, Store, testName);
//
//		Common.sleep(5000);
//		switchToWindow(1);
//		hmcPage.Product_SyncTargetBtn.click();
//		if (Store.contains("AU") || Store.contains("NZ")) {
//			hmcPage.Product_SyncExeJobANZ.click();
//		} else if (Store.contains("US") || Store.contains("CA")) {
//			hmcPage.Product_SyncExeJobNA.click();
//		} else if (Store.contains("GB") || Store.contains("FR")) {
//			hmcPage.Product_SyncExeJobEMEA.click();
//		} else {
//			hmcPage.Product_SyncExeJobAP2.click();
//		}
//		hmcPage.Product_NeedRunMapping.click();
//		new WebDriverWait(driver, 30000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncStart));
//		hmcPage.Product_SyncStart.click();
//		new WebDriverWait(driver, 3000000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncFinishMsg));
//		Common.sleep(3000);
//		Dailylog.logInfoDB(0, "Product_SyncStatus: " + hmcPage.Product_SyncStatus.getText(), Store, testName);
//		Dailylog.logInfoDB(0, "Product_SyncResult : " + hmcPage.Product_SyncResult.getText(), Store, testName);
//		Assert.assertTrue(hmcPage.Product_SyncStatus.getText().contains("FINISHED"), "Product_SyncStatus should be FINISHED");
//		Assert.assertTrue(hmcPage.Product_SyncResult.getText().contains("SUCCESS"), "Product_SyncResult should be SUCCESS");
//		driver.close();
//		switchToWindow(0);
//
//		// Sign-out
//		hmcPage.hmcHome_hmcSignOut.click();
//	}

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
}
