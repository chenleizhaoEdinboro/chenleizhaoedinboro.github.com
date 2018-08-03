package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16659Test extends SuperTestClass {
	
	public NA16659Test(String store) {
		this.Store = store;
		this.testName = "NA-16659";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c" ,"compatibility"})
	public void NA16659(ITestContext ctx) {
		try {
			this.prepareTest();
			
			B2CPage b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.javascriptClick(driver, b2cPage.Homepage_ContactUsLink);

			if(Common.checkElementDisplays(driver, b2cPage.ContactUs_EmailUsLink, 10)){
			
			b2cPage.ContactUs_EmailUsLink.click();
			
			String Email1, Email2, Email3;
			Select dropdown1 = new Select(b2cPage.EmailUs_TypeDropdown);
			
			Common.NavigateToUrl(driver, Browser, driver.getCurrentUrl().replace("https://www3.lenovo.com", testData.envData.getHttpsDomain()));
			Email1 = EMailCommon.getRandomEmailAddress().replace("@sharklasers.com", "@lenovo.com");
			dropdown1.selectByVisibleText("Home and Home Office");
			Common.waitElementClickable(driver, b2cPage.EmailUs_SubjectDropdown, 5);
			Select dropdown2 = new Select(b2cPage.EmailUs_SubjectDropdown);
			dropdown2.selectByVisibleText("New Sales");
			b2cPage.EmailUs_FirstName.sendKeys("AutoFirstName");
			b2cPage.EmailUs_LastName.sendKeys("AutoLastName");
			b2cPage.EmailUs_EmailAddress.sendKeys(Email1);
			Common.waitElementClickable(driver, b2cPage.EmailUs_Phone, 1);
			Common.waitElementClickable(driver, b2cPage.EmailUs_OrderNum, 1);
			b2cPage.EmailUs_Message.sendKeys(Email1);
			b2cPage.EmailUs_SendButton.click();
			Thread.sleep(10000);
			
			Email2 = EMailCommon.getRandomEmailAddress().replace("@sharklasers.com", "@lenovo.com");
			dropdown1.selectByVisibleText("Affinity and Employee Purchase Program");
			Common.waitElementClickable(driver, b2cPage.EmailUs_SubjectDropdown, 5);
			dropdown2.selectByVisibleText("New Sales");
			b2cPage.EmailUs_FirstName.sendKeys("AutoFirstName");
			b2cPage.EmailUs_LastName.sendKeys("AutoLastName");
			b2cPage.EmailUs_EmailAddress.sendKeys(Email2);
			Common.waitElementClickable(driver, b2cPage.EmailUs_Phone, 1);
			Common.waitElementClickable(driver, b2cPage.EmailUs_OrderNum, 1);
			b2cPage.EmailUs_Message.sendKeys(Email2);
			b2cPage.EmailUs_SendButton.click();
			Thread.sleep(10000);
			
			Email3 = EMailCommon.getRandomEmailAddress().replace("@sharklasers.com", "@lenovo.com");
			dropdown1.selectByVisibleText("Outlet");
			Common.waitElementClickable(driver, b2cPage.EmailUs_SubjectDropdown, 5);
			dropdown2.selectByVisibleText("New Sales");
			b2cPage.EmailUs_FirstName.sendKeys("AutoFirstName");
			b2cPage.EmailUs_LastName.sendKeys("AutoLastName");
			b2cPage.EmailUs_EmailAddress.sendKeys(Email3);
			Common.waitElementClickable(driver, b2cPage.EmailUs_Phone, 1);
			Common.waitElementClickable(driver, b2cPage.EmailUs_OrderNum, 1);
			b2cPage.EmailUs_Message.sendKeys(Email3);
			b2cPage.EmailUs_SendButton.click();
			Thread.sleep(10000);
			
		    Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_WebFormANZ.click();
			hmcPage.WebFormANZ_Email.sendKeys(Email1);
			hmcPage.WebFormANZ_SearchButton.click();
			if(!Common.checkElementDisplays(driver, By.xpath("//tr[contains(@class,'doubleclick-event')]"), 5))
				Assert.fail("Result cannot be found! " + Email1);
			
			hmcPage.WebFormANZ_Email.clear();
			hmcPage.WebFormANZ_Email.sendKeys(Email2);
			hmcPage.WebFormANZ_SearchButton.click();
			Thread.sleep(3000);
			if(!Common.checkElementDisplays(driver, By.xpath("//tr[contains(@class,'doubleclick-event')]"), 5))
				Assert.fail("Result cannot be found! " + Email2);
			
			hmcPage.WebFormANZ_Email.clear();
			hmcPage.WebFormANZ_Email.sendKeys(Email3);
			hmcPage.WebFormANZ_SearchButton.click();
			Thread.sleep(3000);
			if(!Common.checkElementDisplays(driver, By.xpath("//tr[contains(@class,'doubleclick-event')]"), 5))
				Assert.fail("Result cannot be found! " + Email3);
			}
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}

