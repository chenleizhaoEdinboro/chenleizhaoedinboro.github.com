package TestScript.B2C;

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

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class COMM208Test extends SuperTestClass {
	B2CPage b2cPage;
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

	public COMM208Test(String store){
		this.Store = store;
		this.testName = "COMM-208";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2c"})
	public void COMM208(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			act = new Actions(driver);
			
			
			//step~2,6,7
			//LOG IN SMB
			driver.get(testData.B2C.getloginPageUrl());
			smbLogin(testData.B2C.getLoginID(),testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(6, "Add a product with subscription item to cart.", Store, testName);
			Common.sleep(2000);
			if(driver.getCurrentUrl().contains("update-profile")){
				driver.get(testData.B2C.getHomePageUrl() + "/cart");
			}else{
				driver.get(driver.getCurrentUrl() + "/cart");
			}
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			addCTO_SUB();
//			addMTMQuick();
			
			//step~1:Add multiple subscription products to the shopping cart.
			Dailylog.logInfoDB(1, "Add multiple subscription products to cart.", Store, testName);
			Common.sleep(2000);
			B2CCommon.addPartNumberToCart(b2cPage, subproNUM);
			b2cPage.cartPage_Quantity2.clear();
			b2cPage.cartPage_Quantity2.sendKeys("2");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update2);
			
			//step~4,5
			Dailylog.logInfoDB(4, "Add multiple DCG products to the shopping cart.", Store, testName);
			Dailylog.logInfoDB(5, "Update quantity to not less than 2.", Store, testName);
			B2CCommon.addPartNumberToCart(b2cPage, dcgproNUM);
			
			//step~8
			Dailylog.logInfoDB(8, "(price of subscription products) < (price of PCG products)<(price of DCG products)", Store, testName);
			if(checkprice()){
				System.out.println("sub:" + price_SUBTotal + "< DCG:" + price_DCG + "PCG: <" + price_PCG);
			}else{
				System.out.println(price_SUBTotal);
				System.out.println(price_PCG);
				System.out.println(price_DCG);
				Assert.fail();
			}
			
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
			List<WebElement> ele_ertries = driver.findElements(entries);
			Assert.assertEquals(ele_ertries.size(), 2);
			
			//step~13
			Dailylog.logInfoDB(13, "Right click entries", Store, testName);
			checkitem(ele_ertries.get(0), "ACCEPTED", "3");
			
			//step~14
			Dailylog.logInfoDB(14, "Check the value of the Transactions status and giantType.", Store, testName);
			Common.switchToWindow(driver, 2);
			driver.navigate().refresh();
			List<WebElement> ele_ertries1 = driver.findElements(entries);
			checkitem(ele_ertries1.get(1), "REJECTED", "1");
			
			
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
	
	public void addMTMQuick(){
		mtmproNUM = testData.B2C.getDefaultMTMPN();
		addPartNumberToCart(b2cPage, mtmproNUM);
		Common.sleep(3000);
		By noStockMess = By.xpath("//*[contains(text(),'No Stock for the Product') or contains(text(),'is invalid')]");
		if(Common.checkElementDisplays(driver, noStockMess, 5)){
			// price is lower is better
			mtmproNUM = "81BD001NUS";
			addPartNumberToCart(b2cPage, mtmproNUM);
		}
	}
	
	public void addCTO_SUB(){
		String b2cProductUrlCustomize = driver.getCurrentUrl() + "/p/" + ctoproNUM + "/customize?";
		driver.get(b2cProductUrlCustomize);
		Common.sleep(5000);
		boolean isNewUI = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_newconfiguratorHeader)));
		By PB_subscription;
		if(!isNewUI){
			b2cPage.Product_AddToCartBtn.click();
			Common.sleep(8000);
			try {
				Common.scrollToElement(driver, b2cPage.Accessories_continue);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Common.javascriptClick(driver, b2cPage.Accessories_continue);
			Common.sleep(3000);
			PB_subscription = By.xpath("//input[@value='" + subproNUM + "']");
			
		}else{
			b2cPage.PB_softwareTab.click();	
			Common.sleep(3000);
			PB_subscription = By.xpath("//div[@id='Software']//div/span[contains(text(),'subscription') or contains(text(),'Subscription')]/../following-sibling::div[1]//"
					+ "label[@class='option-price-label']/input[not(@checked)]/../div[contains(@class,'AddButton')]");
		}
	
		if(Common.checkElementInvisible(driver, driver.findElement(PB_subscription), 15)){
			System.out.println("Subsription does not exist!!!");
		}else{
			try {
				Common.scrollToElement(driver, driver.findElement(PB_subscription));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(PB_subscription).click();
		}
		
		if(isNewUI){
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
		}else{
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_Productbuilder_AddToCartBtn);
			Common.sleep(3000);
			b2cPage.Accessories_continue.click();
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
		b2cPage.OrderSummary_PlaceOrderButton.click();
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
			}else if(Common.checkElementDisplays(driver, By.xpath("//*[@id='smb-login-button']"), 5)){
				Common.sleep(3000);
				driver.findElement(By.xpath("//*[@id='smb-login-button']")).click();
				Common.sleep(3000);
				
				Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.email.id']")), user);
				Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.password']")), password);
		
				driver.findElement(By.xpath("//*[@id='nemoLoginForm']/div/button[@type='submit']")).click();
				
				Common.sleep(6000);
			}else{
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			}
			
		}
	
	public void changeValueMerchantID(String Merchant_ID){
		Common.sleep(2000);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
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
	
	public boolean checkprice(){
		boolean flag = false;
		By cartNewUI = By.xpath("//form[contains(@action,'emptyCart')]/a");
		boolean cartisNewUI = Common.checkElementDisplays(driver, cartNewUI, 5);
		if(cartisNewUI){
			pricePCG = b2cPage.Cart_itemPriceList.get(0).getText();
			priceSUB = b2cPage.Cart_itemPriceList.get(1).getText();
			priceDCG = b2cPage.Cart_itemPriceList.get(2).getText();
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
			if(price_SUBTotal < price_DCG){
				for(int j=2;j<6;j++){
					if(price_DCG < price_PCG){
						flag = true;
						break;	
					}else{
						updateQuatity(b2cPage.cartPage_Quantity, Integer.toString(i));
						pricePCG = b2cPage.Cart_itemPriceList.get(0).getText();
						price_PCG = GetPriceValue(pricePCG);
						if(i==5&&price_DCG < price_PCG){
							flag = true;
						}
					}
				}
				
			}else{
				updateQuantityDCG(Integer.toString(i));
				priceDCG = b2cPage.Cart_itemPriceList.get(2).getText();
				price_DCG = GetPriceValue(priceDCG);
				if(i==5&&price_SUBTotal < price_DCG){
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
		Price = Price.replaceAll("/trial", "");
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
