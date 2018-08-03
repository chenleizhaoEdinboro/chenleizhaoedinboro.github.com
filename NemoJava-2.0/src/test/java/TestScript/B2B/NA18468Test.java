package TestScript.B2B;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA18468Test extends SuperTestClass{
	private static B2BPage b2bPage;

	public NA18468Test(String store) {
		this.Store = store;
		this.testName = "NA-18468";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA18468(ITestContext ctx) {
		try{
			this.prepareTest();
			
			String pathName = "D:\\NA18468\\"+this.Store+"\\";
			changeChromeDefDownFolder(pathName);
	
			b2bPage = new B2BPage(driver);
			
			//1, login store front as a telelsales user
			Dailylog.logInfoDB(1, "login store front as a telesales user", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());

			driver.get(testData.B2B.getHomePageUrl());

			//2, Find My Account link from the navigation bar and then click it 
			Dailylog.logInfoDB(2, "Find My Account link from the navigation bar and then click it", Store, testName);
			
			b2bPage.homepage_MyAccount.click();
			
			
			//3, click Start Assisted Service Session link and login ASM
			Dailylog.logInfoDB(3, "click Start Assisted Service Session link and login ASM", Store, testName);
			
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			Thread.sleep(5000);
			
			//4,click Order/Quote Report Button
			Dailylog.logInfoDB(4, "click Order/Quote Report Button", Store, testName);
			
			b2bPage.orderQuoteReportButton.click();
			Thread.sleep(6000);
			
			Assert.assertTrue(b2bPage.orderQuote_storeType.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_appliedCoupon.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_TransactionType.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_RepID.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_custoemrID.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_startDate.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_endDate.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_couPon.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_storeID.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_transactionID.isDisplayed());
			Assert.assertTrue(b2bPage.orderQuote_pageLimit.isDisplayed());
			
			//5, click search button 
			Dailylog.logInfoDB(5, "click search button", Store, testName);
			b2bPage.searchButton.click();
			Thread.sleep(20000);
			
			String datePlaced = driver.findElement(By.xpath("//*[@id='orderReportTableBody']/tr[1]/td[1]")).getText().toString().trim();
			
			b2bPage.orderQuote_startDate.sendKeys(datePlaced);
			b2bPage.orderQuote_endDate.sendKeys(datePlaced);
			Thread.sleep(10000);
			
			b2bPage.searchButton.click();
			Thread.sleep(10000);
			
			List<WebElement> resultList = driver.findElements(By.xpath(".//*[@id='orderReportTableBody']/tr"));
			
			Assert.assertTrue(resultList.size() >= 1);
			
			
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
				
			
		}catch (Throwable e){
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
