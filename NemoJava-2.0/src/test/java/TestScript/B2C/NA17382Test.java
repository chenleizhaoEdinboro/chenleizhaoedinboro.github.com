package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import TestScript.SuperTestClass;

public class NA17382Test extends SuperTestClass{
	private HMCPage hmcPage;
	private B2CPage b2cPage;
	private String partnumber;
	private String cartName = Common.getDateTimeString();

	public NA17382Test(String store,String partnumber) {
		this.Store = store;
		this.testName = "NA-17382";
		this.partnumber = partnumber;
	}

	@Test(alwaysRun = true,groups = {"accountgroup", "uxui", "p2", "b2c"})
	public void NA17382(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String customiseButton = "(//ol[contains(@class,'product')]/li/div//div[contains(@class,'footer')]/button[contains(@id,'Cart')])[1]";
			String b2cHomeURL = testData.B2C.getloginPageUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();
			String addToCartButton = "(//div[contains(@class,'productListing')]/div//form/button)[1]";
			String ViewSavedQuote_SoldOutPopUpMsg = ".//*[@id='popup-sold-out']//td/label";

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL);
			Common.sleep(2000);
			B2CCommon.DoubleLogin(driver, testData, b2cPage, b2cHomeURL, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			B2CCommon.closeHomePagePopUP(driver);

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
			
			//=========== Step:5 Go to cart page and save the cart ===========//
			if(Common.checkElementExists(driver, b2cPage.Cart_saveCart, 5)) {
				b2cPage.Cart_saveCart.click();
				Dailylog.logInfoDB(5, "Save Cart button is clicked.", Store, testName);
				Common.sleep(2000);
				b2cPage.Cart_nameTextBox.sendKeys(cartName);
				Common.sleep(1500);
				b2cPage.Cart_saveCartBtn.click();
				Dailylog.logInfoDB(5, "Cart is saved", Store, testName);
				Common.sleep(4000);
				Dailylog.logInfoDB(5, "Saved Cart Name: " + cartName, Store, testName);
			}

			//=========== Step:6 Go to My Account and view saved cart ===========//
			//b2cPage.Navigation_MyAccountIcon.click();
			driver.get(testData.B2C.getHomePageUrl() + "/my-account");
			Common.sleep(1500);
			Common.javascriptClick(driver, b2cPage.MyAccount_ViewSavedCartHistory);
			Dailylog.logInfoDB(6, "View Saved cart is clicked.", Store, testName);
			if(driver.findElement(By.xpath("//*[@id=\"cntTable\"]")).getText().contains(cartName)) {
				driver.findElement(By.xpath("//td[contains(.,'" + cartName + "')]/../td//div[contains(.,'View')]")).click();
				Dailylog.logInfoDB(6, "View button is clicked for the saved cart.", Store, testName);
			}
			Common.sleep(2000);
			if(Common.checkElementExists(driver, b2cPage.Cart_openCart, 5)) {
				b2cPage.Cart_openCart.click();
			}
			if(Common.checkElementExists(driver, b2cPage.HomePage_CartIcon, 5)) {
				b2cPage.HomePage_CartIcon.click();
			}
			Common.sleep(2000);

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

			//=========== Step:10 Set 'Sold to' and 'temp unavailable' messages ===========//			
			hmcPage.B2CUnit_SiteAttributeTab_SoldOutMessage.clear();
			hmcPage.B2CUnit_SiteAttributeTab_SoldOutMessage.sendKeys("SoldOut");
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
			Common.sleep(3000);

			//=========== Step:14 Go to B2C site and click Checkout button ===========//
			Common.switchToWindow(driver, 0);
			driver.navigate().refresh();
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Save_Cart_redbanner, 5)) {
				Common.sleep(1000);			
				String soldOutMsg = b2cPage.Save_Cart_redbanner.getText();
				Assert.assertTrue(soldOutMsg.contains("Sold"));
				Dailylog.logInfoDB(14, "Product status after setting the status to 'soldout' :" + soldOutMsg, Store, testName);
			}
			Common.javascriptClick(driver, b2cPage.lenovo_checkout);
			Common.sleep(2000);

			//=========== Step:15 Check the pop up after clicking on 'Checkout' button ===========//
			Assert.assertTrue(Common.isElementExist(driver, By.xpath(ViewSavedQuote_SoldOutPopUpMsg)));
			String soldOutMsg_PopUp = driver.findElement(By.xpath(ViewSavedQuote_SoldOutPopUpMsg)).getText();
			Dailylog.logInfoDB(15, "Sold Out msg is: "+soldOutMsg_PopUp, Store, testName);

			//=========== Step:16 Click 'Continue' button on soldout popup ===========//
			b2cPage.CartPage_SoldOutPopUP_ContinueBtn.click();
			Common.sleep(4000);
			String shippingPageURL = driver.getCurrentUrl();
			Assert.assertTrue(shippingPageURL.contains("checkout"));

		}catch (Throwable e)
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
	
	@Test(priority=1,alwaysRun = true)
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
}
