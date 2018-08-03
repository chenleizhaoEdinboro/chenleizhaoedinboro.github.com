package TestScript.B2B;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21891Test extends SuperTestClass {
	
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	
	public String accountUrl;
	
	

	public NA21891Test(String store) {
		this.Store = store;
		this.testName = "NA-21891";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA21891(ITestContext ctx) {
		
		try {
			
			this.prepareTest();
			
			
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			//1, Login B2C website with B2C account
			Dailylog.logInfoDB(1, "Login B2B website with B2C account", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());

			//2, Add product to cart, change the product quantity is 3,click update, 
			//click 'Request Quote', copy the quote number
			Dailylog.logInfoDB(2, "Add product to cart, change the product quantity is 3,click update, click 'Request Quote', copy the quote number", Store, testName);
			
			String cartUrl = testData.B2B.getHomePageUrl() + "/cart";
			driver.get(cartUrl);
			B2BCommon.clearTheCart(driver, b2bPage);
			
			
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("3");
			
			b2bPage.cartPage_QuantityUpdate.click();
			
			String cart_totalPrice = b2bPage.cartPage_TotalPrice.getText().toString();
			
			
			double price_3product = stringToDouble(cart_totalPrice);
			System.out.println("price_3product is :" + price_3product);
			
			
			b2bPage.cartPage_RequestQuoteBtn.click();
			Thread.sleep(6000);
			
			b2bPage.cartPage_RepIDBox.clear();
			b2bPage.cartPage_RepIDBox.sendKeys(testData.B2B.getRepID());
			b2bPage.cartPage_SubmitQuote.click();
		
			String quoteNumber = b2bPage.QuotePage_QuoteNo.getText().toString();
			System.out.println("quoteNumber is :" + quoteNumber);
			
			driver.get(testData.B2B.getHomePageUrl());
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'common_Menu')]//a[contains(@href,'logout')]/span")));
		
			//3, Login B2C with telsales account
			Dailylog.logInfoDB(3, "Login B2B with telsales account", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());

			
			
			//4, Click 'Start Assisted Service Session' in 'My Account',
			//	input B2B account (users in the same country) under the 'customer ID' label,
			//	click 'Start session',
			//	click 'Copy Tracsaction',
			//	choose the 'Transaction Type' and 'Transaction  ID',
			//	click 'Copy it'.
			
			b2bPage.homepage_MyAccount.click();
			
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			Common.javascriptClick(driver, b2bPage.MyAccountPage_CustomerIDBox);
			//b2bPage.MyAccountPage_CustomerIDBox.click();
			driver.findElement(By.xpath("//*[@id='customerFilter']")).sendKeys(
					testData.B2B.getBuilderId());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			Actions action = new Actions(driver);
			// action.sendKeys(b2bPage.MyAccountPage_CustomerIDBox, Keys.ENTER);
			b2bPage.MyAccount_CustomerResult.click();

			b2bPage.MyAccountPage_StartSessionButton.click();
			Thread.sleep(6000);
			
			b2bPage.assistedServiceMode_copyTransaction.click();
			Thread.sleep(6000);
			
			b2bPage.assistedServiceMode_transactionType.click();
			b2bPage.assistedServiceMode_transactionType_QuoteType.click();
			b2bPage.assistedServiceMode_transactionType_QuoteIDTxtBox.sendKeys(quoteNumber);
			b2bPage.assistedServiceMode_copyIt.click();
			
			String price_copyit = driver.findElement(By.xpath("//dl[@class='cart-item-pricing-and-quantity-finalPrice-amount']/span")).getText().toString();
			double price_copyit1 = stringToDouble(price_copyit);
			System.out.println("price_copyit1 is :" + price_copyit1);
			
			String number_copyit = driver.findElement(By.xpath("//input[@name='quantity']")).getAttribute("value").toString().trim();
			
			Assert.assertTrue(number_copyit.equals("3"));
			Assert.assertTrue(price_3product == price_copyit1);
			
			//5, click 'Edit Quote'
			Dailylog.logInfoDB(5, "click Edit Quote", Store, testName);
			
			b2bPage.Quote_editButton.click();
			
			String prodNumber = driver.findElement(By.xpath("//input[@name='initialQuantity']")).getAttribute("value").toString().trim();
			Assert.assertTrue(prodNumber.equals("3") && !driver.findElement(By.xpath("//input[@name='initialQuantity']")).isEnabled());
			
			
			//6, Add another product to cart
			Dailylog.logInfoDB(6, "Add another product to cart", Store, testName);
			
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			

			String price_3Product = driver.findElement(By.xpath("(//dt[@class='cartDetails-tsPrice'])[1]")).getText().toString();
			double price_3Product1 = stringToDouble(price_3Product);
			
			System.out.println("price_3Product1 is :" + price_3Product1);
			
			String price_addOneProduct = driver.findElement(By.xpath("(//dt[@class='cartDetails-tsPrice'])[2]")).getText().toString();
			double price_addOneProduct1 = stringToDouble(price_addOneProduct);
			
			System.out.println("price_addOneProduct1 is :" + price_addOneProduct1);
			
			String price_subTotal = b2bPage.cartPage_TotalPrice.getText().toString();
			double price_subTotal1 = stringToDouble(price_subTotal);
			
			System.out.println("price_subTotal1 is :" + price_subTotal1);
			
			System.out.println("---------------------------------");
			System.out.println("price_subTotal1 is :" + price_subTotal1);
			
			double plus_total = price_3Product1 + price_addOneProduct1;
					
			BigDecimal bg = new BigDecimal(plus_total);
			plus_total = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			 
			System.out.println("the total is :" + plus_total);
			
			
			
			Assert.assertTrue(price_subTotal1 == plus_total);

			//7, Click 'Save Quote',
			//Click 'End Session'.
			Dailylog.logInfoDB(6, "click Save Quote , then Click End Session", Store, testName);
			
			driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='submit-quote-button']")).click();
			
			String quoteNumebr1 = b2bPage.QuotePage_QuoteNo.getText().toString();
			System.out.println("quoteNumebr1 is :" + quoteNumebr1);
			
			driver.findElement(By.xpath("//button[@id='stopEmulate']")).click();
			
			if(Common.isAlertPresent(driver)){
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
			
			//8,Input the copied quote number in 'Transaction ID and choose it,
			//Click 'Start Session',
			//Click 'CONVERT TO ORDER'.
			Dailylog.logInfoDB(8, "Input the copied quote number in 'Transaction ID and choose it, click Start Session , click Convert to order", Store, testName);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2bPage.homePage_TransactionIDBox.click();
			b2bPage.homePage_TransactionIDBox.sendKeys(quoteNumber);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("[id^='Q']>a")));
			b2bPage.MyAccount_QuoterResult.click();
			Thread.sleep(3000);
			// action.sendKeys(b2bPage.homePage_TransactionIDBox, Keys.ENTER);
			b2bPage.MyAccountPage_StartSessionButton.click();
			
			driver.findElement(By.id("convertoorderButton")).click();
			
			//9,Finish the checkout process.
			Dailylog.logInfoDB(9, "Finish the checkout process.", Store, testName);
			
			if (Store.toLowerCase().equals("us")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "us",
						"testtesttest", "testtesttest", "Georgia",
						"1535 Broadway", "New York", "New York", "10036-4077",
						"2129450100");
			} else if (Store.toLowerCase().equals("au")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "au",
						"testtesttest", "testtesttest", "adobe_global",
						"62 Streeton Dr", "RIVETT",
						"Australian Capital Territory", "2611", "2123981900");
			} else if(Store.toLowerCase().equals("jp")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "jp",
						"testtesttest", "testtesttest", testData.B2B.getCompany(),
						testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
						testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			}

			Thread.sleep(3000);
			b2bPage.shippingPage_ContinueToPayment.click();
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions
							.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));
			
			Payment(driver, b2bPage, "IGF");
		
			Assert.assertTrue(driver.getCurrentUrl().endsWith("summary"));
			if (Common
					.isElementExist(driver, By.xpath("//*[@id='resellerID']"))
					&& driver.findElement(By.xpath("//*[@id='resellerID']"))
							.isEnabled() && driver.findElement(By.xpath("//*[@id='resellerID']")).isDisplayed()) {
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
			}
			b2bPage.placeOrderPage_Terms.click();
			b2bPage.placeOrderPage_PlaceOrder.click();

			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("orderConfirmation"));

			String orderNumber = b2bPage.placeOrderPage_OrderNumber.getText()
					.toString();
			System.out.println("orderNumber is :" + orderNumber);
			Dailylog.logInfoDB(9, "order Number is :"+orderNumber, Store, testName);
			
			
			//10,Login HMC -> Order -> Orders ->Order Number: input the order id, then click search -> double click the search result -> check the order price
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			HMCCommon.HMCOrderCheck(hmcPage, orderNumber);

	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
		
	public double stringToDouble(String str){
		String price1 = str.replace("$", "").replace(",", "").replace(" ", "").replace("￥", "").toString().trim();
		
		double d = Double.parseDouble(price1);
		
		return d;
	}
	public void Payment(WebDriver driver, B2BPage b2bPage, String paymentMethod)
			throws Exception {
		if (paymentMethod.equals("Visa")) {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// By locator4 =
			// By.xpath("//input[@name='external.field.password']");
			// By LocatorDatePicker = By
			// .xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			Actions actions = new Actions(driver);
			actions.moveToElement(b2bPage.paymentPage_creditCardRadio).click()
					.perform();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			b2bPage.paymentPage_CardType.click();
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.elementToBeClickable(b2bPage.paymentPage_Visa));
			b2bPage.paymentPage_Visa.click();
			b2bPage.paymentPage_CardNumber.clear();
			b2bPage.paymentPage_CardNumber.sendKeys("4111111111111111");
			b2bPage.paymentPage_ExpiryMonth.clear();
			b2bPage.paymentPage_ExpiryMonth.sendKeys("06");
			b2bPage.paymentPage_ExpiryYear.clear();
			b2bPage.paymentPage_ExpiryYear.sendKeys("20");
			b2bPage.paymentPage_SecurityCode.clear();
			b2bPage.paymentPage_SecurityCode.sendKeys("132");
			b2bPage.paymentPage_NameonCard.clear();
			b2bPage.paymentPage_NameonCard.sendKeys("LIXE");

			// if (common.checkElementExists(driver,
			// driver.findElement(locator4))) {
			// b2bPage.paymentPage_VisaPassword.sendKeys("1234");
			// b2bPage.paymentPage_VisaSubmit.click();
			// }
			try {
				Thread.sleep(6000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean b = Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
					&& driver
							.findElement(
									By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
							.isDisplayed();
			System.out.println("b is :" + b);

			if (Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
					&& driver
							.findElement(
									By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
							.isDisplayed()) {
				b2bPage.paymentPage_purchaseNum.sendKeys("123456");
				SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
				b2bPage.paymentPage_purchaseDate.sendKeys(dataFormat.format(
						new Date()).toString());
			}
			System.out.println("payment paied");

			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("testtesttest");
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("testtesttest");

			/*
			 * if(Store.toLowerCase().equals("us")){
			 * fillB2BShippingInfo("test","test"
			 * ,"Georgia","1535 Broadway","New York"
			 * ,"New York","10036-4077","2129450100"); }else
			 * if(Store.toLowerCase().equals("au")){
			 * fillB2BShippingInfo("test","test"
			 * ,"adobe_global","62 Streeton Dr",
			 * "RIVETT","Australian Capital Territory","2611","2123981900"); }
			 */
			if (b2bPage.paymentPage_addressLine1.isEnabled()) {
				b2bPage.paymentPage_addressLine1.clear();
				if (Store.toLowerCase().equals("us")) {
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				} else if (Store.toLowerCase().equals("au")) {
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				}

			}
			if (b2bPage.paymentPage_cityOrSuburb.isEnabled()) {
				b2bPage.paymentPage_cityOrSuburb.clear();
				if (Store.toLowerCase().equals("us")) {
					b2bPage.paymentPage_cityOrSuburb.sendKeys("New York");
				} else if (Store.toLowerCase().equals("au")) {
					b2bPage.paymentPage_cityOrSuburb.sendKeys("RIVETT");
				}
			}
			if (b2bPage.paymentPage_addressState.isEnabled()) {
				b2bPage.paymentPage_addressState.click();
				if (Store.toLowerCase().equals("us")) {
					driver.findElement(
							By.xpath(".//*[@id='address.region']/option[contains(.,'New York')]"))
							.click();
				} else if (Store.toLowerCase().equals("au")) {
					driver.findElement(
							By.xpath(".//*[@id='address.region']/option[contains(.,'Australian Capital Territory')]"))
							.click();
				}
			}
			if (b2bPage.paymentPage_addressPostcode.isEnabled()) {
				b2bPage.paymentPage_addressPostcode.clear();
				if (Store.toLowerCase().equals("us")) {
					b2bPage.paymentPage_addressPostcode.sendKeys("10036-4077");
				} else if (Store.toLowerCase().equals("au")) {
					b2bPage.paymentPage_addressPostcode.sendKeys("2611");
				}
			}
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");
			try {
				Thread.sleep(6000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (paymentMethod.equals("IGF")) {
			b2bPage.paymentPage_IGF.click();
			boolean b = Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
					&& driver
							.findElement(
									By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
							.isDisplayed();
			System.out.println("b is :" + b);
			Thread.sleep(5000);
			if (Common
					.isElementExist(
							driver,
							By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
					&& driver
							.findElement(
									By.xpath("//*[@id='checkoutForm-fieldset-purchaseorder']/fieldset"))
							.isDisplayed()) {
				Thread.sleep(2000);
				b2bPage.paymentPage_purchaseNum.clear();
				b2bPage.paymentPage_purchaseNum.sendKeys("123456");
				Thread.sleep(2000);

				b2bPage.paymentPage_purchaseDate.click();

				Calendar CD = Calendar.getInstance();
				String DD = CD.get(Calendar.DATE) + "";
				;

				driver.findElement(
						By.xpath("//*[@id='ui-datepicker-div']//a[text()='"
								+ DD + "']")).click();

				// b2bPage.paymentPage_purchaseDate.clear();
				// SimpleDateFormat dataFormat = new
				// SimpleDateFormat("MM/dd/YYYY");
				// b2bPage.paymentPage_purchaseDate.sendKeys(dataFormat.format(
				// new Date()).toString());
			}
			System.out.println("payment paied");
			b2bPage.paymentPage_billingAddress.click();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Store.toLowerCase().equals("us")) {
				b2bPage.paymentPage_billingAddressDropDwn_US.click();
			}
			if (Store.toLowerCase().equals("au")) {
				b2bPage.paymentPage_billingAddressDropDwn_AU.click();
			}
			if (Store.toLowerCase().equals("jp")) {
				b2bPage.paymentPage_billingAddressDropDwn_JP.click();
			}

			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("testtest");
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("testtest");
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");
			
			if (b2bPage.paymentPage_companyName.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_companyName,"disabled")) {
				b2bPage.paymentPage_companyName.clear();
				b2bPage.paymentPage_companyName.sendKeys(testData.B2B.getCompany());
				System.out.println("Input Company name!");
			}

		}
		b2bPage.paymentPage_ContinueToPlaceOrder.click();
		(new WebDriverWait(driver, 500)).until(ExpectedConditions
				.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
	}
	
	
	
}
