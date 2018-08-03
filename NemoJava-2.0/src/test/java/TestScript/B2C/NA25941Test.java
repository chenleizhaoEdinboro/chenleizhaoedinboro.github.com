package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.List;
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
import CommonFunction.DesignHandler.*;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA25941Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String partNumber;
	public String partNumberBackup="80X7008HUS";
	public int j=1;
	
	public NA25941Test(String Store){
		this.Store = Store;
		this.testName = "NA-25941";
	}
	
	@Test(alwaysRun = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA25491(ITestContext ctx){
		try {
			this.prepareTest();
			mailPage =  new MailPage(driver);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			Dailylog.logInfoDB(j++,"HMC Setting Under Unit Level", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			Common.sleep(10000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_EnableSabrixEngine.click();
			hmcPage.Types_SaveBtn.click();
			Common.sleep(10000);
			
			Dailylog.logInfoDB(j++,"GO to CA Web site", Store,testName);
			driver.get(testData.B2C.getHomePageUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			System.out.println(partNumber);
			HandleJSpring(driver);
			Common.sleep(5000);
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			if(!Common.checkElementExists(driver, b2cPage.Add2Cart, 5)){
				partNumber=partNumberBackup;
			}else{
				partNumber=testData.B2C.getDefaultMTMPN();
			}
			
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			if (Common.isElementExist(driver,
					By.xpath("//a[contains(text(),'Empty cart')]"))) {
				driver.findElement(
						By.xpath("//a[contains(text(),'Empty cart')]")).click();
			}else if(Common.checkElementExists(driver, b2cPage.NewCart_DeleteButton, 5)){
				b2cPage.NewCart_DeleteButton.click();
				b2cPage.NewCart_ConfirmDelete.click();
			}
			CommonFunction.DesignHandler.CartCheckOut.CheckOutUIHandler(b2cPage, "quickadd", partNumber);
			
			float CartSubPrice;
			float CartTotalPrice;
			float CartPromotion=0;
			boolean cartPromotion=false;
			
			if(Common.isElementExist(driver, By.xpath("//button[contains(@class,'Submit_Button')]"))){
				
				CartSubPrice=GetPriceValue(b2cPage.NewCart_SubTotalPrice.getText());
				CartTotalPrice= GetPriceValue(b2cPage.NewCart_TotalPrice.getText());
				
				if(Common.checkElementDisplays(driver, b2cPage.NewCart_CartPromotionPrice, 5)){
					CartPromotion=GetPriceValue(b2cPage.NewCart_CartPromotionPrice.getText());
					cartPromotion=true;
				}else{
					CartPromotion=0;
				}
				
			}else{
				CartSubPrice = GetPriceValue(b2cPage.Cart_PriceSubTotal.getText());
				CartTotalPrice = GetPriceValue(b2cPage.Cart_PriceTotal.getText());
				if(Common.checkElementDisplays(driver, b2cPage.Cart_CartPromotion, 5)){
					CartPromotion=GetPriceValue(b2cPage.Cart_CartPromotion.getText());
					cartPromotion=true;
				}else{
					CartPromotion=0;
				}
			}
			
			Dailylog.logInfoDB(j++,"Cart Page-SubPrice:"+CartSubPrice, Store,testName);
			Dailylog.logInfoDB(j++,"Cart Page-CartPromotion:"+CartPromotion, Store,testName);
			Dailylog.logInfoDB(j++,"Cart Page-TotalPrice:"+CartTotalPrice, Store,testName);
			Assert.assertEquals(CartSubPrice-CartPromotion, CartTotalPrice, 0.01);
			
			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='close-lnv-call-center']"), 5)){
				driver.findElement(By.xpath("//div[@class='close-lnv-call-center']")).click();
			}
			
			b2cPage.Cart_CheckoutButton.click();
			Dailylog.logInfoDB(j++,"Go to Shipping page",Store,testName);
			
			if(Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5)){
				b2cPage.Shipping_editAddress.click();
			}
			B2CCommon.fillShippingInfo(b2cPage,"Auto","Test","2889 EAST HASTINGS STREET ",
					"VANCOUVER","British Columbia","V5K 2A1","1234567890",testData.B2C.getLoginID());
			
			float ShippingSubPrice;
			float ShippingTotalPrice;
			float shippingPrice ;
			
			if(Common.checkElementDisplays(driver, By.xpath("//div/h1[text()='Shipping & Payment']"), 5)){
				ShippingSubPrice=GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				ShippingTotalPrice=GetPriceValue(b2cPage.NewShipping__TotalPrice.getText());
				
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.NewCart_CartPromotionPrice.getText());
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
			
			Dailylog.logInfoDB(j++,"Shipping page-ShippingSubPrice:"+ShippingSubPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Shipping page-shippingPrice:"+shippingPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Shipping page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(j++,"Shipping page-ShippingTotalPrice:"+ShippingTotalPrice,Store,testName);
			Assert.assertEquals(ShippingSubPrice+shippingPrice-CartPromotion, ShippingTotalPrice, 0.01);
			
			JavascriptExecutor js= (JavascriptExecutor) driver;
			Common.sleep(3000);			
		
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);			

			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddressItem, 3)){
				js.executeScript("arguments[0].click();", b2cPage.Shipping_validateAddressItem);	
			}
				
			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddress, 3)){
				js.executeScript("arguments[0].click();", b2cPage.Shipping_validateAddress);	
			}
			Thread.sleep(3000);
			Dailylog.logInfoDB(j++,"Go to Payment page",Store,testName);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			
			
			float PaymentTotalPrice ;
			float PaymentSubPrice ;
			float Payment_PSTPrice;
			float Payment_GSTPrice;
			float Payment_FEEPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping__TotalPrice, 5)){
				PaymentSubPrice=GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				Payment_PSTPrice=GetPriceValue(b2cPage.NewPayment_PSTPrice.getText());
				Payment_GSTPrice=GetPriceValue(b2cPage.NewPayment_GSTPrice.getText());
				Payment_FEEPrice=GetPriceValue(b2cPage.NewPayment_FEEPrice.getText());			
				PaymentTotalPrice=GetPriceValue(b2cPage.NewShipping__TotalPrice.getText());			
						
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
				Payment_PSTPrice=GetPriceValue(b2cPage.Payment_CostPrice.get(2).getText());
				Payment_GSTPrice=GetPriceValue(b2cPage.Payment_CostPrice.get(3).getText());
				Payment_FEEPrice=GetPriceValue(b2cPage.Payment_CostPrice.get(1).getText());	
				
				PaymentTotalPrice = GetPriceValue(b2cPage.Shipping_PriceTotal.getText());
				
				if(b2cPage.Shipping_PriceShipping.getText().contains("FREE")){
					shippingPrice=0;
				}else{
					shippingPrice = GetPriceValue(b2cPage.Payment_CostPrice.get(0).getText());
				}
				
				if(cartPromotion){
					CartPromotion=GetPriceValue(b2cPage.Cart_CartPromotion.getText());
				}else{
					CartPromotion=0;
				}
				
			}
			
			Dailylog.logInfoDB(j++,"Paymnet page-PaymentSubPrice:"+PaymentSubPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Paymnet page-Payment_PSTPrice:"+Payment_PSTPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Paymnet page-Payment_GSTPrice:"+Payment_GSTPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Paymnet page-Payment_FEEPrice:"+Payment_FEEPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Paymnet page-shippingPrice:"+shippingPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Paymnet page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(j++,"Paymnet page-PaymentTotalPrice:"+PaymentTotalPrice,Store,testName);
			Assert.assertEquals(PaymentSubPrice+shippingPrice+Payment_PSTPrice+Payment_GSTPrice+Payment_FEEPrice-CartPromotion, PaymentTotalPrice, 0.01);
			
			js.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(j++,"Go to Review page",Store,testName);
			
			float ReviewTotalPrice ;
			float ReviewSubPrice ;
			float Review_PSTPrice;
			float Review_GSTPrice;
			float Review_FEEPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewReview_PriceTotal, 5)){
				ReviewSubPrice=GetPriceValue(b2cPage.NewSummary__ItemPrice.get(0).getText());
				Review_PSTPrice=GetPriceValue(b2cPage.NewReview_PSTPrice.getText());
				Review_GSTPrice=GetPriceValue(b2cPage.NewReview_GSTPrice.getText());
				Review_FEEPrice=GetPriceValue(b2cPage.NewReview_FEEPrice.getText());
				ReviewTotalPrice=GetPriceValue(b2cPage.NewReview_PriceTotal.getText());

				
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
				ReviewSubPrice = GetPriceValue(b2cPage.Review_PriceSubTotal.getText());
				Review_PSTPrice=GetPriceValue(b2cPage.Review_PSTPrice.getText());
				Review_GSTPrice=GetPriceValue(b2cPage.Review_GSTPrice.getText());
				Review_FEEPrice=GetPriceValue(b2cPage.Review_FEEPrice.getText());
				
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
			
			Dailylog.logInfoDB(j++,"Review page-ReviewSubPrice:"+ReviewSubPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Review page-Review_PSTPrice:"+Review_PSTPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Review page-Review_GSTPrice:"+Review_GSTPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Review page-Review_FEEPrice:"+Review_FEEPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Review page-shippingPrice:"+shippingPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Review page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(j++,"Review page-ReviewTotalPrice:"+ReviewTotalPrice,Store,testName);
			Assert.assertEquals(PaymentSubPrice+shippingPrice+Payment_PSTPrice+Payment_GSTPrice+Payment_FEEPrice-CartPromotion, PaymentTotalPrice, 0.01);
			
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(j++,"Go to Thank you page",Store,testName);
			
			float ThankFEEPrice;
			float ThankPSTPrice;
			float ThankGSTPrice;
			float ThankTotalPrice;
			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);		
			Dailylog.logInfoDB(j++,"Order number is "+OrderNumber,Store,testName);
			
			if(Common.checkElementDisplays(driver, b2cPage.NewThankPage__TotalPrice, 5)){
				
				ThankFEEPrice=GetPriceValue(b2cPage.NewPayment_FEEPrice.getText());
				ThankPSTPrice=GetPriceValue(b2cPage.NewPayment_PSTPrice.getText());
				ThankGSTPrice=GetPriceValue(b2cPage.NewPayment_GSTPrice.getText());
				ThankTotalPrice=GetPriceValue(b2cPage.NewThankPage__TotalPrice.getText());				

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
				ThankFEEPrice=GetPriceValue(b2cPage.Thank_FEEPrice.getText());
				ThankPSTPrice=GetPriceValue(b2cPage.Thank_PSTPrice.getText());
				ThankGSTPrice=GetPriceValue(b2cPage.Thank_GSTPrice.getText());
				
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
			
			
			Dailylog.logInfoDB(j++,"Thankyou page-ThankFEEPrice:"+ThankFEEPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Thankyou page-ThankPSTPrice:"+ThankPSTPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Thankyou page-ThankGSTPrice:"+ThankGSTPrice,Store,testName);
			Dailylog.logInfoDB(j++,"Thankyou page-shippingPrice:"+shippingPrice,Store,testName);
			//Dailylog.logInfoDB(i++,"Thankyou page-CartPromotion:"+CartPromotion,Store,testName);
			Dailylog.logInfoDB(j++,"Thankyou page-ThankTotalPrice:"+ThankTotalPrice,Store,testName);
						
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
	        if(!hmcPage.Home_Order_OrderStatus.getText().contains("Completed")){
	            Thread.sleep(5000);
	            hmcPage.Home_Order_OrderSearch.click();
	        }
	        Dailylog.logInfoDB(j++,"Order Status is : "+hmcPage.Home_Order_OrderStatus.getText(),Store,testName);
	        Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"), "Order Status is Failed"); 
	        hmcPage.Home_Order_OrderDetail.click();
	        //hmcPage.OrderReload.click();
	        Thread.sleep(5000);
	        
	        float HMCSubPrice =GetPriceValue(hmcPage.Home_Order_ProductPrice.getAttribute("value"));
	        float HMCTaxPrice =GetPriceValue(hmcPage.Home_Order_TaxPrice.getAttribute("value"));
	        Dailylog.logInfoDB(j++,"HMC Orer Detail Page-SubPrice: "+HMCSubPrice,Store,testName);
	        Dailylog.logInfoDB(j++,"HMC Orer Detail Page-TaxPrice: "+HMCTaxPrice,Store,testName);
			
	        Assert.assertEquals(HMCSubPrice, ReviewSubPrice, 0.1);
	        Assert.assertEquals(HMCTaxPrice,ThankFEEPrice+ThankPSTPrice+ThankGSTPrice, 0.1);
	        
	        checkConfirmationEmail("testca",OrderNumber,ReviewSubPrice,ThankTotalPrice,4);
	        
	        
	        
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			handleThrowable(e, ctx);
		}	
	}
	public void checkConfirmationEmail(String mailId, String orderNum, float SubTotalPrice, 
			 float TotalPrice, int mailCount) {
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
			
			// Check Email total price
			WebElement EmailTotalPrice = driver.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[last()]/td"));
			Assert.assertEquals(GetPriceValue(EmailTotalPrice.getText()),TotalPrice,"TotalPrice is not right in mail");
			
			List<WebElement> ele =  driver.findElements(By.xpath("//tr/td[contains(text(),'FEE ')]/../../tr"));
			String[] OrderEmaillabels=new String[ele.size()];
			int i=0;
			boolean flagFEE=false;
			for(WebElement e : ele){				
				OrderEmaillabels[i]=e.getText();
				if(OrderEmaillabels[i].contains("FEE")){
					flagFEE=true;
				}
				i++;
			}
			Assert.assertTrue(flagFEE, "FEE not display");
			
			List<WebElement> ele1 =  driver.findElements(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody//td"));
			float[] OrderEmailPrices=new float[ele.size()];
			i=0;
			for(WebElement e : ele1){					
				OrderEmailPrices[i]=GetPriceValue(e.getText());
				i++;
			}
			
			for(i=0;i<OrderEmaillabels.length;i++){
				if(OrderEmailPrices[i]!=0){
					Dailylog.logInfoDB(j++,"Email Order page-"+OrderEmaillabels[i]+":"+OrderEmailPrices[i], Store,testName);
				}					
			}
			
			
		}else {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: " + mailId);	
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
	

	
	

