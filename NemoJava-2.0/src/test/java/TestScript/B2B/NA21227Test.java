package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21227Test extends SuperTestClass{
	
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	
	 
	public NA21227Test(String Store){
		this.Store = Store;
		this.testName = "NA-21227";
	}
	
	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","catalogcontract",  "p2", "b2b"})
	public void NA21227(ITestContext ctx){
	
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			
			// 1, log on web , and go to accessory page 
			Dailylog.logInfoDB(1, "log on web , and go to accessory page", Store, testName);
			
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			
			b2bPage.HomePage_AccessoriesLink.click();
			
			System.out.println("current url is :" + driver.getCurrentUrl());
			
			Assert.assertTrue(driver.getCurrentUrl().contains("accessories"));
			
			//2~3 ,click Select  your category and choose one category , the category should contains at least one product 
			Dailylog.logInfoDB(2, "click Select  your category and choose one category , the category should contains at least one product", Store, testName);
			
			
			List<WebElement> categoryList = driver.findElements(By.xpath("//*[@id='extraOptions-chooseCategroy']/option[@label]"));
			
			for(int x = 1; x <= categoryList.size(); x++){
				driver.findElement(By.xpath(".//*[@id='extraOptions-chooseCategroy']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("(//*[@id='extraOptions-chooseCategroy']/option[@label])["+x+"]")).click();
				
				new WebDriverWait(driver,50000).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='facet-area']"))));
				
				List<WebElement> productList1 = new ArrayList<WebElement>();
				
				if(Common.isElementExist(driver, By.xpath("//ol[contains(@class,'accessoriesListing')]/li"))){
					productList1 = driver.findElements(By.xpath("//ol[contains(@class,'accessoriesListing')]/li"));
					
				}
				
				List<WebElement> productList2 = new ArrayList<WebElement>();
				
				if(Common.isElementExist(driver, By.xpath("//*[@id='resultList']/div"))){
					productList2 = driver.findElements(By.xpath("//*[@id='resultList']/div"));
				}
				
				
				Assert.assertTrue((productList1.size() + productList2.size()) >= 1);
				
				b2bPage.HomePage_AccessoriesLink.click();
				
			}
			
			
			//4, the url should be same 
			Dailylog.logInfoDB(4, "the url should be same ", Store, testName);
			
			
			ArrayList<String> al = new ArrayList<String>();
			
			for(int x = 1; x <= categoryList.size(); x++){
				driver.findElement(By.xpath(".//*[@id='extraOptions-chooseCategroy']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("(//*[@id='extraOptions-chooseCategroy']/option[@label])["+x+"]")).click();
				
				new WebDriverWait(driver,50000).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='facet-area']"))));
				
				String url = driver.getCurrentUrl().toString();
				System.out.println("url is :" + url);
				
				al.add(url);

				b2bPage.HomePage_AccessoriesLink.click();
			}
			
			System.out.println("al is :" + al.toString());
			
			b2bPage.HomePage_AccessoriesLink.click();
			
			List<WebElement> list_Component = driver.findElements(By.xpath("//div[@class='yCmsComponent']/div[@class='prod_cat']"));
			
			for(int x = 1; x <= list_Component.size(); x++){
				driver.findElement(By.xpath("(//div[@class='yCmsComponent']/div[@class='prod_cat']/div/h2/a)["+x+"]")).click();
				
				String currentUrl = driver.getCurrentUrl().toString();
				System.out.println("currentUrl is :" + currentUrl);
				
				Assert.assertTrue(al.contains(currentUrl));
				
				b2bPage.HomePage_AccessoriesLink.click();
			}
			
			
		}catch(Throwable e){	
			handleThrowable(e, ctx);
		}
	}

}
