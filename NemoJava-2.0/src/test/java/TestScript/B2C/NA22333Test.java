package TestScript.B2C;

import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA22333Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String Url;
	public String UnitID = "bpcto_us_insight_direct_usa_unit";
	
	
	
	
	
	public NA22333Test(String Store , String Url){
		this.Store = Store;
		this.Url = Url;
		this.testName = "NA-22333";
	}
	
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA22333(ITestContext ctx){
		
		try{
			this.prepareTest();
			hmcPage =new HMCPage(driver);
			b2cPage =new B2CPage(driver);
			Url=testData.B2C.getHomePageUrl();
			// 1 ,HMC　Setting:
			//HMC->B2Cunit->Site attribute->Hide Billing Address->YES
			Dailylog.logInfoDB(1, "hmc setting", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();

			hmcPage.B2CUnit_IDTextBox.sendKeys(UnitID);
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			if(!hmcPage.HideBillingAddressToggle.isSelected()){
				hmcPage.HideBillingAddressToggle.click();
				hmcPage.Common_SaveButton.click();
			}
			
			boolean isSelected = hmcPage.HideBillingAddressToggle.isSelected();
			
			Assert.assertTrue(isSelected);
			
			//2, Load URL, click return
			Dailylog.logInfoDB(2, "load url", Store, testName);
			driver.get(Url);
			
			String gateKeeperUrl = driver.getCurrentUrl().toString();
			
//			Assert.assertTrue(gateKeeperUrl.endsWith("RegistrationGatekeeper"));
			
			//3, Login with bpcto / password01
			Dailylog.logInfoDB(2, "Login", Store, testName);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver.get(Url+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			
			Dailylog.logInfoDB(7, "Added  product into cart", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
//			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			B2CCommon.addPartNumberToCart(b2cPage, "80Y70064US");
			// 8,Enter Checkout
			Dailylog.logInfoDB(8, "checkout", Store, testName);
			b2cPage.lenovo_checkout.click();
			
			//9,Shipping Address will be prefilled, leave address as is

			Dailylog.logInfoDB(9, "Shipping Address", Store, testName);
			if(Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)){
				b2cPage.ASM_EditAddress.click();
			}
			B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());
			
			Thread.sleep(3000);
			// 10,Click continue
			Dailylog.logInfoDB(10, "continue", Store, testName);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);

			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();

			//Payment Page will load with hidden billing address
			Thread.sleep(3000);	
			By messageX = By.xpath("//label[@for='isDifferentBillingAddress']");
			Boolean isDisplayed = Common.checkElementDisplays(driver, messageX, 10);
			Assert.assertFalse("Payment Page will load with hidden billing address 1",isDisplayed);
			
			
			//11 , Select IGF, PO# = TESTPO_HIDE and PO Date = today
			Dailylog.logInfoDB(11, "Select IGF, PO# = TESTPO_HIDE and PO Date = today", Store, testName);
//			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);
			
			Common.sleep(2000);
			if (Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER"))) {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);

			} else if (Common.isElementExist(driver, By.xpath("//label[@for='PaymentTypeSelection_IGF']"))) {
				driver.findElement(By.xpath("//label[@for='PaymentTypeSelection_IGF']")).click();
			} else {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_IGF);
			}
			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");

			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {

				b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);

			}
	
			
			// 12, click continue
			Dailylog.logInfoDB(12, "continue", Store, testName);
			
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			// 13, drop order
			
			B2CCommon.handleVisaVerify(b2cPage);
			//Payment Page will load with hidden billing address
			Thread.sleep(3000);	
			By messageY = By.xpath("//span[contains(text(),'BILLING ADDRESS')]");
			Boolean paymentHidden = Common.checkElementDisplays(driver, messageY, 10);
			Assert.assertFalse("Payment Page will load with hidden billing address 2",paymentHidden);
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			

			
			Dailylog.logInfoDB(13, "Drop order", Store,
					testName);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.ThankyouMessage))));
			
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}

	}
	
	public void emptyCart(){
		if(Common.isElementExist(driver, By.xpath("//*[@id='emptyCartItemsForm']/a"))){
			driver.findElement(By.xpath("//*[@id='emptyCartItemsForm']/a")).click();
		}else{
			System.out.println("cart is empty");
		}
	}
	
	
	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			System.out.println("rollback"); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();

			hmcPage.B2CUnit_IDTextBox.sendKeys(UnitID);
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			if(!hmcPage.BillingAddressToggle.isSelected()){
				hmcPage.BillingAddressToggle.click();
				hmcPage.Common_SaveButton.click();
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	
}
