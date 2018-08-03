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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class COMM54Test extends SuperTestClass {
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

	public COMM54Test(String store) {
		this.Store = store;
		this.testName = "COMM-54";
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
	public void COMM54(ITestContext ctx) {
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
			//add address
			hmcPage.B2Bunit_Address.click();
			
			
			hmcPage.HMC_Logout.click();
			// step~2
			driver.get(testData.B2B.getLoginUrl());

			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver, b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
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

			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			if (Common.isElementExist(driver, By.id("checkout_validateFrom_ok"))) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			b2bPage.paymentPage_FirstName.clear();
			b2bPage.paymentPage_FirstName.sendKeys("testFname");
			
			b2bPage.paymentPage_LastName.clear();
			b2bPage.paymentPage_LastName.sendKeys("testLname");
			b2bPage.paymentPage_companyName.clear();
			b2bPage.paymentPage_companyName.sendKeys("TESTCOMM54");
			if(b2bPage.paymentPage_addressLine1.isEnabled()){
				b2bPage.paymentPage_addressLine1.clear();
				if(Store.toLowerCase().equals("us")){
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				}else if(Store.toLowerCase().equals("au")){
					b2bPage.paymentPage_addressLine1.sendKeys("1535 Broadway");
				}
				
			}
			if(b2bPage.paymentPage_cityOrSuburb.isEnabled()){
				b2bPage.paymentPage_cityOrSuburb.clear();
				if(Store.toLowerCase().equals("us")){
					b2bPage.paymentPage_cityOrSuburb.sendKeys("New York");
				}else if(Store.toLowerCase().equals("au")){
					b2bPage.paymentPage_cityOrSuburb.sendKeys("RIVETT");
				}
			}
			if(b2bPage.paymentPage_addressState.isEnabled()){
				b2bPage.paymentPage_addressState.click();
				if(Store.toLowerCase().equals("us")){
					driver.findElement(By.xpath(".//*[@id='address.region']/option[contains(.,'New York')]")).click();
				}else if(Store.toLowerCase().equals("au")){
					driver.findElement(By.xpath(".//*[@id='address.region']/option[contains(.,'Australian Capital Territory')]")).click();
				}
			}
			if(b2bPage.paymentPage_addressPostcode.isEnabled()){
				b2bPage.paymentPage_addressPostcode.clear();
				if(Store.toLowerCase().equals("us")){
					b2bPage.paymentPage_addressPostcode.sendKeys("10036-4077");
				}else if(Store.toLowerCase().equals("au")){
					b2bPage.paymentPage_addressPostcode.sendKeys("2611");
				}
			}
			b2bPage.paymentPage_Phone.clear();
			b2bPage.paymentPage_Phone.sendKeys("1234567890");
			
			
			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			
			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			(new WebDriverWait(driver, 500)).until(ExpectedConditions.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
			System.out.println("Go to Order page!");
			
			
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)){
				Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
				b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
			}
			Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
			b2bPage.placeOrderPage_PlaceOrder.click();
			Thread.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("orderConfirmation"));

			 
			String orderNumber = b2bPage.placeOrderPage_OrderNumber.getText()
					.toString();
			System.out.println("orderNumber is :" + orderNumber);
			//TODO CHECKOUT COMPANY NAME
			
			mailPage = new MailPage(driver);
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, testData.B2B.getBuilderId());

			checkEmailContent(driver, mailPage, orderNumber, testData.B2B.getCompany(), "TESTCOMM54");

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

	



}
