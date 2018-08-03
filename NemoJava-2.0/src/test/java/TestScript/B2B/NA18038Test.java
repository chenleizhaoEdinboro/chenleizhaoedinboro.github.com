package TestScript.B2B;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2BPage;
import TestScript.SuperTestClass;

public class NA18038Test extends SuperTestClass{
	public B2BPage b2bPage;
	String timeStamp = Common.getDateTimeString();
	
	public NA18038Test(String Store){
		this.Store = Store;
		this.testName = "NA-18038";
	}
	
	
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2b"})
	public void NA18038(ITestContext ctx) throws Exception {
		
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			String b2bLoginURL = testData.B2B.getLoginUrl();
			String emptyCartButton = ".//*[@id='emptyCartItemsForm']/a";
			String companyCartname = "CompanyCart";
			String baseB2BUnit = testData.B2B.getB2BUnit();
			
			//============= Step:1 Login to B2B Website  ============//
			driver.get(b2bLoginURL);
			Common.sleep(3000);
			B2BCommon.Login(b2bPage, testData.B2B.getBuilderId(), testData.B2B.getDefaultPassword());
			Common.sleep(3000);
			Dailylog.logInfoDB(1, "Successfully logged into B2B site", Store, testName);
			Assert.assertTrue(b2bPage.homepage_MyAccount.isDisplayed());

			//============= Step:2 Add a product to cart and save it  ============//
			b2bPage.HomePage_CartIcon.click();
			if(Common.isElementExist(driver, By.xpath(emptyCartButton))){
				driver.findElement(By.xpath(emptyCartButton)).click();
				Dailylog.logInfoDB(2, "Cart is cleared", Store, testName);
			}
			Common.sleep(3000);
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			Dailylog.logInfoDB(2, "Product has been added to cart.", Store, testName);
			Assert.assertTrue(b2bPage.CartPage_ProductPartNum.isDisplayed());
			//Click Save Cart button
			b2bPage.cartPage_saveCartButton.click();
			//Save cart as Company Cart
			b2bPage.cartPage_companySaveCartButton.click();
			b2bPage.cartPage_SaveCart_cartNameField.sendKeys(companyCartname + timeStamp);
			b2bPage.cartPage_SaveCart_save.click();
			Dailylog.logInfoDB(2, "Cart has been saved.", Store, testName);
			Common.sleep(2000);
			Assert.assertTrue(driver.findElement(By.xpath("(//tr/td/h3[contains(.,'" + companyCartname + timeStamp + "')])[1]")).isDisplayed(),"Saved cart should be displayed.");
			
			//============= Step:3 Add one more product to cart  ============//
			b2bPage.HomePage_CartIcon.click();
			Common.sleep(2000);
			B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
			Dailylog.logInfoDB(3, "Added another product to cart.", Store, testName);
			Common.sleep(2000);
			
			//============= Step:4 Click sold to button  ============//
			Common.scrollToElement(driver, b2bPage.CartPage_ChangeSoldToDropdown);
			b2bPage.CartPage_ChangeSoldToDropdown.click();
			/*new WebDriverWait(driver, 500).until(ExpectedConditions
					.elementToBeClickable(b2bPage.ChangeSoldTo_SelectCountry));*/
			Thread.sleep(3000);
			
	         // driver.findElement(By.xpath("((//div[contains(@class,'dropdown-menu store-selector')]/div)[2]//a)[1]")).click();;
			b2bPage.ChangeSoldTo_SelectCountry.click();

			
			b2bPage.ChangeSoldTo_ChangeStore.click();
			Dailylog.logInfoDB(4, "Clicked on different store", Store, testName);
			
			
			//============= Step:5 Click on Cancel and close the pop up window  ============//
			//TODO: Add code for warning pop up
			
			//============= Step:6 Click sold to button  ============//
			if (Common.isAlertPresent(driver)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
			Common.scrollToElement(driver, b2bPage.CartPage_ChangeSoldToDropdown);
			b2bPage.CartPage_ChangeSoldToDropdown.click();
			b2bPage.ChangeSoldTo_SelectCountry.click();
			b2bPage.ChangeSoldTo_ChangeStore.click();
			if (Common.isAlertPresent(driver)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
			//============= Step:7 Click confirm button on pop up  ============//
			//TODO: Add code to confirm on warning pop up
			
			//============= Step:8 Go to Cart Page  ============//
			b2bPage.HomePage_CartIcon.click();
			Common.sleep(2000);
			Assert.assertTrue(b2bPage.CartPage_EmptyCartMessage.isDisplayed());
			String emptyCartMsg = b2bPage.CartPage_EmptyCartMessage.getText();
			Dailylog.logInfoDB(8, emptyCartMsg, Store, testName);
			
			//============= Step:9 Go to My Account and view saved cart  ============//
			b2bPage.homepage_MyAccount.click();
			Common.sleep(1500);
			b2bPage.myAccountPage_viewCartHistory.click();
			Assert.assertTrue(driver.findElement(By.xpath("(//tr/td/h3[contains(.,'" + companyCartname + timeStamp + "')])[1]")).isDisplayed());
			driver.findElement(By.xpath("(//h3[contains(.,'" + companyCartname + timeStamp + "')]/../../td/following-sibling::td[@data-title='viewCart']/a/div)[1]")).click();
			Common.sleep(4000);
			
			//============= Step:10 View Saved Cart  ============//
			String currentURL = driver.getCurrentUrl();
			Assert.assertTrue(currentURL.contains(baseB2BUnit));
			
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
}
