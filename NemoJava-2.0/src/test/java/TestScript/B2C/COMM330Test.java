package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class COMM330Test extends SuperTestClass {

	public String laptopPageURL;
	private String currentURL;
	private String seriesName;
	private String partNo;
	private String esupport_account = "lenovobptest@outlook.com";
	private String esupport_password = "bprole@lenovo";
	private String B2CPartSalesURL;
	private boolean isDisplayed;
	private B2CPage b2cPage;
	
	public COMM330Test(String store, String partsalesNumber) {
		this.Store = store;
		this.partNo = partsalesNumber;
		this.testName = "NA-27990";
		
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM330(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);
			PartSalesPage PSPage = new PartSalesPage(driver);
			Actions act = new Actions(driver);
			
			//step~1
			Dailylog.logInfoDB(1,"Go to esupport without logon", Store,testName);
//			driver.get(testData.B2C.getPartSalesUrl());
			GoEsupport(PSPage);
			
			//select an item and click "add to cart".
			addToCartEsupport(PSPage);

			//step~2
			Dailylog.logInfoDB(2,"Click 'View my cart' in the pop-up window", Store,testName);
			
			Common.waitElementVisible(driver, PSPage.viewMyCart);
			PSPage.viewMyCart.click();
//			Common.waitElementVisible(driver, PSPage.partSalesCheckOut);
//			PSPage.partSalesCheckOut.click();
			
			//step~3
			Dailylog.logInfoDB(3,"User log in at eSupport site", Store,testName);	
			Common.waitElementVisible(driver, PSPage.partSales_LoginID);
			logonEsupport(PSPage);
			
			//step~4&5
			Dailylog.logInfoDB(4,"function is available for items", Store,testName);
			Dailylog.logInfoDB(5,"Able to change and update quantity", Store,testName);
			Common.sleep(2000);
			checkUpdateQuatity();
			
			//step~6&7
			Dailylog.logInfoDB(6,"Click 'Remove'", Store,testName);
			
			boolean isNewUI;
			isNewUI = Common.checkElementDisplays(driver, b2cPage.NewCart_DeleteButton, 5);
			if(isNewUI){
				b2cPage.NewCart_DeleteButton.click();
				driver.findElement(By.xpath(".//*[@id='cartContinueShopping']/a/span"));
			}else{
				driver.navigate().back();
				driver.navigate().back();
//				JavascriptExecutor executor = (JavascriptExecutor) driver;
//				executor.executeScript("window.open('https://eservice:1qaz!QAZ@presupport.lenovo.com/us/en/partslookup')");
			}
			
			//step~8
			Dailylog.logInfoDB(8,"add accessory on the eservice site. ", Store,testName);
			GoEsupport(PSPage);						
			
			//select an item and click "add to cart".
			addToCartEsupport(PSPage);			
			
			driver.findElement(By.xpath("//*[@id='pcg_shop_addtocart']/div/div/div/div/div/button[1]")).click();
			Common.sleep(2000);
			
			//step~9
			Dailylog.logInfoDB(9,"click on View My cart button the mini cart on eserive.", Store,testName);
			PSPage.partSales_IconCart.click();
			Common.waitElementClickable(driver, PSPage.partSales_ViewCart, 3);
			Common.javascriptClick(driver, PSPage.partSales_ViewCart);
			
			//step~10
			Dailylog.logInfoDB(10,"logon with lenovo ID", Store,testName);
			if(Common.checkElementDisplays(driver, PSPage.partSales_LoginID, 5)){
				logonEsupport(PSPage);
			}

			//step~11
			Dailylog.logInfoDB(11,"Proceed to checkout", Store,testName);
			Common.sleep(3000);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			
			//step~12
			Dailylog.logInfoDB(12,"Fill in shipping information", Store,testName);
			B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			
			//step~13
			Dailylog.logInfoDB(13,"Fill in payment method, continue to review", Store,testName);
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~14
			Dailylog.logInfoDB(14,"Click 'EDIT CART' on 'Review & Purchase' page", Store,testName);
			b2cPage.partSales_EditCart.click();
			
			//step~15
			Dailylog.logInfoDB(15,"proceed to checkout from shopping cart page", Store,testName);
			Common.sleep(3000);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~16
			Dailylog.logInfoDB(16,"Click 'Place your order' on 'Review & Purchase' page", Store,testName);
			Common.sleep(2000);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("orderConfirmation"));
			
			//step~17
			Dailylog.logInfoDB(17,"Go to esupport Parts shopping page, not logon esupport ", Store,testName);
			driver.manage().deleteAllCookies();
			GoEsupport(PSPage);
			By logonAccount = By.xpath("//li[@class='header-account']/div/button[text()='My Account']");
			if(!Common.checkElementDisplays(driver, logonAccount, 5)){
				Common.mouseHover(driver, driver.findElement(logonAccount));
				Common.sleep(1000);
				driver.findElement(By.xpath("//li[@class='account-logout']")).click();
			}
			//select an item and click "add to cart".
			addToCartEsupport(PSPage);
			Common.sleep(2000);			
			
			//step~18
			Dailylog.logInfoDB(18,"Click 'View my cart' in the pop-up window ", Store,testName);
			Common.waitElementVisible(driver, PSPage.viewMyCart);
			PSPage.viewMyCart.click();
			
			//step~19
			Dailylog.logInfoDB(19,"Click 'continue to guest checkout'", Store,testName);
			Common.sleep(3000);
			PSPage.partSales_GuestCheckout.click();
			
			//step~20&21
			Dailylog.logInfoDB(20,"Hybris shopping cart page", Store,testName);
			Dailylog.logInfoDB(21,"Edit quantity", Store,testName);
			checkUpdateQuatity();
			
			//step~22
			Dailylog.logInfoDB(22,"Click 'Remove'", Store,testName);
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.NewCart_DeleteButton.click();
			b2cPage.NewCart_ConfirmDelete.click();
			
			//step~23
			Dailylog.logInfoDB(23,"Click 'continue shopping'", Store,testName);
			Common.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='cartContinueShopping']/a/span")).click();;
			
			//step~24
			Dailylog.logInfoDB(24,"add accessory on the eservice site.", Store,testName);
			addToCartEsupport(PSPage);
			
			//step~25
			Dailylog.logInfoDB(25,"click on View My cart", Store,testName);
			Common.waitElementVisible(driver, PSPage.viewMyCart);
			PSPage.viewMyCart.click();
			
			//step~26
			Dailylog.logInfoDB(26,"Continue to guest checkout", Store,testName);
			Common.sleep(3000);
			if(Common.checkElementDisplays(driver, PSPage.partSales_GuestCheckout, 5)){
				PSPage.partSales_GuestCheckout.click();
			}
			
			//step~27
			Dailylog.logInfoDB(27,"Proceed to checkout", Store,testName);
			Common.sleep(2000);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			
			//step~28
			Dailylog.logInfoDB(28,"Fill in shipping information", Store,testName);
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			Common.sleep(2000);
			B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			
			//step~29
			Dailylog.logInfoDB(29,"Fill in payment method, continue to review", Store,testName);
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~30
			Dailylog.logInfoDB(30,"Click 'EDIT CART' on 'Review & Purchase' page", Store,testName);
			b2cPage.partSales_EditCart.click();
			
			//step~31
			Dailylog.logInfoDB(31,"proceed to checkout from shopping cart page to shipping and payment page", Store,testName);
			Common.sleep(3000);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			
			B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~32
			Dailylog.logInfoDB(32,"Click 'Place your order' on 'Review & Purchase' page", Store,testName);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("orderConfirmation"));
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Promotion)).click().perform();
		}
	}
	
	public void checkUpdateQuatity(){
		b2cPage.cartPage_Quantity.clear();
		String cartQuantity = "2";
		b2cPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		By quantity_Message = By.xpath("// div[contains(text(),'Product quantity has been updated.')]");
		if(!Common.checkElementDisplays(driver, quantity_Message, 5)){
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("1");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		}
		Common.sleep(2000);
		isDisplayed = Common.checkElementDisplays(driver, quantity_Message, 5);
		if (!isDisplayed)
			Assert.fail("mesage is not displayed:Product quantity has been updated. ");
		else
			System.out.println("message is displayed:Product quantity has been updated.");
	}
	
	public void addToCartEsupport(PartSalesPage PSPage){
		closePromotion(driver,b2cPage);
		PSPage.partNumber.sendKeys(partNo);
		Common.sleep(2000);
		Common.javascriptClick(driver, PSPage.partLookUp);
		Common.sleep(3000);
		Common.mouseHover(driver, PSPage.addToCartNew);
		Common.javascriptClick(driver, PSPage.addToCartNew);
	}
	
	public void logonEsupport(PartSalesPage PSPage){
		PSPage.partSales_LoginID.clear();
		PSPage.partSales_LoginID.sendKeys(esupport_account);
		PSPage.partSales_Password.clear();
		PSPage.partSales_Password.sendKeys(esupport_password);
		PSPage.partSales_Signin.click();
	}
	
	public void GoEsupport(PartSalesPage PSPage){
		//https://eservice:1qaz!QAZ@presupport.lenovo.com/us/en/partslookup
		Common.NavigateToUrl(driver, Browser, testData.B2C.getPartSalesUrl().replace("presupport", "eservice:1qaz!QAZ@presupport"));
		
		//select language
		Common.waitElementClickable(driver, PSPage.partSales_SelectLanguage, 6);
		PSPage.partSales_SelectLanguage.click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//div[@class='language-list']/ul/li/a[text()='English']")).click();
		
		//select country
		PSPage.partSales_SelectCountry.click();
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")), 15);
		driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")).click();
		driver.get(testData.B2C.getPartSalesUrl());
		Common.sleep(3000);
	}

}