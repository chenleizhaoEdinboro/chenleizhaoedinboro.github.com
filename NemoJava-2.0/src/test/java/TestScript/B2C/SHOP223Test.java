package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
import TestScript.SuperTestClass;

public class SHOP223Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String defaultProductNo;
	String approverName;
	String approverEmail;

	public SHOP223Test(String store, String defaultProductNo) {
		this.Store = store;
		this.testName = "SHOPE-223";
		this.defaultProductNo = defaultProductNo;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "uxui", "p2", "b2c" })
	public void SHOP223(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			String user1 = "youngmeng_n";
			String user2 = "shop223@sharklasers.com";
			String repId = "2232232232";
			String emailQuoteTo = "shop2231@sharklasers.com";
			
			String quoteNum = null;
			String quantity = null;
			String singlePrice = null;
			boolean isDaynamic = false;
			String emailTitle = "Your Lenovo quote was created!";
			if (Store.equals("HKZF"))
				emailTitle = "Your Lenovo quote was created!";
			
			// show quote approver for tele users : yes
			showQuoteApprover();
			getApproverDetail(user1);
			createApprover(user1, user2, repId);

			// common user request quote
			loginB2C(false, null, null);
			Dailylog.logInfoDB(1, "Go to B2C site and Login", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			createQuote(false, user2);
			quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			quantity = "1";
			singlePrice = b2cPage.QuoteConfirmation_subPrice.getText().trim();
			Dailylog.logInfoDB(2, "Quote number is: " + quoteNum, Store, testName);
			Dailylog.logInfoDB(2, "price is: " + singlePrice, Store, testName);

			emailTitle = "Your Lenovo quote was created!";
			if (Store.equals("HKZF"))
				emailTitle = "Your Lenovo quote was created!";
			isDaynamic = false;
			verifyEmail(testData.B2C.getLoginID(), emailTitle, quoteNum, quantity, singlePrice, isDaynamic);
			Dailylog.logInfoDB(3, "Go to email address and check the email.", Store, testName);

			// Telesales Login and price override
			loginB2C(true, "customer", testData.B2C.getLoginID());
			Dailylog.logInfoDB(4, "Go to B2C site and Login telesales", Store, testName);

			driver.get(testData.B2C.getTeleSalesUrl() + "/cart");
			createQuote(true, user2);
			quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			quantity = "1";
			singlePrice = b2cPage.QuoteConfirmation_subPrice.getText().trim();
			Dailylog.logInfoDB(5, "Quote number is: " + quoteNum, Store, testName);
			Dailylog.logInfoDB(5, "price is: " + singlePrice, Store, testName);
			b2cPage.ASM_signout.click();
			if (!Store.equals("SG"))
				isDaynamic = true;
			verifyEmail(user2, emailTitle, quoteNum, quantity, singlePrice, isDaynamic);
			Dailylog.logInfoDB(6, "Go to email address and check the email.", Store, testName);

			// Telesales approve quote and email quote
			loginB2C(true, "quote", quoteNum);
			Dailylog.logInfoDB(8, "Go to B2C site and Login telesales", Store, testName);

			b2cPage.ASM_ApproveButton.click();
			b2cPage.ASM_ApproveComment.sendKeys("approve");
			b2cPage.ASM_PopupApprove.click();
			Dailylog.logInfoDB(9, "approved the quote: " + quoteNum, Store, testName);

			Common.waitElementClickable(driver, b2cPage.ASM_emailQuote, 10);	
			Common.scrollAndClick(driver, b2cPage.ASM_emailQuote);
			b2cPage.ASM_emailTo.sendKeys(emailQuoteTo);
			b2cPage.ASM_sendEmail.click();
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@class='manual-email-msg']"), 10));
			Assert.assertEquals(b2cPage.ASM_emailQuoteMsg.getText(), "Email has been sent, please check your email.");
			Dailylog.logInfoDB(10, "emailed the quote: " + quoteNum, Store, testName);
			Common.javascriptClick(driver, b2cPage.ASM_signout);

			if (!Store.equals("SG"))
				isDaynamic = true;
			
			verifyEmail(emailQuoteTo, quoteNum, quoteNum, quantity, singlePrice, isDaynamic);
			Dailylog.logInfoDB(11, "Go to email address and check the email.", Store, testName);

			// Telesales Login and no price override
			loginB2C(true, "customer", testData.B2C.getLoginID());
			Dailylog.logInfoDB(4, "Go to B2C site and Login telesales", Store, testName);

			driver.get(testData.B2C.getTeleSalesUrl() + "/cart");
			createQuote(false, user2);
			quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			quantity = "1";
			singlePrice = b2cPage.QuoteConfirmation_subPrice.getText().trim();
			Dailylog.logInfoDB(13, "Quote number is: " + quoteNum, Store, testName);
			Dailylog.logInfoDB(13, "price is: " + singlePrice, Store, testName);
			b2cPage.ASM_signout.click();

			if (!Store.equals("SG"))
				isDaynamic = true;
			verifyEmail(testData.B2C.getLoginID(), emailTitle, quoteNum, quantity, singlePrice, isDaynamic);
			Dailylog.logInfoDB(14, "Go to email address and check the email.", Store, testName);

			// Telesales email quote
			loginB2C(true, "quote", quoteNum);
			Dailylog.logInfoDB(8, "Go to B2C site and Login telesales", Store, testName);
			Common.waitElementClickable(driver, b2cPage.ASM_emailQuote, 10);
			Common.scrollAndClick(driver, b2cPage.ASM_emailQuote);
			b2cPage.ASM_emailTo.sendKeys(emailQuoteTo);
			b2cPage.ASM_sendEmail.click();
			Assert.assertTrue(Common.checkElementDisplays(driver, By.xpath("//div[@class='manual-email-msg']"), 10));
			Assert.assertEquals(b2cPage.ASM_emailQuoteMsg.getText(), "Email has been sent, please check your email.");
			Dailylog.logInfoDB(15, "emailed the quote: " + quoteNum, Store, testName);
			Common.javascriptClick(driver, b2cPage.ASM_signout);

			if (!Store.equals("SG"))
				isDaynamic = true;
			verifyEmail(emailQuoteTo, quoteNum, quoteNum, quantity, singlePrice, isDaynamic);
			Dailylog.logInfoDB(16, "Go to " + emailQuoteTo + " and check the email.", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void loginB2C(boolean isTele, String category, String text) throws InterruptedException {
		if (isTele) {
			String loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			driver.get(loginUrl);
			if (Common.checkElementDisplays(driver, By.xpath(".//*[@id='asmLogoutForm']/fieldset/button"), 10)) {
				b2cPage.SignoutASM.click();
				Thread.sleep(5000);
				if (Common.isAlertPresent(driver)) {
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}
			}
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			int i = 0;
			while ((driver.getCurrentUrl().contains("j_spring_security")) && (i++ < 3)) {
				driver.get(loginUrl);
				B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			}
			Common.sleep(3000);
			B2CCommon.loginASMAndStartSession(driver, b2cPage, category, text);
		} else {
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			int i = 0;
			while ((driver.getCurrentUrl().contains("j_spring_security")) && (i++ < 3)) {
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			}
			Common.sleep(3000);
		}
	}

	private void createQuote(boolean isOverride, String approver) throws Exception {
		
		B2CCommon.emptyCart(driver, b2cPage);
		try {
			addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
		} catch (Exception e) {
			B2CCommon.addPartNumberToCart(b2cPage, defaultProductNo);
		}
		if (isOverride) {
			// Override
			Thread.sleep(4000);
			float Price = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			Price = Price - 1;
			b2cPage.OverrideValue.sendKeys(Price + "");
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			float Price1 = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			Assert.assertEquals(Price, Price1, 0.001);
		}

		B2CCommon.clickRequestQuote(b2cPage);
		Common.sleep(3000);
		try {
			b2cPage.Quote_RepIDTextBox.clear();
		} catch (InvalidElementStateException e) {
			b2cPage.Quote_RepIDTextBox.clear();
		}
		b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());

		if (isOverride) {
			Select approverSel = new Select(b2cPage.requestQuote_quoteApprover);
			approverSel.selectByValue(approver);
		}

		Common.waitElementClickable(driver, b2cPage.Quote_submitQuoteBtn, 5);
		Common.scrollToElement(driver, b2cPage.Quote_submitQuoteBtn);
		b2cPage.Quote_submitQuoteBtn.click();
		Common.sleep(5000);

	}

	public void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		Common.waitElementClickable(b2cPage.PageDriver, b2cPage.PageDriver.findElement(By.xpath("//*[text()='" + partNum + "']")), 5);
	}

	private void verifyEmail(String emailID, String emailTitle, String quoteNum, String quantity, String singlePrice, boolean isDynamic) {
		EMailCommon.createEmail(driver, mailPage, emailID);
		if (EMailCommon.checkEmailContentinAllEmail(driver, mailPage, emailTitle, "//*[contains(text(),'" + quoteNum + "')]")) {
			if (isDynamic) {
				By approverEmailXpath = By.xpath("//a[@href='mailto:" + approverEmail + "']");
				Assert.assertTrue(Common.checkElementDisplays(driver, approverEmailXpath, 10));
				Assert.assertEquals(driver.findElement(approverEmailXpath).getText(), approverEmail);
				By approverNameXpath = By.xpath("//td[contains(.,'contact your sales representative " + approverName + "')]");
				Assert.assertTrue(Common.checkElementDisplays(driver, approverNameXpath, 10));
			} else {
				String testEmail = Store.substring(0, 2).toLowerCase() + "quote@lenovo.com";
				By approverEmailXpath = By.xpath("//a[@href='mailto:" + testEmail + "']");
				Assert.assertTrue(Common.checkElementDisplays(driver, approverEmailXpath, 10));
				approverEmailXpath = By.xpath("//a[@href='mailto:" + approverEmail + "']");
				Assert.assertFalse(Common.checkElementDisplays(driver, approverEmailXpath, 10));
			}

			String quantityText = "Qty:";
			String singlePriceText = "Price:";
			String subTotalPrice = "Sub total:";
			String totalPrice = "Total:";
			if (Store.equals("HKZF") || Store.equals("TW")) {
				quantityText = "數量:";
				singlePriceText = "價錢:";
				subTotalPrice = "小計:";
				totalPrice = "總計:";
			}

			By quantityX = By.xpath("//td[text()='" + quantityText + "']/../../tr[2]/td");
			By singlePriceX = By.xpath("//td[text()='" + singlePriceText + "']/../../tr[2]/td");
			By subTotalPriceX = By.xpath("//td[text()='" + subTotalPrice + "']/../../../../../td[3]//tr[1]/td");
			By totalPriceX = By.xpath("//td[text()='" + totalPrice + "']/../../../../../td[3]//tr[2]/td");

			Assert.assertTrue(Common.checkElementDisplays(driver, quantityX, 10), quantityText);
			Assert.assertTrue(Common.checkElementDisplays(driver, singlePriceX, 10), singlePriceText);
			Assert.assertTrue(Common.checkElementDisplays(driver, subTotalPriceX, 10), subTotalPrice);
			Assert.assertTrue(Common.checkElementDisplays(driver, totalPriceX, 10), totalPrice);

			List<WebElement> ele = driver.findElements(quantityX);
			Assert.assertEquals(ele.size(), 1, quantityText);
			Assert.assertEquals(ele.get(0).getText().trim(), quantity, quantityText);
			ele = driver.findElements(singlePriceX);
			Assert.assertEquals(ele.size(), 1, singlePriceText);
			Assert.assertEquals(ele.get(0).getText().trim(), singlePrice, singlePriceText);
			ele = driver.findElements(subTotalPriceX);
			Assert.assertEquals(ele.size(), 1, subTotalPrice);
			Assert.assertEquals(ele.get(0).getText().trim(), singlePrice, subTotalPrice);
			ele = driver.findElements(totalPriceX);
			Assert.assertEquals(ele.size(), 1, totalPrice);
			Assert.assertEquals(ele.get(0).getText().trim(), singlePrice, totalPrice);
		}else {
			setManualValidateLog("Need Manual Validate in email " + emailID + ", and check email: " + emailTitle + " , quote number:" + quoteNum);
		}
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "").replaceAll("HK", "").replaceAll("SG", "").replace("£", "").replace("€", "").replaceAll("￥", "").replaceAll("NT", "").replaceAll("₩", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	private void showQuoteApprover() {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);

		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(12000);

		if (!driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zTeleUserByGeo') and contains(@id,'true')]")).isSelected()) {
			driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zTeleUserByGeo') and contains(@id,'true')]")).click();
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
		}
		Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zTeleUserByGeo') and contains(@id,'true')]")).isSelected());
		hmcPage.Home_EndSessionLink.click();
	}

	private void createApprover(String user1, String user2, String repId) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.home_userTab.click();
		hmcPage.userTab_customerTab.click();
		hmcPage.customer_customerIDTextBox.clear();
		hmcPage.customer_customerIDTextBox.sendKeys(user2);
		hmcPage.customer_customerSearchButtonn.click();
		if (!Common.checkElementDisplays(driver, By.xpath(".//div[@class='olecIcon']//img"), 15)) {
			hmcPage.customer_customerIDTextBox.clear();
			hmcPage.customer_customerIDTextBox.sendKeys(user1);
			hmcPage.customer_customerSearchButtonn.click();
			Common.rightClick(driver, hmcPage.customer_searchedResultImage);
			hmcPage.result_clone.click();
			hmcPage.customers_uid.clear();
			hmcPage.customers_uid.sendKeys(user2);
			hmcPage.customers_name.clear();
			hmcPage.customers_name.sendKeys(user2);
		} else
			hmcPage.customer_searchedResultImage.click();
		hmcPage.customerSearch_administrationTab.click();
		if (!hmcPage.customers_repId.getAttribute("value").equals(repId)) {
			hmcPage.customers_repId.clear();
			hmcPage.customers_repId.sendKeys(repId);
		}
		if (!hmcPage.customers_zTeleUserEmail.getAttribute("value").equals(user2)) {
			hmcPage.customers_zTeleUserEmail.clear();
			hmcPage.customers_zTeleUserEmail.sendKeys(user2);
		}
		hmcPage.customers_saveAndCreate.click();
		hmcPage.Home_EndSessionLink.click();
	}

	private void getApproverDetail(String user1) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.home_userTab.click();
		hmcPage.userTab_customerTab.click();
		hmcPage.customer_customerIDTextBox.clear();
		hmcPage.customer_customerIDTextBox.sendKeys(user1);
		hmcPage.customer_customerSearchButtonn.click();
		hmcPage.customer_searchedResultImage.click();
		approverName = hmcPage.customers_name.getAttribute("value");
		hmcPage.customerSearch_administrationTab.click();
		approverEmail = hmcPage.customers_zTeleUserEmail.getAttribute("value");
		hmcPage.Home_EndSessionLink.click();
	}

}
