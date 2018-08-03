package TestScript.B2C;

import org.testng.annotations.Test;
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


public class NA24295Test extends SuperTestClass{
	
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public CTOPage ctoPage;
	public String groupid="78419390";
	//public String partNumber="20HFCTO1WWENUS0";
	public String partNumber = "20L5CTO1WWENUS0";
	public String ruleNameWebPrice="NA-24295 Web Price Rule";
	public String ruleNameOnTier="NA-24295 Web Price Rule On Tier";
	public String ruleNameOnUnit="NA-24295 Web Price Rule On Unit";
	public String ruleNameOnCountry="NA-24295 Web Price Rule On Country";
	

	public NA24295Test(String store){
		this.Store= store;	
		this.testName= "NA-24295";
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA24295(ITestContext ctx){
		try{			
			this.prepareTest();		
			b2cPage= new B2CPage(driver);
			hmcPage= new HMCPage(driver);
			
			//Validate if the product is available
			String baseUrl= testData.B2C.getHomePageUrl();
			String URL =baseUrl.substring(0, baseUrl.lastIndexOf("/"))+ "/smbpro";
			//driver.get(testData.B2C.getHomePageUrl()+"/login");
			driver.get(URL+"/login");
			if(Common.checkElementExists(driver, b2cPage.SMB_login, 10)){
				b2cPage.SMB_login.click();
				b2cPage.SMB_uName.clear();
				//b2cPage.SMB_uName.sendKeys(testData.B2C.getLoginID());
				b2cPage.SMB_uName.sendKeys("testusepp@sharklasers.com");
				b2cPage.SMB_uPwd.clear();
				b2cPage.SMB_uPwd.sendKeys(testData.B2C.getLoginPassword());
				b2cPage.SMB_signIn.click();
			}
			Common.sleep(10000);
		/*	driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());
			System.out.println("DefaultMTMPN:"+testData.B2C.getDefaultCTOPN());
			if(Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"), 120)){
				partNumber= testData.B2C.getDefaultCTOPN();
			}
			Dailylog.logInfoDB(1, "partNumber:"+partNumber,Store, testName);*/
			
			System.out.println(partNumber);
			//delete origin price rule
			Dailylog.logInfoDB(2,"Delete rules", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			Common.sleep(3000);			
			B2CPriceSimulateDebug(driver,hmcPage,"[US] United States","[smbpro] Business Insiders? Professional",
					"Nemo Master Multi Country Product Catalog - Online",partNumber);
			
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
			for(int i=0;i<3;i++){
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
				
			}
			Dailylog.logInfoDB(3,"Exsite rule deleted", Store,testName);
			
			driver.switchTo().defaultContent();			
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[US] United States","[smbpro] Business Insiders? Professional",
					"Nemo Master Multi Country Product Catalog - Online",partNumber,"");
		
			String webPricesmb=hmcPage.B2BpriceSimulate_webPrice.getText();	
			Dailylog.logInfoDB(4,"Check Web Price On US SMB Website in HMC before Create usweb Rule:"+webPricesmb, Store,testName);
			
			
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			createRule(hmcPage,ruleNameWebPrice,partNumber,"United States",true,false,59999,"US web store unit");
			Dailylog.logInfoDB(5,"Web Price Rule for US web store Created", Store,testName);
			
			//get web price
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[US] United States","[smbpro] Business Insiders? Professional",
					"Nemo Master Multi Country Product Catalog - Online",partNumber,"");	
			String webPricesmb1=hmcPage.B2BpriceSimulate_webPrice.getText();	
			Dailylog.logInfoDB(6,"Check Web Price On US SMB Website in HMC after Create usweb Rule:"+webPricesmb1, Store,testName);
			
			Assert.assertEquals(Float.parseFloat(webPricesmb1), Float.parseFloat(webPricesmb), 0.1);
			//create tier
			Thread.sleep(5000);
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			Thread.sleep(3000);
			if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
				hmcPage.Home_PriceSettings.click();
			}			
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			createPriceTier(driver,hmcPage);
			Dailylog.logInfoDB(7,"Tier created", Store,testName);
			
			
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			editRule(driver,hmcPage,ruleNameWebPrice);
			Dailylog.logInfoDB(8,"Web Price Rule Edited--Add Price Tier", Store,testName);
			
			float webPriceWithRule_SMB=getWebPrice(driver,b2cPage,partNumber);
			Dailylog.logInfoDB(9,"Web Price -SMB After US SMB Unit Rule Edited:"+webPriceWithRule_SMB, Store,testName);
			Assert.assertEquals(webPriceWithRule_SMB, 59999, 0.1);
			
			//deleted Rule
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			Thread.sleep(3000);
			if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
				hmcPage.Home_PriceSettings.click();
			}	
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			HMCCommon.deleteRule(driver, hmcPage, "Web Prices", ruleNameWebPrice);
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(10,"Web Price Rule Deleted", Store,testName);
			
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			createRule(hmcPage,ruleNameOnTier,partNumber,"United States",false,true,49999);
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(11,"Web Price For Tier Level Created", Store,testName);
			
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			createRule(hmcPage,ruleNameOnUnit,partNumber,"United States",true,false,49979,"Small Business Portal US");
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(12,"Web Price For Unit Level Created", Store,testName);
			
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			createRule(hmcPage,ruleNameOnCountry,partNumber,"United States",false,false,49949);
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(13,"Web Price For Country Level Created", Store,testName);
			
			
			float webPriceWithRules_SMB=getWebPrice(driver,b2cPage,partNumber);
			Dailylog.logInfoDB(15,"Web Price -SMB After Three Rules Created:"+webPriceWithRules_SMB, Store,testName);
			

			Assert.assertEquals(webPriceWithRules_SMB, 49999, 0.1);
			
			//deleted Rule
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
				hmcPage.Home_PriceSettings.click();
			}	
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			HMCCommon.deleteRule(driver, hmcPage, "Web Prices", ruleNameOnTier);
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(16,"Rule On Tier Level Deleted", Store,testName);
			
			float webPriceWithRules_SMB1=getWebPrice(driver,b2cPage,partNumber);
			Dailylog.logInfoDB(18,"Web Price US SMB Site After Rule On Tier Levelbvel Deleted:"+webPriceWithRules_SMB1, Store,testName);
			Assert.assertEquals(webPriceWithRules_SMB1, 49979, 0.1);

			
			//deleted Rule
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
				hmcPage.Home_PriceSettings.click();
			}	
			Thread.sleep(3000);
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			HMCCommon.deleteRule(driver, hmcPage, "Web Prices", ruleNameOnUnit);
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(19,"Rule On Unit Deleted", Store,testName);
			
			float webPriceWithRules_SMB2=getWebPrice(driver,b2cPage,partNumber);
			Dailylog.logInfoDB(21,"Web Price US SMB Site After Rule On Unit Levelbvel Deleted:"+webPriceWithRules_SMB2, Store,testName);
			
			Assert.assertEquals(webPriceWithRules_SMB2, 49949, 0.1);
			
			
			//deleted Rule:Price Tier and Web price on country Level
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			Thread.sleep(3000);
			if(!Common.checkElementDisplays(driver, hmcPage.Home_PricingCockpit, 5)){
				hmcPage.Home_PriceSettings.click();
			}	
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			HMCCommon.deleteRule(driver, hmcPage, "Web Prices", ruleNameOnCountry);
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			Common.sleep(3000);
			hmcPage.PricingCockpit_PriceTierRules.click();
			Common.sleep(3000);
			String xpath="//td[text()='US SMB Silver']/following-sibling::td/a[contains(@class,'deleteGroup')]";
			driver.findElement(By.xpath(xpath)).click();
			Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
			hmcPage.B2CPriceRules_deleteInput.clear();
			hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
			Thread.sleep(1500);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_clearAll);
			Thread.sleep(1500);
			
		}catch(Throwable e){
			handleThrowable(e,ctx);
		}
	}
	
	public float getWebPrice(WebDriver driver,B2CPage b2cPage,String partNumber){
		String baseUrl= testData.B2C.getHomePageUrl();
		String URL =baseUrl.substring(0, baseUrl.lastIndexOf("/"))+ "/smbpro";
		//driver.get(testData.B2C.getHomePageUrl()+"/login");
		driver.get(URL+"/login");
		//driver.get(testData.B2C.getHomePageUrl()+"/login");
		if(Common.checkElementExists(driver, b2cPage.SMB_login, 10)){
			b2cPage.SMB_login.click();
			b2cPage.SMB_uName.clear();
			//b2cPage.SMB_uName.sendKeys(testData.B2C.getLoginID());
			b2cPage.SMB_uName.sendKeys("testusepp@sharklasers.com");
			b2cPage.SMB_uPwd.clear();
			b2cPage.SMB_uPwd.sendKeys(testData.B2C.getLoginPassword());
			b2cPage.SMB_signIn.click();
		}
		Common.sleep(10000);
		driver.get(URL+"/p/"+partNumber);
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
	
	public void editRule(WebDriver driver,HMCPage hmcPage,String ruleName) throws InterruptedException {
		
		System.out.println("Edit rules***********************");
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Common.sleep(5000);
		String xpath="//td[contains(text(),'"+ruleName+"')]/following-sibling::td/div/a[1]";
		driver.findElement(By.xpath(xpath)).click();
		System.out.println("Show Clciked");
		xpath="//td[text()='"+partNumber+"']/following-sibling::td/a[@title='Edit Rule']";
		driver.findElement(By.xpath(xpath)).click();
		System.out.println("Edit Clicked");
		Thread.sleep(5000);
		
		String tier="US SMB Silver";
		xpath = "//div[text()='US SMB Silver You are saving 3% on all purchases']";
		hmcPage.B2BpriceSimulate_SMBPriceTierSelect.click();
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(tier);
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
		
		System.out.println("Price Tier: " + tier);
		Thread.sleep(3000);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_PriceRuleSave);
	}
	
	public void createPriceTier(WebDriver driver,HMCPage hmcPage) throws InterruptedException{
		
		System.out.println("Create Price Tier rules***********************");
		Thread.sleep(3000);
		hmcPage.PricingCockpit_PriceTierRules.click();
		Common.sleep(5000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateNewTier);
		
		Thread.sleep(3000);
		//name
		hmcPage.B2BPriceRules_PriceTierName.clear();
		hmcPage.B2BPriceRules_PriceTierName.sendKeys("US SMB Silver");
		System.out.println("Input rulename");
		
		//Description
		hmcPage.B2BPriceRules_PriceTierDescription.clear();
		hmcPage.B2BPriceRules_PriceTierDescription.sendKeys("You are saving 3% on all purchases");
		System.out.println("Input Description");
		
		//Min
		hmcPage.B2BPriceRules_PriceTierMin.clear();
		hmcPage.B2BPriceRules_PriceTierMin.sendKeys("0");
		System.out.println("Input Min");
		
		//Max
		hmcPage.B2BPriceRules_PriceTierMax.clear();
		hmcPage.B2BPriceRules_PriceTierMax.sendKeys("10000");
		System.out.println("Input Max");
		
		//Day
		hmcPage.B2BPriceRules_PriceTierDay.clear();
		hmcPage.B2BPriceRules_PriceTierDay.sendKeys("30");
		System.out.println("Input Day");
		
		//Store Unit
		String dataInput= "Small Business Portal US";
		String xpath = "//span[text()='" +dataInput+ "' and @class='select2-match']/../../*[not(text())]";
		HMCCommon.fillRuleInfo(driver, hmcPage, dataInput, hmcPage.B2BPriceRules_PriceTierStoreUnit, xpath);
		System.out.println("Input Store Unit");
		Common.sleep(3000);
		
		//add group
		xpath = "//span[text()='" +groupid+ "']";
		/*for(int i=0;i<10;i++) {
			Common.javascriptClick(driver, hmcPage.B2BPriceRules_PriceTierGroup);
			
			if(Common.checkElementDisplays(driver, By.xpath("//div/ul[@class='select2-results' and not(@id)]"), 10)) {
				break;
			}

		}*/
		driver.findElement(By.xpath("//b[contains(text(),'Add New Customer Groups')]")).click();
		Common.sleep(2000);
		hmcPage.B2BPriceRules_PriceTierInput.sendKeys(groupid);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(xpath)), 20);
		driver.findElement(By.xpath(xpath)).click();		
		System.out.println("Input group");	
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_PriceTierSave);
		System.out.println("Clicked Save Button");
		
	}
	
	public void createRule(HMCPage hmcPage,String ruleName,String testProduct,String country,boolean unit,boolean priceTier,float webVaule,String...usunit ) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		
		System.out.println("Create rules***********************");
		hmcPage.PricingCockpit_B2CPriceRules.click();
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
		HMCCommon.fillRuleInfo(driver, hmcPage, testProduct, hmcPage.B2CPriceRules_MaterialSelect, xpath);
		System.out.println("Input MAterial");
		
		// B2Cunit
		if(unit){
			dataInput = usunit[0];
			if (this.Store.equals("AU"))
				xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
			else
				xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
			System.out.println("B2Cunit Xpath: " + xpath);
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
		}
		CommonFunction.Common.InnerScrollTop(driver, "modal fade in", "10000000");
		
		if(priceTier){
			String tier="US SMB Silver";
			System.out.println(tier);
			hmcPage.B2BpriceSimulate_SMBPriceTierSelect.click();
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys(tier);
			Thread.sleep(5000);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);			
			System.out.println("Price Tier: " + tier);
		}
		

		// Web price Value
		dataInput = String.valueOf(webVaule);
		hmcPage.B2CPriceRules_PriceValue.clear();
		hmcPage.B2CPriceRules_PriceValue.sendKeys(dataInput);
		System.out.println("Web Price:" + dataInput);
		Thread.sleep(2000);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("Add Price Rule to Group ");
		
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
	
	}
	
	public void B2CPriceSimulateDebug(WebDriver driver,HMCPage hmcPage,String country,
			String store,String catalog,String partNumber){	
		hmcPage.PricingCockpit_B2CPriceSimulator.click();
		Common.sleep(3000);
		try{
			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.B2BpriceSimulate_CountryButton,hmcPage.B2CPriceRules_SearchInput,country,10);
			Common.sleep(10000);
			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.B2BpriceSimulate_StoreButton,hmcPage.B2CPriceRules_SearchInput,store,10);
			
			HMCCommon.fillB2CPriceRuleDetails(driver,hmcPage.B2CPriceSimulator_catalogVersion,hmcPage.B2CPriceRules_SearchInput,catalog,10);
			Common.waitElementClickable(driver, hmcPage.B2CPriceSimulator_priceDate, 12);
			hmcPage.B2CPriceSimulator_priceDate.click();
			hmcPage.B2CPriceSimulator_priceDate.sendKeys(Keys.ENTER);
			Common.sleep(3000);
			CommonFunction.Common.scrollToElement(driver, hmcPage.B2CPriceSimulator_product);
			Common.waitElementClickable(driver, hmcPage.B2CPriceSimulator_product, 120);
			hmcPage.B2CPriceSimulator_product.click();
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 3);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(partNumber); 
			WebElement productResult = driver.findElement(By.xpath("//span[text()='" + partNumber + "']"));
			Common.waitElementVisible(driver, productResult);
			productResult.click();
			Common.sleep(1500);
			hmcPage.B2CPriceSimulator_debug.click();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		Common.sleep(5000); 		
	}
	
	
	
    
}