package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.annotations.AfterTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.MailPage;


public class NA18085Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public MailPage mailPage;
	public String strCartSubPrice;
	public String strCartTotalPrice;
	public String strTaxPrice;
	public String partNumber;
	public String orderNumber;
	public String partNumberBackup="ZA0W0179JP";
	public float CartTaxPrice;
	public float CartSubPrice;
	public float CartTotalPrice;
	public int i=1;
	DecimalFormat df = new DecimalFormat("#.00");
	public NA18085Test(String store){
		this.Store = store;
		this.testName = "NA-18085";
	}
	
	@Test(alwaysRun = true, priority = 0, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void NA18085(ITestContext ctx){
		try{
			this.prepareTest();
			
			mailPage =  new MailPage(driver);
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//----------------Step 1: Login HMC and  setting on unit level-----------//		
			Dailylog.logInfoDB(i++,"Login HMC and Setting on B2C Unit.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
			
			System.out.println("Navigate to B2C Commerce ->B2C UNIT");
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(5000);
			System.out.println("Setting Tax Toggle->Yes ");
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(5000);
			
			driver.findElement(By.xpath(".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.useSabrixEngine') and contains(@id,'false')]")).click();
			By by=By.xpath("//div[contains(text(),'Delivery Modes')]/../following-sibling::td[3]/table[2]/tbody//tbody//div[contains(@id,'standard-net')]"); 
			if(!Common.isElementExist(driver, by, 1)) {
				
				Common.rightClick(driver, driver.findElement(By
						.xpath("//div[contains(text(),'Delivery Modes')]/..//following-sibling::td[3]")));
				driver.findElement(By.xpath("(//td[text()='Add Delivery Mode'])[1]")).click();
				Common.switchToWindow(driver, 1);
				driver.findElement(By.xpath("//input[@class='enabled']")).sendKeys("standard-net");
				driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
				driver.findElement(By.xpath("(//div[@class='gislcResultList']/table/tbody//td)[3]")).click();
				driver.findElement(By.xpath("//span[contains(text(),'Use')]")).click();
				Common.switchToWindow(driver, 0);
				Dailylog.logInfoDB(i++,"Add the stand-net delovery modes to jpweb unit.", Store,testName);
			}else {
				
				Dailylog.logInfoDB(i++,"Stand-net Delivery Modes aleady added", Store,testName);
			}
			
			hmcPage.B2CUnit_EnableTax.click();	
			
			System.out.println("Maintain tax rate is 8 percent ");
			hmcPage.B2CUnit_TaxValue.clear();
			hmcPage.B2CUnit_TaxValue.sendKeys("8.00");
			
			System.out.println("Sabrix toggle->NO ");
			
            driver.findElement(By.xpath(".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.useSabrixEngine') and contains(@id,'false')]")).click();
            hmcPage.Types_SaveBtn.click();            
            //----------------Step 2: Setting on base store level-----------------//
            Dailylog.logInfoDB(i++,"Setting on B2C Base Store Level.", Store,testName);
			System.out.println("Navigate to Base Commerce -> Base Store");
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			Common.doubleClick(
					driver,
					driver.findElement(By.xpath(".//div[contains(@id,'"
							+ testData.B2C.getStore() + "]')]")));
					
			System.out.println("Go to Administration tab:Net check-box is check");
			hmcPage.baseStore_administration.click();	
			if(hmcPage.basestore_NetValue.getAttribute("value").contains("false")){
				hmcPage.basestore_NetCheck.click();
			}
			
			System.out.println("Make Sure NO Tax is selected");
			hmcPage.basestore_NoTax.click();
			hmcPage.Types_SaveBtn.click();
			Common.sleep(5000);
			
			
		    //----------------Step 3: Go to JP PDP website-----------------//
            Dailylog.logInfoDB(i++,"Login JP website.", Store,testName);			
            driver.get(testData.B2C.getHomePageUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			HandleJSpring(driver);
			Common.sleep(5000);
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			if(!Common.checkElementExists(driver, b2cPage.Add2Cart, 5)){
				partNumber=partNumberBackup;
				driver.get(testData.B2C.getHomePageUrl()+"/p/"+partNumber);
			}else{
				partNumber=testData.B2C.getDefaultMTMPN();
			}
			
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//*[contains(@class,'tax')]")));
			
			//----------------Step 4: Go to JP Cart Page-----------------//
			Dailylog.logInfoDB(i++,"Login JP Cart Page and check tax.", Store,testName);	
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			boolean newCartFlag=false;
			if (Common.isElementExist(driver, By.xpath("//button[contains(.,'追加')]"))){
				newCartFlag=true;
			}
			System.out.println("newCartFlag is "+newCartFlag);
								
			if(newCartFlag){
				if(Common.isElementExist(driver, By.xpath(".//*[@id='emptyCartItemsForm']/a/img"))){
					b2cPage.NewCart_DeleteButton.click();
					b2cPage.NewCart_ConfirmDelete.click();					
				}
				b2cPage.NewCart_PartNumberTextBox.clear();
				b2cPage.NewCart_PartNumberTextBox.sendKeys(partNumber);
				b2cPage.NewCart_Submit.click();
				//check part number if is not valid
				
				//Get SubTota Price
				strCartSubPrice=b2cPage.NewCart_SubTotalPrice.getText();
				//Get total Price
				strCartTotalPrice=b2cPage.NewCart_TotalPrice.getText();
				//Get Tax Price
				strTaxPrice=b2cPage.NewCart_TaxPrice.getText();
			}else{
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
					driver.findElement(
							By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
				}
				B2CCommon.addPartNumberToCart(b2cPage, partNumber); 
				strCartSubPrice = b2cPage.Cart_PriceSubTotal.getText();
			    strCartTotalPrice = b2cPage.Cart_PriceTotal.getText();
			    strTaxPrice = b2cPage.Cart_TaxPrice.getText();
			}
			
		    CartSubPrice = getPriceValue(strCartSubPrice);			
			CartTotalPrice = getPriceValue(strCartTotalPrice);
			CartTaxPrice=getPriceValue(strTaxPrice);
		
			Dailylog.logInfoDB(i++,"Cart Page-CartSubPrice:"+CartSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Cart Page-CartTaxPrice:"+CartTaxPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Cart Page-CartTotalPrice:"+CartTotalPrice, Store,testName);
			
			Assert.assertEquals(CartSubPrice*0.08, CartTaxPrice, 1.0);
			Assert.assertEquals(CartSubPrice+CartTaxPrice, CartTotalPrice, 1.0);
			
			b2cPage.Cart_CheckoutButton.click();	
			
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			
			//----------------Step 5: Go to JP Shipping Page-----------------//
			Dailylog.logInfoDB(i++,"Login JP Shipping Page and check tax.", Store,testName);	
			System.out.println("Go to  Shiping adress page");
			String shippingSubPrice;
			String shippingTaxPrice;
			String shippingTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.Shipping_TaxPrice, 120)){
				shippingSubPrice=b2cPage.Shipping_PriceSubTotal.getText();
				shippingTaxPrice=b2cPage.Shipping_TaxPrice.getText();
				shippingTotalPrice= b2cPage.Shipping_PriceTotal.getText();				
			}else{
				shippingSubPrice=b2cPage.cartInfo_subTotalAftAnnProd.getText();
				shippingTaxPrice=b2cPage.NewShipping_TaxPrice.getText();
				shippingTotalPrice= b2cPage.NewShipping__TotalPrice.getText();
			}
					
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			b2cPage.Shipping_ContinueButton.click();
			
			Dailylog.logInfoDB(i++,"Shipping Page-shippingSubPrice:"+shippingSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Shipping Page-shippingTaxPrice:"+shippingTaxPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Shipping Page-shippingTotalPrice:"+shippingTotalPrice, Store,testName);
			
			Assert.assertEquals(getPriceValue(shippingSubPrice), CartSubPrice, 0);
			Assert.assertEquals(getPriceValue(shippingTaxPrice), CartTaxPrice, 0);
			Assert.assertEquals(getPriceValue(shippingTotalPrice), CartTotalPrice, 0);			
			
			//----------------Step 6: Go to JP payment Page-----------------//
			Dailylog.logInfoDB(i++,"Login JP payment Page and check tax.", Store,testName);	
			System.out.println("Go to Payment page");
			
			String paymentSubPrice;
			String paymentTaxPrice;
			String paymentTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.Shipping_TaxPrice, 5)){
				paymentSubPrice=b2cPage.Shipping_PriceSubTotal.getText();
			    paymentTaxPrice=b2cPage.Shipping_TaxPrice.getText();
			    paymentTotalPrice= b2cPage.Shipping_PriceTotal.getText();			
			}else{
				paymentSubPrice=b2cPage.cartInfo_subTotalAftAnnProd.getText();
				paymentTaxPrice=b2cPage.NewShipping_TaxPrice.getText();
				paymentTotalPrice= b2cPage.NewShipping__TotalPrice.getText();
			}
			Dailylog.logInfoDB(i++,"Payment Page-paymentSubPrice:"+paymentSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Payment Page-paymentTaxPrice:"+paymentTaxPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Payment Page-paymentTotalPrice:"+paymentTotalPrice, Store,testName);
			
			Assert.assertEquals(getPriceValue(paymentSubPrice), CartSubPrice, 0);
			Assert.assertEquals(getPriceValue(paymentTaxPrice), CartTaxPrice, 0);
			Assert.assertEquals(getPriceValue(paymentTotalPrice), CartTotalPrice, 0);		
			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			b2cPage.Payment_ContinueButton.click();
			
			//----------------Step 7: Go to JP Review Page-----------------//
			Dailylog.logInfoDB(i++,"Login JP Review Page and check tax.", Store,testName);	
			System.out.println("Go to Review page");
			
			String reviewSubPrice;
			String reviewTaxPrice;
			String reviewTotalPrice;
			
			if(Common.checkElementDisplays(driver, b2cPage.Review_TaxPrice, 5)){
				reviewSubPrice=b2cPage.Review_SubTotalPrice.getText();
				reviewTaxPrice=b2cPage.Review_TaxPrice.getText();
				reviewTotalPrice=b2cPage.Review_TotalPrice.getText();
			}else{
				reviewSubPrice=b2cPage.NewSummary__ItemPrice.get(0).getText();
				reviewTaxPrice=b2cPage.NewSummary__ItemPrice.get(1).getText();
				reviewTotalPrice=b2cPage.NewThankPage__TotalPrice.getText();
			}
			Dailylog.logInfoDB(i++,"Review Page-reviewSubPrice:"+reviewSubPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Review Page-reviewTaxPrice:"+reviewTaxPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Review Page-reviewTotalPrice:"+reviewTotalPrice, Store,testName);
			
			Assert.assertEquals(getPriceValue(reviewSubPrice), CartSubPrice, 0);
			Assert.assertEquals(getPriceValue(reviewTaxPrice), CartTaxPrice, 0);
			Assert.assertEquals(getPriceValue(reviewTotalPrice), CartTotalPrice, 0);
		    
		    //b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			
			B2CCommon.clickPlaceOrder(b2cPage);
			Common.sleep(3000);
			
			//----------------Step 8: Go to JP Thank you Page-----------------//
			Dailylog.logInfoDB(i++,"Login JP Thank you Page.", Store,testName);
			String OrderPageTaxPrice;
			String OrderPageTotalPrice;
			
			/*if(Common.checkElementDisplays(driver, b2cPage.OrderPage_TaxTotalPrice.get(0), 5)){
				OrderPageTaxPrice=b2cPage.OrderPage_TaxTotalPrice.get(0).getText();
				OrderPageTotalPrice=b2cPage.OrderPage_TaxTotalPrice.get(1).getText();
			}else{
				OrderPageTaxPrice=b2cPage.NewThankPage__TotalTax.getText();
				OrderPageTotalPrice=b2cPage.NewThankPage__TotalPrice.getText();
			}*/
			OrderPageTaxPrice=b2cPage.NewThankPage__TotalTax.getText();
			OrderPageTotalPrice=b2cPage.NewThankPage__TotalPrice.getText();
			float OrderTaxPrice = getPriceValue(OrderPageTaxPrice);
			float OrderTotalPrice=getPriceValue(OrderPageTotalPrice);
			
			Dailylog.logInfoDB(i++,"Thank you Page-OrderTaxPrice:"+OrderTaxPrice, Store,testName);
			Dailylog.logInfoDB(i++,"Thank you Page-OrderTotalPrice:"+OrderTotalPrice, Store,testName);
			
			
			
			Assert.assertEquals(OrderTaxPrice, CartTaxPrice, 0);
			Assert.assertEquals(OrderTotalPrice, CartTotalPrice, 0);
			
			orderNumber =B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(i++,"orderNumber:"+orderNumber, Store,testName);
			checkOrderTax(hmcPage,orderNumber,CartTaxPrice,CartSubPrice);
				
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}	
	}
	
	@Test(alwaysRun = true, priority = 1, groups = { "shopgroup", "pricingpromot", "p2", "b2c" })
	public void rollBack(ITestContext ctx) throws InterruptedException{
		Dailylog.logInfo("Roll back---------");
		try{
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
	     
			System.out.println("Navigate to Base Commerce -> Base Store");
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			Common.doubleClick(
					driver,
					driver.findElement(By.xpath(".//div[contains(@id,'"
							+ testData.B2C.getStore() + "]')]")));
					
			System.out.println("Go to Administration tab:Net check-box is uncheck");
			hmcPage.baseStore_administration.click();	
			if(hmcPage.basestore_NetValue.getAttribute("value").contains("true")){
				hmcPage.basestore_NetCheck.click();
			}
			
			System.out.println("Make Sure GST is selected");
			hmcPage.basestore_GST.click();
			hmcPage.Types_SaveBtn.click();
			Common.sleep(5000);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void checkOrderTax(HMCPage hmcPage,String orderNumber,float CartTaxPrice,float CartSubPrice ) {
		// TODO Auto-generated method stub
		driver.manage().deleteAllCookies();
		Common.NavigateToUrl(driver,Browser,testData.HMC.getHomePageUrl());
		if(Common.isElementExist(driver, By.id("Main_user"))){		
			HMCCommon.Login(hmcPage, testData);
		}
		try {
			HMCCommon.HMCOrderCheck(hmcPage, orderNumber);
			hmcPage.Home_Order_OrderDetail.click();
			String TaxVaule=hmcPage.Home_Order_TaxValue.getText();
			Dailylog.logInfoDB(i++,"Check Tax in HMC", Store,testName);
			System.out.println(TaxVaule);
			Assert.assertTrue(TaxVaule.contains("8.0%"), "Tax in HMC is not correct");
			
			String strHMCTaxPrice=hmcPage.Home_Order_TaxPrice.getAttribute("value");
			String strHMCProductPrice=hmcPage.Home_Order_ProductPrice.getAttribute("value");
			float HMCTaxPrice=getPriceValue(strHMCTaxPrice);
			float HMCProductPrice=getPriceValue(strHMCProductPrice);
			
			System.out.println(HMCTaxPrice);
			System.out.println(HMCProductPrice);
			
			Assert.assertTrue(df.format(HMCTaxPrice).equals(df.format(CartTaxPrice)), "Tax in HMC is not consistent with Cart");
			Assert.assertTrue(df.format(HMCProductPrice).equals(df.format(CartSubPrice)), "Product Price in HMC is not consistent with Cart");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public float getPriceValue(String Price) {
		if (Price.contains("FREE")||Price.contains("INCLUDED")){
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("SG", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll("£", "");
		Price = Price.replaceFirst(" £", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("￥", "");
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