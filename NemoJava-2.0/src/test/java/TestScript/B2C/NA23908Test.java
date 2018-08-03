package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA23908Test extends SuperTestClass {
	public NA23908Test(String store) {
		this.Store = store;
		this.testName = "NA-23908";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p1", "b2c", "compatibility"})
	public void NA23908(ITestContext ctx) {
		try {
			this.prepareTest();
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());

			B2CPage b2cPage = new B2CPage(driver);
			b2cPage.SMB_CreateAnAccountButton.click();

			// Input values
			String tempEmailAddress = EMailCommon.getRandomEmailAddress();
			b2cPage.SMB_FirstName.sendKeys("AutoF");
			b2cPage.SMB_LastName.sendKeys("AutoL");
			b2cPage.SMB_Email.sendKeys(tempEmailAddress);
			b2cPage.SMB_CompanyName.sendKeys("AutoCompany");
			b2cPage.SMB_Password.sendKeys("1q2w3e4r");
			b2cPage.SMB_ConfirmPassword.sendKeys("1q2w3e4r");
			b2cPage.SMB_CompanyAddress1.sendKeys("AutoCompanyAddress1");
//			b2cPage.SMB_CompanyAddress2.sendKeys("AutoCompanyAddress2");
			b2cPage.SMB_City.sendKeys("AutoCompanyCity");
			if(this.Store.contains("US"))
				b2cPage.SMB_ZipCode.sendKeys("12345");
			else
				b2cPage.SMB_ZipCode.sendKeys("1234567");
			Select dropdown = new Select(b2cPage.SMB_State);
			dropdown.selectByIndex(0);
			dropdown = new Select(b2cPage.SMB_Industry);
			dropdown.selectByIndex(0);
			dropdown = new Select(b2cPage.SMB_CompanySize);
			dropdown.selectByIndex(0);
//			dropdown = new Select(b2cPage.SMB_Department);
//			dropdown.selectByVisibleText("Sales");
			if(Common.checkElementDisplays(driver, b2cPage.updateCompanyProfile_CompanyYear, 1))
			{
				b2cPage.updateCompanyProfile_CompanyYear.clear();
				b2cPage.updateCompanyProfile_CompanyYear.sendKeys("1999");
			}
			if(Common.checkElementDisplays(driver, b2cPage.SMB_Phone, 1))
			{
				b2cPage.SMB_Phone.clear();
				b2cPage.SMB_Phone.sendKeys("1234");
			}
			b2cPage.SMB_SubmitButton.click();

			if(!Common.checkElementDisplays(driver, b2cPage.RegistrateAccount_ThankYouMessage, 10))
					Assert.fail("Create user thank you page is not shown!");
			
			// Active account
			EMailCommon.activeNewAccount(driver,tempEmailAddress, 1, testData.Store);

			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			Thread.sleep(13000);
			b2cPage.SMB_LoginButton.click();
			Thread.sleep(5000);
			B2CCommon.login(b2cPage, tempEmailAddress, "1q2w3e4r");

			if(!Common.isElementExist(driver, By.xpath("//ul[@class='menu general_Menu']//*[@id='myAccount']//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'logout')]/div")))
				Assert.fail("New account login failed!");
			
			Common.javascriptClick(driver, b2cPage.Navigation_SignOutLink);
			Thread.sleep(5000);
			
			b2cPage.SMB_LoginButton.click();
			Thread.sleep(5000);
			if(this.Store.contains("US"))
				B2CCommon.login(b2cPage, "lisong2@lenovo.com", "1q2w3e4r");
			else
				B2CCommon.login(b2cPage, "lixe1@lenovo.com", "1q2w3e4r");
			
			if(!Common.isElementExist(driver, By.xpath("//ul[@class='menu general_Menu']//*[@id='myAccount']//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'logout')]/div")))
				Assert.fail("Existing account login failed!");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
