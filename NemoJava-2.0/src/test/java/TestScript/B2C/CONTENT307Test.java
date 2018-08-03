package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class CONTENT307Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	String today=Common.getDateTimeString();
	String emailUs = "smbus@126.com";
	String emailJp = "smbjp@126.com";
	public CONTENT307Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-307";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2c"})
	public void CONTENT307(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//1.login  hmc website
			changeHMCGatekeeper(testData.B2C.getUnit());
			Dailylog.logInfoDB(1, "login  hmc website,B2C Commerce -> B2c unit,double result ->[Site Attributes]", Store, testName);
			
			//4.SMB->SMB Customer Group ->Identification code\n\ndouble click result\n\n   
			if(Store.toLowerCase().equals("us_smb")) {
				saveSMBRepid("103256058","1234567890");
			}else if(Store.toLowerCase().equals("jp_smb")){
				saveSMBRepid("jp00004","1234567890");
			}
			Dailylog.logInfoDB(4, "SMB->SMB Customer Group ->Identification code\\n\\ndouble click result\\n\\", Store, testName);
			
			//6.open b2c website
			if(Store.toLowerCase().equals("us_smb")) {
				confirmRepId("IMPERIAL PAINTING & COATINGS LLC","1234567890");
			}else if(Store.toLowerCase().equals("jp_smb")) {
				confirmRepId("株式会社ispace","1234567890");
			}
			Dailylog.logInfoDB(7, "repid auto ", Store, testName);
			
			//8.change company name
			changeCompanyName("www");
			Dailylog.logInfoDB(8, "change company ", Store, testName);
			
			//9.company rep id blank
			if(Store.toLowerCase().equals("us_smb")) {
				saveRepIdBlank("1063093");
			}else if(Store.toLowerCase().equals("jp_smb")) {
				saveRepIdBlank("jp00405");
			}
			
			if(Store.toLowerCase().equals("us_smb")) {
				confirmRepIdBlank("ARETE SLEEP LLC");
			}else if(Store.toLowerCase().equals("jp_smb")) {
				confirmRepIdBlank("Secomea合同会社");
			}
			Dailylog.logInfoDB(9, "login  hmc website,B2C Commerce -> B2c unit,double result ->[Site Attributes]", Store, testName);
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


	private void confirmRepIdBlank(String company) {
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(3000);
		b2cPage.createAccountSmb.click();
		b2cPage.companyName.clear();
		b2cPage.companyName.sendKeys(company);
		if(Store.toLowerCase().equals("us_smb")) {
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//body[@class=' language-en oo_bar']/ul[contains(text(),'')]")), 15);
			driver.findElement(By.xpath("//body[@class=' language-en oo_bar']/ul[contains(text(),'')]")).click();
		}
		if(Store.toLowerCase().equals("jp_smb")) {
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//body[@class=' language-ja oo_bar']/ul[contains(text(),'')]")), 15);
			driver.findElement(By.xpath("//body[@class=' language-ja oo_bar']/ul[contains(text(),'')]")).click();
		}
		Common.sleep(3000);
		Assert.assertTrue(b2cPage.repId.getAttribute("disabled").contains(""));
		Assert.assertTrue(b2cPage.repId.getAttribute("value").contains(""));
		Dailylog.logInfoDB(9, "rep id balnk", Store, testName);
		Common.sleep(3000);
	}

	private void saveRepIdBlank(String Identificationcode) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_SMB.click();
		hmcPage.SMB_CustomerGroup.click();
		hmcPage.SMB_CustomerGroup_IdentifyCode.clear();
		hmcPage.SMB_CustomerGroup_IdentifyCode.sendKeys(Identificationcode);
		hmcPage.SMB_searchbutton.click();
		Common.doubleClick(driver, driver.findElement(By.xpath("(//div[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCustomerGroup]']//tr)[3]")));
		Common.scrollToElement(driver, driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[NemoSMBCustomerGroup.repId]')]")));
		driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[NemoSMBCustomerGroup.repId]')]")).clear();
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Common.sleep(3000);
		hmcPage.HMC_Logout.click();
	}

	private void changeCompanyName(String company) {
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(3000);
		b2cPage.createAccountSmb.click();
		b2cPage.companyName.clear();
		b2cPage.companyName.sendKeys(company);
		b2cPage.repId.sendKeys("1234567890");
		String attribute = b2cPage.repId.getAttribute("value");
		Assert.assertEquals(attribute, "1234567890");
		Common.sleep(3000);
	}

	private void confirmRepId(String company, String repId) {
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(3000);
		b2cPage.createAccountSmb.click();
		if(Store.toLowerCase().equals("us_smb")) {
			if(Common.checkElementExists(driver, driver.findElement(By.xpath("//a[@class='blue-close']")), 15)) {
				driver.findElement(By.xpath("//a[@class='blue-close']")).click();
			}
		}
		
		b2cPage.companyName.clear();
		b2cPage.companyName.sendKeys(company);
//		driver.switchTo().frame(driver.findElement(By.id("utag_630_iframe")));
		if(Store.toLowerCase().equals("us_smb")) {
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//body[@class=' language-en oo_bar']/ul[contains(.,'"+company+"')]")),50);
			driver.findElement(By.xpath("//body[@class=' language-en oo_bar']/ul[contains(.,'"+company+"')]")).click();
		}
		if(Store.toLowerCase().equals("jp_smb")) {
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//body[@class=' language-ja oo_bar']/ul[contains(.,'"+company+"')]")),50);
			driver.findElement(By.xpath("//body[@class=' language-ja oo_bar']/ul[contains(.,'"+company+"')]")).click();
		}
		Common.sleep(3000);
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='repId']")).getAttribute("value"), repId);
		Dailylog.logInfoDB(5, "rep id is exist", Store, testName);
		
		Common.sleep(3000);
		if(Store.toLowerCase().equals("us_smb")) {
			createUser(today+emailUs);
		}
		if(Store.toLowerCase().equals("jp_smb")) {
			createUser(today+emailJp);
		}
	}

	private void createUser(String emailUsGroup) {
		b2cPage.firstName.clear();
		b2cPage.firstName.sendKeys("autoname");
		b2cPage.lastName.clear();
		b2cPage.lastName.sendKeys("autoname");
		b2cPage.pwd.clear();
		b2cPage.pwd.sendKeys("1q2w3e4r");
		b2cPage.checkPwd.clear();
		b2cPage.checkPwd.sendKeys("1q2w3e4r");
		if(Store.toLowerCase().equals("us_smb")) {
			b2cPage.email.clear();
			b2cPage.email.sendKeys(emailUsGroup);
			b2cPage.industryUs.click();
			b2cPage.companySizeUs.click();
		}
		if(Store.toLowerCase().equals("jp_smb")) {
			b2cPage.email.clear();
			b2cPage.email.sendKeys(emailUsGroup);
			b2cPage.phoneticName.clear();
			b2cPage.phoneticName.sendKeys("sdadas");
			b2cPage.industryJp.click();
			b2cPage.companySizeJp.click();
		}
		Common.javascriptClick(driver, b2cPage.submit);
		Dailylog.logInfoDB(6, "register user ", Store, testName);
	}

	private void saveSMBRepid(String Identificationcode, String repId) throws InterruptedException {
		Common.scrollToElement(driver, hmcPage.Home_SMB);
		hmcPage.Home_SMB.click();
		hmcPage.SMB_CustomerGroup.click();
		hmcPage.SMB_CustomerGroup_IdentifyCode.clear();
		hmcPage.SMB_CustomerGroup_IdentifyCode.sendKeys(Identificationcode);
		hmcPage.SMB_searchbutton.click();
		Common.doubleClick(driver, driver.findElement(By.xpath("(//div[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCustomerGroup]']//tr)[3]")));
		Common.scrollToElement(driver, driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[NemoSMBCustomerGroup.repId]')]")));
		driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[NemoSMBCustomerGroup.repId]')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[NemoSMBCustomerGroup.repId]')]")).sendKeys(repId);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Common.sleep(3000);
		Common.javascriptClick(driver, hmcPage.HMC_Logout);
	}

	private void changeHMCGatekeeper(String unit) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(unit);
		hmcPage.B2CUnit_SearchButton.click();
		Common.sleep(3000);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.scrollToElement(driver, driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.zSwitchSMBProfile]]_spantrue']")));
		driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.zSwitchSMBProfile]]_spantrue']")).click();
		if(!Common.checkElementExists(driver, driver.findElement(By.xpath("//select[contains(@id,'Content/EnumerationValueSelectEditor')]/option[@value='18']")), 10)) {
			Common.rightClick(driver, driver.findElement(By.xpath("//table[@title='zSMBUserProfileAttributes']//th")));
			driver.findElement(By.xpath("//td[contains(text(),'Create SMB User Profile Attribute')]")).click();
			driver.findElement(By.xpath("//select[contains(@id,'Content/EnumerationValueSelectEditor')]/option[@value='18']")).click();
			driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/CreateItemListEntry[n/a]]_input']")).clear();
			driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/CreateItemListEntry[n/a]]_input']")).sendKeys("16");
		}
		hmcPage.Common_SaveButton.click();
		Common.sleep(3000);
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());		
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
