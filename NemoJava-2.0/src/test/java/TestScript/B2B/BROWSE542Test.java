package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE542Test extends SuperTestClass {
	String unit;
	String b2bLoginUrl;
	String b2bHomeUrl;
	String Subscription;
	HMCPage hmcPage;
	B2BPage b2bPage;
	String date = Common.getDateTimeString();

	public BROWSE542Test(String Store, String Subscription) {
		this.Store = Store;
		this.testName = "BROWSE-542";
		this.Subscription = Subscription;
		String date = Common.getDateTimeString();
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "browsegroup","product", "p2", "b2b" })
	public void BROWSE542(ITestContext ctx) {
		try {
			
			//String testUrl = "https://pre-c-hybris.lenovo.com/le/1213071828/us/en/1213071828";

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			By byLocator11 = By
					.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			// check billing cycle on PDP
			driver.get(testData.B2B.getHomePageUrl() + "/p/" + Subscription);
			/*driver.get(testUrl);
			B2BCommon.Login(b2bPage, "rrbuyer@yopmail.com", testData.B2B.getDefaultPassword());
			// check billing cycle on PDP
			driver.get(testUrl + "/p/" + Subscription);*/
			Assert.assertTrue(b2bPage.PDP_Subscription_Price.getText().contains("/"), "check the billing cycle on pdp");
			// check billing cycle on add to cart pop
			b2bPage.AddForm_AddToCartBtn.click();
			Assert.assertTrue(b2bPage.PDP_addToCartPOP_Price.getText().contains("/"),
					"check the billing cycle on addtocart pop");
			b2bPage.productPage_AlertAddToCart.click();
			Thread.sleep(8000);
			b2bPage.ProductPage_AlertGoToCart.click();
			// check billing cycle on plp page
			/*driver.get(testData.B2B.getHomePageUrl() + "/p/" + Subscription);
			b2bPage.PDP_superCategoryLink.click();
			for (int i = 0; i < b2bPage.PLP_Subscription_Prices.size(); i++) {
				Assert.assertTrue(b2bPage.PLP_Subscription_Prices.get(i).getText().contains("/"),
						"check the billing cycle on plp");
			}*/
			// check billing cycle on cart page
			b2bPage.HomePage_CartIcon.click();
			B2BCommon.clearTheCart(driver, b2bPage);
			B2BCommon.addProduct(driver, b2bPage, Subscription);

			Assert.assertTrue(b2bPage.cartPage_FirstItermPrice.getText().contains("/"),
					"check the billing cycle on cart page");

			// check billing cycle on save cart history page
			b2bPage.cartPage_saveCartButton.click();
			b2bPage.cartPage_privateSaveCartn.click();
			b2bPage.cartPage_realsavecartname.clear();
			b2bPage.cartPage_realsavecartname.sendKeys(date);
			b2bPage.cartPage_SaveCart_save.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("save-carts"));

			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//h3[text()='" + date + "']")));
			driver.findElement(By.xpath("//h3[text()='" + date + "']/../../td[@data-title='viewCart']/a")).click();
			Assert.assertTrue(b2bPage.SavedCartPage_FirstItemPrice.getText().contains("/"),
					" check billing cycle on cart history page line item");
			Assert.assertTrue(b2bPage.SavedCartPage_FirstTotalPrice.getText().contains("/"),
					" check billing cycle on cart history page sub total");

			Common.javascriptClick(driver, b2bPage.cartDetailsPage_openCart);

			Thread.sleep(10000);
			//check billing cycle on checkout pages
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("cart"));
			b2bPage.cartPage_LenovoCheckout.click();
			Thread.sleep(3000);
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("add-delivery-address"));
			
			Assert.assertTrue(b2bPage.ShippingPage_FirstItemPrice.getText().contains("/"));
			Assert.assertTrue(b2bPage.ShippingPage_FirstTotalPrice.getText().contains("/"));
			
			// check billing cycle on shipping page
			if(Store.toLowerCase().equals("us")){
				B2BCommon.fillB2BShippingInfo(driver,b2bPage,"us","testFname","testLname","Georgia","1535 Broadway","New York","New York","10036-4077","2129450100");
			}else if(Store.toLowerCase().equals("au")){
				B2BCommon.fillB2BShippingInfo(driver,b2bPage,"au","testFname","testLname","adobe_global","62 Streeton Dr","RIVETT","Australian Capital Territory","2611","2123981900");
			}
			Thread.sleep(3000);
			b2bPage.shippingPage_ContinueToPayment.click();
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions
							.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));

			Thread.sleep(3000);
			if (Common.isElementExist(driver, By.id("group-shipping-address"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			b2bPage.paymentPage_PurchaseOrder.click();
			Thread.sleep(8000);
			b2bPage.paymentPage_purchaseNum.clear();
			b2bPage.paymentPage_purchaseNum.sendKeys("123456");
			b2bPage.purchaseDate.click();
			driver.findElements(byLocator11)
					.get(driver.findElements(byLocator11).size() - 1).click();
			Thread.sleep(8000);
			Assert.assertTrue(b2bPage.PaymentPage_FirstItemPrice.getText().contains("/"));
			Assert.assertTrue(b2bPage.PaymentPage_FirstTotalPrice.getText().contains("/"));
			B2BCommon.fillDefaultB2bBillingAddress (driver,b2bPage, testData);
			
			// check billing cycle on summary page
			Assert.assertTrue(driver.getCurrentUrl().endsWith("summary"));
			if(Common.checkElementExists(driver, b2bPage.placeOrderPage_ResellerID, 3)){
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
	
			}
			Assert.assertTrue(b2bPage.SummaryPage_FirstItemPrice.getAttribute("innerText") .contains("/"));
			System.out.println(b2bPage.SummaryPage_FirstItemPrice.getAttribute("innerText"));
			Assert.assertTrue(b2bPage.SummaryPage_FirstTotalPrice.getText().contains("/"));
			Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_PlaceOrder.click();
			//check billing cycle on thank you page
			Thread.sleep(10000);
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("Confirmation"));
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstItemPrice.getText().contains("/"));
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstTotalPrice.getText().contains("/"));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
