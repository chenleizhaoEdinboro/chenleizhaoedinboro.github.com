package TestScript.B2C;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.Payment;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
public class NA21906Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String ChequeName = "Check Auto";
	public String laptopPageURL;
	public String OrderNumber;
	public String DisplayName = "Pay By Check Auto";	
	private String CartPage_EmptyCartMsg = ".//*[@id='mainContent']/div[2]/h3";
	private String customiseButton = "(//ol[contains(@class,'product')]/li/div//div[contains(@class,'footer')]/button[contains(@id,'Cart')])[1]";
	
	public String tele_homePageUrl;
	public String tele_loginUrl;
	public String tele_cartUrl;
	
	public String ordinary_homePageUrl;
	public String ordinary_loginUrl;
	public String ordinary_cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	
	
	public NA21906Test(String store) {
		this.Store = store;
		this.testName = "NA-21906";
	}	

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21906(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			tele_homePageUrl = testData.B2C.getTeleSalesUrl();
			tele_loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			tele_cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			ordinary_homePageUrl = testData.B2C.getHomePageUrl();
			ordinary_loginUrl = testData.B2C.getloginPageUrl();
			ordinary_cartUrl = ordinary_homePageUrl + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			
			//laptopPageURL="https://pre-c-hybris.lenovo.com/au/en/laptops/c/LAPTOPS?menu-id=Laptops_Ultrabooks";
			// step~1 : Login into HMC -->Create Payment Profile
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Logged in into HMC",Store,testName);
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();
			Common.sleep(3000);
			Dailylog.logInfoDB(1,"Clicked on Payment Customization",Store,testName);			
			hmcPage.Home_paymentProfile.click();
			hmcPage.paymentProfile_paymentType.click();
			hmcPage.paymentProfile_PaymentTypeDd.click();
			hmcPage.paymentProfile_channelALL.click();
			hmcPage.paymentProfile_paymentImgageSearch.click();
			Common.doubleClick(driver, driver.findElement(By.xpath("//div[@class='olecEntry']/div[contains(@id,'Deposit')]")));
			//B2C Properties			
			hmcPage.paymentProfile_configLevel.click();
			
			hmcPage.paymentProfile_configLevelGlobal.click();
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelALL.click();
			hmcPage.paymentProfile_activeTrue.click();
			hmcPage.paymentProfile_disableForCustomerNo.click();
			
			Dailylog.logInfoDB(1,"Clicked on Active : True",Store,testName);			
			//Global Tab
			hmcPage.paymentProfile_global.click();
			Dailylog.logInfoDB(1,"Clicked on Global Tab",Store,testName);
			hmcPage.paymentProfile_ASMVisiblityNo.click();
			hmcPage.paymentProfile_globalMixedOrderYes.click();
			hmcPage.paymentProfile_globalMixedPcgNo.click();
			hmcPage.paymentProfile_globalMixedDcgYes.click();
			hmcPage.baseStore_save.click();
			Dailylog.logInfoDB(1,"DCG Value = Yes",Store,testName);
			
			//Step ~ 2 for B2C Unit
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			Common.sendFieldValue(hmcPage.B2CUnit_IDTextBox, testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			if(Common.checkElementExists(driver, hmcPage.PaymentType_SearchResultDD, 1500)){
				Dailylog.logInfoDB(2,"Payment Type is already present",Store,testName);
			}else{
				CheckAndAddPaymentType();
				if(Common.checkElementExists(driver, hmcPage.PaymentType_SearchResultDD, 1500)){
					Dailylog.logInfoDB(2,"Payment Type was added successfully",Store,testName);
				}else{
					Dailylog.logInfoDB(2,"Something went wrong. Payment Type was not added successfully",Store,testName);
				}
			}
			
			hmcPage.baseStore_save.click();
			
			hmcPage.HMC_Logout.click();
			//step~4 : Login into B2C
			driver.get(ordinary_loginUrl);			
			// close login page pop-up			
			if (Common.isElementExist(driver,By.xpath(".//*[@id='window-close']/img"))) {
				Dailylog.logInfoDB(4,"pop-up is present",Store,testName);
				Common.sleep(10000);
				driver.findElement(By.xpath(".//*[@id='window-close']/img"))
				.click();
				Dailylog.logInfoDB(4,"Closed Pop-up",Store,testName);
			} else {
				Dailylog.logInfoDB(4,"pop-up is not present",Store,testName);
			}
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(2500);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(4,"Logged in into B2C as a normal User",Store,testName);			
			Common.sleep(3500);
			//Clear the cart
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
			
			Dailylog.logInfoDB(4, "Cart is cleared.", Store, testName);
			//Add a product to cart
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Common.sleep(5000);
			laptopPageURL = driver.getCurrentUrl();
			B2CCommon.addToCartB2C(1, driver, laptopPageURL, customiseButton);
			Common.sleep(3000);			
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartMsg)));
			//Lenovo Checkout
			b2cPage.lenovo_checkout.click();
			Dailylog.logInfoDB(4, "Lenovo Checkout is clicked.", Store, testName);
			//Fill Shipping detail
			if(Common.isElementExist(driver, By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]")))
				b2cPage.EditAddress.click();
			B2CCommon.fillShippingInfo(b2cPage, "Tyrian", "Lanister", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "testmail@sharklasers.com", testData.B2C.getConsumerTaxNumber());
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);			
			Common.sleep(20000);
			Dailylog.logInfoDB(4, "Clicked continue button on shipping page.", Store, testName);
			//Payment page 
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/../label")),"Direct Deposit option is not present");
			Common.javascriptClick(driver, b2cPage.DirectDeposit);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "Ramsay", "snow", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			Payment.clickPaymentContinueButton(b2cPage);
			Common.sleep(2500);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);	
			B2CCommon.clickPlaceOrder(b2cPage);
			Common.sleep(2500);
			OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(4, "order Number is :"+OrderNumber, Store, testName);
			//Step~5  Login as ASM
		
			driver.get(ordinary_homePageUrl);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]//a[contains(@href,'logout')]")));
			
			driver.get(tele_loginUrl);
			//B2CCommon.PopUpJP(driver);
			// close login page pop-up			
			if (Common.isElementExist(driver,By.xpath(".//*[@id='window-close']/img"))) {
				Dailylog.logInfoDB(5,"pop-up is present",Store,testName);
				Common.sleep(10000);
				driver.findElement(By.xpath(".//*[@id='window-close']/img"))
				.click();
				Dailylog.logInfoDB(5,"Closed Pop-up",Store,testName);
			} else {
				Dailylog.logInfoDB(5,"pop-up is not present",Store,testName);
			}
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(2500);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(),
					testData.B2C.getTelesalesPassword());
			Dailylog.logInfoDB(5,"Logged in into B2C as an ASM",Store,testName);			
			Common.sleep(3500);
			//step~6
			Common.javascriptClick(driver, b2cPage.MyAccount_myAccount);
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Common.sleep(2000);
			//step~7
//			Common.sendFieldValue(b2cPage.CustomerFilterbox, testData.B2C.getLoginID());
//			Dailylog.logInfoDB(7,"inputted customer in Filter box",Store,testName);
//			Common.sleep(2500);
//			Common.KeyEventEnter();
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			
			
			Thread.sleep(5000);
			
			
			//Step~8
			b2cPage.ASM_StartSession.click();
			Dailylog.logInfoDB(8,"Session has started for :" + testData.B2C.getLoginID(),Store,testName);
			//Step~9			
			//Add a product to cart
			Thread.sleep(5000);
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Common.sleep(5000);
			laptopPageURL = driver.getCurrentUrl();
			Common.sleep(5000);
			driver.get(laptopPageURL);
			B2CCommon.addToCartB2C(1, driver, laptopPageURL, customiseButton);
			Common.sleep(3000);			
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartMsg)));
			//Lenovo Checkout
			b2cPage.lenovo_checkout.click();
			Dailylog.logInfoDB(9, "Lenovo Checkout is clicked.", Store, testName);
			//Fill Shipping detail
			if(Common.isElementExist(driver, By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]")))
				b2cPage.EditAddress.click();
			B2CCommon.fillShippingInfo(b2cPage, "Tyrian", "Lanister", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "testmail@sharklasers.com", testData.B2C.getConsumerTaxNumber());
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Dailylog.logInfoDB(9, "Clicked continue button on shipping page.", Store, testName);			
			Assert.assertFalse(Common.isElementExist(driver, By.xpath("//*[@id='PaymentTypeSelection_BANKDEPOSIT']/../label")),"Direct Deposit option is still present");			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	// +=+=+=+=+=+=+Step~12 : Roll back by deleting step 1 +=+=+=+=+=+=+=//
	@AfterTest
	public void rollback() throws InterruptedException, MalformedURLException {
		Dailylog.logInfoDB(9,"rollback",Store,testName); // roll back
		SetupBrowser();
		driver.get(hmcLoginUrl);
		hmcPage = new HMCPage(driver);
		HMCCommon.Login(hmcPage, testData);
		Dailylog.logInfoDB(12,"Logged in into HMC",Store,testName);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_payment.click();
		hmcPage.Home_paymentCustomize.click();
		hmcPage.Home_paymentProfile.click();
		Dailylog.logInfoDB(12,"clicked on Payment Profile",Store,testName);
		Common.sleep(3000);
		hmcPage.PaymentMessage_PaymentTypeDropDown.click();
		hmcPage.paymentProfile_PaymentTypeDd.click();
		hmcPage.paymentProfile_paymentImgageSearch.click();
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[@class='olecEntry']/div[contains(@id,'Deposit')]")));
		Common.sleep(2000);	
		hmcPage.paymentProfile_activeTrue.click();
		Dailylog.logInfoDB(1,"Clicked on Active : True",Store,testName);
	}
	public void CheckAndAddPaymentType() {
		Dailylog.logInfoDB(2,"Verifying Payment type and adding to list",Store,testName);
		Common.rightClick(driver, hmcPage.B2CSiteAttribute_PayTypeHead);
		hmcPage.B2CSiteAttribute_AddPayType.click();
		Common.sleep(3000);
		Common.switchToWindow(driver, 1);
		Common.sendFieldValue(hmcPage.PaymentType_CheckoutCode, "BANKDEPOSIT");
		hmcPage.PaymentMessage_Search.click();
		Common.doubleClick(driver,hmcPage.PaymentType_SearchResultDD);
		Dailylog.logInfoDB(2,"Used searched result",Store,testName);
		Common.switchToWindow(driver, 0);
		Common.sleep(3000);
		hmcPage.HMC_Save.click();
		Common.sleep(5000);
		Dailylog.logInfoDB(2,"Cheque option was added successfully to payment type",Store,testName);
	}
	

}