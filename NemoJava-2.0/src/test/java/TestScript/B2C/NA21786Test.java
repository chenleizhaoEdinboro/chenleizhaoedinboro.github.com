package TestScript.B2C;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21786Test extends SuperTestClass {
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

	public NA21786Test(String store) {
		this.Store = store;
		this.testName = "NA-21786";
	}

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21786(ITestContext ctx) {

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
			
			// 1, Disable OOTB Maximum Iine Item Quantity restriction : set Maximum Line Item Quantity to 0
			Dailylog.logInfoDB(1, "Disable OOTB Maximum Iine Item Quantity restriction : set Maximum Line Item Quantity to 0", Store, testName);
		
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			
			hmcPage.BaseStore_SearchResult.click();
			hmcPage.baseStore_administration.click();
			
			if(!driver.findElement(By.xpath("//input[contains(@id,'BaseStore.isQuoteAvailable') and contains(@id,'true')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'BaseStore.isQuoteAvailable') and contains(@id,'true')]")).click();
			}
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
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
			
			
			//2, Site manager go to unit level to set up the configuration for Qty limits and message
			Dailylog.logInfoDB(2, "Site manager go to unit level to set up the configuration for Qty limits and message", Store, testName);
			
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
				hmcPage.B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity.sendKeys("15");
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
			
			//3, As a customer, go to website to add accessory option to the shopping cart.
			Dailylog.logInfoDB(3, "As a customer, go to website to add accessory option to the shopping cart", Store, testName);
			
			driver.get(ordinary_loginUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			driver.get(ordinary_cartUrl);
			
			B2CCommon.emptyCart(driver, b2cPage);
			
			driver.get(ordinary_homePageUrl);
			
			
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
			b2cPage.Accessories_browseAllCategory.click();
			b2cPage.Accessories_category_Monitor.click();
			
			List<WebElement> productList = driver.findElements(By.xpath("//*[@id='accessoryProductListing']//h3/a"));
			
			//driver.findElement(By.xpath("(//*[@id='accessoryProductListing']//h3/a)[1]")).click();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//*[@id='accessoryProductListing']//h3/a)[1]")));
			
			
			Thread.sleep(4000);
			
			//4, Click the add to cart btn.
			Dailylog.logInfoDB(4, "Click the add to cart btn.", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='addToCart']")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='cboxLoadedContent']")).isDisplayed());
			
			//5,Change quantity, make it exceed 5 (Line Item Quantity).  the number should be 8
			//Expect :After mouse out from input box
			//* *Add To Cart* btn disabled.
			//* Line time message shown like: +To order more than 5 units please call: 1-885-897-2629+
			b2cPage.Accessories_cart_popup.clear();
			b2cPage.Accessories_cart_popup.sendKeys("8");
			
			Thread.sleep(6000);
			
			Assert.assertTrue(!b2cPage.Accessories_cart_popup_AddToCart.isEnabled() && Common.isElementExist(driver, By.xpath("//div[@class='qty-limit-line-item-msg']")));
			
			//6, Change quantity again, lower than 5(Line Item Quantiy) . the number should be 3
			//Expect : Add To Cart can be clicked, and products  could be put into cart as expected
			b2cPage.Accessories_cart_popup.clear();
			b2cPage.Accessories_cart_popup.sendKeys("3");
			
			Thread.sleep(5000);
			
			Assert.assertTrue(b2cPage.Accessories_cart_popup_AddToCart.isEnabled());
			
			b2cPage.Accessories_cart_popup_AddToCart.click();
			
			driver.get(ordinary_cartUrl);
			
			String productQuantity = driver.findElement(By.xpath("//input[@id='quantity0']")).getAttribute("value").toString();
			
			Assert.assertTrue(productQuantity.equals("3"));
	
			//7, Go to shopping cart. 
			Dailylog.logInfoDB(7, "Go to shopping cart.", Store, testName);
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//div[@class='cart-item']")));
			
			
			//8, Change the line item quantity at item entry level
			//Expect : After mouse out from input box
			//* Update btn along with current item entry disabled 
			
			Dailylog.logInfoDB(8, "Change the line item quantity at item entry level", Store, testName);
			
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("6");
			
			Assert.assertTrue(!b2cPage.cartPage_Quantity_update.isEnabled() && driver.findElement(By.xpath("//div[@class='qty-limit-line-item-msg']")).isDisplayed());
			
			//9, Change the line item quantity at item entry level and click *Update* btn , the number can be 2
			//Expect : The quantity can be updated as expected.
			
			Dailylog.logInfoDB(9, "Change the line item quantity at item entry level and click *Update* btn", Store, testName);
			
			b2cPage.cartPage_Quantity.clear();
			b2cPage.cartPage_Quantity.sendKeys("2");
			
			b2cPage.cartPage_Quantity_update.click();
			
			String cartPage_number = b2cPage.cartPage_Quantity.getAttribute("value").toString();
			
			Assert.assertTrue(cartPage_number.equals("2"));
			
			//10,Clear cart
			//Purchase MTM and walk through Product Builder, 
			//and added warranty, Software or accessory option there, 
			//then go to shopping cart page, use quick add the add the same part.
	
			Dailylog.logInfoDB(10, "Clear cart,Purchase MTM and walk through Product Builder, and added warranty, Software or accessory option there, then go to shopping cart page, use quick add the add the same part.", Store, testName);
	
			B2CCommon.emptyCart(driver, b2cPage);
			
			String productUrl = ordinary_homePageUrl + "/p" + testData.B2C.getDefaultMTMPN();
			
			driver.get(productUrl);
			b2cPage.viewOrCustomize.click();
			Thread.sleep(6000);
			b2cPage.Add2Cart.click();
			
			// warranty
			List<WebElement> al_warranty = new ArrayList<WebElement>();
			if(Common.isElementExist(driver, By.xpath("//div[@data-tabname='Warranty']//ul[@class='configuratorItem-optionList']/li/input"))){
				al_warranty = driver.findElements(By.xpath("//div[@data-tabname='Warranty']//ul[@class='configuratorItem-optionList']/li/input"));
			}
			
			if(al_warranty.size() >=2){
				driver.findElement(By.xpath("(//div[@data-tabname='Warranty']//ul[@class='configuratorItem-optionList']/li/input)[2]")).click();
				System.out.println("warranty clicked !!!!!!!!");
			}
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//span[contains(.,'Continue')]")).click();
			
			// Software
			List<WebElement> al_software = new ArrayList<WebElement>();
			if(Common.isElementExist(driver, By.xpath("//div[@data-tabname='Software']//ul[@class='configuratorItem-optionList']/li/input"))){
				al_software = driver.findElements(By.xpath("//div[@data-tabname='Software']//ul[@class='configuratorItem-optionList']/li/input"));
			}
			
			if(al_software.size() >=2){
				driver.findElement(By.xpath("(//div[@data-tabname='Software']//ul[@class='configuratorItem-optionList']/li/input)[2]")).click();
				System.out.println("software clicked !!!!!!!!");
			}
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//span[contains(.,'Continue')]")).click();
			
			//Accessories
			List<WebElement> al_accessories = new ArrayList<WebElement>();
			if(Common.isElementExist(driver, By.xpath("//div[@data-tabname='Accessories']//ul[@class='configuratorItem-optionList']/li/input"))){
				al_accessories = driver.findElements(By.xpath("//div[@data-tabname='Accessories']//ul[@class='configuratorItem-optionList']/li/input"));
			}
			
			if(al_accessories.size() >=2){
				driver.findElement(By.xpath("(//div[@data-tabname='Accessories']//ul[@class='configuratorItem-optionList']/li/input)[2]")).click();
				System.out.println("software clicked !!!!!!!!");
			}
			Thread.sleep(3000);
			
			if(Common.isElementExist(driver, By.xpath("//span[contains(.,'Continue')]"))){
				driver.findElement(By.xpath("//span[contains(.,'Continue')]")).click();
			}
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@class='pricingSummary-cta']/button/span")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'cart-item-addedItems')]/dl")).isDisplayed());

			
			B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());

			driver.findElement(By.xpath("(//input[@name='quantity'])[last()]")).clear();
			driver.findElement(By.xpath("(//input[@name='quantity'])[last()]")).sendKeys("5");
			
			if(Common.isElementExist(driver, By.xpath("(//input[@value='Update'])[last()]"))){
				driver.findElement(By.xpath("(//input[@value='Update'])[last()]")).click();
			}
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//div[@class='alert negative']")) && driver.findElement(By.xpath("//div[@class='alert negative']")).isDisplayed());
		

			//11, Clear cart.
			//Purchase CTO and walk through Product Builder, 
			//and added an accessory option there, then go to shopping cart page,  
			//updated the quantity to 5,purchase this CTO boundle again with the same added accessories.
			Dailylog.logInfoDB(11, "Clear cart,Purchase CTO and walk through Product Builder, and added an accessory option there, then go to shopping cart page,  updated the quantity to 5,purchase this CTO boundle again with the same added accessories.", Store, testName);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			String CTO_url = ordinary_homePageUrl + "/p" + testData.B2C.getDefaultCTOPN(); 
			
			driver.get(CTO_url);
			b2cPage.viewOrCustomize.click();
			Thread.sleep(6000);
			b2cPage.Add2Cart.click();
			
			Thread.sleep(6000);
			b2cPage.addTocart_configPage.click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//li[@data-tabname='Accessories']/a/span")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("(//div[@data-tabname='Accessories']//input)[1]")).click();
			driver.findElement(By.xpath("//div[@class='pricingSummary-cta']/button")).click();
			
			driver.findElement(By.xpath("//input[@name='quantity']")).clear();
			driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("5");
			
			if(Common.isElementExist(driver, By.xpath("//input[@value='Update']"))){
				driver.findElement(By.xpath("//input[@value='Update']")).click();
			}
			
			driver.get(CTO_url);
			b2cPage.viewOrCustomize.click();
			Thread.sleep(6000);
			b2cPage.Add2Cart.click();
			
			Thread.sleep(6000);
			b2cPage.addTocart_configPage.click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//li[@data-tabname='Accessories']/a/span")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("(//div[@data-tabname='Accessories']//input)[1]")).click();
			driver.findElement(By.xpath("//div[@class='pricingSummary-cta']/button")).click();
			
			Assert.assertTrue(!Common.isElementExist(driver, By.xpath("(//div[contains(@class,'cart-item-addedItems')]/dl)[2]")));
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//div[@class='alert negative']")) && driver.findElement(By.xpath("//div[@class='alert negative']")).isDisplayed());
			
			
			//12, Clear cart.
			//Purchase CTO and walk through Product Builder select  3 options  add to cart ,
			//go to cart page and change CTO quantity to 5 in cart page
			Dailylog.logInfoDB(12, "Clear cart.Purchase CTO and walk through Product Builder select  3 options  add to cart ,go to cart page and change CTO quantity to 5 in cart page", Store, testName);

			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			driver.get(CTO_url);
			b2cPage.viewOrCustomize.click();
			Thread.sleep(6000);
			b2cPage.Add2Cart.click();
			
			Thread.sleep(6000);
			
			
			// warranty
			List<WebElement> warranty_option = driver.findElements(By.xpath("//div[@data-tabname='Warranty']//ul[@class='configuratorItem-optionList']/li"));
			
			Dailylog.logInfoDB(12, "the clickable option number under the warranty is :" + warranty_option.size(), Store, testName);
			
			driver.findElement(By.xpath("(//div[@data-tabname='Warranty']//ul[@class='configuratorItem-optionList']/li)[2]")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//span[contains(.,'Continue')]")).click();
			
			//software
			List<WebElement> software_option = driver.findElements(By.xpath("//div[@data-tabname='Software']//ul[@class='configuratorItem-optionList']/li/input"));
			Dailylog.logInfoDB(12, "the clickable option number under the software is :" + software_option.size(), Store, testName);
			
			driver.findElement(By.xpath("(//div[@data-tabname='Software']//ul[@class='configuratorItem-optionList']/li/input)[2]")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//span[contains(.,'Continue')]")).click();
			Thread.sleep(3000);
			
			// accessories
			List<WebElement> acccessories_option = driver.findElements(By.xpath("//div[@data-tabname='Accessories']//ul[@class='configuratorItem-optionList']/li/input"));
			Dailylog.logInfoDB(12, "the clickable option number under the accessories is :" + acccessories_option.size(), Store, testName);
			driver.findElement(By.xpath("(//div[@data-tabname='Accessories']//ul[@class='configuratorItem-optionList']/li/input)[2]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@class='pricingSummary-cta']/button/span")).click();
			
			Thread.sleep(6000);
			
			driver.findElement(By.xpath("//input[@name='quantity']")).clear();
			driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("5");
			
			driver.findElement(By.xpath("//input[@value='Update']")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert negative']")).isDisplayed(), "the error message is not displayed for the step 12");
			
			
			//13, Remove product or decrease line time quantity to make total cart quantity lower than 15.
			//Update the CTO with  3 options quantity to 3 in cart page
			Dailylog.logInfoDB(13, "Remove product or decrease line time quantity to make total cart quantity lower than 15.Update the CTO with  3 options quantity to 3 in cart page", Store, testName);
			
			driver.findElement(By.xpath("//input[@name='quantity']")).clear();
			driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("3");
			
			driver.findElement(By.xpath("//input[@value='Update']")).click();
			Thread.sleep(5000);
			
			Assert.assertTrue(!driver.findElement(By.xpath("//div[@class='alert negative']")).isDisplayed(), "the error message is not displayed for the step 12");
			

			//14, Change HMC configuration, leave all quantity value as empty.
			Dailylog.logInfoDB(12, "Change HMC configuration, leave all quantity value as empty.", Store, testName);
			
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
			
			//15, Go go website then add one product with quantity of 20 
			Dailylog.logInfoDB(13, "Go go website then add one product with quantity of 20", Store, testName);
			
			driver.get(ordinary_homePageUrl);
			
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
			b2cPage.Accessories_browseAllCategory.click();
			b2cPage.Accessories_category_Monitor.click();
			
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//*[@id='accessoryProductListing']//h3/a)[2]")));
			
			Thread.sleep(4000);
			driver.findElement(By.xpath("//*[@id='addToCart']")).click();

			b2cPage.Accessories_cart_popup.clear();
			b2cPage.Accessories_cart_popup.sendKeys("20");
			Thread.sleep(10000);
			
			boolean  b5 = b2cPage.Accessories_cart_popup_AddToCart.isEnabled();
			boolean b7 = !Common.isElementExist(driver, By.xpath("//div[@class='qty-limit-line-item-msg']"));
			
			System.out.println("b5 is :" + b5);
			System.out.println("b7 is :" + b7);
			
			Assert.assertTrue(b5 && b7); 
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

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
		     case "US_BPCTO" :
		    	 wcmsID = "bpcto";
		    	 break;
		     default :
		    	 wcmsID = "NoValue";
		    	 break;	 
		}
		
		return wcmsID;
		
	}
	
	
	
	

}
