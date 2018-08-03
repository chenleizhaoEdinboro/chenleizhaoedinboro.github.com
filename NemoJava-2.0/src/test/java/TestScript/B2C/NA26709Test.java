package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA26709Test extends SuperTestClass {
	public B2CPage b2cPage;
	private String errorNumber;
	private String rightNumber;
	private String url = "/services-warranty/?menu-id=Services_Warranty";

	public NA26709Test(String store ,String errorNumber, String rightNumber) {
		this.Store = store;
		this.testName = "NA-26709";
		this.errorNumber = errorNumber;
		this.rightNumber = rightNumber;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA26709(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);

			// step 1 b2c site
			String homepageUrl = testData.B2C.getHomePageUrl();
			driver.get(homepageUrl);
			Dailylog.logInfoDB(1, "load b2c", Store, testName);
			
			// step 2 Warranty
			Common.sleep(3000);
			driver.get(homepageUrl+url);
			Dailylog.logInfoDB(2, "Warranty", Store, testName);
			
			//step 3 input errorNumber
			Common.sleep(3000);
			if(Store.toLowerCase().equals("au")) {
				b2cPage.Warranty_input.clear();
				//b2cPage.Warranty_input.sendKeys(errorNumber);
				b2cPage.Warranty_continue.click();
				auAndus();
			}else if(Store.toLowerCase().equals("us")){
				b2cPage.Warranty_input.clear();
				//b2cPage.Warranty_input.sendKeys(errorNumber);
				b2cPage.Warranty_continue.click();
				auAndus();
			}else if(Store.toLowerCase().equals("jp") || Store.toLowerCase().equals("ca")) {
				b2cPage.Warranty_buttoncajp.click();
				Common.sleep(3000);
				b2cPage.Warranty_numberlink.click();
				if(Store.toLowerCase().equals("ca")) {
					Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Warranty_popup, 5));
				}else if(Store.toLowerCase().equals("jp")) {
					Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Warranty_numberlinkresult, 5));
				}
				Dailylog.logInfoDB(3, "input errorNumber", Store, testName);
			}
			
			//step 4 input rightnumber
			Common.sleep(3000);
			driver.get(homepageUrl+url);
			Common.sleep(3000);
			if(Store.toLowerCase().equals("au")) {
				b2cPage.Warranty_input.clear();
				b2cPage.Warranty_input.sendKeys(rightNumber);
				b2cPage.Warranty_continue.click();
			}else if(Store.toLowerCase().equals("us")){
				b2cPage.Warranty_input.clear();
				b2cPage.Warranty_input.sendKeys(rightNumber);
				b2cPage.Warranty_continue.click();
			}else if(Store.toLowerCase().equals("ca") || Store.toLowerCase().equals("jp")){
				b2cPage.Warranty_buttoncajp.click();
				Common.sleep(2000);
				b2cPage.Warranty_serialNumber.clear();
				b2cPage.Warranty_serialNumber.sendKeys(rightNumber);
				b2cPage.Warranty_continue.click();
			}
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Warranty_serialNumberLabel, 5));
			Dailylog.logInfoDB(4, "input rightnumber", Store, testName);
			
			//step5 Warranty_business
			Common.sleep(3000);
			driver.get(homepageUrl+url);
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Warranty_business, 5)) {
				b2cPage.Warranty_business.click();
			}
			driver.get(homepageUrl+url);
			if(Common.checkElementExists(driver, b2cPage.Warranty_business2, 5)) {
				b2cPage.Warranty_business2.click();
			}
			Dailylog.logInfoDB(5, "Warranty_business", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	public void auAndus() {
		Common.sleep(3000);
		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Warranty_numberlink, 5));
		if(Common.checkElementExists(driver, b2cPage.Warranty_numberlink, 5)) {
			b2cPage.Warranty_numberlink.click();
			if(Store.toLowerCase().equals("us")) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Warranty_popup, 5));
			}else if(Store.toLowerCase().equals("au")) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Warranty_numberlinkresult, 5));
			}
			Dailylog.logInfoDB(3, "input errorNumber", Store, testName);
		}
	}
}
