package TestScript.API;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SWAT835Test extends SuperTestClass {

	public HMCPage hmcPage;
	private String country;
	private String unit;

	private String testProduct;
	private String priceRlueID = "";
	private String ruleName = "";

	private String productType = "";
	float salesP;
	String floorP;
	String webP;

	public SWAT835Test(String store, String productType, String ctoPro, String country, String unit, String floorP,
			String webP, float salesP) {
		this.Store = store;
		this.productType = productType;
		this.testProduct = ctoPro;
		this.country = country;
		this.unit = unit;
		this.salesP = salesP;
		this.floorP = floorP;
		this.webP = webP;
		this.testName = "SWAT-835";
	}

	@Test(alwaysRun = true)
	public void SWAT835(ITestContext ctx) throws InterruptedException, MalformedURLException {
		this.prepareTest();

		hmcPage = new HMCPage(driver);

		try {
			String[] activeRule;

			activeRule = checkRule(testProduct);

			if (!activeRule[0].equals("")) {
				// delete floor price rule
				deleteRule(driver, hmcPage, "Floor Prices", activeRule[0]);
			}

			if (!activeRule[1].equals("")) {
				// delete web price
				deleteRule(driver, hmcPage, "Web Prices", activeRule[1]);
			}

			if (!activeRule[2].equals("")) {
				// delete sales(instant saving)
				deleteRule(driver, hmcPage, "Instant Savings", activeRule[2]);
			}

			if (!activeRule[3].equals("")) {
				// delete promo(ecoupon)
				deleteRule(driver, hmcPage, "eCoupon Discounts", activeRule[3]);
			}

			priceRlueID = createRule("Floor");

			createRule("Web");

			createRule("Instant");

			createRule("eCoupon");

			// // Approve Floor Price Rule in HMC
			approveRule(priceRlueID);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public String createRule(String ruleType) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;

		System.out.println("Create rules***********************");
		ruleName = this.Store + this.productType + ruleType;
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
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);

		if (ruleType.equals("Instant")) {
			hmcPage.B2CPriceRules_InstantSavingOption.click();
		} else if (ruleType.equals("Floor")) {
			hmcPage.B2CPriceRules_FloorPriceOption.click();
		} else if (ruleType.equals("Web")) {
			hmcPage.B2CPriceRules_WebPriceOption.click();
		} else if (ruleType.equals("eCoupon")) {
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

		if (ruleType.equals("Instant")) {
			// Click Complex
			float discount = 50;
			// float salesP = 1000;
			hmcPage.B2CPriceRules_Complex.click();
			hmcPage.B2CPriceRules_AddThreshold.click();
			hmcPage.B2CPriceRules_AddThreshold.click();
			hmcPage.B2CPriceRules_Threshold1.clear();
			hmcPage.B2CPriceRules_Threshold1.sendKeys(salesP + "");
			hmcPage.B2CPriceRules_Value1.clear();
			hmcPage.B2CPriceRules_Value1.sendKeys(discount + "");
			hmcPage.B2CPriceRules_Fix1.click();
			hmcPage.B2CPriceRules_Unit1.click();
			hmcPage.B2CPriceRules_Threshold2.clear();
			hmcPage.B2CPriceRules_Threshold2.sendKeys((salesP + 50) + "");
			hmcPage.B2CPriceRules_Value2.clear();
			hmcPage.B2CPriceRules_Value2.sendKeys((discount + 10) + "");
			hmcPage.B2CPriceRules_Fix2.click();
			hmcPage.B2CPriceRules_Unit2.click();
		} else if (ruleType.equals("Floor")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(floorP);
			Thread.sleep(2000);

		} else if (ruleType.equals("eCoupon")) {
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
			hmcPage.B2CPriceRules_ecouponMessageInput
					.sendKeys("<span style=\"color: #fdda97\">Valid through 10/31/2017</span>");
			;

			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					hmcPage.B2CPriceRules_ecouponMessageAdd);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					hmcPage.B2CPriceRules_ecouponMessageSave);

			// Priority
			hmcPage.B2CPriceRules_ecouponPriority.clear();
			hmcPage.B2CPriceRules_ecouponPriority.sendKeys("500");

			// ecoupon price Value
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys("10");
			Thread.sleep(2000);

		} else if (ruleType.equals("Web")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(webP);
			Thread.sleep(2000);

		}

		// Material
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
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
		if (Common.isElementExist(driver,
				By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
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
			WebElement showRuleID = driver
					.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
			priceRlueID = driver
					.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]"))
					.getText();
			System.out.println("Price Rule ID is : " + priceRlueID);

		}

		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();
		return priceRlueID;
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

	public void approveRule(String ruleID) throws InterruptedException {
		System.out.println("Approve rules***********************");
		driver.switchTo().defaultContent();
		hmcPage.Home_System.click();
		hmcPage.B2CPriceRules_Types.click();
		hmcPage.Types_Identifier.clear();
		hmcPage.Types_Identifier.sendKeys("PriceB2CRule");
		hmcPage.Types_Search.click();
		Common.doubleClick(driver, hmcPage.Types_ResultPriceB2CRule);
		Common.waitElementVisible(driver, hmcPage.Types_OpenEditorNewWindow);
		hmcPage.Types_OpenEditorNewWindow.click();
		switchToWindow(driver, 1);
		Common.waitElementVisible(driver, hmcPage.Types_OpenOrganizer);
		hmcPage.Types_OpenOrganizer.click();
		switchToWindow(driver, 2);
		hmcPage.Types_SearchValueInput.clear();
		hmcPage.Types_SearchValueInput.sendKeys(ruleID);
		hmcPage.Types_Search.click();
		Thread.sleep(5000);
		WebElement target = driver.findElement(By.xpath("(//div[contains(text(),'" + ruleID + "')])[1]"));
		Common.doubleClick(driver, target);
		hmcPage.Types_PriceB2CRuleStatus.clear();
		hmcPage.Types_PriceB2CRuleStatus.sendKeys("1");
		Thread.sleep(5000);
		hmcPage.Types_SaveBtn.click();
		System.out.println("Floor Price Rule is approved! Rule ID: " + ruleID);
		driver.close();
		Thread.sleep(500);
		switchToWindow(driver, 1);
		driver.close();
		Thread.sleep(500);
		switchToWindow(driver, 0);
		hmcPage.Home_System.click();
	}

	public void switchToWindow(WebDriver driver, int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
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

	public void deleteRule(WebDriver driver, HMCPage hmcPage, String ruleType, String ruleGroup)
			throws InterruptedException {
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
				WebElement deleteBtn = driver.findElement(
						By.xpath("//td[contains(text(),'" + ruleGroup + "')]/..//a[contains(text(),'Delete')]"));
				Common.waitElementClickable(driver, deleteBtn, 30);
				deleteBtn.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
				hmcPage.B2CPriceRules_deleteInput.clear();
				hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();",
						hmcPage.B2CPriceRules_deleteConfirm);
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
		hmcPage.Home_PriceSettings.click();

	}

	public void loginPricingCockpit() {
		if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
			hmcPage.PricingCockpit_username.clear();
			hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
			hmcPage.PricingCockpit_password.clear();
			hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
			hmcPage.PricingCockpit_Login.click();
		}

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
		Thread.sleep(5000);

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

}
