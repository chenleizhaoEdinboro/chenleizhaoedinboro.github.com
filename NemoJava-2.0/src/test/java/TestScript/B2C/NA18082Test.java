package TestScript.B2C;

import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.ITestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
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
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import junit.framework.Assert;
import CommonFunction.Common;
import CommonFunction.CTOCommon;
import CommonFunction.B2CCommon;
import CommonFunction.DesignHandler.*;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.CTOPage;
import TestData.TestData;
import TestScript.SuperTestClass;


public class NA18082Test extends SuperTestClass{
	
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public CTOPage ctoPage;
	public String ruleName;
	public String defaultCPU;
	public float webPrice;
	public float fixedPrice=800;

	public NA18082Test(String store){
		this.Store= store;	
		this.testName= "NA-18082";
	}
	private enum EnumTestData {
		ctoPro, country,country1, unit,store;
	}
	private String getData(String store, EnumTestData dataName) {
		if (store.equals("USEPP")) {
			switch (dataName) {
			case ctoPro:
				return "20HRCTO1WWENAU9";
			case country:
				return "United States";
			case unit:
				return "US web store unit";		    
			default:
				return null;
			}
		
		} else if(store.equals("AU")) {
			switch (dataName) {
			case ctoPro:
//				return testData.B2C.getDefaultCTOPN();
//				return "20L9CTO1WWENAU1";
				return "20KNCTO1WWENAUH";
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
		}else if(store.equals("US")) {
			switch (dataName) {
			case ctoPro:
				return "20H4CTO1WWENAU4";
			case country:
				return "Hong Kong S.A.R. of China";
			case unit:
				return "HK public store unit";
			default:
				return null;
				}
		} else if(store.equals("CA")) {
			switch (dataName) {
			case ctoPro:
				
				return "20H4CTO1WWENAU4";
			case country:
				return "Canada";
			case unit:
				return "CA web store unit";
			default:
				return null;
			}
		} else if(store.equals("GB")) {
			switch (dataName) {
			case ctoPro:
				return "20H4CTO1WWENAU4";
			case country:
				return "United Kingdom";
			case unit:
				return "gbweb";
			default:
				return null;
			}
		}else{
			return null;
		}
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA18082(ITestContext ctx){
		try{			
			this.prepareTest();		
			b2cPage= new B2CPage(driver);
			hmcPage= new HMCPage(driver);
			ctoPage = new CTOPage(driver);
			ruleName = testName +"Auto_Test";
					
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_CVRules);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CCVRules);
    		Thread.sleep(1500);
    		HMCCommon.deleteRule(driver, hmcPage, "CV List Price Override", ruleName);
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,getData(this.Store,EnumTestData.country1),getData(this.Store,EnumTestData.store),
					"Nemo Master Multi Country Product Catalog - Online",getData(this.Store,EnumTestData.ctoPro));
			
			defaultCPU=hmcPage.B2CPriceRules_DefaultCPU.getText();
			String defaultCPUPriceWithTax=hmcPage.B2CPriceRules_DefaultCPUPriceWithTax.getText();
			System.out.println("defaultCPU:"+defaultCPU);
			
			Dailylog.logInfoDB(1,"Check CPU Price:"+defaultCPUPriceWithTax, Store,testName);
			String webRule="";
			String instantRule="";
			String promoRule="";
			if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_webGroup, 5)){
				webRule = hmcPage.B2CPriceSimulator_webGroup.getText();
				System.out.println("webPrice rule exist:"+webRule);
			}
			if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_instantGroup, 5)){
				instantRule = hmcPage.B2CPriceSimulator_instantGroup.getText();
				System.out.println("instantSaving Price rule exist:"+instantRule);
			}
			if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_promoGroup, 5)){
				promoRule = hmcPage.B2CPriceSimulator_promoGroup.getText();
				System.out.println("promoPrice rule exist:"+promoRule);				
			}
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			Thread.sleep(1500);
			//delete rules
			for(int k=0;k<3;k++){
				if(webRule!=""){
					HMCCommon.deleteRule(driver, hmcPage,"Web Prices", webRule);
					webRule="";
					System.out.println("Rule Deleted:"+webRule);
				}
				if(instantRule!=""){
					HMCCommon.deleteRule(driver, hmcPage,"Instant Savings", instantRule);
					instantRule="";
					System.out.println("Rule Deleted:"+instantRule);
				}
				if(promoRule!=""){
					HMCCommon.deleteRule(driver, hmcPage,"Web Prices", promoRule);
					promoRule="";
					System.out.println("Rule Deleted:"+promoRule);
				}
				if(promoRule==""&& instantRule==""&& webRule==""){
					driver.switchTo().defaultContent();
					HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
					HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[AU] Australia","[auweb] AU Web Store",
								"Nemo Master Multi Country Product Catalog - Online",getData(this.Store,EnumTestData.ctoPro));
				}
				
			}
			String webPrice=hmcPage.B2BpriceSimulate_webPrice.getText();	
			Dailylog.logInfoDB(2,"Check Web Price in HMC before CV Override:"+webPrice, Store,testName);
			
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			System.out.println("----------Create C&V Rule-------");
			createCVRule(hmcPage,getData(this.Store,EnumTestData.country),getData(this.Store,EnumTestData.ctoPro));
			Dailylog.logInfoDB(3,"Create C&V rule in HMC. " + getData(this.Store,EnumTestData.ctoPro) , Store,testName);
			
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			Thread.sleep(5000);
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,getData(this.Store,EnumTestData.country1),getData(this.Store,EnumTestData.store),
					"Nemo Master Multi Country Product Catalog - Online",getData(this.Store,EnumTestData.ctoPro));
			String overridePrice=hmcPage.B2CPriceRules_CPUOverridePrice.getText();
			String overridePriceWithTax=hmcPage.B2CPriceRules_DefaultCPUPriceWithTax.getText();
			Dailylog.logInfoDB(4,"The override list price value for the CV:"+overridePriceWithTax, Store,testName);
			
			Assert.assertEquals(CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(overridePrice),fixedPrice, 0);
			String webPriceOverride=hmcPage.B2BpriceSimulate_webPrice.getText();
			Dailylog.logInfoDB(5,"The override web price:"+webPriceOverride, Store,testName);
			
			float result=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(overridePriceWithTax)-CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(defaultCPUPriceWithTax);
			float overrideresult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(webPriceOverride)-CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(webPrice);
			Assert.assertEquals(result,overrideresult, 0);
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+getData(this.Store,EnumTestData.ctoPro));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
			Common.sleep(10000);
			float webResult;
			if(Common.checkElementExists(driver, b2cPage.cto_WebPrice, 5)){
				webResult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(b2cPage.cto_WebPrice.getText());
			}else if(Common.checkElementExists(driver, b2cPage.newCTO_YourPrice, 5)){
				webResult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(b2cPage.newCTO_YourPrice.getText());				
			}else{
				webResult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(b2cPage.newCTO_BasePrice.getText()); 
			}
			Dailylog.logInfoDB(6,"The Site Web Price:"+webResult, Store,testName);
			Assert.assertEquals(webResult,CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(webPriceOverride), 0);
			
			
			// get base product from HMC
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 5)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_CatalogLink.click();
			Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(getData(this.Store,EnumTestData.ctoPro));
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			String baseProduct = driver.findElement(By.xpath("//table[@title='baseProduct']//input[contains(@id,'baseProduct')]"))
								.getAttribute("value");
			baseProduct = baseProduct.substring(0, 15);
			System.out.println(baseProduct);
						
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(3000);
			CTOCommon.Login(ctoPage, testData);
			Common.sleep(2000);
			checkAlert();
			String bundleUrl = testData.CTO.getHomePageUrl()+"ca/nomenclature=" + baseProduct + getData(this.Store,EnumTestData.ctoPro) + "~~~";
			try {
			driver.get(bundleUrl);
			Common.sleep(30000);}
			catch(TimeoutException e) {
				driver.navigate().refresh();
				Common.sleep(30000);
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_PreviewOptions);
		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_SelectAudience);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_AudiencePublic);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_SelectB2Cunit);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_GloablB2Cunit);
			Thread.sleep(3000);	
			if(this.Store=="AU"){
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_RegionANZ);
				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_Australia);
				Thread.sleep(3000);		
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_AuPublicParent);
			    Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_AuPublicUnit);
			}
			
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_SelectButton);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.PreviewOption_DebugViewCheck);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_PreviewButton);
			Thread.sleep(3000);
			Common.switchToWindow(driver, 1);
			Thread.sleep(3000);
			driver.navigate().refresh();
			Thread.sleep(3000);
			String xpath="//a[@val='NB_CPU:"+defaultCPU+"']";
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
			Thread.sleep(12000);
			String ctoOverridePrice=ctoPage.Debug_ListOverridePrice.getText();
			Dailylog.logInfoDB(7,"Override Price on CTO engine :"+ctoOverridePrice, Store,testName);
			Assert.assertEquals(fixedPrice, CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(ctoOverridePrice),0);
			
			
		}catch(Throwable e){
			handleThrowable(e,ctx);
		}
	}
	
	@Test(priority = 1, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
    public void rollBack(){
    	System.out.println("---------RollBack----------");
    	
    	try{
    		SetupBrowser();
    		hmcPage = new HMCPage(driver);
    		driver.get(testData.HMC.getHomePageUrl());
    		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
    			HMCCommon.Login(hmcPage, testData);
    		}
    		hmcPage.Home_PriceSettings.click();
    		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_CVRules);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CCVRules);
    		Thread.sleep(1500);
    		HMCCommon.deleteRule(driver, hmcPage, "CV List Price Override", ruleName);
    		Dailylog.logInfoDB(8,"Rule deleted", Store,testName);
    		
    	}catch(Exception e){
    		e.printStackTrace();
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
    
	public void createCVRule(HMCPage hmcPage,String country,String testProduct) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_CVRules);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CCVRules);
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_CVOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// CV rule name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		System.out.println("Input rulename");

		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.clear();
		hmcPage.B2CPriceRules_ValidFrom.sendKeys("2018-06-01 00:00:00");
		hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);

		// Country
		xpath = "//span[text()='" + country + "' and @class='select2-match']/../../*[not(text())]";
		HMCCommon.fillRuleInfo(driver, hmcPage, country, hmcPage.B2CPriceRules_CountrySelect, xpath);
		System.out.println("Input Country");
		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		HMCCommon.fillRuleInfo(driver, hmcPage, dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);
		System.out.println("Input Catalog");
		
		// Material
		xpath = "//span[contains(text(),'" + testProduct
					+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, hmcPage,testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);
		System.out.println("Input Material");
		
		// B2Cunit
		dataInput=getData(this.Store,EnumTestData.unit);
		xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
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
		
		//Select characteristic
		dataInput="NB_CPU";
		xpath = "//span[contains(text(),'" + dataInput
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, hmcPage,dataInput, hmcPage.B2CPriceRules_CharacteristicSelect,xpath);
		System.out.println("Input CharacteristicSelect");
		
		dataInput=defaultCPU;
		xpath = "//span[contains(text(),'" + dataInput
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, hmcPage, dataInput,hmcPage.B2CPriceRules_CharacteristicValue, xpath);
		System.out.println("Input Characteristics Values");
		
		// fixed price Value
		dataInput = String.valueOf(fixedPrice);
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(dataInput);
		System.out.println("Web Price:" + dataInput);
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("Add Price Rule to Group ");	
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		
		Thread.sleep(10000);
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.B2CPriceRules_CloseBtn);
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		
	}
    
}
