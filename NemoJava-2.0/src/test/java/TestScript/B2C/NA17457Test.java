package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA17457Test extends SuperTestClass{
	private B2CPage b2cPage;
	private String aboutLenovoLinks = "(//h3[contains(.,'ABOUT LENOVO')]/../ul/li)";
	
	public NA17457Test(String store) {
		this.Store = store;
		this.testName = "NA-17457";
	}
	@Test
	public void NA17457(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl());
			Common.sleep(2000);
			b2cPage.HomePage_AboutLenovoLinks.click();
			int totalLinks = driver.findElements(By.xpath(aboutLenovoLinks)).size();
			for(int i=1;i<totalLinks;i++){
				aboutLenovoLinkVerification(i);
			}
			
			
		}
		catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	private void aboutLenovoLinkVerification(int j){
		String pageTitle = driver.getTitle();
		driver.findElement(By.xpath(aboutLenovoLinks + "[" + j + "]")).click();
		Common.sleep(4000);
		String pageTitleAfterClicking = driver.getTitle();
		if(pageTitle.equalsIgnoreCase(pageTitleAfterClicking)){
			Common.switchToWindow(driver, 1);
			Common.sleep(3000);
			String currentURL = driver.getCurrentUrl();
			Assert.assertFalse(currentURL.equalsIgnoreCase("shop.lenovo"));
			Assert.assertFalse(currentURL.equalsIgnoreCase("www.lenovo.com"));
			Assert.assertFalse(currentURL.equalsIgnoreCase("ap.lenovo.com"));
			Assert.assertFalse(currentURL.equalsIgnoreCase("page.html"));
			driver.close();
			Common.switchToWindow(driver, 0);
		}else{
			String currentURL = driver.getCurrentUrl();
			Assert.assertFalse(currentURL.equalsIgnoreCase("shop.lenovo"));
			Assert.assertFalse(currentURL.equalsIgnoreCase("www.lenovo.com"));
			Assert.assertFalse(currentURL.equalsIgnoreCase("ap.lenovo.com"));
			Assert.assertFalse(currentURL.equalsIgnoreCase("page.html"));
			Common.sleep(3000);
			driver.navigate().back();
			Common.sleep(3000);
			b2cPage.HomePage_AboutLenovoLinks.click();
		}
		
	}
}
