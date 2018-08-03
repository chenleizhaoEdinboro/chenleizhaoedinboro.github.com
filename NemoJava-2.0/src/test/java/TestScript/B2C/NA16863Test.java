package TestScript.B2C;

import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.Assert;
import org.testng.ITestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.testng.annotations.AfterTest;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA16863Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String testProduct;
	public float webPrice = 599999;
	public String ruleName;
	public String ruleID;
	DecimalFormat df = new DecimalFormat("#.00");

	public NA16863Test(String store) {
		this.Store = store;
		this.testName = "NA-16863";
	}

	private enum EnumTestData {
		ctoPro, country, country1, unit, store;
	}

	private String getData(String store, EnumTestData dataName) {
		if (store.equals("AU")) {
			switch (dataName) {
			case ctoPro:
				return "30BKCTO1WWENAU1";
			case country:
				return "Australia";
			case country1:
				return "[AU] Australia";
			case unit:
				return "AU Public unit";
			case store:
				return "[auweb] AU Web Store";
			default:
				return null;
			}
		} else if (store.equals("NZ")) {
			switch (dataName) {
			case ctoPro:
				return "30B3CTO1WWENNZ3";
			case country:
				return "New Zealand";
			case country1:
				return "[NZ] New Zealand";
			case unit:
				return "NZ Public unit";
			case store:
				return "[nzweb] NZ Web Store";
			default:
				return null;
			}
		} else if (store.equals("US")) {
			switch (dataName) {
			case ctoPro:
				return "30BHCTO1WWENUS0";
			case country:
				return "United States";
			case country1:
				return "[US] United States";
			case unit:
				return "US web store unit";
			case store:
				return "[usweb] US Web Store";
			default:
				return null;
			}
		} else if (store.equals("JP")) {
			switch (dataName) {
			case ctoPro:
				return "30BKCTO1WWJAJP0";
			case country:
				return "Japan";
			case country1:
				return "[JP] Japan";
			case unit:
				return "JP public store unit";
			case store:
				return "[jpweb] JP Public Store";
			default:
				return null;
			}
		} else if (store.equals("IE")) {
			switch (dataName) {
			case ctoPro:
				return "30BCCTO1WWENIE0";
			case country:
				return "Ireland";
			case country1:
				return "[IE] Ireland";
			case unit:
				return "Ireland store";
			case store:
				return "[ieweb] Ireland Public Web Store";
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	@Test(alwaysRun = true, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void NA16863(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);

			ruleName = testName + this.Store + Common.getDateTimeString();
			System.out.println(ruleName);
			testProduct = getData(testData.Store, EnumTestData.ctoPro);

			// check rule and if have web price rule then delete
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)) {
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();

			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, getData(this.Store, EnumTestData.country1), getData(this.Store, EnumTestData.store), "Nemo Master Multi Country Product Catalog - Online", getData(this.Store, EnumTestData.ctoPro));
			String webRule = "";
			String instantRule = "";
			String promoRule = "";
			if (Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_webGroup, 5)) {
				webRule = hmcPage.B2CPriceSimulator_webGroup.getText();
				System.out.println("webPrice rule exist:" + webRule);
			}
			if (Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_instantGroup, 5)) {
				instantRule = hmcPage.B2CPriceSimulator_instantGroup.getText();
				System.out.println("instantSaving Price rule exist:" + instantRule);
			}
			if (Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_promoGroup, 5)) {
				promoRule = hmcPage.B2CPriceSimulator_promoGroup.getText();
				System.out.println("promoPrice rule exist:" + promoRule);
			}
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			Thread.sleep(1500);
			// delete rules
			for (int i = 0; i < 3; i++) {
				if (webRule != "") {
					HMCCommon.deleteRule(driver, hmcPage, "Web Prices", webRule);
					webRule = "";
					System.out.println("Rule Deleted:" + webRule);
				}
				if (instantRule != "") {
					HMCCommon.deleteRule(driver, hmcPage, "Instant Savings", instantRule);
					instantRule = "";
					System.out.println("Rule Deleted:" + instantRule);
				}
				if (promoRule != "") {
					HMCCommon.deleteRule(driver, hmcPage, "Web Prices", promoRule);
					promoRule = "";
					System.out.println("Rule Deleted:" + promoRule);
				}

			}
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			createRule(hmcPage, testProduct, ruleName);
			Dailylog.logInfoDB(1, "Created Web Price rule:" + webPrice, Store, testName);

			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testProduct);
			b2cPage.Product_viewModel.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
			Thread.sleep(10000);

			String originPrice;
			String Qty2Price;
			String chanedQty2Price;
			String defaultOptionPrice;
			String changedOptionPrice;

			if (Common.checkElementExists(driver, b2cPage.newCTO_MemoryChanedOption, 50)) {
				System.out.println("Go to CTO Page--------");
				originPrice = b2cPage.newCTO_PBYourPrice.getText();
				Dailylog.logInfoDB(2, "Web Price:" + originPrice, Store, testName);
				if (Common.checkElementExists(driver, b2cPage.newCTO_MemoryChangeButton, 5)) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.newCTO_MemoryChangeButton);
				}
				changedOptionPrice = b2cPage.newCTO_MemoryChanedOption.getText();

				// Change Memory Qty to 2
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.newCTO_MemoryChangeQtyButton);
				System.out.println("NewCTO Page Memory Change Qty Button clicked");
				b2cPage.newCTO_MemoryChangeQty2.click();
				System.out.println("NewCTO Page Memory Change Qty2 clicked");
				Common.sleep(10000);
				Qty2Price = b2cPage.newCTO_PBYourPrice.getText();
				Dailylog.logInfoDB(3, "Web Price After Changed QTY_2:" + Qty2Price, Store, testName);

				// Change Option
				Dailylog.logInfoDB(4, "Price of Changed Memory:" + changedOptionPrice, Store, testName);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.newCTO_MemoryChanedOption);
				System.out.println("NewCTO PageMemory Chaned Option clicked");
				Thread.sleep(10000);
				chanedQty2Price = b2cPage.newCTO_PBYourPrice.getText();
				Dailylog.logInfoDB(5, "Prce After Changed Memory_Qty_2 :" + chanedQty2Price, Store, testName);

				defaultOptionPrice = b2cPage.newCTO_MemoryDefaultOptionPrice.getText();
				Dailylog.logInfoDB(6, "Price of default Memory:" + defaultOptionPrice, Store, testName);
				Thread.sleep(3000);

			} else {
				originPrice = b2cPage.cto_WebPrice.getText();
				Dailylog.logInfoDB(2, "Web Price:" + originPrice, Store, testName);

				changedOptionPrice = b2cPage.ctoPage_MemoryChanedOptionPrice.getText();
				Dailylog.logInfoDB(4, "Price of Changed Memory:" + changedOptionPrice, Store, testName);

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.ctoPage_MemoryChangeQtyButton);
				System.out.println("ctoPage Memory Change QtyButton clicked");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.ctoPage_MemoryChangeQty2);
				System.out.println("ctoPage Memory Change Qty2 clicked");
				Common.sleep(3000);
				Qty2Price = b2cPage.cto_WebPrice.getText();
				Dailylog.logInfoDB(3, "Web Price After Changed QTY_2:" + Qty2Price, Store, testName);

				b2cPage.ctoPage_MemoryChanedOption.click();
				chanedQty2Price = b2cPage.cto_WebPrice.getText();
				Dailylog.logInfoDB(5, "Prce After Changed Memory_Qty_2 :" + chanedQty2Price, Store, testName);

				defaultOptionPrice = b2cPage.ctoPage_MemoryDefaultOptionPrice.getText();
				Dailylog.logInfoDB(6, "Price of default Memory:" + defaultOptionPrice, Store, testName);
			}
			Assert.assertEquals(getPriceValue(originPrice), webPrice, 0);
			Assert.assertEquals(getPriceValue(Qty2Price), webPrice + getPriceValue(defaultOptionPrice), 0.1);
			if(changedOptionPrice.contains("+")||changedOptionPrice.contains("-"))
				Assert.assertEquals(getPriceValue(chanedQty2Price), (getPriceValue(Qty2Price) + getPriceValue(defaultOptionPrice) + getPriceValue(changedOptionPrice)), 0.1);
			else
				Assert.assertEquals(getPriceValue(chanedQty2Price), (getPriceValue(Qty2Price) + 2 * (getPriceValue(changedOptionPrice) - getPriceValue(defaultOptionPrice))), 0.1);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@AfterTest
	public void rollBack() {
		try {
			SetupBrowser();
			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)) {
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			HMCCommon.deleteRule(driver, hmcPage, "Web Prices", ruleName);

		} catch (Exception e) {
			e.printStackTrace();
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

	public String createRule(HMCPage hmcPage, String testProduct, String ruleName) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;

		System.out.println("Create rules***********************");
		Thread.sleep(5000);
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_CreateNewGroup, 120);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_WebPriceOption.click();
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
		dataInput = getData(testData.Store, EnumTestData.country);
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// Material
		xpath = "//span[contains(text(),'" + testProduct + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = getData(testData.Store, EnumTestData.unit);
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

		// Web price Value
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(String.valueOf(webPrice));
		System.out.println("Web Price:" + webPrice);
		Thread.sleep(2000);

		// Add Price Rule To Group
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver, By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
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
		Thread.sleep(3000);
		WebElement showRuleID = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
		String priceRlueID = driver.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]")).getText();
		System.out.println("Price Rule ID is : " + priceRlueID);
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();
		return priceRlueID;
	}

	public float getPriceValue(String Price) {
		if (Price.contains("FREE") || Price.contains("INCLUDED")) {
			return 0;
		}
		if (Price.contains("\n"))
			Price = Price.substring(0, Price.indexOf("\n"));
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("SG", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("£", "");
		Price = Price.replaceFirst(" £", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("￥", "");
		Price = Price.replaceAll("\\-", "");
		Price = Price.replaceAll("\\+", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

}
