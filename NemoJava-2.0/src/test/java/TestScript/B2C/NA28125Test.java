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

public class NA28125Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String countryXpath;
	String message;

	public NA28125Test(String store) {
		this.Store = store;
		this.testName = "NA-28125";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","product",  "p2", "b2c"})
	public void NA28125(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			countryXpath="//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.country]')]/option[contains(text(),'"+Store+"')]";
			message = "automation test override message";
			
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchOnlineProduct(driver,hmcPage,testData.B2C.getDefaultCTOPN());
			setClassficationOverride(countryXpath,message);
			setAttributeOverride(countryXpath,message);
			hmcPage.SaveButton.click();
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultCTOPN());
			Assert.assertTrue(message.equals(b2cPage.PDPPage_ProcessClassficationMessage.getText()), "check the classfication override message");
			Assert.assertTrue(message.equals(b2cPage.PDPPage_SummaryOverrideMessage.getText()), "check the attribute override message");
			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
	}
	

	
	public void setClassficationOverride(String countryXpath,String message){
		hmcPage.Products_ClassficationTab.click();
		if(Common.checkElementExists(driver, hmcPage.Classfication_countryResult, 3)){
			Common.rightClick(driver, hmcPage.Classfication_countryOverride);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.Classfication_countryOverride);
			hmcPage.hmcOverride_rightRemove.click();		
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SaveButton.click();
		}
		Common.rightClick(driver, hmcPage.Classfication_countryOverride);
		hmcPage.Classfication_rightCreate.click();
		String windowHandle = driver.getWindowHandle();
		changeWindow(true,windowHandle);
		hmcPage.Classfication_attribute.click();
		hmcPage.hmcOverrid_Value.clear();
		hmcPage.hmcOverrid_Value.sendKeys(message);
		hmcPage.hmcOverride_Active.click();
		driver.findElement(By.xpath(countryXpath)).click();
		hmcPage.SaveButton.click();
		changeWindow(false,windowHandle);
	}
	
	public void setAttributeOverride(String countryXpath,String message){
		hmcPage.Products_AttributeOverrideTab.click();
		if(Common.checkElementExists(driver, hmcPage.AttributeOverride_countryResult, 3)){
			Common.rightClick(driver, hmcPage.AttributeOverride_countryResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_countryResult);
			hmcPage.hmcOverride_rightRemove.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SaveButton.click();
		}
		Common.rightClick(driver, hmcPage.AttributeOverride_countryOverride);
		hmcPage.AttributeOverride_rightCreate.click();
		String windowHandle = driver.getWindowHandle();
		changeWindow(true,windowHandle);
		hmcPage.AttributeOverride_attribute.click();
		hmcPage.hmcOverrid_Value.clear();
		hmcPage.hmcOverrid_Value.sendKeys(message);
		hmcPage.hmcOverride_Active.click();
		driver.findElement(By.xpath(countryXpath)).click();
		hmcPage.SaveButton.click();
		changeWindow(false,windowHandle);
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
