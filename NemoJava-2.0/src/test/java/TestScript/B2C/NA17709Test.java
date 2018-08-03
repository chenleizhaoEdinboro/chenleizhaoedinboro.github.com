package TestScript.B2C;

import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

import java.util.ArrayList;
import java.util.List;

public class NA17709Test extends SuperTestClass {
	public B2CPage b2cPage;
	public CTOPage ctoPage;
	public HMCPage hmcPage;
	private String Country;
	private String ctoProduct;

	public NA17709Test(String store, String ctoProduct, String country) {
		this.Store = store;
		this.Country = country;
		this.testName = "NA-17709";
		this.ctoProduct = ctoProduct;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void NA17709(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			ctoPage = new CTOPage(driver);
			hmcPage = new HMCPage(driver);

			// B2C Get valid CTO product
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			String optionID = "";

			driver.get(testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?");
			if (isAlertPresent())
				driver.switchTo().alert().accept();
			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 5))
				Assert.fail("CTO_addToCart does not exist, please update test product");
			Dailylog.logInfoDB(0, "ctoProduct: " + ctoProduct, Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			Common.sleep(5000);

			// B2C Get valid accessory
			optionID = findAccessory();
			Dailylog.logInfoDB(0, "optionID: " + optionID, Store, testName);

			// HMC Get template type
			String templateType;
			templateType = getTemplateType(ctoProduct);
			Dailylog.logInfoDB(0, "templateType: " + templateType, Store, testName);

			// HMC Get option Supercategories : identifier
			String optionSupercategories;
			optionSupercategories = getSupercategories(optionID);
			Dailylog.logInfoDB(0, "optionSupercategories: " + optionSupercategories, Store, testName);

			// HMC Get base product
			String baseProduct = getBaseProduct(ctoProduct);
			Dailylog.logInfoDB(0, "baseProduct: " + baseProduct, Store, testName);

			// HMC Edit template
			String tabTitle = "testTab_NA17709" + Store;
			String tabCode = "testTab_NA17709" + Store;
			String sectionTitle = "testSection_NA17709" + Store;
			String sectionCode = "testSection_NA17709" + Store;
			String groupTitle = "testGroup_NA17709" + Store;
			String groupCode = "testGroup_NA17709" + Store;
			editTemplate(templateType, optionSupercategories, tabTitle, tabCode, sectionTitle, sectionCode, groupTitle, groupCode);

			// CTO Publish the template in CTO Engine
			// Open CTO engine
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(3000);
			CTOCommon.Login(ctoPage, testData);
			Common.sleep(2000);
			if (isAlertPresent())
				driver.switchTo().alert().accept();

			// go to add options section of modal
			// http://pre-c-hybris.lenovo.com/configurator_hana/#ca/nomenclature=22TP2TEE47020H120H1CTO1WW########
			String modalUrl = testData.CTO.getHomePageUrl() + "ca/nomenclature=" + baseProduct + ctoProduct.substring(0, 10) + "########";
			Dailylog.logInfoDB(0, "modalUrl: " + modalUrl, Store, testName);
			// wait for modal page loading
			navigateToAndRetry(modalUrl);
			// driver.get(modalUrl);
			Common.sleep(10000);
			if (isAlertPresent())
				driver.switchTo().alert().accept();
			// Remove the template if already exist
			By newTemplateX2 = By.xpath("//span[text()='" + groupTitle + "']");
			if (Common.checkElementDisplays(driver, newTemplateX2, 5)) {
				WebElement open = driver.findElement(By.xpath("//tr[@groupid='DUM_OPT_" + groupTitle + "']/td[contains(@class,'char-open')]"));
				Common.javascriptClick(driver, open);
				WebElement delete = driver.findElement(By.xpath("//button[@groupid='DUM_OPT_" + groupTitle + "' and @class='btn btn-primary delete-opt-section']"));
				Common.javascriptClick(driver, delete);
				Common.sleep(5000);
				driver.findElement(By.xpath("//button[text()='OK']")).click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_save);
				System.out.println("Clicked save button");
				try {
					Common.waitElementVisible(driver, ctoPage.ModalPage_saveMsg);
					String saveMsg = ctoPage.ModalPage_saveMsg.getText();
					System.out.print("saveMsg: " + saveMsg);
					Assert.assertTrue(saveMsg.indexOf("Configuration updated successfully") >= 0);
				} catch (StaleElementReferenceException e) {
					Common.sleep(6000);
					Dailylog.logInfoDB(0, "product sync StaleElementReferenceException", Store, testName);
				} catch (NoSuchElementException e) {
					Common.sleep(6000);
					Dailylog.logInfoDB(0, "product sync NoSuchElementException", Store, testName);
				}
				Dailylog.logInfoDB(0, "Deleted existed group", Store, testName);
				Common.sleep(6000);
			}
			Common.waitElementClickable(driver, ctoPage.ModalPage_addOptionsBtn, 5);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ctoPage.ModalPage_addOptionsBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_addOptionsBtn);
			System.out.println("Clicked Add Options");
			Common.waitElementClickable(driver, ctoPage.ModalPage_selectOptions, 15);
			ctoPage.ModalPage_selectOptions.click();
			// verify the new created template is displayed in select options list
			By newTemplateX = By.xpath("//*[@class='dropdown-menu open']//span[text()='" + groupTitle + "']");
			Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX, 5), "newTemplate should display in select options list");
			// select the new created template
			Common.waitElementClickable(driver, driver.findElement(newTemplateX), 5);
			driver.findElement(newTemplateX).click();
			ctoPage.ModalPage_addOptionsOK.click();
			System.out.println("Added new template");
			// verify the new created template is displayed modal
			Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX2, 5), "newTemplate should display in modal");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_save);
			System.out.println("Clicked save button");
			try {
				Common.waitElementVisible(driver, ctoPage.ModalPage_saveMsg);
				String saveMsg = ctoPage.ModalPage_saveMsg.getText();
				System.out.print("saveMsg: " + saveMsg);
				Assert.assertTrue(saveMsg.indexOf("Configuration updated successfully") >= 0);
			} catch (StaleElementReferenceException e) {
				Common.sleep(6000);
				Dailylog.logInfoDB(0, "product sync StaleElementReferenceException", Store, testName);
			} catch (NoSuchElementException e) {
				Common.sleep(6000);
				Dailylog.logInfoDB(0, "product sync NoSuchElementException", Store, testName);
			} catch (TimeoutException e) {
				Common.sleep(6000);
				Dailylog.logInfoDB(0, "product sync TimeoutException", Store, testName);
			}
			Common.sleep(6000);
			// switch to bundle
			String bundleUrl = testData.CTO.getHomePageUrl() + "ca/nomenclature=" + baseProduct + ctoProduct + "~~~";
			navigateToAndRetry(bundleUrl);
			// driver.get(bundleUrl);
			Dailylog.logInfoDB(0, "bundleUrl: " + bundleUrl, Store, testName);
			Common.sleep(6000);
			// close INVALID CONFIGURATION
			if (isAlertPresent())
				driver.switchTo().alert().accept();
			By invalidModalX = By.xpath("//*[@id='invalid-modal']//button[@class='close']");
			if (Common.checkElementDisplays(driver, invalidModalX, 3)) {
				Common.javascriptClick(driver, ctoPage.BundlePage_closeInvalidModal);
				// ctoPage.BundlePage_closeInvalidModal.click();
			}
			// Approve rule
			Common.javascriptClick(driver, ctoPage.BundlePage_approveRulesBtn);
			// ctoPage.BundlePage_approveRulesBtn.click();
			try {
				Common.waitElementVisible(driver, ctoPage.BundlePage_rulesPanel);
			} catch (TimeoutException e) {
				Common.javascriptClick(driver, ctoPage.BundlePage_approveRulesBtn);
				Common.waitElementVisible(driver, ctoPage.BundlePage_rulesPanel);
			}

			Common.sleep(12000);
			By newTemplateAX = By.xpath("//input[@targetid='DUM_OPT_" + groupTitle + "']");
			if (Common.isElementExist(driver, newTemplateAX, 5)) {
				Common.scrollToElement(driver, driver.findElement(newTemplateAX));
				Common.javascriptClick(driver, driver.findElement(newTemplateAX));
				Common.javascriptClick(driver, ctoPage.BundlePage_approvebtn);
				Common.waitElementVisible(driver, ctoPage.BundlePage_dialogMsg);
				Assert.assertEquals(ctoPage.BundlePage_dialogMsg.getText(), "Selected rules have been approved successfully");
				ctoPage.BundlePage_okOnDialog.click();
				Dailylog.logInfoDB(0, "Approved new option in bundle!", Store, testName);
			} else {
				Dailylog.logInfoDB(0, "The option is not displayed while approing new option in bundle!", Store, testName);
				// Assert.fail("The option is not displayed while approing new option in
				// bundle!");
			}

			// verify the new created template is displayed bundle
			Common.sleep(12000);
			By newTemplateX3 = By.xpath("//span[text()='" + groupTitle + "']");
			Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX3, 30), "newTemplate should display in bundle");
			// select correct unit under audience
			for (int i = 0; i < 3; i++) {
				try {
					Common.waitElementClickable(driver, ctoPage.BundlePage_B2CAudience, 10);
					break;
				} catch (Exception e) {
					Dailylog.logInfoDB(0, "audience is not displayed, refresh and try again", Store, testName);
					if (i == 2)
						Assert.fail("audience is not displayed!!!");
					driver.navigate().refresh();
				}
			}
			if (!ctoPage.BundlePage_B2CAudience.isSelected()) {
				ctoPage.BundlePage_B2CAudience.click();
				Dailylog.logInfoDB(0, "Selected audience -> public", Store, testName);
			}
			if (ctoPage.BundlePage_B2BAudience.isSelected()) {
				ctoPage.BundlePage_B2BAudience.click();
				Dailylog.logInfoDB(0, "Cancel selection of audience -> Large enterprise", Store, testName);
			}
			// publish the template
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_Save);
			// ctoPage.BundlePage_Save.click();
			Thread.sleep(5000);
			Common.scrollToElement(driver, ctoPage.BundlePage_Nomenclature);
			Common.javascriptClick(driver, ctoPage.BundlePage_Publish);
			// ctoPage.BundlePage_Publish.click();
			Thread.sleep(1000);
			Common.javascriptClick(driver, ctoPage.BundlePage_PublishConfirm);
			// ctoPage.BundlePage_PublishConfirm.click();
			Dailylog.logInfoDB(0, "BundlePage_PublishConfirm", Store, testName);
			try {
				new WebDriverWait(driver, 1800).until(ExpectedConditions.visibilityOf(ctoPage.BundlePage_PublishSuccMsg));
				Dailylog.logInfoDB(0, "BundlePage_PublishConfirm success", Store, testName);
			} catch (Exception e) {
				Dailylog.logInfoDB(0, "BundlePage_PublishConfirm failure", Store, testName);
				// assert false;
			}

			// HMC Sync product in HMC
			syncProduct(ctoProduct);

			// HMC Batch Unassign
			batchAssingAndUnassign(false, true, optionID, baseProduct, Country, groupTitle);

			// HMC Batch Assign
			batchAssingAndUnassign(true, true, optionID, baseProduct, Country, groupTitle);

			// Check the new option and price in B2C
			driver.manage().deleteAllCookies();

			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// driver.get(testData.B2C.getHomePageUrl() + "/cart");
			// B2CCommon.clearTheCart(driver, b2cPage, testData);
			String testUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?";
			driver.get(testUrl);

			// verify option display
			boolean isNewUI = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_newconfiguratorHeader)));
			Common.sleep(6000);
			By newOptionX = By.id("list_group_DUM_OPT_" + groupTitle);
			Assert.assertTrue(Common.isElementExist(driver, newOptionX), "option is not displayed on b2c: " + testUrl);

			// // Verify Price with option selected
			if (Common.checkElementDisplays(driver, By.xpath("//span[contains(@group-id,'" + groupTitle + "')]/../../..//button[contains(@class,'sectionExpandButton')]"), 5)) {
				WebElement changeBtn = driver.findElement(By.xpath("//span[contains(@group-id,'" + groupTitle + "')]/../../..//button[contains(@class,'sectionExpandButton')]"));
				Common.javascriptClick(driver, changeBtn);
				// changeBtn.click();
				System.out.println("clicked change");
				Common.sleep(5000);
			}
			WebElement optionInput = driver.findElement(By.xpath(".//tbody[@id='DUM_OPT_" + groupTitle + "_s_" + optionID + "']|//label[@for='DUM_OPT_" + groupTitle + "_s_" + optionID + "']"));
			System.out.println(".//tbody[@id='DUM_OPT_" + groupTitle + "_s_" + optionID + "']|//label[@for='DUM_OPT_" + groupTitle + "_s_" + optionID + "']");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionInput);
			Dailylog.logInfoDB(0, "selected option", Store, testName);

			// Add to cart
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton1);
			} catch (StaleElementReferenceException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton1);
			}
			Common.sleep(5000);
			Common.scrollToElement(driver, b2cPage.PB_unVisitedTab);
			b2cPage.PB_unVisitedTab.click();
			// 12.Click Add to Cart Button
			if (isNewUI)
				b2cPage.PDP_AddToCartButton1.click();
			else
				b2cPage.Product_Productbuilder_AddToCartBtn.click();

			// Dailylog.logInfoDB(0, "actal price in cart: " + cartPrice, Store, testName);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//dd[text()='" + optionID + "']"), 5));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void navigateToAndRetry(String url) {
		try {
			driver.get(url);
		} catch (TimeoutException exception) {
			System.out.println("driver.get timeout");
			driver.navigate().refresh();
		}
	}

	public String getCTOProductNumber() throws InterruptedException {
		String url = driver.getCurrentUrl();
		System.out.println("b2cProductUrl is :" + url);
		String ProductID = url.substring(url.lastIndexOf("p/") + 2, url.lastIndexOf("p/") + 17);
		System.out.println(ProductID);
		return ProductID;
	}

	public String findAccessory() {
		String accessoriesText;
		String optionID;
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
			// b2cPage.newPDP_firstAccessories.click();
			Common.sleep(3000);
			optionID = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];
			if (optionID.substring(optionID.length() - 2).equals(Store.substring(0, 2))) {
				System.out.println("mtm found instead of acc");
				driver.findElement(By.xpath("//i[contains(@class,'close-modal')]")).click();
				accessoriesText = b2cPage.newPDP_firstAccessories.getText();
				Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
				Common.javascriptClick(driver, b2cPage.newPDP_firstAccOfSecSection);
				Common.sleep(3000);
				optionID = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];
			}
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
			optionID = driver.findElement(By.xpath("(//div[@class='lnvmodal-tail']//span)[2]")).getText();
		}
		return optionID;
	}

	public void clickContinue_1() {
		List<WebElement> list = driver.findElements(By.xpath("//*[@id='product-builder-form']//ol[@class='stepsItem']/li"));
		int count = list.size() - 3;
		for (int x = 1; x <= count; x++) {
			int y = x + 1;
			String tab_name = driver.findElement(By.xpath("(//*[@id='product-builder-form']//ol[@class='stepsItem']/li/a/span)[" + y + "]")).getText().toString();
			if ((tab_name.equals("Accessories")) || (tab_name.equals("周辺機器")) || (tab_name.equals("配件"))) {
				break;
			}
			driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")).click();
		}
	}

	private String getTemplateType(String product) throws InterruptedException {
		String templateType = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		searchProduct(product);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		Common.sleep(2000);
		hmcPage.Products_edit.click();
		Common.waitElementVisible(driver, hmcPage.Products_activeTemplate);
		templateType = hmcPage.Products_activeTemplate.getAttribute("value");
		hmcPage.hmcHome_hmcSignOut.click();
		return templateType;
	}

	private String getSupercategories(String product) throws InterruptedException {
		String supercategories = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		searchProduct(product);
		// category system-> record Supercategories : identifier
		hmcPage.Products_categorySystem.click();
		supercategories = hmcPage.Products_firstSupercategoriesID.getText();
		hmcPage.hmcHome_hmcSignOut.click();
		return supercategories;
	}

	private void searchProduct(String product) {
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
		try {
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			Common.sleep(2000);
		}
	}

	private void editTemplate(String template, String optionSupercategories, String tabTitle, String tabCode, String sectionTitle, String sectionCode, String groupTitle, String groupCode) {
		By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
		By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + tabTitle + "]')])[1]");
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

		// if the test tab exists, delete it
		if (Common.isElementExist(driver, testTabX))
			delteTemplate(tabTitle);

		// Create Product Option Tab
		Common.rightClick(driver, hmcPage.Template_tabTitle);
		Common.waitElementClickable(driver, hmcPage.Template_createProductOption, 5);
		hmcPage.Template_createProductOption.click();
		switchToWindow(1);

		// fill in tab title and tab code
		hmcPage.Template_titleInput.sendKeys(tabTitle);
		hmcPage.Template_codeInput.sendKeys(tabCode);

		// select cto flag
		hmcPage.Template_ctoFlagCheckbox.click();

		// Create Product Option Section
		Common.rightClick(driver, hmcPage.Template_sectionsTable);
		hmcPage.Template_createProductOption.click();
		switchToWindow(2);

		// fill in section title and tab code
		hmcPage.Template_titleInput.sendKeys(sectionTitle);
		hmcPage.Template_codeInput.sendKeys(sectionCode);

		// Create Product Option Group
		Common.rightClick(driver, hmcPage.Template_groupsTable);
		hmcPage.Template_createProductOption.click();
		switchToWindow(3);

		// fill in group title and tab code
		hmcPage.Template_titleInput.sendKeys(groupTitle);
		hmcPage.Template_codeInput.sendKeys(groupCode);

		// Add [Hierarchy]
		Common.rightClick(driver, hmcPage.Template_categoriesTable);
		Common.waitElementClickable(driver, hmcPage.Template_addHierarchy, 5);
		hmcPage.Template_addHierarchy.click();
		switchToWindow(4);
		hmcPage.Template_identifier.sendKeys(optionSupercategories);
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

	private void delteTemplate(String tabTitle) {
		By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + tabTitle + "]')])[1]");
		WebElement testTabE = driver.findElement(testTabX);
		Common.rightClick(driver, testTabE);
		hmcPage.B2CLeasing_remove.click();
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		hmcPage.Catalog_save.click();
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

		try {
			hmcPage.Product_SynchronizationBtn.click();
		} catch (TimeoutException e) {
			Common.sleep(18000);
		}
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
		new WebDriverWait(driver, 500).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncFinishMsg));
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

	// batchAssignFlag true: batch assign catalogFlag true online
	private void batchAssingAndUnassign(Boolean batchAssignFlag, Boolean catalogFlag, String optionID, String mt, String country, String groupTitle) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
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
			Common.sleep(1000);
			Common.waitElementVisible(driver, hmcPage.ProductBuilder_mtList);
		} catch (TimeoutException e) {
		}
		hmcPage.ProductBuilder_searchInput.sendKeys(mt);
		Common.sleep(5000);

		By targetMTX = By.xpath("//*[contains(@id,'machineTypesSelectNamems')]/option[contains(text(),'" + mt + "')]");
		if (Common.isElementExist(driver, targetMTX)) {
			Common.waitElementClickable(driver, driver.findElement(targetMTX), 30);
			driver.findElement(targetMTX).click();
			hmcPage.ProductBuilder_addSelected.click();
			if (batchAssignFlag) {
				By templateX = By.xpath("//input[@name='groupCode' and @value!='" + groupTitle + "']");
				List<WebElement> template = driver.findElements(templateX);
				for (WebElement ele : template) {
					if (ele.isSelected())
						ele.click();
				}
			}
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

}
