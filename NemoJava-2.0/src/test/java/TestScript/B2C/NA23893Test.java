package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23893Test extends SuperTestClass{
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String cart_Price;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	

	public NA23893Test(String store){
		this.Store = store;
		this.testName = "NA-23893";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA23893(ITestContext ctx){
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, Go to HMC --> B2C Commerce --> B2C Unit --> Site Attributes
			Dailylog.logInfoDB(1, " Go to HMC --> B2C Commerce --> B2C Unit --> Site Attributes", Store, testName);
			
			driver.get(hmcLoginUrl);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			if(!driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.enableGuestQuote') and contains(@id,'true')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.enableGuestQuote') and contains(@id,'true')]")).click();
			}
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.enableGuestQuote') and contains(@id,'true')]")).isSelected());
			hmcPage.Home_EndSessionLink.click();
			
			//2, Login store front as a telesales user. 
			Dailylog.logInfoDB(2, "Login store front as a telesales user.", Store, testName);
			
			driver.get(loginUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			//3, Add any product into cart
			Dailylog.logInfoDB(3, "Add any product into cart", Store, testName);

			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(7000);
			
			driver.get(cartUrl);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + testData.B2C.getDefaultMTMPN() + "']")));
			
			cart_Price = driver.findElement(By.xpath("//span[contains(@class,'subTotal')]")).getText().toString().trim();
			cart_Price = cart_Price.replace("$", "").replace("HK", "").replaceAll("SG", "").replace("£", "")
					.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
					.replaceAll("₩", "").trim();
			
			Common.javascriptClick(driver, b2cPage.SignoutASM);
			
			//4, Open HMC, search in Marketing --> Order Statistics --> Cart with customer ID
			//anonymous
			Dailylog.logInfoDB(4, "Open HMC, search in Marketing --> Order Statistics --> Cart with customer ID", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			String CARTID = getCardID();
			
			//5, Go back to ASM, type cart id got in step 2 in Transaction Search or Advanced Transaction Search
			//6,After selecting this cart id, the corresponding information in this cart will be shown
			Dailylog.logInfoDB(5, "Go back to ASM, type cart id got in step 2 in Transaction Search or Advanced Transaction Search", Store, testName);
			Dailylog.logInfoDB(6, "After selecting this cart id, the corresponding information in this cart will be shown", Store, testName);
			
			
			driver.get(loginUrl);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());

			B2CCommon.handleGateKeeper(b2cPage, testData);
			
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
			
			//7, Start session
			Dailylog.logInfoDB(7, "Start Session", Store, testName);
			
			Common.javascriptClick(driver, b2cPage.Tele_StartSession);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions.urlContains("cart"));
			
			Assert.assertTrue(driver.getCurrentUrl().endsWith("cart"));
			
			
			//8,Preform price override.
				//1. Enter a new price
				//2. Selected reason option.
				//3. Enter comment.
				//4. Click *Set* button.
			float Price = GetPriceValue(driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText().toString()
					.replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
					.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
					.replaceAll("₩", ""));
			Price = Price - 1;
			
			System.out.println("Price is :" + Price);
			
			b2cPage.OverrideValue.sendKeys(Price + "");
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			
			float Price1 = GetPriceValue(driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText().toString()
					.replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
					.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
					.replaceAll("₩", ""));
			
			System.out.println("Price1 is :" + Price1);
			
			Assert.assertTrue(Price == Price1);
			
			//9, click request quote 
			Dailylog.logInfoDB(9, "click request quote", Store, testName);
			
			b2cPage.Override_RequestQuote.click();
			Thread.sleep(6000);
			
			driver.findElement(By.id("create-one-time-quote")).click();
			Thread.sleep(3000);
			b2cPage.Override_RepID.clear();
			Thread.sleep(4000);
			b2cPage.Override_RepID.sendKeys(testData.B2C.getRepID());
			Thread.sleep(3000);
			driver.findElement(By.id("contactEmail")).clear();
			driver.findElement(By.id("contactEmail")).sendKeys(testData.B2C.getLoginID());
			Thread.sleep(6000);
			b2cPage.Override_SubmitQuote.click();
			Thread.sleep(6000);
			
			String QuoteNo = b2cPage.Override_QuoteNumber.getText().toString();
			Dailylog.logInfo(Store + " quote number is " + QuoteNo);
			
			//10, Check the price in quote
			Dailylog.logInfoDB(10, "Check the price in quote", Store, testName);
			
			String quote_price = driver.findElement(By.xpath("//*[@id='mainContent']/div[1]//div[2]/table/tbody/tr[2]/td[2]")).getText().toString();
			
			float Price_quote = GetPriceValue(quote_price
					.replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
					.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
					.replaceAll("₩", ""));
			
			System.out.println("Price_quote is :" + Price_quote);
			
			Assert.assertTrue(Price1 == Price_quote);

		} catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	@AfterTest(alwaysRun= true)
	public void rollBack(ITestContext ctx){
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//11, Roll Back :
			//Follow step 1 and set
			//EnableGuestQuote:NO
			Dailylog.logInfoDB(11, "Roll Back set the EnableGuestQuote to NO", Store, testName);
			
			driver.get(hmcLoginUrl);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			if(!driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.enableGuestQuote') and contains(@id,'false')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.enableGuestQuote') and contains(@id,'false')]")).click();
			}
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.enableGuestQuote') and contains(@id,'false')]")).isSelected());
			hmcPage.Home_EndSessionLink.click();
			
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	
	public String  getCardID() throws Exception{
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.marketing.click();
		hmcPage.marketing_orderStatistics.click();
		hmcPage.marketing_orderStatistics_carts.click();
		
		Thread.sleep(6000);
		hmcPage.marketing_orderStatistics_carts_user_icon.click();
		
		Thread.sleep(10000);
		Common.switchToWindow(driver, 1);
		
		driver.findElement(By.xpath("//input[contains(@id,'User.uid')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'User.uid')]")).sendKeys("anonymous");
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("(//div[contains(@id,'StringDisplay') and contains(@id,'span') and contains(@id,'anonymous')])[1]")).click();
		driver.findElement(By.xpath("//span[contains(@id,'use')]")).click();
		
		Common.switchToWindow(driver, 0);
		
		
		hmcPage.marketing_orderStatistics_carts_Store_icon.click();
		Thread.sleep(10000);
		
		Common.switchToWindow(driver, 1);
		driver.findElement(By.xpath("//input[contains(@id,'BaseStore.uid')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'BaseStore.uid')]")).sendKeys(testData.B2C.getStore());
		
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("(//div[contains(@id,'StringDisplay') and contains(@id,'span') and contains(@id, '"+ testData.B2C.getStore() +"')])[1]")).click();
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
	
	
	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}
	
	
	
		
	
}
