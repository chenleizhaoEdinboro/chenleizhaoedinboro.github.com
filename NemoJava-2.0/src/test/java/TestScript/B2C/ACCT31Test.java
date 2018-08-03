package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;


public class ACCT31Test extends SuperTestClass {
	
	private boolean loginFlag = false;
	
	public String alcURL="https://account.lenovo.com/au/en";
	
	public ACCT31Test(String store) {
		this.Store = store;
		this.testName = "ACCT-31";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true)
	public void ACCT31(ITestContext ctx) {
		try {
			this.prepareTest();
		
			//1， Go to HMC and Set eService SSO Toggle and Hide Thank You Page Registration Toggle
			Dailylog.logInfoDB(1, "Go to HMC and Set eService SSO Toggle and Hide Thank You Page Registration Toggle", Store, testName);
			
			//set Hide Thank You Page Registration Toggle :No
			HMCPage hmcPage = new HMCPage(driver);
		    driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_NoHideThankyouPageRadioButton1.click();
			
			// set eService SSO Toggle : yes 
			hmcPage.eServiceToggle.click();
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);	
			
			

			//2, Guest checkout and drop an order
			Dailylog.logInfoDB(2, "Guest checkout and drop an order", Store, testName);
			
			driver.manage().deleteAllCookies();
			B2CPage b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl());

			B2CCommon.handleGateKeeper(b2cPage, testData);

			driver.get(driver.getCurrentUrl() + "/cart");

			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			Thread.sleep(10000);
			b2cPage.Cart_CheckoutButton.click();
			
			// If already login in register gate keeper, then no
			// StartCheckout button and ShippingInfo is pre-filled
			String tempEmail = EMailCommon.getRandomEmailAddress();
			String firstName = Common.getDateTimeString();
			String lastName = Common.getDateTimeString();
			if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 3)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}else if (Common.isElementExist(driver, By.xpath("//a[contains(@class,'loginHeaderLink')]/span[@class='colorChange']"))) {
				
			}else{
					loginFlag = true;
				}

			// Fill shipping info
			if(Store.equals("BR")){
				B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, "Brasília-Distrito Federal",
						"Rio de Janeiro", "Brasilia", "70443900",
						"6121958200", tempEmail, "84511773521");
				
			}else{
				B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail);
			}
	
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
//				if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
//					b2cPage.Shipping_AddressMatchOKButton.click();
			B2CCommon.handleAddressVerify(driver, b2cPage);

			// Fill payment info
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();

			B2CCommon.handleVisaVerify(b2cPage);
			
			// Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			// Get Order number
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(2, "Order number is: " + orderNum,Store,testName);
			
			//3, Create an account in guest thank you page*
			//4,Input all necessary fields and click create my account
			Dailylog.logInfoDB(3, "Create an account in guest thank you page*", Store, testName);
			Dailylog.logInfoDB(4, "Input all necessary fields and click create my account", Store, testName);
			
			
			if (!loginFlag) {
			// Check Pre-filled fields
			Assert.assertEquals(b2cPage.RegistrateAccount_FirstNameTextBox.getAttribute("value"), firstName
					.substring(0, b2cPage.RegistrateAccount_FirstNameTextBox.getAttribute("value").length()));
			Assert.assertEquals(b2cPage.RegistrateAccount_LastNameTextBox.getAttribute("value"), lastName
					.substring(0, b2cPage.RegistrateAccount_LastNameTextBox.getAttribute("value").length()));
			Assert.assertEquals(b2cPage.RegistrateAccount_EmailIdTextBox.getAttribute("value"), tempEmail);
			Assert.assertEquals(b2cPage.RegistrateAccount_ConfirmEmailTextBox.getAttribute("value"), tempEmail);
			b2cPage.RegistrateAccount_PasswordTextBox.clear();
			b2cPage.RegistrateAccount_PasswordTextBox.sendKeys("1q2w3e4r");
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys("1q2w3e4r");
//			b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
			Common.javascriptClick(driver, b2cPage.RegistrateAccount_AcceptTermsCheckBox);
//			b2cPage.OrderThankyou_CreateAccountButton.click();
			Common.javascriptClick(driver, b2cPage.OrderThankyou_CreateAccountButton);
			Thread.sleep(10000);
			EMailCommon.activeNewAccount(driver,tempEmail, 1, Store);
			
			//5, *test single sign on accross eService and eCommerce*
			//1)Login the new account in Thank you page
			Dailylog.logInfoDB(5, "*test single sign on accross eService and eCommerce* 1)Login the new account in Thank you page", Store, testName);
			
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, tempEmail, "1q2w3e4r");
			b2cPage.MyAccount_ViewOrderHistoryLink.click();
			if(!B2CCommon.checkOrderInHistroyPage(b2cPage, orderNum))
				Assert.fail(this.Store + " order " + orderNum + " doesn't exist!");
				
			}
			
			//6, open alc in the same browser 
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('"+ alcURL + "')");
			
			Common.switchToWindow(driver, 1);
			
			driver.get(alcURL + "/myorders");
			Assert.assertTrue(!driver.getCurrentUrl().contains("signin") && Common.isElementExist(driver, By.xpath("//div[@id='header-desktop']//li[@class='account-logout']/a")), "after refresh , alc is not logined");
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}

