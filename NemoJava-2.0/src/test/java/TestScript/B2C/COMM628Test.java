package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.FEPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;
import ch.qos.logback.core.joran.action.Action;
import junit.framework.Assert;

public class COMM628Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	FEPage fePage;
	PartSalesPage PSPage;
	private String test_cb_1;
	private String sub_item_a;
	private Actions act;

	

	public COMM628Test(String store){
		this.Store = store;
		this.testName = "COMM-628";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM628(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			fePage = new FEPage(driver);
			act = new Actions(driver);
			
			//test-cb-1(20LDCTO1WWENUS0/20LFCTO1WWENUS0,81CU000TUS)CTO-CTO,MTM
			// test-cb-2(81CU000RUS/81CU000SUS,4XD0K25029)MTM-MTM,ACCESSORY
			//step~1
			test_cb_1 = "test-cb-1";
			Dailylog.logInfoDB(1, "Go to website", Store, testName);
			
			//step~2
			Dailylog.logInfoDB(2, "Set toggle 'LockConBundle' as YES", Store, testName);
			setLockConBundleDefault();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open()");
			Common.switchToWindow(driver, 1);
		
			//step~3&4
			Dailylog.logInfoDB(3, "Add a CB to cart.", Store, testName);
			sub_item_a = "81CU000TUS";
			
			//add CB to cart
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + test_cb_1);
			Common.sleep(2000);
			closePromotion(driver, b2cPage);
			WebElement ConvenienceBundle_PBPrice = driver
					.findElement(By.xpath("(//dd[@class='saleprice pricingSummary-priceList-value priceDataHighlight'][2])"));
			double page_price = GetPriceValue(ConvenienceBundle_PBPrice.getText());
			System.out.println("page_price:" + page_price);
			Common.scrollToElement(driver, b2cPage.ConvenienceBundle_Add);
			Common.javascriptClick(driver, b2cPage.ConvenienceBundle_Add);
			b2cPage.cto_AddToCartButton.click();
	
			
			//add sub item into cart
			Dailylog.logInfoDB(4, "Add a sub item (item-a) to cart.", Store, testName);
			Common.sleep(3000);
			//verify edit button does not exist
			Assert.assertFalse("edit button exist", !Common.isElementExist(driver, By.xpath("//div[@class='editTextBtn']")));
			B2CCommon.addPartNumberToCart(b2cPage, sub_item_a);
			
			//step~5
			Dailylog.logInfoDB(5, "Check the display in Cart page", Store, testName);
			//1>will verify in step 5
			//2>Sub-component quantity can't be edited.
			By sub_quantity = By.xpath("//*[@class='child_entries']/div[@class='cart-item-addedItem-quantity']");
			System.out.println("step5.2");
			Assert.assertTrue("Sub-component quantity can be edited", Common.checkElementDisplays(driver, sub_quantity, 5));
			//3>"Remove button", "Save for later" button is shown
			System.out.println("step5.3");
			Assert.assertTrue(Common.checkElementDisplays(driver, fePage.removeLink, 5));
			WebElement saveForLater = driver.findElement(By.linkText("Save for Later"));
			System.out.println("step5.3.2");
			Assert.assertTrue(Common.checkElementDisplays(driver, saveForLater, 5));
			//4>
			//5>The separate sub item(item-a) can update quantity.
			System.out.println("step5.5");
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//input[@id='QuantityProduct_3']"), 3));
			//6>
			
			//step~6
			Dailylog.logInfoDB(6, "Update the CB quantity to 2", Store, testName);
			double cartSubTotalPricePre = GetPriceValue(b2cPage.ConvenienceBundle_SubTotal.getText());
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("2");
			Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
			Common.sleep(3000);
			double cartSubTotalPriceUpdated = GetPriceValue(b2cPage.ConvenienceBundle_SubTotal.getText());
			System.out.println(cartSubTotalPriceUpdated);
			System.out.println(cartSubTotalPricePre);
			System.out.println(page_price);
			Assert.assertEquals(cartSubTotalPriceUpdated,
					cartSubTotalPricePre + page_price,cartSubTotalPriceUpdated);
			
			//step~7
			Dailylog.logInfoDB(7, "Click Checkout button", Store, testName);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			Common.sleep(3000);
			double summary_price = GetPriceValue(fePage.billingCycleOnPaymentPage.getText());
			Assert.assertEquals(cartSubTotalPriceUpdated, summary_price, cartSubTotalPriceUpdated);
			
			//step~8
			Dailylog.logInfoDB(8, "proceed to thankyou page", Store, testName);
			//fill shipping
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			Common.sleep(2000);
			if(b2cPage.Shipping_FirstNameTextBox.getText().isEmpty()){
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
		
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			//fill payment info
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Common.sleep(10000);
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
			Common.sleep(2000);
			double thankyou_price = GetPriceValue(fePage.billingCycleOnPaymentPage.getText());
			System.out.println(thankyou_price);
			System.out.println(summary_price);
			Assert.assertEquals("price is not correct", thankyou_price, summary_price);
			String orderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			
			//step~9
			Dailylog.logInfoDB(9, "check the display order detail in Account.order number is:" + orderNumber, Store, testName);
			String myAccountUrl = testData.B2C.getHomePageUrl() + "/my-account";
			
			driver.get(myAccountUrl);
			
			b2cPage.MyAccount_ViewOrderHistoryLink.click();
			
			//click view order status link
			
			driver.findElement(
					By.xpath(".//*[@id='accountOrderHistory']//a[contains(.,'"
							+ orderNumber + "')]")).click();
			
			double orderThankyou_SubtotalPrice = GetPriceValue(b2cPage.OrderThankyou_SubtotalPrice.getText());
			Assert.assertEquals("price is not correct on orderhistory", orderThankyou_SubtotalPrice, cartSubTotalPriceUpdated);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	public static double GetPriceValue(String Price) {

		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\￥", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("₹", "");
		Price = Price.trim();
		double priceValue;
		priceValue = Double.parseDouble(Price);
		return priceValue;
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	
	public void setLockConBundleDefault(){
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		WebElement LockConBundle = driver.findElement(By.xpath("//input[contains(@id,'Attribute[B2CUnit.zLockConBundle]]_true')]"));
		LockConBundle.click();
		hmcPage.Common_SaveButton.click();
		Common.sleep(3000);	
	}
	
}
