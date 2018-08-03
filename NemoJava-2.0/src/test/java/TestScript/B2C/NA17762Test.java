package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17762Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public String CTOPN;
	public String MTMPN1;
	public String MTMPN2;
	public String country;
	public String originPrice;
	public String BaseProduct;
	public String activeTemplate;
	public String templateName = "AutoTestNA17762";
	public String CTOPartNumbers = "22TP2WPWP51";
	public String MTMPartNumbers = "88YG9000859";
	public int i = 0;

	public NA17762Test(String store, String country) {
		this.Store = store;
		this.testName = "NA-17762";
		this.country = country;

	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void NA17762(ITestContext ctx) throws InterruptedException {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			// Get the validate CTO and two MTM
			// Validate if the CTO product is available
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultCTOPN());
			System.out.println("DefaultCTOPN:" + testData.B2C.getDefaultCTOPN());
			if (!Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"),
					30)) {
				CTOPN = getCTONumber(CTOPartNumbers);
			} else {
				CTOPN = testData.B2C.getDefaultCTOPN();
			}
			Dailylog.logInfoDB(1, "CTO partNumber:" + CTOPN, Store, testName);

			// Validate if the MTM product is available
			int count = 0;
			MTMPN1 = getMTMNumber(MTMPartNumbers, count++);
			Dailylog.logInfoDB(2, "MTM partNumber1:" + MTMPN1, Store, testName);

			MTMPN2 = getMTMNumber(MTMPartNumbers, count++);
			Dailylog.logInfoDB(3, "MTM partNumber2:" + MTMPN2, Store, testName);

			// get base product for CTO and active template
			getBaseProduct("online", CTOPN);
			Dailylog.logInfoDB(4, "Base Product:" + BaseProduct, Store, testName);

			activeTemplate = getTemplateType(CTOPN);
			Dailylog.logInfoDB(5, "templateType:" + activeTemplate, Store, testName);

			// get category tab and super categories
			String supercategories;
			supercategories = getSupercategories(MTMPN1);
			Dailylog.logInfoDB(6, "supercategories:" + supercategories, Store, testName);

			// create Template
			createTemplate(activeTemplate, templateName, supercategories);
			Dailylog.logInfoDB(7, "createTemplate:" + templateName, Store, testName);

			addGroupItem(MTMPN1, CTOPN, CTOPN, templateName, "B2C");
			Dailylog.logInfoDB(8, "addGroupItem", Store, testName);

			// Check the MTM1
			checkMTMDisplay(CTOPN, templateName, MTMPN1);
			Dailylog.logInfoDB(9, "check MTM1 Display", Store, testName);

			// batch assign
			batchAssing(true, MTMPN2, BaseProduct, country);
			Dailylog.logInfoDB(10, "batchAssing", Store, testName);

			// Check the MTM2
			checkMTMDisplay(CTOPN, templateName, MTMPN2);
			Dailylog.logInfoDB(11, "check MTM2 Display", Store, testName);

			// create rule
			String partNumber = getMTMPriceWithoutRule(CTOPN, templateName, MTMPN1, MTMPN2);
			System.out.println(partNumber);
			if (partNumber != null) {
				createRule(hmcPage, BaseProduct.substring(0, 11), partNumber, templateName, country,
						testData.B2C.getUnit());
				// check price in cart
				String priceWithRule = checkCartPrice(CTOPN, templateName, partNumber);
				Dailylog.logInfoDB(12, "priceWithRule: " + priceWithRule, Store, testName);
				Assert.assertEquals(GetPriceValue(originPrice) - GetPriceValue(priceWithRule), 5, 0.01);
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(priority = 1, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void rollBack() {
		try {

			SetupBrowser();
			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)) {
				HMCCommon.Login(hmcPage, testData);
			}
			// delete group item
			Common.sleep(5000);
			searchProduct("online", CTOPN, hmcPage);
			Common.rightClick(driver, hmcPage.Products_baseProduct);
			hmcPage.Products_edit.click();
			Common.waitElementClickable(driver, hmcPage.Catalog_multiCountry, 5);
			hmcPage.Catalog_multiCountry.click();
			WebElement targetProductBuilder = driver
					.findElement(By.xpath("//div[contains(@id,'ItemDisplay')][contains(.,'" + "B2C"
							+ "')]/../../td[contains(.,'" + Store + "')]"));
			Common.doubleClick(driver, targetProductBuilder);
			switchToWindow(1);

			By targetItem1 = By.xpath("//tr/td/div[contains(@id,'" + templateName + "')]");
			if (Common.checkElementDisplays(driver, targetItem1, 20)) {
				Dailylog.logInfoDB(0, "group item already exist,  need to delete", Store, testName);
				Common.rightClick(driver, driver.findElement(targetItem1));
				hmcPage.HMC_RemoveResult.click();
				Common.waitAlertPresent(driver, 30);
				driver.switchTo().alert().accept();
				hmcPage.products_PB_saveBtn.click();
				driver.close();
				switchToWindow(0);
				hmcPage.products_PB_saveBtn.click();
			}

			// delete template
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
			By targetTemplateX = By.xpath("(//a[contains(.,'" + activeTemplate + "')])[1]");
			WebElement targetTemplate = driver.findElement(targetTemplateX);
			targetTemplate.click();
			Common.sleep(2000);
			delteTemplate(templateName, hmcPage);

			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			Common.sleep(3000);
			HMCCommon.deleteRule(driver, hmcPage, "System Discounts", templateName);
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String checkCartPrice(String product, String tab, String mtm) throws InterruptedException {

		String xpath = "//li/a/span[text()='" + tab + "']";
		driver.get(testData.B2C.getHomePageUrl() + "/p/" + product);
		b2cPage.Product_viewModel.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
		Thread.sleep(5000);
		if (Common.checkElementDisplays(driver, b2cPage.B2C_PDP_AddToCart, 5)) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.B2C_PDP_AddToCart);
			driver.findElement(By.xpath(xpath)).click();
			xpath = "//input[@value='" + mtm + "']/..";
			driver.findElement(By.xpath(xpath)).click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);

		} else {
			b2cPage.Product_AddToCartBtn.click();
			driver.findElement(By.xpath(xpath)).click();
			xpath = "//input[@value='" + mtm + "']";
			driver.findElement(By.xpath(xpath)).click();
			while (Common.checkElementDisplays(driver, b2cPage.Product_Continue, 5)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_Continue);
			}
		}

		xpath = "//div[@data-p-code='" + mtm + "']/dd";
		return driver.findElement(By.xpath(xpath)).getText();
	}

	private String getMTMPriceWithoutRule(String product, String tab, String mtm1, String mtm2)
			throws InterruptedException {
		String partNumber;
		String xpath = "//li/a/span[text()='" + tab + "']";
		driver.get(testData.B2C.getHomePageUrl() + "/p/" + product);
		b2cPage.Product_viewModel.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
		Thread.sleep(5000);
		if (Common.checkElementDisplays(driver, b2cPage.Product_AddToCartBtn, 5)) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.B2C_PDP_AddToCart);
			driver.findElement(By.xpath(xpath)).click();
			xpath = "(//div[contains(@class,'" + tab.toLowerCase()
					+ "')]/div//div/../input/../div/p[@class='del-price product-builder-hide']/del)[1]";
			if (Common.isElementExist(driver, By.xpath(xpath), 5)) {
				originPrice = driver.findElement(By.xpath(xpath)).getAttribute("textContent");
				Dailylog.logInfoDB(0, "originPrice: " + originPrice, Store, testName);
				xpath = "(//div[contains(@class,'" + tab.toLowerCase()
						+ "')]/div//div/../input/../div/p[@class='del-price product-builder-hide']/del/../../../input)[1]";
				partNumber = driver.findElement(By.xpath(xpath)).getAttribute("value");
			} else {
				Dailylog.logInfoDB(0, "Two products all have rules", Store, testName);
				return null;
			}

		} else {
			// old UI
			b2cPage.Product_AddToCartBtn.click();
			driver.findElement(By.xpath(xpath)).click();
			xpath = "(//header[@id='" + tab
					+ "']/..//div[contains(@class,'expandableMenu-list')]/div/ul/li/div/del[contains(@class,'option-origin-price product-builder-hide')])[1]";
			if (Common.checkElementDisplays(driver, By.xpath(xpath), 5)) {
				originPrice = driver.findElement(By.xpath(xpath)).getText();
				xpath = "(//header[@id='" + tab
						+ "']/..//div[contains(@class,'expandableMenu-list')]/div/ul/li/div/del[contains(@class,'option-origin-price product-builder-hide')])[1]/../../input";
				partNumber = driver.findElement(By.xpath(xpath)).getAttribute("value");
			} else {
				Dailylog.logInfoDB(0, "Two products all have rules", Store, testName);
				return null;
			}
		}
		return partNumber;

	}

	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1,
			String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);
	}

	public void createRule(HMCPage hmcPage, String baseproduct, String mtm, String ruleName, String country,
			String unit) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;

		driver.get(testData.HMC.getHomePageUrl());
		if (Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)) {
			HMCCommon.Login(hmcPage, testData);
		}
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
		hmcPage.PricingCockpit_B2CPriceRules.click();
		System.out.println("Create rules***********************");
		Thread.sleep(500);
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_CreateNewGroup, 120);
		Common.javascriptClick(driver, hmcPage.B2CPriceRules_CreateNewGroup);
		// hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_SystemDiscountOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// Web price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.click();

		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}

		// Country
		xpath = "//span[text()='" + country + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", country, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// Material
		xpath = "//span[contains(text(),'" + mtm + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", mtm, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// testBaseProduct
		xpath = "//span[contains(text(),'" + baseproduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Triggers", baseproduct, hmcPage.B2CPriceRules_TriggersSelect, xpath);
		System.out.println("Input Triggers");

		// B2Cunit
		String unitText = "US web store unit";
		xpath = "//ul[@class='content was-hidden']//a[text()='" + unitText + "']";

		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(unit);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + unit);
		Thread.sleep(5000);

		// price Value
		hmcPage.B2CPriceRules_dynamicRate.click();
		hmcPage.B2CPriceRules_DynamicMinusButton.click();
		hmcPage.B2CPriceRules_ecouponDollorButton.click();
		hmcPage.B2CPriceRules_dynamicValue.clear();
		hmcPage.B2CPriceRules_dynamicValue.sendKeys("5");
		Thread.sleep(2000);

		// Add Price Rule To Group
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver,
				By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					hmcPage.B2CPriceRules_AddPriceRuleToGroup);
			System.out.println("click Add Price Rule To Group --- second time");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
			System.out.println("click Create New Group --- second time");
		}
		Thread.sleep(10000);
		// Record Price Rule ID
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CloseBtn);
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
		// hmcPage.B2CPriceRules_FilterBtn.click();
		System.out.println("Clicked filter!");
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();
		hmcPage.hmcHome_hmcSignOut.click();

	}

	private void batchAssing(Boolean catalogFlag, String mtmID, String mt, String country) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.ProductBuilder_batchAssignMTM.click();
		Dailylog.logInfo("...Batch Assign Option...");

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
		hmcPage.ProductBuilder_optionCodeInput.sendKeys(mtmID);
		hmcPage.ProductBuilder_searchOptionBtn2.click();
		Common.sleep(5000);
		By targetOptionX = By.xpath(".//*[@id='optionTable']//*[text()='" + mtmID + "']");
		Common.waitElementClickable(driver, driver.findElement(targetOptionX), 30);
		driver.findElement(targetOptionX).click();
		hmcPage.ProductBuilder_selectOptionBtn.click();

		// Select Machine Type
		try {
			Common.sleep(8000);
			Common.waitElementVisible(driver, hmcPage.ProductBuilder_mtList);
		} catch (TimeoutException e) {
			hmcPage.ProductBuilder_showSearchOptionDialog.click();
			hmcPage.ProductBuilder_optionCodeInput.sendKeys(mtmID);
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
			List<WebElement> otherTemplate = driver.findElements(By.xpath("//input[@name='groupCode' and not(contains(@value,'17762'))]"));
			for(WebElement ele:otherTemplate) {
				ele.click();
			}
			hmcPage.ProductBuilder_submit.click();
			Common.waitAlertPresent(driver, 30);
			driver.switchTo().alert().accept();
			Dailylog.logInfoDB(0, "...Batch UNassign Option success...", Store, testName);
		} else {
			Dailylog.logInfoDB(0, "...Batch UNassign No MT searched out...", Store, testName);
		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void checkMTMDisplay(String product, String tab, String mtm) throws InterruptedException {
		String xpath;
		driver.get(testData.B2C.getHomePageUrl() + "/p/" + product);
		b2cPage.Product_viewModel.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
		Thread.sleep(10000);
		Common.waitElementVisible(driver, b2cPage.PB_accessoriesTab);
		if (Common.checkElementDisplays(driver, b2cPage.B2C_PDP_AddToCart, 5)) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.B2C_PDP_AddToCart);
			xpath = "//li/a/span[text()='" + tab + "']";
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath(xpath), 5));
			xpath = "//div[contains(@class,'" + tab.toLowerCase() + "')]//div[contains(@class,'expandableHeading')]";
			if (!driver.findElement(By.xpath(xpath)).getAttribute("class").contains("expandableHeading-is-expanded")) {
				driver.findElement(By.xpath(xpath)).click();
			}
			xpath = "//div[contains(@class,'" + tab.toLowerCase() + "')]/div//div/../input[@value='" + mtm + "']";
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(xpath), 10));
		} else {
			// old UI
			b2cPage.Product_AddToCartBtn.click();
			xpath = "//li/a/span[text()='" + tab + "']";
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath(xpath), 5));
			xpath = "//li[contains(@class,'configuratorItem-optionList-option')]/input[@value='" + mtm + "']";
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath(xpath), 5));
		}

	}

	private void addGroupItem(String MTM1, String CTO, String productID, String newTempalte, String channel)
			throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online", productID, hmcPage);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		hmcPage.Products_edit.click();
		Common.waitElementClickable(driver, hmcPage.Catalog_multiCountry, 5);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay')][contains(.,'"
				+ channel + "')]/../../td[contains(.,'" + Store + "')]"));
		Common.doubleClick(driver, targetProductBuilder);
		switchToWindow(1);

		By targetItem1 = By.xpath("//tr/td/div[contains(@id,'" + newTempalte
				+ "')]/../../td/div[contains(@id,'ProductOptionsDisplay')]//tr/td[contains(text(),'" + MTM1 + "')]");
		if (Common.checkElementDisplays(driver, targetItem1, 20)) {
			Dailylog.logInfoDB(0, "group item already exist, no need to add", Store, testName);
		} else {
			By targetItem2 = By.xpath("//div[contains(@id,'" + newTempalte + "')]");
			if (Common.checkElementDisplays(driver, targetItem2, 5)) {
				Common.rightClick(driver, driver.findElement(targetItem2));
				Common.waitElementClickable(driver, hmcPage.products_PB_editInNewWindow, 5);
				hmcPage.products_PB_editInNewWindow.click();
			} else {
				Common.rightClick(driver, hmcPage.products_PB_table);
				Common.waitElementClickable(driver, hmcPage.products_PB_createGroupOptionItem, 5);
				hmcPage.products_PB_createGroupOptionItem.click();
			}
			switchToWindow(2);
			Common.waitElementVisible(driver, hmcPage.products_PB_code);
			hmcPage.products_PB_code.clear();
			hmcPage.products_PB_code.sendKeys(newTempalte);

			// Validate error message
			validateMessage(hmcPage, MTM1, hmcPage.products_PB_optionsTable, hmcPage.products_PB_addProductOption,
					hmcPage.products_PB_catalogVersionSel);
			validateMessage(hmcPage, CTO, hmcPage.products_PB_mtmsTable, hmcPage.products_PB_addMTMOption,
					hmcPage.products_MTM_catalogVersionSel);

			// Add MTM
			addMTM(hmcPage, MTM1);

			driver.close();
			switchToWindow(1);
			hmcPage.products_PB_saveBtn.click();
		}
		driver.close();
		switchToWindow(0);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void validateMessage(HMCPage hmcPage, String product, WebElement table, WebElement option,
			WebElement version) throws InterruptedException {
		Common.rightClick(driver, table);
		Common.waitElementClickable(driver, option, 5);
		option.click();
		switchToWindow(3);
		Common.waitElementVisible(driver, hmcPage.products_PB_code);
		hmcPage.products_PB_code.clear();
		hmcPage.products_PB_code.sendKeys(product);
		Select catalog = new Select(version);
		catalog.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		hmcPage.products_PB_searchBtn.click();
		By targetAcc = By.xpath("//div[contains(@id,'" + product + "')]");
		Assert.assertFalse(Common.checkElementDisplays(driver, targetAcc, 5));
		driver.close();
		switchToWindow(2);
	}

	private void addMTM(HMCPage hmcPage, String MTM) throws InterruptedException {
		Common.rightClick(driver, hmcPage.products_PB_mtmsTable);
		Common.waitElementClickable(driver, hmcPage.products_PB_addMTMOption, 5);
		hmcPage.products_PB_addMTMOption.click();
		switchToWindow(3);
		Common.waitElementVisible(driver, hmcPage.products_PB_code);
		hmcPage.products_PB_code.clear();
		hmcPage.products_PB_code.sendKeys(MTM);
		Select catalog = new Select(hmcPage.products_MTM_catalogVersionSel);
		catalog.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		hmcPage.products_PB_searchBtn.click();
		By targetAcc = By.xpath("//div[contains(@id,'" + MTM + "')]");
		Common.waitElementClickable(driver, driver.findElement(targetAcc), 5);
		driver.findElement(targetAcc).click();
		hmcPage.products_PB_useBtn.click();
		switchToWindow(2);
		hmcPage.products_PB_saveBtn.click();
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

	private void delteTemplate(String tabTitle, HMCPage hmcPage) {
		By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + tabTitle + "]')])[1]");
		WebElement testTabE = driver.findElement(testTabX);
		Common.rightClick(driver, testTabE);
		hmcPage.B2CLeasing_remove.click();
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		hmcPage.Catalog_save.click();
	}

	private void createTemplate(String template, String templateName, String supercategories)
			throws InterruptedException {
		By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
		By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + templateName + "]')])[1]");
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
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

		// if the test tab exists, delete it
		if (Common.isElementExist(driver, testTabX))
			delteTemplate(templateName, hmcPage);
		// Create Product Option Tab
		Common.rightClick(driver, hmcPage.Template_tabTitle);
		Common.waitElementClickable(driver, hmcPage.Template_createProductOption, 5);
		hmcPage.Template_createProductOption.click();
		switchToWindow(1);

		// fill in tab title and tab code
		hmcPage.Template_titleInput.sendKeys(templateName);
		hmcPage.Template_codeInput.sendKeys(templateName);

		// Create Product Option Section
		Common.rightClick(driver, hmcPage.Template_sectionsTable);
		hmcPage.Template_createProductOption.click();
		switchToWindow(2);

		// fill in section title and tab code
		hmcPage.Template_titleInput.sendKeys(templateName);
		hmcPage.Template_codeInput.sendKeys(templateName);

		// MTM Flag
		hmcPage.PBTemplate_MTMFlag.click();

		// Create Product Option Group
		Common.rightClick(driver, hmcPage.Template_groupsTable);
		hmcPage.Template_createProductOption.click();
		switchToWindow(3);

		// fill in group title and tab code
		hmcPage.Template_titleInput.sendKeys(templateName);
		hmcPage.Template_codeInput.sendKeys(templateName);

		// Add [Hierarchy]
		Common.rightClick(driver, hmcPage.Template_categoriesTable);
		Common.waitElementClickable(driver, hmcPage.Template_addHierarchy, 5);
		hmcPage.Template_addHierarchy.click();
		switchToWindow(4);
		hmcPage.Template_identifier.sendKeys(supercategories);
		Common.sleep(500);
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		hmcPage.Template_searchBtn.click();
		hmcPage.Template_searchedHierarchy.click();
		hmcPage.Template_useBtn.click();

		// save
		switchToWindow(3);
		hmcPage.Template_saveBtn.click();
		driver.close();
		switchToWindow(2);
		hmcPage.Template_saveBtn.click();
		driver.close();
		switchToWindow(1);
		hmcPage.Template_saveBtn.click();
		driver.close();
		switchToWindow(0);

		// verify template is created successfully
		hmcPage.Template_reloadBtn.click();
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		Assert.assertTrue(Common.checkElementDisplays(driver, testTabX, 5), "tab create failure 2");

		// Sign-out
		hmcPage.hmcHome_hmcSignOut.click();

	}

	private String getSupercategories(String product) throws InterruptedException {
		String supercategories = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		searchProduct("online", product, hmcPage);
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
				if (Common.checkElementDisplays(driver,
						By.xpath("//tr/td[6]/div[contains(@id,'McCategoryAttributeDisplay')]/div[text()='n/a']"), 30)) {
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
		searchProduct("online", product, hmcPage);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		Common.sleep(2000);
		hmcPage.Products_edit.click();
		Common.waitElementVisible(driver, hmcPage.Products_activeTemplate);
		templateType = hmcPage.Products_activeTemplate.getAttribute("value");
		hmcPage.hmcHome_hmcSignOut.click();
		return templateType;
	}

	private void getBaseProduct(String category, String ProductID) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(category, ProductID, hmcPage);
		Common.waitElementVisible(driver, hmcPage.Products_baseProduct);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		BaseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void searchProduct(String category, String productID, HMCPage hmcPage) {
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

	public String partNumber(String str) {

		str = str.trim();
		String pattern = "\\d+.*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		if (m.find()) {
			return m.group();
		} else {
			return null;
		}
	}

	public String getCTONumber(String CTONumbers) {

		driver.get(testData.B2C.getHomePageUrl() + "/p/" + CTONumbers);
		if (Common.checkElementDisplays(driver,
				By.xpath("//form[contains(@id,'addToCartFormTop') and contains(@id,'CTO')]"), 50)) {
			return partNumber(
					driver.findElements(By.xpath("//form[contains(@id,'addToCartFormTop') and contains(@id,'CTO')]"))
							.get(0).getAttribute("id"));
		} else {
			Assert.fail("Have no CTO products");
			return null;
		}
	}

	public String getMTMNumber(String MTMNumbers, int count) {
		driver.get(testData.B2C.getHomePageUrl() + "/p/" + MTMNumbers);
		if (Common.checkElementDisplays(driver, By.xpath("//h3/div[@class='partNumber']"), 50)) {
			return partNumber(driver.findElements(By.xpath("//h3/div[@class='partNumber']")).get(count).getText());
		} else 		if (Common.checkElementDisplays(driver, By.xpath("//span[@class='colorSelector__pn is-active'and text()]"), 50)) {
			return partNumber(driver.findElements(By.xpath("//span[@class='colorSelector__pn is-active'and text()]")).get(count).getText());
		}
		else {
			Assert.fail("Have no MTM products");
			return null;
		}
		
	}

	public float GetPriceValue(String Price) {
		if (Price.contains("FREE") || Price.contains("INCLUDED")) {
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("SG", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		String pattern = "\\d+\\.*\\d*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(Price);
		float priceValue;
		if (m.find()) {
			priceValue = Float.parseFloat(m.group());
			return priceValue;
		} else {
			return 0;
		}
	}
}
