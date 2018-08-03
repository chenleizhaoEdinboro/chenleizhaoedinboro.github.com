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

public class BROWSE23Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String MTM1;
	String MTM2;
	String CTO1;
	String CTO2;
	String MachineType;
	String SubSeries;
	String AccessoryNo;
	

	public BROWSE23Test(String store,String mtm1,String mtm2,String cto1,String cto2,String machineType,String subseries,String accessory){
		this.Store = store;
		this.testName = "BROWSE-23";
		this.MTM1 = mtm1;
		this.MTM2 = mtm2;
		this.CTO1 = cto1;
		this.CTO2 = cto2;
		this.MachineType = machineType;
		this.SubSeries = subseries;
		this.AccessoryNo = accessory;
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void BROWSE23(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String errorMessage = null;
			if(Store.equals("JP")){
				errorMessage = "大変申し訳ございません。お探しの商品は、このストアでは現在販売しておりません。下記、他の商品をお勧めいたします。";
			}else{
				errorMessage="The product you are looking for is no longer available, here is a suggested alternative.";
			}
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTM1);
			Assert.assertTrue(driver.getCurrentUrl().contains(MachineType), "check the redirect url");
			Assert.assertTrue(b2cPage.Product_EOLMessage.getText().contains(errorMessage), "check the eol message");
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+CTO1);
			Assert.assertTrue(driver.getCurrentUrl().contains(MachineType), "check the redirect url");
			Assert.assertTrue(b2cPage.Product_EOLMessage.getText().contains(errorMessage), "check the eol message");
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTM2);
			Assert.assertTrue(driver.getCurrentUrl().contains(SubSeries), "check the redirect url");
			Assert.assertTrue(b2cPage.Product_EOLMessage.getText().contains(errorMessage), "check the eol message");
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+CTO2);
			Assert.assertTrue(driver.getCurrentUrl().contains(SubSeries), "check the redirect url");
			Assert.assertTrue(b2cPage.Product_EOLMessage.getText().contains(errorMessage), "check the eol message");
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+AccessoryNo);
			Assert.assertTrue(driver.getCurrentUrl().contains("monitor"), "check the redirect url");
			Assert.assertTrue(b2cPage.Accessory_ResultList.size()>=1, "check the accessory category page");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(".//a[contains(@href,'"+AccessoryNo+"')]")), "check invalid accessory not exist on plp");
			Assert.assertTrue(b2cPage.Product_EOLMessage.getText().contains(errorMessage), "check the eol message");
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
	}
	

	
	
}
