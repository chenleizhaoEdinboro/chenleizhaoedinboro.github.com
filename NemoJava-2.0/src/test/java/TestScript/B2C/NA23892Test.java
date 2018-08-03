package TestScript.B2C;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23892Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String cart_Price;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;


	public NA23892Test(String store){
		this.Store = store;
		this.testName = "NA-23892";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA23892(ITestContext ctx){
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, Login store front as a telesales user. 
			Dailylog.logInfoDB(1, "Login store front as a telesales user.", Store, testName);
			
			driver.get(homePageUrl);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			//2, Find *My Account* link from the navigation bar,and then click it.
			Dailylog.logInfoDB(2, "Find *My Account* link from the navigation bar,and then click it.", Store, testName);

			//3, Click *Start Assisted Service Session* link.
			Dailylog.logInfoDB(3, "Click *Start Assisted Service Session* link.", Store, testName);
			
			//4, With *Customer Search* feature, enter ID(Email)/Name.
			//5, Picked up the desired customer from the google type ahead list.
			//6, Click *Start Session* button.
			
			Dailylog.logInfoDB(4, "With *Customer Search* feature, enter ID(Email)/Name.", Store, testName);
			Dailylog.logInfoDB(5, "Picked up the desired customer from the google type ahead list.", Store, testName);
			Dailylog.logInfoDB(6, "Click *Start Session* button.", Store, testName);
			
			B2CCommon.loginASMAndStartSession(driver, b2cPage, "customer", testData.B2C.getLoginID());
			
			//7, Add products to cart
			Dailylog.logInfoDB(7, " Add products to cart", Store, testName);
			Thread.sleep(6000);
			
			driver.get(cartUrl);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);

			B2CCommon.addPartNumberToCartTele(b2cPage, testData.B2C.getDefaultMTMPN());
			//B2CCommon.addPartNumberToCartTele(b2cPage, "4X40K09936");
			
			cart_Price = driver.findElement(By.xpath("//span[contains(@class,'subTotal')]")).getText().toString().trim();
			cart_Price = cart_Price.replace("$", "").replaceAll("\\$", "").replaceAll("HK", "").replaceAll("SG", "").replace("£", "").replace("€", "").replaceAll("￥", "").replaceAll("NT", "").replaceAll("₩", "").replaceAll("CAD", "").replaceAll("\\*", "").trim();
			
			System.out.println("cart price is :" + cart_Price);

			//8, End session
			Dailylog.logInfoDB(8, "End session", Store, testName);
			
			b2cPage.ASM_endSession.click();
			Thread.sleep(3000);
			if(Common.isAlertPresent(driver)){
				driver.switchTo().alert().accept();
				System.out.println("There is one alert appears");
			}
			Thread.sleep(3000);
			Common.javascriptClick(driver, b2cPage.SignoutASM);
			
			
			/*//10, Click *X* icon of *Sign Out* to sign  off ASM. 
			Dailylog.logInfoDB(10, "Click *X* icon of *Sign Out* to sign  off ASM. ", Store, testName);
			
			driver.findElement(By.xpath("//button[contains(@class,'ASM_close')]")).click();
			Thread.sleep(6000);
			*/
			
			//9, Open HMC, search in Marketing --> Order Statistics --> Cart with customer ID
			Dailylog.logInfoDB(9, "Open HMC, search in Marketing --> Order Statistics --> Cart with customer ID", Store, testName);
			
			System.out.println(testData.HMC.getHomePageUrl());
			driver.get(hmcLoginUrl);
			String CARTID = getCardID();
			
			//10, Go back to ASM, type cart id got in step 9 in Transaction Search or Advanced Transaction Search
			Dailylog.logInfoDB(10, " Go back to ASM, type cart id got in step 9 in Transaction Search or Advanced Transaction Search", Store, testName);
			
			driver.get(homePageUrl);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(7000);
			
			B2CCommon.closeHomePagePopUP(driver);
			
			b2cPage.Tele_TransactionSearch.click();
			Thread.sleep(5000);
			b2cPage.Tele_TransactionSearch_TransactionId.clear();
			b2cPage.Tele_TransactionSearch_TransactionId.sendKeys(CARTID);
			b2cPage.Tele_TransactionSearch_Search.click();
			Thread.sleep(5000);
			b2cPage.Tele_TransactionSearch_SearchResult.click();
			
			Thread.sleep(10000);
			
			//11, After choosing the tart cart id, the corresponding customer name will be determined in "Customer ID" field
			Dailylog.logInfoDB(11, " After choosing the tart cart id, the corresponding customer name will be determined in Customer ID field", Store, testName);

			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//input[@id='customerFilter']/../input[@name='customerId' and @value]")));

			//12, Click "Start Session", the page will jump to the cart page with the detail in session cart before the session cart is time-out
			Dailylog.logInfoDB(12, " Click Start Session, the page will jump to the cart page with the detail in session cart before the session cart is time-out", Store, testName);
		
			Common.javascriptClick(driver, b2cPage.Tele_StartSession);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions.urlContains("cart"));
			
			Assert.assertTrue(driver.getCurrentUrl().toString().endsWith("cart"));

			String cart_Price_1 = driver.findElement(By.xpath("//span[contains(@class,'subTotal')]")).getText().toString().trim();
			cart_Price_1 = cart_Price_1.replace("$", "").replaceAll("\\$", "").replaceAll("HK", "").replaceAll("SG", "").replace("£", "").replace("€", "").replaceAll("￥", "").replaceAll("NT", "").replaceAll("₩", "").replaceAll("CAD", "").replaceAll("\\*", "").trim();
			

			System.out.println(cart_Price_1+"     "+cart_Price);
			/*Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(cart_Price_1);
			cart_Price_1 = m.replaceAll("");*/
			Assert.assertTrue(cart_Price_1.equals(cart_Price));
			Dailylog.logInfoDB(12, " Click Start Session, the page will jump to the cart page with the detail in session cart before the session cart is time-out", Store, testName);
			

		} catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	public String  getCardID() throws Exception{
		
		driver.get(hmcHomePageUrl);
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.marketing.click();
		hmcPage.marketing_orderStatistics.click();
		hmcPage.marketing_orderStatistics_carts.click();
		
		Thread.sleep(6000);
		hmcPage.marketing_orderStatistics_carts_user_icon.click();
		
		Thread.sleep(10000);
		Common.switchToWindow(driver, 1);
		
		driver.findElement(By.xpath("//input[contains(@id,'User.uid')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'User.uid')]")).sendKeys(testData.B2C.getLoginID());
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//div[contains(@id,'StringDisplay') and contains(@id,'span') and contains(@id,'"+testData.B2C.getLoginID()+"')]")).click();
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[contains(@id,'use')]")).click();
		
		Common.switchToWindow(driver, 0);
		
		Thread.sleep(3000);
		
		hmcPage.marketing_orderStatistics_carts_Store_icon.click();
		Thread.sleep(10000);
		
		Common.switchToWindow(driver, 1);
		driver.findElement(By.xpath("//input[contains(@id,'BaseStore.uid')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'BaseStore.uid')]")).sendKeys(testData.B2C.getStore());
		
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//div[contains(@id,'StringDisplay') and contains(@id,'span') and contains(@id, '"+testData.B2C.getStore()+"')]")).click();
		driver.findElement(By.xpath("//span[contains(@id,'use')]")).click();
		
		Common.switchToWindow(driver, 0);
		
		hmcPage.marketing_os_carts_search.click();
		Thread.sleep(6000);
		
		driver.findElement(By.xpath("//a[contains(@id,'date_sort')]")).click();
		Thread.sleep(6000);
		
		String cartID = Common.javaScriptGetText(driver, driver.findElement(By.xpath("(//div[@class='olecEntry']/div[contains(.,'"+cart_Price+"')]/../../..//div[@class='olecEntry']/div)[1]")));
		
		System.out.println("cartID is :" + cartID);
		
		hmcPage.Home_EndSessionLink.click();
		
		return cartID;

	}
	
	
	
		
	
}
