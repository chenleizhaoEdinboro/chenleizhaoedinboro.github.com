package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;

public class COMM167Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	String SubSeries;
	PartSalesPage PSPage;
	private String subproNUM = "RR00000003";
	private boolean isDisplayed;
	private String WarehouseData = "";
	private String SMBUser = "zhengpx1@lenovo.com";
	private String lineItemQuantity = "5";
	

	public COMM167Test(String store){
		this.Store = store;
		this.testName = "COMM-167";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void COMM167(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			PSPage = new PartSalesPage(driver);
			Actions act = new Actions(driver);

			//step~1:Open web site
			Dailylog.logInfoDB(1, "Go to website", Store, testName);
			driver.get(testData.B2C.getHomePageUrl());
			//Login
			B2CCommon.login(b2cPage, SMBUser, testData.B2C.getLoginPassword());

			//step~2:Go to cart page
			Dailylog.logInfoDB(2, "Go to cart page", Store, testName);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
		
			//step~3
			Dailylog.logInfoDB(3, "Quick order a subscription Product.", Store, testName);
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			addPartNumberToCart(b2cPage, subproNUM);
			
			//step~4
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.open()");
			Common.switchToWindow(driver, 1);
			changeLineItemQuantity(lineItemQuantity);
			
			//step~5
			Dailylog.logInfoDB(5, "Configure stock of the subscription product on HMC,set stock as 4", Store, testName);
			
			hmcPage.HMCBaseCommerce_baseCommerce.click();
			Common.sleep(5000);
			WarehouseData = getwareHouseData();
			hmcPage.HMCBaseCommerce_stockLevel.click();
			hmcPage.StockLevel_PartNoTextBox.clear();
			hmcPage.StockLevel_PartNoTextBox.sendKeys(subproNUM);
			hmcPage.stockLevel_search.click();
			By isOrderExist = By.xpath("//div/div[contains(text(),'" + subproNUM + "')]");
			if(!Common.checkElementDisplays(driver, isOrderExist, 5)){
				Common.rightClick(driver, hmcPage.HMCBaseCommerce_stockLevel);
				Common.javascriptClick(driver, hmcPage.BaseCommerce_CreateStockLevel);
				Common.sendFieldValue(hmcPage.StockLevel_ProductCode, subproNUM);
				hmcPage.StockLevel_WarehouseIcon.click();
				Common.switchToWindow(driver, 2);
				Common.sendFieldValue(hmcPage.WarehouseIcon_Warehousecode, WarehouseData);
				Thread.sleep(5000);
				driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
				driver.findElement(By.xpath("//div/div[contains(@id,'" + WarehouseData + "')]")).click();
				driver.findElement(By.id("ModalGenericItemSearchList[Warehouse]_use")).click();
				Common.switchToWindow(driver, 1);
				Common.sleep(3000);
				createwareHouse("4");

			}else{
				Common.doubleClick(driver, driver.findElement(isOrderExist));
				Common.sleep(3000);
				updatewareHouse("4");
			}
				
			//step~6,7
			Dailylog.logInfoDB(7, "In Cart page, update quantity to 3", Store, testName);
			Common.switchToWindow(driver, 0);
			checkUpdateQuatity("3");
			
			//step~8
			Dailylog.logInfoDB(8, "In Cart page, update quantity to 5", Store, testName);
			checkUpdateQuatity("5");
			
			//step~9
			Dailylog.logInfoDB(9, "In Cart page, update quantity to 7", Store, testName);
			checkoutpopup("7");
			
			
			//step~10
			Dailylog.logInfoDB(10, "set stock as 9", Store, testName);
			Common.switchToWindow(driver, 1);
			updatewareHouse("9");
			
			//step~11
			Dailylog.logInfoDB(11, "In Cart page, update quantity to 10", Store, testName);
			Common.switchToWindow(driver, 0);
			checkoutpopup("10");
			
			//step~12
			Dailylog.logInfoDB(12, "In Cart page, update quantity to 3", Store, testName);
			checkUpdateQuatity("3");
			
			//step~13
			Dailylog.logInfoDB(13, "Then Checkout and Place order.", Store, testName);
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']")).click();
			//fill shipping
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			Common.sleep(2000);
			if(b2cPage.Shipping_FirstNameTextBox.getText().isEmpty()){
				B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			}
		
			act.sendKeys(Keys.PAGE_UP).perform();
			b2cPage.Shipping_ContinueButton.click();
			//fill payment info
			B2CCommon.handleAddressVerify(driver,b2cPage);
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			b2cPage.Payment_ContinueButton.click();
			Common.sleep(2000);
			act.sendKeys(Keys.PAGE_DOWN).perform();
			if (Common
					.isElementExist(
							driver,
							By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))) {
				driver.findElement(
						By.xpath("//label[@class='redesign-term-check redesign-unchecked-icon']"))
						.click();
			} else {
				Common.javascriptClick(driver,
						b2cPage.OrderSummary_AcceptTermsCheckBox);
			}
			b2cPage.OrderSummary_PlaceOrderButton.click();
			Common.sleep(2000);
			System.out.println("Order number is:" +B2CCommon.getOrderNumberFromThankyouPage(b2cPage));
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
	}
	
	public void changeLineItemQuantity(String quantity){
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(3000);
		if(!hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.getAttribute("value").equals(quantity)){
			hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.clear();
			hmcPage.B2CUnit_SiteAttribute_ASM_Line_Item_Quantity.sendKeys(quantity);
		}
		
	}
	
	public void checkoutpopup(String morethan5){
		By popup = By.xpath("//div[@class='popup-itemlimit']");
		b2cPage.cartPage_Quantity.clear();
		b2cPage.cartPage_Quantity.sendKeys(morethan5);
		Common.sleep(2000);
		Assert.assertTrue(Common.checkElementDisplays(driver, popup, 5));
	}
	
	public void createwareHouse(String houserAmount){
		WebElement wareHouseAmount = driver
				.findElement(By
						.xpath(".//*[@id='Content/IntegerEditor[in Content/Attribute[.available]]_input']"));
		Common.sleep(2000);
		wareHouseAmount.clear();
		wareHouseAmount.sendKeys(houserAmount);
		WebElement reserveWareHouseAmount = driver
				.findElement(By
						.xpath(".//*[contains(@id,'Attribute[.reserved]]_input') or contains(@id,'Attribute[StockLevel.reserved]]_input')]"));
		Common.sleep(2000);
		reserveWareHouseAmount.clear();
		reserveWareHouseAmount.sendKeys("0");
		driver.findElement(
				By.id("Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate]_label"))
				.click();
	}
	
	public void updatewareHouse(String houserAmount){
		Common.sleep(3000);
		WebElement wareHouseAmount = driver
				.findElement(By
						.xpath(".//*[contains(@id,'Attribute[.available]]_input') or contains(@id,'Attribute[StockLevel.available]]_input')]"));
		Common.sleep(2000);
		wareHouseAmount.clear();
		wareHouseAmount.sendKeys(houserAmount);
		WebElement reserveWareHouseAmount = driver
				.findElement(By
						.xpath(".//*[contains(@id,'Attribute[.reserved]]_input') or contains(@id,'Attribute[StockLevel.reserved]]_input')]"));
		Common.sleep(2000);
		reserveWareHouseAmount.clear();
		reserveWareHouseAmount.sendKeys("0");
		driver.findElement(
				By.xpath("//img[contains(@id,'Content/ImageToolbarAction[organizer.editor.save.title]')]"))
				.click();
	}
	
	public String getwareHouseData(){
		hmcPage.Home_baseStore.click();
		hmcPage.baseStore_id.clear();
		hmcPage.baseStore_id.sendKeys(testData.B2C.getStore());
		hmcPage.baseStore_search.click();
		hmcPage.BaseStore_SearchResult.click();
//		hmcPage.Common_SaveButton.click();
		hmcPage.BaseStore_PropertiesTab.click();
		WarehouseData = hmcPage.BaseStoreProperties_WarehouseData.getText();
		return WarehouseData;
	}
	
	public static void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		b2cPage.Cart_QuickOrderTextBox.clear();
		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
		Common.sleep(1000);
		b2cPage.Cart_AddButton.click();
	}
	
	
	public void checkUpdateQuatity(String cartQuantity){
		b2cPage.cartPage_Quantity.clear();
		b2cPage.cartPage_Quantity.sendKeys(cartQuantity);
		Common.javascriptClick(driver, b2cPage.cartPage_Quantity_update);
		Common.sleep(3000);
		By quantity_Message1 = By.xpath("// div[contains(text(),'Product quantity has been updated.')]");
		By quantity_Message5 = By.xpath("// div[contains(text(),'has been reduced to 4') or contains(@text,'Unfortunately')]");
		switch(cartQuantity){
			case "3":
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
				Common.sleep(3000);
				isDisplayed = Common.checkElementDisplays(driver, quantity_Message5, 5);
				By stockMess1 = By.xpath("//div[contains(@class,'alert negative')][2]");
				By stockMess2 = By.xpath("//div[contains(@class,'alert neutral')]");

				if(Common.checkElementDisplays(driver, stockMess1, 3)||Common.checkElementDisplays(driver, stockMess2, 3)){
					if (!isDisplayed){
						Assert.fail("mesage is not displayed:");
						Assert.assertEquals(b2cPage.cartPage_Quantity.getText().trim(), "4");
					}	
					else{
						System.out.println("message is displayed:");
					}
				}
				
				break;
		}
		
	}
	
	
	public void closePromotion(WebDriver driver, PartSalesPage PSPage) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Promotion)).click().perform();
		}
	}
	


}
