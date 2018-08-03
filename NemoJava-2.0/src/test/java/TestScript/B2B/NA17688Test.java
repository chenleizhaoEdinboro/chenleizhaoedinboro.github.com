package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA17688Test extends SuperTestClass {

	public B2BPage b2bPage;

	public NA17688Test(String store) {
		this.Store = store;
		this.testName = "NA-17688";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2b"})
	public void NA17688(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			
			//Step 1, login in with telesales
			Dailylog.logInfoDB(1, "login in with telesales", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getTelesalesId(), testData.B2B.getDefaultPassword());
			
			//Step 2, click My Account
			Dailylog.logInfoDB(2, "click My Account", Store, testName);
			
			b2bPage.myAccount_link.click();
			
			
			//Step 3, click start ASM
			Dailylog.logInfoDB(3, "click start ASM", Store, testName);
			
			b2bPage.telesales_startASM.click();
			
			//Step 4, go to advanced customer search page and click search button
			B2BCommon.loginASM(driver, b2bPage, testData);

			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			
			B2CCommon.closeHomePagePopUP(driver);
			
			driver.findElement(By.xpath("//*[@id='showCustomerSearch']/span")).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(".//*[@id='advancedCustomerSearch']")).click();
			Thread.sleep(20000);
			
			List<WebElement> customerList = driver.findElements(By.xpath("//tr[@class='b2bCustomer']"));
			
			Assert.assertTrue(customerList.size() >= 1);
			
			String store = driver.findElement(By.xpath("(//tr[@class='b2bCustomer'])[1]/td[5]")).getText().toString();
			
			Assert.assertTrue(store.equals(testData.B2B.getB2BUnit()));
			
			
		}catch (Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	
}
