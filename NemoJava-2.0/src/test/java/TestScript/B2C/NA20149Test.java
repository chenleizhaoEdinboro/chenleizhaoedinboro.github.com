package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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
import junit.framework.Assert;

public class NA20149Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String email = "testco";
	String emailAddress = "testco@sharklasers.com";
	String totalPrice;
	
	public NA20149Test(String store, String productNo) {
		this.Store = store;
		this.testName = "NA-20149";
		this.productNo = productNo;
	}

	@Test(alwaysRun= true)
	public void NA20149(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			
			Dailylog.logInfoDB(1,"Login HMC, and 'wire' payment type.", Store,testName);		
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			andPaymentType();		
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);
			
			Dailylog.logInfoDB(4,"Login CO public, and product", Store,testName);
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl());
			System.out.println(driver.getCurrentUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			b2cPage.Navigation_MyAccountIcon.click();
			driver.findElement(By.xpath("(//a[contains(@href,'my-account') and not(contains(@class,'has-submenu'))])[1]")).click();
			B2CCommon.login(b2cPage, emailAddress, "1q2w3e4r");
			b2cPage.Navigation_CartIcon.click();
			//Common.scrollToElement(driver, b2cPage.Navigation_ViewCartButton);
			b2cPage.Navigation_ViewCartButton.click();
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			B2CCommon.addPartNumberToCart(b2cPage,productNo);
			
			Dailylog.logInfoDB(6,"Checkout and type shipping information then continue.", Store,testName);
			b2cPage.Cart_CheckoutButton.click();
			//b2cPage.Checkout_StartCheckoutButton.click();
			//b2cPage.Shipping_editAddress.click();
			fillShippingInfo(b2cPage, "autoFirstName", "autoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), 
					testData.B2C.getDefaultAddressPostCode(), "12345678", testData.B2C.getDefaultAddressPhone(),email+"@sharklasers.com");
			b2cPage.Shipping_ContinueButton.submit();
			
			Dailylog.logInfoDB(7,"Select wire transfer and continue.", Store,testName);
			b2cPage.Payment_WireButton.click();
			B2CCommon.fillPaymentAddressInfo(b2cPage, "autoFirstName", "autoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), 
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());			
			b2cPage.Payment_ContinueButton.click();	

			Dailylog.logInfoDB(8,"On summary page,Check the shipping and billing address same with same data and check the paymnet type is deposit.", Store,testName);
			String shippingInfo[]={b2cPage.Payment_verifyShippingName.getText(),b2cPage.Payment_verifyShippingAddress.getText(),
					b2cPage.Payment_verifyShippingLocality.getText(),b2cPage.Payment_verifyShippingRegion.getText(),
					b2cPage.Payment_verifyShippingPhone.getText(),b2cPage.Payment_verifyShippingEmail.getText(),
					b2cPage.Payment_verifyShippingCardHoldName.getText()};
			
			String paymentInfo[]={b2cPage.Payment_verifyPaymentName.getText(),b2cPage.Payment_verifyPaymentAddress.getText(),
					b2cPage.Payment_verifyPaymentLocality.getText(),b2cPage.Payment_verifyPaymentRegion.getText(),
					b2cPage.Payment_verifyPaymentPhone.getText(),b2cPage.Payment_verifyPaymentEmail.getText(),
					b2cPage.Payment_verifyPaymentardHoldName.getText()};
			for(int i=0;i<7;i++){
				System.out.println("The shippingInfo["+i+"] is:"+shippingInfo[i]);
				System.out.println("The paymentInfo["+i+"] is:"+paymentInfo[i]);
			}
			for(int i=0;i<7;i++){
				Assert.assertEquals("The payment page information is not same as the shipping page information!", shippingInfo[i],paymentInfo[i]);
			}
			System.out.println("The payment page information is same as the shipping page information!");
			
			String paymentType = b2cPage.Payment_verifyPaymentType.getText();
			Boolean paymentTypeBoolean = paymentType.contains("Depósito")||paymentType.contains("Wire Payment")||paymentType.contains("Behalf payment");
			Assert.assertTrue("The payment type is not 'Wire Payment'!", paymentTypeBoolean);
			System.out.println("The payment type is right!");			
			
			Dailylog.logInfoDB(9,"Check the T&C and place order, show Thank you page.", Store,testName);
			// Place Order	
			totalPrice = b2cPage.OrderSummary_totalPrice.getText();
			System.out.println("The total price is: "+totalPrice);
			Thread.sleep(30000);
			b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			B2CCommon.clickPlaceOrder(b2cPage);			
			//assert:Show Thank you page
			Assert.assertTrue("Don't show the thankyou page!", Common.checkElementExists(driver, b2cPage.OrderThankyou_confirmMessage, 60));
			// Get Order number
			String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			Dailylog.logInfoDB(9, "Order number is: " + orderNum,Store,testName);

			Dailylog.logInfoDB(10,"Go to email address and check the email.", Store,testName);
			driver.manage().deleteAllCookies();
			EMailCommon.createEmail(driver,mailPage,email);
			Boolean flag = checkEmail(driver, mailPage, "Gracias por tu orden "+orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
//			Boolean flag = checkEmail(driver, mailPage, "Thanks_for_your_order_"+orderNum, email+"@sharklasers.com" , shippingInfo, paymentInfo);
			if(flag==false){
				setManualValidateLog("Need Manual Validate in email "+ email+"@sharklasers.com" +", and check email: "+ "Gracias por tu orden "+orderNum);		
			}
			}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}	
	
	public  void fillShippingInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1,
			String city, String state, String postCode, String taxNumber, String phone, String mail) {
		if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_FirstNameTextBox, 5)) {
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys(firstName);
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(addressline1);
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_CityTextBox, 1)) {
				b2cPage.Shipping_CityTextBox.clear();
				b2cPage.Shipping_CityTextBox.sendKeys(city);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_AddressLine3TextBox, 1)) {
				b2cPage.Shipping_AddressLine3TextBox.clear();
				b2cPage.Shipping_AddressLine3TextBox.sendKeys(city);
			}

			Select stateDropdown = new Select(b2cPage.Shipping_StateDropdown);
			stateDropdown.selectByVisibleText(state);

			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_PostCodeTextBox, 1)) {
				b2cPage.Shipping_PostCodeTextBox.clear();
				b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
			}
			
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_taxNumber, 1)) {
				b2cPage.Shipping_taxNumber.clear();
				b2cPage.Shipping_taxNumber.sendKeys(taxNumber);
			}

			b2cPage.Shipping_ContactNumTextBox.clear();
			b2cPage.Shipping_ContactNumTextBox.sendKeys(phone);
			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys(mail);
		}
	}
	
	public void addProductNO(){
		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getHomePageUrl());
		b2cPage.Navigation_CartIcon.click();
		b2cPage.Navigation_ViewCartButton.click();
		B2CCommon.addPartNumberToCart(b2cPage,productNo);
	}
	
	public void andPaymentType(){
		// Payment Type: --> Add wire to Payment Type
		By wirePaymentType = By.xpath("//table[@title='dynamicPaymentType']//div[contains(@id,'Content/StringDisplay[Wire]_span')]");
		if(Common.isElementExist(driver, wirePaymentType)==false){
			Common.rightClick(driver, hmcPage.B2CUnit_paymentTypeTable);
			hmcPage.B2CLeasing_add.click();
			switchToWindow(1);
			hmcPage.B2CUnit_paymentTypeInput.sendKeys("wire");
			hmcPage.PaymentLeasing_searchbutton.click();
			hmcPage.B2CLeasing_searchedResult1.click();
			hmcPage.B2CLeasing_use.click();
			switchToWindow(0);
		}	
	}
	//Gracias por tu orden 4334123299
	public Boolean checkEmail(WebDriver driver,MailPage mailPage, String emailSubject, String checkEmail, String shipping[], String payment[]){
		String subject = null;
		Boolean reciverEmail=null;
		for(int i=1;i<=20;i++){
			if(i==20){
				reciverEmail=false;
				//System.out.println("ERROR !!!! Email is not received.....!");
				System.out.println("Need Manual Validate in email "+checkEmail+", and check email: "+emailSubject );
			} else if(Common.checkElementExists(driver, mailPage.Inbox_EmailSubject,30)==true){
				subject=mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: "+subject);
				if(subject.contains(emailSubject)){	
					reciverEmail=true;
					i=21;
					mailPage.Inbox_EmailSubject.click();
					System.out.println("Clicked on email containing :" + emailSubject);	
					Common.sleep(10000);
					
					Assert.assertEquals("The total price in email is not same as the actual price!",totalPrice, mailPage.Mail_totalPrice.getText());
					System.out.println("The product price is right in email!");
					
					Boolean paymentType = mailPage.Mail_paymentType.getText().contains("Depósito")||mailPage.Mail_paymentType.getText().contains("transferencia");
					Assert.assertTrue("The payment type is not 'Depósito'!", paymentType);
					System.out.println("The payment type is right in email!");
					
					Boolean shippingAddressFlag = false;
					if(mailPage.Mail_shippingInfo.getText().contains(shipping[1])&&
							mailPage.Mail_shippingInfo.getText().contains(shipping[2])&&
							mailPage.Mail_shippingInfo.getText().contains(shipping[3]))
						shippingAddressFlag = true;
					Assert.assertTrue("The address in email is not same as the actual shipping address!",
							shippingAddressFlag);
					System.out.println("The shipping address is right in email!");
					
					Boolean paymentAddressFlag = false;
					if(mailPage.Mail_paymentInfo.getText().contains(payment[1])&&
							mailPage.Mail_paymentInfo.getText().contains(payment[2])&&
							mailPage.Mail_paymentInfo.getText().contains(payment[3]))
						paymentAddressFlag = true;
					Assert.assertTrue("The address in email is not same as the actual payment address!", 
							paymentAddressFlag);				
					System.out.println("The payment address is right in email!");
				} else {
					System.out.println("email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
					Common.sleep(30000);
				}
			} 
		}
		return reciverEmail;		
	}
	

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}