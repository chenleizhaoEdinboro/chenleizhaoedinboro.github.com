package TestScript.B2B;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
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
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16389Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String homepageUrl;
	public String cartUrl;

	public NA16389Test(String Store) {
		this.Store = Store;
		this.testName = "NA-16389";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "e2e", "b2b" ,"compatibility"})
	public void NA16389(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			
			// 1, Login store front as a telesales user
			if (Browser.toLowerCase().equals("firefox")
					|| Browser.toLowerCase().equals("ie")) {
				driver.get(testData.B2B.getLoginUrl().replace(
						"LIeCommerce:M0C0v0n3L!@", ""));
			} else {
				driver.get(testData.B2B.getLoginUrl());
			}
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(),
					testData.B2B.getDefaultPassword());
			homepageUrl = driver.getCurrentUrl().toString();
			cartUrl = homepageUrl + "cart";
			
			Assert.assertTrue(b2bPage.homepage_Signout.isDisplayed());

			
			// 2, Check navigation bar: Products and ACCESSORIES & UPGRADES
			// display---
			// Assert.assertTrue(b2bPage.HomePage_productsLink.isDisplayed() &&
			// b2bPage.HomePage_AccessoriesLink.isDisplayed());
			Assert.assertTrue(b2bPage.HomePage_productsLink.isDisplayed());

			// 3, Go to My Account Page, and check My account Page
		
			b2bPage.homepage_MyAccount.click();

			// 4, Click "Start Assisted Service Session" link.
			b2bPage.MyAccountPage_StartAssSerSession.click();

			// 5, Input the telesales user name and password to login ASM
			
			B2BCommon.loginASM(driver, b2bPage, testData);
			
			// 6, Use Customer Search feature(Input the Customer ID) to find an
			// existing customer and
			// assign the desired customer into ASM by clicking START SESSION
			// button

			// 7, Choose the desired customer from the search result list.

			// 8, Click *Start Session* button.
			// b2bPage.MyAccountPage_CustomerIDBox.clear();
			// b2bPage.MyAccountPage_CustomerIDBox.sendKeys(testData.B2B.getBuilderId());
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

			// 9 , Click the Products and go to product page--Choose contract
			// product if there is no agreement product

			Thread.sleep(5000);
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]"))
					.click();
			b2bPage.productPage_agreementsAndContract.click();
			if (Common.isElementExist(driver,
					By.xpath("//form/label[contains(.,'Agreement')]"))) {
				b2bPage.productPage_radioAgreementButton.click();
			} else {
				b2bPage.productPage_raidoContractButton.click();
			}

			// 10 ,Choose one APOS Product and add to cart--Choose contract
			// product if there is no agreement product
			// 11 , Add an aggrement option from accessory page.--Choose
			// contract accessory if there is no agreement accessory
			if (Common
					.isElementExist(
							driver,
							By.xpath("(//div[@class='agreementContract'])[contains(.,'Agreement')]"))) {
				List<WebElement> agreementList = driver
						.findElements(By
								.xpath("(//div[@class='agreementContract'])[contains(.,'Agreement')]"));
				if (agreementList.size() >= 1) {
					driver.findElement(
							By.xpath("(//*[@id='resultList']/div/div[4]/a)[1]"))
							.click();
					Thread.sleep(5000);

					// if(isElementExist(driver,By.xpath("//button[text()='Customize']"))){
					// System.out.println("here is ------");
					// driver.findElement(By.xpath("(//button[@class='close' and text()='×'])[2]")).click();
					// }

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
					// ((JavascriptExecutor)
					// driver).executeScript("arguments[0].scrollIntoView();",
					// b2bPage.Agreement_agreementsAddToCart);

					// if(isElementExist(driver,By.xpath("//div/button[@class='add-to-cart']/span[contains(.,'Add to cart')]"))){
					// b2bPage.Agreement_agreementsAddToCart.click();
					// }

					b2bPage.Agreement_agreementsAddToCart.click();
					Thread.sleep(5000);

					while (Common
							.isElementExist(
									driver,
									By.cssSelector("div>button.pricingSummary-button.button-called-out.button-full"))) {
						b2bPage.Agreement_addToCartAccessoryBtn.click();
					}
				
					// if(Common.isElementExist(driver,By.cssSelector("div>button.pricingSummary-button.button-called-out.button-full"))){
					// b2bPage.Agreement_addToCartAccessoryBtn.click();
					// }

					
					// 12 , Add an aggrement option from accessory page.--Choose
					// contract accessory if there is no agreement accessory

					String price = b2bPage.cartPage_PriceOverrideBox
							.getAttribute("placeholder").toString();

					double price_dou = string2Num(price);
					price_dou = price_dou - price_dou * 0.01;

					String final_price = price_dou + "";
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
				}
			} else {
				// b2bPage.productPage_addToCart.click();
				driver.findElement(
						By.xpath("(//*[contains(@id,'addToCartForm')]/button)[1]"))
						.click();
				(new WebDriverWait(driver, 20))
						.until(ExpectedConditions
								.elementToBeClickable(b2bPage.productPage_AlertAddToCart));

				b2bPage.productPage_AlertAddToCart.click();
				(new WebDriverWait(driver, 20))
						.until(ExpectedConditions.invisibilityOfElementLocated(By
								.xpath("//h2[contains(text(),'Adding to Cart')]")));
				driver.get(cartUrl);
			}

			// 13, 【Check Request Quote】 Click *Request Quote* button.
			// 14, Click *Yes* button.
			b2bPage.cartPage_RequestQuoteBtn.click();
			// b2bPage.cartPage_RepIDBox.clear();
			Thread.sleep(5000);
			b2bPage.cartPage_RepIDBox.sendKeys(testData.B2B.getRepID());
			b2bPage.cartPage_SubmitQuote.click();
			String quoteNumber = b2bPage.cartPage_QuoteNumber.getText()
					.toString();
			System.out.println("Quote Number is :" + quoteNumber);
			String quoteStatus = driver
					.findElement(
							By.xpath("//*[@id='my-account-saved-quote-status']"))
					.getText().toString();
			System.out.println("quoteStatus is :" + quoteStatus);
			String quoteTotalPrice = driver
					.findElement(
							By.xpath("//*[@id='mainContent']/div[1]/div/div[2]/ul/li[2]/span"))
					.getText().toString();
			System.out.println("quoteTotalPrice is :" + quoteTotalPrice);
			String quotePartNumber = driver
					.findElement(
							By.xpath("//*[@id='mainContent']/div[1]/div/div[3]/table/tbody/tr[1]/td[2]/span"))
					.getText().toString();
			System.out.println("quotePartNumber is :" + quotePartNumber);
			String quoteQuantity = driver
					.findElement(
							By.xpath("//*[@id='mainContent']/div[1]/div/div[3]/table/tbody/tr[1]/td[1]"))
					.getText().toString();
			System.out.println("quoteQuantity is :" + quoteQuantity);
			
			Dailylog.logInfoDB(14, "quoteNumber of "+ Store +" is :" + quoteNumber, Store, testName);
			Dailylog.logInfoDB(14, "quoteStatus of "+ Store +" is :" + quoteStatus, Store, testName);
			Dailylog.logInfoDB(14, "quoteTotalPrice of "+ Store +" is :" + quoteTotalPrice, Store, testName);
			Dailylog.logInfoDB(14, "quotePartNumber of "+ Store +" is :" + quotePartNumber, Store, testName);
			Dailylog.logInfoDB(14, "quoteQuantity of "+ Store +" is :" + quoteQuantity, Store, testName);
			

			b2bPage.homePage_signout_tele.click();
			Thread.sleep(3000);
			if (isAlertPresent() == true) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				driver.get(testData.B2B.getLoginUrl().replaceAll(
						"LIeCommerce:M0C0v0n3L!@", ""));
			} else {
				driver.get(testData.B2B.getLoginUrl());
			}

			Thread.sleep(3000);
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(),
					testData.B2B.getDefaultPassword());
			b2bPage.homepage_MyAccount.click();
			
			b2bPage.MyAccountPage_StartAssSerSession.click();
			
			B2BCommon.loginASM(driver, b2bPage, testData);

			// 15, 【Check approve or reject Quote】Go to ASM function broad shown
			// on the top of the website. Enter quote # into Transaction ID
			// field in ASM function board.
			// 16, Pick up the desired quote record from this list.
			// 17, Click *Start Session* button.
			// b2bPage.homePage_TransactionIDBox.clear();
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

			// 18, Click *Approve/Reject* button
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='quoteStatusChange']"))) {
				b2bPage.cartPage_ApproveRejectBtn.click();
				Thread.sleep(5000);
				b2bPage.cartPage_ApproverCommentBox.sendKeys("reject");
				b2bPage.cartPage_RejectButton.click();
				Thread.sleep(3000);
				Assert.assertTrue(b2bPage.cartPage_ApproveRejectBtn.getText()
						.equals("APPROVE"));
				b2bPage.cartPage_ApproveRejectBtn.click();
				Thread.sleep(5000);
				b2bPage.cartPage_ApproverCommentBox.sendKeys("approve");
				b2bPage.cartPage_ApproveButton.click();
			}
			// 22 , Click *Convert To Order* button.
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

			// 23 ,Enter the required fields of shipping address and select the
			// shipping method,then click *Continue* to go to payment step of
			// checkout.
			Thread.sleep(3000);
			b2bPage.shippingPage_ContinueToPayment.click();
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions
							.elementToBeClickable(b2bPage.paymentPage_ContinueToPlaceOrder));

			// 24, 【Check Leasing Payment】 Select *Party Leasing Payment*
			// payment option.
			// 25,Enter the payment info and then Click *Countinue* button.
			Payment(driver, b2bPage, "IGF");

			// 26, Click *Place Order* button.
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
			Thread.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("orderConfirmation"));

			// 27 , Using the Order # number to go to Lenovo CRM.
			String orderNumber = b2bPage.placeOrderPage_OrderNumber.getText()
					.toString();
			System.out.println("orderNumber is :" + orderNumber);
			
			Dailylog.logInfoDB(27, "order number of "+ Store +" is :" + orderNumber, Store, testName);
			
			b2bPage.homepage_Signout.click();
			Thread.sleep(3000);
			if (Browser.toLowerCase().equals("ie")) {
				Common.NavigateToUrl(driver, Browser,
						testData.HMC.getHomePageUrl());
			} else {
				driver.get(testData.HMC.getHomePageUrl());
			}
			if (isAlertPresent() == true) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
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
			
			if (b2bPage.paymentPage_companyName.getAttribute("editable").contains("true")&&! Common.isAttributePresent(b2bPage.paymentPage_companyName,"disabled")) {
				b2bPage.paymentPage_companyName.clear();
				b2bPage.paymentPage_companyName.sendKeys(testData.B2B.getCompany());
				System.out.println("Input Company name!");
			}

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

	public float string2Num(String str) {
		str = str.replace("$", "").replace(",", "");
		float str_1 = Float.parseFloat(str);
		return str_1;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
}