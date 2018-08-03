package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA18108Test extends SuperTestClass {
	public B2BPage b2bPage;

	public NA18108Test(String store) {
		this.Store = store;
		this.testName = "NA-18108";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18108(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);

			// Login to B2B Builder account
			Dailylog.logInfoDB(1, "Login to B2B with Builder account." + testData.B2B.getBuilderId(), Store, testName);
			driver.get(testData.B2B.getLoginUrl());
			Thread.sleep(2000);

			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			Thread.sleep(1000);
			Dailylog.logInfoDB(1, "Logged in to B2B with Builder account succesfully.", Store, testName);

			String product1 = testData.B2B.getDefaultMTMPN1();
			// testData.B2B.getDefaultMTMPN1() "20HGS0CB0P"
			Dailylog.logInfoDB(1, "Product part number is : " + product1, Store, testName);

			/**
			 * redirecting to cart page clearing the cart, if any product is available
			 * adding product to cart
			 **/
			
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
			B2BCommon.clearTheCart(driver, b2bPage);
			Thread.sleep(1000);
			Dailylog.logInfoDB(1, "Adding product to cart.", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, product1);
			Assert.assertEquals("YOUR ITEMS", driver.findElement(By.xpath(".//h3[@class='cart-items-heading']")).getText());

			Dailylog.logInfoDB(2, "Clicked on Lenovo checkout button.", Store, testName);
			b2bPage.cartPage_LenovoCheckout.click();
			Dailylog.logInfoDB(2, "Placing order...", Store, testName);
			B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);

			Dailylog.logInfoDB(3, "Sending for approval.", Store, testName);
			String orderNumber1 = b2bPage.placeOrderPage_OrderNumber.getText();
			Dailylog.logInfoDB(3, "Order number is : " + orderNumber1, Store, testName);

			/**
			 * redirecting to cart page adding product to cart
			 **/

			String product2 = testData.B2B.getDefaultMTMPN1();
			;
			// testData.B2B.getDefaultMTMPN2() "20HGS0CB0P"
			Dailylog.logInfoDB(4, "Product part number is : " + product2, Store, testName);
			Dailylog.logInfoDB(4, "Adding product to cart.", Store, testName);
			B2BCommon.addProduct(driver, b2bPage, product2);
			Assert.assertEquals("YOUR ITEMS", driver.findElement(By.xpath(".//h3[@class='cart-items-heading']")).getText());
			Dailylog.logInfoDB(4, "Clicked on Lenovo checkout button.", Store, testName);
			b2bPage.cartPage_LenovoCheckout.click();

			Dailylog.logInfoDB(5, "Sending for approval to : " + testData.B2B.getAdminId(), Store, testName);
			B2BCommon.sendForApproval(driver, Store, b2bPage, testData, testData.B2B.getAdminId());
			String orderNumber2 = b2bPage.placeOrderPage_OrderNumber.getText();
			Dailylog.logInfoDB(5, "Order number is : " + orderNumber2, Store, testName);

			/**
			 * redirecting to cart page adding product to cart
			 **/

			String product3 = testData.B2B.getDefaultMTMPN1();
			// testData.B2B.getDefaultMTMPN3() "20HGS0CB0P"
			Dailylog.logInfoDB(6, "Product part number is : " + product3, Store, testName);
			Dailylog.logInfoDB(6, "Adding product to cart.", Store, testName);
			Thread.sleep(2000);
			B2BCommon.addProduct(driver, b2bPage, product3);
			Assert.assertEquals("YOUR ITEMS", driver.findElement(By.xpath(".//h3[@class='cart-items-heading']")).getText());
			Dailylog.logInfoDB(6, "Clicked on Lenovo checkout button.", Store, testName);
			b2bPage.cartPage_LenovoCheckout.click();

			Dailylog.logInfoDB(7, "Sending for approval to : " + testData.B2B.getApproverId(), Store, testName);
			B2BCommon.sendForApproval(driver, Store, b2bPage, testData, testData.B2B.getApproverId());
			String orderNumber3 = b2bPage.placeOrderPage_OrderNumber.getText();
			Dailylog.logInfoDB(7, "Order number is : " + orderNumber3, Store, testName);

			Thread.sleep(2000);
			Dailylog.logInfoDB(8, "Logged out from builder account.", Store, testName);
			b2bPage.ShippingPage_SignOut.click();
			Thread.sleep(2000);
			Dailylog.logInfoDB(8, "Login with approver auadmin@yopmail.com account.", Store, testName);
			B2BCommon.Login(b2bPage, testData.B2B.getAdminId(), testData.B2B.getDefaultPassword());

			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewOrderRequireApproval.click();

			Thread.sleep(2000);
			Assert.assertEquals(orderNumber2, driver.findElement(By.xpath(".//*[@id='order_history']//a[contains(.,'" + orderNumber2 + "')]")).getText());

			Dailylog.logInfoDB(9, "Product " + orderNumber2 + " is present for approval.", Store, testName);

			Dailylog.logInfoDB(9, "Clicking on edit link.", Store, testName);
			WebElement element = driver.findElement(By.xpath(".//*[@id='" + orderNumber2 + "'][contains(.,'Edit')]"));
			Common.scrollToElement(driver, element);
			Common.javascriptClick(driver, element);
			Dailylog.logInfoDB(9, "Clicked on edit link", Store, testName);
			Thread.sleep(8000);
			Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
			Common.sleep(1000);
			Dailylog.logInfoDB(10, "Approving the 2nd request.", Store, testName);
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewOrderRequireApproval.click();
			driver.findElement(By.xpath("(.//*[@id='" + orderNumber2 + "'])[1]")).click();
			b2bPage.QuotePage_clickApproveButton.click();
			Dailylog.logInfoDB(10, "Order approved successfully", Store, testName);
			Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='globalMessages']/div")).getText().contains("successfully approved"));

			Dailylog.logInfoDB(11, "Approving 1 request.", Store, testName);
			
			driver.manage().deleteAllCookies();
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getAdminId(), testData.B2B.getDefaultPassword());			
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewOrderRequireApproval.click();

			b2bPage.ApprovalDashBoard_SearchFor.click();
			b2bPage.ApprovalDashBoard_OtherPendingOrder.click();
			b2bPage.ApprovalDashBoard_Search.click();
			Dailylog.logInfoDB(11, "Added assert to verify the order 1st and 3rd is present or not.", Store, testName);
			Assert.assertEquals(orderNumber1, driver.findElement(By.xpath(".//*[@id='order_history']//a[contains(.,'" + orderNumber1 + "')]")).getText());
			Assert.assertEquals(orderNumber3, driver.findElement(By.xpath(".//*[@id='order_history']//a[contains(.,'" + orderNumber3 + "')]")).getText());

			b2bPage.ApprovalDashBoard_OrderNumber.clear();
			b2bPage.ApprovalDashBoard_OrderNumber.sendKeys(orderNumber1);
			b2bPage.ApprovalDashBoard_Search.click();
			Dailylog.logInfoDB(12, "Clicked on Edit link on 1st order.", Store, testName);
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("document.getElementsByClassName('order_history_div')[0].scrollLeft=1000");

			WebElement element2 = driver.findElement(By.xpath(".//*[@id='" + orderNumber1 + "'][contains(.,'Edit')]"));
			
			Common.scrollToElement(driver, element2);
			Common.javascriptClick(driver, element2);
			
			
			
			Thread.sleep(8000);
			Dailylog.logInfoDB(12, "veridy cart shows up", Store, testName);
			Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
			b2bPage.cartPage_Quantity.clear();
			b2bPage.cartPage_Quantity.sendKeys("2");
			b2bPage.cartPage_QuantityUpdate.click();
			b2bPage.cartPage_LenovoCheckout.click();
			B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
			Dailylog.logInfoDB(12, "After deleting the product, placed order successfully.", Store, testName);
			Assert.assertEquals("Thank you", b2bPage.ShippingPage_thankyou.getText());

			Dailylog.logInfoDB(13, "Rejecting order 3", Store, testName);
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_viewOrderRequireApproval.click();
			b2bPage.ApprovalDashBoard_SearchFor.click();
			b2bPage.ApprovalDashBoard_OtherPendingOrder.click();
			b2bPage.ApprovalDashBoard_Search.click();
			driver.findElement(By.xpath(".//*[@id='" + orderNumber3 + "']")).click();
			b2bPage.QuotePage_clickRejectButton.click();
			Assert.assertTrue(b2bPage.OrderApprovalDashboard_message.getText().contains("successfully rejected"));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}