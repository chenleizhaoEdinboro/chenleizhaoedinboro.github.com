package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.Payment;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class ACCT117Test extends SuperTestClass {
	
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	public String customer;
	public String password_cus;
	public String partNumber;
	
	public int index = 0;
	
	public ACCT117Test(String store , String customer,String password_cus,String partNumber) {
		this.Store = store;
		this.customer = customer;
		this.password_cus = password_cus;
		this.partNumber = partNumber;
		this.testName = "ACCT-112";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void ACCT112(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//1, *To display new section My Subscriptions and new link "View My Subscription" in my account page*
			Dailylog.logInfoDB(1, "*To display new section My Subscriptions and new link View My Subscription in my account page*", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			HMCCommon.searchB2CUnit(hmcPage, testData);
			
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			driver.findElement(By.xpath("//input[contains(@id,'B2CUnit.zB2CShowSubscription') and contains(@id,'true')]")).click();
			
			hmcPage.baseStore_save.click();
			
			hmcPage.hmcHome_hmcSignOut.click();
			
			//2, *Page Titile:My Suscription which can be localized by message tool*all these messages can configured in HMC by message tool
			Dailylog.logInfoDB(2, "*Page Titile:My Suscription which can be localized by message tool*all these messages can configured in HMC by message tool", Store, testName);
			
			//3, To validate card partClick "View My Subscription"
			Dailylog.logInfoDB(3, "To validate card partClick View My Subscription", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl());
			
			smbLogin(testData.B2C.getLoginID(),testData.B2C.getLoginPassword());
			
			Common.javascriptClick(driver, b2cPage.Navigation_subMyAccountSpan);
			
			Assert.assertTrue(driver.findElement(By.xpath("(//a[contains(@href,'subscription')])[last()]")).isDisplayed(), "My subscription link is not displayed");
			
			//before step 4, need to add one subscription product into cart and purchase order
			Dailylog.logInfoDB(4, "click view my cards", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl() + "/cart");
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			
			B2CCommon.addPartNumberToCart(b2cPage, partNumber);
			
			String orderString = B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, b2cPage, testData, Store);
			
			Dailylog.logInfoDB(4, "need to add one subscription product into cart and purchase order", Store, testName);
			
			driver.get(testData.B2C.getHomePageUrl());
			
			Common.javascriptClick(driver, b2cPage.Navigation_subMyAccountSpan);
			
			driver.findElement(By.xpath("(//a[contains(@href,'subscription')])[last()]")).click();
			
			//5, h1.To validate Filter Group part(All[step2] or Active or Suspended)*option1: choose "Active"*
			
			Dailylog.logInfoDB(5, "h1.To validate Filter Group part(All[step2] or Active or Suspended)*option1: choose Active*", Store, testName);
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='subscriptionLine']//a[contains(.,'Active')]")).isDisplayed(),"Active filter is not shown on the page");
			
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='subscriptionLine']//a[contains(.,'Suspend')]")).isDisplayed(), "Suspend filter is not shown on the page");
	
			if(!driver.findElement(By.xpath("(//*[@id='subscriptionListContent']//span[contains(.,'Status')]/../span[@class='valueColumn'])[1]")).getText().equals("Active")){
				driver.findElement(By.xpath("(//input[@value='Reactivate'])[1]")).click();
				Thread.sleep(3000);
				
				driver.findElement(By.xpath("//div[@class='confirmDiv']/span[contains(.,'Yes')]")).click();
			}		
			
			
			driver.findElement(By.xpath("//div[@class='subscriptionLine']//a[contains(.,'Active')]")).click();
			
			List<WebElement> aList  = driver.findElements(By.xpath("//*[@id='subscriptionListContent']//span[contains(.,'Status')]/../span[text()='Active']"));
			
			Assert.assertTrue(aList.size() >=1, "the Active filter function does not work");
			
			//6, *option2:choose 'suspend'*
			Dailylog.logInfoDB(6, "choose suspend", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='subscriptionListPage']//a[contains(.,'All')]")).click();
			
			if(!driver.findElement(By.xpath("(//*[@id='subscriptionListContent']//span[contains(.,'Status')]/../span[@class='valueColumn'])[1]")).getText().equals("Suspend")){
				driver.findElement(By.xpath("(//input[@value='Suspend'])[1]")).click();
				Thread.sleep(3000);
				
				driver.findElement(By.xpath("//div[@class='confirmDiv']/span[contains(.,'Yes')]")).click();
			}		
			
			driver.findElement(By.xpath("//div[@class='subscriptionLine']//a[contains(.,'Active')]")).click();
			
			List<WebElement> aList1  = driver.findElements(By.xpath("//*[@id='subscriptionListContent']//span[contains(.,'Status')]/../span[text()='Suspend']"));
			
			Assert.assertTrue(aList1.size() >=1, "the Suspend filter function does not work");
			
			driver.findElement(By.xpath(".//*[@id='subscriptionListPage']//a[contains(.,'All')]")).click();
			
			//7, click view all my cards 
			Dailylog.logInfoDB(7, "click view all my cards", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='subscriptionListPage']//a[contains(@href,'payment-details')]")).click();
			
			Assert.assertTrue(driver.getCurrentUrl().contains("payment-details"),"page can not redirected to payment details page");

		
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	

	
	public void smbLogin(String user, String password){
		
		driver.findElement(By.xpath("//*[@id='smb-login-button']")).click();
		Common.sleep(3000);
		
		Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.email.id']")), user);
		Common.sendFieldValue(driver.findElement(By.xpath("//*[@id='login.password']")), password);

		driver.findElement(By.xpath("//*[@id='nemoLoginForm']/div/button[@type='submit']")).click();
		
		Common.sleep(6000);
	}

}
