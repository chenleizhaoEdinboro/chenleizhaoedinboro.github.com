package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22331Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String Country;
	public String BPCTOUrl;
	
	public NA22331Test(String Store){
		this.Store = Store;
		this.testName = "NA-22331";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA22331(ITestContext ctx){
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String username = "bpcto";
			String InvalidPassword = "lenovo1234";
			String ValidPassword ="password01";
			driver.get(testData.B2C.getHomePageUrl().replace("/"+testData.B2C.getStore(),"")+ "/bpcto/insight/" + "/login");
			Common.sleep(10000);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"Cart is open");
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is open");
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys(username);
			b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
			b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys(InvalidPassword);
			b2cPage.RegisterGateKeeper_LoginButton.click();
			Common.sleep(5000);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"Cart is open");
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is open");
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys(username);
			b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
			b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys(ValidPassword);
			b2cPage.RegisterGateKeeper_LoginButton.click();
			Common.sleep(5000);
			Assert.assertFalse(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"Cart is open");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is open");
			
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			handleThrowable(e, ctx);
		}	
	}
	
	
	
}
