package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class DR19Test extends SuperTestClass {
	public B2CPage b2cPage;

	public DR19Test(String store) {
		this.Store = store;
		this.testName = "DR-19";
	}

	@Test(alwaysRun = true, groups= {"content","dr","p2","b2c"})
	public void DR18(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			
			// Skip HMC steps as GB and IE are DR country by default
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultMTMPN());
			
			// Test stock message is shown
			if(!Common.checkElementDisplays(driver, By.className("stock_message"), 10))
				Assert.fail("Stock message is not shown!");
			
			driver.get(testData.B2C.getHomePageUrl() + "/laptops/c/LAPTOPS?q=%3Aprice-asc%3AfacetSys-ScreenSize%3A14&uq=&text=#");
			WebElement noStockMsg = driver.findElement(By.xpath("//div/span/b/font[@color='red']"));
			String pn = noStockMsg.findElement(By.xpath("//following-sibling::input[@name='digitalRiverProductCodeProductList']")).getAttribute("code");
			
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + pn);
			if(driver.findElement(By.id("addToCartButtonTop")).isEnabled())
				Assert.fail("Add to Cart button is enabled!");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
