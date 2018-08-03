package TestScript.B2C;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA23022Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public MailPage mailPage;
	public String strCartSubPrice;
	public String strCartTotalPrice;
	public String strVatPrice;
	public String partNumber="4X40K09936";
	public float VatPrice;
	public float CartSubPrice;
	public float CartTotalPrice;
	public float CompletedTotalPrice;
	public String BillingEmail = "autotest@lenovo.com";
	DecimalFormat df = new DecimalFormat("#.00");
	public NA23022Test(String store){
		this.Store = store;
		this.testName = "NA-23022";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup","pricingpromot", "p2", "b2c" })
	public void NA23022(ITestContext ctx){
		try{
			this.prepareTest();
			
			mailPage =  new MailPage(driver);
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
//			String partNumber=testData.B2C.getDefaultMTMPN();
			
			Dailylog.logInfoDB(1,"Login HMC and Setting.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
			
			//1. Go to HMC,Navigate to Base Commerce -> Base Store
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
			
			//2. Go to Administration tab(Net:Make Sure the Net check-box is uncheck)			
			System.out.println("Go to Administration tab:Net check-box is uncheck");
			hmcPage.baseStore_administration.click();	
			if(hmcPage.basestore_NetValue.getAttribute("value").contains("true")){
				hmcPage.basestore_NetCheck.click();
			}
			
			// 3. Go to Administration tab(Tax Type:Make Sure NO Tax is selected)
			System.out.println("Make Sure NO Tax is selected");
			hmcPage.basestore_NoTax.click();
			hmcPage.Types_SaveBtn.click();
			Common.sleep(5000);
			
			//4. Navigate to B2C Commerce ->B2C UNIT
			System.out.println("Navigate to B2C Commerce ->B2C UNIT");
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			
			//5. Go to Site Atrribute Tab(Enable GEO Link :Make sure the NO radio button is selected)
			System.out.println("Disable GEO Link ");
			hmcPage.B2CUnit_SiteAttributeTab.click();
			driver.findElement(By.xpath(".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.enableGeoLink') and contains(@id,'false')]")).click();
			
			//6. Go to Site Attribute Tab(Tax Toggle:Make sure the YES radio button is selected)
			System.out.println("Setting Tax Toggle->Yes ");
			hmcPage.B2CUnit_EnableTax.click();	
			
			//7. Go to Site Attribute Tab(GB Value:20.00 : Maintain tax rate is twenty percent)
			System.out.println("Maintain tax rate is twenty percent ");
			hmcPage.B2CUnit_TaxValue.clear();
			hmcPage.B2CUnit_TaxValue.sendKeys("20.00");
			
			//8. GO to Site Attribute Tab(Sabrix Engine Toggle:Make sure the NO radio button is selected)
            driver.findElement(By.xpath(".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.useSabrixEngine') and contains(@id,'false')]")).click();
			
            //9. Go to Site Attribute Tab(Is Digital River Country:Make sure the YES radio button is selected)
            System.out.println("Setting Is Digital River Country->Yes ");
            driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.isDigitalRiverCountry]]_true']")).click();
            hmcPage.Types_SaveBtn.click();
            
			//10. Go to B2C website(https://pre-c-hybris.lenovo.com/gb/en/gbweb)
			Dailylog.logInfoDB(2,"Login GB Website.", Store,testName);
            driver.get(testData.B2C.getHomePageUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			//11. Select one product,Add to car then go to Cart Page 
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			
			//Check if the cart page is new UI
			boolean newCartFlag=false;
			if (Common.isElementExist(driver, By.xpath(".//div[@id='quickAddInput']/button[contains(.,'SUBMIT')]"))){
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
				System.out.println("Click Add button");
				//check part number if is not valid
				
				//Get SubTota Price
				strCartSubPrice=b2cPage.NewCart_SubTotalPrice.getText();
				//Get total Price
				strCartTotalPrice=b2cPage.NewCart_TotalPrice.getText();
				//Get VAT Price
				strVatPrice=b2cPage.NewCart_VATPrice.getText();
			}else{
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
					driver.findElement(
							By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
				}
				B2CCommon.addPartNumberToCart(b2cPage, partNumber); 
				strCartSubPrice = driver.findElement(By.xpath(".//*[@id='mainContent']/div[2]/div[3]/div[1]/dl[1]/dd/span")).getText();
			    strCartTotalPrice = driver.findElement(By.xpath(".//*[@id='mainContent']/div[2]/div[3]/div[1]/dl[2]/dd/span")).getText();
			    strVatPrice = driver.findElement(By.xpath("//div/span[@class='cart-summary-vax-digitalRiver']/span[@class='price-calculator-cart-summary-totalTax']")).getText();
			}
			
		    CartSubPrice = getPriceValue(strCartSubPrice);			
			CartTotalPrice = getPriceValue(strCartTotalPrice);
			String arr[]=strVatPrice.split("\\s+");
			VatPrice = getPriceValue(arr[0]);			
			System.out.println("CartSubPrice:"+CartSubPrice);
			System.out.println("CartTotalPrice:"+CartTotalPrice);
			System.out.println("VatPrice:"+VatPrice);
			
			//check CartSubPrice==CartTotalPrice
			Assert.assertEquals(CartSubPrice, CartTotalPrice, 0);
			
			//14. Go to Billing address page
			System.out.println("Go to DR Billing adress page");
			if(Common.checkElementDisplays(driver, b2cPage.Cart_CheckoutButton, 5))
				b2cPage.Cart_CheckoutButton.click();
			//close cookies alert
			 if(Common.checkElementDisplays(driver, By.xpath(".//*[@id='_evh-ric-c']"),5)){
	            	WebElement elem=driver.findElement(By.xpath(".//*[@id='_evh-ric-c']"));
	            	JavascriptExecutor js=(JavascriptExecutor) driver;
	            	js.executeScript("arguments[0].click();", elem);	
	            }
			float ShippingSubPrice = getPriceValue(driver.findElement(By.xpath(".//*[@id='dr_priceTotal']/td[3]")).getText());
			float ShippingVATPrice = getPriceValue(driver.findElement(By.xpath(".//*[@id='dr_estimatedTaxTotal']/td[3]")).getText());
			float ShippingTotalPrice = getPriceValue(driver.findElement(By.xpath(".//*[@id='dr_estimatedPriceTotal']/td[3]")).getText());
			float productPrice = getPriceValue(driver.findElement(By.xpath("//tr[@class='dr_odd dr_oddRow']/td[@class='dr_price']")).getText());
			System.out.println("ShippingSubPrice:"+ShippingSubPrice+"\nShippingVATPrice:"+ShippingVATPrice+"\nShippingTotalPrice:"+ShippingTotalPrice);
			System.out.println("product Price:"+productPrice);
			
			//1. Tax price = step 11 Tax Price 
			Assert.assertEquals(ShippingVATPrice, VatPrice, 0.1);
			//2. Total Price = step 11 total price
//			Assert.assertEquals(ShippingSubPrice, CartTotalPrice, 0.1);
			//3. Subtotal Price = Total price - Tax Price
//			Assert.assertEquals(ShippingSubPrice, ShippingTotalPrice-ShippingVATPrice, 0.1);
			Common.sleep(3000); 
			
			//billing info
			driver.findElement(By.xpath(".//*[@id='email']")).clear();
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(BillingEmail);;
			driver.findElement(By.xpath(".//*[@id='billingName1']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingName1']")).sendKeys("AutoFirstName");
            driver.findElement(By.xpath(".//*[@id='billingName2']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingName2']")).sendKeys("AutoLastName");
            driver.findElement(By.xpath(".//*[@id='billingCompanyName']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingCompanyName']")).sendKeys("testbillingCompanyName");
            driver.findElement(By.xpath(".//*[@id='billingAddress1']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingAddress1']")).sendKeys(testData.B2C.getDefaultAddressLine1());
            driver.findElement(By.xpath(".//*[@id='billingCity']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingCity']")).sendKeys(testData.B2C.getDefaultAddressCity());
            driver.findElement(By.xpath(".//*[@id='billingPostalCode']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingPostalCode']")).sendKeys(testData.B2C.getDefaultAddressPostCode());
            driver.findElement(By.xpath(".//*[@id='billingPhoneNumber']")).clear();
            driver.findElement(By.xpath(".//*[@id='billingPhoneNumber']")).sendKeys(testData.B2C.getDefaultAddressPhone());
            // payment info
            driver.findElement(By.xpath(".//*[@id='ccNum']")).clear();
            driver.findElement(By.xpath(".//*[@id='ccNum']")).sendKeys("4012888888881881");
            driver.findElement(By.xpath(".//*[@id='ccMonth']/option[7]")).click();
            driver.findElement(By.xpath(".//*[@id='ccYear']/option[4]")).click();
            driver.findElement(By.xpath(".//*[@id='cardSecurityCode']")).clear();
            driver.findElement(By.xpath(".//*[@id='cardSecurityCode']")).sendKeys("881");
            WebElement CheckoutButton = driver.findElement(By.xpath(".//*[@id='checkoutButton']"));
            if(Common.checkElementExists(driver, CheckoutButton, 5))
            	CheckoutButton.click(); 
            Common.sleep(3000);
            
            System.out.println("Go to VAT Exemption Registration page");
            WebElement SkipRegistration = driver.findElement(By.xpath(".//*[@id='vr_skipregistration']"));
            if(Common.checkElementExists(driver, SkipRegistration, 5))
            	SkipRegistration.click();
            Common.sleep(8000);
            //15. Go to Verify Order page Tax amount is shown on the page,VAT price and Sub-Total price and Total price is the same as step 14
            System.out.println("Go to Verify Order page");
            float VerifyOrderSubPrice = getPriceValue(driver.findElement(By.xpath(".//*[@id='dr_priceTotal']/td[4]")).getText());
            float VerifyOrderVATPrice = getPriceValue(driver.findElement(By.xpath(".//*[@id='dr_taxTotal']/td[4]")).getText());
            float VerifyOrderTotalPrice = getPriceValue(driver.findElement(By.xpath("(//tr/td[@class='dr_price dr_totals'])[4]")).getText());
            System.out.println("VerifyOrderSubPrice"+VerifyOrderSubPrice+"&VerifyOrderVATPrice"+VerifyOrderVATPrice+"&VerifyOrderTotalPrice"+VerifyOrderTotalPrice);
           
            
            //16. Go to Order Compeleted page
            System.out.println("Go to Order Compeleted page");
            WebElement TosAccepted = driver.findElement(By.xpath(".//*[@id='tosAccepted']"));  
            if(Common.checkElementExists(driver, TosAccepted, 10))
            	TosAccepted.click();
            WebElement BuyNow = driver.findElement(By.xpath(".//*[@id='submitBottom']"));
            if(Common.checkElementExists(driver, BuyNow, 10))
            	BuyNow.click();
            Common.sleep(5000);
            System.out.println("changeCompletedTotalPrice:"+driver.findElement(By.xpath(".//*[@id='dr_orderInformation']/div/span[3]")).getText().split(":")[1].trim());
            CompletedTotalPrice = getPriceValue(driver.findElement(By.xpath(".//*[@id='dr_orderInformation']/div/span[3]")).getText().split(":")[1].trim());
            System.out.println("CompletedTotalPrice:"+df.format(CompletedTotalPrice));
            Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@class='dr_thankYouElementPadding']/span[@id='dr_orderDate']"), 30));
			System.out.println("Checkout completed!!!");
			
            //11. Go to HMC,Navigate to Price Setting ->Pricing Cockpit
			Dailylog.logInfoDB(3,"Login HMC and check VAT.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				HMCCommon.Login(hmcPage, testData);
			}
			if(Common.checkElementExists(driver, hmcPage.Home_PriceSettings, 10))
				hmcPage.Home_PriceSettings.click();
			if(Common.checkElementExists(driver, hmcPage.Home_PricingCockpit, 10))
				hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			Common.sleep(3000);
			if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
				hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
				hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
				hmcPage.PricingCockpit_Login.click();
			}
			Common.sleep(3000);
			//12. Go to B2C Price Simulator
			hmcPage.PricingCockpit_B2CPriceSimulator.click();
			Common.sleep(3000);
			//Country -> [GB]United Kingdom
			WebElement B2CpriceSimulate_CountryButton = driver.findElement(By.xpath("//span[contains(.,'Select Country...')]"));
		    B2CpriceSimulate_CountryButton.click();
			System.out.println("country click");
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 3);
			hmcPage.B2CPriceRules_SearchInput.clear();
	        hmcPage.B2CPriceRules_SearchInput.sendKeys("[GB] United Kingdom");
	        hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
			System.out.println("contry sendkey");
		    Common.sleep(3000);
		    //Store: UK Public Web Store
		    WebElement B2CpriceSimulate_StoreButton = driver.findElement(By.xpath("//span[contains(.,'Select Store...')]"));
		    Common.waitElementClickable(driver, B2CpriceSimulate_StoreButton, 120);
		    B2CpriceSimulate_StoreButton.click();
		 	System.out.println("store click");
		 	Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 10);
		 	hmcPage.B2CPriceRules_SearchInput.clear();
		 	hmcPage.B2CPriceRules_SearchInput.sendKeys("UK Public Web Store");		 			
		 	hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
		 	System.out.println("store sendkey:");
		 	Common.sleep(3000);
			//Catalog Version: Nemo Master Multi Country Product Catalog-Online  
		 	WebElement B2CpriceSimulate_CatalogButton = driver.findElement(By.xpath("//span[contains(.,'Select Catalog...')]"));
		 	Common.waitElementClickable(driver, B2CpriceSimulate_CatalogButton, 120);
		 	B2CpriceSimulate_CatalogButton.click();
			System.out.println("catalog click:");
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 3);
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys("Nemo Master Multi Country Product Catalog - Online");
			hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
			System.out.println("catalog sendkey:");
			Common.sleep(3000);
			// Price Date -> now Date
			WebElement B2CpriceSimulate_DateButton=driver.findElement(By.xpath("//*[@id='ps_date']"));
			Common.waitElementClickable(driver, B2CpriceSimulate_DateButton, 120);
			B2CpriceSimulate_DateButton.click();
			System.out.println("date click:");
			B2CpriceSimulate_DateButton.sendKeys(Keys.ENTER);
			System.out.println("date sendkey:");
			Common.sleep(3000);
			// Product 
			WebElement B2CpriceSimulate_ProductButton = driver.findElement(By.xpath("//span[contains(.,'Select Product...')]"));
			Common.waitElementClickable(driver, B2CpriceSimulate_ProductButton, 120);
			B2CpriceSimulate_ProductButton.click();
			System.out.println("product click:");
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_SearchInput, 3);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(partNumber);  //testData.B2C.getDefaultMTMPN()
			WebElement productResult = driver.findElement(By.xpath("//span[text()='" + partNumber + "']"));//testData.B2C.getDefaultMTMPN()
			Common.waitElementVisible(driver, productResult);
			hmcPage.B2CPriceRules_SearchInput.sendKeys(Keys.ENTER);
			Common.sleep(5000);
			System.out.println("product sendkey:");
			Common.sleep(3000); 
			WebElement B2CpriceSimulate_DebugButton = driver.findElement(By.xpath("//*[@id='ps_button']"));
			B2CpriceSimulate_DebugButton.click();
			Common.sleep(3000);
			System.out.println("TaxPrice:"+hmcPage.B2CpriceSimulate_TaxPrice.getText());
			System.out.println("ListPrice:"+hmcPage.B2CpriceSimulate_ListPrice.getText());
			System.out.println("ListAndGstPrice"+hmcPage.B2CpriceSimulate_ListAndGstPrice.getText());
			System.out.println("TotalPrice"+hmcPage.B2CpriceSimulate_TotalPrice.getText());

			float TaxPrice = getPriceValue(hmcPage.B2CpriceSimulate_TaxPrice.getText().split(" ")[1]);
			float ListPrice = getPriceValue(hmcPage.B2CpriceSimulate_ListPrice.getText());
			float listAndGstPrice =getPriceValue(hmcPage.B2CpriceSimulate_ListAndGstPrice.getText().split(" ")[1]);
			float HMCSimulatorPrice = getPriceValue(hmcPage.B2CpriceSimulate_TotalPrice.getText());
			Dailylog.logInfoDB(4,"TaxPrice Price"+TaxPrice,Store,testName);
			Dailylog.logInfoDB(5,"ListPrice Price"+ListPrice,Store,testName);
			Dailylog.logInfoDB(6,"listAndGstPrice Price"+listAndGstPrice,Store,testName);
			Dailylog.logInfoDB(7,"Simulator Price"+HMCSimulatorPrice,Store,testName);
			
			//Tax price=List price *20% 
			Assert.assertTrue((df.format(0.2*ListPrice).equals(df.format(TaxPrice))), "TaxPrice is not List double 0.2 ");
			//List + GST= List + Tax
			Assert.assertTrue((df.format(TaxPrice+ListPrice).equals(df.format(listAndGstPrice))), "TaxPrice plus ListPrice not equal listAndGstPrice ");
			//Total price is the same as the price on the Web site
			Assert.assertTrue(df.format(HMCSimulatorPrice).equals(df.format(CartTotalPrice)), "Total price is the same as the price on the website");
						
		}catch (Throwable e) {
			handleThrowable(e, ctx);
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
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}
}