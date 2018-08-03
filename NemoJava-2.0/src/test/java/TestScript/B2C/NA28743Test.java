package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class NA28743Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public NA28743Test(String store) {
		this.Store = store;
		this.testName = "NA-28743";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28743(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			int i=0;
			
			//check meta of description on home page
			driver.get(testData.B2C.getHomePageUrl());	
			i=getMetaNum("description");
			Logger.Dailylog.logInfoDB(1, "meta data num is "+i, Store, testName);
			if(i<=0||i>20){
				Assert.fail("description id not in 20");
			}
			
			//check meta of description on mtm product page
			i=0;
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			if(!Common.isElementExist(driver, By.xpath("//meta[@name='description']"))){
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				setMetaDescription(testData.B2C.getDefaultMTMPN(),"auto test meta description");
				HMCCommon.cleanRadis(hmcPage, testData.B2C.getDefaultMTMPN());
				hmcPage.Home_closeSession.click();
				driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
				driver.manage().deleteAllCookies();
				Common.sleep(100000);
				driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			}
			i=getMetaNum("description");
			Logger.Dailylog.logInfoDB(2, "meta data num is "+i, Store, testName);
			if(i<=0||i>20){
				Assert.fail("description id not in 20");
			}
			//check meta of description on cto product page
			i=0;
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());	
			if(!Common.isElementExist(driver, By.xpath("//meta[@name='description']"))){
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				setMetaDescription(testData.B2C.getDefaultCTOPN(),"auto test meta description");
				HMCCommon.cleanRadis(hmcPage, testData.B2C.getDefaultCTOPN());
				hmcPage.Home_closeSession.click();
				driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());
				driver.manage().deleteAllCookies();
				Common.sleep(100000);
				driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());
			}
			i=getMetaNum("description");
			Logger.Dailylog.logInfoDB(3, "meta data num is "+i, Store, testName);
			if(i<=0||i>20){
				Assert.fail("description id not in 20");
			}
			//check meta of description on ACCESSORY page
			i=0;
			driver.get(testData.B2C.getHomePageUrl());
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);		
			i=getMetaNum("description");
			Logger.Dailylog.logInfoDB(4, "meta data num is "+i, Store, testName);
			if(i<=0||i>20){
				Assert.fail("description id not in 20");
			}
		
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public int getMetaNum(String name){
		int i=0;
		List<WebElement> MetaList= b2cPage.MetaList;
		for(WebElement ele: MetaList){
			i++;
			//System.out.println(ele.getAttribute("name"));
			if(ele.getAttribute("name").equals(name)){
				return i;
			}
		}
		return i;
	}
	
	public void setMetaDescription(String partNum,String description){
		searchOnlineProduct(partNum);
		hmcPage.Catalog_PMITab.click();
		hmcPage.Catalog_Products_SEODescription.clear();
		hmcPage.Catalog_Products_SEODescription.sendKeys("AUTO TEST DESCRIPTION");
		hmcPage.SaveButton.click();
	}
	
	public void searchOnlineProduct(String partNum){
		if(!Common.checkElementDisplays(driver, hmcPage.Home_ProductsLink, 3)) {
			hmcPage.Home_CatalogLink.click();
		}
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partNum);
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		Common.sleep(1000);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(2000);
		Dailylog.logInfoDB(11, "Click on search result(Searched with part number)", Store, testName);
	}
	
	

}
