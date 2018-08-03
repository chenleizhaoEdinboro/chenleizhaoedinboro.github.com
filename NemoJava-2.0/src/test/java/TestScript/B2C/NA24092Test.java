package TestScript.B2C;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA24092Test extends SuperTestClass {
	public HMCPage hmcPage;
	String IdentifyCode = "SMBTest_24092ID";
	String Name = "SMBTest_24092Name";
	String SiteLogo = "";
	String CompanyName = "AutoTest_24902";
	String CompanyAddress1 = "SMBTest_24092 Company Address 1";
	String CompanyAddress2 = "SMBTest_24092 Company Address 2";
	String City = "Los Angeles";
	String Zipcode = "90064";
	String CompanyYear = "2008";
	String IframeXpath = ".//iframe[contains(@src,'showSMBQuickSetup')]";
	String CustomerGroupResultXpath = ".//*[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCustomerGroup]']//tr[contains(@id,'SMBTest_24092ID')]/td[last()]//a";
	String ComapanyResultXpath = ".//*[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCompany]']//div[contains(@id,'AutoTest_24902')]";

	public NA24092Test(String store) {
		this.Store = store;
		this.testName = "NA-24092";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA24092(ITestContext ctx) {
		try {
			this.prepareTest();

			hmcPage = new HMCPage(driver);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_SMB.click();
			rollback();
			hmcPage.SMB_QuickSetup.click();
			Common.sleep(2000);
			driver.switchTo().frame(driver.findElement(By.xpath(IframeXpath)));
			String unit = testData.B2C.getUnit();
			By unitSelectXpath = By.xpath("//select[@id='template']/option[@value='" + unit + "']");
			driver.findElement(unitSelectXpath).click();
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_IdentifyCode, IdentifyCode);
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_Name, Name);
			hmcPage.SMB_QuickSetup_SiteLogo_SelectMedia.click();
			hmcPage.SMB_QuickSetup_SelectMedia_Search.click();
			Common.waitElementClickable(driver, hmcPage.SMB_QuickSetup_SelectMedia_FirstResult, 30);
			Common.sleep(2000);
			hmcPage.SMB_QuickSetup_SelectMedia_FirstResult.click();
			hmcPage.SMB_QuickSetup_SelectMedia_UseMedia.click();
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_CompanyName, CompanyName);
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_CompanyAddress1, CompanyAddress1);
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_CompanyAddress2, CompanyAddress2);
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_City, City);
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_Zipcode, Zipcode);
			hmcPage.SMB_QuickSetup_StateSelect.click();
			hmcPage.SMB_QuickSetup_Industry.click();
			hmcPage.SMB_QuickSetup_CompanySize.click();
			Common.sendFieldValue(hmcPage.SMB_QuickSetup_CompanyYear, CompanyYear);		
			Common.sleep(2000);
			hmcPage.SMB_QuickSetup_CreateButton.click();

			driver.switchTo().defaultContent();
			hmcPage.SMB_CustomerGroup.click();
			Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, IdentifyCode);
			Common.sleep(2000);
			hmcPage.SMB_CustomerGroup_Srarch.click();
			Assert.assertEquals(true, Common.isElementExist(driver, By.xpath(CustomerGroupResultXpath), 10));
			hmcPage.SMB_SMBCompany.click();
			Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, CompanyName);
			Common.sleep(2000);
			hmcPage.SMB_SMBCompany_Search.click();
			Assert.assertEquals(true, Common.isElementExist(driver, By.xpath(ComapanyResultXpath), 10));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void rollback() {
		hmcPage.SMB_CustomerGroup.click();
		Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, IdentifyCode);
		Common.sleep(2000);
		hmcPage.SMB_CustomerGroup_Srarch.click();
		if (Common.isElementExist(driver, By.xpath(CustomerGroupResultXpath), 10)) {
			driver.findElement(By.xpath(CustomerGroupResultXpath)).click();
			hmcPage.SMB_SMBCompanyAndCustomer_Delete.click();
			Common.sleep(2000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SMB_CustomerGroup_SearchToggle.click();
			Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, IdentifyCode);
			Common.sleep(2000);
			hmcPage.SMB_CustomerGroup_Srarch.click();
			Assert.assertEquals(false, Common.isElementExist(driver, By.xpath(CustomerGroupResultXpath), 10));
		}
		hmcPage.SMB_SMBCompany.click();
		Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, CompanyName);
		Common.sleep(2000);
		hmcPage.SMB_SMBCompany_Search.click();
		if (Common.isElementExist(driver, By.xpath(ComapanyResultXpath), 10)) {
			Common.doubleClick(driver, driver.findElement(By.xpath(ComapanyResultXpath)));
			hmcPage.SMB_SMBCompanyAndCustomer_Delete.click();
			Common.sleep(2000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SMB_SMBCompany_SearchToggle.click();
			Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, CompanyName);
			Common.sleep(2000);
			hmcPage.SMB_SMBCompany_Search.click();
			Assert.assertEquals(false, Common.isElementExist(driver, By.xpath(ComapanyResultXpath), 10));
		}

	}

}
