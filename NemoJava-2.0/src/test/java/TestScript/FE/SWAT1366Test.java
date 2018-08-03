package TestScript.FE;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * msharma2
 *  Validation for "Accessories Page UI for Deals Page"
 */
public class  SWAT1366Test  extends SuperTestClass {
	public FEPage fePage;
	public HMCPage hmcPage;

	public  SWAT1366Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT1366Test";

	}

	/*
	 * 	Amsa
	 * 	validation for accessories section in deals page
	 */

	@Test(groups={"fescripts"})
	public void deals_accessoriesTab() throws InterruptedException, MalformedURLException {
		this.prepareTest();
		boolean pass=false;
		int j=0;
		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
			System.out.println(EnvData.SitDomain+"us/en/test-new-accessories");
			driver.get("http://web-content.dit1.online.lenovo.com/us/en/p/65DAKCC3US");
			Assert.assertTrue(fePage.productTitle.isDisplayed());
			Assert.assertTrue(fePage.partNumber.isDisplayed());
			System.out.println("No of Images"+ fePage.slickDots.size());
			try{
			for(int i = 0; i<fePage.slickDots.size(); i++)
			{
				System.out.println("i test "+ i);
			//String s1=fePage.slickDots.get(i).getAttribute("class"); 
			  //  Assert.assertTrue(s1.contains("active"));
			//Validate the image display
			Assert.assertTrue(fePage.productImage.isDisplayed());
			System.out.println("i value is "+ i);
			if(i!=fePage.slickDots.size()-1)
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.slickDots.get(i+1));	
			Thread.sleep(2000);
			}
			}
			catch(Exception e){
			e.printStackTrace();
				
			}
			
		    Assert.assertTrue(fePage.productPrice.isDisplayed());
		    System.out.println("Price validated"+ fePage.productPrice);
		    Assert.assertTrue(fePage.shippingLabel.isDisplayed());
		    Assert.assertTrue(fePage.review.isDisplayed());
		    System.out.println("No of Nav Bar items are"+ fePage.navBarItem.size());
		    for(int i = 0; i<fePage.navBarItem.size(); i++)
			{
			    String s1=fePage.navBarItem.get(i).getAttribute("class"); 
			    Assert.assertTrue(s1.contains("active"));
			    String exp_title=fePage.navBarTitle.get(i).getText();
			    System.out.println("Title is"+ exp_title);
			    String actual_title=fePage.contentTitle.get(i).getText();
			    Assert.assertEquals(actual_title, exp_title);
			    System.out.println(fePage.navBarTitle.get(i).getCssValue("color"));
			    String color = cssValue(fePage.navBarTitle.get(i).getCssValue("color"));
			    System.out.println("Color value is"+ color);
			    String exp_color = "#ff6a00";
			    Assert.assertEquals(exp_color,color);
			    if(i!=fePage.navBarItem.size()-1){
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.navBarItem.get(i+1));
			Thread.sleep(2000);
			    }
			}
		    
		    pass=true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(pass);
		driver.close();
	}

	/*
	 * for fetching the css value from the page
	 */
	static String cssValue(String element){

		String[] hexValue;
		if(element.contains("a")) {
			hexValue = element.replace("rgba(", "").replace(")", "").replace(" ", "").split(",");
		}
		else {
			hexValue = element.replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
		}
		/*System.out.println(hexValue[0]);
		System.out.println(hexValue[1]);
		System.out.println(hexValue[2]);*/
		hexValue[0] = hexValue[0].trim();
		int hexValue0 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue1 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue2 = Integer.parseInt(hexValue[2]);
		String actualColor = String.format("#%02x%02x%02x", hexValue0, hexValue1, hexValue2);
		return actualColor;
	}
}
