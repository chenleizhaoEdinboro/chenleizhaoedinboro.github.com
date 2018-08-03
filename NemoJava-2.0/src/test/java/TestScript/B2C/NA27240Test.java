package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
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

public class NA27240Test extends SuperTestClass {
	private String RangeName;
	private HMCPage hmcPage; 
	private B2CPage b2cPage; 
	
	
	public NA27240Test(String store,String RangeName) {
		this.Store = store;
		this.RangeName = RangeName;
		this.testName = "NA-27240";
		
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","facetsearch",  "p2", "b2c"})
	public void NA27240(ITestContext ctx) {
		try {
			this.prepareTest();
			String DefaultName;
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			//find range on website
			driver.get(testData.B2C.getHomePageUrl());
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.AllInOnes);
			if(!b2cPage.Facet_PriceFacet.getAttribute("class").contains("expanded")){
				b2cPage.Facet_PriceFacet.click();
			}
			String firstPriceValure= b2cPage.Facet_PriceFirstRange.getAttribute("value");
			String firstPriceRange = firstPriceValure.split(":")[firstPriceValure.split(":").length-1];
			Dailylog.logInfoDB(1, "get the name of first fecet: "+firstPriceRange, Store, testName);
			//setting the range on HMC
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			openRange(RangeName);
			Dailylog.logInfoDB(1, "open the name of first fecet: "+firstPriceRange, Store, testName);
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY");
			DefaultName =  df.format(new Date())+"_"+Store+"rangeNameTest";
			String currentHandle = driver.getWindowHandle();
			driver.findElement(By.xpath("//div[text()='"+firstPriceRange+"']/../../..//a")).click();
			//hmcPage.Range_FirstRangeEditor.click();
			changeWindow(true,currentHandle);
			hmcPage.FirstRange_Language.click();
			if(Store.contains("US")){
				hmcPage.RangeUSInput.clear();
				hmcPage.RangeUSInput.sendKeys(DefaultName);
			}else if (Store.contains("JP")){
				hmcPage.RangeJPInput.clear();
				hmcPage.RangeJPInput.sendKeys(DefaultName);
			}
			Dailylog.logInfoDB(1, "input the name: "+DefaultName, Store, testName);
			hmcPage.SaveButton.click();
			currentHandle = driver.getWindowHandle();
			changeWindow(false,currentHandle);
			hmcPage.SaveButton.click();
			hmcPage.Home_EndSessionLink.click();
			driver.manage().deleteAllCookies();
			Common.sleep(10000);
			driver.get(testData.B2C.getHomePageUrl());
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.AllInOnes);
			WaitingRangeName(0,DefaultName);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//label[contains(.,'"+DefaultName+"')]"), 5), 
					"Range name has been changed");
			
			//Check on website
			}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
	}
	public void WaitingRangeName( int i,String DefaultName) throws InterruptedException{
		if(i<=3){
			if(!b2cPage.Facet_PriceFacet.getAttribute("class").contains("expanded")){
				Common.scrollToElement(driver, b2cPage.Facet_PriceFacet);
				b2cPage.Facet_PriceFacet.click();
			}
			if(!Common.checkElementDisplays(driver, By.xpath("//label[contains(.,'"+DefaultName+"')]"), 5)){
				Common.sleep(10000);
				driver.navigate().refresh();
				WaitingRangeName( i+1, DefaultName);
			}
		}
	}
	
	public void openRange(String rangename){
		hmcPage.Home_System.click();
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_facet_range.click();
		hmcPage.Range_name.clear();
		hmcPage.Range_name.sendKeys(rangename);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.Range_editor.click();
	}
	public void changeWindow(boolean flag,String currentHandle){
		Common.sleep(3000);
		Set<String> windowHandles = driver.getWindowHandles();
		if(flag){
			for(String hanedle:windowHandles){
				if(!hanedle.equals(currentHandle)){
					driver.switchTo().window(hanedle);
				}
			}
		}else{
			driver.close();
			for(String hanedle:windowHandles){
				if(!hanedle.equals(currentHandle)){
					driver.switchTo().window(hanedle);
				}
			}
			
		}
	}
	
}
