package TestScript.B2C;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA25385Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String today = Common.getDateTimeString();
	
	public NA25385Test(String store) {
		
		this.Store = store;
		this.testName = "NA-25385";

	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c"})
	public void NA25385(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			
			Dailylog.logInfoDB(1,"Enter into HMC, set fine print content.", Store,testName);		
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.hmcHome_WCMS.click();
			hmcPage.wcmsPage_pages.click();
			hmcPage.wcmsPagesPage_pageIDTextBox.sendKeys("WebformPage");
			
			Select catalogVersion = new Select(hmcPage.wcmsPagesPage_catalogVersionDD);
			catalogVersion.selectByVisibleText("masterContentCatalog - Online");
			hmcPage.wcmsPagesPage_searchButton.click();
			Common.rightClick(driver, hmcPage.wcmsPagesPage_uniqueSearchResult);
			hmcPage.wcmsPagesPage_popupEdit.click();
			hmcPage.wcmsPagesPage_contentTab.click();
			Common.rightClick(driver, hmcPage.wcmsPagesPage_contentTab_finePrintCon);
			hmcPage.wcmsPagesPage_contentTab_finePrintCon_EditNew.click();
			Common.switchToWindow(driver, 1);
			Common.rightClick(driver, hmcPage.wcmsPagesPage_contentTab_finePrintCon_Editer_Contentslot);
			hmcPage.wcmsPagesPage_contentTab_finePrintCon_Editer_EditNew.click();
			//To new edit window
			Common.switchToWindow(driver, 2);
			//driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='MC40x2059_setvalue_ifr']")));//"MC40x2059_setvalue_ifr");
			driver.switchTo().frame(0);
			//update fine print message
			String finePrintMessage = hmcPage.wcmsPagesPage_contentTab_finePrintCon_tinyMce.getText();
			hmcPage.wcmsPagesPage_contentTab_finePrintCon_tinyMce.clear();
			hmcPage.wcmsPagesPage_contentTab_finePrintCon_tinyMce.sendKeys(today + finePrintMessage);
			Common.switchToWindow(driver, 1);
			Common.switchToWindow(driver, 2);
			hmcPage.SaveButton.click();
			
			
			Thread.sleep(10000);
			//close edit windows
			driver.close();
			Common.switchToWindow(driver, 1);
			driver.close();
			Common.switchToWindow(driver, 0);
			//check altered message
			driver.get("http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/us/en/startWebForms?WebFormId=LenovoFinancialServices#");
			String finePrintonWebsite = driver.findElement(By.xpath(".//*[@id='LenovoFinancialServices']/div/form")).getText();
			Assert.assertTrue(finePrintonWebsite.contains(today), "Fine Print doesn't contain expected string"); 
				
			
			
			}catch (Throwable e) {
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