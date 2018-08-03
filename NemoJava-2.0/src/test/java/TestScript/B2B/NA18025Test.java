package TestScript.B2B;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18025Test extends SuperTestClass{
	private B2BPage b2bPage;
	private HMCPage hmcPage;	
	private String completeShippingRadioBtn = "//table[@title='enableCompleteShipping']//div/input[@value='";
	private String groupShippingRadioBtn = "//table[@title='enableGroupShipping']//div/input[@value='";
	private String lineItemShippingRadioBtn = "//table[@title='enableLineItemShipping']//div/input[@value='";
	private String CRADRadioBtn = "//table[@title='enableCRAD']//div/input[@value='";
	private String CartPage_EmptyCartButton = ".//*[@id='emptyCartItemsForm']/a";

	public NA18025Test(String Store){
		this.Store = Store;
		this.testName = "NA-18025";
	}                                 
	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "e2e", "b2b"})
	public void NA18025(ITestContext ctx) throws Exception {
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			String b2bLoginURL = testData.B2B.getLoginUrl();
			String hmcLoginURL = testData.HMC.getHomePageUrl();

			String enableCompleteShipping = "true";
			String disableCompleteShipping = "false";

			String enableGroupShippingValue = "true";
			String disableGroupShippingValue = "false";

			String enableLineItemShippingValue = "true";
			String disableLineItemShippingValue = "false";

			String enableCRADValue = "true";
			String disableCRADValue = "false";

			String ShippingPage_completeShipping = "//div/input[contains(@value,'COMPLETESHIPPING')]";
			
			String ShippingPage_AdvancedShipping = ".//*[@id='grayAdvanceMixProduct']";

			//============= Step:1 HMC: Changing HMC setting for group shipping, lineItemShipping & CRAD values to "No"  ============//
			shippingSettingHMC(enableCompleteShipping,disableGroupShippingValue,disableLineItemShippingValue,disableCRADValue);
			Dailylog.logInfoDB(1, "Successfully Updated HMC Setting", Store, testName);

			//============= Step:2 & 3 Add a product in Cart and Verifying that Advance Shipping Option is not displayed ============//
			//((JavascriptExecutor)driver).executeScript("(window.open(''))");
			checkAdvancedShippingOptionInB2B(2,false,"yes",ShippingPage_AdvancedShipping);


			//=========== Step:4 Changing HMC setting for group shipping, lineItemShipping & CRAD values to "Yes" ============//
		
			shippingSettingHMC(enableCompleteShipping,enableGroupShippingValue,enableLineItemShippingValue,enableCRADValue);
			
			//================ Step:5 & 6 Verifying that Advance Shipping option is displayed ================//
			checkAdvancedShippingOptionInB2B(5,true,"yes",ShippingPage_AdvancedShipping);

			//=============== Step:7 Changing HMC setting for complete Shipping option to "No" =============//
			shippingSettingHMC(disableCompleteShipping,enableGroupShippingValue,enableLineItemShippingValue,enableCRADValue);

			//============== Step:8 & 9 Verifying that Complete Shipping Option is not displayed ===============//
			checkAdvancedShippingOptionInB2B(8,false,"yes",ShippingPage_completeShipping);

			//=============== Step:10 Changing HMC setting for complete Shipping option to "Yes" ===============//
			shippingSettingHMC(enableCompleteShipping,enableGroupShippingValue,enableLineItemShippingValue,enableCRADValue);

			//=============== Step:11 & 12 Verifying that Complete Shipping option is displayed =================//
			checkAdvancedShippingOptionInB2B(11,true,"No",ShippingPage_completeShipping);

			//=============== Step:13 & 14 Fill Shipping Details and paymet page details =================//
			String orderNum = B2BCommon.placeAnOrder(driver, this.Store, b2bPage, testData);
			Dailylog.logInfoDB(14, "Order Num" + orderNum, Store, testName);
			//=============== Step:15 Order Check in HMC =================//

			driver.get(hmcLoginURL);
			HMCCommon.Login(hmcPage, testData);
			
			//B2BCommon.HMCOrderCheck(hmcPage, orderNum);

			HMCCommon.HMCOrderCheck(hmcPage, orderNum);


		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}

	private void shippingSettingHMC(String completeShippingValue,String groupShippingValue, String lineItemShippingValue, String CRADValue) throws InterruptedException{
		String hmcLoginURL = testData.HMC.getHomePageUrl();
		driver.get(hmcLoginURL);
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		Common.sleep(2000);
		hmcPage.B2BUnit_ResultItem.click();
		Common.sleep(3000);
		
		hmcPage.B2BUnit_siteAttribute.click();
		Common.sleep(2000);
		WebElement completeShipping = driver.findElement(By.xpath(completeShippingRadioBtn + completeShippingValue + "']"));
		Common.scrollToElement(driver, completeShipping);
		completeShipping.click();
		driver.findElement(By.xpath(groupShippingRadioBtn + groupShippingValue + "']")).click();
		driver.findElement(By.xpath(lineItemShippingRadioBtn + lineItemShippingValue + "']")).click();
		driver.findElement(By.xpath(CRADRadioBtn + CRADValue + "']")).click();
		Common.sleep(1000);
		hmcPage.HMC_Save.click();
		Common.sleep(3000);
		hmcPage.HMC_Logout.click();
		Common.sleep(3000);
	}

	private void checkAdvancedShippingOptionInB2B(int step,boolean ShippingStatus, String signOutReq,String locator){
		String b2bLoginURL = testData.B2B.getLoginUrl();
		driver.get(b2bLoginURL);
		B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		Common.sleep(3000);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(1500);
		if(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartButton))){
			driver.findElement(By.xpath(CartPage_EmptyCartButton)).click();
		}
		B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
		B2BCommon.addProduct(driver, b2bPage, testData.B2B.getDefaultMTMPN1());
		Dailylog.logInfoDB(step, "Added a product into Cart", Store, testName);
		Common.sleep(2000);
		b2bPage.cartPage_LenovoCheckout.click();
		Common.sleep(3000);
		((JavascriptExecutor) driver).executeScript("scroll(0,500)");
		Dailylog.logInfoDB(step, "Checking the shipping button presence", Store, testName);
		System.out.println(Common.isElementExist(driver, By.xpath(locator))+" "+ShippingStatus);
		Assert.assertEquals(Common.isElementExist(driver, By.xpath(locator)),ShippingStatus);

		Common.sleep(1000);
		if(signOutReq.equalsIgnoreCase("yes")){
			((JavascriptExecutor) driver).executeScript("scroll(0,-500)");
			b2bPage.ShippingPage_SignOut.click();
			System.out.println("Signed Out from B2B website.");
		}
		else if(signOutReq.equalsIgnoreCase("No")){
			System.out.println("Continue to checkout.");
		}

	}
}
