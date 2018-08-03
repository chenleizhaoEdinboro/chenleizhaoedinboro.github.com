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

import CommonFunction.DesignHandler.*;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA25376Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String ruleName;
	public String partNumber;
	public String baseProduct;

	
	public NA25376Test(String store) {
		this.Store = store;
		this.testName = "SHOPE-224";
	}
	private enum EnumTestData {
		mtmPro, country, unit;
	}
	private String getData(String store, EnumTestData dataName) {
		if (store.equals("JP")) {
			switch (dataName) {
			case mtmPro:
				return "20H1A09GJP";
			case country:
				return "Japan";
			case unit:
				return "JP public store unit";		    
			default:
				return null;
			}
		
		} else if(store.equals("AU")) {
			switch (dataName) {
			case mtmPro:
				return "81C3000YAU";
			case country:
				return "Australia";
			case unit:
				return "AU Public unit";
			default:
				return null;
			}
		}else if(store.equals("HK")) {
			switch (dataName) {
			case mtmPro:
				return "20K70005HH";
			case country:
				return "Hong Kong S.A.R. of China";
			case unit:
				return "HK public store unit";
			default:
				return null;
				}
		} else if(store.equals("CA")) {
			switch (dataName) {
			case mtmPro:
				return "80Y7006JCF";
			case country:
				return "Canada";
			case unit:
				return "CA web store unit";
			default:
				return null;
			}
		} else if(store.equals("GB")) {
			switch (dataName) {
			case mtmPro:
				return "80X700A4UK";
			case country:
				return "United Kingdom";
			case unit:
				return "gbweb";
			default:
				return null;
			}
		}else if(store.equals("BR")) {
			switch (dataName) {
			case mtmPro:
				return "81CC0007BR";
			case country:
				return "Brazil";
			case unit:
				return "brweb";
			default:
				return null;
			}
		}else{
			return null;
		}
	}
	
	@Test(alwaysRun= true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA22046(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			ruleName = testName+ this.Store+Common.getDateTimeString();
			
			//Validate if product is available
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			System.out.println("DefaultMTMPN:"+testData.B2C.getDefaultMTMPN());
			if(!Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"), 120)){
				partNumber= getData(testData.Store, EnumTestData.mtmPro);
			}else{
				partNumber=testData.B2C.getDefaultMTMPN();
			}
			System.out.println("partNumber:"+partNumber);					

			Dailylog.logInfoDB(1,"Create ecopon in HMC.", Store,testName);	
			createRule(hmcPage,ruleName);
			
			Dailylog.logInfoDB(2,"Check ecopon on Cart page.", Store,testName);
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			CartCheckOut.CheckOutUIHandler(b2cPage,"quickadd",partNumber);
			Thread.sleep(30000);
		    Assert.assertEquals(b2cPage.B2CPriceRules_ecouponMessage.getText(), "Valid through 10/31/2017");
					
					
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@AfterTest
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
	    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CPriceRules);
	    		Thread.sleep(1500);
	    		HMCCommon.deleteRule(driver, hmcPage, "eCoupon Discounts", ruleName);
	    		Dailylog.logInfoDB(3,"Rule deleted", Store,testName);
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1,
			String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(5000);
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
	public void createRule(HMCPage hmcPage, String ruleName) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
        
		loginPriceCockpit(hmcPage);
		
		System.out.println("Create ecopon rules***********************");
		hmcPage.PricingCockpit_B2CPriceRules.click();
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_eCouponDiscountOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		//rule name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		
		//display ecopon message
		hmcPage.B2CPriceRules_ecouponMessageDisplay.click();
		hmcPage.B2CPriceRules_ecouponMessageEdit.click();

		//language
		String language;
		if((this.Store).contains("_")){
			language="en_"+this.Store.split("_")[0];			
		}else if(this.Store=="JP"){
			language="ja_JP";
		}else if(this.Store=="AU"){
			language="en";
		}else if(this.Store=="BR"){
			language="pt";
		}
		else{
			language="en_"+this.Store;
		}
		System.out.println(language);
		
		xpath = "//span[text()='" + language + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Language", language, hmcPage.B2CPriceRules_ChooseCountry, xpath);
		
		hmcPage.B2CPriceRules_ecouponMessageInput.clear();
		hmcPage.B2CPriceRules_ecouponMessageInput.sendKeys("<span style=\"color: #fdda97\">Valid through 10/31/2017</span>");;
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageAdd);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageSave);	
		
		//Priority
		hmcPage.B2CPriceRules_ecouponPriority.clear();
		hmcPage.B2CPriceRules_ecouponPriority.sendKeys("500");
		
		// Country
		dataInput = getData(testData.Store, EnumTestData.country);
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);
		
		CommonFunction.Common.InnerScrollTop(driver, "modal fade in", "10000000");

		//Material
		xpath = "//span[contains(text(),'" + partNumber
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", partNumber, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = getData(testData.Store, EnumTestData.unit);
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		
		else if(this.Store=="BR")
			xpath = "(//div/a[contains(.,'Brazil | Public Web')])[2]";
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

		// ecoupon price Value
		hmcPage.B2CPriceRules_dynamicRate.click();
		hmcPage.B2CPriceRules_DynamicMinusButton.click();
		hmcPage.B2CPriceRules_ecouponDollorButton.click();
		hmcPage.B2CPriceRules_dynamicValue.clear();
		hmcPage.B2CPriceRules_dynamicValue.sendKeys("10");
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
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();

	}
    public void loginPriceCockpit(HMCPage hmcPage){
		
		System.out.println("Go to Price Cockpit-----");
		driver.get(testData.HMC.getHomePageUrl());
		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
			HMCCommon.Login(hmcPage, testData);
		}		
	    hmcPage.Home_PriceSettings.click();
	    if(!Common.checkElementExists(driver, hmcPage.Home_PricingCockpit, 5)){
	    	hmcPage.Home_PriceSettings.click();
	    }
	    hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
		Common.sleep(1000);
		if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
			hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
			hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
			hmcPage.PricingCockpit_Login.click();
		}
		Common.sleep(1000);
	}
	
}
	
	