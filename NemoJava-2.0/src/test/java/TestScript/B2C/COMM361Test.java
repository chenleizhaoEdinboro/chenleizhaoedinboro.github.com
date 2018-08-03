package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class COMM361Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
//	private String subproNUM = "SQG1S0B013333";
	private String subproNUM = "RR00000003";
	private String mtmproNUM;
	private int price_SUB;;
	private String updateQuality = "3";
	private Actions act;

	public COMM361Test(String store){
		this.Store = store;
		this.testName = "COMM-361";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM361(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			act = new Actions(driver);
			
			//step~1
			//LOG IN SMB
			driver.get(testData.B2C.getloginPageUrl());
			smbLogin("zhengpx1@lenovo.com",testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(1, "Add subscription item to cart.", Store, testName);
			driver.get(driver.getCurrentUrl() + "/cart");
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			//add sub
			B2CCommon.addPartNumberToCart(b2cPage, subproNUM);
			
			//step~2
			Dailylog.logInfoDB(2, "Update quantity to 3.", Store, testName);
			updateQuatity(b2cPage.cartPage_Quantity, updateQuality);
			
			//get price to distinguish which order contains SUB in thankyou page
			price_SUB = (int)getPrice(0);
			
			//step~3
			Dailylog.logInfoDB(3, "Proceed to Summary page and place order.", Store, testName);
			checkoutOrder();
			String orderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			//step~4
			Dailylog.logInfoDB(4, "check the value of zPayload", Store, testName);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open()");
			Common.switchToWindow(driver, 1);
			
//			String orderNumber = "4291557393";

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(orderNumber);
			hmcPage.Home_Order_OrderSearch.click();
			Thread.sleep(5000);
			
			hmcPage.Home_Order_OrderDetail.click();
			// page1.OrderReload.click();
			Thread.sleep(5000);
			hmcPage.Home_Order_OrderAdmin.click();
			Thread.sleep(5000);
			
			By zPayload = By.xpath("//*[contains(@id,'Content/GenericReferenceEditor[in Content/Attribute[Order.zPayload]')]");
			Common.scrollToElement(driver, driver.findElement(zPayload));
			Common.rightClick(driver, driver.findElement(zPayload));
			Common.sleep(2000);
			hmcPage.products_PB_editInNewWindow.click();
			Common.switchToWindow(driver, 1);
			String valuePayload =  driver.findElement(By.id("StringEditor[in Attribute[NemoPayload.zPayloadContent]]_input")).getAttribute("value");
			System.out.println(valuePayload);
			Assert.assertTrue(valuePayload.contains(subproNUM), "NO subproNUM");
			Assert.assertTrue(valuePayload.contains("\"quantity\":\"" + updateQuality ), "NO QUANTITY");
			Assert.assertTrue(valuePayload.contains("Individual"), "NO Individual");
			Assert.assertTrue(valuePayload.contains("\"payment_method\":\"CreditCard\""), "NO payment_method");	
			Assert.assertTrue(valuePayload.contains(Integer.toString(price_SUB)));				
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
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

		if (Common.isElementExist(driver, By.xpath("//label[contains(@class,'redesign-term-check')]"))) {
			driver.findElement(By.xpath("//label[contains(@class,'redesign-term-check')]")).click();
		} else {
			driver.findElement(By.xpath("//*[@id='Terms1']")).click();
		}

		Common.sleep(5000);
		driver.findElement(By.xpath("//*[@id='orderSummaryReview_placeOrder']")).click();
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
		Price = Price.replaceAll("/yr", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
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
	
	public void closePromotion(WebDriver driver, PartSalesPage PSPage) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Promotion)).click().perform();
		}
	}
	

}
