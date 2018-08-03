package TestScript.B2B;

import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19974Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	private String USProductNo = "4XB0G88776";
	private String newMail = "Auto19974@sharklasers.com";
	private String b2bUS = "";
	private String loginName = "LCTESTING@LENOVO.COM";
	private String loginPwd = "a12341234";

	public NA19974Test(String store) {
		this.Store = store;
		this.testName = "NA-19974";
	}

	private double getDoublePrice(String price) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		price = price.replace("[", "").replace("]", "").replace("add", "").replace("$", "").replace(",", "")
				.replace("HK", "").replace("SG", "").replace("*", "").trim().toString();
		double price_1 = Double.parseDouble(price);
		String price_2 = decimalFormat.format(price_1);
		Double doublePrice = Double.parseDouble(price_2);
		return doublePrice;
	}

	private void checkOrderPriceInHMC(String orderNum, String orderPrice) {
		// try {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		// B2BCommon.HMCOrderCheck(hmcPage, orderNum);
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(orderNum);
		hmcPage.Home_Order_OrderSearch.click();
		System.out.println("Order status in HMC: " + hmcPage.Home_Order_OrderStatus.getText());
		Assert.assertEquals("Completed", hmcPage.Home_Order_OrderStatus.getText(), "Order Status in HMC");
		Common.doubleClick(driver, driver.findElement(By.xpath("//*[text()='" + orderNum + "']")));
		String productTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalPrice')]")).getAttribute(
				"value");
		System.out.println("productTotal: " + getDoublePrice(productTotal));
		String taxTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalTax')]")).getAttribute("value");
		System.out.println("taxTotal: " + getDoublePrice(taxTotal));
		System.out.println("orderPrice: " + orderPrice);
		Assert.assertEquals(getDoublePrice(orderPrice), getDoublePrice(productTotal) + getDoublePrice(taxTotal));
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }

	}

	private void checkConfirmationEmail(String mailId, String orderNum, String totalPrice) throws InterruptedException {
		System.out.println("Checking email...");
		EMailCommon.goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Thread.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")))
					break;
			}
		}
		System.out.println("Order Email: "
				+ Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")));

		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: Auto19974@sharklasers.com");
			setManualValidateLog("TotalPrice: " + totalPrice);
		} else {
			driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).click();
			// get total price
			String totalPrice_Mail = driver.findElement(
					By.xpath("(//td[contains(text(),'Total')]/../../../../../td)[last()]//tr[2]/td")).getText();
			Assert.assertEquals(getDoublePrice(totalPrice), getDoublePrice(totalPrice_Mail));
		}
	}

	@Test(alwaysRun= true)
	public void NA19974(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			// Step 1 Login B2B
			b2bUS = testData.B2B.getLoginUrl();
			String[] segments = b2bUS.split("/");
			String dmu = segments[4];
			String unit = segments[7];
			b2bUS = b2bUS.replace(dmu, "1213626720").replace(unit, "1213823470");
			System.out.println(b2bUS);
			driver.get(b2bUS);

			B2BCommon.Login(b2bPage, loginName, loginPwd);
			// Step 4 DCG products add to cart successfully

			b2bPage.HomePage_CartIcon.click();
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				b2bPage.cartPage_emptyCartButton.click();
			}
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(USProductNo);
			b2bPage.cartPage_addButton.click();
			// Step 5 checkout
			b2bPage.cartPage_LenovoCheckout.click();
			Thread.sleep(3000);
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("add-delivery-address"));
			// Step 6 Fill in the shipping information, select one shipping
			// method.

			// Health Department and Community Health Center Attn: Aaron
			// Woolever/Brett May
			// 3010 Grand Avenue
			// Waukegan
			// 60085-2321
			b2bPage.shippingPage_ShipFName.clear();
			b2bPage.shippingPage_ShipFName.sendKeys("19974");
			b2bPage.shippingPage_ShipLName.clear();
			b2bPage.shippingPage_ShipLName.sendKeys("19974");
			if (b2bPage.shippingPage_ShipContactNumber.getAttribute("editable").contains("true")) {
				b2bPage.shippingPage_ShipContactNumber.clear();
				b2bPage.shippingPage_ShipContactNumber.sendKeys("1234567890");
			}
			b2bPage.shippingPage_email.clear();
			b2bPage.shippingPage_email.sendKeys(newMail);
			// Click continue
			b2bPage.shippingPage_ContinueToPayment.click();
			// new WebDriverWait(driver, 500)
			// .until(ExpectedConditions.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));
			Thread.sleep(2000);

			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}

			Thread.sleep(3000);

			// Step 7 Select bank deposit Payment, Type,then click continue.
			b2bPage.paymentPage_bankDeposit.click();
			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("19974");
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("19974");
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");
			String totalPrice_Checkout = driver.findElement(By.xpath("//dd[contains(@class,'pricingTotal')]"))
					.getText().toString();

			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			Thread.sleep(3000);
			// Step 8 Check "I have read and agree to the terms and conditions,
			// privacy policy and the warranty service policies"->
			// Click "Place Order"

			// check price in order summary review page
			String totalPrice_Review = driver.findElement(By.xpath("//dd[contains(@class,'totalValue')]")).getText()
					.toString();
			Assert.assertEquals(totalPrice_Checkout, totalPrice_Review);
			if (!b2bPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com")) {
				b2bPage.placeOrderPage_Terms.click();
				b2bPage.placeOrderPage_PlaceOrder.click();
				System.out.println("Clicked place order!");
			}

			String orderNum = b2bPage.orderPage_orderNumber.getText();
			System.out.println("orderNum is :" + orderNum);
			// Step 9 Check orders information
			String totalPrice_Order = driver.findElement(By.xpath("(//td[contains(@class,'orderTotals')])[3]"))
					.getText().toString();
			Assert.assertEquals(getDoublePrice(totalPrice_Review), getDoublePrice(totalPrice_Order));
			// Step 10 Check the order History

			driver.get(b2bUS + "/my-account/orders");
			// verify order exists 4290162505
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + orderNum + "']")));
			// verify order total price
			String totalPrice_OrderHis = driver.findElement(
					By.xpath("//*[text()='" + orderNum + "']/../..//*[@data-title='Order Total']/p")).getText();
			System.out.println("totalPrice_OrderHis: " + totalPrice_OrderHis);
			Assert.assertEquals(totalPrice_Review, totalPrice_OrderHis);
			// Check the order in HMC
			checkOrderPriceInHMC(orderNum, totalPrice_Review);
			// Check the order email
			checkConfirmationEmail(newMail, orderNum, totalPrice_Review);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
