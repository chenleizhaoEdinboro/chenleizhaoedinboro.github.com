package TestScript.B2C;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27368Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	public String myAccountUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	public NA27368Test(String store) {
		this.Store = store;
		this.testName = "NA-27368";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA27336(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			myAccountUrl = homePageUrl + "/my-account"; 
			
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, login website as  telesales user
			Dailylog.logInfoDB(1, "login website as  telesales user", Store, testName);
			
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());

			//2, Start ASM from my account page by click the link there.
			//3, Assign a customer and start session
			Dailylog.logInfoDB(2, "Start ASM from my account page by click the link there", Store, testName);
			Dailylog.logInfoDB(3, "Assign a customer and start session", Store, testName);
			
			B2CCommon.loginASMAndStartSession(driver, b2cPage, "customer", testData.B2C.getLoginID());
			
			//4, Purchase product and request quote at cart page
			Dailylog.logInfoDB(4, "Purchase product and request quote at cart page", Store, testName);
			driver.get(cartUrl);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			B2CCommon.addPartNumberToCartTele(b2cPage, testData.B2C.getDefaultMTMPN());
			
			Common.sleep(6000);
			b2cPage.RequestQuoteBtn.click();
			Common.sleep(6000);
			
			b2cPage.Quote_RepIDTextBox.clear();
			b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());
			Common.sleep(6000);
			b2cPage.Quote_submitQuoteBtn.click();
			
			String quoteNumber = b2cPage.Quote_quoteNum.getText().toString().trim();
			Dailylog.logInfoDB(4, "quote number is :" + quoteNumber, Store, testName);
			
			
			//5, Go to customer account page, and then go to my saved quote.
			Dailylog.logInfoDB(5, "Go to customer account page, and then go to my saved quote.", Store, testName);
			driver.get(myAccountUrl);
			
			b2cPage.MyAccount_ViewSavedQuote.click();
			
			String expiratedDate = driver.findElement(By.xpath("(//td[@class='quote-product-date'][last()])[1]/p")).getText().toString().trim();
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("(//td[@class='quote-product-date'][last()])[1]/p")) && expiratedDate.length() != 0, "the quote does not show the expire date");
			
			
			//6, Login as a regular customer. 
			Dailylog.logInfoDB(6, "Login as a regular customer", Store, testName);
			
			b2cPage.ASM_signout.click();
			
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			
			//7, Go to customer account page, and then go to my saved quote.
			Dailylog.logInfoDB(7, "Go to customer account page, and then go to my saved quote", Store, testName);
			
			driver.get(myAccountUrl);
			b2cPage.MyAccount_ViewSavedQuote.click();
			
			String expiratedDate1 = driver.findElement(By.xpath("(//td[@class='quote-product-date'][last()])[1]/p")).getText().toString().trim();
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("(//td[@class='quote-product-date'][last()])[1]/p")) && expiratedDate1.length() != 0, "the quote does not show the expire date");
			
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	
	public void goToSiteAttribute(){
		
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(10000);	
	}
	
	
}
	


