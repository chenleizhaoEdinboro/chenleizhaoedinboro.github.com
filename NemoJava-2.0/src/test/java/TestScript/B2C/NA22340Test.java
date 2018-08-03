package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22340Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public String Url;
	public String UnitID = "bpcto_us_insight_direct_usa_unit";

	public String commonCustomer = "CommonCustoemr@yopmail.com";
	public String approver = "Approver@yopmail.com";

	public NA22340Test(String Store, String Url) {
		this.Store = Store;
		this.Url = Url;
		this.testName = "NA-22340";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA22340(ITestContext ctx) {

		try {

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			Url = testData.B2C.getHomePageUrl();
			// before setting : HMC->B2C Unit->Site attribute Tab-> Pending
			// BPCTO Order Toggle: YES

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			driver.findElement(
					By.xpath("//*[@class='listtable']/tbody/tr[2]/td[4]/div/div/table/tbody/tr/td/input"))
					.sendKeys(UnitID);
			driver.findElement(By.xpath("//*[@class='xp-button chip-event']/a"))
					.click();

			driver.findElement(By.xpath("//div/div/table/tbody/tr/td[2]/a"))
					.click();
			driver.findElement(
					By.xpath("//span[contains(@id,'Content/EditorTab[B2CUnit.tab.siteattributes]')]"))
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

			driver.findElement(By.xpath("//img[contains(@id,'closesession')]"))
					.click();

			// 1,Log-into BP CTO site as a customer (non-Approver role)

			driver.get(Url);
			String gateKeeperUrl = driver.getCurrentUrl().toString();

			Assert.assertTrue(gateKeeperUrl.endsWith("RegistrationGatekeeper"));

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

			driver.get(Url+ "/cart");
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			B2CCommon.addPartNumberToCart(b2cPage, "06P4069");
			
			// 2,Add product to the cart
//			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
//					SplitterPage.Accessories);
//
//			driver.findElement(
//					By.xpath("//span[contains(@class,'accessoriesCategoriesTitle')]"))
//					.click();
//			driver.findElement(By.xpath("//*[@id='Monitors']/div/h3/a"))
//					.click();
//
//			driver.findElement(By.xpath("(//*[@id='addToCartButtonTop'])[1]"))
//					.click();
//			driver.get(cartUrl);

			// 3,Enter Checkout
			b2cPage.lenovo_checkout.click();
			if (Common.isElementExist(driver,
					By.xpath(".textLink.checkout-shippingAddress-editLink"))) {
				b2cPage.Shipping_editAddress.click();
			}

			B2CCommon.fillShippingInfo(b2cPage, "test", "test",
					testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(),
					testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(),
					testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID(),
					testData.B2C.getConsumerTaxNumber());

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();",
					b2cPage.Shipping_ContinueButton);

			if (Common
					.isElementExist(
							driver,
							By.xpath("//div[@class='checkout_stepone_pop_validateinfo']"))) {
				driver.findElement(
						By.xpath("//input[@id='checkout_validateFrom_ok']"))
						.click();
			}

			// 4,Select Leasing Payment Type OR Purchase order
			Common.sleep(2000);
			if (Common.isElementExist(driver,
					By.xpath("//label[@for='PaymentTypeSelection_IGF']"))) {
				driver.findElement(
						By.xpath("//label[@for='PaymentTypeSelection_IGF']"))
						.click();
			} else if(Common.isElementExist(driver, By.id("PaymentTypeSelection_PURCHASEORDER"))){
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_PurchaseOrder);
				
			}else{
				Common.javascriptClick(b2cPage.PageDriver, b2cPage.payment_IGF);
			}
			b2cPage.payment_purchaseNum.clear();
			b2cPage.payment_purchaseNum.sendKeys("1234567890");

			// b2cPage.payment_purchaseDate.clear();
			// SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
			// b2cPage.payment_purchaseDate.sendKeys(dataFormat.format(
			// new Date()).toString());

			b2cPage.payment_purchaseDate.click();
			Calendar CD = Calendar.getInstance();
			String DD = CD.get(Calendar.DATE) + "";
			;

			driver.findElement(
					By.xpath("//*[@id='ui-datepicker-div']//a[text()='" + DD
							+ "']")).click();
			if (Common.isElementExist(driver,
					By.xpath("//span[@class='continue_to_review']"))) {
				driver.findElement(
						By.xpath("//span[@class='continue_to_review']"))
						.click();
			}else{driver.findElement(
					By.xpath("//*[@id='add-payment-method-continue']")).click();}
			

			// 5, Navigate to Review and Confirm Page
			if (Common
					.isElementExist(driver, By.xpath("//*[@id='resellerID']"))) {
				try {
					driver.findElement(By.xpath("//*[@id='resellerID']"))
							.clear();
					driver.findElement(By.xpath("//*[@id='resellerID']"))
							.sendKeys("1234567890");
				} catch (Exception e) {
					System.out.println("reseller id is not available");
				}
			}

			Assert.assertTrue(driver.findElement(
					By.xpath("//*[@id='approveId']")).isDisplayed());

			// 6, Select T&Cs checkbox, Select approver name from the dropdown,
			// select "Send for Approver"

			if (Common
					.isElementExist(
							driver,
							By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))) {
				driver.findElement(
						By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))
						.click();
			} else {
				Common.javascriptClick(driver,
						b2cPage.OrderSummary_AcceptTermsCheckBox);
			}

			Select select = new Select(driver.findElement(By
					.xpath("//*[@id='approveId']")));

			select.selectByValue("bpcto");

			B2CCommon.clickPlaceOrder(b2cPage);


			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			// 7, Logout

			signOut();

			// 8,Log-into BP CTO site as an Approver
			B2CCommon.login(b2cPage, "bpcto", "password01");

			// 9,Go to "My Account"
			driver.get(Url + "/my-account");
//			driver.findElement(
//					By.xpath("(//a[@class='has-submenu']/span[contains(.,'Account')])[2]"))
//					.click();

			// 10,Select "View Orders that Require Approver"
			driver.findElement(By.xpath("//a[contains(@href,'MYAPPROVAL')]"))
			.click();

			// 11, Check Dashboard Fields

			boolean orderType = driver
					.findElement(By.xpath(".//*[@id='type']")).isDisplayed();
			boolean orderNumber = driver.findElement(
					By.xpath("//*[@id='order_code']")).isDisplayed();
			boolean owner = driver.findElement(By.xpath(".//*[@id='status']"))
					.isDisplayed();
			boolean searchFor = driver.findElement(
					By.xpath("//*[@id='showMode']")).isDisplayed();
			boolean Date = driver.findElement(By.xpath("//*[@id='sort']"))
					.isDisplayed();

			boolean total = orderType && orderNumber && owner && searchFor
					&& Date;

			Assert.assertTrue(total);

			// 12,Select the order # and Reject the order
			driver.findElement(By.xpath("//*[@id='" + OrderNumber + "']"))
					.click();
			driver.findElement(By.xpath(".//*[@id='batchApproveBPCTOBtn']"))
					.click();

			Assert.assertTrue(driver
					.findElement(
							By.xpath(".//*[@id='globalMessages']/div[contains(.,'approved')]"))
					.isDisplayed());

			// 13, sign out
			signOut();

			// 14, Login into "My Account" -> Select "Order History"
			driver.get(Url);
			B2CCommon.login(b2cPage, "CommonCustomer@yopmail.com", "1q2w3e4r");
			driver.get(Url + "/my-account");
			driver.findElement(
					By.xpath("//a[contains(@href,'orders') and contains(.,'history')]"))
					.click();

			boolean b1 = driver.findElement(
					By.xpath("//a[contains(.,'" + OrderNumber + "')]"))
					.isDisplayed();

			Assert.assertTrue(b1);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void signOut() {
		driver.get(Url);
		// ul[@class='menu
		// general_Menu']//a[contains(@href,'logout')]/div[@class='link_text']
		// Actions actions = new Actions(driver);
		// actions.moveToElement(driver.findElement(By.xpath("(//a[@class='has-submenu']/span[contains(.,'Account')])[2]")));
		// actions.moveToElement(driver.findElement(By.xpath("(//a[contains(@href,'logout')]/div[@class='link_text'])[1]"))).perform();
		// driver.findElement(By.xpath("(//a[contains(@href,'logout')]/div[@class='link_text'])[1]")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].click();",
				driver.findElement(By
						.xpath("//ul[@class='menu general_Menu']//a[contains(@href,'logout')]/div[@class='link_text']")));
	}

	public void emptyCart() {
		if (Common.isElementExist(driver,
				By.xpath("//*[@id='emptyCartItemsForm']/a"))) {
			driver.findElement(By.xpath("//*[@id='emptyCartItemsForm']/a"))
					.click();
		} else {
			System.out.println("cart is empty");
		}
	}

}
