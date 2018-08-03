package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA17639Test extends SuperTestClass {
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private MailPage mailPage;
	private String enableEmailCartOnASM = "//div/input[contains(@id,'enableEmailCartOnASM')][@value='";
	private String cartName = Common.getDateTimeString();
	private String SavedCartDetails_EmailCartLink = "//div/a[contains(@href,'EmailCart')]";
	private String customiseButton = "(//ol[contains(@class,'product')]/li/div//div[contains(@class,'footer')]/button[contains(@id,'Cart')])[1]";

	public NA17639Test(String store) {
		this.Store = store;
		this.testName = "NA-17639";

	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17639(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			String enableEmailCartOnASM_Yes = "true";
			String enableEmailCartOnASM_No = "false";
			String SavedCart_EmailSuccessMesg = ".//*[@id='bodywrapinner']/div/p[2]";
			String hmcLoginURL = testData.HMC.getHomePageUrl();

			// Creating Emails
			EMailCommon.createEmail(driver, mailPage, cartName);
			EMailCommon.createEmail(driver, mailPage, cartName + "1");
			((JavascriptExecutor) driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);

			// ==================== Step:1,2,3,4 ======================//
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);
			Common.sleep(3000);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Dailylog.logInfoDB(2, "Clicked search results", Store, testName);
			Common.sleep(2000);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			Thread.sleep(15000);
			Dailylog.logInfoDB(3, "Clicked Site Attribute tab.", Store,
					testName);
			emailCartSetting_HMC(enableEmailCartOnASM_No);
			Dailylog.logInfoDB(4, "HMC changes are saved.", Store, testName);
			((JavascriptExecutor) driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 2);

			// ===================== Step:5,6,7 ======================//
			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			saveACart();
			checkEmailCartOnASM(false, 7);

			// ==================== Step:8,9 ======================//
			Common.switchToWindow(driver, 1);
			emailCartSetting_HMC(enableEmailCartOnASM_Yes);
			Common.sleep(5000);

			// ===================== Step:10,11 ======================//
			Common.switchToWindow(driver, 2);
			checkEmailCartOnASM(true, 10);

			// ===================== Step:12 ======================//
			driver.findElement(By.xpath(SavedCartDetails_EmailCartLink))
					.click();
			Dailylog.logInfoDB(12, "Email Cart link is clicked", Store,
					testName);

			// ===================== Step:13 ======================//
			
			Common.switchToWindow(driver, 3);
			Assert.assertTrue(b2cPage.EmailCart_SenderName.isDisplayed(),
					"Sender Name Txt box should be displayed");
			Assert.assertTrue(b2cPage.EmailCart_SenderEmail.isDisplayed(),
					"Sender Email Txt box should be displayed");
			b2cPage.EmailCart_SenderName.sendKeys("young");
			b2cPage.EmailCart_SenderEmail.clear();
			b2cPage.EmailCart_SenderEmail.sendKeys(cartName
					+ "@sharklasers.com");
			String txtInNameTxtBox = b2cPage.EmailCart_SenderName
					.getAttribute("value");
			String txtInEmailTxtBox = b2cPage.EmailCart_SenderEmail
					.getAttribute("value");
			Assert.assertEquals(txtInNameTxtBox, "young",
					"Txt should be same in first Name txt box");
			Common.sleep(2000);
			b2cPage.EmailCart_ClearButton.click();
			Dailylog.logInfoDB(13, "Clear button is clicked", Store, testName);
			String txtInNameTxtBoxAfterClear = b2cPage.EmailCart_SenderName
					.getAttribute("value");
			String txtInEmailTxtBoxAfterClear = b2cPage.EmailCart_SenderEmail
					.getAttribute("value");
			Assert.assertEquals(txtInNameTxtBoxAfterClear, "",
					"Txt should be same in first Name txt box after Clearing the txt box");
			Assert.assertEquals(txtInEmailTxtBoxAfterClear, txtInEmailTxtBox,
					"Txt should be same in Email txt box after Clearing the txt box");

			// ===================== Step:14 ======================//
			b2cPage.EmailCart_SenderName.sendKeys("young");
			b2cPage.EmailCart_SenderEmail.clear();
			b2cPage.EmailCart_SenderEmail.sendKeys(cartName + "@sharklasers.com");
			b2cPage.EmailCart_CheckBox.click();
			b2cPage.EmailCart_SendButton.click();
			Common.sleep(2000);
			Assert.assertEquals(
					driver.findElement(By.xpath(SavedCart_EmailSuccessMesg))
							.isDisplayed(), true,
					"Email Cart success message should be displayed for step 14.");
			Dailylog.logInfoDB(14,
					"Send button is clicked on email cart page.Email address is " + cartName, Store,
					testName);
			Common.switchToWindow(driver, 0);

			// ===================== Step:15 & 16 ======================//
			
			EMailCommon.createEmail(driver, mailPage, cartName);
			Assert.assertTrue(EMailCommon.checkIfEmailReceived(driver, mailPage, "young"));
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void emailCartSetting_HMC(String enableEmailCartOnASM) {
		driver.findElement(
				By.xpath(this.enableEmailCartOnASM + enableEmailCartOnASM
						+ "']")).click();
		hmcPage.HMC_Save.click();
		Common.sleep(8000);
		/*
		 * hmcPage.Home_EndSessionLink.click(); Common.sleep(2000);
		 */
	}

	private void saveACart() throws InterruptedException {
		String b2cLoginURL = testData.B2C.getloginPageUrl();
		driver.manage().deleteAllCookies();
		// Accessing b2c website and login
		driver.get(b2cLoginURL);
		Common.sleep(3000);
		B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
				testData.B2C.getTelesalesPassword());
		B2CCommon.handleGateKeeper(b2cPage, testData);
		if(!driver.getCurrentUrl().contains("account")) {
			driver.get(testData.B2C.getloginPageUrl());
			Common.sleep(2500);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
		}
		Dailylog.logInfoDB(
				5,
				"Successfully Logged into B2C website with TeleSales Credentials.",
				Store, testName);
		Common.sleep(3000);
		b2cPage.StartASM.click();
		Dailylog.logInfoDB(5, "Clicked on Start ASM.", Store, testName);
		Common.sleep(1500);

		// Search Customer on ASM page
		b2cPage.ASM_SearchCustomer.sendKeys("young meng");
		b2cPage.ASM_CustomerResult.click();
		Common.sleep(1500);
		// Start ASM Session
		b2cPage.ASM_StartSession.click();
		Common.sleep(3000);
		b2cPage.HomePage_CartIcon.click();
		B2CCommon.clearTheCart(driver, b2cPage, testData);
		// Add a product with quick order
		// B2CCommon.addPartNumberToCart(b2cPage, "20FR000QAU");
		b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
		b2cPage.Cart_AddButton.click();
		// If Product coming from DB is out of stock
		if (Common.isElementExist(
				driver,
				By.xpath("//div/div[@class='alert negative'][contains(.,'"
						+ testData.B2C.getDefaultMTMPN() + "')]"))) {
			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
					SplitterPage.Laptops);
			Common.sleep(3000);
			String splitterPageURL = driver.getCurrentUrl();
			B2CCommon.addToCartB2C(1, driver, splitterPageURL, customiseButton);
			Common.sleep(3000);
			Dailylog.logInfoDB(6, "Product is added to cart", Store, testName);
		} else {
			Dailylog.logInfoDB(6, "Product is added to cart", Store, testName);
		}
		Common.sleep(3000);
		// Save the cart
		((JavascriptExecutor) driver).executeScript("scroll(0,200)");
		b2cPage.Cart_saveCart.click();
		Common.sleep(500);
		b2cPage.Cart_nameTextBox.sendKeys(cartName);
		Common.sleep(500);
		b2cPage.Cart_saveCartBtn.click();
		Dailylog.logInfoDB(6, "Cart is saved", Store, testName);
		Common.sleep(2500);
	}

	private void checkEmailCartOnASM(boolean emailCartLinkStatus, int step) {

		// Click My Account Icon and view saved carts
		
		if (!driver.getCurrentUrl().contains("save-carts")) {
			try {
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.saveCart_MyAccount);
		//b2cPage.Navigation_MyAccountIcon.click();
		
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b2cPage.MyAccount_ViewSavedCartHistory.click();
		}
	Common.javascriptClick(driver, driver.findElement(
				By.xpath("//td[contains(.,'" + cartName
						+ "')]/../td/a/div[contains(@class,'linktext')]")));
		/*driver.findElement(
				By.xpath("//td[contains(.,'" + cartName
						+ "')]/../td/a/div[contains(.,'View')]")).click();*/

		Common.sleep(4000);
		Dailylog.logInfoDB(step, "View Cart is clicked.", Store, testName);
		// System.out.println(Common.isElementExist(driver,
		// By.xpath(SavedCartDetails_EmailCartLink))+" "+emailCartLinkStatus);
		System.out.println(emailCartLinkStatus);
		Assert.assertEquals(
				Common.isElementExist(driver,
						By.xpath(SavedCartDetails_EmailCartLink)),
				emailCartLinkStatus, "Email Cart Link status should be: "
						+ emailCartLinkStatus);

		Common.sleep(2000);
	}

}
