package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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

public class NA17665Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	String laptopPageURL;

	public NA17665Test(String store) {
		this.Store = store;
		this.testName = "NA-17665";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup", "productbuilder", "p2", "b2c"})
	public void NA17665(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			// step 1 ~ login into B2C
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);

			// step2 ~ 5
			String testProduct = testData.B2C.getDefaultMTMPN();
			Dailylog.logInfoDB(2, "testProduct: " + testProduct, Store, testName);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testProduct);
			String subseriesPHcode = b2cPage.Meta_subseriesPHcode.getAttribute("content");
			Dailylog.logInfoDB(2, "subseriesPHcode: " + subseriesPHcode, Store, testName);

			String plpUrl = testData.B2C.getHomePageUrl() + "/p/" + subseriesPHcode;
			Dailylog.logInfoDB(2, "plpUrl: " + plpUrl, Store, testName);
			driver.get(plpUrl);
			boolean isPromotedOptionExist = Common.checkElementDisplays(driver, By.xpath("(//div[@class='promotedOptions']//input[not(contains(@value,':'))])[1]"), 3);
			String optionID;
			if (!isPromotedOptionExist) {
				Dailylog.logInfoDB(2, "isPromotedOptionExist: " + isPromotedOptionExist, Store, testName);
				try {
					testProduct = b2cPage.PLP_firstAddToCartWithoutProOpt.getAttribute("submitfromid").replace("addToCartFormTop", "");
				} catch (NoSuchElementException e) {
					Assert.fail("no testProduct found");
				}
				Dailylog.logInfoDB(2, "testProduct: " + testProduct, Store, testName);
				Common.javascriptClick(driver, b2cPage.PLP_firstAddToCartWithoutProOpt);
				Common.sleep(2000);
				optionID = findAccessory();
				Dailylog.logInfoDB(2, "optionID: " + optionID, Store, testName);
				String proOptName = createPromotedOption(optionID);
				Dailylog.logInfoDB(2, "proOptName: " + proOptName, Store, testName);
				String baseProduct = getBaseProduct("online", testProduct);
				Dailylog.logInfoDB(2, "baseProduct: " + baseProduct, Store, testName);
				String region = Store.substring(0, 2).toUpperCase();
				addProOptToProduct(baseProduct, "online", "B2C", region, proOptName);
				driver.get(plpUrl);
			}
			optionID = b2cPage.PLP_firstPromotedOption.getAttribute("value");
			testProduct = b2cPage.PLP_firstPromotedOption.getAttribute("class");
			Dailylog.logInfoDB(2, "optionID: " + optionID, Store, testName);
			Dailylog.logInfoDB(2, "testProduct: " + testProduct, Store, testName);
			Common.javascriptClick(driver, b2cPage.PLP_firstPromotedOption);
			Common.javascriptClick(driver, b2cPage.PLP_firstAddToCartWithProOpt);
			Common.sleep(2000);
			b2cPage.PB_accessoriesTab.click();
			By selectedOptAccessory = By.xpath("//input[@value='" + optionID + "' and @checked]/../div[1]");
			boolean isOptSelected = Common.isElementExist(driver, selectedOptAccessory, 3);
			if(!isOptSelected) {
				By selectedOptWarranty = By.xpath("//div[contains(@class,'option_check')]/input[@value='" + optionID + "']");
				isOptSelected = Common.isElementExist(driver, selectedOptWarranty, 3);
			}
			Assert.assertTrue(isOptSelected, "isOptSelected in PB");

		} catch (Throwable e) {
			handleThrowable(e, ctx);

		}
	}

	private String createPromotedOption(String optionsID) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.ProductBuidler_promotedProductOptions.click();
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		String proOptName = testName.replace("NA-", "AutoTest") + Store;
		hmcPage.ProductBuidler_proOptName.sendKeys(proOptName);
		hmcPage.ProductBuidler_searchBtn.click();
		By targetResult = By.xpath("//div[contains(@id,'" + proOptName + "')]");
		if (Common.checkElementDisplays(driver, targetResult, 3)) {
			Common.doubleClick(driver, driver.findElement(targetResult));
			hmcPage.ProductBuidler_deleteBtn.click();
			if (Common.isAlertPresent(driver))
				driver.switchTo().alert().accept();
		}
		Common.rightClick(driver, hmcPage.ProductBuidler_promotedProductOptions);
		Common.waitElementClickable(driver, hmcPage.ProductBuidler_createProOpt, 3);
		hmcPage.ProductBuidler_createProOpt.click();
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		hmcPage.ProductBuidler_proOptName.sendKeys(proOptName);
		Common.rightClick(driver, hmcPage.ProductBuidler_optionsTable);
		Common.waitElementClickable(driver, hmcPage.HMC_add, 3);
		hmcPage.HMC_add.click();
		Common.switchToWindow(driver, 1);
		hmcPage.Template_codeInput.sendKeys(optionsID);
		Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
		hmcPage.Template_selCatalogVersionOnline.click();
		hmcPage.Template_searchBtn.click();
		hmcPage.Template_searchedHierarchy.click();
		hmcPage.Template_useBtn.click();
		Common.switchToWindow(driver, 0);
		hmcPage.Nemo_InstallmentsCreate.click();
		hmcPage.hmcHome_hmcSignOut.click();
		return proOptName;
	}

	private String findAccessory() {
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
			Common.javascriptClick(driver, b2cPage.newPDP_firstAccessories);
			Common.sleep(3000);
			optionID = driver.findElement(By.xpath(".//*[@class='lnvmodal-title']/p")).getText().split(": ")[1];
			if(optionID.substring(optionID.length()-2).equals(Store.substring(0,2))) {
				System.out.println("mtm found instead of acc");
				driver.findElement(By.xpath("//i[contains(@class,'close-modal')]")).click();
				accessoriesText = b2cPage.newPDP_firstAccOfSecSection.getText();
				Dailylog.logInfoDB(0, "OptionName:" + accessoriesText, Store, testName);
				Common.javascriptClick(driver, b2cPage.newPDP_firstAccOfSecSection);
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
			optionID = driver.findElement(By.xpath("(//div[@class='lnvmodal-tail']//span)[2]")).getText();
		}
		return optionID;
	}

	private void clickContinue_1() {
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

	private String getBaseProduct(String category, String ProductID) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(category, ProductID);
		Common.waitElementVisible(driver, hmcPage.Products_baseProduct);
		String baseProduct = hmcPage.Products_baseProduct.getAttribute("value");
		baseProduct = baseProduct.substring(0, 15);
		hmcPage.hmcHome_hmcSignOut.click();
		return baseProduct;
	}

	private void addProOptToProduct(String productID, String category, String channel, String region, String proOptName) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct(category, productID);
		hmcPage.Catalog_multiCountry.click();
		WebElement targetProductBuilder = driver.findElement(By.xpath("//div[contains(@id,'ItemDisplay[" + channel + "]')][contains(.,'" + channel + "')]/../../td[contains(.,'" + region + "')]"));
		Common.doubleClick(driver, targetProductBuilder);
		Common.switchToWindow(driver, 1);
		hmcPage.Product_promotedOptions.sendKeys(proOptName);
		Common.sleep(3000);
		hmcPage.Product_promotedOptions.sendKeys(Keys.ENTER);
		hmcPage.ProductBuidler_saveBtn.click();
		Common.switchToWindow(driver, 0);
		hmcPage.ProductBuidler_saveBtn.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void searchProduct(String category, String productID) {
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productID);
		hmcPage.Catalog_CatalogVersion.click();
		if (category.contains("staged")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogStaged.click();
		} else if (category.contains("online")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		}
		hmcPage.Catalog_SearchButton.click();
		hmcPage.products_resultItem.click();
	}

}