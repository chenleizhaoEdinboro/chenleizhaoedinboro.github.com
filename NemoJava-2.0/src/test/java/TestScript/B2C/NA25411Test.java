package TestScript.B2C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;  
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import TestData.PropsUtils;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA25411Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String homepageUrl;
	public String cartUrl;
	public String pathName;
	public String partNumber;

	public NA25411Test(String Store) {
		this.Store = Store;
		this.testName = "NA-25411";
	}
	
	private enum EnumTestData {
		mtmPro, country, unit;
	}
	private String getData(String store, EnumTestData dataName) {
		if (store.equals("JP")) {
			switch (dataName) {
			case mtmPro:
				return "20H1A09GJP";
			case country:
				return "Japan";
			case unit:
				return "JP public store unit";		    
			default:
				return null;
			}
		
		} else if(store.equals("NZ")) {
			switch (dataName) {
			case mtmPro:
				return "80UR002JAU";
			case country:
				return "New Zealand";
			case unit:
				return "NZ Public unit";
			default:
				return null;
			}
		}else if(store.equals("US")) {
			switch (dataName) {
			case mtmPro:
				return "80Y70064US";
			case country:
				return "United States";
			case unit:
				return "US web store unit";
			default:
				return null;
				}
		} else if(store.equals("IE")) {
			switch (dataName) {
			case mtmPro:
				return "80X8009QUK";
			case country:
				return "Ireland";
			case unit:
				return "Ireland store";
			default:
				return null;
			}
		}else if(store.equals("CO")) {
			switch (dataName) {
			case mtmPro:
				return "81BD001XLM";
			case country:
				return "Colombia";
			case unit:
				return "coweb";  
			default:
				return null;
			}
		}else{
			return null;
		}
	}

	@Test(alwaysRun = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA16675(ITestContext ctx) {
		try {
			this.prepareTest();
			String ruleName="Auto_NA25411"+this.Store;
			pathName="D:\\NA25411\\"+this.Store+"\\";
			
			createFolderAndClearFiles();
			// set the default download path
			Dailylog.logInfoDB(1, "Setting Chrome download default path",Store, testName);
			changeChromeDefDownFolder(pathName);
			String filePath = "D:\\NA25411\\"+this.Store+"\\"+ruleName+".txt";
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//Validate if the product is available			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			System.out.println("DefaultMTMPN:"+testData.B2C.getDefaultMTMPN());
			if(!Common.checkElementDisplays(driver, By.xpath("//button[@id='addToCartButtonTop' and not(@disabled)]"), 120)){
				partNumber= getData(testData.Store, EnumTestData.mtmPro);
			}else{
				partNumber=testData.B2C.getDefaultMTMPN();
			}
			if(this.Store=="IE"){
				partNumber= getData(testData.Store, EnumTestData.mtmPro);

			}
			Dailylog.logInfoDB(2, "partNumber:"+partNumber,Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			Common.sleep(3000);
			if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 5)){
				HMCCommon.Login(hmcPage, testData);
			}
			hmcPage.Home_CatalogLink.click();
			Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partNumber);
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			String baseProduct = driver.findElement(By.xpath("//table[@title='baseProduct']//input[contains(@id,'baseProduct')]"))
								.getAttribute("value");
			baseProduct = baseProduct.substring(0, 11);
			Dailylog.logInfoDB(3, "baseProduct:"+baseProduct,Store, testName);
			
			//delete exist rule
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
			hmcPage.PricingCockpit_B2CPriceRules.click();			

			// Create eCoupon Discount			
			Dailylog.logInfoDB(4, "Create eCoupon Discount",Store, testName);
			createRule(hmcPage,baseProduct,ruleName);
			Common.sleep(5000);
			
			//Get the single ecoupon code		
            File filename = new File(filePath); 
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader); 
            String code = "";  
            String line;
            while((line=br.readLine())!=null){
            	if(line.contains("+")){
            		System.out.println(line);
            	}else if(line.contains("/")){
            		System.out.println(line);
            	}else if(line.contains("=")){
            		System.out.println(line);
            	}
            	else{
            		System.out.println(line);
            		code = line.split(" ")[0];
            		break;
            	}
            	
            }
             
            Dailylog.logInfoDB(5, "Get the ecoupon code:"+code,Store, testName);
            
            //clear the cart
            driver.get(testData.B2C.getHomePageUrl()+"/cart");			
			boolean newCartFlag=false;
			if (Common.isElementExist(driver, By.xpath("//button[contains(@class,'Submit_Button')]"))){
				newCartFlag=true;
			}
			System.out.println("newCartFlag is "+newCartFlag);
			if(newCartFlag){
				if(Common.isElementExist(driver, By.xpath(".//*[@id='emptyCartItemsForm']/a/img"))){
					b2cPage.NewCart_DeleteButton.click();
					b2cPage.NewCart_ConfirmDelete.click();					
				}

			}else{
				if (Common.isElementExist(driver,
						By.xpath("//a[contains(text(),'Empty cart')]"))) {
					driver.findElement(
							By.xpath("//a[contains(text(),'Empty cart')]")).click();
				}
				
			}	
            
            //Check the ecoupon message
			Dailylog.logInfoDB(6, "Check the ecoupon message",Store, testName);
            String url=testData.B2C.getHomePageUrl()+"?ec="+code;
            Dailylog.logInfoDB(6, url,Store, testName);                   
            driver.get(url);
            Common.sleep(15000);
            Dailylog.logInfoDB(6, "HomePage_EcouponMessage: " + b2cPage.HomePage_EcouponMessage.getText(),Store, testName); 
            if(this.Store=="JP"){
            	Assert.assertTrue(b2cPage.HomePage_EcouponMessage.getText().contains("お客様がお持ちの特別オファーEクーポンは自動でカートに適用されます"));
            }else if(this.Store=="CO"){
            	Assert.assertTrue(b2cPage.HomePage_EcouponMessage.getText().contains("El cupón ha sido aplicado. Tu descuento se verá reflejado en el carrito de compras"));
            }
            else{
            	Assert.assertTrue(b2cPage.HomePage_EcouponMessage.getText().contains("Any valid eCoupons from the email will be applied to cart automatically"));
            }
          
            //Add product to cart
            String PLPUrl=testData.B2C.getHomePageUrl()+"/p/"+partNumber+"?ec="+code;
            driver.get(PLPUrl);
            String xpath="//input[@value='"+partNumber+"']/following-sibling::button[@id='addToCartButtonTop']";
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
            Common.sleep(3000);
            
            if(Common.checkElementDisplays(driver, b2cPage.Product_AddToCartBtn, 15)){
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
            }else{
            	while(Common.checkElementDisplays(driver, b2cPage.Product_Continue, 15)){
                	((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_Continue);
            	}
            }

            Common.sleep(3000);
            
            //Check the ecoupon applies to cart
            Dailylog.logInfoDB(7,"Go to Cart page and check the ecoupon applied",Store,testName);
            System.out.println(b2cPage.NewCart_Ecoupon.getText());
        	System.out.println(b2cPage.NewCart_EcouponSavedPrice.getText());
        	Assert.assertEquals(b2cPage.NewCart_Ecoupon.getText(), code);
        	Assert.assertTrue(b2cPage.NewCart_EcouponSavedPrice.getText().contains("10"));
            
            //Place order
            b2cPage.Cart_CheckoutButton.click();

    		
            if(this.Store=="IE"){
            	Common.sleep(5000);
    			//billing info
    			driver.findElement(By.xpath(".//*[@id='email']")).clear();
    			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("autotest@lenovo.com");;
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

                System.out.println("Go to Order Compeleted page");
                WebElement TosAccepted = driver.findElement(By.xpath(".//*[@id='tosAccepted']"));  
                if(Common.checkElementExists(driver, TosAccepted, 10))
                	TosAccepted.click();
                WebElement BuyNow = driver.findElement(By.xpath(".//*[@id='submitBottom']"));
                if(Common.checkElementExists(driver, BuyNow, 10))
                	BuyNow.click();
                Common.sleep(5000);
                Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@class='dr_thankYouElementPadding']/span[@id='dr_orderDate']"), 30));
    			System.out.println("Checkout completed!!!");
            	
            }else{
            	if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
    				b2cPage.Checkout_StartCheckoutButton.click();
    			}

        		Dailylog.logInfoDB(8,"Go to Shipping page",Store,testName);
    			if(Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5)){
    				b2cPage.Shipping_editAddress.click();
    			}
    			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
    			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);	
                
    			if (Common.checkElementExists(driver, b2cPage.NewShipping_AddressValidateButton, 3)){
    				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.NewShipping_AddressValidateButton);	
    			}

    			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddressItem, 3)){
    				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Shipping_validateAddressItem);	
    			}
    			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
    				b2cPage.ValidateInfo_SkipButton.click();
    			}
    				
    			if (Common.checkElementExists(driver, b2cPage.Shipping_validateAddress, 3)){
    				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Shipping_validateAddress);	
    			}
    			Thread.sleep(3000);
    			Dailylog.logInfoDB(9,"Go to Payment page",Store,testName);
            	if(this.Store=="CO"){
            		if (Common.isElementExist(driver,
        					By.xpath("//*[@id='useDeliveryAddress']"))
        					&& !driver.findElement(
        							By.xpath("//*[@id='useDeliveryAddress']"))
        							.isSelected()) {
        				driver.findElement(By.xpath("//*[@id='useDeliveryAddress']"))
        						.clear();
        			}
        			if (Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']"))) {
        				Common.javascriptClick(driver, driver.findElement(By
        						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));

        			} else {
        				Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_Wire']")));

        			}
            		
            	}else{

        			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);       			
            		
            	}  
            	((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
    			Dailylog.logInfoDB(10,"Go to Review page",Store,testName);
    			
    			Common.sleep(5000);
                
    			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
    			B2CCommon.clickPlaceOrder(b2cPage);
    			Dailylog.logInfoDB(11,"Go to Thank you page",Store,testName);
    			
    			Common.sleep(5000);
    			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);		
    			Dailylog.logInfoDB(12,"Order number is "+OrderNumber,Store,testName);
            	
            }

			
			
			//Click on the target URL again
			Dailylog.logInfoDB(13,"Check the invalid ecoupon message",Store,testName);
			driver.get(url);
			Common.sleep(10000);
			System.out.println(b2cPage.HomePage_EcouponMessage.getText());
			if(this.Store=="JP"){
				 Assert.assertTrue(b2cPage.HomePage_EcouponMessage.getText().contains("大変申し訳ございません。特別オファーEクーポンの期限が切れているか、開始前の為、適用できません"));
			}else if(this.Store=="CO"){			
				 Assert.assertTrue(b2cPage.HomePage_EcouponMessage.getText().contains("Lo sentimos, el cupón que intentas utilizar no se encuentra vigente"));
			}
			else{
				 Assert.assertTrue(b2cPage.HomePage_EcouponMessage.getText().contains( "WE ARE SORRY, The offer has expired, has not yet started or is no longer valid"));
			}
           
            
            //Add product to cart
            Dailylog.logInfoDB(14,"Add product to cart",Store,testName);
            driver.get(PLPUrl);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
            Common.sleep(3000);
            
            if(Common.checkElementDisplays(driver, b2cPage.Product_AddToCartBtn, 5)){
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
            }else{
            	while(Common.checkElementDisplays(driver, b2cPage.Product_Continue, 5)){
                	((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_Continue);
            	}
            }
            
            //Check the ecoupon applies to cart
            Dailylog.logInfoDB(15,"Go to cart page",Store,testName);
            Assert.assertFalse(Common.checkElementDisplays(driver, b2cPage.NewCart_Ecoupon, 5));
        	Assert.assertFalse(Common.checkElementDisplays(driver, b2cPage.NewCart_EcouponSavedPrice, 5));
			
            
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	  
	  public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1,
				String xpath) throws InterruptedException {
			WebElement target;
			Common.waitElementClickable(driver, ele1, 30);
			Thread.sleep(5000);
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
	
	  public void   createRule(HMCPage hmcPage,String testBaseProduct, String ruleName) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
        		
		System.out.println("Create ecopon rules***********************");
		Thread.sleep(5000);
//		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_CreateNewGroup, 120);
		//Will delete the tax rule if the rule existed.
		HMCCommon.deleteRule(driver, hmcPage, "eCoupon Discounts", ruleName);
		
		driver.switchTo().defaultContent();
		HMCCommon.loginPricingCockpit(driver, hmcPage, testData);
		hmcPage.PricingCockpit_B2CPriceRules.click();
		
		Thread.sleep(5000);
		hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(1000);
		hmcPage.B2CPriceRules_eCouponDiscountOption.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		//rule name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);
		
		//Priority
		hmcPage.B2CPriceRules_ecouponPriority.clear();
		hmcPage.B2CPriceRules_ecouponPriority.sendKeys("500");
		
		//Check single use ecoupon
		hmcPage.B2CPriceRules_TypeSingleCoupon.click();
		
		Common.scrollToElement(driver, hmcPage.eCoupon_Stackable);
		
		//Check Stackable on instant saving
		hmcPage.eCoupon_Stackable.click();
		
		//input linit count
		hmcPage.B2CPriceRules_count.clear();
		hmcPage.B2CPriceRules_count.sendKeys("10");
		
		//Input per limit count
		hmcPage.B2CPriceRules_PeruseLimit.clear();
		hmcPage.B2CPriceRules_PeruseLimit.sendKeys("1");
		
		//Check _NUmber Of Products Order
		if(hmcPage.B2CPriceRules_NUmberProductsOrder.getAttribute("value")=="false"){
			hmcPage.B2CPriceRules_NUmberProductsOrder.click();
		}
		
		Common.scrollToElement(driver, hmcPage.B2CPriceRules_CountrySelect);
			
		// Country
		dataInput = getData(testData.Store, EnumTestData.country);
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);
		
		CommonFunction.Common.InnerScrollTop(driver, "modal fade in", "10000000");
		
		// testBaseProduct
		xpath = "//span[contains(text(),'" + testBaseProduct
						+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "BaseProduct", testBaseProduct, hmcPage.B2CPriceRules_BaseProduceSelect, xpath);
		System.out.println("Input testBaseProduct");
				
		//Material
//		xpath = "//span[contains(text(),'" + partNumber
//				+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
//		fillRuleInfo(driver, hmcPage, "Material", partNumber, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// B2Cunit
		dataInput = getData(testData.Store, EnumTestData.unit);
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		
		else if(this.Store=="CO")
			xpath = "(//div/a[contains(.,'Colombia | Public Web')])[2]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);
		
		//currency
		if(this.Store=="CO"){
			dataInput = "COP Peso";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
			fillRuleInfo(driver, hmcPage, "Currency", dataInput, hmcPage.B2BpriceSimulate_CurrencyButton, xpath);		
		}

		// ecoupon price Value
		hmcPage.B2CPriceRules_dynamicRate.click();
		hmcPage.B2CPriceRules_DynamicMinusButton.click();
		hmcPage.B2CPriceRules_ecouponDollorButton.click();
		hmcPage.B2CPriceRules_dynamicValue.clear();
		hmcPage.B2CPriceRules_dynamicValue.sendKeys("10");
		Thread.sleep(2000);

		// Add Price Rule To Group
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver,
				By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
			System.out.println("click Add Price Rule To Group --- second time");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
			System.out.println("click Create New Group --- second time");
		}
		Thread.sleep(10000);

		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CloseBtn);
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}

		Thread.sleep(5000);
		
		//get the ecopon code
		xpath="//td[contains(.,'"+ruleName+"')]/following-sibling::td/div/a[contains(.,'Edit')]";
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
		System.out.println("Clicked Edit!");
		Thread.sleep(5000);
		Assert.assertEquals(hmcPage.B2CPriceRules_RemainingQuantity.getText(), "10");
		
		//down ecoupon code
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_DownloadEcouponButton);
		System.out.println("Clicked Download!");
		Common.sleep(5000);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CloseBtn);
		System.out.println("Clicked Close!");	
		
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();

	}



	public void createFolderAndClearFiles() {
		File file = new File(pathName);

		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}

		if (file.exists()) {
			File[] files = file.listFiles();

			for (File file1 : files) {
				file1.delete();
			}
		}

	}

	public void changeChromeDefDownFolder(String downloadFilepath) throws IOException {
		driver.close();
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

}
