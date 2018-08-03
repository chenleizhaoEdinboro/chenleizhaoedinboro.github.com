package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
import TestScript.SuperTestClass;


public class NA27123Test extends SuperTestClass{

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	
	public String maximumThreshold;
	public String paymentProfile_Name = "profile_cod_27123";
	public String cartUrl;
	
	
	public NA27123Test(String Store){
		this.Store = Store;
		this.testName = "NA-27123";
	}
	
	@Test(priority = 0, alwaysRun = true, enabled = true,groups={"shopgroup","payment","p2","b2c"})
	public void NA27123(ITestContext ctx){
		try{
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			
			//before testing, we need to get the default MTM Product price
			driver.get(testData.B2C.getHomePageUrl());
			
			cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			
			driver.get(cartUrl);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			Common.sleep(1000);
			b2cPage.Cart_AddButton.click();
			
			String productPrice = driver.findElement(By.xpath("//dl[contains(@class,'salePrice')]")).getText().trim();
						
			float product_price = B2CCommon.GetPriceValue(productPrice);
			
			float finalPrice = (float) (product_price*1.5);
			
			maximumThreshold = finalPrice + "";
			
			Dailylog.logInfoDB(1, "before testing , the maxmize threshold price should be :" + finalPrice, Store, testName);

			//1 Go to B2C 'India' unit, 
			//			1. Delete any payment profile, 
			//			2. Set Disable Payment Profile Fallback = No,
			//			3. Add COD as a payment type 
			//			save
			
			Dailylog.logInfoDB(1, "GO to B2C unit , delete any payment profiles", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			
			Thread.sleep(4000);

			HMCCommon.Login(hmcPage, testData);
			
			Thread.sleep(4000);
			
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2BUnit_ResultItem.click();
			
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			// delete any payment profile 
			if(Common.checkElementDisplays(driver, By.xpath("//tbody[@id='Content/GenericResortableItemList_tbody']/tr/td[@class='gilcIcon']"), 4)){
				List<WebElement> paymentProfileList = driver.findElements(By.xpath("//tbody[@id='Content/GenericResortableItemList_tbody']/tr/td[@class='gilcIcon']"));
				
				for(int x = 1 ; x <= paymentProfileList.size(); x++){
					
					Common.rightClick(driver, driver.findElement(By.xpath("(//tbody[@id='Content/GenericResortableItemList_tbody']/tr/td[@class='gilcIcon'])[1]")));
					
					driver.findElement(By.xpath("//td[contains(@id,'remove_true') and @class='icon']")).click();
					
					Thread.sleep(3000);
					
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}

			}else{
				Dailylog.logInfoDB(1, "payment profile is not existing !!!", Store, testName);
			}
			
			//set Set Disable Payment Profile Fallback = No
			
			if(!driver.findElement(By.xpath("//input[contains(@id,'disablePaymentMessageFallback') and contains(@id,'false')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'disablePaymentMessageFallback') and contains(@id,'false')]")).click();
			}
			
			// Add COD as a payment type 
			
			if(Common.checkElementDisplays(driver, By.xpath("//div[contains(@id,'COD') and @class='gilcEntry']"), 4)){
				Dailylog.logInfoDB(1, "COD Payment type is already existing !!", Store, testName);
				
			}else{
				AddPaymentType_COD();
			}
			
			Common.sleep(3000);

			driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zEnableCheckoutRedesignForBackgroundLogic') and contains(@id,'false')]")).click();
			
			hmcPage.HMC_Save.click();
			
			
			//2, Go to Nemo --> Payment --> Payment Profile, create a new profile for COD, save
			
			Dailylog.logInfoDB(2, "Go to Nemo --> Payment --> Payment Profile, create a new profile for COD, save", Store, testName);
			
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();
			Common.sleep(3000);
			
			hmcPage.Home_paymentProfile.click();
			
			Thread.sleep(4000);
			
			Select select_attribute = new Select(driver.findElement(By.xpath("//select[contains(@id,'attributeselect')]")));
			select_attribute.selectByValue("name");
			
			Common.sendFieldValue(driver.findElement(By.xpath("//input[contains(@id,'PaymentTypeProfile.name')]")), paymentProfile_Name);
			
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			Common.sleep(3000);
			
			if(Common.isElementExist(driver, By.xpath("//div[@class='olecEntry' and contains(@id,'"+paymentProfile_Name+"')]"))){
				
				Common.rightClick(driver, driver.findElement(By.xpath("//div[@class='olecEntry' and contains(@id,'"+paymentProfile_Name+"')]")));
				
				Common.sleep(3000);
				
				driver.findElement(By.xpath("//td[contains(@id,'remove_true_label')]")).click();
				
				Common.sleep(2000);
				
				Alert alert = driver.switchTo().alert();
				alert.accept();
				
			}
			hmcPage.Home_closeSession.click();
			HMCCommon.Login(hmcPage, testData);
			
			createNewProfile_COD();
		
			//3, Go to Nemo --> Cart Checkout --> Nemo India Pincode, Note down at least two records
			Dailylog.logInfoDB(3, "Go to Nemo --> Cart Checkout --> Nemo India Pincode, Note down at least two records", Store, testName);
			
			hmcPage.Home_closeSession.click();
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Nemo.click();
			
			driver.findElement(By.xpath("//a[contains(@id,'group.nemo.cartcheckout')]")).click();
			driver.findElement(By.xpath("//a[contains(@id,'NemoIndiaPincode')]")).click();
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			
			if(!Common.isElementExist(driver, By.xpath("//*[@id='Content/StringDisplay[123456]_span']"))){
				hmcPage.Home_closeSession.click();
				HMCCommon.Login(hmcPage, testData);
				createNemoIndiaPincode("123456","true");
			}
			
			hmcPage.Home_closeSession.click();
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Nemo.click();
			
			driver.findElement(By.xpath("//a[contains(@id,'group.nemo.cartcheckout')]")).click();
			driver.findElement(By.xpath("//a[contains(@id,'NemoIndiaPincode')]")).click();
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			
			if(!Common.isElementExist(driver, By.xpath(".//*[@id='Content/StringDisplay[234567]_span']"))){
				hmcPage.Home_closeSession.click();
				HMCCommon.Login(hmcPage, testData);
				createNemoIndiaPincode("234567","false");
			}
			
			hmcPage.Home_closeSession.click();
			
			
			//4, Go to https://tun-c-hybris.lenovo.com/in/en/cart, add item to cart
			Dailylog.logInfoDB(4, "Go to https://tun-c-hybris.lenovo.com/in/en/cart, add item to cart", Store, testName);
			
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			driver.get(cartUrl);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			Common.sleep(1000);
			b2cPage.Cart_AddButton.click();
			
			//5,Proceed to shipping page, enter pincode '2ed23e', click 'Continue'
			Dailylog.logInfoDB(5, "Proceed to shipping page, enter pincode '2ed23e', click 'Continue'", Store, testName);
			
			b2cPage.Cart_CheckoutButton.click();
			clickShippingEditLink();
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("2ed23e");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			
			Thread.sleep(8000);
			
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//span[@id='postcode.errors']"), 5), "when enter the pincode 2ed23e, error message does not display");
			
			String errorMessage_1 = driver.findElement(By.xpath("//span[@id='postcode.errors']")).getText().toString().trim();
			Dailylog.logInfoDB(5, "errorMessage_1 is :" + errorMessage_1, Store, testName);
			
			Assert.assertTrue(errorMessage_1.equals("Please enter a valid value"), "error message is not <Please enter a valid value>");
			
			
			//6, Enter pincode '123456', click 'Continue'
			Dailylog.logInfoDB(6, "Enter pincode '123456', click 'Continue'", Store, testName);
			
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("123456");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_COD']")) && driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_COD']")).isDisplayed(), "COD is not exsiting on the payment page");
			
			//7, Go back to shipping page, enter pincode '183723', click 'Continue'
			Dailylog.logInfoDB(7, "Go back to shipping page, enter pincode '183723', click 'Continue'", Store, testName);
			
			driver.get(cartUrl);
			
			b2cPage.Cart_CheckoutButton.click();
			clickShippingEditLink();
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("183723");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);

			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//span[@id='postcode.errors']"), 5), "when enter the pincode 183723, error message does not display");
			
			String errorMessage_2 = driver.findElement(By.xpath("//span[@id='postcode.errors']")).getText().toString().trim();
			
			Assert.assertTrue(errorMessage_2.equals("Sorry, we do not deliver to this pincode at this time"), "error message is not <Sorry, we do not deliver to this pincode at this time>");
			
			//8,Enter pincode '234567', click 'Continue'
			Dailylog.logInfoDB(8, "Enter pincode '234567', click 'Continue'", Store, testName);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("234567");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);
			
			String globalMessage = driver.findElement(By.xpath("//*[@id='globalMessages']/div")).getText().toString().trim();
			Dailylog.logInfoDB(8, "globalMessage is :" + globalMessage, Store, testName);
			Assert.assertTrue(globalMessage.endsWith("Cash on Delivery payment option is not available for your delivery area."),"the global message on payment page is not <Cash on Delivery payment option is not available for your delivery area.>");
			
			//9, Go back to cart page, change quantity to 2, price is now > 3000
			Dailylog.logInfoDB(9, "Go back to cart page, change quantity to 2, price is now > 3000", Store, testName);
			
			driver.get(cartUrl);
			
			Common.sendFieldValue(driver.findElement(By.xpath("//input[@name='quantity']")), "2");
			
			driver.findElement(By.xpath("//input[@value='Update']")).click();
			
			
			//10,Proceed to shipping page, enter pincode '2ed23e', click 'Continue'
			Dailylog.logInfoDB(10, "Proceed to shipping page, enter pincode '2ed23e', click 'Continue'", Store, testName);
			
			b2cPage.Cart_CheckoutButton.click();
			clickShippingEditLink();
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("2ed23e");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);
			
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//span[@id='postcode.errors']"), 5), "when enter the pincode 2ed23e, error message does not display");
			
			String errorMessage_3 = driver.findElement(By.xpath("//span[@id='postcode.errors']")).getText().toString().trim();
			
			Assert.assertTrue(errorMessage_3.equals("Please enter a valid value"), "error message is not <Please enter a valid value>");
			
			
			//11,Enter pincode '123456', click 'Continue'
			Dailylog.logInfoDB(11, "Enter pincode '123456', click 'Continue'", Store, testName);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("123456");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);
			
			Dailylog.logInfoDB(11, "cod existing ? :" + !Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_COD']")), Store, testName);
			Assert.assertTrue(!Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_COD']")),"COD existing on the payment page");
			
			//12, Go back to shipping page, enter pincode '183723', click 'Continue'
			Dailylog.logInfoDB(12, "Go back to shipping page, enter pincode '183723', click 'Continue'", Store, testName);
			
			driver.get(cartUrl);
			
			b2cPage.Cart_CheckoutButton.click();
			clickShippingEditLink();
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("183723");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);
			
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//span[@id='postcode.errors']"), 5), "when enter the pincode 183723, error message does not display");
			
			String errorMessage_4 = driver.findElement(By.xpath("//span[@id='postcode.errors']")).getText().toString().trim();
			
			Assert.assertTrue(errorMessage_4.equals("Sorry, we do not deliver to this pincode at this time"), "error message is not <Sorry, we do not deliver to this pincode at this time>");
			
			//13, Enter pincode '234567', click 'Continue'
			Dailylog.logInfoDB(13, "Enter pincode '234567', click 'Continue'", Store, testName);
			
			driver.get(cartUrl);
			
			b2cPage.Cart_CheckoutButton.click();
			clickShippingEditLink();
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			driver.findElement(By.xpath("//input[@id='postcode']")).clear();
			driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys("234567");
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(8000);
			
			Assert.assertTrue(!Common.checkElementDisplays(driver, By.xpath("//*[@id='PaymentTypeSelection_COD']"), 4),"COD is listed as a payment option");

		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	
	}

	
	public void clickShippingEditLink(){
		if(Common.checkElementDisplays(driver,b2cPage.Shipping_editAddress, 4)){
			b2cPage.Shipping_editAddress.click();
		}
	}
	
	
	
	public void AddPaymentType_COD() {
		
		Common.rightClick(driver, hmcPage.B2CSiteAttribute_PayTypeHead);
		hmcPage.B2CSiteAttribute_AddPayType.click();
		Common.sleep(3000);
		Common.switchToWindow(driver, 1);
		Common.sendFieldValue(hmcPage.PaymentType_CheckoutCode, "COD");
		hmcPage.PaymentMessage_Search.click();
		Common.doubleClick(driver,driver.findElement(By.xpath("//div/div[contains(@id,'COD')]")));
		
		Common.switchToWindow(driver, 0);
		Common.sleep(3000);
		hmcPage.HMC_Save.click();
		Common.sleep(5000);
		
	}
	
	
	
	public void createNewProfile_COD() throws Exception{
		
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		Common.sleep(3000);
		
		Common.rightClick(driver, hmcPage.Home_paymentProfile);
		Common.sleep(1500);
		hmcPage.Home_paymentProfileCreate.click();
		//B2C Properties
		Common.sendFieldValue(hmcPage.paymentProfile_name, paymentProfile_Name);
		Common.sendFieldValue(hmcPage.paymentProfile_code, "COD_Auto");			
		hmcPage.paymentProfile_paymentType.click();
		
		driver.findElement(By.xpath("//select[contains(@id,'checkoutPaymentType')]/option[contains(text(),'Cash on Delivery')]")).click();
		hmcPage.paymentProfile_configLevel.click();
		hmcPage.paymentProfile_configLevelS.click();
		hmcPage.paymentProfile_channel.click();
		driver.findElement(By.xpath("//select[contains(@id,'channel')]/option[contains(text(),'B2C')]")).click();
		
		hmcPage.paymentProfile_activeTrue.click();
		
		driver.findElement(By.xpath("//input[contains(@id,'enablePaymentThresholdRule') and contains(@id,'true')]")).click();
		
		// set the threshold maximum
		Common.rightClick(driver, driver.findElement(By.xpath("(//div[@title='Currency'])[last()]")));
		
		Common.scrollToElement(driver, driver.findElement(By.xpath("(//td[contains(@class,'name') and contains(.,'Create')])[last()]")));
		Common.sleep(3000);
		Common.mouseHover(driver, driver.findElement(By.xpath("(//td[contains(@class,'name') and contains(.,'Create')])[last()]")));
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("(//td[contains(@id,'create_PromotionPriceRow_label')])[last()]")).click();
		
		Select select = new Select(driver.findElement(By.xpath("//select[contains(@id,'CreateItemListEntry')]")));
		select.selectByVisibleText("INR");
		
		driver.findElement(By.xpath("//input[contains(@id,'CreateItemListEntry')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'CreateItemListEntry')]")).sendKeys(maximumThreshold);
		
		hmcPage.HMC_Save.click();
		
	}
	
	public void createNemoIndiaPincode(String pincode,String judge){
		hmcPage.Home_Nemo.click();
		
		driver.findElement(By.xpath("//a[contains(@id,'group.nemo.cartcheckout')]")).click();
		
		Common.rightClick(driver, driver.findElement(By.xpath("//a[contains(@id,'NemoIndiaPincode')]")));
		
		driver.findElement(By.xpath("//td[contains(@id,'create_NemoIndiaPincode_label')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'zPincode')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'zPincode')]")).sendKeys(pincode);
		
		driver.findElement(By.xpath("//input[contains(@id,'zEnableCashOnDeliveryPayment') and contains(@id,'"+judge+"')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'zChannel')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'zChannel')]")).sendKeys("B2C");

		driver.findElement(By.xpath("//div[contains(@id,'saveandcreate')]")).click();
		
		hmcPage.HMC_Save.click();
	}
	
	
}
