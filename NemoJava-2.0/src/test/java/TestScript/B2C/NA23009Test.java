package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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

public class NA23009Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String partNumber;
	public String partNumberBackup="80Y70064US";
	public int i=1;
	
	public NA23009Test(String Store){
		this.Store = Store;
		this.testName = "NA-23009";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup","pricingpromot" ,"p2", "b2c" })
	public void NA23009(ITestContext ctx){
		try {
			this.prepareTest();
			mailPage =  new MailPage(driver);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			Actions actions = new Actions(driver);
			
			Dailylog.logInfoDB(i++,"HMC Setting Under Store Level",Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			hmcPage.B2BCustomer_1stSearchedResult.click();
			hmcPage.baseStore_administration.click();
			
			if(hmcPage.basestore_NetValue.getAttribute("value").contains("false")){
				hmcPage.basestore_NetCheck.click();
			}
			hmcPage.basestore_NoTax.click();
			hmcPage.Types_SaveBtn.click();
			Common.sleep(5000);
			// Manul Setting in HMC
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_EnableGEOLink.click();
			hmcPage.B2CUnit_EnableTax.click();
			hmcPage.B2CUnit_EnableSabrixEngine.click();
			hmcPage.Types_SaveBtn.click();
			
			Dailylog.logInfoDB(i++,"Go to US WebSite Page",Store,testName);
			driver.get(testData.B2C.getHomePageUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			HandleJSpring(driver);
			Common.sleep(5000);
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			if(!Common.checkElementExists(driver, b2cPage.Add2Cart, 5)){
				partNumber=partNumberBackup;
			}else{
				partNumber=testData.B2C.getDefaultMTMPN();
			}
			
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			
					
			boolean newCartFlag=false;
			if (Common.isElementExist(driver, By.xpath("//button[contains(@class,'Submit_Button')]"))){
				newCartFlag=true;
			}
			System.out.println("newCartFlag is "+newCartFlag);
			
			float CartSubPrice;
			float CartTotalPrice;
			float CartPromotion=0;
			boolean cartPromotion=false;
			
			if(newCartFlag){
				if(Common.isElementExist(driver, By.xpath(".//*[@id='emptyCartItemsForm']/a/img"))){
					b2cPage.NewCart_DeleteButton.click();
					b2cPage.NewCart_ConfirmDelete.click();					
				}
				b2cPage.NewCart_PartNumberTextBox.clear();
				b2cPage.NewCart_PartNumberTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
				b2cPage.NewCart_Submit.click();
				
				CartSubPrice=GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				CartTotalPrice=GetPriceValue(b2cPage.NewCart_PriceTotal.getText());
				
				if(Common.checkElementDisplays(driver, b2cPage.NewCart_CartPromotionPrice, 5)){
					CartPromotion=GetPriceValue(b2cPage.NewCart_CartPromotionPrice.getText());
					cartPromotion=true;
				}else{
					CartPromotion=0;
				}

			}else{
				if (Common.isElementExist(driver,
						By.xpath("//a[contains(text(),'Empty cart')]"))) {
					driver.findElement(
							By.xpath("//a[contains(text(),'Empty cart')]")).click();
				}
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
				if(Common.isElementExist(driver,By.id("active_button"))){
					driver.findElement(By.id("active_button")).click();
					Common.sleep(5000);
				}
				
				CartSubPrice = GetPriceValue(b2cPage.Cart_PriceSubTotal.getText());
				CartTotalPrice = GetPriceValue(b2cPage.Cart_PriceTotal.getText());
				
				if(Common.checkElementDisplays(driver, b2cPage.Cart_CartPromotion, 5)){
					CartPromotion=GetPriceValue(b2cPage.Cart_CartPromotion.getText());
					cartPromotion=true;
				}else{
					CartPromotion=0;
				}
				
			}	
			
			Dailylog.logInfoDB(i++,"Cart page-CartSubPrice:"+CartSubPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Cart page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(i++,"Cart page-CartTotalPrice:"+CartTotalPrice,Store,testName);
			Assert.assertEquals(CartSubPrice-CartPromotion, CartTotalPrice, 0.01);
			
			Common.sleep(3000);
			JavascriptExecutor js= (JavascriptExecutor) driver;

			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='close-lnv-call-center']"), 5)){
				driver.findElement(By.xpath("//div[@class='close-lnv-call-center']")).click();
			}
			b2cPage.Cart_CheckoutButton.click();
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			Dailylog.logInfoDB(i++,"Go to Shipping page",Store,testName);
			if(Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5)){
				b2cPage.Shipping_editAddress.click();
			}
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			float ShippingSubPrice;
			float ShippingTotalPrice;
			float shippingPrice ;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping_ShippingTotalPrice, 5)){
				ShippingSubPrice=GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				ShippingTotalPrice=GetPriceValue(b2cPage.NewShipping_ShippingTotalPrice.getText());
				
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.NewPayment_CartPromotionPrice.getText());
				}else{
					CartPromotion=0;
				}
				
				if(b2cPage.NewShipping_ShippingPrice.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Shipping_PriceShipping.getText());
				}
								
			}else{
				ShippingSubPrice = GetPriceValue(b2cPage.Shipping_PriceSubTotal.getText());
				ShippingTotalPrice = GetPriceValue(b2cPage.Shipping_PriceTotal.getText());
				if(b2cPage.Shipping_PriceShipping.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Shipping_PriceShipping.getText());
				}
				if(Common.checkElementDisplays(driver, b2cPage.Cart_CartPromotion, 5)){
					CartPromotion=GetPriceValue(b2cPage.Cart_CartPromotion.getText());
					cartPromotion=true;
				}else{
					CartPromotion=0;
				}
				
			}
			
			Dailylog.logInfoDB(i++,"Shipping page-ShippingSubPrice:"+ShippingSubPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Shipping page-shippingPrice:"+shippingPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Shipping page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(i++,"Shipping page-ShippingTotalPrice:"+ShippingTotalPrice,Store,testName);
			Assert.assertEquals(ShippingSubPrice+shippingPrice-CartPromotion, ShippingTotalPrice, 0.01);
			Common.sleep(3000);			
			actions.sendKeys(Keys.PAGE_UP).perform();
			
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);	
			
			if (Common.checkElementExists(driver, b2cPage.NewShipping_AddressValidateButton, 3)){
				js.executeScript("arguments[0].click();", b2cPage.NewShipping_AddressValidateButton);	
			}

			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddressItem, 3)){
				js.executeScript("arguments[0].click();", b2cPage.Shipping_validateAddressItem);	
			}
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
				b2cPage.ValidateInfo_SkipButton.click();
			}
				
			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddress, 3)){
				js.executeScript("arguments[0].click();", b2cPage.Shipping_validateAddress);	
			}
			Thread.sleep(3000);
			Dailylog.logInfoDB(i++,"Go to Payment page",Store,testName);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			
			float PaymentTaxPrice;
			float PaymentSubPrice;
			float PaymentTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping__TotalPrice, 5)){
				PaymentSubPrice=GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				PaymentTotalPrice=GetPriceValue(b2cPage.NewShipping__TotalPrice.getText());
				PaymentTaxPrice=GetPriceValue(b2cPage.NewPayment_TaxPrice.getText());
						
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.NewPayment_CartPromotionPrice.getText());
				}else{
					CartPromotion=0;
				}
				
				if(b2cPage.NewShipping_ShippingPrice.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Shipping_PriceShipping.getText());
				}
								
			}else{
				PaymentSubPrice = GetPriceValue(b2cPage.Shipping_PriceSubTotal.getText());
				PaymentTotalPrice = GetPriceValue(b2cPage.Shipping_PriceTotal.getText());
				PaymentTaxPrice  = GetPriceValue(b2cPage.Payment_Tax.getText());
				if(b2cPage.Shipping_PriceShipping.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Shipping_PriceShipping.getText());
				}
				
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.Cart_CartPromotion.getText());
				}else{
					CartPromotion=0;
				}
				
			}
			
			Dailylog.logInfoDB(i++,"Paymnet page-PaymentSubPrice:"+PaymentSubPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Paymnet page-shippingPrice:"+shippingPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Paymnet page-PaymentTaxPrice:"+PaymentTaxPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Paymnet page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(i++,"Paymnet page-PaymentTotalPrice:"+PaymentTotalPrice,Store,testName);
			Assert.assertEquals(PaymentSubPrice+shippingPrice+PaymentTaxPrice-CartPromotion, PaymentTotalPrice, 0.01);
					
			js.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(i++,"Go to Review page",Store,testName);
			
			float ReviewTaxPrice;
			float ReviewSubPrice;
			float ReviewTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewReview_PriceTotal, 5)){
				ReviewSubPrice=GetPriceValue(b2cPage.NewSummary__ItemPrice.get(0).getText());
				ReviewTotalPrice=GetPriceValue(b2cPage.NewReview_PriceTotal.getText());
				ReviewTaxPrice=GetPriceValue(b2cPage.NewSummary__ItemPrice.get(1).getText());
				
				if(b2cPage.NewReview_ShippingPrice.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.NewReview_ShippingPrice.getText());
				}
				
//				if(cartPromotion){
//					CartPromotion=GetPriceValue(b2cPage.Thank_CartPromotion.getText());
//				}else{
//					CartPromotion=0;
//				}
								
			}else{
				ReviewTaxPrice = GetPriceValue(b2cPage.Payment_Tax.getText());
				ReviewSubPrice = GetPriceValue(b2cPage.Review_PriceSubTotal.getText());
				ReviewTotalPrice = GetPriceValue(b2cPage.Review_PriceTotal.getText());
				if(b2cPage.Review_ShippingPrice.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Review_ShippingPrice.getText());
				}
				
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.Review_CartPromotion.getText());
				}else{
					CartPromotion=0;
				}
			}
			
			Dailylog.logInfoDB(i++,"Review page-ReviewSubPrice:"+ReviewSubPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Review page-ReviewTaxPrice:"+ReviewTaxPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Review page-shippingPrice:"+shippingPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Review page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(i++,"Review page-ReviewTotalPrice:"+ReviewTotalPrice,Store,testName);
			
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(i++,"Go to Thank you page",Store,testName);
			
			float ThankTaxPrice;
			float ThankTotalPrice;
			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);		
			Dailylog.logInfoDB(i++,"Order number is "+OrderNumber,Store,testName);
			
			if(Common.checkElementDisplays(driver, b2cPage.NewThank_TaxPrice, 5)){

				ThankTotalPrice=GetPriceValue(b2cPage.NewThankPage__TotalPrice.getText());
				ThankTaxPrice=GetPriceValue(b2cPage.NewThank_TaxPrice.getText());

				if(b2cPage.NewThank_ShippingPrice.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.NewThank_ShippingPrice.getText());
				}
				
//				if(cartPromotion){
//					CartPromotion=GetPriceValue(b2cPage.Thank_CartPromotion.getText());
//				}else{
//					CartPromotion=0;
//				}
								
			}else{
				ThankTaxPrice = GetPriceValue(b2cPage.Thank_Tax.getText());
				ThankTotalPrice = GetPriceValue(b2cPage.Thank_PriceTotal.getText());
				if(b2cPage.Thank_ShippingPrice.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Thank_ShippingPrice.getText());
				}
				
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.Thank_CartPromotion.getText());
				}else{
					CartPromotion=0;
				}
			}
			
			
			Dailylog.logInfoDB(i++,"Thankyou page-ThankTaxPrice:"+ThankTaxPrice,Store,testName);
			//Dailylog.logInfoDB(i++,"Thankyou page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(i++,"Thankyou page-ThankTotalPrice:"+ThankTotalPrice,Store,testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_Order.click();
	        hmcPage.Home_Order_Orders.click();
	        hmcPage.Home_Order_OrderID.clear();
	        hmcPage.Home_Order_OrderID.sendKeys(OrderNumber);
	        hmcPage.Home_Order_OrderSearch.click();
	        Thread.sleep(5000);
	        Dailylog.logInfoDB(i++,"Order Status is : "+hmcPage.Home_Order_OrderStatus.getText(),Store,testName);
	        
	        if(!hmcPage.Home_Order_OrderStatus.getText().contains("Completed")){
	            Thread.sleep(5000);
	            hmcPage.Home_Order_OrderSearch.click();
	        }
	        Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"), "Order Status is Failed"); 
	        hmcPage.Home_Order_OrderDetail.click();
	        //hmcPage.OrderReload.click();
	        Thread.sleep(5000);
	        
	        float HMCSubPrice = GetPriceValue(hmcPage.Home_Order_ProductPrice.getAttribute("value"));
	        float HMCTaxPrice =GetPriceValue(hmcPage.Home_Order_TaxPrice.getAttribute("value"));
	        Dailylog.logInfoDB(i++,"HMC Page-HMCSubPrice: "+HMCSubPrice,Store,testName);
	        Dailylog.logInfoDB(i++,"HMC Page-HMCTaxPrice: "+HMCTaxPrice,Store,testName);
	        hmcPage.Home_Order_OrderAdmin.click();
	        Thread.sleep(5000);
	        Assert.assertTrue(hmcPage.Orders_OrderXML.getText().contains("xml"));
	        
	       
	        Dailylog.logInfoDB(i++,"Verify Price",Store,testName);
	        Assert.assertTrue(PaymentSubPrice==CartSubPrice&&ShippingSubPrice==ReviewSubPrice&&ShippingSubPrice==CartSubPrice,"TotalPrice is not same");
	        Assert.assertTrue(ThankTotalPrice==PaymentTotalPrice&&ThankTotalPrice==ReviewTotalPrice,"TotalPrice is not same");
	        Assert.assertTrue(ThankTaxPrice==PaymentTaxPrice&&ThankTaxPrice==ReviewTaxPrice&&ThankTaxPrice==HMCTaxPrice,"Tax price is not same");
	        
	        
	        Assert.assertEquals(ThankTotalPrice, ReviewTotalPrice, 0.1);
	        Assert.assertEquals(ThankTotalPrice, PaymentTotalPrice, 0.1);
	        Assert.assertEquals(ThankTaxPrice, ReviewTaxPrice, 0.1);
	        Assert.assertEquals(ThankTaxPrice, PaymentTaxPrice, 0.1);
	        if(shippingPrice<0.1){
	        	Assert.assertEquals(PaymentSubPrice, ShippingTotalPrice, 0.1);
		        Assert.assertEquals(PaymentSubPrice, HMCSubPrice, 0.1);
		        Assert.assertEquals(ThankTaxPrice, HMCTaxPrice, 0.1);
		        Assert.assertEquals((ThankTaxPrice+PaymentSubPrice), ThankTotalPrice, 0.1);
	        }else{
	        	Assert.assertEquals((ShippingSubPrice+shippingPrice), ShippingTotalPrice, 0.1);
	        	Assert.assertEquals((ShippingSubPrice+shippingPrice+ThankTaxPrice), ThankTotalPrice, 0.1);
		        Assert.assertEquals(ShippingTotalPrice, HMCSubPrice, 0.1);
		        Assert.assertEquals(ThankTaxPrice, HMCTaxPrice, 0.1);
	        }
	        Dailylog.logInfoDB(i++,"Check  Order Email: "+OrderNumber,Store,testName);
	        checkConfirmationEmail("testus",OrderNumber,PaymentSubPrice,ThankTaxPrice,ThankTotalPrice,4);
	        
	        
	        
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			handleThrowable(e, ctx);
		}	
	}
	public void checkConfirmationEmail(String mailId, String orderNum, float SubTotalPrice, 
			float TaxPrice, float TotalPrice, int mailCount) {
		System.out.println("Checking email..." + mailId);
		EMailCommon.goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+orderNum+"')]"))
				&& mailCount != 1) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Common.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+orderNum+"')]")))
					break;
			}
		}
		System.out.println("Order Email: "
				+ Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+orderNum+"')]")));
		
		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
			driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).click();
			// Check order number
			Common.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).isDisplayed(),
					"Order in mail: " + orderNum);

			// Check EmailSubTotalPrice
			WebElement EmailSubTotalPrice = driver.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[1]/td"));
			Assert.assertEquals(GetPriceValue(EmailSubTotalPrice.getText()),SubTotalPrice,"SubTotalPrice price in mailis not right in mail");
			//System.out.println(GetPriceValue(EmailSubTotalPrice.getText())+"--"+SubTotalPrice+"...............");
			
			// Check Tax price
			WebElement EmailTaxPrice = driver.findElement(By.xpath("//td[contains(text(),'Tax')]/../../../../../td[3]//tbody/tr[2]/td"));
			Assert.assertEquals(GetPriceValue(EmailTaxPrice.getText()),TaxPrice,"TaxPrice price in mailis not right in mail");
			
			// Check total price
			WebElement EmailTotalPrice = driver.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[last()]/td"));
			Assert.assertEquals(GetPriceValue(EmailTotalPrice.getText()),TotalPrice,"TotalPrice is not right in mail");				
		}else {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: " + mailId);
			setManualValidateLog("SubTotalPrice: " + SubTotalPrice);
			setManualValidateLog("TaxPrice: " + TaxPrice);
			setManualValidateLog("TotalPrice: " + TotalPrice);	
		}
		
	}

	
	public void openOrderConfirmationEmail(String OrderNum,int mailCount){
		driver.findElement(By.xpath("//*[contains(text(),'ve received your Lenovo order')]")).click();
		Common.sleep(10000);
		if(!driver.findElement(By.xpath("//*[contains(text(),'" + OrderNum + "')]")).isDisplayed()){
			driver.findElement(By.id("back_to_inbox_link")).click();
			Common.sleep(5000);
		}	
	}
	

	
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name,
			String dataInput, WebElement ele1, String xpath)
			throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);
	}
	
	
	public float GetPriceValue(String Price) {
		if (Price.contains("FREE")||Price.contains("INCLUDED")){
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("SG", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		String pattern = "\\d+\\.*\\d*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Price);
    	float priceValue;
        if (m.find( )) {
    		priceValue = Float.parseFloat( m.group());
    		return priceValue;
        } else {
        	return 0;
        }
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {

			driver.get(driver.getCurrentUrl().replace(
					"j_spring_security_check", "login"));
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}
}
