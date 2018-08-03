package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21914Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2CPage b2cPage;
    public String  wishtest;

	public NA21914Test(String store){
		this.Store = store;
		this.testName = "NA-21914";
	}
	
	@Test(alwaysRun= true)
	public void NA21914(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			driver.get(testData.HMC.getHomePageUrl());
			Common.sleep(5000);
			HMCCommon.Login(hmcPage, testData);
			//Stpe:1
			Dailylog.logInfoDB(1, "HMC Setting：HMC->B2C Commerce->B2C Unit->Site Attribute", Store, testName);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			if(Common.checkElementExists(driver, driver.findElement(By.id("Content/BooleanEditor[in Content/Attribute[B2CUnit.emailWishListToggle]]_true")), 5))
				driver.findElement(By.id("Content/BooleanEditor[in Content/Attribute[B2CUnit.emailWishListToggle]]_true")).click();
			driver.findElement(By.xpath(".//*[@id='Content/ImageToolbarAction[organizer.editor.save.title]_label']")).click();
			//Step:2
			Dailylog.logInfoDB(2, "Go to B2C website on front store and login", Store, testName);
			String URL = testData.B2C.getHomePageUrl();
			driver.manage().deleteAllCookies();
			driver.get(testData.B2C.getHomePageUrl());
			boolean isGateKeeperLogin = B2CCommon.handleGateKeeper(b2cPage, testData);
            Common.javascriptClick(driver, b2cPage.MyAccount_LoginLink);
            if(!isGateKeeperLogin)
            	B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			//Step:3 
			Dailylog.logInfoDB(3, "Add a CTO product to cart", Store, testName);
//			driver.get(testData.B2C.getHomePageUrl());
//			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
			B2CCommon.handleGateKeeper(b2cPage, testData);
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='emptyCartItemsForm']/a"))) {
				driver.findElement(
						By.xpath(".//*[@id='emptyCartItemsForm']/a")).click();
				//new cart page empty cart items
				if(Common.isElementExist(driver, By.xpath("//div[@class='popup_arrow_box']"))){
					if(driver.findElement(By.xpath("//div[@class='popup_arrow_box']")).isDisplayed()){
						driver.findElement(By.xpath("(//input[contains(@class,'popup_button')])[2]")).click();
					}
				}
			}
			String prodNumber = testData.B2C.getDefaultCTOPN();
			System.out.println("prodNumber:"+prodNumber);
			driver.get(URL+"/p/"+prodNumber);
			Common.sleep(6000);
			if(Common.isElementExist(driver, By.id("tab-a-nav-currentmodels"))){
				if (!driver.findElement(By.id("tab-a-nav-currentmodels")).isDisplayed()){
					if(Store.equals("GB")){
						prodNumber = "22TP2WPWP40";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("HK")){
						prodNumber = "22TP2TXX15G";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("AU")){
						prodNumber = "22TP2TXX15G";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("CA_AFFINITY")){
						prodNumber = "22TP2TEE475";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("JP")){
						prodNumber = "22TP2TXY370";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("US")){
						prodNumber = "22TP2TT470S";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("USEPP")){
						prodNumber = "22TP2TT470S";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("US_BPCTO")){
						prodNumber = "22TP2TT470S";
						driver.get(URL+"/p/"+prodNumber);
						
					}else if(Store.equals("US_OUTLET")){
						prodNumber = "10G9X026US";
						driver.get(URL+"/p/"+prodNumber);
					}
				}
			}else{
				if(Store.equals("GB")){
					prodNumber = "22TP2WPWP40";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("HK")){
					prodNumber = "22TP2TXX15G";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("AU")){
					prodNumber = "22TP2TXX15G";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("CA_AFFINITY")){
					prodNumber = "22TP2TEE475";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("JP")){
					prodNumber = "22TP2TXY370";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("US")){
					prodNumber = "22TP2TT470S";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("USEPP")){
					prodNumber = "22TP2TT470S";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("US_BPCTO")){
					prodNumber = "22TP2TT470S";
					driver.get(URL+"/p/"+prodNumber);
					
				}else if(Store.equals("US_OUTLET")){
					prodNumber = "10G9X026US";
					driver.get(URL+"/p/"+prodNumber);
				}
			}
			
			B2CCommon.addProductToCartFromPDPPage(driver);
			//Step:4
			Dailylog.logInfoDB(4, "On cart page,click 'Create another Wish List' "
					+ "in dropdown list under product", Store, testName);
			if(Common.checkElementExists(driver, driver.findElement(By.id("WishlistSelect")), 10)){
				//driver.findElement(By.id("WishlistSelect")).click();
				//driver.findElement(By.xpath(".//*[@id='WishlistSelect']/option[@value='createAnotherWishList']")).click();
				Select wishlist = new Select(driver.findElement(By.id("WishlistSelect")));
				wishlist.selectByValue("createAnotherWishList");
			}
			Common.sleep(3000);
			wishtest = "wln"+Common.getDateTimeString();
				if(driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")).isDisplayed()){
					driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")).clear();
					driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")).sendKeys(wishtest);
				}
				if(driver.findElement(By.xpath(".//*[@id='createWishlistBtn']")).isDisplayed()){
					Common.sleep(2000);
					driver.findElement(By.xpath(".//*[@id='createWishlistBtn']")).click();
				}
			
			// get part Number
			Common.sleep(6000);
			String partNumber = "";
			if(Common.isElementExist(driver, By.xpath("//span[@class='wishlist_partnumber']"))){
				Dailylog.logInfo("origin partNumber:"+driver.findElement(By.xpath("//span[@class='wishlist_partnumber']")).getText());
				partNumber = driver.findElement(By.xpath("//span[@class='wishlist_partnumber']")).getText().split(":")[1].trim();
				Dailylog.logInfo("partNumber:"+partNumber);
			}
			
			//Step:5
			Dailylog.logInfoDB(5, "Click 'View Wish Lish' on dailog", Store, testName);
			if(Common.checkElementExists(driver, driver.findElement(By.id("viewWishListBtn")), 10)){
				driver.findElement(By.id("viewWishListBtn")).click();
			}
			//Step:6
			Dailylog.logInfoDB(6, " Click Wish List name and view deail", Store, testName);
			if(Common.checkElementExists(driver, driver.findElement(By.xpath("//a[contains(text(),'"+wishtest+"')]")), 10)){
				driver.findElement(By.xpath("//a[contains(text(),'"+wishtest+"')]")).click();
			}
			if(!(driver.findElement(By.xpath("//td[@data-title='Product Number']")).getText().equals(partNumber))){
				Assert.fail("Wish List deail page:product number is error.");
			}
			//Step:7 
			Dailylog.logInfoDB(7, "Click 'Return Back'", Store, testName);
			driver.findElement(By.xpath("//a[@class='backtoHome']")).click();
			Common.sleep(3000);
			//Step:8
			Dailylog.logInfoDB(8, " Click 'Delete'", Store, testName);
			if(Common.checkElementExists(driver, driver.findElement(By.xpath("(.//*[@id='mainContent']/..//td/ul/li[2]/a)[last()]")), 5)){
				driver.findElement(By.xpath("(.//*[@id='mainContent']/..//td/ul/li[2]/a)[last()]")).click();
			}
			if(CheckWhetherDeleteWishlistName()){
				Assert.fail("wishlistname delete is failed.");
			}
			Common.sleep(3000);
			//Step:9 
			Dailylog.logInfoDB(9, "Reback Setting in HMC:HMC->B2C Commerce->B2C Unit->Site Attribute", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			driver.manage().deleteAllCookies();
			Common.sleep(3000);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			if(Common.checkElementExists(driver, driver.findElement(By.id("Content/BooleanEditor[in Content/Attribute[B2CUnit.emailWishListToggle]]_false")), 5))
				driver.findElement(By.id("Content/BooleanEditor[in Content/Attribute[B2CUnit.emailWishListToggle]]_false")).click();
			driver.findElement(By.xpath(".//*[@id='Content/ImageToolbarAction[organizer.editor.save.title]_label']")).click();
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}	
	}
	//validate  wishlist name Whether to delete successfully
	public boolean CheckWhetherDeleteWishlistName(){
		boolean flag = false;
		ArrayList<WebElement> RowsInput	= (ArrayList<WebElement>) driver.findElements(By.xpath(".//*[@id='mainContent']/div[2]/div/div/table[1]/tbody/tr/td[1]/a"));
		for(WebElement row : RowsInput){
			System.out.println(" wishlistname value:"+ row.getText());
		}
		for(int i = 0 ;i< RowsInput.size();i++){
			System.out.println("<input> name value:"+ RowsInput.get(i).getText());
			if(RowsInput.get(i).getText().equals(wishtest)){
				flag = true;
				break;
			}
		}
		return flag;
	}
}
	