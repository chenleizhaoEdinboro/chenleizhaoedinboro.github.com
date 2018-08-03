package TestScript.B2C;

import java.util.List;
import org.testng.annotations.AfterTest;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21734Test extends SuperTestClass{
	public B2CPage B2CPage;
	public HMCPage HMCPage;
	public String ruleName1;
	public String ruleName2;
	
	public NA21734Test(String Store){
		this.Store = Store;
		this.testName = "SHOPE-228";
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA21734(ITestContext ctx){
		try{
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			B2CPage = new B2CPage(driver);
			HMCPage = new HMCPage(driver);
			String productNo = "61B1JAT1EU";
			ruleName1 = "Auto_NA21734_Instant Savings South";
			ruleName2 = "Auto_NA21734_FRONLY";
			
			//--------------Step 1 :Create new rule group -> choose Instant Saving Discounts rule-----------
			driver.get(testData.HMC.getHomePageUrl());		
			HMCCommon.Login(HMCPage, testData);
			HMCPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			createRule(productNo,ruleName1,"1");
			Dailylog.logInfoDB(1,"Instant Saving Rule Created",Store, testName);
			
			//--------------Step 2 :Create new rule group -> choose Ecopon Discounts rule-----------
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			createEcoponRule(productNo,ruleName2,"2");
			Dailylog.logInfoDB(2,"Ecopon Rule Created",Store, testName);
			
			//--------------Step 3 :Go to B2C Price Simlator Country: ES-----------
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver,HMCPage,"[ES] Spain","[esweb] Spain Public Web Store",
					"Nemo Master Multi Country Product Catalog - Online",productNo);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@id='groups']/div/samp/i[contains(.,'"+ruleName1+"')]"), 5), "Rule Instant Saving display");
			float webPrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_webPrice.getText());
			float salePrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_salesPrice.getText());				
			Assert.assertEquals(webPrice-salePrice, 1, 0);
			Dailylog.logInfoDB(3,"Price for ES is correct on price simulate",Store, testName);
			
			//--------------Step 4 :Go to B2C Price Simlator Country: FR-----------
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver,HMCPage,"[FR] France","[frweb] France Public Web Store",
					"Nemo Master Multi Country Product Catalog - Online",productNo);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@id='groups']/div/samp/i[contains(.,'"+ruleName1+"')]"), 5), "Rule  Instant Saving display");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@id='groups']/div/samp/i[contains(.,'"+ruleName2+"')]"), 5), "Rule  Ecopon display");
			webPrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_webPrice.getText());
			salePrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_salesPrice.getText());
			float promoPrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_promoPrice.getText());
			Assert.assertEquals(webPrice-salePrice, 1, 0);
			Assert.assertEquals(salePrice-promoPrice, 2, 0);
			Dailylog.logInfoDB(4,"Price for FR is correct on price simulate",Store, testName);

			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	
	}
	
	 @AfterTest 
	    public void rollBack(){
	    	System.out.println("---------RollBack----------");
	    	
	    	try{
	    		SetupBrowser();
	    		HMCPage = new HMCPage(driver);
	    		driver.get(testData.HMC.getHomePageUrl());
	    		if(Common.checkElementExists(driver, HMCPage.Login_IDTextBox, 10)){
	    			HMCCommon.Login(HMCPage, testData);
	    		}
	    		HMCPage.Home_PriceSettings.click();
	    		HMCCommon.loginPricingCockpit(driver,HMCPage,testData);
	    		HMCPage.PricingCockpit_B2CPriceRules.click();
	    		HMCCommon.deleteRule(driver, HMCPage, "Instant Savings", ruleName1);
	    		HMCCommon.deleteRule(driver, HMCPage, "eCoupon Discounts", ruleName2);
	    		Dailylog.logInfoDB(5,"Rules deleted", Store,testName);
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }
	
	
	public void createRule(String testProduct,String ruleName,String discount)
			throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;

		HMCPage.PricingCockpit_B2CPriceRules.click();
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By
//						.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		HMCPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		HMCPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		HMCPage.B2CPriceRules_InstantSavingOption.click();
		((JavascriptExecutor) driver)
		.executeScript("arguments[0].click();",
				HMCPage.B2CPriceRules_Continue);

		// name
		HMCPage.B2CPriceRules_PriceRuleName.clear();
		HMCPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		
		//Priority
		HMCPage.B2CPriceRules_ecouponPriority.clear();
		HMCPage.B2CPriceRules_ecouponPriority.sendKeys("1000");

		// Validate from date
		HMCPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver
				.findElements(
						By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
				.size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver
					.findElements(
							By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			HMCPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver
					.findElements(
							By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			HMCPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}


		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, HMCPage, "Catalog", dataInput,
				HMCPage.B2CPriceRules_CatalogSelect, xpath);
		CommonFunction.Common.InnerScrollTop(driver, "modal fade in", "10000000");
		//CommonFunction.Common.scrollToElement(driver, HMCPage.B2CPriceRules_BaseProduceSelect);
		//baseProduct
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, HMCPage,testProduct, HMCPage.B2CPriceRules_BaseProduceSelect, xpath);
		System.out.println("Input testBaseProduct");

		CommonFunction.Common.scrollToElement(driver, HMCPage.B2CPriceRules_B2CunitSelect);
		// B2Cunit
		dataInput = "South";
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput
					+ "']/../../*[not(text())])[last()]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
		Common.waitElementClickable(driver,HMCPage.B2CPriceRules_B2CunitSelect, 30);
		HMCPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, HMCPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, HMCPage.B2CPriceRules_UnitSearch);
		HMCPage.B2CPriceRules_UnitSearch.clear();
		HMCPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);
		
		// Currency
		CommonFunction.Common.scrollToElement(driver, HMCPage.B2BpriceSimulate_CurrencyButton);
		dataInput = "EUR Euro";
		xpath = "//span[text()='" + dataInput + "']";
		fillRuleInfo(driver, HMCPage, "Currency", dataInput,
						HMCPage.B2BpriceSimulate_CurrencyButton, xpath);
		
		HMCPage.B2CPriceRules_dynamicRate.click();
		HMCPage.B2CPriceRules_DynamicMinusButton.click();
		HMCPage.B2CPriceRules_ecouponDollorButton.click();
		HMCPage.B2CPriceRules_dynamicValue.sendKeys(discount);
		
		// Add Price Rule To Group
		HMCPage.B2CPriceRules_AddPriceRuleToGroup.click();
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver)
		.executeScript("arguments[0].click();",
				HMCPage.B2CPriceRules_CreateGroup);
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common
				.isElementExist(
						driver,
						By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			HMCPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			HMCPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Common.sleep(10000);	
		driver.switchTo().defaultContent();
	}
	
	public void createEcoponRule(String testProduct,String ruleName,String discount)
			throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;

		HMCPage.PricingCockpit_B2CPriceRules.click();
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By
//						.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		HMCPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		HMCPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		HMCPage.B2CPriceRules_eCouponDiscountOption.click();
		((JavascriptExecutor) driver)
		.executeScript("arguments[0].click();",
				HMCPage.B2CPriceRules_Continue);

		// name
		HMCPage.B2CPriceRules_PriceRuleName.clear();
		HMCPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		
		//Priority
		HMCPage.B2CPriceRules_ecouponPriority.clear();
		HMCPage.B2CPriceRules_ecouponPriority.sendKeys("100");

		// Validate from date
		HMCPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver
				.findElements(
						By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
				.size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver
					.findElements(
							By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			HMCPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver
					.findElements(
							By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			HMCPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}
		
		HMCPage.B2CPriceRules_StackableCheckBox.click();
		HMCPage.B2CPriceRules_NumberOfCartCheckedout.click();
		HMCPage.B2CPriceRules_count.clear();
		HMCPage.B2CPriceRules_count.sendKeys("100");
			
		// Country
		dataInput="France";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		HMCCommon.fillRuleInfo(driver, HMCPage,dataInput, HMCPage.B2CPriceRules_CountrySelect, xpath);
		System.out.println("Input Country");
				
		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, HMCPage, "Catalog", dataInput,
				HMCPage.B2CPriceRules_CatalogSelect, xpath);
		CommonFunction.Common.InnerScrollTop(driver, "modal fade in", "10000000");
		//baseProduct
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, HMCPage,testProduct, HMCPage.B2CPriceRules_BaseProduceSelect, xpath);
		System.out.println("Input testBaseProduct");
		
		HMCPage.B2CPriceRules_dynamicRate.click();
		HMCPage.B2CPriceRules_DynamicMinusButton.click();
		HMCPage.B2CPriceRules_ecouponDollorButton.click();
		HMCPage.B2CPriceRules_dynamicValue.sendKeys(discount);
		
		// Add Price Rule To Group
		HMCPage.B2CPriceRules_AddPriceRuleToGroup.click();
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver)
		.executeScript("arguments[0].click();",
				HMCPage.B2CPriceRules_CreateGroup);
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common
				.isElementExist(
						driver,
						By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			HMCPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			HMCPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Common.sleep(10000);	
		driver.switchTo().defaultContent();
	}
	
	
	public void fillRuleInfo(WebDriver driver, HMCPage HMCPage, String name,
			String dataInput, WebElement ele1, String xpath)
			throws InterruptedException {
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
