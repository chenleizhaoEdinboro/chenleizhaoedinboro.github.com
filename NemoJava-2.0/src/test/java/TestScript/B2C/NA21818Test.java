package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21818Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	public NA21818Test(String store) {
		this.Store = store;
		this.testName = "NA-21818";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21818(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			// 1 , Site manager go to unit level to set up the configuration for Qty limits and message
			Dailylog.logInfoDB(1, "Site manager go to unit level to set up the configuration for Qty limits and message", Store, testName);
			setTeleCartQuantityLimits();
			
			//2,* As a tele rep, go to website and start ASM,  search one customer and start session then purchase accessory option to the shopping cart.
			//** Browse accessories, select a certain accessory to go to accessory list page. 
			//** Select a certain facet search option from facet search. 
			Dailylog.logInfoDB(2,  "login with telesales account and browse accessories ", Store, testName);
			
			loginB2CTele("customer");
			
			driver.get(tele_cartUrl);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			driver.get(tele_homePageUrl);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			b2cPage.Accessories_browseAllCategory.click();
			b2cPage.Accessories_category_Monitor.click();
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//*[@id='accessoryProductListing']//h3/a)[3]")));
			Thread.sleep(4000);
			
			//3, Click the add to cart btn.
			Dailylog.logInfoDB(3, "Click the add to cart btn.", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='addToCart']")).click();
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='cboxLoadedContent']")).isDisplayed());
			
			//4, Change quantity, make it exceed 5 (Line Item Quantity). 
			Dailylog.logInfoDB(4, "Change quantity, make it exceed 5 (Line Item Quantity).", Store, testName);
			
			b2cPage.Accessories_cart_popup.clear();
			b2cPage.Accessories_cart_popup.sendKeys("8");
			Thread.sleep(6000);
			
			Assert.assertTrue(b2cPage.Accessories_cart_popup_AddToCart.isEnabled());
			
			b2cPage.Accessories_cart_popup_AddToCart.click();
			
			driver.get(tele_cartUrl);
			
			String cartNumber = b2cPage.cartPage_Quantity.getAttribute("value").toString();
			
			Assert.assertTrue(cartNumber.equals("8"));
			
			B2CCommon.emptyCart(driver, b2cPage);
			
						
			//5, Change quantity again, lower than 5(Line Item Quantiy)
			Dailylog.logInfoDB(5, "Change quantity again, lower than 5(Line Item Quantiy)", Store, testName);
			
			goToAccessoryMonitorPage();
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//*[@id='accessoryProductListing']//h3/a)[3]")));
			Thread.sleep(4000);
			b2cPage.accessoryAddtoCart.click();
			
			b2cPage.Accessories_cart_popup.clear();
			b2cPage.Accessories_cart_popup.sendKeys("3");
			
			Thread.sleep(5000);
			
			Assert.assertTrue(b2cPage.Accessories_cart_popup_AddToCart.isEnabled());
			
			//6. Go to shopping cart. 
			Dailylog.logInfoDB(6, "Go to shopping cart.", Store, testName);
			
			b2cPage.Accessories_cart_popup_AddToCart.click();
			
			driver.get(tele_cartUrl);
			
			String productQuantity = driver.findElement(By.xpath("//input[@id='quantity0']")).getAttribute("value").toString();
			
			Assert.assertTrue(productQuantity.equals("3"));			
			
			//7, Change the line item quantity at item entry level and click *Update* btn
			//* Make quantity higher than line item quantity 5 configured above
			//* Changed line Item Quantity should be lower than ASM cart qty 15
			Dailylog.logInfoDB(7, " Changed line Item Quantity should be lower than ASM cart qty 15", Store, testName);
			
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("6");
			
			b2cPage.cartPage_Quantity_update.click();
			
			String changeProdNumber = b2cPage.cartPage_Quantity.getAttribute("value").toString();
			
			Assert.assertTrue(changeProdNumber.equals("6"));
			
			
			//8, * Go back to product pages to purchase more and put them into shopping cart.
			//** make total cart quantity exceed 10.

			//* Go to cart page.
			Dailylog.logInfoDB(8, "Go back to product pages to purchase more and put them into shopping cart, make total cart quantity exceed 10.", Store, testName);

			
			B2CCommon.emptyCart(driver, b2cPage);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			Thread.sleep(6000);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			Thread.sleep(6000);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			Thread.sleep(6000);
			
			
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("3");
			b2cPage.cartPage_Quantity_update.click();
			
			b2cPage.cartPage_Quantity1.clear();
			b2cPage.cartPage_Quantity1.sendKeys("4");
			b2cPage.cartPage_Quantity_update1.click();
			
			b2cPage.cartPage_Quantity2.clear();
			b2cPage.cartPage_Quantity2.sendKeys("6");
			b2cPage.cartPage_Quantity_update2.click();
			
			Assert.assertTrue(!Common.isElementExist(driver, By.cssSelector("div.alert.negative")));

			//9,Change item line quantity or add more product entry to increase cart quantity higher than 15
			Dailylog.logInfoDB(9, "Change item line quantity or add more product entry to increase cart quantity higher than 15", Store, testName);

			
			b2cPage.cartPage_Quantity2.clear();
			b2cPage.cartPage_Quantity2.sendKeys("10");
			b2cPage.cartPage_Quantity_update2.click();
			Assert.assertTrue(Common.isElementExist(driver, By.cssSelector("div.alert.negative")) && driver.findElement(By.cssSelector("div.alert.negative")).isDisplayed());
			
			
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'ASM_close')]")));
			
			
			//10,Change HMC configuration, leave all quantity value as empty.
			Dailylog.logInfoDB(10, "Change HMC configuration, leave all quantity value as empty.", Store, testName);

			
			configureHMCNumEmpty();
			
			//11,* As a tele rep, go to website and start ASM, search one customer and start session then purchase accessory option to the shopping cart.
			//** Browse accessories, select a certain accessory to go to accessory list page. 
			//** Select a certain facet search option from facet search. 
			//** Add this product with quantity of 20 
			Dailylog.logInfoDB(11, "As a tele rep, go to website and start ASM, search one customer and start session then purchase accessory option to the shopping cart,Add this product with quantity of 20 ", Store, testName);

			loginB2CTele("TransactioID");
			
			driver.get(tele_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			Thread.sleep(6000);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			Thread.sleep(6000);
			
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("10");
			b2cPage.cartPage_Quantity_update.click();
			
			b2cPage.cartPage_Quantity1.clear();
			b2cPage.cartPage_Quantity1.sendKeys("10");
			b2cPage.cartPage_Quantity_update1.click();
			
			Assert.assertTrue(!Common.isElementExist(driver, By.cssSelector("div.alert.negative")));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
	
	public void configureHMCNumEmpty() throws Exception{
		
		driver.get(hmcHomePageUrl);
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		
		
		hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.clear();
		//hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.sendKeys("");
	
		Thread.sleep(5000);
	
		hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.clear();
		//hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.sendKeys("");
		Thread.sleep(5000);
	
	
		hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.clear();
		//hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.sendKeys("");
		Thread.sleep(5000);
		
		
		hmcPage.baseStore_save.click();
		boolean b1 = hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.getAttribute("value").equals("");
		boolean b2 = hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.getAttribute("value").equals("");
		boolean b3 = hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.getAttribute("value").equals("");
		String value1 = hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.getAttribute("value");
		System.out.println("value1 is :" + value1);
		System.out.println("b1 is :" + b1);
		System.out.println("b1 is :" + b2);
		System.out.println("b1 is :" + b3);
		
		
		Assert.assertTrue(hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.getAttribute("value").equals(""));
		Assert.assertTrue(hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.getAttribute("value").equals(""));
		Assert.assertTrue(hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.getAttribute("value").equals(""));

		hmcPage.Home_EndSessionLink.click();

	}	
	
	public void goToAccessoryMonitorPage() throws Exception{
		driver.get(tele_homePageUrl);
		NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
		b2cPage.Accessories_browseAllCategory.click();
		b2cPage.Accessories_category_Monitor.click();
	}

	public void loginB2CTele(String category) throws InterruptedException{
		
		driver.get(tele_loginUrl);
		B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
		B2CCommon.loginASMAndStartSession(driver, b2cPage, category, testData.B2C.getLoginID());

	}
	
	
	public String getWCMSWebsiteID(String storeid){
		
		String wcmsID = "";
		
		switch(storeid){
		     case "AU" :
		    	 wcmsID = "auweb";
		    	 break;
		     case "CA" :
		    	 wcmsID = "capublicsite";
		    	 break;
		     case "US" :
		    	 wcmsID = "uspublicsite";
		    	 break;
		     case "USEPP" :
		    	 wcmsID = "usaffinitysite";
		    	 break;
		     case "CA_AFFINITY" :
		    	 wcmsID = "caaffinitysite";
		    	 break;
		     case "JP" :
		    	 wcmsID = "jpweb";
		    	 break;
		     case "GB" :
		    	 wcmsID = "gbweb";
		    	 break;
		     case "HK" :
		    	 wcmsID = "hkweb";
		    	 break;
		     default :
		    	 wcmsID = "NoValue";
		    	 break;	 
		}
		
		return wcmsID;
		
	}
	
	
	
	public void setTeleCartQuantityLimits() throws InterruptedException{
		
		driver.get(hmcHomePageUrl);
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.hmcHome_WCMS.click();
		hmcPage.wcmsPage_websites.click();
		
		hmcPage.WCMS_Website_ID.clear();
		hmcPage.WCMS_Website_ID.sendKeys(getWCMSWebsiteID(Store));
		hmcPage.WCMS_Website_SearchButton.click();
		
		Thread.sleep(6000);
		
		driver.findElement(By.xpath("(//span[contains(@id,'Content/OrganizerListEntry')]/img)[1]")).click();
		hmcPage.WCMS_Website_Administration.click();
		
		String itemNumber = hmcPage.WCMS_Website_MaximumLineItemQuantity.getAttribute("value").toString();
		System.out.println("itemNumber is :" + itemNumber);
		if(!itemNumber.equals("0")){
			hmcPage.WCMS_Website_MaximumLineItemQuantity.clear();
			hmcPage.WCMS_Website_MaximumLineItemQuantity.sendKeys("0");
			hmcPage.WCMS_Website_Save.click();
		}
		
		String itemNumber1 = hmcPage.WCMS_Website_MaximumLineItemQuantity.getAttribute("value").toString();
		System.out.println("itemNumber1 is :" + itemNumber1);
		
		
		Assert.assertTrue(itemNumber1.equals("0"));
		
		hmcPage.hmcHome_WCMS.click();
		hmcPage.Home_B2CCommercelink.click();
		//hmcPage.Home_B2CUnitLink.click();
		driver.findElement(By.xpath("//a[contains(@id,'B2CUnit')]")).click();
		
		
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		
		
		String ASM_Cart_Quantity = hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.getAttribute("value").toString();
		String Total_Cart_Quantity = hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.getAttribute("value").toString();
		String Line_Item_Quantity = hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.getAttribute("value").toString();
		
		if(!ASM_Cart_Quantity.equals("15")){
			hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.clear();
			hmcPage.B2CUnit_SiteAttribute_ASM_Cart_Quantity.sendKeys("15");
		}
		
		if(!Total_Cart_Quantity.equals("10")){
			hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.clear();
			hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.sendKeys("10");
		}
		
		if(!Line_Item_Quantity.equals("5")){
			hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.clear();
			hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.sendKeys("5");
		}
		
		String lineItemQuantityMessage = "To order more than {{0}} units please call: 1-885-897-2629";
		String cartQuantityMessage = "Unfortunately your order cannot be completed at this time. To assist you more quickly, please contact us at 1-885-253-6686. Please note Code CII and provide if requested.";
		
		driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.lineItemQtyMessage')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.lineItemQtyMessage')]")).sendKeys(lineItemQuantityMessage);
		
		driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.cartQtyMessage')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.cartQtyMessage')]")).sendKeys(cartQuantityMessage);
		
		
		hmcPage.baseStore_save.click();
		hmcPage.Home_EndSessionLink.click();
		
		
		
	}

}
