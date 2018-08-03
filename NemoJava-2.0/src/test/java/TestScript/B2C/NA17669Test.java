package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA17669Test extends SuperTestClass {
	private B2CPage b2cPage;
	private MailPage mailPage;
	private String newPassword = "1q2w3e4r5t";	
	public NA17669Test(String store) {
		this.Store = store;
		this.testName = "NA-17669";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p1", "b2c", "compatibility"})
	public void NA17669(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			// first registration user
			String email = Common.getDateTimeString();
			B2CCommon.NewUserRegistration(Store, driver, b2cPage, testData, email, Browser);

			//=========== Step:1 Accessing B2C URL ===========//
			driver.manage().deleteAllCookies();
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "Accessing B2C website", Store, testName);
			//=========== Step:2 Click Forgot Password ===========//
			b2cPage.LoginPage_forgotPassword.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("pw/request"),"User should be on forgot password page.");
			Dailylog.logInfoDB(2, "Forgot Password Link is clicked.", Store, testName);

			//=========== Step:3 Fill Email ID and submit ===========//
			b2cPage.ForgotPassword_emailIDTxtBox.clear();
			b2cPage.ForgotPassword_emailIDTxtBox.sendKeys(email + "@sharklasers.com");
			b2cPage.ForgotPassword_submitButton.click();
			Dailylog.logInfoDB(3, "Submit button is clicked after entering email ID.", Store, testName);

			//=========== Step:4 & 5 Check whether user received the email and click Reset Password Link  ===========//
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
			
			Dailylog.logInfoDB(5, "resetPageURL is :" + resetPageURL, Store, testName);
			
			Assert.assertTrue(resetPageURL.contains("ResetPass"),"Password reset is successful.");

			//=========== Step:6 Reset the password and login with new password ===========//
			b2cPage.ResetPassword_newPassword.clear();
			b2cPage.ResetPassword_newPassword.sendKeys(newPassword);
			b2cPage.ResetPassword_confirmNewPassword.clear();
			b2cPage.ResetPassword_confirmNewPassword.sendKeys(newPassword);
			b2cPage.ForgotPassword_submitButton.click();
			Dailylog.logInfoDB(6, "Submit button is clicked with new password.", Store, testName);
			Common.sleep(4000);
			String passwordResetSuccMsg = b2cPage.ResetPassword_PasswordResetSuccMsg.getText();
			Dailylog.logInfoDB(6, passwordResetSuccMsg, Store, testName);
			Assert.assertTrue(b2cPage.ResetPassword_PasswordResetSuccMsg.isDisplayed(),"Password reset success msg should be displayed");
			b2cPage.ResetPassword_ContinueButton.click();
			Common.sleep(3000);
			B2CCommon.login(b2cPage, email + "@sharklasers.com", newPassword);	
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(3000);
			String urlAfterLoginWithNewPass = driver.getCurrentUrl();
			Dailylog.logInfoDB(6, "urlAfterLoginWithNewPass is :" + urlAfterLoginWithNewPass, Store, testName);
//			Assert.assertTrue(urlAfterLoginWithNewPass.contains("my-account"));
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//ul[@class='menu general_Menu']//*[@id='myAccount']//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'logout')]/div")), "failed to login with new password");

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
