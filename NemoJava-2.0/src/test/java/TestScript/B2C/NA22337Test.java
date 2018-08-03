package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22337Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public String Url;
	public String UnitID = "bpcto_us_insight_direct_usa_unit";

	public String commonCustomer = "CommonCustoemr@yopmail.com";
	public String approver = "approverbpcto@yopmail.com";

	public NA22337Test(String Store, String Url) {
		this.Store = Store;
		this.Url = Url;
		this.testName = "NA-22337";
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void NA22337(ITestContext ctx) {

		try {

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			Url = testData.B2C.getHomePageUrl();
			// 1,HMC->B2C Unit->Site attribute Tab-> Pending BPCTO Order Toggle:
			// YES

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			driver.findElement(By.xpath("//*[@class='listtable']/tbody/tr[2]/td[4]/div/div/table/tbody/tr/td/input"))
					.sendKeys(UnitID);
			driver.findElement(By.xpath("//*[@class='xp-button chip-event']/a")).click();

			driver.findElement(By.xpath("//div/div/table/tbody/tr/td[2]/a")).click();
			driver.findElement(By.xpath("//span[contains(@id,'Content/EditorTab[B2CUnit.tab.siteattributes]')]"))
					.click();

			if (!hmcPage.HideBillingAddressToggle.isSelected()) {
				hmcPage.HideBillingAddressToggle.click();
				hmcPage.Common_SaveButton.click();
			}

			if (!hmcPage.PendingBPCTOOrderToggle.isSelected()) {
				hmcPage.PendingBPCTOOrderToggle.click();
				driver.findElement(
						By.xpath("//div[contains(@id,'Content/ImageToolbarAction[organizer.editor.save.title]')]"))
						.click();
			}

			boolean b = hmcPage.PendingBPCTOOrderToggle.isSelected();

			Assert.assertTrue(b);

			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();

			// 2,Log-into BP CTO site as a customer (non-Approver role)

			driver.get(Url);

			// Assert.assertTrue(gateKeeperUrl.endsWith("RegistrationGatekeeper"));

			B2CCommon.handleGateKeeper(b2cPage, testData);

			driver.get(Url + "/login");
			B2CCommon.login(b2cPage, "CommonCustomer@yopmail.com", "1q2w3e4r");

			String HomeUrl = driver.getCurrentUrl();
			String cartUrl = "";
			HomeUrl = HomeUrl.replace("my-account", "");
			if (HomeUrl.endsWith("/")) {
				cartUrl = HomeUrl + "cart";
			} else {
				cartUrl = HomeUrl + "/cart";
			}

			driver.get(Url + "/cart");
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			B2CCommon.addPartNumberToCart(b2cPage, "06P4069");

			// 4,Enter Checkout
			b2cPage.lenovo_checkout.click();

			// 5,Select Leasing Payment Type OR Purchase order
			if (b2cPage.Shipping_editAddress.isDisplayed()) {
				b2cPage.Shipping_editAddress.click();
			}

			B2CCommon.fillShippingInfo(b2cPage, "test", "test", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);

			B2CCommon.handleAddressVerify(driver, b2cPage);
			Common.sleep(2000);
			if (Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER"))) {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);

			} else if (Common.isElementExist(driver, By.id("PaymentTypeSelection_IGF"))) {
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_IGF);
			} else {
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}

			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");

			if (Common.isElementExist(driver, By.id("purchase_orderNumber"))) {

				b2cPage.payment_purchaseDate.sendKeys(Keys.ENTER);

			}

			Common.waitElementClickable(driver, b2cPage.Payment_ContinueButton, 15);

			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			// 6, Navigate to Review and Confirm Page
			if (Common.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				try {
					driver.findElement(By.xpath("//*[@id='resellerID']")).clear();
					driver.findElement(By.xpath("//*[@id='resellerID']")).sendKeys("1234567890");
				} catch (Exception e) {
					System.out.println("reseller id is not available");
				}
			}

			if (Common.isElementExist(driver, By.xpath("//button[@class='billing_section_continue_button']"))) {
				driver.findElement(By.xpath("//button[@class='billing_section_continue_button']")).click();
			}
			// 7, Select T&Cs checkbox, Select approver name from the dropdown,
			// select "Send for Approver"
			if (Common.isElementExist(driver,
					By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))) {
				driver.findElement(By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']")).click();
			} else {
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			}

			Select select = new Select(driver.findElement(By.xpath("//*[@id='approveId']")));

			try {
				select.selectByValue("bpcto");
			} catch (Exception e) {
				driver.findElement(By.xpath("//select[@id='approveId']//option[@value='approverbpcto@yopmail.com']"))
						.click();
			}
			B2CCommon.clickPlaceOrder(b2cPage);

			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			// 8, Logout

			signOut();
			if(!Common.checkElementExists(driver,b2cPage.Login_LenovoIDTextBox,5)){
				driver.get(Url + "/login");
			}

			// 9,Log-into BP CTO site as an Approver
			B2CCommon.login(b2cPage, "bpcto", "password01");

			// 10,Go to "My Account"
			driver.get(Url + "/my-account");

			// 11,Select "View Orders that Require Approver"
			driver.findElement(By.xpath("//a[contains(@href,'MYAPPROVAL')]")).click();

			// 12, Check Dashboard Fields

			boolean orderType = driver.findElement(By.xpath(".//*[@id='type']")).isDisplayed();
			boolean orderNumber = driver.findElement(By.xpath("//*[@id='order_code']")).isDisplayed();
			boolean owner = driver.findElement(By.xpath(".//*[@id='status']")).isDisplayed();
			boolean searchFor = driver.findElement(By.xpath("//*[@id='showMode']")).isDisplayed();
			boolean Date = driver.findElement(By.xpath("//*[@id='sort']")).isDisplayed();

			boolean total = orderType && orderNumber && owner && searchFor && Date;

			Assert.assertTrue(total);

			// 13,Select the order # and approve
			By myaccount_orderNumber = By.xpath("//*[@id='" + OrderNumber + "']");
			if(Common.checkElementDisplays(driver, myaccount_orderNumber, 5)){
				driver.findElement(myaccount_orderNumber).click();
			}else{
				select = new Select(driver.findElement(By.xpath("//*[@id='status']")));
				select.selectByValue("OTHERAPPROVAL");
				Common.javascriptClick(driver, driver.findElement(By.xpath(".//*[@id='approvalDashboardPage_submit_button']")));
				Common.sleep(2000);
				driver.findElement(myaccount_orderNumber).click();
			}
			driver.findElement(By.xpath(".//*[@id='batchApproveBPCTOBtn']")).click();

			Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='globalMessages']/div[contains(.,'approved')]"))
					.isDisplayed());

			// 15, Check Dashboard Fields

			// 16, sign out
			signOut();
			if(!Common.checkElementExists(driver,b2cPage.Login_LenovoIDTextBox,5)){
				driver.get(Url + "/login");
			}
			// 17, Login into "My Account" -> Select "Order History"
			
			B2CCommon.login(b2cPage, "CommonCustomer@yopmail.com", "1q2w3e4r");

			driver.get(Url + "/my-account");
			driver.findElement(By.xpath("//a[contains(@href,'orders') and contains(.,'history')]")).click();

			boolean b1 = driver.findElement(By.xpath("//a[contains(.,'" + OrderNumber + "')]")).isDisplayed();
			Assert.assertTrue(b1);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void signOut() {
		driver.get(Url);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(
				By.xpath("//ul[@class='menu general_Menu']//a[contains(@href,'logout')]/div[@class='link_text']")));

	}

}
