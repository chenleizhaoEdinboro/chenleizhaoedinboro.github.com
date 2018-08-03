package TestScript.B2C;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE244Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;

	public BROWSE244Test(String store){
		this.Store = store;
		this.testName = "BROWSE-244";	
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void BROWSE244Test(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			
			//=========== Step:1 Find product in frontpage and find it's product number ===========//
			Dailylog.logInfoDB(1, "Find product in frontpage and find it's product number", Store, testName);
			driver.get(testData.B2C.getHomePageUrl());
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			String laptopsUrl = driver.getCurrentUrl();
			b2cPage.filter_by_specs.click();
			Common.sleep(3000);
			b2cPage.screen_size.click();
			b2cPage.select_screen_size.click();							
			Common.sleep(5000);
			b2cPage.screen_size.click();			
			
			int i = 3;
			String productNo = null;
			List<WebElement> result = driver.findElements(By.xpath("//*[contains(@class,'facetedResults-item')]"));
			System.out.println("use screen size is 11.6 have : " + result);
			for(;i<result.size()*3;) {
				WebElement product_status = driver.findElement(By.xpath("(//div[@id='resultsList']/div)["+i+"]//div[@class='pricingSummary-shipping']"));
				String product_statusInfo = product_status.getText().toString();
				System.out.println("the product in the " + i/3 + "position");
				if(product_statusInfo.contains("ships") || product_statusInfo.contains("Ships")) {
					WebElement product_herf = driver.findElement(By.xpath("(//div[@id='resultsList']/div)["+i+"]/div[1]/a"));
					productNo = product_herf.getAttribute("href").split("/p/")[1];
					System.out.println(productNo);
					break;
				}else {
					i = i+3;
					continue;
				}
			}
			

			//=========== Step:2 Change the base product's status to sold out ===========//
			Dailylog.logInfoDB(2, "Change the base product's status to sold out", Store, testName);
			changeProductStatus(productNo,"sold out");
			driver.get(laptopsUrl);
			Common.sleep(5000);
			b2cPage.filter_by_specs.click();
			Common.sleep(3000);
			b2cPage.screen_size.click();
			b2cPage.select_screen_size.click();
			Common.sleep(5000);
			b2cPage.screen_size.click();			
			String soldOut_message = driver.findElement(By.xpath("(//div[@id='resultsList']/div)["+i+"]//div[@class='pricingSummary-shipping']")).getText().toString();
			System.out.println(productNo + " product status message is: " + soldOut_message + "null");
			Assert.assertTrue(soldOut_message.isEmpty(), "The product not shown as Sold Out in faceted browse results");	
			
			
			//=========== Step:3 Change the base product's status to available ===========//
			Dailylog.logInfoDB(3, "Change the base product's status to available", Store, testName);
			changeProductStatus(productNo,"available");
			driver.get(laptopsUrl);
			Common.sleep(5000);
			b2cPage.filter_by_specs.click();
			Common.sleep(3000);
			b2cPage.screen_size.click();
			b2cPage.select_screen_size.click();
			Common.sleep(5000);
			b2cPage.screen_size.click();			
			String available_message = driver.findElement(By.xpath("(//div[@id='resultsList']/div)["+i+"]//div[@class='pricingSummary-shipping']")).getText().toString();
			System.out.println(productNo + " product status message is: " +available_message);
			Assert.assertTrue(available_message.contains("ships") || available_message.contains("Ships"), "The product not shown as ships in faceted browse results");												
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void changeProductStatus(String productNu,String status) throws Exception {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchOnlineProduct(driver, hmcPage, productNu);			
		
		while(Common.checkElementExists(driver, hmcPage.Products_baseProduct, 3)) {
			Common.rightClick(driver, hmcPage.Products_baseProduct);
			hmcPage.Products_edit.click(); 
		}		
		hmcPage.subseries_PMItab.click();
		if(status.equals("sold out")){
			Common.scrollAndClick(driver, hmcPage.Catalog_ProductStatus_SoldOut);
		}else{
			Common.scrollAndClick(driver, hmcPage.productStatus_available);
		}		
		hmcPage.SaveButton.click();
		Common.sleep(3000);
		HMCCommon.cleanRadis(hmcPage, productNu);	
		Common.sleep(3000);		
		cleanCache();
		Common.sleep(3000);
	}
	
	public void cleanCache(){
		driver.get("chrome://settings/clearBrowserData");
		Common.sleep(2000);
		
		driver.findElement(By.cssSelector("* /deep/ #clearBrowsingDataConfirm")).click();	
		Common.sleep(15000);
	}
}