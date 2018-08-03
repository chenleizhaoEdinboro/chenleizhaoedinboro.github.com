package TestScript.B2C;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class NA26649Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String tag;
	private String url;
	private String blue = "bluetag";
	private String productNm;

	public NA26649Test(String store,String tag) {
		this.Store = store;
		this.testName = "NA-26649";
		this.tag = tag;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA26649(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getHomePageUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			productNm = testData.B2C.getDefaultMTMPN();

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL+"/p/"+productNm);
			Dailylog.logInfoDB(1, "Accessing B2C URL", Store, testName);
			b2cPage.PDP_previousLevel.click();
			url = driver.getCurrentUrl();
			driver.get(b2cHomeURL+"/p/"+productNm);
		    //===========step:2 login hmc"=======//
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			createTag();
			
			//=========== Step:3 pdp tag ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			b2cPage.Models.click();
			if(Common.checkElementExists(driver, b2cPage.Label, 5)) {
				Assert.assertTrue(b2cPage.Label.getText().contains(tag));
				Dailylog.logInfoDB(3, "pdp tag", Store, testName);
			}
			
			driver.get(url);
			driver.navigate().refresh();
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_Label, 5)) {
				Assert.assertTrue(b2cPage.Product_Label.getText().contains(tag));
				Dailylog.logInfoDB(4, "plp tag", Store, testName);
			}
	
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	public void createTag() throws InterruptedException {
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productNm);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(3000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(3000);
		hmcPage.Catalog_PMIAttributeOverride.click();
		Common.scrollToElement(driver, hmcPage.Create_region);
		if(Common.checkElementExists(driver, hmcPage.EditMerchandisingtaglabel, 5)) {
			Common.rightClick(driver, hmcPage.EditMerchandisingtaglabel);
			hmcPage.EditMerchandising.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Collection Attribute Override - hybris Management Console (hMC)");
			Common.sleep(2000);
			Common.scrollToElement(driver, hmcPage.EditMerchandising_table);
			Common.rightClick(driver, hmcPage.EditMerchandising_table);
			hmcPage.EditmerchandisingTag.click();
			Common.switchToWindow_Title(driver, "Editor: [MerchandisingTag] - hybris Management Console (hMC)");
			Common.sleep(2000);
			hmcPage.Create_Create_Merchandisingtaglabel_color.click();
			hmcPage.Create_Create_Merchandisingtaglabel_label.clear();
			hmcPage.Create_Create_Merchandisingtaglabel_label.sendKeys(tag);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.sleep(3000);
			Common.switchToWindow_Title(driver, "Editor: PMI Collection Attribute Override - hybris Management Console (hMC)");
			hmcPage.Create_merchandisingTag.click();
			hmcPage.Edit_country.click();
			hmcPage.MerchandisingTagstart.clear();
			hmcPage.MerchandisingTagstarttime.clear();
			hmcPage.MerchandisingTagend.clear();
			hmcPage.MerchandisingTagendttime.clear();
			hmcPage.Edit_active.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.switchToWindow(driver, 1);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Dailylog.logInfoDB(2, "login hmc", Store, testName);
			
		}else {
			Common.rightClick(driver, hmcPage.regionPmiCollectionAttributeOverrides);
			hmcPage.Create_pmi.click();
			Common.switchToWindow_Title(driver, "Editor: PMI Collection Attribute Override - hybris Management Console (hMC)");
			Common.sleep(2000);
			Common.scrollToElement(driver, hmcPage.Create_Merchandisingtag);
			Common.rightClick(driver, hmcPage.Create_Merchandisingtag);
			hmcPage.Create_Create_Merchandisingtaglabel.click();
			Common.switchToWindow_Title(driver, "Editor: [MerchandisingTag] - hybris Management Console (hMC)");
			Common.sleep(2000);
			hmcPage.Create_Create_Merchandisingtaglabel_color.click();
			hmcPage.Create_Create_Merchandisingtaglabel_label.clear();
			hmcPage.Create_Create_Merchandisingtaglabel_label.sendKeys(tag);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.sleep(3000);
			Common.switchToWindow_Title(driver, "Editor: PMI Collection Attribute Override - hybris Management Console (hMC)");
			hmcPage.Create_merchandisingTag.click();
			hmcPage.Create_country.click();
			hmcPage.active.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.switchToWindow(driver, 1);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Dailylog.logInfoDB(2, "login hmc", Store, testName);
		}
	}
}
