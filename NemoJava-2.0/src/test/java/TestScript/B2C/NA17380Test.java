package TestScript.B2C;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.CartCheckOut;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;
public class NA17380Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String partnumber;
	public NA17380Test(String store,String partnumber) {
		this.Store = store;
		this.testName = "NA-17380";
		this.partnumber = partnumber;
	}
	@Test(priority=0,alwaysRun = true,groups = {"accountgroup", "uxui", "p2", "b2c"})
	public void NA17380(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getloginPageUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			String customiseButton = "(//ol[contains(@class,'product')]/li/div//div[contains(@class,'footer')]/button[contains(@id,'Cart')])[1]";
			String ViewSavedQuote_SoldOutPopUpMsg = ".//*[@id='quote-convert-to-order']//label[contains(@class,'sold-out-popup')]";
			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL);
			Common.sleep(2000);
			B2CCommon.DoubleLogin(driver, testData, b2cPage, b2cHomeURL, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(), Browser);
			B2CCommon.closeHomePagePopUP(driver);
			Dailylog.logInfoDB(1, "login b2c", Store, testName);
			//=========== Step:2 Find a product ===========//
			Dailylog.logInfoDB(2, "MTM part Num is: " + partnumber, Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Dailylog.logInfoDB(2, "Cart is cleared", Store, testName);
			Common.sleep(2000);
			//=========== Step:3 Add one product ===========//
			b2cPage.HomePage_CartIcon.click();
			Common.sleep(3000);
			CartCheckOut.CheckOutUIHandler(b2cPage, "quickadd", partnumber);
			Dailylog.logInfoDB(3, "Added 1st product into cart", Store, testName);
			Common.sleep(2000);
			//=========== Step:4 Add another product ===========//
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Common.sleep(1500);
			String laptopPageURL = driver.getCurrentUrl();
			B2CCommon.addToCartB2C(1, driver, laptopPageURL, customiseButton);
			Dailylog.logInfoDB(4, "Added 2nd product into cart", Store, testName);
			Common.sleep(3000);
			//=========== Step:5 Go to cart page and do request quote ===========//
			b2cPage.HomePage_CartIcon.click();
			Common.scrollToElement(driver, b2cPage.Quote_requestBtn);
			b2cPage.Quote_requestBtn.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(5, "Request Quote button is clicked.", Store, testName);
			if(!Common.checkElementExists(driver, b2cPage.Override_RepID, 5)) {
				b2cPage.Quote_requestBtn.click();
			}
			if(Common.checkElementExists(driver, b2cPage.Override_RepID, 5)) {

//				b2cPage.Override_RepID.clear();
				b2cPage.Override_RepID.sendKeys(testData.B2C.getRepID());
				Common.sleep(3000);
				if(!Common.checkElementExists(driver, b2cPage.Override_SubmitQuote, 5)) {
//					b2cPage.Override_RepID.clear();
				b2cPage.Override_RepID.clear();
				b2cPage.Override_RepID.sendKeys(testData.B2C.getRepID());
				Common.sleep(3000);
				if(!Common.checkElementExists(driver, b2cPage.Override_SubmitQuote, 5)) {
					b2cPage.Override_RepID.clear();
					b2cPage.Override_RepID.sendKeys("1234567890");
				}
				b2cPage.Override_SubmitQuote.click();
				Common.sleep(3000);
			}
			String quoteNum = b2cPage.SavedQuote_QuoteNum.getText();
			Dailylog.logInfoDB(5, "quoteNum get", Store, testName);
			
			//=========== Step:6 Go to My Account and view saved quote ===========//
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			Common.sleep(1500);
			b2cPage.MyAccount_ViewSavedQuote.click();
			Dailylog.logInfoDB(6, "View Saved quote is clicked.", Store, testName);
			driver.findElement(By.xpath("(//p/a[contains(@href,'" + quoteNum + "')])[1]")).click();
			Dailylog.logInfoDB(6, "View more is clicked for the saved quote.", Store, testName);
			//=========== Step:7 Open New Browser Window ===========//
			((JavascriptExecutor)driver).executeScript("(window.open(''))");
			Common.switchToWindow(driver, 1);
			//=========== Step:8 Go to HMC ===========//
			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(8, "Logged into HMC", Store, testName);
			Common.sleep(3000);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			Common.sleep(2000);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			Dailylog.logInfoDB(8, "Clicked search results", Store, testName);
			Common.sleep(2000);
			//=========== Step:9 Go to Site Attributes ===========//
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(2000);
			Dailylog.logInfoDB(9, "Site Attribute tab is clicked.", Store, testName);
			//=========== Step:10 Set Sold to and temp unavailable messages ===========//			
			hmcPage.B2CUnit_SiteAttributeTab_SoldOutMessage.clear();
			hmcPage.B2CUnit_SiteAttributeTab_SoldOutMessage.sendKeys("SoldOut");
			hmcPage.B2CUnit_SiteAttributeTab_TempUnavailableMessage.clear();
			hmcPage.B2CUnit_SiteAttributeTab_TempUnavailableMessage.sendKeys("TempUnavailable");
			hmcPage.HMC_Save.click();
			Dailylog.logInfoDB(10, "Sold Out and Temp unavailable msg are saved", Store, testName);
			Common.sleep(3000);	
			//=========== Step:11 Set Catalog Version ===========//
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partnumber);
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			Dailylog.logInfoDB(11, "Click on search result(Searched with part number)", Store, testName);
			//=========== Step:12 Select PMI tab ===========//
			hmcPage.Catalog_PMITab.click();
			Dailylog.logInfoDB(12, "PMI tab is clicked.", Store, testName);
			//=========== Step:13 Set Product Marketing status as "soldout" ===========//
			setMarketingStatus(hmcPage.Catalog_ProductStatus_SoldOut);
			Dailylog.logInfoDB(13, "Product marketing status is set to 'soldOut.", Store, testName);
			hmcPage.Home_System.click();
			cleanProductCache();
			Dailylog.logInfoDB(13, "Product cache is cleaned", Store, testName);
			Common.sleep(1500);
			//=========== Step:14 Go to B2C site and click convert to order ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			Common.sleep(3000);


			if(Common.checkElementExists(driver, b2cPage.Save_quote_redbanner, 5)) {
				String soldOutMsg = b2cPage.Save_quote_redbanner.getText();
				Dailylog.logInfoDB(14, "Product status after setting the status to 'soldout' :" + soldOutMsg, Store, testName);
				Assert.assertTrue(soldOutMsg.contains("Sold"));
			}
			Common.javascriptClick(driver, b2cPage.Quote_convertOrder);
//			b2cPage.Quote_convertOrder.click();		
			String soldOutMsg = b2cPage.Save_quote_redbanner.getText();
			Dailylog.logInfoDB(14, "Product status after setting the status to 'soldout' :" + soldOutMsg, Store, testName);
			Assert.assertTrue(soldOutMsg.contains("Sold"));
			b2cPage.Quote_convertOrder.click();		
			Common.sleep(2000);
			//=========== Step:15 Check the pop up after clicking on 'Convert to order' ===========//
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(ViewSavedQuote_SoldOutPopUpMsg)));
			String soldOutMsg_PopUp = driver.findElement(By.xpath(ViewSavedQuote_SoldOutPopUpMsg)).getText();
			Dailylog.logInfoDB(15, "Sold Out msg is: "+soldOutMsg_PopUp, Store, testName);
			//=========== Step:16 Click 'Got it' button ===========//
			b2cPage.ViewSavedQuote_GotItButtonOnPopUp.click();
			//Assert.assertFalse(driver.findElement(By.xpath(ViewSavedQuote_SoldOutPopUpMsg)).isDisplayed());
			//=========== Step:17 Change product marketing status to "temp unavailable" ===========//
			Common.switchToWindow(driver, 1);
			hmcPage.Home_ProductsLink.click();
			setMarketingStatus(hmcPage.Catalog_ProductStatus_TempUnavailable);
			Dailylog.logInfoDB(17, "Product marketing status is set to 'temp unavailable." , Store, testName);
			cleanProductCache();
			Common.sleep(1500);
			//=========== Step:18 Check "temp unavailable" status in B2C ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			Common.sleep(3000);
			String tempUnavailableMsg = b2cPage.Save_quote_redbanner.getText();
			Dailylog.logInfoDB(18, "Product status after setting the status to 'temp Unavailable' :" + tempUnavailableMsg, Store, testName);
			Assert.assertTrue(tempUnavailableMsg.contains("Unavailable"));
			b2cPage.Quote_convertOrder.click();		
			Common.sleep(2000);
			//=========== Check the pop up after clicking on 'Convert to order' ===========//
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(ViewSavedQuote_SoldOutPopUpMsg)));
			String tempUnavailableMsg_PopUp = driver.findElement(By.xpath(ViewSavedQuote_SoldOutPopUpMsg)).getText();
			Dailylog.logInfoDB(18, tempUnavailableMsg_PopUp, Store, testName);
			//=========== Click 'Got it' button ===========//
			b2cPage.ViewSavedQuote_GotItButtonOnPopUp.click();
			Assert.assertFalse(driver.findElement(By.xpath(ViewSavedQuote_SoldOutPopUpMsg)).isDisplayed());
			}
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	@Test(priority=1,alwaysRun = true,groups = {"accountgroup", "uxui", "p2", "b2c"})
	public void rollBack(ITestContext ctx){
		try{
			SetupBrowser();
			driver.manage().deleteAllCookies();
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			hmcPage = new HMCPage(driver);
			driver.get(hmcLoginURL);
			Common.sleep(2000);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(2000);
			hmcPage.Home_CatalogLink.click();
			hmcPage.Home_ProductsLink.click();
			hmcPage.Catalog_ArticleNumberTextBox.sendKeys(partnumber);
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Catalog_SearchButton.click();
			Common.sleep(2000);
			Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
			Common.sleep(2000);
			hmcPage.Catalog_PMITab.click();
			setMarketingStatus(hmcPage.Catalog_ProductStatus_InIt);
			Common.sleep(3000);
			hmcPage.HMC_Logout.click();
			Common.sleep(1500);
		}
		catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	private void setMarketingStatus(WebElement productStatus){
		hmcPage.Catalog_Products_MarketingStatus.click();
		productStatus.click();
		Common.sleep(2000);
		hmcPage.HMC_Save.click();
		Common.sleep(3000);		
	}
	private void cleanProductCache(){		
		hmcPage.Home_RadisCacheCleanLink.click();
		driver.switchTo().frame(0);
		hmcPage.Radis_CleanProductTextBox.sendKeys(partnumber);
		hmcPage.Radis_CleanButton.click();
		Common.sleep(15000);
		driver.switchTo().alert().accept();
		Common.sleep(1000);
		driver.switchTo().defaultContent();
	}
}