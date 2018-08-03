package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM166Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public String Url;
	public String subscription = "RR00000003";
	public String plpUrl = "https://pre-c-hybris.lenovo.com/us/en/smbpro/rootsubscriptionoptioncategory/subscription/software-saas/sub-sa02/sub-sa0201/c/Sub_SA0201";
	public String pbpCTO  = "20LDCTO1WWENUS0";

	public COMM166Test(String Store) {
		this.Store = Store;
	
		this.testName = "COMM-166";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage, String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void COMM166(ITestContext ctx) {

		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);

			driver.get(testData.B2C.getloginPageUrl());

			Common.sleep(2500);

			B2CCommon.handleGateKeeper(b2cPage, testData);
			String loginID = testData.B2C.getLoginID();
			String pwd = testData.B2C.getLoginPassword();
			B2CCommon.login(b2cPage, loginID, pwd);
//			B2CCommon.handleGateKeeper(b2cPage, testData);
//			HandleJSpring(driver, b2cPage, loginID, pwd);

			Common.sleep(2000);
			
			 quickOrder();
			// pdp
		
			addToCardPDP();
			// plp
//			 addToCardPLP();
			// pbp
			 
			 addToCardPBP();
			// 8,Enter Checkout
			Dailylog.logInfoDB(8, "checkout", Store, testName);
			
			Common.javascriptClick(b2cPage.PageDriver, b2cPage.lenovo_checkout);
			// 9,Shipping Address will be prefilled, leave address as is

			Dailylog.logInfoDB(8, "Shipping Address", Store, testName);
			if (Common.checkElementDisplays(driver, b2cPage.ASM_EditAddress, 3)) {
				b2cPage.ASM_EditAddress.click();
			}
			B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());

			Thread.sleep(3000);

			Dailylog.logInfoDB(8, "shipping continue", Store, testName);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);

			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
				b2cPage.Shipping_AddressMatchOKButton.click();

			
			Thread.sleep(3000);
			

			Dailylog.logInfoDB(9, "Select Purchase Order payment.", Store, testName);

			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);

			Common.sleep(2000);

			Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);

			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");

			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {

				b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);

			}

			Dailylog.logInfoDB(10, "payment continue", Store, testName);

			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			// 10, drop order

			if (Common
					.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				try {
					driver.findElement(By.xpath("//*[@id='resellerID']"))
							.clear();
					driver.findElement(By.xpath("//*[@id='resellerID']"))
							.sendKeys("1234567890");
				} catch (Exception e) {
					System.out.println("reseller id is not available");
				}
			}

			
			

			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			Dailylog.logInfoDB(10, "Drop order", Store, testName);
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(10, " order number : " + orderNum, Store, testName);
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	private void addToCardPLP() {
		
		Common.NavigateToUrl(driver, Browser, plpUrl);
		Common.sleep(5000);
		if (Common.isElementExist(driver,
				By.xpath("//form[contains(@id,'"+subscription+"')]/button"))) {
			driver.findElement(By.xpath("//form[contains(@id,'"+subscription+"')]/button")).click();

			if (Common.isElementExist(driver,
					By.xpath(".//*[contains(@class,'addToCartPopupButton')]"))) {
				b2cPage.B2C_Accessory_SubAccessory_AddToCartPopup.click();
				Dailylog.logInfoDB(6, "Clicked on Add to cart popup.", Store,
						testName);
			}
			Common.sleep(5000);
			if (Common.isElementExist(driver,
					By.xpath(".//a[contains(@class,'addedToCart')]"))) {
				b2cPage.B2C_Accessory_GoToCart.click();
				Dailylog.logInfoDB(6, "Clicked on go to cart popup.", Store,
						testName);
			}
			Dailylog.logInfoDB(6, "go to cart page", Store,
					testName);
			

			String quantity = driver.findElement(
					By.xpath("//*[@value='"+subscription+"']/../input[contains(@id,'quantity')]")).getAttribute("value");
			Integer.parseInt(quantity);
			Dailylog.logInfoDB(6, "subscription quantity : " + quantity, Store,
					testName);
			Assert.assertTrue("PLP add to cart failed", quantity.equals("3"));
		}else{
			Assert.fail("no subscription product on plp page");
		}
		
		
		
		
	}

	private void addToCardPDP() {
		String URL = testData.B2C.getHomePageUrl() + "/p/"
				+ subscription;
		System.out.println("pdp URL is :" + URL);
		Common.NavigateToUrl(driver, Browser, URL);

		b2cPage.Add2Cart.click();

		if (Common.isElementExist(driver,
				By.xpath(".//*[contains(@class,'addToCartPopupButton')]"))) {
			b2cPage.B2C_Accessory_SubAccessory_AddToCartPopup.click();
			Dailylog.logInfoDB(5, "Clicked on Add to cart popup.", Store,
					testName);
		}
		Common.sleep(5000);
		if (Common.isElementExist(driver,
				By.xpath(".//a[contains(@class,'addedToCart')]"))) {
			b2cPage.B2C_Accessory_GoToCart.click();
			Dailylog.logInfoDB(5, "Clicked on go to cart popup.", Store,
					testName);
		}
		Dailylog.logInfoDB(5, "go to cart page", Store,
				testName);
		
	
	
		String quantity = driver.findElement(
				By.xpath("//*[@value='"+subscription+"']/../input[contains(@id,'quantity')]")).getAttribute("value");
		Integer.parseInt(quantity);
		Dailylog.logInfoDB(5, "subscription quantity : " + quantity, Store,
				testName);
		Assert.assertTrue("pdp add to cart failed", quantity.equals("2"));
	}

	private void addToCardPBP() {
		String URL = testData.B2C.getHomePageUrl() + "/p/"
				+ pbpCTO;
		System.out.println("pbp URL is :" + URL);
		Common.NavigateToUrl(driver, Browser, URL);
		String customize = "(.//*[@id='addToCartButtonTop']/span[@class='icon-cus'])";
		Common.javascriptClick(driver,
				driver.findElement(By.xpath(customize + "[1]")));
		Common.sleep(5000);
		
		//click software ,subscription under softwares
		Dailylog.logInfoDB(2,
				"add subscription to cto", Store,
				testName);
		
		Common.javascriptClick(driver,b2cPage.PBP_SoftwareTag);
		Common.javascriptClick(driver,
				driver.findElement(By.xpath("//*[@value='"+subscription+"']/../div[contains(@class,'AddButton')]")));
		
		
		Dailylog.logInfoDB(2,
				"Clicked on Add To Cart button.", Store,
				testName);
		
		if (Common.isElementExist(driver,
				By.xpath(".//*[@id='cta-builder-continue']/button"))) {
			b2cPage.B2C_PDP_AddToCart.click();
			Dailylog.logInfoDB(2, "Clicked on add to cart button.",
					Store, testName);
		}
		if (Common.isElementExist(driver,
				By.xpath(".//*[@id='CTO_addToCart']"))) {
			b2cPage.Product_AddToCartBtn.click();
			Dailylog.logInfoDB(2, "Clicked on add to cart button.",
					Store, testName);
		}
		Common.sleep(2000);
		Dailylog.logInfoDB(2, "go to cart page", Store,
				testName);
		Assert.assertTrue("add subscription pbp failed", Common.isElementExist(driver, By.xpath("//dd[contains(text(),'"+subscription+"')]")));
		
		
		
	}
	
	
	private void quickOrder(){
		
		 
		b2cPage.Navigation_CartIcon.click();
		if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
			b2cPage.Navigation_ViewCartButton.click();
		Common.sleep(2000);

		Dailylog.logInfoDB(3, "quick order", Store, testName);
		B2CCommon.clearTheCart(driver, b2cPage, testData);
		Common.sleep(2000);

		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(subscription);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
		try {
			Common.waitElementClickable(b2cPage.PageDriver,
					b2cPage.PageDriver.findElement(By.xpath("//div[@data-p-code='" + subscription + "']")), 10);
		} catch (NoSuchElementException e) {
			Assert.fail(subscription + "subscription product quick order failed!");
		}
		String quantity = driver.findElement(
				By.xpath("//*[@value='"+subscription+"']/../input[contains(@id,'quantity')]")).getAttribute("value");
		Integer.parseInt(quantity);
		Dailylog.logInfoDB(2, "quick order subscription quantity : " + quantity, Store,
				testName);
		Assert.assertTrue("quick order failed", quantity.equals("1"));
	}

}
