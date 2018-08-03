package TestScript.B2C;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Pattern.Flag;

import org.apache.commons.codec.language.bm.Languages.SomeLanguages;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE61Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;

	public BROWSE61Test(String store,String SubSeries){
		this.Store = store;
		this.testName = "BROWSE-61";
		this.SubSeries = SubSeries;
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void BROWSE61(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//set hide feature yes on website and unit
			Dailylog.logInfoDB(1, "set the website yes and unit yes", Store, SubSeries);
			driver.get(testData.HMC.getHomePageUrl());
			setFeatureDifferentsOnWebsite(true);
			setFeatureDifferentsOnUnit(true);
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+SubSeries);
			Dailylog.logInfoDB(1, "check the color yes and button no", Store, SubSeries);
			CheckFeatureDifferentsColor(true);
			CheckFeatureDifferenceButton(false);
			
			//set hide feature yes on browse and no on unit
			Dailylog.logInfoDB(2, "set the website yes and unit no", Store, SubSeries);
			driver.get(testData.HMC.getHomePageUrl());
			setFeatureDifferentsOnWebsite(true);
			setFeatureDifferentsOnUnit(false);
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+SubSeries);
			Dailylog.logInfoDB(2, "check the color no and button yes", Store, SubSeries);
			driver.navigate().refresh();
			CheckFeatureDifferentsColor(false);
			CheckFeatureDifferenceButton(true);
			
			//set hide feature no on browse and yes on unit
			Dailylog.logInfoDB(3, "set the website no and unit yes", Store, SubSeries);
			driver.get(testData.HMC.getHomePageUrl());
			setFeatureDifferentsOnWebsite(false);
			setFeatureDifferentsOnUnit(true);
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+SubSeries);
			driver.navigate().refresh();
			Dailylog.logInfoDB(3, "check the color yes and button no", Store, SubSeries);
			CheckFeatureDifferentsColor(true);
			CheckFeatureDifferenceButton(false);
			
			//set hide feature no on browse and unit
			Dailylog.logInfoDB(4, "set the website no and unit no", Store, SubSeries);
			driver.get(testData.HMC.getHomePageUrl());
			setFeatureDifferentsOnWebsite(false);
			setFeatureDifferentsOnUnit(false);
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+SubSeries);
			driver.navigate().refresh();
			Dailylog.logInfoDB(4, "check the color no and button no", Store, SubSeries);
			CheckFeatureDifferentsColor(false);
			CheckFeatureDifferenceButton(false);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
		
	}
	public void setFeatureDifferentsOnUnit(boolean flag) {
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		if(flag) {
			hmcPage.B2CUnit_SiteAttribute_HideFeatureDiff_Yes.click();
		}else {
			hmcPage.B2CUnit_SiteAttribute_HideFeatureDiff_No.click();
		}
		hmcPage.SaveButton.click();
		hmcPage.Home_EndSessionLink.click();
	
	}
	
	public void  setFeatureDifferentsOnWebsite(boolean flag) {
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchWebsites(hmcPage, testData);
		hmcPage.BaseStore_SearchResult.click();
		hmcPage.WCMS_Website_Administration.click();
		if(flag) {
			hmcPage.Websites_Administor_HideFeatureDiff_Yes.click();
		}else {
			hmcPage.Websites_Administor_HideFeatureDiff_No.click();
		}
		hmcPage.SaveButton.click();
		hmcPage.Home_EndSessionLink.click();
	}
	
	public void CheckFeatureDifferentsColor(boolean flag) {
		Dailylog.logInfoDB(5,"color status is"+ (b2cPage.FeaturesDiffColor.size()>=1), Store, SubSeries);
		Assert.assertEquals(flag, b2cPage.FeaturesDiffColor.size()>=1 );
	}
	
	public void CheckFeatureDifferenceButton(boolean flag) {
		Dailylog.logInfoDB(6,"button status is"+ (Common.checkElementExists(driver, b2cPage.FeaturesDiffButton, 3)), Store, SubSeries);
		Assert.assertEquals(flag, Common.checkElementExists(driver, b2cPage.FeaturesDiffButton, 3));
	}
	

	
	
}
