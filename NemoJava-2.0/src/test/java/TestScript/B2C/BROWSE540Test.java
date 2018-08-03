package TestScript.B2C;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class BROWSE540Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String model;
	String accessory1;
	String accessory2;
	String subseries;
	
	public BROWSE540Test (String store, String model, String accessory1, String accessory2, String subseries){
		this.Store = store;
		this.testName = "BROWSE-540";
		this.model = model;
		this.accessory1 = accessory1;
		this.accessory2 = accessory2;
		this.subseries = subseries;
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","product",  "p2", "b2c"})
	public void BROWSE540Test(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String homePageUrl = testData.B2C.getHomePageUrl();
			String subseriesUrl = "https://pre-c-hybris.lenovo.com/jp/ja/notebooks/thinkpad/x-series/c/thinkpad-x-series";

			
			//=========== Step:1 Maintain blank value via Attribute override ===========//
			Dailylog.logInfoDB(1, "Maintain a blank value via  Attribute override, check in the frontpage", Store, testName);
			attributeOverride(model,"");
			driver.get(homePageUrl+"/p/"+model);
			String blankAttributeOverride = b2cPage.productSummaryOnPDP.getText().toString();
			Assert.assertTrue(blankAttributeOverride.equals(""), "The summary attribute override use NULL vaule should be hidden");
			cleanBrowser();
			
			//=========== Step:2 Maintain KO value via Attribute override ===========//
			Dailylog.logInfoDB(2, "Maintain KO value via  Attribute override, check in the frontpage", Store, testName);
			attributeOverride(model,"test KO summary");
			driver.get(homePageUrl+"/p/"+model);
			String KOAttributeOverride = b2cPage.productSummaryOnPDP.getText().toString();
			Assert.assertTrue(KOAttributeOverride.equals(""), "The summary attribute override use KO vaule should be hidden");
			cleanBrowser();
			
			//=========== Step:3 Maintain EN value via Attribute override ===========//
			Dailylog.logInfoDB(3, "Maintain EN value via  Attribute override, check in the frontpage", Store, testName);
			attributeOverride(model,"test EN summary");
			driver.get(homePageUrl+"/p/"+model);
			String ENAttributeOverride = b2cPage.productSummaryOnPDP.getText().toString();
			Assert.assertTrue(ENAttributeOverride.equals("test EN summary"), "The summary attribute override use EN vaule should be changed");
			cleanBrowser();
			
			//=========== Step:4 Maintain blank value via Classification Attribute override ===========//
			Dailylog.logInfoDB(4, "Maintain blank value via  Classification Attribute override, check in the frontpage", Store, testName);
			classificationAttributeOverride(accessory1,"");	
			driver.get(homePageUrl+"/p/"+accessory1);
			Boolean blankClassificationAttributeOverride = !(Common.isElementExist(driver, By.xpath("//table[@class='techSpecs-table']//td[text()='高さ']"), 3));
			Assert.assertTrue(blankClassificationAttributeOverride, "The height classification attribute override use NULL value should be hidden");
			cleanBrowser();
			
			//=========== Step:5 Maintain KO value via Classification Attribute override ===========//
			Dailylog.logInfoDB(5, "Maintain KO value via  Classification Attribute override, check in the frontpage", Store, testName);
			classificationAttributeOverride(accessory1,"test KO height");	
			driver.get(homePageUrl+"/p/"+accessory1);
			Boolean koClassificationAttributeOverride = !(Common.isElementExist(driver, By.xpath("//table[@class='techSpecs-table']//td[text()='高さ']"), 3));
			Assert.assertTrue(koClassificationAttributeOverride, "The height classification attribute override use KO value should be hidden");
			cleanBrowser();
			
			//=========== Step:6 Maintain EN value via Classification Attribute override ===========//
			Dailylog.logInfoDB(6, "Maintain EN value via  Classification Attribute override, check in the frontpage", Store, testName);
			classificationAttributeOverride(accessory1,"test EN height");	
			driver.get(homePageUrl+"/p/"+accessory1);
			Boolean enClassificationAttributeOverride = Common.isElementExist(driver, By.xpath("//table[@class='techSpecs-table']//td[text()='test EN height']"), 3);
			Assert.assertTrue(enClassificationAttributeOverride, "The height classification attribute override use EN value should be changed");
			cleanBrowser();
			
			//=========== Step:7 Maintain balnk value via PMI Attribute Override ===========//
			Dailylog.logInfoDB(7, "Maintain blank value via  PMI Attribute override, check in the frontpage", Store, testName);
			pmiAttributeOverride(accessory1,"",true);	
			driver.get(homePageUrl+"/p/"+accessory1);
			String blankOverridePmi = b2cPage.accesssoryTitleSection.getText().toString();
			String blankTextPmi = b2cPage.accesssoryOverview.getText().toString();
			String blankMediaPmi = b2cPage.accesssoryImage.getAttribute("src");
			Assert.assertTrue(blankOverridePmi.equals(""), "The override mkt_name pmi attribute override use NULL vaule should be hidden");
			Assert.assertTrue(blankTextPmi.equals(""), "The text mkt_desc_long pmi attribute override use NULL vaule should be hidden");
			Assert.assertTrue(blankMediaPmi.equals(""), "The media mkt_imge_list pmi attribute override use NULL vaule should be hidden");
			cleanBrowser();
			
			//=========== Step:8 Maintain KO value via PMI Attribute Override ===========//
			Dailylog.logInfoDB(8, "Maintain KO value via  PMI Attribute override, check in the frontpage", Store, testName);
			pmiAttributeOverride(accessory1,"test KO",true);
			driver.get(homePageUrl+"/p/"+accessory1);
			String koOverridePmi = b2cPage.accesssoryTitleSection.getText().toString();
			String koTextPmi = b2cPage.accesssoryOverview.getText().toString();
			String koMediaPmi = b2cPage.accesssoryImage.getAttribute("src");
			Assert.assertTrue(koOverridePmi.equals(""), "The override mkt_name pmi attribute override use KO vaule should be hidden");			
			Assert.assertTrue(koTextPmi.equals(""), "The text mkt_desc_long pmi attribute override use KO vaule should be hidden");			
			Assert.assertTrue(koMediaPmi.equals(""), "The media mkt_imge_list pmi attribute override use KO vaule should be hidden");			
			cleanBrowser();
			
			//=========== Step:9 Maintain EN value via PMI Attribute Override ===========//
			Dailylog.logInfoDB(9, "Maintain EN value via  PMI Attribute override, check in the frontpage", Store, testName);
			String url = pmiAttributeOverride(accessory1,"test EN",true);
			driver.get(homePageUrl+"/p/"+accessory1);
			String enOverridePmi = b2cPage.accesssoryTitleSection.getText().toString();
			String enTextPmi = b2cPage.accesssoryOverview.getText().toString();
			String src = b2cPage.accesssoryImage.getAttribute("src");
			String[] enMediaPmi = src.split("https://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com");
			Assert.assertTrue(enOverridePmi.equals("test EN"), "The override mkt_name pmi attribute override use EN vaule should be changed");			
			Assert.assertTrue(enTextPmi.equals("test EN"), "The text mkt_desc_long pmi attribute override use EN vaule should be changed");			
			Assert.assertTrue(enMediaPmi[1].equals(url), "The media pmi attribute override use EN vaule should be hidden");			
			cleanBrowser();		
			
			//=========== Step:10 Maintain EN value via PMI Attribute Override ===========//
			Dailylog.logInfoDB(10, "Maintain value via  PMI Attribute override, check in the frontpage", Store, testName);
			pmiAttributeOverride(accessory2,"",false);
			driver.get(homePageUrl+"/p/"+accessory2);
			String productStatus = b2cPage.accesssoryStatus.getText().toString();
			Boolean showTechSpeces = Common.isElementExist(driver, By.xpath("//span[@class='js-navText tab_active']"), 3);
			Assert.assertTrue(productStatus.equals("Sold Out"), "The collection pmi attribute override should be changed");
			Assert.assertTrue(!showTechSpeces, "The boolean pmi attribute override should be changed");
			cleanBrowser();		
			
			//=========== Step:11 Check the dots on subseries PLP page ===========//
			Dailylog.logInfoDB(11, "Check the dots on subseries PLP page", Store, testName);
			subseriesPmiAttributeOverride(subseries,"");
			driver.get(subseriesUrl);
			String subseries = driver.findElement(By.xpath("//div[contains(@class,'seriesListings-description')]")).getText().toString();
			Assert.assertTrue(subseries.equals(""), "The subseries mkt_desc_short should be hidden on PLP page");			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	
	//=========== 1.maintain AttributeOverride Summary ===========//
	public void attributeOverride(String productNo,String summary) throws InterruptedException{	
		//login HMC console
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchOnlineProduct(driver, hmcPage, productNo);
		Common.sleep(3000);	
		hmcPage.product_AttributeOverrideTab.click();
		
		//=========== delete override result ===========//
		//delete group level result
		if(Common.checkElementExists(driver, hmcPage.AttributeOverride_groupResult, 1)){
			Common.rightClick(driver, hmcPage.AttributeOverride_groupResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_groupResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete channel level result
		if(Common.checkElementExists(driver, hmcPage.AttributeOverride_channelResult, 1)){
			Common.rightClick(driver, hmcPage.AttributeOverride_channelResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_channelResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete country level result
		if(Common.checkElementExists(driver, hmcPage.AttributeOverride_countryResult, 1)){
			Common.rightClick(driver, hmcPage.AttributeOverride_countryResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_countryResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete region level result
		if(Common.checkElementExists(driver, hmcPage.AttributeOverride_regionResult, 1)){
			Common.rightClick(driver, hmcPage.AttributeOverride_regionResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_regionResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		
		//=========== create country and region override ===========//
		//create country attribute override
		Common.rightClick(driver, hmcPage.AttributeOverride_countryOverride);
		hmcPage.AttributeOverride_rightCreate.click();		
		Common.switchToWindow(driver, 1);
		hmcPage.AttributeOverride_attribute_Summary.click();
		if(summary.equals("test KO summary")){
			hmcPage.stringValue_Expland.click();
			hmcPage.KO_StringValue.clear();
			hmcPage.KO_StringValue.sendKeys(summary);
			hmcPage.stringValue_Collapse.click();
		}else{
			hmcPage.EN_StringValue.clear();
			hmcPage.EN_StringValue.sendKeys(summary);
		}		
		hmcPage.select_JPCountry.click();
		hmcPage.Active.click();
		hmcPage.SaveButton.click();	
		driver.close();
		Common.switchToWindow(driver, 0);
		
		//create region attribute override
		Common.rightClick(driver, hmcPage.AttributeOverride_regionOverride);
		hmcPage.AttributeOverride_rightCreate.click();		
		Common.switchToWindow(driver, 1);
		hmcPage.AttributeOverride_attribute_Summary.click();		
		hmcPage.EN_StringValue.clear();
		hmcPage.EN_StringValue.sendKeys("test region summary");	
		hmcPage.select_GlobalRegion.click();
		hmcPage.Active.click();
		hmcPage.SaveButton.click();	
		driver.close();
		Common.switchToWindow(driver, 0);
		
		hmcPage.SaveButton.click();
		HMCCommon.cleanRadis(hmcPage, productNo);
	}
	
	
	//=========== 2.maintatin ClassificationAttributeOverride height ===========//
	public void classificationAttributeOverride(String productNo,String height) throws InterruptedException {
		//login HMC console
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchOnlineProduct(driver, hmcPage, productNo);	
		Common.sleep(3000);	
		hmcPage.ClassificationAttributeOverrideTab.click();
		
		//=========== delete override result ===========//
		//delete group level result
		if(Common.checkElementExists(driver, hmcPage.AttributeOverride_groupResult, 1)){
			Common.rightClick(driver, hmcPage.AttributeOverride_groupResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_groupResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete channel level result
		if(Common.checkElementExists(driver, hmcPage.Classification_channelResult, 1)){
			Common.rightClick(driver, hmcPage.Classification_channelResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.Classification_channelResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete country level result
		if(Common.checkElementExists(driver, hmcPage.Classfication_countryResult, 1)){
			Common.rightClick(driver, hmcPage.Classfication_countryResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.Classfication_countryResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete region level result
		if(Common.checkElementExists(driver, hmcPage.Classfication_regionResult, 1)){
			Common.rightClick(driver, hmcPage.Classfication_regionResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.Classfication_regionResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		
		//=========== create country and region override ===========//
		//create country classification attribute override
		Common.rightClick(driver, hmcPage.Classfication_countryOverride);
		hmcPage.Classfication_rightCreate.click();
		Common.switchToWindow(driver, 1);
		hmcPage.Classification_attribute_Height.click();
		if(height.equals("test KO height")){
			hmcPage.stringValue_Expland.click();
			Common.sleep(1000);
			hmcPage.KO_StringValue.clear();
			hmcPage.KO_StringValue.sendKeys(height);
			hmcPage.stringValue_Collapse.click();
		}else{
			hmcPage.EN_StringValue.clear();
			hmcPage.EN_StringValue.sendKeys(height);
		}		
		hmcPage.select_JPCountry.click();
		hmcPage.Active.click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		
		//create region classification attribute override
		Common.rightClick(driver, hmcPage.Classfication_regionOverride);
		hmcPage.Classfication_rightCreate.click();		
		Common.switchToWindow(driver, 1);
		hmcPage.Classification_attribute_Height.click();		
		hmcPage.EN_StringValue.clear();
		hmcPage.EN_StringValue.sendKeys("test region height");	
		hmcPage.select_GlobalRegion.click();
		hmcPage.Active.click();
		hmcPage.SaveButton.click();	
		driver.close();
		Common.switchToWindow(driver, 0);
		
		hmcPage.SaveButton.click();
		HMCCommon.cleanRadis(hmcPage, productNo);
	}
	
	
	//=========== 3.maintatin PMIAttributeOverride ===========//
	public String pmiAttributeOverride(String productNo,String pmi,Boolean pmiFlag) throws InterruptedException {
		String mediaUrl = null;
		//login HMC console
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchOnlineProduct(driver, hmcPage, productNo);
		Common.sleep(3000);	
		hmcPage.Catalog_PMIAttributeOverride.click();
		
		if(pmiFlag.equals(true)) {
			//=========== maintain PMIAttributeOverride of Overrides result ===========//			
			//=========== delete Overrides result ===========//			
			//delete group level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Overrides_groupResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Overrides_groupResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Overrides_groupResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete channel level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Overrides_channelResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Overrides_channelResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Overrides_channelResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete country level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Overrides_countryResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Overrides_countryResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Overrides_countryResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete region level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Overrides_regionResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Overrides_regionResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Overrides_regionResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			
			//=========== create PMIAttributeOverride of Overrides ===========//
			//create group level PMIAttributeOverride of Overrides
			Common.rightClick(driver, hmcPage.PMIOverride_GroupAttribute);
			hmcPage.PMIOverride_GroupAttribute_Create.click();
			Common.switchToWindow(driver, 1);
			hmcPage.PMIOverride_MktName_Attribute.click();
			if(pmi.equals("test KO")){
				hmcPage.stringValue_Expland.click();
				Common.sleep(1000);
				hmcPage.KO_StringValue.sendKeys(pmi);
				Common.sleep(1000);
				hmcPage.stringValue_Collapse.click();
				Common.sleep(1000);
			}else{
				hmcPage.EN_StringValue.sendKeys(pmi);
			}		
			hmcPage.select_JPGroup.sendKeys("jppublic_unit");
			Common.sleep(1000);
			hmcPage.select_JPGroup.sendKeys(Keys.ENTER);			
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			driver.close();
			Common.switchToWindow(driver, 0);
			
			//create region level PMIAttributeOverride of Overrides
			Common.rightClick(driver, hmcPage.PMIOverride_RegionAttribute);
			hmcPage.PMIOverride_GroupAttribute_Create.click();		
			Common.switchToWindow(driver, 1);
			hmcPage.PMIOverride_MktName_Attribute.click();		
			hmcPage.EN_StringValue.clear();
			hmcPage.EN_StringValue.sendKeys("test region MktName");	
			hmcPage.select_GlobalRegion.click();
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			driver.close();
			Common.switchToWindow(driver, 0);
			hmcPage.SaveButton.click();
			
			
			//=========== maintain PMIAttributeOverride of Text result ===========//
			//=========== delete Text result ===========//	
			//delete group level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Text_groupResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Text_groupResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Text_groupResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete channel level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Text_channelResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Text_channelResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Text_channelResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete country level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Text_countryResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Text_countryResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Text_countryResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}

			//=========== create PMIAttributeOverride of Text ===========//
			//create group level PMIAttributeOverride of Text
			Common.rightClick(driver, hmcPage.PMIOverride_GroupText);
			hmcPage.PMIOverride_GroupText_Create.click();		
			Common.switchToWindow(driver, 1);
			hmcPage.PMIOverride_DesLong_Attribute.click();
			if(pmi.equals("test KO")){
				hmcPage.stringValue_Expland.click();
				Common.sleep(1000);
				hmcPage.KO_PMITextValue.sendKeys(pmi);
				Common.sleep(1000);
				hmcPage.stringValue_Collapse.click();
				Common.sleep(1000);
			}else{
				hmcPage.EN_PMITextValue.sendKeys(pmi);
			}		
			hmcPage.select_JPGroup.sendKeys("jppublic_unit");
			Common.sleep(1000);
			hmcPage.select_JPGroup.sendKeys(Keys.ENTER);			
			hmcPage.Active.click();
			hmcPage.SaveButton.click();
			driver.close();
			Common.switchToWindow(driver, 0);
			
			//create country level PMIAttributeOverride of Text
			Common.rightClick(driver, hmcPage.PMIText_CountryAttribute);
			hmcPage.PMIOverride_GroupText_Create.click();		
			Common.switchToWindow(driver, 1);
			hmcPage.PMIOverride_DesLong_Attribute.click();	
			hmcPage.EN_PMITextValue.sendKeys("test country DesLong");	
			hmcPage.select_JPCountry.click();
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			Common.sleep(2000);
			driver.close();
			Common.switchToWindow(driver, 0);
			hmcPage.SaveButton.click();
			
			//delete mkt_desc_name value under 'Properties' tab
			if(pmi.equals("") || pmi.equals("test KO")) {
				Common.sleep(2000);
				hmcPage.product_PropertiesTab.click();
				hmcPage.description_Expland.click();
				driver.switchTo().frame(hmcPage.description_enFrame);
				hmcPage.description_value.clear();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(hmcPage.description_enGBFrame);
				hmcPage.description_value.clear();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(hmcPage.description_jaFrame);
				hmcPage.description_value.clear();
				driver.switchTo().defaultContent();
				driver.switchTo().frame(hmcPage.description_jaJPFrame);
				hmcPage.description_value.clear();
				driver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
				hmcPage.Catalog_PMIAttributeOverride.click();
			}
			
			//=========== maintain PMIAttributeOverride of Media result ===========//
			//=========== delete Media result ===========//	
			//delete group level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Media_groupResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Media_groupResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Media_groupResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete channel level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Media_channelResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Media_channelResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Media_channelResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			
			//=========== create PMIAttributeOverride of Media ===========//
			//create group level PMIAttributeOverride of Media
			Common.rightClick(driver, hmcPage.PMIOverride_GroupMedia);
			hmcPage.PMIOverride_GroupMedia_Create.click();		
			Common.switchToWindow(driver, 1);
			hmcPage.PMIOverride_Mktimgist_Attribute.click();
			if(pmi.equals("")) {
				
			}else if(pmi.equals("test KO")){
				hmcPage.stringValue_Expland.click();
				Common.sleep(1000);
				hmcPage.KO_PMIMediaValue.click();
				Common.switchToWindow(driver, 2);
				hmcPage.Contract_searchbutton.click();
				Common.sleep(5000);
				driver.findElement(By.xpath("//div[contains(@class,'gislcResultList')]//tr[12]")).click();
				hmcPage.PMIMediaUseButton.click();
				Common.switchToWindow(driver, 1);
				hmcPage.stringValue_Collapse.click();
				Common.sleep(1000);
			}else{
				hmcPage.EN_PMIMediaValue.click();
				Common.switchToWindow(driver, 2);
				hmcPage.Contract_searchbutton.click();
				driver.findElement(By.xpath("//div[contains(@class,'gislcResultList')]//tr[12]")).click();
				hmcPage.PMIMediaUseButton.click();
				Common.switchToWindow(driver, 1);
				mediaUrl = driver.findElement(By.xpath("//td[contains(text(),'URL')]/../td[last()]")).getText().toString();
				System.out.println(mediaUrl);
			}		
			hmcPage.select_JPGroup.sendKeys("jppublic_unit");
			Common.sleep(1000);
			hmcPage.select_JPGroup.sendKeys(Keys.ENTER);			
			hmcPage.Active.click();
			hmcPage.SaveButton.click();
			Common.sleep(2000);
			driver.close();
			Common.switchToWindow(driver, 0);
			
			//create channel level PMIAttributeOverride of Media
			Common.rightClick(driver, hmcPage.PMIOverride_ChannelMedia);
			hmcPage.PMIOverride_GroupMedia_Create.click();		
			Common.switchToWindow(driver, 1);
			Common.sleep(2000);
			hmcPage.PMIOverride_Mktimgist_Attribute.click();		
			hmcPage.EN_PMIMediaValue.click();
			Common.switchToWindow(driver, 2);
			hmcPage.Contract_searchbutton.click();
			driver.findElement(By.xpath("//div[contains(@class,'gislcResultList')]//tr[3]")).click();
			hmcPage.PMIMediaUseButton.click();
			Common.sleep(2000);
			Common.switchToWindow(driver, 1);		
			hmcPage.select_JPChannel.click();
			hmcPage.select_JPCountry.click();
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			driver.close();
			Common.switchToWindow(driver, 0);
			hmcPage.SaveButton.click();
			
			//delete mkt_img_list value under 'Multimedia' tab
			hmcPage.MultimediaTab.click();
			if(pmi.equals("") || pmi.equals("test KO")) {
				if(!(Common.isElementExist(driver, By.xpath("//div[contains(text(),'Image:')]/../../td[last()]//td[contains(text(),'n/a')]"), 3))) {
					Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'Image:')]/../../td[last()]")));
					hmcPage.Multimedia_clear.click();
				}
				if(!(Common.isElementExist(driver, By.xpath("//div[contains(text(),'Thumbnail:')]/../../td[last()]//td[contains(text(),'n/a')]"), 3))) {
					Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'Thumbnail:')]/../../td[last()]")));
					hmcPage.Multimedia_clear.click();
				}
				hmcPage.SaveButton.click();
			}
			
			HMCCommon.cleanRadis(hmcPage, productNo);
			Common.sleep(5000);
		}else {
			//=========== maintain PMIAttributeOverride of Collection result ===========//
			//=========== delete Collection result ===========//	
			//delete group level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Collection_groupResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Collection_groupResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Collection_groupResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete channel level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Collection_channelResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Collection_channelResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Collection_channelResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete country level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Collection_countryResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Collection_countryResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Collection_countryResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete region level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Collection_regionResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Collection_regionResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Collection_regionResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			
			//=========== create PMIAttributeOverride of Media ===========//
			//create channel level PMIAttributeOverride of Collection
			Common.rightClick(driver, hmcPage.PMICollection_channelAttribute);
			hmcPage.Region_collectionadd.click();		
			Common.switchToWindow(driver, 1);
			hmcPage.PMICollection_productStatus_Attribute.click();		
			hmcPage.PMICollection_productStatus_soldout.click();
			hmcPage.select_JPCountry.click();
			hmcPage.select_JPChannel.click();			
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			driver.close();
			Common.switchToWindow(driver, 0);
			
			//create region level PMIAttributeOverride of Collection
			Common.rightClick(driver, hmcPage.PMICollection_regionAttribute);
			hmcPage.Region_collectionadd.click();		
			Common.switchToWindow(driver, 1);
			hmcPage.PMICollection_productStatus_Attribute.click();	
			hmcPage.PMICollection_productStatus_available.click();
			hmcPage.select_GlobalRegion.click();
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			driver.close();
			Common.switchToWindow(driver, 0);
			hmcPage.SaveButton.click();
			
			
			//=========== maintain PMIAttributeOverride of Boolean result ===========//
			//=========== delete Boolean result ===========//	
			//delete group level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Boolean_groupResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Boolean_groupResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Boolean_groupResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete channel level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Boolean_channelResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Boolean_channelResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Boolean_channelResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			//delete country level result
			if(Common.checkElementExists(driver, hmcPage.PMI_Boolean_countryResult, 1)){
				Common.rightClick(driver, hmcPage.PMI_Boolean_countryResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver, hmcPage.PMI_Boolean_countryResult);
				hmcPage.hmcOverride_rightRemove.click(); 
				Common.waitAlertPresent(hmcPage.PageDriver, 3);
				hmcPage.PageDriver.switchTo().alert().accept();
				hmcPage.PageDriver.switchTo().defaultContent();
				hmcPage.SaveButton.click();
			}
			
			//=========== create PMIAttributeOverride of Media ===========//
			//create channel level PMIAttributeOverride of Collection
			Common.rightClick(driver, hmcPage.PMIBoolean_channelAttribute);
			hmcPage.Region_booleanadd.click();
			Common.switchToWindow(driver, 1);
			hmcPage.PMIBoolean_showTechSpecs_Attribute.click();
			hmcPage.PMICollection_showTechSpecs_No.click();			
			hmcPage.select_JPCountry.click();
			hmcPage.select_JPChannel.click();
			hmcPage.Active.click();
			hmcPage.SaveButton.click();	
			driver.close();
			Common.switchToWindow(driver, 0);
			
			//create country level PMIAttributeOverride of Collection
			Common.rightClick(driver, hmcPage.PMIBoolean_countryAttribute);
			hmcPage.Region_booleanadd.click();
			Common.switchToWindow(driver, 1);
			hmcPage.PMIBoolean_showTechSpecs_Attribute.click();
			hmcPage.PMICollection_showTechSpecs_Yes.click();		
			hmcPage.select_JPCountry.click();
			hmcPage.Active.click();
			hmcPage.SaveButton.click();
			Common.sleep(2000);
			driver.close();
			Common.switchToWindow(driver, 0);				
			
			hmcPage.SaveButton.click();			
			HMCCommon.cleanRadis(hmcPage, productNo);
			Common.sleep(5000);
		}
		return mediaUrl;
	}
	
	
	
	public void subseriesPmiAttributeOverride(String productNo,String pmi) throws InterruptedException {
		//login HMC console
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchOnlineProduct(driver, hmcPage, productNo);
		Common.sleep(3000);		
		hmcPage.Catalog_PMIAttributeOverride.click();
		
		//=========== delete Text result ===========//	
		//delete group level result
		if(Common.checkElementExists(driver, hmcPage.PMI_Text_groupResult, 1)){
			Common.rightClick(driver, hmcPage.PMI_Text_groupResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.PMI_Text_groupResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		//delete channel level result
		if(Common.checkElementExists(driver, hmcPage.PMI_Text_channelResult, 1)){
			Common.rightClick(driver, hmcPage.PMI_Text_channelResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.PMI_Text_channelResult);
			hmcPage.hmcOverride_rightRemove.click(); 
			Common.waitAlertPresent(hmcPage.PageDriver, 3);
			hmcPage.PageDriver.switchTo().alert().accept();
			hmcPage.PageDriver.switchTo().defaultContent();
			hmcPage.SaveButton.click();
		}
		
		//=========== create PMIAttributeOverride of Text ===========//
		//create group level PMIAttributeOverride of Text
		Common.rightClick(driver, hmcPage.PMIOverride_GroupText);
		Common.sleep(3000);
		hmcPage.PMIOverride_GroupText_Create.click();		
		Common.switchToWindow(driver, 1);
		Common.sleep(2000);
		hmcPage.PMIOverride_DesShort_Attribute.click();
		hmcPage.EN_PMITextValue.sendKeys(pmi);	
		hmcPage.select_JPGroup.sendKeys("jppublic_unit");
		Common.sleep(1000);
		hmcPage.select_JPGroup.sendKeys(Keys.ENTER);			
		hmcPage.Active.click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		
		//create channel level PMIAttributeOverride of Text
		Common.rightClick(driver, hmcPage.PMIText_ChannelAttribute);
		hmcPage.PMIOverride_GroupText_Create.click();		
		Common.switchToWindow(driver, 1);
		hmcPage.PMIOverride_DesLong_Attribute.click();	
		hmcPage.EN_PMITextValue.sendKeys("test country DesShort");	
		hmcPage.select_JPCountry.click();
		hmcPage.select_JPChannel.click();
		hmcPage.Active.click();
		hmcPage.SaveButton.click();	
		driver.close();
		Common.switchToWindow(driver, 0);
		
		hmcPage.SaveButton.click();
		HMCCommon.cleanRadis(hmcPage, productNo);
		Common.sleep(3000);
	}
	
	public void cleanBrowser(){
		driver.get("chrome://settings/clearBrowserData");
		Common.sleep(2000);		
		driver.findElement(By.cssSelector("* /deep/ #clearBrowsingDataConfirm")).click();	
		Common.sleep(5000);
	}
}