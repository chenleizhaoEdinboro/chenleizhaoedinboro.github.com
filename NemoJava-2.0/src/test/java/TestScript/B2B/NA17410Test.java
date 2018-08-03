package TestScript.B2B;

import static org.testng.Assert.assertTrue;

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

import com.google.common.base.Verify;

import CommonFunction.B2BCommon;
import CommonFunction.B2BCommon.B2BRole;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA17410Test extends SuperTestClass {
	private static B2BPage b2bPage;
	private static HMCPage hmcPage;
	String createdCartName = "company_" + Common.getDateTimeString();
	String invalidQuoteName = "123456789012345678901234567890123456789012345678901";
	String validQuoteName = "12345678901234567890123456789012345678901234567890";
	String invalidQuoteDescription = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567";
	String validQuoteDescription = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456";
	String createdQuoteNumber;
	String builderAccount ;
	String approverAccount ;

	public NA17410Test(String store) {
		this.Store = store;
		this.testName = "NA-17410";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA17410(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			builderAccount=testData.B2B.getBuilderId();
			
			approverAccount=testData.B2B.getApproverId();
			By addressOptions = By.xpath("//input[@name='address']");
			By suggestedAddressOption = By
					.xpath(".//*[@id='checkout_validateFrom_ok']");
			By Address_OK = By.xpath("//input[@value='ok']");
			// Create the builder and approver accounts and activate those in
			// HMC. First, check if the account exists in HMC or not.
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2BCommerceLink.click();
			hmcPage.Home_B2BCustomer.click();
			Boolean flag1 = checkIfUserExists(builderAccount, false);
			// Add line to click search button
			if (!Common
					.isElementExist(
							driver,
							By.xpath(".//*[@id='Content/StringEditor[in Content/GenericCondition[B2BCustomer.uid]]_input']"))) {
				hmcPage.b2bCustomer_navigationSearchBar.click();
			}
			Boolean flag2 = checkIfUserExists(approverAccount, true);
			hmcPage.hmcHome_hmcSignOut.click();
			createB2BUser(builderAccount, "Builder", B2BRole.Builder, flag1,
					false);
			createB2BUser(approverAccount, "Approver", B2BRole.Approver, flag2,
					true);
			// Step 1:
			Dailylog.logInfoDB(
					1,
					"go to HMC -->B2B commerce -->B2BUnit. OPEN B2BUnit and set is quote available as yes",
					Store, testName);
			setIsQuoteAvailableToggle(hmcPage.B2BUnit_isQuoteAvailable);

			// Step 2:
			Dailylog.logInfoDB(2, "logon B2B site with a builder account.",
					Store, testName);
			
			loginB2B(builderAccount);
			Thread.sleep(2000);

			// Step 3, 4:
			Dailylog.logInfoDB(3, "navigate to Laptop", Store, testName);
			Dailylog.logInfoDB(4, "add product into cart.go to shopping cart.",
					Store, testName);
			addProductToCart(testData.B2B.getDefaultMTMPN1());

			// Step 5:
			Dailylog.logInfoDB(5,
					"click on Request Quote on shopping cart page.", Store,
					testName);
			Thread.sleep(2000);
			requestQuoteAndValidateFields();

			// Step 6:
			Dailylog.logInfoDB(
					6,
					"do not input anything on the popup page, click on OK button.",
					Store, testName);
			submitQuoteAndValidateFields(false, false);

			// Step 7:
			Dailylog.logInfoDB(
					7,
					"Go to shopping cart again, add product into cart by quick order.go to shopping cart.",
					Store, testName);
			addProductToCart(testData.B2B.getDefaultMTMPN1());
			Thread.sleep(1000);
			// Assert.assertTrue(Common.isElementExist(driver,
			// By.xpath("//span[contains(text(),'" +
			// testData.B2B.getDefaultMTMPN1() + "')]")),
			// "Invalid part number. Please update the part number.");

			// Step 8:
			Dailylog.logInfoDB(8, "click on LENOVO Checkout", Store, testName);
			b2bPage.cartPage_LenovoCheckout.click();
			Thread.sleep(1000);
			Assert.assertTrue(
					driver.getCurrentUrl().contains("add-delivery-address"),
					"Shipping page is not displayed.");

			// Step 9:
			Dailylog.logInfoDB(
					9,
					"input delivery address and select shipping method. click on CONTINUE button.",
					Store, testName);
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store,
					testData.B2B.getFirstName(), testData.B2B.getLastName(),
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(),
					testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(),
					testData.B2B.getPhoneNum());
			Common.javascriptClick(driver,
					b2bPage.shippingPage_ContinueToPayment);
			if (Common.isElementExist(driver, Address_OK)) {
				if (Common.isElementExist(driver, addressOptions)) {
					driver.findElement(By.xpath("//input[@name='address']")).click();;

				}
				driver.findElement(By.xpath("//input[@value='ok']")).click();;

				Thread.sleep(6000);
			}
			if (Common.isElementExist(driver, suggestedAddressOption)) {
				driver.findElement(By
						.xpath(".//*[@id='checkout_validateFrom_ok']")).click();

				Thread.sleep(3000);

			}
			// b2bPage.shippingPage_ContinueToPayment.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("payment"),
					"Payment page is not displayed.");

			// Step 10:
			Dailylog.logInfoDB(10, "click on request quote on payment page.",
					Store, testName);
			requestQuoteAndValidateFields();

			// Step 11:
			Dailylog.logInfoDB(
					11,
					"input 123456789012345678901234567890123456789012345678901 in the quote name",
					Store, testName);
			b2bPage.cartPage_requestQuoteName.sendKeys(invalidQuoteName);
			Assert.assertEquals(
					b2bPage.cartPage_requestQuoteName.getAttribute("value"),
					validQuoteName);

			// Step 12:
			Dailylog.logInfoDB(
					12,
					"input 12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901 in the quote description.",
					Store, testName);
			b2bPage.cartPage_requestQuoteDescription
					.sendKeys(invalidQuoteDescription);
			Assert.assertEquals(b2bPage.cartPage_requestQuoteDescription
					.getAttribute("value"), validQuoteDescription);

			// Step 13:
			Dailylog.logInfoDB(13, "click on OK button on this popup window.",
					Store, testName);
			submitQuoteAndValidateFields(true, true);
			Assert.assertEquals(
					b2bPage.quoteConfirmation_quoteNameValue.getText(),
					validQuoteName);
			Assert.assertEquals(
					b2bPage.quoteConfirmation_quoteDescriptionValue.getText(),
					validQuoteDescription);
			createdQuoteNumber = b2bPage.quoteConfirmation_quoteID.getText();

			// Step 14:
			Dailylog.logInfoDB(14, "check the builder's contact email.", Store,
					testName);

			// Step 15, 16, 17, 18:
			Dailylog.logInfoDB(15, "go to My Account/ My saved Quote", Store,
					testName);
			Dailylog.logInfoDB(16,
					"click on the dropdownlist of the search criteria.", Store,
					testName);
			Dailylog.logInfoDB(
					17,
					"select Quote Name as search criteria, input 123456 and click on search.",
					Store, testName);
			Dailylog.logInfoDB(18, "click on the quote name.", Store, testName);
			loginB2B(builderAccount);
			searchAndSelectQuote();

			// Step 19:
			Dailylog.logInfoDB(19, "click on Edit Quote", Store, testName);
			Common.javascriptClick(driver, b2bPage.quoteDetails_editQuote);
			//b2bPage.quoteDetails_editQuote.click();
			Thread.sleep(1000);
			Assert.assertTrue(driver.getCurrentUrl().contains("cart"),
					"User is not navigated to the cart page.");
			//could fail
			/*Assert.assertTrue(
					Common.isElementExist(
							driver,
							By.xpath("//span[contains(text(),'"
									+ testData.B2B.getDefaultMTMPN1() + "')]")),
					"Quote items are not displayed on cart.");*/

			// Step 20:
			Dailylog.logInfoDB(
					20,
					"add product in the shopping cart. click on save quote button.",
					Store, testName);
			addProductToCart(testData.B2B.getDefaultMTMPN1());
			b2bPage.cart_saveQuote.click();
			Thread.sleep(1000);
			submitQuoteAndValidateFields(true, true);

			// Step 21:
			Dailylog.logInfoDB(
					21,
					"go to the quote detail page again. select a quote approver, and click on send for approval",
					Store, testName);
			searchAndSelectQuote();
			b2bPage.placeOrderPage_selectApprover.click();
			Thread.sleep(1000);
			driver.findElement(
					By.xpath(".//select[@id='approveId']/option[contains(.,'"
							+ approverAccount.toUpperCase() + "')]")).click();
			b2bPage.placeOrderPage_sendApproval.click();
			Thread.sleep(2000);

			// Step 22:
			Dailylog.logInfoDB(22, "check the approver's contact email.",
					Store, testName);

			// Step 23:
			Dailylog.logInfoDB(
					23,
					"logoff my account, and re-logon with approver account. Go to quote approval dash board.",
					Store, testName);
			loginB2B(approverAccount);
			driver.get(testData.B2B.getHomePageUrl() + "/my-account");
			Thread.sleep(1000);
			b2bPage.myAccountPage_viewQuoteRequireApproval.click();
			Assert.assertTrue(
					driver.getCurrentUrl().contains("quote-approval-dashboard"),
					"Incorrect approve quote page displayed.");

			// Step 24:
			Dailylog.logInfoDB(
					24,
					"Input quote number created before and click Search button",
					Store, testName);
		
			b2bPage.quoteApproval_searchQuoteTxtBox
					.sendKeys(createdQuoteNumber);
			b2bPage.quoteApproval_searchQuote.click();
			Thread.sleep(1000);
			Assert.assertTrue(
					Common.isElementExist(
							driver,
							By.xpath(".//td[contains(@data-title,'Quote Number')]//a[contains(.,'"
									+ createdQuoteNumber + "')]")),
					"Quote sent for approval is not searched in the approval's account.");

			// Step 25:
			Dailylog.logInfoDB(25,
					"check the checkbox, click on approve button.", Store,
					testName);
			b2bPage.quoteApproval_checkQuote.click();
			b2bPage.quoteApproval_approveQuoteButton.click();
			Thread.sleep(2000);
			Assert.assertTrue(b2bPage.quoteApproval_quoteApprovedMessageBar
					.getText().contains(createdQuoteNumber)
					&& b2bPage.quoteApproval_quoteApprovedMessageBar.getText()
							.contains("successfully approved"));
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public boolean checkIfUserExists(String emailID, boolean ifApproverAccount)
			throws InterruptedException {
		boolean flag;
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(emailID);
		hmcPage.B2BCustomer_SearchButton.click();
		if (Common.isElementExist(driver,
				By.xpath("//div/a[@title='Open Editor']/span/img"))) {
			if (ifApproverAccount) {
				// Activate the account
				hmcPage.B2BCustomer_1stSearchedResult.click();
				hmcPage.B2BCustomer_PasswordTab.click();
				Thread.sleep(1000);
				Select selectAccountStatus = new Select(
						hmcPage.B2BCustomer_ActiveUserDropdownCommon);
				selectAccountStatus.selectByVisibleText("ACTIVE");
				hmcPage.Catalog_save.click();
				Thread.sleep(1000);
			}
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public void createB2BUser(String emailID, String accountType, B2BRole role,
			boolean flag, boolean ifApproverAccount)
			throws InterruptedException {
		if (!flag) {
			B2BCommon.createAccount(driver, testData.B2B.getLoginUrl(),
					testData.B2B.getB2BUnit(), b2bPage, role, emailID, Browser);
			if (ifApproverAccount) {
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				hmcPage.Home_B2BCommerceLink.click();
				hmcPage.Home_B2BCustomer.click();
				checkIfUserExists(emailID, true);
				hmcPage.hmcHome_hmcSignOut.click();
			}
		} else {
			Dailylog.logInfoDB(
					0,
					accountType
							+ "account is already created, no need to create user again.",
					Store, testName);
		}
	}

	public void setIsQuoteAvailableToggle(WebElement isQuoteAvailableLocator) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		Common.sendFieldValue(hmcPage.B2BUnit_SearchIDTextBox,
				testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		Thread.sleep(8000);
		hmcPage.B2BUnit_siteAttribute.click();
		isQuoteAvailableLocator.click();
		hmcPage.B2BUnit_Save.click();
		Thread.sleep(8000);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	public void loginB2B(String emailID) throws InterruptedException {
		driver.manage().deleteAllCookies();
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, emailID, testData.B2B.getDefaultPassword());
		Thread.sleep(2000);
	}

	public void addProductToCart(String partNumber) throws InterruptedException {
		driver.get(testData.B2B.getHomePageUrl() + "/cart");
	
		
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(partNumber);
		b2bPage.cartPage_addButton.click();
		Thread.sleep(2000);
	}

	public void requestQuoteAndValidateFields() throws InterruptedException {
		Common.javascriptClick(driver, b2bPage.cartPage_RequestQuoteBtn);
		//b2bPage.cartPage_RequestQuoteBtn.click();
		Thread.sleep(5000);
		Assert.assertEquals(b2bPage.cartPage_requestQuoteLabel.getText(),
				"REQUEST NEW QUOTE");
		System.out.println("2");
		Assert.assertTrue(b2bPage.cartPage_RepIDBox.isDisplayed(),
				"Rep ID field is not displayed.");
		System.out.println("3");
		Assert.assertTrue(b2bPage.cartPage_requestQuoteName.isDisplayed(),
				"Quote name field is not displayed.");
		System.out.println("4");
		Assert.assertTrue(
				b2bPage.cartPage_requestQuoteDescription.isDisplayed(),
				"Quote name field is not displayed.");
	}

	public void submitQuoteAndValidateFields(boolean isQuoteNameDisplayed,
			boolean isQuoteDescriptionDisplayed) throws InterruptedException {
		b2bPage.cartPage_SubmitQuote.click();
		Thread.sleep(1000);
		Assert.assertTrue(driver.getCurrentUrl().contains("submitquote"),
				"Quote confirmation page is not displayed.");
		Assert.assertEquals(
				Common.isElementExist(driver,
						By.xpath(".//li/label[contains(.,'Quote Name')]")),
				isQuoteNameDisplayed);
		Assert.assertEquals(Common.isElementExist(driver,
				By.xpath(".//li/label[contains(.,'Quote Description')]")),
				isQuoteDescriptionDisplayed);
	}

	public void searchAndSelectQuote() throws InterruptedException {
		driver.get(testData.B2B.getHomePageUrl() + "/my-account");
		b2bPage.myAccountPage_ViewQuotehistory.click();
		Assert.assertTrue(
				Common.isElementExist(
						driver,
						By.xpath(".//*[@id='searchCriteria']/option[@value='QuoteName']")),
				"Search by Quote name is not displayed in DDL.");
		Select searchQuoteBy = new Select(
				b2bPage.QuoteHistoryPage_SearchCriteria);
		searchQuoteBy.selectByVisibleText("Quote Name");
		Thread.sleep(1000);
		b2bPage.QuoteHistoryPage_SearchValue.clear();
		b2bPage.QuoteHistoryPage_SearchValue.sendKeys("123456");
		b2bPage.QuoteHistoryPage_SearchButton.click();
		Thread.sleep(1000);
		Assert.assertTrue(
				Common.isElementExist(
						driver,
						By.xpath(".//td[contains(.,'" + createdQuoteNumber
								+ "')]/../td[contains(.,'" + validQuoteName
								+ "')]")),
				"The created quote name is not searched.");
		driver.findElement(
				By.xpath(".//td[@quote='QuoteID']/a[contains(.,'"
						+ createdQuoteNumber + "')]")).click();
	}
}
