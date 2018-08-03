package TestScript.B2B;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22685Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	String searchNO = "5308612152"; 
	String getProductPrice = "";
	String getProductRate  = "";
	String productNo = "4X90L66916";
	double B2Bprice;
	public NA22685Test(String store){
		this.Store = store;
		this.testName = "NA-22685";
	}
	
	@Test(alwaysRun= true,groups={"shopgroup","pricingpromot","p2","b2b"})
	public void NA22685(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			Thread.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
			//1. In HMC -> Nemo -> Contract ;Search 5306919345
			if(Common.checkElementExists(driver, hmcPage.Home_Nemo, 5))
				hmcPage.Home_Nemo.click();
			if(Common.checkElementExists(driver, hmcPage.Home_Contract, 5))	
				hmcPage.Home_Contract.click();
			driver.findElement(By.xpath("//input[contains(@id,'Contract.contractId')]")).sendKeys(searchNO);
		    if(Common.checkElementExists(driver, hmcPage.ContaractID_Search, 5))
				hmcPage.ContaractID_Search.click();
			if(Common.checkElementExists(driver, hmcPage.Result_OpenEditor, 5))	
				hmcPage.Result_OpenEditor.click();
			//validate:Authorised Sold-to
			Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='Content/ItemDisplay[1214576398]_div']")).getText(), "1214576398");
			//validate:Leading Sold-to
			Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='Content/ItemDisplay[1213606768]_div']")).getText(), "1213606768");
			//validate:Material
			Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='Content/StringDisplay[4X90L66916]_span']")).getText(), "4X90L66916");
			//validate:Price
			
			String xpath="//div[@id='Content/StringDisplay["+productNo+"]_div']/../following-sibling::td/div/div[contains(@id,'Content/ContractPriceDisplay')]";
			
			getProductPrice = driver.findElement(By.xpath(xpath)).getText();
			Dailylog.logInfoDB(2,"Product Price:"+getProductPrice+"USD", Store,testName);
		    //2. Go to Price Settings -> Exchange Rate
			if(Common.checkElementExists(driver, hmcPage.Home_PriceSettings, 5))
				hmcPage.Home_PriceSettings.click();
			if(Common.checkElementExists(driver, hmcPage.Home_ExchangeRate, 5))
				hmcPage.Home_ExchangeRate.click();
			//1213606768
			driver.findElement(By.xpath("//input[contains(@id,'ExchangeRate.customer')]")).sendKeys("1213606768");
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[ExchangeRate.customer]]_ajaxselect_1213606768']")).click();

			Thread.sleep(2000);
			hmcPage.ExchangeRate_fromCurrency.click();
			driver.findElement(By.xpath(".//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[ExchangeRate.fromCurrency]]_select']/option[contains(text(),'USD')]")).click();
			Thread.sleep(3000);
			hmcPage.ExchangeRate_toCurrency.click();
			driver.findElement(By.xpath(".//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[ExchangeRate.toCurrency]]_select']/option[contains(text(),'CAD')]")).click();
			
			if(Common.checkElementExists(driver, hmcPage.ExchageRate_SearcheButton, 5))
				hmcPage.ExchageRate_SearcheButton.click();
			if(Common.isElementExist(driver, By.xpath("//span[contains(@id,'Content/OrganizerListEntry')]/img"))){
				// order by valid from (descend)
				hmcPage.ExchangeRate_validFrom.click();
				driver.findElement(By.xpath("//span[contains(@id,'Content/OrganizerListEntry')]/img")).click();
			}
			getProductRate = driver.findElement(By.xpath(".//*[@id='Content/DoubleEditor[in Content/Attribute[ExchangeRate.rate]]_input']")).getAttribute("value");
			Dailylog.logInfoDB(3,"Exchange Rate:"+getProductRate, Store,testName);
			//3. Go to Price Settings -> Pricing Cockpit -> B2B Price Simulator
			if(Common.checkElementExists(driver, hmcPage.Home_PricingCockpit, 5))
				hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			Thread.sleep(3000);
			if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
				hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
				hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
				hmcPage.PricingCockpit_Login.click();
			}
			Thread.sleep(3000);
			// Simulator
		    hmcPage.PricingCockpit_B2BpriceSimulate.click();
			 Thread.sleep(5000);
           //Country -> Canada
		    hmcPage.B2BpriceSimulate_CountryButton.click();
			System.out.println("country click");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys("Canada");
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("contry sendkey");
		    Thread.sleep(3000);
		    // Store -> Canada LE Store
		 	Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_StoreButton, 120);
		 	hmcPage.B2BpriceSimulate_StoreButton.click();
		 	System.out.println("store click");
		 	Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 10);		 	
		 	hmcPage.B2BpriceSimulate_CountryText.sendKeys("Canada LE Store");		 			
		 	hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
		 	System.out.println("store sendkey:");
		 	Thread.sleep(3000);
	        // Catalog version -> Nemo Master Multi Country Product Catalog - Online
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CatalogButton, 120);
			hmcPage.B2BpriceSimulate_CatalogButton.click();
			System.out.println("catalog click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys("Nemo Master Multi Country Product Catalog - Online");
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("catalog sendkey:");
			Thread.sleep(3000);
			// B2B Unit -> 1214531127
			Common.checkElementExists(driver, hmcPage.B2BpriceSimulate_B2BUnitDiv, 120);
			hmcPage.B2BpriceSimulate_B2BUnitButton.click();
			System.out.println("B2BUnit click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 120);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys("1214576398");
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("B2BUNIT sendkey:");
			Thread.sleep(3000);
			// Price Date -> now Date
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_DateButton, 120);
			hmcPage.B2BpriceSimulate_DateButton.click();
		    System.out.println("data click:");
			hmcPage.B2BpriceSimulate_DateButton.sendKeys(Keys.ENTER);
			System.out.println("data sendkey:");
			Thread.sleep(3000);
			// Product -> 4X20E75063
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_ProductButton, 120);
			hmcPage.B2BpriceSimulate_ProductButton.click();
			System.out.println("product click:");
			Common.waitElementClickable(driver, hmcPage.B2BPriceRules_SearchInput, 120);
			hmcPage.B2BPriceRules_SearchInput.sendKeys(productNo);
			Thread.sleep(5000);
			xpath="//span[contains(text(),'"+productNo+"')]";
			System.out.println(xpath);
			WebElement productResult = driver.findElement(By.xpath(xpath));
			Common.waitElementVisible(driver, productResult);
			productResult.click();
			System.out.println("product sendkey:");
			Thread.sleep(3000);
			hmcPage.B2BpriceSimulate_DebugButton.click();
			Thread.sleep(3000);
			Common.waitElementVisible(driver, hmcPage.B2BpriceSimulate_webPrice);
			String debugPriceText = hmcPage.B2BpriceSimulate_webPrice.getText().toString();
			System.out.println("debugPriceText"+debugPriceText);
			double debugPrice = String2Num(debugPriceText);
			System.out.println("product debug price : " + debugPrice);
			Dailylog.logInfoDB(4,"Product Price:"+debugPrice+"CAD", Store,testName);
			B2Bprice = new  java.math.BigDecimal(Double.parseDouble(getProductPrice)* Double.parseDouble(getProductRate)).setScale(2,java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out.println("B2Bprice:"+B2Bprice);		
			// check the price in HMC and B2B is equal
			if (debugPrice != B2Bprice) {
				Assert.fail("B2B Price " + B2Bprice + " is not equal to HMC debug price !!" + debugPrice);
				}
			Dailylog.logInfoDB(5,"debugPrice(CAD):"+debugPrice+"="+"Product Price(USD):"+getProductPrice+"*"+"Exchange Rate:"+getProductRate, Store,testName);
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}	
	}
	private double String2Num(String valueString) {
		String price = valueString.replace("$", "").replace("￥", "").replace(",", "").replace("-", "");
		return Double.parseDouble(price);
	}
}
		