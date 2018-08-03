package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27225Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	private String b2bUnit;
	public NA27225Test(String store){
		this.Store = store;
		this.testName = "NA-27225";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA27225(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			b2bUnit = testData.B2B.getB2BUnit();
			//1.login hmc
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "login hmc", Store, testName);
			
			//2-3search B2B unit by ID 
			searchB2BUnit(hmcPage, b2bUnit);
			Dailylog.logInfoDB(2, "3.search B2B unit by ID", Store, testName);
			
			//4-5.click site attribute tab
			hmcPage.B2BUnit_siteAttribute.click();
			Common.scrollToElement(driver, hmcPage.EnableProductBuilder);
			setHmcState(hmcPage.EnableProductBuilder,hmcPage.AccessoryAddToggle,hmcPage.HideAddToCart);
			Dailylog.logInfoDB(4, "5.search B2B unit by ID", Store, testName);
			
			//6.logon website
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(testData.B2B.getHomePageUrl());
			Common.sleep(3000);
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			setLoadWebsite();
			Dailylog.logInfoDB(6, "Open one website", Store, testName);
			
			//7.click products  and choose LAPTOPS&ULTRABOOKS
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, b2bPage.productPage_AlertAddToCart, 10));
			Assert.assertTrue(Common.checkElementExists(driver, b2bPage.ProductPage_addAccessories, 10));
			Dailylog.logInfoDB(7, "click products  and choose LAPTOPS&ULTRABOOKS", Store, testName);
			
			//8.click add accessory button
			Common.sleep(3000);
			b2bPage.ProductPage_addAccessories.click();
			Dailylog.logInfoDB(8, "click add accessory button", Store, testName);
			
			//9.click add to cart button
			Common.sleep(3000);
			B2BCommon.clearTheCart(driver, b2bPage);
			Common.sleep(3000);
			if(Common.checkElementExists(driver, driver.findElement(By.xpath("//button[contains(@class,'pricingSummary-button button-called-out button-full')]")), 10)) {
				driver.findElement(By.xpath("//button[contains(@class,'pricingSummary-button button-called-out button-full')]")).click();
			}
			if(Common.checkElementExists(driver, b2bPage.accessoryTabAdd, 10)) {
				b2bPage.accessoryTabAdd.click();
			}
			
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//label[contains(@class,'cart-item-pricing-and-quantity-form-label')]")), 10));
			Dailylog.logInfoDB(9, "click add to cart button", Store, testName);
			
			//10.change the Accessory Add Toggle value 
			Common.switchToWindow(driver, 0);
			hmcPage.B2BUnit_siteAttribute.click();
			Common.scrollToElement(driver, hmcPage.AccessoryAddToggleFalse);
			setHmcState(hmcPage.EnableProductBuilder,hmcPage.AccessoryAddToggleFalse,hmcPage.HideAddToCart);
			Dailylog.logInfoDB(10, "change the Accessory Add Toggle value ", Store, testName);
			
			//11.continue to step 6,7
			Common.switchToWindow(driver, 1);
			setLoadWebsite();
			Assert.assertTrue(Common.checkElementExists(driver, b2bPage.productPage_AlertAddToCart, 10));
			Assert.assertTrue(!Common.checkElementExists(driver, b2bPage.ProductPage_addAccessories, 10));
			Dailylog.logInfoDB(11, "continue to step 6,7", Store, testName);
			
			//12.change the site attribute value and click save button
			Common.switchToWindow(driver, 0);
			hmcPage.B2BUnit_siteAttribute.click();
			Common.scrollToElement(driver, hmcPage.EnableProductBuilder);
			setHmcState(hmcPage.EnableProductBuilder,hmcPage.AccessoryAddToggleFalse,hmcPage.HideAddToCartTrue);
			Assert.assertTrue(Common.checkElementExists(driver, hmcPage.ErrorMessage, 10));
			Dailylog.logInfoDB(12, "change the site attribute value and click save button", Store, testName);
			
			//13.change the site attribute value
			hmcPage.ErrorMessageok.click();
			hmcPage.B2BUnit_siteAttribute.click();
			Common.scrollToElement(driver, hmcPage.EnableProductBuilder);
			setHmcState(hmcPage.EnableProductBuilder,hmcPage.AccessoryAddToggle,hmcPage.HideAddToCartTrue);
			Dailylog.logInfoDB(13, "change the site attribute value", Store, testName);
			
			//14.continue to step 6,7
			Common.switchToWindow(driver, 1);
			setLoadWebsite();
			Assert.assertTrue(Common.checkElementExists(driver, b2bPage.ProductPage_addAccessories, 10));
			Dailylog.logInfoDB(11, "continue to step 6,7", Store, testName);
			
			//15.16.set the attribute value 
			Common.sleep(3000);
			b2bPage.ProductPage_addAccessories.click();
			Common.sleep(3000);
			Common.switchToWindow(driver, 0);
			hmcPage.B2BUnit_siteAttribute.click();
			Common.scrollToElement(driver, hmcPage.EnableProductBuilderFalse);
			setHmcState(hmcPage.EnableProductBuilderFalse,hmcPage.AccessoryAddToggle,hmcPage.HideAddToCart);
			Dailylog.logInfoDB(15, "15.16.set the attribute value", Store, testName);
			
			//17.continue to step 6,7 and then click add accessory button
			Common.switchToWindow(driver, 1);
			setLoadWebsite();
			Common.sleep(3000);
			b2bPage.ProductPage_addAccessories.click();
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//h1[contains(@class,'bar_3-heading')]")), 10));
			Dailylog.logInfoDB(17, "continue to step 6,7 and then click add accessory button", Store, testName);
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void setLoadWebsite() {
	    b2bPage.HomePage_productsLink.click();
		if(Common.checkElementExists(driver,b2bPage.HomePage_Laptop, 10)) {
			b2bPage.HomePage_Laptop.click();
		}else if(Common.checkElementExists(driver, b2bPage.HomePage_Destop, 10)){
			b2bPage.HomePage_Destop.click();
		}
		
	}

	public void setHmcState(WebElement enableProductBuilder, WebElement accessoryAddToggle, WebElement hideAddToCart) throws InterruptedException {
		enableProductBuilder.click();
		accessoryAddToggle.click();
		if(Common.checkElementExists(driver, hideAddToCart, 10)) {
			hideAddToCart.click();
		}else {
			Common.scrollToElement(driver, hmcPage.administration);
			hmcPage.administration.click();
			Common.scrollToElement(driver, hideAddToCart);
			hideAddToCart.click();
		}
		
		hmcPage.PaymentLeasing_saveAndCreate.click();
	}
	
	@Test(priority=1,alwaysRun = true,groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void rollBack(ITestContext ctx){
		try{
			SetupBrowser();
			driver.manage().deleteAllCookies();
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			hmcPage = new HMCPage(driver);
			driver.get(hmcLoginURL);
			Common.sleep(2000);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(2000);
			searchB2BUnit(hmcPage, b2bUnit);
			hmcPage.B2BUnit_siteAttribute.click();
			setHmcState(hmcPage.EnableProductBuilder,hmcPage.AccessoryAddToggle,hmcPage.HideAddToCart);
			Common.sleep(3000);
			hmcPage.HMC_Logout.click();
			Common.sleep(1500);
		}
		catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public static void searchB2BUnit(HMCPage hmcPage, String unit) {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(unit);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
	}

}
	
			
