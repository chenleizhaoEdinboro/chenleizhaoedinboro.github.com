package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SHOPE336Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2BPage b2bPage;
	String subScription;
	String ctoProduct;
	String sectionTitle;

	public SHOPE336Test(String store, String subscription, String product, String sectionTitle) {
		this.Store = store;
		this.subScription = subscription;
		this.ctoProduct = product;
		this.sectionTitle = sectionTitle;
		this.testName = "SHOPE-336";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2b" })
	public void SHOP336(ITestContext ctx) throws InterruptedException {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);

			String b2bProudctUrl = testData.B2B.getHomePageUrl() + "/p/" + ctoProduct;
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
				String billingCycleTxt = getMsgValue(billingCycleKey, "B2B", language);
				Dailylog.logInfoDB(i + 1, "billingCycleTxt: " + billingCycleTxt, Store, testName);

				driver.get(testData.B2B.getLoginUrl());
				B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
				driver.get(b2bProudctUrl);

				try {
					Common.sleep(1000);
					Common.waitElementClickable(driver, b2bPage.PDPPage_AddAccessories, 60);
					b2bPage.PDPPage_AddAccessories.click();
				} catch (Exception e) {
					Assert.fail(ctoProduct + "Add accessories invalid");
				}

				Common.sleep(1000);

				b2bPage.PB_SoftwareTab.click();

				By b2cSubXpath = By.xpath("//div[contains(text(),'" + sectionTitle + "')]/../..//input[@value='" + subScription + "']");

				System.out.println(b2cSubXpath);
				Assert.assertTrue(Common.checkElementDisplays(driver, b2cSubXpath, 5), "is b2cSubXpath display on B2B");

				By billCycleTextX = By.xpath("//input[@value='" + subScription + "']/..//span[@class='option-price']");
				Assert.assertTrue(Common.checkElementDisplays(driver, billCycleTextX, 5), "is billCycleText display on B2B");

				String billCycleText = driver.findElement(billCycleTextX).getText();
				billCycleText = billCycleText.substring(billCycleText.indexOf("/")).trim();

				System.out.println(billCycleText);
				Assert.assertEquals(billCycleText, "/" + billingCycleTxt);

			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);

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
		default:
			wcmsID = "NoValue";
			break;
		}

		return wcmsID;

	}

}
