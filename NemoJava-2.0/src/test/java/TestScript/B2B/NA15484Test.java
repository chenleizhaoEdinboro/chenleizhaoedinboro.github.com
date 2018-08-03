package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2BCommon.B2BRole;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;


public class NA15484Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
    String tempEmailAddress;
	public NA15484Test(String store) {
		this.Store = store;
		this.testName = "NA-15484";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p1", "b2b"})
	public void NA15484(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
            Dailylog.logInfo("//1======validate buyerDefault role !======1//");
			validateEachRole(driver, b2bPage, hmcPage, "buyerDefault");
			
			Dailylog.logInfo("//2======validate builderDefault role !======2//");
			validateEachRole(driver, b2bPage, hmcPage, "builderDefault");
			
			Dailylog.logInfo("//3======validate approverDefault role !======3//");
			validateEachRole(driver, b2bPage, hmcPage, "approverDefault");
			
			Dailylog.logInfo("//4======validate browserDefault role !======4//");
			validateEachRole(driver, b2bPage, hmcPage, "browserDefault");
			
			Dailylog.logInfo("//5======validate customAdminControlled role !======5//");
			validateEachRole(driver, b2bPage, hmcPage, "customAdminControlled");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	@AfterTest(alwaysRun = true)
	public void rollback(ITestContext ctx){
		try{
			Dailylog.logInfo("rollback!!!");
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 5)) {
				hmcPage.Home_EndSessionLink.click();
			}
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2BCommerceLink.click();
			hmcPage.Home_B2BCustomer.click();
			driver.findElement(By.xpath("//input[contains(@id,'B2BCustomer.name')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'B2BCustomer.name')]")).sendKeys("AutoFNB2B15484");
			hmcPage.B2BCustomer_SearchButton.click();
			Common.sleep(5000);
			Common.rightClick(driver, driver.findElement(By.xpath("//div/a[contains(@id,'Content/McSearchListConfigurable[B2BCustomer]_uid_sort')]")));
			driver.findElement(By.xpath(".//*[@id='Content/McSearchListConfigurable[B2BCustomer]_!select_visible_true_label']")).click();
			Common.sleep(3000);
			driver.findElement(By.xpath(".//*[@id='Content/McSearchListConfigurable[B2BCustomer][delete]_img']")).click();
			
			if(Common.isAlertPresent(driver)){
				driver.switchTo().alert().accept();
			}
		}catch (Throwable e){
			handleThrowable(e,ctx);
		}
	}
	/**
	 * @author haiyang
	 * @param hmcPage
	 * @param roleID
	 * @return flag
	 * @usage Determine whether the role exists
	 */
    public boolean isRoleExist(HMCPage hmcPage,String roleID){
    	boolean flag = false;
    	ArrayList<WebElement> roles = (ArrayList<WebElement>) hmcPage.PageDriver.findElements(By.xpath("//tbody/tr[contains(@id,'B2BUnit.needApproveUserRoles')]/td[2]"));
    	Dailylog.logInfo("roles number:"+roles.size());
    	for(WebElement rows : roles){
    		Dailylog.logInfo("roles ID:"+rows.getText());
    	}
    	for(int i = 0; i < roles.size(); i++){
    		if(roles.get(i).getText().equals(roleID)){
    			flag = true;
    			Dailylog.logInfo("The role exist !");
    			break;
    		}
    	}
    	return flag;
    }
    
    /**
     * @author haiyang
     * @param hmcPage
     * @param roleID
     * @usage add role if this role is not exist.
     */
    public void addRole(HMCPage hmcPage, String roleID){
    	if(Common.isElementExist(driver, By.xpath(".//*[@id='table_Content/NemoAvailableUserGroup[in Content/Attribute[B2BUnit.needApproveUserRoles]]_table']/tbody/tr/th/div[contains(text(),'ID')]")))
    	    Common.rightClick(driver, hmcPage.PageDriver.findElement(By.xpath(".//*[@id='table_Content/NemoAvailableUserGroup[in Content/Attribute[B2BUnit.needApproveUserRoles]]_table']/tbody/tr/th/div[contains(text(),'ID')]")));
    	hmcPage.PageDriver.findElement(By.xpath("//div[@class='dropdown-main']/table/tbody/tr/td[contains(@id,'add_true_label')]")).click();
    	Common.switchToWindow(driver, 1);
    	if(Common.isElementExist(driver, By.xpath("//input[contains(@id,'UserAvailableGroup.uid')]"))){
    		hmcPage.PageDriver.findElement(By.xpath("//input[contains(@id,'UserAvailableGroup.uid')]")).clear();
    		hmcPage.PageDriver.findElement(By.xpath("//input[contains(@id,'UserAvailableGroup.uid')]")).sendKeys(roleID);
    	}
    	hmcPage.PageDriver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();//click search button
    	hmcPage.PageDriver.findElement(By.xpath("//div[contains(text(),'"+roleID+"')]")).click();// select search result
    	hmcPage.PageDriver.findElement(By.xpath("//span[contains(text(),'Use')]")).click();//click use button
    	Dailylog.logInfo("role add complete !");
    	Common.switchToWindow(driver, 0);
    }
    
    /**
     * @author haiyang
     * @param hmcPage
     * @param roleID
     * @param switchValue
     * @Usage Whether the role needs to be opened--need approval
     */
    public void SwitchRoleNeedApproval(HMCPage hmcPage, String roleID, String switchValue){
    	if(switchValue.equals("Yes")){
    		hmcPage.PageDriver.findElement(By.xpath("//input[contains(@name,'"+roleID+"') and (contains(@value,'true'))]")).click();
    		Dailylog.logInfo("role set need approval !");
    	}else{
    		hmcPage.PageDriver.findElement(By.xpath("//input[contains(@name,'"+roleID+"') and (contains(@value,'false'))]")).click();
    		Dailylog.logInfo("role set no need approval !");
    	}
    }
    /**
     * @author haiyang
     * @param Store
     * @param msgName
     * @return error message
     * @usage get error message
     */
    public String getErrorMessage(String Store, EnumErrorMsg msgName){
    	if(Store.equals("JP")){
    		switch (msgName){
    		case accessLevelErr:
    			return "Please select an access level.";
    		case defaultStoreErr:
    			return "Please select a default store.";
    		case selectRoleErr:
    		    return "Please select a role.";
    		case emailErr:
				return "Please enter a valid email";
			case fNameErr:
				return "Please enter a first name";
			case lNameErr:
				return "Please enter a last name";
			case passwordErr:
				return "Passwords must contain at least 8 characters and include two of the following character types: letters, numbers, or symbols.";
			case confirmPassErr:
				return "Please confirm your password";
			case emailNotMatchErr:
				return "Email and confirm email don't match";
			case passwordNotMatchErr:
				return "Password and password confirmation do not match";
			case emailAlreadyExistsErr:
				return "An account already exists for this email address. Please login with your existing account.";
			case accountCreatedThankYouMsg:
				return "Thank You For Creating An Account";
			case accountNotVerifiedErr:
				return "Sorry, you have not been approved by admin";
			default:
				return null;
    		}
    	}else{
    		switch (msgName){
    		case accessLevelErr:
    			return "Please select an access level.";
    		case defaultStoreErr:
    			return "Please select a default store.";
    		case selectRoleErr:
    		    return "Please select a role.";
    		case emailErr:
				return "Please enter a valid email";
			case fNameErr:
				return "Please enter a first name";
			case lNameErr:
				return "Please enter a last name";
			case passwordErr:
				return "Passwords must contain at least 8 characters and include two of the following character types: letters, numbers, or symbols.";
			case confirmPassErr:
				return "Please confirm your password";
			case emailNotMatchErr:
				return "Email and confirm email don't match";
			case passwordNotMatchErr:
				return "Password and password confirmation do not match";
			case emailAlreadyExistsErr:
				return "An account already exists for this email address. Please login with your existing account.";
			case accountCreatedThankYouMsg:
				return "Thank You For Creating An Account";
			case accountNotVerifiedErr:
				return "Sorry, you have not been approved by admin";
			default:
				return null;
    		}
    	}
    }
    
    public enum EnumErrorMsg {
		accessLevelErr,defaultStoreErr,selectRoleErr,emailErr, fNameErr, lNameErr, passwordErr, confirmPassErr, accecptErr, emailNotMatchErr, passwordNotMatchErr, emailAlreadyExistsErr, accountCreatedThankYouMsg, accountNotVerifiedErr,;
	}
    

    /**
     * @author haiyang
     * @param b2bPage
     * @param emailAddress
     * @param ComfirmEmailAddress
     * @param firstName
     * @param lastName
     * @param password
     * @param confirmPassword
     * @Usage Fill in the registration information
     */
    public void registrateFillingInfo(B2BPage b2bPage, String emailAddress, String ComfirmEmailAddress,
    		String firstName, String lastName, String password, String confirmPassword){
    	b2bPage.Register_EmailTextBox.clear();
    	b2bPage.Register_EmailTextBox.sendKeys(emailAddress);
    	
    	b2bPage.Register_ConfirmEmailTextBox.clear();
    	b2bPage.Register_ConfirmEmailTextBox.sendKeys(ComfirmEmailAddress);
		
    	b2bPage.Register_FirstNameTextBox.clear();
    	b2bPage.Register_FirstNameTextBox.sendKeys(firstName);
		
    	b2bPage.Register_LastNameTextBox.clear();
    	b2bPage.Register_LastNameTextBox.sendKeys(lastName);
		
    	b2bPage.Register_PasswordTextBox.clear();
    	b2bPage.Register_PasswordTextBox.sendKeys(password);
		
    	b2bPage.Register_ConfirmPasswordTextBox.clear();
    	b2bPage.Register_ConfirmPasswordTextBox.sendKeys(confirmPassword);
    	b2bPage.Register_AgreeOptinCheckBox.click();
    	b2bPage.Register_CreateAccountButton.click();
    }

    
    public void validateEachRole(WebDriver driver, B2BPage b2bPage,HMCPage hmcPage , String roleID){
    	//Step:1
		Dailylog.logInfoDB(1, "First config the store. Go and login HMC.", Store, testName);
		Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
		Common.sleep(3000);
		if(Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 10)){
			hmcPage.Home_EndSessionLink.click();
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
		}else{
			HMCCommon.Login(hmcPage, testData);
		}
		//Step:2 and 3
		Dailylog.logInfoDB(2, "Click the B2B commerce -- B2B unit "
				+ "-- search one unit you want to config", Store, testName);
		
		Dailylog.logInfoDB(3, "Double click the expected store.", Store, testName);
		HMCCommon.searchB2BUnit(hmcPage, testData);
		//Step:4
		Dailylog.logInfoDB(4, "Click the Site Attributes and"
				+ " find 'Change Available User Roles Need Approval' "
				+ "to add or delete user role.", Store, testName);
		hmcPage.B2BUnit_siteAttribute.click();
		if(!isRoleExist(hmcPage,roleID)){
			addRole(hmcPage,roleID);
		}
		// Step:5
		Dailylog.logInfoDB(5, "Change the 'Need Approval' status to Yes or No. "
				+ "Yes means the user role need approval.", Store, testName);
		
		//Step:6
		Dailylog.logInfoDB(6, "Config the default buyer need approval.", Store, testName);
		SwitchRoleNeedApproval(hmcPage, roleID, "Yes");
		hmcPage.SaveButton.click();
		// create new role user for step 16
		if(roleID.equals("buyerDefault")){
			Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl());
			driver.manage().deleteAllCookies();
			B2BCommon.createAccount(driver, testData.B2B.getHomePageUrl()+"/login", testData.B2B.getB2BUnit(), b2bPage, B2BRole.Buyer, "buyerdefaultExit@sharklasers.com",Browser);
		}else if(roleID.equals("builderDefault")){
			Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl());
			driver.manage().deleteAllCookies();
			B2BCommon.createAccount(driver, testData.B2B.getHomePageUrl()+"/login", testData.B2B.getB2BUnit(), b2bPage, B2BRole.Builder, "builderdefaultExit@sharklasers.com",Browser);
		}else if(roleID.equals("approverDefault")){ 
			Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl());
			driver.manage().deleteAllCookies();
			B2BCommon.createAccount(driver, testData.B2B.getHomePageUrl()+"/login", testData.B2B.getB2BUnit(), b2bPage, B2BRole.Approver, "approverdefaultExit@sharklasers.com",Browser);
		}else if(roleID.equals("browserDefault")){
			Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl());
			driver.manage().deleteAllCookies();
			B2BCommon.createAccount(driver, testData.B2B.getHomePageUrl()+"/login", testData.B2B.getB2BUnit(), b2bPage, B2BRole.Browser, "browserdefaultExit@sharklasers.com",Browser);
		}else if(roleID.equals("customAdminControlled")){
			Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl());
			driver.manage().deleteAllCookies();
			B2BCommon.createAccount(driver, testData.B2B.getHomePageUrl()+"/login", testData.B2B.getB2BUnit(), b2bPage, B2BRole.Admin, "admindefaultExit@sharklasers.com",Browser);
		}
		
		//step:7
		Dailylog.logInfoDB(7, "Navigate to B2B website "
				+ "click 'create an account' button", Store, testName);
		Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl());
		driver.manage().deleteAllCookies();
		Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl()+"/login");
		b2bPage.Login_CreateAnAccountButton.click();
		//validate  jumping to the registration page
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/registration/BP"), "The results did not jump to the registration page");
		//Step:8
		Dailylog.logInfoDB(8, "Nothing input, click 'CREATE' button. ", Store, testName);
		b2bPage.Register_CreateAccountButton.click();
		Assert.assertEquals(b2bPage.RegistrateAccount_AccessLevelErr.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.accessLevelErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_DefaultStoreErr.getText().toString(),
				getErrorMessage(testData.Store, EnumErrorMsg.defaultStoreErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_RoleErr.getText(), 
				getErrorMessage(testData.Store, EnumErrorMsg.selectRoleErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_EmailError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.emailErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_ConfirmEmailError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.emailErr));
		Assert.assertEquals(b2bPage.RegistrateAccount_FirstNameError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.fNameErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_LastNameError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.lNameErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_PasswordError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.passwordErr));
		
		Assert.assertEquals(b2bPage.RegistrateAccount_ConfirmPasswordError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.confirmPassErr));
		//Step:9
		Dailylog.logInfoDB(9, "Select your access level", Store, testName);
		B2BCommon.expandAccessLevel(b2bPage);
		//Step:10
		Dailylog.logInfoDB(10, "Select one store as your default store", Store, testName);
		B2BCommon.clickCorrectAccessLevel(b2bPage, testData.B2B.getB2BUnit());
		//Step:11
		Dailylog.logInfoDB(11, "Select your role. "
				+ "(Default Approver/Default Builder/Default Browser/Default Buyer/"
				+ "Punch Out Customer/Controlled Customer Administrator)", Store, testName);
		if(roleID.equals("buyerDefault")){
			B2BCommon.clickRoleCheckbox(b2bPage, B2BRole.Buyer);
			
		}else if(roleID.equals("builderDefault")){
			B2BCommon.clickRoleCheckbox(b2bPage, B2BRole.Builder);
			
		}else if(roleID.equals("approverDefault")){
			B2BCommon.clickRoleCheckbox(b2bPage, B2BRole.Approver);
			
		}else if(roleID.equals("browserDefault")){
			B2BCommon.clickRoleCheckbox(b2bPage, B2BRole.Browser);
			
		}else if(roleID.equals("customAdminControlled")){
			B2BCommon.clickRoleCheckbox(b2bPage, B2BRole.Admin);
		}
		
		//Step:12
		Dailylog.logInfoDB(12, "Input wrong  Email format for email related field", Store, testName);
		registrateFillingInfo(b2bPage, "InvalidEmailId", "InvalidEmailId", "AutoFirstName",
				"AutoLastName", "1q2w3e4r", "1q2w3e4r");
		Common.sleep(5000);
		Assert.assertEquals(b2bPage.RegistrateAccount_EmailError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.emailErr));
		//Step:13
		Dailylog.logInfoDB(13, "Re-enter Email address not match original Email address", Store, testName);
		registrateFillingInfo(b2bPage, "autotest@lenovo.com", "autotest2@lenovo.com", "AutoFirstName",
				"AutoLastName", "1q2w3e4r", "1q2w3e4r");
		Assert.assertEquals(b2bPage.RegistrateAccount_ConfirmEmailError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.emailNotMatchErr));
		//step:14
		Dailylog.logInfoDB(14, "Input password less than 8 character", Store, testName);
		registrateFillingInfo(b2bPage, "autotest@lenovo.com", "autotest@lenovo.com", "AutoFirstName",
				"AutoLastName", "1q2w3e", "1q2w3e");
		Assert.assertEquals(b2bPage.RegistrateAccount_PasswordError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.passwordErr));
		//Step:15
		Dailylog.logInfoDB(15, "Re-enter password not match original password", Store, testName);
		registrateFillingInfo(b2bPage, "autotest@lenovo.com", "autotest@lenovo.com", "AutoFirstName",
				"AutoLastName", "1q2w3e4r", "1q2w3err");
		Assert.assertEquals(b2bPage.RegistrateAccount_ConfirmPasswordError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.passwordNotMatchErr));
		//Step:16
		Dailylog.logInfoDB(16, "User input email address already existing in B2B system and "
				+ "fill in all related information.", Store, testName);
		
		if(roleID.equals("buyerDefault")){
			registrateFillingInfo(b2bPage, "buyerdefaultExit@sharklasers.com", "buyerdefaultExit@sharklasers.com", "AutoFirstName",
					"AutoLastName", "1q2w3e4r", "1q2w3e4r");	
			
		}else if(roleID.equals("builderDefault")){
			registrateFillingInfo(b2bPage, "builderdefaultExit@sharklasers.com", "builderdefaultExit@sharklasers.com", "AutoFirstName",
					"AutoLastName", "1q2w3e4r", "1q2w3e4r");
			
		}else if(roleID.equals("approverDefault")){
			registrateFillingInfo(b2bPage, "approverdefaultExit@sharklasers.com", "approverdefaultExit@sharklasers.com", "AutoFirstName",
					"AutoLastName", "1q2w3e4r", "1q2w3e4r");
			
		}else if(roleID.equals("browserDefault")){
			registrateFillingInfo(b2bPage, "browserdefaultExit@sharklasers.com", "browserdefaultExit@sharklasers.com", "AutoFirstName",
					"AutoLastName", "1q2w3e4r", "1q2w3e4r");
			
		}else if(roleID.equals("customAdminControlled")){
			registrateFillingInfo(b2bPage, "admindefaultExit@sharklasers.com", "admindefaultExit@sharklasers.com", "AutoFirstName",
					"AutoLastName", "1q2w3e4r", "1q2w3e4r");
			
		}
		
		registrateFillingInfo(b2bPage, "buyerdefault@sharklasers.com", "buyerdefault@sharklasers.com", "AutoFirstName",
				"AutoLastName", "1q2w3e4r", "1q2w3e4r");
		
		
		Assert.assertEquals(b2bPage.RegistrateAccount_EmailError.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.emailAlreadyExistsErr));
		//Step:17
		Dailylog.logInfoDB(17, "User go to registration page, select your access level, "
				+ "select default store, select user role,  input valid email, first name,"
				+ " Lastname, password fit the rule,click create button", Store, testName);
		 tempEmailAddress = EMailCommon.getRandomEmailAddress();
		Dailylog.logInfo("tempEmailAddress:"+tempEmailAddress);
		registrateFillingInfo(b2bPage, tempEmailAddress, tempEmailAddress, "AutoFNB2B15484",
				"AutoLNB2B15484", "1q2w3e4r", "1q2w3e4r");
		Common.sleep(6000);
		Assert.assertEquals(b2bPage.RegistrateAccount_ThankYouMessage.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.accountCreatedThankYouMsg));
		//Step:18
		Dailylog.logInfoDB(18, "Click 'go to my account button' and  go to log in page,"
				+ " input new user ID and password without activate account", Store, testName);
		b2bPage.RegistrateAccount_GotoMyAccount.click();
		Assert.assertTrue(Common.checkElementExists(driver, b2bPage.Login_SignInButton, 10), "Step 18 failed!");
		//Step:19
		Dailylog.logInfoDB(19, "Login with your Default Buyer account you just created.", Store, testName);
		b2bPage.Login_EmailTextBox.clear();
		b2bPage.Login_EmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Login_PasswordTextBox.clear();
		b2bPage.Login_PasswordTextBox.sendKeys("1q2w3e4r");
		b2bPage.Login_SignInButton.click();
		Common.sleep(3000);
		Assert.assertEquals(b2bPage.RegistrateAccount_LoginErrMsg.getText(),
				getErrorMessage(testData.Store, EnumErrorMsg.accountNotVerifiedErr));
		//Step:20
		Dailylog.logInfoDB(20, "Lenovo Admin login HMC search the user and approve the user.", Store, testName);
		Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());

		if(Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 10)){
			hmcPage.Home_EndSessionLink.click();
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
		}else{
			HMCCommon.Login(hmcPage, testData);
		}
		Common.sleep(5000);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(tempEmailAddress);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			hmcPage.B2BCustomer_1stSearchedResult.click();
		}
		if(Common.checkElementExists(driver, hmcPage.CustomerAccount_passwordTab, 60)){
			hmcPage.CustomerAccount_passwordTab.click();
		}
		Select approveStatus = new Select(hmcPage.B2BCustomer_ActiveUserDropdown);
		approveStatus.selectByVisibleText("ACTIVE");
		hmcPage.SaveButton.click();
		Common.sleep(10000);
		//Step:21
		Dailylog.logInfoDB(21, "Log in website by using activate account and password", Store, testName);
		Common.NavigateToUrl(driver, Browser, testData.B2B.getHomePageUrl()+"/login");
		B2BCommon.Login(b2bPage, tempEmailAddress, "1q2w3e4r");
		Assert.assertTrue(Common.checkElementExists(driver, b2bPage.homepage_MyAccount, 60), "Step 21 is failed!");
    }
}

