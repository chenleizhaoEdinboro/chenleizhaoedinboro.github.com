package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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

public class SHOP107Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String subScription;

	public SHOP107Test(String store, String subscription) {
		this.Store = store;
		this.subScription = subscription;
		this.testName = "SHOPE-107";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void SHOP107(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			String region = testData.Store.substring(0, 2);
			String channel = "B2C";
			String ctoProduct = testData.B2C.getDefaultCTOPN();
			ctoProduct = "20KNCTO1WWENUS0";
			String country = "United States";

			String b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?";
			Dailylog.logInfoDB(0, "ctoProduct: " + ctoProduct, Store, testName);

			// HMC Get base product
			String baseProduct = "22TP2WPWP7020ER";
			baseProduct = getBaseProduct(ctoProduct);
			Dailylog.logInfoDB(0, "baseProduct: " + baseProduct, Store, testName);

			// delete subscription from PB container
			checkPBContainer(baseProduct, region, subScription, true);

			// HMC Get template type
			String templateType = "template-for-NoteBook";
			templateType = getTemplateType(ctoProduct);
			Dailylog.logInfoDB(0, "templateType: " + templateType, Store, testName);

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

			// add product to PB container
			boolean isExist = addGroupItem(subScription, baseProduct, region, channel, groupTitle);
			Assert.assertTrue(isExist, "manual assignement suscess");

			// batch unassign
			boolean batchAssignFlag = false;
			boolean catalogFlag = true;
			batchAssingAndUnassign(batchAssignFlag, catalogFlag, subScription, baseProduct, country);

			// verify exist in pb container
			isExist = checkPBContainer(baseProduct, region, subScription, false);
			Assert.assertFalse(isExist, "batch unassign success");

			// batch assign
			batchAssignFlag = true;
			catalogFlag = true;
			batchAssingAndUnassign(batchAssignFlag, catalogFlag, subScription, baseProduct, country);

			// verify exist in pb container
			isExist = checkPBContainer(baseProduct, region, subScription, false);
			Assert.assertTrue(isExist, "batch assign success");

			// verify UI on B2C site
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.login(b2cPage, "usbuyer@yopmail.com", "1q2w3e4r");
			driver.get(b2cProductUrl);
			Common.sleep(5000);
			Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 30);
			Common.javascriptClick(driver, b2cPage.Product_AddToCartBtn);
			//b2cPage.Product_AddToCartBtn.click();


			Common.sleep(1000);
			b2cPage.PB_softwareTab.click();
			Common.sleep(1000);
			By b2cSubXpath = By.xpath("//div[contains(text(),'" + sectionTitle + "')]/../..//input[@value='" + subScription + "']");
			if (!Common.checkElementDisplays(driver, b2cSubXpath, 5))
				b2cSubXpath = By.xpath("//span[@class='section-title' and text()='" + sectionTitle + "']/../following-sibling::div[1]//input[@value='" + subScription + "']/..");
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cSubXpath, 5), "is b2cSubXpath display on B2C");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
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

	// batchAssignFlag true: batch assign catalogFlag true online
	private void batchAssingAndUnassign(boolean batchAssignFlag, boolean catalogFlag, String optionID, String mt, String country) throws InterruptedException {
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
		Common.sleep(5000);
		driver.switchTo().frame(0);
		hmcPage.ProductBuilder_clickSelCatalogVersion.click();
		if (catalogFlag) {
			hmcPage.ProductBuilder_selCatalogVersionOnline.click();
			Dailylog.logInfo("...ProductBuilder_selCatalogVersionOnline...");
		} else {
			hmcPage.ProductBuilder_selCatalogVersionStaged.click();
			Dailylog.logInfo("...ProductBuilder_selCatalogVersionStaged...");
		}
		// select country
		By countryX = By.xpath("//span[text()='" + country + "']");
		Common.javascriptClick(driver, driver.findElement(countryX));
		Dailylog.logInfo("Selected Country: " + country);
		// select channel
		hmcPage.ProductBuilder_channelCode.click();
		hmcPage.ProductBuilder_channelB2C.click();
		Dailylog.logInfo("selected channel B2C");
		// search option
		hmcPage.ProductBuilder_showSearchOptionDialog.click();
		hmcPage.ProductBuilder_optionCodeInput.sendKeys(optionID);
		hmcPage.ProductBuilder_searchOptionBtn2.click();
		Common.sleep(5000);
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
				Dailylog.logInfoDB(0, "...Batch Assign Option success...", Store, testName);
			else
				Dailylog.logInfoDB(0, "...Batch UNassign Option success...", Store, testName);
		} else {
			if (batchAssignFlag)
				Dailylog.logInfoDB(0, "...Batch Assign No MT searched out...", Store, testName);
			else
				Dailylog.logInfoDB(0, "...Batch UNassign No MT searched out...", Store, testName);
		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private boolean addGroupItem(String subscription, String baseProduct, String region, String channel, String newTempalte) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online", baseProduct);
		hmcPage.Catalog_multiCountry.click();
		try {
			WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[" + channel + "]')][contains(.,'" + channel + "')]/../../td/div[contains(@id,'[" + region + "]')]"));
			Common.doubleClick(driver, targetProductBuilder);
		} catch (NoSuchElementException e) {
			Assert.fail("no pb container");
		}
		Common.switchToWindow(driver, 1);
		Common.rightClick(driver, hmcPage.products_PB_table);
		Common.waitElementClickable(driver, hmcPage.products_PB_createGroupOptionItem, 5);
		hmcPage.products_PB_createGroupOptionItem.click();
		Common.switchToWindow(driver, 2);
		Common.waitElementVisible(driver, hmcPage.products_PB_code);
		hmcPage.products_PB_code.clear();
		hmcPage.products_PB_code.sendKeys(newTempalte);
		Common.rightClick(driver, hmcPage.products_PB_optionsTable);
		Common.waitElementClickable(driver, hmcPage.products_PB_addProductOption, 5);
		hmcPage.products_PB_addProductOption.click();
		Common.switchToWindow(driver, 3);
		Common.waitElementVisible(driver, hmcPage.products_PB_code);
		hmcPage.products_PB_code.clear();
		hmcPage.products_PB_code.sendKeys(subscription);
		Select catalog = new Select(hmcPage.products_PB_catalogVersionSel);
		catalog.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		hmcPage.products_PB_searchBtn.click();
		By targetAcc = By.xpath("//div[contains(@id,'" + subscription + "')]");
		Common.waitElementClickable(driver, driver.findElement(targetAcc), 5);
		driver.findElement(targetAcc).click();
		hmcPage.products_PB_useBtn.click();
		Common.switchToWindow(driver, 2);
		hmcPage.products_PB_saveBtn.click();
		driver.close();
		Common.switchToWindow(driver, 1);
		hmcPage.products_PB_saveBtn.click();
		By subscriptionX = By.xpath("//td[contains(text(),'" + subscription + "')]");
		boolean isEixst = Common.isElementExist(driver, subscriptionX);
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.hmcHome_hmcSignOut.click();
		return isEixst;
	}

}
