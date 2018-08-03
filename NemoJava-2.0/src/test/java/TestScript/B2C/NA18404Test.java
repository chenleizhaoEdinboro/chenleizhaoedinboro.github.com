package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA18404Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	private String servers = "/data-center/servers/c/servers?menu-id=Servers";
	private List<WebElement> servers_small;

	public NA18404Test(String store) {
		this.Store = store;
		this.testName = "NA-18404";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18404(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);

			// Step~1  Open the website
			driver.get(testData.B2C.getHomePageUrl());
			Dailylog.logInfoDB(1, "Open the website", Store,testName);

			// step~2  click "PRODUCTS" then "Servers & Storage"
			Common.sleep(3000);
			b2cPage.Navigation_ProductsLink.click();
			if(Common.checkElementExists(driver, b2cPage.Navigation_Servers, 5)) {
				Common.mouseHover(driver, b2cPage.Navigation_ProductsLink);
				Common.sleep(2000);
				Common.mouseHover(driver, b2cPage.Navigation_Servers);
			}
			
			Dailylog.logInfoDB(2, "click PRODUCTS then Servers & Storage", Store,testName);

			// step~3 check the small modules under the Explore 
			driver.get(testData.B2C.getHomePageUrl()+servers);
			Common.sleep(3000);
			if(Common.isElementExist(driver, By.xpath("//div[@class='sidebarNav-section']"))){
				servers_small = driver.findElements(By.xpath("//div[@class='sidebarNav-section']"));
				for (int i = 0; i < servers_small.size(); i++) {
					if(servers_small.get(i).getText().equals("Servers") || servers_small.get(i).getText().equals("Servidores")) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Servers", Store,testName);
					}else if(servers_small.get(i).getText().equals("Storage") ) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Storage", Store,testName);
					}else if(servers_small.get(i).getText().equals("Networking")|| servers_small.get(i).getText().equals("Sistemas")) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Networking", Store,testName);
					}else if (servers_small.get(i).getText().equals("Converged Systems") || servers_small.get(i).getText().equals("Redes")) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Converged Systems", Store,testName);
					}else if(servers_small.get(i).getText().equals("Software") ) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Software", Store,testName);
					}else if(servers_small.get(i).getText().equals("Solutions") || servers_small.get(i).getText().equals("Opciones")) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Solutions", Store,testName);
					}else if(servers_small.get(i).getText().equals("Services") ) {
						servers_small.get(i).click();
						Dailylog.logInfoDB(3, "Services", Store,testName);
					}
					Dailylog.logInfoDB(3, "click PRODUCTS then Servers & Storage", Store,testName);
					
					// step 4 click every small modules 
					Dailylog.logInfoDB(4, "click every small modules ", Store,testName);

					// step 5 choose one modules that under the modules , the products exist
					Common.sleep(3000);
					if(Common.checkElementExists(driver, b2cPage.Servers_Learn, 5)) {
						b2cPage.Servers_Learn.click();
					}
					
					Common.sleep(3000);
					if(Common.checkElementExists(driver, b2cPage.Servers_Learn, 5)) {
						b2cPage.Servers_Learn.click();
					}
					Common.sleep(3000);
					if(Common.checkElementExists(driver, b2cPage.Servers_Learn, 5)) {
						b2cPage.Servers_Learn.click();
					}
					Dailylog.logInfoDB(5, "the products exist ", Store,testName);

					
					// step 6 add one products to cart and then checkout the products 
					if(!Common.checkElementExists(driver, b2cPage.Current_models, 5)) {
						Dailylog.logInfoDB(6, " no product models ", Store, testName);
					}else {
						Common.sleep(3000);
						String[] split = driver.getCurrentUrl().split("/");
						driver.get(testData.B2C.getloginPageUrl());
						B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
								testData.B2C.getLoginPassword());
						B2CCommon.handleGateKeeper(b2cPage, testData);
						Common.sleep(3000);
						driver.get(testData.B2C.getHomePageUrl()+"/cart");
						Common.sleep(3000);
						B2CCommon.emptyCart(driver, b2cPage);
						Common.sleep(2000);
						B2CCommon.addPartNumberToCart(b2cPage, split[split.length-1]);
						if(Common.checkElementExists(driver, b2cPage.Cart_empty, 5)) {
							Dailylog.logInfoDB(6, " no product models ", Store, testName);
						}else {
							Common.sleep(3000);
							B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData, Store);
							Dailylog.logInfoDB(6, " choose one product add the product to cart ", Store, testName);
							
						}
					}
					
				}
			}else {
				Dailylog.logInfoDB(2, "has no small modules", Store,testName);
			}
			
		} catch (Throwable e) {
			servers_small = driver.findElements(By.xpath("//div[@class='sidebarNav-section']"));
			for (int i = 0; i < servers_small.size(); i++) {
				if(servers_small.get(i).getText().equals("Servers") || servers_small.get(i).getText().equals("Servidores")) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Servers", Store,testName);
				}else if(servers_small.get(i).getText().equals("Storage") ) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Storage", Store,testName);
				}else if(servers_small.get(i).getText().equals("Networking")|| servers_small.get(i).getText().equals("Sistemas")) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Networking", Store,testName);
				}else if (servers_small.get(i).getText().equals("Converged Systems") || servers_small.get(i).getText().equals("Redes")) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Converged Systems", Store,testName);
				}else if(servers_small.get(i).getText().equals("Software") ) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Software", Store,testName);
				}else if(servers_small.get(i).getText().equals("Solutions") || servers_small.get(i).getText().equals("Opciones")) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Solutions", Store,testName);
				}else if(servers_small.get(i).getText().equals("Services") ) {
					servers_small.get(i).click();
					Dailylog.logInfoDB(3, "Services", Store,testName);
				}
				Dailylog.logInfoDB(3, "click PRODUCTS then Servers & Storage", Store,testName);
			handleThrowable(e, ctx);
		}
	}
	}
}
