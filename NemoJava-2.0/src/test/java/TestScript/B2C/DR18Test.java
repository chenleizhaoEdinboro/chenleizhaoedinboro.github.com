package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class DR18Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String testProduct;
	private String mtmPro;
	private String eCoupon;
	private String ctoPrice;
	private String mtmPrice;
	private String mtmPriceWithoutEcou;
	private String cartID;

	public DR18Test(String store, String ctoProduct,
			String mtmPro, String eCoupon) {
		this.Store = store;
//		this.CTOUrl = ctoUrl;
		this.testProduct = ctoProduct;
		this.mtmPro = mtmPro;
		this.eCoupon = eCoupon;
		this.testName = "DR-18";
	}

	private String String2Price(String valueString) {
		String price = valueString.replace("$", "").replace("-", "")
				.replace("￥", "").replace("HK", "").replace("SG", "")
				.replace("£", "").replace("€", "").replaceAll(" ", "");
		return price;
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(
					driver.getWindowHandles());
			// System.out.println("Switching window: " + windowNo + " / "
			// + windows.size());
			driver.switchTo().window(windows.get(windowNo));
			// System.out.println("Window switch success");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "dr", "p1", "b2c", "compatibility"})
	public void DR18(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/login");
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(3, "login B2C website", Store, testName);

//			b2cPage.Navigation_CartIcon.click();
//			if (Common.checkElementExists(driver,
//					b2cPage.Navigation_ViewCartButton, 5))
//				b2cPage.Navigation_ViewCartButton.click();
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
			if (Common.isElementExist(driver,
					By.xpath("//a[contains(@href,'emptyCart')]"))) {
				driver.findElement(By.xpath("//a[contains(@href,'emptyCart')]"))
						.click();
			}
			if (Common.isElementExist(driver,
					By.xpath("//img[@alt='Empty cart']"))) {
				driver.findElement(By.xpath("//img[@alt='Empty cart']"))
						.click();
				Thread.sleep(3000);
				driver.findElement(
						By.xpath("//input[@value='Yes, Delete Cart']")).click();
			}
			// mtmPro = testData.B2C.getDefaultMTMPN() ;
			B2CCommon.addPartNumberToCart(b2cPage, mtmPro);
			System.out.println("MTM PN : " + mtmPro);

			Thread.sleep(5000);
			mtmPriceWithoutEcou = b2cPage.cartPrices.getText();
			mtmPriceWithoutEcou = String2Price(mtmPriceWithoutEcou);
			System.out.println("mtmPrice before ecoupon : "
					+ mtmPriceWithoutEcou);

			Dailylog.logInfoDB(5, "add MTM product to cart", Store, testName);

			// testProduct = testData.B2C.getDefaultCTOPN();
			System.out.println("getDefaultCTOPN : " + testProduct);
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/p/" + testProduct);

			String changes = B2CCommon.addProductToCartFromPDPPage(driver,false,false,false,false);
			System.out.println(changes);
			Dailylog.logInfoDB(4, "add CTO product to cart", Store, testName);

			if (Common.isElementExist(driver,
					By.xpath("//div[contains(text(),'eCoupon')]"))
					&& !Common.isElementExist(driver,
							By.xpath(".//*[@id='ecouponForm']/label"))) {
				Assert.assertTrue(
						driver.findElement(
								By.xpath("//div[contains(text(),'eCoupon')]"))
								.isDisplayed(), "invalid eCoupon ");
				b2cPage.couponCode.sendKeys(eCoupon);
				b2cPage.couponApply.click();
				Thread.sleep(5000);
				mtmPrice = b2cPage.afterEcouponPrice.getText();
			} else {
				if(!Common.checkElementDisplays(driver, b2cPage.couponApply, 3)){driver.findElement(By.xpath(".//*[@id='ecouponForm']/label"))
					.click();}
				
				b2cPage.couponCode.sendKeys(eCoupon);
				b2cPage.couponApply.click();
				Thread.sleep(5000);
				try {
					mtmPrice = driver
							.findElement(
									By.xpath("(.//*[contains(@class,'salePrice')])[2]"))
							.getText();
				} catch (Exception e) {
					mtmPrice = GetPriceValue(driver
							.findElement(
									By.xpath("(//dl[contains(@class,'webPrice-label')])[1]"))
							.getText())
							- GetPriceValue(driver
									.findElement(
											By.xpath(".//*[contains(@class,'couponSaved')]"))
									.getText()) + "";

				}

			}

			mtmPrice = String2Price(mtmPrice);

			System.out.println("mtmPrice apply ecuopon : " + mtmPrice);
			Dailylog.logInfoDB(6, "Apply a eCoupon for MTM", Store, testName);

			b2cPage.Cart_CheckoutButton.click();
			Dailylog.logInfoDB(7, "Lenovo Checkout", Store, testName);

			Thread.sleep(500);
			if (Store.equals("IE")) {
			ctoPrice = driver.findElement(By.xpath("(//td[contains(text(),'ThinkPad X1')]/../td)[3]")).getText();
			ctoPrice = String2Price(ctoPrice);
			}
			
			// Go to HMC
			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
				driver.navigate().refresh();
			}
			Thread.sleep(3000);
			hmcPage.Home_Nemo.click();
			hmcPage.NEMO_digitalRiver.click();
			hmcPage.NEMO_digitalRiver_tracelog.click();
			hmcPage.NEMO_digitalRiver_tracelogID.sendKeys(testData.B2C
					.getLoginID());

//			hmcPage.NEMO_digitalRiver_tracelogCountry.click();
			if (Store.equals("GB")) {
				Common.javascriptClick(driver, hmcPage.NEMO_digitalRiver_tracelogCountry_GB);
			} else if (Store.equals("FR")) {
				Common.javascriptClick(driver, hmcPage.NEMO_digitalRiver_tracelogCountry_FR);
			} else if (Store.equals("IE")) {
				Common.javascriptClick(driver, hmcPage.NEMO_digitalRiver_tracelogCountry_IE);
			}

			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_search.click();
			Thread.sleep(5000);
			hmcPage.NEMO_digitalRiver_creationtime_sort.click();
			Thread.sleep(5000);
			cartID = hmcPage.NEMO_digitalRiver_search_firstResultItem.getText();
			System.out.println("cart id : " + cartID);
			Common.doubleClick(driver,
					hmcPage.NEMO_digitalRiver_search_firstResultItem);

			String xmlcontent = hmcPage.NEMO_digitalRiver_search_xml.getText();

			Assert.assertTrue(
					xmlcontent.replaceAll(" ", "").contains(
							testProduct.substring(0, 10).replaceAll(" ", "")),
					"xmlcontent not contains mtm product");
			Assert.assertTrue(
					xmlcontent.contains(testProduct.substring(0, 10)),
					"xmlcontent not contains cto product");
			
			// before ecoupon price
			if (Store.equals("FR")) {
				Assert.assertTrue(xmlcontent.contains(mtmPriceWithoutEcou
						.replace(",", ".")),
						"xmlcontent not contains mtm price");
			
				Assert.assertTrue(
						xmlcontent.contains(ctoPrice.replace(",", ".")),
						"xmlcontent not contains cto price");
				
			} else {

				Assert.assertTrue(xmlcontent.contains(mtmPriceWithoutEcou
						.replace(",", "")), "xmlcontent not contains mtm price");
				System.out.println(xmlcontent+"   "+ctoPrice.replace(",", ""));
				Assert.assertTrue(
						xmlcontent.contains(ctoPrice.replace(",", "")),
						"xmlcontent not contains cto price");
				System.out.println("4");
			}
			
			Assert.assertTrue(xmlcontent.contains("20"),
					"xmlcontent not contains ecoupon price");
			System.out.println("123");
			Dailylog.logInfoDB(8, "Check XML file all content correct.", Store,
					testName);

			// B2c continue checkout
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl() + "/cart");
			b2cPage.Cart_CheckoutButton.click();
			b2cPage.DR_emailTxtBox.sendKeys(testData.B2C.getLoginID());
			b2cPage.DR_fNameTxtBox.sendKeys("TESTdr18");
			b2cPage.DR_lNameTxtBox.sendKeys("TESTdr18");
			b2cPage.DR_addLine1TxtBox.sendKeys(testData.B2C
					.getDefaultAddressLine1());
			b2cPage.DR_cityTxtBox
					.sendKeys(testData.B2C.getDefaultAddressCity());
			b2cPage.DR_PostCode.sendKeys("123");

			b2cPage.DR_phoneNumTxtBox.sendKeys(testData.B2C
					.getDefaultAddressPhone());
			((JavascriptExecutor) driver).executeScript("scroll(0,1000)");
			b2cPage.DR_ccChkBox.click();
			b2cPage.DR_ccNumberTxtBox.sendKeys("4111111111111111");
			b2cPage.DR_monthDD.click();
			b2cPage.DR_selectedMonthOption.click();
			b2cPage.DR_yearDD.click();
			b2cPage.DR_selectedYearOption.click();
			b2cPage.DR_securityCodeTxtBox.sendKeys("123");
			b2cPage.DR_continueBtn.click();

			Thread.sleep(1000);

			b2cPage.DR_tncChkBox.click();
			b2cPage.DR_submitBtn.click();
			Thread.sleep(1000);

			String orderNum = b2cPage.DR_orderNum.getText();
			System.out.println("orderNum is :" + orderNum);

			Dailylog.logInfoDB(9, "Place order", Store, testName);

			Dailylog.logInfoDB(10, "Don't select Continue Shoppint", Store,
					testName);

			// Step 11 Hmc->Marketing->Order Statistics->Carts Search Cart by
			// LenovoID and time

			Common.NavigateToUrl(driver, Browser, testData.HMC.getHomePageUrl());
			if (Common.isElementExist(driver, By.id("Main_user"))) {
				HMCCommon.Login(hmcPage, testData);
				driver.navigate().refresh();
			}
			hmcPage.marketing.click();
			hmcPage.marketing_orderStatistics.click();
			hmcPage.marketing_orderStatistics_carts.click();
			hmcPage.marketing_os_carts_orderNumTxtBox.sendKeys(cartID);
			hmcPage.marketing_os_carts_search.click();
			Common.doubleClick(driver, driver.findElement(By
					.xpath("//*[text()='" + cartID + "']")));

			Assert.assertTrue(
					driver.findElement(
							By.xpath("//input[contains(@value,'" + mtmPro
									+ "')]")).isDisplayed(),
					"mtm product is not displayed ");

			Assert.assertTrue(
					driver.findElement(
							By.xpath("//input[contains(@value,'" + testProduct
									+ "')]")).isDisplayed(),
					"cto product is not displayed ");

			if (Store.equals("FR")) {
				// need transfer format
				String hmcCTOprice = driver.findElement(
						By.xpath("(//input[contains(@id,'" + testProduct
								+ "')])[4]")).getAttribute("value");
				hmcCTOprice = hmcCTOprice.replace(",", "");
				Assert.assertTrue(
						hmcCTOprice.equals(ctoPrice.replace(",", ".")),
						"cto price is not correct ");

				String hmcMTMprice = driver
						.findElement(
								By.xpath("(//input[contains(@id,'" + mtmPro
										+ "')])[4]")).getAttribute("value");
				hmcMTMprice = hmcMTMprice.replace(",", "");
				Assert.assertTrue(
						hmcMTMprice.equals(mtmPrice.replace(",", ".")),
						"mtm price is not correct ");

			} else {
				//temporarily removed.
				/*Assert.assertTrue(
						driver.findElement(
								By.xpath("//input[contains(@id,'" + testProduct
										+ "') and @value='" + ctoPrice + "']"))
								.isDisplayed(), "cto price is not correct ");
				System.out.println(mtmPrice);*/
				/*
				 * Assert.assertTrue(driver.findElement(By.xpath(
				 * "//input[contains(@id,'"
				 * +mtmPro+"') and @value='"+mtmPrice+"']")).isDisplayed(),
				 * "mtm price is not correct " );
				 */
			}

			// tab--administration
			Thread.sleep(5000);
			hmcPage.marketing_os_carts_administration.click();
			/*
			 * (2)"IsDigitalRiverCart" is "yes". (3)"DRCartHasPlacedOrder" is
			 * "yes".
			 */
			String isDigitalRiverCart = hmcPage.marketing_os_carts_isDigitalRiverCart
					.getAttribute("checked");
			System.out.println("isDigitalRiverCart: " + isDigitalRiverCart);
			assert isDigitalRiverCart.equals("true");
			/*
			 * Assert.assertTrue(isDigitalRiverCart.contains("true"),
			 * "isDigitalRiverCart is not correct " );
			 */

			// String dRCartHasPlacedOrder =
			// hmcPage.marketing_os_carts_dRCartHasPlacedOrder.getAttribute("checked");
			// System.out.println("dRCartHasPlacedOrder: " +
			// dRCartHasPlacedOrder);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
