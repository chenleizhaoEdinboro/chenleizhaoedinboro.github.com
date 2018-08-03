package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.CTOCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.CTOPage;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA17711Test extends SuperTestClass {
	public String ProductMT;
	public String PartNo1;
	public String PartNo2;
	public String modalURL;
	public String bundleURL;
	public String Country;
	
	
	public NA17711Test(String store,String country) {
		this.Store = store;
		this.Country = country;
		this.testName = "NA-17711";
	}

	@Test(alwaysRun = true, groups = {"shopgroup", "cto", "p2", "b2c"})
	public void NA17711(ITestContext ctx) {
		try {
			this.prepareTest();
			ProductMT = "20HD";
			modalURL=testData.CTO.getHomePageUrl()+"ca/nomenclature=22TP2TT470020HD20HDCTO1WW########";
			CTOPage page1 = new CTOPage(driver);
			HMCPage page2 = new HMCPage(driver);
			B2CPage page3 = new B2CPage(driver);
			By byLocator3 = By.xpath(".//*[@id='gvaluesNB_CPU']/tbody[3]/tr[2]/td[11]");
	        By byLocator4 = By.xpath(".//*[@id='gvaluesNB_CPU']/tbody[3]/tr[2]/td[11]/i");
	        By byLocator5 = By.xpath(".//*[@id='gvaluesNB_CPU']/tbody[4]/tr[2]/td[11]");
	        By byLocator6 = By.xpath(".//*[@id='gvaluesNB_CPU']/tbody[4]/tr[2]/td[11]/i");
	        By byLocator7 = By.xpath(".//*[@id='gvaluesNB_CPU']/tbody[4]/tr[2]/td[14]/i");
	        By byLocator8 = By.xpath(".//*[@id='gvaluesNB_CPU']/tbody[4]/tr[2]/td[14]");
	        By byLocator9 = By.xpath(".//*[@id='gvaluesNB_MEM']/tbody[1]/tr[2]/td[10]");
	        By byLocator10 = By.xpath(".//*[@id='gvaluesNB_MEM']/tbody[1]/tr[2]/td[10]/i");
	  
	        driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(5000);
			CTOCommon.Login (page1,testData);
			Common.sleep(5000);
			acceptPOPAlert();
			Common.waitElementClickable(driver, page1.Home_SearchButton, 120);
			//page1.Home_SearchButton.click();
			createBundle(page1);
			handleWindow(page1);	        
			Common.sleep(5000);
			
			//create bundle and make enable +disable+lock rule
			bundleURL=driver.getCurrentUrl();
			Dailylog.logInfoDB(8, "create bundle url -- "+ driver.getCurrentUrl(),Store, testName);
			PartNo1 = getproductNo(driver.getCurrentUrl(),ProductMT);
			Dailylog.logInfoDB(8, "productNo1 : " + PartNo1,Store, testName);
		    refreshAudienceResults(1,page1);
		    Common.scrollToElement(driver, page1.BundlePage_StartAt);
		    page1.BundlePage_B2BAudience.click();
		    handleWindow(page1);
		    page1.BundlePage_B2CAudience.click();
		    handleWindow(page1);
		    Dailylog.logInfoDB(10, "bundle --audience chose B2C " ,Store, testName);
		    page1.BundlePage_CPUCollapse.click();
		    Thread.sleep(3000);
	        page1.BundlePage_I5CPU.click();
	        if(!driver.findElement(byLocator3).getAttribute("class").contains("yes")){
	            driver.findElement(byLocator4).click();
	        }
	        if(!driver.findElement(byLocator5).getAttribute("class").contains("yes")){
	            driver.findElement(byLocator6).click();
	        }
	        if(!driver.findElement(byLocator8).getAttribute("class").contains("yes")){
	            driver.findElement(byLocator7).click();
	        }
	        page1.BundlePage_MemoryCollapse.click();
	        if(!driver.findElement(byLocator9).getAttribute("class").contains("yes")){
	            driver.findElement(byLocator10).click();
	        }
	        Thread.sleep(3000);
	        page1.BundlePage_Save.click();
	        Thread.sleep(5000);
		    Common.scrollToElement(driver, page1.BundlePage_Nomenclature);
		    page1.BundlePage_Publish.click();
		    Thread.sleep(1000);
		    page1.BundlePage_PublishConfirm.click();
		    Dailylog.logInfoDB(10, "Confirmed publish" ,Store, testName);
		    try{
		    	new WebDriverWait(driver,180).until(ExpectedConditions.visibilityOf(page1.BundlePage_PublishSuccMsg));
		    	Dailylog.logInfoDB(10, "Configuration successfully published" ,Store, testName);
		    }catch(Exception e){
		    	Dailylog.logInfoDB(10, "Configuration publish failure" ,Store, testName);
		    	//assert false;
		    }
		    
		    //create linked bundle
		    linkBundle(page1);
		    handleWindow(page1);
	        		
			//check linked bundle rule
			Dailylog.logInfoDB(11, "link bundle url -- "+ driver.getCurrentUrl(),Store, testName);
			PartNo2 = getproductNo(driver.getCurrentUrl(),ProductMT);
			Dailylog.logInfoDB(12, "productNo2 : " + PartNo2,Store, testName);
			assert !PartNo1.equals(PartNo2);
		    refreshAudienceResults(1,page1);	    
			Common.scrollToElement(driver, page1.BundlePage_StartAt);	
		    page1.BundlePage_CPUCollapse.click();
	        Thread.sleep(3000);
	        assert page1.BundlePage_I5CPU.getAttribute("already-checked").equals("true");
	        assert driver.findElement(byLocator3).getAttribute("class").contains("yes");
	        assert driver.findElement(byLocator5).getAttribute("class").contains("yes");
	        assert driver.findElement(byLocator8).getAttribute("class").contains("yes");
	        page1.BundlePage_MemoryCollapse.click();
	        assert driver.findElement(byLocator9).getAttribute("class").contains("yes");
	        Thread.sleep(3000);
	        
	        Common.scrollToElement(driver, page1.BundlePage_Nomenclature);
		    page1.BundlePage_Publish.click();
		    Thread.sleep(1000);
		    page1.BundlePage_PublishConfirm.click();
		    Dailylog.logInfoDB(13, "Confirmed publish" ,Store, testName);
		    try{
		    	new WebDriverWait(driver,180).until(ExpectedConditions.visibilityOf(page1.BundlePage_PublishSuccMsg));
		    	Dailylog.logInfoDB(14, "Configuration successfully published" ,Store, testName);
		    }catch(Exception e){
		    	Dailylog.logInfoDB(14, "Configuration publish failure" ,Store, testName);
		    	//assert false;
		    }
		    
	        //Login HMC and Publish Bundle
			syncProduct(PartNo1,page2);
			syncProduct(PartNo2,page2);

	        
	        //Check Rules On Web
			checkRuleOnWeb(PartNo1,page3);
			checkRuleOnWeb(PartNo2,page3);	

			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
		}
	public void createBundle(CTOPage page1){
		driver.get(modalURL);
		Common.sleep(100000);
		assert (driver.getCurrentUrl()).contains("WW###");
	    //page1.ModalPage_CreateFrom.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", page1.ModalPage_CreateFrom);
	    Common.sleep(3000);
	    page1.ModalPage_SelectCountry.click();
	    Dailylog.logInfoDB(5, "selectCountry click" ,Store, testName);
        Common.waitElementClickable(driver, page1.ModalPage_CountryInput, 300);
        driver.findElement(By.xpath(".//*[@id='create-from-me-modal']//span[text()='" + Country + "']")).click();
        Common.sleep(10000);
        page1.ModalPage_BundleAdd.click();
        Dailylog.logInfoDB(6, "Add click" ,Store, testName);
        Common.sleep(5000);
        Common.waitElementClickable(driver, page1.ModalPage_AddConfirm, 300);
        page1.ModalPage_AddConfirm.click();
        Dailylog.logInfoDB(7, "ok button click" ,Store, testName);
        Common.sleep(100000);
	   
	}
	public void handleWindow(CTOPage page1){
		 if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class,'dismiss-invalid-modal')]"),120)){   		
	    	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'dismiss-invalid-modal')]")));			
			Dailylog.logInfoDB(8, "OK button click on pop up invalid configuration window",Store, testName);				 
	 	}
	}

	
	public void linkBundle(CTOPage page1){
		
		Dailylog.logInfoDB(11, "Linked bundle" ,Store, testName);
		Common.sleep(50000);
		//page1.LinkFromMe.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", page1.LinkFromMe);
		Common.sleep(3000);
		page1.ModalPage_SelectCountry.click();
		driver.findElement(By.xpath(".//*[@id='create-from-me-modal']//span[text()='" + Country + "']")).click();
	    Common.sleep(10000);
	    page1.ModalPage_BundleAdd.click();
	    Common.sleep(5000);
	    Common.waitElementClickable(driver, page1.ModalPage_AddConfirm, 300);
	    page1.ModalPage_AddConfirm.click();
	    Common.sleep(100000);
	   
	}
	
	public void clickSearchResults(int index,String ProductMT,CTOPage page1){
		
		if(index <= 3){
			try{
				Common.waitElementClickable(driver, page1.SearchPage_SearchResult, 120);
				//page1.SearchPage_SearchResult.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", page1.SearchPage_SearchResult);
				Common.sleep(10000);
				Dailylog.logInfoDB(1, "Opened model Url",Store, testName);
			}catch(Exception e){
				Dailylog.logInfoDB(2, "No results of model, retry " + index,Store, testName);
				page1.SearchPage_ModalSearchInput.clear();
				page1.SearchPage_ModalSearchInput.sendKeys(ProductMT);
                clickSearchResults(index + 1,ProductMT,page1);
			}finally{
				Dailylog.logInfoDB(3, "No results displaying while searching model",Store, testName);
				assert index<=3;
			}
		}
		
	}
	public String getproductNo(String url,String productMT){
		return url.substring(url.lastIndexOf(productMT), url.indexOf("~"));
	}
	
	public void refreshAudienceResults(int index,CTOPage page1){
		
		if(index<=3){
			try{
				Common.waitElementClickable(driver, page1.BundlePage_B2CAudience, 120);
				Dailylog.logInfoDB(8, "Audience is display",Store, testName);
				}
			catch(Exception e){
				Dailylog.logInfoDB(8, "Audience is not display, retry " + index,Store, testName);
                driver.navigate().refresh();
                refreshAudienceResults(index + 1,page1);
                }           
		}else{
			Dailylog.logInfoDB(8, "Audience is not display" + index,Store, testName);
			assert (index<=3);
		}
			
	}
	
	public void acceptPOPAlert(){
		try {
			Alert alt = driver.switchTo().alert();
			alt.accept();
		}catch(Exception e){
			Dailylog.logInfoDB(5, "No alert Open",Store, testName);
		}
	}
	
	public void getCTOConfiguratorPage(int index,B2CPage page3){
		if(index<=3){
			try{
				Common.sleep(5000);
				acceptPOPAlert();
				Common.waitElementClickable(driver, page3.Product_AddToCartBtn, 100);
				Dailylog.logInfoDB(8, "Configurator Page is display",Store, testName);
				}
			catch(Exception e){
				Dailylog.logInfoDB(8, "Configurator Page is not display, retry " + index,Store, testName);
                driver.navigate().refresh();
                getCTOConfiguratorPage(index + 1,page3);
                }  
		}else{
			Dailylog.logInfoDB(8, "Configurator Page is not display" + index,Store, testName);
			assert (index<=3);
		}
		
	}
	public void checkRuleOnWeb(String ProductNo,B2CPage page3){
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+ProductNo);
		B2CCommon.handleGateKeeper(page3, testData);
		Common.sleep(5000);
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+ProductNo);
		Common.sleep(5000);
		Common.waitElementClickable(driver, page3.PDP_ViewCurrentModelTab, 300);
		page3.PDP_ViewCurrentModelTab.click();
		Common.sleep(5000);
		Common.waitElementClickable(driver, page3.B2C_PDP_CustomizeButton, 300);
		page3.B2C_PDP_CustomizeButton.click();
		Common.sleep(5000);
		getCTOConfiguratorPage(1,page3);
		boolean isNewUI = false;
		if(Common.isElementExist(driver, By.xpath(".//*[@id='cta-builder-skip']/button"))){
			isNewUI = true;
		}
		if(isNewUI){
			assert page3.CPU_EnableOne_New.getAttribute("class").contains("visible");
	        assert page3.CPU_EnableTwo_New.getAttribute("class").contains("visible");
	        //assert page3.CPU_EnableTwo_New.getAttribute("class").contains("disabled");
	        assert page3.CPU_EnableOne_New.getAttribute("class").contains("default");
	        assert page3.Memory_DisableOne_New.getAttribute("class").contains("hidden");
		}else{
			assert page3.CPU_EnableOne.getAttribute("class").contains("visible");
	        assert page3.CPU_EnableTwo.getAttribute("class").contains("visible");
	        assert page3.CPU_EnableTwo_Label.getAttribute("class").contains("disabled");
	        assert page3.CPUCheckBox_EnableOne.isSelected();
	        assert page3.Memory_DisableOne.getAttribute("class").contains("hidden");
		}
        Dailylog.logInfoDB(16, "Check Rules on Web Pass : " + ProductNo,Store, testName);
	}
	
	public void syncProduct(String ProductNo,HMCPage page2){
		driver.manage().deleteAllCookies();
		Common.sleep(10000);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(page2, testData);
		driver.navigate().refresh();
		page2.Home_CatalogLink.click();
		page2.Home_ProductsLink.click();
		Common.sleep(5000);
		page2.Catalog_ArticleNumberTextBox.clear();
		page2.Catalog_ArticleNumberTextBox.sendKeys(ProductNo);
		page2.Cataglog_CatalogVersionSel.click();
	    Common.waitElementClickable(driver, page2.Cataglog_CatalogVersionStaged, 600);
		page2.Cataglog_CatalogVersionStaged.click();
		page2.B2BUnit_SearchButton.click();
		
		By byLocator3 = By.id("Content/StringDisplay[" + ProductNo + "]_span");
		Common.waitElementClickable(driver, driver.findElement(byLocator3), 600);
		Common.doubleClick(driver, driver.findElement(byLocator3));
		page2.Product_DisplayTo.sendKeys(testData.B2C.getUnit());
		Common.waitElementClickable(driver, driver.findElement(By.xpath("//td[contains(text(),'" + testData.B2C.getUnit() + "')]")), 600);
		driver.findElement(By.xpath("//td[contains(text(),'" + testData.B2C.getUnit() + "')]")).click();
		page2.Types_SaveBtn.click();
		Common.waitElementClickable(driver, page2.Product_SynchronizationBtn, 600);
		page2.Product_SynchronizationBtn.click();
		Dailylog.logInfoDB(15, "syn product No : " + ProductNo, Store, testName);
		if(!windowCount(2)){
			Common.sleep(5000);
		}
		
		String mainHandle = driver.getWindowHandle();
		for (String handle:driver.getWindowHandles() ){
			if(!handle.equals(mainHandle)){
				driver.switchTo().window(handle);
				page2.Product_SyncTargetBtn.click();
				if(Store.contains("AU") || Store.contains("NZ")) {
                    page2.Product_SyncExeJobANZ.click();
                } else if(Store.contains("US") || Store.contains("CA")) {
                	page2.Product_SyncExeJobNA.click();
                }  else if(Store.contains("GB")  || Store.contains("FR") ) {
                	page2.Product_SyncExeJobEMEA.click();
                } else {
                	page2.Product_SyncExeJobAP2.click();
                }
				page2.Product_NeedRunMapping.click();
				Common.sleep(3000);
//				new WebDriverWait(driver,300).until(
//						ExpectedConditions.visibilityOf(page2.Product_SyncStart));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", page2.Product_SyncStart);
				//page2.Product_SyncStart.click();
				Common.sleep(30000);
				new WebDriverWait(driver,30000).until(
						ExpectedConditions.visibilityOf(page2.Product_SyncFinishMsg));
				Common.sleep(3000);
				assert page2.Product_SyncStatus.getText().contains("FINISHED");
				//assert page2.Product_SyncResult.getText().contains("SUCCESS");
				driver.close();
				driver.switchTo().window(mainHandle);
			}	
		}
		
		
	}
	public boolean windowCount(int count){
		return driver.getWindowHandles().size()>=count;
	}
	
}
