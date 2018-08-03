package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22330Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public NA22330Test(String Store){
		this.Store = Store;
		this.testName = "NA-22330";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA22330(ITestContext ctx){
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String username = "bpcto";
			String ValidPassword ="password01";
			String ReferralDomain = "https://au.ingrammicro.com";
			String BPCTOURL =testData.B2C.getHomePageUrl().replace("/"+testData.B2C.getStore(),"")+ "/auingramreseller" + "/login";
			driver.get(BPCTOURL);
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys(username);
			
			b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
			b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys(ValidPassword);
			b2cPage.RegisterGateKeeper_LoginButton.click();
			Common.sleep(5000);
			Assert.assertFalse(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"gatekeeper is open");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is not open");
//			driver.get(BPCTOURL);
			driver.manage().deleteAllCookies();
//			Common.sleep(10000);
//			driver.get(BPCTOURL);
//			Common.sleep(5000);
//			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"Cart is open");
//			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is open");
			
			driver.get(ReferralDomain);
			WebElement aElement = driver.findElement(By.xpath("(//a[contains(@href,'/')])[1]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('href', arguments[1])", aElement, "https://pre-c-hybris.lenovo.com/au/en/auingramreseller/");
			aElement.click();
			Assert.assertFalse(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"gatekeeper is not open");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is open");
//			driver.get(BPCTOURL);
			driver.manage().deleteAllCookies();
//			Common.sleep(10000);
			driver.get(BPCTOURL);
			Common.sleep(5000);
			Assert.assertTrue(Common.checkElementDisplays(driver, By.id("gatekeeper.login.email.id"), 3),"Cart is open");
			Assert.assertFalse(Common.checkElementDisplays(driver, By.xpath("//li/a[contains(@class,'cartlink')]"), 3),"Cart is open");

		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}	
	}
	
	
	
}
