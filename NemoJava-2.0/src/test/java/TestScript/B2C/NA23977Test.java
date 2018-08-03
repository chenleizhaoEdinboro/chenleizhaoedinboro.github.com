package TestScript.B2C;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23977Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	private String accessory;
	private String url;

	public NA23977Test(String store,String accessory,String url) {
		this.Store = store;
		this.testName = "NA-23977";
		this.accessory = accessory;
		this.url = url;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA23977(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			// step 1  load hmc
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(2000);
			hmcPage.hmcHome_WCMS.click();
			hmcPage.Wcms_component.click();
			hmcPage.Wcms_Name.clear();
			hmcPage.Wcms_Name.sendKeys(accessory);
			hmcPage.WCMS_Website_SearchButton.click();
			Dailylog.logInfo(" step 1  load hmc");
			
			//step 2 Choose catalog version 'weContentCatalog-Online' in results->Edit
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.Wcms_Name_searchResult);
			Dailylog.logInfo(" step 2  Choose catalog version 'weContentCatalog-Online' in results->Edit");
			
			//step 3 Localized URL',put 'www.bing.com'->Save
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.Wcms_Name_Maxmine);
			hmcPage.Wcms_Name_Maxmine.click();
			hmcPage.Wcms_Name_Localized.clear();
			hmcPage.Wcms_Name_Localized.sendKeys(url);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Dailylog.logInfo(" step 3  Localized URL',put 'www.bing.com'->Save");
			
			//step 4 Access storefront to validate the
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get("https://pre-c-hybris.lenovo.com/de/de");
			Common.scrollToElement(driver, b2cPage.About_Lenovo);
			Common.javascriptClick(driver, b2cPage.About_Lenovo);
			Common.javascriptClick(driver, b2cPage.About_Lenovo_Unser);
			Common.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().contains(url));
			Dailylog.logInfo(" step 4  Access storefront to validate the");
			
			//step 5  change the setting back to previous one
			Common.switchToWindow(driver, 0);
			Common.scrollToElement(driver, hmcPage.Wcms_Name_Localized);
			hmcPage.Wcms_Name_Localized.clear();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Dailylog.logInfo(" step 5  change the setting back to previous one");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}