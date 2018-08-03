package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
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

public class NA26780Test extends SuperTestClass {
	private String MT;
	private String SubSeries;
	private HMCPage hmcPage; 
	private B2CPage b2cPage; 
	
	
	public NA26780Test(String store,String MT,String SubSeries) {
		this.Store = store;
		this.SubSeries = SubSeries;
		this.testName = "NA-26780";
		this.MT = MT;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","product",  "p2", "b2c"})
	public void NA26780(ITestContext ctx) {
		try {
			this.prepareTest();
			String MTPartNo = MT;
			String subSeriesNo =SubSeries ;
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			//Set subseries AVR and check
			setAVR(subSeriesNo,Store,"B2C","APPROVED","DIRECT","true");
			checkAVR(subSeriesNo,true);
			
			setAVR(subSeriesNo,Store,"B2C","APPROVED","DIRECT","false");
			checkAVR(subSeriesNo,false);
			
			setAVR(subSeriesNo,"AU","B2C","APPROVED","DIRECT","true");
			checkAVR(subSeriesNo,true);
			
			setAVR(subSeriesNo,Store,"B2C","APPROVED","INDIRECT","true");
			checkAVR(subSeriesNo,false);			
			
			setAVR(subSeriesNo,Store,"B2B","APPROVED","DIRECT","true");
			checkAVR(subSeriesNo,true);
									
			setAVR(subSeriesNo,Store,"B2C","UNAPPROVED","DIRECT","true");
			checkAVR(subSeriesNo,false);
					
			setAVR(subSeriesNo,Store,"B2C","APPROVED","DIRECT","");
			checkAVR(subSeriesNo,true);
			//Set MT AVR and check
			if(Store.equals("US")){
				setAVR(MTPartNo,Store,"B2C","APPROVED","DIRECT","true");
				checkAVR(MTPartNo,true);				
				
				setAVR(MTPartNo,Store,"B2C","APPROVED","DIRECT","false");
				checkAVR(MTPartNo,false);
				
				setAVR(MTPartNo,Store,"B2B","APPROVED","DIRECT","true");
				checkAVR(MTPartNo,true);
											
				setAVR(MTPartNo,Store,"B2C","APPROVED","INDIRECT","true");
				checkAVR(MTPartNo,false);
							
				setAVR(MTPartNo,"AU","B2C","APPROVED","DIRECT","true");
				checkAVR(MTPartNo,true);
				
				setAVR(MTPartNo,Store,"B2C","UNAPPROVED","DIRECT","true");
				checkAVR(MTPartNo,false);
								
				setAVR(MTPartNo,Store,"B2C","APPROVED","DIRECT","");
				checkAVR(MTPartNo,true);
							
			}
			
			
			//Check on website
			}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
	}
	
	
	public void setAVR(String partNum,String Country, String Channel,String ApproveStatus,String Sales,String data) throws InterruptedException{
		
		String defaultFromDate =  "01/01/2000";
		String defaultEndDate =  "01/01/9999";
		String countryXpath ="//*[@id='Content/AllInstancesSelectEditor[in Content/CreateItemListEntry[n/a]]_select']/option[contains(text(),'"+Country+"')]";
		String channelXpath="//*[@id='Content/AllInstancesSelectEditor[in Content/CreateItemListEntry[n/a]][1]_select']/option[contains(text(),'"+Channel+"')]";
		String approveXpath="//*[@id='Content/EnumerationValueSelectEditor[in Content/CreateItemListEntry[n/a]]_select']/option[contains(text(),'"+ApproveStatus+"')]";
		String salesXpath  ="//*[@id='Content/EnumerationValueSelectEditor[in Content/CreateItemListEntry[n/a]][1]_select']/option[contains(text(),'"+Sales+"')]";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partNum);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		hmcPage.products_resultItem.click();
		hmcPage.Catalog_multiCountry.click();
		if(Common.checkElementDisplays(driver, hmcPage.Product_AVR_ListEdit, 10)){
			Common.rightClick(driver, hmcPage.Product_AVR);
			hmcPage.Product_AVR_selectAll.click();		
			Common.rightClick(driver, hmcPage.Product_AVR);
			hmcPage.Product_AVR_remove.click();
			Common.waitAlertPresent(hmcPage.PageDriver, 30);
			hmcPage.PageDriver.switchTo().alert().accept();
		}
		
		Common.rightClick(driver, hmcPage.Product_AVR);
		hmcPage.Product_AVR_create.click();
		driver.findElement(By.xpath(countryXpath)).click();
		driver.findElement(By.xpath(channelXpath)).click();
		driver.findElement(By.xpath(approveXpath)).click();
		driver.findElement(By.xpath(salesXpath)).click();
		if(data.equals("true")){
			hmcPage.Product_AVR_fromDate.sendKeys(defaultFromDate);
			hmcPage.Product_AVR_endDate.sendKeys(defaultEndDate);
		} else if(data.equals("false")){
			hmcPage.Product_AVR_fromDate.sendKeys(defaultEndDate);
			hmcPage.Product_AVR_endDate.sendKeys(defaultEndDate);	
		}
		hmcPage.Common_SaveButton.click();
		HMCCommon.cleanRadis(hmcPage, partNum);
		
		hmcPage.Home_EndSessionLink.click();
	}
	public void checkAVR(String MTPartNo,boolean flag){
		Dailylog.logInfoDB(1,"Product on website status should be "+flag, Store, testName);
		if(flag){
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTPartNo);
			driver.manage().deleteAllCookies();
			Common.sleep(80000);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTPartNo);
		    if(!flag==Common.checkElementDisplays(driver, b2cPage.Product_viewModel, 20)){
		    	driver.manage().deleteAllCookies();
		    	Common.sleep(80000);
		    	driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTPartNo);
		    	driver.navigate().refresh();
		    }
			Dailylog.logInfoDB(2,"Product on website status should be "+Common.checkElementDisplays(driver, b2cPage.Product_viewModel, 20), Store, testName);
			//Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Product_viewModel, 20), "product page show");
		}else{
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTPartNo);
			driver.manage().deleteAllCookies();
			Common.sleep(80000);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+MTPartNo);
			if(!flag==Common.checkElementDisplays(driver, b2cPage.Product_viewModel, 20)){
		    	Common.sleep(80000);
		    	driver.navigate().refresh();
		    }
			Dailylog.logInfoDB(2,"Product on website status should be "+Common.checkElementDisplays(driver, b2cPage.Product_viewModel, 20), Store, testName);
			//Assert.assertFalse(Common.checkElementDisplays(driver, b2cPage.Product_viewModel, 20), "product page show");
			
		}
	}
	
}
