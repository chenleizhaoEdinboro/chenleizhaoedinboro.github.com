package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2BCommon.B2BRole;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class NA17671Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String tempEmailName = Common.getDateTimeString();
	public String password = "1q2w3e4r";
	public String expandLevel = "(.//div[contains(@class,'expandable-hitarea')])[1]";

	public NA17671Test(String store) {
		this.Store = store;		
		this.testName = "NA-17671";
	}
	public void expandAccessLevel(int counter){
		if(Common.isElementExist(driver,By.xpath(expandLevel))== true && counter < 4){
			b2bPage.Register_AccessLevelExpand.click();
			expandAccessLevel(++counter);
		}
	}
	

	public  void createAccount(WebDriver driver, String B2BUrl,String B2BUnit,B2BPage b2bPage,B2BRole role,String email ) throws InterruptedException{
		
		Common.NavigateToUrl(driver, Browser, B2BUrl);
		b2bPage.Login_CreateAnAccountButton.click();
		Dailylog.logInfoDB(1, "Clicked on Create Account button", Store, testName);
		Common.sleep(2000);
		expandAccessLevel(1);
		Common.sleep(1000);
		B2BCommon.clickCorrectAccessLevel(b2bPage, B2BUnit);
		Common.sleep(1000);
		B2BCommon.clickRoleCheckbox(b2bPage, role);

		b2bPage.Register_EmailTextBox.clear();
		b2bPage.Register_EmailTextBox.sendKeys(email);
		b2bPage.Register_ConfirmEmailTextBox.clear();
		b2bPage.Register_ConfirmEmailTextBox.sendKeys(email);
		b2bPage.Register_FirstNameTextBox.clear();
		b2bPage.Register_FirstNameTextBox.sendKeys("AutoFirstName");
		b2bPage.Register_LastNameTextBox.clear();
		b2bPage.Register_LastNameTextBox.sendKeys("AutoLastName");
		b2bPage.Register_PasswordTextBox.clear();
		b2bPage.Register_PasswordTextBox.sendKeys(password);
		b2bPage.Register_ConfirmPasswordTextBox.clear();
		b2bPage.Register_ConfirmPasswordTextBox.sendKeys("1q2w3e4r");
		if(Common.checkElementExists(driver, b2bPage.Register_acceptterms, 10)){
			b2bPage.Register_acceptterms.click();
		}
		b2bPage.Register_CreateAccountButton.click();
		Dailylog.logInfoDB(1, "Account is created: " + email+"@sharklasers.com", Store, testName);
	} 

	//=*=*=*=*=*=*=*=*=Login into Guerrilla mail=*=*=*=*=*=*=//
	private void LoginGuerrilla(){
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(tempEmailName);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(1,"Clicked on Set button",Store,testName);
		Dailylog.logInfoDB(1,"Email with name :"+tempEmailName+"@sharklasers.com",Store,testName);
	}
	//=*=*=*=*=*=*=*Checking Mail content*=*=*=*=*=*=*=*=*=*=*//
	private void Check_Content(int i){
		Dailylog.logInfoDB(6,"Checking mail content...",Store,testName);
		if (Common.isElementExist(driver,By.xpath("(//td[contains(.,'Forgot your password?')])")) == true) {
			driver.findElement(By.xpath("(//td[contains(.,'Forgot your password?')])")).click();
			Common.sleep(2000);	
			if (Common.isElementExist(driver,By.xpath("//a/font[contains(.,'https://pre-c-hybris.lenovo.com')]")) == true) {
				mailPage.Email_PasswordResetLink.click();
				Dailylog.logInfoDB(5,"Click on Reset link",Store,testName);
				Common.switchToWindow(driver,1);				
				Assert.assertTrue(driver.getTitle().contains("Update Forgotten Password"));
				driver.manage().window().maximize();
				
				mailPage.Email_UpdatePassword.sendKeys("asdf1234");
				mailPage.Email_NewPassBox.sendKeys("asdf1234");
				mailPage.Email_ConfirmNewPass.click();
				Common.sleep(1500);
				mailPage.Email_GoToLogin.click();
				Common.sleep(5500);
				Assert.assertTrue(driver.getTitle().contains("Login | b2bsite"));
				B2BCommon.Login(b2bPage, tempEmailName+"@sharklasers.com", "asdf1234");
				Common.sleep(5500);
				Assert.assertTrue(driver.findElement(By.xpath("//ul/li/a[contains(@href,'logout')]")).isDisplayed(),"Login was not successful");
				Common.switchToWindow(driver,0);
			}else{
				Dailylog.logInfoDB(5,"Reset link is not present",Store,testName);
				Assert.assertTrue(driver.findElement(By.xpath("//a/font[contains(.,'"+ testData.envData.getHttpsDomain().replace("LIeCommerce:M0C0v0n3L!@", "") +"')]")).isDisplayed(),"Reset Link is not present.");
				
			}
			Common.sleep(2500);						
		} else if(i < 6){
			Dailylog.logInfoDB(6,"Mail is not present...waiting to refresh",Store,testName);
			Common.sleep(10000);
			Check_Content(++i);
		}else{
			Dailylog.logInfoDB(6,"Mail is not present...",Store,testName);
		}
	}
	// =*=*=*=*=*=*=*=*=*Checking Mail and verifying country
	// seal=*=*=*=*=*=*=*=//
	private void CheckInbox(){
		Dailylog.logInfoDB(6,"Checking email...",Store,testName);
		LoginGuerrilla();
		Common.sleep(10000);
		Check_Content(1);
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p1", "b2b", "compatibility"})
	public void NA17671(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			String b2bUnit = testData.B2B.getB2BUnit();
			String b2bURL = testData.B2B.getLoginUrl();
			System.out.println("b2bURL is :" + b2bURL);
			//Step~1 
			//creating a new account 
			LoginGuerrilla();
			//Creating a fresh account
			createAccount(driver, b2bURL, b2bUnit, b2bPage,B2BRole.Buyer, tempEmailName+"@sharklasers.com");
			Common.sleep(3500);	
			//Activate account from HMC
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			Common.sleep(8000);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			hmcPage.Home_B2BCommerceLink.click();
			hmcPage.Home_B2BCustomer.click();
			Common.sleep(1500);
			hmcPage.B2BCustomer_SearchIDTextBox.clear();
			hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(tempEmailName+"@sharklasers.com");
			hmcPage.B2BCustomer_SearchButton.click();
			Common.sleep(1500);
			hmcPage.B2BCustomer_1stSearchedResult.click();
			hmcPage.B2BCustomer_PasswordTab.click();
			Common.sleep(1500);
			hmcPage.B2BCustomer_ActiveUserDropdown.click();
			hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
			hmcPage.Common_SaveButton.click();
			Common.sleep(1500);
			hmcPage.HMC_Logout.click();
			// Fetch login URL and click Forgot Password
			Common.NavigateToUrl(driver, Browser, testData.B2B.getLoginUrl());
			Common.sleep(3000);
			//step~2
			b2bPage.B2BLoginPage_ForgotPassLink.click();
			Common.sleep(4000);
			Dailylog.logInfoDB(2,"Clicked on Forgot password link",Store,testName);
			//step~3
			b2bPage.B2BLoginPage_BuyerMailTextBox.sendKeys(tempEmailName+"@sharklasers.com");
			b2bPage.B2BLoginPage_SendForgotMailButton.click();
			Dailylog.logInfoDB(3,"Clicked on send button for Forgot password",Store,testName);
			String ForgotMessage = b2bPage.B2BLoginPage_Message.getText();
			Dailylog.logInfoDB(3,"Message is : "+ForgotMessage,Store,testName);
			b2bPage.B2BLoginPage_CloseIcon.click();
			//step~4,5&6
			//check user's mail
			CheckInbox();
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


}