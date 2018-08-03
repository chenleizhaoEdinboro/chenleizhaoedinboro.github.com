package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class NA25921Test extends SuperTestClass {

	public String laptopPageURL;
	private String partNo;
	private String B2CPartSalesURL;
	public MailPage mailPage;
	public String emailCart;
	
	
	public NA25921Test(String store, String partsalesNumber, String myAccountURL) {
		this.Store = store;
		this.partNo = partsalesNumber;
		this.B2CPartSalesURL = myAccountURL;
		this.testName = "NA-25921";

	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p1", "b2c" })
	public void NA25921(ITestContext ctx) {
		try {
			this.prepareTest();
			mailPage = new MailPage(driver);
						
			emailCart = "test25921@sharklasers.com";
			B2CPage b2cPage = new B2CPage(driver);
			
			PartSalesPage PSPage = new PartSalesPage(driver);
			driver.get(testData.B2C.getPartSalesUrl());
			PSPage.partSales_SelectCountry.click();
			Common.waitElementClickable(driver,
					driver.findElement(By.xpath("(//a[@data-code='" + this.Store.toLowerCase() + "'])[1]")), 15);
			driver.findElement(By.xpath("(//a[@data-code='" + this.Store.toLowerCase() + "'])[1]")).click();
			Common.waitElementClickable(driver, PSPage.cartIcon, 20);
			PSPage.cartIcon.click();
			Common.waitElementClickable(driver, PSPage.LenovoIDSignin, 20);
			PSPage.LenovoIDSignin.click();

			partsalesLogin("testps@sharklasers.com", "1q2w3e4r", PSPage);
			PSPage.partNumber.clear();
			PSPage.partNumber.sendKeys(partNo);
			PSPage.partLookUp.click();
			
			PSPage.addToCart.click();
			PSPage.viewMyCart.click();
			
		
			
			Common.waitElementVisible(driver, PSPage.partSalesCheckOut);
			PSPage.partSalesCheckOut.click();

			B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), emailCart,
					testData.B2C.getConsumerTaxNumber());
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			Common.waitElementDisappear(driver, b2cPage.OrderSummary_AcceptTermsCheckBox, 15);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			String orderNO = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			EMailCommon.checkConfirmationEmail(driver, emailCart, orderNO);
			
			driver.get(testData.B2C.getPartSalesUrl());
			PSPage.partSales_SelectCountry.click();
			Common.waitElementClickable(driver,
					driver.findElement(By.xpath("(//a[@data-code='" + this.Store.toLowerCase() + "'])[1]")), 15);
			driver.findElement(By.xpath("(//a[@data-code='" + this.Store.toLowerCase() + "'])[1]")).click();
			Common.waitElementClickable(driver, PSPage.cartIcon, 20);
			PSPage.cartIcon.click();
			Common.waitElementClickable(driver, PSPage.LenovoIDSignin, 20);
			PSPage.LenovoIDSignin.click();

			partsalesLogin("testbp@sharklasers.com", "1q2w3e4r", PSPage);
			PSPage.partNumber.clear();
			PSPage.partNumber.sendKeys(partNo);
			PSPage.partLookUp.click();
			String partSalesPriceLabel = GetPriceValue(PSPage.partSalesPrice.getText()) + "";
			PSPage.addToCart.click();
			PSPage.viewMyCart.click();
			Common.waitElementVisible(driver, PSPage.partSalesCheckOut);
			String CartSubLabel;
			String CartTotalLabel;
			float CartSubPrice;
			float CartTotalPrice;
			float partSalesPrice	;				
			CartSubLabel = b2cPage.Cart_PriceSubLabel.getText();
			CartTotalLabel = b2cPage.Cart_PriceTotalLabel.getText();
			CartSubPrice=GetPriceValue(b2cPage.Cart_PriceSubTotal.getText());
			CartTotalPrice=GetPriceValue(b2cPage.Cart_PriceTotal.getText());
			partSalesPrice=GetPriceValue(partSalesPriceLabel);
			
			PSPage.partSalesCheckOut.click();

			B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), emailCart,
					testData.B2C.getConsumerTaxNumber());
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			Common.waitElementDisappear(driver, b2cPage.OrderSummary_AcceptTermsCheckBox, 15);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			orderNO = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, emailCart);


			checkConfirmationEmail(emailCart, orderNO, CartSubPrice, partSalesPrice, CartTotalPrice, 1);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	
	public void partsalesLogin(String userName, String password, PartSalesPage PSPage) {
		PSPage.LenovoID.clear();
		PSPage.LenovoID.sendKeys(userName);
		PSPage.password.clear();
		PSPage.password.sendKeys(password);
		PSPage.signinButton.click();

	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	
	public void checkConfirmationEmail(String mailId, String orderNum, float SubTotalPrice, float productPrices,
			float TotalPrice, int mailCount) {
		System.out.println("Checking email..." + mailId);
		EMailCommon.goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")) && mailCount != 1) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Common.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")))
					break;
			}
		}
		System.out.println(
				"Order Email: " + Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]")));

		if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'" + orderNum + "')]"))) {
			driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).click();
			// Check order number
			Common.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + orderNum + "')]")).isDisplayed(),
					"Order in mail: " + orderNum);

			// Check EmailSubTotalPrice
			WebElement EmailSubTotalPrice = driver
					.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[1]/td"));
			Assert.assertEquals(GetPriceValue(EmailSubTotalPrice.getText()), SubTotalPrice,
					"SubTotalPrice price in mailis not right in mail");
					// System.out.println(GetPriceValue(EmailSubTotalPrice.getText())+"--"+SubTotalPrice+"...............");

			// Check Product prices
			//TODO
//			WebElement EmailTaxPrice = driver
//					.findElement(By.xpath("//td[contains(text(),'Tax')]/../../../../../td[3]//tbody/tr[2]/td"));
//			Assert.assertEquals(GetPriceValue(EmailTaxPrice.getText()), TaxPrice,
//					"TaxPrice price in mailis not right in mail");

			// Check total price
			WebElement EmailTotalPrice = driver
					.findElement(By.xpath("//td[contains(text(),'Sub')]/../../../../../td[3]//tbody/tr[last()]/td"));
			Assert.assertEquals(GetPriceValue(EmailTotalPrice.getText()), TotalPrice,
					"TotalPrice is not right in mail");
		} else {
			setManualValidateLog("Order Number: " + orderNum);
			setManualValidateLog("Mailbox: " + mailId);
			setManualValidateLog("SubTotalPrice: " + SubTotalPrice);
			setManualValidateLog("productPrices: " + productPrices);
			setManualValidateLog("TotalPrice: " + TotalPrice);
		}

	}

}