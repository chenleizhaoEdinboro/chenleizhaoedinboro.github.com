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

public class COMM37Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
	private String updateInvURL = "http://10.122.11.57:8003/";
	private String partSaleNUM = "00HM888";
	private String partSaleNUMB = "00KT110";
	private String stockNUM = "3";
	private String esupport_account = "lenovobptest@outlook.com";
	private String esupport_password = "bprole@lenovo";
	private boolean isDisplayed;

	public COMM37Test(String store){
		this.Store = store;
		this.testName = "COMM-37";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM37(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			
			//step~2:config HAC manually if necessary
		
			//step~3
			Dailylog.logInfoDB(3, "Go to PartSales website with a custumer account.", Store, testName);
			GoEsupport();
			addToCartEsupport(partSaleNUM);
			Common.waitElementVisible(driver, PSPage.viewMyCart);
			PSPage.viewMyCart.click();
			Common.waitElementVisible(driver, PSPage.partSales_LoginID);
			logonEsupport(PSPage);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open('" + updateInvURL + "');");
			Common.switchToWindow(driver, 1);
			
			//step~4
			Dailylog.logInfoDB(4, "set " + partSaleNUM + " inventory as A=4, update the quantity successfully", Store, testName);
			updateInv("4", partSaleNUM);
			Common.switchToWindow(driver, 0);
			checkUpdateQuatity("1");
			
			//step~5
			Dailylog.logInfoDB(5, "set " + partSaleNUM + " quality as A=5", Store, testName);
			checkUpdateQuatity("5");
			
			//step~6
			Dailylog.logInfoDB(6, "Click 'proceed to checkout'", Store, testName);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			//fill shipping
			Common.sleep(2000);
			if(b2cPage.Shipping_FirstNameTextBox.getText().isEmpty()){
				B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			}
			Actions act = new Actions(driver);
			act.sendKeys(Keys.PAGE_UP).perform();
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			//b2cPage.Shipping_ContinueButton.click();
			//fill payment info
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~7
			Dailylog.logInfoDB(7, "change " + partSaleNUM + " inventory as A=3, update the quantity successfully", Store, testName);
			Common.switchToWindow(driver, 1);
			updateInv("3", partSaleNUM);
			Common.switchToWindow(driver, 0);
			//Click "Place your order"
			Common.sleep(2000);
			//act.sendKeys(Keys.PAGE_DOWN).perform();
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(5000);
			//By quantity_Message3 = By.xpath("// div[contains(text(),'There are only 3 units of this part 00HM888 available')]");
			//isDisplayed = Common.checkElementDisplays(driver, quantity_Message3, 5);
			By quantity_Message3 = By.xpath("// div[contains(text(),'We are sorry, but this replacement part is now out of stock.')]");
			isDisplayed = Common.checkElementDisplays(driver, quantity_Message3, 5);
			if (!isDisplayed)
				Assert.fail("mesage is not displayed:There are only 3 units of this part" +  partSaleNUM + "available ");
			else{
				System.out.println("message is displayed:There are only 3 units of this part" +  partSaleNUM + "available");
			}
			
			//step~8
			//Dailylog.logInfoDB(8, "Click 'Place your order' again", Store, testName);
			//act.sendKeys(Keys.PAGE_DOWN).perform();
			//b2cPage.OrderSummary_PlaceOrderButton.click();
			//Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			//Common.sleep(2000);
			//Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			//B2CCommon.clickPlaceOrder(b2cPage);

			//step~9、10、11
			Dailylog.logInfoDB(9, "Check the inventory", Store, testName);
			Common.switchToWindow(driver, 1);
			String actual = searchInv(partSaleNUM);
			System.out.println(actual);
			Assert.assertEquals(actual, "0");
			//System.out.println("pass");
			//step~12,13,14
			Dailylog.logInfoDB(12, "change " + partSaleNUM + " inventory as 3", Store, testName);				
			driver.switchTo().alert().accept();
			updateInv(stockNUM, partSaleNUM);
			updateInv(stockNUM, partSaleNUMB);
			Common.switchToWindow(driver, 0);
			Dailylog.logInfoDB(13, "Go to PartSales website with a custumer account.", Store, testName);
			GoEsupport();
			addToCartEsupport(partSaleNUM);
			WebElement conShopping = driver.findElement(By.xpath("//*[contains(text(),'Continue Shopping')]"));
			Common.sleep(2000);
			Common.javascriptClick(driver, conShopping);
			Common.sleep(2000);
			addToCartEsupport(partSaleNUMB);
			Common.waitElementVisible(driver, PSPage.viewMyCart);
			PSPage.viewMyCart.click();
			//Common.waitElementVisible(driver, PSPage.partSales_LoginID);
			//PSPage.partSales_GuestCheckOut.click();
			
			//step~15
			Dailylog.logInfoDB(15, "Proceed to checkout and shipping and billing page", Store, testName);
			Common.sleep(2000);
			String quantityNum = b2cPage.cartPage_Quantity.getAttribute("value");
			if(!quantityNum.equals("1")){
				b2cPage.cartPage_Quantity.clear();
				b2cPage.cartPage_Quantity.sendKeys("1");
				Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
			}
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			//fill shipping
			Common.sleep(2000);
			if(b2cPage.Shipping_FirstNameTextBox.getText().isEmpty()){
				B2CCommon.fillShippingInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "lixe1@lenovo.com", testData.B2C.getConsumerTaxNumber());
			}
			//Actions act = new Actions(driver);
			act.sendKeys(Keys.PAGE_UP).perform();
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			
			//fill payment info
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "asd", "asd", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			b2cPage.Payment_ContinueButton.click();
			
			//step~16
			Dailylog.logInfoDB(16, "change " + partSaleNUMB + " inventory as 0", Store, testName);
			Common.switchToWindow(driver, 1);
			updateInv("0", partSaleNUMB);
			//Click "Place your order"
			Common.switchToWindow(driver, 0);
			Common.sleep(2000);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(2000);
			isDisplayed = Common.checkElementDisplays(driver, By.xpath("// div[contains(text(),'We are sorry, but this replacement part is now out of stock.')]"), 5);
			if (!isDisplayed)
				Assert.fail("mesage is not displayed:We are sorry, but this replacement part is now out of stock. ");
			else{
				System.out.println("message is displayed:We are sorry, but this replacement part is now out of stock.");
			}
			
			//step~17
			Dailylog.logInfoDB(17, "Click place your order again", Store, testName);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			B2CCommon.clickPlaceOrder(b2cPage);
			//b2cPage.OrderSummary_PlaceOrderButton.click();
			//step~18
			Dailylog.logInfoDB(18, "Check the inventory amount should reduce the corresponding quantity", Store, testName);
			Common.switchToWindow(driver, 1);
			String invertoryNum = searchInv(partSaleNUM);
			System.out.println(Integer.parseInt(invertoryNum));
			System.out.println(Integer.parseInt(stockNUM) - 1);
			Assert.assertEquals(Integer.parseInt(invertoryNum), Integer.parseInt(stockNUM) - 1 );


			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		

		
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
		//By quantity_Message5 = By.xpath("// div[contains(text(),'There are only 4 units of this part 00HM888 available')]");
		By quantity_Message5 = By.xpath("// div[contains(text(),'We are sorry, but this replacement part is now out of stock.')]");
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
