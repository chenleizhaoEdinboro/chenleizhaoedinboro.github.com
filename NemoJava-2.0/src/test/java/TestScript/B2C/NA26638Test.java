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
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA26638Test extends SuperTestClass{
	private B2CPage b2cPage;
	private String url = "/accessories-and-monitors/keyboards-and-mice/mice/MOUSE-TPopticalMouse/p/31P7410";

	public NA26638Test(String store) {
		this.Store = store;
		this.testName = "NA-26638";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA26638(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getHomePageUrl();

			//=========== Step:1 Customer Reviews verification ===========//
			driver.get(b2cHomeURL);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			Common.javascriptClick(driver, b2cPage.browseAllCategories);
//			b2cPage.browseAllCategories.click();
			driver.get(b2cHomeURL+"/accessories-and-monitors/chargers-and-batteries/c/chargers-and-batteries");
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PlP_reveiew, 5));
			Common.sleep(3000);
			if(Store.toLowerCase().equals("us") || Store.toLowerCase().equals("jp")) {
				Common.javascriptClick(driver, driver.findElement(By.xpath("//h3[@class='accessoriesList-title qa_product_title']")));
			}else {
				List<WebElement> product_pdp = driver.findElements(By.xpath("//h3[contains(@class,'accessoriesList-title qa_product_title')]"));
				product_pdp.get(product_pdp.size()-1).click();
			}
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_pdp_rate, 5));
			if(Common.checkElementExists(driver, b2cPage.PDP_review, 5)) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PlP_reveiew, 5));
			}
			Dailylog.logInfoDB(1, "login b2c", Store, testName);

		    //===========step:2 Review summary verification"=======//
			if(Common.checkElementExists(driver, b2cPage.PDP_review, 5)) {
				b2cPage.PDP_review.click();
				if(Common.checkElementExists(driver, b2cPage.Write_review, 5)&&Common.checkElementDisplays(driver, b2cPage.Write_review, 5)) {
					Common.sleep(3000);
					b2cPage.Write_review.click();
				}
				
				if(Common.checkElementExists(driver, b2cPage.PDP_review_jumplink, 5)) {
					Common.javascriptClick(driver,b2cPage.PDP_review_jumplink);
					
				}
				Common.sleep(1500);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PDP_popup, 5));
			}
			Dailylog.logInfoDB(2, "Start Assisted Service Session", Store, testName);
			
			 //===========step:3 List of reviews"=======//
			driver.get(b2cHomeURL+url);
			if(Common.checkElementExists(driver, b2cPage.PlP_Count, 5)) {
				Common.mouseHover(driver, b2cPage.PlP_Count);
				Common.sleep(3000);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PlP_redrating, 5));
				Common.javascriptClick(driver, b2cPage.PlP_sort);
				Common.sleep(1500);
				Common.mouseHover(driver, b2cPage.PlP_sort_rating2);
				if(Common.checkElementExists(driver, b2cPage.PlP_sort_starts2, 5)) {
					Common.javascriptClick(driver, b2cPage.PlP_sort_starts2);
					Common.sleep(1500);
					Common.mouseHover(driver, b2cPage.PlP_sort_rating);
					Common.javascriptClick(driver, b2cPage.PlP_sort_starts);
					Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PlP_starts, 5));
					Common.scrollToElement(driver, b2cPage.PlP_write_review);
					Common.javascriptClick(driver, b2cPage.PlP_write_review);
					Assert.assertTrue(Common.checkElementExists(driver, b2cPage.PDP_popup, 5));
				}
			}
			Dailylog.logInfoDB(3, "List of reviews", Store, testName);
			
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

}
