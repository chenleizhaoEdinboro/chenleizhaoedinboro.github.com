package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA21941Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public MailPage mailPage;
	public int shareWishNo;
	public String createdWishlistName;
	public String emailToShareWishlistWith;
	

	public NA21941Test(String store){
		this.Store = store;
		this.testName = "NA-21941";
	}
	
	@Test(alwaysRun= true)
	public void NA21941(ITestContext ctx){
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
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
			driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.isWishListDisplay]]_true']")).click();
			driver.findElement(By.xpath(".//*[@id='Content/ImageToolbarAction[organizer.editor.save.title]_label']")).click();
			//Step:2
			Dailylog.logInfoDB(2, "Go to B2C website on front store and login", Store, testName);
//			String URL = testData.B2C.getHomePageUrl();
            driver.get(testData.B2C.getHomePageUrl()+"/login");
            B2CCommon.handleGateKeeper(b2cPage, testData);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
			if(Store.equals("USEPP")){
				B2CCommon.handleGateKeeper(b2cPage, testData);
			}
			Common.sleep(10000);
			//Step 3:
			Dailylog.logInfoDB(3, "Go to PDP page for MTM product", Store, testName);
            String prodNumber = testData.B2C.getDefaultMTMPN();
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + prodNumber);
			if(!Common.checkElementExists(driver, b2cPage.productDetailPage_wishlistDropDown, 5)){
				driver.manage().deleteAllCookies();
				driver.navigate().refresh();
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.handleGateKeeper(b2cPage, testData);
				B2CCommon.login(b2cPage, testData.B2C.getLoginID(), testData.B2C.getLoginPassword());
				Common.sleep(10000);
				B2CCommon.handleGateKeeper(b2cPage, testData);
				if(Store.equals("GB")){
					prodNumber = "4XD0K74703";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("HK")){
					prodNumber = "GXD0J16085";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("AU")){
					prodNumber = "80X70052AU";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("CA_AFFINITY")){
					prodNumber = "GXD0L03745";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("JP")){
					prodNumber = "4Z20L07919";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("US")){
					prodNumber = "78000450";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("USEPP")){
					prodNumber = "78000450";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("US_BPCTO")){
					prodNumber = "GXD0K09959";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
					
				}else if(Store.equals("US_OUTLET")){
					prodNumber = "20E4S0UG00";
					driver.get(testData.B2C.getHomePageUrl()+"/p/"+prodNumber);
				}
			}
            Assert.assertTrue(driver.findElement(By.id("WishlistSelect")).isDisplayed(), "Under add to cart button,wishlist dropdownlist will display");
          //Step 4:
			Dailylog.logInfoDB(4, "click 'Create another Wish List' in dropdown list under product"
					+ "(If you have created wishlist,you can add to wishlist directly) ", Store, testName);
			ArrayList <WebElement> wishlist = (ArrayList<WebElement>)driver.findElements(By.xpath(".//*[@id='WishlistSelect']/option"));
			for(WebElement rows : wishlist){
				Dailylog.logInfo("ArrayList->wishlist Value"+rows.getText());
			}
			boolean flag = false;
		    createdWishlistName = "Wonderful wishlist";
			for(int i = 0; i< wishlist.size(); i++){
				Dailylog.logInfo("every wishlist Value:"+wishlist.get(i).getText());
				if(wishlist.get(i).getText().equals(createdWishlistName)){
					flag = true;
					Select selectWish = new Select(driver.findElement(By.id("WishlistSelect")));
					selectWish.selectByVisibleText(createdWishlistName);
					break;
				}
			}
			if(!flag){
				if(Common.checkElementExists(driver, driver.findElement(By.id("WishlistSelect")), 10)){
					Select wish = new Select(driver.findElement(By.id("WishlistSelect")));
					wish.selectByValue("createAnotherWishList");
				}
				Common.sleep(6000);
				if(Common.checkElementExists(driver, driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")), 10)){
					if(driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")).isDisplayed()){
						driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")).clear();
						driver.findElement(By.xpath("//div[@class='control-group']/input[@class='left']")).sendKeys(createdWishlistName);
					}
					if(driver.findElement(By.xpath(".//*[@id='createWishlistBtn']")).isDisplayed()){
						Common.sleep(3000);
						driver.findElement(By.xpath(".//*[@id='createWishlistBtn']")).click();
					}
				}
			}

			//Step 5:
			Dailylog.logInfoDB(5, "Click View Wish List on dailog", Store, testName);
			if(Common.checkElementExists(driver, b2cPage.productDetailPage_viewWishlistButton, 10)){
				b2cPage.productDetailPage_viewWishlistButton.click();
			}

			//Step 6:
			Dailylog.logInfoDB(6, "Click share email icon on wishlist history page", Store, testName);
			ArrayList<WebElement> wishlists = (ArrayList<WebElement>)driver.findElements(By.xpath(".//*[@id='mainContent']/div[2]/div/div/table[1]/tbody/tr/td[1]"));
			for(WebElement wishRows : wishlists){
				Dailylog.logInfo("Wish Lish history page Value:"+wishRows.getText());
			}
			
			for(int j = 1; j<= wishlists.size();j++){
				if(wishlists.get(j).getText().equals(createdWishlistName)){
					shareWishNo = j;
					break;
				}
			}
			Dailylog.logInfo("int shareWishNo:"+shareWishNo);
//			if(Store.endsWith("CA_AFFINITY")){
//				driver.findElement(By.xpath(".//*[@id='mainContent']/..//tr["+shareWishNo+"]/td[4]/ul/li[3]/a")).click();
//			}else{
//				driver.findElement(By.xpath(".//*[@id='mainContent']/..//tr["+(shareWishNo+1)+"]/td[4]/ul/li[3]/a")).click();
//			}
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//a[contains(@href,'wishlists/shareByEmail')]")), 30);
			Common.javascriptClick(driver, driver.findElement(By.xpath(".//a[contains(@href,'wishlists/shareByEmail')]")));

			//Step 7:
			Dailylog.logInfoDB(7, "Input email infomation and Send", Store, testName);
			emailToShareWishlistWith = Common.getDateTimeString();
			if(Common.checkElementExists(driver, b2cPage.shareWishlist_yourFirstNameTextBox, 60)){
				b2cPage.shareWishlist_yourFirstNameTextBox.sendKeys("Voldemort");
			}
			b2cPage.shareWishlist_sendToTextBox.sendKeys(emailToShareWishlistWith+"@sharklasers.com");
			b2cPage.shareWishlist_sendButton.click();
			Common.sleep(5000);
			if(Store.equalsIgnoreCase("JP")){
				Assert.assertEquals(b2cPage.shareWishlist_wishlistSentConfirmationMessageBarJP.isDisplayed(), true);
			}else {
				Assert.assertEquals(b2cPage.shareWishlist_wishlistSentConfirmationMessageBar.isDisplayed(), true);
			}
			Dailylog.logInfoDB(7, "Successfully shared wishlist with email ID: " + emailToShareWishlistWith+"@sharklasers.com", Store, testName);
			String wishListLabel = b2cPage.shareWishlist_wishlistNameLabel.getText();
			Dailylog.logInfo("wishListLabel:"+wishListLabel);
			String wishListName;
			if(Store == "JP"){
				wishListName = wishListLabel.split("：")[1].trim();
			}else {
				wishListName = wishListLabel.split(":")[1].trim();
			}
			Dailylog.logInfo(wishListName);
			checkEmailAndVerifyContent("Voldemort" + " has a Lenovo.com Wish List to Share", ".//*[@id='display_email']//p[contains(.,'Wish list name: "+wishListName+"')]");
			
			Common.sleep(3000);
			//Step:8 
			Dailylog.logInfoDB(8, "Reback Setting in HMC:HMC->B2C Commerce->B2C Unit->Site Attribute", Store, testName);
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
			driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.isWishListDisplay]]_false']")).click();
			driver.findElement(By.xpath(".//*[@id='Content/ImageToolbarAction[organizer.editor.save.title]_label']")).click();
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}	
	}
	
	public void checkEmailAndVerifyContent(String emailSubject, String contentLocator) throws InterruptedException{
		EMailCommon.createEmail(driver, mailPage, emailToShareWishlistWith);
		String subject = null;
		for(int i=1;i<=5;i++){
			if(i==5){
				System.out.println("Need Manual Validate in email. Email not received!");
			} else if(Common.checkElementExists(driver, mailPage.Inbox_EmailSubject,5)==true){
				subject=mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: "+subject);
				if(subject.contains(emailSubject)){
					i=6;
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					mailPage.Inbox_EmailSubject.click();
					System.out.println("Clicked on email containing :" + emailSubject);
					Thread.sleep(3000);
					Assert.assertEquals(Common.isElementExist(driver, By.xpath(contentLocator)), true);
				} else {
					System.out.println("Email with this subject is not yet received. Refreshing the inbox in 10 seconds!");
					Common.sleep(10000);
				}
			} 
		}		
	}
}
	