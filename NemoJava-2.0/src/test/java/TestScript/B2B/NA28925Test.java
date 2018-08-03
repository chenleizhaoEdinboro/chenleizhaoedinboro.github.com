package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HACPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA28925Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public HACPage hacPage;
	private String country1;
	private String ProductID = "";
	private String baseProduct = "";
	private String accesoryID = "";
	private String Unit;

	public NA28925Test(String store, String country1) {
		this.Store = store;
		this.country1 = country1;
		this.testName = "NA-28925";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2b" })
	public void NA28925(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			hacPage = new HACPage(driver);

			driver.get(testData.B2B.getLoginUrl());
			Unit = testData.B2B.getLoginUrl();
			Unit = Unit.split("/")[7];
			Dailylog.logInfoDB(0, "Unit: " + Unit, Store, testName);
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver, testData.B2B.getLoginUrl(), testData.B2B.getBuyerId(),
					testData.B2B.getDefaultPassword());
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();

			// Step 1. Find one machine type which has no product builder container
			By partNumberX = By.xpath("//*[@id='resultList']/div//dt[contains(.,'Part Number:')]/../dd[1]");
			List<WebElement> partNumber = driver.findElements(partNumberX);
			int partNumberSize = partNumber.size();
			Dailylog.logInfoDB(1, "partNumberSize: " + partNumberSize, Store, testName);
			Assert.assertTrue(partNumberSize > 0, "No avalibale product");
			if (partNumberSize > 1) {
				ProductID = partNumber.get(1).getText();
				driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[2]")).click();
			} else {
				ProductID = partNumber.get(0).getText();
				driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[1]")).click();
			}

			// check product builder
			String pdpURL = driver.getCurrentUrl();
			String addAccessoryXpath = "//form[contains(@id,'addToAccessorisForm')]/button";
			if (Common.checkElementDisplays(driver, By.xpath(addAccessoryXpath), 3))
				b2bPage.PDPPage_AddAccessories.click();
			else {
				ProductID = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
				b2bPage.Agreement_agreementsAddToCart.click();
			}

			Dailylog.logInfoDB(1, "ProductID: " + ProductID, Store, testName);
			boolean isPBExist = Common.checkElementDisplays(driver,
					By.xpath("//span[contains(text(),'Product Builder')]"), 3);
			Dailylog.logInfoDB(1, "isPBExist: " + isPBExist, Store, testName);

			// Find one option
			By PB_firstUnselectedOption = By
					.xpath("(//div[@class='option-textFrame']//div[text()]/../..//input[@value!=''])[1]");
			if (!Common.checkElementDisplays(driver, PB_firstUnselectedOption, 3))
				b2bPage.PB_expandableSection.click();
			Common.waitElementClickable(driver, b2bPage.PB_firstUnselectedOption, 5);
			b2bPage.PB_firstUnselectedOption.click();
			accesoryID = b2bPage.PB_firstUnselectedOption.getAttribute("value");
			Dailylog.logInfoDB(0, "accesoryID:" + accesoryID, Store, testName);

			// get baseProduct
			baseProduct = getBaseProduct(ProductID);

			// 1. Has machine type, country and channel in impex
			String mt = baseProduct;
			String region = this.Store;
			String channel = "B2C";
			boolean isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			region = this.Store;
			channel = "B2B";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			List<String> countries = new ArrayList<String>();
			countries.add(country1);
			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

			// 2. Has only machine type and country in impex
			mt = baseProduct;
			region = this.Store;
			channel = "NULL";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

			// 3. Has only machine type and channel in impex
			mt = baseProduct;
			region = "NULL";
			channel = "B2C";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = baseProduct;
			region = "NULL";
			channel = "B2B";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

			// 4. Has only country and channel in impex
			mt = "NULL";
			region = this.Store;
			channel = "B2C";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = "NULL";
			region = this.Store;
			channel = "B2B";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = baseProduct;
			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

			// 5. Has only country in impex
			mt = "NULL";
			region = this.Store;
			channel = "NULL";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = baseProduct;
			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

			// 6. Has only channel in impex
			mt = "NULL";
			region = "NULL";
			channel = "B2C";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertTrue(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = "NULL";
			region = "NULL";
			channel = "B2B";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = baseProduct;
			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

			// 7.Has only option id in impex
			mt = "NULL";
			region = "NULL";
			channel = "NULL";
			isImported = impexImport(accesoryID, mt, region, channel);
			Assert.assertTrue(isImported, "successfully imoported");
			Assert.assertFalse(checkAccessory(accesoryID, pdpURL), accesoryID + " " + pdpURL);

			mt = baseProduct;
			batchAssingAndUnassign(true, true, accesoryID, mt, countries, "B2B");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private boolean impexImport(String accesoryID, String mt, String region, String channel)
			throws InterruptedException {
		System.out.println(testData.HAC.getHomePageUrl());
		driver.get(testData.HAC.getHomePageUrl());
		Common.waitElementClickable(driver, hacPage.HacLogin_UserName, 20);
		Common.sendFieldValue(hacPage.HacLogin_UserName, testData.HAC.getLoginId());
		Common.sendFieldValue(hacPage.HacLogin_UserPassword, testData.HAC.getPassword());
		hacPage.HacLogin_LoginButton.click();

		hacPage.HacHome_console.click();
		Common.javascriptClick(driver, hacPage.HacHome_impexImport);
		String command1 = "\"$catalogVersion=catalogVersion(catalog(id[default='masterMultiCountryProductCatalog']), version[default='Online'])[unique=true,default='masterMultiCountryProductCatalog:Online']\"";
		String command2 = "INSERT_UPDATE ProductOption;code[unique=true];$catalogVersion;name[translator=com.nemo.b2b.core.translator.ProductOptionGroupItemsUnassignTranslator]";
		String command3 = ";" + accesoryID + ";;" + mt + ":" + region + ":" + channel;
		Dailylog.logInfoDB(0, command3, Store, testName);

		Actions action = new Actions(driver);
		Common.sleep(1000);
		action.sendKeys(hacPage.ImpexImport_content1, Keys.ENTER).build().perform();
		action.sendKeys(hacPage.ImpexImport_content2, Keys.ENTER).build().perform();
		action.sendKeys(hacPage.ImpexImport_content3, command3).build().perform();
		action.sendKeys(hacPage.ImpexImport_content2, command2).build().perform();
		action.sendKeys(hacPage.ImpexImport_content1, command1).build().perform();

		Common.sleep(1000);
		try {
			hacPage.ImpexImport_importBtn.click();
			Common.waitElementVisible(driver, hacPage.ImpexImport_notificationMsg);
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			hacPage.HacHome_Logout.click();
			System.out.println("timeout try again");
			impexImport(accesoryID, mt, region, channel);
		}
		String notification = hacPage.ImpexImport_notificationMsg.getText();
		hacPage.HacHome_Logout.click();

		return notification.contains("成功") || notification.contains("successfully");

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

	private boolean checkAccessory(String accesoryID, String url) throws InterruptedException {
		driver.get(url);
		String addAccessoryXpath = "//form[contains(@id,'addToAccessorisForm')]/button";
		if (Common.checkElementDisplays(driver, By.xpath(addAccessoryXpath), 3))
			Common.javascriptClick(driver, b2bPage.PDPPage_AddAccessories);
		else
			b2bPage.Agreement_agreementsAddToCart.click();
		By PB_firstUnselectedOption = By
				.xpath("(//div[@class='option-textFrame']//div[text()]/../..//input[@value!=''])[1]");
		if (!Common.checkElementDisplays(driver, PB_firstUnselectedOption, 3))
			b2bPage.PB_expandableSection.click();
		Common.waitElementVisible(driver, b2bPage.PB_firstOption);
		By unassignedOption = By.xpath("//input[@value='" + accesoryID + "']");
		return Common.checkElementDisplays(driver, unassignedOption, 5);

	}

	private void batchAssingAndUnassign(Boolean batchAssignFlag, Boolean catalogFlag, String optionID, String mt,
			List<String> countries, String channel) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		if (batchAssignFlag)
			hmcPage.ProductBuilder_batchAssign.click();
		else
			hmcPage.ProductBuilder_batchUnassign.click();
		// select catalog version
		driver.switchTo().frame(0);
		hmcPage.ProductBuilder_clickSelCatalogVersion.click();
		if (catalogFlag)
			hmcPage.ProductBuilder_selCatalogVersionOnline.click();
		else
			hmcPage.ProductBuilder_selCatalogVersionStaged.click();
		// select country
		for (String coutry : countries) {
			WebElement targetCountry = driver.findElement(By.xpath("//span[text()='" + coutry + "']"));
			Common.javascriptClick(driver, targetCountry);
			try {
				driver.findElement(
						By.xpath("//span[contains(text(),'" + coutry + "') and @class ='filter-option pull-left']"));
			} catch (NoSuchElementException e) {
				Assert.fail("batch assign by MT: country added failure: " + coutry);
			}
		}
		// search channel
		Select channel1 = new Select(hmcPage.ProductBuidler_channelCode);
		channel1.selectByVisibleText(channel);
		// search option
		hmcPage.ProductBuilder_showSearchOptionDialog.click();
		hmcPage.ProductBuilder_optionCodeInput.sendKeys(optionID);
		hmcPage.ProductBuilder_searchOptionBtn2.click();
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
				Dailylog.logInfo("...Batch Assign Option success...");
			else
				Dailylog.logInfo("...Batch UNassign Option success...");
		} else {
			if (batchAssignFlag)
				Dailylog.logInfo("...Batch Assign No MT searched out...");
			else
				Dailylog.logInfo("...Batch UNassign No MT searched out...");
		}
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	public void HandleJSpring(WebDriver driver, String testUrl, String account, String pwd) {
		if (driver.getCurrentUrl().contains("j_spring_security_check")) {
			driver.get(testUrl);
			B2BCommon.Login(b2bPage, account, pwd);
		}
	}
}
