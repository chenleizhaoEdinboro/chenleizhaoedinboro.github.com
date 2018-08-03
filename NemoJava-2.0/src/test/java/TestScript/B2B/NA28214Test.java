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

public class NA28214Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2BPage b2bPage;


	public NA28214Test(String store) {
		this.Store = store;
		this.testName = "NA-28214";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2b"})
	public void NA28214(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			String hmcURL = testData.HMC.getHomePageUrl();
			String b2bURL = testData.B2B.getLoginUrl();
			
			//login b2b and check the sort by function is diaplay
			driver.get(b2bURL);		
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			b2bPage.HomePage_productsLink.click();
			Thread.sleep(5000);
			b2bPage.HomePage_Laptop.click();
			Assert.assertTrue(Common.checkElementDisplays(driver, b2bPage.sortBy_PartNum, 10), "check the sort by partNum on Facet");		
			Assert.assertTrue(Common.checkElementDisplays(driver, b2bPage.displayNum, 10), "check the facet num on Facet");
			//b2bPage.facet_Price.click();
			//Assert.assertTrue(Common.checkElementDisplays(driver, b2bPage.facet_Price_Selection, 10), "check the price facet on Facet");	
			//check the sort by part num function and the sort num function is ready
			
			System.out.println("check a-> z");
			checkSortByPartAZNum();
			System.out.println("check z-> a");
			
			checkSortByPartZANum();
			System.out.println("check price");
			checkdisplayNum();
			//select the product type facet then check the sort by part num function and the sort num function again
			/*b2bPage.facet_ProductType.click();
			Assert.assertTrue(Common.checkElementDisplays(driver, b2bPage.facet_ProductType_Selection, 10), "check the product type on Facet");
			b2bPage.facet_ProductType_Selection.click();
			b2bPage.sortBy_PartNum_AZ.click();
			checkSortByPartAZNum();
			b2bPage.sortBy_PartNum_ZA.click();
			checkSortByPartZANum();
			checkdisplayNum();	*/
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	
	public void checkSortByPartAZNum() {
		b2bPage.sortBy_PartNum_AZ.click();
		List<WebElement> partNoList = b2bPage.PLP_PartNo;
		for(int i=2;i<=partNoList.size()-1;i++) {
			System.out.println(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+i+"]")).getText());
			System.out.println(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+(i+1)+"]")).getText());
			//Assert.assertTrue(checkPartNumSort(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+i+"]")).getText(),
			//		driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+(i+1)+"]")).getText()));
			System.out.println(checkPartNumSort(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+i+"]")).getText(),
					driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+(i+1)+"]")).getText()));
			
		}
		
	}
	public void checkSortByPartZANum() {
		b2bPage.sortBy_PartNum_ZA.click();
		List<WebElement> partNoList = b2bPage.PLP_PartNo;
		for(int i=2;i<=partNoList.size()-1;i++) {
			System.out.println(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+i+"]")).getText());
			System.out.println(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+(i+1)+"]")).getText());
			System.out.println(checkPartNumSort(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+i+"]")).getText(),
							driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+(i+1)+"]")).getText()));
			
			//Assert.assertFalse(checkPartNumSort(driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+i+"]")).getText(),
			//		driver.findElement(By.xpath("(//div[@id='resultList']//b/dl[1]/dd[1])["+(i+1)+"]")).getText()));
			
		}
	}
	public void checkdisplayNum() {
		boolean flag;
		b2bPage.displayNum_Sort5.click();
		flag=Common.isElementExist(driver, By.xpath(".//*[@id='results-area']//a[contains(.,'»')]"));
		checkDisplayNum(5,flag);
		b2bPage.displayNum_Sort10.click();
		flag=Common.isElementExist(driver, By.xpath(".//*[@id='results-area']//a[contains(.,'»')]"));
		checkDisplayNum(10,flag);	
		if(flag) {
			b2bPage.displayNum_Sort20.click();
			flag=Common.isElementExist(driver, By.xpath(".//*[@id='results-area']//a[contains(.,'»')]"));
			checkDisplayNum(10,flag);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
			Common.sleep(2000);
			checkDisplayNum(20,flag);
			if(flag) {
				b2bPage.displayNum_Sort50.click();
				flag=Common.isElementExist(driver, By.xpath(".//*[@id='results-area']//a[contains(.,'»')]"));
				checkDisplayNum(10,flag);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
				Common.sleep(2000);
				checkDisplayNum(20,flag);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
				Common.sleep(2000);
				checkDisplayNum(30,flag);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
				Common.sleep(2000);
				checkDisplayNum(40,flag);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
				Common.sleep(2000);
				checkDisplayNum(50,flag);
			}		
		}
		
		b2bPage.displayNum_SortALL.click();
		flag=Common.isElementExist(driver, By.xpath(".//*[@id='results-area']//a[contains(.,'»')]"));
		Assert.assertFalse(flag);
		checkDisplayNum(10,flag);	
	}
	
	public boolean checkDisplayNum(int num, boolean next) {
		boolean flag;
		if(b2bPage.PLP_PartNo.size()==num) {
			flag=true;
		}else if(b2bPage.PLP_PartNo.size()<=num&&(!next)){
			flag=true;
		}else {
			flag=false;		
		}
		return flag;
	}
    public boolean checkPartNumSort(String PartNum1,String PartNum2) {
    	boolean flag;
    	if(PartNum1.compareTo(PartNum2)>0){  
    		flag=false;    
        }else {
        	flag=true;  
        }
    	return flag;
    }
}
