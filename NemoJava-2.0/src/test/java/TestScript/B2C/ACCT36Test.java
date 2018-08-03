package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class ACCT36Test extends SuperTestClass {
	
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	private String alcUrl = "https://account.lenovo.com";
	
	private String alc_username = "LeACCT36@hotmail.com";
	private String alc_password = "GkAn-8571";
	
	public ACCT36Test(String store) {
		this.Store = store;
		this.testName = "ACCT-36";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true)
	public void ACCT34(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//1, Go to HMC and Set eService SSO Toggle and enable 
			Dailylog.logInfoDB(1, " Go to HMC and Set eService SSO Toggle and enable ", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			// set eService SSO Toggle : yes 
			hmcPage.eServiceToggle.click();
			hmcPage.socialLogin.click();

			hmcPage.Common_SaveButton.click();
			hmcPage.Home_closeSession.click();
			Thread.sleep(10000);
			
			
			//2, login alc with social id 
			Dailylog.logInfoDB(2, "login alc with social id ", Store, testName);
			
			driver.get(alcUrl);
			Thread.sleep(6000);
			
			loginALC(alc_username, alc_password);
			Thread.sleep(10000);
			
			driver.get(driver.getCurrentUrl()+ "myorders");
			
			Assert.assertTrue(driver.getCurrentUrl().endsWith("myorders"), "fail to login alc with social id ");
			
			
			//3, login hybris with social id 
			Dailylog.logInfoDB(3, "login hybris with social id ", Store, testName);
			
			driver.manage().deleteAllCookies();
			
			driver.get(testData.B2C.getloginPageUrl());
			
//			B2CCommon.login(b2cPage, alc_username, alc_password);
			
			driver.findElement(By.xpath("//ul[@class='socialMediaList']/li/a[@class='msn']")).click();
			
//			driver.findElement(By.xpath("//input[@name='loginfmt']")).clear();
//			driver.findElement(By.xpath("//input[@name='loginfmt']")).sendKeys(alc_username);
//			driver.findElement(By.xpath("//input[@type='submit']")).click();
//			
//			driver.findElement(By.xpath("//input[@name='passwd']")).clear();
//			driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys(alc_password);
//			
//			driver.findElement(By.xpath("//input[@type='submit']")).submit();
			
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
			Assert.assertTrue(!driver.getCurrentUrl().endsWith("login") && Common.isElementExist(driver, By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")), "hybris is not logined");

			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public void loginALC(String userName, String passwrod) throws Exception{
		
		driver.manage().deleteAllCookies();
		driver.findElement(By.xpath("//span[contains(.,'Live ID')]")).click();
		Thread.sleep(6000);
		
		driver.findElement(By.xpath("//input[@name='loginfmt']")).clear();
		driver.findElement(By.xpath("//input[@name='loginfmt']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		Thread.sleep(6000);
		
		driver.findElement(By.xpath("//input[@name='passwd']")).clear();
		driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys(passwrod);
		
		driver.findElement(By.xpath("//input[@type='submit']")).submit();
		
	}
	
	
}
