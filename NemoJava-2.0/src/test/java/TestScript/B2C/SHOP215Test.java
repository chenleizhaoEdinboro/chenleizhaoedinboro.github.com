package TestScript.B2C;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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

public class SHOP215Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String subScription;
	String ctoProduct;
	String sectionTitle;

	public SHOP215Test(String store, String subscription, String product, String sectionTitle) {
		this.Store = store;
		this.subScription = subscription;
		this.ctoProduct = product;
		this.sectionTitle = sectionTitle;
		this.testName = "SHOPE-215";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void SHOP215(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);

			boolean isNewPB = true;
			switchPB(driver, hmcPage, isNewPB);
			check(isNewPB);

			isNewPB = false;
			switchPB(driver, hmcPage, isNewPB);
			check(isNewPB);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

//	@Test(priority = 1, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
//	public void rollback(ITestContext ctx) throws MalformedURLException {
//
//		SetupBrowser();
//		hmcPage = new HMCPage(driver);
//
//		switchPB(driver, hmcPage, true);
//
//	}

	private void check(boolean isNewUI) throws InterruptedException {
		driver.get(testData.B2C.getHomePageUrl());
		driver.manage().deleteAllCookies();
		driver.get(testData.B2C.getHomePageUrl());
		B2CCommon.login(b2cPage, "usbuyer@yopmail.com", "1q2w3e4r");
		String b2cProductUrl = testData.B2C.getHomePageUrl() + "/p/" + ctoProduct + "/customize?";
		Dailylog.logInfoDB(1, "ctoProduct: " + ctoProduct, Store, testName);
		String selectedBillingCycle = billCycleSetting(subScription);
		Dailylog.logInfoDB(1, "selectedBillingCycle: " + selectedBillingCycle, Store, testName);
		String billingCylce[] = { "Annual", "Monthly", "Quarterly", "Semi_Annual", "Trial" };
		String billingCylce1[] = new String[billingCylce.length];
		int n = 0;
		billingCylce1[n] = selectedBillingCycle;
		for (String cycle : billingCylce) {
			if (!cycle.equals(selectedBillingCycle)) {
				billingCylce1[++n] = cycle;
			}
		}
		for (String cycle : billingCylce1)
			Dailylog.logInfoDB(1, "billingCycles to verify: " + cycle, Store, testName);

		for (int i = 0; i < billingCylce1.length; i++) {
			String cycle = billingCylce1[i];
			if (i != 0) {
				selectedBillingCycle = billCycleSetting(subScription, cycle);
				Dailylog.logInfoDB(i + 1, "selectedBillingCycle: " + selectedBillingCycle, Store, testName);
			}
			String billingCycleKey = "SubscriptionOption.mktBillingCycle." + selectedBillingCycle;
			Dailylog.logInfoDB(i + 1, "billingCycleKey: " + billingCycleKey, Store, testName);
			String language = "English";
			if (Store.contains("JP"))
				language = "Janpanese";
			String billingCycleTxt = getMsgValue(billingCycleKey, "B2C", language);
			Dailylog.logInfoDB(i + 1, "billingCycleTxt: " + billingCycleTxt, Store, testName);
			try {
				driver.get(testData.B2C.getHomePageUrl());
			} catch (TimeoutException e) {
				driver.navigate().refresh();
			}

			B2CCommon.handleGateKeeper(b2cPage, testData);
			try {
				driver.get(b2cProductUrl);
			} catch (TimeoutException e) {
				driver.navigate().refresh();
			}

			Common.sleep(5000);
			Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 30);
			Common.javascriptClick(driver, b2cPage.Product_AddToCartBtn);
			//b2cPage.Product_AddToCartBtn.click();


			Common.sleep(1000);

			b2cPage.PB_softwareTab.click();

			By b2cSubXpath = By.xpath("//span[@class='section-title' and text()='" + sectionTitle + "']/../following-sibling::div[1]//input[@value='" + subScription + "']/..");
			if (!isNewUI)
				b2cSubXpath = By.xpath("//div[contains(text(),'" + sectionTitle + "')]/../..//input[@value='" + subScription + "']");

			System.out.println(b2cSubXpath);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cSubXpath, 5), "is b2cSubXpath display on B2C");

			By billCycleTextX = By.xpath("//input[@value='" + subScription + "']/..//span[@class='option-billing-cycle']");
			if (!isNewUI)
				billCycleTextX = By.xpath("//input[@value='" + subScription + "']/..//span[@class='option-price']");
			Assert.assertTrue(Common.checkElementDisplays(driver, billCycleTextX, 5), "is billCycleText display on B2C");

			String billCycleText = driver.findElement(billCycleTextX).getText();
			if (!isNewUI)
				billCycleText = billCycleText.substring(billCycleText.indexOf("/")).trim();

			System.out.println(billCycleText);
			Assert.assertEquals(billCycleText, "/" + billingCycleTxt);

		}
	}

	private void searchProduct(String category, String productID) {
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productID);
		hmcPage.Catalog_CatalogVersion.click();
		if (category.equals("staged")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogStaged.click();
		} else if (category.equals("online")) {
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		}
		hmcPage.Catalog_SearchButton.click();
		hmcPage.products_resultItem.click();
	}

	private String billCycleSetting(String subscription, String... billingCycle) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		searchProduct("online", subscription);
		hmcPage.Products_administration.click();
		String selectedCycle = hmcPage.Products_billingCycleSelected.getText().trim();
		if (billingCycle.length != 0 && !selectedCycle.equals(billingCycle[0])) {
			Select billCycleSel = new Select(hmcPage.Products_billingCycle);
			billCycleSel.selectByVisibleText(billingCycle[0]);
			hmcPage.SaveButton.click();
			Common.sleep(500);
			selectedCycle = hmcPage.Products_billingCycleSelected.getText().trim();
		}
		return selectedCycle;
	}

	public String getMsgValue(String key, String channel, String language) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.home_internationalization.click();
		hmcPage.home_messages.click();
		hmcPage.home_message.click();
		if (!Common.checkElementDisplays(driver, hmcPage.message_searchPart, 3)) {
			hmcPage.message_searchPart.click();
		}
		hmcPage.message_searchKey.clear();
		hmcPage.message_searchKey.sendKeys(key);
		Select channelSel = new Select(hmcPage.Message_zChannel);
		channelSel.selectByVisibleText(channel);
		Select languageSel = new Select(hmcPage.Message_zLanguage);
		languageSel.selectByVisibleText(language);
		hmcPage.B2BUnit_SearchButton.click();
		Common.waitElementVisible(driver, hmcPage.B2BCustomer_1stSearchedResult);
		hmcPage.B2BCustomer_1stSearchedResult.click();
		String msg = hmcPage.Message_zValue.getAttribute("value");
		hmcPage.Home_EndSessionLink.click();
		return msg;
	}

	public String getWCMSWebsiteID(String storeid) {

		String wcmsID = "";

		switch (storeid) {
		case "AU":
			wcmsID = "auweb";
			break;
		case "CA":
			wcmsID = "capublicsite";
			break;
		case "US":
			wcmsID = "uspublicsite";
			break;
		case "USEPP":
			wcmsID = "usaffinitysite";
			break;
		case "CA_AFFINITY":
			wcmsID = "caaffinitysite";
			break;
		case "JP":
			wcmsID = "jpweb";
			break;
		case "GB":
			wcmsID = "gbweb";
			break;
		case "HK":
			wcmsID = "hkweb";
			break;
		case "US_BPCTO":
			wcmsID = "bpcto";
			break;
		case "US_SMB":
			wcmsID = "ussmbsite";
			break;
		default:
			wcmsID = "NoValue";
			break;
		}

		return wcmsID;

	}

	private void switchPB(WebDriver driver, HMCPage hmcPage, boolean isNewPB) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.hmcHome_WCMS.click();
		hmcPage.wcmsPage_websites.click();
		hmcPage.WCMS_Website_ID.clear();
		hmcPage.WCMS_Website_ID.sendKeys(getWCMSWebsiteID(Store));
		hmcPage.WCMS_Website_SearchButton.click();
		hmcPage.B2BCustomer_1stSearchedResult.click();
		hmcPage.WCMS_Website_Administration.click();
		if (isNewPB)
			hmcPage.WCMS_newPBExperienceTrue.click();
		else
			hmcPage.WCMS_newPBExperienceFalse.click();
		hmcPage.SaveButton.click();
		hmcPage.Home_EndSessionLink.click();
	}
}
