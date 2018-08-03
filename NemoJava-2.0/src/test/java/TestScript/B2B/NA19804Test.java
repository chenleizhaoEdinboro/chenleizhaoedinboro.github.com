package TestScript.B2B;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA19804Test extends SuperTestClass {
	public B2BPage b2bPage;
	private String newPwd = "a12341234";
	String successMsgBox;

	public NA19804Test(String store, String successMsgBox) {
		this.Store = store;
		this.successMsgBox = successMsgBox;
		this.testName = "NA-19804";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2b"})
	public void NA19804(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			// Step 1 Go to B2B website
			driver.get(testData.B2B.getLoginUrl());
			// Step 2 Login B2B
			B2BCommon.Login(b2bPage, testData.B2B.getResetPasswordId(), testData.B2B.getDefaultPassword());

			if (Common.checkElementDisplays(driver, b2bPage.homepage_MyAccount, 3)) {
				// Step 3 click My Account and click Change your password
				b2bPage.homepage_MyAccount.click();
				b2bPage.MyAccountPage_changeYourPassword.click();
				// click Current Password
				b2bPage.UpdatePasswordPage_currentPassword.click();
				// Fill in the "Current Password" textBox with current password
				b2bPage.UpdatePasswordPage_currentPassword.sendKeys(testData.B2B.getDefaultPassword());
				// click New Password
				b2bPage.UpdatePasswordPage_newPassword.click();
				// fill in the "New Password " textBox with new password
				b2bPage.UpdatePasswordPage_newPassword.sendKeys(newPwd);
				// click Confirm New Password
				b2bPage.UpdatePasswordPage_confirmNewPassword.click();
				// fill in the "Confirm New Password" textBox with new password
				b2bPage.UpdatePasswordPage_confirmNewPassword.sendKeys(newPwd);

				// click "Update Password" button
				b2bPage.UpdatePasswordPage_updatePassword.click();
				Thread.sleep(5000);
				Assert.assertEquals(b2bPage.ProfilePage_successMsgBox.getText(), successMsgBox);

				// change the password to the original
				b2bPage.ProfilePage_changeYourPassword.click();
				b2bPage.UpdatePasswordPage_currentPassword.click();
				b2bPage.UpdatePasswordPage_currentPassword.sendKeys(newPwd);
				b2bPage.UpdatePasswordPage_newPassword.click();
				b2bPage.UpdatePasswordPage_newPassword.sendKeys(testData.B2B.getDefaultPassword());
				b2bPage.UpdatePasswordPage_confirmNewPassword.click();
				b2bPage.UpdatePasswordPage_confirmNewPassword.sendKeys(testData.B2B.getDefaultPassword());
				b2bPage.UpdatePasswordPage_updatePassword.click();
			} else {
				B2BCommon.Login(b2bPage, testData.B2B.getResetPasswordId(), newPwd);
				// Step 3 click My Account and click Change your password
				b2bPage.homepage_MyAccount.click();
				b2bPage.MyAccountPage_changeYourPassword.click();
				// click Current Password
				b2bPage.UpdatePasswordPage_currentPassword.click();
				// Fill in the "Current Password" textBox with current password
				b2bPage.UpdatePasswordPage_currentPassword.sendKeys(newPwd);
				// click New Password
				b2bPage.UpdatePasswordPage_newPassword.click();
				// fill in the "New Password " textBox with new password
				b2bPage.UpdatePasswordPage_newPassword.sendKeys(testData.B2B.getDefaultPassword());
				// click Confirm New Password
				b2bPage.UpdatePasswordPage_confirmNewPassword.click();
				// fill in the "Confirm New Password" textBox with new password
				b2bPage.UpdatePasswordPage_confirmNewPassword.sendKeys(testData.B2B.getDefaultPassword());

				// click "Update Password" button
				b2bPage.UpdatePasswordPage_updatePassword.click();
				Thread.sleep(5000);
				Assert.assertEquals(b2bPage.ProfilePage_successMsgBox.getText(), successMsgBox);
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
