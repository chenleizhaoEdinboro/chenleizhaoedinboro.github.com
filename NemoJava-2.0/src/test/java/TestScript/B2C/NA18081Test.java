package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.Assert;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.B2CCommon;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18081Test extends SuperTestClass{
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	String registeredEmail;
	String accountPassword;
	String accountFirstName;
	String accountLastName;
	public NA18081Test(String store) {
		this.Store = store;
		this.testName = "NA-18081";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2c"})
	public void NA18081(ITestContext ctx) {
		try{
			//Set Email for registration
			registeredEmail = EMailCommon.getRandomEmailAddress();
			Dailylog.logInfoDB(1, "Email 1 is: " + registeredEmail, Store, testName);
			//Set Password
			accountPassword = "1q2w3e4r";
			//Set First name
			accountFirstName = "Batman";
			//Set Last name
			accountLastName = "Robin";

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			//Step 1:
			Dailylog.logInfoDB(1, "In customer registration page", Store, testName);
			driver.get(testData.B2C.getHomePageUrl());
			Thread.sleep(1000);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			Thread.sleep(1000);
			b2cPage.Login_CreateAnAccountButton.click();
			//Enter ID
			fillAccountDetails(b2cPage.RegistrateAccount_EmailIdTextBox, registeredEmail);
			//Enter Confirm Email
			fillAccountDetails(b2cPage.RegistrateAccount_ConfirmEmailTextBox, registeredEmail);
			//Enter Password
			fillAccountDetails(b2cPage.RegistrateAccount_FirstNameTextBox, accountFirstName);
			//Enter First Name
			fillAccountDetails(b2cPage.RegistrateAccount_LastNameTextBox, accountLastName);
			//Enter Last Name
			fillAccountDetails(b2cPage.RegistrateAccount_PasswordTextBox, accountPassword);
			//Enter confirm password
			fillAccountDetails(b2cPage.RegistrateAccount_ConfirmPasswordTextBox, accountPassword);
			//Check the Terms & Conditions check box
			b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
			b2cPage.RegistrateAccount_CreateAccountButton.click();
			Dailylog.logInfoDB(1, "Successfully created new account.", Store, testName);

			//Step 2:
			Dailylog.logInfoDB(2, "Go to HMC, check the customer account which was just registered", Store, testName);
			validateOptIn("true", null, true);

			//Step 3, 4, 5:
			checkOptInCheckBox(true, null);
			//driver.manage().deleteAllCookies();

			//Step 6:
			Dailylog.logInfoDB(6, "Go back to HMC, check this customer account", Store, testName);
			validateOptIn(null, "true", false);

			//Step 7:
			Dailylog.logInfoDB(7, "Go back to website to My account page,Click Update Personal Details and uncheck I would like to receive free email offers from Lenovo. is unchecked.", Store, testName);
			checkOptInCheckBox(false, "true");
			b2cPage.updateProfile_updatePersonalDetails.click();
			Thread.sleep(2000);
			Assert.assertEquals(b2cPage.updateProfile_recieveFreeEmailChkBox.getAttribute("checked"), null);

			//Step 8:
			Dailylog.logInfoDB(8, "Go back to website, choose any product and add to cart", Store, testName);
			/*navigateAndLogInB2C();*/
			Thread.sleep(1000);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			//Add part number
			//B2CCommon.addPartNumberToCart(b2cPage, "60DFAAR1AU");
			B2CCommon.emptyCart(driver, b2cPage);
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			//Step 9:
			Dailylog.logInfoDB(9, "Click Lenovo checkout", Store, testName);
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(1000);
			//b2cPage.ASM_EditAddress.click();
			B2CCommon.fillShippingInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());
			Assert.assertEquals(b2cPage.lenovoShipping_marketAgreementChkBox.isDisplayed()||b2cPage.lenovoShipping_marketAgreementChkBox_New.isDisplayed(), true);
			
			if(b2cPage.lenovoShipping_marketAgreementChkBox.isDisplayed()){
				b2cPage.lenovoShipping_marketAgreementChkBox.click();
			}else {
				b2cPage.lenovoShipping_marketAgreementChkBox_New.click();
			}

			//Step 10:
			Dailylog.logInfoDB(10, "Drop an order", Store, testName);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(3000);;
			//Common.scrollToElement(driver, b2cPage.Shipping_ContinueButton);
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();
			if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 3))
				b2cPage.ValidateInfo_SkipButton.click();
			if (Common.checkElementDisplays(driver, b2cPage.NewShipping_AddressValidateButton, 3))
				b2cPage.NewShipping_AddressValidateButton.click();
			
			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);
			/*Thread.sleep(3000);
			b2cPage.Shipping_ContinueButton.click();*/
			B2CCommon.fillDefaultPaymentInfo(b2cPage,testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();

			B2CCommon.handleVisaVerify(b2cPage);
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			//b2cPage.OrderSummary_PlaceOrderButton.click();
			String orderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(9, "Order placed. Order number is: " + orderNumber, Store, testName);

			//Step 11:
			Dailylog.logInfoDB(11, "Go back to HMC, check this account", Store, testName);
			validateOptIn(null, "true", false);
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}

	private void fillAccountDetails(WebElement locator, String value){
		locator.clear();
		locator.sendKeys(value);
	}

	private void validateOptIn(String noOptInValue, String yesOptInValue, boolean isActiationRequired) throws InterruptedException{
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.home_userTab.click();
		hmcPage.userTab_customerTab.click();
		hmcPage.customer_customerIDTextBox.clear();
		hmcPage.customer_customerIDTextBox.sendKeys(registeredEmail);
		hmcPage.customer_customerSearchButtonn.click();
		Thread.sleep(1000);
		hmcPage.customer_searchedResultImage.click();
		hmcPage.customerSearch_administrationTab.click();
		Thread.sleep(1000);
		Assert.assertEquals(hmcPage.customerSearch_optInNo.getAttribute("checked"), noOptInValue);
		Assert.assertEquals(hmcPage.customerSearch_optInYes.getAttribute("checked"), yesOptInValue);
		if(isActiationRequired){
			//Activating the account after registration
			hmcPage.customerSearch_verifyInLenovoYesChkBox.click();
			hmcPage.customerSearch_saveButton.click();
		}
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void navigateAndLogInB2C() throws InterruptedException{
		driver.get(testData.B2C.getHomePageUrl());
		Thread.sleep(1000);
		B2CCommon.handleTeleGateKeeper(b2cPage, testData);	
		Thread.sleep(1000);
		driver.get(testData.B2C.getloginPageUrl());
		B2CCommon.login(b2cPage, registeredEmail, accountPassword);
		Thread.sleep(1000);
	}

	public void checkOptInCheckBox(boolean displayLogs, String optInCheckBox) throws InterruptedException{
		if(displayLogs){
			Dailylog.logInfoDB(3, "Go back to the website, after login, open My Account", Store, testName);
		}
		navigateAndLogInB2C();
		driver.get(testData.B2C.getHomePageUrl() + "/my-account");
		//Navigate to my account -> update personal details
		b2cPage.myAccount_updatePersonalDetails.click();

		if(displayLogs){
			Dailylog.logInfoDB(4, "Click Update Personal Details", Store, testName);
		}
		b2cPage.updateProfile_updatePersonalDetails.click();
		Assert.assertEquals(b2cPage.updateProfile_recieveFreeEmailChkBox.getAttribute("checked"), optInCheckBox);

		if(displayLogs){
			Dailylog.logInfoDB(5, "Check the checkbox, and save", Store, testName);
		}
		b2cPage.updateProfile_profileTitleDD.click();
		b2cPage.updateProfile_mrsOption.click();
		b2cPage.updateProfile_fName.clear();
		b2cPage.updateProfile_fName.sendKeys(accountFirstName);
		b2cPage.updateProfile_lName.clear();
		b2cPage.updateProfile_lName.sendKeys(accountLastName);
		b2cPage.updateProfile_recieveFreeEmailChkBox.click();
		b2cPage.updateProfile_saveUpdatesButton.click();
	}
}
