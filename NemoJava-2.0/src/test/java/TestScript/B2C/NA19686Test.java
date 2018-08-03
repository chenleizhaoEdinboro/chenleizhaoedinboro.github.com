package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19686Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	double cartTotalPrice = 0d;
	double orderThankyou_TaxPrice = 0d;

	public NA19686Test(String store) {
		this.Store = store;
		this.testName = "NA-19686";
	}

	private double String2Price(String valueString) {
		String price = valueString.replace("HK", "").replace("$", "").replace(",", "").replace(" ", "").replace("，", "");
		return Double.parseDouble(price);
	}

	private double convert(double value) {
		long l1 = Math.round(value * 100); // 四舍五入
		double ret = l1 / 100.0;
		return ret;
	}

	public void addToCartB2C(int workstationsCounter, WebDriver driver, String ButtonLocator)
			throws InterruptedException {
		String workstationsPage_WorkstationsTitle = "(//div/a[contains(@href,'mobile-workstations')])";
		String workstationsSubSeriesPage_ModalName = "(//div/div/h3/a)";
		String PLPPage_ViewCurrentModels = ".//*[@id='tab-a-nav-currentmodels']/span";
		String customizeButton = "(//div[@class='tabbedBrowse-productListing-footer-button-holder']/button[@id='addToCartButtonTop'])[1]";
		Common.scrollToElement(driver, driver.findElement(By.xpath(workstationsPage_WorkstationsTitle + "[" + workstationsCounter + "]")));
		driver.findElement(By.xpath(workstationsPage_WorkstationsTitle + "[" + workstationsCounter + "]")).click();
		Thread.sleep(4000);
		Dailylog.logInfoDB(5, "Series page displays correctly", Store, testName);
		if (Common.isElementExist(driver, By.xpath(workstationsSubSeriesPage_ModalName + "[1]"))) {
			driver.findElement(By.xpath(workstationsSubSeriesPage_ModalName + "[1]")).click();
		}
		Thread.sleep(5000);
		Dailylog.logInfoDB(6, "PLP page displays correctly", Store, testName);
		if (Common.isElementExist(driver, By.xpath(ButtonLocator))) {
			Common.scrollToElement(driver, driver.findElement(By.xpath(PLPPage_ViewCurrentModels)));
			driver.findElement(By.xpath(PLPPage_ViewCurrentModels)).click();
			Thread.sleep(1000);
			Common.scrollToElement(driver, driver.findElement(By.xpath(customizeButton)));
			driver.findElement(By.xpath(customizeButton)).click();
			System.out.println("Customize button is clicked on PLP page.");
			Thread.sleep(4000);
			if (Common.isElementExist(driver, By.xpath(".//*[text()='更改']"))) {
				Dailylog.logInfo("this is new UI");
				// Select Some Software
				driver.findElement(By
						.xpath("(//div[contains(@class,'visible') and not(contains(@class,'is-active'))])[1]//div[contains(@class,'price qa-configurator-groupItem-price')]"))
						.click();
				Common.sleep(3000);
				b2cPage.cto_AddToCartButton.click();
				Thread.sleep(3000);
				Dailylog.logInfoDB(7, "Select First Accessories successfully", Store, testName);
				b2cPage.cto_AddToCartButton.click();
				Thread.sleep(3000);
				System.out.println("Add to Cart button is clicked");
				cartTotalPrice = String2Price(b2cPage.ConvenienceBundle_SubTotal.getText());
				System.out.println("Cart page price : " + cartTotalPrice);
				b2cPage.Cart_CheckoutButton.click();
//				Assert.assertEquals(ctoProductPrice, cartTotalPrice, "PDP page price is equal to cart page price");
			} else {
				Dailylog.logInfo("this is old UI");
				b2cPage.Product_AddToCartBtn.click();
				Thread.sleep(3000);
				// Select Some Software
				b2cPage.PB_SoftwareTab.click();
				String softwareText = b2cPage.oldPDP_softwareText.getText();
				System.out.println("we will check the software name is :" + softwareText);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.oldPDP_softwareText, 60),
						"There is no software to choose");
				Dailylog.logInfoDB(7, "There is no software to choose", Store, testName);
				String webPrice = b2cPage.pb_WebPrice.getText();
				System.out.println("PDP page price : " + webPrice);
				b2cPage.Product_Continue.click();
				b2cPage.Product_Continue.click();
				Thread.sleep(3000);
				System.out.println("Add to Cart button is clicked");
				cartTotalPrice = String2Price(b2cPage.ConvenienceBundle_SubTotal.getText());
				System.out.println("Cart page price : " + cartTotalPrice);
				Assert.assertEquals(String2Price(webPrice), cartTotalPrice, "PDP page price is equal to cart page price");
				b2cPage.Cart_CheckoutButton.click();
			}
		}
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p1", "b2c", "compatibility"})
	public void NA19686(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//Check BANKDEPOSIT payment is added
			Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
			Common.sleep(3000);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			WebElement table = driver.findElement(
					By.id("table_Content/GenericItemList[2]_table"));
			if (!table.getText().contains("BANKDEPOSIT")) {
				Common.rightClick(driver, table.findElement(By.xpath(".//tr[1]")));
				driver.findElement(By.id(
						"Content/GenericItemList[2]_!add_true_label"))
						.click();
				Common.switchToWindow(driver, 1);
				Common.javascriptClick(driver, driver.findElement(By.id("ModalGenericFlexibleSearchOrganizerSearch[CheckoutPaymentType]_searchbutton")));
				Common.javascriptClick(driver, driver.findElement(By.id("StringDisplay[BANKDEPOSIT]_span")));
				driver.findElement(By.id("ModalGenericItemSearchList[CheckoutPaymentType]_use")).click();
				Common.switchToWindow(driver, 0);
				hmcPage.SaveButton.click();
				Thread.sleep(10000);
			}
			hmcPage.HMC_Logout.click();
			
			// step1 login B2C site
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "Successfully Logged into B2C website.", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			B2CCommon.closeHomePagePopUP(driver);
			// step2 PRODUCTS displays correctly
			Assert.assertTrue(b2cPage.Navigation_ProductsLink.isDisplayed(), "PRODUCTS is present");
			Dailylog.logInfoDB(2, "Check the Menu bar contains PRODUCTS", Store, testName);
			// step3 WorkStations displays correctly
			Common.doubleClick(driver, b2cPage.Navigation_ProductsLink);
			/*Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Navigation_WorkStations, 50),
					"WorkStations is present.");*/
			Dailylog.logInfoDB(3, "Check PRODUCTS contains WorkStations", Store, testName);
			// step4 click WorkStations
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.WorkStations);
			Thread.sleep(2000);
			Dailylog.logInfoDB(4, "Splitter page displays correctly", Store, testName);
			Thread.sleep(2000);
			int workStationsCounter = 1;
			String customiseButton = "//a[@id='view-customize']";
			// step5~7 Add "CTO" and "Product Builder" to cart
			addToCartB2C(workStationsCounter, driver, customiseButton);
			Dailylog.logInfoDB(7, "Go to cart page successfully", Store, testName);
			// step8 Check the Product Price,Click Check Out button

			Thread.sleep(5000);
			Dailylog.logInfoDB(8, "Go to shipping page successfully", Store, testName);
			double shippingTotalPrice = String2Price(b2cPage.cartInfo_subTotalAftAnnProd.getText());
			System.out.println("Shipping page price : " + shippingTotalPrice);
			Assert.assertEquals(shippingTotalPrice, cartTotalPrice, "Shipping page price is equal to cart page price");
			if(Common.checkElementInvisible(driver, b2cPage.Shipping_editAddress, 3) )
				b2cPage.Shipping_editAddress.click();
			// step9 Check Shipping information and Fill shippingInfo
			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID());
			b2cPage.Shipping_ContinueButton.click();
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 5)) {
				b2cPage.Shipping_AddressMatchOKButton.click();
				b2cPage.Shipping_ContinueButton.submit();
			}
			Thread.sleep(3000);
			Dailylog.logInfoDB(9, "Go to payment page successfully", Store, testName);
			// step10 Check Paypal Payment,Select "Direct Deposit"
			Actions actions = new Actions(driver);
			//actions.moveToElement(b2cPage.DirectDeposit).click().perform();
			actions.moveToElement(driver.findElement(By.xpath(".//label[@for='PaymentTypeSelection_BANKDEPOSIT']"))).click().perform();

			B2CCommon.fillPaymentAddressInfo(b2cPage, "AutoFirstName", "AutoLastName",
					testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(),
					testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(),
					testData.B2C.getDefaultAddressPhone());
			double paymentSubtotalPrice; 
			if (Common.checkElementExists(driver, b2cPage.checkout_subTotalPrice, 5))
				paymentSubtotalPrice = String2Price(b2cPage.checkout_subTotalPrice.getText());
			else //New UI
				paymentSubtotalPrice = String2Price(driver.findElement(By.className("price-calculator-order-summary-subTotalWithoutCoupon")).getText());
			System.out.println("Payment page subtotal price : " + paymentSubtotalPrice);
			Assert.assertEquals(paymentSubtotalPrice, cartTotalPrice, "Payment page price is equal to cart page price");
			String paymentShippingPrice;
			if (Common.checkElementExists(driver, b2cPage.Payment_ShippingPrice, 5))
				paymentShippingPrice = b2cPage.Payment_ShippingPrice.getText();
			else //New UI
				paymentShippingPrice = driver.findElement(By.xpath("//div[@id='summaryTotalPriceDiv']//span[@id='withTaxTotal']")).getText();
			System.out.println("Payment page Shipping price : " + paymentShippingPrice);
			Common.scrollToElement(driver, b2cPage.Payment_ContinueButton);
			b2cPage.Payment_ContinueButton.click();
			B2CCommon.handleVisaVerify(b2cPage);
			Thread.sleep(6000);
			Dailylog.logInfoDB(10, "Go to Review & Purchase page successfully", Store, testName);
			double orderSummaryTotalPrice;
			if (Common.checkElementExists(driver, b2cPage.OrderSummary_totalPrice, 5))
				orderSummaryTotalPrice = String2Price(b2cPage.OrderSummary_totalPrice.getText());
			else
				orderSummaryTotalPrice = String2Price(driver.findElement(By.xpath("//div[@class='summary-item total']/div[@class='right']")).getText());
			System.out.println("Order summary total price is: " + orderSummaryTotalPrice);
			// step11 Place order
			if (Common.checkElementInvisible(driver, b2cPage.OrderSummary_AcceptTermsCheckBox, 4))
				b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			else
				driver.findElement(By.xpath("//label[@for='Terms1']")).click();
			B2CCommon.clickPlaceOrder(b2cPage);
			Dailylog.logInfoDB(11, "Place order successfully", Store, testName);
			// Get Order number
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(11, "Order number is: " + orderNum, Store, testName);

			Assert.assertEquals(shippingTotalPrice, cartTotalPrice,
					"Order thankyou page subtotal price is equal to cart page price");

			// Get Tax price
			if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'Tax')]/following-sibling::td[1]"))) {
				orderThankyou_TaxPrice = 0.0;
				Dailylog.logInfoDB(11, "Order thankyou tax price is: " + orderThankyou_TaxPrice, Store, testName);
			}
			// Get Total price
			double orderThankyou_TotalPrice;
			if (Common.checkElementExists(driver, b2cPage.OrderThankyou_TotalPriceText, 5))
				orderThankyou_TotalPrice= String2Price((b2cPage.OrderThankyou_TotalPriceText.getText()));
			else
				orderThankyou_TotalPrice = String2Price(driver.findElement(By.xpath("//div[@class='summary-item total']//div[2]")).getText());
			Dailylog.logInfoDB(11, "Order thankyou total price is: " + orderThankyou_TotalPrice, Store, testName);
			Assert.assertEquals(orderThankyou_TotalPrice, orderSummaryTotalPrice,
					"Order thankyou page total price is equal to payment page total price");
			// step12 Check order status in Hmc
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Thread.sleep(8000);
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(orderNum);
			hmcPage.Home_Order_OrderSearch.click();
			System.out.println("Order status in HMC: " + hmcPage.Home_Order_OrderStatus.getText());
			Assert.assertEquals(hmcPage.Home_Order_OrderStatus.getText(), "Completed",
					"Order Status in HMC: " + orderNum);
			Dailylog.logInfoDB(12, "Order status is correct", Store, testName);
			Common.doubleClick(driver, driver.findElement(By.xpath("//*[text()='" + orderNum + "']")));
			String hmcProductTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalPrice')]"))
					.getAttribute("value");
			double hmcProductTotalPrice = String2Price(hmcProductTotal);
			System.out.println("productTotal: " + hmcProductTotalPrice);
			String hmcTaxTotal = driver.findElement(By.xpath("//*[contains(@id,'Order.totalTax')]"))
					.getAttribute("value");
			double hmcTaxTotalPrice = String2Price(hmcTaxTotal);
			System.out.println("taxTotal: " + hmcTaxTotalPrice);
			Assert.assertEquals(hmcTaxTotalPrice, orderThankyou_TaxPrice,
					"Order thankyou page Tax price is equal to HMC page Tax price");
			System.out.println("orderPrice: " + orderThankyou_TotalPrice);
			double totalPrice = hmcTaxTotalPrice + hmcProductTotalPrice;
			totalPrice = convert(totalPrice);
			Assert.assertEquals(totalPrice, orderThankyou_TotalPrice,
					"price in HMC should be the same as the price in order Thankyou page");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
