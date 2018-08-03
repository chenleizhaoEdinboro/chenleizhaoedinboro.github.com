package TestScript.B2C;


import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.TestData;
import TestScript.SuperTestClass;

	
	public class NA20315Test extends SuperTestClass {
		B2CPage b2cPage;
		HMCPage hmcPage;
		Actions actions = null;
		String expectCardNumber;
		String expirationDateValue;
		String CVCValue;
		String productNum ;
		String address2 = "102 North End Avenue";
		//TestData data;
		
		public NA20315Test(String store) {
			this.Store = store;
			this.testName = "NA-20315";	
		}
		
		public void closePromotion(WebDriver driver, B2CPage page) {
			By Promotion = By.xpath(".//*[@title='Close (Esc)']");

			if (Common.isElementExist(driver, Promotion)) {

				Actions actions = new Actions(driver);

				actions.moveToElement(page.PromotionBanner).click().perform();

			}
		}


		public void HandleJSpring(WebDriver driver, B2CPage b2cPage,String loginID, String pwd) {

			if (!driver.getCurrentUrl().contains("account")) {

				driver.get(testData.B2C.getloginPageUrl());
				closePromotion(driver, b2cPage);
				if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
					b2cPage.SMB_LoginButton.click();
				}
				B2CCommon.login(b2cPage, loginID, pwd);
				B2CCommon.handleGateKeeper(b2cPage, testData);
			}
		}
		
		@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
		public void NA20315(ITestContext ctx){
			try{
				this.prepareTest();
				b2cPage = new B2CPage(driver);
				hmcPage = new HMCPage(driver);
				actions = new Actions(driver);
				productNum = testData.B2C.getDefaultMTMPN() ;
//				productNum = "65BAACC1US";
				
				Dailylog.logInfoDB(1,"Setting EnableKlarnaPayment to yes in HMC", Store,testName);
				setKlarnaPaymentInHMC(hmcPage, testData, "yes");
				
				Dailylog.logInfoDB(2,"Add an product.", Store,testName);
				Common.NavigateToUrl(driver, Browser,testData.B2C.getHomePageUrl());

				if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
					B2CCommon.handleGateKeeper(b2cPage, testData);
				} else {
					B2CCommon.handleGateKeeper(b2cPage, testData);
					// login B2C
					Common.NavigateToUrl(driver, Browser,testData.B2C.getloginPageUrl());

					B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
				}
				String loginID = testData.B2C.getLoginID();
				String pwd = testData.B2C.getLoginPassword();

				B2CCommon.handleGateKeeper(b2cPage, testData);
				HandleJSpring(driver,b2cPage, loginID, pwd);
				// empty cart
				B2CCommon.clearTheCart(driver, b2cPage, testData);
				// add product to cart
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
				

				
				Dailylog.logInfoDB(2,"Checkout and input shipping information.", Store,testName);
				b2cPage.lenovo_checkout.click();
				if(Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)){
					b2cPage.ASM_EditAddress.click();
				}
				B2CCommon.fillDefaultShippingInfo(b2cPage,testData);
				b2cPage.Shipping_AddressLine2TextBox.sendKeys(address2);
//				Thread.sleep(3000);
//				actions.sendKeys(Keys.PAGE_UP).perform();
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				
//				if (Common.checkElementDisplays(driver, driver.findElement(By.xpath("//input[@id='checkout_validateFrom_skip']")), 5))
//					driver.findElement(By.xpath("//input[@id='checkout_validateFrom_skip']")).click();
//				Common.sleep(5000);
				B2CCommon.handleAddressVerify(driver, b2cPage);
				Dailylog.logInfoDB(3,"Goto payment and check klarna widget shown on left side of the page.", Store,testName);
				Boolean KlarnaButton = false ;
				KlarnaButton = Common.checkElementDisplays(driver, b2cPage.Klarna_ApplyNow,30);
				System.out.println("Whether the Klarna widget shown on left side of the page : " +KlarnaButton);
				Assert.assertTrue(KlarnaButton,"Klarna applynow is not shown!");
//				Common.sleep(6000);
				
				Dailylog.logInfoDB(4,"Apply now and form displayed above the continue button.", Store,testName);
				klarnaPaymentApplyNow();
				
				Dailylog.logInfoDB(5,"Click continue then submite,and virtual credit card number, CVV and expiration date are shown.", Store,testName);
				getKlarnaInfomation();
				
				Dailylog.logInfoDB(7,"Copy to clipboard then continue.", Store,testName);
				pasteCardNumber();
				
				Dailylog.logInfoDB(8,"Fill payment info.", Store,testName);
				fillPaymentInfo();
				
				B2CCommon.fillPaymentAddressInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
				if(Store=="US_BPCTO"){
					b2cPage.payment_purchaseNum.sendKeys("123");
					b2cPage.payment_purchaseDate.sendKeys(Keys.chord(Keys.ENTER));
				} 
								
				Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
//				System.out.println("Click on continue button again! ");			
				
//				String paymentNum = b2cPage.OrderSummary_checkoutReviewPaymentNum.getText();
//				System.out.println("The payment num is: "+paymentNum);
//				paymentNum = paymentNum.substring(paymentNum.length()-4, paymentNum.length());
//				expectCardNumber = expectCardNumber.substring(expectCardNumber.length()-4, expectCardNumber.length());
//				Assert.assertEquals(paymentNum, expectCardNumber);
				
				Dailylog.logInfoDB(9,"Continue to place order.", Store,testName);
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);
				//String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
				String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
				
				//The KlarnaPayment is set to the initial state 
				//setKlarnaPaymentInHMC(hmcPage, testData, "no");
				
			} catch (Throwable e){
				handleThrowable(e, ctx);
			}
		}
		
		public void fillPaymentInfo(){
			actions.sendKeys(Keys.PAGE_UP).perform();
			//b2cPage.Payment_CreditCardRadioButton.click();
			WebElement cardTypeElement = driver.findElement(By.xpath("//select[@id='c-ct']"));
			Select sel =new Select(cardTypeElement);
			sel.selectByVisibleText("Mastercard");
			System.out.println("Select the card type! ");
//			String month1 = expirationDateValue.substring(0, 1);
//			String month2 = expirationDateValue.substring(1, 2);
//			String month;
//			if(month2.equals(" ")){
//				month = month1;
//			} else {
//				month = month1+month2;
//			}
//			String year = expirationDateValue.substring(6, 8);
//			b2cPage.Payment_CardMonthTextBox.sendKeys(month);
//			b2cPage.Payment_CardYearTextBox.sendKeys(year);
			if (!b2cPage.Payment_CardMonthTextBox.getTagName().toLowerCase().equals("select")) {
				b2cPage.Payment_CardMonthTextBox.clear();
				b2cPage.Payment_CardMonthTextBox.sendKeys("12");
				b2cPage.Payment_CardYearTextBox.clear();
				b2cPage.Payment_CardYearTextBox.sendKeys("20");
			} else {
				// Dropdown month and year
				Select dropdown = new Select(b2cPage.Payment_CardMonthTextBox);
				dropdown.selectByVisibleText("12");
				dropdown = new Select(b2cPage.Payment_CardYearTextBox);
				dropdown.selectByVisibleText("2020");
			}
			System.out.println("Input the card year/month! ");
			
			b2cPage.Payment_SecurityCodeTextBox.sendKeys(CVCValue);
			System.out.println("Input the SecurityCode! ");
//			Common.sleep(6);
			driver.switchTo().defaultContent();
			b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
			System.out.println("Input the CardHolderName! ");
		}

		public void pasteCardNumber() throws AWTException{
			driver.switchTo().frame(b2cPage.Payment_CreditCardFrame);
//			actions.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Payment_CardNumberTextBox.clear();
			b2cPage.Payment_CardNumberTextBox.sendKeys(Keys.chord(Keys.CONTROL, "v"));
			
			System.out.println("Paste!");
			Common.sleep(2000);
		}
		
		public void klarnaPaymentApplyNow() throws InterruptedException{
			b2cPage.Payment_KlarnaApplyNow.click();
			System.out.println("Click on apply now");
			
			Common.checkElementDisplays(driver, By.xpath("//*[@id='lia-iframe']"), 10);
			driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='lia-iframe']")));
//			Boolean KlarnaInfoForm = false ;
//			KlarnaInfoForm = Common.checkElementDisplays(driver, b2cPage.Payment_KlarnaInfoForm,10);
//			System.out.println("Whether the Klarna info form displayed above the continue button : " +KlarnaInfoForm);	
//			Assert.assertTrue(KlarnaInfoForm);
		}
	
		public void getKlarnaInfomation(){
			Actions actions = new Actions(driver);
//			actions.sendKeys(Keys.PAGE_DOWN).perform();
			Common.sleep(5000);

//			Common.waitElementClickable(driver, b2cPage.Payment_KlarnaContinue, 10);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", b2cPage.Payment_KlarnaContinue);
//			b2cPage.Payment_KlarnaContinue.click();
			System.out.println("Click on continue");
			//driver.switchTo().frame("klarna-credit-main");
			//b2cPage.Payment_KlarnaTerms.click();
			//driver.switchTo().parentFrame();
			Common.sleep(6000);
//			b2cPage.Payment_KlarnaContinue.click();
			executor.executeScript("arguments[0].click();", b2cPage.Payment_KlarnaSubmit);
			System.out.println("Click on submit button!");
			
			Boolean creditCardNumber = Common.checkElementDisplays(driver, b2cPage.Payment_KlarnaCreditCardNumber,20);
			System.out.println("Whether the credit card number displayed : " +creditCardNumber);
			Assert.assertTrue(creditCardNumber);
			expectCardNumber = b2cPage.Payment_KlarnaCreditCardNumber1.getText() +
					b2cPage.Payment_KlarnaCreditCardNumber2.getText() +
					b2cPage.Payment_KlarnaCreditCardNumber3.getText() +
					b2cPage.Payment_KlarnaCreditCardNumber4.getText();
			System.out.println("The number in Klarna is: "+expectCardNumber);			
			
			Boolean expirationDate = Common.checkElementDisplays(driver, b2cPage.Payment_KlarnaExpirationDate,10);
			System.out.println("Whether the expiration date displayed : " +expirationDate);
			Assert.assertTrue(expirationDate);
			expirationDateValue = b2cPage.Payment_KlarnaExpirationDate.getText();
			System.out.println("The expiration date is: "+ expirationDateValue);
			
			Boolean CVC = Common.checkElementDisplays(driver, b2cPage.Payment_KlarnaCVC,10);
			System.out.println("Whether the CVC displayed : " +CVC);
			Assert.assertTrue(CVC);
			CVCValue = b2cPage.Payment_KlarnaCVC.getText();
			System.out.println("The CVC is: "+ CVCValue);
//			Common.sleep(1000);
			b2cPage.Payment_KlarnaCopyToClipboard.click();
//			executor.executeScript("arguments[0].click();", b2cPage.Payment_KlarnaCopyToClipboard);
			System.out.println("Click on Copy To Clipboard!");
			Common.sleep(3000);
			driver.switchTo().defaultContent();
		}
		
		public void setKlarnaPaymentInHMC(HMCPage hmcPage, TestData data, String whetherDisplay){
			driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, data);
			HMCCommon.searchB2CUnit(hmcPage, data);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			if(whetherDisplay=="yes"){
				hmcPage.B2CUnit_DisplayEnableKlarnaPayment.click();
			} else {
				hmcPage.B2CUnit_NotDisplayEnableKlarnaPayment.click();
			}		
			hmcPage.Common_SaveButton.click();
			System.out.println("Successfully setting in HMC!");
		} 
}