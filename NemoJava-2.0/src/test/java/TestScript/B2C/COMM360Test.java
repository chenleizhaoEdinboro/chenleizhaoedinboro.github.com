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

public class COMM360Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
	private String subproNUM = "RR00000003";
	private String dcgproNUM = "4N40A33715";
	private String mtmproNUM;
	private boolean isDisplayed;
	private float price_PCG;
	private float price_SUB;;
	private float price_DCG;;
	private String updateQuality = "3";
	private Actions act;
	private String SMBUser = "zhengpx1@lenovo.com";

	public COMM360Test(String store){
		this.Store = store;
		this.testName = "COMM-360";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM360(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			act = new Actions(driver);
			
			//step~1,2,3,4,5
			//LOG IN SMB
			driver.get(testData.B2C.getloginPageUrl());
			smbLogin(SMBUser,testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(6, "Add a product with subscription item to cart.", Store, testName);
			driver.get(driver.getCurrentUrl() + "/cart");
			B2CCommon.clearTheCart(driver, b2cPage, testData);
//			addCTO_SUB();
			//add PCG 
			addMTMQuick();
	
			Dailylog.logInfoDB(1, "Add multiple subscription products to cart.", Store, testName);
			Common.sleep(2000);
			//add sub
			B2CCommon.addPartNumberToCart(b2cPage, subproNUM);
			
			Dailylog.logInfoDB(4, "Add multiple DCG products to the shopping cart.", Store, testName);
			Dailylog.logInfoDB(5, "Update quantity to not less than 2.", Store, testName);
			//add DCG
			B2CCommon.addPartNumberToCart(b2cPage, dcgproNUM);

			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update2);
			b2cPage.cartPage_Quantity2.clear();
			b2cPage.cartPage_Quantity2.sendKeys(updateQuality);
			updateQuatity(b2cPage.cartPage_Quantity, updateQuality);
			updateQuantityDCG(updateQuality);
			
			//get price to distinguish which order contains SUB in thankyou page
			price_PCG = getPrice(0);
			price_SUB = getPrice(1);
			price_DCG = getPrice(2);
			
			//step~6
			Dailylog.logInfoDB(6, "Proceed to Summary page and place order.", Store, testName);
			checkoutOrder();
			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			String Order_PCG;
			String Order_DCG;
			String ThankyouDCGItemName;
			if(price_PCG + price_SUB < price_DCG){
				Order_PCG = OrderNumber.split("/")[0].trim();
				Order_DCG = OrderNumber.split("/")[1].trim();
				ThankyouDCGItemName = driver.findElement(By.xpath("//div[@class='order-summary']/div[7]//span")).getText().trim();
			}else{
				Order_PCG = OrderNumber.split("/")[1].trim();
				Order_DCG = OrderNumber.split("/")[0].trim();
				ThankyouDCGItemName = driver.findElement(By.xpath("//div[@class='order-summary']/div[4]//span")).getText().trim();
			}
			System.out.println("PCG and sub order number is:" + Order_PCG);
			System.out.println("DCG order number is:" + Order_DCG);
			
			//step~7
			Dailylog.logInfoDB(7, "check content of order XML in HMC", Store, testName);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open()");
			Common.switchToWindow(driver, 1);
			
//			String Order_PCG = "4291559812";
//			String Order_DCG = "4291559813";
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			//verify PCG exist in PCG order xml, DCG does not exist in PCG order
//			mtmproNUM = "80Y70063US";
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			String XMLContentDCG = HMCOrderCheck(driver, hmcPage, Order_DCG);
			Assert.assertTrue(XMLContentDCG.contains(dcgproNUM), "DCG display in order XML");
			Assert.assertTrue(!XMLContentDCG.contains(subproNUM), "SUB does not display in order XML");
			
			driver.findElement(By.xpath("//*[contains(@id,'[organizersearch][Order]_togglearrow')]")).click();
			Common.sleep(2000);
			String XMLContentPCG = HMCOrderCheck(driver, hmcPage, Order_PCG);
			Assert.assertTrue(XMLContentPCG.contains(mtmproNUM), "PCG does display in order XML");
			Assert.assertTrue(!XMLContentPCG.contains(subproNUM), "SUB does not display in order XML");
			
			//step~8
			Dailylog.logInfoDB(8, "check the value of zPayload", Store, testName);
			By zPayload = By.xpath("//*[contains(@id,'Content/GenericReferenceEditor[in Content/Attribute[Order.zPayload]')]");
			Common.scrollToElement(driver, driver.findElement(zPayload));
			Common.rightClick(driver, driver.findElement(zPayload));
			Common.sleep(2000);
			hmcPage.products_PB_editInNewWindow.click();
			Common.switchToWindow(driver, 2);
			String valuePayload =  driver.findElement(By.id("StringEditor[in Attribute[NemoPayload.zPayloadContent]]_input")).getAttribute("value");
			System.out.println(valuePayload);
			Assert.assertTrue(valuePayload.contains(subproNUM), "NO subproNUM");
			Assert.assertTrue(valuePayload.contains("\"quantity\":\"" + updateQuality ), "NO QUANTITY");
			Assert.assertTrue(valuePayload.contains("Individual"), "NO Individual");
			Assert.assertTrue(valuePayload.contains("\"payment_method\":\"CreditCard\""), "NO payment_method");				
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
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
//		act.sendKeys(Keys.PAGE_DOWN).perform();
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
		Common.sleep(3000);
//		driver.findElement(By.id("repId")).clear();
//		driver.findElement(By.id("repId")).sendKeys("1234567890");
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
		Price = Price.replaceAll("/yr", "");

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
