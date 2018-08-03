package TestScript.B2C;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23914Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;

	public NA23914Test(String store) {
		this.Store = store;
		this.testName = "NA-23914";
	}

	@Test(alwaysRun = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA23914(ITestContext ctx) {
		try {
			this.prepareTest();

			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_SMB.click();

			String identifyCode = "SMBTest_23914ID";
			String companyName = "AutoTest_23914";
			rollback(identifyCode, companyName);

			String name = "SMBTest_23914Name";
			String customGroup = testData.B2C.getUnit();
			Common.rightClick(driver, hmcPage.SMB_CustomerGroup);
			Common.waitElementClickable(driver, hmcPage.SMBCustomerGroup_create, 3);
			hmcPage.SMBCustomerGroup_create.click();
			Common.sendFieldValue(hmcPage.SMBCustomerGroup_zUid, identifyCode);
			Common.sendFieldValue(hmcPage.SMBCustomerGroup_locname, name);
			hmcPage.SMBCustomerGroup_organisation.click();
			hmcPage.SMBCustomerGroup_memberOf.sendKeys(customGroup);
			Common.sleep(1000);
			hmcPage.SMBCustomerGroup_memberOf.sendKeys(Keys.ENTER);
			hmcPage.SMB_createBtn.click();
			try {
				Common.waitElementClickable(driver, hmcPage.SMB_saveBtn, 5);
			}catch(NoSuchElementException e) {
				Assert.fail("create SMB customer group fail");
			}

			String companyAddress1 = "SMBTest_23914 Company Address 1";
			String companyAddress2 = "SMBTest_23914 Company Address 2";
			String city = "Los Angeles";
			String zipcode = "90064";
			String state = "Alabama";
			String size = "500-999 employees";
			String industry = "Agriculture";//Agriculture
			String companyYear = "2016";
			String Depart = "Design & Engineering";
			Common.rightClick(driver, hmcPage.SMB_SMBCompany);
			Common.waitElementClickable(driver, hmcPage.SMBCompany_create, 3);
			hmcPage.SMBCompany_create.click();
			Common.sendFieldValue(hmcPage.SMBCompany_zCompanyName, companyName);
			sendFieldValue(hmcPage.SMBCompany_zCustomerGroup, identifyCode);
			Common.sendFieldValue(hmcPage.SMBCompany_zCompanyAddress1, companyAddress1);
			Common.sendFieldValue(hmcPage.SMBCompany_zCompanyAddress2, companyAddress2);
			Common.sendFieldValue(hmcPage.SMBCompany_zCompanyCity, city);
			Common.sendFieldValue(hmcPage.SMBCompany_zZipCode, zipcode);
			sendFieldValue(hmcPage.SMBCompany_zCompanyState, state);
			
			sendFieldValue(hmcPage.SMBCompany_zMajorIndustry, industry);
			sendFieldValue(hmcPage.SMBCompany_zCompanySize, size);
			
			Common.sendFieldValue(hmcPage.SMBCompany_zCompanyYear, companyYear);
			hmcPage.SMB_createBtn.click();
			try {
				Common.waitElementClickable(driver, hmcPage.SMB_saveBtn, 5);
			}catch(NoSuchElementException e) {
				Assert.fail("create SMB company fail");
			}

			driver.get(testData.B2C.getHomePageUrl());
			if(Common.checkElementExists(driver, b2cPage.SMB_createAccount, 10)) {
				b2cPage.SMB_createAccount.click();
			}
			
			Common.sleep(2000);
			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("smbRegistration"),"not navigate to registration page: " + url);
			
			String userName = "test";
			String email = EMailCommon.getRandomEmailAddress();
			String pwd = "1q2w3e4r";
			Common.sendFieldValue(b2cPage.SMB_firstName,userName );
			Common.sendFieldValue(b2cPage.SMB_lastName,userName );
			Common.sendFieldValue(b2cPage.SMB_email,email );
			sendFieldValue(b2cPage.SMB_companyName,companyName );
			Common.sleep(3000);
			Common.sendFieldValue(b2cPage.SMB_pwd,pwd );
			Common.sleep(3000);
			Common.sendFieldValue(b2cPage.SMB_checkPwd,pwd );
//			driver.findElement(By.xpath("//*[@id='department']/option[contains(text(),'Design & Engineering')]")).click();
			Common.sleep(3000);
			Common.javascriptClick(driver, b2cPage.SMB_createAccount2);
			//b2cPage.SMB_createAccount2.click();
			try {
				Common.waitElementVisible(driver, b2cPage.SMB_successMsg);
			}catch(NoSuchElementException e) {
				Assert.fail("create account failure");
			}
			
			//TODO
//			EMailCommon.activeNewAccount(driver, email, 3, Store);
//			
//			driver.get(testData.B2C.getHomePageUrl());
//			b2cPage.SMB_login.click();
//			Common.sendFieldValue(b2cPage.SMB_uName,email);
//			Common.sendFieldValue(b2cPage.SMB_uPwd,pwd );
//			b2cPage.SMB_signIn.click();		

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	private void sendFieldValue(WebElement locator, String value) {
		locator.clear();
		locator.sendKeys(value);
		Common.sleep(800);
		locator.sendKeys(Keys.DOWN);
		locator.sendKeys(Keys.ENTER);
	}

	public void rollback(String IdentifyCode, String CompanyName) {
		String CustomerGroupResultXpath = ".//*[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCustomerGroup]']//tr[contains(@id,'"
				+ IdentifyCode + "')]/td[last()]//a";
		String ComapanyResultXpath = ".//*[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCompany]']//div[contains(@id,'"
				+ CompanyName + "')]";
		hmcPage.SMB_CustomerGroup.click();
		Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, IdentifyCode);
		Common.sleep(2000);
		hmcPage.SMB_CustomerGroup_Srarch.click();
		if (Common.isElementExist(driver, By.xpath(CustomerGroupResultXpath), 5)) {
			driver.findElement(By.xpath(CustomerGroupResultXpath)).click();
			hmcPage.SMB_SMBCompanyAndCustomer_Delete.click();
			Common.sleep(2000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SMB_CustomerGroup_SearchToggle.click();
			Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, IdentifyCode);
			Common.sleep(2000);
			hmcPage.SMB_CustomerGroup_Srarch.click();
			Assert.assertEquals(false, Common.isElementExist(driver, By.xpath(CustomerGroupResultXpath), 5));
		}
		hmcPage.SMB_SMBCompany.click();
		Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, CompanyName);
		Common.sleep(2000);
		hmcPage.SMB_SMBCompany_Search.click();
		if (Common.isElementExist(driver, By.xpath(ComapanyResultXpath), 5)) {
			Common.doubleClick(driver, driver.findElement(By.xpath(ComapanyResultXpath)));
			hmcPage.SMB_SMBCompanyAndCustomer_Delete.click();
			Common.sleep(2000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SMB_SMBCompany_SearchToggle.click();
			Common.sendFieldValue(hmcPage.SMB_SMBCompany_CompanyName, CompanyName);
			Common.sleep(2000);
			hmcPage.SMB_SMBCompany_Search.click();
			Assert.assertEquals(false, Common.isElementExist(driver, By.xpath(ComapanyResultXpath), 5));
		}

	}

}
