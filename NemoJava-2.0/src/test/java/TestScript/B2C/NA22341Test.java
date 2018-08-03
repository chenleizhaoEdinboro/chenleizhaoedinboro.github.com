package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

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
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22341Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public B2BPage b2bPage;

	public String Url;
	public String UnitID = "auingramreseller_unit";

	// public String commonCustomer = "CommonCustoemr@yopmail.com";
	// public String approver = "Approver@yopmail.com";

	public NA22341Test(String Store, String Url) {
		this.Store = Store;
		this.Url = Url;
		this.testName = "NA-22341";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA22341(ITestContext ctx) {

		try {

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			b2bPage = new B2BPage(driver);
			Url = testData.B2C.getHomePageUrl().replace("/au/en/auweb", "/au/en/auingramreseller/");
			// 1 : HMC->B2C Unit->Site attribute Tab-> Pending BPCTO Order
			// Toggle: YES
			Dailylog.logInfoDB(1, "HMC->B2C Unit->Site attribute Tab-> Pending BPCTO Order", Store, testName);
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

			// 2,Under Site attribute Tab Remove vaule of Business Email , then
			// click save
			Dailylog.logInfoDB(2, "Site attribute Tab Remove vaule of Business Email", Store, testName);
			driver.findElement(
					By.xpath("//input[contains(@id,'B2CUnit.dynamicBusinessEmail]')]"))
					.clear();
			Thread.sleep(10000);
			driver.findElement(
					By.xpath("//input[contains(@id,'B2CUnit.dynamicBusinessEmail]')]"))
					.sendKeys("bpcto@yopmail.com");
			driver.findElement(
					By.xpath("//div[contains(@id,'Content/ImageToolbarAction[organizer.editor.save.title]')]"))
					.click();

			String email = driver
					.findElement(
							By.xpath("//input[contains(@id,'B2CUnit.dynamicBusinessEmail]')]"))
					.getAttribute("value");
			System.out.println("Email is :" + email);
			Assert.assertTrue(email.equals("bpcto@yopmail.com"));

			driver.findElement(By.xpath("//img[contains(@id,'closesession')]"))
					.click();

			// 3,Load URL, click return

			driver.get(Url);

			// 4, Login with customer account aubpcto@yopmail.com IF have no
			// account,create one on website
			Dailylog.logInfoDB(4, "Login with customer account", Store, testName);
			String gateKeeperUrl = driver.getCurrentUrl().toString();

//			Assert.assertTrue(gateKeeperUrl.endsWith("RegistrationGatekeeper"));
//
//			B2CCommon.handleGateKeeper(b2cPage, testData);

			driver.get(Url + "/login");
			B2CCommon.login(b2cPage, "testau@yopmail.com", "1q2w3e4r");

			B2CCommon.closeHomePagePopUP(driver);


			B2CCommon.clearTheCart(driver, b2cPage, testData);

			Common.sleep(2000);
			//PRICE 500-1000
//			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
			B2CCommon.addPartNumberToCart(b2cPage, "06P4069");
			
			Dailylog.logInfoDB(6, "Added  product into cart", Store, testName);

//			Dailylog.logInfoDB(5, "Login with customer account", Store, testName);
//			// 5,Select products-> Accessories and Upgrades
//			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
//					SplitterPage.Accessories);
//
//			// 6, Select Monitor -> Pick a monitor part number, click
//			// "Learn More"
//			Dailylog.logInfoDB(6, "Learn More", Store, testName);
//			driver.findElement(
//					By.xpath("//span[contains(@class,'accessoriesCategoriesTitle')]"))
//					.click();
//			driver.findElement(By.xpath("//*[@id='Monitors']/div/h3/a"))
//					.click();

			// 7, Add product to cart
//			Dailylog.logInfoDB(7, "add to cart", Store, testName);
//			((JavascriptExecutor) driver).executeScript("scroll(0,300)");
//			driver.findElement(By.xpath("(//*[@id='addToCartButtonTop'])[1]"))
//					.click();
//			Thread.sleep(8000);
//			// 8, Go to cart
//			driver.get(cartUrl);

			// 9,Enter Checkout
			Dailylog.logInfoDB(9, "Checkout", Store, testName);
			b2cPage.lenovo_checkout.click();

			// 10, Fill in valid shipping imformation
			Dailylog.logInfoDB(10, "Fill in valid shipping imformation", Store, testName);
			if (b2cPage.Shipping_editAddress.isDisplayed()) {
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

			// 11, Click continue
			Dailylog.logInfoDB(11, "Click continue", Store, testName);
			Thread.sleep(3000);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);
			
			
			if (Common
					.isElementExist(
							driver,
							By.xpath("//div[@class='checkout_stepone_pop_validateinfo']"))
					&& driver
							.findElement(
									By.xpath("//div[@class='checkout_stepone_pop_validateinfo']"))
							.isDisplayed()) {
				driver.findElement(
						By.xpath("//input[@id='checkout_validateFrom_ok']"))
						.click();
			}

			// 12, Select IGF, PO# = TESTPO_HIDE and PO Date = today
			Dailylog.logInfoDB(12, "Select IGF, PO# = TESTPO_HIDE and PO Date = today", Store, testName);
			if (Common.isElementExist(driver,
					By.id("PaymentTypeSelection_LEASING"))) {
				driver.findElement(By.id("PaymentTypeSelection_LEASING"))
						.click();
			} else {
				if (!b2cPage.payment_IGF.isSelected()) {
					b2cPage.payment_IGF.click();
				}
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

			// 13, Click continue
			Dailylog.logInfoDB(13, "Click continue", Store, testName);
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);

			// 14, Drop order
			Dailylog.logInfoDB(14, "Drop order", Store, testName);
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

			
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			String OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(14, "order number : " +OrderNumber , Store, testName);
			
			// 15, Login into HMC		
			Dailylog.logInfoDB(15, "Login into HMC" , Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			// 16, Locate order number
			Thread.sleep(5000);
			Dailylog.logInfoDB(16, "Locate order number" , Store, testName);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(OrderNumber);
			hmcPage.Home_Order_OrderSearch.click();
			Thread.sleep(5000);

			String status = hmcPage.Home_Order_OrderStatus.getText().toString()
					.trim();
			System.out.println("status is :" + status);
			Dailylog.logInfoDB(16, "order status " + status, Store, testName);
			
			Assert.assertTrue(status.equals("BPCTO Holding"));

			// 17, click the order record
			Dailylog.logInfoDB(17, "click the order record", Store, testName);
			driver.findElement(
					By.xpath("//span[contains(@id,'Content/OrganizerListEntry')]/img"))
					.click();

			// 18, Navigate to Payment and Delivery tab
			Dailylog.logInfoDB(18, "Navigate to Payment and Delivery tab", Store, testName);
			driver.findElement(
					By.xpath(".//*[@id='Content/EditorTab[Order.payment_and_delivery]_span']"))
					.click();

			boolean isDisplayed = Common
					.isElementExist(
							driver,
							By.xpath(".//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[Order.status]]_select']/option[contains(.,'BPCTO Holding') and @selected = '']"));

			Assert.assertTrue(isDisplayed);

			// 19, Navigate to Purchase Order tab
			Dailylog.logInfoDB(19, "Navigate to Purchase Order tab", Store, testName);
			driver.findElement(
					By.xpath(".//*[@id='Content/EditorTab[Order.tab.purchase.order]_span']"))
					.click();

			/*
			 * PO Number and PO Date from Payments page display.
			 * 
			 * MARK: 1. If order status = "BP CTO Holding", then PO Number and
			 * PO Date from Payments page.
			 * 
			 * 2. If order status = "BP CTO Holding NOT PO method", then PO
			 * number and PO Date should be BLANK.
			 */

			boolean purchaseOrderNumber_isDisplayed = driver
					.findElement(
							By.xpath(".//*[@id='Content/StringEditor[in Content/Attribute[Order.purchaseOrderNumber]]_input']"))
					.isDisplayed();
			boolean purchaseOrderDate_isDisplayed = driver
					.findElement(
							By.xpath(".//*[@id='Content/DateTimeEditor[in Content/Attribute[Order.purchaseOrderDate]]_date']"))
					.isDisplayed();

			Assert.assertTrue(purchaseOrderNumber_isDisplayed
					&& purchaseOrderDate_isDisplayed);

			String purchaseOrderNumber = "1234567890";

			String verify_orderNumber = driver
					.findElement(
							By.xpath(".//*[@id='Content/StringEditor[in Content/Attribute[Order.purchaseOrderNumber]]_input']"))
					.getAttribute("value").toString().trim();

			Assert.assertTrue(purchaseOrderNumber.equals(verify_orderNumber));

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
			String orderDate = sdf.format(new Date());

			String verify_orderDate = driver
					.findElement(
							By.xpath(".//*[@id='Content/DateTimeEditor[in Content/Attribute[Order.purchaseOrderDate]]_date']"))
					.getAttribute("value").toString();
			Assert.assertTrue(orderDate.equals(verify_orderDate));

			// 20,Update order status to BPCTO Approved. Save.
			Dailylog.logInfoDB(20, "Update order status to BPCTO Approved. Save.", Store, testName);
			driver.findElement(
					By.xpath(".//*[@id='Content/EditorTab[Order.payment_and_delivery]_span']"))
					.click();

			Select select = new Select(
					driver.findElement(By
							.xpath(".//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[Order.status]]_select']")));
			select.selectByValue("14");

			driver.findElement(
					By.xpath("//div[contains(@id,'organizer.editor.save.title')]"))
					.click();

			// 21,Click Administration, XML content show
			Dailylog.logInfoDB(21, "Click Administration, XML content show", Store, testName);
			driver.findElement(
					By.xpath("//*[@id='Content/EditorTab[Order.administration]_span']"))
					.click();

			String text = driver
					.findElement(
							By.xpath("//textarea[contains(@id,'Content/TextAreaEditor[in Content/Attribute[Order.xmlContentShow]')]"))
					.getText();
			System.out.println("text is :" + text);

			Assert.assertTrue(text.equals(""));

			// 22,Navigate to System -> cronjobs -> Search for
			// "NemoRetriggerBPCTOApprovedOrderCronJob" and execute the cronjob.
			Dailylog.logInfoDB(22, "execute the cronjob", Store, testName);
			driver.findElement(
					By.xpath(".//*[@id='Tree/GenericExplorerMenuTreeNode[system]_label']"))
					.click();
			driver.findElement(
					By.xpath(".//*[@id='Tree/GenericLeafNode[CronJob]_label']"))
					.click();
			// NemoRetriggerBPCTOApprovedOrderCronJob
			Select selectCronJob = new Select(
					driver.findElement(By
							.xpath(".//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[CronJob.job]]_select']")));

			selectCronJob
					.selectByVisibleText("NemoRetriggerBPCTOApprovedOrderCronJob");

			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]"))
					.click();
			Thread.sleep(4000);
			driver.findElement(
					By.xpath("//img[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob')]"))
					.click();

			// 23, Navigate to "Run As" tab, user = byronc AND language =
			// English AND currency = USD
			Dailylog.logInfoDB(23, "Navigate to Run As tab,", Store, testName);
			driver.findElement(By.xpath("//span[contains(.,'Run as')]"))
					.click();

			Select select_lan = new Select(
					driver.findElement(By
							.xpath("//select[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.sessionLanguage')]")));
			select_lan.selectByVisibleText("English");

			Select select_currency = new Select(
					driver.findElement(By
							.xpath("//select[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.sessionCurrency')]")));
			select_currency.selectByVisibleText("USD");

			String nodeValue = driver
					.findElement(
							By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.nodeID')]"))
					.getAttribute("value").toString();
			if (nodeValue.equals("0")) {
				driver.findElement(
						By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.nodeID')]"))
						.clear();
				driver.findElement(
						By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.nodeID')]"))
						.sendKeys("7");
				driver.findElement(
						By.xpath("//img[contains(@id,'organizer.editor.save.title')]"))
						.click();
			}

			String bigDate = driver
					.findElement(
							By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.endTime') and contains(@id,'date')]"))
					.getAttribute("value");
			String smallDate = driver
					.findElement(
							By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.endTime') and contains(@id,'time')]"))
					.getAttribute("value");

			System.out.println("bigDate is :" + bigDate);
			System.out.println("smallDate is :" + smallDate);

			// 24, Click "Start Cronjob Now"
			Dailylog.logInfoDB(24, "Start Cronjob Now", Store, testName);
			String main_Winhandle = driver.getWindowHandle().toString();

			// start cron job now
			driver.findElement(
					By.xpath("//div[contains(@id,'performcronjob')]")).click();

			Set<String> set = driver.getWindowHandles();

			for (String str : set) {
				if (str.equals(main_Winhandle))
					continue;
				driver.switchTo().window(str);
			}

			// String hint_info =
			// driver.findElement(By.xpath(".//*[@id='outerTD']//p[@class='titleOK']")).getText().toString().trim();
			//
			// Assert.assertTrue(hint_info.contains("successfully"));

			String cronJob_performed = driver
					.findElement(
							By.xpath(".//*[@id='outerTD']//td[@class='arcTextArea']/textarea"))
					.getText().toString().trim();
			System.out.println("cronJob_performed is :" + cronJob_performed);

			Assert.assertTrue(cronJob_performed.equals("CronJob performed."));

			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

			driver.findElement(
					By.xpath(".//*[@id='outerTD']//a[@title='Close Window']/span"))
					.click();

			driver.switchTo().window(main_Winhandle);

			String bigDate_after = driver
					.findElement(
							By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.endTime') and contains(@id,'date')]"))
					.getAttribute("value");
			String smallDate_after = driver
					.findElement(
							By.xpath("//input[contains(@id,'NemoRetriggerBPCTOApprovedOrderCronJob.endTime') and contains(@id,'time')]"))
					.getAttribute("value");

			System.out.println("bigDate_after is :" + bigDate_after);
			System.out.println("smallDate_after is :" + smallDate_after);

			// Assert.assertTrue(!bigDate_after.equals(bigDate) &&
			// smallDate_after.equals(smallDate));

			if (!bigDate_after.equals(bigDate)) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(!smallDate_after.equals(smallDate));
			}

			// 25, Navigate to "Orders" and locate the order
			Dailylog.logInfoDB(25, "Navigate to Orders", Store, testName);
			driver.findElement(By.xpath("//img[contains(@id,'closesession')]"))
					.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			Thread.sleep(5000);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(OrderNumber);
			hmcPage.Home_Order_OrderSearch.click();
			Thread.sleep(5000);

			String status_completed = hmcPage.Home_Order_OrderStatus.getText()
					.toString().trim();
			System.out.println("status_completed is :" + status_completed);
			Thread.sleep(5000);
			Assert.assertTrue(status_completed.equals("Completed"));

			// 26,Log into HMC and validate order XML under Administration tab
			Dailylog.logInfoDB(26, "Log into HMC and validate order XML under Administration tab", Store, testName);
			driver.findElement(
					By.xpath("//span[contains(@id,'Content/OrganizerListEntry')]/img"))
					.click();
			Thread.sleep(4000);
			driver.findElement(
					By.xpath("//*[@id='Content/EditorTab[Order.administration]_span']"))
					.click();

			String xmlInfo = driver
					.findElement(
							By.xpath("//textarea[contains(@id,'Order.xmlContentShow')]"))
					.getText().toString();

			Assert.assertTrue(!xmlInfo.isEmpty());

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void signOut() {
		driver.get(Url);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By
				.xpath("(//a[@class='has-submenu']/span[contains(.,'Account')])[2]")));
		actions.moveToElement(
				driver.findElement(By
						.xpath("(//a[contains(@href,'logout')]/div[@class='link_text'])[1]")))
				.perform();
		driver.findElement(
				By.xpath("(//a[contains(@href,'logout')]/div[@class='link_text'])[1]"))
				.click();
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
