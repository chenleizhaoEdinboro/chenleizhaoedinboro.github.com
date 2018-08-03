package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA17243Test extends SuperTestClass {

	public NA17243Test(String store) {
		this.Store = store;
		this.testName = "NA-17243";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA17243(ITestContext ctx) {
		try {
			this.prepareTest();			
			B2CPage b2cPage = new B2CPage(driver);			

			// Log in with new account
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, "gzcd@yopmail.com", "1q2w3e4r");
				
			if (!driver.getCurrentUrl().endsWith("profile"))
				Assert.fail("New user is not redirected to update-profile page after loging in!");
			b2cPage.Navigation_ProductsLink.click();
			
			//Log in with old account
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, "lisong2@lenovo.com", "1q2w3e4r");
			if (!driver.getCurrentUrl().endsWith("my-account"))
				Assert.fail("Old user is not redirected to my-account page after loging in!");
	
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
}
