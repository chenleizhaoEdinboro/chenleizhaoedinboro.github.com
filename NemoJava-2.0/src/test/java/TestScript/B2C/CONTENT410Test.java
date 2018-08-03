package TestScript.B2C;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class CONTENT410Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	String IframeXpath = ".//iframe[contains(@src,'showSMBQuickSetup')]";
	String CustomerGroupResultXpath = ".//*[@id='resultlist_Content/McSearchListConfigurable[NemoSMBCustomerGroup]']//tr[contains(@id,'TestSMBJP')]/td[last()]//a";
	public CONTENT410Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-410";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2c"})
	public void CONTENT410(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//1Go to hmc->smb->smb quick setup,enter info ,click create button
			createSmb("TestSMBJP","Test SMB JP","2900704087","Lenovo Test JP","right","");
			Dailylog.logInfoDB(1,"Go to hmc->smb->smb quick setup,enter info ,click create button", Store,testName);
			
			//5 check rep id
			openSmbWebsite("Lenovo Test JP","2900704087","right");
			Dailylog.logInfoDB(5,"Open the smb site,click create my accout button", Store,testName);
			
			//9.change rep id
			createSmb("TestSMBJP1","Test SMB JP1","2900000030","Lenovo Test JP1","wrong","The Rep ID is invalid.");
			Dailylog.logInfoDB(9,"change rep id", Store,testName);
			
			//10.rep id 9 digits
			createSmb("TestSMBJP1","Test SMB JP1","290000003","Lenovo Test JP1","wrong","The Rep ID should be 10 digits.");
			Dailylog.logInfoDB(10,"rep id 9 digits", Store,testName);
			
			//11. rep id 11 digits
			createSmb("TestSMBJP1","Test SMB JP1","29000000300","Lenovo Test JP1","wrong","The Rep ID is invalid.");
			Dailylog.logInfoDB(11,"rep id 11 digits", Store,testName);
			
			//12.rep id Alphabet mixing
			createSmb("TestSMBJP1","Test SMB JP1","29abcwer23","Lenovo Test JP1","wrong","The Rep ID should be 10 digits.");
			Dailylog.logInfoDB(12,"Alphabet mixing rep id", Store,testName);
			
			//15.check rep id
			openSmbWebsite("Lenovo Test JP1","","wrong");
			Dailylog.logInfoDB(15,"Alphabet mixing rep id", Store,testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	private void openSmbWebsite(String company, String repId, String message) throws InterruptedException {
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(3000);
		b2cPage.createAccountSmb.click();
		b2cPage.companyName.clear();
		b2cPage.companyName.sendKeys(company);
		if(message.equals("right")) {
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//body[@class=' language-ja oo_bar']/ul/li[contains(.,'Lenovo Test JP')]")),50);
			driver.findElement(By.xpath("//body[@class=' language-ja oo_bar']/ul/li[contains(.,'Lenovo Test JP')]")).click();
			Common.sleep(3000);
			Assert.assertEquals(driver.findElement(By.xpath("//input[@id='repId']")).getAttribute("value"), repId);
		}
		if(message.equals("wrong")) {
			b2cPage.repId.sendKeys("1234567890");
			String attribute = b2cPage.repId.getAttribute("value");
			Assert.assertEquals(attribute, "1234567890");
		}
		
	}

	private void createSmb(String IdentifyCode, String Name, String repId, String CompanyName, String right, String message) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_smb.click();
		hmcPage.SMB_QuickSetup.click();
		Common.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.xpath(IframeXpath)));
		hmcPage.sMBGeneralUnit.click();
		Common.sendFieldValue(hmcPage.SMB_QuickSetup_IdentifyCode, IdentifyCode);
		Common.sendFieldValue(hmcPage.SMB_QuickSetup_Name, Name);
		Common.sendFieldValue(hmcPage.repIdSmbSetUp, repId);
		Common.sendFieldValue(hmcPage.SMB_QuickSetup_CompanyName, CompanyName);
		hmcPage.SMB_QuickSetup_CreateButton.click();
		if(right.equals("right")) {
			Common.sleep(3000);
			driver.switchTo().defaultContent();
			hmcPage.SMB_CustomerGroup.click();
			Common.sendFieldValue(hmcPage.SMB_CustomerGroup_IdentifyCode, IdentifyCode);
			Common.sleep(2000);
			hmcPage.SMB_CustomerGroup_Srarch.click();
			Assert.assertEquals(true, Common.isElementExist(driver, By.xpath(CustomerGroupResultXpath), 10));
			
			Common.doubleClick(driver, driver.findElement(By.xpath(CustomerGroupResultXpath)));
			Common.scrollToElement(driver, hmcPage.repIdSMB);
			Assert.assertEquals(hmcPage.repIdSMBCustomerGroup.getAttribute("value"), repId);
		}
		
		if(right.equals("wrong")) {
			Common.sleep(3000);
			Assert.assertEquals(hmcPage.panelWindow.getText(), message);
		}
		driver.switchTo().defaultContent();
		hmcPage.HMC_Logout.click();
		
	}







}
