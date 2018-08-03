package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class CONTENT481Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	String product ="thinkpad";

	public CONTENT481Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-481";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "uxui", "p2", "b2c"})
	public void CONTENT481(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//1.open b2c site
			driver.get(testData.B2C.getHomePageUrl());
			Common.sleep(3000);
			Dailylog.logInfoDB(1, "open b2c website", Store, testName);
			
			//2.search product
			b2cPage.inputSearchText.clear();
			b2cPage.inputSearchText.sendKeys(product);
			b2cPage.searchIcon.click();
			Common.sleep(3000);
			
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.searchanalytics, 10));
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.cardthumbnail, 10));
			Common.sleep(3000);
			Common.javascriptClick(driver, b2cPage.shopTab);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.cardthumbnail, 10));
			Common.javascriptClick(driver, b2cPage.discoverTab);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.searchanalytics, 10));
			Dailylog.logInfoDB(2, "product", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
