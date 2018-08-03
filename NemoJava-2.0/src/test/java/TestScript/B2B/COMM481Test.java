package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class COMM481Test extends SuperTestClass {
	B2BPage b2bPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
	private String subproNUM = "SQG1S0B013333";
	private String dcgproNUM = "4N40A33715";
	private String mtmproNUM;
	private String ctoproNUM = "20KHCTO1WWENUS1";
	private boolean isDisplayed;
	private String WarehouseData = "";
	private String pricePCG;
	private String priceSUB;
	private String priceDCG;
	private String priceSUBAdd;
	private float price_PCG;
	private float price_SUB;;
	private float price_DCG;;
	private float price_SUBAdd;;
	private float price_SUBTotal;
	private String ValidMerchant_ID = "LENOVOUS03";
	private String InvalidMerchant_ID = "LENOVOUS031";
	private Actions act;

	public COMM481Test(String store){
		this.Store = store;
		this.testName = "COMM-481";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "", "p2", "b2c"})
	public void COMM481(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			PSPage = new PartSalesPage(driver);
			act = new Actions(driver);
			
			
			//step~1
			Dailylog.logInfoDB(1, "Add multiple subscription products to cart.", Store, testName);
			//LOG IN B2B unit
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getApproverId(), testData.B2B.getDefaultPassword());
			smbLogin(testData.B2C.getLoginID(),testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(6, "Add a product with subscription item to cart.", Store, testName);
			driver.get(driver.getCurrentUrl() + "/cart");
			B2BCommon.clearTheCart(driver, b2bPage);
			B2BCommon.addProduct(driver, b2bPage, subproNUM);
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("2");
			Common.javascriptClick(driver, b2bPage.cartPage_QuantityUpdate);
			
			//step~1
			Dailylog.logInfoDB(2, "Add multiple PCG products to the shopping cart", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, mtmproNUM);
			Common.sleep(2000);
			WebElement cartPage_Quantity2 = driver.findElement(By.xpath(".//*[@id='quantity1']"));
			WebElement cartPage_Quantity_update2 = driver.findElement(By.xpath(".//*[@id='QuantityProduct_1']"));
			cartPage_Quantity2.clear();
			cartPage_Quantity2.sendKeys("2");
			Common.javascriptClick(driver, cartPage_Quantity_update2);
			
			//step~4
			Dailylog.logInfoDB(4, "Add multiple DCG products to the shopping cart.", Store, testName);
			Dailylog.logInfoDB(5, "Update quantity to not less than 2.", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, dcgproNUM);
		
			//step~8
			Dailylog.logInfoDB(8, "(price of subscription products) < (price of PCG products)<(price of DCG products)", Store, testName);
//			if(checkprice()){
//				System.out.println("sub:" + price_SUBTotal + "< PCG:" + price_PCG + "< DCG: " + price_DCG);
//			}else{
//				System.out.println(price_SUBTotal);
//				System.out.println(price_PCG);
//				System.out.println(price_DCG);
//				Assert.fail();
//			}
			
			//step~9
			Dailylog.logInfoDB(9, "manually failed one of the orders", Store, testName);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open()");
			Common.switchToWindow(driver, 1);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			changeValueMerchantID(InvalidMerchant_ID);
			
			//step~10
			Dailylog.logInfoDB(10, "Then Checkout and Place order.", Store, testName);
			Common.switchToWindow(driver, 0);
			checkoutOrder();
			Common.sleep(3000);
			WebElement ele_authorization = driver.findElement(By.xpath("//div[contains(text(),'Authorization has failed')]"));
			Assert.assertTrue(Common.checkElementDisplays(driver, ele_authorization, 5));
			
			//get cart ID
			driver.findElement(By.xpath("//span[text()='SHOW CART ID>']")).click();
			Common.sleep(2000);
			String cartID = driver.findElement(By.xpath("//span[@class='cartId-info']/span")).getText();
			
			//step~11
			Dailylog.logInfoDB(11, "check authorize subscription parts failed.", Store, testName);
			Common.switchToWindow(driver, 1);
			enterPaymentTransactions(cartID); 
			
			//step~12
			Dailylog.logInfoDB(12, "Check the Entries field should have only one record.", Store, testName);
			Common.switchToWindow(driver, 2);
			By entries = By.xpath("//tr[contains(@class,'draggable doubleclick-event')]");
			
			//step~13
			Dailylog.logInfoDB(13, "Right click entries", Store, testName);
			List<WebElement> ele_ertries = driver.findElements(entries);
			Assert.assertEquals(ele_ertries.size(), 3);
			Common.sleep(3000);
			
			//step~14
			Dailylog.logInfoDB(14, "Transactions status should be ACCEPTED. giantType should be SUBSCRIPTION.", Store, testName);
			checkitem(ele_ertries.get(0), "ACCEPTED", "3");
			
			//step~15
			Dailylog.logInfoDB(15, "Transactions status should be ACCEPTED.giantType should be PCG.", Store, testName);
			Common.switchToWindow(driver, 2);
			driver.navigate().refresh();
			List<WebElement> ele_ertries1 = driver.findElements(entries);
			checkitem(ele_ertries1.get(1), "ACCEPTED", "2");
			
			//step~16
			Dailylog.logInfoDB(16, "Transactions status should be REJECTED.giantType should be DCG.", Store, testName);
			Common.switchToWindow(driver, 2);
			driver.navigate().refresh();
			List<WebElement> ele_ertries2 = driver.findElements(entries);
			checkitem(ele_ertries2.get(2), "REJECTED", "1");
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	
	@AfterTest(alwaysRun = true)
	public void rollback(ITestContext ctx){
		try{
			Dailylog.logInfo("rollback!!!");
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			if (Common.checkElementExists(driver, hmcPage.Home_EndSessionLink, 5)) {
				hmcPage.Home_EndSessionLink.click();
			}
			HMCCommon.Login(hmcPage, testData);
			changeValueMerchantID(ValidMerchant_ID);
			
		}catch (Throwable e){
			handleThrowable(e,ctx);
		}
	}
	
	public void checkitem(WebElement ele_ertrie,String status, String statusValue){
		try {
			Common.scrollToElement(driver, ele_ertrie);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Common.doubleClick(driver, ele_ertrie);
		Common.switchToWindow(driver, 3);
		Common.sleep(3000);
		String TransacationsStatus = driver.findElement(By.xpath("//input[contains(@id,'PaymentTransactionEntry.transactionStatus]')]")).getAttribute("value");
		Assert.assertEquals(TransacationsStatus, status);
		WebElement ele_giantType = driver.findElement(By.xpath("//*[contains(@id,'PaymentTransactionEntry.giantType]')]/option[@value='" + statusValue + "']"));
		String giantTypeisSelected  = ele_giantType.getAttribute("selected");
		Assert.assertEquals(giantTypeisSelected, "true");
		driver.findElement(By.xpath("//img[contains(@id,'ImageToolbarAction[saveandclose]')]")).click();
		Common.sleep(3000);
	}
	
//	public void addMTMQuick(){
//		mtmproNUM = testData.B2C.getDefaultMTMPN();
//		addPartNumberToCart(b2bPage, mtmproNUM);
//		Common.sleep(3000);
//		By noStockMess = By.xpath("//*[contains(text(),'No Stock for the Product') or contains(text(),'is invalid')]");
//		if(Common.checkElementDisplays(driver, noStockMess, 5)){
//			// price is lower is better
//			mtmproNUM = "81BD001NUS";
//			addPartNumberToCart(b2bPage, mtmproNUM);
//		}
//	}
	
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
		if(Common.checkElementDisplays(driver, b2bPage.cartPage_LenovoCheckout, 5)){
			b2bPage.cartPage_LenovoCheckout.click();
		}
		Common.sleep(2000);
		if(b2bPage.shippingPage_ShipFName.getAttribute("value").isEmpty()){
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, testData);
		}
		act.sendKeys(Keys.PAGE_UP).perform();
		b2bPage.shippingPage_ContinueToPayment.click();
		//fill payment info
		if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 10)) {
			b2bPage.shippingPage_validateFromOk.click();
		}
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);

		B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		System.out.println("Go to Order page!");

		Common.sleep(5000);
		if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)) {
			try {
				Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
		}
		Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
		Common.javascriptClick(driver, b2bPage.placeOrderPage_PlaceOrder);
		Common.sleep(2000);
	}
	
	public void enterPaymentTransactions(String cartID){
		hmcPage.marketing.click();
		hmcPage.marketing_orderStatistics.click();
		hmcPage.marketing_orderStatistics_carts.click();
		hmcPage.marketing_os_carts_orderNumTxtBox.sendKeys(cartID);
		hmcPage.marketing_os_carts_search.click();
		Common.doubleClick(driver, driver.findElement(By
				.xpath("//*[text()='" + cartID + "']")));
		Common.sleep(3000);
		hmcPage.marketing_os_carts_administration.click();
		Common.sleep(3000);
		WebElement PaymentTransactions = driver.findElement(By.xpath("//tr[contains(@class,'draggable doubleclick-event')]"));
		Common.doubleClick(driver, PaymentTransactions);
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
	
	public void changeValueMerchantID(String Merchant_ID){
		Common.sleep(2000);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_identifier.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		hmcPage.administration.click();
		Common.sleep(3000);
		WebElement dcgCreditCart = 
				driver.findElement(By.id("Content/GenericReferenceEditor[in Content/Attribute[B2CUnit.dcgCreditCardXiPaySetting]]_div"));
		Common.rightClick(driver, dcgCreditCart);
		Common.sleep(2000);
		driver.findElement(By.id("Content/GenericReferenceEditor[in Content/Attribute[B2CUnit.dcgCreditCardXiPaySetting]]_!open_editor_internal_true_label")).click();
		WebElement MerchantID = 
				driver.findElement(By.id("Content/StringEditor[in Content/Attribute[XiPaySetting.merchantID]]_input"));
		MerchantID.clear();
		MerchantID.sendKeys(Merchant_ID);
		hmcPage.Catalog_SaveButton.click();		
	}
	/*
	public boolean checkprice(){
		boolean flag = false;
		By cartNewUI = By.xpath("//form[contains(@action,'emptyCart')]/a");
		boolean cartisNewUI = Common.checkElementDisplays(driver, cartNewUI, 5);
		if(cartisNewUI){
			pricePCG = b2bPage.Cart_itemPriceList.get(0).getText();
			priceSUB = b2bPage.Cart_itemPriceList.get(1).getText();
			priceDCG = b2bPage.Cart_itemPriceList.get(2).getText();
		}else{
			List<WebElement> Cart_itemPriceListOld = driver.findElements(By.xpath("//dl[@class='pricing-info-value']"));
			pricePCG = Cart_itemPriceListOld.get(0).getText();
			priceSUB = Cart_itemPriceListOld.get(1).getText();
			priceDCG = Cart_itemPriceListOld.get(2).getText();
		}
		priceSUBAdd = driver.findElement(By.xpath("//*[@class='cart-item-addedItem-price']")).getText();
		price_PCG = GetPriceValue(pricePCG);
		price_SUB = GetPriceValue(priceSUB);
		price_DCG = GetPriceValue(priceDCG);
		price_SUBAdd = GetPriceValue(priceSUBAdd);
		price_SUBTotal = price_SUB + price_SUBAdd;
		for(int i=2;i<6;i++){
			if(price_SUBTotal < price_PCG){
				for(int j=2;j<6;j++){
					if(price_PCG < price_DCG){
						flag = true;
						break;	
					}else{
						updateQuantityDCG(Integer.toString(i));
						priceDCG = b2bPage.Cart_itemPriceList.get(2).getText();
						price_DCG = GetPriceValue(priceDCG);
						if(i==5&&price_PCG < price_DCG){
							flag = true;
						}
					}
				}
				
			}else{
				updateQuatity(b2bPage.cartPage_Quantity, Integer.toString(i));
				pricePCG = b2bPage.Cart_itemPriceList.get(0).getText();
				price_PCG = GetPriceValue(pricePCG);
				if(i==5&&price_SUBTotal < price_PCG){
					flag = true;
				}
			}
		}
		
		return flag;
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
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}
	
	public void updateQuantityDCG(String quantity){
		WebElement cartPage_Quantity3 = driver.findElement(By.xpath("//input[@id='quantity3']"));
		WebElement cartPage_Quantity_update3 = driver.findElement(By.xpath("//input[@id='QuantityProduct_3']"));
		cartPage_Quantity3.clear();
		cartPage_Quantity3.sendKeys(quantity);
		Common.javascriptClick(driver, cartPage_Quantity_update3);
	}
	
	public void updateQuatity(WebElement cartPage_Quantity, String cartQuantity){
		Common.sleep(2000);
		b2bPage.cartPage_Quantity.clear();
		b2bPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2bPage.cartPage_Quantity_update);
		Common.sleep(1000);
	}
	
	
	public void checkoutpopup(String morethan5){
		By popup = By.xpath("//div[@class='popup-itemlimit']");
		b2bPage.cartPage_Quantity.clear();
		b2bPage.cartPage_Quantity.sendKeys(morethan5);
		Common.sleep(2000);
		Assert.assertTrue(Common.checkElementDisplays(driver, popup, 5));
	}
	
	public void createwareHouse(String houserAmount){
		WebElement wareHouseAmount = driver
				.findElement(By
						.xpath(".//*[@id='Content/IntegerEditor[in Content/Attribute[.available]]_input']"));
		Common.sleep(2000);
		wareHouseAmount.clear();
		wareHouseAmount.sendKeys(houserAmount);
		WebElement reserveWareHouseAmount = driver
				.findElement(By
						.xpath(".//*[contains(@id,'Attribute[.reserved]]_input') or contains(@id,'Attribute[StockLevel.reserved]]_input')]"));
		Common.sleep(2000);
		reserveWareHouseAmount.clear();
		reserveWareHouseAmount.sendKeys("0");
		driver.findElement(
				By.id("Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate]_label"))
				.click();
	}
	
	public void updatewareHouse(String houserAmount){
		Common.sleep(3000);
		WebElement wareHouseAmount = driver
				.findElement(By
						.xpath(".//*[contains(@id,'Attribute[.available]]_input') or contains(@id,'Attribute[StockLevel.available]]_input')]"));
		Common.sleep(2000);
		wareHouseAmount.clear();
		wareHouseAmount.sendKeys(houserAmount);
		WebElement reserveWareHouseAmount = driver
				.findElement(By
						.xpath(".//*[contains(@id,'Attribute[.reserved]]_input') or contains(@id,'Attribute[StockLevel.reserved]]_input')]"));
		Common.sleep(2000);
		reserveWareHouseAmount.clear();
		reserveWareHouseAmount.sendKeys("0");
		driver.findElement(
				By.id("Content/ImageToolbarAction[organizer.editor.save.title][1]_img"))
				.click();
	}
	
	public String getwareHouseData(){
		hmcPage.Home_baseStore.click();
		hmcPage.baseStore_id.clear();
		hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
		hmcPage.baseStore_search.click();
		hmcPage.BaseStore_SearchResult.click();
		hmcPage.Common_SaveButton.click();
		hmcPage.BaseStore_PropertiesTab.click();
		WarehouseData = hmcPage.BaseStoreProperties_WarehouseData.getText();
		return WarehouseData;
	}
	
	public static void addPartNumberToCart(B2BPage b2bPage, String partNum) {
		b2bPage.Cart_QuickOrderTextBox.clear();
		b2bPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2bPage.Cart_AddButton.click();
	}
	
	
	public void checkUpdateQuatity(String cartQuantity){
		b2bPage.cartPage_Quantity.clear();
		b2bPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2bPage.cartPage_Quantity_update);
		Common.sleep(3000);
		By quantity_Message1 = By.xpath("// div[contains(text(),'Product quantity has been updated.')]");
		By quantity_Message5 = By.xpath("// div[contains(text(),'has been reduced to 4') or contains(@text,'Unfortunately')]");
		switch(cartQuantity){
			case "3":
				if(!Common.checkElementDisplays(driver, quantity_Message1, 5)){
					b2bPage.cartPage_Quantity.clear();
					b2bPage.cartPage_Quantity.sendKeys("2");
					Common.javascriptClick(driver, b2bPage.cartPage_Quantity_update);
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
						Assert.assertEquals(b2bPage.cartPage_Quantity.getText().trim(), "4");
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
	*/


}
