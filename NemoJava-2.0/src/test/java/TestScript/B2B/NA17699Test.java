package TestScript.B2B;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2BCommon.B2BRole;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17699Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2BPage b2bPage;
	public String email = Common.getDateTimeString() + "1@sharklasers.com";
	public String expandLevel = "(.//div[contains(@class,'expandable-hitarea')])[1]";

	public String orderNum;

	public NA17699Test(String store) {
		this.Store = store;
		this.testName = "NA-17699";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2b"})
	public void NA17699(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			String hmcURL = testData.HMC.getHomePageUrl();
			String b2bURL = testData.B2B.getLoginUrl();
			String b2bUnit = testData.B2B.getB2BUnit();
			
			//============= Step:1 Register an Account and uncheck the checkbox: "I agree to opt-in  ============//
			createAccount(driver, b2bURL, b2bUnit, b2bPage,B2BRole.Builder, email);		
			driver.get(hmcURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Successfully logged into HMC", Store, testName);
			// Searching B2B customer with email ID
			HMCCommon.searchB2BCustomer(hmcPage, email);
			Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
			hmcPage.CustomerAccount_passwordTab.click();
			hmcPage.B2BCustomer_ActiveUserDropdown.click();
			hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
			hmcPage.Common_SaveButton.click();
			// Logging out from HMC
			//hmcPage.Home_EndSessionLink.click();
			//Dailylog.logInfoDB(1, "Successfully logged out from HMC", Store, testName);

			//============= Step:2 Go to HMC and check the newly created account ===============//
			hmcOptInCheck(hmcURL, "False");

			//=========== Step:3 Login to B2B website ==============//
			driver.get(b2bURL);
			B2BCommon.Login(b2bPage, email, testData.B2B.getDefaultPassword());
			b2bPage.homepage_MyAccount.click();
			Dailylog.logInfoDB(3, "My Account is clicked.", Store, testName);
			
			//========= Step:4 Update personal details ==============//
			b2bPage.MyAccountPage_updateDetails.click();
			b2bPage.MyAccountPage_updateDetails.click();
			Dailylog.logInfoDB(4, "Update Details is clicked.", Store, testName);
			Assert.assertEquals(b2bPage.UpdateProfilePage_optInCheckBox.getAttribute("checked"), null);
			
			//========= Step:5 check the OptIn checkbox ==============//
			b2bPage.UpdateProfilePage_optInCheckBox.click();
			b2bPage.UpdateProfilePage_saveProfileDetails.click();
			Dailylog.logInfoDB(5, "Save Profile details is clicked.", Store, testName);
			b2bPage.homepage_Signout.click();
			Dailylog.logInfoDB(5, "Signed out from B2B Website", Store, testName);
			
			//========== Step:6 Check OptIn in HMC ==============//
			hmcOptInCheck(hmcURL, "True");

			//=========== Step:7 Go back to B2B website ===========//
			driver.get(b2bURL);
			B2BCommon.Login(b2bPage, email, testData.B2B.getDefaultPassword());
			Common.sleep(2000);
			b2bPage.homepage_MyAccount.click();
			Common.sleep(1000);
			b2bPage.MyAccountPage_updateDetails.click();
			b2bPage.MyAccountPage_updateDetails.click();
			Common.sleep(1000);
			b2bPage.UpdateProfilePage_optInCheckBox.click();
			b2bPage.UpdateProfilePage_saveProfileDetails.click();
			Common.sleep(2000);
			b2bPage.MyAccountPage_updateDetails.click();
			Common.sleep(1500);
			Assert.assertEquals(b2bPage.UpdateProfilePage_optInCheckBox.getAttribute("checked"), null);
			
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			Dailylog.logInfoDB(7, "Added a product to cart", Store, testName);
			
			//=========== Step:8 Click Checkout button ===========//
			b2bPage.lenovoCheckout.click();
			Dailylog.logInfoDB(8, "Lenovo Checkout is clicked", Store, testName);
			Assert.assertTrue(b2bPage.ShippingPage_optInCheckbox.isDisplayed());

			//=========== Step:9 Uncheck the OptIn Checkbox on Shipping Page ===========//
			b2bPage.ShippingPage_optInCheckbox.click();
			Dailylog.logInfoDB(9, "Check opt in checkbox", Store, testName);
			
			b2bPage.ShippingPage_optInCheckbox.click();
			Dailylog.logInfoDB(9, "UnCheck opt in checkbox", Store, testName);
			
			//=========== Step:10 Placing Order ===========//
			orderNum=B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
			Dailylog.logInfoDB(10, "Order Number is: " + orderNum, Store, testName);

			//=========== Step:11 Checking Order in HMC ===========//
			hmcOptInCheck(hmcURL, "False");
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	public void hmcOptInCheck(String url, String status)throws InterruptedException {
		String currentURL = driver.getCurrentUrl();
		if(!currentURL.contains("true")){
			driver.get(url);
			Common.sleep(3000);
			HMCCommon.Login(hmcPage, testData);
			// Searching B2B customer with email ID
			HMCCommon.searchB2BCustomer(hmcPage, email);
			Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		}
		hmcPage.baseStore_administration.click();
		Dailylog.logInfoDB(2, "Administrator Tab is clicked.", Store, testName);
		String optInStatus = hmcPage.CustomerAccount_selectedOptIn.getAttribute("value");
		assert optInStatus.equalsIgnoreCase(status);
		// Signing out from HMC
		hmcPage.Home_EndSessionLink.click();
	}
	public void expandAccessLevel(int counter){
		if(Common.isElementExist(driver,By.xpath(expandLevel))== true && counter < 4){
			b2bPage.Register_AccessLevelExpand.click();
			expandAccessLevel(++counter);
		}
	}
	public  void createAccount(WebDriver driver, String B2BUrl,String B2BUnit,B2BPage b2bPage,B2BRole role,String tempEmailAddress ) throws InterruptedException{
		driver.manage().deleteAllCookies();
		driver.get(B2BUrl);
		b2bPage.Login_CreateAnAccountButton.click();
		Common.sleep(2000);
		B2CCommon.closeHomePagePopUP(driver);
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
		b2bPage.Register_FirstNameTextBox.sendKeys("AutoFirstName");
		b2bPage.Register_LastNameTextBox.clear();
		b2bPage.Register_LastNameTextBox.sendKeys("AutoLastName");
		b2bPage.Register_PasswordTextBox.clear();
		b2bPage.Register_PasswordTextBox.sendKeys(testData.B2B.getDefaultPassword());
		b2bPage.Register_ConfirmPasswordTextBox.clear();
		b2bPage.Register_ConfirmPasswordTextBox.sendKeys(testData.B2B.getDefaultPassword());
		if(Common.checkElementExists(driver, b2bPage.Register_acceptterms, 10)){
			b2bPage.Register_acceptterms.click();
		}
		b2bPage.Register_CreateAccountButton.click();
		Dailylog.logInfoDB(1, "Account is created: " + tempEmailAddress, Store, testName);
	}

}
