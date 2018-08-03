package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class NA17667Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String ChequeName = "Check Auto";
	public String laptopPageURL;
	public String OrderNumber;
	public String DisplayName = "Pay By Check Auto";	
	
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	public NA17667Test(String store) {
		this.Store = store;
		this.testName = "NA-17667";
	}	

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA17667(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//step~1 : Login into B2C with Telesales Account
			Dailylog.logInfoDB(1, "Login into B2C with Telesales Account", Store, testName);
			
			driver.get(loginUrl);			
			// close login page pop-up			
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(2500);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(2500);
			
			B2CCommon.closeHomePagePopUP(driver);
			
			Dailylog.logInfoDB(1,"Logged in into B2C as an ASM",Store,testName);			
			
			//step 3: click My Account
			Dailylog.logInfoDB(3, "click My Account", Store, testName);
			Thread.sleep(5000);
			
			//step 4: click start Assisted Service Session link
			Dailylog.logInfoDB(4, "click start Assisted Service Session link", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(7000);
			B2CCommon.closeHomePagePopUP(driver);		
			
			//step~6
			Dailylog.logInfoDB(6, "click search button and check the results", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='showCustomerSearch']/span")).click();
			Common.sleep(5000);
			
			b2cPage.AdvanceCustomerSearch_SearchButton.click();
			Common.sleep(10000);
			
			List<WebElement> customerList = driver.findElements(By.xpath("//tr[@class='b2cCustomer']"));
			
			Assert.assertTrue(customerList.size() >= 1);
			
			String storeName = driver.findElement(By.xpath("(//tr[@class='b2cCustomer'])[1]/td[5]")).getText().toString();
			
			Assert.assertTrue(storeName.equals(testData.B2C.getStore()));
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);

		}
	}

}