package TestScript.B2B;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA18360Test extends SuperTestClass{
	
	private static B2BPage b2bPage;
	
	private String quoteID;

	public NA18360Test(String store) {
		this.Store = store;
		this.testName = "NA-18360";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA18480(ITestContext ctx) {
		try{
			this.prepareTest();
		
			b2bPage = new B2BPage(driver);
			
			//1, open the b2b site 
			Dailylog.logInfoDB(1, "open the b2b site", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			
			
			//2, check the navigation bar ,products displayed 
			Dailylog.logInfoDB(2, "check the navigation bar , products displayed", Store, testName);
			
			Assert.assertTrue(b2bPage.HomePage_productsLink.isDisplayed());
			
			
			//3, go to my account page 
			Dailylog.logInfoDB(3, "go to my account page", Store, testName);
			b2bPage.myAccount_link.click();
			
			Assert.assertTrue(driver.getCurrentUrl().endsWith("my-account"));

			//4, click "Start Assisted Service Session" link
			Dailylog.logInfoDB(4, "click start assisted service session", Store, testName);
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			//5,login ASM
			Dailylog.logInfoDB(5, "login ASM", Store, testName);
			
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			//6,use customer search function to find an existing users and then click start session button 
			Dailylog.logInfoDB(5, "use customer search function to find an existing users and then click start session button", Store, testName);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2bPage.MyAccountPage_CustomerIDBox.click();
			driver.findElement(By.xpath("//*[@id='customerFilter']")).sendKeys(
					testData.B2B.getBuilderId());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			
			b2bPage.MyAccount_CustomerResult.click();

			b2bPage.MyAccountPage_StartSessionButton.click();
			
			//7, click PRODUCTS in the navigation bar
			Dailylog.logInfoDB(7, "click PRODUCTS in the navigation bar", Store, testName);
			
			Thread.sleep(5000);
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]"))
					.click();
			
			//8,Customize one CTO product and then add the product to cart 
			Dailylog.logInfoDB(8, "Customize one CTO product and then add the product to cart ", Store, testName);
			
			b2bPage.productPage_agreementsAndContract.click();
			b2bPage.productPage_radioAgreementButton.click();
			
			driver.findElement(
					By.xpath("(//*[@id='resultList']/div/div[4]/a)[1]"))
					.click();
			Thread.sleep(5000);
			
			if (Common.isElementExist(driver,
					By.xpath("//button[text()='Customize']"))
					&& driver.findElement(
							By.xpath("//button[text()='Customize']"))
							.isDisplayed()) {
				System.out.println("here is ------");
				driver.findElement(
						By.xpath("(//button[@class='close' and text()='×'])[2]"))
						.click();
				Thread.sleep(5000);
			}

			((JavascriptExecutor) driver)
					.executeScript("scroll(0,1200)");
			
			b2bPage.Agreement_agreementsAddToCart.click();
			Thread.sleep(5000);

			while (Common
					.isElementExist(
							driver,
							By.cssSelector("div>button.pricingSummary-button.button-called-out.button-full"))) {
				b2bPage.Agreement_addToCartAccessoryBtn.click();
			}
			
			//9, Perform price override function and then click the update button 
			Dailylog.logInfoDB(9, "Perform price override function and then click the update button", Store, testName);
			String price = b2bPage.cartPage_PriceOverrideBox
					.getAttribute("placeholder").toString();

			double price_dou = string2Num(price);
			price_dou = price_dou - price_dou * 0.01;

			String final_price = price_dou + "";
			System.out.println("final_price is :" + final_price);
			
			b2bPage.cartPage_PriceOverrideBox.clear();
			b2bPage.cartPage_PriceOverrideBox.sendKeys(final_price);
			Select stateDropdown = new Select(
					b2bPage.cartPage_PriceOverrideDropDown);
			stateDropdown.selectByVisibleText("5 Volume price request");
			/*
			 * b2bPage.cartPage_PriceOverrideDropDown.click();
			 * Thread.sleep(2000);
			 * b2bPage.cartPage_PriceOverrideDropDownSelection.click();
			 */
			Thread.sleep(2000);
			b2bPage.cartPage_PriceOverrideReason.sendKeys(final_price);
			Thread.sleep(2000);
			b2bPage.cartPage_UpdatePriceOverride.click();
			Assert.assertTrue(b2bPage.cartPage_PriceOverrideSuccessfulMsg
					.getText()
					.equals("Price override performed, please proceed to create quote."));
			
			
			//10,request quote and then convert the quote to order .
			Dailylog.logInfoDB(10, "request quote and then convert the quote to order", Store, testName);
			b2bPage.cartPage_RequestQuoteBtn.click();
		
			Thread.sleep(5000);
			b2bPage.cartPage_RepIDBox.sendKeys(testData.B2B.getRepID());
			b2bPage.cartPage_SubmitQuote.click();
			String quoteNumber = b2bPage.cartPage_QuoteNumber.getText()
					.toString();
			System.out.println("Quote Number is :" + quoteNumber);
			
			b2bPage.homePage_signout_tele.click();
			
			driver.get(testData.B2B.getLoginUrl());
			Thread.sleep(3000);
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(),
					testData.B2B.getDefaultPassword());
			b2bPage.homepage_MyAccount.click();
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2bPage.homePage_TransactionIDBox.click();
			b2bPage.homePage_TransactionIDBox.sendKeys(quoteNumber);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("[id^='Q']>a")));
			b2bPage.MyAccount_QuoterResult.click();
			Thread.sleep(3000);
			
			b2bPage.MyAccountPage_StartSessionButton.click();
			
			String price_change = driver.findElement(By.xpath("//dl[@class='cart-item-pricing-and-quantity-finalPrice-amount']/span")).getText().toString();
			String price_original = driver.findElement(By.xpath("//dt[contains(.,'Line Total')]/../dl[1]")).getText().toString();			
			
			String price_accessroy = "";
			
			boolean b = Common.isElementExist(driver, By.xpath("//span[contains(@class,'cart-item-pricing-and-quantity-finalPrice-amount-detail')]"));
			if(b){
				
				List<WebElement> accessroyList = driver.findElements(By.xpath("//span[contains(@class,'cart-item-pricing-and-quantity-finalPrice-amount-detail')]"));
				
				price_accessroy = driver.findElement(By.xpath("(//span[contains(@class,'cart-item-pricing-and-quantity-finalPrice-amount-detail')])[2]")).getText().toString();
	
			}
			
			float price_change1 = string2Num(price_change);
			System.out.println("price_change1 is :" + price_change1);
			
			float price_original1 = string2Num(price_original);
			
			System.out.println("price_accessroy is :" + price_accessroy);
			
			float price_acc = 0;
			if(b){
				price_acc = string2Num(price_accessroy);
			}
			
			
			System.out.println("final_price is :" + final_price);
			System.out.println("price_original is :" + price_original);
			System.out.println("price_acc is :" + price_acc);
			
			
			Thread.sleep(60000);
			
			Assert.assertTrue(final_price.equals((price_change1 - price_acc)+"") && !(price_change1 +"").equals(price_original1+""));

			// approve the quote  and convert it to an order 
			
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quoteStatusChange']"))) {
				b2bPage.cartPage_ApproveRejectBtn.click();
				Thread.sleep(3000);
				b2bPage.cartPage_ApproverCommentBox.sendKeys("reject");
				Thread.sleep(3000);
				b2bPage.cartPage_RejectButton.click();
				Thread.sleep(3000);
				Assert.assertTrue(b2bPage.cartPage_ApproveRejectBtn.getText()
						.equals("APPROVE"));
				b2bPage.cartPage_ApproveRejectBtn.click();
				Thread.sleep(3000);
				b2bPage.cartPage_ApproverCommentBox.sendKeys("approve");
				Thread.sleep(3000);
				b2bPage.cartPage_ApproveButton.click();
			}
			
			Thread.sleep(3000);
			b2bPage.cartPage_ConvertToOrderBtn.click();
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
			
			String orderNumber = b2bPage.placeOrderPage_OrderNumber.getText()
					.toString();
			System.out.println("orderNumber is :" + orderNumber);

			
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
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

		}
		b2bPage.paymentPage_ContinueToPlaceOrder.click();
		(new WebDriverWait(driver, 500)).until(ExpectedConditions
				.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
	}
	
	
	
	
	public float string2Num(String str) {
		str = str.replace("$", "").replace(",", "");
		float str_1 = Float.parseFloat(str);
		return str_1;
	}

	
}
