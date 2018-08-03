package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA28217Test extends SuperTestClass {

	B2CPage b2cPage = null;
	private String testProduct;

	public NA28217Test(String store) {
		this.Store = store;
		this.testName = "NA-28217";

	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage, String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void NA28217(ITestContext ctx) {
		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);

			String closeHomePagePopUp = ".//*[@id='overlay-flashsales']/span/span";
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.closeHomePagePopUP(driver);
			if (Common.isElementExist(driver, By.xpath(closeHomePagePopUp))) {
				driver.findElement(By.xpath(closeHomePagePopUp)).click();
			}
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// Step 2 check homepage legal footer
			Dailylog.logInfoDB(2, "check homepage legal footer", Store, testName);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

			// Step 3 check deals legal footer
			if (!Common.checkElementExists(driver, b2cPage.Navigation_DEALS, 5)) {
				Dailylog.logInfoDB(3, "Deals link is not shown", Store, testName);
			}

			NavigationBar.goToDealsFirstLink(b2cPage);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

			// Click Laptops
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Thread.sleep(2000);
			Dailylog.logInfoDB(4, "Go to Laptops page", Store, testName);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

			// Click Tablets
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Tablets);
			Thread.sleep(2000);
			Dailylog.logInfoDB(4, "Go to Tablets page", Store, testName);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

			// Click Desktops
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.AllInOnes);
			Thread.sleep(2000);
			Dailylog.logInfoDB(4, "Go to Desktops page", Store, testName);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

			Dailylog.logInfoDB(5, "pdp page check legalFooter", Store, testName);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultMTMPN());
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

			Dailylog.logInfoDB(6, "cart page check legalFooter", Store, testName);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			Thread.sleep(2000);
			
			if (Common.checkElementExists(driver, b2cPage.Navigation_CartIcon, 5)) {
				//website is direct
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));

				B2CCommon.clearTheCart(driver, b2cPage, testData);
				Thread.sleep(2000);
				b2cPage.Cart_QuickOrderTextBox.sendKeys(testProduct);
				b2cPage.Cart_AddButton.click();

				Dailylog.logInfoDB(7, "Clicked checkout.", Store, testName);
				b2cPage.Cart_CheckoutButton.click();

				String tempEmail = EMailCommon.getRandomEmailAddress();
				String firstName = Common.getDateTimeString();
				String lastName = Common.getDateTimeString();

				// Fill shipping info
				Actions actions = new Actions(driver);

				if (Common.isElementExist(driver,
						By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]"))) {
					actions.moveToElement(b2cPage.EditAddress).click().perform();
				}
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));
				B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail);
				Dailylog.logInfoDB(2, "Clicked Shipping_ContinueButton.", Store, testName);
				Thread.sleep(5000);
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
				//
				if (Common.isElementExist(driver, By.id("address_sel"))) {
					// US
					Select dropdown = new Select(driver.findElement(By.id("address_sel")));
					dropdown.selectByIndex(1);
					b2cPage.Shipping_AddressMatchOKButton.click();
				} else if (Common.isElementExist(driver,
						By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
					if (b2cPage.Shipping_validateAddressItem.isDisplayed()) {
						// USEPP
						b2cPage.Shipping_validateAddressItem.click();
					}
					// USBPCTO
					b2cPage.Shipping_AddressMatchOKButton.click();
				}
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));
				if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel, 5)) {
					Common.javascriptClick(driver,
							driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));

				} else if (Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']"))) {
					// Fill payment info
					Common.javascriptClick(driver,
							driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_CARD']")));
					B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
				}

				B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());

				Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
				Dailylog.logInfoDB(2, "Clicked Payment_ContinueButton.", Store, testName);

				Thread.sleep(6000);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				Thread.sleep(6000);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.LegalFooter, 5));
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
