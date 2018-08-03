package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class NA28051Test extends SuperTestClass {
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	private String testProduct;
	private String country;
	private String unit;

	public NA28051Test(String store, String country, String unit) {
		this.Store = store;
		this.country = country;
		this.unit = unit;
		this.testName = "COMM-331";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "uxui", "p2", "b2c" })
	public void NA28051(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			testProduct = testData.B2C.getDefaultCTOPN();
			String instantRuleName = this.Store + "InstantSavings";
			String eCouponRuleName = this.Store + "eCouponDiscounts";
			deleteRule(driver, hmcPage, "Instant Savings", instantRuleName);
			deleteRule(driver, hmcPage, "eCoupon Discounts", eCouponRuleName);

			String[] activeRule;
			activeRule = checkRule(testProduct);
			if (!activeRule[2].equals(""))
				deleteRule(driver, hmcPage, "Instant Savings", activeRule[2]);

			if (!activeRule[3].equals(""))
				deleteRule(driver, hmcPage, "eCoupon Discounts", activeRule[3]);

			String testUrl = testData.B2C.getHomePageUrl() + "/p/" + testProduct + "/customize?";
			boolean isYouSaveDisplay = false;
			boolean isCouponDislay = false;
			boolean isConfigDisplay = false;
			String instantDiscount = "10.00";
			String ecouponDiscount = "15.00";
			verifySteps(testUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(1, "no rules pass", Store, testName);

			// telesales
			loginB2CTele();
			String teleUrl = testData.B2C.getTeleSalesUrl() + "/p/" + testProduct + "/customize?";
			verifySteps(teleUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(1, "tele no rules pass", Store, testName);

			// instant saving
			createRule("Instant Savings", instantDiscount, unit, false);
			isYouSaveDisplay = true;
			isConfigDisplay = true;
			verifySteps(testUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(2, "instant saving pass", Store, testName);

			// telesales
			loginB2CTele();
			verifySteps(teleUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(2, "tele instant saving pass", Store, testName);

			// instant saving + non-stakable ecoupon
			isYouSaveDisplay = false;
			isCouponDislay = true;
			createRule("eCoupon Discounts", ecouponDiscount, unit, false);
			verifySteps(testUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(3, "instant saving + non-stakable ecoupon pass", Store, testName);

			// telesales
			loginB2CTele();
			verifySteps(teleUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(3, "tele instant saving + non-stakable ecoupon pass", Store, testName);

			// instant saving + stackable ecoupon
			isYouSaveDisplay = true;
			deleteRule(driver, hmcPage, "eCoupon Discounts", eCouponRuleName);
			createRule("eCoupon Discounts", ecouponDiscount, unit, true);
			verifySteps(testUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(4, "instant saving +stakable ecoupon pass", Store, testName);

			// telesales
			loginB2CTele();
			verifySteps(teleUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(4, "tele instant saving +stakable ecoupon pass", Store, testName);

			// only ecoupon
			isYouSaveDisplay = false;
			deleteRule(driver, hmcPage, "eCoupon Discounts", instantRuleName);
			verifySteps(testUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(5, "ecoupon pass", Store, testName);

			// telesales
			loginB2CTele();
			verifySteps(teleUrl, isYouSaveDisplay, isCouponDislay, isConfigDisplay, instantDiscount, ecouponDiscount, eCouponRuleName);
			Dailylog.logInfoDB(5, "tele ecoupon pass", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
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

	public String[] checkRule(String product) throws InterruptedException {
		String floorRule = "";
		String webRule = "";
		String instantsavingRule = "";
		String ecouponRule = "";
		String dataInput;
		String xpath;

		System.out.println("Check rules***********************");
		driver.get(testData.HMC.getHomePageUrl());
		// driver.manage().deleteCookieNamed(hmcCookieName);
		// Thread.sleep(1000);
		// driver.get(testData.HMC.getHomePageUrl());
		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
		}

		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);
		hmcPage.PricingCockpit_B2CPriceSimulator.click();
		Thread.sleep(8000);

		// Country
		dataInput = "[" + testData.Store.substring(0, 2).toUpperCase() + "] " + country;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceSimulator_country, xpath);

		// B2C store
		dataInput = "[" + testData.B2C.getStore() + "]";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceSimulator_store, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceSimulator_catalogVersion, xpath);

		// Date
		hmcPage.B2CPriceSimulator_priceDate.sendKeys(Keys.ENTER);

		// Product
		dataInput = testProduct;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Product", dataInput, hmcPage.B2CPriceSimulator_product, xpath);

		// Debug
		hmcPage.B2CPriceSimulator_debug.click();

		Common.waitElementVisible(driver, hmcPage.B2CPriceSimulator_simulatorResults);

		// Get floor priceRule
		try {
			floorRule = hmcPage.B2CPriceSimulator_floorGroup.getText();
			System.out.println(floorRule);
		} catch (NoSuchElementException e) {
			System.out.println("No active floor price rule: " + product);
		}

		// Get web price rule
		try {
			webRule = hmcPage.B2CPriceSimulator_webGroup.getText();
			System.out.println(webRule);
		} catch (NoSuchElementException e) {
			System.out.println("No active web price rule: " + product);
		}

		// Get instantsaving rule
		try {

			instantsavingRule = hmcPage.B2CPriceSimulator_instantGroup.getText();
			System.out.println(instantsavingRule);
		} catch (NoSuchElementException e) {
			System.out.println("No active instantsaving price rule: " + product);
		}

		// Get ecoupon rule
		try {
			ecouponRule = hmcPage.B2CPriceSimulator_promoGroup.getText();
			System.out.println(ecouponRule);
		} catch (NoSuchElementException e) {
			System.out.println("No active ecoupon rule: " + product);
		}

		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();

		String[] rules = new String[] { floorRule, webRule, instantsavingRule, ecouponRule };
		return rules;
	}

	public void deleteRule(WebDriver driver, HMCPage hmcPage, String ruleType, String ruleGroup) throws InterruptedException {
		String dataInput;
		String xpath;

		System.out.println("Delete rule: " + ruleGroup + "***********************");
		driver.get(testData.HMC.getHomePageUrl());
		// driver.manage().deleteCookieNamed(hmcCookieName);
		// Thread.sleep(1000);
		// driver.get(testData.HMC.getHomePageUrl());
		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
		}

		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);
		// loginPricingCockpit();
		hmcPage.PricingCockpit_B2CPriceRules.click();

		// rule type
		Thread.sleep(8000);

		xpath = "//span[text()='" + ruleType + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Rule Type", ruleType, hmcPage.B2CPriceRules_ruleType, xpath);

		// group
		dataInput = ruleGroup;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		while (true) {
			Thread.sleep(1000);
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_group, 30);
			hmcPage.B2CPriceRules_group.click();
			// hmcPage.B2CPriceRules_group.click();
			Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
			int count = driver.findElements(By.xpath(xpath)).size();
			System.out.println("floor price rule count: " + count);
			if (count != 0) {
				List<WebElement> rules = driver.findElements(By.xpath(xpath));
				rules.get(0).click();
				// filter
				hmcPage.B2CPriceRules_FilterBtn.click();
				// delete
				WebElement deleteBtn = driver.findElement(By.xpath("//td[contains(text(),'" + ruleGroup + "')]/..//a[contains(text(),'Delete')]"));
				Common.waitElementClickable(driver, deleteBtn, 30);
				deleteBtn.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
				hmcPage.B2CPriceRules_deleteInput.clear();
				hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
				// hmcPage.B2CPriceRules_deleteConfirm.click();
				System.out.println("Rule Deleted!");
				// clear all
				Thread.sleep(5000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_clearAll);
				Thread.sleep(5000);
			}

			if (count == 1 || count == 0)
				break;

		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();

	}

	public String createRule(String ruleType, String discountPrice, String unit, Boolean isStaakble) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		String priceRlueID = null;
		System.out.println("Create rules***********************");
		String ruleName = this.Store + ruleType.replace(" ", "");
		driver.get(testData.HMC.getHomePageUrl());

		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
		}

		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);

		// loginPricingCockpit();
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Thread.sleep(5000);
		Common.javascriptClick(driver, hmcPage.B2CPriceRules_CreateNewGroup);
		// hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);

		if (ruleType.equals("Instant Savings")) {
			hmcPage.B2CPriceRules_InstantSavingOption.click();
		} else if (ruleType.equals("Floor")) {
			hmcPage.B2CPriceRules_FloorPriceOption.click();
		} else if (ruleType.equals("Web")) {
			hmcPage.B2CPriceRules_WebPriceOption.click();
		} else if (ruleType.equals("eCoupon Discounts")) {
			hmcPage.B2CPriceRules_eCouponDiscountOption.click();
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// Floor price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td")).get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}

		// Country
		dataInput = country;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// B2Cunit
		dataInput = unit;
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);

		if (ruleType.equals("Instant Savings")) {
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);

		} else if (ruleType.equals("Floor")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("eCoupon Discounts")) {
			// display ecopon message
			hmcPage.B2CPriceRules_ecouponMessageDisplay.click();
			hmcPage.B2CPriceRules_ecouponMessageEdit.click();

			// language
			String language;
			if ((this.Store).contains("_")) {
				language = "en_" + this.Store.split("_")[0];
			} else if (this.Store == "JP") {
				language = "ja_JP";
			} else if (this.Store == "AU") {
				language = "en";
			} else if (this.Store == "BR") {
				language = "pt";
			} else {
				language = "en_" + this.Store;
			}
			System.out.println(language);

			xpath = "//span[text()='" + language + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Language", language, hmcPage.B2CPriceRules_ChooseCountry, xpath);

			hmcPage.B2CPriceRules_ecouponMessageInput.clear();
			hmcPage.B2CPriceRules_ecouponMessageInput.sendKeys("<span style=\"color: #fdda97\">Valid through 10/31/2017</span>");

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageAdd);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageSave);

			// Priority
			hmcPage.B2CPriceRules_ecouponPriority.clear();
			hmcPage.B2CPriceRules_ecouponPriority.sendKeys("500");

			if (isStaakble)
				Common.javascriptClick(driver, hmcPage.B2CPriceRules_StackableCheckBox);
			// hmcPage.B2CPriceRules_StackableCheckBox.click();

			// ecoupon price Value
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("Web")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		}

		// Material
		xpath = "//span[contains(text(),'" + testProduct + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// Add Price Rule To Group
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		// hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		// hmcPage.B2CPriceRules_CreateGroup.click();
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver, By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			hmcPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Thread.sleep(10000);
		// Record Price Rule ID
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			hmcPage.B2CPriceRules_CloseBtn.click();
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);

		System.out.println("Clicked filter!");
		Thread.sleep(3000);
		if (ruleType.equals("Floor")) {
			WebElement showRuleID = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
			priceRlueID = driver.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]")).getText();
			System.out.println("Price Rule ID is : " + priceRlueID);

		}

		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		return priceRlueID;
	}

	private void loginB2CTele() throws InterruptedException {
		String loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
		driver.get(loginUrl);
		if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='asmLogoutForm']/fieldset/button"), 10)) {
			b2cPage.SignoutASM.click();
			Thread.sleep(5000);
			if (Common.isAlertPresent(driver)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
		}

		B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
		B2CCommon.loginASMAndStartSession(driver, b2cPage, "customer", testData.B2C.getLoginID());

	}

	private String selectCVCTO(boolean isNewUI) {
		String cvText = "";
		String cvPrice = "";

		if (isNewUI) {
			By expandCategoriesX = By.xpath(Common.convertWebElementToString(b2cPage.cto_expandCategories));
			if (Common.checkElementDisplays(driver, expandCategoriesX, 3))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.cto_expandCategories);
			if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItem)), 3)) {
				cvText = b2cPage.CTO_unselectedItemText.getText();
				cvPrice = b2cPage.CTO_unselectedItemPrice.getText();
				// b2cPage.CTO_unselectedItem.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.CTO_unselectedItem);
				// TODO 价格
			} else {
				Dailylog.logInfoDB(5, "change cv in CTO page failed, no cv to change", Store, testName);
			}
		} else {
			if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld)), 3)) {
				cvText = b2cPage.CTO_unselectedItemTextOld.getText();
				cvPrice = b2cPage.CTO_unselectedItemPriceOld.getText();
				System.out.println(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld));
				Dailylog.logInfoDB(5, "cvText: " + cvText, Store, testName);
				Dailylog.logInfoDB(5, "cvPrice: " + cvPrice, Store, testName);
				b2cPage.CTO_unselectedItemTextOld.click();
				// TODO 价格
			} else {
				Dailylog.logInfoDB(5, "change cv in CTO page failed, no cv to change", Store, testName);
			}
		}
		return cvText;
	}

	private String selectOptionPB(boolean isNewUI) throws InterruptedException {
		String testItem = "";
		String testItemID = "";
		if (isNewUI) {
			testItem = Common.convertWebElementToString(b2cPage.PB_accessoryItem);
			if (Common.checkElementDisplays(driver, By.xpath(testItem), 10)) {
				testItemID = b2cPage.PB_addAccessoryItemID.getAttribute("value");
				Common.scrollToElement(driver, b2cPage.PB_accessoryItem);
				Common.javascriptClick(driver, b2cPage.PB_accessoryItem);
			}
		} else {
			testItem = "//div[@data-tabname='" + "Accessories" + "']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']/../input";
			if (Common.checkElementDisplays(driver, By.xpath(testItem), 5)) {
				testItemID = driver.findElement(By.xpath(testItem)).getAttribute("value");
				driver.findElement(By.xpath(testItem)).click();
			}
		}
		return testItemID;
	}

	private void verifySteps(String testUrl, boolean isYouSaveDisplay, boolean isCouponDislay, boolean isConfigDisplay, String instantDiscount, String ecouponDiscount, String eCouponRuleName) throws InterruptedException {
		driver.get(testUrl);
		boolean isNewUI;
		isNewUI = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_newconfiguratorHeader)));
		String cvText = "";
		// your price
		try {
			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("CTO_orderTotal"), 60), "Your price label should display");
		} catch (StaleElementReferenceException e) {
			System.out.println("~~~~~~~~~~~~~~");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("CTO_orderTotal"), 60), "Your price label should display");
		}
		Assert.assertTrue(Common.checkElementDisplays(driver, By.id("ctoYourPrice"), 10), "Your price should display");
		Assert.assertEquals(b2cPage.CTO_yourPriceLabel.getText().trim(), "Your Price");

		// savings price
		if (isYouSaveDisplay) {
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'you-save-label')]"), 10), "You save label should display");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'you-save-price')]"), 10), "You save price should display");
			Assert.assertEquals(b2cPage.CTO_youSavaLabel.getText().trim(), "Item Discount");
			String expectedPrice = "-$" + instantDiscount;
			Assert.assertEquals(b2cPage.CTO_youSavaPrice.getText().trim(), expectedPrice);
		} else {
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'you-save-label')]"), 5), "You save label should not display");
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'you-save-price')]"), 5), "You save price should not display");
		}

		// ecoupon price
		if (isCouponDislay) {
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'useCoupon-label')]"), 10), "useCoupon-label should display");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//lable[contains(@class,'useCoupon-code')]"), 10), "useCoupon-code should display");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'useCoupon-price')]"), 10), "useCoupon-price should display");
			Assert.assertEquals(b2cPage.CTO_couponCode.getText().trim(), eCouponRuleName);
			String expectedPrice = "-$" + ecouponDiscount;
			Assert.assertEquals(b2cPage.CTO_couponPrice.getText().trim(), expectedPrice);
		} else {
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'useCoupon-label')]"), 5), "useCoupon-label should not display");
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//lable[contains(@class,'useCoupon-code')]"), 5), "useCoupon-code should not display");
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//div[contains(@class,'useCoupon-price')]"), 5), "useCoupon-price should not display");
		}

		// CTO As configured
		cvText = selectCVCTO(isNewUI);
		Dailylog.logInfoDB(0, "selected cvText: " + cvText, Store, testName);
		if (isConfigDisplay) {
			try {
				Assert.assertTrue(Common.checkElementDisplays(driver, By.id("w-label"), 10), "As cofigured label should display");
			} catch (StaleElementReferenceException e) {
				Assert.assertTrue(Common.checkElementDisplays(driver, By.id("w-label"), 10), "As cofigured label should display");
			}
			Assert.assertEquals(b2cPage.CTO_asConfigLabel.getText().trim(), "As Configured");
		} else {
			try {
				Assert.assertFalse(Common.checkElementDisplays(driver, By.id("w-label"), 5), "As cofigured label should display");
			} catch (StaleElementReferenceException e) {
				Assert.assertFalse(Common.checkElementDisplays(driver, By.id("w-label"), 5), "As cofigured label should display");
			}
		}

		// PB As configured
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		} catch (StaleElementReferenceException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		}
		Common.sleep(5000);
		String accessoryID = selectOptionPB(isNewUI);
		Dailylog.logInfoDB(0, "accessoryID: " + accessoryID, Store, testName);
		if (!isConfigDisplay) {
			if (Common.isElementExist(driver, By.xpath("//input[@value='" + accessoryID + "']/..//p[@class='del-price']"), 10)) {
				Assert.assertTrue(Common.checkElementDisplays(driver, By.id("w-label"), 60), "As cofigured label should display");
				Assert.assertEquals(b2cPage.CTO_asConfigLabel.getText().trim(), "As Configured");
			}else {
				Assert.assertFalse(Common.checkElementDisplays(driver, By.id("w-label"), 60), "As cofigured label should display");
			}
		} else {
			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("w-label"), 60), "As cofigured label should display");
			Assert.assertEquals(b2cPage.CTO_asConfigLabel.getText().trim(), "As Configured");
		}
	}
}
