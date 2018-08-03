package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.PaymentType;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18253Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	boolean flag = false;
	String OrderTaxPrice;
	String OrderTotalPrice;

	public NA18253Test(String store) {
		this.Store = store;
		this.testName = "NA-18253";
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "payment", "p1", "b2c", "compatibility" })
	public void NA18253(ITestContext ctx) {
		try {
			this.prepareTest();

			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			//
			Dailylog.logInfoDB(1, "Login to HMC..", Store, testName);
//			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
//			HMCCommon.Login(hmcPage, testData);
//			Thread.sleep(5000);
//			HMCCommon.searchB2CUnit(hmcPage, testData);
//			Thread.sleep(2000);
//			hmcPage.B2CUnit_FirstSearchResultItem.click();
//			Thread.sleep(2000);
//			hmcPage.B2CUnit_SiteAttributeTab.click();
//			Thread.sleep(2000);
//			WebElement paymentTypeOptions = driver
//					.findElement(By.xpath(".//table[@id='table_Content/GenericItemList[1]_table']/tbody/tr"));
//			Dailylog.logInfoDB(2, paymentTypeOptions.isDisplayed(), Store, testName);
//			Assert.assertTrue(paymentTypeOptions.isDisplayed());
//			Thread.sleep(5000);
//			int flag = 0;
//			if (paymentTypeOptions.isDisplayed()) {
//				int count = driver
//						.findElements(By.xpath(".//table[@id='table_Content/GenericItemList[1]_table']/tbody/tr"))
//						.size();
//				Dailylog.logInfoDB(1, "Count is : " + count, Store, testName);
//				for (int i = 2; i <= count; i++) {
//					String paymentType = driver.findElement(By.xpath(
//							".//table[@id='table_Content/GenericItemList[1]_table']/tbody/tr[" + i + "]/td[3]/div/div"))
//							.getText();
//					Dailylog.logInfoDB(3, "Payment mode is : " + paymentType, Store, testName);
//					if (paymentType.equals("JACCS")) {
//						flag = 1;
//						Dailylog.logInfoDB(1, "JACCS Payment option is already present!!!.", Store, testName);
//						break;
//					}
//				}
//				if (flag == 0) {
//					addJACCSPaymentOption(hmcPage, paymentTypeOptions);
//				}
//			} else {
//				addJACCSPaymentOption(hmcPage, paymentTypeOptions);
//			}

			B2CPage b2cPage = new B2CPage(driver);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");

			B2CCommon.handleGateKeeper(b2cPage, testData);

			// Quick order
			String defaultMtm = testData.B2C.getDefaultMTMPN();
			String Accessory = testData.B2C.getDefaultAccessoryPN();
			String defaultMtm2 = testData.B2C.getDefaultMTMPN2();
			Dailylog.logInfoDB(8, "defaultMtm : " + defaultMtm, Store, testName);
			Dailylog.logInfoDB(8, "Accessory : " + Accessory, Store, testName);
			Dailylog.logInfoDB(8, "defaultMtm2 : " + defaultMtm2, Store, testName);
			B2CCommon.addPartNumberToCart(b2cPage, defaultMtm);
			B2CCommon.addPartNumberToCart(b2cPage, Accessory);
			B2CCommon.addPartNumberToCart(b2cPage, defaultMtm2);
			// update Quantity
			String mtmprice = updateQuantity(defaultMtm, "2");
			String accessoryPrice = updateQuantity(Accessory, "3");
			String mtm2price = updateQuantity(defaultMtm2, "4");

			Common.scrollAndClick(driver, b2cPage.Cart_CheckoutButton);
			Thread.sleep(2000);

			// Click on guest checkout button if exists
			if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5)) {
				b2cPage.Checkout_StartCheckoutButton.click();
			}

			// Fill default shipping address
			if (Common.checkElementExists(driver, b2cPage.Shipping_FirstNameTextBox, 5)) {
				fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
						testData.B2C.getLoginID(), testData.B2C.getConsumerTaxNumber());
			}

			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			B2CCommon.handleAddressVerify(driver, b2cPage);

			// Payment
			if (CommonFunction.DesignHandler.Payment.isPaymentMethodExists(b2cPage, PaymentType.JACCS_B2C)) {

				Payment.payAndContinue(b2cPage, PaymentType.JACCS_B2C, testData);

				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);
				// Get Order number
				String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);

				Dailylog.logInfoDB(8, b2cPage.ThankyouMessage.getText(), Store, testName);
				String OrderPageTaxPrice;
				String OrderPageTotalPrice;

				/*
				 * if(Common.checkElementDisplays(driver,
				 * b2cPage.OrderPage_TaxTotalPrice.get(0), 5)){
				 * OrderPageTaxPrice=b2cPage.OrderPage_TaxTotalPrice.get(0).
				 * getText();
				 * OrderPageTotalPrice=b2cPage.OrderPage_TaxTotalPrice.get(1).
				 * getText(); }else{
				 * OrderPageTaxPrice=b2cPage.NewThankPage__TotalTax.getText();
				 * OrderPageTotalPrice=b2cPage.NewThankPage__TotalPrice.getText(
				 * ); }
				 */
				OrderPageTaxPrice = b2cPage.NewThankPage__TotalTax.getText();
				OrderPageTotalPrice = b2cPage.NewThankPage__TotalPrice.getText();
				OrderTaxPrice = GetPriceValue(OrderPageTaxPrice);
				OrderTotalPrice = GetPriceValue(OrderPageTotalPrice);
				Dailylog.logInfoDB(8, "OrderTaxPrice : " + OrderTaxPrice, Store, testName);
				Dailylog.logInfoDB(8, "OrderTotalPrice : " + OrderTotalPrice, Store, testName);
				// Assert.assertEquals(b2cPage.ThankyouMessage.getText(),
				// "Thank you for your order.");
				// Thread.sleep(2000);
				b2cPage.Jaccs_Post.click();
				Thread.sleep(2000);

				// Verify HMC value
				Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
				HMCPage hmcPage = new HMCPage(driver);
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.HMCOrderCheck(driver, hmcPage, orderNum);
				// HMCCommon.HMCOrderCheck(driver, hmcPage, "4291391619");

				// Validate YB06 in xml
				if (!HMCCommon.GetYB06Value(hmcPage).equals("J"))
					Assert.fail("YB06 value is wrong");

				Dailylog.logInfoDB(1, "Order Number is: " + orderNum, this.Store, this.testName);
			} else {
				Assert.fail("JACCS is not configured yet!");
			}

			hmcPage.PaymentAndDeliveryTab.click();
			Common.rightClick(driver, hmcPage.PaymentInfoLabel);
			driver.findElement(
					By.id("Content/GenericReferenceEditor[in Content/Attribute[Order.paymentInfo]]_!open_editor_internal_true_label"))
					.click();

			driver.findElement(By.xpath("//span[contains(@id,'administration')]")).click();

			// check zPostJaccsData: Product name, quantity, price and total
			// price.

			String zPostJaccsXML = hmcPage.zPostJaccsData.getAttribute("value");
			JsonParser parser = new JsonParser();
			JsonObject object = (JsonObject) parser.parse(zPostJaccsXML);

			Dailylog.logInfoDB(8, "tax= " + object.get("sYOHIZEI").getAsString(), Store, testName);
			Dailylog.logInfoDB(8, "totalPrice=" + object.get("gOUKEIGAKU").getAsString(), Store, testName);

			Assert.assertTrue(OrderTaxPrice.contains(object.get("sYOHIZEI").getAsString()));
			Assert.assertEquals(object.get("gOUKEIGAKU").getAsString(), OrderTotalPrice);
			JsonArray array = object.get("jaccsOrderEntries").getAsJsonArray();
			for (int i = 0; i < array.size(); i++) {

				JsonObject subObject = array.get(i).getAsJsonObject();
				String productNo = subObject.get("sINAMEI").getAsString();
				String productprice = subObject.get("kINGAKU").getAsString();
				String productquantity = subObject.get("sURYOU").getAsString();

				if (productNo.equals(defaultMtm)) {

					Assert.assertEquals(mtmprice, productprice);
					Assert.assertEquals(productquantity, "2");

				} else if (productNo.equals(Accessory)) {
					Assert.assertEquals(accessoryPrice, productprice);
					Assert.assertEquals(productquantity, "3");
				} else {
					Assert.assertEquals(mtm2price, productprice);
					Assert.assertEquals(productquantity, "4");
				}

			}
			// // login to B2C account
			// Dailylog.logInfoDB(4, "Login to JP : B2C Site !!!", Store,
			// testName);
			// Common.NavigateToUrl(testData.B2C.getloginPageUrl());
			// Thread.sleep(2000);
			// B2CCommon.handleGateKeeper(b2cPage, testData);
			// B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
			// testData.B2C.getLoginPassword());
			// Thread.sleep(2000);
			// String productNumber = testData.B2C.getDefaultMTMPN();
			// // "ZA160035JP"
			//
			// Common.NavigateToUrl(testData.B2C.getHomePageUrl() + "/cart");
			// B2CCommon.addPartNumberToCart(b2cPage, productNumber);
			// Dailylog.logInfoDB(4, "quick order " + productNumber, Store,
			// testName);
			// b2cPage.lenovo_checkout.click();
			//
			// B2CCommon.fillShippingInfo(b2cPage, "Young", "Meng",
			// testData.B2C.getDefaultAddressLine1(),
			// testData.B2C.getDefaultAddressCity(),
			// testData.B2C.getDefaultAddressState(),
			// testData.B2C.getDefaultAddressPostCode(),
			// testData.B2C.getDefaultAddressPhone(),
			// testData.B2C.getLoginID());
			// b2cPage.Shipping_ContinueButton.submit();
			// if (Common.checkElementExists(driver,
			// b2cPage.Shipping_AddressMatchOKButton, 10)) {
			// b2cPage.Shipping_AddressMatchOKButton.click();
			// b2cPage.Shipping_ContinueButton.submit();
			// }
			// b2cPage.Jaccs_PaymentMode.click();
			// b2cPage.ContinueforPayment.click();
			// Thread.sleep(2000);
			// String price = b2cPage.SubTotalPrice.getText();
			// Dailylog.logInfoDB(7, b2cPage.SubTotal.getText() + " : " + price,
			// Store, testName);
			// b2cPage.OrderSummary_AcceptTermsCheckBox.click();
			// b2cPage.OrderSummary_PlaceOrderButton.click();
			// Dailylog.logInfoDB(8, b2cPage.ThankyouMessage.getText(), Store,
			// testName);
			// Assert.assertEquals(b2cPage.ThankyouMessage.getText(),
			// "Thank you for your order.");
			// Thread.sleep(2000);
			// b2cPage.Jaccs_Post.click();
			// Thread.sleep(2000);
			// switchToWindow(1);
			// b2cPage.Jaccs_TB_closeWindowButton6.click();
			// Thread.sleep(2000);
			// b2cPage.Jaccs_agree1.click();
			// b2cPage.Jaccs_agree2.click();
			// b2cPage.Jaccs_agree3.click();
			// b2cPage.Jaccs_buttonNextEn.click();
			// // b2cPage.next.click();
			// b2cPage.Jaccs_PaymentMethod.click();
			// b2cPage.Jaccs_dropdown.click();
			// b2cPage.Jaccs_option.click();
			// b2cPage.Jaccs_accept2.click();
			// b2cPage.Jaccs_next.click();
			// b2cPage.Jaccs_next2.click();
			//
			// b2cPage.Jaccs_firstName.clear();
			// b2cPage.Jaccs_firstName.sendKeys("??");
			// b2cPage.Jaccs_lastName.clear();
			// b2cPage.Jaccs_lastName.sendKeys("??");
			// b2cPage.Jaccs_namePhonetic.clear();
			// b2cPage.Jaccs_namePhonetic.sendKeys("???");
			// b2cPage.Jaccs_surnamePhonetic.clear();
			// b2cPage.Jaccs_surnamePhonetic.sendKeys("???");
			// b2cPage.Jaccs_gender.click();
			//
			// b2cPage.Jaccs_year.clear();
			// b2cPage.Jaccs_year.sendKeys("1990");
			// b2cPage.Jaccs_month.clear();
			// b2cPage.Jaccs_month.sendKeys("12");
			// b2cPage.Jaccs_day.clear();
			// b2cPage.Jaccs_day.sendKeys("22");
			// b2cPage.Jaccs_getDetails.click();
			// WebElement addressDetails = driver.findElement(By
			// .xpath(".//*[@id='addresslink']"));
			// Common.doubleClick(driver, addressDetails);
			//
			// b2cPage.Jaccs_buildingName.clear();
			// b2cPage.Jaccs_buildingName.sendKeys("??????????");
			// b2cPage.Jaccs_buildingNamePhonetic.clear();
			// b2cPage.Jaccs_buildingNamePhonetic.sendKeys("??????????");
			// b2cPage.Jaccs_phoneNo1.clear();
			// b2cPage.Jaccs_phoneNo1.sendKeys("050");
			// b2cPage.Jaccs_phoneNo2.clear();
			// b2cPage.Jaccs_phoneNo2.sendKeys("0120");
			// b2cPage.Jaccs_phoneNo3.clear();
			// b2cPage.Jaccs_phoneNo3.sendKeys("1256");
			// b2cPage.Jaccs_ownOwnership.click();
			// b2cPage.Jaccs_householdMortgage.click();
			// b2cPage.Jaccs_residenceLife.clear();
			// b2cPage.Jaccs_residenceLife.sendKeys("20");
			// b2cPage.Jaccs_houseWife.click();
			// b2cPage.Jaccs_spouse.click();
			// b2cPage.Jaccs_childrenDetails.click();
			// b2cPage.Jaccs_childrenDetailsOption.click();
			// b2cPage.Jaccs_familyDetails.click();
			// b2cPage.Jaccs_familyDetailsOption.click();
			//
			// b2cPage.Jaccs_presenceOfFamily.click();
			// b2cPage.Jaccs_headOfHouseHold.click();
			// b2cPage.Jaccs_livingSituation.click();
			// b2cPage.Jaccs_livingSituationOption.click();
			//
			// b2cPage.Jaccs_next3.click();
			// b2cPage.Jaccs_next4.click();
			//
			// Dailylog.logInfoDB(8,
			// b2cPage.Jaccs_formCompeletionInfo.getText(),
			// Store, testName);
			// Dailylog.logInfoDB(8,
			// b2cPage.Jaccs_formCompeletionInfo2.getText(),
			// Store, testName);
			//
			// b2cPage.Jaccs_submit.click();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void fillShippingInfo(B2CPage b2cPage, String firstName, String lastName, String addressline1, String city,
			String state, String postCode, String phone, String mail, String... consumerTaxNumber) {
		if (Common.checkElementDisplays(b2cPage.PageDriver, b2cPage.Shipping_FirstNameTextBox, 3)) {
			b2cPage.Shipping_FirstNameTextBox.clear();
			b2cPage.Shipping_FirstNameTextBox.sendKeys(firstName);
			b2cPage.Shipping_LastNameTextBox.clear();
			b2cPage.Shipping_LastNameTextBox.sendKeys(lastName);
			Select stateDropdown = new Select(b2cPage.Shipping_StateDropdown);
			stateDropdown.selectByVisibleText(state);

			b2cPage.Shipping_AddressLine1TextBox.clear();
			b2cPage.Shipping_AddressLine1TextBox.sendKeys(addressline1);
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_CityTextBox, 1)) {
				b2cPage.Shipping_CityTextBox.clear();
				b2cPage.Shipping_CityTextBox.sendKeys(city);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_AddressLine3TextBox, 1)) {
				b2cPage.Shipping_AddressLine3TextBox.clear();
				b2cPage.Shipping_AddressLine3TextBox.sendKeys(city);
			}

			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_PostCodeTextBox, 1)) {
				b2cPage.Shipping_PostCodeTextBox.clear();
				b2cPage.Shipping_PostCodeTextBox.sendKeys(postCode);
			}
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.Shipping_consumerTaxNumber, 1)) {
				b2cPage.Shipping_consumerTaxNumber.clear();
				b2cPage.Shipping_consumerTaxNumber.sendKeys(consumerTaxNumber);
			}
			b2cPage.Shipping_ContactNumTextBox.clear();
			b2cPage.Shipping_ContactNumTextBox.sendKeys(phone);
			b2cPage.Shipping_EmailTextBox.clear();
			b2cPage.Shipping_EmailTextBox.sendKeys(mail);

			// BR
			if (Common.isElementExist(b2cPage.PageDriver, By.id("building"))) {
				b2cPage.PageDriver.findElement(By.id("building")).clear();
				b2cPage.PageDriver.findElement(By.id("building")).sendKeys(city);
			}
			if (Common.isElementExist(b2cPage.PageDriver, By.id("line2"))) {
				b2cPage.PageDriver.findElement(By.id("line2")).clear();
				b2cPage.PageDriver.findElement(By.id("line2")).sendKeys(addressline1);
			}
			if (Common.isElementExist(b2cPage.PageDriver, By.id("district"))) {
				b2cPage.PageDriver.findElement(By.id("district")).clear();
				b2cPage.PageDriver.findElement(By.id("district")).sendKeys("São João Batista");
			}
		}
	}

	public void addJACCSPaymentOption(HMCPage hmcPage, WebElement paymentTypeOptions) {
		Common.rightClick(driver, paymentTypeOptions);
		hmcPage.AddCheckoutPaymentType.click();
		switchToWindow(1);
		driver.findElement(By.xpath(".//input[contains(@id,'CheckoutPaymentType.code')]")).clear();

		driver.findElement(By.xpath(".//input[contains(@id,'CheckoutPaymentType.code')]")).sendKeys("JACCS");
		hmcPage.baseStore_search.click();
		WebElement selectJaccsPaymentOption = driver.findElement(By.xpath(".//*[@id='StringDisplay[JACCS]_span']"));
		Common.doubleClick(driver, selectJaccsPaymentOption);
		switchToWindow(0);
		hmcPage.Common_SaveButton.click();
		Dailylog.logInfoDB(3, "JACCS Payment option is added Successfully!!!.", Store, testName);

	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String updateQuantity(String productNo, String quantity) {
		driver.findElement(
				By.xpath("//input[@name='productCode' and @value='" + productNo + "']/../input[@name='quantity']"))
				.clear();
		driver.findElement(
				By.xpath("//input[@name='productCode' and @value='" + productNo + "']/../input[@name='quantity']"))
				.sendKeys(quantity);
		Common.javascriptClick(driver, driver.findElement(By.xpath(
				"//input[@name='productCode' and @value='" + productNo + "']/../input[contains(@class,'update')]")));
		Common.sleep(3000);

		String priceText = driver
				.findElement(By.xpath("//div[@data-p-code='" + productNo + "']//dl[contains(@class,'salePrice')]"))
				.getText();
		String price = GetPriceValue(priceText);
		String decimalPoint = price.substring(0, price.length() - 2);
		Dailylog.logInfoDB(3, productNo + " price : " + decimalPoint, Store, testName);

		return price;
	}

	public static String GetPriceValue(String Price) {
		if (Price.contains("FREE") || Price.contains("INCLUDED")|| Price.contains("送料無料") ) {
			return "0";
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("\\￥", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("₹", "");
		Price = Price.replaceAll("/yer", "");
		Price = Price.trim();

		return Price;
	}
}
