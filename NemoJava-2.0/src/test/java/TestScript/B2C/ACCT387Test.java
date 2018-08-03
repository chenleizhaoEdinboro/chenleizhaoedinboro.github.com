package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;


public class ACCT387Test extends SuperTestClass {

	public B2CPage b2cPage;
	public String alcDomain;
	

	public ACCT387Test(String store, String alcDomain) {
		this.Store = store;
		this.alcDomain = alcDomain;
		this.testName = "ACCT-387";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true)
	public void ACCT350(ITestContext ctx) {

		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);
			
			//1, open the homepage 
			Dailylog.logInfoDB(1, "open the homepage url", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl());
			
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			//2, go to alc login page 
			Dailylog.logInfoDB(2, "go to alc login page", Store, testName);
			
			Common.javascriptClick(driver, driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]/li[@id='myAccount']//li[contains(@class,'guest_menu_header')]//div[@class='link_text']")));
			
			Thread.sleep(10000);
			
			String alcUrl = driver.getCurrentUrl().toString();
			
			Assert.assertTrue(alcUrl.contains(alcDomain) && alcUrl.contains(testData.B2C.getHomePageUrl().split("/")[3]));
			
			//3, login on the alc page 
			Dailylog.logInfoDB(3, "login on the alc page", Store, testName);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	

}
