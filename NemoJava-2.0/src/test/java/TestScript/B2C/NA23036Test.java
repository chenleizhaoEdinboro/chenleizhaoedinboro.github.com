package TestScript.B2C;

import org.testng.annotations.Test;
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
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import junit.framework.Assert;
import CommonFunction.Common;
import CommonFunction.B2CCommon;
import CommonFunction.DesignHandler.*;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;


public class NA23036Test extends SuperTestClass{
	public String[] partNumber={"22TP2TBL470","22TP2TXX12Y","22TP2WPP51S"};
	public List<WebElement> priceList;
	public String seriesNB;
	public int productNumber;
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String PLPURL;
	public String ruleName;
	public String partNB[];
	public float []webPrice;
	public float []Price;
	public String ruleID;
	public int list=1;
	public float webVaule=5999;

	
	public NA23036Test(String store){
		this.Store= store;	
		this.testName= "NA-23036";
	}
	
	@Test(alwaysRun = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA23036(ITestContext ctx){
		try{			
			this.prepareTest();		
			b2cPage= new B2CPage(driver);
			hmcPage= new HMCPage(driver);	
			
			boolean flag=true;
			ruleName = testName + Common.getDateTimeString();
			System.out.println(ruleName);
			
			for(int i=0;i<partNumber.length;i++){
				driver.get(testData.B2C.getHomePageUrl()+"/p/"+partNumber[i]);
				List<WebElement> priceList = driver.findElements(By.xpath("//div[@class='comparecheckbox']"));
				productNumber=priceList.size();
				Dailylog.logInfoDB(list++,"product Number:"+productNumber, Store,testName);
				//check if have at last two products
				if (productNumber<2){
					System.out.println("No products display");
				
				}else{
					partNB = new String[priceList.size()];
					webPrice= new float[priceList.size()];
					for(int j=1;j<=priceList.size();j++){
						 driver.get(testData.B2C.getHomePageUrl()+"/p/"+partNumber[i]);
						 partNB[j-1]=driver.findElement(By.xpath("(//div[@class='comparecheckbox']/input)["+j+"]"))
								 .getAttribute("name").split("_")[0];
						 Dailylog.logInfoDB(list++,"Part Number:"+partNB[j-1], Store,testName);
						 
						 //check web price and delete old web price rule
						 driver.get(testData.HMC.getHomePageUrl());
						 if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
								HMCCommon.Login(hmcPage, testData);
						 }
						 if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
								hmcPage.Home_PriceSettings.click();
						 }
						 HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
						 HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[AU] Australia","[auweb] AU Web Store",
									"Nemo Master Multi Country Product Catalog - Online",partNB[j-1]);
						
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
												"Nemo Master Multi Country Product Catalog - Online",partNB[j-1]);
								}
								
							}
												
						 String webPriceHMC=hmcPage.B2BpriceSimulate_webPrice.getText();	
						 Dailylog.logInfoDB(list++,"Web Price in HMC before Created Rule:"+webPriceHMC, Store,testName);
						 Thread.sleep(3000);
						 driver.get(testData.B2C.getHomePageUrl()+"/p/"+partNumber[i]);
						 webPrice[j-1]=getWebPrice(driver,b2cPage,testData.B2C.getHomePageUrl(),partNB[j-1]);
						 Dailylog.logInfoDB(list++,"Web Price on WebSite before Created Rule:"+webPrice[j-1], Store,testName);			 
					 }  
					 seriesNB=partNumber[i];
					 Dailylog.logInfoDB(list++,"Series partNumber:"+partNumber[i], Store,testName);
					 break;
				}			
				
			}	
			//Adding Rule in HMC
			 driver.get(testData.HMC.getHomePageUrl());
			 if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
					HMCCommon.Login(hmcPage, testData);
			 }
			 if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
					hmcPage.Home_PriceSettings.click();
			 }
			 HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			 createRule(hmcPage,partNB[0],seriesNB,"Australia",ruleName);	
			
			//Check the WebPrice on HMC and store
			float webPrice_HMCWithRule;
			Price= new float[productNumber];
			for(int i=0;i<productNumber;i++){
				System.out.println("------Check the Web Price With Rule----------");
				System.out.println(partNB[i]);
				 driver.get(testData.HMC.getHomePageUrl());
				 if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
						HMCCommon.Login(hmcPage, testData);
				 }
				 if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
						hmcPage.Home_PriceSettings.click();
				 }
				HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
				HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[AU] Australia","[auweb] AU Web Store",
						"Nemo Master Multi Country Product Catalog - Online",partNB[i]);	
				webPrice_HMCWithRule=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(hmcPage.B2BpriceSimulate_webPrice.getText());
				Dailylog.logInfoDB(list++,"Web Price in HMC after Rule:"+webPrice_HMCWithRule, Store,testName);
				Price[i]=getWebPrice(driver,b2cPage,testData.B2C.getHomePageUrl(),partNB[i]);
				Dailylog.logInfoDB(list++,"Web Price on Website after Rule:"+Price[i], Store,testName);
				
				//check the web price of first product is not changed,others changed
				if(i==0){
					Assert.assertEquals(Price[i],webPrice[i], 0.1);
				}else{
					Assert.assertEquals(Price[i],webVaule, 0.1);
					
				}
				
			}
			if(flag){
				rollBack(hmcPage);	
			}

		}catch(Throwable e){
			handleThrowable(e,ctx);
		}
	}
	
	
	public void rollBack(HMCPage hmcPage) {
			System.out.println("rollback-------"); // roll back
			driver.get(testData.HMC.getHomePageUrl());
    		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
    			HMCCommon.Login(hmcPage, testData);
    		}
    		if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 15)){
					hmcPage.Home_PriceSettings.click();
			}
    		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CPriceRules);
			try {
				Common.sleep(3000);
				HMCCommon.deleteRule(driver, hmcPage, "Web Prices", ruleName);
				Dailylog.logInfoDB(list++,"Rule deteled", Store,testName);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}

	
	public float getWebPrice(WebDriver driver,B2CPage b2cPage,String url,String partNumber){
		driver.get(url);
		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
			HMCCommon.Login(hmcPage, testData);
		}
		Common.sleep(10000);
		driver.get(url+"/p/"+partNumber);
		System.out.println(url+"/p/"+partNumber);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartOrCustomize);
		Common.sleep(10000);
		float webResult;
		if(Common.checkElementDisplays(driver, b2cPage.cto_WebPrice, 5)){
			System.out.println("cto_WebPrice"+b2cPage.cto_WebPrice.getText());
			webResult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(b2cPage.cto_WebPrice.getText());
		}else if(Common.checkElementDisplays(driver, b2cPage.PDP_webPrice, 5)){
			System.out.println("newCTO_YourPrice:"+b2cPage.PDP_webPrice.getText());
			webResult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(b2cPage.PDP_webPrice.getText());
		}else{
			System.out.println("newCTO_BasePrice:"+b2cPage.newCTO_BasePrice.getText());			
			webResult=CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(b2cPage.newCTO_BasePrice.getText());
		}
		return webResult;	
	}	
		
	public void createRule(HMCPage hmcPage,String testProduct,String testBaseProduct ,String country,String ruleName) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		
		System.out.println("Create rules***********************");
		hmcPage.PricingCockpit_B2CPriceRules.click();
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_WebPriceOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// Web price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		System.out.println("Input rulename");

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
		xpath = "//span[text()='" + country + "' and @class='select2-match']/../../*[not(text())]";
		HMCCommon.fillRuleInfo(driver, hmcPage,country, hmcPage.B2CPriceRules_CountrySelect, xpath);
		System.out.println("Input Country");
		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		HMCCommon.fillRuleInfo(driver, hmcPage,dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);
		System.out.println("Input Catalog");
		// testBaseProduct
		xpath = "//span[contains(text(),'" + testBaseProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, hmcPage,testBaseProduct, hmcPage.B2CPriceRules_BaseProduceSelect, xpath);
		System.out.println("Input testBaseProduct");
		
		// B2Cunit
		dataInput="AU Public unit";
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

		// Web price Value
		dataInput = String.valueOf(webVaule);
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(dataInput);
		System.out.println("Web Price:" + dataInput);
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("Add Price Rule to Group ");

		//Add Restriction To Group		
		if(Common.isElementExist(driver, By.xpath(".//*[@id='s2id_baseProduct']/a/abbr"))){
			hmcPage.B2CPriceRules_RemoveBaseProduct.click();
		}
		System.out.println("Remove BaseProduct ");
		
		// Material
		xpath = "//span[contains(text(),'" + testProduct
				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		HMCCommon.fillRuleInfo(driver, hmcPage,testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);
		System.out.println("Input MAterial");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddRestrictionToGroup);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		
		Thread.sleep(10000);
		// Record Price Rule ID
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CloseBtn);
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);
		System.out.println("Clicked filter!");
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
	}
}
	
	
	
	
