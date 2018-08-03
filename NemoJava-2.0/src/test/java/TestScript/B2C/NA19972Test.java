package TestScript.B2C;

import java.text.DecimalFormat;

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

public class NA19972Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	String partNum;

	public NA19972Test(String store, String productDCG) {
		this.Store = store;
		this.partNum = productDCG;
		this.testName = "NA-19972";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA19972(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
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
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			// add DCG product into cart
			B2CCommon.addPartNumberToCart(b2cPage, partNum);
			Dailylog.logInfoDB(5, "add DCG product into cart", Store, testName);

			// lenovo checkout
			b2cPage.Cart_CheckoutButton.click();
			Dailylog.logInfoDB(6, "lenovo checkout", Store, testName);
			if (Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5))
				b2cPage.Shipping_editAddress.click();
			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					"Auto19972@sharklasers.com");
			Thread.sleep(3000);
			// continue
			WebElement continueButton = driver.findElement(By.id("checkoutForm-shippingContinueButton"));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", continueButton);
			// UI of validate info is different in each region
			if (Common.isElementExist(driver, By.id("address_sel"))) {
				// US
				Select dropdown = new Select(driver.findElement(By.id("address_sel")));
				dropdown.selectByIndex(1);
				b2cPage.Shipping_AddressMatchOKButton.click();
			} else if (Common.isElementExist(driver, By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
				if (driver.findElement(By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]"))
						.isDisplayed()) {
					// USEPP
					driver.findElement(By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]")).click();
				}
				// USBPCTO
				b2cPage.Shipping_AddressMatchOKButton.click();
			}
			Dailylog.logInfoDB(7, "fill shipping information", Store, testName);

			B2CCommon.fillPaymentAddressInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			// Multiple Credit Cards payment
			if (Common.isElementExist(driver, By.id("PaymentTypeSelection_TWOCARDS")))
				if (Common.checkElementExists(driver, b2cPage.Payment_multipleCreditCards, 5))
					executor.executeScript("arguments[0].click();", b2cPage.Payment_multipleCreditCards);
				// b2cPage.Payment_multipleCreditCards.click();
				else
					Assert.fail("Payment_multipleCreditCards is not enabled!");
//			String totalPrice_Checkout = driver.findElement(By.xpath(
//					"//dd[@class='checkout-orderSummary-price checkout-orderSummary-highlighted checkout-orderSummary-pricingTotal-amount']"))
//					.getText().toString();
			String totalPrice_Checkout = driver.findElement(By.xpath("//span[@class='totalPriceHide']")).getText().toString();
//			double card1Price = getDoublePrice(totalPrice_Checkout) - 100.00;
//			driver.findElement(By.id("card_amount1")).clear();
//			driver.findElement(By.id("card_amount1")).sendKeys(String.valueOf(card1Price));
//			Thread.sleep(1000);
//			Assert.assertEquals(driver.findElement(By.id("card_amount2")).getText(), "100.00",
//					"the amount of credit card2 should be 100.00");
			multipleCreditCards("1", "Mastercard", "5105105105105100");
//			multipleCreditCards("2", "Visa", "4111111111111111");
//			// Check if purchase order payment display
//			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {
//				if (Common.checkElementExists(driver, b2cPage.payment_purchaseNum, 5)) {
//					b2cPage.payment_purchaseNum.sendKeys("1234567890");
//					b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);
//					// b2cPage.payment_purchaseDate.click();
//					// Common.KeyEventEnter();
//				}
//			}
			// continue
			Thread.sleep(500);
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(8, "Multiple Credit Cards payment", Store, testName);

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
			
			// check price in order summary review page
			String totalPrice_Review = driver.findElement(By.xpath("//div[@class='summary-item total']/div[2]")).getText().toString();
			Assert.assertEquals(totalPrice_Review, totalPrice_Checkout,
					"price in review page should be the same as the price in checkout page");
			// place order
			Thread.sleep(500);
			Common.scrollToElement(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			Common.sleep(3000);
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(9, "place order: " + orderNum, Store, testName);

			String totalPrice_Order = driver.findElement(By.xpath("//div[@class='summary-item total']/div[2]")).getText().toString();
			Assert.assertEquals(totalPrice_Order, totalPrice_Review,
					"price in order page should be the same as the price in review page");
			Dailylog.logInfoDB(10, "Check orders information ", Store, testName);

			// Check the order History
			driver.get(testData.B2C.getHomePageUrl() + "/my-account/orders");
			// verify order exists
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + orderNum + "']")),
					orderNum + "should exist in order history");
			// verify order total price
			String totalPrice_OrderHis = driver
					.findElement(By.xpath("//*[text()='" + orderNum + "']/../..//*[@data-title='Total']")).getText();
			System.out.println("totalPrice_OrderHis: " + totalPrice_OrderHis);
			Assert.assertEquals(totalPrice_OrderHis, totalPrice_Review,
					"price in order history should be the same as the price in review page");
			Dailylog.logInfoDB(11, " Check the order History ", Store, testName);

			// Check the order in HMC
			checkOrderPriceInHMC(orderNum, totalPrice_Review);
			Dailylog.logInfoDB(12, " Check the order in HMC ", Store, testName);

			// Check the order email
			checkConfirmationEmail("Auto19972@sharklasers.com", orderNum, totalPrice_Review);
			Dailylog.logInfoDB(13, " Check the order Email ", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void checkOrderPriceInHMC(String orderNum, String orderPrice) {
		// try {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		// B2BCommon.HMCOrderCheck(hmcPage, orderNum);
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(orderNum);
		hmcPage.Home_Order_OrderSearch.click();
		Common.sleep(8000);
		System.out.println("Order status in HMC: " + hmcPage.Home_Order_OrderStatus.getText());
		Assert.assertEquals(hmcPage.Home_Order_OrderStatus.getText(), "Completed", "Order Status in HMC");
		Common.doubleClick(driver, driver.findElement(By.xpath("//*[text()='" + orderNum + "']")));
		String productTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalPrice')]"))
				.getAttribute("value");
		System.out.println("productTotal: " + getDoublePrice(productTotal));
		String taxTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalTax')]")).getAttribute("value");
		System.out.println("taxTotal: " + getDoublePrice(taxTotal));
		System.out.println("orderPrice: " + orderPrice);
		Assert.assertEquals(getDoublePrice(productTotal) + getDoublePrice(taxTotal), getDoublePrice(orderPrice),
				"price in HMC should be the same as the price in order page");
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }

	}

	public void checkConfirmationEmail(String mailId, String orderNum, String totalPrice) throws InterruptedException {
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
		System.out.println(
				"Order Email: " + Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")));

		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: Auto19972@sharklasers.com");
			setManualValidateLog("TotalPrice: " + totalPrice);
		} else {
			driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).click();
			// get total price
			String totalPrice_Mail = driver.findElement(By.xpath("(//*[text()='Total:']/../../../../..//td)[last()]"))
					.getText();
			Assert.assertEquals(totalPrice_Mail, totalPrice,
					"total price in mail should be the same as total price in order");
		}
	}

	public void multipleCreditCards(String cardOrder, String cardType, String cardNum) throws InterruptedException {

		// Card type
		b2cPage.PageDriver.switchTo().frame(b2cPage.PageDriver.findElement(By.id("creditcardiframe0")));
		Select dropdown = new Select(driver.findElement(By.id("c-ct")));
		dropdown.selectByVisibleText(cardType);
//		// Card number
//		driver.findElement(By.id("c-cardnumber")).clear();
//		driver.findElement(By.id("c-cardnumber")).sendKeys(cardNum);
//		// Expiration date
//		driver.findElement(By.id("c-exmth")).clear();
//		driver.findElement(By.id("c-exmth")).sendKeys("12");
//		driver.findElement(By.id("c-exyr")).clear();
//		driver.findElement(By.id("c-exyr")).sendKeys("20");
//		// Security code
//		driver.findElement(By.id("c-cvv")).clear();
//		driver.findElement(By.id("c-cvv")).sendKeys("123");
//		// Cardholder Name
//		driver.switchTo().defaultContent();
//		driver.findElement(By.xpath("//div[@id='iframe" + cardOrder + "_info']//*[contains(@id,'card_nameOnCard')]"))
//				.clear();
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//div[@id='iframe" + cardOrder + "_info']//*[contains(@id,'card_nameOnCard')]"))
//				.sendKeys("Auto");
		
		b2cPage.Payment_CardNumberTextBox.clear();
		b2cPage.Payment_CardNumberTextBox.sendKeys(cardNum);

		if (!b2cPage.Payment_CardMonthTextBox.getTagName().toLowerCase().equals("select")) {
			b2cPage.Payment_CardMonthTextBox.clear();
			b2cPage.Payment_CardMonthTextBox.sendKeys("12");
			b2cPage.Payment_CardYearTextBox.clear();
			b2cPage.Payment_CardYearTextBox.sendKeys("20");
		} else {
			// dropdown month and year
			dropdown = new Select(b2cPage.Payment_CardMonthTextBox);
			dropdown.selectByVisibleText("12");
			dropdown = new Select(b2cPage.Payment_CardYearTextBox);
			dropdown.selectByVisibleText("2020");
		}

		b2cPage.Payment_SecurityCodeTextBox.clear();
		b2cPage.Payment_SecurityCodeTextBox.sendKeys("123");
		b2cPage.PageDriver.switchTo().defaultContent();
		Thread.sleep(2000);
		b2cPage.Payment_CardHolderNameTextBox.clear();
		b2cPage.Payment_CardHolderNameTextBox.sendKeys("Auto");
		// TW invoice
		if (Common.isElementExist(b2cPage.PageDriver, By.xpath(".//select[contains(@id,'invoiceTypeTW')]"))) {
			b2cPage.PageDriver
					.findElement(By.xpath(".//select[contains(@id,'invoiceTypeTW')]/option[contains(.,'紙本發票')]"))
					.click();
		}
		// if PO is mandatory
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.payment_purchaseNum, 2)) {
			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");
			Thread.sleep(2000);
			b2cPage.payment_purchaseDate.click();
			Thread.sleep(3000);
			b2cPage.PageDriver.findElement(By.xpath("//td[contains(@class,'ui-datepicker-today')]/a")).click();
		}

	}

	public double getDoublePrice(String price) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		price = price.replace("[", "").replace("]", "").replace("add", "").replace("$", "").replace(",", "")
				.replace("HK", "").replace("SG", "").trim().toString();
		double price_1 = Double.parseDouble(price);
		String price_2 = decimalFormat.format(price_1);
		Double doublePrice = Double.parseDouble(price_2);
		return doublePrice;
	}

}
