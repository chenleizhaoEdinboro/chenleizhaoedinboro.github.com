package TestScript.B2B;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA19973Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	private String USProductNo = "30BGS01K00";
	private String newMail = Common.getDateTimeString() + "@sharklasers.com";
	private String b2bUS = "";
	private String loginName = "LCTESTING@LENOVO.COM";
	private String loginPwd = "a12341234";

	public NA19973Test(String store) {
		this.Store = store;
		this.testName = "NA-19973";
	}

	private double getDoublePrice(String price) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		price = price.replace("[", "").replace("]", "").replace("add", "").replace("$", "").replace(",", "")
				.replace("HK", "").replace("SG", "").replace("*", "").replace(" ", "").toString();
		double price_1 = Double.parseDouble(price);
		String price_2 = decimalFormat.format(price_1);
		Double doublePrice = Double.parseDouble(price_2);
		return doublePrice;
	}

	// Order 1 number is: 4290163847
	private String getOrderNum(String orderTexts) {
		String orderNum = orderTexts.substring(orderTexts.indexOf(":") + 1, orderTexts.length()).replaceAll(" ", "");
		System.out.println("Order number:" + orderNum + "..");
		return orderNum;
	}

	private void checkOrderPriceInHMC(String orderNum, String orderPrice) throws InterruptedException {
		// try {
		Thread.sleep(20000);
		driver.manage().deleteAllCookies();
		Thread.sleep(20000);
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
		String productTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalPrice')]"))
				.getAttribute("value");
		System.out.println("productTotal: " + getDoublePrice(productTotal));
		String taxTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalTax')]")).getAttribute("value");
		System.out.println("taxTotal: " + getDoublePrice(taxTotal));
		System.out.println("orderPrice: " + orderPrice);
		Double hmcTotal = getDoublePrice(productTotal) + getDoublePrice(taxTotal);
		System.out.println("hmcTotal: " + hmcTotal);
		Assert.assertEquals(getDoublePrice(orderPrice), hmcTotal);
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }

	}

	private void checkConfirmationEmail(String mailId, String orderNum1, String productID1, String productPrice1,
			String orderNum2, String productID2, String productPrice2, String totalPrice) throws InterruptedException {
		System.out.println("Checking email..." + mailId);
		EMailCommon.goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks for your orders')]"))) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Thread.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks for your orders')]")))
					break;
			}
		}
		System.out.println("Order Email: "
				+ Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks for your orders')]")));

		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks for your orders')]"))) {
			setManualValidateLog("Mailbox: " + mailId);
			setManualValidateLog("Order Number1: " + orderNum1 + "Product Number: " + productID1 + "Product price: "
					+ productPrice1);
			setManualValidateLog("Order Number2: " + orderNum2 + "Product Number: " + productID2 + "Product price: "
					+ productPrice2);
			setManualValidateLog("Order total price: " + totalPrice);
		} else {
			driver.findElement(By.xpath("//*[contains(text(),'Thanks for your orders')]")).click();
			Thread.sleep(10000);
			// Check order number
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + orderNum1 + "')]")).isDisplayed(),
					"Order in mail: " + orderNum1);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + orderNum2 + "')]")).isDisplayed(),
					"Order in mail: " + orderNum2);
			// Check product ID
			Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + productID1 + "')]")).isDisplayed(),
					"product1 in mail: " + productID1);
			Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + productID2 + "')]")).isDisplayed(),
					"product2 in mail: " + productID2);
			// Check product price no shipping
			List<WebElement> prices = driver.findElements(By.xpath("//*[contains(text(),'Price:')]/../..//td"));
			Double emailprice1 = getDoublePrice(prices.get(1).getText());
			System.out.println("emailprice1 " + emailprice1);
			Assert.assertEquals(emailprice1, getDoublePrice(productPrice1));
			Double emailprice2 = getDoublePrice(prices.get(3).getText());
			System.out.println("emailprice2 " + emailprice2);
			Assert.assertEquals(emailprice2, getDoublePrice(productPrice2));
			// get total price
			String totalPrice_Mail = driver
					.findElement(By.xpath("(//td[contains(text(),'Total')]/../../../../../td)[last()]//tr[4]/td"))
					.getText();
			Assert.assertEquals(totalPrice, totalPrice_Mail);
		}
	}

	@Test(alwaysRun = true)
	public void NA19973(ITestContext ctx) {

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
			// Step 4 DCG PCG products add to cart successfully
			b2bPage.HomePage_productsLink.click();

			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();

			List<WebElement> partNumber = driver
					.findElements(By.xpath("(//*[@id='resultList']/div/div/div[2]/div[1]/b/dl/dd[1])"));
			ArrayList<String> al = new ArrayList<String>();
			for (int x = 1; x <= partNumber.size(); x++) {
				String str = driver
						.findElement(By.xpath("(//*[@id='resultList']/div/div/div[2]/div[1]/b/dl/dd[1])[" + x + "]"))
						.getText().toString();
				al.add(str);
			}
			System.out.println("pcg product number is :" + al);
			
			

			b2bPage.HomePage_CartIcon.click();
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				b2bPage.cartPage_emptyCartButton.click();
			}
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(USProductNo);
			b2bPage.cartPage_addButton.click();
			Thread.sleep(3000);
			b2bPage.cartPage_quickOrder.clear();
			String mtmproNUM = al.get(0);
			b2bPage.cartPage_quickOrder.sendKeys(mtmproNUM);
			b2bPage.cartPage_addButton.click();
			Common.sleep(3000);
			By noStockMess = By.xpath("//*[contains(text(),'No Stock for the Product') or contains(text(),'is invalid')]");
			for(int i=1;i<al.size();i++){
				if(Common.checkElementDisplays(driver, noStockMess, 5)){
					mtmproNUM = al.get(i);
					b2bPage.cartPage_quickOrder.clear();
					b2bPage.cartPage_quickOrder.sendKeys(mtmproNUM);
					b2bPage.cartPage_addButton.click();
				}else
					break;
			}
			

			// Step 5 checkout
			b2bPage.cartPage_LenovoCheckout.click();
			Thread.sleep(3000);
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("add-delivery-address"));
			// Step 6 Fill in the shipping information, select one shipping
			// method. Click continue
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Edit')]"))) {
				b2bPage.shippingPage_EditCart.click();
			}

			b2bPage.shippingPage_ShipFName.clear();
			b2bPage.shippingPage_ShipFName.sendKeys("19973");
			b2bPage.shippingPage_ShipLName.clear();
			b2bPage.shippingPage_ShipLName.sendKeys("19973");
			if (b2bPage.shippingPage_ShipContactNumber.getAttribute("editable").contains("true")) {
				b2bPage.shippingPage_ShipContactNumber.clear();
				b2bPage.shippingPage_ShipContactNumber.sendKeys("1234567890");
			}
			b2bPage.shippingPage_email.clear();
			System.out.println("newMail :" + newMail);
			b2bPage.shippingPage_email.sendKeys(newMail);

			b2bPage.shippingPage_ContinueToPayment.click();

			Thread.sleep(2000);

			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}

			Thread.sleep(3000);

			// Step 7 Select Card Payment then type data and continue
			Actions actions = new Actions(driver);
			actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click().perform();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// select MasterCard
			// b2bPage.paymentPage_CardType.click();
			// new WebDriverWait(driver, 500).until(ExpectedConditions
			// .elementToBeClickable(b2bPage.paymentPage_MasterCard));
			// b2bPage.paymentPage_MasterCard.click();
			driver.switchTo().frame(b2bPage.creditCardFrame);
			b2bPage.MasterCard.click();
			b2bPage.paymentPage_CardNumber.clear();
			b2bPage.paymentPage_CardNumber.sendKeys("5555555555554444");
			b2bPage.paymentPage_ExpiryMonth.clear();
			b2bPage.paymentPage_ExpiryMonth.sendKeys("06");
			b2bPage.paymentPage_ExpiryYear.clear();
			b2bPage.paymentPage_ExpiryYear.sendKeys("20");
			b2bPage.paymentPage_SecurityCode.clear();
			b2bPage.paymentPage_SecurityCode.sendKeys("132");
			b2bPage.PageDriver.switchTo().defaultContent();
			b2bPage.paymentPage_NameonCard.clear();
			b2bPage.paymentPage_NameonCard.sendKeys("LIXE");

			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("19973");
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("19973");
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");

			String totalPrice_Checkout = driver.findElement(By.xpath("//dd[contains(@class,'pricingTotal')]")).getText()
					.toString();
//			Common.scrollToElement(driver, b2bPage.OrderSummary_AcceptTermsCheckBox);
			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			Thread.sleep(3000);

			// Step 8 1> Show message "Please note: Your items will be processed
			// and shipped separately."
			Assert.assertTrue(driver
					.findElement(By
							.xpath("//*[contains(text(),'Please note that this transaction will be split into two orders for payment and processing.')]"))
					.isDisplayed(), "split-order-remind");
			// 2> Seperate the order to Order #1 and Order #2
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Order #1')]")).isDisplayed(),
					"Order #1");
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Order #2')]")).isDisplayed(),
					"Order #2");
			// Get product number and price
			List<WebElement> products = driver
					.findElements(By.xpath("//*[@class='checkout-review-item-partNumber']//span"));
			String productNum1 = products.get(0).getText();
			String productNum2 = products.get(1).getText();
			List<WebElement> prices = driver.findElements(By.xpath("//*[@class='checkout-review-item-pricing-value']"));
			String price1 = prices.get(0).getText();
			String price2 = prices.get(1).getText();
			System.out.println("Order #1 " + productNum1 + " : " + price1);
			System.out.println("Order #2 " + productNum2 + " : " + price2);

			String totalPrice_Review = driver.findElement(By.xpath("//dd[contains(@class,'totalValue')]")).getText()
					.toString();
			Assert.assertEquals(totalPrice_Checkout, totalPrice_Review);
			// Step 9 Check "I have read and agree to the terms and conditions,
			// privacy policy and the warranty service policies"->
			// Click "Place Order"
			if (!b2bPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com")) {
				b2bPage.placeOrderPage_Terms.click();
				b2bPage.placeOrderPage_PlaceOrder.click();
				System.out.println("Clicked place order!");
			}
			// Step 10 Check orders information split by PCG or DCG group order
			// Assertions Show separated message
			Assert.assertTrue(driver
					.findElement(By
							.xpath("//*[contains(text(),'Please note that this transaction has been separated into two orders for payment and processing.')]"))
					.isDisplayed(), "split-order-remind");
			// Assertions The order summary split to two order
			List<WebElement> orders = driver.findElements(By.xpath("//div[contains(@class,'giantOrderNoColor')]//h1"));
			Assert.assertEquals(orders.size(), 2, "seperated 2 orders in thanks you page");

			String order1 = getOrderNum(orders.get(0).getText());
			String order2 = getOrderNum(orders.get(1).getText());

			// two split order price and one total price
			prices = driver.findElements(By.xpath("//td[contains(@class,'orderTotals-total')]"));

			// price with shipping

			String priceTotal1 = prices.get(0).getText();
			String priceTotal2 = prices.get(1).getText();
			System.out.println("Order #1 " + order1 + " : " + priceTotal1);
			System.out.println("Order #2 " + order2 + " : " + priceTotal2);
			// Assertions The total price is same with Review page.
			String totalPrice_Order = prices.get(2).getText();
			System.out.println("totalPrice_Order: " + getDoublePrice(totalPrice_Order));
			System.out.println("totalPrice_Review: " + getDoublePrice(totalPrice_Review));
			Assert.assertEquals(getDoublePrice(totalPrice_Review), getDoublePrice(totalPrice_Order));

			// Step 11 Check the order History
			driver.get(b2bUS + "/my-account/orders");
			// verify order exists
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + order1 + "']")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + order2 + "']")));
			// Step 14-16 Check the order in HMC
			checkOrderPriceInHMC(order1, price1);
			checkOrderPriceInHMC(order2, price2);
			// Step 12-13 Check the order email

			checkConfirmationEmail(newMail, order1, productNum1, price1, order2, productNum2, price2, totalPrice_Order);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
