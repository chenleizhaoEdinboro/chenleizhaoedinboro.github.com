package TestScript.B2C;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA21876Test extends SuperTestClass{
	private B2CPage b2cPage;
	private HMCPage hmcPage;
	private String partNum_One;
	public String CurrencySign;
	
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;

	public NA21876Test(String store, String currencySign){
		this.Store = store;
		this.testName = "NA-21876";
		this.CurrencySign = currencySign;

	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p1", "b2c", "compatibility"})
	public void NA21876(ITestContext ctx){
		try{
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			
			partNum_One = testData.B2C.getDefaultMTMPN();
			
			System.out.println("partNUMBER IS :" + partNum_One);
			
			//----------------- Step:1 Login with TeleSales Account ---------------//
			Dailylog.logInfoDB(1, "Login with TeleSales Account", Store, testName);
			
//			driver.get(loginUrl);
			Common.NavigateToUrl(driver, Browser, loginUrl);
			Common.sleep(2000);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
//			B2CCommon.DoubleLogin(driver, testData, b2cPage, loginUrl, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			
			
			//----------------- Step:2 Click Start Assisted Session ---------------//
			Dailylog.logInfoDB(2, "Click Start Assisted Session", Store, testName);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			Common.sleep(3500);

			//----------------- Step:3 Search customer and start session ---------------//
			//Search Customer on ASM page
			Dailylog.logInfoDB(3, "Search customer and start session  Search Customer on ASM page", Store, testName);
			
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath(".//*[@id='customerFilter']")));
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			new WebDriverWait(driver, 500).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("[id^='ui-id-']>a")));
			b2cPage.ASM_CustomerResult.click();
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);
			
			
			Common.sleep(2000);
			
			//----------------- Step:4 Add a product to cart ---------------//
			Dailylog.logInfoDB(4, "Add a product to cart ", Store, testName);
			
//			driver.get(cartUrl);
			Common.NavigateToUrl(driver, Browser, cartUrl);
			
			b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum_One);
			b2cPage.Cart_AddButton.click();
			
			Common.sleep(2000);
			//String finalPrice = b2cPage.CartPage_FinalPrice.getText();
			String finalPrice = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText();
			System.out.println("finalPrice is :" + finalPrice);
			
			String[] Price_modulous = finalPrice.split("\\"+CurrencySign);		
			Dailylog.logInfoDB(6,"after removing"+CurrencySign+"  :" + Price_modulous[1],Store,testName);
			String refinedPrice = Price_modulous[1].replaceAll(",","").trim();
			Dailylog.logInfoDB(6,"Refined Price :"+refinedPrice,Store,testName);
			double Price_string_to_double = Double.parseDouble(refinedPrice);
			double MaxPrice_string_to_double = Price_string_to_double-1;
			String Maximum_Price = Double.toString(MaxPrice_string_to_double);
			System.out.println("Maximum_Price is :" + Maximum_Price);
			
			b2cPage.CartPage_PriceOverRide_TxtBox.sendKeys(Maximum_Price);
			b2cPage.CartPage_PriceOverRide_ReasonDropDown.click();
			b2cPage.OverrideDropdown.click();
			Common.sleep(2000);
			b2cPage.OverrideCheckbox.sendKeys("xxxxx");
			b2cPage.OverrideUpdate.click();
			
			
			String finalPrice_afterChange = driver.findElement(By.xpath("//span[contains(@class,'subTotalWithoutCoupon')]")).getText();
			System.out.println("finalPrice is :" + finalPrice);
			System.out.println("finalPrice_afterChange is :" + finalPrice_afterChange);
			
			Assert.assertTrue(!finalPrice.equals(finalPrice_afterChange));
			
			
			
			
			//----------------- Step:5 Change the quantity ---------------//
			Dailylog.logInfoDB(5, "Change the quantity", Store, testName);
			
			b2cPage.Quote_quantity0.clear();
			b2cPage.Quote_quantity0.sendKeys("3");
			b2cPage.Quote_update.click();
			Dailylog.logInfoDB(5, "Updated the quantity.", Store, testName);
			b2cPage.RequestQuoteBtn.click();
			Thread.sleep(6000);
			b2cPage.Quote_RepID.clear();
			b2cPage.Quote_RepID.sendKeys("1234567890");
			Thread.sleep(5000);
			b2cPage.Quote_SubmitQuoteBtn.click();
			
			String quoteNum = b2cPage.QuoteConfirmPage_QuoteNo.getText();			
			
			//----------------- Step:6 End Session ---------------//
			Dailylog.logInfoDB(6, "End Session", Store, testName);
			
			b2cPage.ASM_endSession.click();
			Common.sleep(2000);
			
			if(Common.isAlertPresent(driver)){
				driver.switchTo().alert().accept();
			}
			Thread.sleep(6000);
			Dailylog.logInfoDB(6, "Session is ended.", Store, testName);
			
			b2cPage.TransactionID.sendKeys(quoteNum);
			driver.findElement(By.xpath("//*[@id='Q" + quoteNum + "']/a/span[1]")).click();
			//Start ASM Session
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);
			
			b2cPage.ASM_ApproveOrReject.click();
			Thread.sleep(6000);
			b2cPage.ASM_ApproveComment.clear();
			b2cPage.ASM_ApproveComment.sendKeys("approve");
			b2cPage.ASM_PopupApprove.click();
			Thread.sleep(5000);

			//----------------- Step:7 End Session ---------------//
			Dailylog.logInfoDB(7, "End Session", Store, testName);
			
//			Common.javascriptClick(driver, b2cPage.ASM_endSession);
			b2cPage.ASM_endSession.click();
			Thread.sleep(5000);
			
			if(Common.isAlertPresent(driver)){
				driver.switchTo().alert().accept();
			}
			
			//----------------- Step:8 Copy Quote number as Transaction ID ---------------//
			//Search Customer on ASM page
			Dailylog.logInfoDB(8, "Copy Quote number as Transaction ID  Search Customer on ASM page", Store, testName);
			
			
			Common.javascriptClick(driver, b2cPage.SignoutASM);
//			driver.get(loginUrl);
			Common.NavigateToUrl(driver, Browser, loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			Common.sleep(5000);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();

			b2cPage.ASM_SearchCustomer.clear();
			b2cPage.ASM_SearchCustomer.sendKeys(testData.B2C.getLoginID());
			b2cPage.ASM_CustomerResult.click();
			Common.sleep(1500);
			//Start ASM Session
			b2cPage.ASM_StartSession.click();
			Thread.sleep(6000);
			
			b2cPage.assistedServiceMode_copyTransaction.click();
			Thread.sleep(6000);
			b2cPage.assistedServiceMode_transactionType.click();
			b2cPage.assistedServiceMode_transactionType_QuoteType.click();
			b2cPage.assistedServiceMode_transactionType_QuoteIDTxtBox.sendKeys(quoteNum);
			b2cPage.assistedServiceMode_copyIt.click();
			Assert.assertEquals(b2cPage.QuoteDetails_Quote_Quantity.getAttribute("value"), "3");
			
			//----------------- Step:9 Edit Quote ---------------//
			Dailylog.logInfoDB(9, "Edit Quote ", Store, testName);
			
			b2cPage.Quote_editButton.click();
			Common.sleep(2000);
			
			//----------------- Step:10 Add another product ---------------//
			Dailylog.logInfoDB(10, "Add another product", Store, testName);
			
			
			b2cPage.Cart_QuickOrderTextBox.clear();
			b2cPage.Cart_QuickOrderTextBox.sendKeys(testData.B2C.getDefaultMTMPN());
			b2cPage.Cart_AddButton.click();	
			
			//----------------- Step:11 Save the quote and end session ---------------//
			Dailylog.logInfoDB(11, "Save the quote and end session", Store, testName);
			
			b2cPage.RequestQuoteBtn.click();
			Thread.sleep(6000);
			b2cPage.Quote_SubmitQuoteBtn.click();

			String quoteNum_New = b2cPage.QuoteConfirmPage_QuoteNo.getText();
			b2cPage.ASM_endSession.click();
			
			if(Common.isAlertPresent(driver)){
				driver.switchTo().alert().accept();
			}
			
			Thread.sleep(6000);
			Common.javascriptClick(driver, b2cPage.SignoutASM);
			Thread.sleep(6000);
//			driver.get(loginUrl);
			Common.NavigateToUrl(driver, Browser, loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			Common.sleep(5000);
			
			b2cPage.myAccountPage_startAssistedServiceSession.click();
			
			//----------------- Step:12 Input Transaction ID and convert to order ---------------//
			Dailylog.logInfoDB(12, "Input Transaction ID and convert to order", Store, testName);
			
			b2cPage.TransactionID.sendKeys(quoteNum_New);
			driver.findElement(By.xpath("//*[@id='Q" + quoteNum_New + "']/a/span[1]")).click();
			//Start ASM Session
			b2cPage.ASM_StartSession.click();
			Common.sleep(2000);
			b2cPage.Quote_convertOrder.click();
			
			//13, In shipping page,
			//check Tax Exemption
			//and fill some numbers in
			//override sold to and ship to
			Dailylog.logInfoDB(13, "In the shipping page , check Tax Exemption and fill some numbers in override sold to and ship to",Store, testName);
			
			
			fillExemption();
			
			//----------------- Step:14 Place the order ---------------//
			Dailylog.logInfoDB(14, "Place the order", Store, testName);
			
			B2CCommon.fillDefaultShippingInfo(b2cPage, testData);
			
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			
			if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10)){
				b2cPage.Shipping_AddressMatchOKButton.click();
			}
			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			B2CCommon.fillPaymentAddressInfo(b2cPage,"test" , "test", testData.B2C.getDefaultAddressLine1(), testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(), testData.B2C.getDefaultAddressPostCode(),testData.B2C.getDefaultAddressPhone());
			
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			
			Thread.sleep(10000);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			
			String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
			
			Dailylog.logInfoDB(14, "order number is :" + orderNum, Store, testName);
			//----------------- Step:15 Check Order Status in HMC ---------------//
			Dailylog.logInfoDB(15, "Check Order Status in HMC ", Store, testName);
			
			Thread.sleep(5000);
			
			Common.javascriptClick(driver, b2cPage.SignoutASM);
			Thread.sleep(6000);
			
//			driver.get(hmcHomePageUrl);
			Common.NavigateToUrl(driver, Browser, hmcHomePageUrl);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.HMCOrderCheck(hmcPage, orderNum);
			
			//16, Switch to Administration tab，scroll to the bottom of the page then check the XML
			Dailylog.logInfoDB(16, "Switch to Administration tab，scroll to the bottom of the page then check the XML", Store, testName);
			
			
			driver.findElement(By.xpath("//img[contains(@id,'Content/OrganizerListEntry')]")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//span[contains(@id,'Order.administration')]")).click();
			Thread.sleep(6000);
			
			
			String textarea = Common.javaScriptGetText(driver, driver.findElement(By.xpath("//textarea[contains(@id,'Order.xmlContentShow')]")));
			
			Dailylog.logInfoDB(16, "textarea is :" + textarea, Store, testName);
			
			
			Assert.assertTrue(textarea.contains("1111111111") && textarea.contains("2222222222"));
			
		} catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
	public void fillExemption() throws Exception{

		if(Common.isElementExist(driver, By.xpath("//input[@id='isTaxExemption_toggle']")) && driver.findElement(By.xpath("//input[@id='isTaxExemption_toggle']")).isDisplayed()){
			
			Dailylog.logInfoDB(13, "the Element Tax Exemption exists , so the other informaiton should be filled", Store, testName);
			driver.findElement(By.xpath("//input[@id='isTaxExemption_toggle']")).click();
			
			Thread.sleep(4000);
			
			String overrideSoldTo = "1111111111";
			String shipTo = "2222222222";
			
			driver.findElement(By.xpath("//input[@id='overrideSabrixSoldTo']")).clear();
			driver.findElement(By.xpath("//input[@id='overrideSabrixSoldTo']")).sendKeys(overrideSoldTo);
			
			driver.findElement(By.xpath("//input[@id='sabrixShipTo']")).clear();
			driver.findElement(By.xpath("//input[@id='sabrixShipTo']")).sendKeys(shipTo);
		}else{
			Dailylog.logInfoDB(13, "the Element Tax Exemption exists , so the other informaiton should be filled", Store, testName);
			
			driver.findElement(By.xpath("(//label[@for='isTaxExemption_toggle'])[1]")).click();
			
			Thread.sleep(4000);
			
			String overrideSoldTo = "1111111111";
			String shipTo = "2222222222";
			
			driver.findElement(By.xpath("//input[@id='overrideSabrixSoldTo']")).clear();
			driver.findElement(By.xpath("//input[@id='overrideSabrixSoldTo']")).sendKeys(overrideSoldTo);
			
			driver.findElement(By.xpath("//input[@id='sabrixShipTo']")).clear();
			driver.findElement(By.xpath("//input[@id='sabrixShipTo']")).sendKeys(shipTo);
			
		}
	}
	
	
		
	
}
