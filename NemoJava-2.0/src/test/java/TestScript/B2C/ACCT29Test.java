package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class ACCT29Test extends SuperTestClass {

	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public String cartUrl;
	public String myAccountUrl;
	public String prodNumber;
	public String alcURL="https://account.lenovo.com/au/en";
	public String alcUser = "aubuy@yopmail.com";
	public String alcPwd = "1q2w3e4r";
	
	
	public ACCT29Test(String store) {
		this.Store = store;
		this.testName = "ACCT-29";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true)
	public void ACCT29(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//1,Go to HMC and Set eService SSO Toggle
			Dailylog.logInfoDB(1, "Go to HMC and Set eService SSO Toggle", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			HMCCommon.searchB2CUnit(hmcPage, testData);
			
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.ssoEnabled') and contains(@id,'true')]")).click();
			hmcPage.Common_SaveButton.click();
			
			hmcPage.Home_closeSession.click();
			
			//2, *option1:test the two websiteS not login*:
			//open account.lenovo.com and one hybris website in the same browser
			Dailylog.logInfoDB(2, "option1:test the two websiteS not login *: option1:test the two websiteS not login*:", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('"+ alcURL + "')");
			
			Dailylog.logInfoDB(2, "hybris url is :" + driver.getCurrentUrl(), Store, testName);
			Assert.assertTrue(driver.getCurrentUrl().endsWith("login"), "after refresh , hybris is not in login page");
			
			Common.switchToWindow(driver, 1);
			
			driver.get(alcURL + "/myorders");
			Dailylog.logInfoDB(2, "alc url is :" + driver.getCurrentUrl(), Store, testName);
			Assert.assertTrue(driver.getCurrentUrl().contains("signin"), "after refresh , alc is not in login page");
			
			//3, *option2：test one part login*:
			//1)login account.lenovo.com with LID 
			Dailylog.logInfoDB(3, "*option2：test one part login*: login account.lenovo.com with LID ", Store, testName);

			driver.get(alcURL);
			
			loginALC();
			
			Thread.sleep(10000);

			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//div[@id='header-desktop']//li[@class='account-logout']/a")), "fail login alc");
			
			
			//4, 2)switch to hybris website, click refresh
			Dailylog.logInfoDB(4, "switch to hybris website, click refresh", Store, testName);
			
			Common.switchToWindow(driver, 0);
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
			Assert.assertTrue(!driver.getCurrentUrl().endsWith("login") && Common.isElementExist(driver, By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")), "after refresh hybris, hybris is not logined");
			
			
			//5, 3)in hybris website,add one item to cart then drop an order
			Dailylog.logInfoDB(5, "in hybris website,add one item to cart then drop an order", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			
			String orderNumber = B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData, Store);
			Dailylog.logInfoDB(5, "order number is :" + orderNumber, Store, testName);
			
			//6, 4)click "my account"
			Dailylog.logInfoDB(6, "click my account", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
		
			//7, hybris and account.lenovo.com both keep sign on in the same browser
			Dailylog.logInfoDB(7, "hybris and account.lenovo.com both keep sign on in the same browser", Store, testName);
			
			Assert.assertTrue(!driver.getCurrentUrl().endsWith("login") && Common.isElementExist(driver, By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")), "after refresh hybris, hybris is not logined");
			
			Common.switchToWindow(driver, 1);
			driver.get(alcURL + "/myorders");
			
			Assert.assertTrue(!driver.getCurrentUrl().contains("signin") && Common.isElementExist(driver, By.xpath("//div[@id='header-desktop']//li[@class='account-logout']/a")), "after refresh , alc is not logined");
			
			//8, go to hybris site and click  log out
			Dailylog.logInfoDB(8, "go to hybris site and click  log out", Store, testName);
			
			Common.switchToWindow(driver, 0);
			
			Common.javascriptClick(driver, driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")));
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			Thread.sleep(4000);
			
			Assert.assertTrue(driver.getCurrentUrl().contains("login"), "after refresh hybris, hybris is not logined");
			
			//9, switch to account.lenovo.com then click refresh
			Dailylog.logInfoDB(9, "switch to account.lenovo.com then click refresh", Store, testName);
			Common.switchToWindow(driver, 1);
			
			driver.navigate().refresh();
			driver.get(alcURL + "/myorders");
			
			Assert.assertTrue(driver.getCurrentUrl().contains("signin"), "when hybris logout , refresh alc, alc does not log out");


		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@Test(priority = 1, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void rollBack(){
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		
		HMCCommon.searchB2CUnit(hmcPage, testData);
		
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		
		driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.ssoEnabled') and contains(@id,'false')]")).click();
		hmcPage.Common_SaveButton.click();
		
		hmcPage.Home_closeSession.click();

	}
	
	public void loginALC(){
		
		driver.findElement(By.xpath("//input[@name='UserID']")).clear();
		driver.findElement(By.xpath("//input[@name='UserID']")).sendKeys(alcUser);
		
		driver.findElement(By.xpath("//input[@name='Password']")).clear();
		driver.findElement(By.xpath("//input[@name='Password']")).sendKeys(alcPwd);
		
		driver.findElement(By.xpath("//div[contains(@class,'signin')]")).click();
		
	}

	
	
}

