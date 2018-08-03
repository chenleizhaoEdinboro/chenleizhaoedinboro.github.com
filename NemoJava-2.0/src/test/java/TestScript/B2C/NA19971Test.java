package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19971Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	String partNum;

	public NA19971Test(String store, String productDCG) {
		this.Store = store;
		this.partNum = productDCG;
		this.testName = "NA-19971";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA19971(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			String mailID = "Auto19971@sharklasers.com";
			// gate keeper
			driver.get(testData.B2C.getHomePageUrl());
			if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
				B2CCommon.handleGateKeeper(b2cPage, testData);
			} else {
				B2CCommon.handleGateKeeper(b2cPage, testData);
				// login B2C
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			Dailylog.logInfoDB(1, "Login B2C site.", Store, testName);

			// empty cart
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				driver.findElement(By.xpath("//a[contains(text(),'Empty cart')]")).click();
			}
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			// add DCG product into cart
			B2CCommon.addPartNumberToCart(b2cPage, partNum);
			Dailylog.logInfoDB(3, "Add to cart DCG: " + partNum, Store, testName);
			// add PCG product into cart
			// B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			// Dailylog.logInfoDB(4, "Add to cart PCG: " + testData.B2C.getDefaultMTMPN(),
			// Store, testName);
			B2CCommon.addPartNumberToCart(b2cPage, "40A40090US");
			Dailylog.logInfoDB(4, "Add to cart PCG: 40A40090US", Store, testName);

			// lenovo checkout
			b2cPage.Cart_CheckoutButton.click();
			Dailylog.logInfoDB(5, "Click Lenovo Checkout", Store, testName);
			if (Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5))
				b2cPage.Shipping_editAddress.click();
			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), mailID);
			Thread.sleep(3000);
			// continue
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
			// UI of validate info is different in each region
			if (Common.isElementExist(driver, By.id("address_sel"))) {
				// US
				Select dropdown = new Select(driver.findElement(By.id("address_sel")));
				dropdown.selectByIndex(1);
				b2cPage.Shipping_AddressMatchOKButton.click();
			} else if (Common.isElementExist(driver, By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
				if (b2cPage.Shipping_validateAddressItem.isDisplayed()) {
					// USEPP
					b2cPage.Shipping_validateAddressItem.click();
				}
				// USBPCTO
				b2cPage.Shipping_AddressMatchOKButton.click();
			}
			Dailylog.logInfoDB(6, "Fill shipping information", Store, testName);

			B2CCommon.fillPaymentAddressInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			purchaseOrder();
			// continue
			Thread.sleep(500);
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(7, "Purchase Order payment", Store, testName);

			// Assertions Show message
			// "Please note: Your items will be processed and shipped separately."
//			Assert.assertTrue(Common.isElementExist(driver, By.xpath(
//					"//*[contains(text(),'Please note: Your items will be processed and shipped separately.')]")),
//					"Check Review&Purchase page: is split-order-remind Displayed");
			// Assertions Seperate the order to Order #1 and Order #2
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[contains(text(),'Order #1')]")),
					"Check Review&Purchase page:is Order #1 displayed");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[contains(text(),'Order #2')]")),
					"Check Review&Purchase page:is Order #2 displayed");
			Dailylog.logInfoDB(8, "Check Review&Purchase page", Store, testName);


			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					b2cPage.OrderSummary_AcceptTermsCheckBox);
			try {
				if (Common.isElementExist(driver, By.xpath("//*[@id='repId']"))) {
					// driver.findElement(By.xpath("//*[@id='repId']")).clear();
					driver.findElement(By.xpath("//*[@id='repId']")).sendKeys(testData.B2C.getRepID());
				}
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {
				System.out.println("StaleElementReferenceException");
				if (Common.isElementExist(driver, By.xpath("//*[@id='repId']"))) {
					// driver.findElement(By.xpath("//*[@id='repId']")).clear();
					driver.findElement(By.xpath("//*[@id='repId']")).sendKeys(testData.B2C.getRepID());
				}
			}

			// place order
			Thread.sleep(500);
			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(9, "Place order", Store, testName);

			// Assertions Show separated message
//			Assert.assertTrue(driver.findElement(By.xpath(
//					"//*[contains(text(),'Please note: Your order has been separated into two orders for processing and shipping.')]"))
//					.isDisplayed(), "Check thankyou page:is split-order-remind displayed");
			// Assertions The order summary split to two order
			String order1;
			String order2;
			List<WebElement> orders;
			if (Common.checkElementExists(driver, driver.findElement(By.xpath(".//*[@class='thankYouForYourOrder']/div[1]/div[2]/span[2]/input")), 5)){
				orders = driver
						.findElements(By.xpath(".//*[@class='thankYouForYourOrder']/div[1]/div[2]/span[2]/input"));
				order1 = orders.get(0).getAttribute("value");
				order2 = orders.get(1).getAttribute("value");
			}else{
				orders = driver
						.findElements(By.xpath("//*[@class='thankyoupage-order-group-title']//span"));
				order1 = orders.get(0).getText();;
				order2 = orders.get(1).getText();;
			}
			String price1;
			String price2;
			List<WebElement> prices ;
			if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='mainContent']//div[@class='summary-item total']/div[2]"), 5)){
				prices = driver.findElements(By.xpath(".//*[@id='mainContent']//div[@class='summary-item total']/div[2]"));
			}else{
				prices = driver.findElements(By.xpath(".//*[@id='cart-price']/table/tbody/tr[2]/td[3]"));
			}
			price1 = prices.get(0).getText();
			price2 = prices.get(1).getText();
			System.out.println("Order #1 " + order1 + " : " + price1);
			System.out.println("Order #2 " + order2 + " : " + price2);


			Dailylog.logInfoDB(10, "Check orders information split by PCG or DCG group order", Store, testName);

			// Check the order History
			driver.get(testData.B2C.getHomePageUrl() + "/my-account/orders");
			// verify order exists
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + order1 + "']")),
					"is order " + order1 + " exists in order history");
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + order2 + "']")),
					"is order " + order2 + " exists in order history");
			Dailylog.logInfoDB(11, "Check the order History", Store, testName);

			// Check the order in HMC
			checkOrderPriceInHMC(order1, price1);
			checkOrderPriceInHMC(order2, price2);
			Dailylog.logInfoDB(13, "Check the order in HMC", Store, testName);

			// Check the order email
			//checkConfirmationEmail(mailID, order1, price1, price3, totalPrice_Order, product1, 1);
			driver.manage().deleteAllCookies();
			//checkConfirmationEmail(mailID, order2, price2, price4, totalPrice_Order, product2, 2);
			Dailylog.logInfoDB(13, "Check the order Email", Store, testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void checkOrderPriceInHMC(String orderNum, String orderPrice) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		// to avoid mail status delay in HMC
		Thread.sleep(18000);
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(orderNum);
		hmcPage.Home_Order_OrderSearch.click();
		Common.sleep(8000);
		try{
			Common.waitElementVisible(driver, hmcPage.Home_Order_OrderStatus);
		}catch(Exception e) {
			hmcPage.Home_Order_OrderSearch.click();
			Common.sleep(8000);
			Common.waitElementVisible(driver, hmcPage.Home_Order_OrderStatus);
		}
		System.out.println("Order status in HMC: " + hmcPage.Home_Order_OrderStatus.getText());
		Assert.assertEquals(hmcPage.Home_Order_OrderStatus.getText(), "Completed", "Order Status in HMC: " + orderNum);
		Common.doubleClick(driver, driver.findElement(By.xpath("//*[text()='" + orderNum + "']")));
		String productTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalPrice')]"))
				.getAttribute("value");
		System.out.println("productTotal: " + getDoublePrice(productTotal));
		String taxTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalTax')]")).getAttribute("value");
		System.out.println("taxTotal: " + getDoublePrice(taxTotal));
		System.out.println("orderPrice: " + orderPrice);
		DecimalFormat df = new DecimalFormat("#.00");
		Assert.assertEquals(getDoublePrice(df.format(getDoublePrice(productTotal))),
				getDoublePrice(orderPrice), "price in HMC should be the same with price in order page");

	}

	public void checkConfirmationEmail(String mailId, String orderNum, String productPrice, String productTaxPrice,
			String totalPrice, String productID, int mailCount) throws InterruptedException {
		System.out.println("Checking email..." + mailId);
		EMailCommon.goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks_for_your_orders')]"))
				&& mailCount != 1) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Thread.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks_for_your_orders')]")))
					break;
			}
		}
		System.out.println("Order Email: "
				+ Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks_for_your_orders')]")));

		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'Thanks_for_your_orders')]"))) {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: " + mailId);
			setManualValidateLog("Product Number: " + productID);
			setManualValidateLog("Product price: " + productPrice);
			setManualValidateLog("Product price with tax: " + productTaxPrice);
			setManualValidateLog("Order total price: " + totalPrice);
		} else {
			driver.findElement(By.xpath("//*[contains(text(),'Thanks_for_your_orders')]")).click();
			Thread.sleep(10000);
			// Check order number
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).isDisplayed(),
					"Order in mail: " + orderNum);
			// Check product ID
			List<WebElement> products = driver.findElements(By.xpath("//*[contains(text(),'Part No:')]/../..//td"));
			Assert.assertEquals(products.get(2 * mailCount - 1).getText(), productID, "Product ID in mail");
			// Check product price
			List<WebElement> prices = driver.findElements(By.xpath("//*[contains(text(),'Price:')]/../..//td"));
			Assert.assertEquals(getDoublePrice(prices.get(2 * mailCount - 1).getText()), getDoublePrice(productPrice),
					"Product price in mail");
			// get total price
			String totalPrice_Mail = driver.findElement(By.xpath("(//*[text()='Total:']/../../../../..//td)[last()]"))
					.getText();

			Assert.assertTrue(totalPrice_Mail.indexOf(totalPrice) >= 0 || totalPrice.indexOf(totalPrice_Mail) > 0,
					"total price in mail: " + totalPrice_Mail + " total price order: " + totalPrice);
			// Assert.assertEquals(totalPrice, totalPrice_Mail, "total price in mail");
		}
	}

	public void purchaseOrder() throws InterruptedException {
		if (Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER"))) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.payment_PurchaseOrder);
			// b2cPage.payment_PurchaseOrder.click();
			Thread.sleep(5000);
			if (Common.checkElementDisplays(driver, b2cPage.payment_purchaseNum, 5000)) {
				b2cPage.payment_purchaseNum.sendKeys("1234567890");
				b2cPage.payment_purchaseDate.click();
				b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);
				// Common.sleep(5000);
				// Common.KeyEventEnter();
			}
		} else {
			Assert.fail("No purchase order payment on B2C! Please set in HMC and run again!");
		}
	}

	public double getDoublePrice(String price) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		price = price.replace("[", "").replace("]", "").replace("add", "").replace("$", "").replace(",", "")
				.replace("HK", "").replace("Grand Total:", "").replace("SG", "").trim().toString();
		double price_1 = Double.parseDouble(price);
		String price_2 = decimalFormat.format(price_1);
		Double doublePrice = Double.parseDouble(price_2);
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(doublePrice);
		System.out.println(df.format(doublePrice));
		return Double.parseDouble(df.format(doublePrice));
	}

	/*
	 * public void addPCGToCart(String testUrl) throws InterruptedException {
	 * driver.get(testData.B2C.getHomePageUrl() + testUrl); List<WebElement> series
	 * = driver.findElements(By.xpath("//*[contains(text(),'View Series')]"));
	 * System.out.println("Series number: " + series.size()); for (int i = 0; i <
	 * series.size(); i++) { series =
	 * driver.findElements(By.xpath("//*[contains(text(),'View Series')]"));
	 * series.get(i).click(); List<WebElement> learnmore =
	 * driver.findElements(By.xpath("//*[contains(text(),'Learn more')]"));
	 * System.out.println("learnmore number: " + learnmore.size()); String
	 * thirdLevel = driver.getCurrentUrl(); for (int j = 0; j < learnmore.size();
	 * j++) { learnmore =
	 * driver.findElements(By.xpath("//*[contains(text(),'Learn more')]"));
	 * learnmore.get(j).click(); List<WebElement> acAddToCart = driver
	 * .findElements(By
	 * .xpath("//*[contains(@class,'productListing')]//form//*[@id='addToCartButtonTop' and not(@disabled)]"
	 * )); System.out.println("active add to cart number: " + acAddToCart.size());
	 * String plpUrl = driver.getCurrentUrl(); for (int x = 0; x <
	 * acAddToCart.size(); x++) { acAddToCart = driver .findElements(By
	 * .xpath("//*[contains(@class,'productListing')]//form//*[@id='addToCartButtonTop' and not(@disabled)]"
	 * )); ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	 * acAddToCart.get(x)); Thread.sleep(1000);
	 * driver.get(testData.B2C.getHomePageUrl() + "/cart"); List<WebElement>
	 * cartItems = driver.findElements(By.xpath("//div[@class='cart-item']"));
	 * Thread.sleep(1000); if (cartItems.size() == 2) { break; } else {
	 * driver.get(plpUrl); } } if (driver.getCurrentUrl().indexOf("cart") > 0) {
	 * break; } else { driver.get(thirdLevel); } } if
	 * (driver.getCurrentUrl().indexOf("cart") > 0) { break; } else {
	 * driver.get(testData.B2C.getHomePageUrl() + testUrl); } }
	 * 
	 * }
	 */
}
