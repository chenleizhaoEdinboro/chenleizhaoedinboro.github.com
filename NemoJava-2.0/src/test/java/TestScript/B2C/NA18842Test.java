package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18842Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String cssAndjs;

	public NA18842Test(String store,String cssAndjs) {
		this.Store = store;
		this.testName = "NA-18842";
		this.cssAndjs = cssAndjs;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18842(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			String hmcLoginURL = testData.HMC.getHomePageUrl();

			// Step~1 login hmc, navigate to Multimedia-> Media 
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			hmcPage.MultiMedia_Menu.click();
			hmcPage.Multimedia_media.click();
			Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);
			
			// Step~2 Search below media file by below search criteria:Identifier:about-lenovo-2014-cssv2
			Common.sleep(2000);
			hmcPage.Media_id.clear();
			hmcPage.Media_id.sendKeys(cssAndjs);
			hmcPage.Media_categoryversion.click();
			hmcPage.Media_search.click();
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.Media_search_result, 5));
			Dailylog.logInfoDB(2, "Search below media file by below search criteria:Identifier:about-lenovo-2014-cssv2", Store, testName);
			
			// Step~3  Select and open the media file
			Common.doubleClick(driver, hmcPage.Media_search_result);
			Dailylog.logInfoDB(3, "Select and open the media file", Store, testName);
			
			// Step~4  Click 'Preview' button in General Tab
			Common.sleep(3000);
			hmcPage.Media_preview.click();
			Common.switchToWindow(driver, 1);
			Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.Media_pre, 5));
			Dailylog.logInfoDB(4, "Click 'Preview' button in General Tab", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
