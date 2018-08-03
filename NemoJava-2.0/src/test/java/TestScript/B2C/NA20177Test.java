package TestScript.B2C;

import java.util.HashMap;
import java.util.Map;
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
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA20177Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String b2cProductUrl;
	public String ProductID;
	private String loginID;
	private String pwd;

	public NA20177Test(String Store) {
		this.Store = Store;
		this.testName = "NA-20177";
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

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "e2e",
			"b2c", "compatibility" })
	public void NA20177(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			By BankDeposit = By
					.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']");
			// 1, login the co store and add product into cart
			Common.NavigateToUrl(driver, Browser,
					testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			loginID = testData.B2C.getLoginID();
			pwd = testData.B2C.getLoginPassword();
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			HandleJSpring(driver, b2cPage, loginID, pwd);
			Common.sleep(2000);
			// after the login , get the homepage url immediately
			String url = driver.getCurrentUrl();
			// get the cart url and empty the cart
			// String cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver,
					b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			Thread.sleep(2000);

			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);

			b2cProductUrl = url + "/p/" + testData.B2C.getDefaultMTMPN();
			System.out.println("b2cProductUrl is :::::" + b2cProductUrl);

			Thread.sleep(5000);
			Common.NavigateToUrl(driver, Browser, b2cProductUrl);
			// Common.NavigateToUrl(driver, Browser,
			// B2CCommon.getCTOProduct(url,Store));
			B2CCommon.handleGateKeeper(b2cPage, testData);
			System.out.println("Url is :::::" + driver.getCurrentUrl());
			Thread.sleep(8000);
			driver.findElement(By.xpath("(//*[@id='view-customize'])[1]"))
					.click();

			Thread.sleep(10000);
			driver.findElement(By.xpath("(//*[@id='addToCartButtonTop'])[1]"))
					.click();

			while (Common
					.isElementExist(
							driver,
							By.xpath("(//*[@id='product-builder-form']/descendant::div/button)[3]"))) {
				
				try{driver.findElement(
						By.xpath("(//*[@id='product-builder-form']/descendant::div/button)[3]"))
						.click();
				System.out.println("Countinue button clicked!!!");}
				catch(Exception e){
					driver.findElement(By.id("CTO_addToCart")).click();
					Thread.sleep(3000);
				}
			}

			Thread.sleep(3000);
			// driver.findElement(By.xpath("//*[@id='product-builder-form']//div[@class='pricingSummary-cta']/button")).click();

			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("cart"));

			
			try {
				if (driver.findElement(By.xpath(".//*[@id='CTO_addToCart']"))
						.isDisplayed()) {
					driver.findElement(By.xpath(".//*[@id='CTO_addToCart']"))
							.click();
					Thread.sleep(4000);
				}

			} catch (Exception e) {

			}
			Assert.assertTrue(Common.isElementExist(driver,
					By.xpath("//*[@id='mainContent']//div[@class='cart-item']")));
	
			// 2, click Lenovo checkout on shopping cart page
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"))
					.click();
			Assert.assertTrue(driver.getCurrentUrl().toString()
					.contains("address"));
			// driver.findElement(By.xpath(".//*[@id='addressForm']/fieldset/legend/a")).click();

			boolean judgeFirstName = driver.findElement(
					By.xpath("//*[@id='firstName']")).isEnabled();
			Assert.assertTrue(judgeFirstName);
			System.out.println("judgeFirstName is required :" + judgeFirstName);
			boolean judgeLastName = driver.findElement(
					By.xpath("//*[@id='lastName']")).isEnabled();
			Assert.assertTrue(judgeLastName);
			System.out.println("judgeLastName is required :" + judgeLastName);
		
			boolean judgeLine1 = driver.findElement(
					By.xpath("//*[@id='line1']")).isEnabled();
			Assert.assertTrue(judgeLine1);
			System.out.println("judgeLine1 is required :" + judgeLine1);

			boolean judgeLine2 = driver.findElement(
					By.xpath("//*[@id='line2']")).isEnabled();
			Assert.assertTrue(judgeLine2);
			System.out.println("judgeLine2 is required :" + judgeLine2);

			boolean judgeLine3 = driver.findElement(
					By.xpath("//*[@id='line3']")).isEnabled();
			Assert.assertTrue(judgeLine3);
			System.out.println("judgeLine3 is required :" + judgeLine3);

			boolean judgeTownCity = driver.findElement(
					By.xpath("//*[@id='townCity']")).isEnabled();
			Assert.assertTrue(judgeTownCity);
			System.out.println("judgeTownCity is required :" + judgeTownCity);

			boolean judgePhone = driver.findElement(
					By.xpath("//*[@id='phone']")).isEnabled();
			Assert.assertTrue(judgePhone);
			System.out.println("judgePhone is required :" + judgePhone);

			/*
			 * the following is the old codes
			 */

			// 2, click Lenovo checkout on shopping cart page
			/*
			 * driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")
			 * ).click();
			 * Assert.assertTrue(driver.getCurrentUrl().toString().contains
			 * ("address")); driver.findElement(By.xpath(
			 * ".//*[@id='addressForm']/fieldset/legend/a")).click(); boolean
			 * judgePersona_Natural = driver.findElement(By.xpath(
			 * "//*[@id='address.idCustFiscalType']/option[contains(.,'Persona Natural')]"
			 * )).isDisplayed(); Assert.assertTrue(judgePersona_Natural);
			 * System.out.println("judgePersona_Natural is displayed :" +
			 * judgePersona_Natural); boolean judgeFirstName =
			 * driver.findElement(By.xpath("//*[@id='firstName']")).isEnabled();
			 * Assert.assertTrue(judgeFirstName);
			 * System.out.println("judgeFirstName is required :" +
			 * judgeFirstName); boolean judgeLastName =
			 * driver.findElement(By.xpath("//*[@id='lastName']")).isEnabled();
			 * Assert.assertTrue(judgeLastName);
			 * System.out.println("judgeLastName is required :" +
			 * judgeLastName); boolean judgeTaxNumber =
			 * driver.findElement(By.xpath
			 * ("//*[@id='consumerTaxNumber']")).isDisplayed();
			 * Assert.assertTrue(judgeTaxNumber);
			 * System.out.println("judgeTaxNumber is displayed :" +
			 * judgeTaxNumber);
			 */

			// 3,change the type to Empresa in the drop down list
			/*
			 * Select select = new Select(driver.findElement(By.xpath(
			 * "//*[@id='address.idCustFiscalType']")));
			 * select.selectByValue("1"); boolean judgeFirstName_Empresa =
			 * driver
			 * .findElement(By.xpath("//*[@id='firstName']")).isDisplayed();
			 * Assert.assertFalse(judgeFirstName_Empresa);
			 * System.out.println("judgeFirstName_Empresa is displayed :" +
			 * judgeFirstName_Empresa); boolean judgeLastName_Empresa =
			 * driver.findElement
			 * (By.xpath("//*[@id='lastName']")).isDisplayed();
			 * Assert.assertFalse(judgeLastName_Empresa);
			 * System.out.println("judgeLastName_Empresa is displayed :" +
			 * judgeLastName_Empresa);
			 * 
			 * boolean judgeCompany =
			 * driver.findElement(By.xpath("//*[@id='company']")).isDisplayed();
			 * Assert.assertTrue(judgeCompany);
			 * System.out.println("judgeCompany is displayed :" + judgeCompany);
			 * boolean judgeTaxNumber_1 =
			 * driver.findElement(By.xpath("//*[@id='consumerTaxNumber']"
			 * )).isDisplayed(); Assert.assertFalse(judgeTaxNumber_1);
			 * System.out.println("judgeTaxNumber_1 is displayed :" +
			 * judgeTaxNumber_1); boolean judgeCompanyTaxNumber =
			 * driver.findElement
			 * (By.xpath("//*[@id='companyTaxNumber']")).isDisplayed();
			 * Assert.assertTrue(judgeCompanyTaxNumber);
			 * System.out.println("judgeCompanyTaxNumber is displayed :" +
			 * judgeCompanyTaxNumber);
			 */

			// 4, input address field and input companyTaxNumber:
			// 11111111111111, click on Continue button.
			/*
			 * String max_length =
			 * driver.findElement(By.xpath("//*[@id='companyTaxNumber']"
			 * )).getAttribute("maxlength").toString();
			 * Assert.assertEquals("10", max_length);
			 * driver.findElement(By.xpath
			 * ("//*[@id='companyTaxNumber']")).clear();
			 * driver.findElement(By.xpath
			 * ("//*[@id='companyTaxNumber']")).sendKeys("12601584");
			 * driver.findElement(By.xpath("//*[@id='line1']")).clear();
			 * driver.findElement
			 * (By.xpath("//*[@id='line1']")).sendKeys(testData
			 * .B2C.getDefaultAddressLine1());
			 * driver.findElement(By.xpath("//*[@id='townCity']")).clear();
			 * driver
			 * .findElement(By.xpath("//*[@id='townCity']")).sendKeys(testData
			 * .B2C.getDefaultAddressCity());
			 */

			if (Common
					.checkElementDisplays(
							driver,
							By.cssSelector(".textLink.checkout-shippingAddress-editLink"),
							5)) {
				b2cPage.Shipping_editAddress.click();
			}
			driver.findElement(By.xpath("//*[@id='firstName']")).clear();
			driver.findElement(By.xpath("//*[@id='firstName']")).sendKeys(
					"test");
			driver.findElement(By.xpath("//*[@id='lastName']")).clear();
			driver.findElement(By.xpath("//*[@id='lastName']"))
					.sendKeys("test");
			driver.findElement(By.xpath(".//*[@id='consumerTaxNumber']"))
					.clear();
			driver.findElement(By.xpath(".//*[@id='consumerTaxNumber']"))
					.sendKeys("12601584");
			driver.findElement(By.xpath("//*[@id='line1']")).clear();
			driver.findElement(By.xpath("//*[@id='line1']")).sendKeys(
					testData.B2C.getDefaultAddressLine1());

			driver.findElement(By.xpath("//*[@id='townCity']")).clear();
			driver.findElement(By.xpath("//*[@id='townCity']")).sendKeys(
					testData.B2C.getDefaultAddressCity());

			Select select_state = new Select(driver.findElement(By
					.xpath("//*[@id='state']")));
			select_state.selectByVisibleText(testData.B2C
					.getDefaultAddressState());
			driver.findElement(By.xpath("//*[@id='phone']")).clear();
			driver.findElement(By.xpath("//*[@id='phone']")).sendKeys(
					testData.B2C.getDefaultAddressPhone());
			driver.findElement(By.xpath("//*[@id='email']")).clear();
			driver.findElement(By.xpath("//*[@id='email']")).sendKeys(
					testData.B2C.getLoginID());

			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);

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
			if (Common.isElementExist(driver, BankDeposit)) {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']")));
				/*
				 * driver.findElement(
				 * By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']"))
				 * .click();
				 */
			} else {
				Common.javascriptClick(driver, driver.findElement(By
						.xpath("//*[@id='PaymentTypeSelection_Wire']")));
				/*
				 * driver.findElement(
				 * By.xpath("//*[@id='PaymentTypeSelection_Wire']")) .click();
				 */

			}

			// js.executeScript("arguments[0].click()",
			// driver.findElement(By.xpath("//*[@id='add-payment-method-continue']")));
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
			System.out.println("url_sum is :" + url_sum);

			Assert.assertTrue(driver.getCurrentUrl().toString()
					.endsWith("summary"));
			if (Common
					.isElementExist(
							driver,
							By.xpath("(//*[@id='mainContent']//span[@class='cardHoldName'])[2]"))) {
				// 7, the NIT number input on billing address page is displayed
				// under billing address
				boolean NIT_verify = driver
						.findElement(
								By.xpath("(//*[@id='mainContent']//span[@class='cardHoldName'])[2]"))
						.isDisplayed();
				System.out.println("NIT_verify is displayed :" + NIT_verify);
				Assert.assertTrue(NIT_verify);
			}

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
							By.xpath("//*[@id='configarea_scroller']//h1[contains(.,'Gracias')]"))
					.isDisplayed();
			System.out.println("thankyou is displayed :" + thankyou);
			Assert.assertTrue(thankyou);
			// 9, check order in hmc
			String orderNum = "";
			if (Common
					.isElementExist(
							driver,
							By.xpath("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)"))) {
				orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			} else {
				orderNum = driver
						.findElement(
								By.xpath("(//*[contains(text(),'Número de pedido:')]/../*)[2]"))
						.getText();
			}

			System.out.println("orderNum is :" + orderNum);
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			Thread.sleep(3000);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(hmcPage, orderNum);

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
			System.out.println("xmlcontent is :" + xmlcontent);

			assert xmlcontent.contains("12601584");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		System.out.println("b2cProductUrl is :" + b2cProductUrl);
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0]
				.substring(0, 15);
		System.out.println(ProductID);
		return ProductID;
	}

	public static String getMTMProduct(String pageUrl, String store) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("CO", pageUrl + "/p/22TP2TT460S");
		return map.get(store);
	}

}
