package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17830Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	

	public NA17830Test(String store) {
		this.Store = store;
		this.testName = "NA-17830";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA17830(ITestContext ctx) {
		try {
			this.prepareTest();

			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			ChangeLanguageToggle(false);
			hmcPage.Home_EndSessionLink.click();
			driver.get(testData.B2C.getHomePageUrl());
			checkLanguageToggle(false);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			ChangeLanguageToggle(true);
			hmcPage.Home_EndSessionLink.click();
			driver.get(testData.B2C.getHomePageUrl());
			driver.navigate().refresh();
			checkLanguageToggle(true);
			
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
	public void ChangeLanguageToggle(boolean flag){
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		if(flag){
			hmcPage.B2CUnit_SiteAttribute_ShowLanguageYes.click();
			Dailylog.logInfoDB(1, "Change language Toggle to Yes", Store, testName);
		}else{
			hmcPage.B2CUnit_SiteAttribute_ShowLanguageNo.click();
			Dailylog.logInfoDB(2, "Change language Toggle to No", Store, testName);
		}
		hmcPage.Common_SaveButton.click();
	}
	
	public void checkLanguageToggle(boolean flag){
		try{
			Assert.assertEquals(flag, Common.isElementExist(driver, By.xpath("//*[@id='lang-selector']/option[@value='en_HK']"),10));
		}catch(Exception e){
			driver.navigate().refresh();
			Assert.assertEquals(flag, Common.isElementExist(driver, By.xpath("//*[@id='lang-selector']/option[@value='en_HK']"),10));
			
		}
		if(flag){
			b2cPage.HKZF_ChangeLanguageToggle.click();
			Common.sleep(3000);
			Assert.assertEquals(flag, driver.getCurrentUrl().contains("hk/en"));
		}
	}

}
