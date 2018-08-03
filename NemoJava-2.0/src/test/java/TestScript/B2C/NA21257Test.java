package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21257Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String profileName = "BANK DEPOSIT Auto";
	private String cardProfileName = "Card Auto";
	private String pageLink = "http://cn.bing.com/";
	private String linkLabel = "What is bank deposit?";

	public NA21257Test(String store) {
		this.Store = store;
		this.testName = "NA-21257";
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
			System.out.println("BANKDEPOSIT payment added");
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

	public void addLapTopsToCart(By CopyAddress) throws InterruptedException {

		Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());

		if (b2cPage.PageDriver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
			B2CCommon.handleGateKeeper(b2cPage, testData);
		} else {
			B2CCommon.handleGateKeeper(b2cPage, testData);
			// login B2C
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());

			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
		}
		String loginID = testData.B2C.getLoginID();
		String pwd = testData.B2C.getLoginPassword();

		B2CCommon.handleGateKeeper(b2cPage, testData);
		HandleJSpring(driver, b2cPage, loginID, pwd);
		// empty cart
		B2CCommon.clearTheCart(driver, b2cPage, testData);
		// add product to cart
		B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

		Dailylog.logInfoDB(2, "Checkout and input shipping information.", Store, testName);
		b2cPage.lenovo_checkout.click();
		// checkout and proceed to payment page
		String tempEmail = EMailCommon.getRandomEmailAddress();
		String firstName = Common.getDateTimeString();
		String lastName = Common.getDateTimeString();

		// Fill shipping info
		if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
			B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail,
					testData.B2C.getConsumerTaxNumber());
		}
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);

		B2CCommon.handleAddressVerify(driver, b2cPage);
		Thread.sleep(10000);
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "payment", "p2", "b2c" })
	public void NA21257(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			By CopyAddress = By.xpath("//input[@id='copyAddressToBilling']");
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

			// (4) Choose Channel (applies to B2C or B2B or ALL) : B2C
			// (5) Set active to Yes
			// (6) Give a priority number (sequence number) : 20

			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelB2C.click();
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

			// Go to b2cUnits tab, add unit :
			hmcPage.paymentProfile_B2CUnits.click();
			Common.rightClick(driver, hmcPage.paymentProfile_B2CUnitsAddUnit);
			hmcPage.paymentProfile_B2CUnitsCreateUnit.click();
			Thread.sleep(5000);
			switchToWindow(1);
			hmcPage.paymentProfile_B2CUnitsIsocode.clear();
			hmcPage.paymentProfile_B2CUnitsIsocode.sendKeys(testData.B2C.getUnit());
			hmcPage.paymentProfile_paymentImgageSearch.click();
			Common.checkElementExists(driver, hmcPage.paymentProfile_paymentImgageResult, 120);
			System.out.println("unit result is display");
			hmcPage.paymentProfile_paymentImgageResultFirst.click();
			hmcPage.B2BUnit_DeliveryMode_SettingUse.click();
			switchToWindow(0);

			// click create button
			hmcPage.PaymentMessage_DescInputCode.sendKeys("1234");
			hmcPage.paymentProfile_paymentCreate.click();
			Thread.sleep(5000);
			System.out.println(" deposit paymentProfile created");

			Common.rightClick(driver, hmcPage.Home_paymentProfile);
			Common.waitElementClickable(driver, hmcPage.Home_paymentProfileCreate, 8000);
			hmcPage.Home_paymentProfileCreate.click();
			hmcPage.paymentProfile_name.sendKeys(cardProfileName);
			hmcPage.paymentProfile_PaymentType.click();
			hmcPage.paymentProfile_PaymentTypeCard.click();
			hmcPage.paymentProfile_configLevel.click();
			hmcPage.paymentProfile_configLevelS.click();
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelB2C.click();
			hmcPage.paymentProfile_activeTrue.click();

			hmcPage.PaymentProfile_B2CB2BProperties.click();
			hmcPage.PaymentProfile_DisplayName.clear();
			hmcPage.PaymentProfile_DisplayName.sendKeys("Card Auto");
			hmcPage.PaymentProfile_Countries.click();
			hmcPage.PaymentProfile_codeValue.clear();
			hmcPage.PaymentProfile_codeValue.sendKeys("card_auto");
			Common.rightClick(driver, hmcPage.PaymentProfile_addCountriesList);
			hmcPage.PaymentProfile_addCountry.click();
			switchToWindow(1);
			hmcPage.PaymentProfile_ISOCode.clear();
			hmcPage.PaymentProfile_ISOCode.sendKeys(this.Store);
			hmcPage.PaymentProfile_ISOCodeSearch.click();
			Common.doubleClick(driver,
					driver.findElement(By.xpath(".//*[@id='StringDisplay[" + this.Store + "]_span']")));
			switchToWindow(0);
			hmcPage.paymentProfile_paymentCreate.click();

			Thread.sleep(5000);

			// Step 2 B2C Commerce --> B2C UNIT --> Site Attributes --> Payment
			// Type --> Add BANK DEPOSIT

			addPayment("BANKDEPOSIT");

			// Step 3 B2C Commerce --> B2C UNIT --> Site Attributes --> Payment
			// Attributes
			// If "Disable Payment Profile Fallback" is set to Yes, then
			// this unit will not read the profile of parent level,Choose Yes

			hmcPage.B2CUnit_disablePaymentProfileFallbackYes.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);
			System.out.println("disablePaymentProfileFallback: Yes");

			// Step 4
			// Go to B2C website, add item to cart
			// checkout and proceed to payment page
			addLapTopsToCart(CopyAddress);

			// label changed to BANK DEPOSIT Auto
			// wait until payment continue button displays
			Common.checkElementDisplays(driver, b2cPage.Payment_ContinueButton, 60);
			if (!b2cPage.Payment_bankDepositLabel.getText().contains(profileName)) {
				Assert.fail("Payment message wrong!");
			}
			// Step 5 Set "Disable Payment Profile Fallback" to no
			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_disablePaymentProfileFallbackNo.click();
			hmcPage.B2BUnit_Save.click();
			Thread.sleep(2000);

			System.out.println("disablePaymentProfileFallback: No");

			// Step 6 Go to site, add item to cart, checkout and proceed to
			// payment page(need expect) label change to BANK DEPOSIT Auto
			// checkout and proceed to payment page
			addLapTopsToCart(CopyAddress);

			// label changed to BANK DEPOSIT Auto
			// wait until payment continue button displays
			Common.checkElementDisplays(driver, b2cPage.Payment_ContinueButton, 60);
			if (!b2cPage.Payment_bankDepositLabel.getText().contains(profileName)) {
				Assert.fail("Payment message wrong!");
			}

			// Step 7 There is one link "What is bank deposit?" and the before
			// added picture(STEP1) with Bank Deposit Label

			Assert.assertTrue(
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/..//a")),
					"isElementExist bank deposit link");
			Assert.assertTrue(
					Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/..//img")),
					"isElementExist bank deposit img");
			// Click "What is bank deposit?"
			driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/..//a")).click();
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			System.out.println("windows " + windows.size());
			if (windows.size() != 2) {
				Assert.assertFalse(true, "click link , not open a new page");
			}

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
		driver.manage().deleteAllCookies();
		Thread.sleep(10000);
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

		while (true) {
			int count = driver.findElements(By.xpath("//tr[contains(@id,'Direct Deposit')]")).size();
			System.out.println("floor price rule count: " + count);
			
			if (count != 0) {
				if (Common.isElementExist(driver, By.xpath("//tr[contains(@id,'Direct Deposit')]"))) {
					Common.rightClick(driver, hmcPage.paymentProfile_ddResult);
					hmcPage.paymentProfile_remove.click();
					driver.switchTo().alert().accept();
					Thread.sleep(2000);

				}
			}
			if (count == 1 || count == 0)
				break;
		}
		

	}

}
