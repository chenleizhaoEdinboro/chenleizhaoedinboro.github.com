package TestScript.B2B;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA25657Test extends SuperTestClass{
	private static HMCPage hmcPage;
	String accesLevel1="le_global";
	String accesLevel2="alcoacorp";
	String accesLevel3="abc";
	
	
	public NA25657Test(String store) {
		this.Store = store;
		this.testName = "NA-25657";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA25657(ITestContext ctx) {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC successfully.", Store,testName);

	
			hmcPage.Home_Nemo.click();
			hmcPage.Home_Nemo_Punchout.click();
			
			
			driver.findElement(By.id("Tree/StaticContentLeaf[1]_label")).click();
			
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'nemohmc/nemoPunchoutAccessLevelPage')]")));

			checkAccesLevel(accesLevel1,"'le_global' can't be found");

			checkAccesLevel(accesLevel2,"alcoacorp");
			
			checkAccesLevel(accesLevel3,"'abc' can't be found");
			
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	public void checkAccesLevel(String accesLevel,String expectText){
		
		driver.findElement(By.id("soldTo")).clear();
		driver.findElement(By.id("soldTo")).sendKeys(accesLevel);
		driver.findElement(By.id("soldToBtn")).click();
		
		String checkText="";
		if(Common.checkElementExists(driver, driver.findElement(By.id("accesslevel")), 10)){		
			checkText=driver.findElement(By.id("accesslevel")).getText();
		}
		Dailylog.logInfo(checkText);
		Assert.assertEquals(expectText, checkText);
	}
	
}
