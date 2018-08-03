package TestScript.B2C;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17612Test extends SuperTestClass {
	private HMCPage hmcPage;
	private String country;
	
	public NA17612Test(String store,String country) {
		this.Store = store;
		this.country = country;
		this.testName = "NA-17612";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA17612(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			String b2cPageUrl = testData.B2C.getHomePageUrl();

			// =========== Step:1 login to HMC ===========//
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			((JavascriptExecutor) driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			driver.get(b2cPageUrl + "/laptops/c/LAPTOPS");
			Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);

			// =========== Step:2 Search 'Malaysia' in B2C Commerce -> B2C Unit
			Common.sleep(3000);
			Common.switchToWindow(driver, 0);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.sendKeys(country);
			Common.sleep(5000);
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_SecondSearchResultItem_icon.click();
			hmcPage.B2CUnit_SecondSearchResultItem.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(2, "Clicked search results", Store, testName);

			// =========== Step:3 Go to Site Attribute Tab Choose yes
			// ===========//
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			Common.scrollToElement(driver, hmcPage.HideFacet);
			hmcPage.Radio1.click();
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Common.sleep(3000);
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			driver.navigate().refresh();
			Assert.assertTrue(!Common.checkElementDisplays(driver, hmcPage.NarrowResults, 5));
			Common.sleep(3000);
			Dailylog.logInfoDB(3, "Choose yes.", Store, testName);

			// =========== Step:4 On the site Attribute Tab find “hideFacetNav
			// Choose no ===========//
			Common.switchToWindow(driver, 0);
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.HideFacet);
			hmcPage.Radio2.click();
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Common.sleep(3000);
			Common.switchToWindow(driver, 1);
			driver.navigate().refresh();
			Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.NarrowResults, 5));
			Dailylog.logInfoDB(4, "Choose no", Store, testName);

			// =========== Step:5 On the site Attribute Tab find “hideFacetNav
			// Choose n/a ===========//
			// https://www3.lenovo.com/au/en/laptops/c/LAPTOPS
			Common.switchToWindow(driver, 0);
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.HideFacet);
			hmcPage.Radio3.click();
			Common.sleep(3000);
			hmcPage.HMC_Save.click();
			Common.sleep(3000);
			Common.switchToWindow(driver, 1);
			driver.navigate().refresh();
			Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.NarrowResults, 5));
			Dailylog.logInfoDB(5, "Choose n/a", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
