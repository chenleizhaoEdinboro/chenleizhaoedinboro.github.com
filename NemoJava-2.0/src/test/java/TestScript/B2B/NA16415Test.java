package TestScript.B2B;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16415Test extends SuperTestClass {

	private String HMCWebUrl;
	public String WebURL;
	public String HMCUsername;
	public String HMCPassword;

	public NA16415Test(String Store) {
		this.Store = Store;
		this.testName = "NA-16415";
	}

	@Test(alwaysRun = true, groups = {"browsegroup", "uxui", "e2e", "b2b","compatibility"})
	public void NA16415(ITestContext ctx) {
		try {
			this.prepareTest();
			HMCPage page1 = new HMCPage(driver);
			B2BPage page2 = new B2BPage(driver);
			HMCWebUrl = testData.HMC.getHomePageUrl();
			WebURL = testData.B2B.getLoginUrl();
			HMCUsername = testData.HMC.getLoginId();
			HMCPassword = testData.HMC.getPassword();
			// int timeout = 1000;
			String partNo = null;
			float pricePLP = 0;
			float pricePDP = 0;
			float priceCart1 = 0;
			float priceOrder = 0;
			float priceTotal = 0;
			float priceTax = 0;
			String firstQuoteNum = null;
			String secondQuoutNum = null;
			String repId = "2900718028";
			String CartURL = "";
			String country = Store.toLowerCase();
			String catelog = "Nemo Master Multi Country Product Catalog - Online";
			String B2BUnit = testData.B2B.getB2BUnit();
			System.out.println(country + ":" + B2BUnit);
			String username_builder = country + "builder@sharklasers.com";
			String username_approver = country + "approver@sharklasers.com";
			Boolean isContract = true;
			By byLocator7 = By.id("approveId");
			By byLocator11 = By
					.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a");
			By byLocator12 = By.xpath("//li[@class='menu_level_2']//span");
			By byLocator8 = By
					.xpath("//div[@id='bundleAlert']//div[@class='modal-content']");
			By HMCLogin = By.id("Main_user");
			Dailylog.logInfoDB(1, "Test run for: " + country, Store, testName);
			if (country.equals("au")) {
				CartURL = "https://pre-c-hybris.lenovo.com/le/adobe_global/au/en/1213654197/cart";
			} else if (country.equals("us")) {
				CartURL = "https://pre-c-hybris.lenovo.com/le/1213348423/us/en/1213577815/cart";
			}
			if (Browser.toLowerCase().equals("ie")) {
				Common.NavigateToUrl(driver, Browser, HMCWebUrl);
			} else {
				driver.get(HMCWebUrl);
			}
			if (isAlertPresent() == true) {

				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
			Thread.sleep(5000);
			HMCCommon.Login(page1, testData);
			driver.navigate().refresh();
			HMCCommon.SetQuoteEnabledOnB2B(page1, driver, B2BUnit);
			page1.Home_EndSessionLink.click();
			if (Browser.toLowerCase().equals("firefox")
					|| Browser.toLowerCase().equals("ie")) {

				driver.get(WebURL.replaceAll("LIeCommerce:M0C0v0n3L!@", ""));
			} else {
				driver.get(WebURL);
			}
			B2BCommon.Login(page2, username_builder, "1q2w3e4r");
			page2.HomePage_productsLink.click();
			Thread.sleep(5000);
			page2.HomePage_Laptop.click();
			page2.productPage_agreementsAndContract.click();
			try {
				page2.productPage_raidoContractButton.click();
			} catch (Exception e) {
				isContract = false;
			}
			if (isContract) {
				if (Common.isElementExist(driver,
						By.xpath("//img[@title='Add to Cart']"))) {
					assert B2BCommon
							.verifyContractLabel(page2.HomePage_contractLable);
					assert page2.ProductPage_addAccessories.isDisplayed();
					assert page2.productPage_addToCart.isDisplayed();
					pricePLP = GetPriceValue(page2.ProductPage_PriceOnPLP
							.getText());
					partNo = page2.ProductPage_PartNoOnPLP.getText();
					Dailylog.logInfoDB(2, "PLP price is: " + pricePLP, Store,
							testName);
					Dailylog.logInfoDB(3, "part No is: " + partNo, Store,
							testName);
					page2.ProductPage_details.click();
					Thread.sleep(8000);
					assert page2.PDPPage_AddtoCart.isDisplayed();
					assert page2.PDPPage_AddAccessories.isDisplayed();
					pricePDP = GetPriceValue(page2.PDPPage_ProductPrice
							.getText());
					Dailylog.logInfoDB(4, "PDP price is: " + pricePDP, Store,
							testName);
					try {
						Thread.sleep(8000);
						page2.PDPPage_AddtoCart.click();
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.PDPPage_AddtoCart.click();
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.PDPPage_AddtoCart.click();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.PDPPage_AddtoCart.click();
					}
					if (Browser.toLowerCase().equals("ie")) {
						((JavascriptExecutor) driver)
								.executeScript("scroll(0,250)");
						WebElement IEaddtoCart = driver.findElement(By
								.xpath("//img[@title='Add to Cart']/.."));
						IEaddtoCart.click();
					}
					page2.productPage_AlertAddToCart.click();
					Thread.sleep(8000);
					page2.ProductPage_AlertGoToCart.click();
					try {
						Thread.sleep(5000);
						page2.cartPage_RequestQuoteBtn.isDisplayed();
						page2.CartPage_Price1.isDisplayed();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					}
					page2.cartPage_RequestQuoteBtn.click();
					try {
						Thread.sleep(5000);
						page2.cartPage_RepIDBox.sendKeys(repId);
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
						page2.cartPage_RequestQuoteBtn.click();
						page2.cartPage_RepIDBox.sendKeys(repId);
					}
					page2.cartPage_SubmitQuote.click();
					firstQuoteNum = page2.cartPage_QuoteNumber.getText();
					Dailylog.logInfoDB(5, "first quote number is: "
							+ firstQuoteNum, Store, testName);
					page2.CartPage_HomeLink.click();
					page2.HomePage_productsLink.click();
					Thread.sleep(5000);
					page2.HomePage_Laptop.click();
					page2.productPage_agreementsAndContract.click();
					page2.productPage_raidoContractButton.click();
					/*
					 * if(Common.isElementExsit(driver,By.id(""))){
					 * page2.MenuProduct.click(); Thread.sleep(5000);
					 * page2.Desktop.click(); }
					 */
					page2.ProductPage_addAccessories.click();
					Thread.sleep(2000);
					if (driver.getCurrentUrl().equals(CartURL)) {
						Thread.sleep(5000);
					} else  {
						driver.get(CartURL);
					}
					try {
						page2.cartPage_RequestQuoteBtn.isDisplayed();
						page2.CartPage_Price1.isDisplayed();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					}
					priceCart1 = GetPriceValue(page2.CartPage_Price1.getText());
					Dailylog.logInfoDB(6, "Cart price is: " + priceCart1,
							Store, testName);
					page2.cartPage_RequestQuoteBtn.click();
					try {
						Thread.sleep(5000);
						page2.cartPage_RepIDBox.sendKeys(repId);
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
						page2.cartPage_RequestQuoteBtn.click();
						page2.cartPage_RepIDBox.sendKeys(repId);
					}
					page2.cartPage_SubmitQuote.click();
					secondQuoutNum = page2.cartPage_QuoteNumber.getText();
					Dailylog.logInfoDB(7, "second quote number is: "
							+ secondQuoutNum, Store, testName);
				} else {
					Dailylog.logInfoDB(20, "No product can be used", Store,
							testName);
					assert false;
				}
			} else {
				if (Common.isElementExist(driver,
						By.xpath("//img[@title='Customize and buy']"))) {
					assert B2BCommon
							.verifyContractLabel(page2.HomePage_contractLable);
					assert page2.HomePage_Customize.isDisplayed();
					pricePLP = GetPriceValue(page2.ProductPage_PriceOnPLP
							.getText());
					partNo = page2.ProductPage_PartNoOnPLP.getText();
					Dailylog.logInfoDB(2, "PLP price is: " + pricePLP, Store,
							testName);
					Dailylog.logInfoDB(3, "part No is: " + partNo, Store,
							testName);
					page2.ProductPage_details.click();
					Thread.sleep(3000);
					if (driver.findElement(
							By.xpath("//button[text()='Customize']"))
							.isDisplayed()) {
						System.out.println("here is ------");
						driver.findElement(
								By.xpath("(//button[@class='close' and text()='×'])[2]"))
								.click();
						Thread.sleep(5000);
					}
					pricePDP = GetPriceValue(page2.PDPPage_ProductPrice_CTO
							.getText());
					Dailylog.logInfoDB(4, "PDP price is: " + pricePDP, Store,
							testName);
					try {
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					}
					if (driver.getCurrentUrl().equals(CartURL)) {
						Thread.sleep(5000);
					} else if (driver.getCurrentUrl().contains("cart")) {
						page2.Agreement_addToCartAccessoryBtn.click();
						Thread.sleep(5000);
					}
					try {
						Thread.sleep(5000);
						page2.cartPage_RequestQuoteBtn.isDisplayed();
						page2.CartPage_Price1.isDisplayed();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					}
					page2.cartPage_RequestQuoteBtn.click();
					try {
						Thread.sleep(5000);
						page2.cartPage_RepIDBox.sendKeys(repId);
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
						page2.cartPage_RequestQuoteBtn.click();
						page2.cartPage_RepIDBox.sendKeys(repId);
					}
					page2.cartPage_SubmitQuote.click();
					firstQuoteNum = page2.cartPage_QuoteNumber.getText();
					Dailylog.logInfoDB(5, "first quote number is: "
							+ firstQuoteNum, Store, testName);
					page2.CartPage_HomeLink.click();
					page2.HomePage_productsLink.click();
					Thread.sleep(5000);
					page2.HomePage_Laptop.click();
					page2.productPage_agreementsAndContract.click();
					page2.HomePage_Customize.click();
					Thread.sleep(3000);
					if (driver.findElement(
							By.xpath("//button[text()='Customize']"))
							.isDisplayed()) {
						System.out.println("here is ------");
						driver.findElement(
								By.xpath("(//button[@class='close' and text()='×'])[2]"))
								.click();
						Thread.sleep(5000);
					}
					try {
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(8000);
						page2.Agreement_agreementsAddToCart.click();
					}
					if (driver.getCurrentUrl().equals(CartURL)) {
						Thread.sleep(5000);
					} else if (driver.getCurrentUrl().contains("cart")) {
						page2.Agreement_addToCartAccessoryBtn.click();
						Thread.sleep(5000);
					}
					try {
						page2.cartPage_RequestQuoteBtn.isDisplayed();
						page2.CartPage_Price1.isDisplayed();
					} catch (NoSuchElementException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					} catch (TimeoutException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
					}
					priceCart1 = GetPriceValue(page2.CartPage_Price1.getText());
					Dailylog.logInfoDB(6, "Cart price is: " + priceCart1,
							Store, testName);
					page2.cartPage_RequestQuoteBtn.click();
					try {
						Thread.sleep(5000);
						page2.cartPage_RepIDBox.sendKeys(repId);
					} catch (ElementNotVisibleException e) {
						driver.navigate().refresh();
						Thread.sleep(5000);
						page2.cartPage_RequestQuoteBtn.click();
						page2.cartPage_RepIDBox.sendKeys(repId);
					}
					page2.cartPage_SubmitQuote.click();
					secondQuoutNum = page2.cartPage_QuoteNumber.getText();
					Dailylog.logInfoDB(7, "second quote number is: "
							+ secondQuoutNum, Store, testName);
				} else {
					Dailylog.logInfoDB(20, "No product can be used", Store,
							testName);
					assert false;
				}
			}

			page2.homepage_MyAccount.click();
			Thread.sleep(5000);
			/*
			 * if(!Common.isElementExsit(driver,byLocator12)){
			 * Thread.sleep(3000); } driver.findElement(byLocator12).click();
			 */
			page2.myAccountPage_ViewQuotehistory.click();
			driver.findElement(By.linkText(firstQuoteNum)).click();
			Select sel = new Select(driver.findElement(byLocator7));
			sel.selectByValue(username_approver.toUpperCase());
			page2.placeOrderPage_sendApproval.click();
			Common.sleep(5000);
			driver.findElement(By.linkText(secondQuoutNum)).click();
			driver.findElement(
					By.xpath("//select[@id='approveId']/option[@value='"
							+ username_approver.toUpperCase() + "']")).click();
			page2.placeOrderPage_sendApproval.click();
			page2.homepage_Signout.click();
			B2BCommon.Login(page2, username_approver, "1q2w3e4r");
			page2.homepage_MyAccount.click();
			Thread.sleep(5000);
			/*
			 * if(!Common.isElementExsit(driver,byLocator12)){
			 * Thread.sleep(3000); } driver.findElement(byLocator12).click();
			 */
			page2.myAccountPage_viewQuoteRequireApproval.click();
			Thread.sleep(10000);
			try {
				driver.findElement(By.id(firstQuoteNum)).click();
			} catch (Exception e) {
				page2.ApprovalDashBoard_Search.click();
				driver.findElement(By.id(firstQuoteNum)).click();
			}
			page2.QuotePage_clickRejectButton.click();
			driver.findElement(By.id(secondQuoutNum)).click();
			page2.QuotePage_clickApproveButton.click();
			Dailylog.logInfoDB(8, firstQuoteNum + " has been rejected, "
					+ secondQuoutNum + " has been approved", Store, testName);
			page2.homepage_Signout.click();
			B2BCommon.Login(page2, username_builder, "1q2w3e4r");
			page2.homepage_MyAccount.click();
			Thread.sleep(8000);
			/*
			 * if(!Common.isElementExsit(driver,byLocator12)){
			 * Thread.sleep(3000); } driver.findElement(byLocator12).click();
			 */
			page2.myAccountPage_ViewQuotehistory.click();
			By byLocatorsFirstQuoteStatus = By.xpath("//a[text()='"
					+ firstQuoteNum + "']/../../td[3]/p");
			By byLocatorsSecondQuoteStatus = By.xpath("//a[text()='"
					+ secondQuoutNum + "']/../../td[3]/p");
			assert driver.findElement(byLocatorsFirstQuoteStatus).getText()
					.equals("INTERNALREJECTED");
			assert driver.findElement(byLocatorsSecondQuoteStatus).getText()
					.equals("INTERNALAPPROVED");
			driver.findElement(By.linkText(secondQuoutNum)).click();
			page2.cartPage_ConvertToOrderBtn.click();
			Dailylog.logInfoDB(9, "Starting covert quote to order", Store,
					testName);
			Thread.sleep(5000);

			if (Store.toLowerCase().equals("us")) {
				B2BCommon.fillB2BShippingInfo(driver, page2, "us",
						"test000001", "test000001", "Georgia", "1535 Broadway",
						"New York", "New York", "10036-4077", "2129450100");
			} else if (Store.toLowerCase().equals("au")) {
				B2BCommon.fillB2BShippingInfo(driver, page2, "au",
						"test000001", "test000001", "adobe_global",
						"62 Streeton Dr", "RIVETT",
						"Australian Capital Territory", "2611", "2123981900");
			}

			// 23 ,Enter the required fields of shipping address and select the
			// shipping method,then click *Continue* to go to payment step of
			// checkout.
			Thread.sleep(3000);
			page2.shippingPage_ContinueToPayment.click();
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions
							.elementToBeClickable(page2.paymentPage_ContinueToPlaceOrder));

			Thread.sleep(3000);
			if (Common.isElementExist(driver, By.id("group-shipping-address"))) {
				page2.shippingPage_validateFromOk.click();
			}
			page2.paymentPage_PurchaseOrder.click();
			Thread.sleep(8000);
			page2.paymentPage_purchaseNum.clear();
			page2.paymentPage_purchaseNum.sendKeys("123456");
			/*
			 * SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
			 * //page2.paymentPage_purchaseDate.sendKeys(dataFormat.format(new
			 * Date()).toString()); if(Common.isElementExist(driver,By.xpath(
			 * "//div[@id='ui-datepicker-div']//tr[last()]/td/a"))){
			 * driver.findElements
			 * (byLocator11).get(driver.findElements(byLocator11
			 * ).size()-1).click(); }
			 */
			page2.purchaseDate.click();
			driver.findElements(byLocator11)
					.get(driver.findElements(byLocator11).size() - 1).click();
			Thread.sleep(8000);
			
			B2BCommon.fillDefaultB2bBillingAddress (driver,page2, testData);
			

			// page2.paymentPage_ContinueToPlaceOrder.click();
			Thread.sleep(5000);
			if (Common.isElementExist(driver, By.id("resellerID"))) {
				page2.placeOrderPage_ResellerID.sendKeys(repId);
			}
			Common.scrollToElement(driver, page2.placeOrderPage_Terms);
			page2.placeOrderPage_Terms.click();
			driver.findElement(
					By.xpath("//select[@id='approveId']/option[@value='"
							+ username_approver.toUpperCase() + "']")).click();
			page2.placeOrderPage_sendForApproval.click();
			priceOrder = GetPriceValue(page2.OrderPage_subTotalPrice.getText());
			priceTotal = GetPriceValue(page2.OrderPage_TotalPrice.getText());
			if (Common
					.isElementExist(
							driver,
							By.xpath("(.//*[@class='checkout-confirm-orderSummary-orderTotals-subTotal'])[2]"))) {
				System.out.println(page2.OrderPage_TaxPrice.getText());
				priceTax = GetPriceValue(page2.OrderPage_TaxPrice.getText());
			} else {
				priceTax = 0;
			}

			/* if(Common.isElementExsit(driver,By.xpath("(./ *//*
																 * [@class=
																 * 'checkout-confirm-orderSummary-orderTotals-subTotal'])[2]"))){
																 * priceShipping
																 * = Common.
																 * GetPriceValue
																 * (
																 * page2.TaxPrice
																 * .getText());
																 * }else{
																 * priceTax = 0;
																 * }
																 */
			Dailylog.logInfoDB(10, "Price on order: " + priceOrder, Store,
					testName);
			assert (page2.placeOrderPage_OrderNumber.getText()
					.equals(secondQuoutNum));
			//assert (priceOrder == pricePDP) && (priceCart1 == priceOrder);// &&(pricePDP
																			// ==
																			// pricePLP);
			Dailylog.logInfoDB(11, "Convert to order successfully", Store,
					testName);
			page2.homepage_Signout.click();
			B2BCommon.Login(page2, username_approver, "1q2w3e4r");
			page2.homepage_MyAccount.click();
			Thread.sleep(5000);
			/*
			 * if(!Common.isElementExsit(driver,byLocator12)){
			 * Thread.sleep(3000); } driver.findElement(byLocator12).click();
			 */
			page2.myAccountPage_viewOrderRequireApproval.click();
			try {
				driver.findElement(By.id(secondQuoutNum)).click();
			} catch (Exception e) {
				page2.ApprovalDashBoard_Search.click();
				driver.findElement(By.id(secondQuoutNum)).click();
			}
			page2.QuotePage_clickApproveButton.click();
			Thread.sleep(5000);
			page2.homepage_Signout.click();
			// driver.get(HMCWebUrl);
			Thread.sleep(5000);
			if (Browser.toLowerCase().equals("ie")) {
				Common.NavigateToUrl(driver, Browser, HMCWebUrl);
			} else {
				driver.get(HMCWebUrl);
			}
			Thread.sleep(5000);
			/*
			 * driver.manage().deleteAllCookies(); Thread.sleep(10000);
			 * driver.get(HMCWebUrl);
			 */
			if (Common.isElementExist(driver, HMCLogin)) {
				HMCCommon.Login(page1, testData);
				driver.navigate().refresh();
			}

			Dailylog.logInfoDB(12, "Price of Tax: " + priceTax, Store, testName);
			Dailylog.logInfoDB(13, "Price of Total: " + priceTotal, Store,
					testName);
			// Common.SimulateB2BPrice(page1, driver, coun, country, catelog,
			// B2BUnit, partNo, pricePDP);
			HMCOrderCheck(page1, secondQuoutNum, priceTax, priceTotal);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void HMCOrderCheck(HMCPage page1, String OrderNumber,
			float TaxPrice, float ProductPrice) throws Exception {
		By byLocators01 = By
				.xpath("//div[contains(text(),'Incl. Tax:')]/../../td[5]//input");
		Thread.sleep(5000);
		page1.Home_Order.click();
		page1.Home_Order_Orders.click();
		page1.Home_Order_OrderID.clear();
		page1.Home_Order_OrderID.sendKeys(OrderNumber);
		page1.Home_Order_OrderSearch.click();
		Thread.sleep(5000);
		if (!page1.Home_Order_OrderStatus.getText().contains("Completed")) {
			Thread.sleep(5000);
			page1.Home_Order_OrderSearch.click();
		}
		assert page1.Home_Order_OrderStatus.getText().contains("Completed");
		page1.Home_Order_OrderDetail.click();
		// page1.OrderReload.click();
		Thread.sleep(5000);
		Dailylog.logInfoDB(14, "Order Status is Completed", Store, testName);
		if (!Common.isElementExist(driver, byLocators01)) {
			assert 0.0 == TaxPrice;
		}
		assert TaxPrice == GetPriceValue(page1.Home_Order_TaxPrice
				.getAttribute("value"));
		assert ProductPrice == GetPriceValue(page1.Home_Order_ProductPrice
				.getAttribute("value"));
		page1.Home_Order_OrderAdmin.click();
		Thread.sleep(5000);
		assert page1.Orders_OrderXML.getText().contains("xml");
	}

	public float GetPriceValue(String Price) {
		if (Price.contains("FREE") || Price.contains("INCLUDED")) {
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

}