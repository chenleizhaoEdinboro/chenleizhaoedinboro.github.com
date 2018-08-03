package TestScript.B2C;

import org.openqa.selenium.JavascriptExecutor;
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

public class NA28073Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;

	public NA28073Test(String store) {
		this.Store = store;
		this.testName = "NA-28073";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28073(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getloginPageUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			String customiseButton = "(//ol[contains(@class,'product')]/li/div//div[contains(@class,'footer')]/button[contains(@id,'Cart')])[1]";

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL);
			Common.sleep(2000);
			B2CCommon.DoubleLogin(driver, testData, b2cPage, b2cHomeURL, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			B2CCommon.closeHomePagePopUP(driver);
			Dailylog.logInfoDB(1, "login b2c", Store, testName);

		    //===========step:2 Start Assisted Service Session"=======//
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			Common.sleep(2000);
			b2cPage.Myaccount_telesale.click();
			Dailylog.logInfoDB(2, "Start Assisted Service Session", Store, testName);
			
			//=========== Step:3 Add another product ===========//
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Common.sleep(1500);
			String laptopPageURL = driver.getCurrentUrl();
			B2CCommon.addToCartB2C(1, driver, laptopPageURL, customiseButton);
			Dailylog.logInfoDB(3, "Added  product into cart", Store, testName);

			//=========== Step:4 Go to cart page and do request quote ===========//
			Common.sleep(3000);
			driver.get(b2cHomeURL);
			B2CCommon.DoubleLogin(driver, testData, b2cPage, b2cHomeURL, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			b2cPage.HomePage_CartIcon.click();
			Common.scrollToElement(driver, b2cPage.Quote_requestBtn);
			b2cPage.Quote_requestBtn.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(4, "Request Quote button is clicked.", Store, testName);
			if(Common.checkElementExists(driver, b2cPage.Override_RepID, 5)) {
				b2cPage.Override_RepID.sendKeys(testData.B2C.getRepID());
				Common.sleep(3000);
				b2cPage.Override_SubmitQuote.click();
				Common.sleep(3000);
				String quoteNum = b2cPage.SavedQuote_QuoteNum.getText();
				Dailylog.logInfoDB(4, "quoteNum get", Store, testName);
			}
			
			//=========== Step:5 Go to HMC ===========//
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			hmcPage.home_userTab.click();
			hmcPage.userTab_customerTab.click();
			hmcPage.customer_customerIDTextBox.clear();
			hmcPage.customer_customerIDTextBox.sendKeys(testData.B2C.getTelesalesAccount());
			hmcPage.customer_customerSearchButtonn.click();
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.customer_searchedResultImage);
			hmcPage.customerSearch_administrationTab.click();
			Assert.assertTrue(Common.checkElementExists(driver, hmcPage.repId, 5));
			Dailylog.logInfoDB(5, "Logged into HMC", Store, testName);

			//=========== Step:6 Go to b2cwebsite  print ===========//
			Common.switchToWindow(driver, 0);
			b2cPage.Myaccount_print.click();
			Dailylog.logInfoDB(6, "Go to b2cwebsite  print", Store, testName);
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

}
