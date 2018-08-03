package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class NA17360Test extends SuperTestClass {
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String b2cHomeURL;
	private String hmcLoginURL;
	private String version;
	private String partnum;

	public NA17360Test(String store,String version,String partnum) {
		this.Store = store;
		this.testName = "NA-17360";
		this.version = version;
		this.partnum = partnum;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA17360(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			b2cHomeURL = testData.B2C.getHomePageUrl();
			hmcLoginURL = testData.HMC.getHomePageUrl();

			// =========== Step:1 Go to HMC->WCMS->pages ===========//
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(2000);
			hmcPage.hmcHome_WCMS.click();
			hmcPage.wcmsPage_pages.click();
			Dailylog.logInfoDB(1, "Go to HMC->WCMS->pages: ", Store, testName);

			// =========== Step:2 Search 'longScrollProductDetails' in ID textbox, and
			// 'auContentCatalog - Online' for ANZ B2C in catelog version dropdown
			Common.sleep(3000);
			hmcPage.Wcms_page_id.clear();
			hmcPage.Wcms_page_id.sendKeys("longScrollProductDetails");
			hmcPage.wcmsPagesPage_catalogVersionDD.click();
			if(version =="auContentCatalog") {
				hmcPage.Wcms_category.click();
			}
			if(version =="masterContentCatalog") {
				hmcPage.Wcms_page_master.click();
			}
			hmcPage.wcmsPagesPage_searchButton.click();
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.Wcms_page_searchresult, 5));
			Dailylog.logInfoDB(2, "Search 'longScrollProductDetails' in ID textbox", Store, testName);

			// =========== Step:3 Double click on the only searched out result, and go to
			// restrictions tab ===========//
			Common.doubleClick(driver, hmcPage.Wcms_page_searchresult);
			Common.sleep(3000);
			hmcPage.Wcms_restrictions.click();
			Dailylog.logInfoDB(3, "go to restrictions tab", Store, testName);

			// =========== Step:4 Click on the icon at the most left or under the page of
			// the table for 'Page Restrictions' tab ===========//
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.Wcms_page_restrictions);
			Dailylog.logInfoDB(4, "Page Restrictions", Store, testName);

			// =========== Step:5 randomly choose one of the products in products table
			Common.sleep(3000);
			Common.switchToWindow_Title(driver, "http://admin-pre-c-hybris.lenovo.com/hmc/hybris?wid=MC92x2524");
			try {
				Common.scrollToElement(driver, hmcPage.Wcms_page_parnum);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			String partnum = hmcPage.Wcms_page_parnum.getText();
			Common.sleep(4000);
			((JavascriptExecutor) driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 2);
			driver.get(b2cHomeURL + "/p/" + partnum);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Footer_signup, 5));
			Dailylog.logInfoDB(5, "after page load complete, product page is shown" + partnum, Store, testName);

			// =========== Step:6 Check the 1st screen of PDP ===========//
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Starting, 5)) {
				Common.scrollToElement(driver, b2cPage.Starting);
				Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.Starting, 5));
			}else {
				Assert.fail("has no Starting at:$449.00");
			}
			
			if( Common.checkElementExists(driver, b2cPage.Models_old, 5)) {
				b2cPage.Models_old.click();
				if(Common.checkElementExists(driver, b2cPage.Models_sum, 5) || Common.checkElementExists(driver, b2cPage.Models_other, 5)) {
					Dailylog.logInfoDB(6, "has variant products displayed " + partnum, Store, testName);
				}else {
					Assert.fail("has no variant products displayed");
				}
			}else {
				Dailylog.logInfoDB(6, "has no view models " + partnum, Store, testName);
			}
			
			if (Common.checkElementExists(driver, b2cPage.Models, 5) ) {
				b2cPage.Models.click();
				if(Common.checkElementExists(driver, b2cPage.Models_sum, 5) || Common.checkElementExists(driver, b2cPage.Models_other, 5)) {
					Dailylog.logInfoDB(6, "has variant products displayed " + partnum, Store, testName);
				}else {
					Assert.fail("has no variant products displayed");
				}
			}else {
				Dailylog.logInfoDB(6, "has no view models " + partnum, Store, testName);
			}
			Dailylog.logInfoDB(6, "Check the 1st screen of PDP" + partnum, Store, testName);

			// =========== Step:7 Check 2nd screen of PDP ===========//
			Common.sleep(3000);
			if (Common.checkElementExists(driver, b2cPage.Product_Reviews, 5)) {
				b2cPage.Product_Reviews.click();
			}
			Common.sleep(1500);
			if (Common.checkElementExists(driver, b2cPage.Product_Features, 5)) {
				b2cPage.Product_Features.click();
			}
			Common.sleep(1500);
			if (Common.checkElementExists(driver, b2cPage.Product_Tech_specs, 5)) {
				b2cPage.Product_Tech_specs.click();
			}
			Dailylog.logInfoDB(7, "Check 2nd screen of PDP" + partnum, Store, testName);

			// =========== Step:8 Click "FEATURES" TAB ===========//
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_Features, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_Features);
				b2cPage.Product_Features.click();
				Common.sleep(1500);
				if(Common.checkElementExists(driver, b2cPage.Product_Features_content, 5)) {
					Dailylog.logInfoDB(8, "has content" + partnum, Store, testName);
				}else {
					Assert.fail("has no content");
				}
				
				if(Common.checkElementExists(driver, b2cPage.Product_Features_img, 5)) {
					Dailylog.logInfoDB(8, "has img" + partnum, Store, testName);
				}else {
					Assert.fail("has no img");
				}
				Dailylog.logInfoDB(8, "Click FEATURES TAB" + partnum, Store, testName);
			}

			// =========== Step:9 Click "ACCESSORIES & SERVICES" TAB===========//
			// =========== Step:10 Click "TECH SPECS" TAB===========//
			Common.sleep(3000);
			if (Common.checkElementExists(driver, b2cPage.Product_Tech_specs, 5)) {
				b2cPage.Product_Tech_specs.click();
				Common.sleep(1500);
				if(Common.checkElementExists(driver, b2cPage.Product_Tech_specs_title, 5)) {
					Dailylog.logInfoDB(10, "has Tech_specs_title" + partnum, Store, testName);
				}else {
					Assert.fail("has no Tech_specs_title");
				}
				if(Common.checkElementExists(driver, b2cPage.Product_Tech_specs_content, 5)) {
					Dailylog.logInfoDB(10, "has Tech_specs_content" + partnum, Store, testName);
				}else {
					Assert.fail("has no Tech_specs_content");
				}
			}
			Dailylog.logInfoDB(10, "Click TECH SPECS TAB" + partnum, Store, testName);

			// =========== Step:11 Click 'VIEW CURRENT MODELS' TAB===========//
			Common.sleep(3000);
			if (Common.checkElementExists(driver, b2cPage.Models, 5)) {
				b2cPage.Models.click();
				Common.sleep(1500);
				if(Common.checkElementExists(driver, b2cPage.Product_model_title, 5)) {
					Dailylog.logInfoDB(10, "has model_title" + partnum, Store, testName);
				}else {
					Assert.fail("has no model_title");
				}
				
				if(Common.checkElementExists(driver, b2cPage.Product_model_price, 5) ) {
					Dailylog.logInfoDB(10, "has model_price" + partnum, Store, testName);
				}else {
					Assert.fail("has no model_price");
				}
				
			}
			Dailylog.logInfoDB(11, "Click 'VIEW CURRENT MODELS' TAB" + partnum, Store, testName);

			// =========== Step:12 Click 'Customise' button (if it is a CTO product), or
			// 'Add to Cart' button (if not a CTO product) ===========//
			Common.sleep(3000);
			if (Common.checkElementExists(driver, b2cPage.Product_custiome, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_custiome);
//				b2cPage.Product_custiome.click();
				Common.javascriptClick(driver, b2cPage.Product_custiome);
			}
			if (driver.getCurrentUrl().contains("cart")) {
				Dailylog.logInfoDB(12, "not cto" + partnum, Store, testName);
			} else {
				Dailylog.logInfoDB(12, "cto" + partnum, Store, testName);
			}
			Dailylog.logInfoDB(12, "Click 'Customise' button" + partnum, Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
