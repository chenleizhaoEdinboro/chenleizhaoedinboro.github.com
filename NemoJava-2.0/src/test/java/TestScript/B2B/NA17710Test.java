package TestScript.B2B;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.CTOCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.CTOPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17710Test extends SuperTestClass {
	private String ProductID = "";
	private String baseProduct = "";
	private String accesoryID = "";
	private String Unit;
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public CTOPage ctoPage;

	public NA17710Test(String store, String productID, String accessoryID) {
		this.Store = store;
		this.testName = "NA-17710";
		this.ProductID = productID;
		this.accesoryID = accessoryID;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2b"})
	public void NA17710(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			ctoPage = new CTOPage(driver);

			driver.get(testData.B2B.getLoginUrl());
			Unit = testData.B2B.getLoginUrl();
			Unit = Unit.split("/")[7];
			Dailylog.logInfoDB(0, "Unit: " + Unit, Store, testName);

			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			// ProductID = "20HJCTO1WWENUS4";
			// accesoryID = "5PS0E97110";
			if (ProductID.equals("")) {
				b2bPage.HomePage_productsLink.click();
				b2bPage.HomePage_Laptop.click();
				b2bPage.Laptops_contractAgreementFilter.click();
				Thread.sleep(1000);
				if (!Common.isElementExist(driver, By.xpath("(//label[contains(.,'Agreement')]/input)[last()]"))) {
					Dailylog.logInfoDB(0, "No CTO Product to test", Store, testName);
				} else {
					b2bPage.Laptops_agreementChk.click();
					Thread.sleep(1000);
					Assert.fail("please update test product");
				}
			}
			// TODO:To optimize getting product from b2b when default product not valid

			if (!ProductID.equals("")) {
				// Get baseProduct
				baseProduct = getBaseProduct(ProductID);

				// HMC Get template type
				String templateType;
				templateType = getTemplateType(ProductID);
				Dailylog.logInfoDB(0, "templateType: " + templateType, Store, testName);

				// HMC Get option Supercategories : identifier
				String optionSupercategories;
				optionSupercategories = getSupercategories(accesoryID);
				Dailylog.logInfoDB(0, "optionSupercategories: " + optionSupercategories, Store, testName);

				// HMC Edit template
				String tabTitle = "NA17710" + Store;
				String tabCode = "NA17710" + Store;
				String sectionTitle = "NA17710" + Store;
				String sectionCode = "NA17710" + Store;
				String groupTitle = "NA17710" + Store;
				String groupCode = "NA17710" + Store;
				editTemplate(templateType, optionSupercategories, tabTitle, tabCode, sectionTitle, sectionCode,
						groupTitle, groupCode);

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
				String modalUrl = testData.CTO.getHomePageUrl() + "ca/nomenclature=" + baseProduct
						+ ProductID.substring(0, 10) + "########";
				Dailylog.logInfoDB(0, "modalUrl: " + modalUrl, Store, testName);
				// wait for modal page loading
				navigateToAndRetry(modalUrl);
				Common.sleep(10000);
				if (isAlertPresent())
					driver.switchTo().alert().accept();
				// Remove the template if already exist
				By newTemplateX2 = By.xpath("//span[text()='" + groupTitle + "']");
				if (Common.checkElementDisplays(driver, newTemplateX2, 5)) {
					WebElement open = driver.findElement(
							By.xpath("//tr[@groupid='DUM_OPT_" + groupTitle + "']/td[contains(@class,'char-open')]"));
					Common.javascriptClick(driver, open);
					WebElement delete = driver.findElement(By.xpath("//button[@groupid='DUM_OPT_" + groupTitle
							+ "' and @class='btn btn-primary delete-opt-section']"));
					Common.javascriptClick(driver, delete);
					Common.sleep(5000);
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_save);
					System.out.println("Clicked save button");
					Common.waitElementVisible(driver, ctoPage.ModalPage_saveMsg);
					String saveMsg = ctoPage.ModalPage_saveMsg.getText();
					System.out.print("saveMsg: " + saveMsg);
					Assert.assertTrue(saveMsg.indexOf("Configuration updated successfully") >= 0);
					Dailylog.logInfoDB(0, "Deleted existed group", Store, testName);
					Common.sleep(6000);
				}
				Common.waitElementClickable(driver, ctoPage.ModalPage_addOptionsBtn, 5);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						ctoPage.ModalPage_addOptionsBtn);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_addOptionsBtn);
				System.out.println("Clicked Add Options");
				Common.waitElementClickable(driver, ctoPage.ModalPage_selectOptions, 5);
				ctoPage.ModalPage_selectOptions.click();
				// verify the new created template is displayed in select options list
				By newTemplateX = By.xpath("//*[@class='dropdown-menu open']//span[text()='" + groupTitle + "']");
				Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX, 5),
						"newTemplate should display in select options list");
				// select the new created template
				Common.waitElementClickable(driver, driver.findElement(newTemplateX), 5);
				driver.findElement(newTemplateX).click();
				ctoPage.ModalPage_addOptionsOK.click();
				System.out.println("Added new template");
				// verify the new created template is displayed modal
				Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX2, 5),
						"newTemplate should display in modal");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_save);
				System.out.println("Clicked save button");
				Common.waitElementVisible(driver, ctoPage.ModalPage_saveMsg);
				String saveMsg = ctoPage.ModalPage_saveMsg.getText();
				System.out.print("saveMsg: " + saveMsg);
				Assert.assertTrue(saveMsg.indexOf("Configuration updated successfully") >= 0);
				Common.sleep(6000);
				// switch to bundle
				String bundleUrl = testData.CTO.getHomePageUrl() + "ca/nomenclature=" + baseProduct + ProductID + "~~~";
				navigateToAndRetry(bundleUrl);
				Dailylog.logInfoDB(0, "bundleUrl: " + bundleUrl, Store, testName);
				Common.sleep(6000);
				// close INVALID CONFIGURATION
				if (isAlertPresent())
					driver.switchTo().alert().accept();
				By invalidModalX = By.id("invalid-modal");
				if (Common.checkElementDisplays(driver, invalidModalX, 5))
					ctoPage.BundlePage_closeInvalidModal.click();

				// Approve rule
				Common.javascriptClick(driver, ctoPage.BundlePage_approveRulesBtn);
				Common.waitElementVisible(driver, ctoPage.BundlePage_rulesPanel);
				Common.sleep(12000);
				By newTemplateAX = By.xpath("//input[@targetid='DUM_OPT_" + groupTitle + "']");
				if (Common.isElementExist(driver, newTemplateAX, 5)) {
					driver.findElement(newTemplateAX).click();
					ctoPage.BundlePage_approvebtn.click();
					Common.waitElementVisible(driver, ctoPage.BundlePage_dialogMsg);
					Assert.assertEquals(ctoPage.BundlePage_dialogMsg.getText(),
							"Selected rules have been approved successfully");
					ctoPage.BundlePage_okOnDialog.click();
					Dailylog.logInfoDB(0, "Approved new option in bundle!", Store, testName);
				} else {
					Dailylog.logInfoDB(0, "The option is not displayed while approing new option in bundle!", Store,
							testName);
					// Assert.fail("The option is not displayed while approing new option in
					// bundle!");
				}

				// verify the new created template is displayed bundle
				Common.sleep(12000);
				By newTemplateX3 = By.xpath("//span[text()='" + groupTitle + "']");
				Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX3, 30),
						"newTemplate should display in bundle");
				// select correct unit under audience
				for (int i = 0; i < 3; i++) {
					try {
						Common.waitElementClickable(driver, ctoPage.BundlePage_B2BAudience, 10);
						break;
					} catch (Exception e) {
						Dailylog.logInfoDB(0, "audience is not displayed, refresh and try again", Store, testName);
						if (i == 2)
							Assert.fail("audience is not displayed!!!");
						driver.navigate().refresh();
					}
				}
				if (!ctoPage.BundlePage_B2BAudience.isSelected()) {
					ctoPage.BundlePage_B2BAudience.click();
					Dailylog.logInfoDB(0, "Selected audience -> public", Store, testName);
				}
				if (ctoPage.BundlePage_B2CAudience.isSelected()) {
					ctoPage.BundlePage_B2CAudience.click();
					Dailylog.logInfoDB(0, "Cancel selection of audience -> Large enterprise", Store, testName);
				}
				// publish the template
				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_Save);
				// ctoPage.BundlePage_Save.click();
				Thread.sleep(5000);
				Common.scrollToElement(driver, ctoPage.BundlePage_Nomenclature);
				ctoPage.BundlePage_Publish.click();
				Thread.sleep(1000);
				ctoPage.BundlePage_PublishConfirm.click();
				Dailylog.logInfoDB(0, "BundlePage_PublishConfirm", Store, testName);
				try {
					new WebDriverWait(driver, 1800)
							.until(ExpectedConditions.visibilityOf(ctoPage.BundlePage_PublishSuccMsg));
					Dailylog.logInfoDB(0, "BundlePage_PublishConfirm success", Store, testName);
				} catch (Exception e) {
					Dailylog.logInfoDB(0, "BundlePage_PublishConfirm failure", Store, testName);
					// assert false;
				}

				// HMC Sync product in HMC
				syncProduct(ProductID);

				// Add the target option into group item
				addGroupItem(accesoryID, ProductID, groupTitle, "B2B");

				// Check in B2B
				String pdpUrl = testData.B2B.getHomePageUrl() + "/p/" + ProductID;
				driver.get(pdpUrl);
				Assert.assertTrue(
						Common.checkElementDisplays(driver, By.xpath("//span[text()='" + groupTitle + "']"), 30),
						"new group should be displayed");
				Assert.assertTrue(
						Common.checkElementDisplays(driver, By.xpath("//input[contains(@id,'" + accesoryID + "')]"), 30),
						"correct option should be displayed under the new group");
			}

		} catch (

		Throwable e) {
			handleThrowable(e, ctx);
		}
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

	private String getTemplateType(String product) throws InterruptedException {
		String templateType = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(product);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		Common.sleep(2000);
		hmcPage.Products_edit.click();
		Common.waitElementVisible(driver, hmcPage.Products_activeTemplate);
		templateType = hmcPage.Products_activeTemplate.getAttribute("value");
		hmcPage.hmcHome_hmcSignOut.click();
		return templateType;
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
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(2000);
	}

	private String getSupercategories(String product) throws InterruptedException {
		String supercategories = "";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(product);
		// category system-> record Supercategories : identifier
		hmcPage.Products_categorySystem.click();
		supercategories = hmcPage.Products_firstSupercategoriesID.getText();
		hmcPage.hmcHome_hmcSignOut.click();
		return supercategories;
	}

	private void editTemplate(String template, String optionSupercategories, String tabTitle, String tabCode,
			String sectionTitle, String sectionCode, String groupTitle, String groupCode) {
		By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
		By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + tabTitle + "]')])[1]");
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
	
	public void navigateToAndRetry(String url) {
		try {
			driver.get(url);
		} catch (TimeoutException exception) {
			System.out.println("driver.get timeout");
			driver.navigate().refresh();
		}
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

	private boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
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
		Common.waitElementClickable(driver, hmcPage.Product_SyncStart, 30);
		try {
		Common.javascriptClick(driver, hmcPage.Product_SyncStart);}
		catch(TimeoutException e) {
			Common.sleep(12000);
		}	
		new WebDriverWait(driver, 3000000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncFinishMsg));
		Common.sleep(3000);
//		assert hmcPage.Product_SyncStatus.getText().contains("FINISHED");
//		assert hmcPage.Product_SyncResult.getText().contains("SUCCESS");
		driver.close();
		switchToWindow(0);

		// Sign-out
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void addGroupItem(String accessoryID, String productID, String newTempalte, String channel)
			throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(productID);
		Common.rightClick(driver, hmcPage.Products_baseProduct);
		hmcPage.Products_edit.click();
		Common.waitElementClickable(driver, hmcPage.Catalog_multiCountry, 5);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver
				.findElement(By.xpath("//div[contains(@id,'ItemDisplay[B2B]')][contains(.,'" + channel
						+ "')]/../../td[contains(.,'" + Store + "')]"));
		Common.doubleClick(driver, targetProductBuilder);
		switchToWindow(1);
		By targetItem1 = By
				.xpath("//div[contains(@id,'" + newTempalte + " ')]/../..//div[contains(@id,'" + accessoryID + "')]");
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
			Common.rightClick(driver, hmcPage.products_PB_optionsTable);
			Common.waitElementClickable(driver, hmcPage.products_PB_addProductOption, 5);
			hmcPage.products_PB_addProductOption.click();
			switchToWindow(3);
			Common.waitElementVisible(driver, hmcPage.products_PB_code);
			hmcPage.products_PB_code.clear();
			hmcPage.products_PB_code.sendKeys(accessoryID);
			Select catalog = new Select(hmcPage.products_PB_catalogVersionSel);
			catalog.selectByVisibleText("masterMultiCountryProductCatalog - Online");
			hmcPage.products_PB_searchBtn.click();
			By targetAcc = By.xpath("//div[contains(@id,'" + accessoryID + "')]");
			Common.waitElementClickable(driver, driver.findElement(targetAcc), 5);
			driver.findElement(targetAcc).click();
			hmcPage.products_PB_useBtn.click();
			switchToWindow(2);
			hmcPage.products_PB_saveBtn.click();
			driver.close();
			switchToWindow(1);
			hmcPage.products_PB_saveBtn.click();
		}
		driver.close();
		switchToWindow(0);
		hmcPage.hmcHome_hmcSignOut.click();
		Dailylog.logInfoDB(0, "added unit to group item", Store, testName);
	}
}
