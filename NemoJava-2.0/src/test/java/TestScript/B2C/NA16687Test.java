package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
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

public class NA16687Test extends SuperTestClass {
	private String ctoProduct = "";
	private String optionID = "";
	private String b2cProductUrl;

	private String country;
	private B2CPage b2cPage;
	private HMCPage hmcPage;

	public NA16687Test(String store, String country) {
		this.Store = store;
		this.country = country;
		this.testName = "NA-16687";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p1", "b2c" })
	public void NA16687(ITestContext ctx) {
		try {

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			System.out.println(testData.B2C.getDefaultCTOPN());

			if (!Store.contains("BR")) {
				b2cProductUrl = B2CCommon.getCTOProduct(testData.B2C.getHomePageUrl(), Store) + "/customize?";
				Dailylog.logInfoDB(0, "b2cProductUrl: " + b2cProductUrl, Store, testName);
				driver.get(b2cProductUrl);
				Dailylog.logInfoDB(0, "currentUrl: " + driver.getCurrentUrl(), Store, testName);

				ctoProduct = getCTOID();
				Dailylog.logInfoDB(0, "ctoProduct: " + ctoProduct, Store, testName);

			} else {
				ctoProduct = "20H20001BR"; // 20HM002FBR
				b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct;
				driver.get(b2cProductUrl);
				Dailylog.logInfoDB(0, "BR ctoProduct: " + b2cProductUrl, Store, testName);
				Dailylog.logInfoDB(0, "BR b2cProductUrl: " + b2cProductUrl, Store, testName);
			}

			// check if product is valid
			if (isAlertPresent()) {
				driver.switchTo().alert().accept();
				Assert.fail("test product is invalid: " + ctoProduct);
			}
			Dailylog.logInfoDB(0, "ctoProduct: " + ctoProduct, Store, testName);

			if (!Store.contains("BR")) {
				if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 10)) {
					ctoProduct = testData.B2C.getDefaultCTOPN();
					b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?";
					driver.get(b2cProductUrl);
					Common.sleep(3000);
					if (!Common.checkElementDisplays(driver, By.id("CTO_addToCart"), 10))
						Assert.fail("continue customising is not valid on page, please update test product");
				}
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			} else {
				if (!Common.checkElementDisplays(driver, By.xpath(Common.convertWebElementToString(b2cPage.PDP_AddToCartButton_2)), 10))
					Assert.fail("addToCart is not valid on page, please update test product");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
			}
			Common.sleep(5000);
			Dailylog.logInfoDB(0, "ctoProduct: " + ctoProduct, Store, testName);

			// B2C Get valid accessory
			optionID = findAccessory();
			Dailylog.logInfoDB(0, "optionID: " + optionID, Store, testName);

			// HMC Get base product
			String baseProduct = getBaseProduct(ctoProduct);
			Dailylog.logInfoDB(0, "baseProduct: " + baseProduct, Store, testName);

			// Batch Unassign Option
			batchAssingAndUnassign(false, false, optionID, baseProduct, country);
			batchAssingAndUnassign(false, true, optionID, baseProduct, country);

			// check in B2C
			driver.get(b2cProductUrl);
			if (!Store.contains("BR"))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			else
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
			String optionID2 = findAccessory();
			Dailylog.logInfoDB(0, "optionID2: " + optionID2, Store, testName);
			Assert.assertNotEquals(optionID2, optionID, "optionID2: " + optionID2);

			// Batch Assign Option
			batchAssingAndUnassign(true, false, optionID, baseProduct, country);

			// Waiting for Sync finished and status changed to Success
			syncProduct(baseProduct);
			Dailylog.logInfoDB(0, "sync success", Store, testName);

			// check in B2C
			driver.get(b2cProductUrl);
			if (!Store.contains("BR"))
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.Product_AddToCartBtn);
			else
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PDP_AddToCartButton_2);
			String optionID3 = findAccessory();
			Dailylog.logInfoDB(0, "optionID3: " + optionID3, Store, testName);
			Assert.assertEquals(optionID3, optionID, "optionID3: " + optionID3);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void clickContinue_1() {
		List<WebElement> list = driver.findElements(By.xpath("//*[@id='product-builder-form']//ol[@class='stepsItem']/li"));

		int count = list.size() - 3;
		for (int x = 1; x <= count; x++) {
			// b2cPage.Product_Continue.click();
			int y = x + 1;
			String tab_name = driver.findElement(By.xpath("(//*[@id='product-builder-form']//ol[@class='stepsItem']/li/a/span)[" + y + "]")).getText().toString();
			if ((tab_name.equals("Accessories")) || (tab_name.equals("周辺機器")) || (tab_name.equals("配件"))) {
				break;
			}
			driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")).click();
		}
	}

	public String getCTOID() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		ctoProduct = b2cProductUrl.split("/p/")[1].split("#")[0].substring(0, 15);
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

	public String findAccessory() {
		String accessoriesText;
		String optionID;
		String url = driver.getCurrentUrl();
		if (url.contains("cart?")) {
			Dailylog.logInfoDB(0, "url: " + url, Store, testName);
			Assert.fail("please update test product, no product builder page for this product");
		}
		// Desculpe
		By noAccMsg = By.xpath("//div[@id='Accessories' or @data-tabcode='Accessories']//div[contains(text(),'Desculpe') or contains(text(),'sorry') or contains(.,'Desculpe') or contains(.,'sorry')]");
		if (Common.checkElementDisplays(driver, noAccMsg, 3)) {
			Dailylog.logInfoDB(0, driver.findElement(noAccMsg).getText(), Store, testName);
			Assert.fail("please update test product, no accessory for this product");
		}

		if (Common.checkElementDisplays(driver, By.xpath("//*[contains(@class,'configuratorHeader') or contains(@class,'configuratorItem-accordion-header')]"), 5)) {
			Dailylog.logInfoDB(0, "new product builder", Store, testName);
			Common.javascriptClick(driver, b2cPage.PB_accessoriesTab);
			By option = By.xpath("(.//*[@id='Accessories']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//div[contains(@class,'option-text ')]");
			if (!driver.findElement(option).isDisplayed()) {
				Common.sleep(12000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.PB_firstExpandAccessory);
			}
			Common.sleep(3000);
			accessoriesText = b2cPage.newPDP_firstAccessories.getText();
			Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
			b2cPage.newPDP_firstAccessories.click();
			Common.sleep(3000);
			optionID = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];
			if(optionID.substring(optionID.length()-2).equals(Store.substring(0,2))) {
				System.out.println("mtm found instead of acc");
				driver.findElement(By.xpath("//i[contains(@class,'close-modal')]")).click();
				accessoriesText = b2cPage.newPDP_firstAccessories.getText();
				Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
				b2cPage.newPDP_firstAccOfSecSection.click();
				Common.sleep(3000);
				optionID = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];		
			}
		} else {
			Dailylog.logInfoDB(0, "old product builder", Store, testName);
			clickContinue_1();
			try {
				b2cPage.PDP_firstConfigraution.click();
			} catch (Exception e) {
				Dailylog.logInfo("no need to expand accessory");
			}
			driver.findElement(By.xpath("(//header[contains(@id,'ccessories')]/..//div[contains(@class,'show-detail')])[1]")).click();
			Common.sleep(5000);
			// accessoriesText =
			// driver.findElement(By.xpath("(//div[@class='lnvmodal-tail']//span)[4]")).getText();
			// Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
			optionID = driver.findElement(By.xpath("(//div[@class='lnvmodal-tail']//span)[2]")).getText();
		}
		return optionID;
	}

	// batchAssignFlag true: batch assign catalogFlag true online
	private void batchAssingAndUnassign(Boolean batchAssignFlag, Boolean catalogFlag, String optionID, String mt, String country) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		if (batchAssignFlag) {
			hmcPage.ProductBuilder_batchAssign.click();
			Dailylog.logInfo("...Batch Assign Option...");
		} else {
			hmcPage.ProductBuilder_batchUnassign.click();
			Dailylog.logInfo("...Batch Unassign Option...");
		}
		// select catlog version
		Common.sleep(5000);
		driver.switchTo().frame(0);
		hmcPage.ProductBuilder_clickSelCatalogVersion.click();
		if (catalogFlag) {
			hmcPage.ProductBuilder_selCatalogVersionOnline.click();
			Dailylog.logInfo("...ProductBuilder_selCatalogVersionOnline...");
		} else {
			hmcPage.ProductBuilder_selCatalogVersionStaged.click();
			Dailylog.logInfo("...ProductBuilder_selCatalogVersionStaged...");
		}
		// select country
		By countryX = By.xpath("//span[text()='" + country + "']");
		Common.javascriptClick(driver, driver.findElement(countryX));
		Dailylog.logInfo("Selected Country: " + country);
		// select channel
		hmcPage.ProductBuilder_channelCode.click();
		hmcPage.ProductBuilder_channelB2C.click();
		Dailylog.logInfo("selected channel B2C");
		// search option
		hmcPage.ProductBuilder_showSearchOptionDialog.click();
		hmcPage.ProductBuilder_optionCodeInput.sendKeys(optionID);
		hmcPage.ProductBuilder_searchOptionBtn2.click();
		Common.sleep(5000);
		By targetOptionX = By.xpath(".//*[@id='optionTable']//*[text()='" + optionID + "']");
		Common.waitElementClickable(driver, driver.findElement(targetOptionX), 30);
		driver.findElement(targetOptionX).click();
		hmcPage.ProductBuilder_selectOptionBtn.click();

		// Select Machine Type
		try {
			Common.sleep(8000);
			Common.waitElementVisible(driver, hmcPage.ProductBuilder_mtList);
		} catch (TimeoutException e) {
			hmcPage.ProductBuilder_showSearchOptionDialog.click();
			hmcPage.ProductBuilder_optionCodeInput.sendKeys(optionID);
			hmcPage.ProductBuilder_searchOptionBtn2.click();
			Common.waitElementClickable(driver, driver.findElement(targetOptionX), 30);
			driver.findElement(targetOptionX).click();
			hmcPage.ProductBuilder_selectOptionBtn.click();
			Common.sleep(8000);
		}

		hmcPage.ProductBuilder_searchInput.sendKeys(mt);
		Common.sleep(5000);

		By targetMTX = By.xpath("//*[contains(@id,'machineTypesSelectNamems')]/option[contains(text(),'" + mt + "')]");
		if (Common.isElementExist(driver, targetMTX)) {
			Common.waitElementClickable(driver, driver.findElement(targetMTX), 30);
			driver.findElement(targetMTX).click();
			hmcPage.ProductBuilder_addSelected.click();
			hmcPage.ProductBuilder_submit.click();
			Common.waitAlertPresent(driver, 30);
			driver.switchTo().alert().accept();
			if (batchAssignFlag)
				Dailylog.logInfoDB(0, "...Batch Assign Option success...", Store, testName);
			else
				Dailylog.logInfoDB(0, "...Batch UNassign Option success...", Store, testName);
		} else {
			if (batchAssignFlag)
				Dailylog.logInfoDB(0, "...Batch Assign No MT searched out...", Store, testName);
			else
				Dailylog.logInfoDB(0, "...Batch UNassign No MT searched out...", Store, testName);
		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void syncProduct(String ProductNo) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		Common.sleep(5000);
		hmcPage.Catalog_ArticleNumberTextBox.clear();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(ProductNo);
		hmcPage.Cataglog_CatalogVersionSel.click();
		Common.waitElementClickable(driver, hmcPage.Cataglog_CatalogVersionStaged, 60);
		hmcPage.Cataglog_CatalogVersionStaged.click();
		hmcPage.B2BUnit_SearchButton.click();

		By byLocator3 = By.id("Content/StringDisplay[" + ProductNo + "]_span");
		Common.waitElementClickable(driver, driver.findElement(byLocator3), 60);
		Common.doubleClick(driver, driver.findElement(byLocator3));
		// hmcPage.Product_DisplayTo.sendKeys(testData.B2C.getUnit());
		// driver.findElement(By.xpath("//td[contains(text(),'" + testData.B2C.getUnit()
		// + "')]")).click();
		// hmcPage.Types_SaveBtn.click();

		hmcPage.Product_SynchronizationBtn.click();
		Dailylog.logInfoDB(0, "syn product No : " + ProductNo, Store, testName);

		Common.sleep(5000);
		switchToWindow(1);
		hmcPage.Product_SyncTargetBtn.click();
		if (Store.contains("AU") || Store.contains("NZ")) {
			hmcPage.Product_SyncExeJobANZ.click();
		} else if (Store.contains("US") || Store.contains("CA")) {
			hmcPage.Product_SyncExeJobNA.click();
		} else if (Store.contains("GB") || Store.contains("FR") || Store.contains("IE")) {
			hmcPage.Product_SyncExeJobEMEA.click();
		} else if (Store.contains("BR") || Store.contains("MX")) {
			hmcPage.Product_SyncExeJobLA.click();
		} else {
			hmcPage.Product_SyncExeJobAP2.click();
		}
		hmcPage.Product_NeedRunMapping.click();
		new WebDriverWait(driver, 30000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncStart));
		hmcPage.Product_SyncStart.click();
		new WebDriverWait(driver, 3000000).until(ExpectedConditions.visibilityOf(hmcPage.Product_SyncFinishMsg));
		Common.sleep(5000);
		Dailylog.logInfoDB(0, "Product_SyncStatus: " + hmcPage.Product_SyncStatus.getText(), Store, testName);
		Dailylog.logInfoDB(0, "Product_SyncResult : " + hmcPage.Product_SyncResult.getText(), Store, testName);
//		Assert.assertTrue(hmcPage.Product_SyncStatus.getText().contains("FINISHED"), "Product_SyncStatus should be FINISHED");
//		Assert.assertTrue(hmcPage.Product_SyncResult.getText().contains("SUCCESS"), "Product_SyncResult should be SUCCESS");
		driver.close();
		switchToWindow(0);

		// Sign-out
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			// System.out.println(windows.size());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			Dailylog.logInfoDB(0, "Swith to window timeout, window: " + windowNo, Store, testName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private String getBaseProduct(String product) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(product);
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		Common.sleep(1000);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(2000);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
		return baseProduct;
	}
}
