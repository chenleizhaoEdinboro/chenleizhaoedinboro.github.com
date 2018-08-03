package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18461Test extends SuperTestClass {
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	private String[] hmcKey = { "firstname", "lastname", "region", "town", "postalcode", "line1", "email", "phone1" };
	private String originalValue = "30";
	private String country;

	public NA18461Test(String store, String country) {
		this.Store = store;
		this.testName = "NA-18461";
		this.country = country;
	}

	@Test(alwaysRun = true, priority = 0, enabled = true,groups= {"contentgroup","storemgmt","p2","b2c"})
	public void NA18461(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			switchAddress(hmcPage, false, true, true);

			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			String mtmProduct = testData.B2C.getDefaultMTMPN();
			//mtmProduct = "81A400AGAU";
			Dailylog.logInfoDB(0, "mtmProduct: " + mtmProduct, Store, testName);
			goToShipping(mtmProduct);

			boolean isNewUI = Common.checkElementDisplays(driver, By.xpath(
					"//form[contains(@id,'addressForm')]//*[@class='field_placeholder checkoutForm-formLabel-required']"),
					3);
			LinkedHashMap<String, String> shippingLabel1 = new LinkedHashMap<String, String>();
			getB2CLabel(shippingLabel1, "shipping", isNewUI);
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			LinkedHashMap<String, String> billingLabel1 = new LinkedHashMap<String, String>();
			getB2CLabel(billingLabel1, "billing", isNewUI);

			WebElement emptyMsg = hmcPage.b2cUnit_addressFieldEmpty;
			WebElement targetTable = hmcPage.b2cUnit_addressFieldAttibutesTable;

			openSiteAttribute();
			boolean isItemsStillExits = false;
			if (Common.checkElementDisplays(driver,
					By.xpath("//div[contains(text(),'Address Field Attributes:')]/../..//select"), 3)) {
				if ((hmcPage.hmc_addressFieldItems.size() == hmcKey.length))
					isItemsStillExits = true;
				else
					removeItemsFromTable(false, false, emptyMsg, targetTable);

			}

			if (isItemsStillExits) {
				editAddressField();
			} else {
				int i = 1;
				for (String element : hmcKey) {
					addressFieldAttributes(element, i + "", false, false);
					++i;
				}
			}

			emptyMsg = hmcPage.b2cUnit_addressNameEmpty;
			targetTable = hmcPage.b2cUnit_addressLabelNameTable;
			removeItemsFromTable(false, false, emptyMsg, targetTable);

			addressLabelName("en", hmcKey, shippingLabel1, false, false, isNewUI);

			switchAddress(hmcPage, true, false, false);

			String validateValue = "35";
			originalValue = validateRule(hmcPage, validateValue, country, true, false);// TODO: LOGIN-> FALSE

			goToShipping(mtmProduct);
			LinkedHashMap<String, String> shippingLabel2 = new LinkedHashMap<String, String>();
			getB2CLabel(shippingLabel2, "shipping", isNewUI);

			// To verify key order
			ArrayList<String> shippingKey1 = new ArrayList<String>();
			for (String key : hmcKey)
				shippingKey1.add(getShippingKey(key, isNewUI));
			for (String element : shippingKey1)
				Dailylog.logInfoDB(0, "shippingKey1: " + element, Store, testName);
			ArrayList<String> shippingKey2 = new ArrayList<String>();
			for (Entry<String, String> element : shippingLabel2.entrySet())
				shippingKey2.add(element.getKey());
			for (String element : shippingKey2)
				Dailylog.logInfoDB(0, "shippingKey2: " + element, Store, testName);
			Assert.assertTrue(shippingKey1.size() == shippingKey2.size(), "shippingKey size not correct");
			for (int n = 0; n < shippingKey1.size(); n++)
				Assert.assertTrue(shippingKey1.get(n).contains(shippingKey2.get(n)), "shippingKey order not correct");

			// To verify values
			ArrayList<String> shippingValue1 = new ArrayList<String>();
			for (String key : shippingKey1)
				shippingValue1.add(shippingLabel1.get(key));
			for (String element : shippingValue1)
				Dailylog.logInfoDB(0, "shippingValue1: " + element, Store, testName);
			ArrayList<String> shippingValue2 = new ArrayList<String>();
			for (String key : shippingKey2)
				shippingValue2.add(shippingLabel2.get(key));
			for (String element : shippingValue2)
				Dailylog.logInfoDB(0, "shippingValue2: " + element, Store, testName);
			// TODO:
			for (int n = 0; n < shippingValue1.size(); n++) {
				System.out.println(shippingValue2.get(n));
				System.out.print(convertLabel(shippingValue1.get(n)) + " Test*");
				Assert.assertEquals(convertLabel(shippingValue2.get(n)) + "*",
						convertLabel(shippingValue1.get(n)) + " Test*", "shippingValue not correct");
			}
			// validtae rule
			Assert.assertEquals(b2cPage.Shipping_FirstNameTextBox.getAttribute("maxlength"), validateValue);

			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			if (Common.checkElementExists(driver, b2cPage.Shipping_copyAddressToBilling, 3))
				Common.javascriptClick(driver, b2cPage.Shipping_copyAddressToBilling);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			LinkedHashMap<String, String> billingLabel2 = new LinkedHashMap<String, String>();
			getB2CLabel(billingLabel2, "billing", isNewUI);

			// To verify key order
			ArrayList<String> billingKey1 = new ArrayList<String>();
			for (String key : hmcKey)
				billingKey1.add(getBillingKey(key, isNewUI));
			for (String element : billingKey1)
				Dailylog.logInfoDB(0, "billingKey1: " + element, Store, testName);
			ArrayList<String> billingKey2 = new ArrayList<String>();
			for (Entry<String, String> element : billingLabel2.entrySet())
				billingKey2.add(element.getKey());
			for (String element : billingKey2)
				Dailylog.logInfoDB(0, "billingKey2: " + element, Store, testName);
			Assert.assertTrue(billingKey1.size() == billingKey2.size(), "billingKey size not correct");
			for (int n = 0; n < billingKey1.size(); n++) {
				if (!isNewUI && billingKey1.get(n).equals("address.townCity"))
					Assert.assertEquals(billingKey2.get(n).toLowerCase(), "address.city",
							"billingKey order not correct");
				else
					Assert.assertTrue(billingKey1.get(n).toLowerCase().contains(billingKey2.get(n).toLowerCase()),
							"billingKey order not correct");
			}

			// To verify values
			ArrayList<String> billingValue1 = new ArrayList<String>();
			for (String key : billingKey1)
				billingValue1.add(billingLabel1.get(key));
			for (String element : billingValue1)
				Dailylog.logInfoDB(0, "billingValue1: " + element, Store, testName);
			ArrayList<String> billingValue2 = new ArrayList<String>();
			for (String key : billingKey2)
				billingValue2.add(billingLabel2.get(key));
			for (String element : billingValue2)
				Dailylog.logInfoDB(0, "billingValue2: " + element, Store, testName);
			// TODO:
			for (int n = 0; n < billingValue1.size(); n++) {
				if (billingValue1.get(n) == null) {
					Assert.assertEquals(convertLabel(billingValue2.get(n)) + "*", "Email Test*", "billingValue not correct");
				} else {
					Assert.assertEquals(convertLabel(billingValue2.get(n)).replace(" ", "") + "*",
							convertLabel(billingValue1.get(n)).replace(" ", "") + "Test*", "billingValue not correct");
				}
			}

			// validtae rule
			if (isNewUI)
				Assert.assertEquals(b2cPage.Payment_FirstNameTextBox_new.getAttribute("maxlength"), validateValue);
			else
				Assert.assertEquals(b2cPage.Payment_FirstNameTextBox.getAttribute("maxlength"), validateValue);

			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Dailylog.logInfoDB(0, "Filled in payment info", Store, testName);

			// continue
			Thread.sleep(5000);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);

			// repid
			try {
				if (Common.checkElementExists(driver, b2cPage.OrderSummary_editableRepID, 5)) {
					Thread.sleep(4000);
					// b2cPage.OrderSummary_editableRepID.clear();
					b2cPage.OrderSummary_editableRepID.sendKeys(testData.B2C.getRepID());
				}
			} catch (InvalidElementStateException ex) {
				System.out.println("InvalidElementStateException");
				if (Common.checkElementExists(driver, b2cPage.OrderSummary_editableRepID, 5)) {
					Thread.sleep(4000);
					// b2cPage.OrderSummary_editableRepID.clear();
					b2cPage.OrderSummary_editableRepID.sendKeys(testData.B2C.getRepID());
				}
			} catch (StaleElementReferenceException ex) {
				System.out.println("StaleElementReferenceException");
				if (Common.checkElementExists(driver, b2cPage.OrderSummary_editableRepID, 5)) {
					Thread.sleep(4000);
					// b2cPage.OrderSummary_editableRepID.clear();
					b2cPage.OrderSummary_editableRepID.sendKeys(testData.B2C.getRepID());
				}
			}

			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			b2cPage.OrderSummary_AcceptTermsCheckBox.click();

			// place order
			Thread.sleep(500);
			B2CCommon.clickPlaceOrder(b2cPage);

			String orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
			Dailylog.logInfoDB(0, "Placed oder: " + orderNum, Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@Test(alwaysRun = true, priority = 1, enabled = true)
	public void Rollback() throws MalformedURLException {
		Dailylog.logInfoDB(0, "start rollback", Store, testName);
		SetupBrowser();
		hmcPage = new HMCPage(driver);
		switchAddress(hmcPage, false, false, true);
		validateRule(hmcPage, originalValue, country, true, false);
	}

	private String convertLabel(String original) {
		String label = "";
		label = original.substring(0, original.indexOf("*"));
		return label;
	}

	private void switchAddress(HMCPage hmcPage, boolean status, boolean isSignOut, boolean isSignIn) {
		if (isSignIn)
			openSiteAttribute();
		if (status)
			hmcPage.b2cUnit_switchAddressYes.click();
		else
			hmcPage.b2cUnit_switchAddressNo.click();
		hmcPage.HMC_Save.click();
		if (isSignOut)
			hmcPage.hmcHome_hmcSignOut.click();
	}

	private void getB2CLabel(LinkedHashMap<String, String> labels, String type, boolean isNewUI) {
		Dailylog.logInfoDB(0, type, Store, testName);
		if (isNewUI) {
			List<WebElement> targetLabel = b2cPage.PB_shippingLabel_new;
			if (type.toLowerCase().equals("billing")) {
				Common.waitElementClickable(driver, b2cPage.PB_isDifferentBillingAddress, 10);
				b2cPage.PB_isDifferentBillingAddress.click();
				targetLabel = b2cPage.PB_billingLabel_new;
			} else {
				if (Common.checkElementDisplays(driver,
						By.xpath(Common.convertWebElementToString(b2cPage.Shipping_editAddress)), 3))
					b2cPage.Shipping_editAddress.click();
			}
			for (int i = 0; i < targetLabel.size(); i++)
				labels.put(targetLabel.get(i).getAttribute("for"), targetLabel.get(i).getText());
			for (Entry<String, String> element : labels.entrySet())
				Dailylog.logInfoDB(0, element.getKey() + " : " + element.getValue(), Store, testName);
		} else {
			List<WebElement> targetLabel = b2cPage.PB_shippingLabel;
			if (type.toLowerCase().equals("billing"))
				targetLabel = b2cPage.PB_billingLabel;
			else {
				if (Common.checkElementDisplays(driver, By.cssSelector(".textLink.checkout-shippingAddress-editLink"),
						3))
					b2cPage.Shipping_editAddress.click();
			}
			for (int i = 0; i < targetLabel.size(); i++)
				labels.put(targetLabel.get(i).getAttribute("for"), targetLabel.get(i).getText());
			for (Entry<String, String> element : labels.entrySet())
				Dailylog.logInfoDB(0, element.getKey() + " : " + element.getValue(), Store, testName);
		}
	}

	private void addressFieldAttributes(String addressFieldValue, String fieldSequence, boolean isSignOut,
			boolean isSignIn) {
		if (isSignIn)
			openSiteAttribute();
		Common.rightClick(driver, hmcPage.b2cUnit_addressFieldAttibutesTable);
		Common.waitElementClickable(driver, hmcPage.b2cUnit_create, 3);
		hmcPage.b2cUnit_create.click();
		Select addressField = new Select(hmcPage.b2cUnit_createB2CAddressFieldSel);
		addressField.selectByVisibleText(addressFieldValue);
		int size = hmcPage.b2cUnit_createB2CAddressFieldUnselectedChk.size();
		for (int i = 0; i < size; i++)
			hmcPage.b2cUnit_createB2CAddressFieldUnselectedChk.get(0).click();
		hmcPage.b2cUnit_createB2CAddressFieldInput.clear();
		hmcPage.b2cUnit_createB2CAddressFieldInput.sendKeys(fieldSequence);
		hmcPage.HMC_Save.click();
		Dailylog.logInfoDB(0, "addressFieldAttributes: " + addressFieldValue, Store, testName);
		if (isSignOut)
			hmcPage.hmcHome_hmcSignOut.click();
	}

	private void editAddressField() {
		Select addressFieldSel;
		int i = 1;
		for (String element : hmcKey) {
			addressFieldSel = new Select(hmcPage.hmc_addressFieldItems.get(i - 1));
			addressFieldSel.selectByVisibleText(element);
			hmcPage.hmc_addressLabelSeq.get(i - 1).clear();
			hmcPage.hmc_addressLabelSeq.get(i - 1).sendKeys(i + "");
			++i;
		}
		while (Common.checkElementDisplays(driver,
				By.xpath("//div[contains(text(),'Address Field Attributes:')]/../..//*[@value='false']/../input[2]"),
				3))
			hmcPage.hmc_addressLabelUnselectChk.get(0).click();
		hmcPage.HMC_Save.click();
	}

	private String getShippingKey(String hmcKey, boolean isNewUI) {
		Map<String, String> map = new HashMap<String, String>();
		if (isNewUI) {
			map.put("firstname", "firstName");
			map.put("lastname", "lastName");
			map.put("town", "townCity");
			map.put("postalcode", "postcode");
			map.put("region", "state");
			map.put("line1", "line1");
			map.put("email", "email");
			map.put("phone1", "phone");
		} else {
			map.put("firstname", "firstName");
			map.put("lastname", "lastName");
			map.put("town", "townCity");
			map.put("postalcode", "postcode");
			map.put("region", "state");
			map.put("line1", "line1");
			map.put("email", "email");
			map.put("phone1", "phone1");
		}
		return map.get(hmcKey);
	}

	private String getBillingKey(String hmcKey, boolean isNewUI) {
		Map<String, String> map = new HashMap<String, String>();
		if (isNewUI) {
			map.put("firstname", "billTo_firstName");
			map.put("lastname", "billTo_lastName");
			map.put("town", "billTo_city");
			map.put("postalcode", "billTo_postalCode");
			map.put("region", "address.region");
			map.put("line1", "billTo_street1");
			map.put("email", "billTo_email");
			map.put("phone1", "billTo_phoneNumber");
		} else {
			map.put("firstname", "address.firstName");
			map.put("lastname", "address.lastname");
			map.put("town", "address.townCity");
			map.put("postalcode", "address.postcode");
			map.put("region", "address.region");
			map.put("line1", "address.line1");
			map.put("email", "address.email");
			map.put("phone1", "PHONE_NUMBER1");
		}
		return map.get(hmcKey);
	}

	private void removeItemsFromTable(boolean isSignOut, boolean isSignIn, WebElement emptyMsg,
			WebElement targetTable) {
		if (isSignIn)
			openSiteAttribute();
		if (!Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(emptyMsg)))) {
			Common.rightClick(driver, targetTable);
			Common.waitElementClickable(driver, hmcPage.b2cUnit_selectAll, 3);
			hmcPage.b2cUnit_selectAll.click();
			Common.sleep(2000);
			Common.rightClick(driver, targetTable);
			Common.waitElementClickable(driver, hmcPage.b2cUnit_remove, 3);
			hmcPage.b2cUnit_remove.click();
			Common.sleep(2000);
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
			hmcPage.HMC_Save.click();
		}
		if (isSignOut)
			hmcPage.hmcHome_hmcSignOut.click();
	}

	private void addressLabelName(String language, String[] hmcKey, LinkedHashMap<String, String> shippingLabel1,
			boolean isSignOut, boolean isSignIn, boolean isNewUI) {
		if (isSignIn)
			openSiteAttribute();
		for (String element : hmcKey)
			addAddressLabelName(element, language,
					convertLabel(shippingLabel1.get(getShippingKey(element, isNewUI))) + " Test");
		if (isSignOut)
			hmcPage.hmcHome_hmcSignOut.click();
	}

	private void addAddressLabelName(String addressFieldValue, String language, String labelNameValue) {
		Common.rightClick(driver, hmcPage.b2cUnit_addressLabelNameTable);
		Common.waitElementClickable(driver, hmcPage.b2cUnit_create, 3);
		hmcPage.b2cUnit_create.click();
		switchToWindow(1);
		Select addressField = new Select(hmcPage.b2cUnit_labelName_addressField);
		addressField.selectByVisibleText(addressFieldValue);
		By labelName = By.xpath("(//td[@class='langCode']/div[contains(text(),'" + language + "')]/../..//input)[1]");
		if (!Common.checkElementDisplays(driver,
				By.xpath("//a[contains(@id,'labelName') and contains(@class,'collapse')]"), 2)) {
			driver.findElement(By.xpath("//a[contains(@id,'labelName')]")).click();
			Common.sleep(500);
		}
		driver.findElement(labelName).clear();
		driver.findElement(labelName).sendKeys(labelNameValue);
		Common.javascriptClick(driver, hmcPage.HMC_Save);
		driver.close();
		switchToWindow(0);
		hmcPage.HMC_Save.click();
		Dailylog.logInfoDB(0, "addAddressLabelName: " + labelNameValue, Store, testName);
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			Common.sleep(1000);
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			Dailylog.logInfoDB(0, "Swith to window timeout, windowNo: " + windowNo, Store, testName);
		} catch (InterruptedException e) {

		}
	}

	private void openSiteAttribute() {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2CUnit(hmcPage, testData);
		Common.sleep(2000);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
	}

	// edit firstName max length
	private String validateRule(HMCPage hmcPage, String value, String country, boolean isSignOut, boolean isSignIn) {
		String fieldValue = "30";
		if (isSignIn) {
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
		}
		hmcPage.hmc_internationalization.click();
		Common.waitElementClickable(driver, hmcPage.hmc_addressValidatorRule, 3);
		hmcPage.hmc_addressValidatorRule.click();
		Common.sleep(2000);
		Select countrySel = new Select(
				driver.findElement(By.xpath("//select[contains(@id,'AllInstancesSelectEditor')]")));
		countrySel.selectByVisibleText(country);
		hmcPage.AddCountry_Search.click();
		driver.findElement(By.xpath("//img[contains(@id,'OrganizerListEntry')]")).click();
		fieldValue = hmcPage.addressValidatorRule_firstnameMaxLength.getAttribute("value");
		hmcPage.addressValidatorRule_firstnameMaxLength.clear();
		hmcPage.addressValidatorRule_firstnameMaxLength.sendKeys(value);
		hmcPage.HMC_Save.click();
		if (isSignOut)
			hmcPage.hmcHome_hmcSignOut.click();
		return fieldValue;
	}

	private void goToShipping(String mtmProduct) throws InterruptedException {
		String b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + mtmProduct;
		driver.get(b2cProductUrl);
		Dailylog.logInfoDB(0, "currentUrl: " + driver.getCurrentUrl(), Store, testName);
		if (Common.isAlertPresent(driver)) {
			driver.switchTo().alert().accept();
			Assert.fail("test product is invalid: " + mtmProduct);
		}
		if (!Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.Product_AddToCart)),
				10))
			Assert.fail("b2cPage.Product_AddToCart does not exist, please update test product");
		Common.scrollToElement(driver, b2cPage.Product_AddToCart);
		Common.javascriptClick(driver, b2cPage.Product_AddToCart);
		Common.sleep(8000);
		boolean isNewUI;
		isNewUI = Common.isElementExist(driver, By.xpath("//div[@class='summary-heading category_title']"), 5);
		if (!isNewUI) {
			b2cPage.PB_unVisitedTab.click();
			Common.waitElementClickable(driver, b2cPage.Product_Productbuilder_AddToCartBtn, 5);
			b2cPage.Product_Productbuilder_AddToCartBtn.click();
		} else {
			Common.waitElementClickable(driver, b2cPage.PDP_AddToCartButton1, 5);
			b2cPage.PDP_AddToCartButton1.click();
		}
		Common.waitElementClickable(driver, b2cPage.Cart_CheckoutButton, 5);
		Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
		b2cPage.Cart_CheckoutButton.click();
		if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 5))
			b2cPage.Checkout_StartCheckoutButton.click();
	}

}
