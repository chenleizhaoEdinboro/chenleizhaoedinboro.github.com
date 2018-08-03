package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
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
import junit.framework.Assert;

public class NA28156Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public MailPage mailPage;
	public String homePageUrl;
	private String B2Bunit = "1213219866";
	private String B2BUrl = "le/1212195032/br/en/1213219866";
	public String emailCart = "test28156@sharklasers.com";
	private String brAccount = "mxbuyer@sharklasers.com";
	private String pwd = "1q2w3e4r";

	private String ProdcutNo = "0B47069";

	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";

	public NA28156Test(String Store) {
		this.Store = Store;
		this.testName = "NA-28156";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA28156(ITestContext ctx) throws Exception {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			String hmcLoginURL = testData.HMC.getHomePageUrl();

			// Step 1 : HMC->B2B Unit->Site Attribute Tab->
			Dailylog.logInfoDB(1, "HMC setting", Store, testName);
			fulfillmentType("email");
			Thread.sleep(5000);
			String b2bLoginURL = testData.B2B.getLoginUrl();
			String brUrl = testData.B2B.getHomePageUrl().split("le/")[0] + B2BUrl;
			Dailylog.logInfoDB(4, "B2B bR URL : " + brUrl, Store, testName);
			driver.get(brUrl);
			B2BCommon.Login(b2bPage, brAccount, pwd);
			Common.sleep(3000);
			b2bPage.HomePage_CartIcon.click();
			Common.sleep(1500);
			if (Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))) {
				driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
			}
			B2BCommon.addProduct(driver, b2bPage, ProdcutNo);

			Dailylog.logInfoDB(4, "Added a product into Cart", Store, testName);

			String productPrice = b2bPage.Cart_ProductsPrice.getText().trim();
			Dailylog.logInfoDB(4, "Cart_ProductsPrice : " + productPrice, Store, testName);

			Common.sleep(2000);
			b2bPage.cartPage_LenovoCheckout.click();
			Common.sleep(3000);
			((JavascriptExecutor) driver).executeScript("scroll(0,500)");

			Common.sleep(1000);
			
			//BR
			B2BCommon.fillB2BShippingInfo(driver, b2bPage,"au", "FirstNameJohn", "LastNameSnow", 
					"ORACLE DO BRASIL SISTEMAS LTDA", "SC NORTE QUADRA 02 BLOCO A", "BRASILIA", 
					"Brasilia", "70712-900","+550000000");	
						
			b2bPage.shipping_companyTaxNumber.clear();
			b2bPage.shipping_companyTaxNumber.sendKeys("59456277000338");
			
			b2bPage.shipping_line2.clear();
			b2bPage.shipping_line2.sendKeys("SALAS 203 E 303");
			
			
			Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
			
			System.out.println("Go to Payment page!");
			if(Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)){
				b2bPage.shippingPage_validateFromOk.click();
			}
			b2bPage.paymentPage_PurchaseOrder.click();
			Common.sleep(2000);
			b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
			b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);
			
			b2bPage.paymentPage_ContinueToPlaceOrder.click();
			(new WebDriverWait(driver, 500)).until(ExpectedConditions.visibilityOf(b2bPage.placeOrderPage_PlaceOrder));
			System.out.println("Go to Order page!");
			
			
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2bPage.placeOrderPage_ResellerID, 5)){
				Common.scrollToElement(driver, b2bPage.placeOrderPage_ResellerID);
				b2bPage.placeOrderPage_ResellerID.sendKeys("reseller@yopmail.com");
			}
			
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.PAGE_UP).perform();
			if(Common.checkElementExists(driver, b2bPage.placeOrderPage_Terms, 20)){
				actions.sendKeys(Keys.PAGE_UP).perform();
				Common.scrollToElement(driver, b2bPage.placeOrderPage_Terms);
				b2bPage.placeOrderPage_Terms.click();		
			}		
			b2bPage.placeOrderPage_PlaceOrder.click();
			Assert.assertTrue(driver.getCurrentUrl().toString().contains("Confirmation"));
//			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			String orderNum=b2bPage.placeOrderPage_OrderNumber.getText();
        
			Dailylog.logInfoDB(5, "Order Num : " + orderNum, Store, testName);
			long orderNumber = Long.parseLong(orderNum.trim());
			long limitMin = Long.parseLong("4289200000");
			long limitMax = Long.parseLong("4334999999");
			Assert.assertTrue("order number is in number range: 4289200000 ~4334999999",
					(orderNumber < limitMax) && (orderNumber > limitMin));

			mailPage = new MailPage(driver);
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, "test28156");

			Assert.assertTrue("B2C email is not received ",
					checkEmailContent(driver, mailPage, orderNum, orderNum, ProdcutNo, productPrice));
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void fulfillmentType(String type) {
		driver.get(testData.HMC.getHomePageUrl());
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		driver.navigate().refresh();
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + B2Bunit);
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(B2Bunit);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
		if (type == "email") {

			Dailylog.logInfoDB(2, "Set Fulfillment Type:email", Store, testName);
			hmcPage.fulfillmentType.click();
			hmcPage.fulfillmentType_EMAIL.click();

			hmcPage.B2B_orderType.clear();
			hmcPage.B2B_orderType.sendKeys("YROI");

			hmcPage.B2B_businessToEmailAddress.clear();
			hmcPage.B2B_businessToEmailAddress.sendKeys(emailCart);

		} else {
			hmcPage.fulfillmentType.click();
			hmcPage.fulfillmentType_CRM.click();
		}

		Common.sleep(5000);
		hmcPage.B2BUnit_Save.click();
		Common.sleep(5000);
		hmcPage.HMC_Logout.click();
	}

	public static Boolean checkEmailContent(WebDriver driver, MailPage mailPage, String title, String orderNum,
			String productName, String productPrice) {

		Boolean reciverEmail = false;
		Common.sleep(10000);
		for (int i = 1; i <= 10; i++) {

			List<WebElement> emailTile = driver.findElements(By.xpath("//td[contains(text(),'" + title + "')]"));
			int totalEmail = emailTile.size();
			if (totalEmail == 0) {
				System.out.println(
						"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
				Common.sleep(10000);
			} else {
				System.out.println(emailTile.size());
				// Common.javascriptClick(driver, emailTile.get(0));
				emailTile.get(0).click();

				if (Common.isElementExist(driver, By.xpath("//td[contains(text(),'" + orderNum + "')]"))) {
					if (Common.isElementExist(driver, By.xpath("//td[contains(.,'" + productName + "')]"))) {
						if (Common.isElementExist(driver, By.xpath("//td[contains(.,'" + productName + "')]"))) {
							reciverEmail = true;
							mailPage.Mail_backToInbox.click();
							break;
						}
						continue;
					}
					continue;

				} else {
					mailPage.Mail_backToInbox.click();
					reciverEmail = false;
					continue;
				}
			}

		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;
	}
	
	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			Dailylog.logInfoDB(10, "rollback", Store, testName); // roll back
			SetupBrowser();
			fulfillmentType("CRM");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
