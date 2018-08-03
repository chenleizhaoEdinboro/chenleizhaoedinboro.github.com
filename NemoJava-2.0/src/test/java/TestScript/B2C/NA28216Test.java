package TestScript.B2C;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA28216Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;

	public NA28216Test(String store) {
		this.Store = store;
		this.testName = "NA-28216";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28216(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			// 1, Navigate to Accessories page of B2C
			Dailylog.logInfoDB(1, "Navigate to Accessories page of B2C", Store, testName);
			
			//open url: https://pre-c-hybris.lenovo.com/au/en/auweb
			driver.get(testData.B2C.getHomePageUrl());
			
			//navigate to "Accessories & Monitors" page
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
			
			//judge if there is a popup page and closed it
			if(Common.checkElementDisplays(driver, By.xpath(".//*[@id='myModal']//span[@class='numberclose']/img"), 10)){
				driver.findElement(By.xpath(".//*[@id='myModal']//span[@class='numberclose']/img")).click();
			}
			
			//assert if there is a icon before the "Browse All Categories" tab
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//div[@class='accessoriesCategories']/div/span[1]")), "accessories page failed open");
			
			//2, Check for L2 category
			Dailylog.logInfoDB(2, "Check for L2 category", Store, testName);
			
			String accessories_homePage = driver.getCurrentUrl().toString();
			
			System.out.println("accessories_homePage is :" + accessories_homePage);
			//modify //div[@class='accessoriesCategories']/div/span[1] to //span[@class='accessoriesCategoriesTitle searchBoxLables']
			driver.findElement(By.xpath("//span[@class='accessoriesCategoriesTitle searchBoxLables']")).click();
			Thread.sleep(3000);
			
			List<WebElement> allCategory = driver.findElements(By.xpath("//div[@id='browse-all-categories']/div"));
			
			System.out.println("allCategory is :" + allCategory.size());
			
			for (int i = 1; i <= allCategory.size(); i++) {
				
				Common.scrollToElement(driver, driver.findElement(By.xpath("(//div[@id='browse-all-categories']/div)["+i+"]")));
				
				//Thread.sleep(6000);
				
				List<WebElement> subCategoryList = driver.findElements(By.xpath("(//div[@id='browse-all-categories']/div)["+i+"]/ul/li"));
				
				System.out.println("i start is :" + i);
				
				for (int j = 1; j <= subCategoryList.size(); j++) {
					System.out.println("j start  is :" + j);
					
					//Common.sleep(3000);
					
					String categoryName = driver.findElement(By.xpath("((//div[@id='browse-all-categories']/div)["+i+"]/ul/li)["+j+"]//a")).getText().toString();
					
					System.out.println("categoryName is :" + categoryName);
					
					closeHomePagePopUP(driver);
					
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].click()", driver.findElement(By.xpath("((//div[@id='browse-all-categories']/div)["+i+"]/ul/li)["+j+"]//a")));
					
					//Common.sleep(3000);

					boolean flag = false;
					
					if(Common.isElementExist(driver, By.xpath("//*[@id='addToCartButtonTop']"))){
					
						List<WebElement> list_product = driver.findElements(By.xpath(".//*[@id='addToCartButtonTop']"));
						
						Assert.assertTrue(list_product.size() >= 1, "check the product number under " + categoryName);
						
						for (int k = 1; k <= list_product.size(); k++) {
							flag = driver.findElement(By.xpath("(//*[@id='addToCartButtonTop'])["+k+"]")).isEnabled();
							if(flag == true){
								break;
							}
							
						}
						
						Assert.assertTrue(flag, "under " + categoryName + ", no product is available");
					}else{
						Assert.assertTrue(false, "under " + categoryName + ", no product is available");
					}
					
					driver.get(accessories_homePage);
					//Thread.sleep(6000);
					
					closeHomePagePopUP(driver);
					
					driver.findElement(By.xpath("//div[@class='accessoriesCategories']/div/span[1]")).click();
					//Thread.sleep(6000);
					System.out.println("j end  is :" + j);
					
				}	
				
				
				System.out.println("i end is :" + i);
			}
			
			//3, Check for L1 category
			Dailylog.logInfoDB(3, "Check for L1 category", Store, testName);
			
			ArrayList<String> aList = new ArrayList<String>();
			
			for (int a = 1; a <= allCategory.size(); a++) {
				
				Common.scrollToElement(driver, driver.findElement(By.xpath("(//div[@id='browse-all-categories']/div)["+a+"]")));
				
				//Thread.sleep(6000);
				
				String name = driver.findElement(By.xpath("(//div[@id='browse-all-categories']/div)["+a+"]/div/h3/a")).getText();
				aList.add(name);				
				
				List<WebElement> subCategoryList = driver.findElements(By.xpath("(//div[@id='browse-all-categories']/div)["+a+"]/ul/li"));
				
				Assert.assertTrue(subCategoryList.size() != 0 ,"Check for L1 category");
				
			}
			
			//4, Removal some categories
			Dailylog.logInfoDB(4, "Removal some categories", Store, testName);
			
			String removal_1 = "Accessories & Upgrades";
			String removel_2 = "Visuals";
			
			Assert.assertTrue(!aList.contains(removal_1),"Accessories & Upgrades not removed");
			Assert.assertTrue(!aList.contains(removel_2),"Visuals not removed");
			
			//5, Navigation functionality
			Dailylog.logInfoDB(5, "Navigation functionality", Store, testName);
			
			driver.get(accessories_homePage);
			
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Accessories);
			
			if(Common.checkElementDisplays(driver, By.xpath(".//*[@id='myModal']//span[@class='numberclose']/img"), 10)){
				driver.findElement(By.xpath(".//*[@id='myModal']//span[@class='numberclose']/img")).click();
			}
			
			driver.findElement(By.xpath("//div[@class='accessoriesCategories']/div/span[1]")).click();
			//Thread.sleep(5000);
			
			String level2_name = driver.findElement(By.xpath("((//div[@id='browse-all-categories']/div)[1]/ul/li)[1]//a")).getText().toString();
			System.out.println(level2_name);
			
			Assert.assertTrue(driver.findElement(By.xpath("((//div[@id='browse-all-categories']/div)[1]/ul/li)[1]//a")).isEnabled(),"can not go to level 2 from the category homepage");
			
			//Thread.sleep(6000);
			driver.findElement(By.xpath("(//div[@id='browse-all-categories']/div)[1]/div/h3/a")).click();
			//Thread.sleep(4000);	
			
			ArrayList<String> aList2 = new ArrayList<String>();
			
			List<WebElement> aList3 = driver.findElements(By.xpath("//div[@class='accessoriesList-title']"));
			
			for (int n = 1; n <= aList3.size(); n++) {
				
				String name = driver.findElement(By.xpath("(//div[@class='accessoriesList-title'])["+n+"]")).getText();
				name = name.split("(")[0];
				aList2.add(name);
				System.out.println(name);
			}
			
			Assert.assertTrue(aList2.contains(level2_name), "failed go to level 2 from level 1");

			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void closeHomePagePopUP(WebDriver driver) {
		String productNumberPopUp = "//span[@class='numberclose']/img";

	 if (Common.checkElementDisplays(driver, By.xpath(productNumberPopUp), 4)) {
			driver.findElement(By.xpath(productNumberPopUp)).click();
		}
	}

}
