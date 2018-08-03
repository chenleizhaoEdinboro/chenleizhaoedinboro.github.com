package TestScript.B2C;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
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
import junit.framework.Assert;

public class NA20059Test extends SuperTestClass {

	B2CPage b2cPage = null;
	HMCPage hmcPage = null;
	public MailPage mailPage;
	private String FirstCartPartNo;
	private String loginID;
	private String pwd;
	public String emailCart = "test20059@sharklasers.com";
	private String backProdcutNo = "4X40E77324";

	public NA20059Test(String store) {
		this.Store = store;
		this.testName = "NA-20059";
	}

	public boolean isElementExsit(WebDriver driver, By locator) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(locator);
			flag = null != element;
		} catch (NoSuchElementException e) {
			// System.out.println( "this category has only one page");
		}
		return flag;
	}

	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(date);
		return dateString;
	}

	public void checkInbox(String EmailAccount, String EmailTitle, String partNumber, String partName,
			String productPrice, int count) {
		int i = 1;
		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + EmailTitle + "')]"), 10)) {
			driver.findElement(By.xpath("//*[contains(text(),'" + EmailTitle + "')]")).click();
			;
			CheckEmailDetail(partNumber, partName, productPrice);
		} else if (i < count) {
			count--;
			checkInbox(EmailAccount, EmailTitle, partNumber, partName, productPrice, count);
		} else {
			Dailylog.logInfoDB(10, "No email received", Store, testName);
			Assert.fail("No email received");
		}
	}

	public void CheckEmailDetail(String partNumber, String partName, String productPrice) {

		Assert.assertTrue("part number check",
				Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + partNumber + "')]")));
		Assert.assertTrue("part name check",
				Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + partName + "')]")));
		Assert.assertTrue("part price check",
				Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + productPrice + "')]")));
	};

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

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "e2e", "b2c"})
	public void NA20059(ITestContext ctx) {
		try {
			this.prepareTest();

			Dailylog.logInfoDB(1, "HMC b2c SET", Store, testName);
			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(5000);

			hmcPage.orderType.clear();
			hmcPage.orderType.sendKeys("YLOI");
			hmcPage.fulfillmentType_select.click();
			hmcPage.fulfillmentType_select_email.click();
			hmcPage.businessToEmailAddress.clear();
			hmcPage.businessToEmailAddress.sendKeys(emailCart);

			hmcPage.Common_SaveButton.click();
			Thread.sleep(5000);
			hmcPage.HMC_Logout.click();

			Dailylog.logInfoDB(2, "drop order", Store, testName);
			b2cPage = new B2CPage(driver);

			driver.get(testData.B2C.getloginPageUrl());
			FirstCartPartNo = testData.B2C.getDefaultMTMPN();
			
			Common.sleep(2500);

			B2CCommon.handleGateKeeper(b2cPage, testData);
			loginID = testData.B2C.getLoginID();
			pwd = testData.B2C.getLoginPassword();
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			HandleJSpring(driver, b2cPage, loginID, pwd);

			Common.sleep(2000);
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			Thread.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(FirstCartPartNo);
			b2cPage.Cart_AddButton.click();
			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {

				b2cPage.Cart_QuickOrderTextBox.clear();
				b2cPage.Cart_QuickOrderTextBox.sendKeys(backProdcutNo);
				b2cPage.Cart_AddButton.click();
				FirstCartPartNo = backProdcutNo;
				if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
					NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
					Thread.sleep(5000);

					if (Common.isElementExist(driver, By.xpath("//div[@class='accessoriesCategories']//span"))) {

						b2cPage.B2C_Accessory_BrowseAllCategory.click();
						if (Common.isElementExist(driver, By.xpath("//div[@id='ChargersBatteries']//h3/a"))) {
							driver.findElement(By.xpath("//div[@id='ChargersBatteries']//h3/a")).click();

						} else {
							b2cPage.B2C_Accessory_Charger.click();
						}

					}

					if (Common.isElementExist(driver,
							By.xpath("(.//*[@id='productGrid-target']//div[@class='thumb']//img)"))) {
						b2cPage.B2C_Accessory_Audio.click();
					}
					b2cPage.B2C_Accessory_SubAccessory.click();

					Thread.sleep(2000);

					b2cPage.Add2Cart.click();

					if (Common.isElementExist(driver, By.xpath(".//*[contains(@class,'addToCartPopupButton')]"))) {
						b2cPage.B2C_Accessory_SubAccessory_AddToCartPopup.click();

					}

					if (Common.isElementExist(driver, By.xpath(".//a[contains(@class,'addedToCart')]"))) {
						Thread.sleep(5000);
						Common.javascriptClick(driver,
								driver.findElement(By.xpath(".//a[contains(@class,'addedToCart')]")));

					}

					Thread.sleep(2000);

					if (Common.isElementExist(driver, By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))) {
						FirstCartPartNo = driver.findElement(By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))
								.getText();
					} else {
						FirstCartPartNo = driver.findElement(By.xpath("//p[contains(@class,'partNumber')]/span"))
								.getText();

					}
				}
			}

			Thread.sleep(2000);
			if (Common.isElementExist(driver, By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))) {
				FirstCartPartNo = driver.findElement(By.xpath("(//p[contains(@class,'partNumber')]/span)[3]"))
						.getText();
			} else {
				FirstCartPartNo = driver.findElement(By.xpath("//p[contains(@class,'partNumber')]/span")).getText();

			}

			String productName = b2cPage.Cart_ProductsName.getText();
			String productPrice = b2cPage.Cart_ProductsPrice.getText();
			Dailylog.logInfoDB(2, "Cart_ProductsName : " + productName, Store, testName);
			Dailylog.logInfoDB(2, "Cart_ProductsPrice : " + productPrice, Store, testName);
			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Clicked checkout.", Store, testName);
			b2cPage.Cart_CheckoutButton.click();

			// Fill shipping info
			Actions actions = new Actions(driver);

			if (isElementExsit(driver,
					By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]"))) {
				actions.moveToElement(b2cPage.EditAddress).click().perform();
			}

			B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), loginID);
			Dailylog.logInfoDB(2, "Clicked Shipping_ContinueButton.", Store, testName);
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);

			if (Common.checkElementExists(driver, b2cPage.Payment_bankDepositLabel, 5)) {
				Common.javascriptClick(driver,
						driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));

			} else if (Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_CARD']"))) {
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}

			B2CCommon.fillPaymentAddressInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());

			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			Dailylog.logInfoDB(2, "Clicked Payment_ContinueButton.", Store, testName);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					b2cPage.OrderSummary_AcceptTermsCheckBox);
			try {
				if (Common.isElementExist(driver, By.xpath("//*[@id='repId']"))) {
					// driver.findElement(By.xpath("//*[@id='repId']")).clear();
					driver.findElement(By.xpath("//*[@id='repId']")).sendKeys(testData.B2C.getRepID());
				}
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {
				System.out.println("StaleElementReferenceException");
				if (Common.isElementExist(driver, By.xpath("//*[@id='repId']"))) {
					// driver.findElement(By.xpath("//*[@id='repId']")).clear();
					driver.findElement(By.xpath("//*[@id='repId']")).sendKeys(testData.B2C.getRepID());
				}
			}

			// place order
			Thread.sleep(500);
			B2CCommon.clickPlaceOrder(b2cPage);
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			long orderNumber = Long.parseLong(orderNum.trim());
			long limitMin = Long.parseLong("4289200000");
			long limitMax = Long.parseLong("4334999999");
			Assert.assertTrue("order number is in number range: 4289200000 ~4334999999",
					(orderNumber < limitMax) && (orderNumber > limitMin));

			Dailylog.logInfoDB(2, "B2C Order number is: " + orderNum, Store, testName);

		
			Dailylog.logInfoDB(3, "check B2C email : ", Store, testName);
			mailPage = new MailPage(driver);
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, "test20059");

			Assert.assertTrue("B2C email is not received ",
					checkEmailContent(driver, mailPage, orderNum, orderNum, productName, productPrice));

			

		
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			Dailylog.logInfoDB(5, "rollback", Store, testName); // roll back
			SetupBrowser();
			HMCPage hmcPage = new HMCPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Thread.sleep(5000);

			hmcPage.fulfillmentType_select.click();
			hmcPage.fulfillmentType_select_CRM.click();

			hmcPage.Common_SaveButton.click();
			Thread.sleep(5000);
			hmcPage.HMC_Logout.click();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public static Boolean checkEmailContent(WebDriver driver, MailPage mailPage, String title, String orderNum,
			String productName, String productPrice) {

		Boolean reciverEmail = false;
		Common.sleep(10000);
		for (int i = 1; i <= 10; i++) {

			List<WebElement> emailTile = driver.findElements(By.xpath("//td[contains(text(),'" + title + "')]"));
			int totalEmail = emailTile.size();
			if (totalEmail == 0) {
				System.out.println(
						"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
				Common.sleep(10000);
			} else {
				System.out.println(emailTile.size());
				// Common.javascriptClick(driver, emailTile.get(0));
				emailTile.get(0).click();

				if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'" + orderNum + "')]"))) {
					if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'" + productName + "')]"))) {
						if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'" + productName + "')]"))) {
							reciverEmail = true;
							mailPage.Mail_backToInbox.click();
							break;
						}
						continue;
					}
					continue;

				} else {
					mailPage.Mail_backToInbox.click();
					reciverEmail = false;
					continue;
				}
			}

		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;
	}
}
