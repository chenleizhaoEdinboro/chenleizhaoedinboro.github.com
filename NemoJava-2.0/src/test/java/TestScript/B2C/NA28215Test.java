package TestScript.B2C;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern.Flag;

import org.apache.commons.codec.language.bm.Languages.SomeLanguages;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class NA28215Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String unit;
	String url;
	String account;
	String password;
	String message;
	String site;
	String languageXpath;

	public NA28215Test(String store,String unit,String url,String account,String password,String message) {
		this.Store = store;
		this.testName = "NA-28215";
		this.unit=unit;
		this.account = account;
		this.password = password;
		this.message = message;
		this.url = url;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28215(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String welcomeKey1="affinitysite.homepage.welcomemessge";
			String WelcomeKey2="affinitysite.homepage.graymessge";
			
			//get the unit name
			
			if(Store.equals("JP")) {
				languageXpath=".//*[@id='Content/AllInstancesSelectEditor[in Content/Attribute[.zLanguage]]_select']/option[contains(.,'Japanese') and not(contains(.,'/'))]";
				site = "JP Affinity Site";
			}else if(Store.equals("USEPP")) {
				languageXpath=".//*[@id='Content/AllInstancesSelectEditor[in Content/Attribute[.zLanguage]]_select']/option[contains(.,'English') and not(contains(.,'/'))]";
				site = "US Affinity Sites";
			}else {
				languageXpath=".//*[@id='Content/AllInstancesSelectEditor[in Content/Attribute[.zLanguage]]_select']/option[contains(.,'English') and not(contains(.,'/'))]";
				site = "Share Lenovo | Lenovo Australia";
			}
			
			String unitName;
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.sendKeys(unit);
			hmcPage.B2CUnit_SearchButton.click();
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			unitName = hmcPage.B2CUnit_name.getAttribute("value").replace("<br />", "");
			Dailylog.logInfoDB(1, unitName, Store, testName);
			hmcPage.Home_EndSessionLink.click();
			//maintenance the weclome message 
			maintenceWelcomeMessage(welcomeKey1,message,site);
			
			//maintenance the gray welcome message
			maintenceWelcomeMessage(WelcomeKey2,message+" again",site);
			refreshMessage();
			
			//login affinity site
			driver.get(testData.B2C.getHomePageUrl().replace(testData.B2C.getStore(), url));
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
				b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
				b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys(account);
				b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
				b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys(password);
				b2cPage.RegisterGateKeeper_LoginButton.click();
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.HomePage_CloseAdvButton, 5))
				b2cPage.HomePage_CloseAdvButton.click();
			
			//check the welcome message
			
			Dailylog.logInfoDB(1, b2cPage.Affinity_WelcomeMessage.getText(), Store, testName);
			Dailylog.logInfoDB(1, b2cPage.Affinity_GrayWelcomeMessage.getText(), Store, testName);
			Dailylog.logInfoDB(1, b2cPage.Affinity_UnitMessage.getText().replace("\n", "").replace("\r", ""), Store, testName);
			
			Assert.assertTrue( b2cPage.Affinity_WelcomeMessage.getText().contains(message));
			Assert.assertTrue(b2cPage.Affinity_GrayWelcomeMessage.getText().contains(message+" again"));
			Assert.assertTrue(b2cPage.Affinity_UnitMessage.getText().replace("<br />", "").replace("\n", "").replace("\r", "").equals(unitName));
			
			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
	}
	public void maintenceWelcomeMessage(String key,String value,String site) throws InterruptedException {
		HMCCommon.Login(hmcPage, testData);
		hmcPage.home_internationalization.click();
		hmcPage.home_messages.click();
		/*if(!Common.checkElementDisplays(driver, hmcPage.home_messages, 3)) {
			hmcPage.home_internationalization.click();
		}
		if(!Common.checkElementDisplays(driver, hmcPage.home_message, 3)) {
			hmcPage.home_messages.click();
		}*/
		hmcPage.home_message.click();
		if(!Common.checkElementDisplays(driver, hmcPage.message_searchPart, 3)) {
			hmcPage.message_searchPart.click();
		}
		hmcPage.message_searchKey.clear();
		hmcPage.message_searchKey.sendKeys(key);
		hmcPage.message_searchValue.clear();
		hmcPage.message_searchValue.sendKeys(value);
		hmcPage.B2BUnit_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 5)) {
			Common.rightClick(driver,hmcPage.B2BCustomer_1stSearchedResult);
			hmcPage.message_rightRemove.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
		Common.rightClick(driver, hmcPage.home_message);
		hmcPage.message_rightCreate.click();
		fillWelcomeInfo(key,value,site,languageXpath);
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_CreateButton, 3)) {
			hmcPage.B2BCustomer_CreateButton.click();
		}else {
			hmcPage.SaveButton.click();
		}	
		Common.scrollToElement(driver, hmcPage.Home_EndSessionLink);
		hmcPage.Home_EndSessionLink.click();
	}
	
	public void refreshMessage() {
		HMCCommon.Login(hmcPage, testData);
		hmcPage.home_internationalization.click();
		hmcPage.home_messages.click();
		hmcPage.home_messageRefresh.click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe")));
		hmcPage.messageRefresh_refreshAll.click();
		Common.waitElementText(driver, By.xpath(".//*[@id='selectionTip']/div/p"), "Message refreshed successfully");
		driver.switchTo().defaultContent();	
		hmcPage.Home_EndSessionLink.click();
	}
				
	
	public void fillWelcomeInfo(String key,String value,String site,String languageXpath) {
		hmcPage.message_Key.clear();
		hmcPage.message_Key.sendKeys(key);
		hmcPage.message_Value.clear();
		hmcPage.message_Value.sendKeys(value);
		hmcPage.message_channel.click();
		driver.findElement(By.xpath(languageXpath)).click();
		hmcPage.message_baseName.click();
		hmcPage.message_Site.clear();
		hmcPage.message_Site.sendKeys(site);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'Content/AutocompleteReferenceEditor[in Content/Attribute[.zSite]]_ajaxselect')][contains(.,'"+site+"')]")), 3);
		driver.findElement(By.xpath(".//*[contains(@id,'Content/AutocompleteReferenceEditor[in Content/Attribute[.zSite]]_ajaxselect')][contains(.,'"+site+"')]")).click();	
	}
}
