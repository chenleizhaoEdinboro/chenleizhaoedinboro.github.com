package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21297Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	private String profileName = "BANK DEPOSIT Auto";
	private String pageLink = "http://cn.bing.com/";
	private String linkLabel = "What is bank deposit?";

	public NA21297Test(String store) {
		this.Store = store;
		this.testName = "NA-21297";
	}

	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addPayment(String identifier) throws InterruptedException {
		HMCCommon.searchB2BUnit(hmcPage, testData);
		hmcPage.B2BUnit_siteAttribute.click();
		if (!Common.isElementExist(driver,
				By.xpath(".//*[contains(@id,'paymentTypeAndPayerId')]/td[contains(text(),'" + identifier + "')]"))) {
			Common.rightClick(driver, hmcPage.B2BUnit_paymentTypeTable);
			hmcPage.B2BUnit_add.click();
			switchToWindow(1);
			hmcPage.B2BUnit_identifier.sendKeys(identifier);
			hmcPage.B2BUnit_SearchButton.click();
			hmcPage.B2BUnit_searchedPayment.click();
			hmcPage.B2BUnit_use.click();
			switchToWindow(0);
			hmcPage.B2BUnit_Save.click();
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

	public void addLapTopsToCart() throws InterruptedException {
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
		b2bPage.HomePage_productsLink.click();
		b2bPage.HomePage_Laptop.click();
		String plpURL = driver.getCurrentUrl();
		boolean isProductAdded = false;
		// add contract product to cart
		if (Common.isElementExist(driver, By.xpath("//*[contains(@title,'Add to Cart')]"))) {
			for (int i = 0; i < b2bPage.PLPPage_addToCart.size(); i++) {
				b2bPage.PLPPage_addToCart.get(i).click();
				Common.waitElementClickable(driver, b2bPage.Product_contractAddToCartOnPopup, 60);
				b2bPage.Product_contractAddToCartOnPopup.click();
				Common.waitElementClickable(driver, b2bPage.ProductPage_AlertGoToCart, 60);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2bPage.ProductPage_AlertGoToCart);
				Thread.sleep(1000);
				if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
					isProductAdded = true;
					System.out.println("Contract product added successfully.");
					break;
				} else {
					driver.get(plpURL);
				}
			}
		}
		// contract product add failure, add agreement product
		if (!isProductAdded) {
			int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
			for (int i = 0; i < viewDetailsNo; i++) {
				b2bPage.PLPPage_viewDetails.get(i).click();
				if (isAlertPresent()) {
					System.out.println(driver.switchTo().alert().getText() + " Try next product!");
					driver.switchTo().alert().accept();
					driver.get(plpURL);
				} else {
					String pdpURL = driver.getCurrentUrl();
					String partNum = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
					System.out.println("Product Number: " + partNum);
					Thread.sleep(1000);
					if (Common.isElementExist(driver,
							By.xpath("// *[contains(@id,'Alert')]//*[contains(@id,'_add_to_cart')]"))) {
						Thread.sleep(500);
						if (b2bPage.PDPPage_agreementAddToCartOnPopup.isDisplayed())
							((JavascriptExecutor) driver).executeScript("arguments[0].click();",
									b2bPage.PDPPage_agreementAddToCartOnPopup);
						else {
							b2bPage.Agreement_agreementsAddToCart.click();
							b2bPage.HomePage_CartIcon.click();
						}

					} else {
						b2bPage.Agreement_agreementsAddToCart.click();
						b2bPage.HomePage_CartIcon.click();
					}
					Thread.sleep(1000);
					if (Common.isElementExist(driver, By.xpath("//div[@class='cart-item']"))) {
						System.out.println("Agreement product added successfully.");
						break;
					} else {
						driver.get(plpURL);
					}
				}
			}
		}
	}

	@Test(priority=0,alwaysRun = true, groups = {"commercegroup", "payment", "p2", "b2b"})
	public void NA21297(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			// Step 1 Go to HMC --> Nemo --> Payment --> Payment Type Customize
			// --> Payment Profile, right click create new payment profile
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();
			Common.rightClick(driver, hmcPage.Home_paymentProfile);
			Common.waitElementClickable(driver, hmcPage.Home_paymentProfileCreate, 8000);
			hmcPage.Home_paymentProfileCreate.click();

			// Under B2C Properties tab:
			// (1) Add name : BANK DEPOSIT Auto
			// (2) Choose payment type : Direct Deposit
			// (3) Choose configuration level (Global -> payment level, specific
			// -> unit level) : SPECIFIC
			hmcPage.paymentProfile_name.sendKeys(profileName);
			hmcPage.paymentProfile_PaymentType.click();
			hmcPage.paymentProfile_PaymentTypeDd.click();
			hmcPage.paymentProfile_configLevel.click();
			hmcPage.paymentProfile_configLevelS.click();

			// (4) Choose Channel (applies to B2C or B2B or ALL) : B2B
			// (5) Set active to Yes
			// (6) Give a priority number (sequence number) : 20

			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelB2B.click();
			hmcPage.paymentProfile_activeTrue.click();
			hmcPage.paymentProfile_priority.clear();
			hmcPage.paymentProfile_priority.sendKeys("20");

			// (7) Choose to disable Help URL or not : No
			// (8) Add URL Label: "What is bank deposit?"
			// (9) Choose if the URL opens a new page or a popup : NEWPAGE
			hmcPage.paymentProfile_disableHelpLinkNo.click();
			hmcPage.paymentProfile_helpLinkLabel.clear();
			hmcPage.paymentProfile_helpLinkLabel.sendKeys(linkLabel);
			hmcPage.paymentProfile_helpLinkType.click();
			hmcPage.paymentProfile_helpLinkTypeNew.click();

			// (10) Attach URL if necessary : http://cn.bing.com/
			// (11) Edit popup if necessary
			// (12) choose disable image or not : NO
			// (13) attach image
			hmcPage.paymentProfile_pageLink.clear();
			hmcPage.paymentProfile_pageLink.sendKeys(pageLink);
			hmcPage.paymentProfile_disablePaymentImageNo.click();
			hmcPage.paymentProfile_paymentImgage.click();
			Thread.sleep(5000);
			switchToWindow(1);
			hmcPage.paymentProfile_paymentImgageMedia.clear();
			hmcPage.paymentProfile_paymentImgageMedia.sendKeys("image");
			hmcPage.paymentProfile_paymentImgageSearch.click();

			Common.checkElementExists(driver, hmcPage.paymentProfile_paymentImgageResult, 120);
			System.out.println("image result is display");
			hmcPage.paymentProfile_paymentImgageResultFirst.click();
			hmcPage.B2BUnit_DeliveryMode_SettingUse.click();

			switchToWindow(0);

			// Go to B2C&B2B Properties tab, add display label: BANK DEPOSIT
			// Auto
			hmcPage.paymentProfile_paymentB2BB2C.click();
			hmcPage.paymentProfile_paymentB2BB2CDisplayName.clear();
			hmcPage.paymentProfile_paymentB2BB2CDisplayName.sendKeys(profileName);

			// Go to Global tab
			// Disable Mixed Order:Yes
			// Disable DCG Order:Yes
			hmcPage.paymentProfile_global.click();
			hmcPage.paymentProfile_globalMixedOrderYes.click();
			hmcPage.paymentProfile_globalMixedDcgYes.click();

			// Go to Countries tab, add country :
			hmcPage.paymentProfile_countries.click();
			Common.rightClick(driver, hmcPage.paymentProfile_countriesAddCountryTable);
			hmcPage.paymentProfile_countriesAddCountry.click();
			Thread.sleep(5000);
			switchToWindow(1);
			hmcPage.paymentProfile_countriesIsocode.clear();
			hmcPage.paymentProfile_countriesIsocode.sendKeys(testData.Store);
			hmcPage.paymentProfile_paymentImgageSearch.click();
			Common.checkElementExists(driver, hmcPage.paymentProfile_paymentImgageResult, 120);
			System.out.println("image result is display");
			hmcPage.paymentProfile_paymentImgageResultFirst.click();
			hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
			switchToWindow(0);

			// click create button
			hmcPage.PaymentMessage_DescInputCode.sendKeys("1234");
			hmcPage.paymentProfile_paymentCreate.click();
			System.out.println("paymentProfile created");
			Thread.sleep(5000);

			// Step 2 B2C Commerce --> B2C UNIT --> Site Attributes --> Payment
			// Type --> Add BANK DEPOSIT
			addPayment("BANKDEPOSIT");
			System.out.println("BANKDEPOSIT payment added");
			// Step 3 B2B Commerce --> B2B UNIT --> Site Attributes --> Payment
			// Attributes
			// Step 4 If "Disable Payment Profile Fallback" is set to Yes, then
			// this unit will not read the profile of parent level,Choose Yes
			hmcPage.B2BUnit_disablePaymentProfileFallbackYes.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			hmcPage.Home_B2BCommerceLink.click();
			System.out.println("disablePaymentProfileFallback: Yes");
			// Step 5
			// Go to B2B website, add item to cart
			addLapTopsToCart();
			// checkout and proceed to payment page
			b2bPage.cartPage_LenovoCheckout.click();
			if (Store.toLowerCase().equals("us")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "us", "test", "test", "Georgia", "1535 Broadway",
						"New York", "New York", "10036-4077", "2129450100");
			} else if (Store.toLowerCase().equals("au")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "au", "test", "test", "adobe_global", "62 Streeton Dr",
						"RIVETT", "Australian Capital Territory", "2611", "2123981900");
			}
			Thread.sleep(1000);
			b2bPage.shippingPage_ContinueToPayment.click();

			// label change to BANK DEPOSIT
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_BANKDEPOSIT")),
					"isElementExist BANK DEPOSIT");
			Assert.assertEquals(b2bPage.Payment_bankDepositLabel.getText(), "Direct Deposit");
			// Step 6 Set "Disable Payment Profile Fallback" to no
			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.B2BUnit_disablePaymentProfileFallbackNo.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			hmcPage.Home_B2BCommerceLink.click();
			System.out.println("disablePaymentProfileFallback: No");

			// Step 7 Go to site, add item to cart, checkout and proceed to
			// payment page(need expect) label change to BANK DEPOSIT Auto

			addLapTopsToCart();
			// checkout and proceed to payment page
			b2bPage.cartPage_LenovoCheckout.click();
			if (Store.toLowerCase().equals("us")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "us", "test", "test", "Georgia", "1535 Broadway",
						"New York", "New York", "10036-4077", "2129450100");
			} else if (Store.toLowerCase().equals("au")) {
				B2BCommon.fillB2BShippingInfo(driver, b2bPage, "au", "test", "test", "adobe_global", "62 Streeton Dr",
						"RIVETT", "Australian Capital Territory", "2611", "2123981900");
			}
			Thread.sleep(1000);
			b2bPage.shippingPage_ContinueToPayment.click();
			// label changed to BANK DEPOSIT Auto
			Assert.assertTrue(Common.isElementExist(driver, By.id("PaymentTypeSelection_BANKDEPOSIT")),
					"isElementExist BANK DEPOSIT");
			Assert.assertEquals(b2bPage.Payment_bankDepositLabel.getText(), profileName);
			// Step 8 There is one link "What is bank deposit?" and the before
			// added picture(STEP1) with Bank Deposit Label
			// Click "What is bank deposit?"(need expect)
			// Assert.assertTrue(
			// Common.isElementExist(driver,
			// By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/..//a")),
			// "isElementExist bank deposit link");
			// Assert.assertTrue(
			// Common.isElementExist(driver,
			// By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/..//img")),
			// "isElementExist bank deposit img");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	// Step 9 Roll back by deleting step1 payment profile
	@AfterTest(alwaysRun = true)
	public void rollback() throws InterruptedException, MalformedURLException {
		System.out.println("rollback"); // roll back
		SetupBrowser();
		driver.get(testData.HMC.getHomePageUrl());
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
		hmcPage.paymentProfile_paymentTypeDD.click();
		hmcPage.paymentProfile_paymentSearch.click();

		Thread.sleep(5000);
		if (Common.isElementExist(driver, By.xpath("//tr[contains(@id,'Direct Deposit')]"))) {
			Common.rightClick(driver, hmcPage.paymentProfile_ddResult);
			hmcPage.paymentProfile_remove.click();
			driver.switchTo().alert().accept();
			Thread.sleep(2000);
		}
	}

}
