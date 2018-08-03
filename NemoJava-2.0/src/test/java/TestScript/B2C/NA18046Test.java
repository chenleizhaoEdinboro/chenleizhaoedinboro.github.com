package TestScript.B2C;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class NA18046Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String productMTM;
	private String accessoryNo;
	private String ruleName = "";
	private String allRuleName = "";
	private int configurationDetailsNo;
	private boolean isNewUI;
	private Map<String,String> ctocartDetails;
	private Map<String,String> ctofinal_thankYouDetails;
	private Map<String,String> final_thankYouDetails;
	public NA18046Test(String store, String accessory, String ctoProduct) {
		this.Store = store;
		this.accessoryNo = accessory;
		this.testName = "NA-18046";
	}
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA8046(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			productMTM = testData.B2C.getDefaultMTMPN();
			ruleName = "FP_" + Store + Common.getDateTimeString();
			allRuleName = "FP_" + Store;
			
			//step~1
			// go to us B2C website
			driver.get(testData.B2C.getHomePageUrl());
			Dailylog.logInfoDB(1, "load b2c successfully ", Store, testName);
			
			//add one MTM product into cart
			Common.sleep(1000);
			String b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + productMTM;
			Dailylog.logInfoDB(1, "currentUrl: " + driver.getCurrentUrl(), Store, testName);
			loginB2CPage();
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			driver.get(b2cProductUrl);
			// check if product is valid
			WebElement eleAddToBasket = driver.findElement(By.xpath("//*[@id='addToCartButtonTop']"));
			if (Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.Product_AddToCart)), 5)){
				Common.scrollToElement(driver, b2cPage.Product_AddToCart);
				Common.javascriptClick(driver, b2cPage.Product_AddToCart);
			}else if(Common.checkElementDisplays(driver, eleAddToBasket, 5)){
				Common.scrollToElement(driver, eleAddToBasket);
				Common.javascriptClick(driver, eleAddToBasket);
			}
			Common.sleep(3000);
			isNewUI = Common.isElementExist(driver, By.xpath("//div[@class='summary-heading category_title']"), 5);
			Dailylog.logInfoDB(1, "MTM product is added into cart: " + productMTM, Store, testName);
			
			//step~2 go to Accessories, add an accessory into cart.
			String accessoryMsg = "sorry";
			String accessoryID = "";
			if(!(this.Store=="US_OUTLET")){
			accessoryID = selectOptionPB(accessoryMsg, "Accessories", isNewUI);
			if (isNewUI) {
				try {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				} catch (StaleElementReferenceException e) {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				}
			} else
				b2cPage.PB_addToCart_old.click();
			Dailylog.logInfoDB(2, "add an accessory into cart:" + accessoryNo, Store, testName);
			}
			
			//step3
			Common.sleep(2000);
			if(isNewUI){
				configurationDetailsNo = b2cPage.Cart_ConfigDetailsList.size();
			}else{
				configurationDetailsNo = b2cPage.Cart_ConfigDetailsListOldUI.size();
			}
			Assert.assertNotEquals(configurationDetailsNo, 3, "Configuration Details display:" +  configurationDetailsNo);
			Dailylog.logInfoDB(3, "Configuration Detail displays only with MTM.", Store, testName);
			
			//step~4
			//click on "Configuration Detail"
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 10);
			b2cPage.Cart_configurationDetails.click();
			Map<String,String> cartDetailsMTM = putDetails(b2cPage.Cart_configurationDetailsItems);
			Map<String,String> mtmCartDetails = putDetails(b2cPage.Cart_configurationDetailsItems);
			Map<String,String> mtmfinal_CartDetails = replaceDetails(mtmCartDetails);
			Assert.assertEquals(mtmCartDetails, mtmfinal_CartDetails);
			
			if (accessoryID != "")
				checkCartItems(accessoryID, "accessory");
			Dailylog.logInfoDB(4, "the product attribute of product is displayed. " , Store, testName);
			
			//step~5 
			//click on "LENOVO CHECKOUT"
			Common.sleep(2000);
			Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);
			//GUEST CHECKOUT
			By GuestCheckout = By.xpath(".//*[@id='guestForm']/button");
			if (Common.isElementExist(driver, GuestCheckout)) {
				new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.GuestCheckout));
				b2cPage.GuestCheckout.click();
				Thread.sleep(1000);
	
			}
			//fill in shipping address, click on CONTINUE
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
				b2cPage.ValidateInfo_SkipButton.click();
			}else{
				System.out.println("skip not found");
			}
			Common.sleep(10000);
			Dailylog.logInfoDB(5, "payment page is displayed", Store, testName);
			
			//step~6:input payment, and click on CONTINUE
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_UseSuggestButton, 5)){
				b2cPage.ValidateInfo_UseSuggestButton.click();
			}
			String currentUrl1 = driver.getCurrentUrl();
			boolean paymentNewUI;
			paymentNewUI = currentUrl1.indexOf("add-delivery-address") != -1;;
			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Common.sleep(2000);
			if(paymentNewUI){
				b2cPage.Pay_ContinuNewUI.click();
			}else{
				b2cPage.Pay_Continu.click();
			}
			Common.sleep(3000);
			String currentUrl2 = driver.getCurrentUrl();
			System.out.println(currentUrl2);
			Assert.assertTrue(currentUrl2.contains("summary"));
			Dailylog.logInfoDB(6, "Summary page is displayed", Store, testName);
			
			//open the configuration detail.
			if(paymentNewUI){
				Common.waitElementClickable(driver, b2cPage.Summary_ConfigDetailsNewUI, 5);
				b2cPage.Summary_ConfigDetailsNewUI.click();
			}else{
				Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 5);
				b2cPage.Cart_configurationDetails.click();
			}
			Common.sleep(2000);
			
			//Veify the product attribute of profuct(step 1) is displayed when 
			Map<String,String> mtmSummaryDetails = putDetails(b2cPage.Summary_ConfigDetailsList);
			Map<String,String> mtmfinal_sumaryDetails = replaceDetails(mtmSummaryDetails);
			Assert.assertEquals(mtmSummaryDetails, mtmfinal_sumaryDetails);
	//		if(paymentNewUI){
	//			Common.waitElementClickable(driver, b2cPage.Summary_ConfigDetailsNewUI, 5);
	//			b2cPage.Summary_ConfigDetailsNewUI.click();
	//		}else{
	//			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 5);
	//			b2cPage.Cart_configurationDetails.click();
	//		}
			Common.sleep(2000);
			String summaryPartNO;
			if(!(this.Store=="US_OUTLET")){
				if(paymentNewUI){
					summaryPartNO = b2cPage.Checkout_AddedItemPartnumNewUI.getText().split(":")[1];
				}else{
					summaryPartNO = b2cPage.Checkout_AddedItemPartnum.getText();
				}
				Assert.assertEquals(summaryPartNO, accessoryID);
				Dailylog.logInfoDB(6, "the product attribute of product is displayed well. " , Store, testName);
			}
			
			
			//Step~7 check the T&C term, click on "Place order"
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			//product attributes displayed directly under profuct(step 1) on the page.
			Common.sleep(3000);
			String currentUrl3 = driver.getCurrentUrl();
			System.out.println(currentUrl3);
			Assert.assertTrue(currentUrl3.contains("orderConfirmation"));
			Dailylog.logInfoDB(7, "order confirmation page is displayed.", Store, testName);
			if(!paymentNewUI){
				Map<String,String> thankYouDetails = putDetails(b2cPage.ThankYou_ConfigDetailsList);
				final_thankYouDetails = replaceDetails(thankYouDetails);
				Map<String,String> final_cartDetails = replaceDetails(cartDetailsMTM);
				Assert.assertEquals(final_thankYouDetails, final_cartDetails);
			}
			Dailylog.logInfoDB(7, "product attributes displayed directly under profuct MTM", Store, testName);
			
			//step~8
			String OrderNumberMTM = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			openOrderDetails(paymentNewUI,OrderNumberMTM);
			Dailylog.logInfoDB(8, "open order detail page", Store, testName);
			
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 3);
			b2cPage.Cart_configurationDetails.click();
			Common.sleep(3000);
			Map<String,String> mtmOrderPageDetails = putDetails(b2cPage.OrdersDetail_ConfigDetailsList);
			Map<String,String> mtmfinal_OrderDetails = replaceDetails(mtmOrderPageDetails);
			System.out.println(mtmfinal_OrderDetails);
			System.out.println(mtmCartDetails);
			verifyConfigOrderPage(mtmfinal_OrderDetails, mtmCartDetails);
			Dailylog.logInfoDB(8, "verify configurate is consist with thankyou page", Store, testName);
			
			//step~9 , go to laptops, Customize one CTO product and change some config then add into cart.
			//logon us B2C website
			String b2cProductUrlCustomize = B2CCommon.getCTOProduct(testData.B2C.getHomePageUrl(), Store) + "/customize?";
			Dailylog.logInfoDB(9, "b2cProductUrlCustomize: " + b2cProductUrlCustomize, Store, testName);
			loginB2CPage();
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			driver.get(b2cProductUrlCustomize);
			Common.sleep(2000);
			Dailylog.logInfoDB(9, "currentUrl: " + driver.getCurrentUrl(), Store, testName);
			String ctoProduct = getCTOID();
			Dailylog.logInfoDB(9, "ctoProduct: " + ctoProduct, Store, testName);
			Common.sleep(5000);
			// check if product is valid
			if (isAlertPresent()) {
				driver.switchTo().alert().accept();
				Assert.fail("test product is invalid: " + ctoProduct);
			}
			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 3))
				Assert.fail("CTO_addToCart is not displayed, please update test product");
			//Change C&V on the CTO page
			// 没有ID只能用Text验证
			String cvText = "";
			cvText = selectCVCTO(isNewUI);
			//Click Add to Cart button
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			} catch (StaleElementReferenceException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			}
			Common.sleep(5000);
	
			//step~10 add an accessory into cart
			String ctoaccessoryMsg = "sorry";
			String ctoaccessoryID = "";
			ctoaccessoryID = selectOptionPB(ctoaccessoryMsg, "Accessories", isNewUI);
			if (isNewUI) {
				try {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				} catch (StaleElementReferenceException e) {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				}
			} else
				b2cPage.PB_addToCart_old.click();
			Dailylog.logInfoDB(10, "add an accessory into cart:" + ctoaccessoryID, Store, testName);
			
			//step~11
			Common.sleep(2000);
			checkCartItems(ctoProduct,"cv");
			checkCartItems(ctoaccessoryID,"accessory");
			Dailylog.logInfoDB(11, "shopping cart page is displayed" , Store, testName);
			
			//step~12 click on "Configuration Detail"
			Actions act =new Actions(driver);
			act.sendKeys(Keys.PAGE_UP).perform();;
			Common.waitElementClickable(driver, b2cPage.Cart_ConfigDetailsListOldUI.get(0), 10);
			b2cPage.Cart_ConfigDetailsListOldUI.get(0).click();
			Common.sleep(2000);
			ctocartDetails = putDetails(b2cPage.Cart_configurationDetailsItems);
			if (cvText != "")
				checkCartItems(cvText, "cv");
			if (ctoaccessoryID != "")
				checkCartItems(ctoaccessoryID, "accessory");
			Dailylog.logInfoDB(12, "the product attribute of CTO is displayed.", Store, testName);
					
			//step~13 
			//click on "LENOVO CHECKOUT"
			Common.sleep(2000);
			Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);
			//GUEST CHECKOUT
			By GuestCheckoutCTO = By.xpath(".//*[@id='guestForm']/button");
			if (Common.isElementExist(driver, GuestCheckoutCTO)) {
				new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(b2cPage.GuestCheckout));
				b2cPage.GuestCheckout.click();
				Thread.sleep(1000);
	
			}
			//fill in shipping address, click on CONTINUE
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
				b2cPage.ValidateInfo_SkipButton.click();
			}else{
				System.out.println("skip not found");
			}
			Dailylog.logInfoDB(13, "payment page is displayed", Store, testName);
			
			//step~14:input payment, and click on CONTINUE
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_UseSuggestButton, 5)){
				b2cPage.ValidateInfo_UseSuggestButton.click();
			}
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			if(paymentNewUI){
				b2cPage.Pay_ContinuNewUI.click();
			}else{
				b2cPage.Pay_Continu.click();
			}
			Common.sleep(3000);
			Dailylog.logInfoDB(14, "Summary page is displayed", Store, testName);
			
			//open the configuration detail.
			if(paymentNewUI){
				Common.waitElementClickable(driver, b2cPage.Summary_ConfigDetailsNewUI, 5);
				b2cPage.Summary_ConfigDetailsNewUI.click();
			}else{
				Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 5);
				b2cPage.Cart_configurationDetails.click();
			}
			Common.sleep(1000);
			
			//Veify the product attribute of profuct(step 1) is displayed 
			if (cvText != "")
				checkCartItems(cvText, "cv");
			String checkOutPartNO;
			if(paymentNewUI){
				checkOutPartNO = b2cPage.Checkout_AddedItemPartnumNewUI.getText().split(":")[1];
			}else{
				checkOutPartNO = b2cPage.Checkout_AddedItemPartnum.getText();
			}
			Assert.assertEquals(checkOutPartNO, ctoaccessoryID);
			Dailylog.logInfoDB(13, "the product attribute of product is displayed well. " , Store, testName);
			
			//Step~15 check the T&C term, click on "Place order"
			JavascriptExecutor js14= (JavascriptExecutor) driver;
			js14.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			//product attributes displayed directly under profuct(step 1) on the page.
			Common.sleep(3000);
			String currentUrl5 = driver.getCurrentUrl();
			System.out.println(currentUrl5);
			Assert.assertTrue(currentUrl5.contains("orderConfirmation"));
			Dailylog.logInfoDB(15, "order confirmation page is displayed.", Store, testName);
			
			if(!paymentNewUI){
				Map<String,String> ctothankYouDetails = putDetails(b2cPage.ThankYou_ConfigDetailsList);
				ctofinal_thankYouDetails = replaceDetails(ctothankYouDetails);
				Map<String,String> ctofinal_cartDetails = replaceDetails(ctocartDetails);
				Assert.assertEquals(ctofinal_thankYouDetails, ctofinal_cartDetails);
			}
			String OrderNumber;
			if(paymentNewUI){
				OrderNumber = b2cPage.OrderThankyou_OrderNumberLabelNew.getText();
			}else{
				OrderNumber = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			}
			Dailylog.logInfoDB(15, "product attributes displayed on Thank You page.Order Number is:" + OrderNumber, Store, testName);
			
			//step~16
			//go to My Account
			openOrderDetails(paymentNewUI,OrderNumber);
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 3);
			b2cPage.Cart_configurationDetails.click();
			Common.sleep(1000);
			Map<String,String> ctotOrderPageDetails = putDetails(b2cPage.OrdersDetail_ConfigDetailsList);
			Map<String,String> ctofinal_OrderDetails = replaceDetails(ctotOrderPageDetails);
			verifyConfigOrderPage(ctofinal_OrderDetails, replaceDetails(ctocartDetails));
			Dailylog.logInfoDB(16, "order detail page is displayed.", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
}
	
	private void verifyConfigOrderPage(Map<String, String> final_thankYou, Map<String, String> mtmfinal_Order){
		boolean flag = false;
			for(Map.Entry<String, String> entry1 : mtmfinal_Order.entrySet()){
				if(final_thankYou.entrySet().toString().contains(entry1.getValue())){
					flag = true;
					System.out.println(entry1.getValue());
					break;
				}
			}
		Assert.assertTrue(flag,final_thankYou.entrySet() + "matched");	
	}
	
	private void openOrderDetails(boolean paymentNewUI,String OrderNumber){
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
		String currentUrlOrderDetails = driver.getCurrentUrl();
		System.out.println(currentUrlOrderDetails);
		Assert.assertTrue(currentUrlOrderDetails.contains(OrderNumber));
	}
	private String selectCVCTO(boolean isNewUI) {
		String cvText = "";
		String cvPrice = "";
		if (isNewUI) {
			By expandCategoriesX = By.xpath(Common.convertWebElementToString(b2cPage.cto_expandCategories));
			if (Common.checkElementDisplays(driver, expandCategoriesX, 3))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.cto_expandCategories);
			if (Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItem)), 3)) {
				cvText = b2cPage.CTO_unselectedItemText.getText();
				cvPrice = b2cPage.CTO_unselectedItemPrice.getText();
				Dailylog.logInfoDB(5, "cvText: " + cvText, Store, testName);
				Dailylog.logInfoDB(5, "cvPrice: " + cvPrice, Store, testName);
				// b2cPage.CTO_unselectedItem.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.CTO_unselectedItem);
				// TODO 价格
			} else {
				Dailylog.logInfoDB(5, "change cv in CTO page failed, no cv to change", Store, testName);
			}
		} else {
			if (Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld)), 3)) {
				cvText = b2cPage.CTO_unselectedItemTextOld.getText();
				cvPrice = b2cPage.CTO_unselectedItemPriceOld.getText();
				System.out.println(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld));
				Dailylog.logInfoDB(5, "cvText: " + cvText, Store, testName);
				Dailylog.logInfoDB(5, "cvPrice: " + cvPrice, Store, testName);
				b2cPage.CTO_unselectedItemTextOld.click();
				// TODO 价格
			} else {
				Dailylog.logInfoDB(5, "change cv in CTO page failed, no cv to change", Store, testName);
			}
		}
		return cvText;
	}
	
	public void loginB2CPage(){
		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getHomePageUrl());
		B2CCommon.handleGateKeeper(b2cPage, testData);
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
	
	private String getCTOID() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		String ctoProduct = b2cProductUrl.split("/p/")[1].split("#")[0].substring(0, 10);
		return ctoProduct;
	}
	
	private boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
	
	private String selectOptionPB(String message, String type, boolean isNewUI) throws InterruptedException {
		String testItem = "";
		String testItemID = "";
		By messageX = By.xpath("//div[contains(@data-tabcode,'" + type + "') or contains(@data-nav-type,'" + type
				+ "') or contains(@data-tabname,'" + type + "')]//div[contains(.,'" + message + "')]");
		System.out.println(messageX.toString());
		boolean isNewWarranty = Common.checkElementDisplays(driver, By.id("stackableWarranty"), 3);
		if (isNewUI) {
			switch (type.toLowerCase()) {
			case "warranty":
				testItem = Common.convertWebElementToString(b2cPage.PB_warrantyChange);
				break;
			case "software":
				testItem = Common.convertWebElementToString(b2cPage.PB_softwareItem);
				break;
			case "accessories":
				testItem = Common.convertWebElementToString(b2cPage.PB_accessoryItem);
				break;
			}
			if (Common.checkElementDisplays(driver, By.xpath(testItem), 10)) {
				if (type.toLowerCase().equals("warranty")) {
					if (!isNewWarranty) {
						Common.scrollToElement(driver, b2cPage.PB_warrantyChange);
						b2cPage.PB_warrantyChange.click();
						testItemID = b2cPage.PB_addWarrantyItemID.getAttribute("value");
						b2cPage.PB_addWarrantyItem.click();
					} else {
						System.out.println("stackble warranty");
						if (Common.checkElementDisplays(driver,
								By.xpath(Common.convertWebElementToString(b2cPage.PB_warrantyChange)), 3))
							b2cPage.PB_warrantyChange.click();
						if (Common.checkElementExists(driver, b2cPage.PB_stackableWarrantyItem, 3)) {
							b2cPage.PB_stackableWarrantyItem.click();
							Common.sleep(1000);
							testItemID = b2cPage.PB_stackableWarrantyItemID.getAttribute("value");
						} else
							Dailylog.logInfoDB(0, "stackble warranty -- only default warranty", Store, testName);
					}
				} else if (type.toLowerCase().equals("software")) {
					testItemID = b2cPage.PB_addSoftwareItemID.getAttribute("value");
					Common.scrollToElement(driver, b2cPage.PB_softwareItem);
					b2cPage.PB_softwareItem.click();
				} else if (type.toLowerCase().equals("accessories")) {
					testItemID = b2cPage.PB_addAccessoryItemID.getAttribute("value");
					Common.scrollToElement(driver, b2cPage.PB_accessoryItem);
					Common.javascriptClick(driver, b2cPage.PB_accessoryItem);
				}
			} else {
				Boolean isDisplayed = Common.checkElementDisplays(driver, messageX, 10);
				Assert.assertTrue(isDisplayed, "no " + type + " and no message in PB");
				Dailylog.logInfoDB(0, driver.findElement(messageX).getText(), Store, testName);
			}
		} else {
			// TODO: xpath for message on old UI may not correct
			testItem = "//li[@data-tabname='" + type + "']";
			Common.scrollToElement(driver, driver.findElement(By.xpath(testItem)));
			driver.findElement(By.xpath(testItem)).click();
			if (type.toLowerCase().equals("warranty") && isNewWarranty) {
				System.out.println("stackble warranty");
				if (Common.checkElementExists(driver, b2cPage.PB_stackableWarrantyItem, 3)) {
					b2cPage.PB_stackableWarrantyItem.click();
					Common.sleep(1000);
					//get value from li not correct
					testItemID = b2cPage.PB_stackableWarrantyItemID.getAttribute("value");
				} else
					Dailylog.logInfoDB(0, "stackble warranty -- only default warranty", Store, testName);
			} else {
				testItem = "//div[@data-tabname='" + type
						+ "']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']/../input";
				if (Common.checkElementDisplays(driver, By.xpath(testItem), 5)) {
					testItemID = driver.findElement(By.xpath(testItem)).getAttribute("value");
					driver.findElement(By.xpath(testItem)).click();
				} else {
					Boolean isDisplayed = Common.checkElementDisplays(driver, messageX, 10);
					Assert.assertTrue(isDisplayed, "no " + type + " and no message in PB");
				}
			}
		}
		return testItemID;
	}
	
	private void checkCartItems(String itemID, String type) {
		if (type.toLowerCase().equals("cv")) {
			By check = By.xpath("//*[contains(text(),'" + itemID + "')]");
			if (!Common.checkElementDisplays(driver, check, 10)) {
				for (int i = 0; i < b2cPage.Cart_configurationDetailsItems.size(); i++) {
					String getText = b2cPage.Cart_configurationDetailsItems.get(i).getText();
					// System.out.println(getText.replaceAll(" ", ""));
					//System.out.println(itemID.replaceAll(" ", ""));
					if (getText.replaceAll(" ", "").contains(itemID.replaceAll(" ", "")))
						break;
					else if (i == b2cPage.Cart_configurationDetailsItems.size() - 1)
						Assert.fail("selected cv is not displayed in cart: " + itemID);
					Dailylog.logInfoDB(0, "Cart_configurationDetailsItems: " + i + " " + getText, Store, testName);
				}
			}
		} else {
			By check = By.xpath("//dd[@class='cart-item-addedItem-partNumber' and text()='" + itemID + "']");
			if (!Common.checkElementDisplays(driver, check, 10)) {
				for (int i = 0; i < b2cPage.Cart_addedItem.size(); i++) {
					Dailylog.logInfoDB(0, "Cart_addedItem: " + i + " " + b2cPage.Cart_addedItem.get(i).getText(), Store,
							testName);
				}
				Assert.fail("selected " + itemID + " is not displayed in cart");
			}
		}
	}
	
	public Map<String,String> putDetails(List<WebElement> eles){
		Map<String,String> cartDetails = new HashMap<String,String>();
		for (int i = 0; i < eles.size(); i++) {
			cartDetails.put(String.valueOf(i), eles.get(i).getText());	
		}
		return cartDetails;
	}
	
	public Map<String, String>  replaceDetails(Map<String,String> details){
		String itemsDetails = null;
		Set<String> get = details.keySet(); 
        for (String test:get) {
        	if(details.get(test).contains(":")){
        		itemsDetails = details.get(test).replaceAll(":", "");
        		details.put(test, itemsDetails);
        	}
        
        }
        return details; 
	}
	
}