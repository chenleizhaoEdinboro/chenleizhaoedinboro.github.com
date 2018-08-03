package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.B2BCommon.B2BRole;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA18480Test extends SuperTestClass{
	private static HMCPage hmcPage;
	private static B2BPage b2bPage;
	private static MailPage mailPage;
	private String quoteID;
	String account="Builder"+Common.getDateTimeString()+"@SHARKLASERS.COM";
	
	public NA18480Test(String store) {
		this.Store = store;
		this.testName = "NA-18480";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2b"})
	public void NA18480(ITestContext ctx) {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			mailPage = new MailPage(driver);
			//EMailCommon.createEmail(driver, mailPage, testData.B2B.getBuilderId());
			//Thread.sleep(3000);
			//Step 1:
			//driver.get(testData.HMC.getHomePageUrl());
			Dailylog.logInfoDB(1, "Enter into HMC, open the toggle for country seal in Site Attribute in B2B Unit", Store, testName);
			setCountrySealToggle(hmcPage.siteAttribute_EnableCountrySealYes);

			//Step 2:
			Dailylog.logInfoDB(2, "Open the corresponding B2B website, choose one product, and add to cart", Store, testName);
			addProductToCart();

			//Step 3:
			Dailylog.logInfoDB(3, "Request a quote, and then jump to quote confirmation page", Store, testName);
			requestQuoteAndCheckCountrySeal(true);
			Dailylog.logInfoDB(3, "Quote created. Quote ID is: " + quoteID, Store, testName);

			//Step 4:
			Dailylog.logInfoDB(4, "Check the quote confirmation email", Store, testName);
			checkCountrySealInEmail("Lenovo Quote ID: " + quoteID, true, 4);

			//Step 5:
			driver.manage().deleteAllCookies();
			Dailylog.logInfoDB(5, "Go back the HMC, disable the country seal toggle", Store, testName);
			setCountrySealToggle(hmcPage.siteAttribute_EnableCountrySealNo);

			//Step 6:
			Dailylog.logInfoDB(6, "Repeat step 2-4", Store, testName);
			addProductToCart();
			requestQuoteAndCheckCountrySeal(false);
			Dailylog.logInfoDB(6, "Quote created. Quote ID is: " + quoteID, Store, testName);
			checkCountrySealInEmail("Lenovo Quote ID: " + quoteID, false, 6);
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}

	public void setCountrySealToggle(WebElement countrySealToggleLocator){
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		Common.sendFieldValue(hmcPage.B2BUnit_SearchIDTextBox,testData.B2B.getB2BUnit());                
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
		countrySealToggleLocator.click();
		hmcPage.B2BUnit_Save.click();
		hmcPage.hmcHome_hmcSignOut.click();
	}

	public void addProductToCart() throws InterruptedException{
		
		B2BCommon.createAccount(driver ,testData.B2B.getLoginUrl() ,testData.B2B.getB2BUnit() ,b2bPage, B2BRole.Builder, account, Browser);
		HMCCommon.activeAccount(driver,testData, account);
		driver.manage().deleteAllCookies();
		driver.get(testData.B2B.getLoginUrl());
		B2BCommon.Login(b2bPage, account, testData.B2B.getDefaultPassword());
		Thread.sleep(2000);
		B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
	}

	public void requestQuoteAndCheckCountrySeal(boolean isCountrySealAvailable) throws InterruptedException{
		b2bPage.cartPage_RequestQuoteBtn.click();
		Thread.sleep(2000);
		b2bPage.cartPage_SubmitQuote.click();
		quoteID = b2bPage.quoteConfirmation_quoteID.getText();
		//Checking if country seal is present or not
		Assert.assertEquals(Common.isElementExist(driver, By.xpath(".//div[contains(@class,'checkout-confirm')]/img[contains(@src,'Seal')]")), isCountrySealAvailable);
	}

	public void checkCountrySealInEmail(String emailSubject, boolean isCountrySealAvailable, int stepNumber) throws InterruptedException{
		EMailCommon.createEmail(driver, mailPage, account);
		String subject = null;
		for(int i=1;i<=5;i++){
			if(i==5){
				Dailylog.logInfoDB(stepNumber, "Need Manual Validate in email. Email not received!", Store, testName);
			} else if(Common.checkElementExists(driver, mailPage.Inbox_EmailSubject,5)==true){
				subject=mailPage.Inbox_EmailSubject.getText();
				if(subject.contains(emailSubject)){
					i=6;
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					mailPage.Inbox_EmailSubject.click();
					Thread.sleep(3000);
					Assert.assertEquals(Common.isElementExist(driver, By.xpath(".//*[@id='display_email']//img[contains(@alt,'Seal')]")), isCountrySealAvailable);
				} else {
					Dailylog.logInfoDB(stepNumber, "Email with this subject is not yet received. Refreshing the inbox in 10 seconds!", Store, testName);
					Common.sleep(10000);
				}
			} 
		}		
	}
}
