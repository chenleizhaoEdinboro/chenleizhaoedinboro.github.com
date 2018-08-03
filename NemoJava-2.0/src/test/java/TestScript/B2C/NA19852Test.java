package TestScript.B2C;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19852Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String b2cProductUrl;
	public String ProductID;
	private String b2cUnitID;
	private String BRAdress = "SES-Av. das Nações";
	private String BRBuilding = "Quadra 813";
	private String BRCompletement = "Lote 51";
	private String BRRegion = "Brasília-Distrito";
	private String BRCity = "Federal";
	private String BRState = "Brasilia";

	public NA19852Test(String Store, String b2cUnit) {
		this.Store = Store;
		this.b2cUnitID = b2cUnit;
		this.testName = "NA-19852";
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "e2e", "b2c", "compatibility" })
	public void NA19852(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			By BankDeposit = By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']");
			Dailylog.logInfo("Complete HMC setup is complete");
			// Common.NavigateToUrl(driver,
			// Browser,testData.HMC.getHomePageUrl());
			// Thread.sleep(3000);
			// HMCCommon.Login(hmcPage, testData);
			// hmcPage.Home_B2CCommercelink.click();
			// hmcPage.Home_B2CUnitLink.click();
			// hmcPage.B2CUnit_IDTextBox.clear();
			// hmcPage.B2CUnit_IDTextBox.sendKeys(b2cUnitID);
			// hmcPage.B2CUnit_SearchButton.click();
			// Common.doubleClick(driver,
			// hmcPage.B2CUnit_FirstSearchResultItem);
			//
			// Common.waitElementClickable(driver,
			// hmcPage.B2CUnit_SiteAttributeTab, 30);
			// hmcPage.B2CUnit_SiteAttributeTab.click();
			// hmcPage.b2cUnit_switchAddressYes.click();
			// hmcPage.Types_SaveBtn.click();
			// Thread.sleep(5000);
			Dailylog.logInfo("login the BR store and add product into cart");
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), "1q2w3e4r");
			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper"))
				B2CCommon.handleGateKeeper(b2cPage, testData);

			String url = driver.getCurrentUrl();
			// Dailylog.logInfo(" get the cart url and empty the cart");
			// String cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			// Common.NavigateToUrl(cartUrl);
			// if (Common.isElementExist(driver,
			// By.xpath("//*[@id='emptyCartItemsForm']/a"))) {
			// Dailylog.logInfo("NEED TO EMPTY CART");
			// WebElement EmptyCart = driver.findElement(By
			// .xpath("//*[@id='emptyCartItemsForm']/a"));
			// Common.javascriptClick(driver, EmptyCart);
			//
			// if (Common
			// .isElementExist(
			// driver,
			// By.xpath("//input[@value='Yes, Delete Cart' or @value='Sim,
			// deletar carrinho']"))) {
			// }
			//
			// driver.findElement(
			// By.xpath("//input[@value='Yes, Delete Cart' or @value='Sim,
			// deletar carrinho']"))
			// .click();
			//
			// }
			//
			// if (Common.isElementExist(driver,
			// By.xpath("//img[@alt='Empty cart']"))) {
			// driver.findElement(By.xpath("//img[@alt='Empty cart']"))
			// .click();
			// Thread.sleep(3000);
			//
			// driver.findElement(
			// By.xpath("//input[@value='Yes, Delete Cart' or @value='Sim,
			// deletar carrinho']"))
			// .click();
			//
			// Thread.sleep(3000);
			// }
			//
			// Thread.sleep(5000);
			// Common.NavigateToUrl(url + "/p/" +
			// testData.B2C.getDefaultMTMPN());
			//
			// B2CCommon.handleGateKeeper(b2cPage, testData);
			// Dailylog.logInfo("Url is :::::" + driver.getCurrentUrl());
			// Thread.sleep(8000);
			// driver.findElement(By.xpath("(//*[@id='view-customize'])[1]"))
			// .click();
			//
			// Thread.sleep(10000);
			// driver.findElement(By.xpath("(//*[@id='addToCartButtonTop'])[1]"))
			// .click();
			//
			// while (Common
			// .isElementExist(
			// driver,
			// By.xpath("(//*[@id='product-builder-form']/descendant::div/button)[3]")))
			// {
			// driver.findElement(
			// By.xpath("(//*[@id='product-builder-form']/descendant::div/button)[3]"))
			// .click();
			// Dailylog.logInfo("Countinue button clicked!!!");
			// }
			//
			// Thread.sleep(3000);
			//

			String cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			Common.NavigateToUrl(driver, Browser, cartUrl);
			B2CCommon.clearTheCart(driver, b2cPage, testData);

			Common.sleep(2000);

			// =========== Step:3 Add one product ===========//
			// B2CCommon.addPartNumberToCart(b2cPage, "81CC0002BR");
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			Common.sleep(1000);

			Common.javascriptClick(driver, b2cPage.Cart_AddButton);
			// B2CCommon.addPartNumberToCart(b2cPage,
			// testData.B2C.getDefaultMTMPN());
			Thread.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("cart"));
			Assert.assertTrue(
					Common.isElementExist(driver, By.xpath("//*[@id='mainContent']//div[@class='cart-item']")));

			Common.scrollAndClick(driver, b2cPage.Cart_CheckoutButton);
			Thread.sleep(2000);
			Dailylog.logInfo("2, click Lenovo checkout on shopping cart page");
			/*
			 * while(Common.isElementExist(driver,
			 * By.xpath("//*[@id='lenovo-checkout-sold-out']"))){
			 * Common.javascriptClick(driver, driver.findElement(By
			 * .xpath("//*[@id='lenovo-checkout-sold-out']")));
			 * 
			 * }
			 */

			Common.sleep(2000);

			Assert.assertTrue(driver.getCurrentUrl().toString().contains("address"));

			/*
			 * boolean judgeFirstName = driver.findElement(
			 * By.xpath("//*[@id='firstName']")).isEnabled();
			 * Assert.assertTrue(judgeFirstName); Dailylog.logInfo(
			 * "judgeFirstName is required :" + judgeFirstName); boolean
			 * judgeLastName = driver.findElement(
			 * By.xpath("//*[@id='lastName']")).isEnabled();
			 * Assert.assertTrue(judgeLastName); Dailylog.logInfo(
			 * "judgeLastName is required :" + judgeLastName);
			 * 
			 * boolean judgeLine1 = driver.findElement(
			 * By.xpath("//*[@id='line1']")).isEnabled();
			 * Assert.assertTrue(judgeLine1); Dailylog.logInfo(
			 * "judgeLine1 is required :" + judgeLine1);
			 * 
			 * boolean judgeLine2 = driver.findElement(
			 * By.xpath("//*[@id='line2']")).isEnabled();
			 * Assert.assertTrue(judgeLine2); Dailylog.logInfo(
			 * "judgeLine2 is required :" + judgeLine2);
			 * 
			 * 
			 * 
			 * boolean judgeTownCity = driver.findElement(
			 * By.xpath("//*[@id='townCity']")).isEnabled();
			 * Assert.assertTrue(judgeTownCity); Dailylog.logInfo(
			 * "judgeTownCity is required :" + judgeTownCity);
			 * 
			 * boolean judgePhone = driver.findElement(
			 * By.xpath("//*[@id='phone']")).isEnabled();
			 * Assert.assertTrue(judgePhone); Dailylog.logInfo(
			 * "judgePhone is required :" + judgePhone);
			 */

			try {
				b2cPage.Shipping_editAddress.click();
			} catch (Exception e) {
				Dailylog.logInfo("Edit is not present");
			}
			Thread.sleep(3000);
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys("test");
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys("test");
			/*
			 * driver.findElement(By.xpath(".//*[@id='consumerTaxNumber']"))
			 * .clear();
			 * driver.findElement(By.xpath(".//*[@id='consumerTaxNumber']"))
			 * .sendKeys("12601584");
			 */

			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(BRAdress);
			b2cPage.Shipping_building.clear();
			b2cPage.Shipping_building.sendKeys(BRBuilding);
			b2cPage.Shipping_AddressLine2TextBox.clear();
			b2cPage.Shipping_AddressLine2TextBox.sendKeys(BRCompletement);
			b2cPage.Shipping_district.clear();
			b2cPage.Shipping_district.sendKeys(BRRegion);
			b2cPage.Shipping_CityTextBox.clear();
			b2cPage.Shipping_CityTextBox.sendKeys(BRCity);

			Select select_state = new Select(driver.findElement(By.xpath("//*[@id='state']")));
			select_state.selectByVisibleText(BRState);

			b2cPage.Mobile.clear();
			b2cPage.Mobile.sendKeys("12312412414");
			b2cPage.Shipping_consumerTaxNumber.clear();
			b2cPage.Shipping_consumerTaxNumber.sendKeys("84511773521");

			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys("lixe1@lenovo.com");
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Dailylog.logInfo("Verify that zip codeis mandotory");
			Assert.assertTrue(
					driver.findElement(By.xpath(".//*[contains(text(),'Esta informação é inválida para este campo')]"))
							.isDisplayed());
			b2cPage.Shipping_PostCodeTextBox.clear();
			b2cPage.Shipping_PostCodeTextBox.sendKeys("70443900");

			b2cPage.Shipping_consumerTaxNumber.clear();
			b2cPage.Shipping_consumerTaxNumber.sendKeys("99999999999");
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Dailylog.logInfo("verify that invalid cpf code is not allowed");
			if (Common.isElementExist(driver, By.xpath("//div[contains(text(),'Editar endereço de entrega')]"))) {
				driver.findElement(By.xpath("//div[contains(text(),'Editar endereço de entrega')]")).click();
			}
			Assert.assertTrue(driver.findElement(By.xpath(".//*[contains(text(),'CPF inválido')]")).isDisplayed());
			b2cPage.Shipping_consumerTaxNumber.clear();
			b2cPage.Shipping_consumerTaxNumber.sendKeys("84511773521");
			b2cPage.Mobile.clear();
			b2cPage.Mobile.sendKeys("12345678901");
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(3000);
			Common.waitElementClickable(driver, driver.findElement(By.xpath("//span[contains(text(),'Enviar para')]")),
					25);

			driver.findElement(By.xpath("//span[contains(text(),'Enviar para')]")).click();

			// Common.javascriptClick(driver,
			// driver.findElement(By.xpath(".//*[contains(text(),'Editar
			// endereço de entrega')]")));
			Thread.sleep(3000);
			if (Common.isElementExist(driver, By.cssSelector(".textLink.checkout-shippingAddress-editLink"))) {
				b2cPage.Shipping_editAddress.click();
			}

			/*
			 * could be abug / temporarily removed assert
			 * b2cPage.Mobile.getAttribute("value").toString() .equals(
			 * "(12) 34567-8901");
			 */
			Dailylog.logInfo("verify that summary page has cpf code displayed");
			assert b2cPage.Shipping_consumerTaxNumber.getAttribute("value").toString().equals("845.117.735-21");

			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);

			// on the payment page , check the checkbox
			// "SAME AS DELIVERY ADDRESS" and select Direct Deposit payment then
			// continue;
			Thread.sleep(2000);
			if (Common.isElementExist(driver, By.xpath("//*[@id='useDeliveryAddress']"))
					&& !driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).isSelected()
					&& Common.isEditable(driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")))) {
				driver.findElement(By.xpath("//*[@id='useDeliveryAddress']")).clear();
			}
			if (Common.isElementExist(driver, BankDeposit)) {
				Common.javascriptClick(driver,
						driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));

			} else {
				Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='PaymentTypeSelection_Wire']")));
				Dailylog.logInfo("bank deposit is not present");

			}

			if (Common.isElementExist(driver, By.xpath("//span[@class='continue_to_review']"))) {
				WebElement paymentContinue = driver.findElement(By.xpath("//span[@class='continue_to_review']"));
				Common.javascriptClick(driver, paymentContinue);
			} else {
				WebElement paymentContinue = driver.findElement(By.xpath("//*[@id='add-payment-method-continue']"));
				Common.javascriptClick(driver, paymentContinue);
			}

			Thread.sleep(10000);

			String url_sum = driver.getCurrentUrl().toString();
			Dailylog.logInfo("url_sum is :" + url_sum);

			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("summary"));
			assert driver.findElement(By.xpath("(.//*[@class='cardHoldName'])[1]")).getText().equals("845.117.735-21");
			assert driver.findElement(By.xpath("(.//*[@class='cardHoldName'])[3]")).getText().equals("845.117.735-21");

			// assert
			// driver.findElement(By.xpath(".//*[@class='deliverItemsOthers']")).getText()
			// .contains("12.601.584/0001-30");
			// assert
			// driver.findElement(By.xpath(".//*[@class='billingItemsOthers']")).getText()
			// .contains("12.601.584/0001-30");

			// 8, check the T&C and click on "place order"
			if (Common.isElementExist(driver, By.xpath("//label[contains(@class,'redesign-term-check')]"))) {
				driver.findElement(By.xpath("//label[contains(@class,'redesign-term-check')]")).click();
			} else {
				driver.findElement(By.xpath("//*[@id='Terms1']")).click();
			}

			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[@id='orderSummaryReview_placeOrder']")).click();

			boolean thankyou = driver
					.findElement(By.xpath("//*[@id='configarea_scroller']//h1[contains(.,'Obrigado')]")).isDisplayed();
			Dailylog.logInfo("thankyou is displayed :" + thankyou);
			Assert.assertTrue(thankyou);
			// 9, check order in hmc
			String orderNum1 = "";
			if (Common.isElementExist(driver,
					By.xpath("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)"))) {
				orderNum1 = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			} else {
				orderNum1 = driver
						.findElement(By
								.xpath("(//*[contains(text(),'Número de pedido:') or contains(text(),'Número do Pedido')]/../*)[2]"))
						.getText();
			}

			Dailylog.logInfo("orderNum is :" + orderNum1);
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			Thread.sleep(3000);

			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
			}

			HMCCommon.HMCOrderCheck(hmcPage, orderNum1);

			// verify the xml
			driver.findElement(By.xpath("//span[contains(@id,'Content/OrganizerListEntry')]")).click();
			driver.findElement(By.xpath(".//span[contains(@id,'Content/EditorTab[Order.administration]')]")).click();
			String xmlcontent = driver.findElement(By.xpath(".//*[contains(@id,'Order.xmlContentShow')]")).getText();
			Dailylog.logInfo("xmlcontent is :" + xmlcontent);
			Dailylog.logInfo("verify xml has the right value");
			String YZ37 = "<text>0</text>";
			String YZ39 = "<text>6</text>";
			assert xmlcontent.contains(YZ37);
			assert xmlcontent.contains(YZ39);
			assert xmlcontent.contains("84511773521");
			assert xmlcontent.contains("ISENTO");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		Dailylog.logInfo("b2cProductUrl is :" + b2cProductUrl);
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0].substring(0, 15);
		Dailylog.logInfo(ProductID);
		return ProductID;
	}

}
