package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import org.openqa.selenium.JavascriptExecutor;

public class NA16656Test extends SuperTestClass {
	public B2CPage b2cPage;

	public NA16656Test(String store) {
		this.Store = store;
		this.testName = "NA-16656";
	}

	private void checkFacet(int level) throws InterruptedException {
		String SeriesLevel3Text = "(.//*[@id='seriesRedesignPage']/div[1]/nav/span/a/span)[2]";
		// Verify facet search panel on the left-side of the page is displayed.
		Dailylog.logInfoDB(1, "Level 1 Facet Panel  is displayed", Store, testName);
		List<WebElement> facetList1 = driver.findElements(By.xpath("//*[@class='facet-list-item']/h3"));
		Dailylog.logInfoDB(1, "Level 1 facet List count is " + facetList1.size(), Store, testName);
		// Level 1 Sub facet List
		if (facetList1.size() > 0) {
			List<WebElement> facetSubList = driver.findElements(By.xpath("//*[@class='facet-list-item']/ol"));
			for (int x = 1; x <= facetSubList.size(); x++) {
				List<WebElement> facetSubLists = driver
						.findElements(By.xpath("(//*[@class='facet-list-item']/ol)[" + x + "]/li"));
				System.out.println(" Sub facet List count is " + facetSubLists.size());
				if (facetSubLists.size() == 0) {
					Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
					System.out.println(" Sub facet List count is 0 ");
				}
			}
		} else {
			// facet list is not displayed if this series has no any products
			List<WebElement> seriesLevel3StartAt = driver.findElements(By.xpath("//dt[contains(text(),'Starting at')]"));
			if (seriesLevel3StartAt.size() > 0) {
				Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
				System.out.println(" facet list is not displayed ");
			}
		}
		// Step 2 Click on each series to go to level2 page,only Laptops and
		// Ultrabook have level 2
		// check out how many series of products
		if (level == 1) {
			List<WebElement> seriesTitles = driver.findElements(By.xpath("//article//h1/a"));
			System.out.println("seriesTitle count is " + seriesTitles.size());
			for (int i = 1; i <= seriesTitles.size(); i++) {
				String seriesTitle = driver.findElement(By.xpath("(//article//h1/a)[" + i + "]")).getText().toString();
				System.out.println("Series Title is :" + seriesTitle);
				driver.findElement(By.xpath("(//article/div/a/img)[" + i + "]")).click();
				Thread.sleep(5000);
				// Verify facet search panel on the left-side of the page is
				// displayed.
				Assert.assertTrue(b2cPage.FacetPanel.isDisplayed());
				Dailylog.logInfoDB(2, "Level 2 Facet Panel  is displayed", Store, testName);
				List<WebElement> facetList2 = driver.findElements(By.xpath("//*[@class='facet-list-item']/h3"));
				Dailylog.logInfoDB(2, "Level 2 facet List count  is " + facetList2.size(), Store, testName);
				// Level 2 Sub facet List
				if (facetList2.size() > 0) {
					List<WebElement> facetSubList = driver.findElements(By.xpath("//*[@class='facet-list-item']/ol"));
					for (int x = 1; x <= facetSubList.size(); x++) {
						List<WebElement> facetSubLists = driver
								.findElements(By.xpath("(//*[@class='facet-list-item']/ol)[" + x + "]/li"));
						System.out.println(" Sub facet List count  is " + facetSubLists.size());
						if (facetSubLists.size() == 0) {
							Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
							System.out.println(" Sub facet List count is 0 ");
						}
					}
				} else {
					// facet list is not displayed if this series has no any
					// products
					List<WebElement> seriesLevel3StartAt = driver
							.findElements(By.xpath("//dt[contains(text(),'Starting at')]"));
					if (seriesLevel3StartAt.size() > 0) {
						Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
						System.out.println(" facet list is not displayed ");
					}
				}
				// Step 3 click on each of the "view series" button to enter the
				// level 3 page
				List<WebElement> viewSeriesButton = driver.findElements(By.xpath(".//*[@id='product-']/div/div[6]/a"));
				Dailylog.logInfoDB(3, "view Series Button count is " + viewSeriesButton.size(), Store, testName);
				System.out.println(viewSeriesButton.size());
				for (int j = 1; j <= viewSeriesButton.size(); j++) {
					if(j>3) {
						((JavascriptExecutor)driver).executeScript("scroll(0,1500*((j+2)/3))");
					}
					else {
						((JavascriptExecutor)driver).executeScript("scroll(0,1200*((j+2)/3))");
					}
					String viewSeriesProductName = driver
							.findElement(By.xpath("(.//*[@id='product-']/div/div[2]/h3/a)[" + j + "]")).getText()
							.toString();
					System.out.println("View Series Product Name is : " + viewSeriesProductName);
					driver.findElement(By.xpath("(.//*[@id='product-']/div/div[6]/a)[" + j + "]")).click();
					if(j>6) {
							System.out.println("2");
					}
					Thread.sleep(5000);
					// Verify facet search panel on the left-side of the page is
					// displayed.
					if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
						Dailylog.logInfoDB(3, "Level 3 Facet Panel is not displayed", Store, testName);
					}
					List<WebElement> facetList3 = driver.findElements(By.xpath("//*[@class='facet-list-item']/h3"));
					Dailylog.logInfoDB(3, "Level 3 facet List count is " + facetList3.size(), Store, testName);
					// Level 3 Sub facet List
					if (facetList3.size() > 0) {
						List<WebElement> facetSubList = driver
								.findElements(By.xpath("//*[@class='facet-list-item']/ol"));
						for (int x = 1; x <= facetSubList.size(); x++) {
							List<WebElement> facetSubLists = driver
									.findElements(By.xpath("(//*[@class='facet-list-item']/ol)[" + x + "]/li"));
							System.out.println(" Sub facet List count  is " + facetSubLists.size());
							if (facetSubLists.size() == 0) {
								Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
								System.out.println(" Sub facet List count is 0 ");
							}
						}
					} else {
						// facet list is not displayed if this series has no any
						// products
						List<WebElement> seriesLevel3StartAt = driver
								.findElements(By.xpath("//dt[contains(text(),'Starting at')]"));
						if (seriesLevel3StartAt.size() > 0) {
							Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
							System.out.println(" facet list is not displayed ");
						}
					}
					String seriesLevel3Link = b2cPage.SeriesLevel3Link.getText().toString();
					System.out.println("Level 3 page back to: " + seriesLevel3Link);
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					if (Common.isElementExist(driver, By.xpath(SeriesLevel3Text))) {
						driver.findElement(By.xpath(SeriesLevel3Text)).click();
					} else {
						b2cPage.SeriesLevel3Link.click();
					}
					Thread.sleep(5000);
				}
				Thread.sleep(5000);
				String seriesLink = b2cPage.SeriesLink.getText().toString();
				System.out.println("Level 2 back to: " + seriesLink);
				b2cPage.SeriesLink.click();
				Thread.sleep(5000);
			}

		} else {
			if (Common.isElementExist(driver, By.xpath("//article//h1/a"))) {
				List<WebElement> seriesTitles = driver.findElements(By.xpath("//article//h1/a"));
				System.out.println("seriesTitle count is " + seriesTitles.size());
				for (int i = 1; i <= seriesTitles.size(); i++) {
					String seriesTitle = driver.findElement(By.xpath("(//article//h1/a)[" + i + "]")).getText()
							.toString();
					System.out.println("Series Title is :" + seriesTitle);
					driver.findElement(By.xpath("(//article/div/a/img)[" + i + "]")).click();
					Thread.sleep(5000);
					// Verify facet search panel on the left-side of the page is
					// displayed.
					Assert.assertTrue(b2cPage.FacetPanel.isDisplayed());
					Dailylog.logInfoDB(2, "Level 2 Facet Panel  is displayed", Store, testName);
					List<WebElement> facetList2 = driver.findElements(By.xpath("//*[@class='facet-list-item']/h3"));
					Dailylog.logInfoDB(2, "Level 2 facet List count  is " + facetList2.size(), Store, testName);
					// Level 2 Sub facet List
					if (facetList2.size() > 0) {
						List<WebElement> facetSubList = driver
								.findElements(By.xpath("//*[@class='facet-list-item']/ol"));
						for (int x = 1; x <= facetSubList.size(); x++) {
							List<WebElement> facetSubLists = driver
									.findElements(By.xpath("(//*[@class='facet-list-item']/ol)[" + x + "]/li"));
							System.out.println(" Sub facet List count  is " + facetSubLists.size());
							if (facetSubLists.size() == 0) {
								Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
								System.out.println(" Sub facet List count is 0 ");
							}
						}
					} else {
						// facet list is not displayed if this series has no any
						// products
						List<WebElement> seriesLevel3StartAt = driver
								.findElements(By.xpath("//dt[contains(text(),'Starting at')]"));
						if (seriesLevel3StartAt.size() > 0) {
							Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
							System.out.println(" facet list is not displayed ");
						}
					}
					// Step 3 click on each of the "view series" button to enter
					// the
					// level 3 page
					List<WebElement> viewSeriesButton = driver
							.findElements(By.xpath(".//*[@id='product-']/div/div[6]/a"));
					Dailylog.logInfoDB(3, "view Series Button count is " + viewSeriesButton.size(), Store, testName);
					for (int j = 1; j <= viewSeriesButton.size(); j++) {
						String viewSeriesProductName = driver
								.findElement(By.xpath("(.//*[@id='product-']/div/div[2]/h3/a)[" + j + "]")).getText()
								.toString();
						System.out.println("View Series Product Name is : " + viewSeriesProductName);
						if(j>3) {
							((JavascriptExecutor)driver).executeScript("scroll(0,1500*((j+2)/3))");
						}
						else {
							((JavascriptExecutor)driver).executeScript("scroll(0,1200*((j+2)/3))");
						}
						driver.findElement(By.xpath("(.//*[@id='product-']/div/div[6]/a)[" + j + "]")).click();
						Thread.sleep(5000);
						// Verify facet search panel on the left-side of the
						// page is
						// displayed.
						if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
							Dailylog.logInfoDB(3, "Level 3 Facet Panel is not displayed", Store, testName);
						}
						List<WebElement> facetList3 = driver.findElements(By.xpath("//*[@class='facet-list-item']/h3"));
						Dailylog.logInfoDB(3, "Level 3 facet List count is " + facetList3.size(), Store, testName);
						// Level 3 Sub facet List
						if (facetList3.size() > 0) {
							List<WebElement> facetSubList = driver
									.findElements(By.xpath("//*[@class='facet-list-item']/ol"));
							for (int x = 1; x <= facetSubList.size(); x++) {
								List<WebElement> facetSubLists = driver
										.findElements(By.xpath("(//*[@class='facet-list-item']/ol)[" + x + "]/li"));
								System.out.println(" Sub facet List count  is " + facetSubLists.size());
								if (facetSubLists.size() == 0) {
									Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
									System.out.println(" Sub facet List count is 0 ");
								}
							}
						} else {
							// facet list is not displayed if this series has no
							// any
							// products
							List<WebElement> seriesLevel3StartAt = driver
									.findElements(By.xpath("//dt[contains(text(),'Starting at')]"));
							if (seriesLevel3StartAt.size() > 0) {
								Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
								System.out.println(" facet list is not displayed ");
							}
						}
						String seriesLevel3Link = b2cPage.SeriesLevel3Link.getText().toString();
						System.out.println("Level 3 page back to: " + seriesLevel3Link);
						Actions actions = new Actions(driver);
						actions.sendKeys(Keys.PAGE_UP).perform();
						if (Common.isElementExist(driver, By.xpath(SeriesLevel3Text))) {
							driver.findElement(By.xpath(SeriesLevel3Text)).click();
						} else {
							b2cPage.SeriesLevel3Link.click();
						}
						Thread.sleep(5000);
					}
					String seriesLink = b2cPage.SeriesLink.getText().toString();
					System.out.println("Level 2 back to: " + seriesLink);
					b2cPage.SeriesLink.click();
					Thread.sleep(5000);
				}

			} else {
				// Step 3 click on each of the "view series" button to enter the
				// level 3 page
				List<WebElement> viewSeriesButton = driver.findElements(By.xpath(".//*[@id='product-']/div/div[6]/a"));
				Dailylog.logInfoDB(3, "view Series Button count is " + viewSeriesButton.size(), Store, testName);
				for (int j = 1; j <= viewSeriesButton.size(); j++) {
					if(j>3) {
						((JavascriptExecutor)driver).executeScript("scroll(0,1400*((j+2)/3))");
					}
					else {
						((JavascriptExecutor)driver).executeScript("scroll(0,1200*((j+2)/3))");
					}
					String viewSeriesProductName = driver
							.findElement(By.xpath("(.//*[@id='product-']/div/div[2]/h3/a)[" + j + "]")).getText()
							.toString();
					System.out.println("View Series Product Name is : " + viewSeriesProductName);
					driver.findElement(By.xpath("(.//*[@id='product-']/div/div[6]/a)[" + j + "]")).click();
					Thread.sleep(5000);
					// Verify facet search panel on the left-side of the page is
					// displayed.
					if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
						Dailylog.logInfoDB(3, "Level 3 Facet Panel is not displayed", Store, testName);
					}
					List<WebElement> facetList3 = driver.findElements(By.xpath("//*[@class='facet-list-item']/h3"));
					Dailylog.logInfoDB(3, "Level 3 facet List count is " + facetList3.size(), Store, testName);
					// Level 3 Sub facet List
					if (facetList3.size() > 0) {
						List<WebElement> facetSubList = driver
								.findElements(By.xpath("//*[@class='facet-list-item']/ol"));
						for (int x = 1; x <= facetSubList.size(); x++) {
							List<WebElement> facetSubLists = driver
									.findElements(By.xpath("(//*[@class='facet-list-item']/ol)[" + x + "]/li"));
							System.out.println(" Sub facet List count  is " + facetSubLists.size());
							if (facetSubLists.size() == 0) {
								Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
								System.out.println(" Sub facet List count is 0 ");
							}
						}
					} else {
						// facet list is not displayed if this series has no any
						// products
						List<WebElement> seriesLevel3StartAt = driver
								.findElements(By.xpath("//dt[contains(text(),'Starting at')]"));
						if (seriesLevel3StartAt.size() > 0) {
							Common.NavigateToUrl(driver, Browser,driver.getCurrentUrl());
							System.out.println(" facet list is not displayed ");
						}
					}
					String seriesLink = b2cPage.SeriesLink.getText().toString();
					System.out.println("Level 3 back to: " + seriesLink);
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					Common.scrollAndClick(driver, b2cPage.SeriesLink);
					//b2cPage.SeriesLink.click();
					Thread.sleep(5000);
				}
			}
		}
	}

	@Test(alwaysRun = true, groups = {"browsegroup","facetsearch",  "p1", "b2c"})
	public void NA16656(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String closeHomePagePopUp = ".//*[@id='overlay-flashsales']/span/span";
			// step1 Enter B2C site
			
			Common.NavigateToUrl(driver, Browser,testData.B2C.getHomePageUrl());
			B2CCommon.closeHomePagePopUP(driver);
			if (Common.isElementExist(driver, By.xpath(closeHomePagePopUp))) {
				driver.findElement(By.xpath(closeHomePagePopUp)).click();
			}
			B2CCommon.handleGateKeeper(b2cPage, testData);
			if (testData.Store.equals("US_OUTLET") || testData.Store.equals("GB")) {
				Dailylog.logInfo("this is old UI");
				// Click Laptop
				b2cPage.Navigation_ProductsLink.click();
				Common.waitElementVisible(driver, b2cPage.Navigation_Laptop);
				b2cPage.Navigation_Laptop.click();
				System.out.println("Go to Laptops page.");
				if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
					Dailylog.logInfoDB(1, "Level 1 Facet Panel is not displayed ", Store, testName);
				} else {
					checkFacet(1);
				}
				// Click Tablets
				b2cPage.Navigation_ProductsLink.click();
				Common.waitElementVisible(driver, b2cPage.Navigation_Tablets);
				b2cPage.Navigation_Tablets.click();
				System.out.println("Go to Tablets page.");
				if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
					Dailylog.logInfoDB(1, "Level 1 Facet Panel is not displayed ", Store, testName);
				} else {
					checkFacet(2);
				}
				// Click Desktops
				b2cPage.Navigation_ProductsLink.click();
				Common.waitElementVisible(driver, b2cPage.Navigation_Desktops);
				b2cPage.Navigation_Desktops.click();
				System.out.println("Go to Desktops page.");
				if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
					Dailylog.logInfoDB(1, "Level 1 Facet Panel is not displayed ", Store, testName);
				} else {
					checkFacet(2);
				}

			} else {
				Dailylog.logInfo("this is new UI");
				// Click Laptops
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
				Thread.sleep(2000);
				System.out.println("Go to Laptops page.");
				if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
					Dailylog.logInfoDB(1, "Level 1 Facet Panel is not displayed ", Store, testName);
				} else {
					checkFacet(1);
				}
				// Click Tablets
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Tablets);
				Thread.sleep(2000);
				System.out.println("Go to Tablets page.");
				if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
					Dailylog.logInfoDB(1, "Level 1 Facet Panel is not displayed ", Store, testName);
				} else {
					checkFacet(2);
				}
				// Click Desktops
				NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.AllInOnes);
				Thread.sleep(2000);
				System.out.println("Go to Desktops page.");
				if (!Common.checkElementExists(driver, b2cPage.FacetPanel, 5)) {
					Dailylog.logInfoDB(1, "Level 1 Facet Panel is not displayed ", Store, testName);
				} else {
					checkFacet(2);
				}
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
