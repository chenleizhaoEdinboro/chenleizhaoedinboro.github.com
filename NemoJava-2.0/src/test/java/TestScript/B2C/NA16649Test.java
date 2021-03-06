package TestScript.B2C;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16649Test extends SuperTestClass {

	String regionAUXpath;
	String regionUSXpath;
	String countryAUXpath;
	String countryUSXpath;
	B2CPage b2cPage;
	HMCPage hmcPage;

	public NA16649Test(String store) {
		this.Store = store;
		this.testName = "NA-16649";
	}

	@Test(alwaysRun = true, groups = { "browsegroup", "product", "p1", "b2c" })
	public void NA16649(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			countryAUXpath = "//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.country]')]/option[contains(text(),'AU')]";
			countryUSXpath = "//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.country]')]/option[contains(text(),'US')]";
			regionAUXpath = "//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.region]')]/option[contains(text(),'ANZ')]";
			regionUSXpath = "//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.region]')]/option[contains(text(),'NA)]";
			driver.get(testData.HMC.getHomePageUrl());

			HMCCommon.Login(hmcPage, testData);

			// Go to MultiCountryCatalogOnline
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(testData.B2C
					.getOverwritePN());
			hmcPage.Catalog_SearchButton.click();
			Common.doubleClick(driver,
					hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);

			// Go to Attribute Override tab
			hmcPage.Catalog_AttributeOverrideTabLink.click();
			// delete before atttibute override
			String valueRegion = "RegionAutoTestSummary"
					+ Common.getDateTimeString();
			if (Common.checkElementExists(driver,
					hmcPage.AttributeOverride_regionResult, 3)) {
				Common.rightClick(driver,
						hmcPage.AttributeOverride_regionResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver,
						hmcPage.AttributeOverride_regionResult);
				hmcPage.hmcOverride_rightRemove.click();
				Alert alert = driver.switchTo().alert();
				alert.accept();
				hmcPage.SaveButton.click();
			}
			if (Common.checkElementExists(driver,
					hmcPage.AttributeOverride_countryResult, 3)) {
				Common.rightClick(driver,
						hmcPage.AttributeOverride_countryResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver,
						hmcPage.AttributeOverride_countryResult);
				hmcPage.hmcOverride_rightRemove.click();
				Alert alert = driver.switchTo().alert();
				alert.accept();
				hmcPage.SaveButton.click();
			}

			if (Common.checkElementExists(driver,
					hmcPage.AttributeOverride_channelResult, 3)) {
				Common.rightClick(driver,
						hmcPage.AttributeOverride_channelResult);
				hmcPage.hmcOverride_SelectAll.click();
				Common.rightClick(driver,
						hmcPage.AttributeOverride_channelResult);
				hmcPage.hmcOverride_rightRemove.click();
				Alert alert = driver.switchTo().alert();
				alert.accept();
				hmcPage.SaveButton.click();
			}

			// setting the region override
			if (Store.equals("AU"))
				setOptionRegionAttributeOverride(regionAUXpath, valueRegion);
			else if (Store.equals("US"))
				setOptionRegionAttributeOverride(regionUSXpath, valueRegion);

			// check on region level
			HMCCommon.cleanRadis(hmcPage, testData.B2C.getOverwritePN());
			driver.manage().deleteAllCookies();
			Thread.sleep(10000);
			driver.get(testData.B2C.getHomePageUrl());
			driver.get(driver.getCurrentUrl() + "/p/"
					+ testData.B2C.getOverwritePN());

			driver.navigate().refresh();
			Thread.sleep(5000);
			try {
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewNavigationSummaryLabel, 3)) {
					Assert.assertEquals(
							b2cPage.Monitor_NewNavigationSummaryLabel.getText()
									.replaceAll(" ", ""), valueRegion);
				} else {
					Assert.assertEquals(b2cPage.Monitor_NavigationSummaryLabel
							.getText().replaceAll(" ", ""), valueRegion);
				}
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewSummaryLabel, 3)) {
					Assert.assertEquals(b2cPage.Monitor_NewSummaryLabel
							.getText().replaceAll(" ", ""), valueRegion);
				} else {
					Assert.assertEquals(b2cPage.Monitor_SummaryLabel.getText()
							.replaceAll(" ", ""), valueRegion);
				}
			} catch (Throwable e) {
				Thread.sleep(60000);
				driver.manage().deleteAllCookies();
				driver.get(testData.HMC.getHomePageUrl());
				driver.navigate().refresh();
				// hmcPage.Home_EndSessionLink.click();
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.cleanRadis(hmcPage, testData.B2C.getOverwritePN());
				driver.manage().deleteAllCookies();
				Thread.sleep(10000);
				driver.get(testData.B2C.getHomePageUrl());
				driver.get(driver.getCurrentUrl() + "/p/"
						+ testData.B2C.getOverwritePN());
				driver.navigate().refresh();
				Thread.sleep(5000);
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewNavigationSummaryLabel, 3)) {
					Assert.assertEquals(
							b2cPage.Monitor_NewNavigationSummaryLabel.getText()
									.replaceAll(" ", ""), valueRegion);
				} else {
					Assert.assertEquals(b2cPage.Monitor_NavigationSummaryLabel
							.getText().replaceAll(" ", ""), valueRegion);
				}
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewSummaryLabel, 3)) {
					Assert.assertEquals(b2cPage.Monitor_NewSummaryLabel
							.getText().replaceAll(" ", ""), valueRegion);
				} else {
					Assert.assertEquals(b2cPage.Monitor_SummaryLabel.getText()
							.replaceAll(" ", ""), valueRegion);
				}
			}

			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(testData.B2C
					.getOverwritePN());
			hmcPage.Catalog_SearchButton.click();
			Common.doubleClick(driver,
					hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);

			// setting the country override
			String valueCountry = "CountryAutoTestSummary"
					+ Common.getDateTimeString();
			if (Store.equals("AU"))
				setOptionCountryAttributeOverride(countryAUXpath, valueCountry);
			else if (Store.equals("US"))
				setOptionCountryAttributeOverride(countryUSXpath, valueCountry);
			Thread.sleep(10000);
			HMCCommon.cleanRadis(hmcPage, testData.B2C.getOverwritePN());
			// check on Country Level

			driver.manage().deleteAllCookies();
			Thread.sleep(10000);
			driver.get(testData.B2C.getHomePageUrl());
			driver.get(driver.getCurrentUrl() + "/p/"
					+ testData.B2C.getOverwritePN());
			driver.navigate().refresh();
			Thread.sleep(5000);
			try {
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewNavigationSummaryLabel, 3)) {
					Assert.assertEquals(
							b2cPage.Monitor_NewNavigationSummaryLabel.getText()
									.replaceAll(" ", ""), valueCountry);
				} else {
					Assert.assertEquals(b2cPage.Monitor_NavigationSummaryLabel
							.getText().replaceAll(" ", ""), valueCountry);
				}
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewSummaryLabel, 3)) {
					Assert.assertEquals(b2cPage.Monitor_NewSummaryLabel
							.getText().replaceAll(" ", ""), valueCountry);
				} else {
					Assert.assertEquals(b2cPage.Monitor_SummaryLabel.getText()
							.replaceAll(" ", ""), valueCountry);
				}
			} catch (Throwable e) {
				Thread.sleep(120000);
				driver.manage().deleteAllCookies();
				driver.get(testData.HMC.getHomePageUrl());
				// hmcPage.Home_EndSessionLink.click();
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.cleanRadis(hmcPage, testData.B2C.getOverwritePN());
				driver.manage().deleteAllCookies();
				Thread.sleep(10000);
				driver.get(testData.B2C.getHomePageUrl());
				driver.get(driver.getCurrentUrl() + "/p/"
						+ testData.B2C.getOverwritePN());
				Thread.sleep(10000);
				driver.navigate().refresh();
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewNavigationSummaryLabel, 3)) {
					Assert.assertEquals(
							b2cPage.Monitor_NewNavigationSummaryLabel.getText()
									.replaceAll(" ", ""), valueCountry);
				} else {
					Assert.assertEquals(b2cPage.Monitor_NavigationSummaryLabel
							.getText().replaceAll(" ", ""), valueCountry);
				}
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewSummaryLabel, 3)) {
					Assert.assertEquals(b2cPage.Monitor_NewSummaryLabel
							.getText().replaceAll(" ", ""), valueCountry);
				} else {
					Assert.assertEquals(b2cPage.Monitor_SummaryLabel.getText()
							.replaceAll(" ", ""), valueCountry);
				}
			}

			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(testData.B2C
					.getOverwritePN());
			hmcPage.Catalog_SearchButton.click();
			Common.doubleClick(driver,
					hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);

			String valueChannel = "ChannelAutoTestSummary"
					+ Common.getDateTimeString();
			if (Store.equals("AU"))
				setOptionChannelAttributeOverride(countryAUXpath, valueChannel);
			else if (Store.equals("US"))
				setOptionChannelAttributeOverride(countryUSXpath, valueChannel);
			Thread.sleep(10000);
			HMCCommon.cleanRadis(hmcPage, testData.B2C.getOverwritePN());

			// check on Channel Level
			driver.manage().deleteAllCookies();
			Thread.sleep(10000);
			driver.get(testData.B2C.getHomePageUrl());
			driver.get(driver.getCurrentUrl() + "/p/"
					+ testData.B2C.getOverwritePN());
			Thread.sleep(10000);
			driver.navigate().refresh();
			try {
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewNavigationSummaryLabel, 3)) {
					Assert.assertEquals(
							b2cPage.Monitor_NewNavigationSummaryLabel.getText()
									.replaceAll(" ", ""), valueChannel);
				} else {
					Assert.assertEquals(b2cPage.Monitor_NavigationSummaryLabel
							.getText().replaceAll(" ", ""), valueChannel);
				}
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewSummaryLabel, 3)) {
					Assert.assertEquals(b2cPage.Monitor_NewSummaryLabel
							.getText().replaceAll(" ", ""), valueChannel);
				} else {
					Assert.assertEquals(b2cPage.Monitor_SummaryLabel.getText()
							.replaceAll(" ", ""), valueChannel);
				}
			} catch (Throwable e) {
				Thread.sleep(120000);
				driver.manage().deleteAllCookies();
				driver.get(testData.HMC.getHomePageUrl());
				// hmcPage.Home_EndSessionLink.click();
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.cleanRadis(hmcPage, testData.B2C.getOverwritePN());
				driver.manage().deleteAllCookies();
				Thread.sleep(10000);
				driver.get(testData.B2C.getHomePageUrl());
				driver.get(driver.getCurrentUrl() + "/p/"
						+ testData.B2C.getOverwritePN());
				Thread.sleep(10000);
				driver.navigate().refresh();
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewNavigationSummaryLabel, 3)) {
					Assert.assertEquals(
							b2cPage.Monitor_NewNavigationSummaryLabel.getText()
									.replaceAll(" ", ""), valueChannel);
				} else {
					Assert.assertEquals(b2cPage.Monitor_NavigationSummaryLabel
							.getText().replaceAll(" ", ""), valueChannel);
				}
				if (Common.checkElementDisplays(driver,
						b2cPage.Monitor_NewSummaryLabel, 3)) {
					Assert.assertEquals(b2cPage.Monitor_NewSummaryLabel
							.getText().replaceAll(" ", ""), valueChannel);
				} else {
					Assert.assertEquals(b2cPage.Monitor_SummaryLabel.getText()
							.replaceAll(" ", ""), valueChannel);
				}
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void setOptionRegionAttributeOverride(String regionXpath,
			String message) {
		hmcPage.Catalog_AttributeOverrideTabLink.click();
		if (Common.checkElementExists(driver,
				hmcPage.AttributeOverride_regionResult, 3)) {
			Common.rightClick(driver, hmcPage.AttributeOverride_regionResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_regionResult);
			hmcPage.hmcOverride_rightRemove.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SaveButton.click();
		}
		Common.rightClick(driver, hmcPage.AttributeOverride_regionOverride);
		hmcPage.AttributeOverride_rightCreate.click();
		Common.switchToWindow(driver, 1);
		hmcPage.AttributeOverride_attribute.click();
		hmcPage.hmcOverrid_Value.clear();
		hmcPage.hmcOverrid_Value.sendKeys(message);
		hmcPage.hmcOverride_Active.click();
		driver.findElement(By.xpath(regionXpath)).click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.SaveButton.click();
	}

	public void setOptionCountryAttributeOverride(String countryXpath,
			String message) {
		hmcPage.Catalog_AttributeOverrideTabLink.click();
		if (Common.checkElementExists(driver,
				hmcPage.AttributeOverride_countryResult, 3)) {
			Common.rightClick(driver, hmcPage.AttributeOverride_countryResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_countryResult);
			hmcPage.hmcOverride_rightRemove.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SaveButton.click();
		}
		Common.rightClick(driver, hmcPage.AttributeOverride_countryOverride);
		hmcPage.AttributeOverride_rightCreate.click();
		Common.switchToWindow(driver, 1);
		hmcPage.AttributeOverride_attribute.click();
		hmcPage.hmcOverrid_Value.clear();
		hmcPage.hmcOverrid_Value.sendKeys(message);
		hmcPage.hmcOverride_Active.click();
		driver.findElement(By.xpath(countryXpath)).click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.SaveButton.click();
	}

	public void setOptionChannelAttributeOverride(String countryXpath,
			String message) {
		hmcPage.Catalog_AttributeOverrideTabLink.click();
		if (Common.checkElementExists(driver,
				hmcPage.AttributeOverride_channelResult, 3)) {
			Common.rightClick(driver, hmcPage.AttributeOverride_channelResult);
			hmcPage.hmcOverride_SelectAll.click();
			Common.rightClick(driver, hmcPage.AttributeOverride_channelResult);
			hmcPage.hmcOverride_rightRemove.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			hmcPage.SaveButton.click();
		}
		Common.rightClick(driver, hmcPage.AttributeOverride_channelOverride);
		hmcPage.AttributeOverride_rightCreate.click();
		Common.switchToWindow(driver, 1);
		hmcPage.AttributeOverride_attribute.click();
		hmcPage.hmcOverrid_Value.clear();
		hmcPage.hmcOverrid_Value.sendKeys(message);
		hmcPage.hmcOverride_Active.click();
		driver.findElement(By.xpath(countryXpath)).click();
		hmcPage.hmcOverrid_b2cChannel.click();
		hmcPage.SaveButton.click();
		driver.close();
		Common.switchToWindow(driver, 0);
		hmcPage.SaveButton.click();
	}
}
