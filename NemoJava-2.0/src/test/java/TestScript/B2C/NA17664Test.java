package TestScript.B2C;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;
public class NA17664Test extends SuperTestClass {
	B2CPage b2cPage = null;
	Actions actions = null;
	String orderNum = null;
	private String customer;
	public HMCPage hmcPage;
	private String addressline;
	private String county;
	private String zipcode;
	
	private boolean flag;
	private String tempBrowser;
	String WarehouseName;
	public NA17664Test(String store) {
		this.Store = store;
		this.testName = "NA-17664";
	
	}
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17664(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			actions = new Actions(driver);
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.clear();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.waitElementClickable(driver,
					hmcPage.B2CUnit_SiteAttributeTab, 30);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.waitElementClickable(driver, hmcPage.B2CUnit_EnableGEOLink,
					30);
			if (this.Store.equals("AU")) {
				hmcPage.B2CUnit_EnableQAS.click();				
			} else {
				hmcPage.B2CUnit_EnableGEOLink.click();
			}
			hmcPage.B2CUnit_EnableQASNew.click();
			hmcPage.SaveButton.click();
			Common.waitElementClickable(driver, hmcPage.SaveButton,
					30);
			Thread.sleep(5000);
			hmcPage.HMC_Logout.click();
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open('about:blank','_blank');", "");
			Set<String> winHandels = driver.getWindowHandles();
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(1));
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			if (b2cPage.PageDriver.getCurrentUrl().endsWith(
					"RegistrationGatekeeper")) {
				B2CCommon.handleGateKeeper(b2cPage, testData);
			}
			// add product to cart
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.clear();
			String mtmPn = "06P4069";
//			mtmPn = testData.B2C.getDefaultMTMPN();
			driver.findElement(By.xpath("//*[@id='quickOrderProductId']"))
					.sendKeys(mtmPn);
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quickAddInput']/a"))) {
				driver.findElement(By.xpath("//*[@id='quickAddInput']/a"))
						.click();
			} else {
				driver.findElement(By.xpath(".//*[@id='quickAddInput']/button"))
						.click();
			}
			Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
			b2cPage.Cart_CheckoutButton.click();
			
			Common.sleep(2000);
			if(Common.isElementExist(driver, By.xpath("//*[@id='guestForm']/button"))){
				b2cPage.GuestCheckout.click();
			}
			
			Common.sleep(2000);
			B2CCommon.fillShippingInfo(b2cPage, "autotest", "autotest", " ", testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), "3126676700", "lixe1@lenovo.com", "");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			
			Thread.sleep(5000);
			String errorMessage_1 = driver.findElement(By.xpath("//input[@id='line1']")).getAttribute("data-invalid");
			Assert.assertTrue(errorMessage_1.contains("Please enter a valid address line 1"));
			b2cPage.Shipping_AddressLine1TextBox.sendKeys("abcdefg");
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			Common.sleep(5000);
			
//			Assert.assertTrue(Common.isElementExist(driver, By.xpath(".//*[contains(text(),'Address validation failed. Please correct address')]")));
//
//			driver.findElement(By.xpath(".//*[@id='checkout_validateFrom_error_ok']")).click();
//			
//			
//			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
//			Assert.assertTrue(Common.isElementExist(driver, By.xpath(".//*[contains(text(),'Address matched')]")));
//
//			if(Common.isElementExist(driver, By.xpath("//div[@class='checkout_stepone_pop_validateinfo']"))){
//				driver.findElement(By.xpath("//input[@id='checkout_validateFrom_ok']")).click();
//			}
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(".//*[@id='PaymentTypeSelection_CARD']")));
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.clear();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.waitElementClickable(driver,
					hmcPage.B2CUnit_SiteAttributeTab, 30);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.waitElementClickable(driver, hmcPage.B2CUnit_EnableGEOLink,
					30);
			
			hmcPage.B2CUnit_EnableQASNewNo.click();
			hmcPage.SaveButton.click();
			Common.waitElementClickable(driver, hmcPage.SaveButton,
					30);
			Thread.sleep(5000);
			hmcPage.HMC_Logout.click();
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}