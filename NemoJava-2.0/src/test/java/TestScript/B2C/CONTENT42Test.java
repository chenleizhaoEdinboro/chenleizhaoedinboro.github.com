package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class CONTENT42Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	String patnumber;
	private B2BPage b2bPage;
	String sinaUrl = "https://search.sina.com.cn";
	String baiduUrl = "https://www.baidu.com/";
	String regon = "^https?://.*\\.baidu\\..*$";
	String vbaidu = "https://v.baidu.com/";
	String url360 = "https://ext.se.360.cn/";
	String regonSina = "^https?://.*\\.sina\\..*$";
	String entSina = "https://ent.sina.com.cn/ku";
	String entStar = "https://ent.sina.com.cn/star/";
	String taobao = "https://www.taobao.com/";
	String catches = "chrome://settings/clearBrowserData";
	public CONTENT42Test(String store,String patnumber) {
		this.Store = store;
		this.testName = "CONTENT-42";
		this.patnumber = patnumber;
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p1", "b2c"})
	public void CONTENT42(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			b2bPage = new B2BPage(driver);
			
			//1.Go to HMC,B2Ccommerce->B2C unitEnter Identification code,click search button,
			editHmcSiteattribute(patnumber,hmcPage.RegistrationGatekeeperToggleYES,hmcPage.ReferralUrlGateKeeperToggleNO);
			Dailylog.logInfoDB(1, "Go to HMC,B2Ccommerce->B2C unitEnter Identification code,click search button", Store, testName);
			
			//2.In the Chrome Browser,open the  url,change title news href of one <a> 
			changeHref(sinaUrl,patnumber);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(2, "In the Chrome Browser,open the  url,change title news href of one <a>", Store, testName);
			
			//3.clear cache
			clearCache();
			Dailylog.logInfoDB(3, "clear cache", Store, testName);
			
			//4.Go to HMC,B2Ccommerce->B2C unit
			editHmcSiteattribute(patnumber,hmcPage.RegistrationGatekeeperToggleYES,hmcPage.ReferralUrlGateKeeperToggleYES);
			loginHmc(patnumber);
			intoReferralURLGatekeeper(sinaUrl);
			Dailylog.logInfoDB(4, "Go to HMC,B2Ccommerce->B2C unit", Store, testName);
			
			//5.In the Chrome Browser,open the url,change title news href of one <a> to url lenovo site,click title news
			changeHref(sinaUrl,patnumber);
			Assert.assertTrue(!Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(5, "In the Chrome Browser,open the url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//6.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(6, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//7.In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news
			changeHref(baiduUrl,patnumber);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(7, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//8.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(8, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//9.Go to HMC,B2Ccommerce->B2C unit
			editHmcSiteattribute(patnumber,hmcPage.RegistrationGatekeeperToggleYES,hmcPage.ReferralUrlGateKeeperToggleYES);
			loginHmc(patnumber);
			intoReferralURLGatekeeper(regon);
			Dailylog.logInfoDB(9, "Go to HMC,B2Ccommerce->B2C unit", Store, testName);
			
			//10.In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news
			changeHref(sinaUrl,patnumber);
			Assert.assertTrue(!Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(10, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//11.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(11, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//12.In the Chrome Browser,open the  url,change title finance href of one <a> to url lenovo site,click title finance
			changeHref(baiduUrl,patnumber);
			Assert.assertTrue(!Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(12, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//13.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(13, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//14.In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news
			changeHref(vbaidu,patnumber);
			Assert.assertTrue(!Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(14, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//15.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(15, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//16.in the Chrome Browser,open the  url,change title Community href of one <a> to url lenovo site,click title Community
			changeHref(url360,patnumber);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(16, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//17.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(17, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//18.Go to HMC,B2Ccommerce->B2C unit
			editHmcSiteattribute(patnumber,hmcPage.RegistrationGatekeeperToggleYES,hmcPage.ReferralUrlGateKeeperToggleYES);
			loginHmc(patnumber);
			intoReferralURLGatekeeper(regonSina);
			Dailylog.logInfoDB(18, "Go to HMC,B2Ccommerce->B2C unit", Store, testName);
			
			//19.In the Chrome Browser,open the  url,change title film href of one <a> to url lenovo site,click title film
			changeHref(entSina,patnumber);
			Assert.assertTrue(!Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(19, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//20.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(20, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//21.In the Chrome Browser,open the  url,change title film href of one <a> to url lenovo site,click title film
			changeHref(entStar,patnumber);
			Assert.assertTrue(!Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(21, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
			//22.Clear  browser caching in the Chrome browser.
			clearCache();
			Dailylog.logInfoDB(22, "Clear  browser caching in the Chrome browser.", Store, testName);
			
			//23.In the Chrome Browser,open the  url,change title new dress href of one <a> to url lenovo site,click title new dress
			changeHref(taobao,patnumber);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@class='button-called-out signInForm-submitButton g-recaptcha']")), 10));
			Dailylog.logInfoDB(23, "In the Chrome Browser,open the  url,change title news href of one <a> to url lenovo site,click title news", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


	private void loginHmc(String patnumberUnit) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(patnumberUnit);
		hmcPage.B2BUnit_SearchButton.click();
		Common.sleep(3000);
		Common.doubleClick(driver, driver.findElement(By.xpath("//table[@id='Content/McSearchListConfigurable[B2CUnit]_outertable']//div//tbody//tr[@id='Content/NemoOrganizerListEntry[aupremium_unit]_tr']")));
		hmcPage.B2CUnit_SiteAttributeTab.click();
	}

	private void intoReferralURLGatekeeper(String sinaUrl) throws InterruptedException {
		Common.scrollToElement(driver, driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicRegistrationGatekeeperToggle]]_spantrue']")));
		if(!Common.checkElementExists(driver, hmcPage.ReferralURLGatekeeper, 10)) {
			Common.rightClick(driver, hmcPage.tableValue);
			hmcPage.Selectall.click();
			Common.rightClick(driver, hmcPage.tableValue);
			hmcPage.Remove.click();
		}
		Common.rightClick(driver, hmcPage.ReferralURLGatekeeper);
		hmcPage.Createnew.click();
		hmcPage.ReferralURLGatekeeperinput.clear();
		hmcPage.ReferralURLGatekeeperinput.sendKeys(sinaUrl);
		hmcPage.HMC_Save.click();
	}

	private void clearCache() throws InterruptedException {
		driver.get(catches);
        Common.sleep(5000);
        driver.switchTo().activeElement();
        driver.findElement(By.cssSelector("* /deep/ #clearBrowsingDataConfirm")).click();
        Common.sleep(3000);
        driver.get(catches);
        Common.sleep(5000);
        driver.switchTo().activeElement();
        driver.findElement(By.cssSelector("* /deep/ #clearBrowsingDataDialog :nth-child(2) .iron-selected")).click();
        driver.findElement(By.cssSelector("* /deep/ #clearBrowsingDataConfirm")).click();
	}

	private void changeHref(String sinaUrl, String patnumber) {
		driver.get(sinaUrl); 
		Common.sleep(3000);
		if(Store.equals("AU")) {
			String jsString = "document.getElementById('tab02').setAttribute('href','https://pre-c-hybris.lenovo.com/au/en/aupremium'); ";
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript(jsString);
		}
		if(Store.equals("NZ")) {
			String jsString = "document.getElementById('tab02').setAttribute('href','https://pre-c-hybris.lenovo.com/nz/en/nzaffinity'); ";
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript(jsString);
		}
		driver.findElement(By.xpath("//a[@id='tab02']")).click();
	}

	private void editHmcSiteattribute(String patnumberUnit, WebElement registrationGatekeeperToggleYES, WebElement referralUrlGateKeeperToggleNO) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(patnumberUnit);
		hmcPage.B2BUnit_SearchButton.click();
		Common.sleep(3000);
		Common.doubleClick(driver, driver.findElement(By.xpath("//table[@id='Content/McSearchListConfigurable[B2CUnit]_outertable']//div//tbody//tr[@id='Content/NemoOrganizerListEntry[aupremium_unit]_tr']")));
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.scrollToElement(driver, driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicRegistrationGatekeeperToggle]]_spantrue']")));
		registrationGatekeeperToggleYES.click();
		referralUrlGateKeeperToggleNO.click();
		hmcPage.HMC_Save.click();
		Common.sleep(3000);
		hmcPage.HMC_Logout.click();
	}


	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());		
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
