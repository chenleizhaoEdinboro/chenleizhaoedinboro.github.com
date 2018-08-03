package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA19418Test extends SuperTestClass {

	private HMCPage hmcPage;
	private String pknumber;
	private String name;
	private String description;
	private String content;
	private String productnumber;
	private B2CPage b2cPage;
	public NA19418Test(String store,String pknumber,String name,String description,String content,String productnumber) {
		this.Store = store;
		this.testName = "NA-19418";
		this.pknumber = pknumber;
		this.name = name;
		this.description = description;
		this.content = content;
		this.productnumber = productnumber;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA19418(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			
			//=========== Step:1 Login HMC ===========//
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);
			
			//=========== Step:2 Navigate to Nemo->[CustomTab] ===========//
			Common.sleep(3000);
			hmcPage.Home_Nemo.click();
			Common.javascriptClick(driver, hmcPage.Nemo_customtab);
			hmcPage.CustomTab_attributeselectname.click();
			hmcPage.CustomTab_nameinput.sendKeys(name);
			hmcPage.CustomTab_search.click();
			if(Common.checkElementExists(driver, hmcPage.CustomTab_noresult, 3)) {
				Common.rightClick(driver, hmcPage.Nemo_customtab);
				hmcPage.Nemo_customtab_label.click();
				Common.scrollToElement(driver, hmcPage.Nemo_properties);
				hmcPage.Nemo_customtab_name.clear();
				hmcPage.Nemo_customtab_name.sendKeys(name);
				hmcPage.Nemo_customtab_description.clear();
				hmcPage.Nemo_customtab_description.sendKeys(description);
				if(!hmcPage.Nemo_customtab_checkbox.isSelected()) {
					hmcPage.Nemo_customtab_checkbox.click();
				}
				if(!hmcPage.Nemo_customtab_flag.isSelected()) {
					hmcPage.Nemo_customtab_flag.click();
				}
				hmcPage.Nemo_customtab_create.click();
				Common.sleep(3000);
			}else {
				hmcPage.CustomTab_resultEdit.click();
				if(!hmcPage.Nemo_customtab_checkbox.isSelected()) {
					hmcPage.Nemo_customtab_checkbox.click();
				}
				if(!hmcPage.Nemo_customtab_flag.isSelected()) {
					hmcPage.Nemo_customtab_flag.click();
				}
				hmcPage.SaveButton.click();
				Common.sleep(3000);
			}
			hmcPage.baseStore_administration.click();
			Common.scrollToElement(driver, hmcPage.Nemo_lastchange);
			Common.doubleClick(driver,  hmcPage.Nemo_lastchange);
			Common.switchToWindow_Title(driver, "http://admin-pre-c-hybris.lenovo.com/hmc/hybris?wid=MC842x128625&prgrequest=true&null");
			hmcPage.baseStore_administration.click();
			Common.sleep(3000);
			Common.scrollToElement(driver, hmcPage.Nemo_desprition_pk);
			String text = Common.javaScriptGetText(driver, hmcPage.Nemo_desprition_pk);
			Dailylog.logInfoDB(2, "create "+text, Store, testName);
			
			//=========== Step:3 sesrch product ===========//
			driver.close();
			Common.switchToWindow(driver, 0);
			Common.sleep(3000);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			Common.sleep(2000);
			hmcPage.Catalog_ArticleNumberTextBox.clear();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productnumber);
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(3000);
			Common.doubleClick(driver, hmcPage.Nemo_customtab_result);
			Dailylog.logInfoDB(3, "sesrch product", Store, testName);
			
			//=========== Step:4 Navigate to PMI tab and check the showCustomTabs checkbox ===========//
			Common.sleep(3000);
			hmcPage.Catalog_PMITab.click();
			if(!hmcPage.Nemo_showcustom_check.isSelected()) {
				hmcPage.Nemo_showcustom_check.click();	
			}
			
			if(!Common.checkElementExists(driver, hmcPage.Nemo_pmi_show, 5)) {
				setShowTab(text);
			}else {
				Common.doubleClick(driver, hmcPage.Nemo_pmi_show);
				Common.switchToWindow_Title(driver, "Editor: [CustomTab] - hybris Management Console (hMC)");
				if(!hmcPage.Nemo_showcustom_active1.isSelected()) {
					hmcPage.Nemo_showcustom_active1.click();
				}
				if(!hmcPage.Nemo_showcustom_buy1.isSelected()) {
					hmcPage.Nemo_showcustom_buy1.click();
				}
				hmcPage.PaymentLeasing_saveAndCreate.click();
				driver.close();
				Common.switchToWindow(driver, 0);
				hmcPage.PaymentLeasing_saveAndCreate.click();
				Common.sleep(3000);
			}
			
			//=========== Step:5 Go to website to check how buttons work ===========//
			Common.sleep(3000);
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+productnumber);
			Common.scrollToElement(driver, b2cPage.Product_viewModel);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Buybuyfromreseller, 5),"Buy From Reseller is visable");
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Buybuyfromresellertab, 5),"BuyFromResellerTab is visable");
			Dailylog.logInfoDB(5, "Go to website to check how buttons work", Store, testName);
			
			//=========== Step:6 Go Click "View and Customize",Click "Buy from Reseller" button ===========//
			Common.sleep(3000);
			Common.javascriptClick(driver, b2cPage.Product_viewModel);
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.Buybuyfromreseller);
			Dailylog.logInfoDB(6, "Go Click \"View and Customize\",Click \"Buy from Reseller\" button", Store, testName);
			
			//=========== Step:7 Buy from reseller" also invisible ===========//
			driver.close();
			Common.sleep(3000);
			Common.switchToWindow(driver, 0);
			Common.scrollToElement(driver, hmcPage.Nemo_showcustom_check);
			hmcPage.Nemo_showcustom_check.click();
			Common.doubleClick(driver, hmcPage.Nemo_pmi_show1);
			Common.sleep(3000);
			Common.switchToWindow_Title(driver, "http://admin-pre-c-hybris.lenovo.com/hmc/hybris?wid=MC842x125242");
			hmcPage.Nemo_showcustom_active.click();
			hmcPage.Nemo_showcustom_buy.click();
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.sleep(3000);
			driver.close();
			Common.switchToWindow(driver, 0);
			hmcPage.PaymentLeasing_saveAndCreate.click();
			Common.sleep(3000);
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+productnumber);
			Common.sleep(2000);
			driver.navigate().refresh();
			closeHomePagePopUP(driver);
			Common.sleep(2000);
			Assert.assertTrue(!Common.checkElementExists(driver, b2cPage.Buybuyfromreseller, 5),"Buy From Reseller is visable");
			Assert.assertTrue(!Common.checkElementExists(driver, b2cPage.Buybuyfromresellertab, 5),"BuyFromResellerTab is visable");
			Dailylog.logInfoDB(7, "Buy from reseller\" also invisible", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void setShowTab(String text) throws InterruptedException {
		Common.scrollToElement(driver, hmcPage.Nemo_pmi_showempty);
		Common.rightClick(driver, hmcPage.Nemo_pmi_showempty);
		hmcPage.Nemo_pmi_addtab.click();
		Common.sleep(2000);
		Common.switchToWindow_Title(driver, "[CustomTab] search - hybris Management Console (hMC)");
		hmcPage.Nemo_pmi_inputpk.clear();
		hmcPage.Nemo_pmi_inputpk.sendKeys(text);
		hmcPage.Nemo_pmi_search.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Nemo_pmi_searchresult);
		Common.sleep(2000);
		Common.switchToWindow(driver, 0);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Dailylog.logInfoDB(4, "showCustomTabs checkbox", Store, testName);
	}
	
	public void closeHomePagePopUP(WebDriver driver) {
		String productNumberPopUp = "//*[contains(text(),'Close dialog')]";

	 if (Common.checkElementDisplays(driver, By.xpath(productNumberPopUp), 4)) {
			driver.findElement(By.xpath(productNumberPopUp)).click();
		}
	}
}
