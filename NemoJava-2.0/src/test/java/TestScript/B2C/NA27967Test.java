package TestScript.B2C;

import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27967Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String Country;
	public String testProduct;
	public String Unit;
	public String ruleName = "NA27967";
	public String modal = "";

	public NA27967Test(String store, String country, String unit) {
		this.Store = store;
		this.Country = country;
		this.Unit = unit;
		this.testName = "NA-27967";
	}

	@Test(alwaysRun = true, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void NA27967(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			testProduct = testData.B2C.getDefaultCTOPN();
			System.out.println(testProduct);
			modal = testProduct.substring(0, 10);

			// Step 1 B2C Price Simulator
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, Country, testData.B2C.getStore(),
					"Nemo Master Multi Country Product Catalog - Online", testProduct);
			By listRule = By.xpath("//td[@id='list']//div[@id='groups']//div[@id='container']/samp/i");
			String listRuleGroup;
			if (Common.checkElementDisplays(driver, listRule, 10)) {
				listRuleGroup = hmcPage.B2CpriceSimulate_listRuleGroup.getText();
				driver.switchTo().defaultContent();
				HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
				hmcPage.PricingCockpit_B2CPriceRules.click();
				Thread.sleep(1500);
				HMCCommon.deleteRule(driver, hmcPage, "List Price Override", listRuleGroup);
				driver.switchTo().defaultContent();
				HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, Country, testData.B2C.getStore(),
						"Nemo Master Multi Country Product Catalog - Online", testData.B2C.getDefaultCTOPN());
			}

			// List: 226000 Tax: 25400 List + GST: 251400 CTO Base: 118500 Web: 236520
			String listPrice = hmcPage.B2CpriceSimulate_ListPrice.getText();
			String textPrice = hmcPage.B2CpriceSimulate_TaxPrice.getText();
			String listAndGstPrice = hmcPage.B2CpriceSimulate_ListAndGstPrice.getText();
			String ctoBasePrice = hmcPage.B2CpriceSimulate_ctoBasePrice.getText();
			String webPrice = hmcPage.B2BpriceSimulate_webPrice.getText();
			double dListPrice = getSimuPrice(listPrice);
			double dTaxPrice = getSimuPrice(textPrice);
			double dListAndGstPrice = getSimuPrice(listAndGstPrice);
			double dCtoBasePrice = getSimuPrice(ctoBasePrice);
			double dWebPrice = getSimuPrice(webPrice);
			Dailylog.logInfoDB(
					1, "dListPrice: " + dListPrice + " dTaxPrice: " + dTaxPrice + " dListAndGstPrice: "
							+ dListAndGstPrice + "dCtoBasePrice: " + dCtoBasePrice + " dWebPrice: " + dWebPrice,
					Store, testName);

			// Step 2 create rule List Price Override
			String price = "100000";
			String priceRlueID = createRule(modal, ruleName, Country, Unit, price);
			Dailylog.logInfoDB(2, "Rule created: " + priceRlueID, Store, testName);

			// Step3 B2C Price Simulator
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, Country, testData.B2C.getStore(),
					"Nemo Master Multi Country Product Catalog - Online", testProduct);
			listPrice = hmcPage.B2CpriceSimulate_ListPrice.getText();
			textPrice = hmcPage.B2CpriceSimulate_TaxPrice.getText();
			listAndGstPrice = hmcPage.B2CpriceSimulate_ListAndGstPrice.getText();
			ctoBasePrice = hmcPage.B2CpriceSimulate_ctoBasePrice.getText();
			webPrice = hmcPage.B2BpriceSimulate_webPrice.getText();
			double dListPrice2 = getSimuPrice(listPrice);
			double dTaxPrice2 = getSimuPrice(textPrice);
			double dListAndGstPrice2 = getSimuPrice(listAndGstPrice);
			double dCtoBasePrice2 = getSimuPrice(ctoBasePrice);
			double dWebPrice2 = getSimuPrice(webPrice);
			double cvListPrice = getCVPrice();
			double cvListOverridePrice = getCVOverridePrice();
			double taxRate = 0.08; // 18085 will set the tax rate to 0.08

			Dailylog.logInfoDB(3,
					"dListPrice: " + dListPrice + " dTaxPrice: " + dTaxPrice + " dListAndGstPrice: " + dListAndGstPrice
							+ "dCtoBasePrice: " + dCtoBasePrice + " dWebPrice: " + dWebPrice + " cvListPrice: "
							+ cvListPrice + " cvListOverridePrice: " + cvListOverridePrice,
					Store, testName);
		    System.out.println("dCtoBasePrice2: ");
		    System.out.println(dCtoBasePrice2);
		    System.out.println(getSimuPrice(price));
		    
		    System.out.println("dListPrice2: ");
		    System.out.println(dListPrice2);
		    System.out.println(cvListPrice + dCtoBasePrice2);
		    
		    System.out.println("dTaxPrice2: ");
		    System.out.println(dTaxPrice2);
		    System.out.println((dCtoBasePrice2 + cvListOverridePrice) * taxRate);
		    
		    System.out.println("dListAndGstPrice2: ");
		    System.out.println(dListAndGstPrice2);
		    System.out.println(dListPrice2 + dTaxPrice2);
		    
		    System.out.println("dWebPrice2: ");
		    System.out.println(dWebPrice2);
		    System.out.println(dCtoBasePrice2 + cvListOverridePrice + dTaxPrice2);
		    
		    
		    
			Assert.assertEquals(dCtoBasePrice2, getSimuPrice(price), "dCtoBasePrice2");
			Assert.assertEquals(dListPrice2, cvListPrice + dCtoBasePrice2, "dListPrice2");
			Assert.assertEquals(dTaxPrice2, (dCtoBasePrice2 + cvListOverridePrice) * taxRate, "dTaxPrice2");
			Assert.assertEquals(dListAndGstPrice2, dListPrice2 + dTaxPrice2, "dListAndGstPrice2");
			Assert.assertEquals(dWebPrice2, dCtoBasePrice2 + cvListOverridePrice + dTaxPrice2, "dWebPrice2");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(alwaysRun = true, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void rollback() throws MalformedURLException, InterruptedException {
		System.out.println("rollback");
		SetupBrowser();
		hmcPage = new HMCPage(driver);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Thread.sleep(1500);
		HMCCommon.deleteRule(driver, hmcPage, "List Price Override", ruleName);
	}

	private double getSimuPrice(String price) {
		return Double.parseDouble(price.replace("?", "").trim());
	}

	public String createRule(String testProduct, String ruleName, String country, String unit, String price)
			throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;

		System.out.println("Create rules***********************");
		driver.get(testData.HMC.getHomePageUrl());
		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			hmcPage.Home_PriceSettings.click();
		}
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_listPriceOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// Floor price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from datehmcPage.B2CPriceRules_ValidFrom
		hmcPage.B2CPriceRules_ValidFrom.clear();
		hmcPage.B2CPriceRules_ValidFrom.sendKeys("2018-06-01 00:00:00");
		hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);

		// Country
		dataInput = country;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// Material
		xpath = "(//span[contains(text(),'" + testProduct + "')]/../../div)[1]";
		// xpath = "//span[contains(text(),'" + testProduct
		// + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = unit;
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text()) and @class='branch']";
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		target.click();
		// Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);

		// Floor price Value
		dataInput = price;
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(dataInput);
		System.out.println("List override price:" + dataInput);
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
		WebElement showRuleID = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
		String priceRlueID = driver
				.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]")).getText();
		System.out.println("Price Rule ID is : " + priceRlueID);
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

	private double getCVPrice() {
		double price = 0;
		for (WebElement ele : hmcPage.B2CpriceSimulate_cvListPrice) {
			if (!ele.getText().trim().equals("0") && !ele.getText().contains("n/a")) {
				price = price + getSimuPrice(ele.getText());
			}
		}
		return price;
	}

	private double getCVOverridePrice() {
		double price = 0;
		for (WebElement ele : hmcPage.B2CpriceSimulate_cvListOverridePrice) {
			if (!ele.getText().trim().equals("0") && !ele.getText().contains("n/a")) {
				price = price + getSimuPrice(ele.getText());
			}
		}
		return price;
	}
}
