package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class COMM52Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public MailPage mailPage;
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
	String ProductNo;
	String Rep_Value = "2900718028";
	String QuoteStatus;
	String QuotePartNumber;
	String QuoteId;
	String QuoteQuantity;
	String QuoteTotalPrice;
	String Unit_One;
	String Unit_Two;

	public COMM52Test(String store) {
		this.Store = store;
		this.testName = "COMM-52";
	}

	public void closePromotion(WebDriver driver, B2BPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2BPage b2bPage, String loginID, String pwd) {

		if (driver.getCurrentUrl().contains("security")) {

			driver.get(testData.B2B.getLoginUrl());
			closePromotion(driver, b2bPage);
			// if (Common.isElementExist(driver,
			// By.xpath(".//*[@id='smb-login-button']"))) {
			// b2bPage.SMB_LoginButton.click();
			// }
			B2BCommon.Login(b2bPage, loginID, pwd);

		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2b" })
	public void COMM52(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			// Step~1 : Go to HMC and enable request quote
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Logged in into HMC", Store, testName);
			Common.sleep(5000);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Common.sleep(4500);

			hmcPage.B2BUnit_siteAttribute.click();

			hmcPage.enableB2BQuoteApprovedEmail.click();
			hmcPage.enableB2BEmailQuote.click();
			hmcPage.enableB2BQuoteSendEmail.click();
			hmcPage.enableQuoteApproval.click();
			hmcPage.enableQuoteConvert.click();
			hmcPage.enableB2BQuoteReviewerRemindSendEmail.click();
			hmcPage.enableB2BQuoteReviewerReassignedRemindSendEmail.click();

			hmcPage.B2BUnit_isQuoteAvailable.click();
			hmcPage.addressFromCRM.click();

			hmcPage.B2BUnit_Save.click();
			hmcPage.HMC_Logout.click();
			// step~2
			driver.get(testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver, b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			// step~3
			Common.sleep(3000);
			b2bPage.HomePage_CartIcon.click();
			Common.sleep(1500);
			if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
				driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
			}
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			Common.sleep(2000);
			b2bPage.cartPage_LenovoCheckout.click();

			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			
			driver.findElement(By.xpath(".//*[@id='email']")).clear();
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("testcomm52@sharklasers.com");
			
			
			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}

			b2bPage.paymentPage_companyName.clear();
			b2bPage.paymentPage_companyName.sendKeys("TESTCOMM52");
			RequestQuote();
			String FirstQuoteId = QuoteId;
			Dailylog.logInfoDB(5, "First Quote Id : " + FirstQuoteId, Store, testName);

			mailPage = new MailPage(driver);
			EMailCommon.goToMailHomepage(driver);
		
				
			EMailCommon.createEmail(driver, mailPage, "testcomm52");
			
			

			checkEmailContent(driver, mailPage, FirstQuoteId, testData.B2B.getCompany(), "TESTCOMM52");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public static Boolean checkEmailContent(WebDriver driver, MailPage mailPage, String title, String billingCompany,
			String shippingCompany) {

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

				WebElement EmailSubTotalPrice = driver
						.findElement(By.xpath("//td[contains(text(),'Billing')]/../../../../../td[3]//tbody/tr[2]/td"));
				Assert.assertTrue(EmailSubTotalPrice.getText().contains(billingCompany),
						"billing company name is not correct");

				WebElement shippingName = driver.findElement(
						By.xpath("//td[contains(text(),'Shipping')]/../../../..//tbody/tr[2]/td"));
				Assert.assertTrue(shippingName.getText().contains(shippingCompany),
						"shipping company name is not correct");

			}

		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;
	}

	// validate options of search criteria and Column list
	private void Validate_SearchCriteriaText(String text) {
		Assert.assertTrue(
				driver.findElement(By.xpath(".//*[@id='searchCriteria']/option[text()='" + text + "']")).isDisplayed(),
				text + " is not present in search criteria");
	}

	// +=+=+=+=++=+=+=+=+=+=Request Quote Method+=+=+=+=+=+=+=+=+=+=//
	private void RequestQuote() {
		Assert.assertTrue(b2bPage.cartPage_RequestQuoteBtn.isDisplayed(), "Request Quote button is not present.");
		b2bPage.cartPage_RequestQuoteBtn.click();
		Dailylog.logInfoDB(4, "Clicked on Request Quote button", Store, testName);
		Common.sleep(3000);
		Common.sendFieldValue(b2bPage.cartPage_RepIDBox, Rep_Value);
		b2bPage.cartPage_SubmitQuote.click();
		Common.sleep(2000);
		QuoteStatus = b2bPage.QuotePage_QuoteStatus.getText();
		QuotePartNumber = b2bPage.QuotePage_PartNo.getText();
		QuoteId = b2bPage.QuotePage_QuoteNo.getText();
		QuoteQuantity = b2bPage.QuotePage_Quantity.getText();
		QuoteTotalPrice = b2bPage.QuotePage_LinePrice.getText();
		Dailylog.logInfoDB(4, "Captured information from Quote page", Store, testName);
	}

}
