package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA26453Test extends SuperTestClass {

	public NA26453Test(String store) {
		this.Store = store;
		this.testName = "NA-26453";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","product",  "p2", "b2c"})
	public void NA26453(ITestContext ctx) {
		try {
			this.prepareTest();
			B2CPage b2cPage = new B2CPage(driver);
			HMCPage hmcPage = new HMCPage(driver);

			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Tablets);

			String PN = "";
			int index = 0;
			List<WebElement> productList;
			while(PN.equals("") && index < 10)
			{
			// Search for PN like xxxx-xxx, and in stock
			productList = driver
					.findElements(By.xpath(".//*[@class='facetedResults-item only-allow-small-pricingSummary']"));
			for (WebElement product : productList) {
				if (product.findElement(By.xpath(".//div[@class='facetedResults-feature-list']/dl[1]/dd[1]")).getText()
						.contains("-")
						&& (product.findElement(By.xpath(".//span[@class='rci-msg']")).getText().contains("n stock")||
								product.findElement(By.xpath(".//span[@class='rci-msg']")).getText().contains("n Stock"))) {
					
					System.out.println(product.findElement(By.xpath(".//div[@class='facetedResults-feature-list']/dl[1]/dd[1]")).getText()
							.contains("-")
							&& (product.findElement(By.xpath(".//span[@class='rci-msg']")).getText().contains("n stock")||
									product.findElement(By.xpath(".//span[@class='rci-msg']")).getText().contains("n Stock")));
					PN = product.findElement(By.xpath(".//div[@class='facetedResults-feature-list']/dl[1]/dd[1]"))
							.getText();
					break;
				}
			}
			index++;
			if(Common.checkElementDisplays(driver, By.xpath(".//a[@rel='next' and contains(@href,'pageSize=')]"), 3)){
				Common.javascriptClick(driver, driver.findElement(By.xpath(".//a[@rel='next' and contains(@href,'pageSize=')]")));
			}else{
				break;
			}
			}
			if(PN.equals("")){
				this.setNoDataLog("There is no stock product with part number contains -");
			}
			// Quick order
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.addPartNumberToCart(b2cPage, PN);

			// Amazon has special process
			b2cPage.Cart_CheckoutButton.click();
			Thread.sleep(2000);

			// Click on guest checkout button if exists
			if (Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}

			// Fill default shipping address
			if (Common.checkElementDisplays(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);

			// Payment
			Payment.payAndContinue(b2cPage, PaymentType.Visa_B2C, testData);

			// Place Order
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);

			// Get Order number
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			// Verify HMC value
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);

			// Validate in xml
			if (!hmcPage.Orders_OrderXML.getText()
					.contains("<text_line>" + PN.split("-")[1] + "</text_line>"))
				Assert.fail("Product product id is wrong!");

			Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}
