package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class BROWSE18Test extends SuperTestClass {
	B2CPage b2cPage;
	

	public BROWSE18Test(String store){
		this.Store = store;
		this.testName = "BROWSE-18";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void BROWSE18(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());
			Dailylog.logInfoDB(1, "check title of product "+testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN(), Store, testName);
			//Common.scrollAndClick(driver, b2cPage.PDP_ViewCurrentModelTab);
			Common.javascriptClick(driver, b2cPage.PDP_ViewCurrentModelTab);
			//b2cPage.PDP_ViewCurrentModelTab.click();
			Common.sleep(5000);
			b2cPage.B2C_PDP_CustomizeButton.click();
			Common.sleep(50000);
			Dailylog.logInfoDB(2, "the title is"+driver.getTitle(), Store, testName);
			Assert.assertTrue(driver.getTitle().contains(" | "+testData.B2C.getDefaultCTOPN()+" | "+"Lenovo "+Store), "check the title");
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
	}
	

	
	
}
