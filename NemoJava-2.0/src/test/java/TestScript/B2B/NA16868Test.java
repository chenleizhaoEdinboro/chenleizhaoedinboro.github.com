package TestScript.B2B;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class NA16868Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String homepageUrl;
	public String cartUrl;

	public NA16868Test(String Store) {
		this.Store = Store;
		this.testName = "NA-16868";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA16868(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			//step 1, login in with telesales 
			//Expect : login store successfully
			Dailylog.logInfoDB(1, "login in with telesales", Store, testName);
			
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			
			Assert.assertTrue(b2bPage.homepage_Signout.isDisplayed());
			Dailylog.logInfo("step1 , login in with telesales successfully !!!");
			
			//step 2~3,login ASM
			Dailylog.logInfoDB(2, "login ASM", Store, testName);
			
			b2bPage.homepage_MyAccount.click();
			b2bPage.MyAccountPage_StartAssSerSession.click();
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			Dailylog.logInfo("step2~3 successfully");
			
			//step 4, find an customer and start session
			Dailylog.logInfoDB(4, "find an customer and start session", Store, testName);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2bPage.MyAccountPage_CustomerIDBox.click();
			driver.findElement(By.xpath("//*[@id='customerFilter']")).sendKeys(
					testData.B2B.getBuilderId());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			Actions action = new Actions(driver);
			b2bPage.MyAccount_CustomerResult.click();

			b2bPage.MyAccountPage_StartSessionButton.click();
			
			Dailylog.logInfo("step 4 successfully");
			
			//step 5,find an CTO product and go to the configurator page
			Dailylog.logInfoDB(5, "find an CTO product and go to the configurator page", Store, testName);
			
			Thread.sleep(5000);
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]"))
					.click();
			b2bPage.productPage_agreementsAndContract.click();
			if (Common.isElementExist(driver,
					By.xpath("//form/label[contains(.,'Agreement')]"))) {
				b2bPage.productPage_radioAgreementButton.click();
			} 

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
			Thread.sleep(5000);
			
			Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='tab-a-customize']")).isDisplayed());
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//button[@class='set-discount']")));

			Dailylog.logInfo("step 5 successfully");
			
			//step 6, perform price override 
			Dailylog.logInfoDB(6, "perform price override", Store, testName);
			
			Select select_configuration = new Select(driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//select[@class='over_reason'])[1]")));
			select_configuration.selectByValue("1");
			
			String originalPrice = driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//input[@class='discount'])[1]")).getAttribute("value").toString();
			int price_before = Integer.parseInt(originalPrice);
			
			int now = (int) (price_before - price_before*0.1);
			
			
			driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//input[@class='discount'])[1]")).clear();
			driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//input[@class='discount'])[1]")).sendKeys(now + "");
			
			String comment = "configuration page price override";
			
			driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//input[@class='over_description'])[1]")).clear();
			driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//input[@class='over_description'])[1]")).sendKeys(comment);
			
			String webPrice = driver.findElement(By.xpath("//*[@id='w-price']")).getText().toString();
			
			webPrice = webPrice.replace("$", "").replace(",", "").toString().trim();
			
			System.out.println("webprice_before is :" + webPrice);
			
			driver.findElement(By.xpath("(//tbody[contains(@id,'group_')]/tr[not(contains(@class,'hidden'))]//button[@class='set-discount'])[1]")).click();
			Thread.sleep(5000);
			
			String webPrice_after = driver.findElement(By.xpath("//*[@id='w-price']")).getText().toString();
			
			webPrice_after = webPrice_after.replace("$", "").replace(",", "").toString().trim();
			System.out.println("webPrice_after is :" + webPrice_after);
			
			Assert.assertNotEquals(webPrice, webPrice_after);
	
			Dailylog.logInfo("step 6 successfully");
			
			//7, skip this step 
			
			//8, Click *Add to cart* Button. 
			Dailylog.logInfoDB(8, "Click *Add to cart* Button", Store, testName);
			
			b2bPage.Agreement_agreementsAddToCart.click();
			Thread.sleep(5000);

			while (Common
					.isElementExist(
							driver,
							By.cssSelector("div>button.pricingSummary-button.button-called-out.button-full"))) {
				b2bPage.Agreement_addToCartAccessoryBtn.click();
			}
				
			Dailylog.logInfo("step 8 successfully");
			//9,Click *CheckOut* button to go to check out process.
			Dailylog.logInfoDB(9, "Click *CheckOut* button to go to check out process", Store, testName);
			
			b2bPage.lenovoCheckout.click();
			
			//10, fill in the necessary field on shipping page and go to payment page 
			Dailylog.logInfoDB(10, "fill in the necessary field on shipping page and go to payment page", Store, testName);
			
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, "us",
					"testtesttest", "testtesttest", "Georgia",
					"1535 Broadway", "New York", "New York", "10036-4077",
					"2129450100");
			
			Thread.sleep(3000);
			b2bPage.shippingPage_ContinueToPayment.click();
			
			Thread.sleep(10000);
			
			Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='quote_button']")).isDisplayed());
			Dailylog.logInfo("step 10 successfully");
			
			//11, Click *Quote Creation* button.
			Dailylog.logInfoDB(10, "Click *Quote Creation* button", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='quote_button']")).click();
			
			Thread.sleep(5000);
			b2bPage.cartPage_RepIDBox.sendKeys(testData.B2B.getRepID());
			b2bPage.cartPage_SubmitQuote.click();
			
			String quoteNumber = b2bPage.cartPage_QuoteNumber.getText()
					.toString();
			System.out.println("Quote Number is :" + quoteNumber);
			
			Assert.assertNotNull(quoteNumber);
			
			Dailylog.logInfo("step 11 successfully !!!");
			
			//12, Enter the payment info and then click Continue button 
			Dailylog.logInfoDB(12, "Enter the payment info and then click Continue button ", Store, testName);
			
			b2bPage.homePage_signout_tele.click();
			
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
			// action.sendKeys(b2bPage.homePage_TransactionIDBox, Keys.ENTER);
			b2bPage.MyAccountPage_StartSessionButton.click();
			
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quoteStatusChange']"))) {
				b2bPage.cartPage_ApproveRejectBtn.click();
				b2bPage.cartPage_ApproverCommentBox.sendKeys("reject");
				b2bPage.cartPage_RejectButton.click();
				Thread.sleep(3000);
				Assert.assertTrue(b2bPage.cartPage_ApproveRejectBtn.getText()
						.equals("APPROVE"));
				b2bPage.cartPage_ApproveRejectBtn.click();
				b2bPage.cartPage_ApproverCommentBox.sendKeys("approve");
				b2bPage.cartPage_ApproveButton.click();
			}
			
			Thread.sleep(3000);
			b2bPage.cartPage_ConvertToOrderBtn.click();

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
			b2bPage.homepage_Signout.click();
			Thread.sleep(3000);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			HMCCommon.HMCOrderCheck(hmcPage, orderNumber);
	
		} catch (Throwable e) {
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
