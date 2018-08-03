package TestScript.B2C;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA17643Test extends SuperTestClass {
	B2CPage b2cPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	

	public NA17643Test(String store) {
		this.Store = store;
		this.testName = "NA-17643";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA17643(ITestContext ctx) {
		try {
			this.prepareTest();
			
			String pathName = "D:\\NA17643\\"+this.Store+"\\";
			changeChromeDefDownFolder(pathName);
			
			
			b2cPage = new B2CPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			// step 1,login store front with telesales user
			Dailylog.logInfoDB(1, "login store front with telesales user", Store, testName);
			
			
			driver.get(homePageUrl);
			Thread.sleep(5000);
			B2CCommon.closeHomePagePopUP(driver);
			
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver.get(loginUrl);
			
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			Thread.sleep(10000);
			
			Dailylog.logInfo("Step 1 successfully");
			
			driver.get(homePageUrl);
			Thread.sleep(5000);
			B2CCommon.closeHomePagePopUP(driver);
			
			//2, click my account 
			Dailylog.logInfoDB(2, "click my account ", Store, testName);
			
			driver.get(homePageUrl+ "/my-account");

			Assert.assertTrue(b2cPage.myAccountPage_startAssistedServiceSession.isDisplayed());
			
			Dailylog.logInfo("step 2 successsfully");
			
			//3, click Start Assisted Service Session link
			Dailylog.logInfoDB(3, "click Start Assisted Service Session link", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(7000);
			B2CCommon.closeHomePagePopUP(driver);
			
			Thread.sleep(5000);
			
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='_asm']")).isDisplayed());
			Dailylog.logInfo("Step 3 successfully");
			
			//4, click Order/Quote Report button
			Dailylog.logInfoDB(4, "click Order/Quote Report button", Store, testName);
			
			b2cPage.ASM_QuoteOrderReportButton.click();
			Thread.sleep(6000);
			
			Assert.assertTrue(b2cPage.orderQuote_storeType.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_appliedCoupon.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_TransactionType.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_RepID.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_custoemrID.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_startDate.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_endDate.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_couPon.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_storeID.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_transactionID.isDisplayed());
			Assert.assertTrue(b2cPage.orderQuote_pageLimit.isDisplayed());
			
			Dailylog.logInfo("step 4 successfully");
			
			//5, click search button
			Dailylog.logInfoDB(5, "click search button", Store, testName);
			
			b2cPage.searchButton.click();
			Thread.sleep(20000);
			
			String datePlaced = driver.findElement(By.xpath("//*[@id='orderReportTableBody']/tr[1]/td[1]")).getText().toString().trim();
			
			b2cPage.orderQuote_startDate.sendKeys(datePlaced);
			b2cPage.orderQuote_endDate.sendKeys(datePlaced);
			Thread.sleep(10000);
			
			b2cPage.searchButton.click();
			
			Thread.sleep(10000);
			
			List<WebElement> resultList = driver.findElements(By.xpath(".//*[@id='orderReportTableBody']/tr"));
			
			Assert.assertTrue(resultList.size() >= 1);
			
			Dailylog.logInfo("step 5 successfully");
			
			
			//6, click Export button
			Dailylog.logInfoDB(6, "click Export button", Store, testName);
			
			
			File file = new File(pathName);
			
			if(!file.exists() && !file.isDirectory()){
				file.mkdirs();
			}
			
			if(file.exists()){
				File[] files = file.listFiles();
				
				for(File file1 : files){
					file1.delete();
				}
			}
			
			
			driver.findElement(By.xpath("//button[text()='Export']")).click();
			Thread.sleep(6000);
			
			File file1 = new File(pathName);
			
			File[] files = file1.listFiles();
			
			Assert.assertTrue(files.length == 1);
			
			Dailylog.logInfo("step 6 successfully");
		
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void changeChromeDefDownFolder(String downloadFilepath) throws IOException {
		driver.close();
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
}