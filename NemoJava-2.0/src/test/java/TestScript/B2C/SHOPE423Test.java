package TestScript.B2C;
import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class SHOPE423Test extends SuperTestClass{ 
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public String country;
	public String unit;
	public String store;
	public String bundleID;
	public String leadingProduct;
	public String referProduct;

	public SHOPE423Test (String store, String country, String unit) {
		this.Store = store;
		this.country = country;
		this.unit = unit;
		this.testName = "SHOPE-423";
		
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup","p2", "b2c"})
	public void SHOPE423 (ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			bundleID="CBforUSTest5";
			//Prepare Enable cart Edit function
			lockBundleToggle(false);
			Dailylog.logInfoDB(1,"Lock Bundle Toggle change to No" , Store, testName);
			//Step1 Go to HMC find Leading Product No. and Reference Product No.
			leadingProduct = getProductNo("leadingProduct");
			leadingProduct="20L9CTO1WWENUS0";
			Dailylog.logInfoDB(2,"Leading Product Number is: "+leadingProduct , Store, testName);
			referProduct = getProductNo("referProduct");
			Dailylog.logInfoDB(3,"Refer Product Number is: "+ referProduct , Store, testName);
			Map<String, Float> webMap = new HashMap<String, Float>();
			//delete all rule
			deleRule(country,store,leadingProduct);
			Dailylog.logInfoDB(4,"Leading Product rule is deleted" , Store, testName);
			deleRule(country,store,referProduct);
			Dailylog.logInfoDB(5,"Refer Product rule is deleted" , Store, testName);
			deleRule(country,store,bundleID);
			Dailylog.logInfoDB(6,"Bundle Product rule is deleted" , Store, testName);
			
			//Step2 Go to HMC Debug Leading Product web price
			createRule("Instant Savings", "100",unit,leadingProduct);
			Dailylog.logInfoDB(7,"Leading Product Instant rule is created" , Store, testName);
			createRule("eCoupon Discounts", "50",unit,leadingProduct);
			Dailylog.logInfoDB(8,"Leading Product Ecoupon rule is created" , Store, testName);
			Map<String, Float> leadingMap = getDebugPrice(driver,hmcPage,country,store,leadingProduct);
			
			//Step3 Go to HMC Debug Reference Product web price
			createRule("Instant Savings", "110",unit,referProduct);
			Dailylog.logInfoDB(9,"Refer Product Instant rule is created" , Store, testName);
			createRule("eCoupon Discounts", "60",unit,referProduct);
			Dailylog.logInfoDB(10,"Refer Product Ecoupon rule is created" , Store, testName);
			Map<String, Float> referMap = getDebugPrice(driver,hmcPage,country,store,referProduct);
			System.out.println("leading Product total Price is: " +leadingMap.get("totalprice"));
			System.out.println("refer Product total Price is: " +referMap.get("totalprice"));
			//Step4 Go to HMC Debug Bundle price
			Map<String, Float> bundleMap = getDebugPrice(driver,hmcPage,country,store,bundleID);
			Assert.assertEquals(bundleMap.get("floorprice"),leadingMap.get("costprice")+referMap.get("costprice"));
			System.out.println("bundle floor price is: "+bundleMap.get("floorprice") + "ld+rd price is: "+(leadingMap.get("costprice")+referMap.get("costprice")) );
			Assert.assertEquals(bundleMap.get("listprice"),leadingMap.get("listprice")+referMap.get("listprice"));
			System.out.println("bundle list price is: "+bundleMap.get("listprice") + "ld+rd price is: "+(leadingMap.get("listprice")+referMap.get("listprice")) );
			Assert.assertEquals(bundleMap.get("webprice"),leadingMap.get("webprice")+referMap.get("webprice"));
			System.out.println("bundle web price is: "+bundleMap.get("webprice") + "ld+rd price is: "+(leadingMap.get("webprice")+referMap.get("webprice")) );
			Dailylog.logInfoDB(11,"Bundle Debug price is correct" , Store, testName);
			
			//Step5 Go to B2C site Check web price equal debug web price
			cleanRadis(driver,hmcPage,bundleID);
			Dailylog.logInfoDB(12,"Bundle cache clean" , Store, testName);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundleID);
			webMap.put("pdpwebprice", B2CCommon.GetPriceValue(b2cPage.PDP_bundlewebprice.getText()));
			Assert.assertEquals(bundleMap.get("webprice"),webMap.get("pdpwebprice"));
			Dailylog.logInfoDB(13,"PDP web price correct" , Store, testName);
			//Step6 Click Add to cart direct to CTO page
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_bundleAddtocart);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//p[@class='groups-title']")));
			webMap.put("ctoyourwebprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundleyourprice.getText()));
			Assert.assertEquals(webMap.get("ctoyourwebprice"), bundleMap.get("webprice"));
			Dailylog.logInfoDB(14,"CTO web price correct" , Store, testName);
			//Step7 Click CONTINUE to cart page Check the price is same between cart page and new CTO page.
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.cto_AddToCartButton);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			//webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("ctoyourwebprice"), webMap.get("cartsubtotal"));
			Assert.assertEquals(webMap.get("ctoyourwebprice"), webMap.get("cartesttotal"));
			webMap.put("leadingProductwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//div[@class='cart-item-details'])[1]//div[contains(@class,'WebPrice')]/dl[2]")).getText()));
			Assert.assertEquals(webMap.get("leadingProductwebprice"), leadingMap.get("webprice"));
			webMap.put("referProductwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[@class='cart-item-addedItem-price'])[2]")).getText()));
			Assert.assertEquals(webMap.get("referProductwebprice"), referMap.get("webprice"));
			Dailylog.logInfoDB(15,"Cart web price correct" , Store, testName);
			//Step8 Click PROCEED TO CHECKOUT check total price equal Estimated total price
			//((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.Cart_CheckoutButton);
			driver.findElement(By.id("lenovo-checkout-sold-out")).click();
			webMap.put("shippingtotal", B2CCommon.GetPriceValue(b2cPage.NewShipping_ShippingTotalPrice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),webMap.get("shippingtotal"));
			Dailylog.logInfoDB(16,"Shipping web price correct" , Store, testName);
			//Step9 Click cancel on bundle CTO page
			//back to cart page
			driver.navigate().back();
			//Remove from cart page
			driver.findElement(By.xpath("//a[@class='submitRemoveProduct']")).click();
			driver.navigate().back();
			//back to bundle CTO page
			driver.navigate().back();
			//click cancel 
			driver.findElement(By.xpath("//a[@class='cancel-add-to-cart']")).click();
			Assert.assertTrue(driver.getCurrentUrl().contains(bundleID));
			Dailylog.logInfoDB(17,"Direct go to bundle cto page" , Store, testName);
			
			//Step22 Edit leading product and break the bundle
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundleID);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_bundleAddtocart);
			
			webMap.put("ctoyourwebprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundleyourprice.getText()));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.cto_AddToCartButton);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			b2cPage.Cart_edit.click();
			Common.sleep(2000);
			driver.findElement(By.xpath("(//input[@value='Yes'])[1]")).click();
			driver.getCurrentUrl().contains(leadingProduct);
			//change some CV on Cto page 
			Common.sleep(5000);
			String priceString=driver.findElement(By
					.xpath("//span[contains(text(),'1.70GHz, up to 3.60GHz')]/../../../div[@class='price qa-configurator-groupItem-price']")).getText().toString().trim();
			String discountPrice=priceString.substring(0, priceString.lastIndexOf("$")).replace("+","").trim();
			String cvwebprice=priceString.substring( priceString.lastIndexOf("$"),priceString.length()).replace("+","").trim();
			webMap.put("CVafterdiscountprice", B2CCommon.GetPriceValue(discountPrice));
			webMap.put("CVwebprice",B2CCommon.GetPriceValue(cvwebprice));
			driver.findElement(By.xpath("//span[contains(text(),'1.70GHz, up to 3.60GHz')]/../../../div[@class='price qa-configurator-groupItem-price']")).click();
			driver.findElement(By.xpath("//div[@id='cta-builder-continue']/button")).click();
			Common.sleep(10000);
			b2cPage.Product_AddToCartBtn.click();
			// find this element not exist driver.findElement(By.xpath("//h4[@class='cart-item-addedItems-heading']");
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//h4[@class='cart-item-addedItems-heading']"), 1));
			//find leading product webPrice
			webMap.put("leadingProductwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//div[@class='cart-item-details'])[1]//div[contains(@class,'WebPrice')]/dl[2]")).getText()));
			Assert.assertEquals(webMap.get("leadingProductwebprice"), leadingMap.get("webprice")+webMap.get("CVafterdiscountprice"));
			//find leading product salePrice
			webMap.put("leadingProductsaleprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//div[@class='cart-item-details'])[1]//div[contains(@class,'SalePrice')]/dl[2]")).getText()));
			Assert.assertEquals(webMap.get("leadingProductsaleprice"), leadingMap.get("salesprice")+webMap.get("CVafterdiscountprice"));
			//find reference product webprice
			webMap.put("subProductwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//div[@class='cart-item-details'])[2]//div[contains(@class,'WebPrice')]/dl[2]")).getText()));
			Assert.assertEquals(webMap.get("subProductwebprice"), referMap.get("webprice"));
			//find reference product saleprice
			webMap.put("subProductsaleprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//div[@class='cart-item-details'])[2]//div[contains(@class,'SalePrice')]/dl[2]")).getText()));
			Assert.assertEquals(webMap.get("subProductsaleprice"), referMap.get("salesprice"));
			webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("cartdiscounttotal"), 210f);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),leadingMap.get("salesprice")+referMap.get("salesprice")+webMap.get("CVafterdiscountprice"));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),leadingMap.get("salesprice")+referMap.get("salesprice")+webMap.get("CVafterdiscountprice"));
			Assert.assertEquals(webMap.get("cartesttotal"),webMap.get("cartsubtotal"));
			driver.findElement(By.xpath("//input[@id='active_button']")).click();
			webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			
			Assert.assertEquals(webMap.get("cartdiscounttotal"), 160f);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			
			Assert.assertEquals(webMap.get("cartsubtotal"),leadingMap.get("webprice")+referMap.get("salseprice")+webMap.get("CVafterdiscountprice"));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			
			Assert.assertEquals(webMap.get("cartesttotal"),leadingMap.get("promoprice")+referMap.get("salseprice")+webMap.get("CVafterdiscountprice"));
			Dailylog.logInfoDB(18,"Cart web price correct after change leading product CV" , Store, testName);
			//Step10 Go to HMC create bundle product instant saving discount
			createRule("Instant Savings", "120",unit,bundleID);
			Dailylog.logInfoDB(19,"Bundle Product instant rule created" , Store, testName);
			cleanRadis(driver,hmcPage,bundleID);
			float price=bundleMap.get("webprice")-120;
			bundleMap.put("salesprice ", price);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundleID);
			webMap.put("pdpwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[contains(@class,'saleprice')])[1]")).getText()));
			webMap.put("pdpsaleprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[contains(@class,'saleprice')])[2]")).getText()));
			Assert.assertEquals(webMap.get("pdpwebprice"), bundleMap.get("webprice"));
			Assert.assertEquals(webMap.get("pdpsaleprice"), bundleMap.get("salesprice"));
			Dailylog.logInfoDB(20,"PDP web price correct after create bundle instant rule" , Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_bundleAddtocart);
			webMap.put("ctodiscountprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("ctodiscountprice"), 120f);
			webMap.put("ctobaseprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlebaseprice.getText()));
			Assert.assertEquals(webMap.get("ctobaseprice"), bundleMap.get("webprice"));
			webMap.put("ctoyourprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundleyourprice.getText()));
			Assert.assertEquals(webMap.get("ctoyourprice"), bundleMap.get("salesprice"));
			Dailylog.logInfoDB(21,"CTO web price correct after create bundle instant rule" , Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.cto_AddToCartButton);
			webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("cartdiscounttotal"), 120f);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),bundleMap.get("salesprice"));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			Assert.assertEquals(webMap.get("cartesttotal"),bundleMap.get("salesprice"));
			Dailylog.logInfoDB(22,"Cart web price correct after create bundle instant rule" , Store, testName);
			//Step14 Go to HMC create bundle product web price rule
			createRule("web","10",unit,bundleID);
			cleanRadis(driver,hmcPage,bundleID);
			price=bundleMap.get("webprice")-10;
			bundleMap.put("webprice", price);
			price=bundleMap.get("webprice")-120;
			bundleMap.put("salesprice", price);
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundleID);
			webMap.put("pdpwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[contains(@class,'saleprice')])[1]")).getText()));
			webMap.put("pdpsaleprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[contains(@class,'saleprice')])[2]")).getText()));
			Assert.assertEquals(webMap.get("pdpwebprice"), bundleMap.get("webprice"));
			Assert.assertEquals(webMap.get("pdpsaleprice"), bundleMap.get("salesprice"));
			Dailylog.logInfoDB(23,"PDP web price correct after create bundle web rule" , Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_bundleAddtocart);
			webMap.put("ctodiscountprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("ctodiscountprice"), 120f);
			webMap.put("ctobaseprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlebaseprice.getText()));
			Assert.assertEquals(webMap.get("ctobaseprice"), bundleMap.get("webprice"));
			webMap.put("ctoyourprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundleyourprice.getText()));
			Assert.assertEquals(webMap.get("ctoyourprice"), bundleMap.get("salesprice"));
			Dailylog.logInfoDB(24,"CTO web price correct after create bundle web rule" , Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.cto_AddToCartButton);
			webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("cartdiscounttotal"), 120f);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),bundleMap.get("salesprice"));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			Assert.assertEquals(webMap.get("cartesttotal"),bundleMap.get("salesprice"));
			Dailylog.logInfoDB(25,"Cart web price correct after create bundle web rule" , Store, testName);
			//Step15 Go to HMC create bundle product ecoupon price rule
			createRule("ecoupom", "70",unit,bundleID);
			cleanRadis(driver,hmcPage,bundleID);
			price=bundleMap.get("webprice")-70;
			bundleMap.put("promoprice", price);
			
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundleID);
			webMap.put("pdpwebprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[contains(@class,'saleprice')])[1]")).getText()));
			webMap.put("pdpsaleprice", B2CCommon.GetPriceValue(driver.findElement(By
					.xpath("(//dd[contains(@class,'saleprice')])[2]")).getText()));
			Assert.assertEquals(webMap.get("pdpwebprice"), bundleMap.get("webprice"));
			Assert.assertEquals(webMap.get("pdpsaleprice"), bundleMap.get("salesprice"));
			Dailylog.logInfoDB(26,"PDP web price correct after create bundle ecoupon rule" , Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_bundleAddtocart);
			webMap.put("ctodiscountprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("ctodiscountprice"), 70f);
			webMap.put("ctobaseprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlebaseprice.getText()));
			Assert.assertEquals(webMap.get("ctobaseprice"), bundleMap.get("webprice"));
			webMap.put("ctoyourprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundleyourprice.getText()));
			Assert.assertEquals(webMap.get("ctoyourprice"), bundleMap.get("promoprice"));
			Dailylog.logInfoDB(27,"CTO web price correct after create bundle ecoupon rule" , Store, testName);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.cto_AddToCartButton);
			webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("cartdiscounttotal"), 130f);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),bundleMap.get("salesprice"));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			Assert.assertEquals(webMap.get("cartesttotal"),bundleMap.get("salesprice"));
			driver.findElement(By.xpath("//input[@id='active_button']")).click();
			webMap.put("cartdiscounttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlediscountprice.getText()));
			Assert.assertEquals(webMap.get("cartdiscounttotal"), 80f);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotal"),bundleMap.get("salesprice"));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			Assert.assertEquals(webMap.get("cartesttotal"),bundleMap.get("promoprice"));
			Dailylog.logInfoDB(28,"CART web price correct after create bundle ecoupon rule" , Store, testName);
			//Step16,18,19 Go to B2C site (Checked by step 15,14,10)
			//Step20 Edit leading product and do not change then add to cart
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+bundleID);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.PDP_bundleAddtocart);
			webMap.put("ctobaseprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundlebaseprice.getText()));
			webMap.put("ctoyourwebprice", B2CCommon.GetPriceValue(b2cPage.CTO_bundleyourprice.getText()));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",b2cPage.cto_AddToCartButton);
			webMap.put("cartsubtotal", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			webMap.put("cartesttotal", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			b2cPage.Cart_edit.click();
			//Do not change option
			Common.sleep(2000);
			driver.findElement(By.xpath("(//input[@value='YES'])[1]")).click();
			driver.getCurrentUrl().contains(leadingProduct);
			driver.findElement(By.xpath("//div[@id='cta-builder-continue']/button")).click();
			b2cPage.Product_AddToCartBtn.click();
			
			//Check the bundle not break
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//h4[@class='cart-item-addedItems-heading']"), 1));
			Dailylog.logInfoDB(28,"Do not change leading product cv the bundle not break" , Store, testName);
			// find this element exist driver.findElement(By.xpath("//h4[@class='cart-item-addedItems-heading']");
			webMap.put("cartsubtotalEdited", B2CCommon.GetPriceValue(b2cPage.CART_bundlesubtotalprice.getText()));
			webMap.put("cartesttotalEdited", B2CCommon.GetPriceValue(b2cPage.CART_bundleesttotalprice.getText()));
			Assert.assertEquals(webMap.get("cartsubtotalEdited"),webMap.get("cartsubtotal")); 
			Assert.assertEquals(webMap.get("cartesttotalEdited"),webMap.get("cartesttotal"));
			Dailylog.logInfoDB(29,"Do not change leading product cv the cart web price correct" , Store, testName);
			//Step21 edit sub product this function not use 
			//Step11,12,13 Go to HMC create bundle product floor price rule and price large than after instant price
			
			createRule("Floor", "99999",unit,bundleID);
			Dailylog.logInfoDB(30,"Bundle floor rule created" , Store, testName);
			Assert.assertTrue(isConformRule(driver,hmcPage,country,store,bundleID));
			Dailylog.logInfoDB(31,"Debug price displayed red invalid When floor price large than instant price" , Store, testName);
			//Step23 edit sub product this function not use 
	
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		//Roll Back
		lockBundleToggle(true);
	}
	
	public String getProductNo(String productType) {
		//TODO get lead or refer product number
		String productNo="";
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		Common.waitElementClickable(driver, hmcPage.Home_ProductsLink, 5);
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(bundleID);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",hmcPage.Catalog_SearchButton);
		Common.sleep(3000);
		hmcPage.products_resultItem.click();
		hmcPage.Products_categorySystem.click();
		if (productType.equals("leadingProduct")) {
			String lProduct= driver.findElement(By
					.xpath("(//input[contains(@id,'leadingProduct')])[1]")).getAttribute("value");
			lProduct=lProduct.substring(0, lProduct.indexOf("-")).trim();
			productNo = lProduct;
		}else if (productType.equals("referProduct")) {
			String referproductxpath="//tbody[contains(@id,'Content/GenericResortableItemList[1]_tbody')]//input[contains(@id,'AutocompleteReferenceEditor')]";
			String rfProduct= driver.findElement(By.xpath(referproductxpath)).getAttribute("value");
			rfProduct = rfProduct.substring(0, rfProduct.indexOf("-")).trim();;
			productNo = rfProduct;
		}
		System.out.println(productType + " is: " + productNo);
		hmcPage.hmcHome_hmcSignOut.click();
		return productNo;
		
	}
	
	public Map<String,Float> getDebugPrice(WebDriver driver,HMCPage hmcPage,String country,String store,String productNumber) {
		//TODO get debug price
		Map<String, Float> map = new HashMap<String, Float>();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		Common.sleep(2000);
		HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"["+this.Store+ "] " +country,this.Store+ " "+"Web Store",
				"Nemo Master Multi Country Product Catalog - Online",productNumber);
		Common.sleep(2000);
		map.put("costprice",B2CCommon.GetPriceValue( driver.
				findElement(By.xpath("//td[@class='cost']/samp[@id='value']")).getText()));
		System.out.println("costprice is "+ map.get("costprice"));
		
		map.put("floorprice",B2CCommon.GetPriceValue( driver.
				findElement(By.xpath("//td[@class='floor']/samp[@id='value']")).getText()));
		System.out.println("floorprice is "+ map.get("floorprice"));
		
		map.put("listprice",B2CCommon.GetPriceValue( driver.
				findElement(By.xpath("//td[@class='list']/samp[@id='value']")).getText()));
		System.out.println("listprice is "+ map.get("listprice"));
		
		map.put("webprice",B2CCommon.GetPriceValue(hmcPage.B2BpriceSimulate_webPrice.getText()));
		
		map.put("salesprice", B2CCommon.GetPriceValue( driver.
				findElement(By.xpath("//td[@class='savings']/samp[@id='value']")).getText()));
		
		map.put("promoprice", B2CCommon.GetPriceValue( driver.
				findElement(By.xpath("//td[@class='promo']/samp[@id='value']")).getText()));
		
		map.put("totalprice", B2CCommon.GetPriceValue( driver.
				findElement(By.xpath("//td[@class='total']/samp[@id='value']")).getText()));
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		return map;
	}
	
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
		//TODO fill in Rule Info
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

	public void createRule(String ruleType, String discountPrice, String unit,String productNumber) throws InterruptedException {
		//TODO create Rule
		WebElement target;
		String dataInput;
		String xpath;
		//String priceRlueID = null;
		System.out.println("Create rules***********************"+ ruleType);
		String ruleName = this.Store + productNumber + ruleType.replace(" ", "");
		driver.get(testData.HMC.getHomePageUrl());

		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
			
		}
		driver.navigate().refresh();
		Common.sleep(1000);
		hmcPage.Home_PriceSettings.click();
		Common.sleep(1000);
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);

		// loginPricingCockpit();
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Thread.sleep(5000);
		Common.javascriptClick(driver, hmcPage.B2CPriceRules_CreateNewGroup);
		// hmcPage.B2CPriceRules_CreateNewGroup.click();
		Thread.sleep(3000);
		hmcPage.B2CPriceRules_SelectGroupType.click();
		Thread.sleep(3000);
		
		if (ruleType.equals("Instant Savings")) {
			hmcPage.B2CPriceRules_InstantSavingOption.click();
		} else if (ruleType.equals("Floor")) {
			hmcPage.B2CPriceRules_FloorPriceOption.click();
		} else if (ruleType.equals("Web")) {
			hmcPage.B2CPriceRules_WebPriceOption.click();
		} else if (ruleType.equals("eCoupon Discounts")) {
			hmcPage.B2CPriceRules_eCouponDiscountOption.click();
		}else if (ruleType.equals("List Price Override")) {
			driver.findElement(By.xpath("//ul/li/div[contains(.,'List Price Override')]")).click();
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_Continue);

		// Floor price name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).get(count-1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td")).get(count-1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		}

		// Country
		dataInput = country;
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceRules_CountrySelect, xpath);

		// Catalog
		dataInput = "Nemo Master Multi Country Product Catalog - Online";
		xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
		fillRuleInfo(driver, hmcPage, "Catalog", dataInput, hmcPage.B2CPriceRules_CatalogSelect, xpath);

		// B2Cunit
		dataInput = unit;
		if (this.Store.equals("AU"))
			xpath = "(//span[text()='" + dataInput + "']/../../*[not(text())])[last()]";
		else
			xpath = "//span[text()='" + dataInput + "']/../../*[not(text())]";
		Common.waitElementClickable(driver, hmcPage.B2CPriceRules_B2CunitSelect, 30);
		hmcPage.B2CPriceRules_B2CunitSelect.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_MasterUnit);
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_UnitSearch);
		hmcPage.B2CPriceRules_UnitSearch.clear();
		hmcPage.B2CPriceRules_UnitSearch.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.doubleClick(driver, target);
		System.out.println("B2Cunit: " + dataInput);
		Thread.sleep(5000);
		
		if (ruleType.equals("List Price Override")) {
			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);
		}else if (ruleType.equals("Instant Savings")) {
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);

		} else if (ruleType.equals("Floor")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("eCoupon Discounts")) {
			// display ecopon message
			//hmcPage.B2CPriceRules_ecouponMessageDisplay.click();
			driver.findElement(By.xpath("//div[@class='checkbox']/label[contains(text(),'Display CouponMessage')]")).click();
			hmcPage.B2CPriceRules_ecouponMessageEdit.click();
			// language
			String language;
			if ((this.Store).contains("_")) {
				language = "en_" + this.Store.split("_")[0];
			} else if (this.Store == "JP") {
				language = "ja_JP";
			} else if (this.Store == "AU") {
				language = "en";
			} else if (this.Store == "BR") {
				language = "pt";
			} else {
				language = "en_" + this.Store;
			}
			System.out.println(language);

			xpath = "//span[text()='" + language + "' and @class='select2-match']";
			fillRuleInfo(driver, hmcPage, "Language", language, hmcPage.B2CPriceRules_ChooseCountry, xpath);
			hmcPage.B2CPriceRules_ecouponMessageInput.clear();
			hmcPage.B2CPriceRules_ecouponMessageInput.sendKeys("<span style=\"color: #fdda97\">Valid through 10/31/2017</span>");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageAdd);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_ecouponMessageSave);
			// Priority
			hmcPage.B2CPriceRules_ecouponPriority.clear();
			hmcPage.B2CPriceRules_ecouponPriority.sendKeys("500");
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("Web")) {
			hmcPage.B2CPriceRules_dynamicRate.click();
			hmcPage.B2CPriceRules_DynamicMinusButton.click();
			hmcPage.B2CPriceRules_ecouponDollorButton.click();
			hmcPage.B2CPriceRules_dynamicValue.clear();
			hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		}

		// Material
		xpath = "//span[contains(text(),'" + productNumber + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", productNumber, hmcPage.B2CPriceRules_MaterialSelect, xpath);
		hmcPage.B2CPriceRules_addToGroupButton.click();
		Common.sleep(1000);
		Common.scrollToElement(driver, hmcPage.B2CPriceRules_createNewGroupButton);
		hmcPage.B2CPriceRules_createNewGroupButton.click();			
		Dailylog.logInfoDB(4, "Create New Coupon button is clicked.", Store, testName);
		Common.sleep(5000);
		driver.switchTo().defaultContent();
		if (ruleType.equals("Floor")) {
			approveRule(ruleName);
		}

		hmcPage.hmcHome_hmcSignOut.click();
	} 
	
	public boolean isConformRule(WebDriver driver,HMCPage hmcPage,String country,String store,String productNumber) {
		//TODO check if the sales price conform floor rule
		boolean flag = false;
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		Common.sleep(2000);
		HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"["+this.Store+ "] " +country,this.Store+ " "+"Web Store",
				"Nemo Master Multi Country Product Catalog - Online",productNumber);
		Common.sleep(2000);
		
		//if instant < floor price  check the price color is red
		String classname = driver.findElement(By.xpath("//samp[@id='total_diff']/..")).getAttribute("class");
		System.out.println("classname is " + classname);
		String classname1 = driver.findElement(By.xpath("//samp[@id='web_diff']/..")).getAttribute("class");
		System.out.println("classname1 is :"+classname1);
		if (classname.equals("invalid-amount")){
			flag = true;
		}else flag = false;
				
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		
		return flag;
	}
	
	public void deleRule(String country,String store,String partNumber) throws InterruptedException {
		//TODO delete Rule
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
/*		driver.get(testData.HMC.getHomePageUrl());
		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 2)){
			HMCCommon.Login(hmcPage, testData);
		}
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);*/
		Common.sleep(2000);	
		HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"["+this.Store+ "] " +country,this.Store+ " "+"Web Store",
				"Nemo Master Multi Country Product Catalog - Online",partNumber);
		String webRule="";
		String instantRule="";
		String promoRule="";
		String floorRule="";
		String listRule="";
		if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_webGroup, 5)){
			webRule = hmcPage.B2CPriceSimulator_webGroup.getText();
			System.out.println("webPrice rule exist:"+webRule);
		}
		if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_instantGroup, 5)){
			instantRule = hmcPage.B2CPriceSimulator_instantGroup.getText();
			System.out.println("instantSaving Price rule exist:"+instantRule);
		}
		if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_promoGroup, 5)){
			promoRule = hmcPage.B2CPriceSimulator_promoGroup.getText();
			System.out.println("promoPrice rule exist:"+promoRule);				
		}
		if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_floorGroup, 5)){
			floorRule = hmcPage.B2CPriceSimulator_floorGroup.getText();
			System.out.println("promoPrice rule exist:"+floorRule);				
		}
		if(Common.checkElementExists(driver, hmcPage.B2CPriceSimulator_listGroup, 5)){
			listRule = hmcPage.B2CPriceSimulator_listGroup.getText();
			System.out.println("list rule exist:"+listRule);				
		}
		driver.switchTo().defaultContent();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		hmcPage.PricingCockpit_B2CPriceRules.click();
		Common.sleep(1500);
		//delete rules
		for(int i=0;i<2;i++){
			System.out.println("check rule times" + i);
			if(webRule!=""){
				HMCCommon.deleteRule(driver, hmcPage,"Web Prices", webRule);
				webRule="";
				System.out.println("Rule Deleted:"+webRule);
			}
			if(instantRule!=""){
				HMCCommon.deleteRule(driver, hmcPage,"Instant Savings", instantRule);
				instantRule="";
				System.out.println("Rule Deleted:"+instantRule);
			}
			if(promoRule!=""){
				HMCCommon.deleteRule(driver, hmcPage,"eCoupon Discounts", promoRule);
				promoRule="";
				System.out.println("Rule Deleted:"+promoRule);
			}
			if(floorRule!=""){
				HMCCommon.deleteRule(driver, hmcPage,"Floor Prices", floorRule);
				floorRule="";
				System.out.println("Rule Deleted:"+floorRule);
			}
			if(listRule!=""){
				HMCCommon.deleteRule(driver, hmcPage,"List Price Override", listRule);
				listRule="";
				System.out.println("Rule Deleted:"+listRule);
			}
		}
		Dailylog.logInfoDB(30,"Exsite rule deleted", Store,testName);
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
	}
	
	public void lockBundleToggle(boolean flag) {
		//TODO change LockBundle Toggle
		driver.get(testData.HMC.getHomePageUrl());
		Common.sleep(5000);
		HMCCommon.Login(hmcPage, testData);
		System.out.println("Navigate to B2C Commerce ->B2C UNIT");
		HMCCommon.searchB2CUnit(hmcPage, testData);
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		Common.sleep(5000);
		hmcPage.B2CUnit_SiteAttributeTab.click();
		if(flag) {
			//change the LockBundle Toggle to Yes
			driver.findElement(By.xpath("//input[contains(@id,'[B2CUnit.zLockConBundle]]_true')]")).click();
			System.out.println("Lock Bundle Toggle change to Yes");
		}else {
			//change the LockBundle Toggle to No
			driver.findElement(By.xpath("//input[contains(@id,'[B2CUnit.zLockConBundle]]_false')]")).click();
			System.out.println("Lock Bundle Toggle change to No");
		}
		hmcPage.Types_SaveBtn.click();
	}

	public void approveRule(String ruleName) throws InterruptedException {
		//TODO approve Floor Rule
		System.out.println("Approve rules***********************");
		//driver.switchTo().defaultContent();
		hmcPage.Home_System.click();
		hmcPage.B2CPriceRules_Types.click();
		hmcPage.Types_Identifier.clear();
		hmcPage.Types_Identifier.sendKeys("PriceB2CRule");
		hmcPage.Types_Search.click();
		Common.doubleClick(driver, hmcPage.Types_ResultPriceB2CRule);
		Common.waitElementVisible(driver, hmcPage.Types_OpenEditorNewWindow);
		hmcPage.Types_OpenEditorNewWindow.click();
		Common.switchToWindow(driver, 1);
		Common.waitElementVisible(driver, hmcPage.Types_OpenOrganizer);
		hmcPage.Types_OpenOrganizer.click();
		Common.switchToWindow(driver, 2);
		//hmcPage.Types_SearchValueInput.clear();
		//hmcPage.Types_SearchValueInput.sendKeys(ruleID);
		driver.findElement(By.xpath("//select[contains(@id,'attributeselect')]")).click();
		driver.findElement(By.xpath("//option[@value='groupName']")).click();
		driver.findElement(By.xpath("//input[contains(@id,'groupName')]")).sendKeys(ruleName);
		hmcPage.Types_Search.click();
		Thread.sleep(5000);
		WebElement target = driver.findElement(By.xpath("//td[contains(@id,'PKDisplay')]"));
		Common.doubleClick(driver, target);
		hmcPage.Types_PriceB2CRuleStatus.clear();
		hmcPage.Types_PriceB2CRuleStatus.sendKeys("1");
		Thread.sleep(5000);
		hmcPage.Types_SaveBtn.click();
		System.out.println("Floor Price Rule is approved! Rule ID: " + ruleName);
		driver.close();
		Thread.sleep(500);
		Common.switchToWindow(driver, 1);
		driver.close();
		Thread.sleep(500);
		Common.switchToWindow(driver, 0);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	public void cleanRadis(WebDriver driver,HMCPage hmcPage, String partNm)
			throws InterruptedException {
		//TODO clean radis cache 
		driver.get(testData.HMC.getHomePageUrl());
		Common.sleep(5000);
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_System.click();
		//hmcPage.Home_RadisCacheCleanLink.click();
		driver.findElement(By.xpath("//a[contains(text(),'rediscacheclean')]")).click();
		Thread.sleep(5000);
		hmcPage.PageDriver
				.switchTo()
				.frame(hmcPage.PageDriver.findElement(By
						.xpath(".//iframe[contains(@src,'nemoClearCachePage')]")));
		hmcPage.Radis_CleanProductTextBox.clear();
		hmcPage.Radis_CleanProductTextBox.sendKeys(partNm);
		hmcPage.Radis_CleanButton.click();
		Common.waitAlertPresent(hmcPage.PageDriver, 60);
		hmcPage.PageDriver.switchTo().alert().accept();
		hmcPage.PageDriver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		
	}
	
}
