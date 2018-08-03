package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21952Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;

	public NA21952Test(String store) {
		this.Store = store;
		this.testName = "NA-21952";
	}

	@Test(alwaysRun = true,groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA21952(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			String testProduct = testData.B2B.getDefaultMTMPN1();
			String testApprover = testData.B2B.getApproverId();
			if (Store.contains("AU"))
				testProduct = "20HGS0CB0P";
			else if (Store.contains("US"))
				testProduct = "20JES1X200";
			else if (Store.contains("JP"))
				testProduct = "20JJS08W4M";

			// 1. logon website
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver);
			Dailylog.logInfoDB(1, "login b2b site", Store, testName);

			// 2. go to homepage and choose one product add to cart
			B2BCommon.addProduct(driver, b2bPage, testProduct);
			Dailylog.logInfoDB(2, "added product to cart", Store, testName);

			// 3. continue to go to the check out process and can see summary page
			if (Common.isElementExist(driver,
					By.xpath(Common.convertWebElementToString(b2bPage.cartPage_LenovoCheckout))))
				b2bPage.cartPage_LenovoCheckout.click();
			else
				Assert.fail("click checkout failure");
			Dailylog.logInfoDB(3, "clicked checkout", Store, testName);

			fillAddress();

			Common.sleep(2000);
			if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 10)) {
				System.out.println("reseller display");
				b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
			}
				

			Dailylog.logInfoDB(3, "filled in shipping and billing info", Store, testName);

			// 4. choose approver and add comments
			String builderComment = "builder comment test";
			String oderNumber = sendForApproval(builderComment, testApprover);
			
			// 5. use approver to login the website
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getApproverId(), testData.B2B.getDefaultPassword());
			Dailylog.logInfoDB(5, "approver login b2b site", Store, testName);

			// 6. go to my account link
			if (Common.isElementExist(driver, By.xpath(".//a/span[@class='link-title']"), 5))
				b2bPage.myAccount_link.click();
			else
				Assert.fail("can not see my account Button");

			// 7. click View orders that require approval
			b2bPage.myAccountPage_viewOrderRequireApproval.click();

			// 8. click order number will go to detail page
			By testOderX = By.xpath(".//a[contains(text(),'" + oderNumber + "')]");
			if (Common.checkElementDisplays(driver, testOderX, 10)) {
				driver.findElement(testOderX).click();
			} else {
				Assert.fail("order is not displayed under require approval page: " + oderNumber);
			}

			switchToWindow(1);
			String approverComment = "reject comment test";
			String builderComment2 = "";
			builderComment2 = b2bPage.summary_builderComment.getAttribute("value");
			Dailylog.logInfoDB(8, "builder comment is: " + builderComment2, Store, testName);

			if (!builderComment2.equals(builderComment)) {
				Assert.fail("builder comment is not correct");
			}

			// 9. approver add the comments and then reject order
			b2bPage.orderRequireApproval_approverComment.clear();
			b2bPage.orderRequireApproval_approverComment.sendKeys(approverComment);
			driver.findElement(By.xpath(".//*[@id='approverDecisionReject']")).click();
			Common.waitElementVisible(driver, b2bPage.OrderApprovalDashboard_message);
			Dailylog.logInfoDB(9, "OrderApprovalDashboard_message" + b2bPage.OrderApprovalDashboard_message.getText(),
					Store, testName);

			// 10. builder login website
			switchToWindow(0);
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());

			// 11. go to my account link
			b2bPage.myAccount_link.click();

			// 12. click View Web Orders History link
			b2bPage.myAccount_viewOrderHistory.click();

			// 13. click edit link which status is rejected
			b2bPage.viewOrderHistory_orderCode.clear();
			b2bPage.viewOrderHistory_orderCode.sendKeys(oderNumber);
			b2bPage.viewOrderHistory_orderHistorySearch.click();
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//a[@id='" + oderNumber + "']")), 15);
			driver.findElement(By.xpath("//a[@id='" + oderNumber + "']")).click();

			// 14. go to lenovo check out process and goto summary page
			Common.waitElementClickable(driver, b2bPage.cartPage_LenovoCheckout, 15);
			b2bPage.cartPage_LenovoCheckout.click();

			fillAddress();

			// validate approver comment
			String approverComment2 = b2bPage.summary_approverComment.getAttribute("value");
			Dailylog.logInfoDB(9, "approverComment2: " + approverComment2, Store, testName);
			Assert.assertEquals(approverComment2, approverComment, "approver comment is not correct");

			// 15. Send for approver again
			if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)) {
				b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
			}

			builderComment = "builder commnet test 2";
			String orderNumber2 = sendForApproval(builderComment, testApprover);
			if (orderNumber2.equals("") || orderNumber2 == null) {
				Assert.fail("orderNumber2: " + orderNumber2);
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			// System.out.println(windows.size());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			System.out.println("Swith to window timeout, window: " + windowNo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void fillAddress() throws InterruptedException {
		// shipping
		b2bPage.Shipping_showAllAddress.click();
		if (Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2bPage.Shipping_addressItem)))) {
			b2bPage.Shipping_addressItem.click();
		} else {
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
		}
		Common.sleep(5000);

		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("FirstNameJohn");
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("LastNameSnow");
		// continue
		b2bPage.shippingPage_ContinueToPayment.click();
//		By validateOK = By.id("checkout_validateFrom_ok");
//		if(Common.isElementExist(driver, validateOK))
//			driver.findElement(validateOK).click();
//		
//		By errorMsgX = By.xpath("//div[@class='alert neutral']");
//		if (Common.checkElementDisplays(driver, errorMsgX, 10)) {
//			Dailylog.logInfoDB(0, driver.findElement(errorMsgX).getText(), Store, testName);
//			B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store, "FirstNameJohn", "LastNameSnow",
//					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
//					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
//			b2bPage.shippingPage_ContinueToPayment.click();
//			if(Common.isElementExist(driver, validateOK))
//				driver.findElement(validateOK).click();
//		}

		// billing
//		B2BCommon.creditCardPayment(driver, b2bPage);
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
		Common.javascriptClick(driver, b2bPage.Shipping_showAllAddress);
		driver.findElement(By.xpath("//input[@id='address.firstName']")).sendKeys("FirstNameJohn");
		driver.findElement(By.xpath("//input[@id='address.lastname']")).sendKeys("LastNameSnow");
//		b2bPage.Shipping_showAllAddress.click();
		driver.findElement(By.xpath("//input[@id='address.phoneNumber1']")).sendKeys("1234567890");
		b2bPage.paymentPage_ContinueToPlaceOrder.click();
//		if (Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2bPage.Shipping_addressItem)))) {
//			Common.scrollToElement(driver, b2bPage.Shipping_addressItem);
//			b2bPage.Shipping_addressItem.click();
//			b2bPage.paymentPage_ContinueToPlaceOrder.click();
//		} else {
//			B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
//		}
	}

	private String sendForApproval(String builderComment, String testApprover) throws InterruptedException {
		if (Common.isElementExist(driver, By.xpath(".//*[@id='approveId']"), 10))
			Common.javascriptClick(driver, b2bPage.placeOrderPage_selectApprover);
//			b2bPage.placeOrderPage_selectApprover.click();
		else
			Assert.fail("select approver is not displayed");

		WebElement approverOption = driver.findElement(
				By.xpath(".//*[@id='approveId']/option[contains(text(),'" + testApprover.toUpperCase() + "')]"));
		Common.waitElementClickable(driver, approverOption, 15);
		approverOption.click();

		if (Common.isElementExist(driver, By.xpath(".//*[@id='builderCommit']"), 10)) {
			b2bPage.summary_builderComment.clear();
			b2bPage.summary_builderComment.sendKeys(builderComment);
		} else
			Assert.fail("builder comment is not displayed");

		Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
		Common.javascriptClick(driver, b2bPage.placeOrderPage_sendForApproval);
		Common.waitElementVisible(driver, b2bPage.placeOrderPage_OrderNumber);
		String orderNumber = b2bPage.placeOrderPage_OrderNumber.getText();
		return orderNumber;

	}
	

	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		}
	}
}
