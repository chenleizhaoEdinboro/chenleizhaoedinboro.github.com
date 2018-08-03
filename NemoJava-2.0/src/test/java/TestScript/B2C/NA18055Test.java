package TestScript.B2C;


import java.util.ArrayList;

import junit.framework.Assert;

import org.openqa.selenium.By;
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

public class NA18055Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String emailCart;
	public NA18055Test(String store) {
		this.Store = store;
		this.testName = "NA-18055";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2c"})
	public void NA18055(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			hmcPage = new HMCPage(driver);			
			emailCart = "testemail@sharklasers.com";

			Dailylog.logInfoDB(1, "Login to B2C site...", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.siteAttribute_emailCartToggleYes.click();
			hmcPage.siteAttribute_emailCartInputToggleYes.click();
			hmcPage.SaveButton.click();
			hmcPage.hmcHome_hmcSignOut.click();
			driver.get(testData.B2C.getHomePageUrl());
			Thread.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			//Common.javascriptClick(driver, b2cPage.MyAccount_LoginLink);
			driver.get(testData.B2C.getloginPageUrl());
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			Thread.sleep(2000);
			Dailylog.logInfoDB(1, "Login to B2C site successfully.", Store,
					testName);
			Dailylog.logInfoDB(1, "Open cart page...", Store, testName);
			/*Common.javascriptClick(driver, b2cPage.HomePage_CartIcon);
			if(Common.checkElementDisplays(driver, b2cPage.Cart_OpenCartDropdown, 10)){
				b2cPage.Cart_OpenCartDropdown.click();
			}*/
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			
			//driver.get(testData.B2C.getHomePageUrl() + "/cart");
			B2CCommon.emptyCart(driver, b2cPage);
			Dailylog.logInfoDB(1, "Adding product to cart", Store, testName);
			String productNumber = testData.B2C.getDefaultMTMPN();
			//productNumber="60DFAAR1US";
			B2CCommon.addPartNumberToCart(b2cPage, productNumber);
			Dailylog.logInfoDB(2, "quick order " + productNumber, Store,
					testName);
			
			String productName = b2cPage.Cart_ProductsName.getText();
			String productPrice = b2cPage.Cart_ProductsPrice.getText();
			Dailylog.logInfoDB(2, "Product Name : " + productName + ".  Product Price : " + productPrice, Store,
					testName);
			Dailylog.logInfoDB(3, "Clicked on Email cart link", Store, testName);
			Common.sleep(3000);
			b2cPage.Cart_Emailcart.click();
			switchToWindow(1);
			Dailylog.logInfoDB(4, "Filling informations in fields", Store, testName);
			b2cPage.EmailCart_SenderName.clear();
			b2cPage.EmailCart_SenderName.sendKeys(testName+Store);
			b2cPage.EmailCart_SenderEmail.clear();
			b2cPage.EmailCart_SenderEmail.sendKeys(emailCart);
			b2cPage.EmailCart_CheckBox.click();
			Common.sleep(3000);
			Dailylog.logInfoDB(4, "Clicked on send button", Store, testName);
			b2cPage.EmailCart_SendButton.click();
			Thread.sleep(2000);

			Dailylog.logInfoDB(5, "Login to mail account to check order.",
					Store, testName);
			EMailCommon.goToMailHomepage(driver);
			EMailCommon.createEmail(driver, mailPage, emailCart);
			if(Store.equals("JP")){
				checkInbox(emailCart,testName+" "+Store,productNumber,productName.split("-")[0],productPrice,3);
			}else{
				checkInbox(emailCart,testName+Store,productNumber,productName,productPrice,3);
			}
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	
	public void checkInbox(String EmailAccount,String EmailTitle,String partNumber,String partName,String productPrice, int count){
		int i = 1;
		if(Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+EmailTitle+"')]"), 10)){
			driver.findElement(By.xpath("//*[contains(text(),'"+EmailTitle+"')]")).click();;
			CheckEmailDetail( partNumber,partName ,productPrice);
		}else if(i<count){
			count--;
			checkInbox(EmailAccount, EmailTitle, partNumber, partName, productPrice,count);
		}else{
			Dailylog.logInfoDB(10, "No email received", Store, testName);
			Assert.fail("No email received");
		}
	}
	
	
	public void CheckEmailDetail(String partNumber,String partName ,String productPrice){
		if(!Store.equals("HK"))
			Assert.assertTrue("part number check", Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+partNumber+"')]")));
		Assert.assertTrue("part name check", Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+partName+"')]")));
		Assert.assertTrue("part price check", Common.isElementExist(driver, By.xpath("//*[contains(text(),'"+productPrice+"')]")));
	};
	
	

	/*public void loginGuerrilla(String tempEmailName) {
		Dailylog.logInfoDB(5, "Login to email account", Store, testName);
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		Common.sleep(2000);
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(tempEmailName);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(5, "Clicked on Set button", Store, testName);
		Dailylog.logInfoDB(5, "Email with name :" + tempEmailName
				+ "@sharklasers.com", Store, testName);
	}*/

	
	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(
					driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
