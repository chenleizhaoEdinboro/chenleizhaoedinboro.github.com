package TestScript.B2C;

import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Integer;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.*;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA19760Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String ruleName;
	public String partNumber;
	public String partNumberbackup="80YS0003US";
	
	public NA19760Test(String Store){
		this.Store = Store;
		this.testName = "SHOPE-229";
	}
	
	
	private enum EnumTestData {
		country,country1,unit,store;
	}

	private String getData(String store, EnumTestData dataName) {
		if (store.equals("US_OUTLET")) {
			switch (dataName) {
				
			case country1:
				return "[US] United States";
			case country:
				return "United States";
			case unit:
				return "US Outlet";
			case store:
				return "[outletus] US Outlet";
			default:
				return null;
			}
		}else{
			return null;
		} 
	}
	
	private double getDiscount(long days){
		 if(days<4){
		    	return 0.05;
		    }else if(days<7){
		    	return 0.1;
		    }else if(days<10){
		    	return 0.15;
		    }else if(days<13){
		    	return 0.2;
		    }else if(days<16){
		    	return 0.25;
		    }else if(days<19){
		    	return 0.3;
		    }else if(days<22){
		    	return 0.35;
		    }else if(days<25){
		    	return 0.4;
		    }else{
		    	return 0.45;
		    }
	}
		
	@Test(alwaysRun = true, priority = 0, enabled = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA25491(ITestContext ctx){
		try {
			
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			ruleName = testName + "Auto_Test";

			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			if(!Common.checkElementExists(driver, b2cPage.Add2Cart, 5)){
				partNumber=partNumberbackup;
			}else{
				partNumber= testData.B2C.getDefaultMTMPN();
			}
			System.out.println(partNumber);
			
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			
			//Delete existed rule
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			Common.sleep(3000);
			HMCCommon.deleteRule(driver, hmcPage, "Aging Inventory Discounts", ruleName);
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,getData(this.Store,EnumTestData.country1),getData(this.Store,EnumTestData.store),
					"Nemo Master Multi Country Product Catalog - Online",partNumber);
			String webPrice= hmcPage.B2BpriceSimulate_webPrice.getText();
			Dailylog.logInfoDB(1,"Web Price before rule in HMC:"+webPrice, Store,testName);
			
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			createAgingInventoryDiscountsRule(hmcPage,getData(this.Store,EnumTestData.country),partNumber);
			Dailylog.logInfoDB(2,"Create Aging Inventory Discounts Rule rule in HMC.", Store,testName);
			driver.switchTo().defaultContent();
			
			hmcPage.Home_baseCommerce.click();
			hmcPage.BaseCommerce_StockLevel.click();
			hmcPage.StockLevel_ProductCode.clear();
			hmcPage.StockLevel_ProductCode.sendKeys(partNumber);
			hmcPage.HMCBaseCommerce_searchButton.click();
			String xpath=".//*[@id='Content/OrganizerListEntry["+partNumber+" - (OUTLET_US - Outlet Warehouse US)]_img']";
			System.out.println(xpath);
			driver.findElement(By.xpath(xpath)).click();
			hmcPage.StockLevel_AdministratorTab.click();
			String[] IntroductionDate=hmcPage.StockLevel_AdministratorIntroductionTime.getAttribute("value").split("/");
			
			Date today= new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String Introductiondate=IntroductionDate[2]+'-'+IntroductionDate[0]+'-'+IntroductionDate[1];		
		    Date fDate=sdf.parse(Introductiondate);	    
		    System.out.println(fDate);
		    Date oDate=sdf.parse(sdf.format(today));
		    long days=(oDate.getTime()-fDate.getTime())/(1000*3600*24);
		    System.out.println(days);
		    
		    HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		    HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,getData(this.Store,EnumTestData.country1),getData(this.Store,EnumTestData.store),
					"Nemo Master Multi Country Product Catalog - Online",partNumber);
		    xpath=".//*[@id='container']/samp/i[contains(.,'"+ruleName+"')]/../span";
		    String priceWithDiscount=driver.findElement(By.xpath(xpath)).getText();
		    Dailylog.logInfoDB(3,"Price after rule in HMC:"+priceWithDiscount, Store,testName);
		    driver.switchTo().defaultContent();
		    
		    System.out.println(CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(priceWithDiscount));
		    System.out.println(CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(webPrice));
		    System.out.println(getDiscount(days));
		    
		    Assert.assertEquals(CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(priceWithDiscount),
		    		(1-getDiscount(days))*CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(webPrice), 0.05);
		    
		    startCronJob(driver, hmcPage, "NemoClusterClearPriceCacheCronJob", 1);
		    
			driver.get(testData.B2C.getHomePageUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			if (Common.isElementExist(driver,
					By.xpath("//a[contains(text(),'Empty cart')]"))) {
				driver.findElement(
						By.xpath("//a[contains(text(),'Empty cart')]")).click();
			}else if(Common.checkElementExists(driver, b2cPage.NewCart_DeleteButton, 5)){
				b2cPage.NewCart_DeleteButton.click();
				b2cPage.NewCart_ConfirmDelete.click();
			}
			
			Thread.sleep(3000);
			CommonFunction.DesignHandler.CartCheckOut.CheckOutUIHandler(b2cPage, "quickadd", partNumber);
			String WebPrice;
			if(Common.checkElementDisplays(driver, b2cPage.Cart_WebPrice, 5)){
				WebPrice=b2cPage.Cart_WebPrice.getText();
			}else{
				WebPrice=b2cPage.NewCart_SubTotalPrice.getText();
			}
			Dailylog.logInfoDB(4,"Website Price after rule in HMC:"+WebPrice, Store,testName);
			
			Assert.assertEquals(CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(WebPrice),
			    		(1-getDiscount(days))*CommonFunction.DesignHandler.CartCheckOut.GetPriceValue(webPrice), 0.05);
	        
		}catch (Throwable e) {
			// TODO Auto-generated catch block
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
    		HMCCommon.deleteRule(driver, hmcPage, "Aging Inventory Discounts", ruleName);
    		Dailylog.logInfoDB(8,"Rule deleted", Store,testName);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
	

	public void createAgingInventoryDiscountsRule(HMCPage hmcPage,String country,String testProduct) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		
		hmcPage.PricingCockpit_B2CPriceRules.click();
//		new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='loader']")));
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_AgingInventoryDiscountsRule.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// 
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
		
		//click AddTimePeriod
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("0");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("5");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("4");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("10");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("7");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("15");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("10");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("20");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("13");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("25");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("16");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("30");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("19");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("35");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("22");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("40");
		hmcPage.B2CPriceRules_AddTimePeriod.click();
		hmcPage.B2CPriceRules_Thresholds.clear();
		hmcPage.B2CPriceRules_Thresholds.sendKeys("25");
		hmcPage.B2CPriceRules_ThresholdsMinusIcon.click();
		hmcPage.B2CPriceRules_ThresholdsPercentIcon.click();
		hmcPage.B2CPriceRules_ThresholdsValue.clear();
		hmcPage.B2CPriceRules_ThresholdsValue.sendKeys("45");
			
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("Add Price Rule to Group ");	
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		
		Thread.sleep(10000);
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.B2CPriceRules_CloseBtn);
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		driver.switchTo().defaultContent();
		
	}
	
	public void startCronJob(WebDriver driver, HMCPage hmcPage, String jobName, int windowSize)
			throws InterruptedException {
		System.out.println("CronJob start");
		//System > CronJob > singleCouponJob
		hmcPage.Home_System.click();
		hmcPage.Home_cronJob.click();
		if (!Common.isElementExist(driver, By.xpath("//a/span[contains(.,'Search')]"))) {
			driver.findElement(By.xpath("(//*[contains(@id,'[organizersearch][CronJob]')])[1]")).click();
			driver.findElement(By.xpath("(//*[contains(@id,'[organizerlist][CronJob]')])[1]")).click();
		}
		// JobName
		hmcPage.CronJob_jobName.clear();
		hmcPage.CronJob_jobName.sendKeys(jobName);
		hmcPage.CronJob_searchButton.click();
		// Selecting the Job From Search Results
		Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		// Start CronJob
		hmcPage.CronJob_startCronJob.click();
		Thread.sleep(5000);
		Common.switchToWindow(driver, windowSize);
		Assert.assertEquals(hmcPage.CronJob_cronJobSuccessMsg.getText(), "CronJob performed.");
		driver.close();
		Common.switchToWindow(driver, windowSize - 1);
		hmcPage.Home_System.click();
		System.out.println("CronJob end");
	}
	
	
}
	

	
	

