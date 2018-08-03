package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA28040Test extends SuperTestClass{
	private B2CPage b2cPage;
	private String url = "/v4/deals/laptops";
	private String text = "lenovo";

	public NA28040Test(String store) {
		this.Store = store;
		this.testName = "NA-28040";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA28040(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String b2cHomeURL = testData.B2C.getHomePageUrl();

			//=========== Step:1 Accessing B2C URL ===========//
			driver.get(b2cHomeURL+url);
			Dailylog.logInfoDB(1, "open homelaptops success", Store, testName);

		    //===========step:2 Check tab lists, whether this links direct to right page."=======//
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Professional_laptops, 5)) {
				b2cPage.Professional_laptops.click();
			}
			if(Common.checkElementExists(driver, b2cPage.Desktops, 5)) {
				Common.sleep(3000);
				driver.get(b2cHomeURL+"/v4/deals/desktops");
			}
			if(Common.checkElementExists(driver, b2cPage.Accessories, 5)) {
				Common.sleep(3000);
				driver.get(b2cHomeURL+"/v4/deals/accessories");
			}
			Dailylog.logInfoDB(2, "Check tab lists, whether this links direct to right page", Store, testName);
			
			//=========== Step:3 Product title ===========//
			Common.sleep(3000);
			setRichTextBox(text, b2cPage.Accessories_title);
			Assert.assertTrue(b2cPage.Accessories_title.getText().contains(text));
			b2cPage.Accessories_title.click();
			Dailylog.logInfoDB(3, "Added  product into cart", Store, testName);

			//=========== Step:4 #Short description===========//
			Common.sleep(3000);
			driver.get(b2cHomeURL+url);
			Common.sleep(2000);
			setRichTextBox(text, b2cPage.Accessories_description);
			Assert.assertTrue(b2cPage.Accessories_description.getText().contains(text));
			Dailylog.logInfoDB(4, "#Short description", Store, testName);
			
			//=========== Step:5 click "see full specs"===========//
			Common.sleep(2000);
			Common.scrollToElement(driver, b2cPage.Accessories_fullSpecs);
			Common.javascriptClick(driver, b2cPage.Accessories_fullSpecs);
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_popup, 5));
			Dailylog.logInfoDB(5, "see full specs", Store, testName);
			
			//=========== Step:6 #quote"===========//
			Dailylog.logInfoDB(6, "#quote", Store, testName);
			
			//=========== Step:7 #price===========//
			Common.sleep(3000);
			driver.get(b2cHomeURL+url);
			List<WebElement> accessories_price = b2cPage.Accessories_price;
			String productPrice = accessories_price.get(accessories_price.size()-1).getText();
			List<WebElement> accessories_titlePrice = b2cPage.Accessories_titlePrice;
			Common.javascriptClick(driver, accessories_titlePrice.get(accessories_titlePrice.size()-1));
			Common.sleep(3000);
			Dailylog.logInfoDB(7, "#price", Store, testName);
			
			//=========== Step:8 #review===========//
			Common.sleep(3000);
			driver.get(b2cHomeURL+url);
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Accessories_rate, 5)) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_rate, 5));
			}
			b2cPage.Accessories_shopnow.click();
			Common.sleep(3000);
			if(Common.checkElementExists(driver, b2cPage.Accessories_pdprate, 5)) {
				Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_rate, 5));
			}
			Dailylog.logInfoDB(8, "#review", Store, testName);
			
			//=========== Step:9 #Buttons===========//
		    Common.sleep(3000);
		    if(Common.checkElementExists(driver, b2cPage.Product_model_addtocart, 5)) {
		    		b2cPage.PDP_ViewCurrentModelTab.click();
		    		List<WebElement> findElements = driver.findElements(By.xpath("//button[@id='addToCartButtonTop']"));
		    		Common.javascriptClick(driver, findElements.get(findElements.size()-1));
		     	Common.sleep(3000);
		     	Assert.assertTrue(driver.getCurrentUrl().toString().contains("customize"));
		     	Dailylog.logInfoDB(9, "#Buttons", Store, testName);
		     	//=========== Step:10 ##Add on accessories===========//
		     	if(Store.toLowerCase().contains("jp") || Store.toLowerCase().contains("us")) {
		     		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Product_AddToCartBtn, 5));
		     	}else {
		     		Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_custiomz_AddToCart, 5));
		     	}
				Dailylog.logInfoDB(10, "#Add on accessories", Store, testName);
		    }
		    if(Common.checkElementExists(driver, b2cPage.Accessories_addTocart, 5)) {
		     	b2cPage.Accessories_addTocart.click();
		     	Common.sleep(3000);
		     	Assert.assertTrue(driver.getCurrentUrl().toString().contains("cart"));
		     	Dailylog.logInfoDB(9, "#Buttons", Store, testName);
		     	//=========== Step:10 ##Add on accessories===========//
		     	Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_continue, 5));
				Dailylog.logInfoDB(10, "#Add on accessories", Store, testName);
		    }
			
		  //=========== Step:11 #Merchandising tag===========//
		  Common.sleep(3000);
		  driver.get(b2cHomeURL+url);
		  Common.sleep(3000);
		  if(Common.checkElementExists(driver, b2cPage.Accessories_Merchandising, 5)) {
			Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_Merchandising, 5));
		 }
		  Dailylog.logInfoDB(11, "#Merchandising tag", Store, testName);	
		
		//=========== Step:12 #Inventory message===========//
		  if(Common.checkElementExists(driver, b2cPage.Accessories_Inventory, 5)) {
			  Assert.assertTrue(Common.checkElementExists(driver, b2cPage.Accessories_Inventory, 5));  
		  }
		  Dailylog.logInfoDB(12, "#Inventory message", Store, testName);	
		  
		//=========== Step:13 #legal Disclaimer===========//
		  Common.sleep(3000);
		  b2cPage.Accessories_legal.click();
		  Dailylog.logInfoDB(13, "#legal Disclaimer", Store, testName);	
		  
		//=========== Step:14 #Tablet Test===========//
		  driver.get(b2cHomeURL+url);
		 Common.sleep(3000);
		 driver.manage().window().setSize(new Dimension(768, 1024));
		 if(Common.checkElementExists(driver, b2cPage.Accessories_tablets, 5)) {
			 b2cPage.Accessories_tablets.click(); 
		 }
		 Dailylog.logInfoDB(14, "#Tablet Test", Store, testName);
		  
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	
	protected void setRichTextBox(String text, WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].innerHTML = \"" + text + "\"", webElement);
	}
}
