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

public class NA18380Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String partnumber;

	public NA18380Test(String store,String partnumber) {
		this.Store = store;
		this.testName = "NA-18380";
		this.partnumber = partnumber;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18380(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getloginPageUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL);
			Common.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			if(driver.getCurrentUrl().contains("lenovousepp")) {
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
				Common.sleep(2000);
				B2CCommon.closeHomePagePopUP(driver);
				Common.sleep(2000);
				b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.clear();
				b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.sendKeys(testData.B2C.getGateKeeperSerialNmuber());
				b2cPage.SerialNumberGateKeeper_LastNameTextBox.clear();
				b2cPage.SerialNumberGateKeeper_LastNameTextBox.sendKeys(testData.B2C.getGateKeeperSerialLastName());
				b2cPage.GateKeeper_LoginButton.click();
			}else {
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
				Common.sleep(2000);
				B2CCommon.closeHomePagePopUP(driver);
			}
			Dailylog.logInfoDB(1, "Logged into B2C website", Store, testName);

			//=========== Step:2 Find a product ===========//
			driver.get(b2cHomeURL+"/p/"+partnumber);
			Dailylog.logInfoDB(2, "MTM part Num is: " + partnumber, Store, testName);
			Common.sleep(2000);

			//=========== Step:3 Go to HMC->B2C Commerce->B2C Unit, and search particular unit ===========//
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

			//=========== Step:4 Click on searched result and to go 'site attributes' tab ===========//
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(4, "Site Attribute tab is clicked.", Store, testName);

			//=========== Step:5 find 'Notify Me' field, and set the value to yes, and save ===========//
			Common.scrollToElement(driver, hmcPage.NotifyMeToggle);
			hmcPage.NotifyMeToggle_yes.click();
			Common.sleep(3000);
			hmcPage.Catalog_save.click();
			Dailylog.logInfoDB(5, "Site Attribute tab is clicked.", Store, testName);

			//=========== Step:6 In HMC, go to Catelog->Products, and search the product number captured in step2, catelog version should be 'masterMultiCountryProductCatalog' ===========//
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
			
			//=========== Step:7 click on the searched result and go to Administration tab ===========//
			hmcPage.Product_admin.click();
			Dailylog.logInfoDB(7, "click on the searched result and go to Administration tab", Store, testName);
			
			//=========== Step:8 find 'Notify Me' field and set the value to yes, and save ===========//
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.Admin_notify);
			hmcPage.Admin_yes.click();
			Common.sleep(3000);
			hmcPage.Catalog_save.click();
			Dailylog.logInfoDB(8, "find 'Notify Me' field and set the value to yes, and save", Store, testName);
			
			//=========== Step:9 Go to PMI tab and, find 'Product Marketing Status' field and select 'CommingSoon' and save ===========//
			hmcPage.Catalog_PMITab.click();
			Common.sleep(3000);
			hmcPage.Catalog_Products_MarketingStatus.click();
			hmcPage.Catalog_ProductStatus_ComingSoon.click();
			Common.sleep(2000);
			hmcPage.HMC_Save.click();
			Common.sleep(3000);	
			Dailylog.logInfoDB(9, "Go to PMI tab and, find 'Product Marketing Status' field and select 'CommingSoon' and save", Store, testName);
			
			//=========== Step:10 Go to b2c ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			Common.sleep(2000);
			if(Common.checkElementExists(driver, b2cPage.Commingsoon, 5) && Common.checkElementExists(driver, b2cPage.Notifyme, 5)) {
				Common.sleep(1000);			
				String soldOutMsg = b2cPage.Commingsoon.getText();
				String notifyMe = b2cPage.Notifyme.getText();
				Assert.assertTrue(soldOutMsg.contains("COMING"));
				Assert.assertTrue(notifyMe.contains("Notify"));
				Dailylog.logInfoDB(9, "Product status after setting the status to 'COMING SOON' :" + soldOutMsg, Store, testName);
			}else {
				Assert.fail("has no RedBanner or notify me");
			}
			
			
			//=========== Step:11 After testing, change back to previous setting ===========//
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			hmcPage.Catalog_Products_MarketingStatus.click();
			hmcPage.Catalog_ProductStatus_empty.click();
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Dailylog.logInfoDB(11, "After testing, change back to previous setting' :", Store, testName);

		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

}
