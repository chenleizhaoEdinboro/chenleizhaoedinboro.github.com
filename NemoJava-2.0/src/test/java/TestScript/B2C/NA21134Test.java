package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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

public class NA21134Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;

	public NA21134Test(String store) {
		this.Store = store;
		this.testName = "NA-21134";
	}

	@Test(alwaysRun = true, priority = 0, enabled = true,groups= {"","storemgmt","p2","b2c"})
	public void NA21134(ITestContext ctx) {
		try {
			this.prepareTest();
			String TITLEAE = "AUTOTEST_PRODUCTS_EN_AE";
			String TITLEGB = "AUTOTEST_PRODUCTS_EN_GB";
			String storeID = "saind";
			String testUrl = testData.envData.getHttpsDomain() + "/sa/en/" + storeID;

			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			List<String> languages = new ArrayList<String>();
			languages.add("en_AE");
			maintainLanguage(storeID, languages);
			driver.get(testUrl);
			String source = driver.getPageSource();
			Assert.assertTrue(source.contains("ACC.config.language = (\"en_AE\""), "source en_AE");
			Dailylog.logInfoDB(0, "en_AE pass", Store, testName);

			languages.add("en_UK");
			maintainLanguage(storeID, languages);
			driver.get(testUrl);
			source = driver.getPageSource();
			Assert.assertTrue(source.contains("ACC.config.language = (\"en_AE\""));
			Dailylog.logInfoDB(0, "en_AE, en_UK pass", Store, testName);

			languages.clear();
			maintainLanguage(storeID, languages);
			driver.get(testUrl);
			driver.navigate().refresh();
			try {
				Common.waitElementVisible(driver, b2cPage.Home_errorHeading);
			} catch (Exception e) {
				Assert.fail("Home_errorHeading is not displayed after removing all languages");
			}
			Dailylog.logInfoDB(0, "remove all languages pass", Store, testName);

			languages.add("en_AE");
			maintainLanguage(storeID, languages);
			addFallback("en_AE", "en_GB");
			maintainContentSlots(driver, hmcPage, "cs_00006E7D", "ProductsNewNavSA", "en_AE");
			driver.get(testUrl);
			driver.navigate().refresh();
			String actual = b2cPage.Home_productTitle.getText();
			Assert.assertEquals(actual, TITLEAE);
			Dailylog.logInfoDB(0, TITLEAE + " pass", Store, testName);

			maintainContentSlots(driver, hmcPage, "cs_00006E7D", "ProductsNewNavSA", "en_GB");
			driver.get(testUrl);
			driver.navigate().refresh();
			actual = b2cPage.Home_productTitle.getText();
			Assert.assertEquals(actual, TITLEGB);
			Dailylog.logInfoDB(0, TITLEGB + " pass", Store, testName);

			maintainCategories(driver, hmcPage, "thinkpad-laptops", "en_AE");
			driver.get(testUrl + "/laptops/thinkpad-en-ae-name/c/thinkpad-laptops");
			// driver.navigate().refresh();
			actual = b2cPage.LaptopsL1_title.getText();
			Assert.assertEquals(actual, "AutoTest_thinkpad_en_AE_name");
			Dailylog.logInfoDB(0, "AutoTest_thinkpad_en_AE_name pass", Store, testName);
			actual = b2cPage.LaptopsL1_tagline.getText();
			Assert.assertEquals(actual, "AUTOTEST_EN_AE PREMIUM PERFORMANCE, LEGENDARY RELIABILITY");
			Dailylog.logInfoDB(0, "AUTOTEST_EN_AE PREMIUM PERFORMANCE, LEGENDARY RELIABILITY pass", Store, testName);

			maintainCategories(driver, hmcPage, "thinkpad-laptops", "en_GB");
			driver.get(testUrl + "/laptops/thinkpad-en-gb-name/c/thinkpad-laptops");
			// driver.navigate().refresh();
			actual = b2cPage.LaptopsL1_title.getText();
			Assert.assertEquals(actual, "AutoTest_thinkpad_en_GB_name");
			Dailylog.logInfoDB(0, "AutoTest_thinkpad_en_GB_name pass", Store, testName);
			actual = b2cPage.LaptopsL1_tagline.getText();
			Assert.assertEquals(actual, "AUTOTEST_EN_GB PREMIUM PERFORMANCE, LEGENDARY RELIABILITY");
			Dailylog.logInfoDB(0, "AUTOTEST_EN_GB PREMIUM PERFORMANCE, LEGENDARY RELIABILITY pass", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(alwaysRun = true, priority = 1, enabled = true)
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			Dailylog.logInfoDB(0, "Start rollback", Store, testName);
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			maintainCategories(driver, hmcPage, "thinkpad-laptops", "en");
			String storeID = "saind";
			String testUrl = testData.envData.getHttpsDomain() + "/sa/en/" + storeID;
			driver.get(testUrl + "/laptops/thinkpad/c/thinkpad-laptops");
			String actual = b2cPage.LaptopsL1_title.getText();
			Assert.assertEquals(actual, "thinkpad");
			Dailylog.logInfoDB(0, "thinkpad pass", Store, testName);
			actual = b2cPage.LaptopsL1_tagline.getText();
			Assert.assertEquals(actual, "PREMIUM PERFORMANCE, LEGENDARY RELIABILITY");
			Dailylog.logInfoDB(0, "PREMIUM PERFORMANCE, LEGENDARY RELIABILITY pass", Store, testName);

			maintainContentSlots(driver, hmcPage, "cs_00006E7D", "ProductsNewNavSA", "en");
			driver.get(testUrl);
			driver.navigate().refresh();
			actual = b2cPage.Home_productTitle.getText();
			Assert.assertEquals(actual, "PRODUCTS");
			Dailylog.logInfoDB(0, "PRODUCTS pass", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void maintainLanguage(String store, List<String> languages) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.HMCBaseCommerce_baseCommerce.click();
		hmcPage.HMCBaseCommerce_baseStore.click();
		hmcPage.baseStore_id.clear();
		hmcPage.baseStore_id.sendKeys(store);
		hmcPage.baseStore_search.click();
		Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
		hmcPage.baseStore_administration.click();
		if (!Common.checkElementDisplays(driver, hmcPage.BaseStore_languagesEmptyMsg, 5)) {
			Common.rightClick(driver, hmcPage.BaseStore_languagesTable);
			Common.waitElementClickable(driver, hmcPage.BaseStore_selectAll, 5);
			hmcPage.BaseStore_selectAll.click();
			Common.rightClick(driver, hmcPage.BaseStore_languagesTable);
			Common.waitElementClickable(driver, hmcPage.BaseStore_remove, 5);
			hmcPage.BaseStore_remove.click();
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
		}
		for (int i = 0; i < languages.size(); i++) {
			Common.rightClick(driver, hmcPage.BaseStore_languagesTable);
			Common.waitElementClickable(driver, hmcPage.HMC_add, 5);
			hmcPage.HMC_add.click();
			Common.switchToWindow(driver, 1);
			hmcPage.HMC_isoCode.sendKeys(languages.get(i));
			hmcPage.HMC_isoCode.sendKeys(Keys.ENTER);
			By results = By.xpath("//div[text()='" + languages.get(i) + "']");
			driver.findElement(results).click();
			hmcPage.HMC_use.click();
			Common.switchToWindow(driver, 0);
		}
		hmcPage.Common_SaveButton.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void addFallback(String origin, String fallback) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.hmc_internationalization.click();
		hmcPage.Internationalization_languages.click();
		hmcPage.HMC_isoCode.sendKeys(origin);
		hmcPage.HMC_isoCode.sendKeys(Keys.ENTER);
		Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
		By fallbackXpath = By.xpath("//table[@title='fallbackLanguages']//table//div[text()='" + fallback + "']");
		if (!Common.checkElementDisplays(driver, fallbackXpath, 5)) {
			Common.rightClick(driver, hmcPage.Languages_fallbackLanguages);
			hmcPage.HMC_add.click();
			Common.switchToWindow(driver, 1);
			hmcPage.HMC_isoCode.sendKeys(fallback);
			hmcPage.HMC_isoCode.sendKeys(Keys.ENTER);
			By results = By.xpath("//div[text()='" + fallback + "']");
			driver.findElement(results).click();
			hmcPage.HMC_use.click();
			Common.switchToWindow(driver, 0);
		}
		hmcPage.SaveButton.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void maintainContentSlots(WebDriver driver, HMCPage hmcPage, String contentSlotsID, String navigationNodeValue, String languageCode) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.hmcHome_WCMS.click();
		hmcPage.WCMS_contentSlot.click();
		hmcPage.ContentSlot_uid.sendKeys(contentSlotsID);
		hmcPage.ContentSlot_uid.sendKeys(Keys.ENTER);
		Common.doubleClick(driver, hmcPage.HMC_searchResultOnlie);
		By productsBarComponentXpath = By.xpath("//div[contains(@id,'ProductsBarComponent-SA-new')]");
		Common.doubleClick(driver, driver.findElement(productsBarComponentXpath));
		Common.switchToWindow(driver, 1);
		String navigationNodeValueOrigin = hmcPage.ContentSlot_navigationNode.getAttribute("value");
		if (!navigationNodeValueOrigin.contains(navigationNodeValue)) {
			hmcPage.ContentSlot_navigationNode.clear();
			hmcPage.ContentSlot_navigationNode.sendKeys(navigationNodeValue);
			Common.sleep(1000);
			hmcPage.ContentSlot_navigationNode.sendKeys(Keys.DOWN);
			hmcPage.ContentSlot_navigationNode.sendKeys(Keys.ENTER);
			hmcPage.Common_SaveButton.click();
		}
		Common.rightClick(driver, hmcPage.ContentSlot_navigationNode);
		hmcPage.HMC_internalEdit.click();
		hmcPage.CMSNavigationNode_title.click();
		Common.sleep(1000);
//		for (int i = 0; i < hmcPage.CMSNavigationNode_titleInput.size(); i++) {
//			if (!hmcPage.CMSNavigationNode_titleInput.get(i).getAttribute("value").equals("")) {
//				hmcPage.CMSNavigationNode_titleInput.get(i).clear();
//			}
//		}
		hmcPage.CMSNavigationNode_titleInputEN.clear();
		hmcPage.CMSNavigationNode_titleInputEN_AE.clear();
		hmcPage.CMSNavigationNode_titleInputEN_GB.clear();
		By titleXpath = By.xpath("//div[contains(text(),'" + languageCode + "')]/../..//input[contains(@id,'CMSNavigationNode.title')]");
		String titleText;
		if (!languageCode.equals("en"))
			titleText = "AutoTest_PRODUCTS_" + languageCode;
		else
			titleText = "PRODUCTS";
		driver.findElement(titleXpath).sendKeys(titleText);
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void maintainCategories(WebDriver driver, HMCPage hmcPage, String category, String languageCode) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.HMCHome_Catalog.click();
		hmcPage.Home_category.click();
		hmcPage.Category_code.sendKeys(category);
		Select catalogVersion = new Select(hmcPage.Category_catalogVersion);
		catalogVersion.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		Common.sleep(1000);
		hmcPage.Category_search.click();
		By result = By.xpath("//*[contains(text(),'MEA')]");
		driver.findElement(result).click();
		hmcPage.Category_name.clear();
		String categoryName;
		if (!languageCode.equals("en"))
			categoryName = "AutoTest_thinkpad_" + languageCode + "_name";
		else
			categoryName = "thinkpad";
		hmcPage.Category_name.sendKeys(categoryName);
		hmcPage.Category_pmi.click();
		hmcPage.Category_mkt_tagline.click();
		Common.sleep(1000);
//		for (int i = 0; i < hmcPage.Category_mkt_taglineInput.size(); i++) {
//			if (!hmcPage.Category_mkt_taglineInput.get(i).getAttribute("value").equals("")) {
//				hmcPage.Category_mkt_taglineInput.get(i).clear();
//			}
//		}
		hmcPage.Category_mkt_taglineInputEN.clear();
		hmcPage.Category_mkt_taglineInputEN_GB.clear();
		hmcPage.Category_mkt_taglineInputEN_AE.clear();
		By titleXpath = By.xpath("//div[contains(text(),'" + languageCode + "')]/../..//input[contains(@id,'mkt_tagline')]");
		String categoryTitle;
		if (!languageCode.equals("en"))
			categoryTitle = "AutoTest_" + languageCode + " Premium Performance, Legendary Reliability";
		else
			categoryTitle = "Premium Performance, Legendary Reliability";
		driver.findElement(titleXpath).sendKeys(categoryTitle);
		hmcPage.Common_SaveButton.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}
}
