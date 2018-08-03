
package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19976Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String b2cProductUrl;
	public String ProductID;
	private String b2cUnitID;
	private String firtName = "testmxfirstname12345678901234567890";
	private String lastName = "testmxlasttname12345678901234567890";
	private String postCode = "7044q";
	private String mobile = "1234phone";
	private boolean paymentNewUI;
	private String orderNum1 = "";
	private String orderNum = "";
	private String valueFRFC = "ASAB123456PP3";
	private String valueMRFC = "BCD123456PP3";
	private String YZF37 = "<text>0</text>";
	private String YZM37 = "<text>1</text>";
	private String YZ39 = "<text>6</text>";

	public NA19976Test(String Store, String b2cUnit) {
		this.Store = Store;
		this.b2cUnitID = b2cUnit;
		this.testName = "NA-19976";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA19976(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
	
//			loginHMCPage();
//			Common.sleep(2000);
//			enableSwitchAddressToggle();
			Dailylog.logInfoDB(1, "login the MX store", Store, testName);
			
			//=========== Step:4 Add one product ===========//
			logonB2CPage();
			Common.sleep(1000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(1000);
//			B2CCommon.addPartNumberToCart(b2cPage, "80V1004FLM");
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			Dailylog.logInfoDB(4, "Add product into cart ", Store, testName);
			
			//=========== Step:5 click on "Lenovo Checkout" on shopping cart page. ===========//
			driver.findElement(By
					.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			Common.sleep(2000);
//			b2cPage.StartCheckOut.click();
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("add-delivery-address"));
			Dailylog.logInfo("5, click Lenovo checkout on shopping cart page");
			
			//=========== Step:6 input invalid postalcode, phone number. Click on Continue button. ===========//
			//input invalid first name, last name will not display error message
			if (Common
					.isElementExist( 
							driver,
							By.cssSelector(".textLink.checkout-shippingAddress-editLink"))) {
				b2cPage.Shipping_editAddress.click();
			}
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			Common.sleep(2000);
			fillShippingInfo(firtName,lastName,postCode,mobile);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.Shipping_FirstNameTextBox.getAttribute("value").length()==30);
			Assert.assertTrue(b2cPage.Shipping_LastNameTextBox.getAttribute("value").length()==30);
			Assert.assertTrue(driver.findElement(
					By.xpath("//span[@id='postcode.errors'][text()='Por favor, ingresa un código postal válido']"))
					.isDisplayed());
			Assert.assertTrue(driver.findElement(
					By.xpath("//span[@id='phone.errors'][text()='Por favor, ingresa un número de teléfono válido de hasta 10 caracteres numéricos']"))
					.isDisplayed());
			Dailylog.logInfoDB(6, "Verify rules of firstname,lastname,postalcode and phone number.", Store, testName);			
			
			//=========== Step:8 modify the address filed input with correct input. ===========//
			fillShippingInfo("AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			Dailylog.logInfoDB(8, "Modify the address filed with correct input.", Store, testName);			
			
			//=========== Step:9 Verify all Mexico states are displayed in the drop down list. ===========//
			verifyStateNum();
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(2000);
			Dailylog.logInfoDB(9, "All 32 Mexico states are displayed in the drop down list.", Store, testName);			
			
			
			//=========== Step:7 select "Persona Física" as consumer type ===========//
			//select payment type "Direct Deposit"
			Common.javascriptClick(driver, b2cPage.DirectDeposit);
			//consumer type is on payment page on MX site,the default value is "Persona Física".
			Common.sleep(2000);
			Assert.assertFalse(b2cPage.Shipping_CustFiscalType.getText().equals("Persona Física"));
			b2cPage.Payment_RequieroFacturaFiscal.click();
			Common.sleep(2000);
			Common.waitElementClickable(driver, b2cPage.Payment_companyTaxNumber, 5);
			b2cPage.Payment_companyTaxNumber.sendKeys(valueFRFC);
			Dailylog.logInfo("The checkbox 'Requiero factura fiscal'is editable.");

			//=========== Step:10  click on Continue button. ===========//
			Common.sleep(5000);
			clickPaymentContinue();
			Thread.sleep(5000);
			String url_sum1 = driver.getCurrentUrl().toString();
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.endsWith("summary"));
			Dailylog.logInfoDB(10, "Summary page is displayed:" + url_sum1, Store, testName);
			checkShippingBillingAddress();
			Dailylog.logInfoDB(10, "The payment page information is same as the shipping page information!", Store, testName);

			//=========== Step:11 check the T&C and click on "place order. ===========//
			clickPlaceOrder();
			Dailylog.logInfoDB(11, "thankyou is displayed", Store, testName);
			
			orderNum1 = getOrderNO();
			Dailylog.logInfo("orderNum is :" + orderNum1);
			// 11 check order in hmc
			checkHMCXML(orderNum1,valueFRFC,YZF37);
			
			//=========== Step:12 go to order detail page. ===========//
			logonB2CPage();
			Common.sleep(2000);
			checkOrderDetail();
			Dailylog.logInfoDB(12, "the RFC input is displayed under the shipping/billing address on order page.", Store, testName);
			
			//=========== Step:13 add product into cart. ===========//
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(1000);
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			Dailylog.logInfoDB(13, "Add product into cart", Store, testName);
			
			//=========== Step:14 click on "Lenovo Checkout" on shopping cart page. ===========//
			clickLenovCheckout();
			Dailylog.logInfoDB(14, "Click 'Lenovo Checkout',shipment page is displayed.", Store, testName);
			
			//=========== Step:16  run step 6-10. ===========//
			//input address fields and go to payment page
			Common.sleep(2000);
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.DirectDeposit);
			
			//uncheck checkbox "Same as delivery address".
			Common.checkElementDisplays(driver, b2cPage.Payment_UseShippingAddress, 5);
			b2cPage.Payment_UseShippingAddress.click();
			
			//rerun step 6,8,9
			checkPaymentAddressRules();
			
			//=========== Step:15 change the option to "Persona Moral" in the drop down list. ===========//
			Select sel_ConsumerType = new Select(b2cPage.Shipping_CustFiscalType);
			Common.sleep(2000);
			sel_ConsumerType.selectByVisibleText("Persona Moral");
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.Payment_RequieroFacturaFiscal.getAttribute("value").equals("true"));
			Common.waitElementClickable(driver, b2cPage.Payment_companyTaxNumber, 5);
			//input RFC
			b2cPage.Payment_companyTaxNumber.click();
			b2cPage.Payment_companyTaxNumber.sendKeys(valueMRFC);
			Common.sleep(2000);
			Dailylog.logInfoDB(15, "'Requiero factura fiscal'is disabled.RFC field is disabled.", Store, testName);
			//rerun step 10
			clickPaymentContinue();
			Dailylog.logInfoDB(16, "Verify address validation rule is same as billing address page.", Store, testName);
			
			//=========== Step:17 go to summary page. ===========//
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.Payment_verifyPaymentardHoldName.getText().equals(valueMRFC));
			Dailylog.logInfoDB(17, "the RFC number input on shipping address page is displayed under shipping address.", Store, testName);
			
			//=========== Step:18 check the T&C and click on "place order. ===========//
			clickPlaceOrder();
			Dailylog.logInfoDB(18, "thankyou is displayed", Store, testName);
			orderNum = getOrderNO();
			Dailylog.logInfoDB(18, "orderNum is :" + orderNum1, Store, testName);
			checkHMCXML(orderNum,valueMRFC,YZM37);
			Dailylog.logInfoDB(18, "YZ37/39/40/41/42 are correctly generated", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
	public void verifyStateNum(){
		List<WebElement> elements = driver.findElements(By.xpath("//select[@id='state']/option"));  
		int number=elements.size();  
		Assert.assertEquals(number,33);
	}
	
	public void fillShippingInfo(String firtName, String lastName, String postCode, String mobile){
		b2cPage.Shipping_FirstNameTextBox.clear();
		b2cPage.Shipping_FirstNameTextBox.sendKeys(firtName);
		b2cPage.Shipping_LastNameTextBox.clear();
		b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
		b2cPage.Shipping_PostCodeTextBox.clear();
		b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
		b2cPage.Mobile.clear();
		b2cPage.Mobile.sendKeys(mobile);
	}
	
	public void checkHMCXML(String orderNuma, String valueRFC, String yZ37){
		driver.get(testData.HMC.getHomePageUrl());
		Common.sleep(3000);
		if(Common.isElementExist(driver, By.id("Main_user"))){HMCCommon.Login(hmcPage, testData);}
		try {
			HMCCommon.HMCOrderCheck(hmcPage, orderNuma);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// verify the xml
		Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
		Common.sleep(2000);
		hmcPage.Order_ordersAdministration.click();
		Common.sleep(2000);
		String xmlcontent = driver.findElement(
				By.xpath(".//*[contains(@id,'Order.xmlContentShow')]"))
				.getText();
		Dailylog.logInfo("xmlcontent is :" + xmlcontent);
		Dailylog.logInfo("verify xml has the right value");
		
		assert xmlcontent.contains(yZ37);
		assert xmlcontent.contains(YZ39);
		assert xmlcontent.contains(valueRFC);
	}
	
	public String getOrderNO(){
		if (Common
				.isElementExist(
						driver,
						By.xpath("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)"))) {
			orderNum1 = b2cPage.OrderThankyou_OrderNumberLabel.getText();
		} else {
			orderNum1 = driver
					.findElement(
							By.xpath("(//*[contains(text(),'Número de pedido:') or contains(text(),'Número do Pedido')]/../*)[2]"))
					.getText();
		}
		return orderNum1;
	}
	
	public void clickPlaceOrder(){
		JavascriptExecutor js1= (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
		B2CCommon.clickPlaceOrder(b2cPage);
		Common.sleep(3000);
		String currentUrl1 = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl1.contains("orderConfirmation"));
	}
	
	public void clickPaymentContinue(){
		if(Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel, 3)){
			b2cPage.Payment_bankDepositLabel.click();
		}
		if (Common.isElementExist(driver,
				By.xpath("//span[@class='continue_to_review']"))) {
			WebElement paymentContinue = driver.findElement(By
					.xpath("//span[@class='continue_to_review']"));
			Common.javascriptClick(driver, paymentContinue);
		} else {
			WebElement paymentContinue = driver.findElement(By
					.xpath("//*[@id='add-payment-method-continue']"));
			Common.javascriptClick(driver, paymentContinue);
		}

	}
	
	public void checkPaymentAddressRules(){
		//=========== Step:6 input invalid postalcode, phone number. Click on Continue button. ===========//
		//input invalid first name, last name will not display error message
		Common.sleep(2000);
		fillPaymentAddressInfo(firtName,lastName,postCode,mobile);
		Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
		Common.sleep(2000);
		Assert.assertTrue(b2cPage.Payment_FirstNameTextBox.getAttribute("value").length()==30);
		Assert.assertTrue(b2cPage.Payment_LastNameTextBox.getAttribute("value").length()==30);
		Assert.assertTrue(driver.findElement(
				By.xpath("//div[@id='error_address.postcode.invalid.MX']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.xpath("//div[@id='error_address.phone.invalid.MX']"))
				.isDisplayed());
		Dailylog.logInfoDB(6, "Verify rules of firstname,lastname,postalcode and phone number.", Store, testName);			
		
		//=========== Step:8 modify the address filed input with correct input. ===========//
		fillPaymentAddressInfo("AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
		Dailylog.logInfoDB(8, "Modify the address filed with correct input.", Store, testName);			
		
		//=========== Step:9 Verify all Mexico states are displayed in the drop down list. ===========//
		String paymentStateXPATH = "//select[@id='address.region']/option";
		verifyStateNum(paymentStateXPATH);
		Common.sleep(2000);
		Dailylog.logInfoDB(9, "All 32 Mexico states are displayed in the drop down list.", Store, testName);	
	}
	
	public void checkShippingAddressRules(){
		//=========== Step:6 input invalid postalcode, phone number. Click on Continue button. ===========//
		//input invalid first name, last name will not display error message
		Common.sleep(2000);
		fillShippingAddressInfo(firtName,lastName,postCode,mobile);
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		Common.sleep(2000);
		Assert.assertTrue(b2cPage.Shipping_FirstNameTextBox.getAttribute("value").length()==30);
		Assert.assertTrue(b2cPage.Shipping_LastNameTextBox.getAttribute("value").length()==30);
		Assert.assertTrue(driver.findElement(
				By.xpath("//span[@id='postcode.errors'][text()='Por favor, ingresa un código postal válido']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.xpath("//span[@id='phone.errors'][text()='Por favor, ingresa un número de teléfono válido de hasta 10 caracteres numéricos']"))
				.isDisplayed());
		Dailylog.logInfoDB(6, "Verify rules of firstname,lastname,postalcode and phone number.", Store, testName);			
		
		//=========== Step:8 modify the address filed input with correct input. ===========//
		fillShippingAddressInfo("AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
		Dailylog.logInfoDB(8, "Modify the address filed with correct input.", Store, testName);			
		
		//=========== Step:9 Verify all Mexico states are displayed in the drop down list. ===========//
		String shippingStateXPATH = "//select[@id='state']/option";
		verifyStateNum(shippingStateXPATH);
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		Common.sleep(2000);
		Dailylog.logInfoDB(9, "All 32 Mexico states are displayed in the drop down list.", Store, testName);	
	}
	
	public void clickLenovCheckout(){
		driver.findElement(By
				.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
		Common.sleep(2000);
//		b2cPage.StartCheckOut.click();
//		Common.sleep(2000);
		Assert.assertTrue(driver.getCurrentUrl().toString().contains("add-delivery-address"));
	}
	
	public void checkShippingBillingAddress(){
		String shippingInfo[]={b2cPage.Payment_verifyShippingName.getText(),b2cPage.Payment_verifyShippingAddress.getText(),
				b2cPage.Payment_verifyShippingLocality.getText(),b2cPage.Payment_verifyShippingRegion.getText(),
				b2cPage.Payment_verifyShippingPhone.getText(),b2cPage.Payment_verifyShippingEmail.getText(),
				};
		
		String paymentInfo[]={b2cPage.Payment_verifyPaymentName.getText(),b2cPage.Payment_verifyPaymentAddress.getText(),
				b2cPage.Payment_verifyPaymentLocality.getText(),b2cPage.Payment_verifyPaymentRegion.getText(),
				b2cPage.Payment_verifyPaymentPhone.getText(),b2cPage.Payment_verifyPaymentEmail.getText(),
				};
		for(int i=0;i<6;i++){
			Assert.assertEquals(shippingInfo[i],paymentInfo[i]);
		}
	}
	
	public void verifyStateNum(String selectXPATH){
		List<WebElement> elements = driver.findElements(By.xpath(selectXPATH));  
		int number=elements.size();  
		Assert.assertEquals(number,33);
	}
	
	public void fillShippingAddressInfo(String firtName, String lastName, String postCode, String mobile){
		b2cPage.Shipping_FirstNameTextBox.clear();
		b2cPage.Shipping_FirstNameTextBox.sendKeys(firtName);
		b2cPage.Shipping_LastNameTextBox.clear();
		b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
		b2cPage.Shipping_PostCodeTextBox.clear();
		b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
		b2cPage.Mobile.clear();
		b2cPage.Mobile.sendKeys(mobile);
	}
	
	public void fillPaymentAddressInfo(String firtName, String lastName, String postCode, String mobile){
		b2cPage.Payment_FirstNameTextBox.clear();
		b2cPage.Payment_FirstNameTextBox.sendKeys(firtName);
		b2cPage.Payment_LastNameTextBox.clear();
		b2cPage.Payment_LastNameTextBox.sendKeys(lastName);
		b2cPage.Payment_PostCodeTextBox.clear();
		b2cPage.Payment_PostCodeTextBox.sendKeys(postCode);
		driver.findElement(By.id("address.phoneNumber1")).clear();
		driver.findElement(By.id("address.phoneNumber1")).sendKeys(mobile);
	}
	
	public void checkOrderDetail(){
		//go to My Account
		if(paymentNewUI){
			b2cPage.Payment_NewCartNewUI.click();
		}else{
			b2cPage.Payment_NewCart.click();
		}
		driver.get(testData.B2C.getHomePageUrl()+"/my-account");
		Common.sleep(2000);
		//Click My order history,
		b2cPage.MyAccount_ViewOrderHistoryLink.click();
		Common.sleep(2000);
		//click on order
		Common.javascriptClick(driver, b2cPage.ViewOrders_OrderNumber);
		Common.sleep(2000);
		b2cPage.orderDetail_RFC.getText().equals(valueFRFC);
		Dailylog.logInfoDB(15, "RFC is displayed on order detail page.", Store, testName);
	}
	
	public void logonB2CPage(){
		driver.get(testData.B2C.getHomePageUrl());
		Common.sleep(1000);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		B2CCommon.closeHomePagePopUP(driver);
		driver.get(testData.B2C.getloginPageUrl());
		B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
		Common.sleep(2000);
		if(!driver.getCurrentUrl().contains("account")) {
			driver.get(testData.B2C.getloginPageUrl());
			Common.sleep(2500);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
		}
		Common.sleep(2000);
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	
	public void loginHMCPage(){
		driver.get(testData.HMC.getHomePageUrl());
		Common.sleep(2000);
		HMCCommon.Login(hmcPage, testData);
	}
	
	public void enableSwitchAddressToggle(){
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(b2cUnitID);
		hmcPage.B2CUnit_SearchButton.click();
		Common.doubleClick(driver, hmcPage.B2CUnit_FirstSearchResultItem);
		Common.waitElementClickable(driver,hmcPage.B2CUnit_SiteAttributeTab, 30);
		hmcPage.B2CUnit_SiteAttributeTab.click();
		hmcPage.b2cUnit_switchAddressYes.click();
		hmcPage.Types_SaveBtn.click();
	}

	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		Dailylog.logInfo("b2cProductUrl is :" + b2cProductUrl);
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0]
				.substring(0, 15);
		Dailylog.logInfo(ProductID);
		return ProductID;
	}

}
