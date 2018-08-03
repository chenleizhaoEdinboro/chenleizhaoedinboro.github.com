package TestScript.B2C;


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class NA27834Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	public String emailCart;
	public NA27834Test(String store) {
		this.Store = store;
		this.testName = "NA-27834";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p1", "b2c"})
	public void NA27834(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			mailPage = new MailPage(driver);
			hmcPage = new HMCPage(driver);			
			emailCart = "testemail@sharklasers.com";

			Dailylog.logInfoDB(1, "Login to HMC site...", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.siteAttribute_emailCartToggleYes.click();
			hmcPage.siteAttribute_emailCartGuestToggleYes.click();
			hmcPage.SaveButton.click();
			hmcPage.hmcHome_hmcSignOut.click();
			driver.get(testData.B2C.getHomePageUrl());
			Thread.sleep(2000);
			B2CCommon.handleGateKeeper(b2cPage, testData);

			Dailylog.logInfoDB(1, "Open cart page...", Store, testName);
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
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
				checkEmailContentinAllEmail(driver, mailPage, testName+" "+Store);
			}else{
				checkEmailContentinAllEmail(driver, mailPage, testName+Store);
			}
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	
	public static Boolean checkEmailContentinAllEmail(WebDriver driver, MailPage mailPage, String emailSubject) {
		String subject = null;
		Boolean reciverEmail = false;
		Common.sleep(3000);
		for (int i = 1; i <= 10; i++) {
			List<WebElement> emailList = driver.findElements(By.xpath("//tbody[@id='email_list']/tr/td[3]"));
			if (emailList.size() > 0) {

				for (WebElement emailTitle : emailList) {
					subject = emailTitle.getText();
					System.out.println("The subject is: " + subject);
					if (subject.contains(emailSubject)) {
						emailTitle.click();
						System.out.println("Clicked on email containing :" + emailSubject);
						Common.sleep(5000);
						Assert.assertFalse("", Common.checkElementDisplays(driver, By.xpath("//*[contains(text(),'Go to cart')]"), 10));
						Assert.assertFalse("", Common.checkElementDisplays(driver, By.xpath("//*[contains(text(),'access the cart directly')]"), 10));
						reciverEmail = true;
						break;
					}
				}
			}
			if (reciverEmail)
				break;
			else {
				System.out.println(
						"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
				Common.sleep(10000);
			}
		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;
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

