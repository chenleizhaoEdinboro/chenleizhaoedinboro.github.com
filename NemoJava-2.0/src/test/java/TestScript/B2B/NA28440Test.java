package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA28440Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public String homePageUrl;
	private boolean isCreate = false;
	private String lenovoMail = "testjp@lenovo.com";
	private String emailPattern = "^[A-Za-z0-9._%+-]+@lenovo.com$";
	private String emailDefaultPattern = "\\" + "b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" + "\\" + "b";
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
	private String country;
	private String ruleName;

	public NA28440Test(String Store, String country, String ruleName) {
		this.Store = Store;
		this.testName = "NA-28440";
		this.country = country;
		this.ruleName = ruleName;
	}

	private void validateRule(String rule) {

		hmcPage.addressValidatorRule_EmailPattern.clear();
		hmcPage.addressValidatorRule_EmailPattern.sendKeys(rule);
		
		if (isCreate) {

			hmcPage.paymentProfile_paymentCreate.click();
		} else {
			hmcPage.HMC_Save.click();
		}

		Common.sleep(5000);

	}

	private void addRuleToUnit() {
		Dailylog.logInfoDB(4, "add [auB2CName] to this unit and save", Store, testName);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();

		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();

		hmcPage.AddressValidatorRule.click();
		switchToWindow(1);
		hmcPage.management_id.sendKeys(ruleName);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_searchedPayment.click();
		hmcPage.B2BUnit_use.click();
		switchToWindow(0);
		hmcPage.B2BUnit_Save.click();
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

	private void inputemail(String email) {
		b2bPage.shippingPage_email.clear();
		b2bPage.shippingPage_email.sendKeys(email);
	
		Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
	}

	

	private void searchRule(boolean isRollBack) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);

		hmcPage.hmc_internationalization.click();
		Common.waitElementClickable(driver, hmcPage.hmc_addressValidatorRule, 3);
		hmcPage.hmc_addressValidatorRule.click();
		Common.sleep(2000);
		Select countrySel = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'AllInstancesSelectEditor')]")));
		countrySel.selectByVisibleText(country);
		if (isRollBack) {
			Select audience = new Select(driver.findElement(By.xpath("//select[contains(@id,'zAudience]]')]")));
			audience.selectByVisibleText("B2B");
		}
		hmcPage.AddCountry_Search.click();

	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA28440(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			// Step 1 : HMC->Internationalization --> address validation rule
			searchRule(false);
			Common.sleep(5000);
			if (Common.isElementExist(driver, By.xpath("//div[contains(@id,'B2B')]"))) {
				// update
				Dailylog.logInfoDB(2, "have a B2B rule,UPDATE", Store, testName);
				Common.doubleClick(driver, hmcPage.serach_result_B2B);
			} else {

				// Step 2 :create an address validation rule for JP,audience:
				// B2B;
				isCreate = true;
				Dailylog.logInfoDB(2, "create an address validation rule", Store, testName);
				Common.rightClick(driver, hmcPage.serach_result);
				hmcPage.result_clone.click();
				Select audience = new Select(driver.findElement(By.xpath("//select[contains(@id,'zAudience')]")));
				audience.selectByVisibleText("B2B");
				hmcPage.management_id.clear();
				hmcPage.management_id.sendKeys(ruleName);
			}
			// Step 3 rule: email must be lenovo email
			Dailylog.logInfoDB(3, "update rule", Store, testName);
			validateRule(emailPattern);

			// Step 4 go to B2BUnit , find the attribute [AddressValidatorRule]
			// and add rule to this unit and save
		/*	Dailylog.logInfoDB(4, "add rule to this unit and save", Store, testName);
			addRuleToUnit();*/
			hmcPage.hmcHome_hmcSignOut.click();
			// Step 5 add product into cart
			Dailylog.logInfoDB(5, "add product into cart", Store, testName);
			checkAdvancedShippingOptionInB2B();

			// Step 6 shipping page check rule
			Dailylog.logInfoDB(6, "check first name on shiiping page", Store, testName);
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store,"FirstNameJohn", "LastNameSnow", 
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
			
			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			
			Dailylog.logInfoDB(6, "check email rule on shipping page", Store, testName);
			// TODO verify
			
			Assert.assertFalse(driver.getTitle().contains("Payment"));								
			inputemail(lenovoMail);
					

			if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			Common.sleep(4000);
			Assert.assertTrue(driver.getTitle().contains("Payment"));

			//payment page no email input
//			b2bPage.paymentPage_PurchaseOrder.click();
//			Common.sleep(2000);
//			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
//			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
//
//			// Step 7 billing page check rule
//			Dailylog.logInfoDB(6, "check email rule on payment page", Store, testName);
//			inputemail(testData.B2B.getBuyerId());
//			Assert.assertFalse(driver.getTitle().contains("Checkout"));
//
//			
//			// TODO verify
//			inputemail(lenovoMail);
//			
//			Assert.assertTrue(driver.getTitle().contains("Checkout"));
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void checkAdvancedShippingOptionInB2B() {
		String b2bLoginURL = testData.B2B.getLoginUrl();
		driver.get(b2bLoginURL);
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		Common.sleep(3000);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(1500);
		if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
		}
		B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());

		Dailylog.logInfoDB(4, "Added a product into Cart", Store, testName);
		Common.sleep(2000);
		b2bPage.cartPage_LenovoCheckout.click();
		Common.sleep(3000);
		((JavascriptExecutor) driver).executeScript("scroll(0,500)");

		Common.sleep(1000);

	}

	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			Dailylog.logInfoDB(8, "rollback", Store, testName); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			searchRule(true);
			if (Common.isElementExist(driver, By.xpath("//div[contains(@id,'B2B')]"))) {

				Dailylog.logInfoDB(8, "rollback run to default email rule", Store, testName);
				Common.doubleClick(driver, hmcPage.serach_result_B2B);
				
				Dailylog.logInfoDB(8, "update rule", Store, testName);
				validateRule(emailDefaultPattern);

			}

			hmcPage.hmcHome_hmcSignOut.click();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
