package TestScript.B2B;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import TestScript.SuperTestClass;

public class NA17401Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	private String emailDomain = "@sharklasers.com";
	private String buyerAccount;
//	private String adminAccount_1 = "admin1";
	private String ERRORMESSAGE = "Sorry, you have not been approved by admin";
	private String THANKYOUMESSAGE = "Thank You For Creating An Account";
	private String expandLevel = "(.//div[contains(@class,'expandable-hitarea')])[1]";

	public NA17401Test(String store) {
		this.Store = store;
		this.testName = "NA-17401";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2b"})
	public void NA17401(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);

			// Login B2B store and find an agreement product

			Dailylog.logInfoDB(1, "Login to HMC site", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Searching for B2B unit.", Store, testName);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Thread.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(1000);
			Dailylog.logInfoDB(4, "Adding buyer roles.", Store, testName);
			if(!isRoleExist(hmcPage,"buyerDefault")){
				addRole(hmcPage,"buyerDefault");
			}
			Thread.sleep(2000);
			Dailylog.logInfoDB(6, "Config the default buyer need approval.", Store,testName);
			hmcPage.PageDriver.findElement(By.xpath("//input[contains(@name,'buyerDefault') and (contains(@value,'true'))]")).click();
//			Dailylog.logInfoDB(7, "Adding ADMIN ids to approve accounts.",
//					Store, testName);
//			if(Store.equals("AU")){
//				hmcPage.B2BUnit_SA_LenovoAdminEmail.clear();
//				hmcPage.B2BUnit_SA_LenovoAdminEmail.sendKeys(adminAccount_1 + emailDomain);
//				hmcPage.B2BUnit_SA_AdminEmail.clear();
//				hmcPage.B2BUnit_SA_AdminEmail.sendKeys(adminAccount_1 + emailDomain);
//			}else if(Store.equals("JP")){
//				hmcPage.B2BUnit_SA_LenovoAdminEmail.clear();
//				hmcPage.B2BUnit_SA_LenovoAdminEmail.sendKeys("adminjp" + emailDomain);
//				hmcPage.B2BUnit_SA_AdminEmail.clear();
//				hmcPage.B2BUnit_SA_AdminEmail.sendKeys("adminjp" + emailDomain);
//			}else if(Store.equals("US")){
//				hmcPage.B2BUnit_SA_LenovoAdminEmail.clear();
//				hmcPage.B2BUnit_SA_LenovoAdminEmail.sendKeys("adminus" + emailDomain);
//				hmcPage.B2BUnit_SA_AdminEmail.clear();
//				hmcPage.B2BUnit_SA_AdminEmail.sendKeys("adminus" + emailDomain);
//			}
//			
//			Dailylog.logInfoDB(7,
//					"Added ADMIN ids successfully and clicked on save button.",
//					Store, testName);
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			Dailylog.logInfoDB(7, "Logged out from HMC site.", Store, testName);
			hmcPage.Home_EndSessionLink.click();
			Thread.sleep(2000);

			// Creating B2B Buyer account
			buyerAccount = "buyer"+Common.getDateTimeString()+emailDomain;
			Dailylog.logInfoDB(8, "Creating buyer account.", Store, testName);
			createAccount(driver, testData.B2B.getHomePageUrl(),
					testData.B2B.getB2BUnit(), b2bPage, B2BRole.Buyer,
					buyerAccount);
			Thread.sleep(2000);
			Dailylog.logInfoDB(8, "Successful:"
					+ b2bPage.SuccessfullyAccountCreated.getText(), Store, testName);
			Assert.assertEquals(b2bPage.SuccessfullyAccountCreated.getText(),
					THANKYOUMESSAGE);

			// login to Buyer account
			driver.get(testData.B2B.getHomePageUrl());
			Thread.sleep(2000);
			Dailylog.logInfoDB(10, "Logging to Buyer account.", Store, testName);
			B2BCommon.Login(b2bPage, buyerAccount,
					testData.B2B.getDefaultPassword());
			Thread.sleep(2000);
			Assert.assertEquals(b2bPage.B2BLoginErrorMessage.getText(),
					ERRORMESSAGE);
//			Thread.sleep(2000);
//			Dailylog.logInfoDB(12, "Login to B2B and approving buyer account, "
//					+ buyerAccount, Store, testName);
//            if(Store.equals("AU")){
//            	checkInbox(adminAccount_1);
//    			Thread.sleep(2000);
//            }else if(Store.equals("JP")){
//            	checkInbox("adminjp");
//    			Thread.sleep(2000);
//            }else if(Store.equals("US")){
//            	checkInbox("adminus");
//    			Thread.sleep(2000);
//            }
//			checkInbox(buyerAccount);
//			Thread.sleep(2000);
			
			Thread.sleep(2000);
			driver.get(testData.B2B.getLoginUrl());
			Thread.sleep(2000);
//			if(Store.equals("AU")){
//				B2BCommon.Login(b2bPage, adminAccount_1 + emailDomain,
//						testData.B2B.getDefaultPassword());
//			}else if(Store.equals("JP")){
//				B2BCommon.Login(b2bPage, "adminjp@sharklasers.com",
//						testData.B2B.getDefaultPassword());
//			}else if(Store.equals("US")){
//				B2BCommon.Login(b2bPage, "adminus@sharklasers.com",
//						testData.B2B.getDefaultPassword());
//			}
			B2BCommon.Login(b2bPage, testData.B2B.getAdminId(),
					testData.B2B.getDefaultPassword());

			B2BCommon.approveBuyerAccount(driver, testData, b2bPage,
					buyerAccount);
			
			Thread.sleep(5000);
			Dailylog.logInfoDB(12, "Buyer account : " + buyerAccount
					+ " is approved successfully.", Store, testName);
			
			B2BPage b2bPage = new B2BPage(driver);
			b2bPage.ShippingPage_SignOut.click();
			
//			driver.get(testData.B2B.getLoginUrl());
			Thread.sleep(2000);
			B2BCommon.Login(b2bPage, buyerAccount,
					testData.B2B.getDefaultPassword());
			Thread.sleep(2000);

			String WELCOMEMESSAGE = b2bPage.WelcomeMessage.getText();
			assert WELCOMEMESSAGE.contains("Welcome");
			
			Thread.sleep(2000);
			Dailylog.logInfoDB(12, "Logged in to Buyer account : " + buyerAccount
					+ " successfully.", Store, testName);
//			//Roll back to HMC for admin account
//			rollbackHMC();
//			Thread.sleep(2000);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

//	private void rollbackHMC() {
//		driver.get(testData.HMC.getHomePageUrl());
//		HMCCommon.Login(hmcPage, testData);
//		HMCCommon.searchB2BUnit(hmcPage, testData);
//		hmcPage.B2CUnit_SiteAttributeTab.click();
//		hmcPage.B2BUnit_SA_LenovoAdminEmail.clear();
//		hmcPage.B2BUnit_SA_LenovoAdminEmail.sendKeys("byronc@lenovo.com");
//		hmcPage.B2BUnit_SA_AdminEmail.clear();
//		hmcPage.B2BUnit_SA_AdminEmail.sendKeys("byronc@lenovo.com");
//
//		hmcPage.B2BUnit_Save.click();
//
//		hmcPage.Home_EndSessionLink.click();
//
//	}

//	private void addRoles(WebElement role, String roleName) {
//		Common.rightClick(driver, hmcPage.B2BUnit_SA_UserApproval);
//		hmcPage.B2BUnit_SA_AddRole.click();
//		Common.switchToWindow(driver, 1);
//		Common.doubleClick(driver, role);
//		Dailylog.logInfoDB(6, roleName + " role is updated successfully!!!",
//				Store, testName);
//		Common.switchToWindow(driver, 0);
//	}

//	private void addBuyerRoles(WebElement selectBuyerRole, String buyerRoles) {
//		addRoles(selectBuyerRole, buyerRoles);
//	}

	public void createAccount(WebDriver driver, String B2BUrl, String B2BUnit,
			B2BPage b2bPage, B2BRole role, String tempEmailAddress)
			throws InterruptedException {
		driver.manage().deleteAllCookies();
		driver.get(B2BUrl);
		b2bPage.Login_CreateAnAccountButton.click();
		Common.sleep(2000);
		expandAccessLevel(1);
		Common.sleep(1000);
		B2BCommon.clickCorrectAccessLevel(b2bPage, B2BUnit);
		Common.sleep(1000);
		B2BCommon.clickRoleCheckbox(b2bPage, role);

		b2bPage.Register_EmailTextBox.clear();
		b2bPage.Register_EmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Register_ConfirmEmailTextBox.clear();
		b2bPage.Register_ConfirmEmailTextBox.sendKeys(tempEmailAddress);
		b2bPage.Register_FirstNameTextBox.clear();
		b2bPage.Register_FirstNameTextBox.sendKeys(testData.B2B.getFirstName());
		b2bPage.Register_LastNameTextBox.clear();
		b2bPage.Register_LastNameTextBox.sendKeys(testData.B2B.getLastName());
		b2bPage.Register_PasswordTextBox.clear();
		b2bPage.Register_PasswordTextBox.sendKeys(testData.B2B
				.getDefaultPassword());
		b2bPage.Register_ConfirmPasswordTextBox.clear();
		b2bPage.Register_ConfirmPasswordTextBox.sendKeys(testData.B2B
				.getDefaultPassword());
		if (Common.checkElementExists(driver, b2bPage.Register_acceptterms, 10)) {
			b2bPage.Register_acceptterms.click();
		}
		b2bPage.Register_CreateAccountButton.click();
		Dailylog.logInfoDB(4, "Account is created: " + tempEmailAddress, Store,
				testName);
		Thread.sleep(2000);
	}

	public void expandAccessLevel(int counter) {
		if (Common.isElementExist(driver, By.xpath(expandLevel)) == true
				&& counter < 4) {
			b2bPage.Register_AccessLevelExpand.click();
			expandAccessLevel(++counter);
		}
	}

	private void checkInbox(String tempEmailName) {
		loginGuerrilla(tempEmailName);
		Common.sleep(2000);
		checkMail(1);
	}

	private void loginGuerrilla(String tempEmailName) {
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		Common.sleep(2000);
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(tempEmailName);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(2,"Clicked on Set button",Store,testName);
		Dailylog.logInfoDB(2,"Email with name :"+tempEmailName+"@sharklasers.com",Store,testName);
	}

	private void checkMail(int i) {
		if (Common.isElementExist(driver,
				By.xpath("(//td[contains(.,'Customer')])[1]")) == true) {
			driver.findElement(By.xpath("(//td[contains(.,'Customer')])[1]"))
					.click();
			Common.sleep(2000);
			mailPage.Mail_backToInbox.click();
			Common.sleep(1000);
		} else if (i < 5) {
			Dailylog.logInfoDB(5, "Mail is not received...waiting", Store,
					testName);
			Common.sleep(12000);
			checkMail(++i);
		}
	}
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
}