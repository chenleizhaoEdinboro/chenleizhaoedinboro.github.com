package TestScript.B2B;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA19027Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public MailPage mailPage;
	String tempEmailName = Common.getDateTimeString();
	String ProductNo;
	String QuoteNo;


	public NA19027Test(String store) {
		this.Store = store;		
		this.testName = "NA-19027";
	}
	//=*=*=*=*=*=*=*=*=Login into Guerrilla mail=*=*=*=*=*=*=//
	private void LoginGuerrilla(String mailID){
		EMailCommon.goToMailHomepage(driver);
		Common.sleep(2000);
		mailPage.Inbox_EditButton.click();
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(mailID);
		mailPage.Inbox_SetButton.click();
		Dailylog.logInfoDB(2,"Clicked on Set button",Store,testName);
		Dailylog.logInfoDB(2,"Email with name :"+mailID+"@sharklasers.com",Store,testName);
	}
	private void checkMail(int i){
		if(Common.isElementExist(driver, By.xpath("(//td[contains(.,'Lenovo Quote ID: "+QuoteNo+"')])[1]")) == true){
			driver.findElement(By.xpath("(//td[contains(.,'Lenovo Quote ID: "+QuoteNo+"')])[1]")).click();
			Common.sleep(2000);
			mailPage.Mail_backToInbox.click();
		}else if(i < 5) {
			Dailylog.logInfoDB(5, "Mail is not received...waiting", Store, testName);
			Common.sleep(12000);
			checkMail(++i);
		}
	}
	//=*=*=*=*=*=*=*=*Check User's Email mailbox(guerrilla mail)*=*=*=*=*=*=*=//
	private void checkInbox(String mailID){			
		LoginGuerrilla(mailID);
		Common.sleep(12000);
		checkMail(1);
	}
	//=*=*=*=*=*=*=*Add A product to cart for B2B*=*=*=*=*=*=*=//
	private void AddToCartB2B(WebElement LaptopXpath,WebElement DetailXpath){
		Dailylog.logInfoDB(3,"Adding a product to cart...",Store,testName);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(2500);
		if(Common.isElementExist(driver, By.xpath("//a[contains(text(),'Empty cart')]")) == true){
			b2bPage.cartPage_emptyCartButton.click();
		}
		b2bPage.HomePage_productsLink.click();
		b2bPage.HomePage_Laptop.click();
		Dailylog.logInfoDB(3, "Clicked on Laptop link", Store, testName);
		ProductNo = LaptopXpath.getText();
		Dailylog.logInfoDB(3, "part no of selected laptop is :"+ProductNo, Store, testName);
		if (Common.isElementExist(driver, By.xpath(".//*[@id='addToCartForm"+ProductNo+"']/button"))== true) {
			driver.findElement(By.xpath(".//*[@id='addToCartForm"+ProductNo+"']/button")).click();
			Common.sleep(3000);
			driver.findElement(By.xpath("//div[@class='addtoCartCTA']/button[contains(@onclick,'"+ProductNo+"')]")).click();
			Common.sleep(2500);
			driver.findElement(By.xpath("//div[@class='addtoCartCTA']/button[contains(@onclick,'"+ProductNo+"')]/../a[2]")).click();
			Common.sleep(2500);
		}else {
			DetailXpath.click();
			Dailylog.logInfoDB(3, "Clicked on View Detail", Store, testName);
			b2bPage.PDP_AddToCart.click();
			if (Common.isElementExist(driver, By.xpath("//span[contains(.,'Add to cart')]"))== true) {
				driver.findElement(By.xpath("//span[contains(.,'Add to cart')]")).click();
			}
			Common.sleep(3000);
		}
	}
	//==============Email Quote method====================//
	public void EmailQuote(String emailId) {
		b2bPage.QuotePage_EmailTextArea.sendKeys(emailId);
		b2bPage.QuotePage_EmailDescription.sendKeys("testing email quote");
		b2bPage.QuotePage_SendEmailButton.click();
		Common.sleep(2000);
		b2bPage.QuotePage_CloseEmailButton.click();
	}
	//===============Step~7&8==========================//
	/*public void CheckQuoteHistory(int i, String quote) {
			Dailylog.logInfoDB(7, "Checking quote history...", Store, testName);
			if (Common.isElementExist(driver,By.xpath("(//td[@data-title='Quote ID']/a)["+i+"]")) == true) {
				String MatchQuote = driver.findElement(By.xpath("(//td[@data-title='Quote ID']/a)["+i+"]")).getText();
				Dailylog.logInfoDB(7, "text in Quote history block : "+MatchQuote, Store, testName);
				Dailylog.logInfoDB(7, "Verifying Quote no 1 : "+quote, Store, testName);
				if (quote.equalsIgnoreCase(MatchQuote)) {
					Dailylog.logInfoDB(7, "Verifying Quote no 2 : "+quote, Store, testName);
					//clicking email link of corresponding quote
					Assert.assertTrue(driver.findElement(By.xpath("(//td[@data-title='Actions']//a[2])[" + i + "]")).isDisplayed(), "Email Link is not present");
					driver.findElement(By.xpath("(//td[@data-title='Actions']//a[2])[" + i + "]")).click();					
					Dailylog.logInfoDB(7, "clicked Email Link on Quote History page", Store, testName);
					Common.sleep(2000);
					EmailQuote(tempEmailName+"@sharklasers.com");
					Common.sleep(2000);
					//Check user's mailbox
					checkInbox(tempEmailName);
					//Step~8
					driver.get(testData.B2B.getHomePageUrl());
					b2bPage.homepage_MyAccount.click();
					Common.sleep(2000);
					b2bPage.myAccountPage_ViewQuotehistory.click();
					Common.sleep(2000);
					driver.findElement(By.xpath("(//td[@data-title='Quote ID']/a)["+i+"]")).click();
					Common.sleep(2000);
					Dailylog.logInfoDB(8, "Clicked on quoteID link", Store, testName);					
					Assert.assertTrue(driver.getTitle().contains("Quote Details"), "we are on Quote Detail page");
					Assert.assertTrue(b2bPage.QuotePage_EmailQuoteAdd.isDisplayed(),"Email Link is not present");
					b2bPage.QuotePage_EmailQuoteAdd.click();
					Common.sleep(2000);
					EmailQuote(tempEmailName+"@sharklasers.com");
					//Check user's mailbox
					checkInbox(tempEmailName+1);
				}
			}

		}*/

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "email", "p2", "b2b"})
	public void NA19027(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			mailPage = new MailPage(driver);
			//Step~1 : Go to HMC -->B2B Commerce-> B2B UNIT-> Search b2b unit
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(5000);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			Common.sleep(2000);
			Dailylog.logInfoDB(1,"Clicked on search result",Store,testName);
			//Step~2 : Go to Site Attribute and enable Email Quote
			hmcPage.B2CUnit_SiteAttributeTab.click();
			Common.sleep(1000);
			//click on enable Email Quote
			hmcPage.SiteAttribute_EnableQuote.click();
			Dailylog.logInfoDB(1,"Enabled Email Quote",Store,testName);
			hmcPage.HMC_Save.click();
			Common.sleep(2000);
			//Enabled Request Quote			
			hmcPage.baseStore_administration.click();
			hmcPage.B2BAdministration_isQuoteAvailable.click();
			Dailylog.logInfoDB(1,"IsQuoteAvailable enabled",Store,testName);
			hmcPage.B2BUnit_Save.click();
			Common.sleep(2500);
			hmcPage.HMC_Logout.click();
			Dailylog.logInfoDB(2,"Logged out from HMC",Store,testName);			
			//===============Step~3 : Login into B2B======================//
			//open B2B web site and create quote 
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			Dailylog.logInfoDB(3,"Successfully logged in into B2B",Store,testName);
			//Step~4
			//Add a product to cart
			AddToCartB2B(b2bPage.Laptop_SelectedLaptop1,b2bPage.Laptop_ViewDetail1);
			Common.sleep(2000);
			//Request Quote for added product
			Assert.assertTrue(b2bPage.cartPage_RequestQuoteBtn.isDisplayed(), "Request Quote Button is not present");
			b2bPage.cartPage_RequestQuoteBtn.click();
			Dailylog.logInfoDB(3, "Request Quote button is clicked", Store, testName);
			Common.sleep(2000);
			b2bPage.cartPage_RepIDBox.clear();
			b2bPage.cartPage_RepIDBox.sendKeys("2900718028");
			b2bPage.cartPage_SubmitQuote.click();
			driver.getTitle().contains("Quote Confirmation");
			QuoteNo = b2bPage.cartPage_QuoteNumber.getText();
			Dailylog.logInfoDB(3, "Quote No : "+QuoteNo, Store, testName);
			Assert.assertTrue(b2bPage.QuotePage_EmailQuoteAdd.isDisplayed(), "Email Quote Link is not present");
			b2bPage.QuotePage_EmailQuoteAdd.click();
			//Step~5 : click email quote and request sending email
			Common.sleep(2000);
			EmailQuote(tempEmailName+"@sharklasers.com");			 
			//Check user's mailbox
			checkInbox(tempEmailName);
			//Step~6 : Opening Quote history page in my account
			driver.get(testData.B2B.getHomePageUrl());
			b2bPage.homepage_MyAccount.click();
			Dailylog.logInfoDB(6, "Clicked on My Account", Store, testName);
			Common.sleep(2000);
			b2bPage.myAccountPage_ViewQuotehistory.click();
			Common.sleep(2000);
			//step~7
			Dailylog.logInfoDB(7, "Checking quote history...", Store, testName);
			if (Common.isElementExist(driver,By.xpath("//td[@data-title='Quote ID']/a[contains(.,'"+QuoteNo+"')]"))) {
				//clicking email link of corresponding quote
				Assert.assertTrue(driver.findElement(By.xpath("//td[@data-title='Actions']//a[contains(@href,'"+QuoteNo+"')]/../a[2]")).isDisplayed(), "Email Link is not present");
				driver.findElement(By.xpath("(//td[@data-title='Actions']//a[contains(@href,'"+QuoteNo+"')]/../a)[2]")).click();					
				Dailylog.logInfoDB(7, "clicked Email Link on Quote History page", Store, testName);
				Common.sleep(2000);
				EmailQuote(tempEmailName+1+"@sharklasers.com");
				Common.sleep(2000);
				//Check user's mailbox
				checkInbox(tempEmailName+1);
				//Step~8
				driver.get(testData.B2B.getHomePageUrl());
				b2bPage.homepage_MyAccount.click();
				Common.sleep(2000);
				b2bPage.myAccountPage_ViewQuotehistory.click();
				Common.sleep(2000);
				driver.findElement(By.xpath("//td[@data-title='Quote ID']/a[contains(.,'"+QuoteNo+"')]")).click();
				Common.sleep(2000);
				Dailylog.logInfoDB(8, "Clicked on quoteID link", Store, testName);					
				Assert.assertTrue(driver.getTitle().contains("Quote Details"), "we are on Quote Detail page");
				Assert.assertTrue(b2bPage.QuotePage_EmailQuoteAdd.isDisplayed(),"Email Link is not present");
				b2bPage.QuotePage_EmailQuoteAdd.click();
				Common.sleep(2000);
				EmailQuote(tempEmailName+2+"@sharklasers.com");
				//Check user's mailbox
				checkInbox(tempEmailName+2);
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}