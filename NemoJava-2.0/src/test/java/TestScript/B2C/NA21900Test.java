package TestScript.B2C;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
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
public class NA21900Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String ChequeName = "Check Auto";
	public String laptopPageURL;
	public String OrderNumber;
	public String DisplayName = "Pay By Check Auto";
	private String  Payment_Terms = " <payment_terms>US12</payment_terms>";
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
	

	public NA21900Test(String store) {
		this.Store = store;
		this.testName = "NA-21900";

	}	

	@Test(alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA21900(ITestContext ctx) {
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
			
			//step 12, Roll Back :
			//Deleting the step1 created payment profile
			Dailylog.logInfoDB(12, "Deleting the step1 created payment profile", Store, testName);
			
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();
			Common.sleep(3000);
			hmcPage.Home_paymentProfile.click();
			
			Select select = new Select(driver.findElement(By.xpath("//select[contains(@id,'[attributeselect][PaymentTypeProfile]_select')]")));
			select.selectByValue("name");
			Thread.sleep(5000);
			
			driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/GenericCondition[PaymentTypeProfile.name]]_input')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/GenericCondition[PaymentTypeProfile.name]]_input')]")).sendKeys(ChequeName);
			
			driver.findElement(By.xpath("//span[contains(@id,'searchbutton')]")).click();
			
			if(Common.isElementExist(driver, By.xpath("//div[text()='Check Auto']"))){
				List<WebElement> resultList = driver.findElements(By.xpath("//div[text()='Check Auto']"));
				
				for(int x = 1; x <= resultList.size(); x++){
					Common.rightClick(driver, driver.findElement(By.xpath("(//div[text()='Check Auto'])[1]")));
					driver.findElement(By.xpath("//td[contains(@id,'remove') and contains(@id,'label')]")).click();
					Thread.sleep(2000);
					Alert alert = driver.switchTo().alert();
					alert.accept();
					Thread.sleep(6000);
				}
			}

			hmcPage.Home_EndSessionLink.click();

			// step~1 : Login into HMC -->Create Payment Profile
			
			Dailylog.logInfoDB(1,"Logged in into HMC",Store,testName);
			
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Nemo.click();
			hmcPage.Home_payment.click();
			hmcPage.Home_paymentCustomize.click();
			Common.sleep(3000);
			Dailylog.logInfoDB(1,"Clicked on Payment Customization",Store,testName);
			Common.rightClick(driver, hmcPage.Home_paymentProfile);
			Common.sleep(1500);
			hmcPage.Home_paymentProfileCreate.click();
			//B2C Properties
			Common.sendFieldValue(hmcPage.paymentProfile_name, ChequeName);
			Common.sendFieldValue(hmcPage.paymentProfile_code, "Check_Auto");			
			hmcPage.paymentProfile_paymentType.click();
			Dailylog.logInfoDB(1,"Clicked on Payment Type",Store,testName);
			hmcPage.paymentProfile_PaymentTypeCheque.click();
			hmcPage.paymentProfile_configLevel.click();
			hmcPage.paymentProfile_configLevelS.click();
			hmcPage.paymentProfile_channel.click();
			hmcPage.paymentProfile_channelALL.click();
			hmcPage.paymentProfile_activeTrue.click();
			Dailylog.logInfoDB(1,"Clicked on Active : True",Store,testName);
			//B2C&B2B Properties
			hmcPage.paymentProfile_paymentB2BB2C.click();
			Common.sendFieldValue(hmcPage.paymentProfile_paymentB2BB2CDisplayName, DisplayName);
			//Global Tab
			hmcPage.paymentProfile_global.click();
			Dailylog.logInfoDB(1,"Clicked on Global Tab",Store,testName);
			hmcPage.paymentProfile_ASMVisiblity.click();
			hmcPage.paymentProfile_globalMixedOrderYes.click();
			hmcPage.paymentProfile_globalMixedPcgNo.click();
			hmcPage.paymentProfile_globalMixedDcgYes.click();
			Dailylog.logInfoDB(1,"DCG Value = Yes",Store,testName);
			//B2C Unit
			hmcPage.PaymentMessage_B2CUnitTab.click();
			Common.rightClick(driver, hmcPage.PaymentMessage_B2CUnitTableHead);
			hmcPage.PaymentMessage_AddB2CUnit.click();
			Common.switchToWindow(driver,1);
			Dailylog.logInfoDB(1,"Switched to window 1",Store,testName);
			// input unit id
			hmcPage.PaymentMessage_IdCodeInputBox.clear();
			hmcPage.PaymentMessage_IdCodeInputBox.sendKeys(testData.B2C.getUnit());
			hmcPage.PaymentMessage_Search.click();
			Dailylog.logInfoDB(1,"Clicked on Search Button",Store,testName);			
			driver.findElement(
					By.xpath("(//div[contains(@id,'" + testData.B2C.getUnit()
							+ "')]/div)[1]")).click();
			hmcPage.PaymentMessage_Use.click();
			Dailylog.logInfoDB(1,"Clicked on Use button",Store,testName);
			Common.switchToWindow(driver,0);
			Dailylog.logInfoDB(1,"Switched back to main window",Store,testName);
			Common.sleep(4000);
			hmcPage.B2BCustomer_CreateButton.click();
			Dailylog.logInfoDB(1,"Saved all the changes",Store,testName);
			Common.sleep(2000);
			//Step ~ 2 for B2C Unit
			hmcPage.Home_B2CCommercelink.click();
			//hmcPage.Home_B2CUnitLink.click();
			driver.findElement(By.xpath("//a[contains(@id,'B2CUnit')]")).click();
			
			
			Common.sendFieldValue(hmcPage.B2CUnit_IDTextBox, testData.B2C.getUnit());
			hmcPage.B2CUnit_SearchButton.click();
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();

			if(Common.checkElementDisplays(driver, By.xpath("//div/div[contains(@id,'Cheque Payment')]"), 2)){
				Dailylog.logInfoDB(2,"Payment Type is already present",Store,testName);
			}else{
				CheckAndAddPaymentType();
				if(Common.checkElementDisplays(driver, By.xpath("//div/div[contains(@id,'Cheque Payment')]"), 2)){
					Dailylog.logInfoDB(2,"Payment Type was added successfully",Store,testName);
				}else{
					Dailylog.logInfoDB(2,"Something went wrong. Payment Type was not added successfully",Store,testName);
				}
			}
			hmcPage.HMC_Logout.click();
			//step~3 : Login into B2C
			driver.get(ordinary_loginUrl);			
			// close login page pop-up			
			if (Common.isElementExist(driver,By.xpath(".//*[@id='window-close']/img"))) {
				Dailylog.logInfoDB(3,"pop-up is present",Store,testName);
				Common.sleep(10000);
				driver.findElement(By.xpath(".//*[@id='window-close']/img"))
				.click();
				Dailylog.logInfoDB(3,"Closed Pop-up",Store,testName);
			} else {
				Dailylog.logInfoDB(3,"pop-up is not present",Store,testName);
			}
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(2500);
			B2CCommon.login(b2cPage, testData.B2C.getLoginID(),
					testData.B2C.getLoginPassword());
			Dailylog.logInfoDB(3,"Logged in into B2C as a normal User",Store,testName);			
			Common.sleep(3500);
			//Step~4
			//Clear the cart
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			Common.sleep(2000);
//			Assert.assertTrue(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartMsg)));
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
			
			if(Common.isElementExist(driver, By.xpath("//fieldset/legend/a[contains(text(),'Edit')]"))){
				b2cPage.EditAddress.click();
			}
			B2CCommon.fillShippingInfo(b2cPage, "Tyrian", "Lanister", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "testmail@sharklasers.com", testData.B2C.getConsumerTaxNumber());
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);			
			Common.sleep(20000);
			Dailylog.logInfoDB(4, "Clicked continue button on shipping page.", Store, testName);
			//Payment page 
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(".//*[@id='PaymentTypeSelection_CHECK']")),"Cheque option is present");

			//Step~5  Login as ASM
			driver.get(tele_loginUrl);
			//B2CCommon.PopUpJP(driver);
			// close login page pop-up	
			B2CCommon.closeHomePagePopUP(driver);
		
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
			B2CCommon.addToCartB2C(1, driver, laptopPageURL, customiseButton);
			Common.sleep(3000);			
			Assert.assertFalse(Common.isElementExist(driver, By.xpath(CartPage_EmptyCartMsg)));
			//Lenovo Checkout
			b2cPage.lenovo_checkout.click();
			Dailylog.logInfoDB(9, "Lenovo Checkout is clicked.", Store, testName);
			//Fill Shipping detail
			if(Common.isElementExist(driver, By.xpath("//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]"))){
				b2cPage.EditAddress.click();
			}
			
			B2CCommon.fillShippingInfo(b2cPage, "Tyrian", "Lanister", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), "testmail@sharklasers.com", testData.B2C.getConsumerTaxNumber());
			Common.sleep(2000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			Dailylog.logInfoDB(9, "Clicked continue button on shipping page.", Store, testName);
			//Step~10 Payment page 
			new WebDriverWait(driver, 500)
			.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@for='PaymentTypeSelection_CHECK']")));
			Assert.assertTrue(Common.isElementExist(driver, By.xpath("//label[@for='PaymentTypeSelection_CHECK']")));
			Common.scrollToElement(driver, driver.findElement(By.xpath("//label[@for='PaymentTypeSelection_CHECK']")));
			Common.sleep(4000);
//			driver.findElement(By.xpath("//label[@for='PaymentTypeSelection_CHECK']")).click();
			Common.javascriptClick(driver, b2cPage.ChequePayment);
			B2CCommon.fillPaymentAddressInfo(b2cPage, "Ramsay", "snow", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
			
			Payment.clickPaymentContinueButton(b2cPage);
			
			Common.sleep(2500);
//			b2cPage.OrderSummary_AcceptTermsCheckBox.click();
//			Common.scrollAndClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			Common.sleep(2500);
			OrderNumber = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			Dailylog.logInfoDB(11, "Order Number is :" + OrderNumber, Store, testName);
			driver.get(hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(11,"Logged in into HMC to check order status",Store,testName);
			hmcPage.Order.click();
			hmcPage.Orders.click();
			Common.sendFieldValue(hmcPage.Home_Order_OrderID, OrderNumber);
			hmcPage.Home_Order_OrderSearch.click();
			Dailylog.logInfoDB(11,"clicked on search button",Store,testName);
			String OrderStatus = hmcPage.Home_Order_OrderStatus.getText();
			Dailylog.logInfoDB(11,"Order Status :"+OrderStatus,Store,testName);
			Assert.assertEquals(OrderStatus,"Completed");
			driver.findElement(By.xpath("//span[contains(@id,'OrganizerListEntry')]/img")).click();
			hmcPage.baseStore_administration.click();
			Thread.sleep(4000);
			Assert.assertTrue(hmcPage.Order_XmlContent.getText().contains(Payment_Terms), Payment_Terms+"is not present");
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void CheckAndAddPaymentType() {
		Dailylog.logInfoDB(2,"Verifying Payment type and adding to list",Store,testName);
		Common.rightClick(driver, hmcPage.B2CSiteAttribute_PayTypeHead);
		hmcPage.B2CSiteAttribute_AddPayType.click();
		Common.sleep(3000);
		Common.switchToWindow(driver, 1);
		Common.sendFieldValue(hmcPage.PaymentType_CheckoutCode, "Check");
		hmcPage.PaymentMessage_Search.click();
		Common.doubleClick(driver,hmcPage.PaymentType_SearchResultCheque);
		Dailylog.logInfoDB(2,"Used searched result",Store,testName);
		Common.switchToWindow(driver, 0);
		Common.sleep(3000);
		hmcPage.HMC_Save.click();
		Common.sleep(5000);
		Dailylog.logInfoDB(2,"Cheque option was added successfully to payment type",Store,testName);
	}

}