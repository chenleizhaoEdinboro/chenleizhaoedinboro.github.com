package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HACPage;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA18233Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HACPage hacPage;
	public B2BPage b2bPage;
	String HacLoginID = "autoHMCMationTest";
	String HacLoginPassword = "Lenovo1234";
	String HACLoginURL = "http://LIeCommerce:M0C0v0n3L!@admin-pre-c-hybris.lenovo.com/hac/login.jsp";
	String B2BAUURL = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/le/adobe_global/au/en/1213654197/login";

	public NA18233Test(String store) {
		this.Store = store;
		this.testName = "NA-18233";
	}
	private void HACLogin(){
		Common.sendFieldValue(hacPage.HacLogin_UserName, HacLoginID);
		Common.sendFieldValue(hacPage.HacLogin_UserPassword, HacLoginPassword);
		hacPage.HacLogin_LoginButton.click();
	}
	private void setTimeoutValue(String data){
		Common.sendFieldValue(hacPage.HacCinfiguration_TimeOutValue, data);
		Common.sleep(3500);
		hacPage.HacCinfiguration_ApplyIcon.click();		
		Common.sleep(4500);
		//Common.waitElementClickable(driver, hacPage.HacCinfiguration_ApplyAllBtn, 15);
		hacPage.HacCinfiguration_ApplyAllBtn.click();
		Dailylog.logInfoDB(6, "Timeout value changed to :"+data+" seconds", Store, testName);
	}


	@Test(priority=0)
	public void NA18233(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hacPage = new HACPage(driver);
			b2bPage = new B2BPage(driver);

			//Step~1 : login HAC and check session timeout
			//login into Hac 
			driver.get(HACLoginURL);
			Common.sleep(3000);
			HACLogin();
			Common.sleep(2000);
			hacPage.HacHome_Monitoring.click();
			Dailylog.logInfoDB(1, "Clicked on Monitoring", Store, testName);
			hacPage.HacMonitoring_Session.click();
			Common.sleep(4000);
			String Monitor_SessionTimeOut = hacPage.HacMonitorSession_SessionTimeout.getText();
			Dailylog.logInfoDB(1, "Session Timeout is :" +Monitor_SessionTimeOut, Store, testName);
			String[] OnlyNumeralValue = Monitor_SessionTimeOut.split(" ");
			Assert.assertEquals(OnlyNumeralValue[0], "1800", "Timeout value from page should be :"+OnlyNumeralValue[0]);
			Common.sleep(2500);
			//step~2 : Platform-->Configuration
			hacPage.HacHome_PlatformBtn.click();
			hacPage.HacPlatform_ConfigurationBtn.click();	
			Dailylog.logInfoDB(2, "Clicked on Configuration button..", Store, testName);
			Common.waitElementVisible(driver,hacPage.HacCinfiguration_SearchInput);
			Common.sendFieldValue(hacPage.HacCinfiguration_SearchInput, "default.session.timeout");			
			Common.waitElementVisible(driver,hacPage.HacCinfiguration_TimeOutValue);
			String Plaform_SessionTimeOut = hacPage.HacCinfiguration_TimeOutValue.getAttribute("value");
			Dailylog.logInfoDB(2, "Session Timeout is :" +Plaform_SessionTimeOut, Store, testName);			
			Assert.assertEquals(Plaform_SessionTimeOut, "1800", "Timeout value from page should be :"+Plaform_SessionTimeOut);
			Common.sleep(2500);
			hacPage.HacHome_Logout.click();
			//Step~3 : Login Into B2C
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl() + "/login");
			Common.sleep(2500);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(3,"Logged in into B2C as a Buyer",Store,testName);
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("my-account"));
			//switch to other application for 1860 seconds			
			Common.sleep(1200000);			
			//refresh page to verify that page has not timed out
			driver.navigate().refresh();
			Common.sleep(2000);	
			Assert.assertTrue(driver.getCurrentUrl().contains("my-account"));
			//Step~4 : switch to other application for 1860 seconds			
			Common.sleep(1860000);			
			//refresh page to check timeout message
			driver.navigate().refresh();
			Common.sleep(2000);	
			//step~5
			String timeout_Mesg = b2cPage.Login_TimeoutMesg.getText();
			Assert.assertTrue(driver.getCurrentUrl().contains("login"), timeout_Mesg);
			driver.findElement(By.xpath("//div[@id='accErrorMsgs']/ul/li")).isDisplayed();			
			//step~6 : Login into HAC and change timeout value to 5 minute
			driver.manage().deleteAllCookies();
			driver.get(HACLoginURL);
			Common.sleep(3000);
			HACLogin();
			Common.sleep(2000);
			Dailylog.logInfoDB(6, "Entered into HAC again", Store, testName);
			hacPage.HacHome_PlatformBtn.click();
			hacPage.HacPlatform_ConfigurationBtn.click();
			Common.sleep(1500);					
			Common.waitElementVisible(driver,hacPage.HacCinfiguration_SearchInput);
			Common.sendFieldValue(hacPage.HacCinfiguration_SearchInput, "default.session.timeout");			
			Common.waitElementVisible(driver,hacPage.HacCinfiguration_TimeOutValue);
			setTimeoutValue("60");
			hacPage.HacHome_Logout.click();		
			//step~7 : Login into B2B
			driver.get(B2BAUURL);
			Common.sleep(3000);
			Dailylog.logInfoDB(7, "Entered into B2B again", Store, testName);
			B2BCommon.Login(b2bPage, "aubuyer@yopmail.com", "1q2w3e4r");
			//step~8
			//switch to other application for 360 second
			//Common.sleep(360000);
			Common.sleep(120000);
			//refresh page to check timeout message
			driver.navigate().refresh();
			Common.sleep(2000);				
			String Timeout_Mesg = b2cPage.Login_TimeoutMesg.getText();
			Assert.assertTrue(driver.getCurrentUrl().contains("login"), Timeout_Mesg);
			driver.findElement(By.xpath("//div[@id='accErrorMsgs']/ul/li")).isDisplayed();
			Dailylog.logInfoDB(8, "Moving to Roll-back", Store, testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	// Step 9 Roll back by deleting step1 payment profile
	@Test(priority=1,alwaysRun = true)
	public void rollback() throws InterruptedException {
		WebDriver driver1;
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver1 = new ChromeDriver();
		hacPage = new HACPage(driver1);
		Dailylog.logInfoDB(9, "Roll-back", Store, testName);
		driver1.manage().deleteAllCookies();
		driver1.get(HACLoginURL);
		Common.sleep(3000);
		HACLogin();
		/*Common.sendFieldValue(hacPage.HacLogin_UserName, HacLoginID);
				Common.sendFieldValue(hacPage.HacLogin_UserPassword, HacLoginPassword);
				hacPage.HacLogin_LoginButton.click();*/
		Common.sleep(2000);
		hacPage.HacHome_PlatformBtn.click();
		hacPage.HacPlatform_ConfigurationBtn.click();			
		Common.waitElementVisible(driver1,hacPage.HacCinfiguration_SearchInput);
		Common.sendFieldValue(hacPage.HacCinfiguration_SearchInput, "default.session.timeout");			
		Common.waitElementVisible(driver1,hacPage.HacCinfiguration_TimeOutValue);
		String Plaform_SessionTimeOut = hacPage.HacCinfiguration_TimeOutValue.getAttribute("value");
		System.out.println("Timeout value from field :"+Plaform_SessionTimeOut);
		if (Integer.parseInt(Plaform_SessionTimeOut) == 1800) {
			Dailylog.logInfoDB(9, "no need to change value", Store, testName);
		}else{
			Common.sendFieldValue(hacPage.HacCinfiguration_TimeOutValue, "1800");
			Common.sleep(3500);
			hacPage.HacCinfiguration_ApplyIcon.click();	
			Dailylog.logInfoDB(9, "Clicked on Apply Icon", Store, testName);
			Common.sleep(4500);
			Common.waitElementClickable(driver1, hacPage.HacCinfiguration_ApplyAllBtn, 15);
			hacPage.HacCinfiguration_ApplyAllBtn.click();
			Dailylog.logInfoDB(6, "Timeout value changed to :"+"1800"+" seconds", Store, testName);
			hacPage.HacHome_Logout.click();
		}
	}

}