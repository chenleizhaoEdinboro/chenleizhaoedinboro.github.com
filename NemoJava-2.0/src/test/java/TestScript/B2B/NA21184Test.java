package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class NA21184Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;

	private String productNo =  "20HMS3Q046";//"20FES3EF00";
	private String b2bUS = "";
	private String loginName = "oraclepdit@lenovo.com";
	private String loginPwd = "Lenovotest1!";
	private String newUnit = "1214210470";
	private String newDmu = "oraclepditglobal";

	public NA21184Test(String store) {
		this.Store = store;
		this.testName = "NA-21184";
	}

	private String changeURL(String newDmu, String newUnit) {
		String newUrl = testData.B2B.getLoginUrl();
		String[] segments = newUrl.split("/");
		String dmu = segments[4];
		String unit = segments[7];
		newUrl = newUrl.replace(dmu, newDmu).replace(unit, newUnit);
		System.out.println(newUrl);
		return newUrl;
	}

	private void enableQuickOrder(WebElement enableQuickOrder,boolean isFirst) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		// driver.manage().deleteAllCookies();
		// Thread.sleep(20000);
		// driver.get(testData.HMC.getHomePageUrl());
		// Step 1 logon hmc
		// HMCCommon.Login(hmcPage, testData);

		// Step 2 open B2B unit search page
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		if(isFirst){
			// Step 3 search B2B Unit by ID
			hmcPage.B2BUnit_SearchIDTextBox.clear();
			System.out.println("B2BUNIT IS :" + newUnit);
			hmcPage.B2BUnit_SearchIDTextBox.sendKeys(newUnit);
			hmcPage.B2BUnit_SearchButton.click();
			hmcPage.B2BUnit_ResultItem.click();
		}
		
		// Step 4 maintain site attribute for the B2B unit
		hmcPage.B2BUnit_siteAttribute.click();
		// Step 5 check the value of enable quick order(value NO is checked)
		enableQuickOrder.click();
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Thread.sleep(5000);
		hmcPage.Home_B2BCommerceLink.click();
	}

	private void productQuickOrder(WebElement enableQuickOrder,boolean isFirst) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		// driver.manage().deleteAllCookies();
		// Thread.sleep(20000);
		// driver.get(testData.HMC.getHomePageUrl());
		// HMCCommon.Login(hmcPage, testData);

		// Step 9 HMC->Catalog->Products
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		if(isFirst){
			// Step 10 search the product by ID
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productNo);
			hmcPage.Catalog_SearchButton.click();
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		}
		
		// Step 11 administration tab->Enable Quick Order value:YES
		// Step 20 administration tab->Enable Quick Order value:NO
		hmcPage.Catalog_Administration.click();
		enableQuickOrder.click();
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Thread.sleep(5000);
		hmcPage.Home_CatalogLink.click();
	}

	private void runSolr() throws InterruptedException {

		// Step 12 System->Facet search->Indexer hot-update wizard
		hmcPage.Home_System.click();
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_indexerHotUpdWiz.click();
		// Step 13 tab:Set solr facet search configuration
		switchToWindow(1);
		// Solr configuration name:mcnemob2bindex
		hmcPage.IndexerHotUpdate_solrConfigName.click();
		hmcPage.IndexerHotUpdate_mcnemob2bIndex.click();
		// Step 14 Index type:Product Indexer operation:Update Index
		hmcPage.IndexerHotUpdate_nextBtn.click();
		hmcPage.IndexerHotUpdate_indexTyeDD.click();
		hmcPage.IndexerHotUpdate_productIndexType.click();
		hmcPage.IndexerHotUpdate_updateIndexRadioBtn.click();
		// Step 15 click next to go to Select items to index add the product
		// to the list
		hmcPage.IndexerHotUpdate_nextBtn.click();
		Common.rightClick(driver, hmcPage.IndexerHotUpdate_emptyRowToAddProduct);
		hmcPage.IndexerHotUpdate_addProductOption.click();
		switchToWindow(2);
		// product number
		hmcPage.IndexerHotUpdate_articleNumber.sendKeys(productNo);
		// catalog version
		hmcPage.IndexerHotUpdate_catalogSelect.click();
		hmcPage.IndexerHotUpdate_multiCountryOption.click();
		hmcPage.IndexerHotUpdate_searchBtn.click();
		Common.doubleClick(driver, hmcPage.IndexerHotUpdate_searchResult);
		switchToWindow(1);
		Assert.assertEquals(hmcPage.IndexerHotUpdate_articleNum.getText(), productNo);
		// Step 16 click start to run the solr hot update
		hmcPage.IndexerHotUpdate_startJobBtn.click();
		Thread.sleep(10000);
		new WebDriverWait(driver, 240000)
				.until(ExpectedConditions.visibilityOf(hmcPage.IndexerHotUpdate_indexSuccessMsgBox));
		Assert.assertEquals(hmcPage.IndexerHotUpdate_indexSuccessMsgBox.getText(), "Indexing finished successfully.");
		hmcPage.IndexerHotUpdate_doneBtn.click();

		switchToWindow(0);
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_System.click();
		System.out.println("hot update end");
	}

	private void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(alwaysRun= true,groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA21184(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			// Step 5 check the value of enable quick order(value NO is checked)
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();

			enableQuickOrder(hmcPage.Catalog_enableQuickOrderFalse,true);

			// Step 6 log on website
			b2bUS = changeURL(newDmu, newUnit);
			driver.get(b2bUS);
			B2BCommon.Login(b2bPage, loginName, loginPwd);
			
			// Step 7 check the product on the front end website
			b2bUS = driver.getCurrentUrl();
			driver.get(b2bUS + "p/" + productNo);
			Common.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//button[contains(@class,'add_to_cart')]")),
					"PLP page need display (Step7)");
			
			// Step 8 change the site attribute for the B2B unit(checked the
			// value as YES)

			enableQuickOrder(hmcPage.Catalog_enableQuickOrderTrue,false);
			// Step 9 HMC->Catalog->Products
			// Step 10 search the product by ID
			// Step 11 administration tab->Enable Quick Order value:YES
			productQuickOrder(hmcPage.Catalog_enableQuickOrderTrue,true);
			// Step 12-16
			runSolr();
			// Step 17 log on website

			// Step 18 search the product on the website(can not see the
			// product)
			driver.get(b2bUS + "p/" + productNo);
			driver.navigate().refresh();
			Common.sleep(5000);
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//button[contains(@class,'add_to_cart')]")),
					"PLP page need disappear (Step18)");
			// Step 19 can add to cart by quick order

			b2bPage.HomePage_CartIcon.click();
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				b2bPage.cartPage_emptyCartButton.click();
			}
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(productNo);
			b2bPage.cartPage_addButton.click();
			Common.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[text()='" + productNo + "']")),
					"cannot use quick order (Step19)");

			// Step 20 continue to step 9,10,11 (value no)
			productQuickOrder(hmcPage.Catalog_enableQuickOrderFalse,false);

			// Step 21 excute hot update for the product same step as 12-16
			runSolr();

			// Step 22 search the product on the website(can see the product)
			driver.get(b2bUS + "p/" + productNo);
			driver.navigate().refresh();
			Common.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//button[contains(@class,'add_to_cart')]")),
					"PLP page need display (Step22)");
			// Step 23 attribute name:Hide quick order site attribute value:NO

			enableQuickOrder(hmcPage.Catalog_enableQuickOrderFalse,false);

			// Step 24 20FMS6JM00 will display on the website and can use quick
			// order
			driver.get(b2bUS + "p/" + productNo);
			driver.navigate().refresh();
			Common.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//button[contains(@class,'add_to_cart')]")),
					"PLP page need display (Step24)");

			b2bPage.HomePage_CartIcon.click();
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				b2bPage.cartPage_emptyCartButton.click();
			}
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(productNo);
			b2bPage.cartPage_addButton.click();
			Common.sleep(5000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//span[text()='" + productNo + "']")),
					"cannot use quick order (Step24)");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
}
