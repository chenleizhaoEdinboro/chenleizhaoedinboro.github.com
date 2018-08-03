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
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17691Test extends SuperTestClass{
	public B2CPage B2CPage;
	public HMCPage HMCPage;
	public String ruleName;
	
	public NA17691Test(String Store){
		this.Store = Store;
		this.testName = "SHOPE-230";
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA17691(ITestContext ctx){
		try{
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			B2CPage = new B2CPage(driver);
			HMCPage = new HMCPage(driver);
			String productNo = testData.B2C.getDefaultMTMPN();
			System.out.println("Part Number:"+productNo);
			ruleName = "Auto_NA17691";
			
			//--------------Step 1 :Create new rule group-----------
			driver.get(testData.HMC.getHomePageUrl());		
			HMCCommon.Login(HMCPage, testData);
			HMCPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			createRule(productNo,ruleName,"50");
			Dailylog.logInfoDB(1,"Instant Saving Rule Created",Store, testName);
			
			//--------------Step 2 :There is Show,Add,Edit and Copy button on rule group level -----------
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			HMCPage.PricingCockpit_B2CPriceRules.click();	
			Common.sleep(3000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//tr/td[text()='Instant Savings']/following-sibling::td[text()='"+ruleName+"']"), 5), "Rule Auto_NA17691 display");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//tr/td[text()='"+ruleName+"']/following-sibling::td/div//span[contains(.,'Show')]"), 5), "Rule Show Button display");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//tr/td[text()='"+ruleName+"']/following-sibling::td/div/a[contains(.,'Edit')]"), 5), "Rule Edit Button display");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//tr/td[text()='"+ruleName+"']/following-sibling::td/div/a[contains(.,'Copy')]"), 5), "Rule Copy Button display");
			//--------------Step 3 :Click on the Copy button on the rule level -----------
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					driver.findElement(By.xpath("//tr/td[text()='"+ruleName+"']/following-sibling::td/div/a[contains(.,'Copy')]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					HMCPage.B2CPriceRules_EditRuleSaveButton);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//tr/td[text()='Instant Savings']/following-sibling::td[text()='"+ruleName+"_copy"+"']"), 5), "Rule Auto_NA17691 display");
			Dailylog.logInfoDB(2,"Instant Saving Rule Copy Created",Store, testName);
			
			//--------------Step 4 :Price Simulate show the NA17691 Price Rule_copy under sales-----------
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver,HMCPage,"[GB] United Kingdom","[gbweb] UK Public Web Store",
					"Nemo Master Multi Country Product Catalog - Online",productNo);			
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@id='groups']/div/samp/i[contains(.,'"+ruleName+"_copy')]"), 5), "Rule Copy display");
			float webPrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_webPrice.getText());
			float salePrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_salesPrice.getText());				
			Assert.assertEquals(webPrice-salePrice, 50, 0.1);
			Dailylog.logInfoDB(3,"Rule Copy display on price simulate",Store, testName);
			
			//--------------Step 5 :Delete Copy Rule-----------
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			HMCPage.PricingCockpit_B2CPriceRules.click();
			Common.sleep(3000);
			HMCCommon.deleteRule(driver, HMCPage, "Instant Savings", ruleName+"_copy");
			Dailylog.logInfoDB(4,"Rule Copy Deleted ",Store, testName);
			Thread.sleep(5000);
			//--------------Step 6 :Edit Rule-Change the Fix price to -60$ and click Save-----------
			Common.waitElementClickable(driver, HMCPage.B2CPriceRules_FilterBtn, 120);
			HMCPage.B2CPriceRules_FilterBtn.click();
			//edit rule 
			driver.findElement(By.xpath("//tr/td[text()='"+ruleName+"']/following-sibling::td/div//span[contains(.,'Show')]")).click();
			driver.findElement(By.xpath("//td[contains(.,'"+ruleName+"')]/div/div//tbody//td/a[@title='Edit Rule']")).click();
			HMCPage.B2CPriceRules_EditRuleDynamicValue.clear();
			HMCPage.B2CPriceRules_EditRuleDynamicValue.sendKeys("60");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					HMCPage.B2BPriceRules_PriceRuleSave);		
			Dailylog.logInfoDB(5,"Rule Edited ",Store, testName);
			
			//--------------Step 7 :Price Simulate Should show the NA17691 Price Rule under sales with one "-60" on the sales list-----------
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, HMCPage, testData);
			HMCCommon.B2CPriceSimulateDebug(driver,HMCPage,"[GB] United Kingdom","[gbweb] UK Public Web Store",
					"Nemo Master Multi Country Product Catalog - Online",productNo);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@id='groups']/div/samp/i[contains(.,'"+ruleName+"')]"), 5), "Rule Copy display");
			webPrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_webPrice.getText());
			salePrice=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(HMCPage.B2BpriceSimulate_salesPrice.getText());					
			Assert.assertEquals(webPrice-salePrice, 60, 0);
			Dailylog.logInfoDB(6,"Price display correct After Rule Edited ",Store, testName);
			
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	
	}
	
	@Test(alwaysRun = true, priority = 1, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
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
	    		Common.sleep(3000);
	    		HMCCommon.deleteRule(driver, HMCPage, "Instant Savings", ruleName);
	    		Dailylog.logInfoDB(7,"Rule deleted", Store,testName);
	    		
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

		// Country
		dataInput = "United Kingdom";
		xpath = "//span[text()='" + dataInput
				+ "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, HMCPage, "Country", dataInput,
				HMCPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, HMCPage, "Catalog", dataInput,
				HMCPage.B2CPriceRules_CatalogSelect, xpath);

		// Material
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, HMCPage, "Material", testProduct,
				HMCPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = "gbweb";
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
