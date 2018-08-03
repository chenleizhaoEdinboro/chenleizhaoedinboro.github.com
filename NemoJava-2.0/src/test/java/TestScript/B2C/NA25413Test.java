package TestScript.B2C;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA25413Test extends SuperTestClass {
	public NA25413Test(String store) {
		this.Store = store;
		this.testName = "NA-25413";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA25413(ITestContext ctx) {
		try {
			this.prepareTest();
			driver.get(testData.B2C.getHomePageUrl());

			B2CPage b2cPage = new B2CPage(driver);
			b2cPage.SMB_LoginButton.click();
			if(this.Store.contains("US"))
				B2CCommon.login(b2cPage, "lisong2@lenovo.com", "1q2w3e4r");
			else
				B2CCommon.login(b2cPage, "lixe1@lenovo.com", "1q2w3e4r");

			Common.javascriptClick(driver, b2cPage.Navigation_SignInLink);

			// Update personal profile
			b2cPage.myAccount_updatePersonalDetails.click();
			b2cPage.updateProfile_updatePersonalDetails.click();

			Select titleName = new Select(b2cPage.updateProfile_Title);
			String title = titleName.getFirstSelectedOption().getText();
			if (title.equals("Mr"))
				titleName.selectByVisibleText("Mrs");
			else
				titleName.selectByVisibleText("Mr");
			title = titleName.getFirstSelectedOption().getText();

			b2cPage.updateProfile_fName.clear();
			String newFName = "AutoFirstName" + Common.getDateTimeString();
			b2cPage.updateProfile_fName.sendKeys(newFName);

			b2cPage.updateProfile_lName.clear();
			String newLName = "AutoLastName" + Common.getDateTimeString();
			b2cPage.updateProfile_lName.sendKeys(newLName);

			b2cPage.updateProfile_saveUpdatesButton.click();

			if (!b2cPage.updateProfile_profileInfoTable.getText().contains(newFName)
					|| !b2cPage.updateProfile_profileInfoTable.getText().contains(newLName)
					|| !b2cPage.updateProfile_profileInfoTable.getText().contains(title)) {
				Assert.fail("User profile is not updated!");
			}

			// Update company profile
			b2cPage.myAccount_updateCompanyInfoNav.click();
			b2cPage.updateProfile_updateCompanyInfoButton.click();
			Thread.sleep(3000);

			Select industry = new Select(b2cPage.updateCompanyProfile_Industry);
			Select state = new Select(b2cPage.updateCompanyProfile_State);
			Select companySize = new Select(b2cPage.updateCompanyProfile_CompanySize);

			// boolean departDisplay = false;
			String departmentStr = "";
			if (Common.checkElementDisplays(driver, b2cPage.updateCompanyProfile_Department, 1)) {
				// departDisplay = true;
				Select department = new Select(b2cPage.updateCompanyProfile_Department);
				departmentStr = department.getFirstSelectedOption().getText();
				
				if (departmentStr.equals(department.getOptions().get(0).getText()))
					department.selectByIndex(1);
				else
					department.selectByIndex(0);
				departmentStr = department.getFirstSelectedOption().getText();
			}

			String industryStr = industry.getFirstSelectedOption().getText();
			if (industryStr.equals(industry.getOptions().get(0).getText()))
				industry.selectByIndex(1);
			else
				industry.selectByIndex(0);
			industryStr = industry.getFirstSelectedOption().getText();

			String stateStr = state.getFirstSelectedOption().getText();
			if (stateStr.equals(state.getOptions().get(0).getText()))
				state.selectByIndex(1);
			else
				state.selectByIndex(0);
			stateStr = state.getFirstSelectedOption().getText();

			String companySizeStr = companySize.getFirstSelectedOption().getText();
			if (companySizeStr.equals(companySize.getOptions().get(0).getText()))
				companySize.selectByIndex(1);
			else
				companySize.selectByIndex(0);
			companySizeStr = companySize.getFirstSelectedOption().getText();

			String address1 = "AutoAddress" + Common.getDateTimeString();
			String city = "AutoCity" + Common.getDateTimeString();
			String zipCode = "";
			if(this.Store.contains("US"))
				zipCode = Common.getDateTimeString().substring(8, 13);
			else
				zipCode = Common.getDateTimeString().substring(6, 13);
			String companyYear = Common.getDateTimeString().substring(8, 12);

			b2cPage.updateCompanyProfile_Address1.clear();
			b2cPage.updateCompanyProfile_Address1.sendKeys(address1);
			b2cPage.updateCompanyProfile_City.clear();
			b2cPage.updateCompanyProfile_City.sendKeys(city);
			b2cPage.updateCompanyProfile_ZipCode.clear();
			b2cPage.updateCompanyProfile_ZipCode.sendKeys(zipCode);
			if (Common.checkElementDisplays(driver, b2cPage.updateCompanyProfile_CompanyYear, 1)) {
				b2cPage.updateCompanyProfile_CompanyYear.clear();
				b2cPage.updateCompanyProfile_CompanyYear.sendKeys(companyYear);
			}
			if(Common.checkElementDisplays(driver, b2cPage.SMB_Phone, 1))
			{
				b2cPage.SMB_Phone.clear();
				b2cPage.SMB_Phone.sendKeys("4321");
			}
			b2cPage.updateCompanyProfile_SaveUpdateButton.click();
			Thread.sleep(3000);

			if (!b2cPage.updateProfile_CompanyInfoTable.getText().contains(address1)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(city)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(zipCode)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(companyYear)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(companySizeStr)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(stateStr)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(industryStr)
					|| !b2cPage.updateProfile_CompanyInfoTable.getText().contains(departmentStr)) {
				Assert.fail("Company profile udpate failed!");
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
