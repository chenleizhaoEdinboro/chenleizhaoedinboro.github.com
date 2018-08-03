package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;


public class NA19792Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
    String tempEmailAddress;
	private String number;
	public NA19792Test(String store,String number) {
		this.Store = store;
		this.testName = "NA-19792";
		this.number = number;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2b"})
	public void NA19792(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			//step 1 Login B2B Website, thenOpen one CTO products on B2B website
			driver.get(testData.B2B.getHomePageUrl()+"/login");
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), "1q2w3e4r");
			if(Common.checkElementExists(driver, b2bPage.HomePage_productsLink, 5)) {
				b2bPage.HomePage_productsLink.click();
				b2bPage.HomePage_Laptop.click();
				Common.sleep(3000);
				if(Common.checkElementExists(driver, b2bPage.Product_cto, 5)) {
					List<WebElement> findElements = driver.findElements(By.xpath("//*[@id=\"resultList\"]//div[4]/a"));
					findElements.get(findElements.size()-1).click();
					Dailylog.logInfo(" step 1 Login B2B Website");
					
					
		            //step 2 check REVIEW SUMMARY & BUY section :Delivery Date is hidden
		            Common.sleep(3000);
		            Common.scrollToElement(driver, b2bPage.Product_Review);
		            Dailylog.logInfo(" step 2 Delivery Date is hidden");
		            
		            //step 3 Set to show ListPrice in HMC
					((JavascriptExecutor)driver).executeScript("(window.open(''))");
					Common.switchToWindow(driver, 1);
					driver.get(testData.HMC.getHomePageUrl());
					HMCCommon.Login(hmcPage, testData);
		            hmcPage.Home_B2BCommerceLink.click();
		            hmcPage.Home_B2BUnitLink.click();
		            Common.sleep(2000);
		            hmcPage.B2BUnit_SearchIDTextBox.clear();
		            hmcPage.B2BUnit_SearchIDTextBox.sendKeys(number);
		            hmcPage.B2BUnit_SearchButton.click();
		            Common.sleep(3000);
		            Common.doubleClick(driver, hmcPage.B2BUnit_ResultItem);
		            Common.sleep(3000);
		            hmcPage.B2CUnit_SiteAttributeTab.click();
		            Common.scrollToElement(driver, b2bPage.Product_toggle);
		            b2bPage.Product_toggle_yes.click();
		            hmcPage.PaymentLeasing_saveAndCreate.click();
		            Dailylog.logInfo(" step 3 Set to show ListPrice in HMC");
		            
		          //step 4 Go to forefront and check 'YOU SAVE' amount
		            Common.sleep(3000);
		            Common.switchToWindow(driver, 0);
		            driver.navigate().refresh();
		            Common.sleep(3000);
		            Assert.assertTrue(Common.checkElementExists(driver, b2bPage.Product_lists, 5));
		            Assert.assertTrue(Common.checkElementExists(driver, b2bPage.Product_list_price, 5));
		            Dailylog.logInfo(" step 4 check 'YOU SAVE' amount");
		            
		            //step 5 back to setting
		            Common.sleep(3000);
		            Common.switchToWindow(driver, 1);
		            Common.scrollToElement(driver, b2bPage.Product_toggle);
		            b2bPage.Product_toggle_no.click();
		            Common.sleep(3000);
		            hmcPage.PaymentLeasing_saveAndCreate.click();
		            Dailylog.logInfo(" step 5 back to setting");
				}else {
					Dailylog.logInfo(" step 1has no cto products");
				}
			}else {
				this.setManualValidateLog("product is not shown");
			}
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
}

