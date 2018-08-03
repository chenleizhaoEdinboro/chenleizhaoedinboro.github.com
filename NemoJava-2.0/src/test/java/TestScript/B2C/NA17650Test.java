package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class NA17650Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String orderNum;

	public NA17650Test(String store) {
		this.Store = store;
		this.testName = "NA-17650";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}
	
	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {

			driver.get(driver.getCurrentUrl().replace(
					"j_spring_security_check", "login"));
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}
	
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17650(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			// Login to HMC and set delivery timezone as NO
			setDeliveryTimeZone(1, "NO");

			// login to B2C Site and order product using quick cart option
			quickAddProduct(2);

			// check out the product and login with JP account
		
			if(Common.isElementExist(driver, By.xpath(".//*[@id='j_username']"))){
				checkoutProductAndLogin(3);
			}
			

			// Fill in shipping address and make payment
			productShippingAndPayment(4, false,
					"Delivery time zone is not present.");

			// Accept Term and conditions and place order
			placeOrder(5, false);

			// Go to My Account and check order history
			checkOrderHistory(6, false);

			// Login to HMC and set delivery timezone as Yes
			setDeliveryTimeZone(7, "YES");

			// login to B2C Site and order product using quick cart option
			quickAddProduct(8);

			// check out the product and login with JP account
			// checkoutProductAndLogin(9);

			// Fill in shipping address and make payment
			productShippingAndPayment(10, true,
					"Delivery time zone is present.");

			// Accept Term and conditions and place order
			placeOrder(11, true);

			// Go to My Account and check order history
			checkOrderHistory(12, true);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void checkOrderHistory(int stepNo, boolean status) {

		/*Dailylog.logInfoDB(stepNo, "Mouse hover on My Account icon.", Store,
				testName);
		Common.mouseHover(driver, b2cPage.Navigation_MyAccountIcon);
		Common.sleep(500);
		Common.mouseHover(driver, b2cPage.Navigation_MyAccountIcon);*/
		// b2cPage.Navigation_MyAccountIcon.click();
		
			/*b2cPage.Navigation_subMyAccountSpan.click();*/
		driver.get(testData.B2C.getHomePageUrl()+"/my-account");
			Dailylog.logInfoDB(stepNo, "Clicked on view history link.", Store,
					testName);
			
		if(Common.isElementExist(driver, By.xpath("//input[contains(@id,'login.email.id')]"))){
		B2CCommon.login(b2cPage, "testjp@sharklasers.com", "1q2w3e4r");
			
		}
		Common.sleep(2000);
		driver.get(testData.B2C.getHomePageUrl()+"/my-account");
		b2cPage.MyAccount_ViewOrderHistoryLink.click();
		driver.findElement(
				By.xpath(".//*[@id='accountOrderHistory']//a[contains(.,'"
						+ orderNum + "')]")).click();

		Dailylog.logInfoDB(stepNo, "Clicked on Order number : " + orderNum,
				Store, testName);

		Assert.assertEquals(
				status,
				Common.isElementExist(
						driver,
						By.xpath(".//*[@id='cart-price']//td[contains(.,'お届希望時間帯')]/../td[3]")));

		if (status) {
			String time = b2cPage.OrderHistory_DeliveryTimezone.getText();
			Assert.assertEquals("午前（8-12時）", time);
			Dailylog.logInfoDB(stepNo, "Delivery timezone is : " + time, Store,
					testName);
		} else {
			Dailylog.logInfoDB(stepNo, "Delivery timezone is not present.",
					Store, testName);
		}
		/*
		 * Dailylog.logInfoDB(stepNo, "Mouse hover on My Account icon.", Store,
		 * testName); Common.mouseHover(driver,
		 * b2cPage.Navigation_MyAccountIcon); Common.sleep(500);
		 * //b2cPage.Navigation_MyAccountIcon.click();
		 * b2cPage.Navigation_SignOutLink.click(); Dailylog.logInfoDB(stepNo,
		 * "Clicked on sign out button.", Store, testName);
		 */
	}

	public void placeOrder(int stepNo, boolean status) {
		Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
		//b2cPage.OrderSummary_AcceptTermsCheckBox.click();
		B2CCommon.clickPlaceOrder(b2cPage);
		orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
		Assert.assertEquals(status, Common.isElementExist(driver,
				By.xpath(".//*[contains(.,'お届希望時間帯')]")));
		Dailylog.logInfoDB(stepNo, "Order number is: " + orderNum, Store,
				testName);
	}

	public void productShippingAndPayment(int stepNo, boolean status,
			String message) throws InterruptedException {
		Dailylog.logInfoDB(stepNo, "Filling shipping details.", Store, testName);
		Common.sleep(2000);

		if (status) {
			if(Common.isElementExist(driver,
					By.xpath("//label[@for='DELIVERTIMEZONE']"))){
				Dailylog.logInfoDB(stepNo, "new UI", Store, testName);
				Assert.assertEquals(
						"午前（8-12時）",
						driver.findElement(
								By.xpath("(.//label[@for='DELIVERTIMEZONE'])[1]"))
								.getText());

				Assert.assertEquals(
						"午後（13-18時）",
						driver.findElement(
								By.xpath("(.//label[@for='DELIVERTIMEZONE'])[2]"))
								.getText());

				Assert.assertEquals(
						"夜間（18-20時）",
						driver.findElement(
								By.xpath("(.//label[@for='DELIVERTIMEZONE'])[3]"))
								.getText());

				Assert.assertEquals(
						"指定なし",
						driver.findElement(
								By.xpath("(.//label[@for='DELIVERTIMEZONE'])[4]"))
								.getText());
				b2cPage.OrderHistory_DeliveryTimezoneMorning.click();
			}else{
				Dailylog.logInfoDB(stepNo, "old UI", Store, testName);
				Assert.assertEquals(
						"午前（8-12時）",
						driver.findElement(
								By.xpath("(.//*[@name='deliverTimeZone']/..//strong)[1]"))
								.getText().trim());

				Assert.assertEquals(
						"午後（13-18時）",
						driver.findElement(
								By.xpath("(.//*[@name='deliverTimeZone']/..//strong)[2]"))
								.getText().trim());

				Assert.assertEquals(
						"夜間（18-20時）",
						driver.findElement(
								By.xpath("(.//*[@name='deliverTimeZone']/..//strong)[3]"))
								.getText().trim());

				Assert.assertEquals(
						"指定なし",
						driver.findElement(
								By.xpath("(.//*[@name='deliverTimeZone']/..//strong)[4]"))
								.getText().trim());
				b2cPage.OrderHistory_DeliveryTimezoneMorning_old.click();
			}
			
		}

		if (Common.isElementExist(driver,
				By.xpath("//*[@id='addressForm']/fieldset[1]/legend/a"))) {
			b2cPage.shippingEdit.click();
			Dailylog.logInfoDB(stepNo, "Clicked on edit button.", Store,
					testName);
		}

		B2CCommon.fillDefaultShippingInfo(b2cPage, testData);

		WebElement element = driver.findElement(By
				.xpath(".//*[@id='checkoutForm-shippingContinueButton']"));

		Common.javascriptClick(driver, element);
		Common.sleep(1000);

		Dailylog.logInfoDB(stepNo, "Filling payment details for JP site.",
				Store, testName);
		B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
		Common.sleep(2000);
		Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
		//b2cPage.Payment_ContinueButton.click();
		Common.sleep(1000);
		Dailylog.logInfoDB(stepNo, "Payment done successfully!!!", Store,
				testName);
	}

	public void checkoutProductAndLogin(int stepNo) {
		
		b2cPage.JPLogin_UserName.clear();
		b2cPage.JPLogin_UserName.sendKeys(testData.B2C.getLoginID());
		b2cPage.JPLogin_Password.clear();
		b2cPage.JPLogin_Password.sendKeys(testData.B2C.getLoginPassword());
		b2cPage.Login_LogInButton.click();
		Dailylog.logInfoDB(stepNo, "Logged in successfully.", Store, testName);
	}

	public void quickAddProduct(int stepNo) throws InterruptedException {
		Dailylog.logInfoDB(stepNo,
				"Login to B2C-JP Site and adding product to cart.", Store,
				testName);
		driver.get(testData.B2C.getloginPageUrl());
		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getloginPageUrl());
		
		Common.sleep(2500);
	

		B2CCommon.handleGateKeeper(b2cPage, testData);
		
		B2CCommon.login(b2cPage, "testjp@sharklasers.com", "1q2w3e4r");
		
		HandleJSpring(driver);
//		B2CCommon.handleGateKeeper(b2cPage, testData);
		Common.sleep(2000);
		b2cPage.Navigation_CartIcon.click();
		if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
			b2cPage.Navigation_ViewCartButton.click();
//		driver.get(testData.B2C.getHomePageUrl() + "/cart");
		B2CCommon.clearTheCart(driver, b2cPage, testData);
		Common.sleep(2000);
		Dailylog.logInfoDB(stepNo, "Adding product to CART.", Store, testName);
		addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
		Common.sleep(5000);
		By stockMes = By.xpath("//div[contains(text(),'ご入力された製品番号')]");
		if(Common.checkElementDisplays(driver, stockMes, 5)){
			addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN2());
		}
//		B2CCommon.addPartNumberToCart(b2cPage, "GX20J21008");
		// part number 80VF0018JP 90GB0045JP testData.B2C.getDefaultMTMPN() GX20J21008
		Dailylog.logInfoDB(stepNo,
				"mtmpn: " + testData.B2C.getDefaultMTMPN(), Store, testName);
		Dailylog.logInfoDB(stepNo,
				"Checking out the product and login to site.", Store, testName);
		Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
		b2cPage.Cart_CheckoutButton.click();

		
		
		
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
	}

	public void setDeliveryTimeZone(int stepNo, String status) {
		Dailylog.logInfoDB(stepNo, "Login HMC account.", Store, testName);
		driver.get(testData.HMC.getHomePageUrl());

		HMCCommon.Login(hmcPage, testData);
		Dailylog.logInfoDB(stepNo, "Searching JP unit at B2C Unit page.",
				Store, testName);

		HMCCommon.searchB2CUnit(hmcPage, testData);
		Dailylog.logInfoDB(stepNo, "Clicked on result.", Store, testName);
		Common.sleep(8000);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		
		Dailylog.logInfoDB(stepNo, "Clicked on Site Attribute.", Store,
				testName);
		new WebDriverWait(driver, 500)
		.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//span[contains(.,'Site Attributes')]")));
		
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(10000);

		Dailylog.logInfoDB(stepNo, "Selecting Delivery time zone as : "
				+ status, Store, testName);
		if (status.equals("NO")) {
			hmcPage.B2CSiteAttribute_DeliveryTimeZoneNo.click();
		} else {
			hmcPage.B2CSiteAttribute_DeliveryTimeZoneYes.click();
		}

		Dailylog.logInfoDB(stepNo, "Set Delivery time zone as : " + status
				+ " successfully!!!", Store, testName);
		Dailylog.logInfoDB(stepNo, "Clicked on SAVE button.", Store, testName);
		Common.javascriptClick(driver, hmcPage.Common_SaveButton);
		//hmcPage.Common_SaveButton.click();

		Dailylog.logInfoDB(stepNo, "Clicked on logout button.", Store, testName);
		hmcPage.HMC_Logout.click();
		Common.sleep(2000);
	}

}