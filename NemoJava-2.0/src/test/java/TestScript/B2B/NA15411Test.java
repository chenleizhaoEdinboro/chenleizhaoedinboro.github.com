package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA15411Test extends SuperTestClass {

	String ToMail = "";
	String CCMail = "";
	String BCCMail = "";

	public NA15411Test(String Store) {
		this.Store = Store;
		this.testName = "NA-15411";
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p1", "b2b" })
	public void NA15411(ITestContext ctx) {
		try {
			this.prepareTest();
			HMCPage hmcPage = new HMCPage(driver);
			B2BPage b2bPage = new B2BPage(driver);

			// HMC Configuration
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);

			hmcPage.B2BUnit_siteAttribute.click();
			setupHMCConfig();

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

			String orderNum = B2BCommon.placeOrderFromCartCheckout(driver, b2bPage, testData);
			Dailylog.logInfoDB(1, "Order Number: " + orderNum, this.Store, this.testName);

			// Check Email
			Thread.sleep(60000);
			EMailCommon.checkConfirmationEmail_Yopmail(driver, ToMail, orderNum);
			driver.manage().deleteAllCookies();
			EMailCommon.checkConfirmationEmail_Yopmail(driver, CCMail, orderNum);
			driver.manage().deleteAllCookies();
			EMailCommon.checkConfirmationEmail_Yopmail(driver, BCCMail, orderNum);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void setupHMCConfig() {
		// TO
		WebElement table = driver.findElement(
				By.id("table_Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailTo]]_table"));
		if (!table.getText().contains("PURCHASEORDER")) {
			Common.rightClick(driver, table.findElement(By.xpath(".//tr[1]")));
			driver.findElement(By.id(
					"Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailTo]]_!add_true_label"))
					.click();
			Common.switchToWindow(driver, 1);
			Common.javascriptClick(driver, driver.findElement(By.id("StringDisplay[PURCHASEORDER]_span")));
			driver.findElement(By.id("ModalGenericItemSearchList[CheckoutPaymentType]_use")).click();
			Common.switchToWindow(driver, 0);
		}
		// Update email address
		table = driver.findElement(
				By.id("table_Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailTo]]_table"));
		for (WebElement row : table.findElements(By.tagName("tr"))) {
			if (row.getText().contains("PURCHASEORDER")) {
				ToMail = "To_" + EMailCommon.getRandomEmailAddress().replace("@sharklasers.com", "@yopmail.com");
				row.findElement(By.xpath(".//td[3]/input")).clear();
				row.findElement(By.xpath(".//td[3]/input")).sendKeys(ToMail);
				break;
			}
		}
		
		// CC
		table = driver.findElement(
				By.id("table_Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailCc]]_table"));
		if (!table.getText().contains("PURCHASEORDER")) {
			Common.rightClick(driver, table.findElement(By.xpath(".//tr[1]")));
			driver.findElement(By.id(
					"Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailCc]]_!add_true_label"))
					.click();
			Common.switchToWindow(driver, 1);
			Common.javascriptClick(driver, driver.findElement(By.id("StringDisplay[PURCHASEORDER]_span")));
			driver.findElement(By.id("ModalGenericItemSearchList[CheckoutPaymentType]_use")).click();
			Common.switchToWindow(driver, 0);
		}
		// Update email address
		table = driver.findElement(
				By.id("table_Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailCc]]_table"));
		for (WebElement row : table.findElements(By.tagName("tr"))) {
			if (row.getText().contains("PURCHASEORDER")) {
				CCMail = "CC_" + EMailCommon.getRandomEmailAddress().replace("@sharklasers.com", "@yopmail.com");
				row.findElement(By.xpath(".//td[3]/input")).clear();
				row.findElement(By.xpath(".//td[3]/input")).sendKeys(CCMail);
				break;
			}
		}
		
		// BCC
		table = driver.findElement(
				By.id("table_Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailBcc]]_table"));
		if (!table.getText().contains("PURCHASEORDER")) {
			Common.rightClick(driver, table.findElement(By.xpath(".//tr[1]")));
			driver.findElement(By.id(
					"Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailBcc]]_!add_true_label"))
					.click();
			Common.switchToWindow(driver, 1);
			Common.javascriptClick(driver, driver.findElement(By.id("StringDisplay[PURCHASEORDER]_span")));
			driver.findElement(By.id("ModalGenericItemSearchList[CheckoutPaymentType]_use")).click();
			Common.switchToWindow(driver, 0);
		}
		// Update email address
		table = driver.findElement(
				By.id("table_Content/NemoPaymentTypeToMail[in Content/Attribute[B2BUnit.paymentTypeToMailBcc]]_table"));
		for (WebElement row : table.findElements(By.tagName("tr"))) {
			if (row.getText().contains("PURCHASEORDER")) {
				BCCMail = "BCC_" + EMailCommon.getRandomEmailAddress().replace("@sharklasers.com", "@yopmail.com");
				row.findElement(By.xpath(".//td[3]/input")).clear();
				row.findElement(By.xpath(".//td[3]/input")).sendKeys(BCCMail);
				break;
			}
		}
	}
}
