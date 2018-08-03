package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE62Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String subseriesOne;
	String subseriesTwo;
	String MT;

	public BROWSE62Test(String store, String subseriesOne, String subseriesTwo,
			String MT) {
		this.Store = store;
		this.testName = "BROWSE-62";
		this.subseriesOne = subseriesOne;
		this.subseriesTwo = subseriesTwo;
		this.MT = MT;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup", "uxui", "p2", "b2c" })
	public void BROWSE62(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String ManaelSubseries = "ManualSubSeriesTest";
			String mktName = "Manual subseries test country mkt_name";
			String DesLong = "TEST MANUAL SUBSERIES With powerful processing, a superb operating system, and an 18-hour battery life, "
					+ "the ThinkPad T470 is designed to enhance your productivity, anywhere. Easy to use, "
					+ "deploy, and service, this 14” robust laptop has a host of cutting-edge technology, including solid-state storage, "
					+ "secure fingerprint reading, and advanced facial recognition. All of this, plus the legendary ThinkPad reliability and support.";
			String merchandisingTagPK = "8803498687410";

			// login HMC and crrate Manual Subseries Page
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			try {
				HMCCommon.searchOnlineProduct(driver, hmcPage, ManaelSubseries);
			} catch (NoSuchElementException e) {
				HMCCommon.searchOnlineProduct(driver, hmcPage, subseriesOne);
				hmcPage.Catalog_CategoryTab.click();
				Common.rightClick(driver,
						hmcPage.Catalog_Category_SuperCategories);
				hmcPage.PMIOverride_GroupCollection_SelectAll.click();
				Common.rightClick(driver,
						hmcPage.Catalog_Category_SuperCategories);
				hmcPage.Catalog_copy.click();
				Common.rightClick(driver, hmcPage.Home_ProductsLink);
				Common.mouseHover(driver, hmcPage.catalog_Products_rightCreate);
				Common.javascriptClick(driver,
						hmcPage.catalog_Products_manualSub_Create);
				hmcPage.Catalog_CategoryTab.click();
				Common.rightClick(driver,
						hmcPage.Catalog_Category_SuperCategories);
				hmcPage.Catalog_paste.click();
				Common.sendFieldValue(hmcPage.catalog_Products_articleNum,
						ManaelSubseries);
				Common.sendFieldValue(hmcPage.catalog_Products_identifier,
						ManaelSubseries);
				hmcPage.catalog_Products_CatalogOnlineVersion.click();
				hmcPage.catalog_Products_Approval.click();
				hmcPage.Catalog_PropertiesTab.click();
				//hmcPage.Nemo_InstallmentsCreate.click();
				hmcPage.Product_DisplayTo.sendKeys("PUBLIC_GLOBAL_B2C_UNIT");
				driver.findElement(
						By.xpath("//td[contains(text(),'PUBLIC_GLOBAL_B2C_UNIT')]"))
						.click();
				//hmcPage.SaveButton.click();
				hmcPage.Catalog_AdminTab.click();
				hmcPage.catalog_Products_VariantsType
						.sendKeys("NemoMachineTypeVariantProduct");
				driver.findElement(
						By.xpath("//td[contains(text(),'NemoMachineTypeVariantProduct')]"))
						.click();
				hmcPage.Nemo_InstallmentsCreate.click();
				//hmcPage.SaveButton.click();
				hmcPage.Catalog_PMIAttributeOverride.click();
				createPMIMktNameOverride(mktName, testData.B2C.getUnit());
				createPMILongDesOverride(DesLong, testData.B2C.getUnit());
				createPMIMerchTagOverride(merchandisingTagPK,
						testData.B2C.getUnit());
			}
			hmcPage.Home_closeSession.click();
			driver.manage().deleteAllCookies();
			Common.sleep(10000);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchOnlineProduct(driver, hmcPage, MT);
			hmcPage.Catalog_AdminTab.click();
			Common.sendFieldValue(hmcPage.Catalog_mktBaseProduct,
					ManaelSubseries);
			driver.findElement(
					By.xpath("//td[contains(text(),'" + ManaelSubseries + "')]"))
					.click();
			hmcPage.SaveButton.click();
			hmcPage.Home_closeSession.click();
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + subseriesTwo);
			
			
			if ((Common
					.checkElementExists(
							driver,
							driver.findElement(By.xpath("//button[contains(@id,'addToCart') and contains(@submitfromid,'addToCartFormTop') ]")),
							5))) {
				List<WebElement> addtoCart = driver
						.findElements(By
								.xpath("//button[contains(@id,'addToCart') and contains(@submitfromid,'addToCartFormTop') ]"));
				String[] partNo = new String[addtoCart.size() / 2];
				
				for (int i = 0; i < addtoCart.size() / 2; i++) {
					System.out.println(addtoCart.get(i)
							.getAttribute("submitfromid")
							.replace("addToCartFormTop", ""));
					 partNo[i] = addtoCart.get(i)
							.getAttribute("submitfromid")
							.replace("addToCartFormTop", "");
					}
				for (int i = 0; i < addtoCart.size() / 2; i++) {
					driver.get(testData.B2C.getHomePageUrl() + "/p/" + partNo[i]);
					Assert.assertEquals(b2cPage.PDP_mktName.getText(), mktName);
					Assert.assertEquals(b2cPage.PDP_DesLong.getText(), DesLong);
					//Assert.assertEquals(b2cPage.PDP_merchandisingTag.getText(), "NEW");
				}
			} else {
				setManualValidateLog("No product under this subseries " + subseriesTwo);
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void createPMIMktNameOverride(String MktName, String unit) {

		Common.rightClick(driver, hmcPage.PMIOverride_GroupAttribute);
		hmcPage.PMIOverride_GroupAttribute_Create.click();
		Common.switchToWindow(driver, 1);
		hmcPage.PMIOverride_MktName_Attribute.click();
		Common.sendFieldValue(hmcPage.PMIOverride_MktName_StringValue, MktName);
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.clear();
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.sendKeys(unit);
		String PMIOverride_MktName_GroupResult = ".//*[contains(@id,'AutocompleteReferenceEditor[in Attribute[.group]')][contains(@id,'"
				+ unit + "')]";
		Common.waitElementClickable(driver,
				driver.findElement(By.xpath(PMIOverride_MktName_GroupResult)),
				10);
		driver.findElement(By.xpath(PMIOverride_MktName_GroupResult)).click();
		hmcPage.PMIOverride_CTAButtonLink_Active.click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
	}

	public void createPMILongDesOverride(String LongDes, String unit) {

		Common.rightClick(driver, hmcPage.PMIOverride_GroupText);
		hmcPage.PMIOverride_GroupText_Create.click();
		Common.switchToWindow(driver, 1);
		hmcPage.PMIOverride_DesLong_Attribute.click();
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.clear();
		driver.switchTo().frame(
				driver.findElement(By
						.xpath(".//iframe[contains(@id,'setvalue_ifr')]")));
		Common.sendFieldValue(hmcPage.PMIOverride_DesLong_TextValue, LongDes);
		driver.switchTo().defaultContent();
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.sendKeys(unit);
		String PMIOverride_MktName_GroupResult = ".//*[contains(@id,'AutocompleteReferenceEditor[in Attribute[.group]')][contains(@id,'"
				+ unit + "')]";
		Common.waitElementClickable(driver,
				driver.findElement(By.xpath(PMIOverride_MktName_GroupResult)),
				10);
		driver.findElement(By.xpath(PMIOverride_MktName_GroupResult)).click();
		hmcPage.PMIOverride_CTAButtonLink_Active.click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
	}

	public void createPMIMerchTagOverride(String merchandisingTag, String unit) {

		Common.rightClick(driver, hmcPage.PMIOverride_GroupCollection);
		hmcPage.PMIOverride_GroupCollection_Create.click();
		Common.switchToWindow(driver, 1);
		hmcPage.PMIOverride_merchandisingTag_Attribute.click();
		hmcPage.PMIOverride_merchandisingTag_Search.click();
		Common.switchToWindow(driver, 2);
		Common.sendFieldValue(hmcPage.PMIOverride_merchandisingTag_PK,
				merchandisingTag);
		hmcPage.B2BUnit_SearchButton.click();
		String merchandisingTagResult = ".//div[contains(@id,'"
				+ merchandisingTag
				+ "') and contains(@id,'GenericItemListEntry')]";
		Common.waitElementClickable(driver,
				driver.findElement(By.xpath(merchandisingTagResult)), 10);
		driver.findElement(By.xpath(merchandisingTagResult)).click();
		hmcPage.HMCB2CUnitSiteAttributes_SettingUse.click();
		Common.switchToWindow(driver, 1);
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.clear();
		hmcPage.PMIOverride_CTAButtonLink_GroupInput.sendKeys(unit);
		String PMIOverride_CTAButtonLink_GroupResult = ".//*[contains(@id,'AutocompleteReferenceEditor[in Attribute[.group]')][contains(@id,'"
				+ unit + "')]";
		Common.waitElementClickable(driver, driver.findElement(By
				.xpath(PMIOverride_CTAButtonLink_GroupResult)), 10);
		driver.findElement(By.xpath(PMIOverride_CTAButtonLink_GroupResult))
				.click();
		hmcPage.PMIOverride_CTAButtonLink_Active.click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
	}
}
