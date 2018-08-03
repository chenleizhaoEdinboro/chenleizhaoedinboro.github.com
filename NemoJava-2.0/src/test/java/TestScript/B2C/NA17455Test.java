package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class NA17455Test extends SuperTestClass {
	private B2CPage b2cPage;
	private HMCPage hmcPage;

	public NA17455Test(String store) {
		this.Store = store;
		this.testName = "NA-17455";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void NA17455(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			// 1. Enable Product Builder
			// try {
			// enablePB();
			// Dailylog.logInfoDB(1, "enbaled product builder in HMC", Store, testName);
			// } catch (TimeoutException e) {
			// Dailylog.logInfoDB(1, "hmc unit setting enable pb timeout", Store, testName);
			// }

			// 2. Go to B2C website
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);

			// 3~4. Go to CTO page
			String b2cProductUrl = B2CCommon.getCTOProduct(testData.B2C.getHomePageUrl(), Store) + "/customize?";
			Dailylog.logInfoDB(2, "b2cProductUrl: " + b2cProductUrl, Store, testName);
			driver.get(b2cProductUrl);
			Dailylog.logInfoDB(3, "currentUrl: " + driver.getCurrentUrl(), Store, testName);
			String ctoProduct = getCTOID();
			Dailylog.logInfoDB(4, "ctoProduct: " + ctoProduct, Store, testName);
			// check if product is valid
			if (isAlertPresent()) {
				driver.switchTo().alert().accept();
				Assert.fail("test product is invalid: " + ctoProduct);
			}

			if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 5)) {
				ctoProduct = testData.B2C.getDefaultCTOPN();
				b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?";
				driver.get(b2cProductUrl);
				Common.sleep(3000);
				if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 5))
					Assert.fail("continue customising is not valid on page, please update test product");
			}

			boolean isNewUI;
			isNewUI = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_newconfiguratorHeader)));

			// 5. Change C&V on the CTO page
			// 没有ID只能用Text验证
			String cvText = "";
			cvText = selectCVCTO(isNewUI);

			// 6. Click Add to Cart button
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			} catch (StaleElementReferenceException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			}
			Common.sleep(5000);

			// 7. Select option or verify message
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

			// 8. Click Add to Cart button
			if (isNewUI) {
				try {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				} catch (StaleElementReferenceException e) {
					Common.javascriptClick(driver, b2cPage.PDP_AddToCartButton1);
				}
			} else
				b2cPage.Product_Productbuilder_AddToCartBtn.click();
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 10);
			b2cPage.Cart_configurationDetails.click();
			if (cvText != "")
				checkCartItems(cvText, "cv");
			if (warrantyID != "")
				checkCartItems(warrantyID, "warranty");
			if (softwareID != "")
				checkCartItems(softwareID, "software");
			if (accessoryID != "")
				checkCartItems(accessoryID, "accessory");

			// 9. Click Edit button under this product Part Number
			Common.scrollToElement(driver, b2cPage.Cart_edit);
			b2cPage.Cart_edit.click();
			String url = driver.getCurrentUrl();
			Dailylog.logInfoDB(9, "url: " + url, Store, testName);
			if (!url.contains("customize?guid"))
				Assert.fail("step 9, click edit did not navigate to CTO configuration page");

			if (cvText != "") {
				// verify correct CV is selected
				String cvInput = "//span[text()='" + cvText + "']/../../input";
				// to handle text not same
				if (!Common.checkElementDisplays(driver, By.xpath("cvInput"), 10)) {
					for (int i = 0; i < b2cPage.CTO_selectedCVText.size(); i++) {
						String getText = b2cPage.CTO_selectedCVText.get(i).getText();
						if (getText.replaceAll(" ", "").contains(cvText.replaceAll(" ", "")))
							break;
						else if (i == b2cPage.CTO_selectedCVText.size() - 1)
							Assert.fail("selected cv is not displayed in cart: " + cvText);
					}

				} else
					Assert.assertTrue(driver.findElement(By.xpath(cvInput)).getAttribute("class").contains("checked"));
			}

			// 10. Click continue customizing
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			Common.sleep(8000);

			// 11.Check every PB Tab
			boolean isSeleted;
			if (warrantyID != "") {
				boolean isNewWarranty = Common.checkElementDisplays(driver, By.id("stackableWarranty"), 3);
				if (isNewWarranty) {
					Common.scrollToElement(driver, b2cPage.PB_warrantyTab);
					b2cPage.PB_warrantyTab.click();
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedWarrantyID_stackable)), 3);
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedWarrantyID_stackable.getAttribute("value"), warrantyID, "verify selected warranty");
					else
						Assert.fail("no selected warranty");
				} else if (isNewUI) {
					if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_warrantyChange)), 5)) {
						Common.scrollToElement(driver, b2cPage.PB_warrantyChange);
						Common.javascriptClick(driver, b2cPage.PB_warrantyChange);
					}
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedWarrantyID)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedWarrantyID.getAttribute("value"), warrantyID, "verify selected warranty");
					else
						Assert.fail("no selected warranty");
				} else {
					Common.scrollToElement(driver, b2cPage.PB_warrantyTab);
					b2cPage.PB_warrantyTab.click();
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedWarrantyID_Old)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedWarrantyID_Old.getAttribute("value"), warrantyID, "verify selected warranty");
					else
						Assert.fail("no selected warranty");
				}

			}

			if (softwareID != "") {
				if (isNewUI) {
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedSoftwareID)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedSoftwareID.getAttribute("value"), softwareID, "verify selected software");
					else
						Assert.fail("no selected software");
				} else {
					Common.scrollToElement(driver, b2cPage.PB_softwareTab);
					b2cPage.PB_softwareTab.click();
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedSoftwareID_Old)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedSoftwareID_Old.getAttribute("value"), softwareID, "verify selected software");
					else
						Assert.fail("no selected software");
				}
			}

			if (accessoryID != "") {
				if (isNewUI) {
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedAccessoriesID)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedAccessoriesID.getAttribute("value"), accessoryID, "verify selected Accessories");
					else
						Assert.fail("no selected Accessories");
				} else {
					Common.scrollToElement(driver, b2cPage.PB_accessoriesTab);
					b2cPage.PB_accessoriesTab.click();
					isSeleted = Common.isElementExist(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_selectedAccessoriesID_Old)));
					if (isSeleted)
						Assert.assertEquals(b2cPage.PB_selectedAccessoriesID_Old.getAttribute("value"), accessoryID, "verify selected Accessories");
					else
						Assert.fail("no selected Accessories");
				}
			}

			// 12.Click Add to Cart Button
			if (isNewUI)
				b2cPage.PDP_AddToCartButton1.click();
			else
				b2cPage.Product_Productbuilder_AddToCartBtn.click();
			Common.waitElementClickable(driver, b2cPage.Cart_configurationDetails, 10);
			b2cPage.Cart_configurationDetails.click();
			if (cvText != "")
				checkCartItems(cvText, "cv");
			if (warrantyID != "")
				checkCartItems(warrantyID, "warranty");
			if (softwareID != "")
				checkCartItems(softwareID, "software");
			if (accessoryID != "")
				checkCartItems(accessoryID, "accessory");

			// 13.Click Checkout and Place Order
			Common.scrollToElement(driver, b2cPage.Cart_CheckoutButton);
			String orderNumber = B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData, Store);
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

	private String getCTOID() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		String ctoProduct = b2cProductUrl.split("/p/")[1].split("#")[0].substring(0, 15);
		return ctoProduct;
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
		By messageX = By.xpath("//div[contains(@data-tabcode,'" + type + "') or contains(@data-nav-type,'" + type + "') or contains(@data-tabname,'" + type + "')]//div[contains(.,'" + message + "')]");

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
						b2cPage.PB_addWarrantyItem.click();
					} else {
						System.out.println("stackble warranty");
						if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.PB_warrantyChange)), 3))
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
					// get value from li not correct
					testItemID = b2cPage.PB_stackableWarrantyItemID.getAttribute("value");
				} else
					Dailylog.logInfoDB(0, "stackble warranty -- only default warranty", Store, testName);

			} else {
				testItem = "//div[@data-tabname='" + type + "']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']/../input";
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

	private String selectCVCTO(boolean isNewUI) {
		String cvText = "";
		String cvPrice = "";

		if (isNewUI) {
			By expandCategoriesX = By.xpath(Common.convertWebElementToString(b2cPage.cto_expandCategories));
			if (Common.checkElementDisplays(driver, expandCategoriesX, 3))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.cto_expandCategories);
			if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItem)), 3)) {
				cvText = b2cPage.CTO_unselectedItemText.getText();
				cvPrice = b2cPage.CTO_unselectedItemPrice.getText();
				Dailylog.logInfoDB(5, "cvText: " + cvText, Store, testName);
				Dailylog.logInfoDB(5, "cvPrice: " + cvPrice, Store, testName);
				// b2cPage.CTO_unselectedItem.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.CTO_unselectedItem);
				// TODO 价格
			} else {
				Dailylog.logInfoDB(5, "change cv in CTO page failed, no cv to change", Store, testName);
			}
		} else {
			if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld)), 3)) {
				cvText = b2cPage.CTO_unselectedItemTextOld.getText();
				cvPrice = b2cPage.CTO_unselectedItemPriceOld.getText();
				System.out.println(Common.convertWebElementToString(b2cPage.CTO_unselectedItemTextOld));
				Dailylog.logInfoDB(5, "cvText: " + cvText, Store, testName);
				Dailylog.logInfoDB(5, "cvPrice: " + cvPrice, Store, testName);
				b2cPage.CTO_unselectedItemTextOld.click();
				// TODO 价格
			} else {
				Dailylog.logInfoDB(5, "change cv in CTO page failed, no cv to change", Store, testName);
			}
		}
		return cvText;
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
					Dailylog.logInfoDB(0, "Cart_addedItem: " + i + " " + b2cPage.Cart_addedItem.get(i).getText(), Store, testName);
				}
				Assert.fail("selected " + itemID + " is not displayed in cart");
			}
		}
	}
}
