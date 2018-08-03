package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA28469Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2BPage b2bPage;

	public String orderNum;
	private String url = "/laptops-and-ultrabooks/c/LAPTOPS?menu-id=Laptops_Ultrabooks";
	private String mtmTitle;
	private String partnumCto2;
	private String titleCto;
	private String partnumber;

	public NA28469Test(String store,String partnumber) {
		this.Store = store;
		this.testName = "NA-28469";
		this.partnumber = partnumber;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2b"})
	public void NA28469(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			String hmcURL = testData.HMC.getHomePageUrl();
			String b2bURL = testData.B2B.getLoginUrl();

			// step1 Open one website
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			Dailylog.logInfoDB(1, "Open one website", Store, testName);
			
			//step2 Find one MTM product number, remember the part Number ON PLP and PDP
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2bPage.HomePage_productsLink, 5)) {
				b2bPage.HomePage_productsLink.click();
				b2bPage.HomePage_Laptop.click();
				Common.sleep(3000);
				if(Common.checkElementExists(driver, b2bPage.productPage_AddToCart, 5)) {
					driver.get(b2bURL+"/thinkpad-t-series/ThinkPad-T570"+"/p/"+partnumber);
					Common.sleep(3000);
					Common.scrollToElement(driver, b2bPage.Product_MTMpdp_part);
					String[] split = b2bPage.Product_MTMpdp_part.getText().split(":");
					String partnumber2 = split[split.length-1].trim();
					mtmTitle = driver.getTitle();
					Assert.assertEquals(partnumber, partnumber2);
					Dailylog.logInfoDB(2, "MTM product number", Store, testName);
					
				}else {
					Dailylog.logInfoDB(2, "has no mtm product", Store, testName);
				}
			}else {
				this.setNoDataLog("product link is not shown");
			}
			
			//step3 Find one CTO product number, remember the part Number ON PLP and PDP
			Common.switchToWindow(driver, 0);
			Common.sleep(3000);
			driver.get(b2bURL+url);
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2bPage.Product_cto, 5)) {
				List<WebElement> findElements = driver.findElements(By.xpath(".//img[@title='Customize and buy']"));
				findElements.get(findElements.size()-1).click();
				Common.sleep(3000);
				String[] split = driver.getCurrentUrl().split("/");
				partnumCto2 = split[split.length-1].trim();
				titleCto = driver.getTitle();
				Dailylog.logInfoDB(3, "CTO product number", Store, testName);
				
			}else {
				Dailylog.logInfoDB(3, "has no cto product", Store, testName);
			}
			
			
			//step4 logon HMC
			Common.sleep(3000);
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(4, "Logged into HMC", Store, testName);
			
			//step5 Catalog --> Products input PN   mtm
			Common.sleep(3000);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			Common.sleep(2000);
			hmcPage.Catalog_ArticleNumberTextBox.clear();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partnumber);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(3000);
			Assert.assertEquals(hmcPage.Catalog_partnbum.getText().trim(), partnumber);
			Assert.assertTrue(mtmTitle.contains(hmcPage.Catalog_identifler.getText().trim()));
			Dailylog.logInfoDB(5, "Products input PN   mtm", Store, testName);
			
			
			//step6 Catalog --> Products input PN   cto
			if(partnumCto2!=null) {
				Common.sleep(3000);
				hmcPage.Catalog_ArticleNumberTextBox.clear();
				hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partnumCto2+"");
				hmcPage.Catalog_SearchButton.click();
				Common.sleep(3000);
				Assert.assertEquals(hmcPage.Catalog_partnbum.getText().trim(), partnumCto2);
				Assert.assertTrue(titleCto.contains(hmcPage.Catalog_identifler.getText().trim()));
				Dailylog.logInfoDB(6, "Catalog --> Products input PN   cto", Store, testName);
			}else {
				Dailylog.logInfoDB(6, "has no cto product", Store, testName);
			}
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

}
