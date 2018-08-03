package TestScript.B2C;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA23911Test extends SuperTestClass{
	public B2CPage b2cPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	public NA23911Test(String Store){
		this.Store = Store;
		this.testName = "NA-23911";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA23911(ITestContext ctx){
		try{
			this.prepareTest();
			
			b2cPage = new B2CPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			//1, go to the tested site , such as "https://pre-c-hybris.lenovo.com/au/en/"
			Dailylog.logInfoDB(1, "go to the tested site", Store, testName);
			
			driver.get(loginUrl);
			
			//2,login the tested site with telesales user
			Dailylog.logInfoDB(2, "login the tested site with telesales user", Store, testName);
			B2CCommon.handleGateKeeper(b2cPage, testData);

			B2CCommon.DoubleLogin(driver, testData, b2cPage, loginUrl, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword(),Browser);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			//3,go to "My Account" page and click "Start Assisted Service Session" link 
			//Expect : the Assisted Service Mode will appear at the top of the page
			Dailylog.logInfoDB(3, "click Start Assisted Service Session", Store, testName);
			
			//b2cPage.Navigation_MyAccountIcon.click();
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			Assert.assertTrue("ASM does not appear at the top of the page", b2cPage.SignoutASM.isDisplayed());
			
			//4, set the Store Type as "PUBLIC_CONSUMER", click Customer ID search icon 
			//Expect : An Advanced Customer Search dialogue will appear
			Dailylog.logInfoDB(4, "set the Store Type as PUBLIC_CONSUMER, click Customer ID search icon ", Store, testName);
			
			String defaultStoreType = driver.findElement(By.xpath("//select[@name='storeType']/option[@selected='selected']")).getText().toString();
			if(!defaultStoreType.equals("PUBLIC_CONSUMER")){
				Select select_StoreType = new Select(driver.findElement(By.xpath("//select[@name='storeType']")));
				
				select_StoreType.selectByValue("PUBLIC_CONSUMER");
			}
			String defaultStoreType1 = driver.findElement(By.xpath("//select[@name='storeType']/option[@selected='selected']")).getText().toString();
			
			Assert.assertTrue("After set the store type , the store type is not PUBLIC_CONSUMER",defaultStoreType1.equals("PUBLIC_CONSUMER"));
			
			b2cPage.ASM_AdvanceCustomerSearchIcon.click();
			Thread.sleep(5000);
			Assert.assertTrue("An Advanced Customer Search dialogue does not appear", b2cPage.Tele_CustomerSearch_Search.isDisplayed());

			//5, click Search button on the Advanced Customer Search dialogue  
			//Expect : the result is not null , some results can be found 
			Dailylog.logInfoDB(5, "click Search button on the Advanced Customer Search dialogue", Store, testName);
			
			driver.findElement(By.xpath("//*[@id='firstName']")).clear();
			driver.findElement(By.xpath("//*[@id='firstName']")).sendKeys("A");
			
			b2cPage.Tele_CustomerSearch_Search.click();
			Thread.sleep(90000);
			
			if(Common.isElementExist(driver, By.xpath("//*[@id='customerPopupContent']/div/table/tbody/tr[@class='b2cCustomer']"))){
				List<WebElement> list_advancedCustomer = driver.findElements(By.xpath("//*[@id='customerPopupContent']/div/table/tbody/tr[@class='b2cCustomer']"));
				
				Assert.assertTrue("After click the Advanced Search Button , no results display", list_advancedCustomer.size() != 0);
				
			}else{
				Assert.assertTrue("After click the Advanced Search Button , no results display", false);
				
			}
			
			//6, close the Advanced Customer Search dialogue 
			Dailylog.logInfoDB(6, "close the Advanced Customer Search dialogue", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='cboxClose']")).click();
			Thread.sleep(5000);
			
			//7, click Store ID search icon 
			//Expect : An advanced Store Search dialogue will appear
			Dailylog.logInfoDB(7, "click Store ID search icon ", Store, testName);
			
			b2cPage.asm_StoreIDIcon.click();
			Thread.sleep(6000);
			
			Assert.assertTrue("After click Store ID search icon , the search dialogue does not appear !!!",b2cPage.asm_StoreSearchButton.isDisplayed());
			
			//8, click Search button on the Advanced Store Search dialogue
			//Expect : the search result is not null , some results can be found 
			Dailylog.logInfoDB(8, "click Search button on the Advanced Store Search dialogue", Store, testName);
			
			b2cPage.asm_StoreSearchButton.click();
			Thread.sleep(60000);
			
			if(Common.isElementExist(driver, By.xpath("//*[@id='advStoreSearchTable']/tr[contains(@onclick,'changeStore')]"))){
				List<WebElement> list_advancedStoreSearch = driver.findElements(By.xpath("//*[@id='advStoreSearchTable']/tr[contains(@onclick,'changeStore')]"));
				Assert.assertTrue("After click advanced search button , no results display !!", list_advancedStoreSearch.size() != 0);
				
			}else{
				Assert.assertTrue("After click advanced search button , no results display !!", false);
			}
			
			//9 , close the Advanced Customer Search dialogue 
			Dailylog.logInfoDB(9, "close the Advanced Customer Search dialogue", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='cboxClose']")).click();
			Thread.sleep(5000);
			
			//10, click Transaction ID search icon 
			// Expect : An Advanced Transaction Search dialogue will appear 
			Dailylog.logInfoDB(10, "click Transaction ID search icon", Store, testName);
			
			b2cPage.asm_TransactionSearchIcon.click();
			Thread.sleep(6000);
			
			Assert.assertTrue("After click Transaction icon , the search dialogue does not appear !!", b2cPage.asm_TransactionSearchButton.isDisplayed());
			
			//11, click Search button on the Advanced Transaction Search dialogue
			Dailylog.logInfoDB(11, "click Search button on the Advanced Transaction Search dialogue", Store, testName);
			
			
			b2cPage.asm_TransactionSearchButton.click();
			Thread.sleep(20000);
			
			if(Common.isElementExist(driver, By.xpath(".//*[@id='advTransactionSearchTable']/tr[contains(@onclick,'selectTransaction')]"))){
				List<WebElement> list_transaction = driver.findElements(By.xpath(".//*[@id='advTransactionSearchTable']/tr[contains(@onclick,'selectTransaction')]"));
				
				Assert.assertTrue("After click Transaction search button , no results display !!", list_transaction.size() != 0);
			}else{
				Assert.assertTrue("After click Transaction search button , no results display !!", false);
			}
			
			//12 , close the Advanced Transaction Search dialogue.
			Dailylog.logInfoDB(12, "close the Advanced Transaction Search dialogue", Store, testName);
			
			driver.findElement(By.xpath(".//*[@id='cboxClose']")).click();
			Thread.sleep(5000);
	
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	
	}
	
}
