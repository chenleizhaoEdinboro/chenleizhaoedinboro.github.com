package TestScript.B2C;


import org.openqa.selenium.By;
import org.springframework.ui.context.Theme;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class NA27336Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;
	
	public String homePageUrl;
	public String loginUrl;
	public String cartUrl;
	
	public String hmcLoginUrl;
	public String hmcHomePageUrl;
	
	public NA27336Test(String store) {
		this.Store = store;
		this.testName = "NA-27336";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "telesales", "p2", "b2c"})
	public void NA27336(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			homePageUrl = testData.B2C.getTeleSalesUrl();
			loginUrl = testData.B2C.getTeleSalesUrl() + "/login";
			cartUrl = testData.B2C.getTeleSalesUrl() + "/cart";
			
			hmcLoginUrl = testData.HMC.getHomePageUrl();
			hmcHomePageUrl = testData.HMC.getHomePageUrl();
			
			// this case just run on us 
			
			
			//1, Go HMC to enable show chat IP text input on checkout page 3.
			Dailylog.logInfoDB(1, "Go HMC to enable show chat IP text input on checkout page 3", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			goToSiteAttribute();
			
			
			//2, Select yes and save it 
			Dailylog.logInfoDB(2, "Select yes and save it!", Store, testName);
			
			hmcPage.chatIP_yes.click();
			hmcPage.SaveButton.click();
			
			hmcPage.Home_closeSession.click();
			
			//3, Go to website and login as telesales user, and then start ASM session.
			//4, Search customer and assign into ASM
			Dailylog.logInfoDB(3, "Go to website and login as telesales user, and then start ASM session", Store, testName);
			Dailylog.logInfoDB(4, "Search customer and assign into ASM", Store, testName);
			
			driver.get(loginUrl);
			B2CCommon.login(b2cPage, testData.B2C.getTelesalesAccount(), testData.B2C.getTelesalesPassword());
			
			B2CCommon.loginASMAndStartSession(driver, b2cPage, "customer", testData.B2C.getLoginID());
			
			
			//5, Purchase product and start checkout
			Dailylog.logInfoDB(5, "Purchase product and start checkout", Store, testName);
			
			driver.get(cartUrl);
			
			B2CCommon.clearTheCart(driver, b2cPage, testData);
			B2CCommon.addPartNumberToCartTele(b2cPage, testData.B2C.getDefaultMTMPN());

			b2cPage.Cart_CheckoutButton.click();
			
			B2CCommon.fillShippingInfo(b2cPage, "AutoFirstName", "AutoLastName", testData.B2C.getDefaultAddressLine1(),
					testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
					testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(),
					testData.B2C.getLoginID());
			Thread.sleep(3000);
			Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
			
			B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
			Thread.sleep(5000);
			Common.javascriptClick(driver, b2cPage.Payment_ContinueButton);
			
			//6, Fill out IP address which Tele user get from chat tool, and create order
			Dailylog.logInfoDB(6, "Fill out IP address which Tele user get from chat tool, and create order", Store, testName);
			
			driver.findElement(By.xpath("//input[@id='customerChatIp']")).clear();
			driver.findElement(By.xpath("//input[@id='customerChatIp']")).sendKeys("192.168.1.1");
	
			Common.scrollToElement(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
			B2CCommon.clickPlaceOrder(b2cPage);
			System.out.println("Clicked place order!");
			
			
			String orderNum = "";
			
			if(Common.isElementExist(driver, By.cssSelector("div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)"))){
				orderNum = b2cPage.OrderThankyou_OrderNumberLabel.getText();
				System.out.println("orderNum is :" + orderNum);
			}else{
				orderNum = b2cPage.OrderThankyou_OrderNumberLabelNew.getText();
				System.out.println("orderNum is :" + orderNum);
			}
	
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			hmcPage.Home_Order.click();
			hmcPage.Home_Order_Orders.click();
			hmcPage.Home_Order_OrderID.clear();
			hmcPage.Home_Order_OrderID.sendKeys(orderNum);
			hmcPage.Home_Order_OrderSearch.click();
			Thread.sleep(5000);
			hmcPage.Home_Order_OrderDetail.click();
			hmcPage.Order_ordersAdministration.click();
			
			String ip = driver.findElement(By.xpath("//input[contains(@id,'Order.zCustomerChatIp')]")).getAttribute("value").toString().trim();
			
			Assert.assertTrue("192.168.1.1".equals(ip),"the ip address is not correct, the customer ip is :" + ip+", the input ip address is 192.168.1.1");

		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	
	
	public void goToSiteAttribute(){
		
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		hmcPage.B2CUnit_IDTextBox.clear();
		hmcPage.B2CUnit_IDTextBox.sendKeys(testData.B2C.getUnit());
		
		hmcPage.B2CUnit_SearchButton.click();
		hmcPage.B2CUnit_FirstSearchResultItem.click();
		
		hmcPage.B2CUnit_SiteAttributeTab.click();
		Common.sleep(10000);	
	}
	
	
}
	


