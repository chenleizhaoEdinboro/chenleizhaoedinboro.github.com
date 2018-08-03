package CommonFunction;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.HMCPage;
import TestData.TestData;

public class HMCCommon {

	/**
	 * @Owner Zhongxu
	 * @Parameter:
	 * @Usage:
	 */
	public static void Login(HMCPage hmcPage, TestData testData) {
		if (Common.checkElementExists(hmcPage.PageDriver, hmcPage.Login_IDTextBox, 5)) {
			hmcPage.Login_IDTextBox.clear();
			hmcPage.Login_IDTextBox.sendKeys(testData.HMC.getLoginId());
			hmcPage.Login_PasswordTextBox.clear();
			hmcPage.Login_PasswordTextBox.sendKeys(testData.HMC.getPassword());
			hmcPage.Login_RememberCheckBox.click();
			hmcPage.Login_LoginButton.click();
		}
	}

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void HMCOrderCheck(HMCPage page, String OrderNumber) throws Exception {
		Thread.sleep(5000);
		page.Home_Order.click();
		page.Home_Order_Orders.click();
		page.Home_Order_OrderID.clear();
		page.Home_Order_OrderID.sendKeys(OrderNumber);
		page.Home_Order_OrderSearch.click();
		Thread.sleep(5000);
		if (!page.Home_Order_OrderStatus.getText().contains("Completed")) {
			Thread.sleep(5000);
			page.Home_Order_OrderSearch.click();
		}
		if (!page.Home_Order_OrderStatus.getText().contains("Completed")) {
			Assert.fail("Order status is not Completed! " + OrderNumber);
		}

	}

	/**
	 * @Owner Zhongxu
	 * @Parameter:
	 * @Usage:
	 */
	public static void searchB2BCustomer(HMCPage hmcPage, String emailId) {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(emailId);
		hmcPage.B2BCustomer_SearchButton.click();
	}

	/**
	 * @Owner Zhongxu
	 * @Parameter:
	 * @Usage:
	 */
	public static void searchB2CUnit(HMCPage hmcPage, TestData testData) {
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();

	}

	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void activeAccount(WebDriver driver, TestData testData, String tempEmailAddress) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCPage hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2BCustomer(hmcPage, tempEmailAddress);
		hmcPage.B2BCustomer_1stSearchedResult.click();
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveUserDropdown.click();
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.Common_SaveButton.click();
		System.out.println("Active account: " + tempEmailAddress);
	}

	/**
	 * @Owner Tianyi
	 * @Parameter:
	 * @Usage:
	 */
	public static void cleanRadis(HMCPage hmcPage, String partNm) throws InterruptedException {
		hmcPage.Home_System.click();
		Thread.sleep(5000);
		hmcPage.Home_RadisCacheCleanLink.click();
		Thread.sleep(5000);
		hmcPage.PageDriver.switchTo().frame(hmcPage.PageDriver.findElement(By.xpath(".//iframe[contains(@src,'nemoClearCachePage')]")));
		hmcPage.Radis_CleanProductTextBox.clear();
		hmcPage.Radis_CleanProductTextBox.sendKeys(partNm);
		hmcPage.Radis_CleanButton.click();
		Common.waitAlertPresent(hmcPage.PageDriver, 60);
		hmcPage.PageDriver.switchTo().alert().accept();
		hmcPage.PageDriver.switchTo().defaultContent();
		hmcPage.Home_System.click();
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static int getOrderRecordsNumber(WebDriver driver, HMCPage page, String date, String customer) throws Exception {
		int currentRecords = 0;
		String customerXpath = ".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[Order.user]]_ajaxselect_" + customer + "']";
		String totalRecords;

		Thread.sleep(3000);
		page.Order.click();
		page.Orders.click();
		page.OrderDate.clear();
		page.OrderDate.sendKeys(date);
		page.CustomerID.clear();
		page.CustomerID.sendKeys(customer);
		Thread.sleep(2000);
		WebElement CustomerOption = driver.findElement(By.xpath(customerXpath));
		CustomerOption.click();

		page.OrderSearch.click();
		Thread.sleep(5000);
		if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'1 -')]"))) {
			totalRecords = page.NumberofOrders.getText().toString().replaceAll(" ", "");
		} else {
			totalRecords = driver.findElement(By.xpath("//td[contains(text(),'0 -')]")).getText().toString().replaceAll(" ", "");
		}

		String[] temp = totalRecords.split("of");
		currentRecords = Integer.parseInt(temp[temp.length - 1]);
		return currentRecords;

	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void isOrderGenerated(WebDriver driver, HMCPage page, int previousRecord) throws Exception {
		String totalRecords;
		int currentRecords = 0;
		page.OrderSearch.click();
		Thread.sleep(5000);
		if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'1 -')]"))) {
			totalRecords = page.NumberofOrders.getText().toString().replaceAll(" ", "");
		} else {
			totalRecords = driver.findElement(By.xpath("//td[contains(text(),'0 -')]")).getText().toString().replaceAll(" ", "");
		}

		String[] temp = totalRecords.split("of");
		currentRecords = Integer.parseInt(temp[temp.length - 1]);
		assert previousRecord == currentRecords : "A new order has been generated in HMC!";

	}

	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static void SetQuoteEnabledOnB2B(HMCPage page1, WebDriver driver2, String B2BUnit) {
		By byLocater1 = By.partialLinkText(B2BUnit);
		page1.Home_B2BCommerceLink.click();
		page1.Home_B2BUnitLink.click();
		page1.B2BUnit_SearchIDTextBox.sendKeys(B2BUnit);
		page1.B2BUnit_SearchButton.click();
		driver2.findElement(byLocater1).click();
		page1.B2BUnit_siteAttribute.click();
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		page1.B2BUnit_enableQuoteApprove.click();
		page1.B2BUnit_enableQuoteConvert.click();
		page1.B2BUnit_enableQuoteResId.click();
		page1.B2BUnit_isQuoteAvailable.click();
		page1.B2BUnit_Save.click();
	}

	/**
	 * @Owner Zhongxu
	 * @Parameter:
	 * @Usage:
	 */
	public static void searchB2BUnit(HMCPage hmcPage, TestData testData) {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
	}

	/**
	 * @Owner Shuangshuang
	 * @Parameter:
	 * @Usage:
	 */
	public static void loginPricingCockpit(WebDriver driver, HMCPage hmcPage, TestData testData) {
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
		if (Common.checkElementDisplays(driver, hmcPage.PricingCockpit_username, 5)) {
			hmcPage.PricingCockpit_username.clear();
			hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
			hmcPage.PricingCockpit_password.clear();
			hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
			hmcPage.PricingCockpit_Login.click();
		}
	}

	/**
	 * @Owner Shuangshuang
	 * @Parameter:
	 * @Usage:
	 */
	public static void fillB2CPriceRuleDetails(WebDriver driver, WebElement DDLocator, WebElement txtBoxLocator, String value, int waitTime) throws InterruptedException {
		try {
			Common.waitElementClickable(driver, DDLocator, 30);
		} catch (StaleElementReferenceException e) {
			Thread.sleep(5000);
		}
		Thread.sleep(1000);
		DDLocator.click();
		Common.waitElementVisible(driver, txtBoxLocator);
		txtBoxLocator.sendKeys(value);
		Thread.sleep(waitTime);
		txtBoxLocator.sendKeys(Keys.ENTER);
		Common.sleep(1500);
	}

	/**
	 * @Owner Shuangshuang
	 * @Parameter:
	 * @Usage:HMC->Price setting->Price Cockpit-> Price Simulator-Debug
	 * 
	 */
	public static void B2CPriceSimulateDebug(WebDriver driver, HMCPage hmcPage, String country, String store, String catalog, String partNumber, String... smbPriceTier) {
		hmcPage.PricingCockpit_B2CPriceSimulator.click();
		Common.sleep(3000);
		try {
			fillB2CPriceRuleDetails(driver, hmcPage.B2BpriceSimulate_CountryButton, hmcPage.B2CPriceRules_SearchInput, country, 10);
			Common.sleep(10000);
			fillB2CPriceRuleDetails(driver, hmcPage.B2BpriceSimulate_StoreButton, hmcPage.B2CPriceRules_SearchInput, store, 10);
			if (Common.checkElementDisplays(driver, hmcPage.B2BpriceSimulate_SMBPriceTierButton, 10)) {
				Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_SMBPriceTierButton, 30);
				Thread.sleep(1000);
				hmcPage.B2BpriceSimulate_SMBPriceTierButton.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
				hmcPage.B2CPriceRules_SearchInput.sendKeys(smbPriceTier);
				hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
			}
			fillB2CPriceRuleDetails(driver, hmcPage.B2CPriceSimulator_catalogVersion, hmcPage.B2CPriceRules_SearchInput, catalog, 10);
			Common.waitElementClickable(driver, hmcPage.B2CPriceSimulator_priceDate, 12);
			hmcPage.B2CPriceSimulator_priceDate.clear();
			hmcPage.B2CPriceSimulator_priceDate.sendKeys("2018-07-01 00:00:00");
			hmcPage.B2CPriceSimulator_priceDate.sendKeys(Keys.ENTER);
			Common.sleep(3000);
			CommonFunction.Common.scrollToElement(driver, hmcPage.B2CPriceSimulator_product);
			Common.waitElementClickable(driver, hmcPage.B2CPriceSimulator_product, 120);
			hmcPage.B2CPriceSimulator_product.click();
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 13);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(partNumber);
			WebElement productResult = driver.findElement(By.xpath("//span[text()='" + partNumber + "']"));
			Common.waitElementVisible(driver, productResult);
			productResult.click();
			Common.sleep(1500);
			hmcPage.B2CPriceSimulator_debug.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Common.sleep(5000);
	}

	/**
	 * @Owner Shuangshuang
	 * @Parameter:
	 * @Usage:
	 */
	public static void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
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
		Thread.sleep(1500);
	}

	/**
	 * @Owner Shuangshuang
	 * @Parameter:
	 * @Usage: Debug in HMC->Price setting->Price Rules-> Search rule group then
	 *         delete
	 */
	public static void deleteRule(WebDriver driver, HMCPage hmcPage, String ruleType, String ruleGroup) throws InterruptedException {

		try {
			Thread.sleep(3000);
			String xpath = "//span[text()='" + ruleType + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, ruleType, hmcPage.B2CPriceRules_ruleType, xpath);
			xpath = "//span[text()='" + ruleGroup + "' and @class='select2-match']";
			while (true) {
				Thread.sleep(1000);
				Common.waitElementClickable(driver, hmcPage.B2CPriceRules_group, 30);
				hmcPage.B2CPriceRules_group.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
				hmcPage.B2CPriceRules_SearchInput.clear();
				hmcPage.B2CPriceRules_SearchInput.sendKeys(ruleGroup);
				int count = driver.findElements(By.xpath(xpath)).size();
				if (count != 0) {
					List<WebElement> rules = driver.findElements(By.xpath(xpath));
					rules.get(0).click();
					hmcPage.B2CPriceRules_FilterBtn.click();
					WebElement deleteBtn = driver.findElement(By.xpath("//td[contains(text(),'" + ruleGroup + "')]/..//a[contains(text(),'Delete')]"));
					Common.waitElementClickable(driver, deleteBtn, 30);
					deleteBtn.click();
					Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
					hmcPage.B2CPriceRules_deleteInput.clear();
					hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
					Thread.sleep(3000);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_clearAll);
				}

				if (count == 1 || count == 0)
					break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Owner Qianqian
	 * @Parameter
	 * @Usage Check the order status in HMC is Completed and check the order xml is
	 *        created.
	 */
	public static void HMCOrderCheck(WebDriver driver, HMCPage page1, String OrderNumber) throws Exception {
		page1.Home_Order.click();
		page1.Home_Order_Orders.click();
		page1.Home_Order_OrderID.clear();
		page1.Home_Order_OrderID.sendKeys(OrderNumber);
		page1.Home_Order_OrderSearch.click();
		Thread.sleep(5000);
		if (!page1.Home_Order_OrderStatus.getText().contains("Completed")) {
			Thread.sleep(5000);
			page1.Home_Order_OrderSearch.click();
		}
		if (!page1.Home_Order_OrderStatus.getText().contains("Completed"))
			Assert.fail("Order status is not completed!");
		page1.Home_Order_OrderDetail.click();
		// page1.OrderReload.click();
		Thread.sleep(5000);
		page1.Home_Order_OrderAdmin.click();
		Thread.sleep(5000);
		if (!page1.Orders_OrderXML.getText().contains("xml"))
			Assert.fail("XML is not generated!");
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Get YB06 in order xml.
	 */
	public static String GetYB06Value(HMCPage hmcPage) throws Exception {
		String YBXML = hmcPage.Orders_OrderXML.getText().substring(hmcPage.Orders_OrderXML.getText().indexOf("YB06"));
		return YBXML.split("<text>")[1].substring(0, 1);
	}

	/**
	 * @Owner Qianqian
	 * @Parameter
	 * @Usage search Online Product
	 */
	public static void searchOnlineProduct(WebDriver driver, HMCPage hmcPage, String productNo) {
		if (!Common.checkElementDisplays(driver, hmcPage.Home_ProductsLink, 3)) {
			hmcPage.Home_CatalogLink.click();
		}
		hmcPage.Home_ProductsLink.click();
		if (!Common.checkElementDisplays(driver, hmcPage.Catalog_ArticleNumberTextBox, 3)) {
			hmcPage.Products_SearchPartOpen.click();
		}
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productNo);
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		Common.sleep(1000);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(2000);
	}

	/**
	 * @Owner Qianqian
	 * @Parameter
	 * @Usage search Staged Product
	 */
	public static void searchStagedProduct(WebDriver driver, HMCPage hmcPage, String productNo) {
		if (!Common.checkElementDisplays(driver, hmcPage.Home_ProductsLink, 3)) {
			hmcPage.Home_CatalogLink.click();
		}
		hmcPage.Home_ProductsLink.click();
		if (!Common.checkElementDisplays(driver, hmcPage.Catalog_ArticleNumberTextBox, 3)) {
			hmcPage.Products_SearchPartOpen.click();
		}
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productNo);
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogStaged.click();
		Common.sleep(1000);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatStagedLinkInSearchResult);
		Common.sleep(2000);
	}

	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static void searchWebsites(HMCPage hmcPage, TestData testData) {
		hmcPage.hmcHome_WCMS.click();
		hmcPage.wcmsPage_websites.click();
		hmcPage.WCMS_Website_ID.clear();
		hmcPage.WCMS_Website_ID.sendKeys(testData.B2C.getWebsites());
		hmcPage.WCMS_Website_SearchButton.click();

	}

	public static String getBaseProduct(HMCPage hmcPage) {
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		return baseProduct;

	}

	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static void hotUpdate(WebDriver driver, HMCPage hmcPage, String testProduct) throws InterruptedException {
		// try {
		System.out.println("hot update start");
		driver.switchTo().defaultContent();
		// HMC -> System -> Facet search -> Indexer hot-update wizard :Solr
		// job
		hmcPage.Home_System.click();
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_indexerHotUpdWiz.click();
		// Set solr facet search configuration
		Common.switchToWindow(driver, 2);
		hmcPage.IndexerHotUpdate_solrConfigName.click();
		hmcPage.IndexerHotUpdate_mcnemob2bIndex.click();
		hmcPage.IndexerHotUpdate_nextBtn.click();
		hmcPage.IndexerHotUpdate_indexTyeDD.click();
		hmcPage.IndexerHotUpdate_productIndexType.click();
		hmcPage.IndexerHotUpdate_updateIndexRadioBtn.click();
		hmcPage.IndexerHotUpdate_nextBtn.click();
		Common.rightClick(driver, hmcPage.IndexerHotUpdate_emptyRowToAddProduct);
		hmcPage.IndexerHotUpdate_addProductOption.click();
		Common.switchToWindow(driver, 3);
		;
		// product number
		hmcPage.IndexerHotUpdate_articleNumber.sendKeys(testProduct);
		// catalog version
		hmcPage.IndexerHotUpdate_catalogSelect.click();
		hmcPage.IndexerHotUpdate_multiCountryOption.click();
		hmcPage.IndexerHotUpdate_searchBtn.click();
		Common.doubleClick(driver, hmcPage.IndexerHotUpdate_searchResult);
		Common.switchToWindow(driver, 2);
		;
		// Assert.assertEquals(hmcPage.IndexerHotUpdate_articleNum.getText(),
		// testProduct);
		hmcPage.IndexerHotUpdate_startJobBtn.click();
		// System.out.println("Clicked on the start button to start the index update
		// job!!");
		Thread.sleep(10000);
		// System.out.println("waited 240000");
		new WebDriverWait(driver, 240000).until(ExpectedConditions.visibilityOf(hmcPage.IndexerHotUpdate_indexSuccessMsgBox));
		Assert.assertEquals(hmcPage.IndexerHotUpdate_indexSuccessMsgBox.getText(), "Indexing finished successfully.");
		hmcPage.IndexerHotUpdate_doneBtn.click();
		// System.out.println("Solr Job Done!!");
		Common.switchToWindow(driver, 1);
		;
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_System.click();
		System.out.println("hot update end");
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }
	}

}
