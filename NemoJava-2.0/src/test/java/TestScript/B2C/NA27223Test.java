package TestScript.B2C;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA27223Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String b2cProductUrl;
	public String ProductID;
	private String b2cUnitID;
	private String suggested_Line1;
	private String suggested_city;
	private String suggested_ZipCode;
	private String shipping_SuggestedLine1;
	private String address4 = "1009 think place morrisville,27560,NC";
	private String address2 = "1009 think place cary,27519,NC";
	private String address3 = "1008 think place cary,27519,NC";
	private String address7 = "20 N. Hord St, Grayson, KY, 41143";
	private String address8 = "300 West 22nd Street, Oak Brook, IL, 60523";
	private String address9 = "100 Industrial, rittman, oh, 44270, USA";
	private String address07 = "1009 think place morrisville,27560,NC";
	private String address10 = "55 Looping Ln, Portland, LA, 81234";
	private String address11 = "14 Elmwood, Rome, GA, 30161";
	private String testProduct;
	private String loginID;
	private String pwd;
	
	public NA27223Test(String Store, String b2cUnit) {
		this.Store = Store;
		this.b2cUnitID = b2cUnit;
		this.testName = "NA-27223";
	}

	@Test(alwaysRun = true, groups = {"commercegroup", "cartcheckout", "p2", "b2c"})
	public void NA27223(ITestContext ctx) {

		try {
			this.prepareTest();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
	
			//=========== Step:1 "Enable QAS New" as yes under site attributes tab.===========//
			driver.get(testData.HMC.getHomePageUrl());
			Thread.sleep(3000);
			HMCCommon.Login(hmcPage, testData);
			checkHmcQAS();
			Dailylog.logInfoDB(1, "Enable QAS New is set YES", Store, testName);

			//=========== Step:2 login the US store and add product into cart.===========//
			loginB2C();
			Dailylog.logInfoDB(2, "Login B2C website successfully", Store, testName);

			//=========== Step:3 Add one product ===========//
			b2cPage.Navigation_CartIcon.click();
			if (Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 5))
				b2cPage.Navigation_ViewCartButton.click();
			Common.sleep(2000);;
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			testProduct = testData.B2C.getDefaultMTMPN();
			B2CCommon.addPartNumberToCart(b2cPage, testProduct);	
			Thread.sleep(3000);
			Dailylog.logInfoDB(3, "Add one product into cart", Store, testName);

			//click Lenovo checkout on shopping cart page
			driver.findElement(By.xpath("//*[@id='lenovo-checkout-sold-out']"))
					.click();
			Common.sleep(3000);
			String shippingURL = driver.getCurrentUrl();
			Assert.assertTrue(shippingURL.contains("add-delivery-address"));
			Dailylog.logInfoDB(3, "shipment page is displayed:" + shippingURL, Store, testName);

			//=========== Step:4&12 input valid shipping address and click continue button ===========//
			Common.sleep(2000);
			inputAddressZipCode(address4,address4.split(",")[1]);
			Common.sleep(2000);
			Assert.assertTrue(driver.getCurrentUrl().contains("payment"));
			Assert.assertEquals("27560-9002", b2cPage.Payment_UseShippingAddress.getAttribute("data-postalcode"));
			Dailylog.logInfoDB(4, address4 + ",pass the qas validation and go to payment page", Store, testName);
			
			//=========== Step:7&13 valid address line1, but city/zip/state mismatch.click on "use suggested address". ===========//
			String ValidateInfo_message1 = "Sorry, we think your apartment/suite/unit is missing or wrong.";
			checkPopMessage(address7,ValidateInfo_message1);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.NewShipping_AddressValidateButton, 5));
			Dailylog.logInfoDB(7, address7 + ",cannot pass the qas validation,suggested address display on popup windows", Store, testName);
			
			//Step:13
			Common.sleep(2000);
			Common.checkElementDisplays(driver, b2cPage.ValidateInfo_InputAddress, 3);
			b2cPage.ValidateInfo_InputAddress.clear();
			String input_SuggestedAddress = "100 N Hord St StGrayson";
			b2cPage.ValidateInfo_InputAddress.sendKeys(input_SuggestedAddress);
			if(Common.checkElementExists(driver, b2cPage.ValidateInfo_ConfirmAddressButton, 5)){
				Common.javascriptClick(driver, b2cPage.ValidateInfo_ConfirmAddressButton);
				Common.sleep(2000);
				if(Common.checkElementExists(driver, b2cPage.ValidateInfo_UseSuggestButton, 5)){
					Common.sleep(3000);
					if(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_SuggestedAddress.get(0), 3)){
						suggested_Line1 = b2cPage.ValidateInfo_SuggestedAddress.get(0).getAttribute("innerHTML");
						suggested_city = b2cPage.ValidateInfo_SuggestedAddress.get(1).getAttribute("innerHTML");
						suggested_ZipCode = b2cPage.ValidateInfo_SuggestedAddress.get(3).getAttribute("innerHTML");
						Common.sleep(2000);
						Common.javascriptClick(driver, b2cPage.ValidateInfo_UseSuggestButton);
						if(Common.checkElementDisplays(driver, b2cPage.Payment_UseShippingAddress, 5)){
							shipping_SuggestedLine1 = b2cPage.Payment_UseShippingAddress.getAttribute("data-line1");	
						}
						System.out.println(shipping_SuggestedLine1);
						System.out.println(suggested_Line1 +","+ suggested_city +","+ suggested_ZipCode);
						Assert.assertEquals(suggested_Line1, shipping_SuggestedLine1);
						Assert.assertEquals(suggested_city, b2cPage.Payment_UseShippingAddress.getAttribute("data-town"));
						Assert.assertEquals(suggested_ZipCode, b2cPage.Payment_UseShippingAddress.getAttribute("data-postalcode"));
					}else{
						Assert.fail("suggested address cannot be get");
					}
				}
			}
			Dailylog.logInfoDB(13, "QAS address is applied onto the cart(payment page)", Store, testName);

			//=========== Step:8 input address with correct street but with multi-matches. ===========//
			String ValidateInfo_message2 = "We found more than one match for your address.";
			checkPopMessage(address8,ValidateInfo_message2);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_ConfirmAddressButton, 5));
			Dailylog.logInfoDB(8, address8 + ",cannot pass the qas validation,suggested address display on popup windows", Store, testName);
			
			//=========== Step:9 input valid address but with multi-matches. ===========//
			checkPopMessage(address9,ValidateInfo_message2);
			verifySuggestedAddressApplied();
			Dailylog.logInfoDB(9, address9 + ",cannot pass the qas validation,suggested address display on popup windows", Store, testName);
			
			//=========== Step:10 input valid address but with none matches. ===========//
			String ValidateInfo_message3 = "Sorry, we could not find a match for your address.";
			checkPopMessage(address10,ValidateInfo_message3);
			Dailylog.logInfoDB(10, address10 + ",cannot pass:" + ValidateInfo_message3, Store, testName);
			
			//=========== Step:11 input valid but incompleted address line1. ===========//
			String ValidateInfo_message4 = "We think that your address may be incorrect or incomplete.";
			checkPopMessage(address11,ValidateInfo_message4);
			Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.ValidateInfo_UseSuggestButton, 5));
			Dailylog.logInfoDB(11, address11 + ",cannot pass:" + ValidateInfo_message4, Store, testName);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	
//	@AfterTest(alwaysRun = true)
//	public void rollBack(ITestContext ctx) throws InterruptedException {
//		try {
//			SetupBrowser();
//			hmcPage = new HMCPage(driver);
//			driver.get(testData.HMC.getHomePageUrl());
//			HMCCommon.Login(hmcPage, testData);
//
//			hmcPage.Home_B2CCommercelink.click();
//			hmcPage.Home_B2CUnitLink.click();
//			hmcPage.B2CUnit_IDTextBox.clear();
//			hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
//			hmcPage.B2CUnit_SearchButton.click();
//
//			hmcPage.B2CUnit_FirstSearchResultItem.click();
//			Common.waitElementClickable(driver,
//					hmcPage.B2CUnit_SiteAttributeTab, 30);
//			hmcPage.B2CUnit_SiteAttributeTab.click();
//			Common.waitElementClickable(driver, hmcPage.B2CUnit_EnableGEOLink,
//					30);
//			
//			hmcPage.B2CUnit_EnableQASNewNo.click();
//			hmcPage.SaveButton.click();
//
//			Common.waitElementClickable(driver, hmcPage.SaveButton,
//					30);
//			Thread.sleep(5000);
//			hmcPage.HMC_Logout.click();
//		} catch (Throwable e) {
//			handleThrowable(e, ctx);
//		}
//	}
	
	public void verifySuggestedAddressApplied(){
		String link_Text = b2cPage.ValidateInfo_AddressCheckLink.getText();
		String link_Line1 = link_Text.split(",")[0];
		String link_City = link_Text.split(",")[1];
		String link_zipCode = link_Text.split(",")[3];
		Common.javascriptClick(driver, b2cPage.ValidateInfo_AddressCheckLink);
		Common.sleep(2000);
		Assert.assertEquals(link_Line1, b2cPage.Payment_UseShippingAddress.getAttribute("data-line1"));
		Assert.assertEquals(link_City, b2cPage.Payment_UseShippingAddress.getAttribute("data-town"));
		Assert.assertEquals(link_zipCode, b2cPage.Payment_UseShippingAddress.getAttribute("data-postalcode"));
	}
	
	public void checkPopMessage(String address, String message){
		switchBackShipping();
		Common.sleep(3000);
		inputAddressZipCode(address,address.split(",")[3]);
		Common.sleep(5000);
		Assert.assertEquals(message, b2cPage.ValidateInfo_message1.getAttribute("innerHTML"));
	}
	
	public void switchBackShipping(){
		if(!Common.checkElementExists(driver, b2cPage.ValidateInfo_Edit, 5)){
			driver.navigate().back();
		}else{
			b2cPage.ValidateInfo_Edit.click();
		}
	}
	
	public void inputAddressZipCode(String address, String zipCode){
		Common.sleep(5000);
		try {
			Common.javascriptClick(driver, b2cPage.Shipping_editAddress);
		} catch (Exception e) {
			Dailylog.logInfo("Edit is not present");
		}
		Common.sleep(2000);
		b2cPage.Shipping_AddressLine1TextBox.clear();
		b2cPage.Shipping_AddressLine1TextBox.sendKeys(address);
		b2cPage.Shipping_PostCodeTextBox.clear();
		b2cPage.Shipping_PostCodeTextBox.sendKeys(zipCode.trim());
		Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
	}
	
	public void HandleJSpring(WebDriver driver, B2CPage b2cPage,String loginID, String pwd) {

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

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(page.PromotionBanner).click().perform();
		}
	}

	public void loginB2C(){
		b2cPage = new B2CPage(driver);
		Common.sleep(2500);
		driver.get(testData.B2C.getloginPageUrl());
		B2CCommon.handleGateKeeper(b2cPage, testData);
		loginID = testData.B2C.getLoginID();
		pwd = testData.B2C.getLoginPassword();
		B2CCommon.login(b2cPage, loginID, pwd);
		B2CCommon.handleGateKeeper(b2cPage, testData);
		HandleJSpring(driver,b2cPage, loginID, pwd);
		Common.sleep(2000);
	}
	
	public void checkHmcQAS(){
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		hmcPage.B2CUnit_SearchButton.click();

		hmcPage.B2CUnit_FirstSearchResultItem.click();
		Common.waitElementClickable(driver,
				hmcPage.B2CUnit_SiteAttributeTab, 30);
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.waitElementClickable(driver, hmcPage.B2CUnit_EnableGEOLink,
				30);
		hmcPage.B2CUnit_EnableGEOLink.click();
		hmcPage.B2CUnit_EnableQASNew.click();
		hmcPage.SaveButton.click();

		Common.waitElementClickable(driver, hmcPage.SaveButton,
				30);
		Common.sleep(5000);
		hmcPage.HMC_Logout.click();
	}
	
	public String findCTOProduct() throws InterruptedException {
		String b2cProductUrl = driver.getCurrentUrl();
		Dailylog.logInfo("b2cProductUrl is :" + b2cProductUrl);
		ProductID = b2cProductUrl.split("/p/")[1].split("#")[0]
				.substring(0, 15);
		Dailylog.logInfo(ProductID);
		return ProductID;
	}

}
