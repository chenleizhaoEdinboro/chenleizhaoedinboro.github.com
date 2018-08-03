package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA18406Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	private String quoteId = "";
	private String email = "NA18406@sharklasers.com";
	private String errorMsg = "Eメールアドレスを入力してください。";
	private String quantity = "2";
	private String quoteEmail = "お見積りありがと うございました";
	private String orderEmail = "﻿ご注文ありがと うございました";

	public NA18406Test(String store) {
		this.Store = store;
		this.testName = "NA-18406";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c", "compatibility"})
	public void NA18406(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			String testProudct = testData.B2C.getDefaultMTMPN();
			
			// Step1 B2C Commerce--> B2CUnit-> set EnabaleGuestQuote as yes.
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
//			driver.navigate().refresh();
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_enableGuestQuoteYes.click();
			hmcPage.B2BUnit_Save.click();
			Dailylog.logInfoDB(1, "Set EnabaleGuestQuote to yes", Store,
					testName);

			// step1 go to base store jpweb -> set isQuoteAvailable as yes.
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			Common.doubleClick(
					driver,
					driver.findElement(By.xpath(".//div[contains(@id,'"
							+ testData.B2C.getStore() + "]')]")));
			Common.doubleClick(driver, hmcPage.baseStore_administration);
			hmcPage.baseStore_isQuoteAvailable.click();
			hmcPage.baseStore_save.click();

			Dailylog.logInfoDB(1, "Set isQuoteAvailable to yes", Store,
					testName);
			
			// step 2 guest quick order DefaultMTMPartNumber
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			else
				Assert.fail("RegistrationGateKeeper is turned on! Guest cannot quick order!");

			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.addPartNumberToCart(b2cPage, testProudct);
			Dailylog.logInfoDB(2, "guest quick order " + testProudct, Store, testName);

			// step 3 click on "request quote" button.
			((JavascriptExecutor) driver).executeScript("scroll(0,100)");
			new WebDriverWait(driver, PropsUtils.getDefaultTimeout())
					.until(ExpectedConditions
							.visibilityOf(b2cPage.Quote_requestBtn));

			if (!b2cPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com")) {
				Common.javascriptClick(driver, b2cPage.Quote_requestBtn);
			}

			Thread.sleep(3000);
			Dailylog.logInfoDB(3, "click on request quote button.", Store,
					testName);

			// step 4 click on save a one time quote.
			Common.waitElementVisible(driver, b2cPage.Quote_createOneTimeQuote);
			b2cPage.Quote_createOneTimeQuote.click();
			Dailylog.logInfoDB(4, "click on create one time quote button.",
					Store, testName);

			// step 5 do not input contact email, click on "yes".
			Common.sleep(5000);
			Common.waitElementVisible(driver, b2cPage.Quote_submitQuoteBtn);
			b2cPage.Quote_submitQuoteBtn.click();
			Assert.assertTrue(
					driver.findElement(
							By.xpath("//*[contains(text(),'" + errorMsg + "')]"))
							.isDisplayed(),
					"Eメールアドレスを入力してください。 is not displayed");
			Dailylog.logInfoDB(5,
					"click on submit quote button without email.", Store,
					testName);

			// step 6 input a valid email address, click on yes.
			b2cPage.Quote_contactEmail.sendKeys(email);
			b2cPage.Quote_submitQuoteBtn.click();
			quoteId = b2cPage.Quote_quoteNum.getText();
			Dailylog.logInfoDB(6, "click on submit quote button with email: "
					+ email, Store, testName);
			Dailylog.logInfoDB(6, "quoteId: " + quoteId, Store, testName);
			
			// step 7 logon with the tele sales account
			Common.NavigateToUrl(driver, Browser, testData.B2C.getTeleSalesUrl() + "/login");
			Common.sleep(3000);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			Dailylog.logInfoDB(7, "logon with the tele sales account", Store,
					testName);

			// step 8 Search the quote ID.
			Thread.sleep(500);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getTeleSalesUrl() + "/my-account");

			Thread.sleep(500);
			b2cPage.MyAccount_Telesales.click();
			b2cPage.Tele_TransactionSearch.click();
			Thread.sleep(20000);

			b2cPage.Tele_TransactionSearch_TransactionId.sendKeys(quoteId);
			b2cPage.Tele_TransactionSearch_Search.click();
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Tele_TransactionSearch_SearchResult);
			Dailylog.logInfoDB(8, "Searched the quote ID. " + quoteId, Store,
					testName);

			// step 9 start session.
			Common.waitElementClickable(driver, b2cPage.Tele_StartSession, 5);
			Thread.sleep(5000);
			b2cPage.Tele_StartSession.click();
			Dailylog.logInfoDB(9, "Clicked start session", Store, testName);

			// step 10 Click Edit, Change Product to 2 and Save
			Common.waitElementVisible(driver, b2cPage.Quote_editButton);
			// ((JavascriptExecutor) driver).executeScript("scroll(0,100)");
			Common.javascriptClick(driver, b2cPage.Quote_editButton);
			// b2cPage.Quote_editButton.click();
			Actions act = new Actions(driver);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			Common.waitElementVisible(driver, b2cPage.Quote_quantity0);
			b2cPage.Quote_quantity0.clear();
			b2cPage.Quote_quantity0.sendKeys(quantity);
			Common.javascriptClick(driver, b2cPage.Quote_update);

			((JavascriptExecutor) driver).executeScript("scroll(0,500)");
			// B2CCommon.clickRequestQuote(b2cPage);
			if (!b2cPage.PageDriver.getCurrentUrl().contains("www3.lenovo.com")) {
				Common.javascriptClick(driver, b2cPage.Quote_requestBtn);
				// b2cPage.Quote_requestBtn.click();
			}
			Common.waitElementClickable(driver, b2cPage.Quote_submitQuoteBtn, 5);
			b2cPage.Quote_submitQuoteBtn.click();
			Dailylog.logInfoDB(10, "Changed product to 2", Store, testName);

			// step 11 Click End Session then repeat step 8-9????
			Common.waitElementClickable(driver, b2cPage.ASM_endSession, 5);
			b2cPage.ASM_endSession.click();
			Common.waitAlertPresent(driver, 5);
			driver.switchTo().alert().accept();
			Dailylog.logInfoDB(11, "Ended session", Store, testName);

			Thread.sleep(5000);
			b2cPage.Tele_TransactionSearch.click();
			Thread.sleep(20000);

			b2cPage.Tele_TransactionSearch_TransactionId.sendKeys(quoteId);
			b2cPage.Tele_TransactionSearch_Search.click();
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Tele_TransactionSearch_SearchResult);
			Dailylog.logInfoDB(11, "Searched the quote ID. " + quoteId, Store,
					testName);

			Common.waitElementClickable(driver, b2cPage.Tele_StartSession, 5);
			Thread.sleep(5000);
			b2cPage.Tele_StartSession.click();
			Dailylog.logInfoDB(11, "Clicked start session", Store, testName);

			Thread.sleep(5000);
			Assert.assertEquals(b2cPage.Quote_quantity3.getAttribute("value"),
					quantity, "Product number in quote should be updated.");

			// step 12 Click convert quote to order.
			Common.waitElementClickable(driver, b2cPage.Quote_convertOrder, 5);
			Thread.sleep(5000);

			// ((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			Common.javascriptClick(driver, b2cPage.Quote_convertOrder);
			// b2cPage.Quote_convertOrder.click();

			Dailylog.logInfoDB(11, "Clicked convert quote to order", Store,
					testName);

			// step 12 Continue to place order, Reseller ID:2900718028
			Thread.sleep(5000);
			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName",
					"AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(),
					testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(),
					testData.B2C.getDefaultAddressPhone(), email);
			Thread.sleep(3000);
			Dailylog.logInfoDB(12, "Filled in shipping info", Store, testName);

			// continue
			WebElement continueButton = driver.findElement(By
					.id("checkoutForm-shippingContinueButton"));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", continueButton);

			// validate info
			if (Common.isElementExist(driver, By.id("address_sel"))) {
				Select dropdown = new Select(driver.findElement(By
						.id("address_sel")));
				dropdown.selectByIndex(1);
				b2cPage.Shipping_AddressMatchOKButton.click();
			} else if (Common
					.isElementExist(
							driver,
							By.xpath("//li[@class='list-group-item']/input[@type='radio']"))) {
				if (driver
						.findElement(
								By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]"))
						.isDisplayed()) {
					driver.findElement(
							By.xpath("(//li[@class='list-group-item']/input[@type='radio'])[1]"))
							.click();
				}
				b2cPage.Shipping_AddressMatchOKButton.click();
			}

			// fill payment info
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Dailylog.logInfoDB(12, "Filled in payment info", Store, testName);

			// continue
			Thread.sleep(5000);
			executor.executeScript("arguments[0].click();",
					b2cPage.Payment_ContinueButton);

			// repid
			try {
				if (Common.checkElementExists(driver,
						b2cPage.OrderSummary_editableRepID, 5)) {
					Thread.sleep(4000);
					// b2cPage.OrderSummary_editableRepID.clear();

					if (Common
							.isElementExist(
									driver,
									By.xpath("//input[@id='repId' and not(@readonly)]"))) {

						b2cPage.OrderSummary_editableRepID
								.sendKeys(testData.B2C.getRepID());
					}

				}
			} catch (InvalidElementStateException ex) {
				System.out.println("InvalidElementStateException");
				if (Common.checkElementExists(driver,
						b2cPage.OrderSummary_editableRepID, 5)) {
					Thread.sleep(4000);
					// b2cPage.OrderSummary_editableRepID.clear();
					b2cPage.OrderSummary_editableRepID.sendKeys(testData.B2C
							.getRepID());
				}
			} catch (StaleElementReferenceException ex) {
				System.out.println("StaleElementReferenceException");
				if (Common.checkElementExists(driver,
						b2cPage.OrderSummary_editableRepID, 5)) {
					Thread.sleep(4000);
					// b2cPage.OrderSummary_editableRepID.clear();
					b2cPage.OrderSummary_editableRepID.sendKeys(testData.B2C
							.getRepID());
				}
			}

			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			Thread.sleep(3000);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			//b2cPage.OrderSummary_AcceptTermsCheckBox.click();

			// place order
			Thread.sleep(500);
			B2CCommon.clickPlaceOrder(b2cPage);
			String orderNum =B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			//String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			Dailylog.logInfoDB(12, "Placed oder: " + orderNum, Store, testName);
			
			
			
			
			EMailCommon.createEmail(driver, mailPage, email);
			EMailCommon.checkEmailContent(driver, mailPage, orderNum,"//*[contains(text(),'"+orderNum+"')]");
			// check quote email
			if (!checkEmail(quoteEmail, email, quoteId, 0)) {
				Dailylog.logInfoDB(12, "Email " + quoteEmail
						+ " is not received in " + email + " Quote number is "
						+ quoteId, Store, testName);
				setManualValidateLog("Please Manual Validate in email " + email
						+ ", and check email: " + quoteEmail
						+ " Quote number is: " + quoteId);
			}
//			Boolean flag1 = false;
//			
//			flag1 = EMailCommon.checkEmailContent(driver, mailPage, "お見積","//*[contains(text(),'"+quoteId+"')]");
//			
//			if(flag1==false){
//				setManualValidateLog("Need Manual Validate in email "+testData.B2C.getLoginID() +", and check email: "+ "Gracias por tu orden "+quoteId);		
//			}
			// check order email
			if (!checkEmail(orderEmail, email, orderNum, 0)) {
				Dailylog.logInfoDB(12, "Email " + orderEmail
						+ " is not received in " + email + " Order number is "
						+ orderNum, Store, testName);
				setManualValidateLog("Please Manual Validate in email " + email
						+ ", and check email: " + orderEmail
						+ " Order number is: " + orderNum);
			}
//			Boolean flag = false;
//			
//			flag = EMailCommon.checkEmailContent(driver, mailPage, "注文","//*[contains(text(),'"+orderNum+"')]");
//			
//			if(flag==false){
//				setManualValidateLog("Need Manual Validate in email "+ testData.B2C.getLoginID() +", and check email: "+ "Gracias por tu orden "+orderNum);		
//			}
			// delete all emails
			// deleteAllEmail();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public Boolean checkEmail(String emailSubject, String email,
			String orderNum, int flag) throws InterruptedException {
		Boolean reciverEmail = true;
		if (flag <= 1) {
			List<WebElement> emailTile = driver.findElements(By
					.xpath("//td[contains(text(),'" + emailSubject + "')]"));
			int totalEmail = emailTile.size();
			if (totalEmail == 0) {
				reciverEmail = false;
			}

			else {
				System.out.println(emailTile.size());
				// Common.javascriptClick(driver, emailTile.get(0));
				emailTile.get(0).click();

				if (Common.isElementExist(driver,
						By.xpath("//td[contains(text(),'" + orderNum + "')]"))) {
					reciverEmail = true;
					mailPage.Mail_backToInbox.click();
					Dailylog.logInfoDB(12, emailSubject + " is received "
							+ orderNum, Store, testName);

				} else {
					mailPage.Mail_backToInbox.click();
					reciverEmail = false;
				}
			}

		}
		if (!reciverEmail) {
			Thread.sleep(60000);
			Dailylog.logInfoDB(12, "Waited for 1 minute, check again!", Store,
					testName);
			checkEmail(emailSubject, email, orderNum, ++flag);
		}
		return reciverEmail;
	}

	// public void deleteAllEmail() {
	// // delete all emails
	// List<WebElement> emails =
	// driver.findElements(By.xpath("//input[@type='checkbox' and not(@id)]"));
	// for (int i = 0; i < emails.size(); i++) {
	// emails.get(i).click();
	// }
	// driver.findElement(By.id("del_button")).click();
	// }
}
