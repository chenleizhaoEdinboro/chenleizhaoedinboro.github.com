package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class DR75Test extends SuperTestClass {

	public DR75Test(String Store) {
		this.Store = Store;
		this.testName = "DR-75";
	}

	@Test(alwaysRun = true)
	public void NA75(ITestContext ctx) {
		try {
			this.prepareTest();
			
			// Skip first 2 steps
			B2CPage b2cPage = new B2CPage(driver);
			driver.get(testData.envData.getHttpsDomain() + "/de/de/publicsector");
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
			b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys("lisong2@lenovo.com");
			b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
			b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys("1q2w3e4r");
			b2cPage.RegisterGateKeeper_LoginButton.click();
			
			driver.get(testData.envData.getHttpsDomain() + "/de/de/publicsector/laptops/c/LAPTOPS?q=%3Aprice-asc%3AfacetSys-ScreenSize%3A10&uq=&text=");
			
			WebElement item = driver.findElements(By.xpath(".//div[@class='facetedResults-item only-allow-small-pricingSummary']")).get(0);
			if(!item.findElement(By.xpath(".//font")).isDisplayed())
				Assert.fail("Stock message is not shown!");
			if(!item.findElement(By.xpath(".//div[@class='facetedResults-footer']/input[2]")).getAttribute("value").contains("lenovb2b"))
				Assert.fail("Stock message url is wrong!");
			
			driver.get(testData.envData.getHttpsDomain() + "/de/de/publicsector/p/ZA0W0142DE");
			if(Common.checkElementDisplays(driver, b2cPage.RegistrationGateKeeper_LenovoIdTextBox, 1))
			{
				b2cPage.RegistrationGateKeeper_LenovoIdTextBox.clear();
				b2cPage.RegistrationGateKeeper_LenovoIdTextBox.sendKeys("lisong2@lenovo.com");
				b2cPage.RegistrationGateKeeper_PasswordTextBox.clear();
				b2cPage.RegistrationGateKeeper_PasswordTextBox.sendKeys("1q2w3e4r");
				b2cPage.RegisterGateKeeper_LoginButton.click();
			}
			B2CCommon.addProductToCartFromPDPPage(driver);
			Common.javascriptClick(driver, b2cPage.Cart_CheckoutButton);
			
			Thread.sleep(5000);
			if(!driver.getCurrentUrl().contains("digitalriver.com"))
				Assert.fail("Doesn't go to DR successfully!");
			
			driver.findElement(By.id("loginEmail")).clear();
			driver.findElement(By.id("loginEmail")).sendKeys("akhotp1@digitalriver.com");
			driver.findElement(By.id("loginPass")).clear();
			driver.findElement(By.id("loginPass")).sendKeys("Test@123");
			driver.findElement(By.id("dr_cc_login")).click();
			
			driver.findElement(By.id("ENDUSERzip")).clear();
			driver.findElement(By.id("ENDUSERzip")).sendKeys("12345");
			driver.findElement(By.id("ENDUSERname1")).clear();
			driver.findElement(By.id("ENDUSERname1")).sendKeys("MINGFEI");
			driver.findElement(By.id("ENDUSERaddr1")).clear();
			driver.findElement(By.id("ENDUSERaddr1")).sendKeys("1234");
			driver.findElement(By.id("ENDUSERcity")).clear();
			driver.findElement(By.id("ENDUSERcity")).sendKeys("Eden Primer"); 
			driver.findElement(By.id("checkoutButton")).click();
			
			driver.findElement(By.id("vr_skipregistration")).click();
			
			driver.findElement(By.id("tosAccepted")).click();
			driver.findElement(By.id("submitBottom")).click();
			
			Thread.sleep(5000);
			if(!driver.getCurrentUrl().contains("id=ThankYouPage"))
				Assert.fail("Place order failed!");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
