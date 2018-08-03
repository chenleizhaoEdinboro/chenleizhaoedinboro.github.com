package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
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

public class NA18973Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	private String emailDomain = "@SHARKLASERS.COM";
	private String today;
	private String builderAccount;
	private String expandLevel = "(.//div[contains(@class,'expandable-hitarea')])[1]";
	private String THANKYOUMESSAGE = "Thank You For Creating An Account";
	private String company = "Invalid";

	public NA18973Test(String store) {
		this.Store = store;
		this.testName = "NA-18973";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2b"})
	public void NA18973(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			today = Common.getDateTimeString();
			builderAccount = "builder" + today + emailDomain;
			// Login B2B store and find an agreement product
			Dailylog.logInfoDB(1, "Login to HMC...", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(2000);
			Dailylog.logInfoDB(1, "Searching unit id...", Store, testName);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Thread.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(1000);
			Dailylog.logInfoDB(2, "Adding new Approver account...", Store,
					testName);
			hmcPage.B2BUnit_SA_LenovoAdminEmail.clear();
			hmcPage.B2BUnit_SA_LenovoAdminEmail
					.sendKeys("auapprover@SHARKLASERS.COM,byronc@lenovo.com");
			hmcPage.B2BUnit_SA_AdminEmail.clear();
			hmcPage.B2BUnit_SA_AdminEmail
					.sendKeys("auapprover@SHARKLASERS.COM,byronc@lenovo.com");
			Thread.sleep(1000);
			Dailylog.logInfoDB(2, "Adding builder roles...", Store, testName);
//			addBuilderRoles(hmcPage.B2BUnit_SA_SelectBuilderRole,
//					"Builder account");
			Thread.sleep(2000);
			System.out.println("Roles are created successfully.");
			Dailylog.logInfoDB(2,
					"Set, require approver for builder account...", Store,
					testName);
			hmcPage.BuilderAccountApproverYes.click();
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "clicked on save button...", Store, testName);
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			hmcPage.Home_EndSessionLink.click();
			Dailylog.logInfoDB(2, "Logged out from HMC...", Store, testName);
			Thread.sleep(2000);

			// Creating B2B Builder account
			Dailylog.logInfoDB(4, "Creating builder account...", Store,
					testName);
			
			createAccount(driver, testData.B2B.getHomePageUrl(),
					testData.B2B.getB2BUnit(), b2bPage, B2BRole.Builder,
					builderAccount);
			Thread.sleep(10000);
			Dailylog.logInfoDB(4, "Successful:"
					+ b2bPage.SuccessfullyAccountCreated.getText(), Store, testName);
			Assert.assertEquals(b2bPage.SuccessfullyAccountCreated.getText(),
					THANKYOUMESSAGE);
			Dailylog.logInfoDB(3, "Builder account created successfuly...",
					Store, testName);

			// login to builder account
			Dailylog.logInfoDB(5,
					"Checking approver account to approver builder account...",
					Store, testName);
			checkInbox("auapprover");
			Thread.sleep(2000);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void addRoles(WebElement role, String roleName) {
		Common.rightClick(driver, hmcPage.B2BUnit_SA_UserApproval);
		hmcPage.B2BUnit_SA_AddRole.click();
		switchToWindow(1);
		Common.doubleClick(driver, role);
		System.out.println(roleName + " role is updated successfully!!!");
		switchToWindow(0);
	}

	private void addBuilderRoles(WebElement selectBuilderRole,
			String builderRoles) {
		addRoles(selectBuilderRole, builderRoles);
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(
					driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void checkInbox(String tempEmailName) {
		loginGuerrilla(tempEmailName);
		Common.sleep(2000);
		checkMail(1);
	}

	public void loginGuerrilla(String tempEmailName) {
		Dailylog.logInfoDB(5, "Login to email account", Store, testName);
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		Common.sleep(2000);
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(tempEmailName);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(2, "Clicked on Set button", Store, testName);
		Dailylog.logInfoDB(2, "Email with name :" + tempEmailName
				+ "@sharklasers.com", Store, testName);
	}

	public void checkMail(int i) {
		if (Common.isElementExist(driver,
				By.xpath("(//td[contains(.,'Customer')])[1]")) == true) {
			driver.findElement(By.xpath("(//td[contains(.,'Customer')])[1]"))
					.click();
			Common.sleep(2000);
			String name = mailPage.Email_Name.getText();
			Dailylog.logInfoDB(6, "Name : " + name, Store, testName);
			Assert.assertTrue(name.contains(testData.B2B.getFirstName() + " " + testData.B2B.getLastName()));
			String store = mailPage.Email_Store.getText();
			Dailylog.logInfoDB(6, "Store : " + store, Store, testName);
			Assert.assertTrue(store.contains(company));
			Dailylog.logInfoDB(6,
					"Email Content : " + mailPage.Email_Content.getText(),
					Store, testName);

			mailPage.Mail_backToInbox.click();
			Common.sleep(1000);
		} else if (i < 5) {
			Dailylog.logInfoDB(5, "Mail is not received...waiting", Store,
					testName);
			Common.sleep(12000);
			checkMail(++i);
		}
	}

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
		company = driver.findElement(By.xpath("//ul[@id='defaultDmuAndSoldTo']//td[@data-title='Store Name']")).getText();
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

}