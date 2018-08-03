package TestScript.B2B;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class NA21875Test extends SuperTestClass{
	
	HMCPage hmcPage;
	B2BPage b2bPage;
	
	String loginUrl = "";
	String cartUrl = "";
	
	public NA21875Test(String store){
		this.Store = store;
		this.testName = "NA-21875";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA21875(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			//1, Login B2B with B2B account
			loginUrl = testData.B2B.getLoginUrl();
			cartUrl = testData.B2B.getHomePageUrl()+"/cart";
			Dailylog.logInfoDB(1, "Login B2B with B2B account", Store, testName);
			driver.get(loginUrl);
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			
			//2, Add product to cart, change the product quantity is 3,click update, copy the cart id
			Dailylog.logInfoDB(2, "Add product to cart, change the product quantity is 3,click update, copy the cart id", Store, testName);
			driver.get(cartUrl);
			
			if(Common.isElementExist(driver, By.xpath("//*[@id='emptyCartItemsForm']/a"))){
				driver.findElement(By.xpath("//*[@id='emptyCartItemsForm']/a")).click();
			}
			
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_addButton.click();
			
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("3");
			b2bPage.cartPage_QuantityUpdate.click();
			
			b2bPage.cartPage_saveCartButton.click();
			Thread.sleep(3000);
			b2bPage.cartPage_privateSaveCartn.click();
			
			String dateStr = Common.getDateTimeString();
			
			String cartName = "NA21875Test"+dateStr;
			
			driver.findElement(By.xpath("//*[@id='realsavecartname']")).clear();
			driver.findElement(By.xpath("//*[@id='realsavecartname']")).sendKeys(cartName);
			
			driver.findElement(By.xpath("//*[@id='addToCartButtonTop']")).click();
			
			Thread.sleep(4000);
			
			String cartID = driver.findElement(By.xpath("//td[@data-title='Cart Name']/h3[contains(.,'"+cartName+"')]/../../td[@data-title='Cart ID']/h3/a")).getText();			
			
			Dailylog.logInfoDB(2, "the cart id is :" + cartID, Store, testName);
			
			
			//3, Login B2B with telesales account
			Dailylog.logInfoDB(3, "Login B2B with telesales account", Store, testName);
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'common_Menu')]//a[contains(@href,'logout')]/span")));
			
			driver.get(loginUrl);
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			
			
			//4,under ASM mode , click copy it button
			Dailylog.logInfoDB(4, "under ASM mode , click copy it button", Store, testName);
			
			b2bPage.homepage_MyAccount.click();

			b2bPage.MyAccountPage_StartAssSerSession.click();

			B2BCommon.loginASM(driver, b2bPage, testData);

			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			Common.javascriptClick(driver, b2bPage.MyAccountPage_CustomerIDBox);
		
			driver.findElement(By.xpath("//*[@id='customerFilter']")).sendKeys(
					testData.B2B.getBuilderId());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			Actions action = new Actions(driver);
		
			b2bPage.MyAccount_CustomerResult.click();

			b2bPage.MyAccountPage_StartSessionButton.click();
			Thread.sleep(6000);
			b2bPage.assistedServiceMode_copyTransaction.click();
			Thread.sleep(6000);
			b2bPage.assistedServiceMode_transactionID.clear();
			b2bPage.assistedServiceMode_transactionID.sendKeys(cartID);
			
			b2bPage.assistedServiceMode_copyIt.click();
			
			String savedCartPrice = driver.findElement(By.xpath("//div[contains(@class,'finalPrice')]/span")).getText();
			float price = getPrice(savedCartPrice);
						
			//5, click 'Edit Cart'
			Dailylog.logInfoDB(5, "Click Edit cart button", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='editCartButton']")).click();
			
			Thread.sleep(6000);
			
			
			Assert.assertTrue(!driver.findElement(By.xpath("//input[@name='initialQuantity']")).isEnabled(), "After click edit button , the qunatity is editable.");
		
			//6,Add another product to cart
			Dailylog.logInfoDB(6, "Add another product to cart", Store, testName);
			
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_addButton.click();
			
			Thread.sleep(4000);
			String price_anotherProduct = driver.findElement(By.xpath("(//dt[@class='cartDetails-tsPrice'])[last()]")).getText();
			float price_ano_f = getPrice(price_anotherProduct);
			
			String subTotalPrice = driver.findElement(By.xpath("//dd[contains(@class,'cart-summary-pricing-webPrice-price')]")).getText();
			float subTotalPrice_f = getPrice(subTotalPrice);
			
			Assert.assertTrue(Common.getFloat(price_ano_f+price) == subTotalPrice_f, "after add another product into cart , the price is not correct");
			
			
			//7, Finish the checkout process.
			Dailylog.logInfoDB(7, "Finish the checkout process.", Store, testName);
			
			b2bPage.lenovoCheckout.click();
			
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, testData);
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2bPage.shippingPage_ContinueToPayment);
			
			if(Common.isElementExist(driver, By.xpath(".//*[@id='checkout_validateFrom_ok']"))){
				driver.findElement(By.xpath(".//*[@id='checkout_validateFrom_ok']")).click();
			}
			
			Payment(driver, b2bPage, "IGF");
			
			if(Common.isElementExist(driver, By.xpath(".//*[@id='resellerID']")) && driver.findElement(By.xpath(".//*[@id='resellerID']")).isEnabled() && driver.findElement(By.xpath(".//*[@id='resellerID']")).isDisplayed()){
				driver.findElement(By.xpath(".//*[@id='resellerID']")).clear();
				driver.findElement(By.xpath(".//*[@id='resellerID']")).sendKeys(testData.B2B.getRepID());
			}
			
			b2bPage.placeOrderPage_Terms.click();
			
			b2bPage.placeOrderPage_PlaceOrder.click();
			
			String orderNumberString =  b2bPage.orderPage_orderNumber.getText();
			
			//8. go to hmc and check the order price 
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'ASM_close')]")));
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(hmcPage, orderNumberString);
			
			driver.findElement(By.xpath("//img[contains(@id,'OrganizerListEntry')]")).click();
			String priceValue = driver.findElement(By.xpath("//input[contains(@id,'Order.totalPrice')]")).getAttribute("value");
			
			float priceValue_f = getPrice(priceValue);
			
			Assert.assertTrue(priceValue_f == subTotalPrice_f, "In hmc , the price is not equal with the price that is on the web");

			
		} catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	public Float getPrice(String str){
		
		String price = str.replace("$", "").replace(",", "").replace("¥", "").replace(" ", "").replace("￥", "");
		float price_f = Float.parseFloat(price);
		return price_f;
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
			if(Store.toLowerCase().equals("jp")){
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