package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

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

public class NA27440Test extends SuperTestClass {
	public String ProductMT;
	public String ProductNo;
	public String ProductNoLink;
	public String PartNo;
	public String modalURL;
	public String PartNoLink;
	private String Country;
	private CTOPage pageCTO; 
	private HMCPage pageHMC; 
	private B2CPage pageB2C; 
	
	
	public NA27440Test(String store,String country) {
		this.Store = store;
		this.Country = country;
		this.testName = "NA-27440";
		
	}

	@Test(alwaysRun = true, groups = {"shopgroup", "cto", "p2", "b2c"})
	public void NA27440(ITestContext ctx) {
		try {
			this.prepareTest();
			pageCTO = new CTOPage(driver);
			pageHMC = new HMCPage(driver);
			pageB2C = new B2CPage(driver);
			ProductMT = "20L5";//nomenclature=22TP2TT470020HD20HDCTO1WWENCA20~
			modalURL=testData.CTO.getHomePageUrl()+"ca/nomenclature=22TP2TT480020L520L5CTO1WW########";
			String ImageUrl= "https://www.lenovo.com/medias/visacard.png?context=bWFzdGVyfC9pbWFnZXMvbGEvbGFuZGluZ"
					+ "3wyNTg1fGltYWdlL3BuZ3wvaW1hZ2VzL2xhL2xhbmRpbmcvaGZmL2g5OC85NDMwMjE5NDg5MzEwLnBuZ3w5OWZiNjA2M"
					+ "mU3Nzg2ZjQ2YjU2NTA1OWVjNmFmNDY0Zjc5MjQwMTE1ZjBjYzI0MmVlOGI4Zjg5YWE3MzViNjBi&w=1920";
			
			//String BundleUrl="http://pre-c-hybris.lenovo.com/configurator_hana/#ca/nomenclature=22TP2TT470020HD20HDCTO1WWENCA3~~~&create=true";
			driver.get(testData.CTO.getHomePageUrl());
			Common.sleep(5000);
			CTOCommon.Login (pageCTO,testData);
			Common.sleep(5000);
			acceptPOPAlert();
			Common.waitElementClickable(driver, pageCTO.Home_SearchButton, 10);
			//page1.Home_SearchButton.click();
			
			createBundle(pageCTO);
			handleCreateWindow(pageCTO);
			Dailylog.logInfoDB(8, "create bundle url -- "+ driver.getCurrentUrl(),Store, testName);
			PartNo = getpartNo(driver.getCurrentUrl(),ProductMT);
			ProductNo = getproductNo(driver.getCurrentUrl(),ProductMT); 
			Dailylog.logInfoDB(8, "partNo1 : " + PartNo,Store, testName);
			Common.scrollToElement(driver, pageCTO.BundlePage_StartAt);
		    refreshAudienceResults(1,pageCTO);
		    
		    Common.scrollToElement(driver, pageCTO.BundlePage_StartAt);
		    handleCreateWindow(pageCTO);
		    Common.scrollToElement(driver, pageCTO.BundlePage_StartAt);
		    pageCTO.BundlePage_B2BAudience.click();
		    handleWindow(pageCTO);
		    pageCTO.BundlePage_B2CAudience.click();
		    handleWindow(pageCTO);
		    Dailylog.logInfoDB(10, "bundle --audience chose B2C " ,Store, testName);	       
	        Thread.sleep(3000);
	        pageCTO.BundlePage_Save.click();
			Common.scrollToElement(driver, pageCTO.BundlePage_FirstCPUCollapse);
			setTheCTORule(ImageUrl);
	        Thread.sleep(5000);
		    Common.scrollToElement(driver, pageCTO.BundlePage_Publish);
		    pageCTO.BundlePage_Publish.click();
		    Thread.sleep(1000);
		    pageCTO.BundlePage_PublishConfirm.click();
		    Dailylog.logInfoDB(10, "Confirmed publish" ,Store, testName);
		    try{
		    	new WebDriverWait(driver,180).until(ExpectedConditions.visibilityOf(pageCTO.BundlePage_PublishSuccMsg));
		    	Dailylog.logInfoDB(10, "Configuration successfully published" ,Store, testName);
		    }catch(Exception e){
		    	Dailylog.logInfoDB(10, "Configuration publish failure" ,Store, testName);
		    	//assert false;
		    }
		    String WindowHandle = driver.getWindowHandle();
		    pageCTO.BundlePage_PreviewButton.click();
		    for (String handle:driver.getWindowHandles() ){
				if(!handle.equals(WindowHandle)){
					driver.switchTo().window(handle);
					checkPreviewRule(ImageUrl);
					driver.close();
				    driver.switchTo().window(WindowHandle);
				}
		    }
		    
		    
		    //create linked bundle
		    pageCTO.LinkFromMe.click();
		    Common.sleep(3000);
		    pageCTO.ModalPage_SelectCountry.click();
		    driver.findElement(By.xpath(".//*[@id='create-from-me-modal']//span[text()='" + Country + "']")).click();
	        Common.sleep(10000);
	        if(Store.equals("CA")){
	        	pageCTO.BundlePage_FRDelete.click();
	        }else if(Store.equals("HK")){
	        	pageCTO.BundlePage_CHDelete.click();
	        }else if(Store.equals("TW")){
	        	pageCTO.BundlePage_CHDelete.click();
	        }else if(Store.equals("JP")){
	        	pageCTO.BundlePage_JPDelete.click();
	        }
	        pageCTO.ModalPage_BundleAdd.click();
	        Common.sleep(5000);
	        Common.waitElementClickable(driver, pageCTO.ModalPage_AddConfirm, 300);
	        pageCTO.ModalPage_AddConfirm.click();
	        Common.sleep(10000);
	        		
			//check linked bundle rule
			Dailylog.logInfoDB(11, "link bundle url -- "+ driver.getCurrentUrl(),Store, testName);
			PartNoLink = getpartNo(driver.getCurrentUrl(),ProductMT);
			ProductNoLink = getproductNo(driver.getCurrentUrl(),ProductMT); 
			Dailylog.logInfoDB(12, "productNo2 : " + PartNoLink,Store, testName);
			assert !PartNo.equals(PartNoLink);
		    refreshAudienceResults(1,pageCTO);	    
			Common.scrollToElement(driver, pageCTO.BundlePage_StartAt);	
		    checkTheLinkRule(ImageUrl);
	        
	        Thread.sleep(3000);      
	        Common.scrollToElement(driver, pageCTO.BundlePage_Nomenclature);
	        pageCTO.BundlePage_Publish.click();
		    Thread.sleep(1000);
		    pageCTO.BundlePage_PublishConfirm.click();
		    Dailylog.logInfoDB(13, "Confirmed publish" ,Store, testName);
		    try{
		    	new WebDriverWait(driver,180).until(ExpectedConditions.visibilityOf(pageCTO.BundlePage_PublishSuccMsg));
		    	Dailylog.logInfoDB(14, "Configuration successfully published" ,Store, testName);
		    }catch(Exception e){
		    	Dailylog.logInfoDB(14, "Configuration publish failure" ,Store, testName);
		    	//assert false;
		    }
		    WindowHandle = driver.getWindowHandle();
		    pageCTO.BundlePage_PreviewButton.click();
		    for (String handle:driver.getWindowHandles() ){
				if(!handle.equals(WindowHandle)){
					driver.switchTo().window(handle);
					checkPreviewRule(ImageUrl);
					driver.close();
				    driver.switchTo().window(WindowHandle);
				}
		    }
		    
	        //Login HMC and Publish Bundle
			syncProduct(PartNo,pageHMC);
			syncProduct(PartNoLink,pageHMC);
	        
	        //Check Rules On Web
			checkRuleOnWeb(PartNo,ImageUrl);
			checkRuleOnWeb(ProductNoLink,ImageUrl);
			  			
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
		}
	
	public void createBundle(CTOPage page1){
		getModalUrl(modalURL,1);
		Common.sleep(10000);
		//pageCTO.ModalPage_CreateFrom.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", page1.ModalPage_CreateFrom);
	    Common.sleep(3000);
	    pageCTO.ModalPage_SelectCountry.click();
	    Dailylog.logInfoDB(5, "selectCountry click" ,Store, testName);
        Common.waitElementClickable(driver, pageCTO.ModalPage_CountryInput, 300);
        driver.findElement(By.xpath(".//*[@id='create-from-me-modal']//span[text()='" + Country + "']")).click();
        Common.sleep(10000);
        if(Store.equals("CA")){
        	pageCTO.BundlePage_FRDelete.click();
        }else if(Store.equals("HK")){
        	pageCTO.BundlePage_CHDelete.click();
        }else if(Store.equals("TW")){
        	pageCTO.BundlePage_CHDelete.click();
        }else if(Store.equals("JP")){
        	pageCTO.BundlePage_JPDelete.click();
        }        
        pageCTO.ModalPage_BundleAdd.click();
        Dailylog.logInfoDB(6, "Add click" ,Store, testName);
        Common.sleep(5000);
        Common.waitElementClickable(driver, pageCTO.ModalPage_AddConfirm, 300);
        pageCTO.ModalPage_AddConfirm.click();
        Dailylog.logInfoDB(7, "ok button click" ,Store, testName);
        Common.sleep(10000);
	   
	}
	public void handleWindow(CTOPage page1){
		 if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class,'dismiss-invalid-modal')]"),120)){   		
	    	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'dismiss-invalid-modal')]")));			
			Dailylog.logInfoDB(8, "OK button click on pop up invalid configuration window",Store, testName);
			Common.sleep(5000);
	 	}
	}
	
	public void handleCreateWindow(CTOPage page1){
		for(int i=0;i<4;i++) {
			 if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class,'dismiss-invalid-modal')]"),120)){
		    	 if(Common.checkElementDisplays(driver, By.xpath("//div[@class='modal-body invalid-modal-body']/i[text()='Processor']"),120)){    		
		    		 handleWindow(page1);			 
					 pageCTO.BundlePage_Save.click();
					 createBundle(page1); 					
		 		}else {
					 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'dismiss-invalid-modal')]")));			
					 Dailylog.logInfoDB(8, "OK button click on pop up invalid configuration window",Store, testName);	
					 break;
				}		    
			}else{
				Dailylog.logInfoDB(9, "No invalid configuration window",Store, testName);	
				break;
			}
		}
		Common.sleep(5000);
	   
	}
	
	public void setTheCTORule(String ImageUrl){
		pageCTO.BundlePage_FirstCPUCollapse.click();
		Common.waitElementClickable(driver, pageCTO.BundlePage_firstCPUCollapse, 3);
		pageCTO.BundlePage_firstCPUCollapse.click();
		Common.sleep(5000);
		try{
			Common.scrollToElement(driver, pageCTO.BundlePage_CPUImageUrl);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		pageCTO.BundlePage_CPUImageUrl.clear();
		pageCTO.BundlePage_CPUImageUrl.sendKeys(ImageUrl);
		try{
			Common.scrollToElement(driver, pageCTO.BundlePage_StartAt);
		}
	    catch(Exception e){
	    	
	    }
		pageCTO.BundlePage_Save.click();
	
	}
	public void checkTheLinkRule(String ImageUrl){
		pageCTO.BundlePage_FirstCPUCollapse.click();
		pageCTO.BundlePage_firstCPUCollapse.click();
		Assert.assertTrue("check Rule On Linked Bundle ", pageCTO.BundlePage_CPUImageUrl.getAttribute("value").equals(ImageUrl));
		
	}
	public void checkPreviewRule(String ImageUrl){
		Assert.assertTrue("check Rule On Preview Fail ", pageB2C.CTOConfig_CPUImage.getAttribute("src").equals(ImageUrl));
	}
	public void getModalUrl(String URL,int index){
		if(index<=3){
			boolean flag = false;
			try{
				driver.get(URL);
				Dailylog.logInfoDB(5, "Get Modal URL",Store, testName);
				Common.sleep(100000);
				try{Alert alr= driver.switchTo().alert();
					alr.accept();
					flag =  true;
				}catch(Exception e){
					Dailylog.logInfoDB(5, "No alert Open",Store, testName);
				}
				if(flag == true){
					getModalUrl(URL, index+1);
				}
			}catch(Exception e){
				Dailylog.logInfoDB(5, "Open Modal 3 times but still fail",Store, testName);
			}
		}
	}
	

	public String getpartNo(String url,String productMT){
		return url.substring(url.lastIndexOf(productMT), url.indexOf("~"));
	}
	
	public String getproductNo(String url,String productMT){
		return url.substring(url.lastIndexOf("nomenclature=") + 13, url.indexOf(productMT));
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
	

	public void checkRuleOnWeb(String ProductNo,String ImageUrl){
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+ProductNo);
		B2CCommon.handleGateKeeper(pageB2C, testData);
		Common.sleep(5000);
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+ProductNo);
		Common.sleep(5000);
		Common.waitElementClickable(driver, pageB2C.PDP_ViewCurrentModelTab, 300);
		pageB2C.PDP_ViewCurrentModelTab.click();
		Common.sleep(5000);
		if (Common.checkElementExists(driver, pageB2C.B2C_PDP_CustomizeButton, 5))
			pageB2C.B2C_PDP_CustomizeButton.click();
		else
			pageB2C.Cart_saveCartBtn.click();
		Common.sleep(5000);
		getCTOConfiguratorPage(1,pageB2C);
		boolean isNewUI = false;
		if(Common.isElementExist(driver, By.xpath(".//*[@id='cta-builder-skip']/button"))){
			isNewUI = true;
		}
		if(isNewUI){
			Assert.assertTrue("check Image On WEb Fail", pageB2C.CTOConfig_CPUImage.getAttribute("src").equals(ImageUrl));
		}else{
			Assert.assertTrue("check Image On WEb Fail", pageB2C.CTOConfig_CPUImage.getAttribute("src").equals(ImageUrl));
		}
        Dailylog.logInfoDB(16, "Check Rules on Web Pass : " + ProductNo,Store, testName);
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
}
