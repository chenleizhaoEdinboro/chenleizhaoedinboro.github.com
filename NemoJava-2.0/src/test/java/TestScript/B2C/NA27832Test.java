package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
import Pages.MailPage;
import TestScript.SuperTestClass;


public class NA27832Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String totalPrice;
	String mailBoxName;
	String loginUser;
	String defaultProductNo;
	String baseUnitName;
	
	public NA27832Test(String store,String defaultProductNo) {
		this.Store = store;
		this.testName = "NA-27832";
		this.defaultProductNo = defaultProductNo;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c" ,"compatibility"})
	public void NA27832(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			//mailBoxName = testData.B2C.getLoginID();
			loginUser = testData.B2C.getLoginID();
	
			mailBoxName = "testMail@sharklasers.com";
			switch (Store){
			case "CA":
				baseUnitName = "webca";
				break;
			case "US":
				baseUnitName = "usweb";
				break;
			}
			
			// Check gtcEmailList status in Hmc
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(8000);
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.sendKeys(baseUnitName);
			hmcPage.baseStore_search.click();
			hmcPage.BaseStore_SearchResult.click();
			hmcPage.baseStore_administration.click();
			System.out.println("gtcEmailList is:" + hmcPage.baseStore_gtcEmailList.getAttribute("value"));
			
			
			Dailylog.logInfoDB(4,"Login website, add product", Store,testName);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			//Handle advertisement
			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']"), 3)){
				driver.findElement(By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']")).click();
			}
			B2CCommon.login(b2cPage, loginUser, "1q2w3e4r");
			int i=0;
			while(driver.getCurrentUrl().contains("j_spring_security")&&(i++<3)) {
				Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			Common.sleep(3000);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()+"/cart");
			B2CCommon.emptyCart(driver, b2cPage);
			
			//B2CCommon.addPartNumberToCart(b2cPage,"80VR00DALS");
			try {
				addPartNumberToCart(b2cPage,testData.B2C.getDefaultMTMPN());
			}catch(Exception e) {
				B2CCommon.addPartNumberToCart(b2cPage,defaultProductNo);
			}
			//Step 6:
			Dailylog.logInfoDB(6,"Checkout and type shipping information then continue.", Store,testName);
			b2cPage.Cart_CheckoutButton.click();
			Common.sleep(1000);
			if(driver.getCurrentUrl().contains("login")){
				B2CCommon.login(b2cPage, loginUser, "1q2w3e4r");
			}
			if(Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)){
				b2cPage.ASM_EditAddress.click();
			}
			Thread.sleep(1000); //"Mohammed", "Jamal"
			B2CCommon.fillShippingInfo(b2cPage, "Mohammed", "Jamal", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), mailBoxName, testData.B2C.getConsumerTaxNumber());
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(10000);
			
			if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 10))
				b2cPage.ValidateInfo_SkipButton.click();
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressVerifyButton, 10))
				b2cPage.Shipping_AddressVerifyButton.click();
			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);
			/*Thread.sleep(3000);
			b2cPage.Shipping_ContinueButton.click();*/

			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			//Common.javascriptClick(b2cPage.PageDriver, driver.findElement(By.xpath("//*[@for='PaymentTypeSelection_Wire']")));
			if (Common.checkElementExists(driver,
					b2cPage.Payment_UseShippingAddress, 10)
					&& (b2cPage.Payment_UseShippingAddress
							.getAttribute("value").equals("true"))) {
				b2cPage.Payment_UseShippingAddress.click();
			}
			fillPaymentAddressInfo(b2cPage, "Batman", "Begins",
					testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(),
					testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(),
					testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getConsumerTaxNumber());
			Common.scrollToElement(driver, b2cPage.Payment_ContinueButton);
			b2cPage.Payment_ContinueButton.click();

			B2CCommon.handleVisaVerify(b2cPage);
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			//b2cPage.OrderSummary_PlaceOrderButton.click();
			//check if cart id is shown
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='checkout-confirm-orderNumbers']/table/tbody/tr[2]/td[1]")).getText().contains("Cart ID"), "Should show cart ID in thankyou page"); 
			String cartNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(9, "Order placed. Cart number is: " + cartNumber, Store, testName);		
			
			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			
			EMailCommon.createEmail(driver,mailPage,mailBoxName);
			//Boolean flag = checkEmail(driver, mailPage,orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			Boolean flag = false;
		
			flag = EMailCommon.checkEmailContent(driver, mailPage, cartNumber,"//*[contains(text(),'"+cartNumber+"')]");
			
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ mailBoxName +", and check email: "+ "Gracias por tu orden "+cartNumber);		
			}
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	public void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		Common.waitElementClickable(b2cPage.PageDriver,
				b2cPage.PageDriver.findElement(By.xpath("//*[text()='" + partNum + "']")), 5);
	}
	
	public void fillPaymentAddressInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String phone,String consumerTaxNumber) {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_FirstNameTextBox, 3)) {
			if (b2cPage.Payment_FirstNameTextBox.isEnabled()) {
				b2cPage.Payment_FirstNameTextBox.clear();
				b2cPage.Payment_FirstNameTextBox.sendKeys(firstName);
			}
			if (b2cPage.Payment_LastNameTextBox.isEnabled()) {
				b2cPage.Payment_LastNameTextBox.clear();
				b2cPage.Payment_LastNameTextBox.sendKeys(lastName);
			}
			if (b2cPage.Payment_AddressLine1TextBox.isEnabled()) {
				b2cPage.Payment_AddressLine1TextBox.clear();
				b2cPage.Payment_AddressLine1TextBox.sendKeys(addressline1);
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_CityTextBox, 1)) {
				if (b2cPage.Payment_CityTextBox.isEnabled()) {
					b2cPage.Payment_CityTextBox.clear();
					b2cPage.Payment_CityTextBox.sendKeys(city);
				}
			} else if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_City2TextBox, 1)) {
				if (b2cPage.Payment_City2TextBox.isEnabled()) {
					b2cPage.Payment_City2TextBox.clear();
					b2cPage.Payment_City2TextBox.sendKeys(city);
				}
			}
			if (b2cPage.Payment_StateDropdown.isEnabled()) {
				Select dropdown = new Select(b2cPage.Payment_StateDropdown);
				dropdown.selectByVisibleText(state);
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_PostCodeTextBox, 1)) {
				if (b2cPage.Payment_PostCodeTextBox.isEnabled()) {
					b2cPage.Payment_PostCodeTextBox.clear();
					b2cPage.Payment_PostCodeTextBox.sendKeys(postCode);
				}
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_ContactNumTextBox, 1)) {
				if (b2cPage.Payment_ContactNumTextBox.isEnabled()) {
					b2cPage.Payment_ContactNumTextBox.clear();
					b2cPage.Payment_ContactNumTextBox.sendKeys(phone);
				}
			}
			if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Payment_consumerTaxNumber, 1)) {
				if (b2cPage.Payment_consumerTaxNumber.isEnabled()) {
					b2cPage.Payment_consumerTaxNumber.clear();
					b2cPage.Payment_consumerTaxNumber.sendKeys(consumerTaxNumber);
				}
			}
			
		}
	}
	

}
	

