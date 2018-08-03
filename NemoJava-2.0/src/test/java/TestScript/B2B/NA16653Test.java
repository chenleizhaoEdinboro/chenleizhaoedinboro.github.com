package TestScript.B2B;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Pages.B2BPage;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA16653Test extends SuperTestClass {
	public NA16653Test(String store){
		this.Store = store;
		this.testName = "NA-16653";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p1", "b2b", "compatibility"})
	public void NA16653(ITestContext ctx){
		try{
			this.prepareTest();
			// Per confirmation from Michael, just need to test the OVP page is shown is fine in PreC
			B2BPage b2bPage = new B2BPage(driver);
			B2CPage b2cPage = new B2CPage(driver);
			
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), "1q2w3e4r");
			b2bPage.myAccount_link.click();
			b2bPage.MyAccount_OVPLink.click();
			Common.switchToWindow(driver, 1);
			if(!Common.checkElementDisplays(driver, b2bPage.OVP_CheckStatusButton, 1))
				Assert.fail("B2B doesn't link to OVP!");
			driver.close();
			Common.switchToWindow(driver, 0);
			
			driver.get(testData.envData.getHttpsDomain()+"/au/en/login");
			B2CCommon.login(b2cPage, "lisong2@lenovo.com", "1q2w3e4r");
			b2cPage.MyAccount_ViewOrderHistoryLink.click();
			b2cPage.OrderHistory_TrackOrderStatus.click();
			Common.switchToWindow(driver, 1);
			if(!Common.checkElementDisplays(driver, b2bPage.OVP_CheckStatusButton, 1))
				Assert.fail("B2C doesn't link to OVP!");
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
}
	
			