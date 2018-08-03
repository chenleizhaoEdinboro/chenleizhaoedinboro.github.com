package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class NA25040Test extends SuperTestClass {
	private B2CPage b2cPage; 
	
	
	public NA25040Test(String store) {
		this.Store = store;
		this.testName = "NA-25040";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","product",  "p1", "b2c"})
	public void NA25040(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			//Set subseries AVR and check
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			NavigationBar.goToSplitterPageUnderProducts(b2cPage,SplitterPage.Accessories );
			b2cPage.AccessoriesPage_FindForMySystem.click();
			b2cPage.AccessoriesPage_SelectProduct.click();
			b2cPage.AccessoriesPage_FirstProduct.click();

			b2cPage.AccessoriesPage_SelectMT.click();
			b2cPage.AccessoriesPage_FirstMT.click();
			Assert.assertTrue(b2cPage.AccessoriesPage_AccessoryFilterList.size()>0, "No result on filter sresult page");
			
			}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
	}
	
	
	
	
}
