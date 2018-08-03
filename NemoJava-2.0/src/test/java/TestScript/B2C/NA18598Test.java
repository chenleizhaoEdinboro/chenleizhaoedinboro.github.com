package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18598Test extends SuperTestClass {

	public B2CPage b2cPage;
	private String number;
	private HMCPage hmcPage;
	private String url = "/laptops/thinkpad/thinkpad-p-series/c/thinkpadp";
	
	public NA18598Test(String store) {
		this.Store = store;
		this.testName = "NA-18598";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"uxui",  "p2", "b2c"})
	public void NA18598(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			String homePageUrl = testData.B2C.getHomePageUrl();

			// Step~1 login hmc
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.hmcHome_WCMS.click();
			hmcPage.wcmsPage_pages.click();
			hmcPage.Wcms_category.click();
			hmcPage.Type_select.click();
			hmcPage.Type_search.click();
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.Type_result);
			hmcPage.ApprovalStatus.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);
			
			//step 2 Go to B2C live site and login
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(testData.B2C.getHomePageUrl());
			Dailylog.logInfoDB(2, "Go to B2C live site", Store, testName);
			
			//step 3 Find a series category under Products
			driver.get(homePageUrl+url);
			Dailylog.logInfoDB(3, "Find a series category under Products", Store, testName);
			
			//step 4 Click 'Learn more' on one product with legacy PDP style
			List<WebElement> findElements = driver.findElements(By.xpath("//a[@class='seriesListings-footer-button button-called-out button-full']"));
			findElements.get(findElements.size()-1).click();
			Dailylog.logInfoDB(4, "Click 'Learn more' on one product with legacy PDP style", Store, testName);
			
			//step 5  Check the legacy PDP top section
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_hreoimg, 5)) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_hreoimg, 5));
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_herodescription, 5));
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_start, 5));
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_cus, 5));
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_intellogo, 5));
				Dailylog.logInfoDB(5, "Check the legacy PDP top section", Store, testName);
			}
			
			//step 6  Check legacy PDP middle section - Models Tab
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_model, 5)) {
				b2cPage.Product_model.click();
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_model_addtocart, 5));
				Dailylog.logInfoDB(6, "Check legacy PDP middle section - Models Tab", Store, testName);	
			}
			
			// step 7 Check legacy PDP middle section - Review Tab
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_reviews, 5)) {
				b2cPage.Product_reviews.click();
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_reviews_title, 5));
				Dailylog.logInfoDB(7, "Check legacy PDP middle section - Review Tab", Store, testName);
			}
			
			//step 8  Check legacy PDP middle section - Features Tab
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_features, 5)) {
				Common.javascriptClick(driver, b2cPage.Product_features);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_features_text, 5));
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_features_title, 5));
				Dailylog.logInfoDB(8, "Check legacy PDP middle section - Features Tab", Store, testName);
			}
			
			//step 9 Check legacy PDP middle section - Tech Specs Tab
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Product_tech_specs, 5)) {
				b2cPage.Product_tech_specs.click();
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_tech_content, 5));
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_tech_title, 5));
				Dailylog.logInfoDB(9, "Check legacy PDP middle section - Tech Specs Tab", Store, testName);
			}
			
			//step 10 Check legacy PDP footer
			Common.scrollToElement(driver, b2cPage.Product_sign_up);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_sign_up, 5));
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_foot_about, 5));
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_foot_print, 5));
			Dailylog.logInfoDB(10, "Check legacy PDP footer", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@Test(priority = 1,alwaysRun = true,  enabled = true,groups = {"uxui",  "p2", "b2c"})
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			System.out.println("rollback"); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.hmcHome_WCMS.click();
			hmcPage.wcmsPage_pages.click();
			hmcPage.Wcms_category.click();
			hmcPage.Type_select.click();
			hmcPage.Type_search.click();
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.Type_result);
			hmcPage.Approval.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
		
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	

}
