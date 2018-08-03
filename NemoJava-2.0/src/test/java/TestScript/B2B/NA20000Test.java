package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.B2BCommon.B2BRole;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA20000Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String expandLevel = "(.//div[contains(@class,'expandable-hitarea')])[1]";

	public NA20000Test(String store) {
		this.Store = store;
		this.testName = "NA-20000";
	}

	@Test(alwaysRun = true)
	public void NA20000(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			// Login B2B store and find an agreement product
			Dailylog.logInfoDB(1, "Logging to HMC site.", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(2000);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Thread.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(1000);
			addAdminRoles(hmcPage.B2BUnit_SA_SelectAdminRole, "Admin account");
			Thread.sleep(2000);
			addApproverRoles(hmcPage.B2BUnit_SA_SelectApproverRole, "Approver account");
			Thread.sleep(2000);
			addBuyerRoles(hmcPage.B2BUnit_SA_SelectBuyerRole, "Buyer account");
			Thread.sleep(2000);
			addBuilderRoles(hmcPage.B2BUnit_SA_SelectBuilderRole, "Builder account");
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Roles are created successfully.", Store, testName);

			int count = driver
					.findElements(By.xpath(
							".//input[contains(@id,'[in Content/Attribute[B2BUnit.needApproveUserRoles]]_false')]"))
					.size();
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Roles available : " + count, Store, testName);

			for (int i = 1; i <= count; i++) {
				Thread.sleep(1000);
				driver.findElement(By
						.xpath("(.//input[contains(@id,'[in Content/Attribute[B2BUnit.needApproveUserRoles]]_false')])["
								+ i + "]"))
						.click();
			}

			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			hmcPage.Home_EndSessionLink.click();
			Thread.sleep(2000);

			// Creating B2B Approver1 account
			createAccount(driver, testData.B2B.getHomePageUrl(), testData.B2B.getB2BUnit(), b2bPage, B2BRole.Approver,
					testData.B2B.getApproverId());

			// // login to Approver one account
			// driver.get(testData.B2B.getHomePageUrl());
			// Thread.sleep(2000);
			// B2BCommon.Login(b2bPage, testData.B2B.getApproverId(),
			// testData.B2B.getDefaultPassword());
			// Thread.sleep(2000);
			// Dailylog.logInfoDB(4, testData.B2B.getApproverId()
			// + " logged in successfully!!!", Store, testName);
			// Thread.sleep(2000);

			// Creating B2B Approver2 account
			createAccount(driver, testData.B2B.getLoginUrl(), testData.B2B.getB2BUnit(), b2bPage, B2BRole.Approver,
					testData.B2B.getApproverId2());

			// Creating B2B Admin account
			createAccount(driver, testData.B2B.getLoginUrl(), testData.B2B.getB2BUnit(), b2bPage, B2BRole.Admin,
					testData.B2B.getAdminId());

			// Creating B2B Buyer account
			createAccount(driver, testData.B2B.getLoginUrl(), testData.B2B.getB2BUnit(), b2bPage, B2BRole.Buyer,
					testData.B2B.getBuyerId());

			// Creating B2B Builder account
			createAccount(driver, testData.B2B.getLoginUrl(), testData.B2B.getB2BUnit(), b2bPage, B2BRole.Builder,
					testData.B2B.getBuilderId());

			// Creating B2B password account
			createAccount(driver, testData.B2B.getLoginUrl(), testData.B2B.getB2BUnit(), b2bPage, B2BRole.Buyer,
					testData.B2B.getResetPasswordId());

			// Adding Nemo B2B Quote Review roles to approval roles
			addNemoB2BQuoteReview(testData.B2B.getApproverId());
			Thread.sleep(2000);
			addNemoB2BQuoteReview(testData.B2B.getApproverId2());
			Thread.sleep(2000);
			Dailylog.logInfoDB(12, "Scripts executed successfully!!!", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void addNemoB2BQuoteReview(String approverAccount) throws InterruptedException {
		Dailylog.logInfoDB(10, "Logging to HMC site...", Store, testName);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		Thread.sleep(2000);
		Dailylog.logInfoDB(10, "Logging to HMC site successfully...", Store, testName);
		Dailylog.logInfoDB(10, "Searching b2b customer : " + approverAccount, Store, testName);
		HMCCommon.searchB2BCustomer(hmcPage, approverAccount);
		Thread.sleep(2000);
		hmcPage.B2BCustomer_1stSearchedResult.click();
		Thread.sleep(2000);
		hmcPage.B2BCustomer_SearchIcon.click();
		Dailylog.logInfoDB(10, "Select searched result...", Store, testName);
		Thread.sleep(2000);
		switchToWindow(1);
		Thread.sleep(2000);
		hmcPage.B2BCustomer_PrincipalGroupId.clear();
		Thread.sleep(2000);
		hmcPage.B2BCustomer_PrincipalGroupId.sendKeys("nemob2b_quote_reviewer");
		Thread.sleep(2000);
		hmcPage.B2BCustomer_Search.click();
		Thread.sleep(2000);
		Common.doubleClick(driver, hmcPage.B2BCustomer_NemoB2BQuoteReview);
		Thread.sleep(2000);
		switchToWindow(0);
		Thread.sleep(2000);
		hmcPage.B2BUnit_Save.click();
		Dailylog.logInfoDB(10, "Selected NemoB2BQuoteReview option.", Store, testName);
		Thread.sleep(2000);
		hmcPage.Home_EndSessionLink.click();
		Thread.sleep(2000);
		driver.get(testData.B2B.getLoginUrl());
		Thread.sleep(2000);
	}

	private void addRoles(WebElement role, String roleName) throws InterruptedException {
		Common.rightClick(driver, hmcPage.B2BUnit_SA_UserApproval);
		hmcPage.B2BUnit_SA_AddRole.click();
		Thread.sleep(5000);
		switchToWindow(1);
		Common.doubleClick(driver, role);
		Dailylog.logInfoDB(2, roleName + " role is updated successfully!!!", Store, testName);
		switchToWindow(0);
	}

	private void addAdminRoles(WebElement selectAdminRole, String adminRole) throws InterruptedException {
		addRoles(selectAdminRole, adminRole);
	}

	private void addApproverRoles(WebElement selectApproverRole, String approverRoles) throws InterruptedException {
		addRoles(selectApproverRole, approverRoles);
	}

	private void addBuyerRoles(WebElement selectBuyerRole, String buyerRoles) throws InterruptedException {
		addRoles(selectBuyerRole, buyerRoles);
	}

	private void addBuilderRoles(WebElement selectBuilderRole, String builderRoles) throws InterruptedException {
		addRoles(selectBuilderRole, builderRoles);
	}

	public void createAccount(WebDriver driver, String B2BUrl, String B2BUnit, B2BPage b2bPage, B2BRole role,
			String tempEmailAddress) throws InterruptedException {
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
		b2bPage.Register_PasswordTextBox.sendKeys(testData.B2B.getDefaultPassword());
		b2bPage.Register_ConfirmPasswordTextBox.clear();
		b2bPage.Register_ConfirmPasswordTextBox.sendKeys(testData.B2B.getDefaultPassword());
		if (Common.checkElementDisplays(driver, b2bPage.Register_acceptterms, 10)) {
			b2bPage.Register_acceptterms.click();
		}
		b2bPage.Register_CreateAccountButton.click();
		Dailylog.logInfoDB(4, "Account is created: " + tempEmailAddress, Store, testName);
		Thread.sleep(2000);
	}

	public void expandAccessLevel(int counter) {
		if (Common.isElementExist(driver, By.xpath(expandLevel)) == true && counter < 4) {
			b2bPage.Register_AccessLevelExpand.click();
			expandAccessLevel(++counter);
		}
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}