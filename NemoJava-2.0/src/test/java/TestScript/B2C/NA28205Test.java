package TestScript.B2C;

import org.apache.log4j.DailyRollingFileAppender;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA28205Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String totalPrice;
	String mailBoxName;
	String loginUser;
	String defaultProductNo;
	
	public NA28205Test(String store,String defaultProductNo) {
		this.Store = store;
		this.testName = "NA-28205";
		this.defaultProductNo = defaultProductNo;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2c"})
	public void NA28205(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			mailBoxName = testData.B2C.getLoginID();
			
			switch(Store){
			case "MX_OUTLET":
				mailBoxName = "testmx@sharklasers.com";
				break;
			case "CO_OUTLET":
				mailBoxName = "testco@sharklasers.com";
				break;
			default:
				mailBoxName = "testbr@sharklasers.com";	
			}
			
			loginUser = testData.B2C.getLoginID();
			
			
			Dailylog.logInfoDB(1,"Go to website", Store,testName);
			
			driver.get(testData.B2C.getHomePageUrl());
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			//Handle advertisement
			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']"), 3)){
				driver.findElement(By.xpath("//div[@class='mfp-iframe-scaler']/button[@title='Close (Esc)']")).click();
			}
			B2CCommon.login(b2cPage, loginUser, "1q2w3e4r");
			int i=0;
			while(driver.getCurrentUrl().contains("j_spring_security")&&(i++<3)) {
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			Common.sleep(3000);
			
			Dailylog.logInfoDB(2, "Go to cart directly,  Quick add to cart the product", Store, testName);

			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			B2CCommon.emptyCart(driver, b2cPage);
			
			//B2CCommon.addPartNumberToCart(b2cPage,"80VR00DALS");
			try {
				addPartNumberToCart(b2cPage, defaultProductNo);
			}catch(Exception e) {
				B2CCommon.addPartNumberToCart(b2cPage,defaultProductNo);
			}
			
			Dailylog.logInfoDB(3,"Go to checkout under anonymous mode", Store,testName);
			
			b2cPage.Cart_CheckoutButton.click();
			Common.sleep(1000);
			if(driver.getCurrentUrl().contains("login")){
				B2CCommon.login(b2cPage, loginUser, "1q2w3e4r");
			}
			
			Dailylog.logInfoDB(4, "Enter the correct delivery address with the testing email address,Continue", Store, testName);
			
			if(Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)){
				b2cPage.ASM_EditAddress.click();
			}
			Thread.sleep(1000);
			B2CCommon.fillShippingInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), mailBoxName, testData.B2C.getConsumerTaxNumber());
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(30000);;
			//Common.scrollToElement(driver, b2cPage.Shipping_ContinueButton);
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();
			if (Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 10))
				b2cPage.ValidateInfo_SkipButton.click();
			/*if (Common.checkElementDisplays(driver, b2cPage.Shipping_validateAddressItem, 3))
				b2cPage.Shipping_validateAddressItem.click();*/
			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);
			/*Thread.sleep(3000);
			b2cPage.Shipping_ContinueButton.click();*/
			
			Dailylog.logInfoDB(5, "Choose direct deposit as the payment method ,Continue", Store, testName);
			
			By BankDeposit = By
					.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']");
			if(Store.equals("CO_OUTLET")||Store.equals("MX_OUTLET")||Store.equals("BR_OUTLET")) {
				if (Common.isElementExist(driver, BankDeposit)) {
					Common.javascriptClick(driver, driver.findElement(By
							.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
					
				} else {Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_Wire']")));
				}
				if(Common.checkElementDisplays(driver, b2cPage.Payment_UseShippingAddress, 10)
						&&(b2cPage.Payment_UseShippingAddress.getAttribute("value").equals("true"))) {
					b2cPage.Payment_UseShippingAddress.click();
				}
				
				fillPaymentAddressInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),testData.B2C.getConsumerTaxNumber());
			}else {
				B2CCommon.fillDefaultPaymentInfo(b2cPage,testData);
				if(Common.checkElementExists(driver, b2cPage.Payment_UseShippingAddress, 10)
						&&(b2cPage.Payment_UseShippingAddress.getAttribute("value").equals("true"))) {
					b2cPage.Payment_UseShippingAddress.click();
				}
				fillPaymentAddressInfo(b2cPage, "Batman", "Begins", testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),testData.B2C.getConsumerTaxNumber());
			}
			Common.scrollToElement(driver, b2cPage.Payment_ContinueButton);
			b2cPage.Payment_ContinueButton.click();

			B2CCommon.handleVisaVerify(b2cPage);
			
			Thread.sleep(6000);
			
			/*
			 * get the product name , price ,  shipping address , payment address
			 * 
			 * 
			 * */
			
			String productName = driver.findElement(By.xpath("//div[@class='itemName']")).getText().toString();
			
			//get the shipping address 
			String fristNameString  = "Batman";
			String lastNameString = "Begins";
			
			String city = testData.B2C.getDefaultAddressCity();
			String line1 = testData.B2C.getDefaultAddressLine1();
			String state = testData.B2C.getDefaultAddressState();
			String phone = testData.B2C.getDefaultAddressPhone();
			
			Dailylog.logInfoDB(5, "productName is :" + productName, Store, testName);
			Dailylog.logInfoDB(5, "shipping address fristNameString is :" + fristNameString, Store, testName);
			Dailylog.logInfoDB(5, "shipping address lastNameString is :" + lastNameString, Store, testName);
			
			Dailylog.logInfoDB(5, "shipping address city is :" + city, Store, testName);
			Dailylog.logInfoDB(5, "shipping address line1 is :" + line1, Store, testName);
			Dailylog.logInfoDB(5, "shipping address state is :" + state, Store, testName);
			Dailylog.logInfoDB(5, "shipping address phone is :" + phone, Store, testName);
			
			
			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			//b2cPage.OrderSummary_PlaceOrderButton.click();
			
			Dailylog.logInfoDB(6, "Check the order info on the review page. and drop the order successfully", Store,testName);
			
			String orderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			
			String productPrice = Common.javaScriptGetText(driver, driver.findElement(By.xpath("//td[@class='checkout-confirm-orderSummary-table-productTotal']")));
			System.out.println("productPrice is :" + productPrice);
			
			Dailylog.logInfoDB(6, "Order placed. Order number is: " + orderNumber, Store, testName);		
			
			Dailylog.logInfoDB(7, "Go to the testing email address and check the order confirmation email", Store, testName);
			
			EMailCommon.createEmail(driver,mailPage,mailBoxName);
			//Boolean flag = checkEmail(driver, mailPage,orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			Boolean flag = false;
			
			flag = EMailCommon.checkEmailContent(driver, mailPage, orderNumber,"//*[contains(text(),'"+orderNumber+"')]");
			
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ mailBoxName +", and check email: "+ "Gracias por tu orden "+orderNumber);
				throw new Exception("Mail is not received.");
			}
			
			Dailylog.logInfoDB(7, "Email is received , checking email content", Store, testName);
			
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[@id='display_email']//td[contains(text(),'"+ productName +"')]"), 10));
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[@id='display_email']//td[contains(text(),'"+ productPrice.trim() +"')]"), 10));
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath(".//*[@id='display_email']//td[contains(.,'"+ city.trim() +"')]"), 10));
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[@id='display_email']//td[contains(.,'"+ state +"')]"), 10));
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//*[@id='display_email']//td[contains(.,'"+ phone +"')]"), 10));
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
	

