package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA22978Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;

	public String UnitID = "coweb";
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	public NA22978Test(String Store){
		this.Store = Store;
		this.testName = "NA-22978";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA22978(ITestContext ctx){
		
		try{
			this.prepareTest();
			hmcPage =new HMCPage(driver);
			b2cPage =new B2CPage(driver);
			
			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			// 1 ,Create quantity limit ecopon rule for a product
			//HMC->Pricing Cockpit->B2C rules,
			//Click create new group then select Group Type, click Continue,input necessary field
			Dailylog.logInfoDB(1, "Create quantity limit ecopon rule for a product", Store, UnitID);
			
			
			driver.get(hmcHomePageUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			
			Thread.sleep(10000);
			
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'europe1novarto')]")));
			
			
			driver.findElement(By.xpath("//div/a[contains(@href,'europe1novarto/price/listRules')]")).click();
			Thread.sleep(10000);
			
			String title = "NA-22978-Test";
			driver.findElement(By.xpath("//span[text()='Select Group']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
			driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(title);
			Thread.sleep(4000);
			while(Common.isElementExist(driver, By.xpath("//div[contains(@id,'select2-result')]/span"))){
				driver.findElement(By.xpath("(//div[contains(@id,'select2-result')]/span)[1]")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//button[text()='Filter']")).click();
				
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//td[contains(.,'NA-22978-Test')]/../td/div/a[contains(.,'Delete')]")));
				driver.findElement(By.xpath("//td[contains(.,'NA-22978-Test')]/../td/div/a[contains(.,'Delete')]")).click();
				Thread.sleep(10000);
				
				driver.findElement(By.xpath("//input[contains(@class,'confirmation')]")).clear();
				driver.findElement(By.xpath("//input[contains(@class,'confirmation')]")).sendKeys("DELETE");
				
				driver.findElement(By.xpath("//button[text()='Confirm']")).click();
				
				driver.navigate().refresh();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'europe1novarto')]")));
				
				
				driver.findElement(By.xpath("//div/a[contains(@href,'europe1novarto/price/listRules')]")).click();
				
				driver.findElement(By.xpath("//span[text()='Select Group']")).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
				driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(title);
				Thread.sleep(4000);

			}
			
			driver.navigate().refresh();
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'europe1novarto')]")));
			
			
			driver.findElement(By.xpath("//div/a[contains(@href,'europe1novarto/price/listRules')]")).click();
			Thread.sleep(4000);

			hmcPage.B2CPriceRules_CreateNewGroup.click();
			
			Thread.sleep(4000);
			
			
			driver.findElement(By.xpath("//span[@class='select2-chosen'][contains(.,'Select Group Type')]")).click();
			driver.findElement(By.xpath("//li/div[contains(@id,'select2-result-label')][contains(.,'eCoupon Discounts')]")).click();
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//li/div[contains(@id,'select2-result-label')][contains(.,'eCoupon Discounts')]")));
//			
			driver.findElement(By.xpath("//button[contains(.,'Continue')]")).click();
			
			// input the necessary field 
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			
//			String startTime = sdf.format(new Date());
//			
//			System.out.println("start time is :" + startTime);
			
			String count = "3";
			
			hmcPage.eCoupon_Discounts.clear();
			hmcPage.eCoupon_Discounts.sendKeys(title);
//			hmcPage.eCoupon_ValidFrom.clear();
//			hmcPage.eCoupon_ValidFrom.sendKeys(startTime);
			
			driver.findElement(By.xpath("//textarea[@name='marketingMessage']")).click();
			
			Thread.sleep(10000);
			
			driver.findElement(By.xpath("//*[@id='priorityId']")).clear();
			driver.findElement(By.xpath("//*[@id='priorityId']")).sendKeys("500");
			
			
			
			if(!hmcPage.eCoupon_Stackable.isSelected()){
				hmcPage.eCoupon_Stackable.click();
			}
			
			hmcPage.eCoupon_Count.clear();
			hmcPage.eCoupon_Count.sendKeys(count);
			
			if(!hmcPage.eCoupon_cart_Checkout.isSelected()){
				hmcPage.eCoupon_cart_Checkout.click();
			}
			
			String country = getCountry(Store);
			System.out.println("country is :" + country);
//			Select select_country = new Select(driver.findElement(By.xpath("//select[@id='countryId']")));
//			select_country.selectByVisibleText(country);
			driver.findElement(By.xpath("//div[@id='s2id_countryId']/a/span[contains(.,'Select Country')]")).click();
			//driver.findElement(By.xpath("//div[contains(@id,'select2-result-label')][contains(.,'Australia')]")).click();
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(@id,'select2-result-label')][contains(.,'"+country+"')]")));
			driver.findElement(By.xpath("//div[@id='select2-drop']/div[@class='select2-search']/input")).clear();
			driver.findElement(By.xpath("//div[@id='select2-drop']/div[@class='select2-search']/input")).sendKeys(country);
			driver.findElement(By.xpath("(//div[@id='select2-drop']/ul/li/div)[last()]")).click();
			
			
			Thread.sleep(5000);
			String catalogVersion = "Nemo Master Multi Country Product Catalog - Online";
//			Select select_CatalogVersion = new Select(driver.findElement(By.xpath("//select[@id='catalogId']")));
//			select_CatalogVersion.selectByVisibleText(catalogVersion);
			driver.findElement(By.xpath("//div[@id='s2id_catalogId']/a/span[contains(.,'Select Catalog Version')]")).click();
//			JavascriptExecutor js1 = (JavascriptExecutor)driver;
//			js1.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(@id,'select2-result-label')][contains(.,'"+catalogVersion+"')]")));
			driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
			driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(catalogVersion);
			driver.findElement(By.xpath("//div[@id='select2-drop']/ul/li/div/span")).click();
			
			
			
			
			
			hmcPage.eCoupon_Dynamic.click();
			driver.findElement(By.xpath("//label[contains(@for,'negative')]/span[contains(@class,'minplus')]")).click();
			driver.findElement(By.xpath("//span[contains(@class,'usd')]")).click();
			
			driver.findElement(By.xpath(".//*[@id='dynamic-temp']//input[contains(@class,'operationValue ')]")).clear();
			driver.findElement(By.xpath(".//*[@id='dynamic-temp']//input[contains(@class,'operationValue ')]")).sendKeys("10");
			
			
			Thread.sleep(5000);
			String prodNumber = getMaterial(Store);
			
			hmcPage.eCoupon_material.click();
			
			boolean b = hmcPage.eCoupon_material_search.isEnabled();
			System.out.println("b is :" + b);

			Thread.sleep(5000);
			
			hmcPage.eCoupon_material_search.clear();
			hmcPage.eCoupon_material_search.sendKeys(prodNumber);
			
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='select2-drop']/ul/li/div")).click();
			
			//hmcPage.eCoupon_addPriceRuleToGroup.click();
			
			driver.findElement(By.xpath("//button[contains(.,'Add Price Rule to Group')]")).click();
			hmcPage.eCoupon_createGroup.click();
			
			driver.switchTo().defaultContent();
			hmcPage.Home_EndSessionLink.click();
			
			//2, Login website customer or Tele user.
			Dailylog.logInfoDB(2, "Login website customer or Tele user.", Store, UnitID);
			
			driver.get(ordinary_loginUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			//3, Pick up product (with available quantity limit coupon) and add to cart
			Dailylog.logInfoDB(3, "Pick up product (with available quantity limit coupon) and add to cart", Store, UnitID);
			
			
			driver.get(ordinary_cartUrl);
			B2CCommon.emptyCart(driver, b2cPage);
			b2cPage.Payment_QuickAddBox.clear();
			b2cPage.Payment_QuickAddBox.sendKeys(getMaterial(Store));
			driver.findElement(By.xpath("//*[@id='quickAddInput']/*[@type='submit']")).click();
			
			//4, Apply eCoupon
			Dailylog.logInfoDB(4, "Apply eCoupon", Store, UnitID);
			
			String price_before = driver.findElement(By.xpath("//dl[@class='cart-summary-pricingTotal']//span[contains(@class,'calculator-cart-summary')]")).getText().toString();
			System.out.println("price_before :" + price_before);
			driver.findElement(By.xpath(".//*[@id='active_button']")).click();
			
			String price_after = driver.findElement(By.xpath("//dl[@class='cart-summary-pricingTotal']//span[contains(@class,'calculator-cart-summary')]")).getText().toString();
			System.out.println("price_after :" + price_after);
			Assert.assertTrue(!price_before.equals(price_after));
			
			//5, Click *Request Quote* btn on Cart or  Payment Page(Checkout P2)
			Dailylog.logInfoDB(5, "Click *Request Quote* btn on Cart or  Payment Page(Checkout P2)", Store, UnitID);
			
			Thread.sleep(6000);
			b2cPage.RequestQuoteBtn.click();
			Thread.sleep(5000);
			
			Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='quote_removeCouponBtn']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='quote_continueToCartBtn']")).isDisplayed());
			System.out.println("======================");
			
			//6, Click *Keep coupon and continue with order* btn
			Dailylog.logInfoDB(6, "Click *Keep coupon and continue with order* btn", Store, UnitID);
			
			driver.findElement(By.xpath("//*[@id='quote_continueToCartBtn']")).click();
			String currentUrl = driver.getCurrentUrl().toString();
			
			Assert.assertTrue(currentUrl.endsWith("cart"));
			System.out.println("------------------------------");
			//7, Click *Remove Quantity Coupon* btn
//			b2cPage.RequestQuoteBtn.click();
			Dailylog.logInfoDB(7, "Click *Remove Quantity Coupon* btn", Store, testName);
			
			Thread.sleep(10000);
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("arguments[0].click();",b2cPage.RequestQuoteBtn);
			
			Thread.sleep(10000);
			
			driver.findElement(By.xpath("//*[@id='quote_removeCouponBtn']")).click();
			Thread.sleep(10000);
			String price_again = driver.findElement(By.xpath("//dl[@class='cart-summary-pricingTotal']//span[contains(@class,'calculator-cart-summary')]")).getText().toString();
			
			Assert.assertTrue(price_before.equals(price_again));
			
			
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}

	}

	public String getCountry(String store){
		
		String country = "";
		
		if(store.equals("AU")){
			country = "Australia";
		}else if(store.equals("US")){
			country = "United States";
		}else if(store.equals("JP")){
			country = "Japan";
		}

		return country;
		
	}
	
	
	public String getMaterial(String store){
		
		String material = testData.B2C.getDefaultMTMPN();
		
		System.out.println("material is :" + material);
		
		return material;
	}

}
