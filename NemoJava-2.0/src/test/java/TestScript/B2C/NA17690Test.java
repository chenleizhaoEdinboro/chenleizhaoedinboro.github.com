package TestScript.B2C;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA17690Test extends SuperTestClass {

	public B2CPage b2cPage;

	public NA17690Test(String store) {
		this.Store = store;
		this.testName = "NA-17690";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA17690(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);

			// Log in with new account
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Dailylog.logInfoDB(1, "Logged in successfully!!!", Store, testName);
			Dailylog.logInfoDB(1, "Clearing the cart.", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);

			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
					SplitterPage.Laptops);
			Thread.sleep(2000);
			String url = driver.getCurrentUrl();
			int productCount = driver
					.findElements(
							By.xpath(".//ol[@class='categoryListing columnSlider-move']//h1[@class='seriesPreview-title']"))
					.size();
			String customize = "(.//*[@id='addToCartButtonTop']/span[@class='icon-cus'])";

			AddSeries(1, url, 1, productCount, customize);

			Thread.sleep(2000);
			Dailylog.logInfoDB(2, "Clicked on cart button", Store, testName);
			b2cPage.HomePage_CartIcon.click();
			Dailylog.logInfoDB(3, "Checking Products in cart", Store, testName);
			Assert.assertEquals(
					2,
					driver.findElements(
							By.xpath("(.//*[@id='editConfig']/h4/div)")).size());
			String product1 = driver.findElement(
					By.xpath("(.//*[@id='editConfig']/h4/div)[1]")).getText();
			String product2 = driver.findElement(
					By.xpath("(.//*[@id='editConfig']/h4/div)[2]")).getText();

			Dailylog.logInfoDB(3, "2 CTO products are present at cart page!!!",
					Store, testName);
			Assert.assertEquals(product1, product2);
			Thread.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Thread.sleep(2000);

			Dailylog.logInfoDB(4, "Adding MTM products", Store, testName);
			driver.get(url);
			Thread.sleep(4000);
			String mtmProduct = "(.//div[contains(@class,'productListing')]//button[@id='addToCartButtonTop']/span[@class='icon-atc'])";
			AddSeries(1, url, 1, productCount, mtmProduct);
			Thread.sleep(2000);
			// Dailylog.logInfoDB(2, "Clicked on cart button", Store, testName);
			b2cPage.HomePage_CartIcon.click();
			Dailylog.logInfoDB(5, "Checking 2 MTM Products in cart", Store, testName);
			Assert.assertEquals(
					2,
					driver.findElements(
							By.xpath("(.//*[@id='editConfig']/h4/div)")).size());
			product1 = driver.findElement(
					By.xpath("(.//*[@id='editConfig']/h4/div)[1]")).getText();
			product2 = driver.findElement(
					By.xpath("(.//*[@id='editConfig']/h4/div)[2]")).getText();

			Dailylog.logInfoDB(5, "2 MTM products are present at cart page!!!",
					Store, testName);
			Assert.assertEquals(product1, product2);
			Thread.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Thread.sleep(2000);

			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
					SplitterPage.Accessories);
			Thread.sleep(5000);

			if (Common.isElementExist(driver,
					By.xpath("//div[@class='accessoriesCategories']//span"))) {
				
				b2cPage.B2C_Accessory_BrowseAllCategory.click();
				if (Common.isElementExist(driver,
						By.xpath("//div[@id='ChargersBatteries']//h3/a"))) {
					driver.findElement(By.xpath("//div[@id='ChargersBatteries']//h3/a")).click();
					
				} else {
					b2cPage.B2C_Accessory_Charger.click();
				}
				
			}

			if (Common
					.isElementExist(
							driver,
							By.xpath("(.//*[@id='productGrid-target']//div[@class='thumb']//img)"))) {
				b2cPage.B2C_Accessory_Audio.click();
			}
			b2cPage.B2C_Accessory_SubAccessory.click();

			Thread.sleep(2000);
			String accessoryURL = driver.getCurrentUrl();
			
			b2cPage.Add2Cart.click();

			if (Common.isElementExist(driver,
					By.xpath(".//*[contains(@class,'addToCartPopupButton')]"))) {
				b2cPage.B2C_Accessory_SubAccessory_AddToCartPopup.click();
				Dailylog.logInfoDB(6, "Clicked on Add to cart popup.", Store,
						testName);
			}
			Thread.sleep(5000);
			if (Common.isElementExist(driver,
					By.xpath(".//a[contains(@class,'addedToCart')]"))) {
				b2cPage.B2C_Accessory_GoToCart.click();
				Dailylog.logInfoDB(6, "Clicked on go to cart popup.", Store,
						testName);
			}

			Thread.sleep(3000);
			driver.get(accessoryURL);
			Thread.sleep(5000);
			b2cPage.Add2Cart.click();

			if (Common.isElementExist(driver,
					By.xpath(".//*[contains(@class,'addToCartPopupButton')]"))) {
				b2cPage.B2C_Accessory_SubAccessory_AddToCartPopup.click();
				Dailylog.logInfoDB(7, "Clicked on Add to cart popup.", Store,
						testName);
			}

			if (Common.isElementExist(driver,
					By.xpath(".//a[contains(@class,'addedToCart')]"))) {
				Thread.sleep(5000);
				Common.javascriptClick(driver, driver.findElement(By
						.xpath(".//a[contains(@class,'addedToCart')]")));
				// driver.findElement(By.xpath(".//a[contains(@class,'addedToCart')]")).click();
				Dailylog.logInfoDB(7, "Clicked on go to cart popup.", Store,
						testName);
			}

			Assert.assertEquals(
					1,
					driver.findElements(
							By.xpath("(.//*[@id='editConfig']/h4/div)")).size());
			String accessoryName = driver.findElement(
					By.xpath("(.//*[@id='editConfig']/h4/div)[1]")).getText();
			Dailylog.logInfoDB(8, "Accessory Name : " + accessoryName, Store,
					testName);
			String quantity = driver.findElement(
					By.xpath(".//*[contains(@id,'quantity')]")).getAttribute("value");
			Integer.parseInt(quantity);
			Assert.assertEquals("2",
					driver.findElement(By.xpath(".//*[contains(@id,'quantity')]"))
							.getAttribute("value"));
			Thread.sleep(2000);
			B2CCommon.clearTheCart(driver, b2cPage, testData);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void AddSeries(int stepNo, String url, int index, int productCount,
			String button) {
		if (index < productCount) {
			driver.findElement(
					By.xpath("(.//ol[@class='categoryListing columnSlider-move']//h1[@class='seriesPreview-title'])["
							+ index + "]")).click();
			if (Common
					.isElementExist(
							driver,
							By.xpath("(.//*[@id='mainContent']//div[@class='seriesListings-footer']/a)[1]"))) {
				int count = driver
						.findElements(
								By.xpath("(.//*[@id='mainContent']//div[@class='seriesListings-footer']/a)"))
						.size();
				Dailylog.logInfoDB(stepNo, "Subseries count : " + count, Store,
						testName);
				String subUrl = driver.getCurrentUrl();
				Dailylog.logInfoDB(stepNo, "Calling AddSubSeries method.",
						Store, testName);
				AddSubSeries(stepNo, 1, count, subUrl, button, index, url,
						productCount);
			} else {
				Dailylog.logInfoDB(stepNo, "No sub series present!!!", Store,
						testName);
				driver.get(url);
				Common.sleep(5000);
				AddSeries(stepNo, url, ++index, productCount, button);
			}
		} else {
			Dailylog.logInfoDB(stepNo, "No product left!!!", Store, testName);
		}
	}

	public void AddSubSeries(int stepNo, int index, int count, String subUrl,
			String button, int index2, String url, int productCount) {
		if (index <= count) {
			Common.javascriptClick(
					driver,
					driver.findElement(By
							.xpath("(.//*[@id='mainContent']//div[@class='seriesListings-footer']/a)["
									+ index + "]")));
			// driver.findElement(By.xpath("(.//*[@id='mainContent']//div[@class='seriesListings-footer']/a)["
			// + index + "]")).click();
			Common.sleep(2000);
			if (Common.isElementExist(driver, By.xpath(button))) {
				String currentUrl = driver.getCurrentUrl();
				Common.javascriptClick(driver,
						driver.findElement(By.xpath(button + "[1]")));
				Common.sleep(5000);
				Dailylog.logInfoDB(2,
						"Clicked on Add To Cart/customize button.", Store,
						testName);
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='cta-builder-skip']/button"))) {
					b2cPage.B2C_PDP_SkipAndAddToCart.click();
					Dailylog.logInfoDB(2, "Clicked on skip button.", Store,
							testName);
				}
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='cta-builder-continue']/button"))) {
					b2cPage.B2C_PDP_AddToCart.click();
					Dailylog.logInfoDB(2, "Clicked on add to cart button.",
							Store, testName);
				}
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='CTO_addToCart']"))) {
					b2cPage.Product_AddToCartBtn.click();
					Dailylog.logInfoDB(2, "Clicked on add to cart button.",
							Store, testName);
				}
				Common.sleep(2000);
				driver.get(currentUrl);
				Common.sleep(2000);
				Common.javascriptClick(driver,
						driver.findElement(By.xpath(button + "[1]")));
				Common.sleep(2000);
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='cta-builder-skip']/button"))) {
					b2cPage.B2C_PDP_SkipAndAddToCart.click();
					Dailylog.logInfoDB(3, "Clicked on skip button.", Store,
							testName);
				}
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='cta-builder-continue']/button"))) {
					b2cPage.B2C_PDP_AddToCart.click();
					Dailylog.logInfoDB(3, "Clicked on add to cart button.",
							Store, testName);
				}
				if (Common.isElementExist(driver,
						By.xpath(".//*[@id='CTO_addToCart']"))) {
					b2cPage.Product_AddToCartBtn.click();
					Dailylog.logInfoDB(3, "Clicked on add to cart button.",
							Store, testName);
				}

				Common.sleep(2000);
			} else {
				Dailylog.logInfoDB(
						stepNo,
						"No Customize/MTM product is present, checking other product.",
						Store, testName);
				driver.get(subUrl);
				Common.sleep(5000);
				AddSubSeries(stepNo, ++index, count, subUrl, button, index2,
						url, productCount);
			}
		} else {
			Dailylog.logInfoDB(stepNo, "No sub-series left!!!", Store, testName);
			driver.get(url);
			Common.sleep(5000);
			AddSeries(stepNo, url, ++index2, productCount, button);
		}
	}
}
