package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class ACCT33Test extends SuperTestClass {
	
	private B2CPage b2cPage;
	private MailPage mailPage;
	private HMCPage hmcPage;
	private String newPassword = "1q2w3e4r5t";	
	private String alcURL="https://account.lenovo.com/au/en";
	
	
	public ACCT33Test(String store) {
		this.Store = store;
		this.testName = "ACCT-33";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true)
	public void ACCT33(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			
			//1, Go to HMC and Set eService SSO Toggle
			Dailylog.logInfoDB(1, "Go to HMC and Set eService SSO Toggle", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			// set eService SSO Toggle : yes 
			hmcPage.eServiceToggle.click();
			hmcPage.Common_SaveButton.click();
			hmcPage.Home_closeSession.click();
			Thread.sleep(10000);
			
			// first registration user
			String email = Common.getDateTimeString();
			B2CCommon.NewUserRegistration(Store, driver, b2cPage, testData, email, Browser);

			//2, navigate the url such as https://tun-c-hybris.lenovo.com/au/en/login
			Dailylog.logInfoDB(2, "navigate to login url", Store, testName);
			
			driver.manage().deleteAllCookies();
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			//3, click "Forget your password?"
			Dailylog.logInfoDB(3, "click Forget your password?", Store, testName);
			
			b2cPage.LoginPage_forgotPassword.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("pw/request"),"User should be on forgot password page.");
		
			//4,In the email address , fill in the user account that you forgotten the password
			//and then click "Submit"
			Dailylog.logInfoDB(4, "In the email address , fill in the user account that you forgotten the password and then click Submit", Store, testName);
			
			b2cPage.ForgotPassword_emailIDTxtBox.clear();
			b2cPage.ForgotPassword_emailIDTxtBox.sendKeys(email + "@sharklasers.com");
			b2cPage.ForgotPassword_submitButton.click();
			
			//5, Check whether the user have receive the email
			//6, Click Reset password link in Email
			Dailylog.logInfoDB(5, "Check whether the user have receive the email", Store, testName);
			Dailylog.logInfoDB(6, "Click Reset password link in Email", Store, testName);
			
			EMailCommon.createEmail(driver, mailPage, email);			
			Common.sleep(2000);
			String  Currenthandle= driver.getWindowHandle();
			Check_Content(0);			
			for (String handle : driver.getWindowHandles()) {
				if(handle.equals(Currenthandle))
					continue;
				driver.switchTo().window(handle);
			}
			Common.sleep(2000);
			
			driver.manage().window().maximize();
			
			Common.sleep(2000);
			
			String resetPageURL = driver.getCurrentUrl();
			
			Assert.assertTrue(resetPageURL.contains("ResetPass"),"Password reset is successful.");

			//7, Fill in the info that required and update the password
			Dailylog.logInfoDB(7, "Fill in the info that required and update the password", Store, testName);
			
			b2cPage.ResetPassword_newPassword.clear();
			b2cPage.ResetPassword_newPassword.sendKeys(newPassword);
			b2cPage.ResetPassword_confirmNewPassword.clear();
			b2cPage.ResetPassword_confirmNewPassword.sendKeys(newPassword);
			b2cPage.ForgotPassword_submitButton.click();
			
			Common.sleep(4000);
			String passwordResetSuccMsg = b2cPage.ResetPassword_PasswordResetSuccMsg.getText();
			Dailylog.logInfoDB(6, passwordResetSuccMsg, Store, testName);
			Assert.assertTrue(b2cPage.ResetPassword_PasswordResetSuccMsg.isDisplayed(),"Password reset success msg should be displayed");
			
			//8, go to the login page, login with the new password
			Dailylog.logInfoDB(8, "go to the login page, login with the new password", Store, testName);
			
			b2cPage.ResetPassword_ContinueButton.click();
			Common.sleep(3000);
			B2CCommon.login(b2cPage, email + "@sharklasers.com", newPassword);	
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(3000);
			String urlAfterLoginWithNewPass = driver.getCurrentUrl();
			Dailylog.logInfoDB(6, "urlAfterLoginWithNewPass is :" + urlAfterLoginWithNewPass, Store, testName);
//			Assert.assertTrue(urlAfterLoginWithNewPass.contains("my-account"));
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//ul[@class='menu general_Menu']//*[@id='myAccount']//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'logout')]/div")), "failed to login with new password");

			//9, open https://uataccount.lenovo.com in the same browser 
			Dailylog.logInfoDB(9, "open alc in the same browser", Store, testName);
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('"+ alcURL + "')");
			
			Common.switchToWindow(driver, 1);
			
			driver.get(alcURL + "/myorders");
			Assert.assertTrue(!driver.getCurrentUrl().contains("signin") && Common.isElementExist(driver, By.xpath("//div[@id='header-desktop']//li[@class='account-logout']/a")), "after refresh , alc is not logined");

			driver.close();
			driver.switchTo().window(Currenthandle);
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	private void Check_Content(int i){
		Dailylog.logInfoDB(5,"Checking mail content...time="+i,Store,testName);
		if (Common.isElementExist(driver,By.xpath("(//td[contains(.,'Forgot your password')])")) == true || Common.isElementExist(driver,By.xpath("(//td[contains(.,'パスワードを忘れた場合')])")) == true) {
			Dailylog.logInfoDB(4, "Forgot Password Email is present.", Store, testName);
			if(Store.equals("JP")){
				driver.findElement(By.xpath("(//td[contains(.,'パスワードを忘れた場合')])")).click();
			}else{
				driver.findElement(By.xpath("(//td[contains(.,'Forgot your password')])")).click();
			}
			Common.sleep(2000);	
			//=========== Step:5 Click Password Reset Link ===========//
			if(Store.equals("JP")){
				driver.findElement(By.xpath("//tr/td/a[contains(.,'新しいパスワードを設定するには、ここをクリックしてください')]")).click();
				
			}else{
				driver.findElement(By.xpath("//tr/td/a[contains(.,'click here')]")).click();
			}
			Dailylog.logInfoDB(5, "Reset Password Link is clicked from the email.", Store, testName);
			Common.sleep(2000);	
			mailPage.Mail_backToInbox.click();			
		} else if(i < 10){
			Dailylog.logInfoDB(5,"Mail is not present...check later",Store,testName);
			Check_Content(++i);
		}
	}
}
