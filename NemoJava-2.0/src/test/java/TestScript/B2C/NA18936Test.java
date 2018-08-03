package TestScript.B2C;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.Assert;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18936Test extends SuperTestClass{

	private static HMCPage hmcPage;
	private static String pageName = "IFA 2016 Landing AU";

	public NA18936Test(String store) {
		this.Store = store;
		this.testName = "NA-18936";
	}

	@Test(priority = 0)
	public void NA18936(ITestContext ctx) {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);

			//Step 1:
			Dailylog.logInfoDB(1, "Login hmc->WCMS->Pages", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.hmcHome_WCMS.click();
			Thread.sleep(1000);
			hmcPage.wcmsPage_pages.click();
			hmcPage.wcmsPagesPage_pageNameTextBox.sendKeys(pageName);
			Select selectCatalogVersion = new Select(hmcPage.wcmsPagesPage_catalogVersionDD);
			selectCatalogVersion.selectByVisibleText("auContentCatalog - Online");
			hmcPage.wcmsPagesPage_searchButton.click();
			Thread.sleep(1000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(".//*[@id='Content/StringDisplay[page_00006ZJL]_span']")), "No results found for page name: IFA 2016 Landing AU");

			//Step 2:
			Dailylog.logInfoDB(2, "Open page: page_00006ZJL and check tab Administration", Store, testName);
			hmcPage.wcmsPagesPage_searchedResult.click();
			Common.doubleClick(driver, hmcPage.wcmsPagesPage_searchedResult);
			Thread.sleep(1000);
			hmcPage.wcmsPagesPage_administrationTab.click();
			Thread.sleep(1000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(".//td[@class='label']//div[contains(.,'CSS JS Override Components:')]")), "CSS JS Override components are not available.");

			//Step 3:
			Dailylog.logInfoDB(3, "Open the page level CSS JS Override Components one by one", Store, testName);
			//Performing validations on Group-1 page level CSS JS Override Components
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[global-events-2016-style-lato-wmd-css]_img']", "1");
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[global-events-2016-menu-wmd-css]_img']", "2");
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[global-events-2016-responsive-wmd-css]_img']", "3");
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[global-events-2016-bootstrap-wmd-css]_img']", "4");
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[global-events-2016-animate-wmd-css]_img']", "5");
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[global-events-2016-ifa-custom-wmd-css]_img']", "6");
			group1BodyFieldValidation(".//*[@id='Content/GenericItemListEntry[lenovo-launch-ifa-2016-extras-v3-wmd-js]_img']", "7");
			//Performing validations on Group-2 page level CSS JS Override Components
			group2PreviewFileValidation(".//*[@id='Content/GenericItemListEntry[js-tw2016-common_scripts_min]_img']", "1");
			group2PreviewFileValidation(".//*[@id='Content/GenericItemListEntry[js-tw2016-functions]_img']", "2");
			group2PreviewFileValidation(".//*[@id='Content/GenericItemListEntry[js-tw2016-jquery.fancybox.pack]_img']", "3");
			group2PreviewFileValidation(".//*[@id='Content/GenericItemListEntry[js-tw2016-jquery.fancybox-media]_img']", "4");
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}

	public void group1BodyFieldValidation(String componentLocator, String i) throws InterruptedException{
		if(Common.isElementExist(driver, By.xpath(componentLocator))){
			driver.findElement(By.xpath(componentLocator)).click();
			Thread.sleep(1000);
			Common.switchToWindow(driver, 1);
			Thread.sleep(1000);
			Assert.assertNotEquals(hmcPage.wcmsPagesPageAdministration_BodyTextBox.getText(), null);
			Dailylog.logInfoDB(3, "Validated group 1 CSS file# " + i, Store, testName);
			driver.close();
			Common.switchToWindow(driver, 0);
		}else {
			Dailylog.logInfoDB(3, "Group 1 CSS file# " + i + " not present!", Store, testName);
		}
	}
	
	public void group2PreviewFileValidation(String componentLocator, String i) throws InterruptedException{
		if(Common.isElementExist(driver, By.xpath(componentLocator))){
			driver.findElement(By.xpath(componentLocator)).click();
			Thread.sleep(1000);
			Common.switchToWindow(driver, 1);
			Thread.sleep(1000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(".//a[contains(@onclick,'open_editor')]")), "Media file link is not displayed.");
			hmcPage.cssjsOverridePage_mediaFileLink.click();
			Thread.sleep(1000);
			Common.switchToWindow(driver, 2);
			Thread.sleep(1000);
			Assert.assertTrue(driver.getTitle().contains("Editor: Media - hybris Management Console (hMC)"), "Incorrect Media page title.");
			hmcPage.cssjsOverridePage_previewMediaFileButton.click();
			Thread.sleep(1000);
			Common.switchToWindow(driver, 3);
			Thread.sleep(1000);
			Assert.assertTrue(driver.getTitle().contains("Preview"), "Incorrect Preview page displayed!");
			Dailylog.logInfoDB(3, "Validated CSS JS file Group2 component#: " + i, Store, testName);
			driver.close();
			Common.switchToWindow(driver, 2);
			Thread.sleep(1000);
			driver.close();
			Common.switchToWindow(driver, 1);
			Thread.sleep(1000);
			driver.close();
			Common.switchToWindow(driver, 0);
			Thread.sleep(1000);
		}else {
			Dailylog.logInfoDB(3, "Group 2 CSS file# " + i + " is not present!", Store, testName);
		}
	}
}
