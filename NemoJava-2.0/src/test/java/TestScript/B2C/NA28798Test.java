package TestScript.B2C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA28798Test extends SuperTestClass {
	private B2CPage b2cPage; 
	private HMCPage hmcPage;
	String productNo;
	
	public NA28798Test(String store,String productNo) {
		this.Store = store;
		this.testName = "NA-28798";
		this.productNo = productNo;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28798(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			//Set subseries Sort By MTM and Ecoupon and Web price by ascend
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchOnlineProduct(driver, hmcPage, productNo);
			setModalSortRule(productNo,"CTO",true ,"Web Price",true );
			hmcPage.Home_closeSession.click();
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()+"/p/"+productNo);
			CheckModalSortRule("CTO",true ,"Web Price",true);
			//Set subseries Sort By CTO and Web price by descend
			driver.navigate().refresh();
			
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchOnlineProduct(driver, hmcPage, productNo);
			setModalSortRule(productNo,"MTM",true ,"Web Price",true );
			HMCCommon.cleanRadis(hmcPage, productNo);
			hmcPage.Home_closeSession.click();
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()+"/p/"+productNo);		
			
			driver.navigate().refresh();
			CheckModalSortRule("MTM",true ,"Web Price",true);
			
			}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}	
	}
	
	public void CheckModalSortRule(String productType,boolean ecoupon,String priceType,boolean priceSort){
		
		if(productType.equals("MTM")){
			int size = b2cPage.Subseries_ProductList.size();
			int[] buttonNames = new int[size];
			for(int i =1;i<=size;i++ ){
				if(driver.findElement(By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//form")).getAttribute("id").contains("CTO1WW")){
					buttonNames[i-1]=1;
				}else {
					buttonNames[i-1]=0;
				}
				if(i>1){
					System.out.println(i+""+(buttonNames[i-1]>=buttonNames[i-2]));
					Assert.assertTrue(buttonNames[i-1]>=buttonNames[i-2], "check if the product Type sort is right");
				}
			
			}
		}else if(productType.equals("CTO")){
			int size = b2cPage.Subseries_ProductList.size();
			int[] buttonNames = new int[size];
			for(int i =1;i<=size;i++ ){
				if(driver.findElement(By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//form")).getAttribute("id").contains("CTO1WW")){
					buttonNames[i-1]=0;
				}else {
					buttonNames[i-1]=1;
				}
				if(i>1){
					System.out.println(i+""+(buttonNames[i-1]>=buttonNames[i-2]));
					Assert.assertTrue(buttonNames[i-1]>=buttonNames[i-2], "check if the product Type sort is right");
				}
			
			}
		}
		if(ecoupon){
			int size = b2cPage.Subseries_ProductList.size();
			List<String> couponName =new ArrayList();
			List<String> couponSortName =new ArrayList();
			for(int i =1;i<=size;i++ ){
				if(Common.isElementExist(driver, By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//span[contains(@class,'couponCode')]"))){
					couponName.add(driver.findElement(By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//span[contains(@class,'couponCode')]")).getText());
				}else {
					couponName.add("zzzzzzzz");
				}
			}
			couponSortName = couponName;
			Collections.sort(couponName);
			Assert.assertEquals(couponSortName, couponName);		
		}
		if(priceType.equals("Web Price")){
			int size = b2cPage.Subseries_ProductList.size();
			List<Float> webPriceCTO =new ArrayList();
			List<Float> webPriceMTM =new ArrayList();
			List<Float> webPriceCTOSort =new ArrayList();
			List<Float> webPriceMTMSort =new ArrayList();
			for(int i =1;i<=size;i++ ){
				if(driver.findElement(By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//form")).getAttribute("id").contains("CTO1WW")){
					webPriceCTO.add(B2CCommon.GetPriceValue(driver.findElement(By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//dt[contains(@class,'webprice')]/following-sibling::dd")).getText()));
				}else{
					webPriceMTM.add(B2CCommon.GetPriceValue(driver.findElement(By.xpath(".//*[@id='tab-li-currentmodels']//ol/li["+i+"]//dt[contains(@class,'webprice')]/following-sibling::dd")).getText()));					
				}
											
			}
			if(webPriceCTO.size()>1){
				webPriceCTOSort = webPriceCTO;
				if(priceSort){
					Collections.sort(webPriceCTO);
				}else{
					Collections.sort(webPriceCTO, Collections.reverseOrder());
				}	
				Assert.assertEquals(webPriceCTO, webPriceCTOSort);		
			}
			if(webPriceMTM.size()>1){
				webPriceMTMSort = webPriceMTM;	
				if(priceSort){
					Collections.sort(webPriceMTM);
				}else{
					Collections.sort(webPriceMTM, Collections.reverseOrder());
				}
				Assert.assertEquals(webPriceMTM, webPriceMTMSort);	
			}		
		}
	}
	
	public void setModalSortRule(String productNo,String productType,boolean ecoupon,String priceType,boolean priceSort  ){
		hmcPage.Catalog_PMIAttributeOverride.click();
		if(Common.checkElementExists(driver, hmcPage.PMIOverride_GroupCollection_Result, 3)){
			Common.rightClick(driver, hmcPage.PMIOverride_GroupCollection);
			hmcPage.PMIOverride_GroupCollection_SelectAll.click();
			Common.sleep(3000);
			Common.waitElementClickable(driver,  hmcPage.PMIOverride_GroupCollection, 10);
			Common.rightClick(driver, hmcPage.PMIOverride_GroupCollection);
			hmcPage.PMIOverride_GroupCollection_Remove.click();
			Alert alert= driver.switchTo().alert();
			alert.accept();
			hmcPage.SaveButton.click();
		}
		//Common.waitElementClickable(driver, hmcPage.PMIOverride_GroupCollection, 3);
		Common.rightClick(driver, hmcPage.PMIOverride_GroupCollection);
		hmcPage.PMIOverride_GroupCollection_Create.click();
		Common.switchToWindow(driver, 1);
		hmcPage.PMIOverride_ModalSorting_Attribute.click();
		Common.sleep(2000);
		hmcPage.PMIOverride_ModalSorting_Search.click();
		Common.switchToWindow(driver,2);
		if(!productType.equals("")){
			driver.findElement(By.xpath(".//*[contains(@id,'.zProductType')]/option[contains(text(),'"+productType+"')]")).click();
		}else{
			driver.findElement(By.xpath(".//*[contains(@id,'.zProductType')]/option[contains(text(),'is empty')]")).click();
		}
		if(ecoupon){
			hmcPage.PMIOverride_ModalSorting_EcouponEnable.click();
		}else{
			hmcPage.PMIOverride_ModalSorting_EcouponDisable.click();
		}
		if(!productType.equals("")){
			driver.findElement(By.xpath(".//*[contains(@id,'.zPriceType')]/option[contains(text(),'"+priceType+"')]")).click();
			if(priceSort){
				hmcPage.PMIOverride_ModalSorting_SortByAscend.click();
			}else{
				hmcPage.PMIOverride_ModalSorting_SortByDescend.click();
			}
		}else{
			driver.findElement(By.xpath(".//*[contains(@id,'.zPriceType')]/option[contains(text(),'is empty')]")).click();
		}
		hmcPage.B2BUnit_SearchButton.click();
		
		if(Common.checkElementExists(driver, hmcPage.PMIOverride_ModalSorting_Result, 3)){
			hmcPage.PMIOverride_ModalSorting_Result.click();
			hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
			Common.switchToWindow(driver, 1);
		}else{
			driver.close();
			Common.switchToWindow(driver, 1);
			hmcPage.PMIOverride_ModalSorting_Rule.click();
			Common.rightClick(driver, hmcPage.PMIOverride_ModalSorting_Rule);
			Common.waitElementClickable(driver,  hmcPage.PMIOverride_ModalSorting_Create, 10);	
			hmcPage.PMIOverride_ModalSorting_Create.click();
			Common.switchToWindow(driver, 2);
			if(!productType.equals("")){
				driver.findElement(By.xpath(".//*[contains(@id,'.zProductType')]/option[contains(text(),'"+productType+"')]")).click();
			}
			if(ecoupon){
				hmcPage.PMIOverride_ModalSorting_SortByEcoupon.click();
			}
			if(!productType.equals("")){
				driver.findElement(By.xpath(".//*[contains(@id,'.zPriceType')]/option[contains(text(),'"+priceType+"')]")).click();
				if(priceSort){
					hmcPage.PMIOverride_ModalSorting_SortByAscend.click();
				}else{
					hmcPage.PMIOverride_ModalSorting_SortByDescend.click();
				}
			}
			hmcPage.SaveButton.click();
			driver.close();
			Common.switchToWindow(driver, 1);
		}		
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.clear();
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.sendKeys(testData.B2C.getUnit());
		String  PMIOverride_CTAButtonLink_GroupResult = ".//*[contains(@id,'AutocompleteReferenceEditor[in Attribute[.group]')][contains(@id,'"+testData.B2C.getUnit()+"')]";
		Common.waitElementClickable(driver, driver.findElement(By.xpath(PMIOverride_CTAButtonLink_GroupResult)), 10);
		driver.findElement(By.xpath(PMIOverride_CTAButtonLink_GroupResult)).click();		
		hmcPage.PMIOverride_CTAButtonLink_Active.click();
		hmcPage.SaveButton.click();	
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.SaveButton.click();
	}
	

	
	
	
	
	
}
