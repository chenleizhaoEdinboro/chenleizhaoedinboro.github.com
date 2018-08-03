package TestScript.B2B;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.openqa.selenium.By;
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

public class NA23046Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;

	public NA23046Test(String store) {
		this.Store = store;
		this.testName = "NA-23046";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2b"})
	public void NA23046(ITestContext ctx) {

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
			boolean isContractExist = Common.checkElementDisplays(driver, By.xpath("(//label[contains(.,'Contract')]/input)[last()]"), 3);
			if (isContractExist) {
				b2bPage.productPage_raidoContractButton.click();
				Thread.sleep(1000);
				// 4.Click Add Accessories button
				String produdctNumber = "";
				try {
					produdctNumber = b2bPage.PLP_mtmID.getText();
				} catch (NoSuchElementException e) {
					Assert.fail("no product found");
				}
				Dailylog.logInfoDB(4, "produdctNumber: " + produdctNumber, Store, testName);
				driver.findElement(By.xpath("//form[contains(@id,'addToAccessorisForm')]/button")).click();

				// 5. Select one option under every Tab every section on PB page
				boolean isPBExist = Common.checkElementDisplays(driver, By.xpath("//span[contains(text(),'Product Builder')]"), 3);
				if (isPBExist) {
					Double webPrice = Double.valueOf(b2bPage.PDPPage_WebPrice.getText().replace(",", ""));
					Dailylog.logInfoDB(5, "webPrice:  " + webPrice, Store, testName);
					LinkedHashMap<String, Double> optionAndPrice = new LinkedHashMap<String, Double>();

					optionAndPrice = selectWarranty(optionAndPrice);
					optionAndPrice = selectAccessory(optionAndPrice);

					// 6. Click Add to Cart button
					cartCheck(webPrice, optionAndPrice);

					// 7. Click Edit button under this product Part Number
					Common.scrollToElement(driver, b2bPage.CartPage_Editlink);
					b2bPage.CartPage_Editlink.click();

					// 8. check in pb
					for (Entry<String, Double> element : optionAndPrice.entrySet()) {
						String xpath = "//input[@value='" + element.getKey() + "']";
						String isChecked = driver.findElement(By.xpath(xpath)).getAttribute("checked");
						Assert.assertEquals(isChecked, "true");
					}

					// 9. Click Add to Cart button
					cartCheck(webPrice, optionAndPrice);

					// 10. Click Checkout and Place Order
					Common.scrollToElement(driver, b2bPage.lenovoCheckout);
					b2bPage.lenovoCheckout.click();
					String orderNumber = B2BCommon.placeAnOrder(driver, Store, b2bPage, testData);
					Dailylog.logInfoDB(10, "orderNumber: " + orderNumber, Store, testName);

				} else {
					Dailylog.logInfoDB(5, "no product builder: " + produdctNumber, Store, testName);
				}

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

	private void cartCheck(Double webPrice, LinkedHashMap<String, Double> optionAndPrice) {
		b2bPage.Agreement_addToCartAccessoryBtn.click();
		try {
			Common.waitElementVisible(driver, driver.findElement(By.xpath("//h1[contains(text(),'Shopping Cart')]")));
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
		Double actualTotalPrice = getPrice(b2bPage.cartPage_TotalPrice.getText());
		Assert.assertEquals(actualTotalPrice, totalPrice, "totalPrice");
	}

}
