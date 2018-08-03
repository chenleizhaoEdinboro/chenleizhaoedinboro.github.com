package TestScript.B2C;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
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

public class CONTENT464Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	String product = "20KFCTO1WW";
	String productOutOfStock = "20KLCTO1WW";
	String productMessage = "//span[@class='shippingLeadTimeMessage']";
	String productEmpty = "20L7CTO1WW";

	public CONTENT464Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-464";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "storemgmt", "p2", "b2c"})
	public void CONTENT464(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			//1.go to hmc->B2C Commerce->B2C UNIT,enter the b2c unit,click search button
			searchUnit();
			Dailylog.logInfoDB(1, "search unit", Store, testName);
			
			//2.open the result ,go to site attribute tab,set Enable atp service and  Time Span For ATP Service,
			setEnableAtpService(hmcPage.zEnableATPService,"3");
			Dailylog.logInfoDB(2, "set Enable atp service and  Time Span For ATP Service", Store, testName);
			
			//3.go to jp site,add a cto product to cart
			addCtoToCart(product);
			Dailylog.logInfoDB(3, "add cto to cart", Store, testName);
			
			//4.when the cto product in stock and enough 
			placeOrder(productMessage,4);
			Dailylog.logInfoDB(4, "5.place order", Store, testName);
			
			//6.when the cto product out of  stock
			addCtoToCart(productOutOfStock);
			placeOrder(productMessage,6);
			Dailylog.logInfoDB(6, "7.place order", Store, testName);
			
			//8.when lead time from ATP is empty or invaild 
			addCtoToCart(productEmpty);
			placeOrder(productMessage,6);
			Dailylog.logInfoDB(8, "9.place order", Store, testName);
			
			//10.repeat step 1-2
			searchUnit();
			setEnableAtpService(hmcPage.zEnableATPServiceNo,"3");
			Dailylog.logInfoDB(10, "repeat step 1-2", Store, testName);
			
			//11.go to jp site,add a cto product to cart 
			addCtoToCart(product);
			Dailylog.logInfoDB(11, "go to jp site,add a cto product to cart ", Store, testName);
			
			//12.place order
			placeOrder(productMessage,6);
			Dailylog.logInfoDB(12, "13 place order ", Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void placeOrder(String productMessage2, int num) throws InterruptedException {
		b2cPage.checkoutSmb.click();
		Common.sleep(3000);
		if(num == 4) {
			Assert.assertTrue(driver.findElement(By.xpath(productMessage)).getText().contains("-"));
		}
		if(num == 6) {
			Assert.assertTrue(driver.findElement(By.xpath(productMessage)).getText().contains("2"));
		}
		B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
		driver.findElement(By.xpath("//div[@class='shipping_section_continue_box']")).click();
		B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
		b2cPage.Payment_ContinueButonNew.click();
		b2cPage.redesignUnchecked.click();
		b2cPage.placeOrder.click();
	}

	private void addCtoToCart(String product) {
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+product);
		b2cPage.Cart_saveCartBtn.click();
		Common.sleep(3000);
		b2cPage.Product_AddToCartBtn.click();
		Common.sleep(3000);
		b2cPage.Product_AddToCartBtn.click();
	}

	private void setEnableAtpService(WebElement zEnableATPService, String num) throws InterruptedException {
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(2000);
		Common.scrollToElement(driver,hmcPage.atpService);
		zEnableATPService.click();
		hmcPage.zTimeSpanForATP.clear();
		hmcPage.zTimeSpanForATP.sendKeys(num);
		hmcPage.HMC_Save.click();
	}

	private void searchUnit() {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		Common.sleep(3000);
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();
		Common.sleep(2000);
	}
}
