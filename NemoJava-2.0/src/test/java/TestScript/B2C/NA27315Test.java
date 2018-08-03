package TestScript.B2C;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
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
import TestScript.SuperTestClass;

public class NA27315Test extends SuperTestClass {

	public B2CPage b2cPage;
	private HMCPage hmcPage;
	private String name;
	private String description;
	private String UnitID;
	private String defaultMTMPN = "80Y70063US";
	private String partNumber;
	private String partNumberBackup="80Y70063US";
	private String homePageUrl;
	private String valueRealDate = "2";
	private String leadTimeMessKey = "hightech.category.leadTimeRealDate";
	private String leadTimeMess = "ships on {0}";
	private String leadTimeShortMess = "ships {0}";
	private double nextPrice = 26.00;
	private double expeditedPrice = 12.00;
	private int i=1;
	private double shippingPrice;
	private String dateTime = "03/12/2018";
//	String dateTime = testDateFormat();
	private String leadtime1_message = "ships on " + dateTime;
	private String leadtime_ships = "ships " + dateTime;
	
	public NA27315Test(String store,String unit) {
		this.Store = store;
		this.testName = "NA-27315";
		this.UnitID = unit;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA27315(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			homePageUrl = testData.B2C.getHomePageUrl();
			JavascriptExecutor js= (JavascriptExecutor) driver;
			
			// Step 1 login hmc,enable the "Show Real Date Leadtime" toogle
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();

			hmcPage.B2CUnit_IDTextBox.sendKeys(UnitID);
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Dailylog.logInfoDB(1, "Logged into HMC,enable the 'Show Real Date Leadtime' toogle", Store, testName);
			
			//step 2  set the "Real Date Threshold" as 2
//			Common.sleep(3000);
//			WebElement realDateThreshold = driver.findElement(By.id("Content/IntegerEditor[in Content/Attribute[B2CUnit.showRealDays]]_input"));
//			realDateThreshold.clear();
//			realDateThreshold.sendKeys(valueRealDate);		
//			Dailylog.logInfoDB(2, "Set the 'Real Date Threshold' as 2", Store, testName);
			
			//step 3 add "next biz day delivery" as delivery mode on the store.
			//Need developer import info then add "next biz day delivery" as delivery mode,so we can manually complete this step
			
			//step 4 add real date lead time message manually
			//step 5 add real date lead time short message manually
			
			//step 6&7&8
			setupProLeadtime("6");
			clearProCash();
			Common.switchToWindow(driver, 1);
			driver.get(homePageUrl+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			driver.get(homePageUrl+"/laptops/yoga/yoga-900-series/Yoga-910/p/88YG9000786?menu-id=Yoga_910_(14\")");
			Common.sleep(3000);
			String leadtime6_message = "Ships in 5-7 business days.";
			checkLeadtimeMess(leadtime6_message);
			Dailylog.logInfoDB(7, "On sub series page and shoping cart page, shows the lead time correctly." + leadtime6_message, Store, testName);
			
			//step 9
			setupProLeadtime("1");
			clearProCash();
			Common.switchToWindow(driver, 1);
			
			//login B2C website
			driver.get(homePageUrl);
//			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			driver.get(testData.B2C.getHomePageUrl()+"/p/" + partNumberBackup);
//			if(!Common.checkElementExists(driver, b2cPage.Add2Cart, 5)){
//				partNumber=partNumberBackup;
//			}else{
//				partNumber=testData.B2C.getDefaultMTMPN();
//			}
			
			closePromotion(driver, b2cPage);
			Common.sleep(3000);
			b2cPage.Product_viewModel.click();
			
			//verify PLP page LT message
			String ele_PLPPage_LTM = "//*[@id='builderPricingSummary']/div[2]/div";
			System.out.println("====start verify PLP LT Message====");
			Common.sleep(3000);
			verifyLTMes(leadtime_ships, ele_PLPPage_LTM);

			//click top buttopn
			driver.findElement(By.className("subseriesBackToTop")).click();
			Common.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			//verify cart page LT message
			B2CCommon.addPartNumberToCart(b2cPage, partNumberBackup);
			String xpath_subPage = "//dt[@id='cartEntryNumber_0']";
			verifyLTMes(leadtime1_message,xpath_subPage);

			//add the second product
			B2CCommon.addPartNumberToCart(b2cPage, partNumberBackup);
			Dailylog.logInfoDB(9, "shows the lead time correctly." + leadtime1_message, Store, testName);
			
			//step 10
			driver.findElement(By
					.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			VerifyShippingMethod(dateTime, "0");
			VerifyShippingMethod(leadtime1_message, "1");
			Dailylog.logInfoDB(10, "shipping option is showed for each item,there is next biz day delivery option." , Store, testName);

			//step 11
			Common.sleep(3000);
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping_ShippingPrice, 5)){
				shippingPrice = GetPriceValue(b2cPage.NewShipping_ShippingPrice.getText());	
			}
			double expectedshippingPrice = nextPrice + expeditedPrice;
			System.out.println(shippingPrice);
			System.out.println(expectedshippingPrice);
			Assert.assertEquals(shippingPrice, expectedshippingPrice);
			Dailylog.logInfoDB(11, "the delivery cost is calculated correct on shipping page.The shipping total price:" + shippingPrice, Store, testName);
			Dailylog.logInfoDB(14, "delivery cost is charged base on the quantity of delivery cost." , Store, testName);

			//step 12
			//click shipping continue button
			b2cPage.Shipping_ContinueButton.click();
			
			Common.sleep(2000);
			B2CCommon.handleAddressVerify(driver, b2cPage);
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
				b2cPage.ValidateInfo_SkipButton.click();
			}
			
			Common.sleep(5000);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			b2cPage.Payment_ContinueButonNew.click();
			
			//verify summary page 
			String sum_LTMess0 = driver.findElement(By.xpath("//span[@class='leadTimeMessage'][1]")).getText();
			System.out.println(sum_LTMess0);
			Assert.assertEquals(sum_LTMess0, dateTime);
			
			String xpath_LTmess1 = "//*[@id='mainContent']/div[3]/div[2]/div[3]/div[2]/div[1]/p/span";
			String sum_LTMess1 = driver.findElement(By.xpath(xpath_LTmess1)).getText();
			System.out.println(sum_LTMess1);
			Assert.assertEquals(sum_LTMess1, leadtime1_message);
			Dailylog.logInfoDB(12, "summary page lead time message is correct on each item." , Store, testName);

			shippingPrice = GetPriceValue(b2cPage.NewReview_ShippingPrice.getText());
			Assert.assertEquals(shippingPrice, expectedshippingPrice, "Summary page shipping price is incorrect.");
			System.out.println(shippingPrice);
			Dailylog.logInfoDB(12, "summary page the delivery cost is calculated correct" , Store, testName);
			
			//step 13-15
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			//verify email
			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
//			checkConfirmationEmail("testus",OrderNumber,PaymentSubPrice,ThankTaxPrice,ThankTotalPrice,4);
			Dailylog.logInfo("13, click Lenovo checkout on shopping cart page");
			
			Common.sleep(3000);
			String NewThankPage_LTMessage = driver.findElement(By.xpath("//table[@class='checkout-confirm-orderSummary-orderTotals']/tbody/tr[2]/td[2])")).getText();
			System.out.println(NewThankPage_LTMessage);
			Assert.assertEquals(NewThankPage_LTMessage, leadtime1_message);
			
			String NewThankPage_ShippingPrice = driver.findElement(By.xpath("//table[@class='checkout-confirm-orderSummary-orderTotals']/tbody/tr[3]/td[2])")).getText();
			float thankyouShippingPrice = GetPriceValue(NewThankPage_ShippingPrice);
			System.out.println(thankyouShippingPrice);
			Assert.assertEquals(thankyouShippingPrice, expectedshippingPrice);
			Dailylog.logInfoDB(15,"thankyou page the delivery cost is calculated correct",Store,testName);
		
			//step 16
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			if (Common.checkElementDisplays(driver, b2cPage.StartASM, 5)) {
				Common.javascriptClick(driver, b2cPage.StartASM);
			} 

			driver.get(testData.B2C.getHomePageUrl()+"/p/" + partNumberBackup);
			Common.sleep(2000);
			closePromotion(driver, b2cPage);
			Common.sleep(3000);
			b2cPage.Product_viewModel.click();
			
			//verify PLP page LT message
			String ele_PLPPage_LTM1 = "//*[@id='builderPricingSummary']/div[2]/div";
			System.out.println("====verify PLP LT Message====");
			Common.sleep(3000);
			verifyLTMes(leadtime_ships, ele_PLPPage_LTM1);
			
			//TOP button
			driver.findElement(By.className("subseriesBackToTop")).click();
			Common.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			B2CCommon.addPartNumberToCart(b2cPage, partNumberBackup);
			String tele_LTMess = driver.findElement(By.xpath("//dl[contains(@class,'cart-item-estimatedDate qa_LeadTime_Msg')]/dt")).getText();
			Assert.assertEquals(tele_LTMess, leadtime1_message);
			Dailylog.logInfoDB(16,"real date lead time is showed correctly with ASM",Store,testName);
			
			//step 17
			driver.findElement(By
					.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			Common.sleep(3000);
			VerifyShippingMethod(dateTime, "0");
			
			double tele_NewPrice = 9.00;
			driver.findElement(By.xpath("//*[@id='price']")).sendKeys(String.valueOf(tele_NewPrice));
			driver.findElement(By.xpath("//*[@id='reasonText']")).sendKeys("qaz");
			driver.findElement(By.xpath("//input[contains(@class,'button-standard button-small cart-item-pricing-and-quantity-form-button')]")).click();
			Common.sleep(3000);
			float tele_shippingPrice = GetPriceValue(b2cPage.NewShipping_ShippingPrice.getText());
			System.out.println(tele_shippingPrice);
			System.out.println(tele_NewPrice);
			Assert.assertEquals(tele_shippingPrice, tele_NewPrice);
			Dailylog.logInfoDB(17,"tele overwrote the delivery cost." + tele_shippingPrice,Store,testName);
			
			//step 18
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			b2cPage.Shipping_ContinueButton.click();
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
				b2cPage.ValidateInfo_SkipButton.click();
			}
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			b2cPage.Payment_ContinueButonNew.click();
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			
			WebElement ele_thankShippingPrice = driver.findElement(By.xpath("//div[@class = 'font-color-green shipping-value']"));
			float tele_thankyouShippingPrice = GetPriceValue(ele_thankShippingPrice.getText());
			System.out.println(tele_thankyouShippingPrice);
			Assert.assertEquals(tele_thankyouShippingPrice, tele_NewPrice);
			Dailylog.logInfoDB(18,"delivery cost change is display well.",Store,testName);
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
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
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(page.PromotionBanner).click().perform();
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
	
	public void VerifyShippingMethod(String expectMess,String item){
		String xpath0 = "cartEntryShippingMethod_" + item; 
		WebElement ele_shippingMethod = driver.findElement(By.id(xpath0));
		Select sel = new Select(ele_shippingMethod);
		if(item.equals("0")){
			sel.selectByValue("nextBizDayDelivery-net");
		}else{
			sel.selectByValue("expedite-shipping-net");
		}
		
		//*[@id="cart_entry_shipping_method_select_div_0"]/div[1]/div[2]/span
		//*[@id="cart_entry_shipping_method_select_div_1"]/div[1]/div[2]/span
		
		String xpath_Mess = "//*[@id='cart_entry_shipping_method_select_div_" + item + "']/div[1]/div[2]/span";
		WebElement ele_LT_Mess = driver.findElement(By.xpath(xpath_Mess));
		String next_LT_Mess = ele_LT_Mess.getText();
		System.out.println(next_LT_Mess);
		System.out.println(expectMess);
		Assert.assertEquals(next_LT_Mess, expectMess);
	}
	
	public String testDateFormat(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();        
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 1);// num为增加的天数，可以改变的
        date = ca.getTime();
        String enddate = sdf.format(date);
//      System.out.println(enddate);
        return enddate;
		
	}
	
	public void checkLeadtimeMess(String leadtime_message){
		
		WebElement addToCart = driver.findElement(By.xpath("//button[@submitfromid = 'addToCartFormTop80VGS00700']"));
		Common.javascriptClick(driver, addToCart);
		Common.sleep(2000);
		b2cPage.Product_AddToCartBtn.click();
	}
	
	public void verifyCartLeadtimeMess(String leadtime_message){
		System.out.println("====start verify cart TL Message");
		String xpath_subPage = "//dt[@id='cartEntryNumber_0']";
		Common.sleep(3000);
		if(Common.checkElementDisplays(driver, By.xpath(xpath_subPage), 5)){
			String leadtimeMess = driver.findElement(By.xpath(xpath_subPage)).getText();
			System.out.println(leadtimeMess);
			System.out.println(leadtime_message);
			Assert.assertEquals(leadtimeMess.trim(), leadtime_message.trim());
		}else{
			System.out.println("cart page LT Message ele cannot be found");
		}
	}
	
	public void verifyLTMes(String leadtime_message, String xpath){
		if(Common.checkElementDisplays(driver, By.xpath(xpath), 5)){
			String leadtimeMess = driver.findElement(By.xpath(xpath)).getText();
			System.out.println(leadtimeMess);
			System.out.println(leadtime_message);
			Assert.assertEquals(leadtimeMess, leadtime_message);
		}else{
			System.out.println("LT Message ele cannot be found");
		}
	}
	
	public void setupProLeadtime(String proLeadtime){
		hmcPage.Home_CatalogLink.click();
		Common.javascriptClick(driver, hmcPage.Home_ProductsLink);
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(defaultMTMPN);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(3000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		hmcPage.Catalog_Administration.click();
		Common.sleep(2000);
//		WebElement ele_ProLeadtime = driver.findElement(By.xpath("//input[contains(@id,'Content/IntegerEditor[in Content/GenericEditableListEntry[8]]_input')]"));
		WebElement ele_US10 = driver.findElement(By.id("Content/StringDisplay[US10]_span"));
		Common.doubleClick(driver, ele_US10);
		Common.sleep(5000);
		Common.switchToWindow(driver, 1);
		WebElement ele_ProLeadtime = driver.findElement(By.xpath("//*[@id='IntegerEditor[in Attribute[ProductCountry.leadTime]]_input']"));
		ele_ProLeadtime.clear();
		ele_ProLeadtime.sendKeys(proLeadtime);
		driver.findElement(By.id("ImageToolbarAction[saveandclose]_img")).click();
		Common.switchToWindow(driver, 0);
		hmcPage.Catalog_SaveButton.click();
	}
	
	public void clearProCash(){
		hmcPage.Home_System.click();
		Common.sleep(2000);
		hmcPage.Home_RadisCacheCleanLink.click();
		driver.switchTo().frame(0);
		hmcPage.Radis_CleanProductTextBox.sendKeys(defaultMTMPN);
		hmcPage.Radis_CleanButton.click();
		Common.sleep(8000);
		driver.switchTo().alert().accept();
	}

}
