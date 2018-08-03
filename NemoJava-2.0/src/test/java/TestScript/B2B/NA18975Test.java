package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA18975Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	String tempEmailAddress;

	public NA18975Test(String store) {
		this.Store = store;
		this.testName = "NA-18975";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18975(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			b2bPage.HomePage_CartIcon.click();
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_RequestQuoteBtn.click();
			Common.checkElementDisplays(driver, b2bPage.cartPage_SubmitQuote, 5);
			b2bPage.cartPage_SubmitQuote.click();
			String quoteID1 = b2bPage.QuoteConfirmPage_QuoteID.getText();
             System.out.println(quoteID1);
			b2bPage.HomePage_CartIcon.click();
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_RequestQuoteBtn.click();
			Common.checkElementDisplays(driver, b2bPage.cartPage_SubmitQuote, 5);
			b2bPage.cartPage_SubmitQuote.click();
			String quoteID2 = b2bPage.QuoteConfirmPage_QuoteID.getText();
		     System.out.println(quoteID2);
			b2bPage.HomePage_CartIcon.click();
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_RequestQuoteBtn.click();
			Common.checkElementDisplays(driver, b2bPage.cartPage_SubmitQuote, 5);
			b2bPage.cartPage_SubmitQuote.click();
			String quoteID3 = b2bPage.QuoteConfirmPage_QuoteID.getText();
			 System.out.println(quoteID3);
			b2bPage.myAccount_link.click();
			b2bPage.myAccountPage_ViewQuotehistory.click();

			// Open quote 1
			Dailylog.logInfo("Verify quote1");
			driver.findElement(By.xpath("//a[contains(@href,'" + quoteID1 + "')]")).click();
			Select approverSelect = new Select(b2bPage.placeOrderPage_selectApprover);
			approverSelect.selectByValue(testData.B2B.getApproverId().toUpperCase());
			b2bPage.placeOrderPage_sendApproval.click();
			
			if(!driver.findElement(By.xpath("//a[contains(@href,'" + quoteID1 + "')]/../../td[3]")).getText().equals("INTERNALPENDING"))
				Assert.fail("Quote status is wrong!");
			// Open quote 2
			Dailylog.logInfo("Verify quote2");
			driver.findElement(By.xpath("//a[contains(@href,'" + quoteID2 + "')]")).click();
			approverSelect = new Select(b2bPage.placeOrderPage_selectApprover);
			approverSelect.selectByValue(testData.B2B.getApproverId2().toUpperCase());
			b2bPage.placeOrderPage_sendApproval.click();
			if(!driver.findElement(By.xpath("//a[contains(@href,'" + quoteID2 + "')]/../../td[3]")).getText().equals("INTERNALPENDING"))
				Assert.fail("Quote status is wrong!");
			// Open quote 3
			Dailylog.logInfo("Verify quote3");
			driver.findElement(By.xpath("//a[contains(@href,'" + quoteID3 + "')]")).click();
			b2bPage.placeOrderPage_sendApproval.click();
			if(!driver.findElement(By.xpath("//a[contains(@href,'" + quoteID3 + "')]/../../td[3]")).getText().equals("INTERNALPENDING"))
				Assert.fail("Quote status is wrong!");
			
			b2bPage.homepage_Signout.click();
			B2BCommon.Login(b2bPage, testData.B2B.getApproverId(), testData.B2B.getDefaultPassword());
			
			b2bPage.myAccount_link.click();
			b2bPage.myAccountPage_viewQuoteRequireApproval.click();
			Dailylog.logInfo("approve quote1");
			Common.javascriptClick(driver, driver.findElement(By.xpath("//input[@id='"+quoteID1+"']")));
			Common.javascriptClick(driver, b2bPage.QuotePage_clickApproveButton);
			
			Select scope = new Select(b2bPage.ApprovalDashBoard_SearchFor);
			scope.selectByValue("ALL");
			b2bPage.quoteApproval_searchQuote.click();
			Dailylog.logInfo("reject quote1");
			Common.javascriptClick(driver, driver.findElement(By.xpath("//input[@id='"+quoteID3+"']")));
			Common.javascriptClick(driver, b2bPage.QuotePage_clickRejectButton);
			Dailylog.logInfo("verify email");
			Thread.sleep(60000);
			if(!EMailCommon.checkQuoteStatus(driver, testData.B2B.getBuilderId(), quoteID1, true))
				Assert.fail("Quote approve email issue!");
			if(!EMailCommon.checkQuoteStatus(driver, testData.B2B.getBuilderId(), quoteID3, false))
				Assert.fail("Quote approve email issue!");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
