package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE889Test extends SuperTestClass {
	String unit;
	String b2bLoginUrl;
	String b2bHomeUrl;
	String Subscription;
	HMCPage hmcPage;
	B2BPage b2bPage;
	String date = Common.getDateTimeString();

	public BROWSE889Test(String Store, String Subscription) {
		this.Store = Store;
		this.testName = "BROWSE-889";
		this.Subscription = Subscription;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "browsegroup","product", "p2", "b2b" })
	public void BROWSE889(ITestContext ctx) {
		try {
			//should delete  54~56
			//String testUrl = "https://pre-c-hybris.lenovo.com/le/1213071828/us/en/1213071828";
			

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			By byLocator11 = By
					.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			By byLocator7 = By.id("approveId");
			driver.get(testData.B2B.getLoginUrl());
			String builderAcct =testData.B2B.getBuilderId();
			String approverAcct =testData.B2B.getApproverId();
			
			/*driver.get(testUrl);
			String builderAcct ="rrbuilder@yopmail.com";
			String approverAcct ="rrapprover@yopmail.com";*/
		
			B2BCommon.Login(b2bPage, builderAcct, testData.B2B.getDefaultPassword());			
			// add rr product to cart
			b2bPage.HomePage_CartIcon.click();
			//B2BCommon.clearTheCart(driver, b2bPage);
			B2BCommon.addProduct(driver, b2bPage, Subscription);
			//request one quote
			b2bPage.cartPage_RequestQuoteBtn.click();
			try {
				Thread.sleep(5000);
				b2bPage.cartPage_RepIDBox.sendKeys("1234567890");
			} catch (ElementNotVisibleException e) {
				driver.navigate().refresh();
				Thread.sleep(5000);
				b2bPage.cartPage_RequestQuoteBtn.click();
				b2bPage.cartPage_RepIDBox.sendKeys("1234567890");
			}
			b2bPage.cartPage_SubmitQuote.click();
			//1. check the quote confirm page billcycle
			Assert.assertTrue(b2bPage.QuoteConfirm_FirstItemPrice.getText().contains("/"));
			Assert.assertTrue(b2bPage.QuoteConfirm_FirstTotalPrice.getText().contains("/"));
			
			String quoteNum = b2bPage.cartPage_QuoteNumber.getText();
			Dailylog.logInfoDB(5, "Quote number is: "
					+ quoteNum, Store, testName);
			Thread.sleep(5000);
			//send quote to approver		
			b2bPage.homepage_MyAccount.click();
			b2bPage.myAccountPage_ViewQuotehistory.click();
			driver.findElement(By.linkText(quoteNum)).click();
			//2. check the quote history page billcycle
			Assert.assertTrue(b2bPage.SavedCartPage_FirstItemPrice.getText().contains("/"),
					" check billing cycle on cart history page line item");
			Assert.assertTrue(b2bPage.SavedCartPage_FirstTotalPrice.getText().contains("/"),
					" check billing cycle on cart history page sub total");
			
			Select sel = new Select(driver.findElement(byLocator7));
			sel.selectByValue(approverAcct.toUpperCase());
			b2bPage.placeOrderPage_sendApproval.click();		
			b2bPage.homepage_Signout.click();
			//login approver acct and approve this quote			
			B2BCommon.Login(b2bPage, approverAcct, "1q2w3e4r");
			b2bPage.homepage_MyAccount.click();
			Thread.sleep(5000);
			b2bPage.myAccountPage_viewQuoteRequireApproval.click();
			Thread.sleep(10000);
			driver.findElement(By.xpath("//a[text()="+quoteNum+"]")).click();
			Common.switchToWindow(driver, 1);
			//3. check the quote approver dashboard page billcycle
			Assert.assertTrue(b2bPage.SavedCartPage_FirstItemPrice.getText().contains("/"),
					" check billing cycle on cart history page line item");
			Assert.assertTrue(b2bPage.SavedCartPage_FirstTotalPrice.getText().contains("/"),
					" check billing cycle on cart history page sub total");
			
			driver.close();
			Common.switchToWindow(driver, 0);
			try {
				driver.findElement(By.xpath(quoteNum)).click();
			} catch (Exception e) {
				b2bPage.ApprovalDashBoard_Search.click();
				driver.findElement(By.id(quoteNum)).click();
			}
			b2bPage.QuotePage_clickApproveButton.click();
			Dailylog.logInfoDB(8, quoteNum + " has been approved", Store, testName);
			b2bPage.homepage_Signout.click();		
			//login builder again and send order to approver
			B2BCommon.Login(b2bPage, builderAcct, "1q2w3e4r");
			b2bPage.homepage_MyAccount.click();
			Thread.sleep(8000);
			/*
			 * if(!Common.isElementExsit(driver,byLocator12)){
			 * Thread.sleep(3000); } driver.findElement(byLocator12).click();
			 */
			b2bPage.myAccountPage_ViewQuotehistory.click();
			
			driver.findElement(By.linkText(quoteNum)).click();
			b2bPage.cartPage_ConvertToOrderBtn.click();
			Dailylog.logInfoDB(9, "Starting covert quote to order", Store,
					testName);
			Thread.sleep(5000);
			B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
			
			//4. check the order confirm page billcycle
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstItemPrice.getText().contains("/"));
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstTotalPrice.getText().contains("/"));
			b2bPage.homepage_MyAccount.click();
			Thread.sleep(5000);
			b2bPage.myAccount_viewOrderHistory.click();	
			driver.findElement(By.xpath("//a[text()="+quoteNum+"]")).click();
			//5. check the order history page billcycle
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstItemPrice.getText().contains("/"));
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstTotalPrice.getText().contains("/"));
			
			//login approve to approve this order
			b2bPage.homepage_Signout.click();
			B2BCommon.Login(b2bPage, approverAcct, "1q2w3e4r");
			b2bPage.homepage_MyAccount.click();
			Thread.sleep(5000);
			b2bPage.myAccountPage_viewOrderRequireApproval.click();	
			b2bPage.OrderWaitingApprove_OthersPending.click();
			b2bPage.ApprovalDashBoard_Search.click();
			Thread.sleep(10000);
			driver.findElement(By.xpath("//a[text()="+quoteNum+"]")).click();
			//6. check the order approver dashboard page billcycle
			Common.switchToWindow(driver, 1);
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstItemPrice.getText().contains("/"),
					" check billing cycle on cart history page line item");
			Assert.assertTrue(b2bPage.ThanksYouPage_FirstTotalPrice.getText().contains("/"),
					" check billing cycle on cart history page sub total");
			
			driver.close();
			Common.switchToWindow(driver, 0);	
			try {
				driver.findElement(By.id(quoteNum)).click();
			} catch (Exception e) {
				b2bPage.ApprovalDashBoard_Search.click();
				driver.findElement(By.id(quoteNum)).click();
			}
			b2bPage.QuotePage_clickApproveButton.click();
			Thread.sleep(5000);
			b2bPage.homepage_Signout.click();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
