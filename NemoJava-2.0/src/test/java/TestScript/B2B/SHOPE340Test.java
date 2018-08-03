package TestScript.B2B;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import CommonFunction.B2BCommon;
import CommonFunction.HMCCommon;

public class SHOPE340Test extends SuperTestClass {
	//public B2CPage b2cPage;
	public HMCPage hmcPage;
	public B2BPage b2bPage;
	String partNumber1;
	String partNumber2;
	String ID1;
	String ID2;
	
	public SHOPE340Test(String store,String country,String unit) {
		this.Store = store;
		this.testName = "SHOPE-340";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "p2", "b2b" })
	public void SHOP340 (ITestContext ctx) throws InterruptedException {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			//b2cPage = new B2CPage(driver);
			b2bPage = new B2BPage(driver);
			partNumber1 = "10FGS00F0L";
			partNumber2 = "20ENCTOEDU_SAP";
			ID1="1213286247";
		//	ID2="1213410863";
			ID2="1213348423";
			//System.out.println(testData.B2B.getHomePageUrl());
			//driver.get(testData.B2B.getHomePageUrl());
			int[] Toggle = {7,4,2,0};
			for(int i =0;i< Toggle.length;i++) {
				enableToggle(driver,hmcPage,Toggle[i],ID1);
				gotoCMCTOB2Bwebsite(driver, b2bPage,Toggle[i]);
				enableToggle(driver,hmcPage,Toggle[i],ID2);
				gotoB2Bwebsite(driver, b2bPage,Toggle[i]);
			}
		}catch (Throwable e) {
			handleThrowable(e, ctx);
			
		}
	//Roll back
		enableToggle(driver,hmcPage,7,ID1);
		enableToggle(driver,hmcPage,7,ID2);
	}
	
	public void enableToggle(WebDriver driver,HMCPage hmcPage,int num,String ID) throws InterruptedException {
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(ID);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
		// Accessory = 1 or 0; Customise Buy =2 or 0; Customise Buy For CM =4 or 0;
		// num = sum(Accessory,Customise Buy,Customise Buy For CM)
		if (num==7) {
			System.out.println("Accessory Add,Customise Buy  and Customise Buy For CM All Enale");
			//Enable Accessory Add Toggle
			//driver.findElement(By.xpath("//*[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.accesoryAddToggle][1]]_spantrue']")).click();
			hmcPage.AccessoryAddToggle.click();
			//Enable Customise Buy Toggle
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spantrue']")).click();
			
			//Enable Customise Buy For CM Toggle
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spantrue']")).click();
			Dailylog.logInfoDB(0,"All three toggles are enabled" , Store, testName);
			
			
		}else if (num==0) {
			System.out.println("Accessory Add,Customise Buy  and Customise Buy For CM All Disable");
			hmcPage.AccessoryAddToggleFalse.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spanfalse']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spanfalse']")).click();
			Dailylog.logInfoDB(1,"All three toggles are disabled" , Store, testName);
		}else if (num==1) {
			hmcPage.AccessoryAddToggle.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spanfalse']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spanfalse']")).click();
			Dailylog.logInfoDB(2,"Only Accessory Add toggle enabled" , Store, testName);	
			
		}else if (num==2) {
			System.out.println("Customise Buy Enale");
			hmcPage.AccessoryAddToggleFalse.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spantrue']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spanfalse']")).click();
			Dailylog.logInfoDB(2,"Only Customis Buy toggle enabled" , Store, testName);	
		}else if (num==3) {
			hmcPage.AccessoryAddToggle.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spantrue']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spanfalse']")).click();
			Dailylog.logInfoDB(3,"Accessory Add and Customise Buy toggles are enabled" , Store, testName);	
		}else if (num==4) {
			System.out.println("Customise Buy For CM All Enale");
			hmcPage.AccessoryAddToggleFalse.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spanfalse']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spantrue']")).click();
			Dailylog.logInfoDB(4,"Only Customise Buy For CM toggle is enabled" , Store, testName);	
		}else if (num==5) {
			hmcPage.AccessoryAddToggle.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spanfalse']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spantrue']")).click();
			Dailylog.logInfoDB(5,"Accessory Add and Customise Buy For CM toggles are enabled" , Store, testName);
		}else if (num==6) {
			hmcPage.AccessoryAddToggleFalse.click();
			driver.findElement(By.xpath("//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spantrue']")).click();
			driver.findElement(By.xpath( "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_spantrue']")).click();
			Dailylog.logInfoDB(6,"Customise Buy and Customise Buy For CM toggles are enabled" , Store, testName);
		}
		//click save button
		//driver.findElement(By.xpath("//div[contains(@id,'organizer.editor.save.title')]")).click();
		hmcPage.SaveButton.click();
		Common.sleep(2000);
		cleanRadis(driver,hmcPage, partNumber1);
		Common.sleep(2000);
		hmcPage.Home_System.click();
		cleanRadis(driver,hmcPage, partNumber2);
		Common.sleep(2000);
		hmcPage.hmcHome_hmcSignOut.click();
		
	}
	
	public static void cleanRadis(WebDriver driver,HMCPage hmcPage, String partNum)
			throws InterruptedException {
		hmcPage.Home_System.click();
		//hmcPage.Home_RadisCacheCleanLink.click();
		driver.findElement(By.xpath(".//a[@id='Tree/StaticContentLeaf[2]_label']")).click();
		Thread.sleep(5000);
		hmcPage.PageDriver
				.switchTo()
				.frame(hmcPage.PageDriver.findElement(By
						.xpath(".//iframe[contains(@src,'nemoClearCachePage')]")));
		hmcPage.Radis_CleanProductTextBox.clear();
		hmcPage.Radis_CleanProductTextBox.sendKeys(partNum);
		hmcPage.Radis_CleanButton.click();
		Common.waitAlertPresent(hmcPage.PageDriver, 60);
		hmcPage.PageDriver.switchTo().alert().accept();
		hmcPage.PageDriver.switchTo().defaultContent();
	}
	
	public void gotoCMCTOB2Bwebsite(WebDriver driver,B2BPage b2bPage,int num) {
		boolean flag;
		String url = "https://pre-c-hybris.lenovo.com/le/1213286247/au/en/1213286247/p/10FGS00F0L";
		//driver.get(testData.B2B.getHomePageUrl());
		driver.get(url);
		driver.manage().deleteAllCookies();
		driver.get(url);
		//System.out.println(testData.B2B.getBuyerId());
		//System.out.println(testData.B2B.getDefaultPassword());
		B2BCommon.Login(b2bPage, "liyh36@lenovo.com", "1q2w3e4r");
		Dailylog.logInfoDB(01,"Login website successfully" , Store, testName);
		driver.get(url);
		//b2cPage.Navigation_ProductsLink.click();
		driver.findElement(By.xpath("//li[contains(@class,'product')]/a/span")).click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//*[@id='Desktops & All-In-Ones']")).click();
		Common.sleep(2000);	
		String addtoCartxpath="//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//img[@title='Add to Cart']";
		String Accessoriesxpath = "//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//button[contains(text(),'Accessorize & Buy')]";
		String customizeAndBuy = "//*[@id='resultList']//dd[contains(text(),'"+partNumber1+"')]/../../../../../../div[@class='facetedResults-footer']//a[contains(text(),'Customize and buy')]";
		if(num==7) {
			//Check CM+CTO ADDTOCART button display
			flag = Common.checkElementDisplays(driver, By.xpath(addtoCartxpath), 3);
			Assert.assertTrue(flag,"Add To Cart button not display on web page");
			Dailylog.logInfoDB(11,"Add To Cart button is displayed on web page" , Store, testName);
			//Check CM+CTO Accessories button display
			flag = Common.checkElementDisplays(driver,By.xpath(Accessoriesxpath), 3);
			Assert.assertTrue(flag,"Add Accessories button not display on web page");
			Dailylog.logInfoDB(12,"Add Accessories button is displayed on web page" , Store, testName);
			//Check CM+CTO Customize And Buy button display
			//String xpath= "//form[contains(@id,'addToAccessorisForm')]/a";
			//String xpath= ".//*[@id='resultList'][1]/div/div[4]/a";
			flag = Common.checkElementDisplays(driver,By.xpath(customizeAndBuy), 3);
			Assert.assertTrue(flag,"Customize And Buy button not display on web page");
			Dailylog.logInfoDB(13,"Customize And Buy button is displayed on web page" , Store, testName);
			Common.sleep(2000);
		}else if (num ==4) {
			//Check CM+CTO ADDTOCART button display
			flag = Common.checkElementDisplays(driver, By.xpath(addtoCartxpath), 3);
			Assert.assertTrue(flag,"Add To Cart button not display on web page");
			Dailylog.logInfoDB(15,"Add To Cart button is displayed on web page" , Store, testName);
			//Check CM+CTO Accessories button not display
			flag = Common.checkElementDisplays(driver,By.xpath(Accessoriesxpath), 3);
			Assert.assertFalse(flag,"Add Accessories button display on web page");
			Dailylog.logInfoDB(17,"Add Accessories button is displayed on web page" , Store, testName);
			//Check CM+CTO Customize And Buy button display
			//String xpath= ".//*[@id='resultList'][1]/div/div[4]/a";
			flag = Common.checkElementDisplays(driver,By.xpath(customizeAndBuy), 3);
			Assert.assertTrue(flag,"Customize And Buy button not display on web page");
			Dailylog.logInfoDB(19,"Customize And Buy button is displayed on web page" , Store, testName);
			Common.sleep(2000);
		}else if (num ==0) {
			//Check CM+CTO ADDTOCART button display
			flag = Common.checkElementDisplays(driver, By.xpath(addtoCartxpath), 3);
			Assert.assertTrue(flag,"Add To Cart button not display on web page");
			Dailylog.logInfoDB(21,"Add To Cart button is displayed on web page" , Store, testName);
			//Check CM+CTO Accessories button not display
			flag = Common.checkElementDisplays(driver,By.xpath(Accessoriesxpath), 3);
			Assert.assertFalse(flag,"Add Accessories button display on web page");
			Dailylog.logInfoDB(23,"Add Accessories button is not displayed on web page" , Store, testName);
			//Check CM+CTO Customize And Buy button display
			//String xpath= ".//*[@id='resultList'][1]/div/div[4]/a";
			flag = Common.checkElementDisplays(driver,By.xpath(customizeAndBuy), 3);
			Assert.assertFalse(flag,"Customize And Buy button display on web page");
			Dailylog.logInfoDB(25,"Customize And Buy button is not displayed on web page" , Store, testName);
			Common.sleep(2000);
		}else if (num ==2) {
			//Check CM+CTO ADDTOCART button display
			flag = Common.checkElementDisplays(driver, By.xpath(addtoCartxpath), 3);
			Assert.assertTrue(flag,"Add To Cart button not display on web page");
			Dailylog.logInfoDB(27,"Add To Cart button is displayed on web page" , Store, testName);
			//Check CM+CTO Accessories button not display
			flag = Common.checkElementDisplays(driver, By.xpath(Accessoriesxpath), 3);
			Assert.assertFalse(flag,"Add Accessories button not display on web page");
			Dailylog.logInfoDB(29,"Add Accessories button is not displayed on web page" , Store, testName);
			//Check CM+CTO Customize And Buy button display
			//String xpath= ".//*[@id='resultList'][1]/div/div[4]/a";
			flag = Common.checkElementDisplays(driver,By.xpath(customizeAndBuy), 3);
			Assert.assertFalse(flag,"Customize And Buy button display on web page");
			Dailylog.logInfoDB(31,"Customize And Buy button is not displayed on web page" , Store, testName);
			Common.sleep(2000);
		}
	}

	public void gotoB2Bwebsite(WebDriver driver,B2BPage b2bPage,int num) {
		boolean flag;
		String url = "https://pre-c-hybris.lenovo.com/le/1213348423/us/en/1213410863";
		//driver.get(testData.B2B.getHomePageUrl());
		driver.get(url);
		driver.manage().deleteAllCookies();
		driver.get(url);
		//System.out.println(testData.B2B.getBuyerId());
		//System.out.println(testData.B2B.getDefaultPassword());
		B2BCommon.Login(b2bPage, "usbuyer@sharklasers.com", "1q2w3e4r");
		Dailylog.logInfoDB(0,"Login website successfully" , Store, testName);
		//driver.get(url);
		//b2cPage.Navigation_ProductsLink.click();
		driver.findElement(By.xpath("//li[contains(@class,'product')]/a/span")).click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//*[@id='Laptops & Ultrabooks']")).click();
		driver.findElement(By.xpath("//h3[contains(text(),'Contract & Agreement')]")).click();
		driver.findElement(By.xpath("//*[@id='facet2_1']/form/label")).click();
		Common.sleep(2000);	
		//String xpath2= "//*[@id='resultList']//dd[contains(text(),'20ENCTOEDU_SAP')]/../../../../../../div[@class='facetedResults-footer']/a[@class='button-called-out button-full button-bottom-margin ']";
		String xpath2= "//*[@id='resultList']//dd[contains(text(),'"+partNumber2+"')]/../../../../../../div[@class='facetedResults-footer']//img";
		if(num==7) {
			Common.sleep(60000);
			//Check CTO Customize And Buy button display
			flag = Common.checkElementDisplays(driver, By.xpath(xpath2), 3);
			Assert.assertTrue(flag,"Customize And Buy button not display on web page");
			Dailylog.logInfoDB(14,"Customize And Buy button is displayed on web page" , Store, testName);
		}else if (num ==4) {
			//Check CTO Customize And Buy button display
			flag = Common.checkElementDisplays(driver, By.xpath(xpath2), 3);
			Assert.assertFalse(flag,"Customize And Buy button display on web page");
			Dailylog.logInfoDB(16,"Customize And Buy button is not displayed on web page" , Store, testName);
		}else if (num ==0) {
			//Check CTO Customize And Buy button display
			flag = Common.checkElementDisplays(driver, By.xpath(xpath2), 3);
			Assert.assertFalse(flag,"Customize And Buy button display on web page");
			Dailylog.logInfoDB(18,"Customize And Buy button is not displayed on web page" , Store, testName);
		}else if (num ==2) {
			//Check CTO Customize button display
			flag = Common.checkElementDisplays(driver, By.xpath(xpath2), 3);
			Assert.assertTrue(flag,"Customize And Buy button not display on web page");
			Dailylog.logInfoDB(20,"Customize And Buy button is displayed on web page" , Store, testName);
		}
	}

}
