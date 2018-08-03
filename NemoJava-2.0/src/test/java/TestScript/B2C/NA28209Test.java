package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
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

public class NA28209Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	private String country1;

	public NA28209Test(String store, String country1) {
		this.Store = store;
		this.country1 = country1;
		this.testName = "NA-28209";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void NA28209(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			// Step 1 Find one machine type with PB
			String product;
			if (Store.equals("GB"))
				product = "20KSCTO1WWENGB0";
			else
				product = "20ERCTO1WWENUS0";//testData.B2C.getDefaultCTOPN();
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

			Common.switchToWindow(driver, 0);
			String sectionTitle = "Charges   Batteries";//Power accessories
			String sectionTitleB2C = "Charges & Batteries";
			if (Store.equals("JP"))
				sectionTitleB2C = "電源関係";
			int originOrder = getSectionOrder(sectionTitleB2C);
			Assert.assertNotEquals(originOrder, 0,
					"Power accessories not exist for this product, please change test product");
			String originExpanded = isSectionExpanded(sectionTitleB2C);

			String warrantyType = getWarrantyType();
			Dailylog.logInfoDB(1, "warrantyType: " + warrantyType, Store, testName);

			String lastWarranty = "not defined";
			String sectionCode = null;
			String groupText = null;
			if (warrantyType.equals("new")) {
				groupText = getNewLastWrrantyGroup().toLowerCase();
				if (groupText.replace(" ", "").contains("1年") || groupText.contains("one year"))
					lastWarranty = "Warranty services 1 Year";// get from hmc template -> warranty -> group title
				else if (groupText.replace(" ", "").contains("2年") || groupText.contains("two year"))
					lastWarranty = "Warranty services 2 Years";
				else if (groupText.replace(" ", "").contains("3年") || groupText.contains("three year"))
					lastWarranty = "Warranty services 3 Years";
				else if (groupText.replace(" ", "").contains("4年") || groupText.contains("four year"))
					lastWarranty = "Warranty services 4 Years";
				else if (groupText.replace(" ", "").contains("5年") || groupText.contains("five year"))
					lastWarranty = "Warranty services 5 Years";
				else if (groupText.replace(" ", "").contains("6年") || groupText.contains("six year"))
					lastWarranty = "Warranty services 6 Years";
				else if (groupText.replace(" ", "").contains("標準保証") || groupText.contains("base warranty"))
					lastWarranty = "Base Warranty";
				else
					lastWarranty = "other groupText: " + groupText;
			} else if (warrantyType.equals("stackable")) {
				sectionCode = getStackableWrrantySection();
				if (sectionCode.equals("warrServices") || sectionCode.equals("upgradeWarranty")) {
					lastWarranty = getStackableLastWrrantyGroupCode(sectionCode);
					Dailylog.logInfoDB(1, "lastWarranty: " + lastWarranty, Store, testName);
					groupText = getStackableLastWrrantyGroupText(sectionCode);
					Dailylog.logInfoDB(1, "groupText: " + groupText, Store, testName);
				} else
					Assert.fail(sectionCode);
			} else {

			}
			Dailylog.logInfoDB(1, "lastWarranty: " + lastWarranty, Store, testName);

			// Step 2~3 create section order
			String templateTitle;
			Common.switchToWindow(driver, 1);
			templateTitle = getTemplateType(product);
			Dailylog.logInfoDB(0, "templateType: " + templateTitle, Store, testName);
			String tabTitle = "Accessories";
			int windowNo = 1;
			String channel = "B2C";
			int sectionOrder = originOrder + 1;
			String isExpanded = "Yes";
			String isActive = "Yes";
			boolean isSectionOrderCreated;
			int currentOrder = 0;
			isSectionOrderCreated = editTemplate(templateTitle, tabTitle, windowNo, sectionTitle, country1, channel,
					baseProduct, sectionOrder, isExpanded, isActive);
			Assert.assertTrue(isSectionOrderCreated, "section order is not successfully created");
			Dailylog.logInfoDB(2, "section order is created", Store, testName);
			openTestProduct(pdpUrl, productType);
			currentOrder = getSectionOrder(sectionTitleB2C);
			Assert.assertEquals(currentOrder, sectionOrder, "section order not correct");
			Dailylog.logInfoDB(2, "section order is correct: " + currentOrder, Store, testName);
			String isSectionExpanded = isSectionExpanded(sectionTitleB2C);
			Assert.assertEquals(isSectionExpanded, isExpanded, "expand and collopse error");
			Dailylog.logInfoDB(2, "section expand is correct", Store, testName);

			// Step 4 Change collapse of the section
			isExpanded = "No";
			isSectionOrderCreated = editTemplate(templateTitle, tabTitle, windowNo, sectionTitle, country1, channel,
					baseProduct, sectionOrder, isExpanded, isActive);
			Assert.assertTrue(isSectionOrderCreated, "section order is not successfully created");
			openTestProduct(pdpUrl, productType);
			currentOrder = getSectionOrder(sectionTitleB2C);
			Assert.assertEquals(currentOrder, sectionOrder, "section order not correct");
			Dailylog.logInfoDB(2, "section order is correct: " + currentOrder, Store, testName);
			isSectionExpanded = isSectionExpanded(sectionTitleB2C);
			// if (Store.equals("GB"))
			// Assert.assertEquals(isSectionExpanded, "Yes", "expand and collopse error");
			// else
				Assert.assertEquals(isSectionExpanded, isExpanded, "expand and collopse error");
			Dailylog.logInfoDB(2, "section collopse is correct", Store, testName);

			// Step 5 Change active to inactive in this section
			isActive = "No";
			isSectionOrderCreated = editTemplate(templateTitle, tabTitle, windowNo, sectionTitle, country1, channel,
					baseProduct, sectionOrder, isExpanded, isActive);
			Assert.assertTrue(isSectionOrderCreated, "section order is not successfully created");
			openTestProduct(pdpUrl, productType);
			currentOrder = getSectionOrder(sectionTitleB2C);
			Assert.assertNotEquals(currentOrder, sectionOrder, "section order not correct");
			// Assert.assertEquals(currentOrder, originOrder, "section order not correct");
			Dailylog.logInfoDB(2, "section order is correct: " + currentOrder, Store, testName);
			isSectionExpanded = isSectionExpanded(sectionTitleB2C);
			Assert.assertEquals(isSectionExpanded, originExpanded, "expand and collopse error");
			Dailylog.logInfoDB(2, "section expand and collopse correct", Store, testName);

			// Step 6~7 find last warranty group in HMC
			if (warrantyType.equals("new")) {
				tabTitle = "Warranty";
				sectionTitle = "Warranty Upgrade";
			} else if (warrantyType.equals("stackable")) {
				tabTitle = "stackable";
				if (sectionCode.equals("warrServices"))
					sectionTitle = "Additional Services";
				else
					sectionTitle = "Warranty Upgrade";
			}
			int groupOrder = 1;
			isActive = "Yes";
			String groupTitle = lastWarranty;
			isSectionOrderCreated = editTemplate(templateTitle, tabTitle, windowNo, sectionTitle, groupTitle, country1,
					channel, baseProduct, groupOrder, isActive);
			Assert.assertTrue(isSectionOrderCreated, "group order is not successfully created");
			Dailylog.logInfoDB(6, "group order is successfully created", Store, testName);
			openTestProduct(pdpUrl, productType);
			b2cPage.PB_warrantyTab.click();
			if (warrantyType.equals("new")) {
				currentOrder = getNewWarrantyGroupOrder(groupText);
			} else if (warrantyType.equals("stackable")) {
				currentOrder = getStackableWarrantyGroupOrder(groupText);
			}
			Assert.assertEquals(currentOrder, groupOrder, "group order not correct");
			Dailylog.logInfoDB(6, "group order is correct: " + currentOrder, Store, testName);

			// Step 8 Change active to inactive in this group -> order: last
			isActive = "No";
			int expectedOrder = 0;
			isSectionOrderCreated = editTemplate(templateTitle, tabTitle, windowNo, sectionTitle, groupTitle, country1,
					channel, baseProduct, groupOrder, isActive);
			openTestProduct(pdpUrl, productType);
			b2cPage.PB_warrantyTab.click();
			if (warrantyType.equals("new")) {
				currentOrder = getNewWarrantyGroupOrder(groupText);
				expectedOrder = b2cPage.PB_newWarrantyGroup.size();
			} else if (warrantyType.equals("stackable")) {
				currentOrder = getStackableWarrantyGroupOrder(groupText);
				expectedOrder = b2cPage.PB_stackableUpgradeWarrantyGroup.size();
			}
			Assert.assertEquals(currentOrder, expectedOrder, "group order not correct");
			Dailylog.logInfoDB(8, "group order is correct: " + currentOrder, Store, testName);
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
			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 15))
				Dailylog.logInfoDB(0, "continue customising is not valid on page: " + product, Store, testName);
			else
				isValid = true;

		} else if (productType.equals("mtm")) {
			if (!Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 15))
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

	private boolean editTemplate(String template, String tabTitle, int windowNo, String sectionTitle, String country,
			String channel, String baseProduct, int sectionOrder, String isExpanded, String isActive) {
		By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
		By targetTabX = By.xpath("//span[contains(@id,'" + tabTitle + "')]");
		By targetSectionX = By.xpath("//span[contains(@id,'" + sectionTitle + "')]");
		Common.switchToWindow(driver, windowNo);
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
		WebElement targetTab = driver.findElement(targetTabX);
		targetTab.click();
		Common.sleep(2000);
		Common.switchToWindow(driver, ++windowNo);

		// open target section
		WebElement targetSection = driver.findElement(targetSectionX);
		targetSection.click();
		Common.sleep(3000);
		Common.switchToWindow(driver, ++windowNo);

		// create section order
		By targetSectionOrderX1 = By.xpath("//table[@title='sectionOrders']//table//table//tr[contains(.,'" + country
				+ "') and contains(.,'" + channel + "') and contains(.,'" + baseProduct + "')]");
		By targetSectionOrderX2 = By.xpath(
				"//table[@title='sectionOrders']//table//table//tr[contains(.,'" + country + "') and contains(.,'"
						+ channel + "') and contains(.,'" + baseProduct + "')]/td[7][contains(.,'" + isExpanded
						+ "')]/../td[8][contains(.,'" + isActive + "')]/../td[6][contains(.,'" + sectionOrder + "')]");
		if (Common.isElementExist(driver, targetSectionOrderX1)) {
			WebElement targetSectionOrder = driver.findElement(targetSectionOrderX1);
			Common.doubleClick(driver, targetSectionOrder);
		} else {
			Common.rightClick(driver, hmcPage.Template_sectionOrders);
			hmcPage.Template_ProductOptionSectionOrder.click();
		}
		Common.switchToWindow(driver, ++windowNo);
		Select countrySel = new Select(hmcPage.Template_country);
		countrySel.selectByVisibleText(country);
		Select channelSel = new Select(hmcPage.Template_channel);
		channelSel.selectByVisibleText(channel);
		hmcPage.Template_machineTypeSearch.click();
		Common.switchToWindow(driver, ++windowNo);
		hmcPage.Template_articleNumber.sendKeys(baseProduct);
		Common.sleep(500);
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		hmcPage.Template_searchBtn.click();
		hmcPage.Template_searchedHierarchy.click();
		hmcPage.Template_useBtn.click();
		Common.switchToWindow(driver, --windowNo);
		hmcPage.Template_sortOrderNumber.clear();
		hmcPage.Template_sortOrderNumber.sendKeys(String.valueOf(sectionOrder));
		if (isExpanded.toLowerCase().equals("yes"))
			hmcPage.Template_expandedTrue.click();
		else
			hmcPage.Template_expandedFalse.click();
		if (isActive.toLowerCase().equals("yes")) {
			if (!hmcPage.Template_actived.isSelected())
				hmcPage.Template_actived.click();
		} else {
			if (hmcPage.Template_actived.isSelected())
				hmcPage.Template_actived.click();
		}

		// save section order
		hmcPage.Template_saveBtn.click();
		Common.sleep(6000);
		driver.close();
		// verify section order saved successfully and close section
		Common.switchToWindow(driver, --windowNo);
		Common.sleep(6000);
		hmcPage.Template_saveBtn.click();
		Common.sleep(2000);
		boolean isCreated = Common.isElementExist(driver, targetSectionOrderX2);
		driver.close();
		// close tab
		Common.switchToWindow(driver, --windowNo);
		driver.close();
		// switch back to template
		Common.switchToWindow(driver, --windowNo);
		hmcPage.hmcHome_hmcSignOut.click();
		return isCreated;
	}

	private int getSectionOrder(String sectionTitleText) {
		b2cPage.PB_accessoriesTab.click();
		int order = 0;
		Common.sleep(8000);
		if (Common.checkElementDisplays(driver, By.xpath(
				"//div[@class='configuratorItem-accordion-content accessories' or @data-tabname='Accessories']//span[@class='section-title']"),
				30)) {
			int size = b2cPage.PB_accessorySectionTitle.size();
			for (int i = 0; i < size; i++) {
				String sectionTitle = b2cPage.PB_accessorySectionTitle.get(i).getText();
				System.out.println(sectionTitle);
				if (sectionTitle.contains(sectionTitleText)) {
					order = ++i;
					break;
				}
			}
		}
		return order;
	}

	private String isSectionExpanded(String sectionTitleText) {
		String isExpanded = "section not found";
		int size = b2cPage.PB_accessorySectionTitle.size();
		for (int i = 0; i < size; i++) {
			String sectionTitle = b2cPage.PB_accessorySectionTitle.get(i).getText();
			if (sectionTitle.contains(sectionTitleText)) {
				String getClass = b2cPage.PB_accessorySectionTitlePreLevel.get(i).getAttribute("class");
				if (getClass.contains("expandableHeading-is-expanded"))
					isExpanded = "Yes";
				else
					isExpanded = "No";
				break;
			}
		}
		return isExpanded;
	}

	private String getWarrantyType() {
		String warrantyType = "old";
		Common.javascriptClick(driver, b2cPage.PB_warrantyTab);
		// b2cPage.PB_warrantyTab.click();
		if (Common.checkElementDisplays(driver, By.xpath("//*[@id='stackableWarranty']"), 3))
			warrantyType = "stackable";
		else if (Common.checkElementDisplays(driver,
				By.xpath("//header[@id='Warranty']//h3[contains(@class,'configuratorHeader')]"), 3))
			warrantyType = "new";
		return warrantyType;
	}

	private String getNewLastWrrantyGroup() {
		String groupCode;
		b2cPage.PB_warrantyTab.click();
		By newWarrantyChangeBtnX = By.xpath(
				"//*[@id='Warranty' or @id='stackableWarranty']//button[contains(@class,'sectionExpandButton')]");
		if (Common.checkElementDisplays(driver, newWarrantyChangeBtnX, 3))
			Common.javascriptClick(driver, b2cPage.PB_newWarrantyChangeBtn);
		// b2cPage.PB_newWarrantyChangeBtn.click();
		int groupSize = b2cPage.PB_newWarrantyGroup.size();
		if (groupSize > 1) {
			String groupText = b2cPage.PB_newWarrantyGroup.get(groupSize - 1).getText();
			groupCode = groupText;
		} else
			groupCode = "no warranty group";
		return groupCode;
	}

	private String getStackableWrrantySection() {
		String sectionCode = "no upgradeWarranty section, no warrServices section";
		By newWarrantyChangeBtnX = By.xpath(
				"//*[@id='Warranty' or @id='stackableWarranty']//button[contains(@class,'sectionExpandButton')]");
		if (Common.checkElementDisplays(driver, newWarrantyChangeBtnX, 3))
			Common.javascriptClick(driver, b2cPage.PB_newWarrantyChangeBtn);
		// b2cPage.PB_newWarrantyChangeBtn.click();
		if (Common.checkElementDisplays(driver, By.id("upgradeWarranty"), 3)) {
			int size = b2cPage.PB_stackableUpgradeWarrantyGroup.size();
			if (size > 0)
				sectionCode = "upgradeWarranty";
			else
				sectionCode = "upgradeWarranty section exist but no upgradeWarranty group";
		}
		if (Common.checkElementDisplays(driver, By.id("warrServices"), 3) && !sectionCode.equals("upgradeWarranty")) {
			int size = b2cPage.PB_stackableWarrServicesGroup.size();
			if (size > 0)
				sectionCode = "warrServices";
			else
				sectionCode = "warrServices section exist but no warrServices group";
		}
		return sectionCode;
	}

	private String getStackableLastWrrantyGroupText(String sectionCode) {
		String groupText = "groupText not defined";
		int groupSize;
		if (sectionCode.equals("upgradeWarranty")) {
			groupSize = b2cPage.PB_stackableUpgradeWarrantyGroup.size();
			groupText = b2cPage.PB_stackableUpgradeWarrantyGroup.get(groupSize - 1).getText();
		} else if (sectionCode.equals("warrServices")) {
			groupSize = b2cPage.PB_stackableWarrServicesGroup.size();
			groupText = b2cPage.PB_stackableWarrServicesGroup.get(groupSize - 1).getText();
		}
		return groupText;
	}

	private String getStackableLastWrrantyGroupCode(String sectionCode) {
		String groupCode = "groupCode not defined";
		int groupSize;
		if (sectionCode.equals("upgradeWarranty")) {
			groupSize = b2cPage.PB_stackableUpgradeWarrantyGroupCode.size();
			groupCode = b2cPage.PB_stackableUpgradeWarrantyGroupCode.get(groupSize - 1).getAttribute("groupcode")
					.trim();
		} else if (sectionCode.equals("warrServices")) {
			groupSize = b2cPage.PB_stackableUpgradeWarrantyGroupCode.size();
			groupCode = b2cPage.PB_stackableUpgradeWarrantyGroupCode.get(groupSize - 1).getAttribute("groupcode")
					.trim();
		}
		return groupCode;
	}

	private void openTestProduct(String pdpUrl, String productType) {
		Common.switchToWindow(driver, 0);
		driver.get(pdpUrl);
		if (productType.equals("cto"))
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		else
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
	}

	private boolean editTemplate(String template, String tabTitle, int windowNo, String sectionTitle, String groupTitle,
			String country, String channel, String baseProduct, int sectionOrder, String isActive) {
		By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
		By targetTabX;
		if (tabTitle.toLowerCase().equals("warranty"))
			targetTabX = By.xpath("(//span[contains(@id,'Warranty')])[1]");
		else
			targetTabX = By.xpath("(//span[contains(@id,'Warranty')])[2]");
		By targetSectionX = By.xpath("//span[contains(@id,'" + sectionTitle + "')]");
		By targetGroupX = By.xpath("//input[@value='" + groupTitle + "']/../../../../../../../..//img");
		Common.switchToWindow(driver, windowNo);
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
		WebElement targetTab = driver.findElement(targetTabX);
		targetTab.click();
		Common.sleep(2000);
		Common.switchToWindow(driver, ++windowNo);

		// open target section
		WebElement targetSection = driver.findElement(targetSectionX);
		targetSection.click();
		Common.sleep(2000);
		Common.switchToWindow(driver, ++windowNo);

		// open target group
		WebElement targetGroup = driver.findElement(targetGroupX);
		targetGroup.click();
		Common.sleep(2000);
		Common.switchToWindow(driver, ++windowNo);

		// create group order
		By targetGroupOrderX1 = By.xpath("//table[@title='groupOrders']//table//table//tr[contains(.,'" + country
				+ "') and contains(.,'" + channel + "') and contains(.,'" + baseProduct + "')]");
		By targetGroupOrderX2 = By.xpath("//table[@title='groupOrders']//table//table//tr[contains(.,'" + country
				+ "') and contains(.,'" + channel + "') and contains(.,'" + baseProduct + "')]/td[7][contains(.,'"
				+ isActive + "')]/../td[6][contains(.,'" + sectionOrder + "')]");
		if (Common.isElementExist(driver, targetGroupOrderX1)) {
			WebElement targetGroupOrder = driver.findElement(targetGroupOrderX1);
			Common.doubleClick(driver, targetGroupOrder);
		} else {
			Common.rightClick(driver, hmcPage.Template_groupOrders);
			hmcPage.Template_ProductOptionGroupOrder.click();
		}
		Common.switchToWindow(driver, ++windowNo);
		Select countrySel = new Select(hmcPage.Template_country);
		countrySel.selectByVisibleText(country);
		Select channelSel = new Select(hmcPage.Template_channel);
		channelSel.selectByVisibleText(channel);
		hmcPage.Template_machineTypeSearch.click();
		Common.switchToWindow(driver, ++windowNo);
		hmcPage.Template_articleNumber.sendKeys(baseProduct);
		Common.sleep(500);
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		hmcPage.Template_searchBtn.click();
		hmcPage.Template_searchedHierarchy.click();
		hmcPage.Template_useBtn.click();
		Common.switchToWindow(driver, --windowNo);
		hmcPage.Template_sortOrderNumber.clear();
		hmcPage.Template_sortOrderNumber.sendKeys(String.valueOf(sectionOrder));
		if (isActive.toLowerCase().equals("yes")) {
			if (!hmcPage.Template_actived.isSelected())
				hmcPage.Template_actived.click();
		} else {
			if (hmcPage.Template_actived.isSelected())
				hmcPage.Template_actived.click();
		}

		// save section order
		hmcPage.Template_saveBtn.click();
		Common.sleep(5000);
		driver.close();
		// verify group order saved successfully and close group
		Common.switchToWindow(driver, --windowNo);
		Common.sleep(8000);
		boolean isCreated = Common.isElementExist(driver, targetGroupOrderX2);
		driver.close();
		// close section
		Common.switchToWindow(driver, --windowNo);
		driver.close();
		// close tab
		Common.switchToWindow(driver, --windowNo);
		driver.close();
		// switch back to template
		Common.switchToWindow(driver, --windowNo);
		hmcPage.hmcHome_hmcSignOut.click();
		return isCreated;
	}

	private int getNewWarrantyGroupOrder(String groupText) {
		Common.javascriptClick(driver, b2cPage.PB_warrantyTab);
		// b2cPage.PB_warrantyTab.click();
		Common.sleep(5000);
		By newWarrantyChangeBtnX = By.xpath(
				"//*[@id='Warranty' or @id='stackableWarranty']//button[contains(@class,'sectionExpandButton')]");
		if (Common.checkElementDisplays(driver, newWarrantyChangeBtnX, 3))
			Common.javascriptClick(driver, b2cPage.PB_newWarrantyChangeBtn);
		int order = 0;
		Common.sleep(5000);
		if (Common.checkElementDisplays(driver,
				By.xpath("//div[contains(@class,'warranty')]//div[@class='group-title']"), 15)) {
			int size = b2cPage.PB_newWarrantyGroup.size();
			for (int i = 0; i < size; i++) {
				String groupTitle = b2cPage.PB_newWarrantyGroup.get(i).getText();
				if (groupTitle.contains(groupText)) {
					order = ++i;
					break;
				}
			}
		}
		return order;
	}

	private int getStackableWarrantyGroupOrder(String groupText) {
		Common.javascriptClick(driver, b2cPage.PB_warrantyTab);
		// b2cPage.PB_warrantyTab.click();
		Common.sleep(5000);
		By newWarrantyChangeBtnX = By.xpath(
				"//*[@id='Warranty' or @id='stackableWarranty']//button[contains(@class,'sectionExpandButton')]");
		if (Common.checkElementDisplays(driver, newWarrantyChangeBtnX, 3))
			Common.javascriptClick(driver, b2cPage.PB_newWarrantyChangeBtn);
		Common.sleep(5000);
		int order = 0;
		if (Common.checkElementDisplays(driver, By.xpath(
				"//section[contains(@id,'upgradeWarranty')]//div[contains(@class,'stackableSection') and not(@id)]//div[@class='stackableHeader']/span"),
				15)) {
			int size = b2cPage.PB_stackableUpgradeWarrantyGroup.size();
			for (int i = 0; i < size; i++) {
				String groupTitle = b2cPage.PB_stackableUpgradeWarrantyGroup.get(i).getText();
				if (groupTitle.contains(groupText)) {
					order = ++i;
					break;
				}
			}
		}
		return order;
	}
}
