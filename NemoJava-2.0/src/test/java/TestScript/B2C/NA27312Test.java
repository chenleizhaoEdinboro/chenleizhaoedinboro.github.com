package TestScript.B2C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27312Test extends SuperTestClass {

	B2CPage b2cPage = null;
	private String pincodeDay5 = "123456";
	private String pincodeDay6 = "234567";
	private String invailde_pincode = "134567";
	private HMCPage hmcPage;

	public NA27312Test(String store) {
		this.Store = store;
		this.testName = "NA-27312";

	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public void HandleJSpring(WebDriver driver, B2CPage b2cPage, String loginID, String pwd) {

		if (!driver.getCurrentUrl().contains("account")) {

			driver.get(testData.B2C.getloginPageUrl());
			closePromotion(driver, b2cPage);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='smb-login-button']"))) {
				b2cPage.SMB_LoginButton.click();
			}
			B2CCommon.login(b2cPage, loginID, pwd);
			B2CCommon.handleGateKeeper(b2cPage, testData);
		}
	}

	private String getDate(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

		Date date = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		String dateString = sdf.format(c.getTime());
		
		return dateString;
	}

	@Test(alwaysRun = true, groups = { "commercegroup", "cartcheckout", "p2", "b2c" })
	public void NA27312(ITestContext ctx) {
		try {
			this.prepareTest();

			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			// Step 1 : B2C unit --> Site Attributes, turn on 'EnablePinCode'
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			hmcPage.B2CUnit_IDTextBox.clear();
			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();

			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.EnablePinCode_true.click();
			hmcPage.Types_SaveBtn.click();
			
			// Step 2 :Nemo --> Cart Checkout --> Nemo India Pincode,
			hmcPage.Home_Nemo.click();
			hmcPage.Home_Nemo_cartcheckout.click();
			hmcPage.Home_Nemo_cartcheckout_pincode.click();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(pincodeDay5, "5");
			map.put(pincodeDay6, "6");
			for(Map.Entry<String, Object> pincode : map.entrySet()){
				WebElement Pincode_input =  driver.findElement(By.xpath("//input[contains(@id,'zPincode]]_')]"));
				Pincode_input.clear();
				Pincode_input.sendKeys(pincode.getKey().toString());
				hmcPage.Pincode_search.click();
				Thread.sleep(5000);

				if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//td[contains(@id,'" + pincode.getKey().toString() + "')]"), 5)) {
					// create pincodeDay
					Common.rightClick(driver, hmcPage.serach_result);
					hmcPage.result_clone.click();
					
					hmcPage.Pincode_input.sendKeys(pincode.getKey().toString());				
					hmcPage.Pincode_EnableCashOnDeliveryPayment_no.click();							
					hmcPage.Pincode_zDeliveryDays.sendKeys(pincode.getValue().toString());
					hmcPage.paymentProfile_paymentCreate.click();
					
				}else{
					//update pincodeDay
					Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(@id,'" + pincode.getKey().toString() + "]')]")));
					Common.sleep(3000);
					driver.findElement(By.xpath("//*[text()='Edit in new window']")).click();
					Common.sleep(3000);
					Common.switchToWindow(driver, 1);
					hmcPage.Pincode_EnableCashOnDeliveryPayment_yes.click();	
					hmcPage.Pincode_zDeliveryDays.clear();
					hmcPage.Pincode_zDeliveryDays.sendKeys(pincode.getValue().toString());
					driver.findElement(By.xpath("//*[contains(@id,'ImageToolbarAction[saveandclose]')]")).click();
					Common.sleep(5000);
					Common.switchToWindow(driver, 0);
				}
			}
			
			hmcPage.hmcHome_hmcSignOut.click();

			String cartUrl = testData.B2C.getHomePageUrl() + "/cart";
			Common.NavigateToUrl(driver, Browser, cartUrl);

			Thread.sleep(5000);

			if (!Common.isElementExist(b2cPage.PageDriver, By.xpath("//*[contains(@id,'quantity')]"), 5)) {
				b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
				b2cPage.Cart_AddButton.click();
			}

			b2cPage.cart_pincode.sendKeys(pincodeDay5);
			b2cPage.cart_pincodeCheck.click();
			//Dailylog.logInfo("after 5 days : " + getDate(5));
			
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.cart_pincodeMessage.getText().contains(getDate(5)));

			b2cPage.cart_pincode.clear();
			b2cPage.cart_pincode.sendKeys(pincodeDay6);
			b2cPage.cart_pincodeCheck.click();
			
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.cart_pincodeMessage.getText().contains(getDate(6)));


			b2cPage.cart_pincode.clear();
			b2cPage.cart_pincode.sendKeys(invailde_pincode);
			b2cPage.cart_pincodeCheck.click();
			
			Common.sleep(2000);
			Assert.assertTrue(b2cPage.cart_pincodeMessage.getText().contains("we do not deliver to this PIN code"));

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}
