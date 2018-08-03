package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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



public class NA21866Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	
	public String processNode;
	public String partNumber;
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	
	
	public NA21866Test(String Store, String processNode){
		this.Store = Store;
		this.processNode = processNode;
		this.testName = "NA-21866";
	}
	
	
	@Test
	public void NA21866(ITestContext ctx){
		
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
			
			//1, login hmc
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			//2, Base Commerce-> Base store -> search url's store ->double click result
			//under "Locations" tab
			//Check "Default Delivery Origin" value.
			hmcPage.Home_baseCommerce.click();
			hmcPage.HMCBaseCommerce_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			
			hmcPage.baseStore_search.click();
			Actions actions = new Actions(driver);
			actions.doubleClick(hmcPage.BaseStore_SearchResult);
			
			String defaultDeliveryOrigin = driver.findElement(By.xpath("//div[contains(@id,'BaseStore.defaultDeliveryOrigin')]")).getText().toString();
			Dailylog.logInfoDB(2, "defaultDeliveryOrigin is :" + defaultDeliveryOrigin, Store, testName);
			
			//3, Click "Administration" 
			//change the "Quote Expired Time WithoutStock" and set "is Quote Available" Yes
			//Save
			hmcPage.baseStore_administration.click();
			if(!driver.findElement(By.xpath("//input[contains(@id,'BaseStore.isQuoteAvailable') and contains(@id,'true')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'BaseStore.isQuoteAvailable') and contains(@id,'true')]")).click();
			}
			
			driver.findElement(By.xpath("//input[contains(@id,'BaseStore.quoteExpiredTimeWithoutStock')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'BaseStore.quoteExpiredTimeWithoutStock')]")).sendKeys("30");
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			String withoutStockTime = driver.findElement(By.xpath("//input[contains(@id,'BaseStore.quoteExpiredTimeWithoutStock')]")).getAttribute("value").toString();
			
			Assert.assertTrue(withoutStockTime.equals("30"));
			
			
			//4, Base Commerce -->Stock Level, right click and "create stock level". 
			//Input the Part No. , select warehouse as step2's result and 
			//set as ForceInStock
			//Click create button
			partNumber = testData.B2C.getDefaultMTMPN();
						
			hmcPage.BaseCommerce_StockLevel.click();
			hmcPage.StockLevel_PartNoTextBox.clear();
			hmcPage.StockLevel_PartNoTextBox.sendKeys(partNumber);
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			Thread.sleep(6000);
			
			while(Common.isElementExist(driver, By.xpath("//tr[contains(@id,'OrganizerListEntry')]//div[@class='olecEntry']"))){
				Common.rightClick(driver, driver.findElement(By.xpath("(//tr[contains(@id,'OrganizerListEntry')]//div[@class='olecEntry'])[1]")));
				Thread.sleep(2000);
				driver.findElement(By.xpath("//td[contains(@id,'StockLevel') and contains(@id,'remove_true_label')]")).click();
				Thread.sleep(3000);
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}

			hmcPage.BaseCommerce_StockLevel.click();
			Common.rightClick(driver, hmcPage.BaseCommerce_StockLevel);
			hmcPage.BaseCommerce_CreateStockLevel.click();

			hmcPage.StockLevel_PartNoTextBox.clear();
			hmcPage.StockLevel_PartNoTextBox.sendKeys(partNumber);
			
			driver.findElement(By.xpath("//img[contains(@id,'warehouse')]")).click();
			Common.switchToWindow(driver, 1);
			
			driver.findElement(By.xpath("//input[contains(@id,'Warehouse.code')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'Warehouse.code')]")).sendKeys(defaultDeliveryOrigin);
			
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("(//div[@class='gilcEntry']/div)[1]")).click();
			
			driver.findElement(By.xpath("//a[@title='Use']/span")).click();
			
			Select select = new Select(driver.findElement(By.xpath("//select[contains(@id,'inStockStatus')]")));
			select.selectByValue("0");
			
			driver.findElement(By.xpath("//div[contains(@id,'saveandcreate')]")).click();
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			hmcPage.Home_EndSessionLink.click();
			
			//5, Login B2C site 
			driver.get(ordinary_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			
			//6, Choose one product , add to cart,
			//click “REQUEST QUOTE" button on shopping cart page,click "Yes" button on the pop up page.
			
			driver.get(ordinary_cartUrl);
			
			B2CCommon.emptyCart(driver, b2cPage);
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(partNumber);
			b2cPage.Cart_AddButton.click();
			
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//div[@data-p-code='" + partNumber + "']")) && driver.findElement(By.xpath("//div[@data-p-code='" + partNumber + "']")).isDisplayed());

			String quoteNumber = getQuoteNumber();
			Dailylog.logInfoDB(6, "quote number of ordinary user is :" + quoteNumber, Store, testName);
			
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")));
			
			// get the quote status 
			Thread.sleep(4000);
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(quoteNumber);
			
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();
			
			hmcPage.Order_ordersAdministration.click();
			
			String quoteInitialStatus = Common.javaScriptGetText(driver, driver.findElement(By.xpath("//select[contains(@id,'Quote.quoteStatus')]/option[@selected]"))).trim();
			Assert.assertTrue(quoteInitialStatus.equals("Approved"));
			
			Thread.sleep(30000);
			// run the cron job
			runCronJob();
			
			// get the quote status again;
			hmcPage.Home_EndSessionLink.click();
			
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(quoteNumber);
			
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();
			
			hmcPage.Order_ordersAdministration.click();
			
			String quoteChangedStatus = Common.javaScriptGetText(driver, driver.findElement(By.xpath("//select[contains(@id,'Quote.quoteStatus')]/option[@selected]"))).trim();
			Assert.assertTrue(quoteInitialStatus.equals("Expired"));
			
			hmcPage.Home_EndSessionLink.click();

			//7, login in HMC again 
			Thread.sleep(4000);
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			//8, Base Commerce-> Base store -> search url's store ->double click result
			hmcPage.Home_baseCommerce.click();
			hmcPage.HMCBaseCommerce_baseStore.click();
			hmcPage.baseStore_id.clear();
			hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
			
			hmcPage.baseStore_search.click();
			Actions actions1 = new Actions(driver);
			actions1.doubleClick(hmcPage.BaseStore_SearchResult);
			
			//9, Click "Administration" 
			//change the "ts Quote Expired Time WithStock" and set "is Quote Available" Yes
			//Save
			
			hmcPage.baseStore_administration.click();
			if(!driver.findElement(By.xpath("//input[contains(@id,'BaseStore.isQuoteAvailable') and contains(@id,'true')]")).isSelected()){
				driver.findElement(By.xpath("//input[contains(@id,'BaseStore.isQuoteAvailable') and contains(@id,'true')]")).click();
			}
			
			driver.findElement(By.xpath("//input[contains(@id,'BaseStore.tsQuoteExpiredTimeWithStock')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'BaseStore.tsQuoteExpiredTimeWithStock')]")).sendKeys("30");
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			
			
			//10, Base Commerce -->Stock Level, right click and "create stock level". 

			//Input the Part No. , select warehouse as step2's result and 
			//set the stock status as notSpecified
			//Click create button
			hmcPage.Home_baseCommerce.click();
			
			hmcPage.BaseCommerce_StockLevel.click();
			hmcPage.StockLevel_PartNoTextBox.clear();
			hmcPage.StockLevel_PartNoTextBox.sendKeys(partNumber);
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			Thread.sleep(6000);
			
			driver.findElement(By.xpath("//img[contains(@id,'OrganizerListEntry')]")).click();
			
			Select select1 = new Select(driver.findElement(By.xpath("//select[contains(@id,'inStockStatus')]")));
			select1.selectByValue("2");
			
			driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
			
			
			//11, use Telesales account lgoin B2C site 
			hmcPage.Home_EndSessionLink.click();
			
			driver.get(tele_loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			
			//12, Go to My Account page and click on 'Start Assisted Service Session' link
			//13, On assisted Service Mode ,fill in Customer ID use ordinary user
			//14, Click "Start Session"
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			startASMSession();
			
			//15, Choose one product , add to cart,
			// click “REQUEST QUOTE" button on shopping cart page,click "Yes" button on the pop up page.
			Thread.sleep(4000);
			driver.get(tele_cartUrl);
			
			B2CCommon.emptyCart(driver, b2cPage);
			
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(partNumber);
			b2cPage.Cart_AddButton.click();
			
			Thread.sleep(4000);
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[text()='" + partNumber + "']")) && driver.findElement(By.xpath("//*[text()='" + partNumber + "']")).isDisplayed());
					
			String quoteNumber1 = getQuoteNumber();
			Dailylog.logInfoDB(6, "quote number of ordinary user is :" + quoteNumber1, Store, testName);
			
			JavascriptExecutor js2 = (JavascriptExecutor)driver;
			js2.executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")));
			
			//16, Login HMC 
			//Order->Orders->Search order Number use step6's "Quote Number"->double click result

			driver.get(hmcHomePageUrl);
			
			String Tele_quoteInitialStatus = getQuoteStatus(quoteNumber1);
			
			Assert.assertTrue(Tele_quoteInitialStatus.equals("Approved"));
			
			Thread.sleep(30000);
			
			// run the cron job
			runCronJob();
			
			// get the quote status again;
			hmcPage.Home_EndSessionLink.click();
			
			String Tele_quoteChangedStatus = getQuoteStatus(quoteNumber1);
			Assert.assertTrue(Tele_quoteChangedStatus.equals("Expired"));
	
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}

	}
	
	
	public void runCronJob() throws InterruptedException{
		hmcPage.Home_System.click();
		hmcPage.System_CronJobs.click();
		
		driver.findElement(By.xpath("//input[contains(@id,'CronJob.code')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'CronJob.code')]")).sendKeys("nemoRemoveExpiredQuoteCronJob");
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//span[contains(@id,'processas')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'nodeID')]")).clear();
		driver.findElement(By.xpath("//input[contains(@id,'nodeID')]")).sendKeys(processNode);
		driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
		
		driver.findElement(By.xpath("//div[contains(@id,'performcronjob')]")).click();
		
		Thread.sleep(5000);
		
		Common.switchToWindow(driver, 1);
		driver.findElement(By.xpath("//a[contains(@title,'Close')]/span")).click();
	}
	
	public String getQuoteStatus(String quoteNumber) throws InterruptedException{
		
		HMCCommon.Login(hmcPage, testData);
		
		hmcPage.Home_Order.click();
		hmcPage.Home_Order_Orders.click();
		hmcPage.Home_Order_OrderID.clear();
		hmcPage.Home_Order_OrderID.sendKeys(quoteNumber);
		
		driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();
		
		hmcPage.Order_ordersAdministration.click();
		
		String status = Common.javaScriptGetText(driver, driver.findElement(By.xpath("//select[contains(@id,'Quote.quoteStatus')]/option[@selected]"))).trim();
		
		return status;
	}
	
	
	public String getQuoteNumber(){
		
		b2cPage.Quote_requestBtn.click();
		b2cPage.Quote_RepIDTextBox.clear();
		b2cPage.Quote_RepIDTextBox.sendKeys(testData.B2C.getRepID());
		
		b2cPage.Quote_submitQuoteBtn.click();
		
		String quoteNumber1 = b2cPage.Quote_quoteNum.getText().toString();

		return quoteNumber1;
	}
	
	public void startASMSession(){
		new WebDriverWait(driver, 500).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath(".//*[@id='customerFilter']")));
		b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
		new WebDriverWait(driver, 500).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector("[id^='ui-id-']>a")));
		b2cPage.ASM_CustomerResult.click();
		b2cPage.ASM_StartSession.click();
	}
	
	
	
	
	
	
	
}
