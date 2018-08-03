package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21981Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String MTMUrl;
	private String testProd;
	By emptyCart = By.xpath(
			"//*[contains(text(),'Empty cart') or contains(text(),'カートを空にする') or contains(@alt,'カートを空にする') or contains(@alt,'Empty cart')]");
	By confirmEmpty = By.xpath("//input[contains(@onclick, '.submit')]");

	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	

	public NA21981Test(String store, String mtmUrl) {
		this.Store = store;
		this.MTMUrl = mtmUrl;
		this.testName = "NA-21981";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21981(ITestContext ctx) throws InterruptedException {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			
			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
	
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			
			if (Store.indexOf("JP") >= 0)
				testProd = "80U4000CJP";
			else
				testProd = testData.B2C.getDefaultMTMPN2();

			// Open B2C and HMC
			driver.get(ordinary_homePageUrl);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('" + hmcHomePageUrl + "')");

			// Login B2C with ordinary account
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(ordinary_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());

			// Add available product into cart
			driver.get(ordinary_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			Common.sleep(1000);
			if (Common.isElementExist(driver, confirmEmpty)) {
				driver.findElement(confirmEmpty).click();
			}
			if (!quickOrder(b2cPage, testProd))
				testProd = addProductToCart(MTMUrl, "MTM");
			Dailylog.logInfoDB(1, "avaliable product: " + testProd, Store, testName);

			// Set Availability Rules to unapproved in HMC
			switchToWindow(1);
			changeAvaRule(driver, testProd, "UNAPPROVED");
			Dailylog.logInfoDB(2, "set availability rule to UNAPPROVED", Store, testName);

			// rediscacheclean
			rediscacheclean(driver, testProd);
			Dailylog.logInfoDB(3, "rediscacheclean", Store, testName);

			// check cart page: product is removed
			switchToWindow(0);
			driver.navigate().refresh();
			if (Common.isElementExist(driver,
					By.xpath("//*[contains(@class,'cart-item')]/span[text()='" + testProd + "']"))) {
				if (Common.checkElementExists(driver, driver.findElement(
						By.xpath("//*[contains(@class,'cart-item')]/span[text()='" + testProd + "']")), 5))
					Assert.fail(testProd + " is not removed from cart!");
			}
			Dailylog.logInfoDB(4, "check in cart", Store, testName);

			// check pdp page: product is removed
			driver.get(ordinary_homePageUrl + "/p/" + testProd);
			
			System.out.println(ordinary_homePageUrl + "/p/" + testProd);
			
			if (Common.isElementExist(driver, By.xpath("//div[contains('Product " + testProd + " does not exist')]"))) {
				Assert.fail("Product " + testProd + " does not exist message is not displayed in PDP!");
			}
			
			Dailylog.logInfoDB(5, "check PDP", Store, testName);

			// Logout
			driver.get(ordinary_homePageUrl);
			Thread.sleep(4000);
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", b2cPage.Navigation_SignOutLink);

			B2CCommon.DoubleLogin(driver, testData, b2cPage, tele_loginUrl, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			
			// Start Assisted Service Session
			Thread.sleep(500);
			driver.get(tele_homePageUrl + "/my-account");
			Thread.sleep(500);
			b2cPage.MyAccount_Telesales.click();
			if (Common.checkElementExists(b2cPage.PageDriver, b2cPage.HomePage_CloseAdvButton, 5))
				b2cPage.HomePage_CloseAdvButton.click();
			Common.waitElementClickable(driver, b2cPage.Tele_CustomerSearch, 5);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Tele_CustomerSearch);
			// b2cPage.Tele_CustomerSearch.click();
			b2cPage.Tele_CustomerSearch_customerID.sendKeys(testData.B2C.getLoginID());
			b2cPage.Tele_CustomerSearch_Search.click();
			b2cPage.Tele_CustomerSearch_SearchResult.click();
			b2cPage.Tele_StartSession.click();
			Dailylog.logInfoDB(6, testData.B2C.getTelesalesAccount() + " start session", Store, testName);

			// check pdp page: displays correctly
			Thread.sleep(5000);
			driver.get(tele_homePageUrl + "/p/" + testProd);
			try {
				WebElement addToCart = driver.findElement(
						By.xpath("//input[@value='" + testProd + "']/../button[@id='addToCartButtonTop']"));
				Common.waitElementClickable(driver, addToCart, 30);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);
			} catch (TimeoutException e) {
				Assert.fail("Add to cart button in PDP is not clickable!");
			} catch (NoSuchElementException e) {
				Assert.fail("Add to cart button is not displayed in PDP page!");
			}
			Dailylog.logInfoDB(7, testData.B2C.getTelesalesAccount() + " check pdp", Store, testName);
			// add product to cart: successfully added
			Thread.sleep(5000);
			driver.get(tele_cartUrl);
			if (!Common.isElementExist(driver,
					By.xpath("//*[contains(@class,'cart-item')]/span[text()='" + testProd + "']"))) {
				Assert.fail(testProd + " is not added to cart!");
			}
			Dailylog.logInfoDB(8, testData.B2C.getTelesalesAccount() + " add product to cart", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	@AfterTest(alwaysRun = true)
	public void rollBack(ITestContext ctx) throws InterruptedException {
		try {
			System.out.println("rollback"); // roll back
			SetupBrowser();
			hmcPage = new HMCPage(driver);
			// roll back set availability rules to approved
			driver.get(hmcHomePageUrl);
			changeAvaRule(driver, testProd, "APPROVED");
			Dailylog.logInfoDB(9, "rollback APPROVED", Store, testName);

			// rediscacheclean
			rediscacheclean(driver, testProd);
			Dailylog.logInfoDB(9, "rollback rediscacheclean", Store, testName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public boolean quickOrder(B2CPage b2cPage, String partNum) throws InterruptedException {
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		b2cPage.Cart_AddButton.click();
		try {
			Common.waitElementClickable(b2cPage.PageDriver,
					b2cPage.PageDriver.findElement(By.xpath("//div[@data-p-code='" + partNum + "']")), 5);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String addProductToCart(String testUrl, String productType) throws Exception {
		System.out.println("Find " + productType + "***********************");
		String validProduct = "";
		driver.get(ordinary_homePageUrl + testUrl);

		// Portrait Style: view series button
		List<WebElement> series = driver.findElements(By.xpath("//*[@class='brandListings-footer']/a"));
		System.out.println("series: " + series.size());

		// Landscape Style:US_OUTLET,SG
		List<WebElement> facetedResults = driver.findElements(By.xpath("//*[@class='facetedResults-footer']/a"));
		System.out.println("facetedResults: " + facetedResults.size());

		// if portrait style: find product
		if (facetedResults.size() == 0 || series.size() > 0) {
			System.out.println("Portrait_View Series: " + series.size());
			for (int i = -1; i < series.size(); i++) {
				// if view series exists, click 'view series'
				if (series.size() != 0) {
					series = driver.findElements(By.xpath("//*[@class='brandListings-footer']/a"));
					series.get(i + 1).click();
				}

				// click learn more
				List<WebElement> learnmore = driver.findElements(By.xpath("//*[@class='seriesListings-footer']/a"));
				System.out.println("learnmore number: " + learnmore.size());
				String thirdLevel = driver.getCurrentUrl();
				for (int j = 0; j < learnmore.size(); j++) {
					learnmore = driver.findElements(By.xpath("//*[@class='seriesListings-footer']/a"));
					learnmore.get(j).click();

					// click add to cart(MTM) or customize(CTO)
					List<WebElement> acAddToCart = driver.findElements(By.xpath(
							"//*[contains(@class,'productListing')]//form//*[@id='addToCartButtonTop' and not(@disabled)]"));
					System.out.println("active add to cart number: " + acAddToCart.size());
					// fourthLevel -- after clicking learn more
					String fourthLevel = driver.getCurrentUrl();
					for (int x = 0; x < acAddToCart.size(); x++) {
						acAddToCart = driver.findElements(By.xpath(
								"//*[contains(@class,'productListing')]//form//*[@id='addToCartButtonTop' and not(@disabled)]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", acAddToCart.get(x));
						Thread.sleep(1000);

						// CTO: remember product id
						if (productType.toUpperCase().equals("CTO")) {
							if (isAlertPresent()) {
								System.out.println(driver.switchTo().alert().getText());
								driver.switchTo().alert().accept();
							} else {
								String url = driver.getCurrentUrl();
								validProduct = url.substring(url.lastIndexOf("p/") + 2, url.lastIndexOf("p/") + 17);
								System.out.println("CTO: " + validProduct);

								// CTO: try to click 'add to cart'
								if (Common.isElementExist(driver, By.id("CTO_addToCart"))) {
									if (b2cPage.Product_AddToCartBtn.isDisplayed()) {
										((JavascriptExecutor) driver).executeScript("arguments[0].click();",
												b2cPage.Product_AddToCartBtn);
										Thread.sleep(5000);
									}
								}
							}
						}

						// go to cart
						driver.get(ordinary_cartUrl);
						List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cart-item']"));
						Thread.sleep(1000);

						// if product exists in cart
						if (cartItems.size() >= 1) {
							// MTM: get product id
							if (productType.toUpperCase().equals("MTM")) {
								validProduct = driver
										.findElement(By.xpath("(//*[contains(@class,'cart-item-partNumber')]/span[not(@class)])[last()]"))
										.getText();
								System.out.println("MTM: " + validProduct);
							}

							// break if product type is added correctly
							if (productType.toUpperCase().equals("MTM") && validProduct.indexOf("CTO") < 0) {
								break;
							} else if (productType.toUpperCase().equals("CTO") && validProduct.indexOf("CTO") > 0) {
								break;
							} else {
								// empty cart if wrong product type is added
								B2CCommon.emptyCart(driver, b2cPage);
								Common.sleep(1000);
								if (Common.isElementExist(driver, confirmEmpty)) {
									driver.findElement(confirmEmpty).click();
								}
								// try to add next product in fourthLevel
								driver.get(fourthLevel);
							}
						} else {
							// if item is not added, try to add next product in
							// fourthLevel
							driver.get(fourthLevel);
						}
					}

					if (driver.getCurrentUrl().indexOf("cart") > 0) {
						// break if product type is added correctly
						break;
					} else {
						// back to thirdLevel to check next fourthLevel
						driver.get(thirdLevel);
					}
				}

				if (driver.getCurrentUrl().indexOf("cart") > 0) {
					// break if product type is added correctly
					break;
				} else {
					// back to testUrl to check next thirdLevel
					driver.get(testData.B2C.getHomePageUrl() + testUrl);
				}
			}
		} else if (facetedResults.size() > 0) {
			// if landscape view
			System.out.println("Landscape_facetedResults: " + facetedResults.size());
			Assert.fail("Test product is invalid, please update test product!");
			// for (int i = 0; i < facetedResults.size(); i++) {
			// facetedResults = driver.findElements(By
			// .xpath("//*[@class='facetedResults-footer']/a"));
			// facetedResults.get(i).click();
			// }
		}

		if (validProduct.equals("")) {
			Assert.fail("Test url is invalid, please update test product!");
		}

		return validProduct;
	}

	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void rediscacheclean(WebDriver driver, String testProduct) throws InterruptedException {
		System.out.println("rediscacheclean start");
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_System.click();
		hmcPage.Home_rediscacheclean.click();
		driver.switchTo().frame(0);
		hmcPage.Rediscacheclean_productCode.sendKeys(testProduct);
		hmcPage.Rediscacheclean_clean.click();
		// System.out.println("Cleaned the product cache.");
		Thread.sleep(10000);
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		hmcPage.Home_System.click();
		System.out.println("rediscacheclean end");
		hmcPage.Home_EndSessionLink.click();
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			// System.out.println("Switching window: " + windowNo + " / "
			// + windows.size());
			driver.switchTo().window(windows.get(windowNo));
			// System.out.println("Window switch success");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeAvaRule(WebDriver driver, String testProduct, String selOption) {
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(testProduct);
		hmcPage.Catalog_SearchButton.click();
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		hmcPage.Catalog_multiCountry.click();
		String country = Store.substring(0, 2);
		Select sel = new Select(driver.findElement(By.xpath(
				"//*[contains(text(),'Availability Rules:')]/../../td[last()]//option[@selected and contains(text(),'"
						+ country
						+ "')]/ancestor::tr[2]/td//option[@selected and contains(text(),'B2C')]/../../../../../../../../..//option[contains(text(),'APPROVED')]/..")));
		sel.selectByVisibleText(selOption);
		// for outlet:
		System.out.print(Store);
		if (Store.indexOf("OUTLET") >= 0) {
			sel = new Select(driver.findElement(By.xpath(
					"//*[contains(text(),'Availability Rules:')]/../../td[last()]//option[@selected and contains(text(),'OUTLET')]/ancestor::tr[2]/td//option[contains(text(),'APPROVED')]/..")));
			sel.selectByVisibleText(selOption);
		}
		hmcPage.Catalog_save.click();
		hmcPage.Home_EndSessionLink.click();
	}

}
