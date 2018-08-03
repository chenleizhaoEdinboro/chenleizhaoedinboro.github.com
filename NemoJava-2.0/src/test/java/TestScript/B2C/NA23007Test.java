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

public class NA23007Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String Country;
	public MailPage mailPage;
	public String partNumber;
	public String partNumberBackup="81A5003AAU";
	public int i=1;
	
	public NA23007Test(String Store,String Country){
		this.Store = Store;
		this.Country = Country;
		this.testName = "NA-23007";
	}
	
	@Test(alwaysRun = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA23007(ITestContext ctx){
		try {
			this.prepareTest();
			mailPage =  new MailPage(driver);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			Actions actions = new Actions(driver);
			driver.get(testData.HMC.getHomePageUrl());
			String dataInput;
			String xpath;
			
			Dailylog.logInfoDB(i++,"Login HMC and Setting under basestore", Store,testName);
			HMCCommon.Login(hmcPage, testData);			
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			hmcPage.B2BCustomer_1stSearchedResult.click();
			hmcPage.baseStore_administration.click();
			
			if(hmcPage.basestore_NetValue.getAttribute("value").contains("true")){
				hmcPage.basestore_NetCheck.click();
			}
			hmcPage.basestore_GST.click();
			hmcPage.Types_SaveBtn.click();
			Common.sleep(5000);
			
			Dailylog.logInfoDB(i++,"Search Unit and Setting under Site arrtibute", Store,testName);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Thread.sleep(5000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(10000);
			hmcPage.B2CUnit_DisableGEOLink.click();
			hmcPage.B2CUnit_EnableTax.click();
			hmcPage.B2CUnit_TaxValue.clear();
			hmcPage.B2CUnit_TaxValue.sendKeys("10.00");
			hmcPage.Types_SaveBtn.click();
			
			Dailylog.logInfoDB(i++,"Login AU website and redirect to cart page", Store,testName);
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
			
			String CartSubLabel;
			String CartTotalLabel;
			float CartSubPrice;
			float CartTotalPrice;
								
			if(newCartFlag){
				if(Common.isElementExist(driver, By.xpath(".//*[@id='emptyCartItemsForm']/a/img"))){
					b2cPage.NewCart_DeleteButton.click();
					b2cPage.NewCart_ConfirmDelete.click();					
				}
				b2cPage.NewCart_PartNumberTextBox.clear();
				b2cPage.NewCart_PartNumberTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
				b2cPage.NewCart_Submit.click();
				
				CartSubLabel=b2cPage.Cart_PriceSubLabel.getText();
				CartTotalLabel=b2cPage.Cart_PriceTotalLabel.getText();
				CartSubPrice=GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				CartTotalPrice=GetPriceValue(b2cPage.NewCart_PriceTotal.getText());

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
				
				CartSubLabel = b2cPage.Cart_PriceSubLabel.getText();
				CartTotalLabel = b2cPage.Cart_PriceTotalLabel.getText();
				CartSubPrice=GetPriceValue(b2cPage.Cart_PriceSubTotal.getText());
				CartTotalPrice=GetPriceValue(b2cPage.Cart_PriceTotal.getText());
				
			}	
			Dailylog.logInfoDB(i++,"Cart Page-Cart_PriceSubLabel:"+CartSubLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Cart Page-CartTotalLabel:"+CartTotalLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Cart Page-CartSubPrice:"+CartSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Cart Page-CartTotalPrice:"+CartTotalPrice, Store,testName);
			
			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='close-lnv-call-center']"), 5)){
				driver.findElement(By.xpath("//div[@class='close-lnv-call-center']")).click();
			}
			
			b2cPage.Cart_CheckoutButton.click();
			
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			if(Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5)){
				b2cPage.Shipping_editAddress.click();
			}
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			String ShippingSubLabel;
			String ShippingTotalLabel;
			float ShippingSubPrice;
			float ShippingTotalPrice;
			Common.sleep(3000);
			
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping__TotalPrice, 5)){
				ShippingSubLabel = b2cPage.NewShipping_PriceSubLabel.getText();
				ShippingTotalLabel = b2cPage.NewShipping_PriceTotalLabel.getText();
				ShippingSubPrice = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				ShippingTotalPrice = GetPriceValue(b2cPage.NewShipping__TotalPrice.getText());
				
			}else{
				ShippingSubLabel = b2cPage.Shipping_PriceSubLabel.getText();
				ShippingTotalLabel = b2cPage.Shipping_PriceTotalLabel.getText();
				ShippingSubPrice = GetPriceValue(b2cPage.Shipping_PriceSubTotal.getText());
				ShippingTotalPrice = GetPriceValue(b2cPage.Shipping_PriceTotal.getText());
				
			}
			Dailylog.logInfoDB(i++,"Shipping Page-ShippingSubLabel:"+ShippingSubLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Shipping Page-ShippingTotalLabel:"+ShippingTotalLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Shipping Page-ShippingSubPrice:"+ShippingSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Shipping Page-ShippingTotalPrice:"+ShippingTotalPrice, Store,testName);
			
			actions.sendKeys(Keys.PAGE_UP).perform();
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
				b2cPage.ValidateInfo_SkipButton.click();
			}
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			
			String PaymentSubLabel;
			String PaymentTotalLabel;
			float PaymentSubPrice;
			float PaymentTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping__TotalPrice, 5)){
				PaymentSubLabel = b2cPage.NewShipping_PriceSubLabel.getText();
				PaymentTotalLabel = b2cPage.NewShipping_PriceTotalLabel.getText();
				PaymentSubPrice = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText());
				PaymentTotalPrice = GetPriceValue(b2cPage.NewShipping__TotalPrice.getText());
				
			}else{
				PaymentSubLabel = b2cPage.Shipping_PriceSubLabel.getText();
				PaymentTotalLabel = b2cPage.Shipping_PriceTotalLabel.getText();
				PaymentSubPrice = GetPriceValue(b2cPage.Shipping_PriceSubTotal.getText());
				PaymentTotalPrice = GetPriceValue(b2cPage.Shipping_PriceTotal.getText());
				
			}
			Dailylog.logInfoDB(i++,"Payment Page-PaymentSubLabel:"+PaymentSubLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Payment Page-PaymentTotalLabel:"+PaymentTotalLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Payment Page-PaymentSubPrice:"+PaymentSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Payment Page-PaymentTotalPrice:"+PaymentTotalPrice, Store,testName);
			
			b2cPage.Payment_ContinueButton.click();
			
			String ReviewSubLabel;
			String ReviewTotalLabel;
			float ReviewSubPrice;
			float ReviewTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.NewReview_PriceSubTotal, 5)){
				ReviewSubLabel = b2cPage.NewShipping_PriceSubLabel.getText();
				ReviewTotalLabel = b2cPage.NewReview_PriceTotalLabel.getText();
				ReviewSubPrice = GetPriceValue(b2cPage.NewReview_PriceSubTotal.getText());
				ReviewTotalPrice = GetPriceValue(b2cPage.NewReview_PriceTotal.getText());
				
			}else{
				ReviewSubLabel = b2cPage.Review_PriceSubLabel.getText();
				ReviewTotalLabel = b2cPage.Review_PriceTotalLabel.getText();
				ReviewSubPrice = GetPriceValue(b2cPage.Review_PriceSubTotal.getText());
				ReviewTotalPrice = GetPriceValue(b2cPage.Review_PriceTotal.getText());
				
			}
			
			Dailylog.logInfoDB(i++,"Review Page-ReviewSubLabel:"+ReviewSubLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Review Page-ReviewTotalLabel:"+ReviewTotalLabel, Store,testName);
			Dailylog.logInfoDB(i++,"Review Page-ReviewSubPrice:"+ReviewSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Review Page-ReviewTotalPrice:"+ReviewTotalPrice, Store,testName);
			
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);

			B2CCommon.clickPlaceOrder(b2cPage);
			
//			String ThankSubLabel = b2cPage.Thank_PriceSubLabel.getText();
//			String ThankTotalLabel = b2cPage.Thank_PriceTotalLabel.getText();
//			float ThankSubPrice = GetPriceValue(b2cPage.Thank_PriceSubTotal.getText());
			float ThankTotalPrice;
			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(i++,"Thank Page-Order Number:"+OrderNumber, Store,testName);
			if(Common.checkElementDisplays(driver, b2cPage.NewThankPage__TotalPrice, 5)){
				ThankTotalPrice = GetPriceValue(b2cPage.NewThankPage__TotalPrice.getText());
				
			}else{
				ThankTotalPrice = GetPriceValue(b2cPage.Thank_PriceTotal.getText());
				
			}
			Dailylog.logInfoDB(i++,"Thank Page-ThankTotalPrice:"+ThankTotalPrice, Store,testName);
					
			Dailylog.logInfoDB(i++,"Cart Label and Price"+CartSubLabel+CartSubPrice+CartTotalLabel+CartTotalPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Shipping Label and Price"+ShippingSubLabel+ShippingSubPrice+ShippingTotalLabel+ShippingTotalPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Payment Label and Price"+PaymentSubLabel+PaymentSubPrice+PaymentTotalLabel+PaymentTotalPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Review Label and Price"+ReviewSubLabel+ReviewSubPrice+ReviewTotalLabel+ReviewTotalPrice,Store,testName);
//			Dailylog.logInfoDB(13,"Thankyou Label and Price"+ThankSubLabel+ThankSubPrice+ThankTotalLabel+ThankTotalPrice,Store,testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(0);
			hmcPage.PricingCockpit_B2CPriceSimulator.click();
			Thread.sleep(5000);

			// Country
			dataInput = "[" + testData.Store.substring(0, 2).toUpperCase() + "] "
					+ Country;
			xpath = "//span[text()='" + dataInput
					+ "' and @class='select2-match']/../../*[not(text())]";
			fillRuleInfo(driver, hmcPage, "Country", dataInput,
					hmcPage.B2CPriceSimulator_country, xpath);

			// B2C store
			dataInput = "[" + testData.B2C.getStore() + "]";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Catalog", dataInput,
					hmcPage.B2CPriceSimulator_store, xpath);

			// Catalog
			dataInput = "Nemo Master Multi Country Product Catalog - Online";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Catalog", dataInput,
					hmcPage.B2CPriceSimulator_catalogVersion, xpath);

			// Date
			hmcPage.B2CPriceSimulator_priceDate.sendKeys(Keys.ENTER);

			// Product
			dataInput = testData.B2C.getDefaultMTMPN();
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Product", dataInput,
					hmcPage.B2CPriceSimulator_product, xpath);

			// Debug
			hmcPage.B2CPriceSimulator_debug.click();
			Common.sleep(8000);
			DecimalFormat df = new DecimalFormat("#.00");
			float TaxPrice = GetPriceValue(hmcPage.B2CpriceSimulate_TaxPrice.getText());
			float ListPrice = GetPriceValue(hmcPage.B2CpriceSimulate_ListPrice.getText());
			float listAndGstPrice =GetPriceValue(hmcPage.B2CpriceSimulate_ListAndGstPrice.getText());
			float HMCSimulatorPrice = GetPriceValue(hmcPage.B2CpriceSimulate_TotalPrice.getText());
			Dailylog.logInfoDB(i++,"Price Simulator-TaxPrice Price:"+TaxPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Price Simulator-ListPrice Price:"+ListPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Price Simulator-listAndGstPrice Price:"+listAndGstPrice,Store,testName);
			Dailylog.logInfoDB(i++,"Price Simulator-Simulator Price:"+HMCSimulatorPrice,Store,testName);
			Assert.assertTrue(checkPriceEqual(TaxPrice+ListPrice,listAndGstPrice), "TaxPrice plus ListPrice not equal listAndGstPrice ");
			Assert.assertTrue(checkPriceEqual((float)(0.1*ListPrice),TaxPrice), "TaxPrice is not List double 0.1 ");
					
			driver.switchTo().defaultContent();
			driver.navigate().refresh();
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
	        Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"), "Order Status is Failed"); 
	        hmcPage.Home_Order_OrderDetail.click();
	        //hmcPage.OrderReload.click();
	        Thread.sleep(5000);
	        Dailylog.logInfoDB(i++,"Order Status is Completed: "+OrderNumber,Store,testName);
	        
	        float HMCPrice = GetPriceValue(hmcPage.Home_Order_ProductPrice.getAttribute("value"));
	        System.out.println(HMCPrice);
	        hmcPage.Home_Order_OrderAdmin.click();
	        Thread.sleep(5000);
	        
	        System.out.println(df.format(HMCPrice/1.1));
	        Assert.assertTrue(hmcPage.Orders_OrderXML.getText().contains("<condition_rate>"+df.format(HMCPrice/1.1)+"</condition_rate>"),"Order xml confition price is not right");
	        
	        Assert.assertTrue(CartSubLabel.contains("inc. GST"),"CartSubLabel did not contians GST");
	        Assert.assertTrue(ShippingSubLabel.contains("inc. GST"),"ShippingSubLabel did not contians GST");
	        Assert.assertTrue(PaymentSubLabel.contains("inc. GST"),"PaymentSubLabel did not contians GST");
	        Assert.assertTrue(ReviewSubLabel.contains("inc. GST"),"ReviewSubLabel did not contians GST");
//	        Assert.assertTrue(ThankSubLabel.contains("inc. GST"),"ThankSubLabel did not contians GST");
	        Assert.assertTrue(CartTotalLabel.contains("inc. GST"),"CartTotalLabel did not contians GST");
	        Assert.assertTrue(ShippingTotalLabel.contains("inc. GST"),"ShippingTotalLabel did not contians GST");
	        Assert.assertTrue(PaymentTotalLabel.contains("inc. GST"),"PaymentTotalLabel did not contians GST");
	        Assert.assertTrue(ReviewTotalLabel.contains("inc. GST"),"ReviewTotalLabel did not contians GST");
//	        Assert.assertTrue(ThankTotalLabel.contains("inc. GST"),"ThankTotalLabel did not contians GST");
	        
	        
	        Assert.assertTrue(CartSubPrice==ShippingSubPrice&&PaymentSubPrice==ReviewSubPrice&&PaymentSubPrice==CartSubPrice,"SubTotalPrice isnot same");
	        Assert.assertTrue(CartTotalPrice==ShippingTotalPrice&&PaymentTotalPrice==ReviewTotalPrice&&ThankTotalPrice==PaymentTotalPrice&&PaymentTotalPrice==CartTotalPrice&&CartTotalPrice==HMCPrice,"Total price is not same");
	        Assert.assertTrue(CartTotalPrice==HMCSimulatorPrice||CartSubPrice==HMCSimulatorPrice);
	        
	        checkConfirmationEmail("testau",OrderNumber,"inc GST",CartSubPrice,CartTotalPrice,4);
	        
	        
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			handleThrowable(e, ctx);
		}	
	}
	
	
	public boolean checkPriceEqual(float priceone,float pricetwo){
		return Math.abs(priceone-pricetwo)<0.1;
	}
	public void checkConfirmationEmail(String mailId, String orderNum, String TotalLabel,
			float SubTotalPrice, float TotalPrice, int mailCount) throws InterruptedException {
		System.out.println("Checking email..." + mailId);
		EMailCommon.goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+orderNum+"')]"))
				&& mailCount != 1) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Thread.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+orderNum+"')]")))
					break;
			}
		}
		System.out.println("Order Email: "
				+ Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+orderNum+"')]")));

		
		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
			driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).click();
			Thread.sleep(10000);
			// Check order number
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).isDisplayed(),
					"Order in mail: " + orderNum);

			WebElement EmailSubTotalPrice = driver.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[1]/td"));
			Assert.assertTrue(checkPriceEqual(GetPriceValue(EmailSubTotalPrice.getText()),SubTotalPrice),"SubTotalPrice price in mailis not right in mail");
			
			WebElement EmailTotalLabel = driver.findElement(By.xpath("//td[contains(text(),'Total')]"));
			Assert.assertTrue(EmailTotalLabel.getText().contains(TotalLabel), "TotalLabel is not right in maill");

			WebElement EmailTotalPrice = driver.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[last()]/td"));
			Assert.assertTrue(checkPriceEqual(GetPriceValue(EmailTotalPrice.getText()),TotalPrice),"TotalPrice is not right in mail");
			//System.out.println("...............");
			
		} else {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: " + mailId);
			setManualValidateLog("SubTotalPrice: " + SubTotalPrice);
			setManualValidateLog("TotalLabel: " + TotalLabel);
			setManualValidateLog("TotalPrice: " + TotalPrice);
			
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
