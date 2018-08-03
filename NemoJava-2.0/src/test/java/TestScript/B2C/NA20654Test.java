package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA20654Test extends SuperTestClass{

	HMCPage hmcPage;
	B2CPage b2cPage;
	String subseriesNum;
	String customizedButtonLabel = "FIND IN RETAILER STORE";
	Boolean whetherDeletePMI = false;
	
	public NA20654Test(String store){
		this.Store = store;
		this.testName = "NA-20654";	
	}
	
	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA20654(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			subseriesNum = testData.B2C.getDefaultMTMPN();
			
			//step 1 login hmc
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			
			//step 2 Maintain button color and label: B2C commerce-> usweb
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_BrandleapToggleYes.click();
			hmcPage.B2CUnit_CustomizedButtonColor.clear();
			hmcPage.B2CUnit_CustomizedButtonColor.sendKeys("0033FF");
			hmcPage.B2CUnit_CustomizedButtonLabel.clear();
			hmcPage.B2CUnit_CustomizedButtonLabel.sendKeys(customizedButtonLabel);
			hmcPage.Common_SaveButton.click();
			Dailylog.logInfoDB(2,"Maintain button color and label.", Store,testName);
			
			//step 3 create new PMI override.
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(subseriesNum);
			hmcPage.Catalog_SearchButton.click();
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			hmcPage.Catalog_PMIAttributeOverride.click();
			Common.sleep(3000);
			deleteNewPMIOverride();
			addNewPMIOverride();
			whetherDeletePMI = true;
			Dailylog.logInfoDB(3,"Goto PMI override and create new PMI override.", Store,testName);
			
			//step 4  Validate in website PDP page
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(driver.getCurrentUrl()+"/p/"+subseriesNum);
			Dailylog.logInfoDB(4,"Validate in website PLP page.", Store,testName);
			
			//FIND IN RETAILER STORE buttons show up
			List<WebElement> addToCartButton = null;
			if(Store=="US_OUTLET"){
				if(!Common.isElementExist(driver, By.xpath("//button[@id='addToCartButtonTop']"))){
					Assert.assertTrue(false, "The product is not avaible,please choose another product.");
				} else {
					addToCartButton = driver.findElements(By.xpath("//button[@id='addToCartButtonTop']"));
				}
			} else {
				if(!Common.isElementExist(driver, By.xpath("//span[contains(@id,'_cb_label_unchecked')]"), 10)){
					Assert.assertTrue(false, "The product is not avaible,please choose another product.");
				} else {
					addToCartButton = driver.findElements(By.xpath("//span[contains(@id,'_cb_label_unchecked')]"));
				}
			}
			if(addToCartButton!=null){
				List<WebElement> findInRetailerStoreButton = driver.findElements(By.xpath("//a[contains(.,'"+customizedButtonLabel+"')]")) ;
				Boolean buttonExist = Common.checkElementExists(driver, findInRetailerStoreButton.get(0), 60);
				if(addToCartButton.size()>0){
					Assert.assertTrue(buttonExist, "FIND IN RETAILER STORE buttons don't show up!");
				}	
				if(findInRetailerStoreButton.size() != addToCartButton.size()){
					if(findInRetailerStoreButton.size() != addToCartButton.size()*2){
						List<WebElement> newAddToCartButton =driver.findElements(By.xpath("//button[@id='addToCartButtonTop']"));
						List<WebElement> newFindInRetailerStoreButton =driver.findElements(By.xpath("//a[contains(.,'"+customizedButtonLabel+"')]"));
						Assert.assertEquals(newFindInRetailerStoreButton.size(), newAddToCartButton.size(),"The num of FIND IN RETAILER STORE buttons is error !");
					} else {
						Assert.assertEquals(findInRetailerStoreButton.size(), addToCartButton.size()*2,"The num of FIND IN RETAILER STORE buttons is error !");
					}			
				} else {
					Assert.assertEquals(findInRetailerStoreButton.size(), addToCartButton.size(),"The num of FIND IN RETAILER STORE buttons is error !");
				}		
				Common.sleep(10);
				if(buttonExist){
					Common.waitElementClickable(driver, b2cPage.Product_viewModel, 100);
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", b2cPage.Product_viewModel);
					Common.sleep(3000);
					findInRetailerStoreButton = driver.findElements(By.xpath("//a[contains(.,'"+customizedButtonLabel+"')]")) ;
					Common.javascriptClick(driver, findInRetailerStoreButton.get(0));
					driver.switchTo().frame("customized-button-iframe");
					if(Common.checkElementExists(driver, b2cPage.From_q, 5)) {
						Boolean popUpWindowExist = Common.checkElementExists(driver, b2cPage.From_q, 60);
						Assert.assertTrue(popUpWindowExist, "Don't go to bing pop-up window !");	
					}
				}	
			} else {
				Assert.assertTrue(false,"The product is not avaible,please choose another product.");
			}
				
		} catch(Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public void deleteNewPMIOverride(){
		if(Common.isElementExist(driver, By.xpath("//td[@class='label']/div[contains(.,'Country PMI Attribute Override:')]/../..//td[last()]//tr//td[5]/div"))){
			List<WebElement> countries = driver.findElements(By.xpath("//td[@class='label']/div[contains(.,'Country PMI Attribute Override:')]/../..//td[last()]//tr//td[5]/div"));		
			for(int i=countries.size()-1; i>=0; i--){
				List<WebElement> newCountries = driver.findElements(By.xpath("//td[@class='label']/div[contains(.,'Country PMI Attribute Override:')]/../..//td[last()]//tr//td[5]/div"));		
				String newStore = Store;
				if(newStore=="CA_AFFINITY"){
					newStore="CA";
				} else if(newStore=="USEPP" || newStore =="US_OUTLET" || newStore =="US_BPCTO"){
					newStore="US";
				} 
				if(newCountries.get(i).getText().toUpperCase().equals(newStore.toUpperCase())){
					Common.rightClick(driver, newCountries.get(i));
					System.out.println("rightClick!!!");
					hmcPage.Catalog_RemovePMIAttributeOverride.click();
					System.out.println("Click remove!!!");
					driver.switchTo().alert().accept();
					System.out.println("Click accept!!!");
					System.out.println("Remove the PMI override successfully!");
					} 
			}	
			hmcPage.Catalog_SaveButton.click();
			System.out.println("Click save button!!!");
		} else {
			System.out.println("Don't need to delete!");
		}
	}
	
	public void deleteSetOfCustomizedButton(){
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		hmcPage.B2CUnit_BrandleapToggleNA.click();
		hmcPage.B2CUnit_CustomizedButtonColor.clear();
		hmcPage.B2CUnit_CustomizedButtonLabel.clear();
		hmcPage.Common_SaveButton.click();
		System.out.println("Delete set customized button! ");
	}
	
	
	public void addNewPMIOverride(){
		Common.rightClick(driver, hmcPage.Catalog_CountryrPMIAttributeOverride);
		hmcPage.Catalog_CreatePMIAttributeOverride.click();
		System.out.println("Create PMI attribute override !");
		Common.sleep(6000);
		Common.switchToWindow(driver, 1);	
		Select attribute = new Select(hmcPage.Catalog_PMIAttribute);
		attribute.selectByVisibleText("customizedButtonURL");
		hmcPage.Catalog_PMIValue.clear();
		hmcPage.Catalog_PMIValue.sendKeys("http://cn.bing.com");
		Select country = new Select(hmcPage.Catalog_PMICountry);
		if(Store=="CA_AFFINITY"){
			country.selectByVisibleText("CA");
		} else if(Store=="USEPP" || Store =="US_OUTLET" || Store =="US_BPCTO"){
			country.selectByVisibleText("US");
		} else{
			country.selectByVisibleText(Store);
		}
		
		hmcPage.Catalog_PMIStartDateButton.click();	
		Common.switchToWindow(driver, 2);		
		hmcPage.Catalog_PMIStartDate.click();	
		Common.switchToWindow(driver, 1);
		hmcPage.Catalog_PMIEndDateButton.click();	
		Common.switchToWindow(driver, 2);
		hmcPage.Catalog_PMIEndDate.click();
		Common.switchToWindow(driver, 1);
	
		hmcPage.Catalog_PMIActive.click();
		hmcPage.Catalog_PMISaveButton.click();
		System.out.println("Click on PMISaveButton, and creat PMIOverride successfully!");
		driver.close();
		System.out.println("Close the driver!");
		Common.switchToWindow(driver,0);
		hmcPage.Catalog_SaveButton.click();
	}
	
}
