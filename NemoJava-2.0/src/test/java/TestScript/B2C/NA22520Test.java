package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22520Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public NA22520Test(String store) {
		this.Store = store;
		this.testName = "NA-22520";
	}

	@Test(alwaysRun = true)
	public void NA22520(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			
			// step 1 login hmc
			driver.get(testData.B2C.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Login hmc", Store, testName);

			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	

}
