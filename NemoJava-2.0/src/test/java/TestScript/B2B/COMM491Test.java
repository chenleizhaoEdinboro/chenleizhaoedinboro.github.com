package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM491Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public String homePageUrl;
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
	public String subscription = "RR00000003";
	public String plpUrl = "https://pre-c-hybris.lenovo.com/us/en/rootsubscriptionoptioncategory/sub-s/sub-sa/sub-sams/sub-sams01/c/Sub_SAMS01";
	public String pbpCTO  = "20JES1X200";

	public COMM491Test(String Store) {
		this.Store = Store;
		this.testName = "COMM-491";

	}

	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2b" })
	public void COMM491(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			// Step 5 add product into cart
			Dailylog.logInfoDB(5, "add product into cart", Store, testName);
			checkAdvancedShippingOptionInB2B();

			// Step 6 shipping page check rule
			Dailylog.logInfoDB(6, "check first name on shiiping page", Store, testName);
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());

			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);

			if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			Common.sleep(4000);

			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			(new WebDriverWait(driver, 500)).until(ExpectedConditions.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
			System.out.println("Go to Order page!");
			
			
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)){
				Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
				b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
			}
			
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.PAGE_UP).perform();
			if(Common.checkElementExists(driver, b2bPage.placeOrderPage_Terms, 20)){
				actions.sendKeys(Keys.PAGE_UP).perform();
				Common.scrollToElement(driver, b2bPage.placeOrderPage_Terms);
				b2bPage.placeOrderPage_Terms.click();		
			}		
			b2bPage.placeOrderPage_PlaceOrder.click();
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("Confirmation"));
//			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        
			Dailylog.logInfoDB(5, "Order Num : " + orderNum, Store, testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void checkAdvancedShippingOptionInB2B() {
		String b2bLoginURL = testData.B2B.getLoginUrl();
		driver.get(b2bLoginURL);
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		Common.sleep(3000);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(1500);
		if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
		}
		
		

		addToCardPDP();
	
		addToCardPLP();
		
		//TODO
		addToCardPBP();
		
		quickOrder();
		B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());

		Dailylog.logInfoDB(4, "Added a product into Cart", Store, testName);
		Common.sleep(2000);
		b2bPage.cartPage_LenovoCheckout.click();
		Common.sleep(3000);
		((JavascriptExecutor) driver).executeScript("scroll(0,500)");

		Common.sleep(1000);

	}

	private void addToCardPLP() {

		Common.NavigateToUrl(driver, Browser, plpUrl);
		Common.sleep(5000);
		if (Common.isElementExist(driver, By.xpath("//form[contains(@id,'" + subscription + "')]/button"))) {
			driver.findElement(By.xpath("//form[contains(@id,'" + subscription + "')]/button")).click();

			if (Common.isElementExist(driver, By.xpath("//div[@id='cboxContent']//div[@class='addtoCartCTA']/button"))) {
				b2bPage.addtoCartPOP.click();
				Dailylog.logInfoDB(6, "Clicked on Add to cart popup.", Store, testName);
			}
			Common.sleep(5000);
			if (Common.isElementExist(driver, By.xpath("(//*[@class='addtoCartCTA']/a[@class='goToCartCTA'])[last()]"))) {
				b2bPage.goToCartPop.click();
				Dailylog.logInfoDB(6, "Clicked on go to cart popup.", Store, testName);
			}
			Dailylog.logInfoDB(6, "go to cart page", Store, testName);

			try {
				Common.isElementExist(b2bPage.PageDriver,
						By.xpath("//span[contains(text(),'" + subscription + "')]"), 10);
			} catch (NoSuchElementException e) {
				Assert.fail(subscription + "subscription product quick order failed!");
			}
		} else {
			Assert.fail("no subscription product on plp page");
		}
		Common.sleep(1500);
		if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
		}

	}

	private void addToCardPDP() {
		String URL = testData.B2C.getHomePageUrl() + "/p/" + subscription;
		System.out.println("pdp URL is :" + URL);
		Common.NavigateToUrl(driver, Browser, URL);

		b2bPage.addToCartBtn.click();
		Common.sleep(1000);
		
		b2bPage.addtoCartPOP.click();
		Common.sleep(3000);
	
		b2bPage.goToCartPop.click();
		Common.sleep(5000);
		
		Dailylog.logInfoDB(5, "go to cart page", Store, testName);
		try {
			Common.isElementExist(b2bPage.PageDriver,
					By.xpath("//span[contains(text(),'" + subscription + "')]"), 10);
		} catch (NoSuchElementException e) {
			Assert.fail(subscription + "subscription product quick order failed!");
		}
		Common.sleep(1500);
		if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
		}
	
	}

	private void addToCardPBP() {
//		String URL = testData.B2C.getHomePageUrl() + "/p/" + pbpCTO;
//		System.out.println("pbp URL is :" + URL);
//		Common.NavigateToUrl(driver, Browser, URL);
//		String customize = "(.//*[@id='addToCartButtonTop']/span[@class='icon-cus'])";
//		Common.javascriptClick(driver, driver.findElement(By.xpath(customize + "[1]")));
//		Common.sleep(5000);
//
//		// click software ,subscription under softwares
//		Dailylog.logInfoDB(2, "add subscription to cto", Store, testName);
//
//		Common.javascriptClick(driver, b2cPage.PBP_SoftwareTag);
//		Common.javascriptClick(driver, driver
//				.findElement(By.xpath("//*[@value='" + subscription + "']/../div[contains(@class,'AddButton')]")));
//
//		Dailylog.logInfoDB(2, "Clicked on Add To Cart button.", Store, testName);
//
//		if (Common.isElementExist(driver, By.xpath(".//*[@id='cta-builder-continue']/button"))) {
//			b2cPage.B2C_PDP_AddToCart.click();
//			Dailylog.logInfoDB(2, "Clicked on add to cart button.", Store, testName);
//		}
//		if (Common.isElementExist(driver, By.xpath(".//*[@id='CTO_addToCart']"))) {
//			b2cPage.Product_AddToCartBtn.click();
//			Dailylog.logInfoDB(2, "Clicked on add to cart button.", Store, testName);
//		}
//		Common.sleep(2000);
//		Dailylog.logInfoDB(2, "go to cart page", Store, testName);
//		Assert.assertTrue("add subscription pbp failed",
//				Common.isElementExist(driver, By.xpath("//dd[contains(text(),'" + subscription + "')]")));

	}

	private void quickOrder() {

		

		Dailylog.logInfoDB(3, "quick order", Store, testName);
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(subscription);
		b2bPage.cartPage_addButton.click();

	
		try {
			Common.isElementExist(b2bPage.PageDriver,
					By.xpath("//span[contains(text(),'" + subscription + "')]"), 10);
		} catch (NoSuchElementException e) {
			Assert.fail(subscription + "subscription product quick order failed!");
		}
		Common.sleep(1500);
		
//		if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
//			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
//		}
	}

}
