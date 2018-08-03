package TestScript.FE;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * msharma2
 *  Validation for "create legal component"
 */
public class  SWAT1241Test  extends SuperTestClass {
	public FEPage fePage;
	public HMCPage hmcPage;

	public  SWAT1241Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT1241Test";

	}

	
	/*
	 * 	msharma2
	 * 	validation for creating legal component
	 */

	@Test(groups={"fescripts"})
	public void createLegal_component() throws InterruptedException, MalformedURLException {
		this.prepareTest();
		boolean pass=false;
	
		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
			System.out.println(EnvData.SitDomain+"ww-hp");
			driver.get(EnvData.SitDomain+"us/en/ww-hp#");

			Thread.sleep(15000);
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",fePage.legalComponent);
			Thread.sleep(10000);
			System.out.println(fePage.legalComponent.getAttribute("class"));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.legalComponent.findElement(By.tagName("a")));
			Thread.sleep(2000);
			System.out.println(fePage.legalComponent.getAttribute("class"));
			Thread.sleep(10000);
			Assert.assertTrue(fePage.legalComponent.getAttribute("class").contains("active"));				
			Assert.assertTrue(fePage.legalComponentContent.isDisplayed());
			
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
		System.out.println(hexValue[0]);
		System.out.println(hexValue[1]);
		System.out.println(hexValue[2]);
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
