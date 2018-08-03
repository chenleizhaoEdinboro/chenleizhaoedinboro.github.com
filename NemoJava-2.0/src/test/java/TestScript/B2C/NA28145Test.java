package TestScript.B2C;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA28145Test extends SuperTestClass {
	
	B2CPage b2cPage;
	float Price;
	
	String partSalesUrl = "https://eservice:1qaz!QAZ@presupport.lenovo.com/au/en/partslookup";
	String partSales_partNumber = "00HM888";
	String customer = "2230496813@qq.com";

	public NA28145Test(String store) {
		this.Store = store;
		this.testName = "NA-28145";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA28145(ITestContext ctx) {
		try {
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			
			//1, Open a part sales site
			Dailylog.logInfoDB(1, " Open a part sales site", Store, testName);
			
			driver.get(partSalesUrl);
		
			switchToCorrespondingCountryAndLanguage();
			
			
	
			//2, Get a proper product, add it into cart without login 
			Dailylog.logInfoDB(2, "Get a proper product, add it into cart without login", Store, testName);
			B2CCommon.closeHomePagePopUP(driver);
			
			String currentUrl = driver.getCurrentUrl().toString().replace("?cl=1", "");
			driver.get(currentUrl);
			
			b2cPage.partNumQuery.clear();
			b2cPage.partNumQuery.sendKeys(partSales_partNumber);
//			driver.findElement(By.xpath("//*[@id='partNumberSearchbutton']")).click();
			Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='partNumberSearchbutton']")));
			
			Thread.sleep(3000);

			//3, After add product to cart ,click
			//*View my Cart* button in the pop up page
			Dailylog.logInfoDB(3, "After add product to cart ,click *View my Cart* button in the pop up page", Store, testName);
			
			driver.findElement(By.xpath("//div[@class='first-line']//label[contains(@class,'icon-cart')]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(".//*[@id='pcg_shop_addtocart']//button[contains(@title,'View')]")).click();
			Thread.sleep(4000);
			
			//4, Click *Continue to guest checkout* button
			Dailylog.logInfoDB(4, "Click *Continue to guest checkout* button", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='shoplogin']//span[contains(.,'Continue')]")).click();
			Thread.sleep(8000);
			
			
			//5,Page jumps to hybris website
			Dailylog.logInfoDB(5, "Page jumps to hybris website", Store, testName);
			
			Assert.assertTrue(driver.getCurrentUrl().contains("hybris"), "failed to jump to hybris");
			
			//6, Click account icon to login as telesales 
			Dailylog.logInfoDB(6, "Click account icon to login as telesales ", Store, testName);
			
			String hybrisUrl = driver.getCurrentUrl().toString();
			
			String loginUrl = hybrisUrl.split("cart")[0]+"login";
			Dailylog.logInfoDB(6, "loginUrl is :" + loginUrl, Store, testName);
			
			driver.get(loginUrl);
			
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			//7,Enter ASM, and start session using part customer ID 
			Dailylog.logInfoDB(7, "Enter ASM, and start session using part customer ID ", Store, testName);
			
			B2CCommon.loginASMAndStartSession(driver, b2cPage, "customer", customer);
			Thread.sleep(4000);
			
			//8, After start customer session, the page enters into my account page, and then click cart icon to enter into cart which keeps session: 
			Dailylog.logInfoDB(8, "After start customer session, the page enters into my account page, and then click cart icon to enter into cart which keeps session:", Store, testName);
			
			String cartUrl = hybrisUrl.split("cart")[0]+"cart";
			Dailylog.logInfoDB(8, "cartUrl is :" + cartUrl, Store, testName);
			
			driver.get(cartUrl);
			
			//9,Do price override 
			Dailylog.logInfoDB(9, "Do price override ", Store, testName);
			
			doPriceOverride();
			
			//10, Request quote, and go to Quote confirmation page ,Remember the quote number.
			Dailylog.logInfoDB(10, "Request quote, and go to Quote confirmation page ,Remember the quote number.", Store, testName);
			
			Thread.sleep(6000);
			String QuoteNo = b2cPage.Override_QuoteNumber.getText().toString();
			
			Dailylog.logInfoDB(10, "quote number is :" + QuoteNo, Store, testName);
			
			//11, Click *X* icon of *Sign Out* to sign  off ASM. 
			//12, Sign in with telesales account again, and start ASM
			//13, Enter quote number into Transaction ID field in ASM function board. 
			//14, Pick up the desired quote record from this list. 
			//15, Click *Start Session* button.. 
			Dailylog.logInfoDB(11, "Click *X* icon of *Sign Out* to sign  off ASM. ", Store, testName);
			Dailylog.logInfoDB(12, "Sign in with telesales account again, and start ASM", Store, testName);
			Dailylog.logInfoDB(13, "Enter quote number into Transaction ID field in ASM function board. ", Store, testName);
			Dailylog.logInfoDB(14, "Pick up the desired quote record from this list. ", Store, testName);
			Dailylog.logInfoDB(15, "Click *Start Session* button.", Store, testName);
			
			b2cPage.SignoutASM.click();
			
//			driver.get(testData.B2C.getloginPageUrl());
			driver.get(testData.B2C.getTeleSalesUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			Thread.sleep(5000);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			
			switchStoreType();		
			
			b2cPage.TransactionID.sendKeys(QuoteNo);
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("[id^='Q']>a")));
			b2cPage.ASM_QuoteResult.click();
			Thread.sleep(3000);
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);
			
			//16, Click *Approve/Reject* button
			Dailylog.logInfoDB(16, "Click *Approve/Reject* button", Store, testName);
			
			b2cPage.ASM_ApproveButton.click();
			Thread.sleep(5000);
			
			//17, Enter comment and click *Approve* button 
			Dailylog.logInfoDB(17, "Enter comment and click *Approve* button ", Store, testName);
			
			b2cPage.ASM_ApproveComment.sendKeys("approve");
			Thread.sleep(2000);
			b2cPage.ASM_PopupApprove.click();
			Thread.sleep(5000);
			
			//18, Click *Convert To Order* button.
			Dailylog.logInfoDB(18, "Click *Convert To Order* button.", Store, testName);
			
			b2cPage.ASM_ConvertToOrder.click();
			Thread.sleep(8000);
			
			Assert.assertTrue(driver.getCurrentUrl().contains("address"), "after click convert to order , page does not redirected to shipping page");

			//19,  Login website with telesales and start ASM.
			Dailylog.logInfoDB(19, "Login website with telesales and start ASM.", Store, testName);
			
			b2cPage.SignoutASM.click();
			
			driver.get(testData.B2C.getTeleSalesUrl()+"/login");
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			
			//20, In ASM ,click Store type choose SERVICE_CUSTOMER
			//and in Store ID input the part sales id
			Dailylog.logInfoDB(20, "In ASM ,click Store type choose SERVICE_CUSTOMER and in Store ID input the part sales id", Store, testName);
			
			switchStoreType();
			
			//21, Start session using part customer ID 
			Dailylog.logInfoDB(21, "Start session using part customer ID ", Store, testName);
			
			new WebDriverWait(driver, 500)
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.clear();
			b2cPage.ASM_SearchCustomer.sendKeys(customer);
			new WebDriverWait(driver, 500)
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			Thread.sleep(3000);
			
			b2cPage.Tele_StartSession.click();
			
			
			//22, Open parts site in another browser tag and add one product into cart, 
			//and then view cart, the page jumps back to Hybris page: 
			Dailylog.logInfoDB(22, "Open parts site in another browser tag and add one product into cart, and then view cart, the page jumps back to Hybris page", Store, testName);
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('"+ partSalesUrl + "')");
			
			Thread.sleep(3000);
			
			Common.switchToWindow(driver, 1);
			
			Thread.sleep(3000);

			switchToCorrespondingCountryAndLanguage();
			
			B2CCommon.closeHomePagePopUP(driver);
			
			String currentUrl_1 = driver.getCurrentUrl().toString().replace("?cl=1", "");
			driver.get(currentUrl_1);
			
			b2cPage.partNumQuery.clear();
			b2cPage.partNumQuery.sendKeys(partSales_partNumber);
			Common.javascriptClick(driver, driver.findElement(By.xpath("//*[@id='partNumberSearchbutton']")));
			
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@class='first-line']//label[contains(@class,'icon-cart')]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(".//*[@id='pcg_shop_addtocart']//button[contains(@title,'View')]")).click();
			Thread.sleep(4000);
			
			driver.findElement(By.xpath(".//*[@id='shoplogin']//span[contains(.,'Continue')]")).click();
			Thread.sleep(8000);
			
			Assert.assertTrue(driver.getCurrentUrl().contains("hybris"), "failed to jump to hybris");
			
			String cartID = "";
			if(Common.isElementExist(driver, By.xpath("//*[@id='confignumber']/div/span[@class='show_cartid']"))){
				driver.findElement(By.xpath("//*[@id='confignumber']/div/span[@class='show_cartid']")).click();
				Thread.sleep(3000);
				cartID = driver.findElement(By.xpath(".//*[@id='confignumber']/div/span/span[@class='cart_id']")).getText();
			}
			
			Assert.assertTrue(!cartID.equals(""), "failed to get cartID");
			
			//23, Go to ASM, 
			//	click 'Copy Tracsaction',
			//	choose the 'Transaction Type' and 'Transaction  ID',
			//	click 'Copy it',
			
			String partSalesWindow = driver.getWindowHandle();
			System.out.println("partSalesWindow is :" + partSalesWindow);
			
			Set<String> set = driver.getWindowHandles();
			
			for(String str : set){
				if(str.equals(partSalesWindow))
					continue;
				driver.switchTo().window(str);
				
				break;
			}
			
			String switchWindow = driver.getWindowHandle();
			System.out.println("switchWindow is :" + switchWindow);
			
			b2cPage.assistedServiceMode_copyTransaction.click();
			Thread.sleep(6000);
			b2cPage.assistedServiceMode_transactionID.clear();
			b2cPage.assistedServiceMode_transactionID.sendKeys(cartID);
			
			b2cPage.assistedServiceMode_copyIt.click();
			
			//24, In this case, the price in cart cannot be overridden, so tele can only drop order directly 
			Dailylog.logInfoDB(24, "In this case, the price in cart cannot be overridden, so tele can only drop order directly ", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='editCartButton']")).click();	
			Thread.sleep(6000);
			Assert.assertTrue(!Common.isElementExist(driver, By.xpath("//input[contains(@name,'price')]")), "price override input existing on the web");
	
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
	}
	
	public void switchStoreType() throws Exception{
		
		Select select = new Select(driver.findElement(By.xpath("//select[@name='storeType']")));
		select.selectByValue("SERVICE_CUSTOMER");
		
		Thread.sleep(8000);
		
		driver.findElement(By.xpath("//input[@name='storeId']")).clear();
		
		if(Store.equals("AU")){
			driver.findElement(By.xpath("//input[@name='storeId']")).sendKeys("aup");
		}else if(Store.equals("US")){
			driver.findElement(By.xpath("//input[@name='storeId']")).sendKeys("usp");
		}
		Thread.sleep(5000);
		
		driver.findElement(By.xpath(".//*[contains(@id,'partsales')]/a/span[contains(.,'Part')]")).click();
		Thread.sleep(5000);
		
	}
	
	public void switchToCorrespondingCountryAndLanguage() throws Exception{
		
		Thread.sleep(6000);
		
		if(!driver.getCurrentUrl().split("/")[4].equals("en")){
			driver.findElement(By.xpath("//div[@id='header-desktop']//p[@class='selected-language']//span[@class='icon-l-down']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(".//*[@id='header-desktop']//a[@data-code='en']")).click();
			Thread.sleep(3000);
		}
		
		Assert.assertTrue(driver.getCurrentUrl().split("/")[4].equals("en"), "failed to switch to english language");
		
		if(Store.equals("AU")){
			if(!driver.getCurrentUrl().split("/")[3].equals("au")){
				driver.findElement(By.xpath("//div[@id='header-desktop']//p[@class='selected-country']//span[@class='icon-l-down']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath(".//*[@id='header-desktop']//a[@data-code='au']")).click();
				Thread.sleep(3000);
			}
			
			Assert.assertTrue(driver.getCurrentUrl().split("/")[3].equals("au"), "failed to switch to Australia");
		}
		
		if(Store.equals("US")){
			if(!driver.getCurrentUrl().split("/")[3].equals("us")){
				driver.findElement(By.xpath("//div[@id='header-desktop']//p[@class='selected-country']//span[@class='icon-l-down']")).click();
				Thread.sleep(3000);
				Common.javascriptClick(driver, driver.findElement(By.xpath(".//*[@id='header-desktop']//a[@data-code='us']")));
				Thread.sleep(3000);
			}
			
			Assert.assertTrue(driver.getCurrentUrl().split("/")[3].equals("us"), "failed to switch to America");
		}
		
	}
	
	
	
	
	
	public void doPriceOverride() throws Exception{
		Thread.sleep(4000);
		if (Common.isElementExist(b2cPage.PageDriver,
				By.xpath("//button[contains(@class,'Submit_Button')]"))) {
			Price = GetPriceValue(b2cPage.NewCart_SalesPrice.getText()
					.toString().replaceAll("HK", "").replaceAll("SG", "")
					.replace("£", "").replace("€", "").replaceAll("￥", "")
					.replaceAll("NT", "").replaceAll("₩", ""));
			Price = Price - 1;
			b2cPage.OverrideValue.sendKeys((int) (Price) + "");
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.NewCart_UpdatePrice.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			b2cPage.Override_RequestQuote.click();
			Thread.sleep(6000);
			b2cPage.Override_RepID.clear();
			Thread.sleep(4000);
			b2cPage.Override_RepID.sendKeys("1234567890");
			Thread.sleep(6000);
			b2cPage.Override_SubmitQuote.click();

		} else {
			Price = GetPriceValue(driver.findElement(By.xpath("//dd[@class='cartDetails-tsPrice']")).getText().toString().trim()
					.replaceAll("HK", "").replaceAll("SG", "")
					.replace("£", "").replace("€", "").replaceAll("￥", "")
					.replaceAll("NT", "").replaceAll("₩", ""));
			Price = Price - 1;
			b2cPage.OverrideValue.sendKeys((int) (Price) + "");
			b2cPage.OverrideDropdown.click();
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			System.out.println("Override performs successfully");
			Thread.sleep(5000);
			b2cPage.Override_RequestQuote.click();
			Thread.sleep(6000);
			b2cPage.Override_RepID.clear();
			Thread.sleep(4000);
			b2cPage.Override_RepID.sendKeys("1234567890");
			Thread.sleep(6000);
			b2cPage.Override_SubmitQuote.click();
		}

	}
	
	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}
	
}
