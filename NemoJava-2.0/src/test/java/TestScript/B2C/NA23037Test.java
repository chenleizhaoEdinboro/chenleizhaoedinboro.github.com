package TestScript.B2C;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23037Test extends SuperTestClass {
	public B2CPage B2CPage;
	public HMCPage HMCPage;
	public String productNo;
	public String partNumberbackup = "20HNCTO1WWENAU5";

	public NA23037Test(String Store) {
		this.Store = Store;
		this.testName = "NA-23037";
	}

	@Test(alwaysRun = true, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void NA23037(ITestContext ctx) {
		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			B2CPage = new B2CPage(driver);
			HMCPage = new HMCPage(driver);

			// Validate if product is available
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultCTOPN());
			System.out.println("DefaultCTOPN:" + testData.B2C.getDefaultCTOPN());
			if (!Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"), 120)) {
				productNo = partNumberbackup;
			} else {
				productNo = testData.B2C.getDefaultCTOPN();
				productNo = "20L7CTO1WWENAU1";
			}
			System.out.println("partNumber:" + productNo);

			String ruleName = "Auto_NA2303";
			float productPrice;
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + productNo);
			Common.sleep(3000);
			productPrice = getPriceValue(B2CPage.Product_WebPrice.getText());

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(HMCPage, testData);
			HMCPage.Home_PriceSettings.click();
			HMCPage.Home_PricingCockpit.click();
			deleteRule(driver, ruleName);

			Dailylog.logInfoDB(1, "Create Instant Saving Rule: ", Store, testName);
			createRule(productNo, ruleName, productPrice, 50);

			driver.get(testData.B2C.getHomePageUrl() + "/p/" + productNo);
			String url = testData.B2C.getHomePageUrl() + "/p/" + productNo;
			System.out.println("url is :" + url);

			Common.sleep(5000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", B2CPage.PDP_AddToCartOrCustomize);
			getCTOConfiguratorPage(1, B2CPage);
			Common.sleep(10000);

			boolean newPBUI = false;
			newPBUI = Common.isElementExist(driver, By.xpath("//div[contains(text(),'Your Price')]"));
			System.out.println("newPBUI:" + newPBUI);

			if (newPBUI) {

				String disCountValue = driver.findElement(By.xpath("//div[contains(@class,'you-save-price')]")).getText().toString().trim();
				Dailylog.logInfoDB(4, "disCountValue is: " + disCountValue, Store, testName);
				assert getPriceValue(disCountValue) == 50;

				changeCV(productPrice);
				String disCountValue1 = driver.findElement(By.xpath("//div[contains(@class,'you-save-price')]")).getText().toString().trim();
				Dailylog.logInfoDB(5, "disCountValue1 is: " + disCountValue1, Store, testName);

				assert getPriceValue(disCountValue1) == 60;

			} else {
				String disCountValue = driver.findElement(By.xpath("//div[@class='you-save-label']/div[2]")).getText().toString().trim();
				Dailylog.logInfoDB(4, "disCountValue is: " + disCountValue, Store, testName);
				assert getPriceValue(disCountValue) == 50;

				changeCV(productPrice);
				String disCountValue1 = driver.findElement(By.xpath("//div[@class='you-save-label']/div[2]")).getText().toString().trim();
				Dailylog.logInfoDB(5, "disCountValue1 is: " + disCountValue1, Store, testName);

				assert getPriceValue(disCountValue1) == 60;
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void changeCV(float price) throws InterruptedException {
		System.out.println("Change CV --------");
		if (Common.isElementExist(driver, By.xpath("//div[@data-showtab='true'][not(contains(@class,'newPBEhiddenByRule'))]//button[text()='CHANGE']"))) {
			List<WebElement> ele = driver.findElements(By.xpath("//div[@data-showtab='true'][not(contains(@class,'newPBEhiddenByRule'))]//button[text()='CHANGE']"));
			for (WebElement e : ele) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
			}
			Thread.sleep(3000);
			List<WebElement> elem = driver.findElements(By.xpath("//div[contains(@class,'qa-configurator-selectionResult')][not(contains(@class,'selection-result--collapsed'))]//div[contains(@class,'qa-configurator-groupItem-price')][contains(text(),'+')]"));
			for (WebElement e : elem) {
				if (!e.getText().equals("")) {
					if (getPriceValue(e.getText()) > 50) {
						System.out.println("add option Price:" + e.getText());
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
						break;
					}
				}
			}

		} else {
			List<WebElement> ele = driver.findElements(By.xpath("//tbody/tr[@class='comp-item radio-item visible']/td/input"));
			for (WebElement e : ele) {
				System.out.println("Web Price After Select Option:" + driver.findElement(By.xpath(".//*[@id='w-price']")).getText());
				if (getPriceValue(driver.findElement(By.xpath(".//*[@id='w-price']")).getText()) < price + 100) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
				} else {
					break;
				}
			}
		}

	}

	public void deleteRule(WebDriver driver, String ruleGroup) throws InterruptedException {
		String dataInput;
		String xpath;

		System.out.println("Delete rule: " + ruleGroup + "***********************");

		driver.switchTo().frame(0);
		// loginPricingCockpit();
		HMCPage.PricingCockpit_B2CPriceRules.click();

		// rule type
		Thread.sleep(8000);
		dataInput = "Instant Savings";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, HMCPage, "Rule Type", dataInput, HMCPage.B2CPriceRules_ruleType, xpath);

		// group
		dataInput = ruleGroup;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		while (true) {
			Thread.sleep(1000);
			Common.waitElementClickable(driver, HMCPage.B2CPriceRules_group, 30);
			HMCPage.B2CPriceRules_group.click();
			// HMCPage.B2CPriceRules_group.click();
			Common.waitElementVisible(driver, HMCPage.B2CPriceRules_SearchInput);
			HMCPage.B2CPriceRules_SearchInput.clear();
			HMCPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
			int count = driver.findElements(By.xpath(xpath)).size();
			System.out.println("floor price rule count: " + count);
			if (count != 0) {
				List<WebElement> rules = driver.findElements(By.xpath(xpath));
				rules.get(0).click();
				// filter
				HMCPage.B2CPriceRules_FilterBtn.click();
				// delete
				WebElement deleteBtn = driver.findElement(By.xpath("//td[contains(text(),'" + ruleGroup + "')]/..//a[contains(text(),'Delete')]"));
				Common.waitElementClickable(driver, deleteBtn, 30);
				deleteBtn.click();
				Common.waitElementVisible(driver, HMCPage.B2CPriceRules_deleteInput);
				HMCPage.B2CPriceRules_deleteInput.clear();
				HMCPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", HMCPage.B2CPriceRules_deleteConfirm);
				System.out.println("Rule Deleted!");
				// clear all
				Thread.sleep(5000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", HMCPage.B2CPriceRules_clearAll);
				Thread.sleep(5000);
			}

			if (count == 1 || count == 0)
				break;

		}
		driver.switchTo().defaultContent();
		HMCPage.Home_PricingCockpit.click();

	}

	public float getPriceValue(String Price) {
		if (Price.contains("FREE") || Price.contains("INCLUDED")) {
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\+", "");
		Price = Price.replaceAll("\\[", "");
		Price = Price.replaceAll("\\]", "");
		Price = Price.replaceAll("add", "");
		Price = Price.replaceAll("-", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public void acceptPOPAlert() {
		try {
			Alert alt = driver.switchTo().alert();
			alt.accept();
		} catch (Exception e) {
			Dailylog.logInfoDB(5, "No alert Open", Store, testName);
		}
	}

	public void getCTOConfiguratorPage(int index, B2CPage page3) {
		if (index <= 3) {
			try {
				Common.sleep(5000);
				acceptPOPAlert();
				Common.waitElementClickable(driver, page3.Product_AddToCartBtn, 100);
				Dailylog.logInfoDB(2, "Configurator Page is display", Store, testName);
			} catch (Exception e) {
				Dailylog.logInfoDB(3, "Configurator Page is not display, retry " + index, Store, testName);
				driver.navigate().refresh();
				getCTOConfiguratorPage(index + 1, page3);
			}
		} else {
			Dailylog.logInfoDB(8, "Configurator Page is not display" + index, Store, testName);
			assert (index <= 3);
		}

	}

	public void createRule(String testProduct, String ruleName, float price, float discount) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		driver.switchTo().frame(0);

		// loginPricingCockpit();
		HMCPage.PricingCockpit_B2CPriceRules.click();
		// new WebDriverWait(driver, 10)
		// .until(ExpectedConditions.invisibilityOfElementLocated(By
		// .xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		HMCPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		HMCPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		HMCPage.B2CPriceRules_InstantSavingOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", HMCPage.B2CPriceRules_Continue);

		// Floor price name
		HMCPage.B2CPriceRules_PriceRuleName.clear();
		HMCPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from date
		HMCPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			HMCPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td")).get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			HMCPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}

		// Country
		dataInput = "Australia";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, HMCPage, "Country", dataInput, HMCPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, HMCPage, "Catalog", dataInput, HMCPage.B2CPriceRules_CatalogSelect, xpath);

		// Material
		xpath = "//span[contains(text(),'" + testProduct + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, HMCPage, "Material", testProduct, HMCPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = "AU Public unit";
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
		Common.waitElementClickable(driver, HMCPage.B2CPriceRules_B2CunitSelect, 30);
		HMCPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, HMCPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, HMCPage.B2CPriceRules_UnitSearch);
		HMCPage.B2CPriceRules_UnitSearch.clear();
		HMCPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);

		// Click Complex
		HMCPage.B2CPriceRules_Complex.click();
		HMCPage.B2CPriceRules_AddThreshold.click();
		HMCPage.B2CPriceRules_AddThreshold.click();
		HMCPage.B2CPriceRules_Threshold1.clear();
		HMCPage.B2CPriceRules_Threshold1.sendKeys(price + "");
		HMCPage.B2CPriceRules_Value1.clear();
		HMCPage.B2CPriceRules_Value1.sendKeys(discount + "");
		HMCPage.B2CPriceRules_Fix1.click();
		HMCPage.B2CPriceRules_Unit1.click();
		HMCPage.B2CPriceRules_Threshold2.clear();
		HMCPage.B2CPriceRules_Threshold2.sendKeys((price + 50) + "");
		HMCPage.B2CPriceRules_Value2.clear();
		HMCPage.B2CPriceRules_Value2.sendKeys((discount + 10) + "");
		HMCPage.B2CPriceRules_Fix2.click();
		HMCPage.B2CPriceRules_Unit2.click();

		// Add Price Rule To Group
		HMCPage.B2CPriceRules_AddPriceRuleToGroup.click();
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", HMCPage.B2CPriceRules_CreateGroup);
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver, By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			HMCPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			HMCPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Common.sleep(10000);
		driver.switchTo().defaultContent();
	}

	public void fillRuleInfo(WebDriver driver, HMCPage HMCPage, String name, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, HMCPage.B2CPriceRules_SearchInput);
		HMCPage.B2CPriceRules_SearchInput.clear();
		HMCPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);
	}
}
