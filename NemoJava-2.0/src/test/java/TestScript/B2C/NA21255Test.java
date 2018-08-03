package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21255Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String profileName;
	// private String pageLink = "http://cn.bing.com/";
	// private String linkLabel = "What is purchase order?";
	private String poLableDefault = "Purchase Order Payment";

	public NA21255Test(String store) {
		this.Store = store;
		this.testName = "NA-21255";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "payment", "p1", "b2c"})
	public void NA21255(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			By CopyAddress = By.xpath("//input[@id='copyAddressToBilling']");
			profileName = "PURCHASE ORDER Auto" + Store;
			String testProduct = testData.B2C.getDefaultMTMPN();

			// HMC->Nemo->Payment->Payment Type Customize->Payment Profile
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();

			// right click create new payment profile
			Common.scrollToElement(driver, hmcPage.Home_paymentProfile);
			Common.rightClick(driver, hmcPage.Home_paymentProfile);
			Common.waitElementClickable(driver, hmcPage.Home_paymentProfileCreate, 8000);
			hmcPage.Home_paymentProfileCreate.click();

			// Name：PURCHASE ORDER Auto
			hmcPage.paymentProfile_name.sendKeys(profileName);
			driver.findElement(By.xpath("//input[contains(@id,'code')]")).sendKeys(profileName);

			// Payment Type: Purchase Order
			hmcPage.paymentProfile_PaymentType.click();
			hmcPage.paymentProfile_PaymentTypePo.click();

			// Configuration Level ：Specific
			hmcPage.paymentProfile_configLevel.click();
			hmcPage.paymentProfile_configLevelS.click();

			// Chanel：ALL
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelALL.click();

			// Active
			hmcPage.paymentProfile_activeTrue.click();

			// // Help url:added because of the required verification in step 6
			// hmcPage.paymentProfile_disableHelpLinkNo.click();
			// hmcPage.paymentProfile_helpLinkLabel.clear();
			// hmcPage.paymentProfile_helpLinkLabel.sendKeys(linkLabel);
			// hmcPage.paymentProfile_helpLinkType.click();
			// hmcPage.paymentProfile_helpLinkTypeNew.click();
			// hmcPage.paymentProfile_pageLink.clear();
			// hmcPage.paymentProfile_pageLink.sendKeys(pageLink);
			//
			// // Image:added because of the required verification in step 6
			// hmcPage.paymentProfile_disablePaymentImageNo.click();
			// hmcPage.paymentProfile_paymentImgage.click();
			// Thread.sleep(5000);
			// switchToWindow(1);
			// hmcPage.paymentProfile_paymentImgageMedia.clear();
			// hmcPage.paymentProfile_paymentImgageMedia.sendKeys("image");
			// hmcPage.paymentProfile_paymentImgageSearch.click();
			// Common.checkElementExists(driver,
			// hmcPage.paymentProfile_paymentImgageResult, 120);
			// System.out.println("image result is display");
			// hmcPage.paymentProfile_paymentImgageResultFirst.click();
			// hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
			// switchToWindow(0);

			// B2C&B2B Properties tab, add display label: PURCHASE ORDER Auto
			hmcPage.paymentProfile_paymentB2BB2C.click();
			hmcPage.paymentProfile_paymentB2BB2CDisplayName.clear();
			hmcPage.paymentProfile_paymentB2BB2CDisplayName.sendKeys(profileName);

			// Country unit tab, add countries:
			hmcPage.paymentProfile_countries.click();
			Common.scrollToElement(driver, hmcPage.paymentProfile_countriesAddCountryTable);
			Common.rightClick(driver, hmcPage.paymentProfile_countriesAddCountryTable);
			Common.javascriptClick(driver, hmcPage.paymentProfile_countriesAddCountry);
//			hmcPage.paymentProfile_countriesAddCountry.click();
			Thread.sleep(5000);
			switchToWindow(1);
			hmcPage.paymentProfile_countriesIsocode.clear();
			hmcPage.paymentProfile_countriesIsocode.sendKeys(testData.Store.substring(0, 2));
			hmcPage.paymentProfile_paymentImgageSearch.click();
			Common.checkElementExists(driver, hmcPage.paymentProfile_paymentImgageResult, 120);
			System.out.println("image result is display");
//			hmcPage.paymentProfile_paymentImgageResultFirst.click();
			By targetAcc = By.xpath("//div[contains(@id,'" + testData.Store.substring(0, 2) + "')]");
			Common.waitElementClickable(driver, driver.findElement(targetAcc), 5);
			driver.findElement(targetAcc).click();
			hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
			
			if (Browser.toLowerCase().startsWith("ie")) {
				// Global tab,Disable Mixed Order:Yes
				switchToWindow(0);
				Common.sleep(2000);
				switchToWindow(1);
				Common.javascriptClick(driver, hmcPage.paymentProfile_global);
				Common.javascriptClick(driver, hmcPage.paymentProfile_globalMixedOrderYes);
				

				// create
				Common.javascriptClick(driver, hmcPage.paymentProfile_paymentCreate);
				driver.close();
				switchToWindow(0);
				hmcPage.HMC_Logout.click();
				Common.sleep(1500);
				Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
				
				driver.manage().deleteAllCookies();
				driver.navigate().refresh();
				HMCCommon.Login(hmcPage, testData);
				
				addPayment("PURCHASEORDER");
				Dailylog.logInfoDB(2, "add PURCHASEORDER to B2C unit", Store, testName);

				// "Disable Payment Profile Fallback" :Yes
				hmcPage.B2CUnit_disablePaymentProfileFallbackYes.click();
				hmcPage.B2BUnit_Save.click();
				Thread.sleep(2000);

				Dailylog.logInfoDB(3, "disablePaymentProfileFallback: Yes", Store, testName);
			}else {
				switchToWindow(0);

				// Global tab,Disable Mixed Order:Yes
				Common.javascriptClick(driver, hmcPage.paymentProfile_global);
				Common.javascriptClick(driver, hmcPage.paymentProfile_globalMixedOrderYes);
				

				// create
				Common.javascriptClick(driver, hmcPage.paymentProfile_paymentCreate);
				System.out.println("paymentProfile created");
				Thread.sleep(5000);
				Dailylog.logInfoDB(1, "create new PURCHASEORDER payment profile", Store, testName);

				// B2C UNIT->Site Attributes->Payment Type->Add PURCHASE ORDER

				addPayment("PURCHASEORDER");
				Dailylog.logInfoDB(2, "add PURCHASEORDER to B2C unit", Store, testName);

				// "Disable Payment Profile Fallback" :Yes
				hmcPage.B2CUnit_disablePaymentProfileFallbackYes.click();
				hmcPage.B2BUnit_Save.click();
				Thread.sleep(2000);

				Dailylog.logInfoDB(3, "disablePaymentProfileFallback: Yes", Store, testName);
			}
			
			
			
			
			// Go to B2C website, add item to cart
			// checkout and proceed to payment page
			
			addLapTopsToCart(CopyAddress, testProduct);
			Dailylog.logInfoDB(4, "add product to cart and check out to payment", Store, testName);

			// purchase order payment label did not change
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER")),
					"isElementExist PURCHASEORDER");
//			System.out.println(b2cPage.Payment_purchaseOrderLabel.getText());
			if(!b2cPage.Payment_purchaseOrderLabel.getText().equals(poLableDefault) && !b2cPage.Payment_purchaseOrderLabel.findElement(By.xpath("/..")).getText().equals(poLableDefault))
				Assert.assertEquals(b2cPage.Payment_purchaseOrderLabel.getText(), poLableDefault);

			// "Disable Payment Profile Fallback" :NO
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_disablePaymentProfileFallbackNo.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);

			Dailylog.logInfoDB(5, "disablePaymentProfileFallback: No", Store, testName);

			// Go to B2B website, add item to cart,
			// checkout and proceed to payment page
			addLapTopsToCart(CopyAddress, testProduct);
			Dailylog.logInfoDB(6, "add product to cart and check out to payment", Store, testName);

			// purchase order payment label changed to PURCHASE ORDER Auto
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER")),
					"isElementExist PURCHASEORDER");
//			System.out.println(b2cPage.Payment_purchaseOrderLabel.getText());
			if(!b2cPage.Payment_purchaseOrderLabel.getText().equals(profileName) && !b2cPage.Payment_purchaseOrderLabel.findElement(By.xpath("/..")).getText().equals(profileName))
				Assert.assertEquals(b2cPage.Payment_purchaseOrderLabel.getText(), profileName);

			// no other image or help link after purchase order(NA-21257)?
			Assert.assertFalse(
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_PURCHASEORDER']/..//a")),
					"isElementExist PURCHASEORDER link");
			Assert.assertFalse(
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_PURCHASEORDER']/..//img")),
					"isElementExist PURCHASEORDER img");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	// Roll back by deleting step1 payment profile
	@AfterTest(alwaysRun = true)
	public void rollback() throws InterruptedException, MalformedURLException {
		System.out.println("rollback"); // roll back
		SetupBrowser();
		Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		hmcPage.paymentProfile_attributeselect.click();
		hmcPage.paymentProfile_attributeselectName.click();

		Common.checkElementExists(driver, hmcPage.paymentProfile_nameInput, 120);
		hmcPage.paymentProfile_nameInput.clear();
		hmcPage.paymentProfile_nameInput.sendKeys(profileName);
		hmcPage.paymentProfile_paymentType.click();
		hmcPage.paymentProfile_paymentTypePo.click();
		hmcPage.paymentProfile_paymentSearch.click();

		Thread.sleep(5000);
		for (int i = 0; i < 5; i++) {
			if (Common.isElementExist(driver, By.xpath("//tr[contains(@id,'Purchase Order')]"))) {
				Common.rightClick(driver, hmcPage.paymentProfile_poResult);
				hmcPage.paymentProfile_remove.click();
				driver.switchTo().alert().accept();
				Thread.sleep(2000);
			} else
				break;
		}

		// Assert.assertFalse(Common.isElementExist(driver,
		// By.xpath("//tr[contains(@id,'Purchase Order')]")),
		// "isElementExist hmcPage.paymentProfile_poResult");

	}

	private void addPayment(String identifier) throws InterruptedException {
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();

		if (!Common.isElementExist(driver,
				By.xpath("//*[contains(@title,'dynamicPaymentType')]//div[contains(text(),'" + identifier + "')]"))) {
			Common.rightClick(driver, hmcPage.B2CUnit_paymentTypeTable);
			hmcPage.B2CLeasing_add.click();
			switchToWindow(1);
			hmcPage.B2CUnit_paymentTypeInput.sendKeys(identifier);
			hmcPage.PaymentLeasing_searchbutton.click();
			hmcPage.B2CLeasing_searchedResult1.click();
			hmcPage.B2CLeasing_use.click();
			switchToWindow(0);
			hmcPage.B2BUnit_Save.click();
			Common.sleep(1000);

		}
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void addLapTopsToCart(By CopyAddress, String product) throws InterruptedException {

		Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
		B2CCommon.handleGateKeeper(b2cPage, testData);
		// Common.NavigateToUrl(driver.getCurrentUrl() + "/login");
		// B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
		// testData.B2C.getLoginPassword());
		Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");

		B2CCommon.addPartNumberToCart(b2cPage, product);
		Thread.sleep(10000);
		try {
			if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='window-close']"), 5)) {
				Common.sleep(5000);
				b2cPage.Close_PopUpJP.click();
			}
		}catch(WebDriverException e) {
			
		}

		b2cPage.Cart_CheckoutButton.click();

		if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 3)) {
			b2cPage.Checkout_StartCheckoutButton.click();
		}
		// checkout and proceed to payment page
//		String tempEmail = EMailCommon.getRandomEmailAddress();
//		String firstName = Common.getDateTimeString();
//		String lastName = Common.getDateTimeString();

		// Fill shipping info
		if (Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 2))
			b2cPage.Shipping_editAddress.click();
//		B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
//				testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
//				testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail);
		B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
		B2CCommon.handleAddressVerify(driver, b2cPage);
	}

}
