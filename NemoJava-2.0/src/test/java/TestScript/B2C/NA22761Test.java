package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA22761Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String UnitID = "coweb";
	public String Url;
	
	public NA22761Test(String Store){
		this.Store = Store;
		this.testName = "NA-22761";
	}
		
	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void NA22761(ITestContext ctx){
		try{		
			// Skip HMC part
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			
			driver.get(testData.B2C.getHomePageUrl() + "/cart");

			B2CCommon.handleGateKeeper(b2cPage, testData);

			// Quick order
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			// Amazon has special process
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);

			// Click on guest checkout button if exists
			if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}

			// Fill default shipping address
			if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			
			// Payment
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.PayU_B2C)) {

				Payment.payAndContinue(b2cPage, PaymentType.PayU_B2C, testData);

				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				Payment.payWithPayUAfterPlaceOrder(b2cPage, testData);
								
				// Get Order number for Approved
				String orderNumApproved = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
				System.out.println("Approve order done!");
				
				// For Pending order
				driver.get(testData.B2C.getHomePageUrl() + "/cart");

				B2CCommon.handleGateKeeper(b2cPage, testData);

				// Quick order
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

				// Amazon has special process
				b2cPage.Cart_CheckoutButton.click();
				Thread.sleep(2000);

				// Click on guest checkout button if exists
				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
					b2cPage.Checkout_StartCheckoutButton.click();
				}

				// Fill default shipping address
				if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
					B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
				}
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				B2CCommon.handleAddressVerify(driver, b2cPage);
				System.out.println("PayU_B2C");
				Payment.payAndContinue(b2cPage, PaymentType.PayU_B2C, testData);

				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				fillPayUInfo(driver, testData, "PENDING");
				
				if(!Common.checkElementDisplays(driver, b2cPage.PayU_PendingStatus, 30))
					Assert.fail("PayU order is not pending!");
				System.out.println("Pending order done!");
				
				// For Declined Order
				driver.manage().deleteAllCookies();
				driver.quit();
				this.SetupBrowser();
				b2cPage = new B2CPage(driver);
				driver.get(testData.B2C.getHomePageUrl() + "/cart");

				B2CCommon.handleGateKeeper(b2cPage, testData);

				// Quick order
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

				// Amazon has special process
				b2cPage.Cart_CheckoutButton.click();
				Thread.sleep(2000);

				// Click on guest checkout button if exists
				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
					b2cPage.Checkout_StartCheckoutButton.click();
				}

				// Fill default shipping address
				if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
					B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
				}
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				B2CCommon.handleAddressVerify(driver, b2cPage);
				
				Payment.payAndContinue(b2cPage, PaymentType.PayU_B2C, testData);

				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				fillPayUInfo(driver, testData, "REJECTED");
				
				if(!Common.checkElementDisplays(driver, b2cPage.PayU_DeclinedStatus, 30))
					Assert.fail("PayU order is not decliened!");
				System.out.println("Reject order done!");
				
				
				// Verify HMC value
				driver.get(testData.HMC.getHomePageUrl());
				HMCPage hmcPage = new HMCPage(driver);
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.HMCOrderCheck(driver, hmcPage, orderNumApproved);
				
				// Validate YB06 in xml
				if(!HMCCommon.GetYB06Value(hmcPage).equals("3"))
					Assert.fail("YB06 value is wrong");

				Dailylog.logInfoDB(1, "Order Number is: " + orderNumApproved, this.Store, this.testName);
			} else {
				Assert.fail("PayU is not configured yet!");
			}
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}

	}

public static void fillPayUInfo(WebDriver driver, TestData testData, String fullName) throws InterruptedException {
	Common.waitElementClickable(driver, driver.findElement(By.xpath("(//*[@id='pm-VISA'])[1]")), 20);
	driver.findElement(By.xpath("(//*[@id='pm-VISA'])[1]")).click();

	// enter the necessary information
	Common.sleep(5000);
	Common.waitElementClickable(driver, driver.findElement(By.xpath("//*[@id='cc_fullName']")), 10);
	driver.findElement(By.xpath("//*[@id='cc_fullName']")).clear();
	driver.findElement(By.xpath("//*[@id='cc_fullName']")).sendKeys(fullName);

	if (Common.checkElementDisplays(driver, By.xpath("//*[@id='cc_dniNumber']"), 1)) {
		driver.findElement(By.xpath("//*[@id='cc_dniNumber']")).clear();
		driver.findElement(By.xpath("//*[@id='cc_dniNumber']")).sendKeys("123456789");
	}

	driver.findElement(By.xpath("//*[@id='ccNumber']")).clear();
	driver.findElement(By.xpath("//*[@id='ccNumber']")).sendKeys("4012888888881881");

	if (Common.checkElementDisplays(driver, By.xpath("//*[@id='securityCodeAux_']"), 1)) {
		driver.findElement(By.xpath("//*[@id='securityCodeAux_']")).clear();
		driver.findElement(By.xpath("//*[@id='securityCodeAux_']")).sendKeys("881");
	} else {
		driver.findElement(By.xpath("//*[@id='securityCode']")).clear();
		driver.findElement(By.xpath("//*[@id='securityCode']")).sendKeys("881");
	}

	Select select = new Select(driver.findElement(By.xpath("//*[@id='expirationDateMonth']")));
	select.selectByValue("10");

	select = new Select(driver.findElement(By.xpath("//*[@id='expirationDateYear']")));
	select.selectByValue("30");

	if (driver.findElement(By.xpath("//*[@id='installments']")).isEnabled()) {
		select = new Select(driver.findElement(By.xpath("//*[@id='installments']")));
		select.selectByIndex(0);
	}

	if (Common.checkElementDisplays(driver, By.xpath("//select[@ng-model='selectedBank']"), 10)) {
		select = new Select(driver.findElement(By.xpath("//select[@ng-model='selectedBank']")));
		select.selectByVisibleText("Banorte");
	}

	if (Common.checkElementDisplays(driver, By.id("birthDateYear"), 1)) {
		select = new Select(driver.findElement(By.id("birthDateYear")));
		select.selectByVisibleText("1983");
		Thread.sleep(2000);
		select = new Select(driver.findElement(By.id("birthDateMonth")));
		select.selectByVisibleText("6");
		Thread.sleep(2000);
		select = new Select(driver.findElement(By.id("birthDateDay")));
		select.selectByVisibleText("25");
	}

	driver.findElement(By.xpath("//*[@id='contactPhone']")).clear();
	driver.findElement(By.xpath("//*[@id='contactPhone']")).sendKeys("3112222222");
	if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cc_street1']"), 1)) {
		driver.findElement(By.xpath(".//*[@id='cc_street1']")).clear();
		driver.findElement(By.xpath(".//*[@id='cc_street1']")).sendKeys(testData.B2C.getDefaultAddressLine1());
	}
	if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cc_street2']"), 1)) {
		driver.findElement(By.xpath("//*[@id='cc_street2']")).clear();
		driver.findElement(By.xpath("//*[@id='cc_street2']")).sendKeys("Co street1");
	}
	if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cc_city']"), 1)) {
		driver.findElement(By.xpath("//*[@id='cc_city']")).clear();
		driver.findElement(By.xpath("//*[@id='cc_city']")).sendKeys(testData.B2C.getDefaultAddressCity());
	}
	if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='cpfNumber']"), 1)) {
		driver.findElement(By.xpath("//*[@id='cpfNumber']")).clear();
		driver.findElement(By.xpath("//*[@id='cpfNumber']")).sendKeys(testData.B2C.getConsumerTaxNumber());
	}

	if (Common.checkElementDisplays(driver,
			By.xpath("//select[@ng-model='transaction.payer.billingAddress.state']"), 1)) {
		select = new Select(
				driver.findElement(By.xpath("//select[@ng-model='transaction.payer.billingAddress.state']")));
		select.selectByValue("Colima");
	}
	driver.findElement(By.xpath(".//*[@id='buyer_data_button_pay']")).click();
}

	

//	public String getAccountID(String country){
//		
//		String accountID = "";
//		
//		switch(country){
//			case "Columbia" :
//				accountID = "512321";
//				break;
//			case "Brazil" :
//				accountID = "512327";
//				break;
//			case "Mexico" :
//				accountID = "512324";
//				break;
//			default :
//				accountID = "";
//		}
//		
//		return accountID;
//		
//	}
	
	
	
//	public void from4To7() throws Exception{
//		driver.get(Url);
//		
//		driver.get(testData.B2C.getloginPageUrl());
//		B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
//		
//		String pdpUrl = "";
//		
//		String prodNumber = testData.B2C.getDefaultCTOPN();
//		
//		if(Url.endsWith("/")){
//			pdpUrl = Url + "p/" + prodNumber;
//		}else{
//			pdpUrl = Url + "/p/" + prodNumber;
//		}
//		
//		
//		driver.get(pdpUrl);
//		
//		B2CCommon.addProductToCartFromPDPPage(driver);
//		
//		// proceed to shipping 
//		b2cPage.lenovo_checkout.click();
//		
//		
//		// 5, On Shipping page, enter address, choose shipping method
//		B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());
//		
//		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
//		
//		// 6, On Payment page, choose PayU， continue
//		b2cPage.paymentPage_payU_Method.click();
//		
//		if(Common.isElementExist(driver, By.xpath(".//*[@id='useDeliveryAddress']")) && !driver.findElement(By.xpath(".//*[@id='useDeliveryAddress']")).isSelected()){
//			driver.findElement(By.xpath(".//*[@id='useDeliveryAddress']")).click();
//		}
//		
//		b2cPage.Payment_ContinueButton.click();
//		
//		// 7, On Review page, check Terms & Conditions, place order 
//		Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
//		B2CCommon.clickPlaceOrder(b2cPage);
//		
//		String currentUrl = driver.getCurrentUrl().toString();
//		
//		Assert.assertTrue(currentUrl.contains("payu"));
//	}
	
	
	
	
	
	
	
//	public String getCountry(String url){
//		
//		String coun = url.split("/")[3];
//		
//		String country = "";
//		
//		switch(coun){
//			case "co":
//				country = "Columbia";
//				break;
//			case "br":
//				country = "Brazil";
//				break;
//			case "mx":
//				country = "Mexico";
//				break;
//			default:
//				country = "NotExist";
//		}
//		
//		return country;
//
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
