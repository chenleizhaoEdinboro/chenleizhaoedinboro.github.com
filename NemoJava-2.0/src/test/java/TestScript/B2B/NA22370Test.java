package TestScript.B2B;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.B2BCommon.B2BRole;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA22370Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	int listLength;
	String []domainEmail = new String[10];
	Boolean whertherRollBack = false;
	String emailDomain = "@SHARKLASERS.COM";
    String expectErrorMessage = "You are not allowed to register an account for this site from the given email address domain.";
	public NA22370Test(String store) {
		this.Store = store;
		this.testName = "NA-22370";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2b"})
	public void NA22370(ITestContext ctx) {
		
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			hmcPage = new HMCPage(driver);
			//Step1-2: Set Email Domain in HMC.
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.B2BUnit_siteAttribute_emailDomainValidatoinToggleYes.click();
			System.out.println("'Email Domain Validation toggle' is set to Yes!");
			listLength = hmcPage.B2BUnit_siteAttribute_emailDomainValidationList.size();
			System.out.println("The emailDomainValidatoinList length is: "+listLength);		
			for(int i=0;i<listLength;i++){
				domainEmail[i] = hmcPage.B2BUnit_siteAttribute_emailDomainValidationList.get(i).getAttribute("value");
				System.out.println("Thr domainEmail["+i+"] is:" +domainEmail[i]);
			}	
			
			removeDomainEmail();
			whertherRollBack = true;
			addDomainEmail(emailDomain);
			
			hmcPage.Home_closeSession.click();
			
			Dailylog.logInfoDB(2,"Set Email Domain.", Store,testName);		
			//Step3: Go to B2B site, click "Create an account" button. Check can go to create an account page.			
			//Step4: Input valid value in field, and create an account. Check can create successfully.
			String today=Common.getDateTimeString();
			String email = "test22370"+Store+today;
			String account = email+emailDomain;
//			EMailCommon.createEmail(driver,mailPage,email);
			B2BCommon.createAccount(driver ,testData.B2B.getHomePageUrl() ,testData.B2B.getB2BUnit() ,b2bPage, B2BRole.Buyer, account, Browser);
			Boolean thankYouDisplay = Common.checkElementExists(driver, b2bPage.Register_thankYouMessage,10);
			Assert.assertTrue("Don't display the thank you page, or the xpath is error!", thankYouDisplay);
			System.out.println("Create account successfully!");
			Dailylog.logInfoDB(4,"Input valid value in field, and create account successfully.", Store,testName);
			//Step5: Login HMC search the user and approve the user. Check account will be active.[Step6 will check it.]
			HMCCommon.activeAccount(driver,testData, account);
			Dailylog.logInfoDB(5,"Active account in HMC successfully.", Store,testName);
			
			//Step6: Login B2B with just created on B2B site. Check can login successfully.
			driver.manage().deleteAllCookies();
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, account, "1q2w3e4r");
			Boolean welcomeDisplay = Common.checkElementExists(driver, b2bPage.homepage_Signout,10);
			Assert.assertTrue("Login failed, or the xpath is error!", welcomeDisplay);
			System.out.println("Login in successfully!");
			Dailylog.logInfoDB(6,"Login B2B successfully.", Store,testName);
			
			//Step7: Sign out and back to B2B login site. Check sign out successfully and go to login page.
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.PAGE_UP).perform();
			b2bPage.homepage_Signout.click();
			String url = driver.getCurrentUrl();
			System.out.println(url);
			Boolean signOutUrl = url.contains("login");
			Assert.assertTrue("Sign out failed and don't go to login page", signOutUrl);
			System.out.println("Sign out successfully!");
			Dailylog.logInfoDB(7,"Sign out successfully.", Store,testName);
			
			//Step8: Click "Create an account" button. Check can go to create an account page.			
			//Step9: Input valid value in field, and error email. Check create failed.
			String yopmail = "test22370"+Store;
			String yopmailAccount = yopmail+"@yopmail.com";
			createYopmail(driver,mailPage,yopmail);
			B2BCommon.createAccount(driver ,testData.B2B.getHomePageUrl() ,testData.B2B.getB2BUnit() ,b2bPage, B2BRole.Buyer, yopmailAccount, Browser);
			Boolean errorDisplay = Common.checkElementExists(driver, b2bPage.Register_emailError,10);
			Assert.assertTrue("Don't display the error message, or the xpath is error!", errorDisplay);
			String getErrorMessage = b2bPage.Register_emailError.getText();
			System.out.println("The error message is: "+getErrorMessage);
			Assert.assertEquals("The error message is different with expect! ", expectErrorMessage, getErrorMessage);
			Dailylog.logInfoDB(9,"Input valid value in field, and error email, create failed.", Store,testName);
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	@AfterTest(alwaysRun= true)
	public void rollBack(){
		//Step10: Roll back-reset in HMC.
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2BUnit(hmcPage, testData);
		hmcPage.B2BUnit_siteAttribute.click();
		hmcPage.B2BUnit_siteAttribute_emailDomainValidationToggleNo.click();
		if(whertherRollBack){
			driver.findElement(By.xpath("(//*[@id='resultlist_Content/GenericResortableList']//input[@type='checkbox'])[2]")).click();
			Common.rightClick(driver, hmcPage.B2BUnit_siteAttribute_emailDomainValidationList.get(0));
			hmcPage.B2BUnit_siteAttribute_emailDomainValidationListRemove.get(0).click();
			System.out.println("Remove the value in 'Email Domain Validation list'! ");
		
			for(int i=0;i<listLength;i++){	
				addDomainEmail(domainEmail[i]);
			}
			System.out.println("Roll back the 'Email Domain Validation toggle' to No, and roll back the email domain!");
			
		} 
		Dailylog.logInfoDB(10,"Roll back successfully.", Store,testName);
	}
	public void addDomainEmail(String emailDomain){
		Common.rightClick(driver, driver.findElement(By.xpath("//div[@id='resultlist_Content/GenericResortableList']//div[contains(.,'Value')]")));
		hmcPage.B2BUnit_siteAttribute_emailDomainValidationListCreate.click();
		hmcPage.B2BUnit_siteAttribute_emailDomainValidationListInput.sendKeys(emailDomain);
		hmcPage.B2BUnit_Save.click();
		System.out.println("Add value '"+ emailDomain +"' into 'Email Domain Validation list'! ");
	}
	
	public void removeDomainEmail(){
		String onclickValue = hmcPage.B2BUnit_siteAttribute_emailDomainValidationListCheckbox.getAttribute("onclick");
		if(onclickValue.contains("true")){
			hmcPage.B2BUnit_siteAttribute_emailDomainValidationListCheckbox.click();
		}		
		Common.rightClick(driver, hmcPage.B2BUnit_siteAttribute_emailDomainValidationList.get(0));
		int removeElementLength = hmcPage.B2BUnit_siteAttribute_emailDomainValidationListRemove.size();
		
		System.out.println("The size is: "+removeElementLength);
		hmcPage.B2BUnit_siteAttribute_emailDomainValidationListRemove.get(removeElementLength-1).click();
		System.out.println("Remove all the value in 'Email Domain Validation list'! ");
	}

	public void createYopmail(WebDriver driver, MailPage mailPage,String email){
		driver.manage().deleteAllCookies();
		driver.get("http://www.yopmail.com");
		mailPage.yopMail_login.clear();	
		mailPage.yopMail_login.click();
		mailPage.yopMail_checkInbox.sendKeys(email);
	}
}
