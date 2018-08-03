package TestScript.B2C;

import static org.testng.Assert.assertTrue;

import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class SHOP220Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	String partNumber;
	String partNumber1;
	String partNumber2;
	String partNumber3;
	String country;
	String unit;
	public float webprice;
	public float floorprice;
	public float listprice;
	public float costPrice;
	
	public SHOP220Test(String store,String country,String unit) {
		this.Store = store;
		this.testName = "SHOPE-220";
		this.country= country;
		this.unit= unit;
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup","pricingpromot", "p2", "b2c" })
	public void SHOP220 (ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			partNumber = testData.B2C.getDefaultMTMPN();
			System.out.println(partNumber);
			
			/*run by manual
			 * 
			partNumber1 = "20GFX003US"; //list EQ web
			partNumber2= "81A400BFUS"; //list GT web
			partNumber3 = "81A400BGUS"; //check out list GT and with Saving and eCoupon

			outletSavingEnable(driver,hmcPage,true);
			deleRule(country, Store,partNumber1);
			//scenario 1	
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber1);
			if (listprice==webprice) {
				listEQweb(driver,b2cPage,partNumber1);
			}else {
				createRule("List Price Override", String.valueOf(webprice), unit, false,partNumber1);
				listEQweb(driver,b2cPage,partNumber1);
			}
			Dailylog.logInfoDB(6,partNumber1 + "List Price is" + listprice, Store,testName);
			Dailylog.logInfoDB(8,partNumber1 + "web Price is" + webprice, Store,testName);
			//scenario 2
			deleRule(country, Store,partNumber2);
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber2);
			createRule("List Price Override", String.valueOf(webprice+30.0), unit, false,partNumber2);
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber2);
			listRTweb(driver,b2cPage,partNumber2);
			Dailylog.logInfoDB(10,partNumber2 + "List Price is" + listprice, Store,testName);
			Dailylog.logInfoDB(12,partNumber2 + "web Price is" + webprice, Store,testName);
			startCronJob(driver, hmcPage,"full-mcnemoNAOutletIndex-cronJob", 1);
			//scenario 3
			deleRule(country, Store,partNumber3);
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber3);
			createRule("List Price Override", String.valueOf(231.99+10), unit, false,partNumber3);
			createRule("Instant Savings", String.valueOf(webprice-15),unit, false,partNumber3);
			Dailylog.logInfoDB(14,partNumber3 + "instance savings is" + String.valueOf(webprice-15), Store,testName);
			createRule("eCoupon Discounts", String.valueOf(webprice-10),unit, false,partNumber3);
			Dailylog.logInfoDB(16,partNumber3 + "eCoupon Discounts is" + String.valueOf(webprice-10), Store,testName);
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber3);
			Dailylog.logInfoDB(18,partNumber3 + "List Price is" + listprice, Store,testName);
			Dailylog.logInfoDB(20,partNumber3 + "web Price is" + webprice, Store,testName);
			//driver.manage().deleteAllCookies();
			checkOut(driver, b2cPage,partNumber3);  
			startCronJob(driver, hmcPage, "full-mcnemoNAOutletIndex-cronJob", 1);*/
			
			outletSavingEnable(driver,hmcPage,true);
			deleRule(country, Store,partNumber);
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber);
			if (listprice==webprice) {
				startCronJob(driver, hmcPage, "full-mcnemoNAOutletIndex-cronJob", 1);
				listEQweb(driver,b2cPage,partNumber);
			}else {
				createRule("List Price Override", String.valueOf(webprice), unit, false,partNumber);
				startCronJob(driver, hmcPage, "full-mcnemoNAOutletIndex-cronJob", 1);
				listEQweb(driver,b2cPage,partNumber);
			}
			deleRule(country, Store,partNumber);
			createRule("List Price Override", String.valueOf(webprice+10), unit, false,partNumber);
			startCronJob(driver, hmcPage, "full-mcnemoNAOutletIndex-cronJob", 1);
			listprice = webprice+10;
			listRTweb(driver,b2cPage,partNumber);
			createRule("Instant Savings", String.valueOf(webprice-15),unit, false,partNumber);
			Dailylog.logInfoDB(14,partNumber3 + "instance savings is" + String.valueOf(webprice-15), Store,testName);
			createRule("eCoupon Discounts", String.valueOf(webprice-10),unit, false,partNumber);
			Dailylog.logInfoDB(16,partNumber3 + "eCoupon Discounts is" + String.valueOf(webprice-10), Store,testName);
			startCronJob(driver, hmcPage, "full-mcnemoNAOutletIndex-cronJob", 1);
			getDebugPrice(driver, hmcPage, country, Store, unit,partNumber);
			checkOut(driver, b2cPage,partNumber);
			//deleRule(country, Store,partNumber);
			outletSavingEnable(driver,hmcPage,false); 
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	
	}
	
	/**
	 * Enable or Disable Savings toggle
	 */
	public void outletSavingEnable(WebDriver driver,HMCPage hmcPage,Boolean toggle) {
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(Store.replace("_","").toLowerCase());
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		if (toggle) {
			driver.findElement(By.xpath("//input[contains(@id,'[B2CUnit.outLetSavingDisplay]]_true')]")).click();
			Common.sleep(2000);
			driver.findElement(By.xpath("//div[@id='Content/ImageToolbarAction[organizer.editor.save.title]_label']")).click();
			Dailylog.logInfoDB(1,"outlet saving display change to YES.", Store,testName);
		}else {
			driver.findElement(By.xpath("//input[contains(@id,'[B2CUnit.outLetSavingDisplay]]_false')]")).click();
			Common.sleep(2000);
			driver.findElement(By.xpath("//div[@id='Content/ImageToolbarAction[organizer.editor.save.title]_label']")).click();
			Dailylog.logInfoDB(28,"outlet saving display change to NO.", Store,testName);
			
		}
		hmcPage.hmcHome_hmcSignOut.click();
	}

	/**
	 * check list price equal web price
	 */
	public void listEQweb(WebDriver driver,B2CPage b2cPage,String partNumber) {

		driver.get(testData.B2C.getHomePageUrl());
		
		//B2CCommon.handleGateKeeper(b2cPage, testData);
		
		b2cPage.Navigation_ProductsLink.click();
		//check PLP Saving and list price 
		driver.findElement(By.xpath("//*[@id='Laptops & Ultrabooks']")).click();
		String PLPSavingsxpath = "//a[contains(@id,'" + partNumber+ "')]/..//dt[contains(text(),'   Savings:')]/span";
		String plpListPricexpath = "//a[contains(@id,'" + partNumber+ "')]/..//dd[@itemprop='listPrice']";
		Boolean flag = isDisplay(PLPSavingsxpath);
		Assert.assertFalse(flag,"The saving option is dispalyed on PLPpage!");
		Dailylog.logInfoDB(4,"The saving option is not dispalyed on PLPwebpage", Store,testName);
		flag = isDisplay(plpListPricexpath);
		Assert.assertFalse(flag,"The list price is dispalyed on PLPpage!");
		Dailylog.logInfoDB(6,"The list price is not dispalyed on PLPwebpage", Store,testName);
		
		//check PDP Saving and list price lab
		driver.get(testData.B2C.getHomePageUrl()+ "/p//" + partNumber);
		String PDPSavingsxpath = "//*[@id='builderPricingSummary']/dt[contains(text(),'            Savings:')]";
		flag = isDisplay(PDPSavingsxpath);
		Assert.assertFalse(flag,"The saving option is dispalyed on PDPpage!");
		Dailylog.logInfoDB(8,"The saving option is not dispalyed on PDPwebpage", Store,testName);
		String pdpListPricexpath = "//dt[contains(text(),'List Price:')]/following-sibling::dd[1]";
		flag = isDisplay(pdpListPricexpath);
		Assert.assertFalse(flag,"The list price is dispalyed on PLPpage!");
		Dailylog.logInfoDB(10,"The list price is not dispalyed on PDPwebpage", Store,testName);
	}
	/**
	 * check list price great than web price and without other discount rule
	 */
	public void listRTweb(WebDriver driver,B2CPage b2cPage,String partNumber) {

		driver.get(testData.B2C.getHomePageUrl());
		b2cPage.Navigation_ProductsLink.click();
		
		//check PLP Saving and list price lab and price value
		driver.findElement(By.xpath("//*[@id='Laptops & Ultrabooks']")).click();
		String PLPSavingsxpath = "//a[contains(@id,'" + partNumber+ "')]/..//dd[@itemprop='youSave']";
		String plpListPricexpath = "//a[contains(@id,'" + partNumber+ "')]/..//dd[@itemprop='listPrice']";
		Boolean flag = isDisplay(PLPSavingsxpath);
		Assert.assertTrue(flag,"The saving option not dispalyed on webpage!");
		Dailylog.logInfoDB(12,"The saving option dispalyed on PLPwebpage", Store,testName);
		flag = isDisplay(plpListPricexpath);
		Assert.assertTrue(flag);
		//check value
		String listPriceValue = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dd[@itemprop='listPrice']")).getText().toString();
		float plpListPrice = B2CCommon.GetPriceValue(listPriceValue);
		System.out.println("listPriceValue is "+ plpListPrice);
		String outletPriceValue = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dd[@itemprop='price']")).getText().toString();
		float plpOutletPrice = B2CCommon.GetPriceValue(outletPriceValue);
		System.out.println("outletPriceValue is "+ plpOutletPrice);
		String savings = driver.findElement(By.xpath(PLPSavingsxpath)).getText().toString();
		float plpSavings = B2CCommon.GetPriceValue(savings);
		System.out.println("savings price is "+ plpSavings);
		Assert.assertTrue(plpSavings==(plpListPrice-plpOutletPrice),"the price is not equal!");
		Dailylog.logInfoDB(14,"The saving value dispalyed correct on PLPwebpage", Store,testName);
		
		//check PDP Saving and list price lab and price value
		driver.get(testData.B2C.getHomePageUrl()+ "/p//" + partNumber);
		String PDPSavingsxpath = "//*[@id='builderPricingSummary']/dt[contains(text(),'            Savings:')]";
		flag = isDisplay(PDPSavingsxpath);
		Assert.assertTrue(flag,"The saving option not dispalyed on webpage!");
		Dailylog.logInfoDB(2,"The saving option dispalyed on PDPwebpage", Store,testName);
		String pdpListPricexpath = "//dt[contains(text(),'List Price:')]/following-sibling::dd[1]";
		flag = isDisplay(pdpListPricexpath);
		Assert.assertTrue(flag);
		//check value
		String pdplistPriceValue = driver.findElement(By.xpath("//dt[contains(text(),'List Price:')]/following-sibling::dd[1]")).getText().toString();
		System.out.println("pdplistPriceValue is "+ pdplistPriceValue);
		String pdpoutletPriceValue = driver.findElement(By.xpath("//dt[contains(text(),'Outlet Price:')]/following-sibling::dd[1]")).getText().toString();
		System.out.println("pdpoutletPriceValue is "+ pdpoutletPriceValue);
		String pdpsavings = driver.findElement(By.xpath("//dt[contains(text(),'   Savings:')]/following-sibling::dd[1]")).getText().toString();
		float pdpSavingsPrice = B2CCommon.GetPriceValue(pdpsavings);
		Assert.assertTrue(plpSavings==pdpSavingsPrice,"the price is not equal!");
		Dailylog.logInfoDB(16,"The saving value dispalyed correct on PDPwebpage", Store,testName);
	}
	
	public Boolean isDisplay(String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		}catch(NoSuchElementException e) {
			return false;
		}
		
	}

	public void deleRule(String country,String store,String partNumber) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 2)){
			HMCCommon.Login(hmcPage, testData);
		}
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		Common.sleep(2000);			
		HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[" + Store.substring(0, 2)+"] " + country,store.replace("_", " "),
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
			/*if(webRule!=""){
				HMCCommon.deleteRule(driver, hmcPage,"Web Prices", webRule);
				webRule="";
				System.out.println("Rule Deleted:"+webRule);
			}*/
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
	
	/**
	 * check list price great than web price and with other rule (Instant Savings discount >  eCoupon discount)
	 */
	public void checkOut(WebDriver driver,B2CPage b2cPage,String partNumber) throws InterruptedException {
		float plpListPrice;
		float plpOutletPrice;
		float plpAfterSavingsPrice;
		float plpeCouponPrice;
		float plpSavings;
		
		driver.get(testData.B2C.getHomePageUrl());
		System.out.println("============go to plp page==================");
		b2cPage.Navigation_ProductsLink.click();
		driver.findElement(By.xpath("//*[@id='Laptops & Ultrabooks']")).click();
		//WebElement PLPSavings = driver.findElement(By.xpath("//div[@class='pricingSummary']/div/dt[contains(text(),'Savings:')]"));
		
		String listPriceValue = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dd[@itemprop='listPrice']")).getText().toString();
		plpListPrice = B2CCommon.GetPriceValue(listPriceValue);
		System.out.println("listPriceValue is "+ plpListPrice);
		String outletPriceValue = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dt[contains(text(),'Outlet Price:')]/span")).getText().toString();
		plpOutletPrice = B2CCommon.GetPriceValue(outletPriceValue);
		System.out.println("outletPriceValue is "+ plpOutletPrice);
		String SavingValue = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dt[contains(text(),'After Instant Savings:')]/following-sibling::dd[1]")).getText().toString();
		plpAfterSavingsPrice = B2CCommon.GetPriceValue(SavingValue);
		System.out.println("After Saving price is "+ plpAfterSavingsPrice);
		String eCoupon = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dt[contains(text(),'After eCoupon:')]//following-sibling::dd[1]")).getText().toString();
		plpeCouponPrice = B2CCommon.GetPriceValue(eCoupon);
		System.out.println("After eCoupon price is "+ plpeCouponPrice);
		String savings = driver.findElement(By.xpath("//a[contains(@id,'" + partNumber+ "')]/..//dt[contains(text(),'   Savings:')]/span")).getText().toString();
		plpSavings = B2CCommon.GetPriceValue(savings);
		System.out.println("Total savings price is "+ plpSavings);
		Assert.assertTrue(plpSavings==(plpListPrice-plpAfterSavingsPrice),"The PLP Page Savings Price is not correct!");
		Dailylog.logInfoDB(18,"The PLP Page Savings Price is correct!", Store,testName);
		
		
		// to PDP page
		System.out.println("============go to pdp page==================");
		driver.get(testData.B2C.getHomePageUrl()+ "/p//" + partNumber);
		//WebElement PDPSavings = driver.findElement(By.xpath("//*[@id='builderPricingSummary']/dt[contains(text(),'            Savings:')]"));
		//flag = isDisplay(driver,PDPSavings,5);
		//Assert.assertTrue(flag,"The saving option not dispalyed on webpage!");
		float pdpSavingsPrice;
		String pdplistPriceValue = driver.findElement(By.xpath("//dt[contains(text(),'List Price:')]/following-sibling::dd[1]")).getText().toString();
		System.out.println("pdplistPriceValue is "+ pdplistPriceValue);
		String pdpoutletPriceValue = driver.findElement(By.xpath("//dt[contains(text(),'Outlet Price:')]/following-sibling::dd[1]")).getText().toString();
		System.out.println("pdpoutletPriceValue is "+ pdpoutletPriceValue);
		String pdpSavingValue = driver.findElement(By.xpath("//dt[contains(text(),'After Instant Savings:')]/following-sibling::dd[1]")).getText().toString();
		System.out.println("pdp After Saving price is "+ pdpSavingValue);
		String pdpeCoupon = driver.findElement(By.xpath("//dt[contains(text(),'After eCoupon:')]//following-sibling::dd[1]")).getText().toString();
		System.out.println("pdp After eCoupon price is "+ pdpeCoupon);
		String pdpsavings = driver.findElement(By.xpath("//dt[contains(text(),'   Savings:')]/following-sibling::dd[1]")).getText().toString();
		pdpSavingsPrice = B2CCommon.GetPriceValue(pdpsavings);
		Assert.assertTrue((listprice - webprice + 15)==pdpSavingsPrice,"The PDP Page Savings Price is not correct!");
		Dailylog.logInfoDB(20,"The PDP Page Savings Price is correct!", Store,testName);
		System.out.println("pdp Total savings price is "+ pdpsavings);
		
		
		
		// go to cart page
		System.out.println("============go to cart page==================");
		float cartSavingsPrice;
		B2CCommon.clickAddtocartOrCustomizeOnPDP(driver);
		Common.sleep(3000);
		String cartPrice = driver.findElement(By.xpath("//span[contains(@class,'price-calculator-cart-summary-subTotalWithoutCoupon')]")).getText().toString();
		System.out.println("Cart page price is "+ cartPrice);
		float cartprice = B2CCommon.GetPriceValue(cartPrice);
		String carttotalPrice = driver.findElement(By.xpath("//span[contains(@class,'qa_estimatedTotal_Price')]")).getText().toString();
		System.out.println("Cart page total price is "+ carttotalPrice);
		String yourSavingPrice = driver.findElement(By.xpath("//div[@class='cart-summary-totalSavings']/span")).getText().toString();
		cartSavingsPrice = B2CCommon.GetPriceValue(yourSavingPrice);
		float allSavings = listprice - cartprice + 15;
		Assert.assertTrue(allSavings==cartSavingsPrice,"the Cart Page Price is not correct!");
		System.out.println("Cart page total savings price is " + cartSavingsPrice);
		Dailylog.logInfoDB(22,"The Cart Page Savings Price is correct!", Store,testName);
		
		//go to checkout page
		System.out.println("============go to checkout Shipping&Payment page==================");
		float chkSavingsPrice;
		b2cPage.Cart_CheckoutButton.click();
		String chkbaseprice = driver.findElement(By.xpath("//div[@class='base-price']")).getText().toString();
		System.out.println("checkout page base price is "+ chkbaseprice);
		String chksutotalprice = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText().toString();
		System.out.println("checkout page subtotal price is "+ chksutotalprice);
		String chkshppingprice = driver.findElement(By.xpath("//div[contains(text(),'Shipping:')]/following-sibling::div[1]")).getText().toString();
		System.out.println("checkout page shpping base price is "+ chkshppingprice);
		String chktotalprice = driver.findElement(By.xpath("//div[@class='summary-item total']//span[@id='noTaxTotal']")).getText().toString();
		System.out.println("checkout page total price is "+ chktotalprice);
		String chksavings = driver.findElement(By.xpath("//div[@class='cart-summary-totalSavings']/span")).getText().toString();
		chkSavingsPrice = B2CCommon.GetPriceValue(chksavings);
		Assert.assertTrue(allSavings==chkSavingsPrice,"the price is not equal!");
		Dailylog.logInfoDB(24,"The Checkout Page Savings Price is correct!", Store,testName);
		System.out.println("checkout page savings price is "+ chksavings);
		
		B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
		Common.sleep(5000);
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", b2cPage.Shipping_ContinueButton);	
		if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SkipButton, 5)){
			b2cPage.ValidateInfo_SkipButton.click();
		}
		
		B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
		Common.sleep(5000);
		
		//go to Review and Purchase page
		js.executeScript("arguments[0].click();", b2cPage.Payment_ContinueButton);
		Common.sleep(1000);
		
		//go to thank you page
		System.out.println("============go to Thank you page==================");
		js.executeScript("arguments[0].click();", b2cPage.OrderSummary_AcceptTermsCheckBox);
		B2CCommon.clickPlaceOrder(b2cPage);
		Common.sleep(5000);	
		
		String thxbaseprice = driver.findElement(By.xpath("//div[@class='base-price']")).getText().toString();
		System.out.println("Thx page base price is "+ thxbaseprice);
		String thxsutotalprice = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText().toString();
		System.out.println("checkout page subtotal price is "+ thxsutotalprice);
		String thxshppingprice = driver.findElement(By.xpath("//div[contains(@class,'shipping-value')]")).getText().toString();
		System.out.println("Thx page shpping base price is "+ thxshppingprice);
		String thxtotalprice = driver.findElement(By.xpath("//div[@class='right total-price']")).getText().toString();
		System.out.println("Thx page total price is "+ thxtotalprice);
		String thxTax = driver.findElement(By.xpath("//div[contains(text(),'Tax:')]/following-sibling::div[1]")).getText().toString();
		float thxTaxprice = B2CCommon.GetPriceValue(thxTax);
		System.out.println("Thx page total price is "+ thxTaxprice);
		String thxsavings = driver.findElement(By.xpath("//div[@class='totalSaving']")).getText().toString().replaceAll("[^0-9\\.]", "");
		chkSavingsPrice = B2CCommon.GetPriceValue(thxsavings);
		Assert.assertTrue(allSavings==chkSavingsPrice,"the price is not equal!");
		String orderNumber= driver.findElement(By.xpath("//span[contains(text(),'Order number:')]/following-sibling::span[1]")).getText().toString();
		System.out.println(orderNumber);
		Dailylog.logInfoDB(26,"The Thank you Page Savings Price is correct!", Store,testName);
		System.out.println("Thank you page savings price is "+ chksavings);
		
		}

	public String createRule(String ruleType, String discountPrice, String unit, Boolean isStakable,String partNumber) throws InterruptedException {
		WebElement target;
		String dataInput;
		String xpath;
		String priceRlueID = null;
		System.out.println("Create rules***********************" + ruleType);
		String ruleName = this.Store + testName + partNumber+ ruleType.replace(" ", "");
		driver.get(testData.HMC.getHomePageUrl());

		if (Common.isElementExist(driver, By.id("Main_user"))) {
			HMCCommon.Login(hmcPage, testData);
			driver.navigate().refresh();
		}

		hmcPage.Home_PriceSettings.click();
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

		// rule name
		hmcPage.B2CPriceRules_PriceRuleName.clear();
		hmcPage.B2CPriceRules_PriceRuleName.sendKeys(ruleName);

		// Validate from date
		hmcPage.B2CPriceRules_ValidFrom.click();
		Thread.sleep(1000);
		int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
		WebElement yesterday;
		if (count > 0) {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).get(count - 1);
			System.out.println("Valid From: " + yesterday.getText());
			yesterday.click();
			hmcPage.B2CPriceRules_ValidFrom.sendKeys(Keys.ENTER);
		} else {
			yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td")).get(count - 1);
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
			//hmcPage.B2CPriceRules_dynamicRate.click();
			//hmcPage.B2CPriceRules_DynamicMinusButton.click();
			//hmcPage.B2CPriceRules_ecouponDollorButton.click();
			//hmcPage.B2CPriceRules_dynamicValue.clear();
			//hmcPage.B2CPriceRules_dynamicValue.sendKeys(discountPrice);
			//discountPrice = String.valueOf(webprice);
			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);		

		} else if (ruleType.equals("Floor")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		} else if (ruleType.equals("eCoupon Discounts")) {
			// display ecopon message
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

			if (isStakable)
				Common.javascriptClick(driver, hmcPage.B2CPriceRules_StackableCheckBox);

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			
			Thread.sleep(2000);

		} else if (ruleType.equals("Web")) {

			hmcPage.B2CPriceRules_PriceValue.clear();
			hmcPage.B2CPriceRules_PriceValue.sendKeys(discountPrice);
			Thread.sleep(2000);

		}

		// Material
		xpath = "//span[contains(text(),'" + partNumber + "')]/../../div[starts-with(text()[2],']') or not(text()[2])]";
		fillRuleInfo(driver, hmcPage, "Material", partNumber, hmcPage.B2CPriceRules_MaterialSelect, xpath);

		// Add Price Rule To Group
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_AddPriceRuleToGroup);
		// hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
		System.out.println("click Add Price Rule To Group --- first time");
		Thread.sleep(2000);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_CreateGroup);
		// hmcPage.B2CPriceRules_CreateGroup.click();
		System.out.println("click Create New Group --- first time");
		Thread.sleep(1000);
		if (Common.isElementExist(driver, By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
			hmcPage.B2CPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- second time");
			hmcPage.B2CPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- second time");
		}
		Thread.sleep(10000);
		// Record Price Rule ID
		if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
			hmcPage.B2CPriceRules_CloseBtn.click();
			System.out.println("Clicked close!");
			Thread.sleep(3000);
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_FilterBtn);

		System.out.println("Clicked filter!");
		Thread.sleep(3000);
		if (ruleType.equals("Floor")) {
			WebElement showRuleID = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
			priceRlueID = driver.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]")).getText();
			System.out.println("Price Rule ID is : " + priceRlueID);

		}

		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		return priceRlueID;
	}
	
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
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

	public void getDebugPrice(WebDriver driver,HMCPage hmcPage,String country,String store,String unit,String partNumber) {
		String costprice;
		String floorPrice;
		String listPrice;
		String webPrice;
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		HMCCommon.loginPricingCockpit(driver,hmcPage,testData);
		Common.sleep(2000);
			HMCCommon.B2CPriceSimulateDebug(driver,hmcPage,"[" + Store.substring(0, 2)+"] " + country,unit,
					"Nemo Master Multi Country Product Catalog - Online",partNumber);
			Common.sleep(2000);

		costprice= driver.findElement(By.xpath("//td[@class='cost']/samp[@id='value']")).getText();
		listPrice= driver.findElement(By.xpath("//td[@class='list']/samp[@id='value']")).getText();
		floorPrice= driver.findElement(By.xpath("//td[@class='floor']/samp[@id='value']")).getText();
		webPrice= hmcPage.B2BpriceSimulate_webPrice.getText();
		Common.sleep(2000);
		costPrice = B2CCommon.GetPriceValue(costprice);
		System.out.println("Debug Cost Price is:" + costPrice);
		listprice =B2CCommon.GetPriceValue(listPrice);
		System.out.println("Debug List Price is:" + listPrice);
		webprice = B2CCommon.GetPriceValue(webPrice);
		System.out.println("Debug Web Price is:" + webPrice);
		floorprice = B2CCommon.GetPriceValue(floorPrice);
		System.out.println("Debug Floor Price is:" + floorPrice);
		Dailylog.logInfoDB(101,"Debug defualt price rules.", Store,testName);
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		
	}	
	
	public void startCronJob(WebDriver driver, HMCPage hmcPage, String jobName, int windowSize)
			throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 2)){
			HMCCommon.Login(hmcPage, testData);
		}
		System.out.println("CronJob start");
		//System > CronJob > singleCouponJob
		hmcPage.Home_System.click();
		hmcPage.Home_cronJob.click();
		if (!Common.isElementExist(driver, By.xpath("//a/span[contains(.,'Search')]"))) {
			driver.findElement(By.xpath("(//*[contains(@id,'[organizersearch][CronJob]')])[1]")).click();
			driver.findElement(By.xpath("(//*[contains(@id,'[organizerlist][CronJob]')])[1]")).click();
		}
		// JobName
		hmcPage.CronJob_jobName.clear();
		hmcPage.CronJob_jobName.sendKeys(jobName);
		hmcPage.CronJob_searchButton.click();
		// Selecting the Job From Search Results
		Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		// Start CronJob
		hmcPage.CronJob_startCronJob.click();
		Thread.sleep(5000);
		Common.switchToWindow(driver, windowSize);
		//Assert.assertEquals(hmcPage.CronJob_cronJobSuccessMsg.getText(), "CronJob performed.");
		driver.close();
		Common.switchToWindow(driver, windowSize - 1);
		String endTime = "";
		int j =0 ;
		while (endTime.length()< 10) {
			Common.sleep(120000);
			hmcPage.Home_System.click();
			hmcPage.Home_System.click();
			hmcPage.Home_cronJob.click();
			driver.findElement(By.xpath("//*[@id='Content/OrganizerComponent[organizersearch][CronJob]_togglelabel']")).click();
			if (!Common.isElementExist(driver, By.xpath("//a/span[contains(.,'Search')]"))) {
				driver.findElement(By.xpath("(//*[contains(@id,'[organizersearch][CronJob]')])[1]")).click();
				driver.findElement(By.xpath("(//*[contains(@id,'[organizerlist][CronJob]')])[1]")).click();
			}
			// JobName
			hmcPage.CronJob_jobName.clear();
			hmcPage.CronJob_jobName.sendKeys(jobName);
			hmcPage.CronJob_searchButton.click();
			// Selecting the Job From Search Results
			Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
			Common.doubleClick(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
			driver.navigate().refresh();
			endTime = driver.findElement(By.xpath("//*[@id='Content/DateTimeEditor[in Content/Attribute[SolrIndexerCronJob.endTime][1]]_date']")).getAttribute("value");
			System.out.print(endTime);
			j++;
			System.out.println(j);
		}
		System.out.print(endTime);
		hmcPage.Home_System.click();
		System.out.println("CronJob end");
	}
}