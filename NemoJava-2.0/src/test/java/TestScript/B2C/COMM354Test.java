package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestData.TestData;
import TestScript.SuperTestClass;

public class COMM354Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
//	private String subproNUM = "SQG1S0B013333";
	private String subproNUM = "RR00000003";
	private String dcgproNUM = "4N40A33715";
	private String mtmproNUM;
	private boolean isDisplayed;
	private float price_PCG;
	private float price_SUB;;
	private float price_DCG;;
	private Actions act;
	private String Account = "zhengpx1@lenovo.com";
	private By smbLoginButton;
	private String inputFirstName = "AutoFirstNameqaz";
	private String cardNumber = "4012888888881881";
	private int cartListAmount;

	public COMM354Test(String store){
		this.Store = store;
		this.testName = "COMM-354";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM354(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			act = new Actions(driver);
			
			//step~1
			Dailylog.logInfoDB(1, "Go to website, and login.", Store, testName);
			//LOG IN SMB
			driver.get(testData.B2C.getloginPageUrl());
			smbLogin(Account,testData.B2C.getLoginPassword());
//			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			//Account: has non-subscription saved payment, no subscriptoin saved payment judgment manually
			
			//step~2,3
			Dailylog.logInfoDB(2, "Add a product with subscription item to cart.", Store, testName);
			//get address and payment history
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			driver.findElement(By.linkText("View my subscriptions")).click();
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			//add sub
			B2CCommon.addPartNumberToCart(b2cPage, subproNUM);
			Dailylog.logInfoDB(3, "Add DCG and PCG products to cart.", Store, testName);
			//add PCG 
			addMTMQuick();
			
			//step~4 
			//Switch new UI and old UI
			
			//step~5
			Dailylog.logInfoDB(5, "Click Checkout button on shopping cart page.", Store, testName);
			Common.sleep(3000);
			WebElement ele_checkout = driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"));
			try {
				Common.scrollToElement(driver, ele_checkout);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ele_checkout.click();
			//fill shipping
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			
			//step~6
			Dailylog.logInfoDB(6, "Click Checkout button on shopping cart page.", Store, testName);
			Common.sleep(2000);
			if(b2cPage.Shipping_FirstNameTextBox.getAttribute("value").isEmpty()){
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			
//			b2cPage.Shipping_FirstNameTextBox.clear();
//			b2cPage.Shipping_FirstNameTextBox.sendKeys(inputFirstName);
			Common.scrollToElement(driver, b2cPage.Shipping_SaveAddressToBook);
			Common.javascriptClick(driver, b2cPage.Shipping_SaveAddressToBook);
		
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.handleAddressVerify(driver, b2cPage);
			
			//step~7
			Dailylog.logInfoDB(7, "Check reminder message", Store, testName);
			Common.sleep(3000);
			By remindMess = By.xpath("//div[contains(@class,'subscriptionInfoMessageNewCheckout')]");
			Assert.assertTrue(driver.findElement(remindMess).isDisplayed());
			
			//step~8
			Dailylog.logInfoDB(8, "Click 'View Saved Payment' button", Store, testName);
			b2cPage.payment_viewSavedPayments.click();
			Common.sleep(5000);
			//record card amount 1881&AutoFirstName
			cartListAmount = getCartListAmount();
			By PopRemindMess = By.xpath("//*[@id='cboxLoadedContent']/div[@id='savedPaymentList']/div[3]");
			Assert.assertTrue(driver.findElement(PopRemindMess).isDisplayed(), "remind message is diplay");
			System.out.println("ewfefdf");
			Assert.assertFalse(driver.findElements(By.xpath("//button[text()='Use this payment info']")).isEmpty(), "USE THIS PAYMENT INFO DISPLAY");
			Assert.assertFalse(driver.findElements(By.xpath("//button[text()='Remove']")).isEmpty(), "REMOVE DISPLAY");
			

			//step~9
			Dailylog.logInfoDB(9, "Close 'Select Payment Details' popup window.", Store, testName);
			b2cPage.Tele_DPLReport_Close.click();
			
			//step~10
			Dailylog.logInfoDB(10, "Close 'Select Payment Details' popup window.", Store, testName);
			//fill payment info
			try {
				fillDefaultPaymentInfo(b2cPage, testData, cardNumber);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//step~11
			Dailylog.logInfoDB(11, "Click Continue button.", Store, testName);
			b2cPage.Payment_ContinueButton.click();
			
			//step~12
			Dailylog.logInfoDB(12, "Place order.", Store, testName);
			Common.sleep(2000);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			if (Common
					.isElementExist(
							driver,
							By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))) {
				driver.findElement(
						By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))
						.click();
			} else {
				Common.javascriptClick(driver,
						b2cPage.OrderSummary_AcceptTermsCheckBox);
			}
			driver.findElement(By.id("repId")).clear();
			driver.findElement(By.id("repId")).sendKeys("1234567890");
			Common.javascriptClick(driver, b2cPage.OrderSummary_PlaceOrderButton);
			
			//step~13
			Dailylog.logInfoDB(13, "Go to My Account-> my subscription-> view my subscription", Store, testName);
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			closePromotion(driver,b2cPage);
			driver.findElement(By.linkText("View my subscriptions")).click();
			
			
			
//			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
					
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	
	public int getCartListAmount(){
		String cartNumberLast4 = cardNumber.substring(cardNumber.length()-3, cardNumber.length());
		By by_cardList = By.xpath("//div[@class='paymentItemInfoMain']/div[contains(text(),'AutoFirstName') and "
				+ "contains(text(), " + cartNumberLast4 + ")]");
		List<WebElement> cardList = driver.findElements(by_cardList);
		return cardList.size();
	}
	
	public static void fillDefaultPaymentInfo(B2CPage b2cPage, TestData testData, String cartNumber) throws InterruptedException {
		Thread.sleep(2000);
		Common.javascriptClick(b2cPage.PageDriver, b2cPage.Payment_CreditCardRadioButton);
		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		Common.waitElementVisible(b2cPage.PageDriver, b2cPage.Payment_CardTypeDropdown);
		Select dropdown = new Select(b2cPage.Payment_CardTypeDropdown);
		dropdown.selectByVisibleText("Visa");
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys(cartNumber);

		if (!b2cPage.Payment_CardMonthTextBox.getTagName().toLowerCase().equals("select")) {
			b2cPage.Payment_CardMonthTextBox.clear();
			b2cPage.Payment_CardMonthTextBox.sendKeys("12");
			b2cPage.Payment_CardYearTextBox.clear();
			b2cPage.Payment_CardYearTextBox.sendKeys("20");
		} else {
			// dropdown month and year
			dropdown = new Select(b2cPage.Payment_CardMonthTextBox);
			dropdown.selectByVisibleText("12");
			dropdown = new Select(b2cPage.Payment_CardYearTextBox);
			dropdown.selectByVisibleText("2020");
		}

		b2cPage.Payment_SecurityCodeTextBox.clear();
		b2cPage.Payment_SecurityCodeTextBox.sendKeys("123");
		b2cPage.PageDriver.switchTo().defaultContent();
		Thread.sleep(2000);
		b2cPage.Payment_CardHolderNameTextBox.clear();
		b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
		// TW invoice
		if (Common.isElementExist(b2cPage.PageDriver, By.xpath(".//select[contains(@id,'invoiceTypeTW')]"))) {
			b2cPage.PageDriver
					.findElement(By.xpath(".//select[contains(@id,'invoiceTypeTW')]/option[contains(.,'紙本發票')]"))
					.click();
		}
		// if PO is mandatory
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.payment_purchaseNum, 2)) {
			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");
			Thread.sleep(2000);
			b2cPage.payment_purchaseDate.click();
			Thread.sleep(3000);
			b2cPage.PageDriver.findElement(By.xpath("//td[contains(@class,'ui-datepicker-today')]/a")).click();
		}
	}
	
	public void loginALC(){
			
			driver.findElement(By.xpath("//input[@name='UserID']")).clear();
			driver.findElement(By.xpath("//input[@name='UserID']")).sendKeys(Account);
			
			driver.findElement(By.xpath("//input[@name='Password']")).clear();
			driver.findElement(By.xpath("//input[@name='Password']")).sendKeys(testData.B2C.getLoginPassword());
			
			driver.findElement(By.xpath("//div[contains(@class,'signin')]")).click();
			
		}
	
	public static String HMCOrderCheck(WebDriver driver, HMCPage page1, String OrderNumber){
		page1.Home_Order_OrderID.clear();
		page1.Home_Order_OrderID.sendKeys(OrderNumber);
		page1.Home_Order_OrderSearch.click();
		Common.sleep(5000);
		if (!page1.Home_Order_OrderStatus.getText().contains("Completed")) {
			Common.sleep(5000);
			page1.Home_Order_OrderSearch.click();
		}
		if(!page1.Home_Order_OrderStatus.getText().contains("Completed"))
			Assert.fail("Order status is not completed!");
		page1.Home_Order_OrderDetail.click();
		// page1.OrderReload.click();
		Common.sleep(5000);
		page1.Home_Order_OrderAdmin.click();
		Common.sleep(5000);
		return page1.Orders_OrderXML.getText();
		
	}
	
	public float getPrice(int i){
		By cartNewUI = By.xpath("//form[contains(@action,'emptyCart')]/a");
		boolean cartisNewUI = Common.checkElementDisplays(driver, cartNewUI, 5);
		String price;
		if(cartisNewUI){
			price = b2cPage.Cart_itemPriceList.get(i).getText();
		}else{
			List<WebElement> Cart_itemPriceListOld = driver.findElements(By.xpath("//dl[@class='pricing-info-value']"));
			price = Cart_itemPriceListOld.get(i).getText();
		}
		return GetPriceValue(price);
	}
	
	public String itemName(String i){
		WebElement ele_PCGSUB = null;
		if(price_PCG + price_SUB < price_DCG){
			ele_PCGSUB = driver.findElement(By.xpath("//div[@class='order-summary']/div[4]/div[" + i + "]//span"));
		}else{
			ele_PCGSUB = driver.findElement(By.xpath("//div[@class='order-summary']/div[7]/div[" + i + "]//span"));
		}
		return ele_PCGSUB.getText().trim();
	}
	
	public void addMTMQuick(){
		mtmproNUM = testData.B2C.getDefaultMTMPN();
		addPartNumberToCart(b2cPage, mtmproNUM);
		Common.sleep(3000);
		By noStockMess = By.xpath("//*[contains(text(),'No Stock for the Product') or contains(text(),'is invalid')]");
		if(Common.checkElementDisplays(driver, noStockMess, 5)){
			// price is lower is better
			mtmproNUM = "80Y70063US";
			addPartNumberToCart(b2cPage, mtmproNUM);
		}
	}
	
	public void checkoutOrder(){
		Common.sleep(3000);
		WebElement ele_checkout = driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"));
		try {
			Common.scrollToElement(driver, ele_checkout);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ele_checkout.click();
		//fill shipping
		Common.sleep(2000);
		if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
			b2cPage.Checkout_StartCheckoutButton.click();
		}
		Common.sleep(2000);
		if(b2cPage.Shipping_FirstNameTextBox.getAttribute("value").isEmpty()){
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
		}
	
		act.sendKeys(Keys.PAGE_UP).perform();
		b2cPage.Shipping_ContinueButton.click();
		//fill payment info
		B2CCommon.handleAddressVerify(driver,b2cPage);
		try {
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b2cPage.Payment_ContinueButton.click();
		Common.sleep(2000);
		act.sendKeys(Keys.PAGE_DOWN).perform();
		if (Common
				.isElementExist(
						driver,
						By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))) {
			driver.findElement(
					By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))
					.click();
		} else {
			Common.javascriptClick(driver,
					b2cPage.OrderSummary_AcceptTermsCheckBox);
		}
		driver.findElement(By.id("repId")).clear();
		driver.findElement(By.id("repId")).sendKeys("1234567890");
		b2cPage.OrderSummary_PlaceOrderButton.click();
	}
	

	public void smbLogin(String user, String password){
		Common.sleep(2000);
		By loginPage = By.linkText("log in page");
		if(Common.checkElementDisplays(driver, loginPage, 5)){
			driver.findElement(loginPage).click();
		}
		Common.sleep(3000);
		driver.findElement(By.xpath("//*[@id='smb-login-button']")).click();
		Common.sleep(3000);
		
		Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.email.id']")), user);
		Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.password']")), password);

		driver.findElement(By.xpath("//*[@id='nemoLoginForm']/div/button[@type='submit']")).click();
		
		Common.sleep(6000);
	}
	
	public static float GetPriceValue(String Price) {

		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\￥", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("₹", "");
		Price = Price.replaceAll("/yer", "");
		Price = Price.replaceAll("/mon", "");
		Price = Price.replaceAll("/syer", "");
		Price = Price.replaceAll("/que", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}
	
	public void updateQuantityDCG(String quantity){
		WebElement cartPage_Quantity3 = driver.findElement(By.xpath("//input[@id='quantity1']"));
		WebElement cartPage_Quantity_update3 = driver.findElement(By.xpath("//input[@id='QuantityProduct_1']"));
		cartPage_Quantity3.clear();
		cartPage_Quantity3.sendKeys(quantity);
		Common.javascriptClick(driver, cartPage_Quantity_update3);
	}
	
	public void updateQuatity(WebElement cartPage_Quantity, String cartQuantity){
		Common.sleep(2000);
		b2cPage.cartPage_Quantity.clear();
		b2cPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		Common.sleep(1000);
	}

	public void checkoutpopup(String morethan5){
		By popup = By.xpath("//div[@class='popup-itemlimit']");
		b2cPage.cartPage_Quantity.clear();
		b2cPage.cartPage_Quantity.sendKeys(morethan5);
		Common.sleep(2000);
		Assert.assertTrue(Common.checkElementDisplays(driver, popup, 5));
	}

	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
	}
	
	public void checkUpdateQuatity(String cartQuantity){
		b2cPage.cartPage_Quantity.clear();
		b2cPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		Common.sleep(3000);
		By quantity_Message1 = By.xpath("// div[contains(text(),'Product quantity has been updated.')]");
		By quantity_Message5 = By.xpath("// div[contains(text(),'has been reduced to 4') or contains(@text,'Unfortunately')]");
		switch(cartQuantity){
			case "3":
				if(!Common.checkElementDisplays(driver, quantity_Message1, 5)){
					b2cPage.cartPage_Quantity.clear();
					b2cPage.cartPage_Quantity.sendKeys("2");
					Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
				}
				Common.sleep(2000);
				isDisplayed = Common.checkElementDisplays(driver, quantity_Message1, 5);
				if (!isDisplayed)
					Assert.fail("mesage is not displayed:Product quantity has been updated. ");
				else{
					System.out.println("message is displayed:Product quantity has been updated.");
				}
				break;
			
			case "5":
				Common.sleep(3000);
				isDisplayed = Common.checkElementDisplays(driver, quantity_Message5, 5);
				By stockMess1 = By.xpath("//div[contains(@class,'alert negative')][2]");
				By stockMess2 = By.xpath("//div[contains(@class,'alert neutral')]");

				if(Common.checkElementDisplays(driver, stockMess1, 3)||Common.checkElementDisplays(driver, stockMess2, 3)){
					if (!isDisplayed){
						Assert.fail("mesage is not displayed:");
						Assert.assertEquals(b2cPage.cartPage_Quantity.getText().trim(), "4");
					}	
					else{
						System.out.println("message is displayed:");
					}
				}
				
				break;
		}
		
	}
	
	public void closePromotion(WebDriver driver, PartSalesPage PSPage) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Promotion)).click().perform();
		}
	}
	

}
