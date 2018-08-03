package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class ACCT34Test extends SuperTestClass {
	
	private B2CPage b2cPage;
	private MailPage mailPage;
	private String email_checkMessage = "Email validation passed, please go ahead to finish create account.";
	private String newPassword = "1q2w3e4r5t";
	
	public ACCT34Test(String store) {
		this.Store = store;
		this.testName = "ACCT-34";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void ACCT34(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			
			//1, Login website with credential of a Tele Rep
			Dailylog.logInfoDB(1, "Login website with credential of a Tele Rep", Store, testName);
			
			driver.get(testData.B2C.getTeleSalesUrl() + "/login");
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			driver.get(testData.B2C.getTeleSalesUrl() + "/my-account");
			
			Assert.assertTrue(b2cPage.myAccountPage_startAssistedServiceSession.isDisplayed(), "on the my account page, the Start Assisted Service Session link is not existing");
			
			//2, Click *Start Assisted Service Session* like to start ASM
			Dailylog.logInfoDB(2, "Click *Start Assisted Service Session* like to start ASM", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(3000);
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[@id='_asm']")).isDisplayed(), "After click start assisted service session , ASM function dashboard does not show ");
			
			//3, Click My Account navigation node on the header.
			Dailylog.logInfoDB(3, "Click My Account navigation node on the header.", Store, testName);
			
			Common.javascriptClick(driver, b2cPage.Navigation_subMyAccountSpan);
			
			Assert.assertTrue(b2cPage.createAccount.isDisplayed(), "After click my account navigation node on the header, the create an account button is not shown");
			
			//4, Click *Create Account* Btn
			Dailylog.logInfoDB(4, "Click *Create Account* Btn", Store, testName);
			
			b2cPage.createAccount.click();
			
			Assert.assertTrue(b2cPage.RegistrateAccount_ConfirmEmailTextBox.isDisplayed(), "After click create Account button , page does not redirected to the registration page");
			
			//5,Fill out customer info like ID(email), NAME... and then click Lenovo ID check button
			Dailylog.logInfoDB(5, "Fill out customer info like ID(email), NAME... and then click Lenovo ID check button", Store, testName);
			
			String userEmail = Common.getDateTimeString()+"@sharklasers.com";
			Dailylog.logInfoDB(5, "user Email is :" + userEmail, Store, testName);
			
			b2cPage.RegistrateAccount_EmailIdTextBox.clear();
			b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys(userEmail);
			
			b2cPage.asm_check.click();
			
			Thread.sleep(20000);
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='emailcheck_msg']")).getText().trim().equals(email_checkMessage), "After check email on registration page , the check message is not right !!!");
			
			//6, Click *I Accept Create My Account* Btn
			Dailylog.logInfoDB(6, "Click *I Accept Create My Account* Btn", Store, testName);
			
			NewUserRegistration(Store,driver,b2cPage,testData,userEmail,Browser);
			
			//7, 【Customer use this ID to login website by himself】
			//Open B2C login  url
			Dailylog.logInfoDB(7, "【Customer use this ID to login website by himself】 Open B2C login  url", Store, testName);
			
			b2cPage.ASM_signout.click();
			
			driver.get(testData.B2C.getHomePageUrl() + "/login");
			
			//8, Click *Forget Password* to walk through forget password
			//Input Email address->Click submit->Click Reset password link in Emal-> update password
			Dailylog.logInfoDB(8, "Click *Forget Password* to walk through forget password  Input Email address->Click submit->Click Reset password link in Emal-> update password", Store, testName);
			
			b2cPage.LoginPage_forgotPassword.click();
			b2cPage.ForgotPassword_emailIDTxtBox.clear();
			b2cPage.ForgotPassword_emailIDTxtBox.sendKeys(userEmail);
			b2cPage.ForgotPassword_submitButton.click();
			
			EMailCommon.createEmail(driver, mailPage, userEmail);			
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
			b2cPage.ResetPassword_newPassword.clear();
			b2cPage.ResetPassword_newPassword.sendKeys(newPassword);
			b2cPage.ResetPassword_confirmNewPassword.clear();
			b2cPage.ResetPassword_confirmNewPassword.sendKeys(newPassword);
			b2cPage.ForgotPassword_submitButton.click();
			
			Common.sleep(4000);
			
			//9, Customer login website with the new password
			Dailylog.logInfoDB(9, "Customer login website with the new password", Store, testName);
			
			b2cPage.ResetPassword_ContinueButton.click();
			Common.sleep(3000);
			B2CCommon.login(b2cPage, userEmail, newPassword);	
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
			Thread.sleep(6000);
			
			Assert.assertTrue(!driver.getCurrentUrl().contains("login"), "after modifed the password , login failed");
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	
	public void NewUserRegistration(String store, WebDriver driver, B2CPage b2cPage, TestData testData,
			String email,String browser) {
		// Enter Confirm Email
		Common.sendFieldValue(b2cPage.RegistrateAccount_ConfirmEmailTextBox, email);
		// Enter Password
		Common.sendFieldValue(b2cPage.RegistrateAccount_FirstNameTextBox, "First_Nmae");
		// Enter First Name
		Common.sendFieldValue(b2cPage.RegistrateAccount_LastNameTextBox, "Last_Name");
		
		b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
		b2cPage.RegistrateAccount_CreateAccountButton.click();
		Common.sleep(3000);
		if (!Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.RegistrateAccount_ThankYouMessage, 10))
			Assert.fail("New Account is not created successfully!");
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
