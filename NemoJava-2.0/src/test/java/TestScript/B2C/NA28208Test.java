package TestScript.B2C;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import ch.qos.logback.core.boolex.Matcher;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;


public class NA28208Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String ruleName;
	public String partNumber="20HEA07700";
	public String partNumber1="20HEA07600";
	double debugListPrice[]=new double[3];
	double debugTaxPrice[]=new double[3];
	double debugListAndGstPrice[]=new double[3];
	double debugWebPrice[]=new double[3];
	double debugSalesPrice[]=new double[3];
	double debugTotalPrice[]=new double[3];
	
	double TotalPriceNoTax[]=new double[3];
	double Tax[]=new double[3];
	double TotalPriceTax[]=new double[3];

	
	public NA28208Test(String Store){
		this.Store=Store;
		this.testName="COMM-328";
	}
	private double String2Num(String valueString){
		String price=valueString.replace("$", "").replace("￥", "").replace(",", "").replace("-", "");
		return Double.parseDouble(price);
	}
	@Test(alwaysRun=true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA28208(ITestContext ctx){
		
		try{
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage=new B2CPage(driver);
			hmcPage=new HMCPage(driver);
			
			
			//CTO:get debug price;List、Tax、List+GST、Web、Sales;
			System.out.print("Get CTO Pirce ");
			debugPrice(partNumber,0);
			
			//MTM:Get debug price;List、Tax、List+GST、Web、Sales;
			System.out.print("MTM Pirce before created the tax rule");
			Thread.sleep(1000);
			debugPrice(partNumber1,1);
			System.out.println("Tax before created rule"+Tax[1]);
			//Create Tax_Exemption rule for MTM;
			CreateTaxExemption(hmcPage,partNumber1);
			//MTM:Get debug TAX price;
			debugPrice(partNumber1,2);

			
			//Checkout(productNo);--MTM1
			checkPaymentPagePrice(partNumber,"",0,true);
//			On payU Page,check the price;
			Dailylog.logInfoDB(2, "Validate MTM1 Price",Store, testName);
			Assert.assertEquals(TotalPriceNoTax[0], debugListPrice[0], 1, "MTM:TotalPriceNoTax =List price in hmc");
			Assert.assertEquals(Tax[0], debugTaxPrice[0], 1,"MTM:Tax =Tax in hmc");
			Assert.assertEquals(TotalPriceTax[0], debugTotalPrice[0], 1, "MTM:TotalPriceTax display correctly");
			
			
			//Checkout(productNo);--MTM2
			checkPaymentPagePrice(partNumber1,"",1,false);
			//On payU Page ,check the price
			Dailylog.logInfoDB(3, "Validate MTM2 Price",Store, testName);
			Assert.assertEquals(Tax[1], debugTaxPrice[1], 1,"Tax =Tax in hmc");
			Assert.assertEquals(TotalPriceTax[1], debugTotalPrice[1], 1, "TotalPriceTax =ListAndGst in hmc");						

					
			//checkout(productNo);--MTM1+MTM2;
			checkPaymentPagePrice(partNumber,partNumber1,2,true);
			//On the payU Page ,check the price +Tax.
			Dailylog.logInfoDB(4, "Validate MTM1+MTM2 Price",Store, testName);
			Assert.assertEquals(TotalPriceNoTax[2], debugListPrice[0]+debugListPrice[1],2, "TotalPriceWithoutTax shows correctly");
			Assert.assertEquals(Tax[2], debugTaxPrice[0],1, "Tax shows correctly");
			Assert.assertEquals(TotalPriceTax[2], debugTotalPrice[0]+debugTotalPrice[1],2,"TotalPricewithTax shows correctly");
		}catch(Throwable e){
			handleThrowable(e,ctx);
			}
			
		}
		public void debugPrice(String productNo,int i) throws InterruptedException  {
			
			//Go to hmc -->Price Settings -->Pricing Cockpit -->B2C Price Simulator
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			if(Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))){
				HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			}
			HMCCommon.B2CPriceSimulateDebug(driver, hmcPage, "[CO] Colombia", "coweb", "Nemo Master Multi Country Product Catalog", productNo, "");
			
			//Get Price
			String debugListPriceText=hmcPage.B2CpriceSimulate_ListPrice.getText().toString();
			debugListPrice[i]= String2Num(debugListPriceText);
			String debugTaxPriceText=hmcPage.B2CpriceSimulate_TaxPrice.getText().toString();
			debugTaxPrice[i]= String2Num(debugTaxPriceText);
			String debugListAndGstPriceText=hmcPage.B2CpriceSimulate_ListAndGstPrice.getText().toString();
			debugListAndGstPrice[i]=String2Num(debugListAndGstPriceText);
			//web price
			String debugWebPriceText= driver.findElement(By.xpath(".//*[@id='web']/samp")).getText().toString();
			debugWebPrice[i] =String2Num(debugWebPriceText);
			//sales
			String debugSalesPriceText=driver.findElement(By.xpath(".//*[@id='sales']/samp")).getText().toString();
			debugSalesPrice[i]=String2Num(debugSalesPriceText);
			//total price
			String totalPriceText = hmcPage.B2CpriceSimulate_TotalPrice.getText().toString();
			debugTotalPrice[i] = String2Num(totalPriceText);
			System.out.println("ListPrice: "+debugListPrice[i]+" ;Tax price: "+debugTaxPrice[i]+" ;Web Price :"+debugWebPrice[i]+" ;ListAndGstPrice"+debugListAndGstPrice[i]+" ;SalesPrice"+debugSalesPrice[i]+" ;Total Price :"+debugTotalPrice[i]);
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();
		}
		
		public void checkPaymentPagePrice(String productNo,String partNum,int i,boolean tax) throws InterruptedException, IOException{
			
			B2CCommon.handleGateKeeper(b2cPage, testData);		
			driver.get(testData.B2C.getHomePageUrl()+"/cart");			
			if (Common.isElementExist(driver,
					By.xpath("//a[contains(text(),'Empty cart')]"))) {
				driver.findElement(
						By.xpath("//a[contains(text(),'Empty cart')]")).click();
			}else if(Common.checkElementExists(driver, b2cPage.NewCart_DeleteButton, 5)){
				b2cPage.NewCart_DeleteButton.click();
				b2cPage.NewCart_ConfirmDelete.click();
			}
			
			// Quick order
			B2CCommon.addPartNumberToCart(b2cPage, productNo);
			if(partNum!=""){
				B2CCommon.addPartNumberToCart(b2cPage, partNum);
			}
			
			if(Common.checkElementDisplays(driver, By.xpath("//div/input[@id='active_button']"), 10)){
				driver.findElement(By.xpath("//div/input[@id='active_button']")).click();
			}

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
				Thread.sleep(8000);
				
				if(tax){
					//Get price on payment page.
					String TotalPriceNoTaxText=driver.findElement(By.xpath("*//div[@id='total_purchase']/div/span[2]")).getText().toString();
					TotalPriceNoTax[i]=String2Num(TotalPriceNoTaxText);
					String TaxText=driver.findElements(By.xpath("*//div[@class='bg-box-orderdotted']/span[2]")).get(1).getText().toString();
					Tax[i]=String2Num(TaxText);
					String TotalPriceTaxText=driver.findElements(By.xpath("*//div/span[@class='ng-binding ng-scope']")).get(0).getText().toString();
					TotalPriceTax[i]=String2Num(TotalPriceTaxText);
					System.out.println("TotalPriceNoTax :"+TotalPriceNoTax[i]+" ;Tax: "+Tax[i]+" ;TotalPriceTax: "+TotalPriceTax[i]);
				}else{
					Assert.assertFalse(Common.checkElementDisplays(driver,By.xpath("*//div[@id='total_purchase']/div/span[2]"), 10));
					
					TotalPriceNoTax[i]=String2Num("0");
					Tax[i]=String2Num("0");
					String TotalPriceTaxText=driver.findElements(By.xpath("*//div/span[@class='ng-binding ng-scope']")).get(0).getText().toString();
					TotalPriceTax[i]=String2Num(TotalPriceTaxText);
					System.out.println("TotalPriceNoTax :"+TotalPriceNoTax[i]+" ;Tax: "+Tax[i]+" ;TotalPriceTax: "+TotalPriceTax[i]);
				}
				

			}else{
				Assert.fail("PayU paymnet method need config");
				
			}
	}
		
		public void CreateTaxExemption(HMCPage hmcPage,String productNo) throws InterruptedException{
			ruleName=testName+"Auto_Test";
			//Delete existed rule.
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_PriceSettings.click();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();
			//Will delete the tax rule if the rule existed.
			HMCCommon.deleteRule(driver, hmcPage, "Tax Exemption", ruleName);
			
			driver.switchTo().defaultContent();
			HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();

			Thread.sleep(5000);
			hmcPage.B2CPriceRules_CreateNewGroup.click();
			Thread.sleep(3000);
			hmcPage.B2CPriceRules_SelectGroupType.click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[contains(text(),'Tax Exemption')]")).click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);
			hmcPage.B2CPriceRules_PriceRuleName.clear();
			hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
			System.out.println("Input rulename");

			// Validate from date
			hmcPage.B2CPriceRules_ValidFrom.click();
			Thread.sleep(1000);
			int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
			WebElement yesterday;
			if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
			} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td"))
					.get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
			}

			// Country
			String dataInput = "Colombia";
			String xpath = "//span[text()='" +dataInput + "' and @class='select2-match']/../../*[not(text())]";
			HMCCommon.fillRuleInfo(driver, hmcPage, dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);
			System.out.println("Input Country");
			// Catalog
			dataInput = "Nemo Master Multi Country Product Catalog - Online";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			HMCCommon.fillRuleInfo(driver, hmcPage, dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);
			System.out.println("Input Catalog");
		
			// Material
			xpath = "//span[contains(text(),'" + productNo
					+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
			HMCCommon.fillRuleInfo(driver, hmcPage,productNo, hmcPage.B2CPriceRules_MaterialSelect, xpath);
			System.out.println("Input Material");
		
			dataInput = "COP Peso";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
			HMCCommon.fillRuleInfo(driver, hmcPage,dataInput, hmcPage.B2BpriceSimulate_CurrencyButton, xpath);	
			
			Thread.sleep(5000);
			
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.B2CPriceRules_AddPriceRuleToGroup);
			System.out.println("Add Price Rule to Group ");	
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		
			Thread.sleep(5000);
			if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.B2CPriceRules_CloseBtn);
				System.out.println("Clicked close!");
				Thread.sleep(3000);
			}
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();
		
		}
		@AfterTest 
	    public void rollBack(){
	    	System.out.println("---------RollBack----------");
	    	
	    	try{
	    		SetupBrowser();
	    		hmcPage = new HMCPage(driver);
	    		driver.get(testData.HMC.getHomePageUrl());
	    		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
	    			HMCCommon.Login(hmcPage, testData);
	    		}
	    		hmcPage.Home_PriceSettings.click();
	    		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
	    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.PricingCockpit_B2CPriceRules);
	    		Thread.sleep(1500);
	    		HMCCommon.deleteRule(driver, hmcPage, "Tax Exemption", ruleName);
				driver.switchTo().defaultContent();
				hmcPage.hmcHome_hmcSignOut.click();
	    		Dailylog.logInfoDB(8,"Rule deleted", Store,testName);
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}}	

		
}

