package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23895Test extends SuperTestClass {

	public B2CPage b2cPage;
	private HMCPage hmcPage;
	private String name;
	private String description;
	private String unit;
	private String defaultMTMPN;
	
	public NA23895Test(String store,String name,String description,String unit) {
		this.Store = store;
		this.testName = "NA-23895";
		this.name = name;
		this.description = description;
		this.unit = unit;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA23895(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			defaultMTMPN = testData.B2C.getDefaultMTMPN();
			String homePageUrl = testData.B2C.getHomePageUrl();

			// Step 1 login hmc
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.clear();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(defaultMTMPN);
			hmcPage.IndexerHotUpdate_multiCountryOption.click();
			hmcPage.CronJob_searchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(3000);
			hmcPage.Catalog_PMIAttributeOverride.click();
			Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);
			
			//step 2  create Region PMI Attribute Override
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.RegionPmi);
			if(Common.checkElementExists(driver, hmcPage.RegionPmi_name, 5)) {
				Common.doubleClick(driver, hmcPage.RegionPmi_name);
				Common.switchToWindow_Title(driver, "Editor: PMI Attribute Override - hybris Management Console (hMC)");
				hmcPage.Region_editstringvalue.clear();
				hmcPage.Region_editstringvalue.sendKeys(name);
				hmcPage.PaymentLeasing_saveAndCreate.click();
				
			}else {
				Common.rightClick(driver, hmcPage.RegionPmi_none);
				hmcPage.RegionPmi_create.click();
				Common.switchToWindow_Title(driver, "Editor: PMI Attribute Override - hybris Management Console (hMC)");
				hmcPage.RegionPmi_create_att.click();
				hmcPage.RegionPmi_stringvalue.clear();
				hmcPage.RegionPmi_stringvalue.sendKeys(name);
				hmcPage.RegionPmi_region.click();
				hmcPage.RegionPmi_active.click();
				hmcPage.PaymentLeasing_saveAndCreate.click();
			}
			driver.close();
			Common.switchToWindow(driver, 0);
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(homePageUrl+"/p/"+defaultMTMPN);
			Common.sleep(3000);
			Assert.assertTrue(b2cPage.Product_serriesname.getText().contains(name));
			Dailylog.logInfoDB(2, "Region PMI Attribute Override", Store, testName);
			
			//step 3 Open the attribute ‘mkt_name’
			Common.sleep(3000);
			Common.switchToWindow(driver, 0);
			Common.scrollToElement(driver, hmcPage.RegionPmi);
			Common.doubleClick(driver, hmcPage.RegionPmi_name);
			Common.switchToWindow_Title(driver, "Editor: PMI Attribute Override - hybris Management Console (hMC)");
			Common.sleep(3000);
			hmcPage.RegionPmi_date.clear();
			hmcPage.RegionPmi_date.sendKeys(getDate(1));
			hmcPage.RegionPmi_endDate.clear();
			hmcPage.RegionPmi_endDate.sendKeys(getDate(2));
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			driver.navigate().refresh();
			Assert.assertTrue(b2cPage.Product_serriesname.getText().contains(b2cPage.Product_sertitle.getText()));
			Dailylog.logInfoDB(3, "Open the attribute ‘mkt_name’", Store, testName);
			
			//step 4   create a new attribute ‘mkt_name’ for Country PMI Attribute Override 
			Common.sleep(3000);
			Common.switchToWindow(driver, 0);
			Common.scrollToElement(driver, hmcPage.RegionPmi_name);
			Common.rightClick(driver, hmcPage.RegionPmi_name);
			hmcPage.RegionPmi_remove.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Common.sleep(2000);
			Common.rightClick(driver, hmcPage.CountryPmi);
			hmcPage.CountryPmi_add.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Attribute Override - hybris Management Console (hMC)");
			hmcPage.RegionPmi_create_att.click();
			hmcPage.RegionPmi_stringvalue.sendKeys(name);
			hmcPage.RegionPmi_region.click();
			hmcPage.RegionPmi_active.click();
			hmcPage.CountryPmi_country.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Common.sleep(3000);
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			driver.navigate().refresh();
			Assert.assertTrue(b2cPage.Product_serriesname.getText().contains(name));
			Dailylog.logInfoDB(4, "create a new attribute ‘mkt_name’ for Country PMI Attribute Override", Store, testName);
			
			// step 5  if there’s ‘mkt_desc_long’ attribute under Channel PMI Text Attribute Override
			Common.sleep(2000);
			Common.switchToWindow(driver, 0);
			Common.scrollToElement(driver, hmcPage.Channel_text);
			Common.rightClick(driver, hmcPage.Channel_text);
			hmcPage.Channel_textadd.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Channel_textattr.click();
			hmcPage.Channel_textacountry.click();
			hmcPage.Channel_textchannel.click();
			hmcPage.Channel_textactive.click();
			driver.switchTo().frame("MC712x50514_setvalue_ifr");
			hmcPage.Channel_textbody.sendKeys(description);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			driver.navigate().refresh();
			Assert.assertTrue(b2cPage.Product_serdescription.getText().contains(description));
			Dailylog.logInfoDB(5, "if there’s ‘mkt_desc_long’ attribute under Channel PMI Text Attribute Override", Store, testName);
			
			//step 6  Open the attribute ‘mkt_desc_long’ and set the Start Date & End Date,
			Common.sleep(3000);
			Common.switchToWindow(driver, 0);
			Common.scrollToElement(driver, hmcPage.Channel_textdesc);
			Common.doubleClick(driver, hmcPage.Channel_textdesc);
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Channel_textdescstartdate.click();
			hmcPage.Channel_textdescenddate.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(6, "Open the attribute ‘mkt_desc_long’ and set the Start Date & End Date,", Store, testName);
			
			//step 7  Remove the ‘mkt_desc_long’ attribute under Region PMI Attribute Override
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.Channel_textdescremove);
			Common.rightClick(driver, hmcPage.Channel_textdescremove);
			hmcPage.Channel_textdescremove.click();
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			Common.scrollToElement(driver, hmcPage.Group_add);
			Common.rightClick(driver, hmcPage.Group_add);
			hmcPage.Group_addtext.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Channel_textattr.click();
			hmcPage.Channel_textacountry.click();
			hmcPage.Channel_textchannel.click();
			hmcPage.Channel_textactive.click();
			driver.switchTo().frame("MC712x50514_setvalue_ifr");
			hmcPage.Channel_textbody.sendKeys(description);
			hmcPage.Group_addgroup.sendKeys(unit);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(7, "Remove the ‘mkt_desc_long’ attribute under Region PMI Attribute Override,", Store, testName);
			
			//step 8 create a new one(Use Test data on the right).Go to the frontpage and validate the product long description
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.Region_collection);
			Common.rightClick(driver, hmcPage.Region_collection);
			hmcPage.Region_collectionadd.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_collectionattr.click();
			hmcPage.RegionPmi_region.click();
			hmcPage.RegionPmi_active.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(8, "create a new one(Use Test data on the right)", Store, testName);
			
			//step 9  Open the attribute ‘merchandisingTag’ and set the Start Date & End Date,
			Common.scrollToElement(driver, hmcPage.Region_collectiontag);
			Common.doubleClick(driver, hmcPage.Region_collectiontag);
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_collectiontagstartdate.click();
			hmcPage.Region_collectiontagenddate.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(9, "Open the attribute ‘merchandisingTag’ and set the Start Date & End Date,", Store, testName);
			
			//step 10 Remove the ‘merchandisingTag’ attribute under Region PMI Attribute Override,
			Common.scrollToElement(driver, hmcPage.Region_collectiontag);
			Common.rightClick(driver, hmcPage.Region_collectiontag);
			hmcPage.Region_collectionremove.click();
			Alert alert2 = driver.switchTo().alert();
			alert2.accept();
			Common.scrollToElement(driver, hmcPage.Group_collectionremove);
			Common.rightClick(driver, hmcPage.Group_collectionremove);
			hmcPage.Region_collectionadd.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_collectionattr.click();
			hmcPage.RegionPmi_region.click();
			hmcPage.RegionPmi_active.click();
			hmcPage.Group_collectionadd.sendKeys(unit);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(10, "Remove the ‘merchandisingTag’ attribute under Region PMI Attribute Override,", Store, testName);
			
			//step 11 Test Media type PMI Attributes：Check if there’s ‘processor_logo’ attribute under Country PMI Media Attribute Override
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.Country_mediaadd);
			Common.rightClick(driver, hmcPage.Country_mediaadd);
			hmcPage.Region_collectionadd.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Country_mediaattr.click();
			hmcPage.RegionPmi_active.click();
			hmcPage.CountryPmi_country.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(11, "Test Media type PMI Attributes：", Store, testName);
			
			//step 12  Open the attribute ‘processor_logo’ and set the Start Date & End Date,
			Common.scrollToElement(driver, hmcPage.Country_mediaadd);
			Common.doubleClick(driver, hmcPage.Country_mediaaprocess);
			Common.switchToWindow_Title(driver, "Editor: PMI Text Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_collectiontagstartdate.click();
			hmcPage.Region_collectiontagenddate.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(12, "Test Media type PMI Attributes：", Store, testName);
			
			//step 13 Remove the ‘processor_logo’ 
			Common.scrollToElement(driver, hmcPage.Country_mediaaprocess);
			Common.rightClick(driver, hmcPage.Country_mediaaprocess);
			hmcPage.Region_collectionremove.click();
			alert2.accept();
			Dailylog.logInfoDB(13, "Remove the ‘processor_logo’ ", Store, testName);
			
			//step 14 Region PMI Boolean Attribute Override, add ‘showFeatures’ 
			Common.scrollToElement(driver, hmcPage.Region_boolean);
			Common.rightClick(driver, hmcPage.Region_boolean);
			hmcPage.Region_booleanadd.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Boolea Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_booleanaddatt.click();
			hmcPage.RegionPmi_active.click();
			hmcPage.CountryPmi_country.click();
			hmcPage.Region_booleanvalue.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			driver.close();
			Dailylog.logInfoDB(14, "Region PMI Boolean Attribute Override, add ‘showFeatures’ ", Store, testName);
			
			//step 15 set the Start Date & End Date
			Common.scrollToElement(driver, hmcPage.Region_boolean);
			Common.doubleClick(driver, hmcPage.Region_boolean);
			Common.switchToWindow_Title(driver, "Editor: PMI Boolea Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_collectiontagstartdate.click();
			hmcPage.Region_collectiontagenddate.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			
			Dailylog.logInfoDB(15, "set the Start Date & End Date", Store, testName);
			
			//step 16 Create a new attribute ‘processor_logo’ for Coutry PM
			Common.scrollToElement(driver, hmcPage.Region_boolean);
			Common.rightClick(driver, hmcPage.Region_boolean);
			hmcPage.Region_booleanadd.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Boolea Attribute Override - hybris Management Console (hMC)");
			hmcPage.Region_booleanaddatt.click();
			hmcPage.RegionPmi_active.click();
			hmcPage.CountryPmi_country.click();
			hmcPage.Region_booleanvalue.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Dailylog.logInfoDB(16, "Create a new attribute ‘processor_logo’ for Coutry PM", Store, testName);
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public String getDate(int number) {
		Calendar calendar = Calendar.getInstance();  
	    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + number);  
	    Date today = calendar.getTime();  
	    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");  
	    String result = format.format(today);  
	    return result;  
	}
	
	
	@Test(priority=1,alwaysRun = true)
	public void rollBack(ITestContext ctx){
		try{
			SetupBrowser();
			driver.manage().deleteAllCookies();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.clear();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(defaultMTMPN);
			hmcPage.IndexerHotUpdate_multiCountryOption.click();
			hmcPage.CronJob_searchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(3000);
			hmcPage.Catalog_PMIAttributeOverride.click();
			
			if(Common.checkElementExists(driver, hmcPage.RegionPmi_name, 5)) {
				Common.rightClick(driver, hmcPage.RegionPmi_name);
				hmcPage.RegionPmi_remove.click();
				Alert alert = driver.switchTo().alert();
				alert.accept();
				hmcPage.PaymentLeasing_saveAndCreate.click();
			}
			
		}
		catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

}
