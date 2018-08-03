package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SHOPE293Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	String referenceProduct;

	public SHOPE293Test(String store, String product) {
		this.Store = store;
		referenceProduct = product;
		this.testName = "SHOPE-293";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "cto", "p2", "b2c" })
	public void SHOP293(ITestContext ctx) {
		try {

			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			String CBName = "AutoTest_SHOPE293";
			removeConvenienceBundle(CBName);
			String leadingProduct = testData.B2C.getDefaultCTOPN();
			leadingProduct = "20LDCTO1WWENUS0";
			Dailylog.logInfoDB(1, "leadingProduct: " + leadingProduct, Store, testName);
			Dailylog.logInfoDB(1, "referenceProduct: " + referenceProduct, Store, testName);
			String baseProduct = getBaseProduct(leadingProduct);
			createConvenienceBundle(CBName, baseProduct, leadingProduct, referenceProduct);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + CBName);
			boolean isCBNameDisplay = Common.checkElementDisplays(driver, By.xpath("//td[text()='" + CBName + "']"), 15);
			Assert.assertTrue(isCBNameDisplay, "isCBNameDisplay");
			Assert.assertEquals(b2cPage.CB_leadingProductNumber.getText(), leadingProduct);
			Assert.assertEquals(b2cPage.CB_referenceProductNumber.getText(), referenceProduct);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void createConvenienceBundle(String CBName, String baseProduct, String leadingProduct, String referenceProduct) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		Common.rightClick(driver, hmcPage.Home_ProductsLink);
		Common.mouseHover(driver, hmcPage.Products_create);
		hmcPage.Products_create_NemoConvenienceBundle.click();
		hmcPage.Products_articleNumber.sendKeys(CBName);
		hmcPage.Products_Identifier.sendKeys(CBName);
		Select Products_catalogversionSel = new Select(hmcPage.Products_catalogversion);
		Products_catalogversionSel.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		Select Products_approvalSel = new Select(hmcPage.Products_approval);
		Products_approvalSel.selectByVisibleText("approved");
		int i = 0;
		String targetDisplayTo = "PUBLIC_GLOBAL_B2C_UNIT";
		searchTarget(hmcPage.Products_displayToSearch, i, targetDisplayTo, "displayto");
		hmcPage.Products_categorySystem.click();
		searchTarget(hmcPage.Products_baseProductSearch, i, baseProduct, "product");
		searchTarget(hmcPage.Products_leadingProductSearch, i, leadingProduct, "product");
		hmcPage.Products_leadingProductQuantity.sendKeys("1");
		Common.rightClick(driver, hmcPage.Products_productReference);
		hmcPage.Products_createProductReference.click();
		Common.switchToWindow(driver, ++i);
		Select typeSel = new Select(hmcPage.CreateProductReference_referenceType);
		typeSel.selectByVisibleText("Consists of");
		hmcPage.CreateProductReference_active.click();
		searchTarget(hmcPage.CreateProductReference_targetProductSearch, i, referenceProduct, "product");
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, --i);
		hmcPage.Products_multicountry.click();
		Common.rightClick(driver, hmcPage.Products_availabilityTable);
		hmcPage.Products_createAvailabilityRules.click();
		WebElement countryOption = driver.findElement(By.xpath("//table[@title='availability']//option[contains(text(),'" + Store.substring(0, 2) + "')]"));
		Common.waitElementClickable(driver, countryOption, 5);
		countryOption.click();
		hmcPage.Availability_channelB2C.click();
		hmcPage.Availability_approvalApproved.click();
		// hmcPage.Availability_onlineDate.sendKeys("07/01/2018");
		// hmcPage.Availability_offlineDate.sendKeys("07/01/2099");
		hmcPage.SaveButton.click();
	}

	public void searchTarget(WebElement searchBtn, int i, String target, String type) {
		searchBtn.click();
		Common.switchToWindow(driver, ++i);
		hmcPage.Products_articleNumber.sendKeys(target);
		if (type.equals("product")) {
			Select catalogSel = new Select(hmcPage.Products_catalogversion);
			catalogSel.selectByVisibleText("masterMultiCountryProductCatalog - Online");
		}
		hmcPage.Products_searchBtn.click();
		hmcPage.Template_searchedHierarchy.click();
		Common.sleep(2000);
		hmcPage.Products_useBtn.click();
		Common.switchToWindow(driver, --i);
	}

	public void removeConvenienceBundle(String CBName) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		try {
			HMCCommon.searchOnlineProduct(driver, hmcPage, CBName);
			hmcPage.PaymentLeasing_delete.click();
			driver.switchTo().alert().accept();
		} catch (NoSuchElementException e) {

		}
		hmcPage.Home_EndSessionLink.click();
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
