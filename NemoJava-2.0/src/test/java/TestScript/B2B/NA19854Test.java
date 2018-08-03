package TestScript.B2B;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;


public class NA19854Test extends SuperTestClass {
	public B2BPage b2bPage;
	private String us ;
	private String au ;
	private String abb;
	
	public NA19854Test(String store) {
		this.Store = store;
		this.testName = "NA-19854";
	}


	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2b"})
	public void NA19854(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			us = testData.B2B.getLoginUrl().replace("/le/adobe_global/au/en/1213654197/login", "/le/1213348423/us/en/1213577815/login");
			au = testData.B2B.getLoginUrl().replace("/le/adobe_global/au/en/1213654197/login", "/le/adobe_global/au/en/1213654197/login");
			abb = testData.B2B.getLoginUrl().replace("/le/adobe_global/au/en/1213654197/login", "/le/abbwiproglobal/us/en/login");
			
			// Step 1 Login us B2B
			driver.get(us);
			B2BCommon.Login(b2bPage, "usbuyer@yopmail.com", testData.B2B.getDefaultPassword());
			Common.sleep(3000);
			if(driver.getCurrentUrl().contains("us")) {
				String text = b2bPage.Country.getText();
				Assert.assertEquals(text,"United States");
				Dailylog.logInfoDB(1, "Successfully logged into B2B site", Store, testName);
			}
			Common.sleep(3000);
			b2bPage.Logout.click();
			
			// Step 2 Login au B2B
			Common.sleep(3000);
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(au);
			B2BCommon.Login(b2bPage, "aubuyer@yopmail.com", testData.B2B.getDefaultPassword());
			Common.sleep(3000);
			if(driver.getCurrentUrl().contains("au") ){
				String text = b2bPage.Country.getText();
				Assert.assertEquals(text,"Australia");
				Dailylog.logInfoDB(1, "Successfully logged into B2B site", Store, testName);
			}
			Common.sleep(3000);
			b2bPage.Logout.click();
	
			// Step 3 Login abb B2B
			Common.sleep(3000);
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 2);
			driver.get(abb);
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			Common.sleep(3000);
			if(driver.getCurrentUrl().contains("abbwiproglobal") ){
				Dailylog.logInfoDB(3, "Successfully logged into B2B site", Store, testName);
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
