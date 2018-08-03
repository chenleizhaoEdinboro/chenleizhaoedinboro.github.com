package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;
import org.openqa.selenium.JavascriptExecutor;

public class NA27014Test extends SuperTestClass {

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
	private String testProduct;
	private String loginID;
	private String pwd;

	public NA27014Test(String Store, String b2cUnit) {
		this.Store = Store;
		this.b2cUnitID = b2cUnit;
		this.testName = "NA-27014";
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage,
			String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			Common.NavigateToUrl(driver, Browser,
					testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p1",
			"b2c", "compatibility" })
	public void NA27014(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			By BankDeposit = By
					.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']");
			// Make sure HMC setup is complete
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			Thread.sleep(3000);
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(3000);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.clear();
			hmcPage.B2CUnit_IDTextBox.sendKeys(b2cUnitID);
			hmcPage.B2CUnit_SearchButton.click();
			Common.doubleClick(driver, hmcPage.B2CUnit_FirstSearchResultItem);

			Common.waitElementClickable(driver,
					hmcPage.B2CUnit_SiteAttributeTab, 30);
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.b2cUnit_switchAddressYes.click();
			hmcPage.Types_SaveBtn.click();

			// 1, login the BR store and add product into cart
			b2cPage = new B2CPage(driver);
			Common.NavigateToUrl(driver, Browser,
					testData.B2C.getloginPageUrl());
			testProduct = testData.B2C.getDefaultMTMPN();
			Common.sleep(2500);

			B2CCommon.handleGateKeeper(b2cPage, testData);
			loginID = testData.B2C.getLoginID();
			pwd = testData.B2C.getLoginPassword();
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			HandleJSpring(driver, b2cPage, loginID, pwd);
			Common.sleep(2000);
			b2cPage.Navigation_CartIcon.click();
			Common.sleep(4000);
			if (Common.checkElementExists(driver,
					b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			Thread.sleep(2000);

			B2CCommon.clearTheCart(driver, b2cPage, testData);

			Common.sleep(2000);

			// =========== Step:3 Add one product ===========//
			B2CCommon.addPartNumberToCart(b2cPage,
					testData.B2C.getDefaultMTMPN());
			//
			Thread.sleep(3000);
			// driver.findElement(By.xpath("//*[@id='product-builder-form']//div[@class='pricingSummary-cta']/button")).click();

			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("cart"));
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//*[@id='mainContent']//div[@class='cart-item']")));

			// 2, click Lenovo checkout on shopping cart page
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"))
					.click();
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("address"));

			/*
			 * boolean judgeFirstName = driver.findElement(
			 * By.xpath("//*[@id='firstName']")).isEnabled();
			 * Assert.assertTrue(judgeFirstName);
			 * Dailylog.logInfo("judgeFirstName is required :" +
			 * judgeFirstName); boolean judgeLastName = driver.findElement(
			 * By.xpath("//*[@id='lastName']")).isEnabled();
			 * Assert.assertTrue(judgeLastName);
			 * Dailylog.logInfo("judgeLastName is required :" + judgeLastName);
			 * 
			 * boolean judgeLine1 = driver.findElement(
			 * By.xpath("//*[@id='line1']")).isEnabled();
			 * Assert.assertTrue(judgeLine1);
			 * Dailylog.logInfo("judgeLine1 is required :" + judgeLine1);
			 * 
			 * boolean judgeLine2 = driver.findElement(
			 * By.xpath("//*[@id='line2']")).isEnabled();
			 * Assert.assertTrue(judgeLine2);
			 * Dailylog.logInfo("judgeLine2 is required :" + judgeLine2);
			 * 
			 * 
			 * 
			 * boolean judgeTownCity = driver.findElement(
			 * By.xpath("//*[@id='townCity']")).isEnabled();
			 * Assert.assertTrue(judgeTownCity);
			 * Dailylog.logInfo("judgeTownCity is required :" + judgeTownCity);
			 * 
			 * boolean judgePhone = driver.findElement(
			 * By.xpath("//*[@id='phone']")).isEnabled();
			 * Assert.assertTrue(judgePhone);
			 * Dailylog.logInfo("judgePhone is required :" + judgePhone);
			 */

			try {
				b2cPage.Shipping_editAddress.click();
			} catch (Exception e) {
				Dailylog.logInfo("Edit is not present");
			}
			/*
			 * driver.findElement(By.xpath(".//*[@id='consumerTaxNumber']"))
			 * .clear();
			 * driver.findElement(By.xpath(".//*[@id='consumerTaxNumber']"))
			 * .sendKeys("12601584");
			 */
			driver.findElement(
					By.xpath("//*[@name='customerFiscalType']/option[contains(text(),'Pessoa Jurídica não contribuinte')]"))
					.click();
			b2cPage.Shipping_CompanyNameTextBox.clear();
			b2cPage.Shipping_CompanyNameTextBox.sendKeys("lenovo");
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

			Select select_state = new Select(driver.findElement(By
					.xpath("//*[@id='state']")));
			select_state.selectByVisibleText(BRState);

			b2cPage.Mobile.clear();
			b2cPage.Mobile.sendKeys("12312412414");
			b2cPage.Shipping_CompanyTaxNumber.clear();
			b2cPage.Shipping_CompanyTaxNumber.sendKeys("12601584000130");
			b2cPage.Shipping_PostCodeTextBox.clear();
			b2cPage.Shipping_PostCodeTextBox.sendKeys("70443900");
			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys("lixe1@lenovo.com");
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Thread.sleep(5000);

			Common.javascriptClick(driver, driver.findElement(By
					.xpath(".//*[contains(text(),'Enviar para')]")));
			Thread.sleep(5000);
			Common.javascriptClick(driver, driver.findElement(By
					.xpath(".//*[contains(text(),'ALTERAR')]")));
			Thread.sleep(5000);
			if (!driver.getCurrentUrl().contains("address")) {
				Common.javascriptClick(
						driver,
						driver.findElement(By
								.xpath(".//*[contains(text(),'Editar endereço de entrega')]")));

			}
			// Thread.sleep(3000);
			// if (Common
			// .isElementExist(
			// driver,
			// By.cssSelector(".textLink.checkout-shippingAddress-editLink"))) {
			// b2cPage.Shipping_editAddress.click();
			// }

			assert b2cPage.Shipping_CompanyTaxNumber.getAttribute("value")
					.toString().equals("12.601.584/0001-30");

			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);

			// on the payment page , check the checkbox
			// "SAME AS DELIVERY ADDRESS" and select Direct Deposit payment then
			// continue;
			if (Common.isElementExist(driver,
					By.xpath("//*[@id='useDeliveryAddress']"))
					&& !driver.findElement(
							By.xpath("//*[@id='useDeliveryAddress']"))
							.isSelected()) {
				driver.findElement(By.xpath("//*[@id='useDeliveryAddress']"))
						.clear();
			}
			if (Common.checkElementExists(driver,
					b2cPage.Payment_bankDepositLabel, 5)) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));

			} else if (Common.isElementExist(driver,
					By.xpath("//*[@id='PaymentTypeSelection_CARD']"))) {
				// Fill payment info
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_CARD']")));
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			}

			if (Common.isElementExist(driver,
					By.xpath("//span[@class='continue_to_review']"))) {
				WebElement paymentContinue = driver.findElement(By
						.xpath("//span[@class='continue_to_review']"));
				Common.javascriptClick(driver, paymentContinue);
			} else {
				WebElement paymentContinue = driver.findElement(By
						.xpath("//*[@id='add-payment-method-continue']"));
				Common.javascriptClick(driver, paymentContinue);
			}

			Thread.sleep(10000);

			String url_sum = driver.getCurrentUrl().toString();
			Dailylog.logInfo("url_sum is :" + url_sum);

			Assert.assertTrue(driver.getCurrentUrl().toString()
					.endsWith("summary"));
			assert driver
					.findElement(By.xpath("(.//*[@class='cardHoldName'])[1]"))
					.getText().contains("12.601.584/0001-30");
			assert driver
					.findElement(By.xpath("(.//*[@class='cardHoldName'])[3]"))
					.getText().contains("12.601.584/0001-30");

			// 8, check the T&C and click on "place order"
			if (Common
					.isElementExist(
							driver,
							By.xpath("//label[contains(@class,'redesign-term-check')]"))) {
				driver.findElement(
						By.xpath("//label[contains(@class,'redesign-term-check')]"))
						.click();
			} else {
				driver.findElement(By.xpath("//*[@id='Terms1']")).click();
			}

			Thread.sleep(5000);
			driver.findElement(
					By.xpath("//*[@id='orderSummaryReview_placeOrder']"))
					.click();

			boolean thankyou = driver
					.findElement(
							By.xpath("//*[@id='configarea_scroller']//h1[contains(.,'Obrigado')]"))
					.isDisplayed();
			Dailylog.logInfo("thankyou is displayed :" + thankyou);
			Assert.assertTrue(thankyou);
			// 9, check order in hmc
			String orderNum1 = "";
			if (Common
					.isElementExist(
							driver,
							By.xpath("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)"))) {
				orderNum1 = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			} else if (Common
					.isElementExist(
							driver,
							By.xpath("(//*[contains(text(),'Número de pedido:') or contains(text(),'Número do pedido')]/../*)[2]"))) {
				orderNum1 = driver
						.findElement(
								By.xpath("(//*[contains(text(),'Número de pedido:') or contains(text(),'Número do pedido')]/../*)[2]"))
						.getText();
			}else{
				orderNum1 = driver
						.findElement(
								By.xpath("(//td[contains(text(),'Número do Pedido:')]/../td)[2]"))
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
			driver.findElement(
					By.xpath("//span[contains(@id,'Content/OrganizerListEntry')]"))
					.click();
			driver.findElement(
					By.xpath(".//span[contains(@id,'Content/EditorTab[Order.administration]')]"))
					.click();
			String xmlcontent = driver.findElement(
					By.xpath(".//*[contains(@id,'Order.xmlContentShow')]"))
					.getText();
			Dailylog.logInfo("xmlcontent is :" + xmlcontent);

			String YZ39 = "<text>6</text>";
			String YZ37 = "<text>1</text>";

			assert xmlcontent.contains(YZ39);
			assert xmlcontent.contains(YZ37);
			assert xmlcontent.contains("12601584000130");
			assert xmlcontent.contains("ISENTO");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		Dailylog.logInfo("b2cProductUrl is :" + b2cProductUrl);
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0]
				.substring(0, 15);
		Dailylog.logInfo(ProductID);
		return ProductID;
	}

}
