package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA15487Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;

	public NA15487Test(String store) {
		this.Store = store;
		this.testName = "NA-15487";
	}

	private double convert(double value) {
		long l1 = Math.round(value * 100); // 四舍五入
		double ret = l1 / 100.0;
		return ret;
	}
	
	private double String2Num(String valueString) {
		String price = valueString.replace("$", "").replace("￥", "").replace(",", "").replace("-", "");
		return Double.parseDouble(price);
	}

	@Test(alwaysRun= true,groups={"shopgroup","pricingpromot","p1","b2b"})
	public void NA15487(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			// B2B find a contract product remeber the price and productNo
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());

			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();
			driver.findElement(By.xpath("//h3[contains(text(),'Contract')]")).click();
//			driver.findElement(By.xpath("//label[contains(.,'Contract') or contains(.,'Agreement')]")).click();
			Thread.sleep(3000);

			List<WebElement> mtmPrices = driver
					.findElements(By.xpath("//div[@id='resultList']//p[@class='accessoriesDetail-priceBlock-price']"));
			String productNo = "";
			double B2Bprice = 0d;
			double PLPpriceText = 0d;
			double newTotalPrices = 0d;
			double webPrices = 0d;
			double webPriceList = 0d;
			double orderTotalPrices = 0d;
			if (mtmPrices.size() == 0) {
				Assert.fail("B2B page have no Products");
			} else {
				productNo = driver.findElement(By.xpath("//div[@id='resultList'][1]//b/dl[1]/dd")).getText().toString();
				// Make sure the price match with what you saw in product
				// listing and detail pages and cart page
				String B2BpriceText1 = b2bPage.ProductPage_PriceOnPLP.getText().toString();
				PLPpriceText = String2Num(B2BpriceText1);
				Dailylog.logInfoDB(1, "PLPpriceText:"+PLPpriceText, Store, testName);
				b2bPage.ProductPage_details.click();
				productNo=driver.getCurrentUrl().split("/p/")[1];
				System.out.println("productNo:"+productNo);

				if (driver.getCurrentUrl().contains("CTO")&&driver.getCurrentUrl().contains("WW")) {
					// a pop-up warning may appear on the page
					if (Common.checkElementDisplays(driver, b2bPage.WarningAddtoCart, 5)) {
						String newTotalPrice = b2bPage.WarningNewTotalPrice.getText().toString();
						newTotalPrices = String2Num(newTotalPrice);
						Dailylog.logInfoDB(2, newTotalPrices, Store, testName);
						Assert.assertEquals(PLPpriceText, newTotalPrices);
						b2bPage.WarningAddtoCart.click();
						
						String webPrice1 = b2bPage.PDPPage_WebPrice.getText().toString();
						webPriceList = String2Num(webPrice1);
						Dailylog.logInfoDB(2, webPriceList, Store, testName);
						Assert.assertEquals(PLPpriceText, webPriceList);
						b2bPage.Agreement_addToCartAccessoryBtn.click();
					} else {
					String orderTotalPrice = b2bPage.PDPPage_OrderTotalPrice.getText().toString();
					orderTotalPrices = String2Num(orderTotalPrice);
					Dailylog.logInfoDB(2, "orderTotalPrices:"+orderTotalPrices, Store, testName);
					Assert.assertEquals(PLPpriceText, orderTotalPrices);
					b2bPage.laptops_addToCartForCTO.click();

					String webPrice = b2bPage.PDPPage_WebPrice.getText().toString();
					webPrices = String2Num(webPrice);
					Dailylog.logInfoDB(2, webPrices, Store, testName);
					Assert.assertEquals(PLPpriceText, webPrices);
					b2bPage.Agreement_addToCartAccessoryBtn.click();
					}
				}else{
					String B2BpriceText2 = b2bPage.PDPPage_ProductPrice.getText().toString();
					Dailylog.logInfoDB(2, B2BpriceText2, Store, testName);
					Assert.assertEquals(B2BpriceText1, B2BpriceText2);
					b2bPage.PDPPage_AddtoCart.click();
					b2bPage.productPage_AlertAddToCart.click();
					Thread.sleep(3000);
					b2bPage.ProductPage_AlertGoToCart.click();
					}
					// Jump to cart page
					Thread.sleep(3000);
					String B2BpriceText = b2bPage.CartPage_ItemPrice.getText().toString();
					Dailylog.logInfoDB(2, B2BpriceText, Store, testName);
					Assert.assertEquals(B2BpriceText1, B2BpriceText);

					B2Bprice = String2Num(B2BpriceText);
					System.out.println("B2B product :" + productNo + " ,price : " + B2Bprice);
				}
				
			// Go to HMC -> Price Settings -> Pricing Cockpit -> B2B Price

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			Thread.sleep(3000);
			if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
				hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
				hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
				hmcPage.PricingCockpit_Login.click();
			}

			Thread.sleep(3000);
			// Simulator

			hmcPage.PricingCockpit_B2BpriceSimulate.click();

			// For example:
			/*
			 * Country -> Australia
			 * 
			 */
			Thread.sleep(5000);

			hmcPage.B2BpriceSimulate_CountryButton.click();
			System.out.println("country click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			if (testData.Store.equals("AU")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("Australia");
			}
			if (testData.Store.equals("US")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("[US]");
			}
			if (testData.Store.equals("JP")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("Japan");
			}
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("contry sendkey:");
			Thread.sleep(10000);
			// Store -> aule
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_StoreButton, 120);
			hmcPage.B2BpriceSimulate_StoreButton.click();
			System.out.println("store click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 120);
			if (testData.Store.equals("AU")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("aule");
			}
			if (testData.Store.equals("US")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("usle");
			}
			if (testData.Store.equals("JP")) {
				hmcPage.B2BpriceSimulate_CountryText.sendKeys("Japan");
			}
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("store sendkey:");
			Thread.sleep(3000);
			// Catalog version -> Nemo Master Multi Country Product Catalog -
			// Online
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CatalogButton, 120);
			hmcPage.B2BpriceSimulate_CatalogButton.click();
			System.out.println("catalog click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys("Nemo Master Multi Country Product Catalog - Online");
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("catalog sendkey:");
			Thread.sleep(30000);
			// B2B Unit -> 1213654197 1213577815
			Common.checkElementExists(driver, hmcPage.B2BpriceSimulate_B2BUnitDiv, 120);
			System.out.println("B2BUNIT Visible :" + testData.B2B.getB2BUnit());
			hmcPage.B2BpriceSimulate_B2BUnitButton.click();
			System.out.println("B2BUnit click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 120);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(testData.B2B.getB2BUnit());
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
			System.out.println("B2BUNIT sendkey:");
			Thread.sleep(3000);
			// Price Date -> 2016-04-28
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_DateButton, 120);
			hmcPage.B2BpriceSimulate_DateButton.click();
			System.out.println("data click:");
			hmcPage.B2BpriceSimulate_DateButton.sendKeys(Keys.ENTER);
			System.out.println("data sendkey:");
			Thread.sleep(3000);
			// Product -> 20FAS0S010
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_ProductButton, 120);
			hmcPage.B2BpriceSimulate_ProductButton.click();
			System.out.println("product click:");
			Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
			hmcPage.B2BpriceSimulate_CountryText.sendKeys(productNo);
			Thread.sleep(5000);
			WebElement productResult = driver.findElement(By.xpath("//div[text()='[']/span[text()='" + productNo + "']"));
			Common.waitElementVisible(driver, productResult);
			productResult.click();
			System.out.println("product sendkey:");
			Thread.sleep(3000);
			hmcPage.B2BpriceSimulate_DebugButton.click();
			Thread.sleep(3000);
			Common.waitElementVisible(driver, hmcPage.B2BpriceSimulate_webPrice);
			String debugPriceText = hmcPage.B2BpriceSimulate_webPrice.getText().toString();
			double debugPrice = String2Num(debugPriceText);
			System.out.println("product debug price : " + debugPrice);
			// check the price in HMC and B2B is equal
			if (debugPrice != B2Bprice) {
				Assert.fail("B2B Price " + B2Bprice + " is not equal to HMC debug price !!" + debugPrice);
			}
			if (testData.Store.equals("AU") || testData.Store.equals("JP")) {
				// Go to HMC -> Nemo -> Contract
				driver.switchTo().defaultContent();
				hmcPage.Home_Nemo.click();
				hmcPage.Home_Contract.click();
				hmcPage.Contract_productCode.sendKeys(productNo);
				Thread.sleep(3000);
				hmcPage.Contract_searchbutton.click();
				if (Common.isElementExist(driver,
						By.xpath("//td[contains(text(),'The search was finished. No results were found')]"))) {
					Assert.fail("Contract no result!!");
				}
				Common.doubleClick(driver, hmcPage.Contract_searchResult);

				WebElement currentEle = driver.findElement(
						By.xpath("//div[text()='" + productNo + "']/../../..//div/div[contains(@id,'Currency')]"));
				Common.waitElementVisible(driver, currentEle);
				String current = currentEle.getText().toString();
				System.out.println("product contract currency : " + current);
				double usdPrice = 0d;
				if (current.equals("AUD")||current.equals("JPY")) {
					Common.doubleClick(driver,
							driver.findElement(By.xpath("//div[text()='" + productNo + "']/../../..")));
					String handle = driver.getWindowHandle().toString();
					System.out.println("handle is :" + handle);
					for (String winHandle : driver.getWindowHandles()) {
						if (winHandle.equals(handle)) {
							continue;
						} else {
							driver.switchTo().window(winHandle);
						}
					}

					String usdPriceText = driver
							.findElement(By.xpath("//div[contains(@id,'USD')]/../..//div/div[contains(@id,'Double')]"))
							.getText().toString();
					usdPrice = String2Num(usdPriceText);
					System.out.println("USD price : " + usdPrice);
					driver.close();
					driver.switchTo().window(handle);
				} else {
					// remeber the usd price
					WebElement usdPriceEle = driver.findElement(
							By.xpath("//div[text()='" + productNo + "']/../../..//div/div[contains(@id,'Price')]"));
					Common.waitElementVisible(driver, usdPriceEle);
					String usdPriceText = usdPriceEle.getText().toString();
					usdPrice = String2Num(usdPriceText);
					System.out.println("USD price : " + usdPrice);
				}

				// Go to HMC -> Price Settings -> ExchangeRate
				if (testData.Store.equals("AU")) {
					hmcPage.Home_ExchangeRate.click();
					// From Currency: AUD(B2B page price) To Currency: USD
					hmcPage.ExchangeRate_fromCurrency.sendKeys("AUD");
					Thread.sleep(3000);
					hmcPage.ExchangeRate_toCurrency.sendKeys("USD");
				}
				if (testData.Store.equals("JP")) {
					hmcPage.Home_ExchangeRate.click();
					// From Currency: AUD(B2B page price) To Currency: USD
					hmcPage.ExchangeRate_fromCurrency.sendKeys("JPY");
					Thread.sleep(3000);
					hmcPage.ExchangeRate_toCurrency.sendKeys("USD");
				}
				Thread.sleep(3000);
				hmcPage.Contract_searchbutton.click();
				if (Common.isElementExist(driver,
						By.xpath("//td[contains(text(),'The search was finished. No results were found')]"))) {
					Assert.fail("ExchangeRate_rate no result!!");
				}
				// order by valid from (descend)
				hmcPage.ExchangeRate_validFrom.click();
				Thread.sleep(3000);
				Common.doubleClick(driver, hmcPage.ExchangeRate_rateRow);
				Common.waitElementVisible(driver, hmcPage.ExchangeRate_rate);
				String rateText = hmcPage.ExchangeRate_rate.getAttribute("value").toString();
				System.out.println("Exchange Rate : " + rateText);
				
				double rate = String2Num(rateText);
				System.out.println("rate : " + rate);
				// record rate USD price * rate = Aud price
				double result = rate * usdPrice;
				if (testData.Store.equals("AU")) {
					result = convert(result);
					System.out.println("USD price * rate : " + result);
					if (result != B2Bprice) {
						Assert.fail("USD price * rate result is not equal to B2B Price !!");
					}
				}
				if (testData.Store.equals("JP")) {
					int results = (int)Math.rint(result);
					int B2Bprices = (int) B2Bprice;
					System.out.println("USD price * rate : " + results);
					System.out.println("B2B Price : " + B2Bprices);
					if (results != B2Bprices) {
						Assert.fail("USD price * rate result is not equal to B2B Price !!");
					}
				}
				
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
