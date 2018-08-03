package TestScript.B2B;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18080Test extends SuperTestClass {

	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public String homepageUrl;
	public String cartUrl;
	public String B2B_Country;
	public String WebsitesSearchID;
	public String b2bunitNo;
	private String originalPrice1;
	private String originalPrice2;
	private String newMTMPrice;
	public String cartName;
	public String dataInput;
	public String xpath;
	public String mtmNum;
	private String unit;
	
	
	public NA18080Test(String Store, String siteName, String countrySearch, String b2bUnit,String unit) {
		this.Store = Store;
		this.WebsitesSearchID = siteName;
		this.B2B_Country = countrySearch;
		this.b2bunitNo = b2bUnit;
		this.testName = "NA-18080";
		this.unit = unit;
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("€", "");

		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18080(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			Actions actions = new Actions(driver);
			// step1
			Dailylog.logInfoDB(1, "HMC:priceChangeMessage", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.HMCBaseCommerce_baseCommerce.click();
			hmcPage.HMCBaseCommerce_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(WebsitesSearchID);

			hmcPage.baseStore_search.click();
			Common.doubleClick(driver, hmcPage.BaseStore_SearchResult);
			hmcPage.baseStore_administration.click();
			hmcPage.baseStore_priceChangeMessageInput.clear();
			hmcPage.baseStore_priceChangeMessageInput.sendKeys("The price has change");
			hmcPage.Types_SaveBtn.click();

			Dailylog.logInfoDB(2, "login B2B website", Store, testName);
			// step 3 add product to cart
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open('about:blank','_blank');", "");
			Set<String> winHandels = driver.getWindowHandles();
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(1));
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
//			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			Dailylog.logInfoDB(3, "add 2 products into cart", Store, testName);
			driver.get(testData.B2B.getHomePageUrl() + "/cart");
			if (Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]"))) {
				b2bPage.CartPage_EmptyCart.click();
				Thread.sleep(3000);
			}

			b2bPage.cartPage_quickOrder.clear();
			mtmNum = testData.B2B.getDefaultMTMPN1();
//			mtmNum = "20HAS1L000";
			b2bPage.cartPage_quickOrder.sendKeys(mtmNum);
			b2bPage.cartPage_addButton.click();
			b2bPage.cartPage_quickOrder.clear();
			
		
//			b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultAccessoryPN1());
			if(Store.contains("AU")){
				b2bPage.cartPage_quickOrder.sendKeys(testData.B2B.getDefaultAccessoryPN1());
			}else{
				b2bPage.cartPage_quickOrder.sendKeys("20JJS21N00_edu");
			}
			
			
			b2bPage.cartPage_addButton.click();
			originalPrice1 = b2bPage.cartPage_FirstItermPrice.getText();
			originalPrice2 = b2bPage.cartPage_SecondItermPrice.getText();
			Dailylog.logInfoDB(3, mtmNum + " price : " + originalPrice1, Store, testName);
			Dailylog.logInfoDB(3, testData.B2B.getDefaultAccessoryPN1() + " price : " + originalPrice2, Store,
					testName);

			Thread.sleep(3000);

			// step4-7 save cart and verify cart information
			Dailylog.logInfoDB(4, "save cart", Store, testName);
			b2bPage.cartPage_saveCartButton.click();
			b2bPage.cartPage_privateSaveCartn.click();
			cartName = Common.getDateTimeString();

			b2bPage.cartPage_realsavecartname.clear();
			b2bPage.cartPage_realsavecartname.sendKeys(cartName);
			b2bPage.cartPage_SaveCart_save.click();
			Assert.assertTrue(driver.getCurrentUrl().contains("save-carts"));

			Dailylog.logInfoDB(7, "verify vart information", Store, testName);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//h3[text()='" + cartName + "']")));
			driver.findElement(By.xpath("//h3[text()='" + cartName + "']/../../td[@data-title='viewCart']/a")).click();

			Assert.assertEquals(originalPrice1,
					driver.findElement(By.xpath("(//div[@class='cart-item-pricing-and-quantity-itemPrice-amount'])[1]"))
							.getText());

			Assert.assertEquals(originalPrice2,
					driver.findElement(By.xpath("(//div[@class='cart-item-pricing-and-quantity-itemPrice-amount'])[2]"))
							.getText());

			// go back to hmc
			// Step 8
			Dailylog.logInfoDB(8, "create the webprice rule", Store, testName);
			driver.switchTo().window(it.get(0));
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			Thread.sleep(2000);
			driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
			Thread.sleep(2000);
			if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
				hmcPage.PricingCockpit_username.sendKeys("lisong2");
				hmcPage.PricingCockpit_password.sendKeys("Lelisong2");
				hmcPage.PricingCockpit_Login.click();
			}
			Thread.sleep(2000);
			hmcPage.PricingCockpit_b2bPriceRules.click();
			Thread.sleep(2000);
			Common.waitElementClickable(driver, hmcPage.B2BPriceRules_CreateNewGroup, 12000);
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_CreateNewGroup);
			System.out.println("B2BPriceRules_CreateNewGroup");
			Common.waitElementClickable(driver, hmcPage.B2BPriceRules_SelectGroupType, 12000);
			Thread.sleep(1000);
			hmcPage.B2BPriceRules_SelectGroupType.click();
			System.out.println("B2BPriceRules_SelectGroupType");
			hmcPage.B2BPriceRules_WebPrices.click();
			System.out.println("B2BPriceRules_WebPrices");
			hmcPage.B2BPriceRules_Continue.click();
			System.out.println("B2BPriceRules_Continue");

			hmcPage.webPricesGroup_title.clear();
			hmcPage.webPricesGroup_title.sendKeys("wp_" + Store + cartName);

//		

			// Country
			dataInput = B2B_Country;
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
			fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

			// Catalog
			dataInput = "Nemo Master Multi Country Product Catalog - Online";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

			// B2Cunit
		
			Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
			hmcPage.B2CPriceRules_B2CunitSelect.click();
			hmcPage.B2CPriceRules_SearchInput.clear();
			hmcPage.B2CPriceRules_SearchInput.sendKeys(b2bunitNo);
			driver.findElement(By.xpath(".//*[contains(text(),'" + b2bunitNo + "')]")).click();
		
			hmcPage.B2BPriceRules_FixTempPrice.sendKeys(GetPriceValue(originalPrice1) - 1 + "");
			
			Thread.sleep(2000);
			
			
			// Material
			xpath = "//span[contains(text(),'" + mtmNum
					+ "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
			fillRuleInfo(driver, hmcPage, "Material", mtmNum, hmcPage.B2CPriceRules_MaterialSelect, xpath);

			// Add Price Rule To Group
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
			// hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- first time");
			Thread.sleep(2000);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
			// hmcPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- first time");
			Thread.sleep(1000);
		
		
			Thread.sleep(3000);

			// Step 9
			Dailylog.logInfoDB(9, "update the price", Store, testName);
			driver.switchTo().defaultContent();
			hmcPage.Home_Nemo.click();
			hmcPage.Home_Contract.click();
			hmcPage.Contract_productCode.clear();
			hmcPage.Contract_productCode.sendKeys(mtmNum);
			hmcPage.ContaractID_Search.click();
			Common.doubleClick(driver, hmcPage.Contract_searchResult);
			Common.waitElementClickable(driver,
					driver.findElement(By.xpath("//div[contains(text(),'" + mtmNum + "')]")),
					10);
			Common.doubleClick(driver,
					driver.findElement(By.xpath("//div[contains(text(),'" + mtmNum + "')]")));
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(2));
			Common.doubleClick(driver, hmcPage.contract_ContractPrice);
			tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(3));
			newMTMPrice = GetPriceValue(hmcPage.contract_EditPrice.getAttribute("value").toString()) + 20.00 + "";
			hmcPage.contract_EditPrice.clear();
			hmcPage.contract_EditPrice.sendKeys(newMTMPrice);
			hmcPage.contractPrice_Save.click();
			driver.switchTo().window(tabs.get(2));
			hmcPage.contractPrice_Save.click();
			driver.switchTo().window(tabs.get(0));
			hmcPage.contractPrice_Save.click();

			// Step 10
			Dailylog.logInfoDB(10, "run solr", Store, testName);
			hmcPage.System_Menu.click();
			hmcPage.Home_facetSearch.click();
			hmcPage.home_Hot_Update.click();
			tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(4));
			hmcPage.IndexerHotUpdate_solrConfigName.click();
			driver.findElement(By
					.xpath(".//select[contains(@id,'solrFacetSearchConfig')]/option[contains(text(),'mcnemob2bIndex')]"))
					.click();
			hmcPage.IndexerHotUpdate_nextBtn.click();
			Common.waitElementClickable(driver, hmcPage.IndexerHotUpdate_indexTyeDD, 15);
			hmcPage.IndexerHotUpdate_indexTyeDD.click();
			hmcPage.IndexerHotUpdate_productIndexType.click();
			hmcPage.IndexerHotUpdate_updateIndexRadioBtn.click();

			hmcPage.IndexerHotUpdate_nextBtn.click();
			Thread.sleep(5000);
			Common.rightClick(driver, hmcPage.IndexerHotUpdate_emptyRowToAddProduct);
			hmcPage.IndexerHotUpdate_addProductOption.click();
			Thread.sleep(3000);
			tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(5));

			hmcPage.IndexerHotUpdate_articleNumber.clear();
			hmcPage.IndexerHotUpdate_articleNumber.sendKeys(mtmNum);
			hmcPage.Template_searchBtn.click();
			Common.doubleClick(driver, hmcPage.hotUpdate_ArticleResultOnline);
			driver.switchTo().window(tabs.get(4));
			hmcPage.hotUpdate_StartHotUpdate.click();

			
			while (!Common.isElementExist(driver,
					By.xpath(".//*[contains(text(),'Indexing finished successfully')]"))) {
				Thread.sleep(5000);
			}

			// Step 11-13
			Dailylog.logInfoDB(11, "view saved carts", Store, testName);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			driver.navigate().refresh();
			String tempPrice = driver
					.findElement(By.xpath("(//div[@class='cart-item-pricing-and-quantity-itemPrice-amount'])[1]"))
					.getText();
			Dailylog.logInfoDB(11, "tempPrice : " + tempPrice, Store, testName);
			assert GetPriceValue(originalPrice1) < GetPriceValue(
					driver.findElement(By.xpath("(//div[@class='cart-item-pricing-and-quantity-itemPrice-amount'])[1]"))
							.getText());

			Dailylog.logInfoDB(12, "open cart", Store, testName);

			b2bPage.cartDetailsPage_openCart.click();

			Assert.assertEquals(tempPrice, driver
					.findElement(By.xpath("((.//*[contains(@class,'finalPrice-label')])[1]/../div)[2]/dl")).getText());


		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	@AfterTest(alwaysRun = true,  enabled = true)
	public void rollback(ITestContext ctx) throws InterruptedException, MalformedURLException {
		try {
			Dailylog.logInfoDB(14, "rollback", Store, testName); // roll back
			SetupBrowser();
			driver.get(testData.HMC.getHomePageUrl());
			hmcPage = new HMCPage(driver);
			HMCCommon.Login(hmcPage, testData);

			Common.sleep(5000);
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(0);
			// // loginPricingCockpit();
			hmcPage.PricingCockpit_b2bPriceRules.click();

			// rule type
			Thread.sleep(8000);
			dataInput = "Web Prices";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Rule Type", dataInput, hmcPage.B2CPriceRules_ruleType, xpath);

			// group
			dataInput = "wp_" + Store;
			
			xpath = "//span[contains(text(),'" + dataInput + "') and @class='select2-match']";

			while (true) {
				Thread.sleep(1000);
				Common.waitElementClickable(driver, hmcPage.B2CPriceRules_group, 30);
				hmcPage.B2CPriceRules_group.click();
				// hmcPage.B2CPriceRules_group.click();
				Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
				hmcPage.B2CPriceRules_SearchInput.clear();
				hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
				int count = driver.findElements(By.xpath(xpath)).size();
				System.out.println("web price rule count: " + count);
				if (count != 0) {
					List<WebElement> rules = driver.findElements(By.xpath(xpath));
					rules.get(0).click();
					// filter
					hmcPage.B2CPriceRules_FilterBtn.click();
					// delete
					WebElement deleteBtn = driver.findElement(
							By.xpath("//td[contains(text(),'" + dataInput + "')]/..//a[contains(text(),'Delete')]"));
					Common.waitElementClickable(driver, deleteBtn, 30);
					deleteBtn.click();
					Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
					hmcPage.B2CPriceRules_deleteInput.clear();
					hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
					hmcPage.B2CPriceRules_deleteConfirm.click();
					System.out.println("Rule Deleted!");
					// clear all
					Thread.sleep(5000);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();",
							hmcPage.B2CPriceRules_clearAll);
					Thread.sleep(5000);
				}

				if (count == 1 || count == 0)
					break;

			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1,
			String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);
	}
}
