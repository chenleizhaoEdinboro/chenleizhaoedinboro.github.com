package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HACPage;
import TestScript.SuperTestClass;

public class NA18342Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HACPage hacPage;
	public B2BPage b2bPage;
	public NA18342Test(String store) {
		this.Store = store;
		this.testName = "NA-18342";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18342(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);

			// Step~1 : open the website
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(3000);
			Dailylog.logInfoDB(1, "load b2c: ", Store, testName);

			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Reviews_Title, 3), "check View ** at page");
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Score_Title, 3), "check View ** at page");
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Reviews, 3), "check View ** at page");
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Score, 3), "check View ** at page");
			
		/*	//Step~5 go to the Write Review part and then click Write Review button
			Common.scrollToElement(driver, driver.findElement(By.xpath("//button[@class='bv-write-review bv-focusable bv-submission-button']")));
			if(Common.checkElementExists(driver, b2cPage.Write_review, 5)&&Common.checkElementDisplays(driver, b2cPage.Write_review, 5)) {
				Common.sleep(3000);
				b2cPage.Write_review.click();
			}
			
			if(Common.checkElementExists(driver, b2cPage.PDP_review_jumplink, 5)) {
				Common.javascriptClick(driver,b2cPage.PDP_review_jumplink);
				
			}*/
			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());		
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}