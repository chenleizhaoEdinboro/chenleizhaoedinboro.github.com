package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA16658Test extends SuperTestClass {

	String date = Common.getDateTimeString();

	public NA16658Test(String Store) {
		this.Store = Store;
		this.testName = "NA-16658";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup", "email", "p1", "b2b"})
	public void NA16658(ITestContext ctx) {
		try {
			this.prepareTest();
			HMCPage hmcPage = new HMCPage(driver);
			B2BPage b2bPage = new B2BPage(driver);

			// HMC Configuration
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);

			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.SiteAttribute_CompleteShipping_Yes.click();
			hmcPage.SiteAttribute_GroupShipping_Yes.click();
			hmcPage.SiteAttribute_LineItemShipping_Yes.click();
			hmcPage.SiteAttribute_EnableCARDShipping_Yes.click();
			hmcPage.SiteAttribute_MaxGroupShipping.clear();
			hmcPage.SiteAttribute_MaxGroupShipping.sendKeys("5");
			hmcPage.SaveButton.click();
			Thread.sleep(10000);

			// Add products to cart
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			Thread.sleep(2000);

			driver.get(driver.getCurrentUrl() + "cart");
			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN1());
			b2bPage.cartPage_addButton.click();
			Thread.sleep(3000);
			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN2());
			b2bPage.cartPage_addButton.click();
			Thread.sleep(3000);
			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultMTMPN3());
			b2bPage.cartPage_addButton.click();
			Thread.sleep(3000);

			Common.javascriptClick(driver, b2bPage.cartPage_LenovoCheckout);
			B2BCommon.fillB2BShippingInfo(driver, b2bPage, this.Store, "FirstNameJohn", "LastNameSnow",
					testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
					testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());

			String email = EMailCommon.getRandomEmailAddress();
			driver.findElement(By.xpath(".//*[@id='email']")).clear();
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(email);
			Common.javascriptClick(driver, b2bPage.shippingPage_GroupShipping);
			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			if(Common.checkElementDisplays(driver, b2bPage.shippingPage_validateFromOk, 3)){
				b2bPage.shippingPage_validateFromOk.click();
			}
			// Add Shipping info
			List<WebElement> shipRows = driver
					.findElements(By.xpath("//div[contains(@id,'shipping-row-') and @class='row-group']"));
			Select select;
			int index = 0;
			String[][] Data = new String[3][2]; // Date, Address
			for (WebElement row : shipRows) {
				index += 1;
				// Group
				select = new Select(row.findElement(By.tagName("select")));
				select.selectByVisibleText(Integer.toString(index));

				// Shipping date
				row.findElement(By.xpath((".//input[contains(@class,'form-control shipping-date hasDatepicker')]")))
						.click();
				Thread.sleep(3000);
				Common.javascriptClick(driver,
						driver.findElement(By.xpath("//a[contains(@class,'ui-datepicker-next')]"))); // go to next month
				Thread.sleep(3000);
				Data[index - 1][0] = (Integer.parseInt(driver.findElements(By.xpath("//td[@data-handler='selectDay']"))
						.get(index - 1).getAttribute("data-month")) + 1) + "/"
						+ driver.findElements(By.xpath("//td[@data-handler='selectDay']")).get(index - 1).getText()
						+ "/" + driver.findElements(By.xpath("//td[@data-handler='selectDay']")).get(index - 1)
								.getAttribute("data-year");
				Common.javascriptClick(driver,
						driver.findElements(By.xpath("//td[@data-handler='selectDay']")).get(index - 1));

				// Address
				Common.javascriptClick(driver,
						row.findElement(By.xpath(".//button[contains(@class,'shipping-address-btn')]")));
				Thread.sleep(5000);

				driver.switchTo().frame(b2bPage.shippingPage_GroupShippingIframe);
				driver.findElement(By.xpath("//a[@class='textLink checkout-shippingAddress-editLink']")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("line1")).sendKeys(" # " + Integer.toString(index));
				Thread.sleep(2000);
				Data[index - 1][1] = driver.findElement(By.id("line1")).getAttribute("value");
				Common.javascriptClick(driver, driver.findElement(By.id("selectThisAddress")));
				driver.switchTo().defaultContent();
				if(Common.checkElementDisplays(driver, By.id("mutil_validateFrom_ok"), 3)){
					//Data[index - 1][1] = driver.findElement(By.xpath("//div[@id='address_errorInfo']/div[2]/div[2]")).getText();
					Common.javascriptClick(driver, driver.findElement(By.id("mutil_validateFrom_ok")));
				}
				Thread.sleep(2000);
			}

			Common.javascriptClick(driver, b2bPage.shippingPage_GroupShippingContinueButton);
			Common.sleep(10000);
			if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 10)) {
				b2bPage.shippingPage_validateFromOk.click();
			}
			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);

			B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
			System.out.println("Go to Order page!");

			Thread.sleep(5000);
			if (Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)) {
				Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
				b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
			}
			Common.javascriptClick(driver, b2bPage.placeOrderPage_Terms);
			Common.javascriptClick(driver, b2bPage.placeOrderPage_PlaceOrder);
			String orderNum = b2bPage.placeOrderPage_OrderNumber.getText();
			Dailylog.logInfoDB(1, "Order Number: " + orderNum, this.Store, this.testName);
			Dailylog.logInfoDB(1, "Order email: " + email, this.Store, this.testName);
			// Check Email
			MailPage mailPage = new MailPage(driver);
			EMailCommon.checkConfirmationEmail(driver, email, orderNum);

			driver.findElement(By.xpath("//td[@class = 'td3']")).click();

			// Verify ship addresses
			if (!mailPage.GroupAddress1.getText().contains(Data[0][1]))
				Assert.fail("Group 1 address wrong! expected: " + Data[0][1] + "; Actual: "
						+ mailPage.GroupAddress1.getText());
			if (!mailPage.GroupAddress2.getText().contains(Data[1][1]))
				Assert.fail("Group 2 address wrong! expected: " + Data[1][1] + "; Actual: "
						+ mailPage.GroupAddress2.getText());
			if (!mailPage.GroupAddress3.getText().contains(Data[2][1]))
				Assert.fail("Group 3 address wrong! expected: " + Data[2][1] + "; Actual: "
						+ mailPage.GroupAddress3.getText());

			List<WebElement> dates = driver
					.findElements(By.xpath("//span[contains(text(),'Requested Arrival Date')]/.."));
			for (int i = 0; i < dates.size(); i++) {
				if (!dates.get(i).getText().contains(strToDate(Data[i][0])))
					Assert.fail("Shipping Date 1 is wrong! expected: " + strToDate(Data[i][0]) + "; Actual: "
							+ dates.get(i).getText());
			}

			if (!mailPage.ShippingAddress.getText().equals(""))
				Assert.fail("Final shipping address is not empty!");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public String strToDate(String strDate) {
		String[] list = strDate.split("/");
		if (list[0].length() < 2)
			list[0] = "0" + list[0];
		if (list[1].length() < 2)
			list[1] = "0" + list[1];
		return list[0] + "/" + list[1] + "/" + list[2];
	}
}
