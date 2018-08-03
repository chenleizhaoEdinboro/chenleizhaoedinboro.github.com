package TestScript.B2C;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class NA28781Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public NA28781Test(String store) {
		this.Store = store;
		this.testName = "NA-28781";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28781(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//check meta of description on home page
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());	
			String pdpURL = driver.getCurrentUrl().replace("LIeCommerce:M0C0v0n3L!@", "");
			String tool = "https://search.google.com/structured-data/testing-tool";
			driver.get(tool);
			b2cPage.GEOTool_InputURL.clear();
			b2cPage.GEOTool_InputURL.sendKeys(pdpURL);		
			b2cPage.GEOTool_Runtest.click();
			b2cPage.GEOTool_Product.click();
			b2cPage.GEOTool_Product_Review.click();
			String windowHandle=driver.getWindowHandle();
			changeWindow(true,windowHandle);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.GEOTool_Product_Price, 3), "Price on GEO Tool");
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.GEOTool_Product_Avaliable, 3), "Avaliability on GEO Tool");
			changeWindow(false,windowHandle);
		
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void changeWindow(boolean flag,String currentHandle){
		Common.sleep(3000);
		Set<String> windowHandles = driver.getWindowHandles();
		if(flag){
			for(String hanedle:windowHandles){
				if(!hanedle.equals(currentHandle)){
					driver.switchTo().window(hanedle);
				}
			}
		}else{
			driver.close();
			driver.switchTo().window(currentHandle);
			
		}
	}
	
	
	

}
