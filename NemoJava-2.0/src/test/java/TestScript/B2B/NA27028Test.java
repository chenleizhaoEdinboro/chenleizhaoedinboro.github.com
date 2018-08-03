package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA27028Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public MailPage mailPage;
	String ProductNo;
	String Rep_Value = "2900718028";
	String QuoteStatus;
	String QuotePartNumber;
	String QuoteId;
	String QuoteQuantity;
	String QuoteTotalPrice;
	String Unit_One;
	String Unit_Two;
	private static List<String> shippingAddress =  new ArrayList<String>();

	public NA27028Test(String store) {
		this.Store = store;
		this.testName = "NA-27028";
	}

	public static Boolean checkEmailContent(WebDriver driver, MailPage mailPage, String title, String quoteId) {

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
				//
				if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'" + quoteId + "')]"))) {
					if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'Billing Address')]"))) {
						if (Common.isElementExist(driver,
								By.xpath("//td[contains(text(),'Shipping Address')]"))) {

							for (int j = 0; j < shippingAddress.size(); j++) {
								Assert.assertTrue(
										Common.isElementExist(driver,
												By.xpath("//td[contains(text(),'" + shippingAddress.get(j) + "')]")),
										shippingAddress.get(j) + " is not correct");
							}
							reciverEmail = true;
							mailPage.Mail_backToInbox.click();
							break;
						} else {
							Assert.fail("received email, but no shipping address");
						}
					} else {
						Assert.fail("received email, but no billing address");
					}

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

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA27028(ITestContext ctx) {
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
			hmcPage.baseStore_administration.click();
			hmcPage.B2BAdministration_isQuoteAvailable.click();
			hmcPage.B2BUnit_Save.click();
			Common.sleep(2500);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.B2BUnit_isQuoteAvailable.click();
			hmcPage.B2BUnit_Save.click();
			hmcPage.HMC_Logout.click();
			// step~2
			driver.get(testData.B2B.getLoginUrl());
			Unit_One = testData.B2B.getB2BUnit();
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());

			// step~3
			AddToCartB2B(b2bPage.Laptop_SelectedLaptop2, b2bPage.Laptop_ViewDetail2, 6);
			Common.sleep(2000);
			String SecondCartPartNo = b2bPage.CartPage_OnlyPartnum.getText();
			Dailylog.logInfoDB(3, "part no of Second product on cart page : " + SecondCartPartNo, Store, testName);
			// step~4
			b2bPage.lenovoCheckout.click();
			Common.sleep(4000);
			Assert.assertTrue(driver.getTitle().contains("Shipping"));
			// step~5
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, testData.B2B.getFirstName(),
					testData.B2B.getLastName(), testData.B2B.getCompany(), testData.B2B.getAddressLine1(),
					testData.B2B.getAddressCity(), testData.B2B.getAddressState(), testData.B2B.getPostCode(),
					testData.B2B.getPhoneNum());
			driver.findElement(By.xpath(".//*[@id='email']")).clear();

			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys("test27028@sharklasers.com");

			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			// b2bPage.shippingPage_ContinueToPayment.click();
			if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			Common.sleep(4000);
			Assert.assertTrue(driver.getTitle().contains("Payment"));
			// step~6 : select payment method
			Actions actions = new Actions(driver);

			//
			actions.moveToElement(b2bPage.paymentPage_PurchaseOrder).click().perform();
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.click();
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);

			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys(testData.B2B.getFirstName());
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys(testData.B2B.getLastName());
			Common.sleep(2000);
			if (b2bPage.paymentPage_addressLine1.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_addressLine1.clear();
				b2bPage.paymentPage_addressLine1.sendKeys(testData.B2B.getAddressLine1());
				System.out.println("Input address!");
			}

			if (b2bPage.paymentPage_cityOrSuburb.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_cityOrSuburb.clear();
				b2bPage.paymentPage_cityOrSuburb.sendKeys(testData.B2B.getAddressCity());
			}
			if (b2bPage.paymentPage_addressState.getAttribute("editable").contains("true")) {
				Select stateDropdown = new Select(b2bPage.paymentPage_addressState);
				stateDropdown.selectByVisibleText(testData.B2B.getAddressState());
			}

			if (b2bPage.paymentPage_addressPostcode.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_addressPostcode.clear();
				b2bPage.paymentPage_addressPostcode.sendKeys(testData.B2B.getPostCode());
			}

			if (b2bPage.paymentPage_Phone.getAttribute("editable").contains("true")) {
				b2bPage.paymentPage_Phone.clear();
				b2bPage.paymentPage_Phone.sendKeys(testData.B2B.getPhoneNum());
			}

			RequestQuote();
			// storing value from Quote page
			String SecondQuoteStatus = QuoteStatus;
			Dailylog.logInfoDB(9, "Second Quote Status : " + SecondQuoteStatus, Store, testName);
			Assert.assertTrue(SecondQuoteStatus.contains("SAVED"));

			String SecondQuotePartNumber = QuotePartNumber;
			Dailylog.logInfoDB(9, "Second Quote PartNumber : " + SecondQuotePartNumber, Store, testName);
			Assert.assertTrue(SecondQuotePartNumber.equals(SecondCartPartNo));

			String SecondQuoteId = QuoteId;
			Dailylog.logInfoDB(9, "Second Quote Id : " + SecondQuoteId, Store, testName);

			String SecondQuoteQuantity = QuoteQuantity;
			Dailylog.logInfoDB(9, "Second Quote Quantity : " + SecondQuoteQuantity, Store, testName);

			String SecondQuoteTotalPrice = QuoteTotalPrice;
			Dailylog.logInfoDB(9, "Second Quote TotalPrice : " + SecondQuoteTotalPrice, Store, testName);

			// GOTO my account page remember the address
			driver.get(testData.B2B.getHomePageUrl() + "/my-account");
			b2bPage.myAccountPage_ViewQuotehistory.click();
			Common.javascriptClick(driver, driver.findElement(By.xpath("//a[text()='" + SecondQuoteId + "']")));

			List<WebElement> groups = driver.findElements(By.xpath("(//div[contains(@class,'address')])[1]//li"));

			for (int i = 0; i < groups.size(); i++) {
				String addressTEMP = groups.get(i).getText().trim();
				//first is name 
				if (addressTEMP != "" && i>0) {
					shippingAddress.add(addressTEMP);
//					System.out.println(".." + addressTEMP);
				}
			}

			mailPage = new MailPage(driver);
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, "test27028");
			Dailylog.logInfoDB(7, "check email: " + SecondQuoteQuantity, Store, testName);

			// verify purchase order information Billing address/shipping
			// address
			Assert.assertTrue(checkEmailContent(driver, mailPage, SecondQuoteId, SecondQuoteId),
					"B2C email is not received ");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	// =*=*=*=*=*=*=*Add A product to cart for B2B*=*=*=*=*=*=*=//
	private void AddToCartB2B(WebElement LaptopXpath, WebElement DetailXpath, int StepNo) {
		Dailylog.logInfoDB(StepNo, "Adding a product to cart...", Store, testName);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(2500);
		//
		b2bPage.cartPage_quickOrder.clear();

		b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
		b2bPage.cartPage_addButton.click();

		Assert.assertTrue(driver.getTitle().contains("Cart"));
		Assert.assertTrue(b2bPage.cartPage_Quantity.isDisplayed(), "Item was not added to cart successfully");
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
