package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class COMM482Test extends SuperTestClass {

	private B2BPage b2bPage;
	public HMCPage hmcPage;

	public String Url;
	public String subscription = "RR00000003";
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
	
	public COMM482Test(String Store) {
		this.Store = Store;

		this.testName = "COMM-482";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}



	@Test(alwaysRun = true, groups = { "commercegroup", "payment", "p2", "b2c" })
	public void COMM482(ITestContext ctx) {

		try {
			this.prepareTest();

			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Common.sleep(4500);

			hmcPage.B2BUnit_siteAttribute.click();
			Dailylog.logInfoDB(3, "Set Disable Payment Profile Fallback to No", Store, testName);
			hmcPage.B2BUnit_disablePaymentProfileFallbackNo.click();
			hmcPage.B2BUnit_Save.click();

			Common.sleep(5000);
			Dailylog.logInfoDB(1, "check payment type ", Store, testName);
			checkPaymentType();

			Dailylog.logInfoDB(2, "create payment profile", Store, testName);
			createNewProfile();

			driver.get(testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			b2bPage.HomePage_CartIcon.click();
			Dailylog.logInfoDB(4, "add subscription product to cart", Store, testName);
			Common.sleep(1500);
			if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
				driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
			}
			B2BCommon.addProduct(driver, b2bPage, subscription);
			
			

			Dailylog.logInfoDB(5, "checkout", Store, testName);
			b2bPage.cartPage_LenovoCheckout.click();

			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			
			
			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}

			Thread.sleep(3000);

		
			Dailylog.logInfoDB(6, " Check the display of PO and Credit Card payment", Store, testName);
			Assert.assertFalse("cart are hidden from payment page faild",
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']")));

			Assert.assertTrue("PO on payment page",
					Common.checkElementExists(driver, b2bPage.paymentPage_PurchaseOrder, 5));

			Dailylog.logInfoDB(7, " Select PO", Store, testName);
			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			Dailylog.logInfoDB(8, "payment continue", Store, testName);
			
			B2BCommon.fillDefaultB2bBillingAddress (driver,b2bPage, testData);
			


			if(Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))){
				b2bPage.placeOrderPage_ResellerID.clear();
				b2bPage.placeOrderPage_ResellerID.sendKeys("2900718028");
	
			}
			
			Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_PlaceOrder.click();
		
			Common.sleep(10000);
			
			Assert.assertTrue("go to thankyou page failed!",driver.getCurrentUrl().toString().contains("Confirmation"));
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	

	private void checkPaymentType() {
		String[] paymentType = { "PURCHASEORDER", "CARD", "PAYPAL", "AMAZON", "BANKDEPOSIT" };

		for (int i = 0; i < paymentType.length; i++) {
			if (Common.checkElementDisplays(driver,
					By.xpath(".//td[contains(@id,'[B2BUnit.paymentTypeAndPayerId]]_"+paymentType[i]+"')]"), 4)) {
				Dailylog.logInfoDB(1, paymentType[i] + " Payment type is already existing !!", Store, testName);

			} else {
				AddPaymentType(paymentType[i]);
			}
		}

	}

	public void AddPaymentType(String paymentType) {
		Dailylog.logInfoDB(1, "ADD Payment type : " + paymentType, Store, testName);
		Common.rightClick(driver, hmcPage.paymentProfileGlobalTab_paymentTypeAndPayerIDTop);
		Common.sleep(1000);
		hmcPage.b2bUnitSiteAttribute_addPaymentTypePayerID.click();
		Common.switchToWindow(driver, 1);
		Common.sleep(1000);
		hmcPage.checkoutPaymentType_identifierTxtBox.clear();
		hmcPage.checkoutPaymentType_identifierTxtBox.sendKeys(paymentType);
		hmcPage.B2BUnit_SearchButton.click();
		Common.sleep(5000);
		hmcPage.checkoutPaymentType_searchedResult.click();
		hmcPage.checkoutPaymentType_useButton.click();
		Common.sleep(1000);
		Common.switchToWindow(driver, 0);
		hmcPage.B2BUnit_Save.click();
		
		Common.sleep(5000);

	}

	@AfterTest(alwaysRun = true, enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {

		Dailylog.logInfoDB(14, "rollback", Store, testName); // roll back
		SetupBrowser();
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);

		Common.sleep(5000);
		deletePaymentProfile();
		
	}

	private void deletePaymentProfile() {
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		
		Common.sleep(3000);
		hmcPage.paymentProfile_PaymentType.click();
		hmcPage.paymentProfile_PaymentTypeCard.click();
		while (true) {
			Common.sleep(1000);
			hmcPage.WCMS_Website_SearchButton.click();

			int count = driver
					.findElements(By.xpath("//table[contains(@id,'innertable')]/tbody/tr[contains(@id,'Card')]"))
					.size();
			System.out.println("card payment profile count: " + count);
			if (count != 0) {
				WebElement deleteBtn = driver.findElement(
						By.xpath("(//table[contains(@id,'innertable')]/tbody/tr[contains(@id,'Card')])[1]"));
				Common.mouseHover(driver, deleteBtn);
				Common.rightClick(driver, deleteBtn);
				hmcPage.b2cUnit_remove.click();
				// accept alert
				Alert alert = driver.switchTo().alert();
				alert.accept();
				Common.sleep(5000);
			}

			if (count == 1 || count == 0)
				break;

		}

	}

	private void createNewProfile() {

		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		Common.sleep(3000);

		Common.rightClick(driver, hmcPage.Home_paymentProfile);
		Common.sleep(1500);
		hmcPage.Home_paymentProfileCreate.click();

		// B2B Properties
		String profileName = "Card Profile 482";
		Common.sendFieldValue(hmcPage.paymentProfile_name, profileName);
		Common.sendFieldValue(hmcPage.paymentProfile_code, profileName);
		hmcPage.paymentProfile_paymentType.click();
		Dailylog.logInfoDB(1, "Clicked on cartpayment Type", Store, testName);
		hmcPage.paymentProfile_PaymentTypeCard.click();
		hmcPage.paymentProfile_configLevel.click();
		hmcPage.paymentProfile_configLevelGlobal.click();
		hmcPage.paymentProfile_channel.click();
		hmcPage.paymentProfile_channelB2B.click();
		hmcPage.paymentProfile_activeTrue.click();
		Dailylog.logInfoDB(1, "Clicked on Active : True", Store, testName);
		// B2C&B2B Properties
		hmcPage.paymentProfile_paymentB2BB2C.click();
		Common.sendFieldValue(hmcPage.paymentProfile_paymentB2BB2CDisplayName, profileName);
		// Global Tab
		hmcPage.paymentProfile_global.click();
		Dailylog.logInfoDB(1, "Clicked on Global Tab", Store, testName);
		hmcPage.paymentProfile_globalSubscriptionYes.click();


		hmcPage.B2BCustomer_CreateButton.click();
		Dailylog.logInfoDB(1, "Saved all the changes", Store, testName);
		Common.sleep(2000);
	}

}
