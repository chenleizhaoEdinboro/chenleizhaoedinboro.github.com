package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class NA18106Test extends SuperTestClass {
	public B2CPage b2cPage;

	public String laptopPageURL;
	private String currentURL;
	private String seriesName;
	String CartName = Common.getDateTimeString();
	String StockChangeMessage = "Out of stock";

	public NA18106Test(String store) {
		this.Store = store;

		this.testName = "NA-18106";
	}

	@Test(alwaysRun = true, groups = {"browsegroup","product",  "p2", "b2c"})
	public void NA18078(ITestContext ctx) {
		try {
			this.prepareTest();
			List<WebElement> SeriesItems;
			List<WebElement> ModelItems;
			List<WebElement> MaterialItems;
			int index = 0;
			ArrayList<String> seriesUrlArray = new ArrayList<String>();
			ArrayList<String> modelUrlArray = new ArrayList<String>();
			b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl());
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			B2CCommon.handleGateKeeper(b2cPage, testData);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage,
					SplitterPage.Laptops);
			Thread.sleep(5000);
			if (Common
					.isElementExist(driver, By.xpath(".//*[@id='separator']"))) {
				// new UI
				((JavascriptExecutor) driver).executeScript("scroll(0,300)");
				SeriesItems = driver
						.findElements(By
								.xpath(".//*[@id='viewmodel-container']/div[1]/ul//div[1]/img"));
				
				for (int i = 0; i < SeriesItems.size(); i++) {
				
					
					
					SeriesItems.get(i).click();
					seriesName = SeriesItems.get(i).getAttribute("alt").replaceAll(" ", "").replaceAll(":", "");
					Thread.sleep(3000);
					modelUrlArray.add(driver.findElement(
							By.xpath(".//*[@id='" + seriesName
									+ "']/div[2]/div/div[2]/div/a[1]")).getAttribute("href").toString());
				}
				for (int j = 0; j < modelUrlArray.size(); j++) {
					if (modelUrlArray.get(j).contains("lenovo.com")) {
						driver.get(modelUrlArray.get(j));
					} else {
						driver.get(testData.envData.getHttpsDomain()
								+ modelUrlArray.get(j));
					}
					if (Common
							.checkElementDisplays(
									driver,
									By.xpath(".//*[contains(@class,'tabbedBrowse-productListing-header')]/*"),
									10)) {
						MaterialItems = driver
								.findElements(By
										.xpath(".//*[contains(@class,'tabbedBrowse-productListing-header')]/*"));
						for (int q = 0; q < MaterialItems.size(); q++) {
							// verify webprice
							Dailylog.logInfo("verify webprice  " + Store);
							index = q + 1;
							Assert.assertTrue(Common.isElementExist(
									driver,
									By.xpath("(.//*[contains(@class,'saleprice pricingSummary-details-final-price')])["
											+ index + "]")));
							// verify shipping
							Dailylog.logInfo("verify shipping  " + Store);
							Assert.assertTrue(Common.isElementExist(
									driver,
									By.xpath("(.//*[contains(@class,'pricingSummary-shipping')])["
											+ index + "]")));
							// verify customise or add to cart
							if(!Common.isElementExist(driver, By.xpath("//button[contains(@class,'out-of-stock')]"))){
								Dailylog.logInfo("verify customise or add to cart  "
										+ Store);
								Assert.assertTrue(Common.isElementExist(driver, By
										.xpath("(.//*[@id='addToCartButtonTop'])["
												+ index + "]")));
							}
							

						}
					}
				}
					

				

			} else {
				// old UI
				// get all plp urls
				SeriesItems = driver
						.findElements(By
								.xpath(".//*[contains(@id,'tab-content')]/article/div[1]/h1/a"));

				for (WebElement e : SeriesItems) {
					seriesUrlArray.add(e.getAttribute("href").toString());
				}

				for (int i = 0; i < seriesUrlArray.size(); i++) {

					if (seriesUrlArray.get(i).contains("lenovo.com")) {
						driver.get(seriesUrlArray.get(i));
					} else {
						driver.get(testData.envData.getHttpsDomain()
								+ seriesUrlArray.get(i));
					}

					driver.findElement(
							By.xpath("(.//*[@id='product-']/div/div[6]/a/span)[1]"))
							.click();
					Thread.sleep(3000);
					ModelItems = driver
							.findElements(By
									.xpath(".//*[@id='mainContent']/div[2]/ol//div/div[4]/a"));

					for (WebElement e : ModelItems) {
						modelUrlArray.add(e.getAttribute("href").toString());
					}

					for (int j = 0; j < modelUrlArray.size(); j++) {
						if (modelUrlArray.get(j).contains("lenovo.com")) {
							driver.get(modelUrlArray.get(j));
						} else {
							driver.get(testData.envData.getHttpsDomain()
									+ modelUrlArray.get(j));
						}
						if (Common
								.checkElementDisplays(
										driver,
										By.xpath(".//*[contains(@class,'tabbedBrowse-productListing-header')]/*"),
										10)) {
							MaterialItems = driver
									.findElements(By
											.xpath(".//*[contains(@class,'tabbedBrowse-productListing-header')]/*"));
							for (int q = 0; q < MaterialItems.size(); q++) {
								// verify webprice
								Dailylog.logInfo("verify webprice  " + Store);
								index = q + 1;
								Assert.assertTrue(Common.isElementExist(
										driver,
										By.xpath("(.//*[contains(@class,'saleprice pricingSummary-details-final-price')])["
												+ index + "]")));
								// verify shipping
								Dailylog.logInfo("verify shipping  " + Store);
								Assert.assertTrue(Common.isElementExist(
										driver,
										By.xpath("(.//*[contains(@class,'pricingSummary-shipping')])["
												+ index + "]")));
								// verify customise or add to cart
							
								if(!Common.isElementExist(driver, By.xpath("//button[contains(@class,'out-of-stock')]"))){
									Dailylog.logInfo("verify customise or add to cart  "
											+ Store);
									Assert.assertTrue(Common.isElementExist(
											driver,
											By.xpath("(.//*[@id='addToCartButtonTop'])["
													+ index + "]")));
								}
								

							}
						}

					}

				}

			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}