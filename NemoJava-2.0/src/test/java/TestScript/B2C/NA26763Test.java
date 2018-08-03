package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA26763Test extends SuperTestClass {
	public NA26763Test(String store) {
		this.Store = store;
		this.testName = "NA-26763";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA26763(ITestContext ctx) {
		try {
			this.prepareTest();
			driver.manage().deleteAllCookies();

			B2CPage b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.SMB_SwitchSMBProfile_Yes.click();
			
			List<WebElement> rows = driver.findElements(By.xpath("//tbody[@id='Content/GenericResortableItemList[9]_tbody']/tr"));
			Common.scrollToElement(driver, rows.get(0));
			Select dropdown;
			boolean isEditable = false,isMandatory = false,isDisplay = false;
			for(int i = 0; i < rows.size(); i++)
			{
				dropdown = new Select(rows.get(i).findElement(By.xpath(".//select")));
				if(dropdown.getFirstSelectedOption().getText().contains("zCompanyAddress2"))
				{
					// Editable
					rows.get(i).findElement(By.xpath(".//input[contains(@id, '[editable]_checkbox')]")).click();
					isEditable = rows.get(i).findElement(By.xpath(".//input[contains(@id, '[editable]_checkbox')]/../input[1]")).getAttribute("value").equals("true");
				}
				if(dropdown.getFirstSelectedOption().getText().contains("zDepartment"))
				{
					// Display
					rows.get(i).findElement(By.xpath(".//input[contains(@id, '[display]_checkbox')]")).click();
					isDisplay = rows.get(i).findElement(By.xpath(".//input[contains(@id, '[display]_checkbox')]/../input[1]")).getAttribute("value").equals("true");
				}
				if(dropdown.getFirstSelectedOption().getText().contains("zCompanyAddress1"))
				{
					// Mandatory
					rows.get(i).findElement(By.xpath(".//input[contains(@id, '[mandatory]_checkbox')]")).click();
					isMandatory = rows.get(i).findElement(By.xpath(".//input[contains(@id, '[mandatory]_checkbox')]/../input[1]")).getAttribute("value").equals("true");
				}
			}
			hmcPage.SaveButton.click();
			Thread.sleep(10000);
			
			// Registration
			driver.get(testData.B2C.getHomePageUrl() + "/smbRegistration");
			boolean RegistrationAddress2Editable, RegistrationDepartmentDisplay, RegistrationZipCodeMandatory;
			if(b2cPage.SMB_CompanyAddress2.getAttribute("disabled") == null)
				RegistrationAddress2Editable = true;
			else
				RegistrationAddress2Editable = false;
//			RegistrationAddress2Editable = !b2cPage.SMB_CompanyAddress2.getAttribute("disabled").equals("disabled");
			RegistrationDepartmentDisplay = Common.checkElementDisplays(driver, b2cPage.SMB_Department, 1);
			RegistrationZipCodeMandatory = b2cPage.SMB_CompanyAddress1.getAttribute("mandatory").equals("true");
			
			if(RegistrationAddress2Editable!=isEditable || RegistrationDepartmentDisplay!=isDisplay || RegistrationZipCodeMandatory!=isMandatory)
				Assert.fail("Changes doesn't reflect in registration page!");
			
			// Update profile
			driver.get(testData.B2C.getHomePageUrl());
			b2cPage.SMB_LoginButton.click();
			B2CCommon.login(b2cPage, "lisong2@lenovo.com", "1q2w3e4r");
//			b2cPage.MyAccount_myAccount.click();
			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl() + "my-account");
			b2cPage.myAccount_updateCompanyInfoNav.click();
			b2cPage.updateProfile_updateCompanyInfoButton.click();
			
			boolean UpdateAddress2Editable, UpdateDepartmentDisplay, UpdateZipCodeMandatory;
			if(b2cPage.updateCompanyProfile_Address2.getAttribute("disabled") == null)
				UpdateAddress2Editable = true;
			else
				UpdateAddress2Editable = false;
//			UpdateAddress2Editable = !b2cPage.updateCompanyProfile_Address2.getAttribute("disabled").equals("disabled");
			UpdateDepartmentDisplay = Common.checkElementDisplays(driver, b2cPage.updateCompanyProfile_Department, 1);
			UpdateZipCodeMandatory = b2cPage.updateCompanyProfile_Address1.getAttribute("mandatory").equals("true");
			
			if(UpdateAddress2Editable!=isEditable || UpdateDepartmentDisplay!=isDisplay || UpdateZipCodeMandatory!=isMandatory)
				Assert.fail("Changes doesn't reflect in update profile page!");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
