package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class NA23109Test extends SuperTestClass {
	private B2CPage b2cPage;
	private HMCPage hmcPage;

	public NA23109Test(String store) {
		this.Store = store;
		this.testName = "NA-23109";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2c"})
	public void NA23109(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			// 1. Enable Product Builder
			// try {
			// enablePB();
			// Dailylog.logInfoDB(1, "enbaled product builder in HMC", Store, testName);
			// }catch(TimeoutException e) {
			// Dailylog.logInfoDB(1, "hmc unit setting enable pb timeout", Store, testName);
			// }

			// 2. Go to B2C website
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);

			// 3~4. Select one MTM product to cart
			String mtmProduct = testData.B2C.getDefaultMTMPN();
			// mtmProduct = "80XB0013US";
			Dailylog.logInfoDB(2, "mtmProduct: " + mtmProduct, Store, testName);
			String b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + mtmProduct;
			driver.get(b2cProductUrl);
			Dailylog.logInfoDB(3, "currentUrl: " + driver.getCurrentUrl(), Store, testName);
			// check if product is valid
			if (isAlertPresent()) {
				driver.switchTo().alert().accept();
				Assert.fail("test product is invalid: " + mtmProduct);
			}
			if (!Common.checkElementDisplays(driver,
					By.xpath(Common.convertWebElementToString(b2cPage.Product_AddToCart)), 10))
				Assert.fail("b2cPage.Product_AddToCart does not exist, please update test product");
			Common.scrollToElement(driver, b2cPage.Product_AddToCart);
			Common.javascriptClick(driver, b2cPage.Product_AddToCart);
			Common.sleep(8000);
			boolean isNewUI;
			isNewUI = Common.isElementExist(driver, By.xpath("//div[@class='summary-heading category_title']"), 5);

			// 5. Select option or verify message
			String warrantyMsg = "sorry";
			String accessoryMsg = "sorry";
			String softwareMsg = "sorry";
			if (Store.contains("JP")) {
				warrantyMsg = "大変申し訳ございませんが";
				accessoryMsg = "大変申し訳ございませんが";
				softwareMsg = "大変申し訳ございませんが";
			}
			String warrantyID = "";
			String softwareID = "";
			String accessoryID = "";
			warrantyID = selectOptionPB(warrantyMsg, "Warranty", isNewUI);
			Dailylog.logInfoDB(7, "warrantyID: " + warrantyID, Store, testName);
			softwareID = selectOptionPB(softwareMsg, "Software", isNewUI);
			Dailylog.logInfoDB(7, "softwareID: " + softwareID, Store, testName);
			accessoryID = selectOptionPB(accessoryMsg, "Accessories", isNewUI);
			Dailylog.logInfoDB(7, "accessoryID: " + accessoryID, Store, testName);

			// 6. Click Add to Cart button
			if (isNewUI) {
				try {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				} catch (StaleElementReferenceException e) {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				}
			} else
				b2cPage.PB_addToCart_old.click();
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 10);
			try {
				b2cPage.Cart_configurationDetails.click();
			} catch (StaleElementReferenceException e) {
				b2cPage.Cart_configurationDetails.click();
			}

			if (warrantyID != "")
				checkCartItems(warrantyID, "warranty");
			if (softwareID != "")
				checkCartItems(softwareID, "software");
			if (accessoryID != "")
				checkCartItems(accessoryID, "accessory");

			// 7. Click Edit button under this product Part Number
			Common.scrollToElement(driver, b2cPage.Cart_edit);
			b2cPage.Cart_edit.click();
			String url = driver.getCurrentUrl();
			Dailylog.logInfoDB(9, "url: " + url, Store, testName);
			if (!url.contains("#currentmodels"))
				Assert.fail("step 9, click edit did not navigate to PB");

			// 8.Check every PB Tab
			boolean isSeleted;
			if (warrantyID != "") {
				boolean isNewWarranty = Common.checkElementDisplays(driver, By.id("stackableWarranty"), 3);
				if (isNewWarranty) {
					Common.scrollToElement(driver, b2cPage.PB_warrantyTab);
					b2cPage.PB_warrantyTab.click();
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedWarrantyID_stackable)), 3);
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedWarrantyID_stackable.getAttribute("value"), warrantyID,
								"verify selected warranty");
					else
						Assert.fail("no selected warranty");
				} else if (isNewUI) {
					if (Common.checkElementDisplays(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_warrantyChange)), 5)) {
						Common.scrollToElement(driver, b2cPage.PB_warrantyChange);
						Common.javascriptClick(driver, b2cPage.PB_warrantyChange);
					}
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedWarrantyID)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedWarrantyID.getAttribute("value"), warrantyID,
								"verify selected warranty");
					else
						Assert.fail("no selected warranty");
				} else {
					Common.scrollToElement(driver, b2cPage.PB_warrantyTab);
					b2cPage.PB_warrantyTab.click();
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedWarrantyID_Old)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedWarrantyID_Old.getAttribute("value"), warrantyID,
								"verify selected warranty");
					else
						Assert.fail("no selected warranty");
				}

			}

			if (softwareID != "") {
				if (isNewUI) {
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedSoftwareID)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedSoftwareID.getAttribute("value"), softwareID,
								"verify selected software");
					else
						Assert.fail("no selected software");
				} else {
					Common.scrollToElement(driver, b2cPage.PB_softwareTab);
					b2cPage.PB_softwareTab.click();
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedSoftwareID_Old)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedSoftwareID_Old.getAttribute("value"), softwareID,
								"verify selected software");
					else
						Assert.fail("no selected software");
				}
			}

			if (accessoryID != "") {
				if (isNewUI) {
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedAccessoriesID)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedAccessoriesID.getAttribute("value"), accessoryID,
								"verify selected Accessories");
					else
						Assert.fail("no selected Accessories");
				} else {
					Common.scrollToElement(driver, b2cPage.PB_accessoriesTab);
					b2cPage.PB_accessoriesTab.click();
					isSeleted = Common.isElementExist(driver,
							By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedAccessoriesID_Old)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedAccessoriesID_Old.getAttribute("value"), accessoryID,
								"verify selected Accessories");
					else
						Assert.fail("no selected Accessories");
				}
			}

			// 9.Click Add to Cart Button
			if (isNewUI) {
				try {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				} catch (StaleElementReferenceException e) {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				}
			} else
				b2cPage.PB_addToCart_old.click();
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 10);
			b2cPage.Cart_configurationDetails.click();
			if (warrantyID != "")
				checkCartItems(warrantyID, "warranty");
			if (softwareID != "")
				checkCartItems(softwareID, "software");
			if (accessoryID != "")
				checkCartItems(accessoryID, "accessory");

			// 10.Click Checkout and Place Order
			Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
			String orderNumber = B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData,
					Store);
			Dailylog.logInfoDB(13, "Order number: " + orderNumber, Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void enablePB() {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		if (!hmcPage.B2C_SiteAttribute_EnableProductBuilder.isSelected())
			hmcPage.B2C_SiteAttribute_EnableProductBuilder.click();
		hmcPage.SaveButton.click();
		hmcPage.Home_EndSessionLink.click();
	}

	private boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	private String selectOptionPB(String message, String type, boolean isNewUI) throws InterruptedException {
		String testItem = "";
		String testItemID = "";
		By messageX = By.xpath("//div[contains(@data-tabcode,'" + type + "') or contains(@data-nav-type,'" + type
				+ "') or contains(@data-tabname,'" + type + "')]//div[contains(.,'" + message + "')]");

		System.out.println(messageX.toString());
		boolean isNewWarranty = Common.checkElementDisplays(driver, By.id("stackableWarranty"), 3);

		if (isNewUI) {
			switch (type.toLowerCase()) {
			case "warranty":
				testItem = Common.convertWebElementToString(b2cPage.PB_warrantyChange);
				break;
			case "software":
				testItem = Common.convertWebElementToString(b2cPage.PB_softwareItem);
				break;
			case "accessories":
				testItem = Common.convertWebElementToString(b2cPage.PB_accessoryItem);
				break;
			}
			if (Common.checkElementDisplays(driver, By.xpath(testItem), 10)) {
				if (type.toLowerCase().equals("warranty")) {
					if (!isNewWarranty) {
						Common.scrollToElement(driver, b2cPage.PB_warrantyChange);
						b2cPage.PB_warrantyChange.click();
						testItemID = b2cPage.PB_addWarrantyItemID.getAttribute("value");
						Common.waitElementClickable(driver, b2cPage.PB_addWarrantyItem, 5);
						b2cPage.PB_addWarrantyItem.click();
					} else {
						System.out.println("stackble warranty");
						if (Common.checkElementDisplays(driver,
								By.xpath(Common.convertWebElementToString(b2cPage.PB_warrantyChange)), 3))
							b2cPage.PB_warrantyChange.click();
						if (Common.checkElementExists(driver, b2cPage.PB_stackableWarrantyItem, 3)) {
							b2cPage.PB_stackableWarrantyItem.click();
							Common.sleep(1000);
							testItemID = b2cPage.PB_stackableWarrantyItemID.getAttribute("value");
						} else
							Dailylog.logInfoDB(0, "stackble warranty -- only default warranty", Store, testName);

					}
				} else if (type.toLowerCase().equals("software")) {
					testItemID = b2cPage.PB_addSoftwareItemID.getAttribute("value");
					Common.scrollToElement(driver, b2cPage.PB_softwareItem);
					b2cPage.PB_softwareItem.click();
				} else if (type.toLowerCase().equals("accessories")) {
					testItemID = b2cPage.PB_addAccessoryItemID.getAttribute("value");
					Common.scrollToElement(driver, b2cPage.PB_accessoryItem);
					Common.javascriptClick(driver, b2cPage.PB_accessoryItem);
				}

			} else {
				Boolean isDisplayed = Common.checkElementDisplays(driver, messageX, 10);
				Assert.assertTrue(isDisplayed, "no " + type + " and no message in PB");
				Dailylog.logInfoDB(0, driver.findElement(messageX).getText(), Store, testName);
			}
		} else {
			// TODO: xpath for message on old UI may not correct
			testItem = "//li[@data-tabname='" + type + "']";
			Common.scrollToElement(driver, driver.findElement(By.xpath(testItem)));
			driver.findElement(By.xpath(testItem)).click();
			if (type.toLowerCase().equals("warranty") && isNewWarranty) {
				System.out.println("stackble warranty");
				if (Common.checkElementExists(driver, b2cPage.PB_stackableWarrantyItem, 3)) {
					b2cPage.PB_stackableWarrantyItem.click();
					Common.sleep(1000);
					//get value from li not correct
					testItemID = b2cPage.PB_stackableWarrantyItemID.getAttribute("value");
				} else
					Dailylog.logInfoDB(0, "stackble warranty -- only default warranty", Store, testName);

			} else {
				testItem = "//div[@data-tabname='" + type
						+ "']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']/../input";
				if (Common.checkElementDisplays(driver, By.xpath(testItem), 5)) {
					testItemID = driver.findElement(By.xpath(testItem)).getAttribute("value");
					driver.findElement(By.xpath(testItem)).click();
				} else {
					Boolean isDisplayed = Common.checkElementDisplays(driver, messageX, 10);
					Assert.assertTrue(isDisplayed, "no " + type + " and no message in PB");
				}
			}
		}
		return testItemID;
	}

	private void checkCartItems(String itemID, String type) {
		if (type.toLowerCase().equals("cv")) {
			By check = By.xpath("//*[contains(text(),'" + itemID + "')]");
			if (!Common.checkElementDisplays(driver, check, 10)) {
				for (int i = 0; i < b2cPage.Cart_configurationDetailsItems.size(); i++) {
					String getText = b2cPage.Cart_configurationDetailsItems.get(i).getText();
					// System.out.println(getText.replaceAll(" ", ""));
					// System.out.println(itemID.replaceAll(" ", ""));
					if (getText.replaceAll(" ", "").contains(itemID.replaceAll(" ", "")))
						break;
					else if (i == b2cPage.Cart_configurationDetailsItems.size() - 1)
						Assert.fail("selected cv is not displayed in cart: " + itemID);

					Dailylog.logInfoDB(0, "Cart_configurationDetailsItems: " + i + " " + getText, Store, testName);
				}

			}
		} else {
			By check = By.xpath("//dd[@class='cart-item-addedItem-partNumber' and text()='" + itemID + "']");
			if (!Common.checkElementDisplays(driver, check, 10)) {
				for (int i = 0; i < b2cPage.Cart_addedItem.size(); i++) {
					Dailylog.logInfoDB(0, "Cart_addedItem: " + i + " " + b2cPage.Cart_addedItem.get(i).getText(), Store,
							testName);
				}
				Assert.fail("selected " + itemID + " is not displayed in cart");
			}
		}
	}
}
