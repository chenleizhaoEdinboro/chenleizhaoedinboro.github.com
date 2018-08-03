package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16968Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	String url;
	
	
	public NA16968Test(String store) {
		this.Store = store;
		this.testName = "NA-16968";
	}

	@Test(alwaysRun = true, groups = {"browsegroup","uxui",  "p1", "b2c","compatibility "})
	public void NA16968(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);

			// login b2c site
			Dailylog.logInfoDB(1, "load b2c", Store, testName);
			String FirstDEALSLinlXpath = "//a[contains(@data-main-id,'explore_deals')]";
			String CTOAddToCartXpath = ".//*[@id='addToCartButtonTop'][contains(@submitfromid,'CTO1')]";
			String MTMAddtoCart = ".//*[@id='addToCartButtonTop'][contains(@submitfromid,'CTO1')]";
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			/*Common.NavigateToUrl(driver, Browser,testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());*/
			// click Deals
			Dailylog.logInfoDB(2, "click Deals", Store, testName);
			if (!Common.checkElementExists(driver, b2cPage.Navigation_DEALS, 5)) {
				this.setNoDataLog("Deals link is not shown");
			}else {	
				NavigationBar.goToDealsFirstLink(b2cPage);
				/*if(Common.isElementExist(driver, By.xpath(FirstDEALSLinlXpath))) {
					b2cPage.Deals_Link.click();
					url = driver.findElement(By.xpath(FirstDEALSLinlXpath)).getAttribute("href");
					Dailylog.logInfoDB(1,url, Store, testName);
					driver.get(Common.NavigateToUrl(driver, Browser,url);
					Common.sleep(3000);
				}*/
				
				//check Verify the hero image "Save up to $.."
				/*Dailylog.logInfoDB(3, "open first part", Store, testName);
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Save_upto, 3), "check save up message exist");
				
				
				if(Common.checkElementExists(driver, b2cPage.Deals_PartOneSelectOne, 3)) {
					b2cPage.Deals_PartOneSelectOne.click();
				}else if(Common.checkElementExists(driver, b2cPage.Deals_PartOneSelectTwo, 3)) {
					//Common.javascriptClick(driver,b2cPage.Deals_PartOneSelectTwo);
					b2cPage.Deals_PartOneSelectTwo.click();
				}else if(Common.checkElementExists(driver, b2cPage.Deals_PartOneSelectThree, 3)) {
					b2cPage.Deals_PartOneSelectThree.click();
				}*/
								
				// Switch between different deals categories by click the tab
				Dailylog.logInfoDB(4, "switch deals", Store, testName);
				boolean flag = false;
				List<WebElement> listingTabs_all = driver.findElements(By.xpath(".//*[contains(@class,'tab-item')]/a/span"));
				for (int i = 1; i <=listingTabs_all.size(); i++) {
					Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@class,'tab-item')]/a/span)["+i+"]")), 3);
					driver.findElement(By.xpath("(.//*[contains(@class,'tab-item')]/a/span)["+i+"]")).click();;
					Assert.assertTrue(b2cPage.DEALS_ProductsList.size()>=1, "check products exist on deals list page");
					if(b2cPage.DEALS_ProductsPlusBounsOffer.size()>=1) {
						flag=true;
					}
				}
				System.out.print(flag);
				Assert.assertTrue(flag);
						
				String currentUrl = driver.getCurrentUrl();
				System.out.println("check the deals product buy");
				// 5,Click Shop Now under one Product
				List<WebElement> shopnowbutton_list = driver.findElements(By.xpath("//a[contains(text(),'Shop Now')]"));
				for(int i = 1;i<=shopnowbutton_list.size();i++) {
					Common.NavigateToUrl(driver, Browser, currentUrl);
					Common.javascriptClick(driver, driver.findElement(By.xpath("(//a[contains(text(),'Shop Now')])["+i+"]")));
					//driver.findElement(By.xpath("(//a[contains(text(),'Shop Now')])["+i+"]")).click();
					Common.sleep(8000);
					if(!(driver.getCurrentUrl().contains("cart"))){
						//System.out.println(!(driver.getCurrentUrl().contains("/p/"))||(driver.getCurrentUrl().contains("www3")));
						if(!(driver.getCurrentUrl().contains("/p/"))||(driver.getCurrentUrl().contains("www3"))) {
							continue;
						}
						b2cPage.PDP_ViewCurrentModelTab.click();
						if(Common.checkElementExists(driver, b2cPage.B2C_PDP_CustomizeButton, 3)) {
							b2cPage.B2C_PDP_CustomizeButton.click();
							Common.waitElementClickable(driver, b2cPage.Product_AddToCartBtn, 5);
							b2cPage.Product_AddToCartBtn.click();
							break;
						} else if(Common.checkElementExists(driver, b2cPage.B2C_PDP_MTMAddToCartButton, 3)) {
							b2cPage.B2C_PDP_MTMAddToCartButton.click();
							break;
						}else {
							continue;
						}
					}else{
						if(driver.getCurrentUrl().contains("www3")) {
							continue;
						}else{
							break;
						}
					}
					
					
				}
				
				//Add current product to cart from PDP page
				
				//check if cto exist and add to cart
				//then check the mtm exist
				// if all not exist then change to another part
				
				Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl()+"/cart");		
				Common.sleep(3000);
				String orderNum = B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData);
				System.out.println("orderNum is :" + orderNum);
				Dailylog.logInfoDB(1, orderNum,Store,testName);
				
				
				Common.NavigateToUrl(driver, Browser,testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				Common.sleep(3000);
				hmcPage.Home_Order.click();
				Common.sleep(3000);
				hmcPage.Home_Order_Orders.click();
				Common.sleep(2000);
				hmcPage.Home_Order_OrderID.sendKeys(orderNum);
				hmcPage.Home_Order_OrderSearch.click();
				if (hmcPage.Home_Order_OrderStatus.getText().toLowerCase().equals("fraud")) {
					Common.sleep(3000);
					hmcPage.Home_Order_OrderSearch.click();
				}
				Assert.assertTrue(hmcPage.Home_Order_OrderStatus.getText().contains("Completed"));
				Dailylog.logInfoDB(7, "Login in HMC , click Order -->Orders", Store, testName);	
				
			}
			
			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
}
