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

public class NA26821Test extends SuperTestClass {
	public NA26821Test(String store) {
		this.Store = store;
		this.testName = "NA-26821";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p1", "b2c", "compatibility"})
	public void NA26821(ITestContext ctx) {
		try {
			this.prepareTest();
			String tempEmailAddress = EMailCommon.getRandomEmailAddress();

			Common.NavigateToUrl(driver, Browser, testData.envData.getHttpsDomain() + "/au/en/login");
			B2CPage b2cPage = new B2CPage(driver);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			b2cPage.Login_CreateAnAccountButton.click();

			// Input all of the valid details then click create account
			b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
			NA15480Test.inputValuesAndClickCreate(b2cPage, tempEmailAddress, tempEmailAddress, "1q2w3e4r", "1q2w3e4r");

			// Active account
			EMailCommon.activeNewAccount(driver,tempEmailAddress, 1, testData.Store);
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			Thread.sleep(13000);
			
			b2cPage.SMB_LoginButton.click();
			Common.sleep(2000);
			B2CCommon.login(b2cPage, tempEmailAddress, "1q2w3e4r");
			
			b2cPage.SMB_FirstName.sendKeys("AutoF");
			b2cPage.SMB_LastName.sendKeys("AutoL");
			b2cPage.SMB_CompanyName.sendKeys("AutoCompany");
			b2cPage.SMB_CompanyAddress1.sendKeys("AutoCompanyAddress1");
//			b2cPage.SMB_CompanyAddress2.sendKeys("AutoCompanyAddress2");
			b2cPage.SMB_City.sendKeys("AutoCompanyCity");
			if(this.Store.contains("US"))
				b2cPage.SMB_ZipCode.sendKeys("12345");
			else
				b2cPage.SMB_ZipCode.sendKeys("1234567");
			Select dropdown = new Select(b2cPage.SMB_State);
//			if(this.Store.contains("US"))
//				dropdown.selectByVisibleText("Alaska");
//			else
//				dropdown.selectByVisibleText("三重県");
			dropdown.selectByIndex(1);
			dropdown = new Select(b2cPage.SMB_Industry);
//			if(this.Store.contains("US"))
//				dropdown.selectByVisibleText("Retail");
//			else
//				dropdown.selectByVisibleText("不動産業");
			dropdown.selectByIndex(1);
			dropdown = new Select(b2cPage.SMB_CompanySize);
//			dropdown.selectByVisibleText("Zero");
			dropdown.selectByIndex(1);
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
			
			if(!Common.isElementExist(driver, By.xpath("//ul[@class='menu general_Menu']//*[@id='myAccount']//li[not(contains(@class,'hidden'))]/div/a[contains(@href,'logout')]/div")))
				Assert.fail("New account login failed!");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
