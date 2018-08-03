package TestScript.B2C;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17627Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String partNum;
	private String US = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/us/en";
	private String JP = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/jp/ja";
	private String CA = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/ca/en/webca";
	private String AU = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/au/en/";

	public NA17627Test(String store,String partNum) {
		this.Store = store;
		this.testName = "NA-17627";
		this.partNum = partNum;
	}
	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA17627(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			String b2cHomeURL = testData.B2C.getHomePageUrl();

			//=========== Step:1 open B2C URL ===========//
			driver.get(testData.B2C.getHomePageUrl());
			Common.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "load b2c: ", Store, testName);
			
			//=========== Step:2 Find a tablet product under Product->Tablet, this product should have available models partnumber ===========//
			Common.mouseHover(driver, b2cPage.Navigation_ProductsLink);
			Common.sleep(3000);
			Common.mouseHover(driver, b2cPage.Navigation_Tablets);
			Common.sleep(3000);
			Common.mouseHover(driver, b2cPage.Tablets_First);
			Dailylog.logInfoDB(2, "should have available models" + partNum, Store, testName);
			
			//=========== Step:3  Go to HMC->Catalog->Products,===========//
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			Common.sleep(3000);		
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partNum);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(3000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(3000);
			Dailylog.logInfoDB(3, "search result : ",Store, testName);
			
			//=========== Step:4  double click on the product and go to PMI tab===========//
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(3000);
			hmcPage.Catalog_PMITab.click();
			Dailylog.logInfoDB(4, "PMI tab is clicked.", Store, testName);
			
			//=========== Step:5  Update Marketing Status-> End of life, to Yes, and click save ===========//	
			Common.sleep(3000);
			hmcPage.Catalog_yes.click();
			Common.sleep(2000);
			hmcPage.HMC_Save.click();
			Dailylog.logInfoDB(5, "End of life, to Yes.", Store, testName);
			
			//=========== Step:6 Go to Category System tab ===========//
			Common.sleep(3000);
			hmcPage.Products_categorySystem.click();
			Dailylog.logInfoDB(6, "Go to Category System tab.", Store, testName);

			//=========== Step:7 Products referenced by this product -> Product references, Select the alternative order unit, maintain the Targetproduct: i.e. 22TP2TX132E, check the "Active" checkbox, click on Save ===========//
			Common.sleep(3000);
			hmcPage.Products_Typecontent.click();
			if(hmcPage.Products_checkbox.isSelected()) {
				hmcPage.Products_checkbox.click();
			}
			hmcPage.Products_region.click();
			hmcPage.Products_country.click();
			Common.scrollToElement(driver, hmcPage.Products_Active);
			Common.sleep(3000);
			if(!hmcPage.Products_Active.isSelected()) {
				hmcPage.Products_Active.click();
			}
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Dailylog.logInfoDB(7, "Select the alternative order unit", Store, testName);
			
			//=========== Step:8 go to product url for 5 regions  ===========//
			setRegions(AU, 2);
			setRegions(US, 3);
			setRegions(JP, 4);
			setRegions(CA, 5);
		
			//=========== Step:10 Refer to step 5: End of life, to No, and click save  ===========//
			Common.switchToWindow(driver, 1);
			hmcPage.Catalog_PMITab.click();
			Common.sleep(3000);
			hmcPage.Catalog_no.click();
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Common.sleep(3000);
			hmcPage.Products_categorySystem.click();
			Common.sleep(3000);
			hmcPage.Products_Typecontent_back.click();
			if(hmcPage.Products_Active.isSelected()) {
				hmcPage.Products_Active.click();
			}
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Dailylog.logInfoDB(10, "After testing, change back to previous setting", Store, testName);
		    
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	private void setRegions(String country,int window) {
		((JavascriptExecutor)driver).executeScript("(window.open(''))");
		Common.switchToWindow(driver, window);
		driver.get(country + "/p/" + partNum);
		Common.sleep(3000);
		Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.popup, 5));
		Dailylog.logInfoDB(8, "go to product url for 5 regions"+country, Store, testName);
		
		//=========== Step:9 Click on Learn more on the popup window ===========//
		Common.sleep(2000);
		b2cPage.Learnmore.click();
	    String[] split2 = driver.getCurrentUrl().split("/");
	    String newPartnum = split2[split2.length-1];
	    Assert.assertNotEquals(partNum, newPartnum);
	    Dailylog.logInfoDB(9, "should be a popup window", Store, testName);
	}
}
