package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class NA26087Test extends SuperTestClass {
	
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;


	
	public NA26087Test(String store) {
		this.Store = store;
		this.testName = "NA-26087";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA26087(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, Open a test environment, login using telesales account
			Dailylog.logInfoDB(1, "Open a test environment, login using telesales account", Store, testName);
			
			driver.get(homePageUrl);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			//2, Open ASM model under "My Account"
			Dailylog.logInfoDB(2, "Open ASM model under My Account", Store, testName);

			Assert.assertTrue(driver.getCurrentUrl().endsWith("my-account"));
			
			//3, With *Customer Search* feature, enter ID(Email)/Name
			//4, Picked up the desired customer from the google type ahead list.
			//5,Click *Start Session* button.
			Dailylog.logInfoDB(3, "With *Customer Search* feature, enter ID(Email)/Name", Store, testName);
			Dailylog.logInfoDB(4, "Picked up the desired customer from the google type ahead list.", Store, testName);
			Dailylog.logInfoDB(5, "Click *Start Session* button.", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Thread.sleep(5000);
			B2CCommon.closeHomePagePopUP(driver);
			
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);
			
			//6, Select an product, Click Add to Cart or customize button,go on and go to cart page.
			driver.get(cartUrl);
			
			B2CCommon.emptyCart(driver, b2cPage);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(driver.findElement(By.xpath("//input[@class='cartDetails-overriddenPrice' and @name = 'price']")).isDisplayed());

			//7, Preform price override.
				//1.  Enter a new price into *Price Override* box, entered price which exceed delegation range, then quote approval will be triggered.
				//2. Selected reason option.
				//3. Enter comment.
				//4. Click *Update* button.
			
			Thread.sleep(4000);
			float Price = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			Price = Price - 1;
			b2cPage.OverrideValue.sendKeys(Price + "");
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			
			float Price1 = GetPriceValue(b2cPage.cartInfo_subTotalAftAnnProd.getText().toString());
			
			
			
			//8, Click *Request Quote* button.  
			Dailylog.logInfoDB(8, "Click *Request Quote* button. ", Store, testName);
			
			
			Common.scrollToElement(driver, b2cPage.Override_RequestQuote);
			Thread.sleep(4000);
			
			Common.javascriptClick(driver, b2cPage.Override_RequestQuote);
			
			Thread.sleep(6000);
			b2cPage.Override_RepID.clear();
			Thread.sleep(4000);
			b2cPage.Override_RepID.sendKeys(testData.B2C.getRepID());
			Thread.sleep(6000);
			
			//9, click  YES button
			Dailylog.logInfoDB(9, "click  YES button", Store, testName);
			
			b2cPage.Override_SubmitQuote.click();
			Thread.sleep(6000);
			String QuoteNo = b2cPage.Override_QuoteNumber.getText().toString();
			Dailylog.logInfo(Store + " quote number is " + QuoteNo);
			
			
			//10, Click *X* icon of *Sign Out* to sign  off ASM. 
			Dailylog.logInfoDB(10, "Click *X* icon of *Sign Out* to sign  off ASM. ", Store, testName);
			
			driver.findElement(By.xpath("//button[contains(@class,'ASM_close')]")).click();
			Thread.sleep(6000);
			//11,login website and start ASM.
			Dailylog.logInfoDB(11, "login website and start ASM.", Store, testName);
			
			driver.get(homePageUrl);
			B2CCommon.handleTeleGateKeeper(b2cPage, testData);
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			
			//12, Enter quote number into Transaction ID field in ASM function board. 
			//13, Pick up the desired quote record from this list. 
			//14, Click *Start Session* button.
			Dailylog.logInfoDB(12, "Enter quote number into Transaction ID field in ASM function board. ", Store, testName);
			Dailylog.logInfoDB(13, "Pick up the desired quote record from this list. ", Store, testName);
			Dailylog.logInfoDB(14, "Click *Start Session* button. ", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			B2CCommon.closeHomePagePopUP(driver);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			
			B2CCommon.closeHomePagePopUP(driver);
			
			b2cPage.TransactionID.click();
			b2cPage.TransactionID.sendKeys(QuoteNo);
			new WebDriverWait(driver, 500)
			.until(ExpectedConditions.presenceOfElementLocated(By
					.cssSelector("[id^='Q']>a")));
			b2cPage.ASM_QuoteResult.click();
			Thread.sleep(3000);
			b2cPage.ASM_StartSession.click();
			
			//15, Enter into the quote detail page, will see the profit value
			Dailylog.logInfoDB(15, "Enter into the quote detail page, will see the profit value", Store, testName);
			
			String profitValue = driver.findElement(By.xpath("//dt[@class='cart-item-pricing-and-quantity-finalPrice-amount']")).getText().trim();
			
			profitValue = profitValue.replace("(", "").replace(")", "").replace("HK", "").replaceAll("SG", "").replace("£", "")
					.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
					.replaceAll("₩", "").replace("$", "").trim();
			System.out.println("profitValue is :" + profitValue);
			String[] strs = profitValue.split(" ");
			
			String profit = strs[0];
			System.out.println("profit is :" + profit);
			
			
			
			float profitPrice = GetPriceValue(profit);
			System.out.println("profit price is :" + profitPrice);
			System.out.println("Price is :" + Price);
			
			//signout the ASM 
			
			b2cPage.ASM_signout.click();
			
			//16, Search cost on HMC
			//1.HMC>Price Settings>Price Cockpit>B2C Price Simulator
			//2.fill in test info and click debug button
			
			Dailylog.logInfoDB(16, "Search cost on HMC", Store, testName);
			
			
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/europe1novarto/index.jsp?lang')]")));
			
			hmcPage.PricingCockpit_B2CPriceSimulator.click();
			Thread.sleep(6000);
			fillB2CPriceSimulator();
			
			
			String costValue = driver.findElement(By.xpath("//td[@id='cost']/samp")).getText().trim();
			
			float costPrice = GetPriceValue(costValue);
			System.out.println("costPrice is :" + costPrice);
			
			
			//17,Search tax value on HMC
			//1.HMC --> B2C unit 
			//2.search by test unit
			//3.switch to site attribute
			//and find the tax value
			Dailylog.logInfoDB(17, "Search tax value on HMC", Store, testName);
			
			driver.switchTo().defaultContent();
			hmcPage.Home_PriceSettings.click();
			
			
			hmcPage.Home_baseCommerce.click();
			hmcPage.Home_baseStore.click();
			Common.sendFieldValue(hmcPage.baseStore_id, testData.B2C.getStore());
			hmcPage.baseStore_search.click();
			hmcPage.BaseStore_SearchResult.click();
			Dailylog.logInfoDB(10, "Net Value is "+hmcPage.basestore_NetValue.getAttribute("value").equals("true"), Store, testName);
			if(hmcPage.basestore_NetValue.getAttribute("value").equals("true")){
				float expectedPrice = Price - costPrice;
				
				expectedPrice   =  (float)(Math.round(expectedPrice*100))/100;
				expectedPrice = Math.abs(expectedPrice);
				
				System.out.println("profitPrice is :" + profitPrice);
				System.out.println("expectedPrice1 is :" + expectedPrice);
				Assert.assertEquals(expectedPrice, profitPrice, 0.01);
			}else{
				hmcPage.Home_B2CCommercelink.click();
				hmcPage.Home_B2CUnitLink.click();
				hmcPage.B2CUnit_IDTextBox.clear();
				hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
				
				hmcPage.B2CUnit_SearchButton.click();
				hmcPage.B2CUnit_FirstSearchResultItem.click();
				
				hmcPage.B2CUnit_SiteAttributeTab.click();
				Thread.sleep(60000);
				
				String taxValue = hmcPage.B2CUnit_TaxValue.getAttribute("value").toString();
				
				float tax = Float.parseFloat(taxValue);
				System.out.println("tax is :"+ tax);
				
				//18, Check the profit and sales price of the product in quote, 
				//validate the profit = sales price(the price should be the override price) - cost
				
				//profit = sales price/(100+tax value)% - cost
				Dailylog.logInfoDB(18, "Check the profit and sales price of the product in quote,validate the profit = sales price(the price should be the override price) - cost ", Store, testName);
				
				
				System.out.println("taxValue is :" + taxValue);
				System.out.println("tax is :" + tax);
				float rate = (100+tax)/100;
				System.out.println("Price is :" + Price);
				System.out.println("rate is :" + rate);
				System.out.println("costPrice is :" + costPrice);
				
				
				float expectedPrice = Price/rate - costPrice;
				//DecimalFormat df = new DecimalFormat();
				//String expectedPrice1 = df.format(expectedPrice);
				
				System.out.println("profitPrice is :" + profitPrice);
				System.out.println("expectedPrice1 is :" + expectedPrice);
				Assert.assertEquals(expectedPrice, profitPrice, 0.01);
				//Assert.assertTrue(expectedPrice1.equals(profitPrice+""));
			}	

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	
	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
				.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
				.replaceAll("₩", "");
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
	
	public void fillB2CPriceSimulator() throws InterruptedException{
		String categoryVersion = "Nemo Master Multi Country Product Catalog - Online";;
		driver.findElement(By.xpath("//div[contains(@id,'s2id_ps_country')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(B2CCommon.getCountry(Store));
		System.out.println(B2CCommon.getCountry(Store));
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//ul[@class='select2-results']/li/div)[last()]")).click();
		Thread.sleep(7000);
		driver.findElement(By.xpath("//div[contains(@id,'s2id_ps_store')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(testData.B2C.getStore());	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@class='select2-results']/li/div")).click();
		Thread.sleep(7000);
		driver.findElement(By.xpath("//div[contains(@id,'s2id_ps_catalog')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(categoryVersion);	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@class='select2-results']/li/div")).click();
		Thread.sleep(7000);
		String MTM = testData.B2C.getDefaultMTMPN();
		driver.findElement(By.xpath("//div[contains(@id,'s2id_ps_product')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='select2-drop']/div/input")).sendKeys(MTM);	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@class='select2-results']/li/div")).click();
		
		
		Thread.sleep(7000);
		driver.findElement(By.xpath("//input[@id='ps_date']")).clear();
		driver.findElement(By.xpath("//input[@id='ps_date']")).sendKeys(getDate());
		
	
		
//		driver.findElement(By.xpath("//input[@id='ps_button']")).click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.xpath("//input[@id='ps_button']")));
		
		Thread.sleep(6000);

	}
	
	public String getDate(){
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		String str = sdf.format(new Date());
		
		return str;	
	}

}
	
	