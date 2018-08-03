package TestScript.B2C;

import org.apache.log4j.DailyRollingFileAppender;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class COMM338Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	MailPage mailPage;
	String productNo;
	String totalPrice;
	String mailBoxName;
	String loginUser;
	String defaultProductNo;
	
	public COMM338Test(String store) {
		this.Store = store;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM338(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String productNum = "80Y70064US";
			//hmc configuration manually
			
			//step~9
			Dailylog.logInfoDB(9,"Go to website", Store,testName);
			
			//go to website
//			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			driver.get("https://tun-c-hybris.lenovo.com/in/en/cart");
			//add item to cart
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			B2CCommon.addPartNumberToCart(b2cPage, productNum);

			//step~10
			Dailylog.logInfoDB(10,"Proceed to payment page", Store,testName);
			b2cPage.lenovo_checkout.click();
			
			//step~11:Choose COD
			Dailylog.logInfoDB(11,"Choose COD", Store,testName);

			Common.sleep(2000);
			b2cPage.StartCheckOut.click();
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			Common.mouseHover(driver, b2cPage.Shipping_ContinueButton);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Common.sleep(2000);
			b2cPage.payment_COD.click();
			
			//step~13:
			Dailylog.logInfoDB(13,"'Accept' button grays out", Store,testName);
			Common.sleep(2000);
			Assert.assertEquals(b2cPage.payment_CODAccept.getAttribute("disabled"), "true");
			
			//step~14:Click Cancel
			Dailylog.logInfoDB(14,"Go to website", Store,testName);
			b2cPage.payment_CODCancel.click();
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.payment_MethodList1.isSelected());
			
			//step~12&15
			Dailylog.logInfoDB(15,"Go to website", Store,testName);
			
			Common.sleep(2000);
			b2cPage.payment_COD.click();
			Common.sleep(2000);
			b2cPage.payment_CODCheckbox.click();
			b2cPage.payment_CODAccept.click();
			Common.javascriptClick(driver, b2cPage.ContinueforPayment);
			
			//step~16
			Dailylog.logInfoDB(16,"Go to website", Store,testName);
			b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("orderConfirmation"));
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
	

