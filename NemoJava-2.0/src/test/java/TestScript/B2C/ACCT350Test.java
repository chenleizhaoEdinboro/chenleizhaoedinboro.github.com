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
import TestScript.SuperTestClass;


public class ACCT350Test extends SuperTestClass {

	public B2CPage b2cPage;
	private String newPwd = "a12341234";
	String successMsgBox;
	String tempEmailAddress;

	public ACCT350Test(String store, String successMsgBox) {
		this.Store = store;
		this.successMsgBox = successMsgBox;
		this.testName = "ACCT-350";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void ACCT350(ITestContext ctx) {

		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);
			
			//Before testing . first we need to create an account
			createAnAccount();
			
			// Step 1 Go to B2C website
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// Step 2 Login B2C
			B2CCommon.login(b2cPage, tempEmailAddress, testData.B2C.getLoginPassword());
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
			Thread.sleep(5000);
			
			b2cPage.MyAccountPage_ChangePassword.click();
			
			b2cPage.UpdatePasswordPage_CurrentPassword.clear();
			b2cPage.UpdatePasswordPage_CurrentPassword.sendKeys(testData.B2C.getLoginPassword());
			b2cPage.UpdatePasswordPage_NewPassword.clear();
			b2cPage.UpdatePasswordPage_NewPassword.sendKeys(newPwd);
			b2cPage.UpdatePasswordPage_ConfirmNewPassword.clear();
			b2cPage.UpdatePasswordPage_ConfirmNewPassword.sendKeys(newPwd);
			
			b2cPage.UpdatePasswordPage_UpdatePasswordBtn.click();
			
			
			Thread.sleep(5000);
			
			Assert.assertEquals(driver.findElement(By.xpath("//div[@class='alert positive']")).getText(), successMsgBox);
			
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			
			Thread.sleep(5000);
			
			b2cPage.MyAccountPage_ChangePassword.click();
			
			b2cPage.UpdatePasswordPage_CurrentPassword.clear();
			b2cPage.UpdatePasswordPage_CurrentPassword.sendKeys(newPwd);
			b2cPage.UpdatePasswordPage_NewPassword.clear();
			b2cPage.UpdatePasswordPage_NewPassword.sendKeys(testData.B2C.getLoginPassword());
			b2cPage.UpdatePasswordPage_ConfirmNewPassword.clear();
			b2cPage.UpdatePasswordPage_ConfirmNewPassword.sendKeys(testData.B2C.getLoginPassword());
			
			b2cPage.UpdatePasswordPage_UpdatePasswordBtn.click();
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void createAnAccount() throws Exception{
		tempEmailAddress = EMailCommon.getRandomEmailAddress();
		Dailylog.logInfoDB(0, "tempEmailAddress is :" + tempEmailAddress, Store, testName);

		Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());

		B2CCommon.handleGateKeeper(b2cPage, testData);
		Common.javascriptClick(driver, b2cPage.Navigation_SignInLink);
		
		if (Common.checkElementExists(driver, b2cPage.Login_CreateAnAccountButton, 5)){
			b2cPage.Login_CreateAnAccountButton.click();
		}
		else
			b2cPage.RegisterGateKeeper_CreateAnAccountButton.click();
		
		// Input all of the valid details then click create account
		inputValuesAndClickCreate(b2cPage, tempEmailAddress, tempEmailAddress, testData.B2C.getLoginPassword(), testData.B2C.getLoginPassword());
					
		EMailCommon.activeNewAccount(driver,tempEmailAddress, 1, testData.Store);
	}

	public void inputValuesAndClickCreate(B2CPage b2cPage, String email, String conEmail, String password,
			String conPassword) throws InterruptedException {
		b2cPage.RegistrateAccount_EmailIdTextBox.clear();
		b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys(email);
		b2cPage.RegistrateAccount_ConfirmEmailTextBox.clear();
		b2cPage.RegistrateAccount_ConfirmEmailTextBox.sendKeys(conEmail);
		b2cPage.RegistrateAccount_FirstNameTextBox.clear();
		b2cPage.RegistrateAccount_FirstNameTextBox.sendKeys("AutoFirstName");
		b2cPage.RegistrateAccount_LastNameTextBox.clear();
		b2cPage.RegistrateAccount_LastNameTextBox.sendKeys("AutoLastName");
		b2cPage.RegistrateAccount_PasswordTextBox.clear();
		b2cPage.RegistrateAccount_PasswordTextBox.sendKeys(password);
		b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
		b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys(conPassword);
		b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
		b2cPage.RegistrateAccount_CreateAccountButton.click();
		Thread.sleep(5000);
	}

}
