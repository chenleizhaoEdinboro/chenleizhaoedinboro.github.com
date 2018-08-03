
package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class COMM44Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
	private String updateInvURL = "http://10.122.11.57:8003/";
	private String partSaleNUM = "00HM888";
	private String stockNUM = "4";
	private String esupport_account = "lenovobptest@outlook.com";
	private String esupport_password = "bprole@lenovo";
	private boolean isDisplayed;

	public COMM44Test(String store){
		this.Store = store;
		this.testName = "COMM-44";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM44(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			
			//step~1:config HAC manually if necessary
		
			//step~2
			Dailylog.logInfoDB(2, "Go to PartSales website with a custumer account.", Store, testName);
			GoEsupport();
			addToCartEsupport(partSaleNUM);
			Common.waitElementVisible(driver, PSPage.viewMyCart);
			PSPage.viewMyCart.click();
			Common.waitElementVisible(driver, PSPage.partSales_LoginID);
			logonEsupport(PSPage);
			
			//step~3
			Dailylog.logInfoDB(3, "Click 'Request Quote'", Store, testName);
			Common.sleep(3000);
			b2cPage.Quote_requestBtn.click();
			
			//step~4
			Dailylog.logInfoDB(4, "Click 'YES'", Store, testName);
			Common.sleep(2000);
			b2cPage.Override_SubmitQuote.click();
			Common.sleep(3000);
			String quoteNum = b2cPage.Quote_quoteNum.getText();
			
			//step~5
			Dailylog.logInfoDB(5, "Click My Account-> View my saved quote", Store, testName);
			Common.javascriptClick(driver, b2cPage.MyAccount_myAccount);
			
			//click View my saved quote
			Common.sleep(2000);
			if(Common.isElementExist(driver,By.linkText("View my saved quote"))){
				driver.findElement(By.linkText("View my saved quote")).click();
			}else{
				Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl()+"/my-account/quotes");			
			}
			
			//step~6
			Dailylog.logInfoDB(6, "Click Edit Quote button.", Store, testName);
			searchOpenQuoto(quoteNum);
			b2cPage.Quote_editButton.click();
			
			//step~7
			Dailylog.logInfoDB(7, "set inventory amount as" + stockNUM, Store, testName);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open('" + updateInvURL + "');");
			Common.switchToWindow(driver, 1);
			updateInv(stockNUM, partSaleNUM);
			Common.switchToWindow(driver, 0);
			checkUpdateQuatity("1");
			
			//step~8
			//Dailylog.logInfoDB(8, "Change and update the quantity to a number as 5" + stockNUM, Store, testName);
			//checkUpdateQuatity("5");
			Dailylog.logInfoDB(8, "set " + partSaleNUM + " quality as A=5", Store, testName);
			checkUpdateQuatity("5");
			//System.out.println(partSaleNUM);
			Common.sleep(2000);
			//step~9
			Dailylog.logInfoDB(9, "Click 'Request Quote'", Store, testName);
			//Dailylog.logInfoDB(9, "Click Save Quote button." + stockNUM, Store, testName);
			b2cPage.Quote_requestBtn.click();
			Common.sleep(2000);
			b2cPage.Override_SubmitQuote.click();
			
			//step~10
			Dailylog.logInfoDB(10, "Click Save Quote button." + stockNUM, Store, testName);
			Common.javascriptClick(driver, b2cPage.MyAccount_myAccount);
			
			//click View my saved quote
			Common.sleep(2000);
			if(Common.isElementExist(driver,By.linkText("View my saved quote"))){
				driver.findElement(By.linkText("View my saved quote")).click();
			}else{
				Common.NavigateToUrl(driver,Browser,testData.B2C.getHomePageUrl()+"/my-account/quotes");			
			}
			
			searchOpenQuoto(quoteNum);
			b2cPage.Quote_convertOrder.click();
			
			//step~11
			Dailylog.logInfoDB(11, "proceed to Review & Purchase page" + stockNUM, Store, testName);
			Actions act = new Actions(driver);
			act.sendKeys(Keys.PAGE_UP).perform();
			if(b2cPage.Shipping_FirstNameTextBox.getText().isEmpty()){
				B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			}
			b2cPage.Shipping_ContinueButton.click();
			B2CCommon.handleAddressVerify(driver, b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~12,13
			Dailylog.logInfoDB(12, "set inventory amount as 2", Store, testName);
			Common.switchToWindow(driver, 1);
			updateInv("3", partSaleNUM);
			Common.switchToWindow(driver, 0);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			By quantity_Message3 = By.xpath("// div[contains(text(),'There are only 3 units of this part number available')]");
			isDisplayed = Common.checkElementDisplays(driver, quantity_Message3, 5);
			if (!isDisplayed)
				Assert.fail("mesage is not displayed:There are only 3 units of this part" +  partSaleNUM + "available ");
			else{
				System.out.println("message is displayed:There are only 3 units of this part" +  partSaleNUM + "available");
			}

			//step~14
			Dailylog.logInfoDB(14, "Click 'Place your order'", Store, testName);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			
			//step~15
			Dailylog.logInfoDB(15, "Check the inventory amount should reduce the corresponding quantity", Store, testName);
			Common.switchToWindow(driver, 1);
			String invertoryNum = searchInv(partSaleNUM);
			System.out.println(invertoryNum);
			Assert.assertEquals(invertoryNum.trim(), "0" );
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		

		
	}
	
	public void searchOpenQuoto(String quoteNum){
		b2cPage.Quote_searchTextBox.clear();
		b2cPage.Quote_searchTextBox.sendKeys(quoteNum);
		Common.sleep(3000);
		b2cPage.Quote_searchSubmitBtn.click();
		driver.findElement(By.xpath("//*[@id='accountQuote-content']/div[3]/table/tbody/tr[1]/td[9]/p/a")).click();
	}
	
	public String searchInv(String partSaleNUM){
		Common.sleep(3000);
		WebElement eleSel = driver.findElement(By.xpath("//*[@id='type']"));
		Select sel = new Select(eleSel);
		sel.selectByVisibleText("Search");
		driver.findElement(By.id("partid")).clear();
		driver.findElement(By.id("partid")).sendKeys(partSaleNUM);
		driver.findElement(By.id("country")).clear();
		driver.findElement(By.id("country")).sendKeys(this.Store);
		driver.findElement(By.id("searchBtn")).click();
		Common.sleep(5000);
		String stockMess = driver.switchTo().alert().getText();
		char numStock = stockMess.charAt(stockMess.length() - 1);
		return String.valueOf(numStock);
	}
	
	public void checkUpdateQuatity(String cartQuantity){
		b2cPage.cartPage_Quantity.clear();
		b2cPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		Common.sleep(3000);
		By quantity_Message1 = By.xpath("// div[contains(text(),'Product quantity has been updated.')]");
		By quantity_Message5 = By.xpath("// div[contains(text(),'There are only 4 units of this part number available.')]");
		
		switch(cartQuantity){
			case "1":
				if(!Common.checkElementDisplays(driver, quantity_Message1, 5)){
					b2cPage.cartPage_Quantity.clear();
					b2cPage.cartPage_Quantity.sendKeys("2");
					Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
				}
				Common.sleep(2000);
				isDisplayed = Common.checkElementDisplays(driver, quantity_Message1, 5);
				if (!isDisplayed)
					Assert.fail("mesage is not displayed:Product quantity has been updated. ");
				else{
					System.out.println("message is displayed:Product quantity has been updated.");
				}
				break;
			
			case "5":
				Common.sleep(2000);
				isDisplayed = Common.checkElementDisplays(driver, quantity_Message5, 5);
				if (!isDisplayed)
					Assert.fail("mesage is not displayed:There are only 4 units of this part" +  partSaleNUM + "available ");
				else{
					System.out.println("message is displayed:There are only 4 units of this part" +  partSaleNUM + "available");
				}
				break;
		}
		
	}
	
	
	public void logonEsupport(PartSalesPage PSPage){
		PSPage.partSales_LoginID.clear();
		PSPage.partSales_LoginID.sendKeys(esupport_account);
		PSPage.partSales_Password.clear();
		PSPage.partSales_Password.sendKeys(esupport_password);
		PSPage.partSales_Signin.click();
	}
	
	public void closePromotion(WebDriver driver, PartSalesPage PSPage) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Promotion)).click().perform();
		}
	}
	
	public void addToCartEsupport(String partSaleNUM){
		closePromotion(driver,PSPage);
		PSPage.partNumber.clear();
		PSPage.partNumber.sendKeys(partSaleNUM);
		Common.sleep(2000);
		Common.javascriptClick(driver, PSPage.partLookUp);
		Common.sleep(3000);
		Common.mouseHover(driver, PSPage.addToCartNew);
		Common.javascriptClick(driver, PSPage.addToCartNew);
	}
	
	public void GoEsupport(){
		//https://eservice:1qaz!QAZ@presupport.lenovo.com/us/en/partslookup
		Common.NavigateToUrl(driver, Browser, testData.B2C.getPartSalesUrl());
		Common.sleep(2000);
		closePromotion(driver,PSPage);

		//select language
		Common.waitElementClickable(driver, PSPage.partSales_SelectLanguage, 6);
		PSPage.partSales_SelectLanguage.click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//div[@class='language-list']/ul/li/a[text()='English']")).click();
		
		//select country
		PSPage.partSales_SelectCountry.click();
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")), 15);
		driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")).click();
		driver.get(testData.B2C.getPartSalesUrl());
		Common.sleep(3000);
	}

	private void updateInv(String amountA, String partSaleNUM){
		Common.sleep(3000);
		WebElement eleSel = driver.findElement(By.xpath("//*[@id='type']"));
		Select sel = new Select(eleSel);
		sel.selectByVisibleText("Update");
		driver.findElement(By.id("partid")).clear();
		driver.findElement(By.id("partid")).sendKeys(partSaleNUM);
		driver.findElement(By.id("country")).clear();
		driver.findElement(By.id("country")).sendKeys(this.Store);
		driver.findElement(By.id("quantity")).clear();
		driver.findElement(By.id("quantity")).sendKeys(amountA);
		driver.findElement(By.id("updateBtn")).click();
		Common.sleep(5000);
		driver.switchTo().alert().accept();

	}
}
