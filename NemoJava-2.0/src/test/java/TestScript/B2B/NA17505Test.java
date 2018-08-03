package TestScript.B2B;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA17505Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;

	public NA17505Test(String store) {
		this.Store = store;
		this.testName = "NA-17505";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2b"})
	public void NA17505(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			hmcPage = new HMCPage(driver);

			 // 1. Enable Product Builder: YES
			 enablePB();
			 Dailylog.logInfoDB(1, "enbaled product builder in HMC", Store, testName);

			// 2, Login to US B2B website
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			String storeID_1 = driver.getCurrentUrl().split("/")[7];
			Dailylog.logInfoDB(2, "storeID_1: " + storeID_1, Store, testName);

			 driver.get(testData.B2B.getHomePageUrl() + "/cart");
			 B2BCommon.clearTheCart(driver, b2bPage);
			 driver.get(testData.B2B.getHomePageUrl());

			// 3. Select one MTM product
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();
			b2bPage.Laptops_contractAgreementFilter.click();
			Thread.sleep(1000);
			boolean isAgreementEixst = Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2bPage.Laptops_agreementChk)), 3);
			if (isAgreementEixst) {
				b2bPage.Laptops_agreementChk.click();
				// get PLP url
				String plpURL = driver.getCurrentUrl();
				System.out.println("PLP URL: " + plpURL);
				// get PDP url of the second product
				int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
				for (int i = 1; i <= viewDetailsNo; i++) {
					driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[" + i + "]")).click();
					if (Common.isAlertPresent(driver)) {
						System.out.println(driver.switchTo().alert().getText() + " Try next product!");
						driver.switchTo().alert().accept();
						driver.get(plpURL);
					} else if (driver.getTitle().contains("Not Found")) {
						System.out.println("Product Not Found, Try next product!");
						driver.get(plpURL);
					} else if (Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart') and @disabled='disabled']/span[contains(.,'Add to cart')]"))) {
						System.out.println("Add to cart not clickable, Try next product!");
						driver.get(plpURL);
					} else
						break;
				}
				// get PDP url of the second product
				String pdpURL = driver.getCurrentUrl();
				System.out.println("PDP URL: " + pdpURL);
				// get product number
				String produdctNumber = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
				Dailylog.logInfoDB(4, "produdctNumber: " + produdctNumber, Store, testName);
				if (Common.checkElementDisplays(driver, By.xpath("//*[@id='b_alert_add_to_cart' or @id='b_marning_add_to_cart']"), 3)) {
					driver.findElement(By.id("b_marning_customize")).click();
					Dailylog.logInfoDB(4, "!!!!b_alert_add_to_cart exits", Store, testName);
				}
				Double webPrice = Double.valueOf(b2bPage.PDPPage_OrderTotalPrice.getText().replace(",", "").replace("$", ""));
				Dailylog.logInfoDB(5, "webPrice:  " + webPrice, Store, testName);

				// 5. select cv
				String cvText = b2bPage.CTO_unselectedItemText.getText();
				String cvPrice = b2bPage.CTO_unselectedItemPrice.getText().trim();
				Dailylog.logInfoDB(5, "cvText: " + cvText, Store, testName);
				Dailylog.logInfoDB(5, "cvPrice: " + cvPrice, Store, testName);
				cvPrice = cvPrice.replace("[", "").replace("]", "").trim();
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(cvPrice);
				cvPrice = m.replaceAll("");
				String temp[] = cvPrice.split("\\$");
				cvPrice = temp[1];
				String calculateCV = temp[0];
				b2bPage.CTO_unselectedItemText.click();

				// 6.Click Add to Cart button
				b2bPage.Agreement_agreementsAddToCart.click();
				Common.sleep(1000);

				// 7. Select one option under every Tab every section on PB page
				boolean isPBExist = Common.checkElementDisplays(driver, By.xpath("//span[contains(text(),'Product Builder')]"), 3);
				if (!isPBExist) {
					Dailylog.logInfoDB(7, "no product builder: " + produdctNumber, Store, testName);
					cartCheck(webPrice, cvText, cvPrice, calculateCV);

					Common.scrollToElement(driver, b2bPage.CartPage_Editlink);
					b2bPage.CartPage_Editlink.click();

					if (cvText != "") {
						// verify correct CV is selected
						String cvInput = "//span[text()='" + cvText + "']/../../input";
						// to handle text not same
						if (!Common.checkElementDisplays(driver, By.xpath("cvInput"), 10)) {
							for (int i = 0; i < b2bPage.CTO_selectedCVText.size(); i++) {
								String getText = b2bPage.CTO_selectedCVText.get(i).getText();
								if (getText.replaceAll(" ", "").contains(cvText.replaceAll(" ", "")))
									break;
								else if (i == b2bPage.CTO_selectedCVText.size() - 1)
									Assert.fail("selected cv is not displayed in cart: " + cvText);
							}

						} else
							Assert.assertTrue(driver.findElement(By.xpath(cvInput)).getAttribute("class").contains("checked"));
					} else {
						Dailylog.logInfoDB(7, "no cv to select", Store, testName);
					}

					b2bPage.Agreement_agreementsAddToCart.click();
					Common.sleep(1000);
					cartCheck(webPrice, cvText, cvPrice, calculateCV);

				} else {
					LinkedHashMap<String, Double> optionAndPrice = new LinkedHashMap<String, Double>();

					optionAndPrice = selectWarranty(optionAndPrice);
					optionAndPrice = selectAccessory(optionAndPrice);

					// 8. Click Add to Cart button
					cartCheck(webPrice, optionAndPrice, cvText, cvPrice, calculateCV);

					// 9. Click Edit button under this product Part Number
					Common.scrollToElement(driver, b2bPage.CartPage_Editlink);
					Common.javascriptClick(driver, b2bPage.CartPage_Editlink);
					//b2bPage.CartPage_Editlink.click();

					// 10. check in pb
					if (cvText != "") {
						// verify correct CV is selected
						String cvInput = "//span[text()='" + cvText + "']/../../input";
						// to handle text not same
						if (!Common.checkElementDisplays(driver, By.xpath("cvInput"), 10)) {
							for (int i = 0; i < b2bPage.CTO_selectedCVText.size(); i++) {
								String getText = b2bPage.CTO_selectedCVText.get(i).getText();
								if (getText.replaceAll(" ", "").contains(cvText.replaceAll(" ", "")))
									break;
								else if (i == b2bPage.CTO_selectedCVText.size() - 1)
									Assert.fail("selected cv is not displayed in cart: " + cvText);
							}

						} else
							Assert.assertTrue(driver.findElement(By.xpath(cvInput)).getAttribute("class").contains("checked"));
					}

					b2bPage.Agreement_agreementsAddToCart.click();
					Common.sleep(1000);

					for (Entry<String, Double> element : optionAndPrice.entrySet()) {
						String xpath = "//input[@value='" + element.getKey() + "']";
						String isChecked = driver.findElement(By.xpath(xpath)).getAttribute("checked");
						Assert.assertEquals(isChecked, "true");
					}

					// 11. Click Add to Cart button
					cartCheck(webPrice, optionAndPrice, cvText, cvPrice, calculateCV);

				}

				// 12. Click Checkout and Place Order
				Common.scrollToElement(driver, b2bPage.lenovoCheckout);
				b2bPage.lenovoCheckout.click();
				String orderNumber = B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
				Dailylog.logInfoDB(12, "orderNumber: " + orderNumber, Store, testName);

			}else {
				Dailylog.logInfoDB(3, "no agreement product", Store, testName);
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void enablePB() {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		String storeID = testData.B2B.getHomePageUrl().split("/")[7];
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(storeID);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
		if (!hmcPage.B2C_SiteAttribute_EnableProductBuilder.isSelected())
			hmcPage.B2C_SiteAttribute_EnableProductBuilder.click();
		hmcPage.SaveButton.click();
		hmcPage.Home_EndSessionLink.click();
	}

	private LinkedHashMap<String, Double> selectAccessory(LinkedHashMap<String, Double> optionAndPrice) throws InterruptedException {
		if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2bPage.PB_accessoryTab)), 5)) {
			b2bPage.PB_accessoryTab.click();
			for (int j = 0; j < b2bPage.PB_sections.size(); j++) {
				if (!b2bPage.PB_sections.get(j).getAttribute("class").contains("expanded")) {
					Common.scrollToElement(driver, b2bPage.PB_sections.get(j));
					b2bPage.PB_sections.get(j).click();
					// unselected options
					String optionPriceXpath = "(//div[@data-show='true']//div[@class='configuratorItem group-frame'])[" + (j + 1) + "]//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']";
					By optionXpath = By.xpath(optionPriceXpath + "/../input");
					if (Common.checkElementDisplays(driver, optionXpath, 3)) {
						String optionID = driver.findElement(optionXpath).getAttribute("value");
						By calculateTypeXpath = By.xpath(optionPriceXpath + "//span[@class='option-price-opt']");
						String calculteType = driver.findElement(calculateTypeXpath).getText().trim();
						By originPriceXpath = By.xpath(optionPriceXpath + "//span[@class='option-price']");
						String originPrice = driver.findElement(originPriceXpath).getText().trim();
						Double actualPrice = getPrice(calculteType, originPrice);
						boolean flag = optionAndPrice.containsKey(optionID);
						if (!flag) {
							optionAndPrice.put(optionID, actualPrice);
							driver.findElement(optionXpath).click();
						}
					}

				}
			}
		}

		for (Entry<String, Double> element : optionAndPrice.entrySet())
			Dailylog.logInfoDB(0, element.getKey() + " : " + element.getValue(), Store, testName);

		return optionAndPrice;
	}

	private LinkedHashMap<String, Double> selectWarranty(LinkedHashMap<String, Double> optionAndPrice) throws InterruptedException {
		if (Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2bPage.PB_warrantyTab)), 5)) {
			b2bPage.PB_warrantyTab.click();
			String warrantyID = b2bPage.PB_warrantyRdo.getAttribute("value");
			String originPrice = b2bPage.PB_warrantyPrice.getText().trim();
			String calculteType = b2bPage.PB_warrantyCalculateType.getText().trim();
			Double actualPrice = getPrice(calculteType, originPrice);
			optionAndPrice.put(warrantyID, actualPrice);
			b2bPage.PB_warrantyRdo.click();
		}
		return optionAndPrice;
	}

	private double getPrice(String calculateType, String originPrice) {
		double price = 0;
		price = Double.valueOf(originPrice.replace(",", ""));
		if (!calculateType.equals("add"))
			price = price * (-1);
		return price;
	}

	private double getPrice(String originPrice) {
		originPrice = originPrice.replace("$", "").replace(",", "").replace("￥", "").replace(" ", "").trim();
		return Double.valueOf(originPrice);
	}

	private void cartCheck(Double webPrice, LinkedHashMap<String, Double> optionAndPrice, String cvText, String cvPrice, String cvCalculateType) {
		b2bPage.Agreement_addToCartAccessoryBtn.click();
		try {
			Common.waitElementVisible(driver, driver.findElement(By.xpath("//*[text()='Your Shopping Cart']")));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			Assert.fail("navigate to cart failure");
		}
		Double totalPrice = webPrice;
		for (Entry<String, Double> element : optionAndPrice.entrySet()) {
			String option = element.getKey();
			Double price = element.getValue();
			boolean isOptionInCart = Common.checkElementDisplays(driver, By.xpath("//*[text()='" + option + "']"), 3);
			Assert.assertTrue(isOptionInCart, "isOptionInCart: false: " + option);
			if (price > 0) {
				String actualPrice = driver.findElement(By.xpath("//*[text()='" + option + "']/..//dd[@class='cart-item-addedItem-price']")).getText();
				Double actualPriceDouble = getPrice(actualPrice);
				Assert.assertEquals(actualPriceDouble, price, "price not correct: " + option);
			}
			totalPrice = totalPrice + price;
		}
		Common.waitElementClickable(driver, b2bPage.Cart_configurationDetails, 3);
		b2bPage.Cart_configurationDetails.click();
		By check = By.xpath("//*[contains(text(),'" + cvText + "')]");
		if (!Common.checkElementDisplays(driver, check, 10)) {
			for (int i = 0; i < b2bPage.Cart_configurationDetailsItems.size(); i++) {
				String getText = b2bPage.Cart_configurationDetailsItems.get(i).getText();
				if (getText.replaceAll(" ", "").contains(cvText.replaceAll(" ", "")))
					break;
				else if (i == b2bPage.Cart_configurationDetailsItems.size() - 1)
					Assert.fail("selected cv is not displayed in cart: " + cvText);
				Dailylog.logInfoDB(0, "Cart_configurationDetailsItems: " + i + " " + getText, Store, testName);
			}
		}
		Double cvPrice1 = getPrice(cvCalculateType, cvPrice);
		totalPrice = totalPrice + cvPrice1;
		Double actualTotalPrice = getPrice(b2bPage.cartPage_TotalPrice.getText());
		//Assert.assertEquals(actualTotalPrice, totalPrice, "totalPrice");
	}

	private void cartCheck(Double webPrice, String cvText, String cvPrice, String cvCalculateType) {
		Double totalPrice = webPrice;
		Common.waitElementClickable(driver, b2bPage.Cart_configurationDetails, 3);
		b2bPage.Cart_configurationDetails.click();
		By check = By.xpath("//*[contains(text(),'" + cvText + "')]");
		if (!Common.checkElementDisplays(driver, check, 10)) {
			for (int i = 0; i < b2bPage.Cart_configurationDetailsItems.size(); i++) {
				String getText = b2bPage.Cart_configurationDetailsItems.get(i).getText();
				if (getText.replaceAll(" ", "").contains(cvText.replaceAll(" ", "")))
					break;
				else if (i == b2bPage.Cart_configurationDetailsItems.size() - 1)
					Assert.fail("selected cv is not displayed in cart: " + cvText);
				Dailylog.logInfoDB(0, "Cart_configurationDetailsItems: " + i + " " + getText, Store, testName);
			}
		}
		Double cvPrice1 = getPrice(cvCalculateType, cvPrice);
		totalPrice = totalPrice + cvPrice1;
		Double actualTotalPrice = getPrice(b2bPage.cartPage_TotalPrice.getText());
		Assert.assertEquals(actualTotalPrice, totalPrice, "totalPrice");
	}

}
