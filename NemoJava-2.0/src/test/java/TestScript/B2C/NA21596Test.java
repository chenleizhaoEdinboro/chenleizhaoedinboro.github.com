package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21596Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String Url;
	public String UnitID = "bpcto_us_insight_direct_usa_unit";
	
	
	public NA21596Test(String Store){
		this.Store = Store;
		this.testName = "NA-21596";
	}
	
	
	@Test
	public void NA21596(ITestContext ctx){
		
		try{
			this.prepareTest();
			hmcPage =new HMCPage(driver);
			b2cPage =new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getHomePageUrl();
			
			// 1 ,HMC　Setting:
			//HMC->B2Cunit->Site attribute->SLI TOGGLE->YES
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(20000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(20000);
			
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(40000);
			
			if(!hmcPage.SLIToggle.isSelected()){
				hmcPage.SLIToggle.click();
				hmcPage.Common_SaveButton.click();
			}
			
			boolean isSelected = hmcPage.SLIToggle.isSelected();
			
			Assert.assertTrue(isSelected);
			
			driver.get(b2cHomeURL);
			if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
				B2CCommon.handleGateKeeper(b2cPage, testData);
			} else {
				B2CCommon.handleGateKeeper(b2cPage, testData);
				// login B2C
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			
			// 2 ,GLOBAL SEARCH:
			if(Common.isElementExist(driver, By.xpath("//input[contains(@class,'searchInput-text')]"))) {
				b2cPage.Global_search.sendKeys("Thinkpad");
			}
			else if(Common.isElementExist(driver, By.xpath(".//*[@id='inputSearchText']"))) {
				b2cPage.Global_search2.sendKeys("Thinkpad");
			}
			b2cPage.Global_searchout.click();
			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			if(Common.isElementExist(driver, By.xpath("//a[@class='sli_product_link' or contains(@href,'us/en/lenovousepp/laptops')]"))){
				System.out.println("The function is worked.");
			}
			else {
				System.out.println("The function is not worked.");
			}
					
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}

	}
}
	

	