package TestScript.B2C;

import java.net.MalformedURLException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
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

import java.util.Random;


public class NA19008Test extends SuperTestClass {
	public B2CPage b2cPage;
	public CTOPage ctoPage;
	public HMCPage hmcPage;
	private String ctoProductNo;
	private String bundleUrl;
	private String vLevel;
	private String cLevel;
	private String Language;
	private String Country;
	private String Language1;
	private String Language2;
	private String mtUrl;
	private String r3Url;
	Random rand = new Random();
	int n = rand.nextInt(500) + 1;

	public NA19008Test(String store, String language, String country, String language1, String language2) {
		this.Store = store;
		this.testName = "NA-19008";
		this.Language = language;
		this.Language1 = language1;
		this.Language2 = language2;
		this.Country = country;
	}

	@Test(alwaysRun = true, groups = {"shopgroup", "cto", "p2", "b2c"})
	public void NA19008(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			ctoPage = new CTOPage(driver);
			hmcPage = new HMCPage(driver);
			// ctoProductNo = testData.B2C.getDefaultCTOPN();
			ctoProductNo = "20KFCTO1WWENHK0";
			// n = 191;

			// go to b2c to check if default cto exist

			// get base product from HMC
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			hmcPage.Home_CatalogLink.click();
			Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(ctoProductNo);
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			String baseProduct = driver
					.findElement(By.xpath("//table[@title='baseProduct']//input[contains(@id,'baseProduct')]"))
					.getAttribute("value");
			baseProduct = baseProduct.substring(0, 15);

			// TODO Maintaining optional text for C level
			// bundle url -> C level -> option text A
			// login cto
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(3000);
			CTOCommon.Login(ctoPage, testData);
			Common.sleep(2000);
			checkAlert();

			// search product and go to CTO bundle
			// Common.waitElementClickable(driver, ctoPage.SearchPage_ModalSearchInput, 20);
			// checkAlert();
			// String mt = ctoProductNo.substring(0, 10);
			// Dailylog.logInfoDB(1, "CTO: " + ctoProductNo + " MT: " +
			// ctoProductNo.substring(0, 10), Store, testName);
			// ctoPage.SearchPage_ModalSearchInput.clear();
			// ctoPage.SearchPage_ModalSearchInput.sendKeys(mt);
			// By searchResult = By.xpath("//td[contains(.,'" + Language + "_" + Country +
			// "')]");
			// clickSearchResults(1, searchResult, mt);
			//
			// // wait for bundle page loading Common.sleep(30000); checkAlert();
			// String url = driver.getCurrentUrl();
			// System.out.println(url);
			// //
			// http://pre-c-hybris.lenovo.com/configurator_hana/#ca/nomenclature=22TP2TX260020F620F6CTO1WWENHK0~~~
			// String urlProduct = url.substring(url.indexOf(mt), url.indexOf(mt) + 15);
			// Dailylog.logInfoDB(1, urlProduct, Store, testName);
			// if (urlProduct.equals(ctoProductNo)) {
			// bundleUrl = url;
			// } else {
			// bundleUrl = url.replace(urlProduct, ctoProductNo);
			// Dailylog.logInfoDB(1, "Bundle Url: " + bundleUrl, Store, testName);
			// driver.get(bundleUrl);
			// }

			// http://pre-c-hybris.lenovo.com/configurator_hana/#
			// http://pre-c-hybris.lenovo.com/configurator_hana/#ca/nomenclature=22TP2TX260020F620F6CTO1WWENHK0~~~
			bundleUrl = testData.CTO.getHomePageUrl() + "ca/nomenclature=" + baseProduct + ctoProductNo + "~~~";
			driver.get(bundleUrl);
			Dailylog.logInfoDB(1, "Bundle Url: " + bundleUrl, Store, testName);

			// get visible C
			Common.sleep(6000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_firstOpen);
			Common.sleep(5000);
			cLevel = ctoPage.BundlePage_firstTitleID.getText();
			Dailylog.logInfoDB(1, "bundle-- optial TEXT first ID: " + cLevel, Store, testName);

			// get visible V
			vLevel = ctoPage.BundlePage_secondeTitleID.getText();
			Dailylog.logInfoDB(1, "bundle-- optial TEXT second ID: " + vLevel, Store, testName);

			// update option text in bundle
			editOPtionText("c", "Bundle", "default", n, false);

			// MT url -> C level -> option text B
			// switch to MT
			// http://pre-c-hybris.lenovo.com/configurator_hana/#ca/nomenclature=22TP2TX260020F6##################
			String mt = ctoProductNo.substring(0, 10);
			String replaceWordA = bundleUrl.substring(bundleUrl.indexOf(mt), bundleUrl.length());
			String replaceWordB = "#";
			for (int i = 1; i < bundleUrl.length() - bundleUrl.indexOf(mt); i++) {
				replaceWordB = replaceWordB + "#";
			}
			mtUrl = bundleUrl.replace(replaceWordA, replaceWordB);
			Dailylog.logInfoDB(1, "MT URl : " + mtUrl, Store, testName);
			driver.get(mtUrl);

			// wait for MT url loading
			Common.sleep(30000);
			checkAlert();
			editOPtionText("c", "MT", "default", n, false);

			// R3 url -> C level -> another language-> option text C
			// switch to R3
			// http://pre-c-hybris.lenovo.com/configurator_hana/#ca/nomenclature=22TP2TX260020F620F6CTO1WW########
			replaceWordA = bundleUrl.substring(bundleUrl.indexOf(mt) + 10, bundleUrl.length());
			replaceWordB = "#";
			for (int i = 1; i < bundleUrl.length() - (bundleUrl.indexOf(mt) + 10); i++) {
				replaceWordB = replaceWordB + "#";
			}
			r3Url = bundleUrl.replace(replaceWordA, replaceWordB);
			Dailylog.logInfoDB(1, "R3 URl : " + r3Url, Store, testName);
			driver.get(r3Url);

			// wait for r3 url loading
			Common.sleep(30000);
			checkAlert();
			editOPtionText("c", "R3", Language2, n, false);

			// Sync C level option text
			syncOption("c");

			// TODO Maintaining optional text for V level
			// bundle url -> V level -> option text A
			// wait for bundle page loading
			driver.get(bundleUrl);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("v", "Bundle", "default", n, false);

			// MT url -> V level -> option text B
			// switch to MT
			driver.get(mtUrl);
			// wait for MT url loading
			Common.sleep(30000);
			checkAlert();
			editOPtionText("v", "MT", "default", n, false);

			// R3 url -> V level -> another language-> option text C
			// switch to R3
			driver.get(r3Url);
			// wait for r3 url loading
			Common.sleep(30000);
			checkAlert();
			editOPtionText("v", "R3", Language2, n, false);

			// Sync V level option text
			syncOption("v");

			// Check in B2C
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + ctoProductNo);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(5000);
			b2cPage.PDP_ViewCurrentModelTab.click();
			Common.sleep(5000);
			b2cPage.B2C_PDP_CustomizeButton.click();
			Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 10);
			// check option text for c level
			checkRules("cOptionTest Bundle" + n, true);
			checkRules("cOptionTest MT" + n, false);
			checkRules("cOptionTest R3" + n, false);
			// cehck option text for v level
			b2cPage.B2C_CTO_expandCollapse.click();
			Common.sleep(1000);
			checkRules("vOptionTest Bundle" + n, true);
			checkRules("vOptionTest MT" + n, false);
			checkRules("vOptionTest R3" + n, false);

			driver.get(
					testData.B2C.getHomePageUrl().replace("/" + Language.toLowerCase(), "/" + Language1.toLowerCase())
							+ "/p/" + ctoProductNo);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(5000);
			b2cPage.PDP_ViewCurrentModelTab.click();
			Common.sleep(5000);
			b2cPage.B2C_PDP_CustomizeButton.click();
			Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 10);
			// check option text for c level
			checkRules("cOptionTest Bundle" + n, false);
			checkRules("cOptionTest MT" + n, false);
			checkRules("cOptionTest R3" + n, true);
			// cehck option text for v level
			b2cPage.B2C_CTO_expandCollapse.click();
			Common.sleep(5000);
			checkRules("vOptionTest Bundle" + n, false);
			checkRules("vOptionTest MT" + n, false);
			checkRules("vOptionTest R3" + n, true);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(alwaysRun = true, priority = 1, enabled = true)
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			System.out.println("rollback"); // roll back
			SetupBrowser();
			ctoPage = new CTOPage(driver);
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(3000);
			CTOCommon.Login(ctoPage, testData);
			Common.sleep(2000);
			checkAlert();

			// search product and go to CTO bundle
			driver.get(bundleUrl);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("c", "Bundle", "default", n, true);
			Dailylog.logInfoDB(1, "Rollback Bundle: " + bundleUrl, Store, testName);

			// switch to MT		
			driver.get(mtUrl);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("c", "MT", "default", n, true);
			Dailylog.logInfoDB(1, "Rollback MT: " + mtUrl, Store, testName);

			// switch to R3
			driver.get(r3Url);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("c", "R3", Language2, n, true);
			Dailylog.logInfoDB(1, "Rollback R3: " + r3Url, Store, testName);

			// Sync C level option text
			syncOption("c");
			Dailylog.logInfoDB(1, "Rollback Symc C level", Store, testName);

			// wait for bundle page loading
			driver.get(bundleUrl);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("v", "Bundle", "default", n, true);
			Dailylog.logInfoDB(1, "Rollback Bundle: " + bundleUrl, Store, testName);

			// switch to MT
			driver.get(mtUrl);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("v", "MT", "default", n, true);
			Dailylog.logInfoDB(1, "Rollback MT: " + bundleUrl, Store, testName);

			// switch to R3
			driver.get(r3Url);
			Common.sleep(30000);
			checkAlert();
			editOPtionText("v", "R3", Language2, n, true);
			Dailylog.logInfoDB(1, "Rollback R3: " + bundleUrl, Store, testName);
			
			// Sync V level option text
			syncOption("v");
			Dailylog.logInfoDB(1, "Rollback Symc V level", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void checkAlert() {
		try {
			Alert alt = driver.switchTo().alert();
			alt.accept();
		} catch (Exception e) {
			System.out.println("No alert Open");
		}
	}

	private void editOPtionText(String cv, String level, String language, int random, Boolean rollback) {
		while (Common.isElementExist(driver, By.id(("alert_button"))))
			driver.findElement(By.id("alert_button")).click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_translationsButton);
		System.out.println("clicked translation button");
		Common.sleep(5000);
		if (cv.toLowerCase().equals("c")) {
			System.out.println("C Level");
			if (!language.toLowerCase().equals("default")) {
				System.out.println("language: " + language);
				changeLanguage(language);
			}
			// change option text of test cLevel
			WebElement cOptionTestInput = driver
					.findElement(By.xpath("//input[@gid='" + cLevel + "' and @class='optional-text form-control']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", cOptionTestInput);
			Common.sleep(5000);
			cOptionTestInput.clear();
			if (!rollback) {
				cOptionTestInput.sendKeys("cOptionTest " + level + random);
				System.out.println("cOptionTest " + level + random);
			}
			Common.sleep(5000);
		} else if (cv.toLowerCase().equals("v")) {
			System.out.println("V Level");
			if (!language.toLowerCase().equals("default")) {
				System.out.println("language: " + language);
				changeLanguage(language);
			}
			WebElement cValueBtn = driver.findElement(By.xpath("//button[@href='#" + cLevel + "']"));
			Common.sleep(5000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", cValueBtn);
			WebElement vOptionTestInput = driver.findElement(
					By.xpath("//label[text()='Optional text']/../div/input[contains(@cid,'" + vLevel + "')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", vOptionTestInput);
			Common.sleep(5000);
			vOptionTestInput.clear();
			if (!rollback) {
				vOptionTestInput.sendKeys("vOptionTest " + level + random);
				System.out.println("vOptionTest " + level + random);
			}
			Common.sleep(5000);
		}

		// click save
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				ctoPage.BundlePage_translateTitle);
		ctoPage.BundlePage_translateSaveButton.click();
		System.out.println("translate Save button click");
	}

	private void changeLanguage(String language) {
		Common.waitElementClickable(driver, ctoPage.BundlePage_languageSel, 30);
		ctoPage.BundlePage_languageSel.click();
		WebElement lanOption = driver.findElement(By.xpath("//span[text()='" + language + "']"));
		Common.waitElementClickable(driver, lanOption, 30);
		lanOption.click();
		ctoPage.BundlePage_changeLocale.click();
		Common.sleep(5000);
	}

	private void syncOption(String cv) throws InterruptedException {
		// sync OPTIONAL TEXT
		Common.sleep(5000);
		driver.get(testData.CTO.getHomePageUrl() + "sync");
		// get first old result time
		Common.waitElementVisible(driver, ctoPage.HomePage_firstResult);
		String oldTime = ctoPage.HomePage_firstResult.getText();
		System.out.println("history time : " + oldTime);
		ctoPage.HomePage_syncRuleButton.click();
		System.out.println("Clicked delta data Sync Button");
		Common.sleep(5000);
		ctoPage.HomePage_ruleType.click();
		Common.sleep(5000);
		if (cv.toLowerCase().equals("c")) {
			ctoPage.HomePage_characterOptionText.click();
		} else if (cv.toLowerCase().equals("v")) {
			ctoPage.HomePage_valueOptionText.click();
		}
		Common.sleep(5000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				ctoPage.HomePage_confirmRuleTypes);
		Common.sleep(5000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.HomePage_confirmRuleTypes);
		Common.sleep(5000);
		ctoPage.HomePage_confirmYes.click();
		Common.sleep(30000);
		syncResults(1, oldTime);
	}

	private void syncResults(int index, String oldTime) throws InterruptedException {
		if (index <= 3) {
			ctoPage.HomePage_refreshButton.click();
			Common.sleep(8000);
			// Common.waitElementVisible(driver, ctoPage.HomePage_firstResult);
			String result = ctoPage.HomePage_firstResult.getText();
			System.out.println("after refresh first result : " + result);
			if (result.equals(oldTime)) {

				System.out.println("No Sync results ,refresh again!" + index);
				// wait 1 minutes,refresh
				Common.sleep(30000);
				syncResults(index + 1, oldTime);
			} else
				System.out.println("Sync rules success!");
		} else {
			System.out.println("Sync rules failed!");
			assert index <= 3;
		}
	}

	private void checkRules(String text, boolean status) {
		By s = By.xpath("//*[text()='" + text + "']");
		Assert.assertEquals(Common.isElementExist(driver, s, 5), status, text);
	}

	// private void clickSearchResults(int index, By searchResult, String productMT)
	// {
	// if (index <= 3) {
	// try {
	// Common.sleep(30000);
	// WebElement searchResultEle = driver.findElement(searchResult);
	// Common.waitElementClickable(driver, searchResultEle, 180);
	// searchResultEle.click();
	// Dailylog.logInfoDB(1, "Opened model Url", Store, testName);
	// } catch (Exception e) {
	// Dailylog.logInfoDB(1, "No results of model, retry " + index, Store,
	// testName);
	// ctoPage.SearchPage_ModalSearchInput.clear();
	// Common.sleep(500);
	// ctoPage.SearchPage_ModalSearchInput.sendKeys(productMT);
	// clickSearchResults(index + 1, searchResult, productMT);
	// }
	// } else {
	// Dailylog.logInfoDB(1, "No results displaying while searching model", Store,
	// testName);
	// assert index <= 3;
	// }
	// }
}
