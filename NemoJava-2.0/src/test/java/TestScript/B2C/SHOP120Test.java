package TestScript.B2C;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SHOP120Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;

	public SHOP120Test(String store) {
		this.Store = store;
		this.testName = "SHOPE-120";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void SHOP120(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			String subScription = "RR00000001";
			String region = "US";
			String channel = "B2C";
			String testProduct = testData.B2C.getDefaultMTMPN();		

			// HMC Get template type
			String templateType = "template-for-NoteBook";
			templateType = getTemplateType(testProduct);
			Dailylog.logInfoDB(0, "templateType: " + templateType, Store, testName);

			// HMC Get base product
			String mt = testProduct.substring(0,4);
			String baseProduct = "22TP2WPWP7020ER";
			baseProduct = getBaseProduct(testProduct);
			Dailylog.logInfoDB(0, "baseProduct: " + baseProduct, Store, testName);

			// HMC Get option Supercategories : identifier
			String supercategories = "Sub_SAMS01";
			supercategories = getSupercategories(subScription);
			Dailylog.logInfoDB(0, "supercategories of subScription:" + supercategories, Store, testName);

			// HMC create tempate for subscription
			String tabTitle = "Software";
			String sectionTitle = "Subscription Test";
			String groupTitle = "Subscription Test";
			subTemplate(templateType, tabTitle, sectionTitle, groupTitle, supercategories);
			Dailylog.logInfoDB(0, "template of subScription done: " + tabTitle, Store, testName);

			// check if exist in pb container
			boolean isExist = checkPBContainer(baseProduct, region, subScription, true);

			// import impex
			String filePath = "D:\\SHOP120\\";

			deleteFile(filePath);

			String contents = "$productCatalog=masterMultiCountryProductCatalog\r\n" + "$catalogVersion=catalogversion(catalog(id[default=$productCatalog]),version[default='Online'])[unique=true,default=$productCatalog:Online]\r\n" + "$OSType=OS Independent\r\n" + " \r\n" + "INSERT_UPDATE ProductReference;source(code,$catalogVersion)[unique=true];target(code,$catalogVersion)[unique=true];referenceType(code);regionCountry;active;preselected;OSType[default=$OSType]\r\n" + ";" + mt + ";" + subScription + ";SUBSCRIPTION;:;true;false;;\r\n" + "";

			createFile(filePath, contents);

			importImpex(filePath);

			deleteFile(filePath);

			String contents1 = "$catalogVersion=catalogVersion(catalog(id[default='masterMultiCountryProductCatalog']), version[default='Online'])[unique=true,default='masterMultiCountryProductCatalog:Online']\r\n" + "INSERT_UPDATE ProductBuilderContainer;$catalogVersion;product(code,catalogVersion(catalog(id),version))[unique=true];country(code)[unique=true];channel(code)[unique=true];groupItems[translator=com.nemo.b2b.core.translator.ProductOptionGroupItemsTranslator];\r\n" + ";;" + baseProduct + ":masterMultiCountryProductCatalog:Online;" + region + ";" + channel + ";" + subScription + ":false";

			createFile(filePath, contents1);

			importImpex(filePath);

			deleteFile(filePath);

			// verify exist in pb container
			isExist = checkPBContainer(baseProduct, region, subScription, true);

			Assert.assertTrue(isExist, "subscription exist in pb container");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void deleteFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file1 : files) {

				file1.delete();
			}
		}
	}

	private void createFile(String filePath, String contents) throws UnsupportedEncodingException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(filePath + "shop120.impex"));
			out.write(contents.getBytes("utf-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	private void importImpex(String filePath) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.system.click();
		hmcPage.System_tools.click();
		hmcPage.Tools_import.click();
		Common.sleep(5000);
		Common.switchToWindow(driver, 1);
		Common.waitElementClickable(driver, hmcPage.Import_importImpexIcon, 10);
		hmcPage.Import_importImpexIcon.click();
		Common.sleep(5000);
		Common.switchToWindow(driver, 2);
		Common.waitElementClickable(driver, hmcPage.ImportImpex_selectFile, 10);
		hmcPage.ImportImpex_selectFile.sendKeys(filePath + "shop120.impex");
		hmcPage.ImportImpex_uploadBtn.click();
		Common.switchToWindow(driver, 1);
		hmcPage.ImportImpex_startBtn.click();
		Common.sleep(5000);
		new WebDriverWait(driver, 3000000).until(ExpectedConditions.visibilityOf(hmcPage.ImportImpex_finishMsg));
		Common.sleep(5000);
		Dailylog.logInfoDB(0, "ImportImpex_status: " + hmcPage.ImportImpex_status.getText(), Store, testName);
		Dailylog.logInfoDB(0, "ImportImpex_result : " + hmcPage.ImportImpex_result.getText(), Store, testName);
		Assert.assertTrue(hmcPage.ImportImpex_status.getText().contains("FINISHED"), "ImportImpex_status should be FINISHED");
		Assert.assertTrue(hmcPage.ImportImpex_result.getText().contains("SUCCESS"), "ImportImpex_result should be SUCCESS");
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.HMC_Logout.click();
	}

	private boolean checkPBContainer(String baseProduct, String region, String subscription, boolean isRemoved) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online", baseProduct);
		hmcPage.Catalog_multiCountry.click();
		try {
			WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[B2C]')][contains(.,'B2C')]/../../td/div[contains(@id,'[" + region + "]')]"));
			Common.doubleClick(driver, targetProductBuilder);
		} catch (NoSuchElementException e) {
			Assert.fail("no pb container");
		}
		Common.switchToWindow(driver, 1);
		By subscriptionX = By.xpath("//td[contains(text(),'" + subscription + "')]");
		int i = 0;
		boolean isExist = false;
		if (Common.isElementExist(driver, subscriptionX))
			isExist = true;
		boolean isExist1 = isExist;
		while (isExist1 && i < 3) {
			if (isRemoved) {
				Common.doubleClick(driver, driver.findElement(subscriptionX));
				Common.switchToWindow(driver, 2);
				WebElement target = driver.findElement(By.xpath("//div[text()='" + subscription + "']"));
				target.click();
				hmcPage.ProductBuidler_deleteBtn.click();
				driver.switchTo().alert().accept();
				Common.switchToWindow(driver, 1);
				isExist1 = Common.isElementExist(driver, subscriptionX);
			}
			i++;
		}
		if (isRemoved)
			hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.hmcHome_hmcSignOut.click();
		return isExist;
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

	private String getSupercategories(String product) throws InterruptedException {
		String supercategories = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		searchProduct("online", product);
		// category system-> record Supercategories : identifier
		hmcPage.Products_categorySystem.click();
		if (Common.checkElementDisplays(driver, hmcPage.Products_firstSupercategoriesID, 10)) {
			supercategories = hmcPage.Products_firstSupercategoriesID.getText();
		} else {
			while (Common.checkElementDisplays(driver, hmcPage.Products_SupercategoriesEmpty, 10)) {
				Common.rightClick(driver, hmcPage.Products_baseProduct);
				Common.sleep(2000);
				hmcPage.Products_edit.click();
				Common.sleep(5000);
				hmcPage.Products_categorySystem.click();
				Common.sleep(5000);
				if (Common.checkElementDisplays(driver, By.xpath("//tr/td[6]/div[contains(@id,'McCategoryAttributeDisplay')]/div[text()='n/a']"), 30)) {
					supercategories = hmcPage.Products_SupercategoriesIdentifier.getText();
					break;
				}
			}
		}
		hmcPage.hmcHome_hmcSignOut.click();
		return supercategories;
	}


	private String getTemplateType(String product) throws InterruptedException {
		String templateType = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		searchProduct("online", product);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		Common.sleep(2000);
		hmcPage.Products_edit.click();
		Common.waitElementVisible(driver, hmcPage.Products_activeTemplate);
		templateType = hmcPage.Products_activeTemplate.getAttribute("value");
		hmcPage.hmcHome_hmcSignOut.click();
		return templateType;
	}

	private String getBaseProduct(String product) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
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

	private void subTemplate(String template, String tabTitle, String sectionTitle, String groupTitle, String supercategories) {
		By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
		By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + tabTitle + "]')])[1]");
		By testSectionX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + sectionTitle + "]')])[1]");
		By testGroupX = By.xpath("(//div[contains(@id,'resultlist')]//*[contains(@value,'" + groupTitle + "')])[1]");
		By testCategoriesX = By.xpath("//div[text()='" + supercategories + "']");
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		hmcPage.Home_Nemo.click();
		Common.waitElementClickable(driver, hmcPage.Nemo_productBuilder, 5);
		hmcPage.Nemo_productBuilder.click();
		Common.waitElementClickable(driver, hmcPage.Nemo_template, 5);
		hmcPage.Nemo_template.click();
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		Common.sleep(1000);
		hmcPage.Contract_searchbutton.click();
		Common.sleep(2000);

		// open target template
		WebElement targetTemplate = driver.findElement(targetTemplateX);
		targetTemplate.click();
		Common.sleep(2000);

		// open target tab
		WebElement targetTab = driver.findElement(testTabX);
		targetTab.click();
		Common.sleep(2000);
		Common.switchToWindow(driver, 1);

		// open target section
		if (!Common.isElementExist(driver, testSectionX)) {
			Common.rightClick(driver, hmcPage.Template_sectionsTable);
			hmcPage.Template_createProductOption.click();
			Common.switchToWindow(driver, 2);
			// fill in section title and tab code
			hmcPage.Template_titleInput.sendKeys(sectionTitle);
			hmcPage.Template_codeInput.sendKeys(sectionTitle);
			hmcPage.Template_saveBtn.click();
		} else {
			WebElement targetSection = driver.findElement(testSectionX);
			targetSection.click();
			Common.switchToWindow(driver, 2);
		}

		// open target group
		if (!Common.isElementExist(driver, testGroupX)) {
			Common.rightClick(driver, hmcPage.Template_groupsTable);
			hmcPage.Template_createProductOption.click();
			Common.switchToWindow(driver, 3);
			// fill in section title and tab code
			hmcPage.Template_titleInput.sendKeys(sectionTitle);
			hmcPage.Template_codeInput.sendKeys(sectionTitle);
			hmcPage.Template_saveBtn.click();
		} else {
			WebElement targetGroup = driver.findElement(testGroupX);
			Common.doubleClick(driver, targetGroup);
			Common.sleep(500);
			Common.switchToWindow(driver, 3);
		}

		// Categories
		if (!Common.isElementExist(driver, testCategoriesX)) {
			Common.rightClick(driver, hmcPage.Template_categoriesTable);
			Common.waitElementClickable(driver, hmcPage.Template_addHierarchy, 5);
			hmcPage.Template_addHierarchy.click();
			Common.switchToWindow(driver, 4);
			hmcPage.Template_identifier.sendKeys(supercategories);
			Common.sleep(500);
			Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
			hmcPage.Template_selCatalogVersionOnline.click();
			hmcPage.Template_searchBtn.click();
			hmcPage.Template_searchedHierarchy.click();
			hmcPage.Template_useBtn.click();
		}
		
		Common.switchToWindow(driver, 3);
		hmcPage.Template_saveBtn.click();
		driver.close();
		Common.switchToWindow(driver, 2);
		driver.close();
		Common.switchToWindow(driver, 1);
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.hmcHome_hmcSignOut.click();
	}

}
