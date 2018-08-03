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
import Pages.CTOPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21942Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public CTOPage ctoPage;

	public NA21942Test(String store) {
		this.Store = store;
		this.testName = "NA-21942";
	}

	@Test(alwaysRun = true,groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA21942(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			ctoPage = new CTOPage(driver);

			// ListPrice: true, Strikethrough: true
			listPriceToggle(true, true);
			Dailylog.logInfoDB(0, "HMC ListPrice: true, Strikethrough: true", Store, testName);
			checkInB2C(true, true);
			Dailylog.logInfoDB(0, "B2C ListPrice: true, Strikethrough: true", Store, testName);

			// ListPrice: true, Strikethrough: false
			listPriceToggle(true, false);
			Dailylog.logInfoDB(0, "HMC ListPrice: true, Strikethrough: false", Store, testName);
			checkInB2C(true, false);
			Dailylog.logInfoDB(0, "B2C ListPrice: true, Strikethrough: false", Store, testName);
			
			// ListPrice: false, Strikethrough: true
			listPriceToggle(false, true);
			Dailylog.logInfoDB(0, "HMC ListPrice: false, Strikethrough: true", Store, testName);
			checkInB2C(false, true);
			Dailylog.logInfoDB(0, "B2C ListPrice: false, Strikethrough: true", Store, testName);
			
			
		}

		catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	private void listPriceToggle(boolean isListPriceDisplay, boolean isStrikethroughDisplay) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		HMCCommon.searchB2BUnit(hmcPage, testData);
		hmcPage.B2BUnit_siteAttribute.click();
		By togLPDisplay = By
				.xpath("//input[contains(@id,'.togLPDisplay') and contains(@id,'" + isListPriceDisplay + "')]");
		Common.waitElementClickable(driver, driver.findElement(togLPDisplay), 3);
		driver.findElement(togLPDisplay).click();
		Common.sleep(2000);
		driver.findElement(
				By.xpath("//input[contains(@id,'.togStrDisplayy') and contains(@id,'" + isStrikethroughDisplay + "')]"))
				.click();
		hmcPage.HMC_Save.click();
		Common.sleep(2000);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void checkInB2C(boolean isListPriceDisplay, boolean isStrikethroughDisplay) {
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		
		driver.get(testData.B2B.getHomePageUrl()+"/cart");
		B2BCommon.clearTheCart(driver, b2bPage);
		
		driver.get(testData.B2B.getHomePageUrl());
		b2bPage.HomePage_productsLink.click();
		
		if(Common.checkElementExists(driver,b2bPage.HomePage_Laptop, 10)) {
			b2bPage.HomePage_Laptop.click();
		}else if(Common.checkElementExists(driver, b2bPage.HomePage_Destop, 10)){
			b2bPage.HomePage_Destop.click();
		}
		b2bPage.Laptops_contractAgreementFilter.click();
		b2bPage.productPage_raidoContractButton.click();
		Common.sleep(2000);

		// PLP
		By partNumberX = By.xpath("//*[@id='resultList']/div//dt[contains(.,'Part Number:')]/../dd[1]");
		List<WebElement> partNumber = driver.findElements(partNumberX);
		int partNumberSize = partNumber.size();
		Dailylog.logInfoDB(0, "partNumberSize: " + partNumberSize, Store, testName);
		Assert.assertTrue(partNumberSize > 0, "No avalibale product");
		By listPrice = By.xpath("//div[@class='pricingSummary']//div[contains(text(),'List Price:')]");
		if (isListPriceDisplay)
			Assert.assertTrue(Common.checkElementDisplays(driver, listPrice, 3));
		else
			Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
		Dailylog.logInfoDB(0, "PLP list price pass", Store, testName);
		By strike = By.xpath("//div[@class='pricingSummary']//strike");
		if (isListPriceDisplay && isStrikethroughDisplay)
			Assert.assertTrue(Common.checkElementDisplays(driver, strike, 3));
		else
			Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
		Dailylog.logInfoDB(0, "PLP strike pass", Store, testName);

		// PDP
		listPrice = By.xpath("//div[contains(text(),'List Price:') and @class='pdpprice1']");
		b2bPage.PLPPage_viewDetails.get(0).click();
		Common.sleep(500);
		if (isListPriceDisplay)
			Assert.assertTrue(Common.checkElementDisplays(driver, listPrice, 3));
		else
			Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
		Dailylog.logInfoDB(0, "PDP check pass", Store, testName);
		strike = By.xpath("//div[contains(text(),'List Price:') and @class='pdpprice1']//strike");
		if (isListPriceDisplay && isStrikethroughDisplay)
			Assert.assertTrue(Common.checkElementDisplays(driver, strike, 3));
		else
			Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
		Dailylog.logInfoDB(0, "PDP strike pass", Store, testName);
		if (Common.isElementExist(driver, By.xpath("//img[@title='Add to Cart']"))) {
			driver.findElement(By.xpath("//img[@title='Add to Cart']")).click();
			Dailylog.logInfoDB(0, "Clicked on Add to Cart on PDP", Store, testName);
		}

		// Popup
		listPrice = By.xpath(
				"//div[@id='cboxLoadedContent']//div[contains(text(),'List Price:') and @class='accessoriesDetail-priceBlock-labels']");
		if (Common.isElementExist(driver, By.xpath("//div[@id='cboxLoadedContent']"))) {
			if (isListPriceDisplay)
				Assert.assertTrue(Common.checkElementDisplays(driver, listPrice, 3));
			else
				Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
			Dailylog.logInfoDB(0, "contractAddToCartOnPopup list price pass", Store, testName);
			strike = By.xpath(
					"//div[@id='cboxLoadedContent']//div[contains(text(),'List Price:') and @class='accessoriesDetail-priceBlock-labels']/..//strike");
			if (isListPriceDisplay && isStrikethroughDisplay)
				Assert.assertTrue(Common.checkElementDisplays(driver, strike, 3));
			else
				Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
			Dailylog.logInfoDB(0, "contractAddToCartOnPopup strike pass", Store, testName);

			b2bPage.Product_contractAddToCartOnPopup.click();
			By addedToCart = By.xpath("//h2[contains(text(),'Added to Cart')]");
			Assert.assertTrue(Common.checkElementDisplays(driver, addedToCart, 3),
					"contractAddToCartOnPopup added to cart");
			Dailylog.logInfoDB(0, "Clicked contractAddToCartOnPopup", Store, testName);
		}

		// Accessories&Upgrades
		boolean isAcc = Common.isElementExist(driver, By.xpath("//a[contains(@href,'Accessories_Upgrades')]"));
		int count = 1;
		if (isAcc) {
			count = 2;
			driver.get(testData.B2B.getHomePageUrl());
			b2bPage.HomePage_AccessoriesLink.click();
			List<WebElement> accessories = driver.findElements(By.xpath("//div[@class='prod_cat']"));
			accessories.get(0).click();
			listPrice = By.xpath(
					"//div[@class='accessoriesListing-body']//div[contains(text(),'List Price:') and @class='accessoriesDetail-priceBlock-labels']");
			if (isListPriceDisplay)
				Assert.assertTrue(Common.checkElementDisplays(driver, listPrice, 3));
			else
				Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
			Dailylog.logInfoDB(0, "Accessories&Upgrades list price pass", Store, testName);
			strike = By.xpath(
					"//div[@class='accessoriesListing-body']//div[contains(text(),'List Price:') and @class='accessoriesDetail-priceBlock-labels']/..//strike");
			if (isListPriceDisplay && isStrikethroughDisplay)
				Assert.assertTrue(Common.checkElementDisplays(driver, strike, 3));
			else
				Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
			Dailylog.logInfoDB(0, "Accessories&Upgrades strike pass", Store, testName);

			Common.javascriptClick(driver, driver.findElement(By.xpath("//h3//a")));
			listPrice = By.xpath("//div[@class='accessoriesDetail-webPrice']//div[contains(text(),'List Price:')]");
			if (isListPriceDisplay)
				Assert.assertTrue(Common.checkElementDisplays(driver, listPrice, 3));
			else
				Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
			Dailylog.logInfoDB(0, "Accessories detail page list price pass", Store, testName);
			strike = By.xpath(
					"//div[@class='accessoriesDetail-webPrice']//div[contains(text(),'List Price:')]/..//strike");
			if (isListPriceDisplay && isStrikethroughDisplay)
				Assert.assertTrue(Common.checkElementDisplays(driver, strike, 3));
			else
				Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
			Dailylog.logInfoDB(0, "Accessories detail page strike pass", Store, testName);
			driver.findElement(By.id("addToCart")).click();
			
			// popup
			listPrice = By.xpath("//div[@id='cboxLoadedContent']//div[contains(text(),'List Price:')]");
			if (Common.isElementExist(driver, By.xpath("//div[@id='cboxLoadedContent']"))) {
				if (isListPriceDisplay)
					Assert.assertTrue(Common.checkElementDisplays(driver, listPrice, 3));
				else
					Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
				Dailylog.logInfoDB(0, "accessoryAddToCartOnPopup list price pass", Store, testName);
				strike = By.xpath("//div[@id='cboxLoadedContent']//div[contains(text(),'List Price:')]/..//strike");
				if (isListPriceDisplay && isStrikethroughDisplay)
					Assert.assertTrue(Common.checkElementDisplays(driver, strike, 3));
				else
					Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
				Dailylog.logInfoDB(0, "accessoryAddToCartOnPopup strike pass", Store, testName);
				b2bPage.Product_contractAddToCartOnPopup.click();
				By addedToCart = By.xpath("//h2[contains(text(),'Added to Cart')]");
				Assert.assertTrue(Common.checkElementDisplays(driver, addedToCart, 3),
						"contractAddToCartOnPopup added to cart");
				Dailylog.logInfoDB(0, "Clicked contractAddToCartOnPopup", Store, testName);
			}
		}

		// Summary
		driver.get(testData.B2B.getHomePageUrl() + "/cart");
		b2bPage.cartPage_LenovoCheckout.click();
		Common.sleep(3000);
		Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("add-delivery-address"));
		B2BCommon.fillB2BShippingInfo(driver, b2bPage, Store.toLowerCase(), "FirstNameJohn", "LastNameSnow",
				testData.B2B.getCompany(), testData.B2B.getAddressLine1(), testData.B2B.getAddressCity(),
				testData.B2B.getAddressState(), testData.B2B.getPostCode(), testData.B2B.getPhoneNum());
		Common.javascriptClick(driver, b2bPage.shippingPage_ContinueToPayment);
		if (Common.checkElementExists(driver, b2bPage.shippingPage_validateFromOk, 5)) {
			b2bPage.shippingPage_validateFromOk.click();
		}
		b2bPage.paymentPage_PurchaseOrder.click();
		Common.sleep(2000);
		b2bPage.paymentPage_purchaseNum.sendKeys("1234567890");
		b2bPage.paymentPage_purchaseDate.sendKeys(Keys.ENTER);

		B2BCommon.fillDefaultB2bBillingAddress(driver, b2bPage, testData);
		
		//to handle delay after clicking continue
		Common.sleep(9000);
		Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("/checkout/multi/summary"),"not summary page");
		listPrice = By.xpath("//p[@class='checkout-review-item-partNumber' and contains(text(),'List Price')]");
		List<WebElement> listPrices = driver.findElements(listPrice);
		if (isListPriceDisplay)
			Assert.assertEquals(listPrices.size(), count);
		else
			Assert.assertTrue(!Common.checkElementDisplays(driver, listPrice, 3));
		Dailylog.logInfoDB(0, "summary list price pass", Store, testName);
		strike = By.xpath("//p[@class='checkout-review-item-partNumber' and contains(text(),'List Price')]//strike");
		List<WebElement> strikes = driver.findElements(listPrice);
		if (isListPriceDisplay && isStrikethroughDisplay)
			Assert.assertEquals(strikes.size(), count);
		else
			Assert.assertTrue(!Common.checkElementDisplays(driver, strike, 3));
		Dailylog.logInfoDB(0, "accessoryAddToCartOnPopup strike pass", Store, testName);
	}
}
