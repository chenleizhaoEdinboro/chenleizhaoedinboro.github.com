package TestScript.B2C;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17361Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String partnumber;

	public NA17361Test(String store,String partnumber) {
		this.Store = store;
		this.testName = "NA-17361";
		this.partnumber = partnumber;
	}
	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA17361(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getHomePageUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL);
			Common.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "CTO part Num is: " + partnumber, Store, testName);

			//=========== Step:2 Accessing pdp page url using part num ===========//
			driver.get(b2cHomeURL + "/p/" + partnumber);
			Dailylog.logInfoDB(2, "load partnum product ", Store, testName);

			//=========== Step:3 Go to HMC and search for the unit ===========//
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(3, "Logged into HMC", Store, testName);
			Common.sleep(3000);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Dailylog.logInfoDB(3, "Clicked search results", Store, testName);
			Common.sleep(2000);

			//=========== Step:4 Go to Site Attribute Tab ===========//
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(4, "Site Attribute tab is clicked.", Store, testName);

			//=========== Step:5 Set Sold to and temp unavailable messages ===========//			
			hmcPage.B2CUnit_SiteAttributeTab_SoldOutMessage.clear();
			hmcPage.B2CUnit_SiteAttributeTab_SoldOutMessage.sendKeys("SoldOut");
			hmcPage.B2CUnit_SiteAttributeTab_TempUnavailableMessage.clear();
			hmcPage.B2CUnit_SiteAttributeTab_TempUnavailableMessage.sendKeys("TempUnavailable");
			hmcPage.HMC_Save.click();
			Dailylog.logInfoDB(5, "Sold Out and Temp unavailable msg are saved", Store, testName);
			Common.sleep(3000);	

			//=========== Step:6 Set Catalog Version ===========//
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partnumber);
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			Dailylog.logInfoDB(6, "Click on search result(Searched with part number)", Store, testName);
			
			//=========== Step:7 Select PMI tab ===========//
			hmcPage.Catalog_PMITab.click();
			Dailylog.logInfoDB(7, "PMI tab is clicked.", Store, testName);
			
			//=========== Step:8 Set Product Marketing status as "soldout" ===========//
			setMarketingStatus(hmcPage.Catalog_ProductStatus_SoldOut);
			Dailylog.logInfoDB(8, "Product marketing status is set to 'soldOut.", Store, testName);
			hmcPage.Home_System.click();
			cleanProductCache();
			Dailylog.logInfoDB(8, "Product cache is cleaned", Store, testName);
			Common.sleep(1500);

			//=========== Step:9 Go to B2C website and check the status ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			Common.sleep(4000);
			if(Common.checkElementExists(driver, b2cPage.B2C_PLP_RedBannerTxtUSepp, 5)) {
				Common.sleep(1000);			
				String soldOutMsg = b2cPage.B2C_PLP_RedBannerTxtUSepp.getText();
				Assert.assertTrue(soldOutMsg.contains("Sold"));
				Dailylog.logInfoDB(9, "Product status after setting the status to 'soldout' :" + soldOutMsg, Store, testName);
			}else if(Common.checkElementExists(driver, b2cPage.B2C_PLP_RedBannerTxt, 5)) {
				Common.sleep(1000);			
				String soldOutMsg = b2cPage.B2C_PLP_RedBannerTxt.getText();
				Assert.assertTrue(soldOutMsg.contains("Sold"));
				Dailylog.logInfoDB(9, "Product status after setting the status to 'soldout' :" + soldOutMsg, Store, testName);
			}else {
				Assert.fail("has no RedBanner");
			}

			//=========== Step:10 Go to HMC and change marketing status to "temp available" ===========//
			Common.switchToWindow(driver, 1);
			hmcPage.Home_ProductsLink.click();
			setMarketingStatus(hmcPage.Catalog_ProductStatus_TempUnavailable);
			cleanProductCache();
			Dailylog.logInfoDB(10, "Product marketing status is set to 'temp unavailable." , Store, testName);
			Common.sleep(3000);

			//=========== Step:11 Go to B2C website and check the status ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			Common.sleep(4000);
			if (Common.checkElementExists(driver, b2cPage.B2C_PLP_RedBannerTxtUSepp, 5)) {
				Common.sleep(1000);
				String tempUnavailableMsg = b2cPage.B2C_PLP_RedBannerTxtUSepp.getText();
				Dailylog.logInfoDB(11, "Product status after setting the status to 'Temp unavailable' :" + tempUnavailableMsg, Store, testName);
				Assert.assertTrue(tempUnavailableMsg.contains("Unavailable"));
			}else if(Common.checkElementExists(driver, b2cPage.B2C_PLP_RedBannerTxt, 5)) {
				Common.sleep(1000);			
				String tempUnavailableMsg = b2cPage.B2C_PLP_RedBannerTxt.getText();
				Dailylog.logInfoDB(11, "Product status after setting the status to 'Temp unavailable' :" + tempUnavailableMsg, Store, testName);
				Assert.assertTrue(tempUnavailableMsg.contains("Unavailable"));
			}else {
				Assert.fail("has no RedBanner");
			}
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	private void setMarketingStatus(WebElement productStatus){

		hmcPage.Catalog_Products_MarketingStatus.click();
		productStatus.click();
		Common.sleep(2000);
		hmcPage.HMC_Save.click();
		Common.sleep(3000);		
	}

	private void cleanProductCache(){		
		hmcPage.Home_RadisCacheCleanLink.click();
		driver.switchTo().frame(0);
		hmcPage.Radis_CleanProductTextBox.sendKeys(partnumber);
		hmcPage.Radis_CleanButton.click();
		Common.sleep(15000);
		driver.switchTo().alert().accept();
		Common.sleep(1000);
		driver.switchTo().defaultContent();
	}
	
	
	@Test(priority = 1,alwaysRun = true,  enabled = true)
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
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partnumber);
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			hmcPage.Catalog_PMITab.click();
			setMarketingStatus(hmcPage.Catalog_ProductStatus_InIt);
			Common.sleep(3000);
			hmcPage.HMC_Logout.click();
			Common.sleep(1500);
		}
		catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
}
