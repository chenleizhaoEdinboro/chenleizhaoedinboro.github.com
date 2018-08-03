package TestScript.B2B;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA18057Test extends SuperTestClass{
	public B2BPage b2bPage;
	public MailPage mailPage;
	public String email = Common.getDateTimeString();

	public NA18057Test(String store) {
		this.Store = store;
		this.testName = "NA-18057";
	}
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2b"})
	public void NA18057(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			String b2bURL = testData.B2B.getLoginUrl();
			String productNo = testData.B2B.getDefaultMTMPN1();
			String fullName = "AutoFullName";
			LoginGuerrilla();
			Common.sleep(2000);
			Dailylog.logInfoDB(1, "Temp Email id is: " + email, Store, testName);
			//============= Step:1 Login to B2B website ===================//
			driver.get(b2bURL);
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			Dailylog.logInfoDB(1, "Successfully logged into B2B Website", Store, testName);

			//============= Step:2 Add a product to cart ===================//
			B2BCommon.addProduct(driver, b2bPage, productNo);
			Dailylog.logInfoDB(2, "Successfully added a product in cart", Store, testName);
			Common.sleep(2000);
			String productName = b2bPage.CheckOutPage_productName.getText();
			String productPrice = b2bPage.CheckOutPage_productPrice.getText();
			//--- Checking the presence of Product in Cart ----//
			Assert.assertTrue(b2bPage.CheckOutPage_productName.isDisplayed());
			String  Currenthandle= driver.getWindowHandle();

			//============= Step:3 Click Email Cart Link ===================//
			b2bPage.CheckOutPage_EmailLink.click();
			Dailylog.logInfoDB(3, "Email Cart Link is clicked.", Store, testName);
			Common.sleep(3000);			
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}

			//============= Step:4 Fill in the required info  ===================//
			b2bPage.EmailCart_fullName.sendKeys(fullName);
			b2bPage.EmailCart_emailTxtBox.sendKeys(email + "@sharklasers.com");
			b2bPage.EmailCart_sendButton.click();
			Dailylog.logInfoDB(4, "Clicked send button after filling all the required info", Store, testName);
			Common.sleep(5000);
			String sendSuccessMsg = b2bPage.EmailCart_sendSuccessMsg.getText();
			Dailylog.logInfoDB(4, sendSuccessMsg, Store, testName);
			Assert.assertTrue(b2bPage.EmailCart_sendSuccessMsg.isDisplayed());
			Common.sleep(5000);
			driver.close();
			driver.switchTo().window(Currenthandle);

			//============= Step:5 Check Email Content ===================//
			LoginGuerrilla();
			Check_Content(4,productName,productPrice);

		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	private void LoginGuerrilla(){
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(email);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(5,"Clicked on Set button",Store,testName);
	}
	//------------------ Checking Mail content ------------------ //
	private void Check_Content(int i,String productName, String productPrice){
		Dailylog.logInfoDB(5,"Checking mail content...",Store,testName);
		if (Common.isElementExist(driver,By.xpath("(//td[contains(.,'Cart From')])")) == true) {
			driver.findElement(By.xpath("(//td[contains(.,'Cart From')])")).click();
			Common.sleep(2000);	
			//checking Country Seal
			String prodNameInEmail = driver.findElement(By.xpath("//td/span[contains(.,'" + productName + "')]")).getText();
			String prodPriceInEmail = driver.findElement(By.xpath("(//tr[@valign='top']/td[contains(.,'" + productPrice + "')][@align='left'])[1]")).getText();
			Dailylog.logInfoDB(5,"Product Name in Email is: "+prodNameInEmail,Store,testName);
			Dailylog.logInfoDB(5,"Product Price in Email is: "+prodPriceInEmail,Store,testName);
			Assert.assertTrue(driver.findElement(By.xpath("//td/span[contains(.,'" + productName + "')]")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("(//tr[@valign='top']/td[contains(.,'" + productPrice + "')][@align='left'])[1]")).isDisplayed());
			Common.sleep(2500);
			mailPage.Mail_backToInbox.click();			
		} else if(i < 6){
			Dailylog.logInfoDB(5,"Mail is not present...check later",Store,testName);
			Check_Content(++i,productName,productPrice);
		}
	}

}
