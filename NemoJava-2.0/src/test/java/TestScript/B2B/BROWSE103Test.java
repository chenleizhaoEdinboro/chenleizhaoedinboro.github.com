package TestScript.B2B;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE103Test extends SuperTestClass {

	String unit;
	String b2bLoginUrl;
	String b2bHomeUrl;
	String BundleMTM;
	String BundleCTO;
	String userName;
	HMCPage hmcPage;
	B2BPage b2bPage;
	String date = Common.getDateTimeString();
	

	public BROWSE103Test(String Store, String unit, String BundleMTM,
			String BundleCTO, String userName) {
		this.Store = Store;
		this.testName = "BROWSE-103";
		this.unit = unit;
		this.BundleMTM = BundleMTM;
		this.BundleCTO = BundleCTO;
		this.userName = userName;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {
			"browsegroup", "product", "p2", "b2b" })
	public void BROWSE103(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);

			b2bLoginUrl = testData.B2B.getLoginUrl()
					.replace("adobe_global", unit).replace("1213654197", unit);
			b2bHomeUrl = testData.B2B.getHomePageUrl()
					.replace("adobe_global", unit).replace("1213654197", unit);
			// HMC Configuration
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCSearchB2BUnit(unit);
			hmcPage.B2BUnit_siteAttribute.click();
			hmcPage.B2BUNIT_CustomiseForCM_Yes.click();
			hmcPage.B2BUNIT_CustomiseBuy_Yes.click();
			hmcPage.SaveButton.click();
			HMCCommon.cleanRadis(hmcPage, BundleMTM);
			hmcPage.Home_closeSession.click();
			//check the total number of customize button on plp
			driver.get(b2bLoginUrl);
			B2BCommon.Login(b2bPage, userName, "1q2w3e4r");
			b2bPage.HomePage_productsLink.click();
			Thread.sleep(5000);
			b2bPage.HomePage_Destop.click();
			int number= b2bPage.customizeButtons.size();
			Assert.assertTrue(number>0);
			//hot update bundle cto
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCSearchB2BUnit(unit);
			HMCCommon.hotUpdate(driver, hmcPage, BundleCTO);
			HMCCommon.cleanRadis(hmcPage, BundleCTO);
			hmcPage.Home_closeSession.click();
			//check the total customize button after hot update bundle cto			
			driver.get(b2bLoginUrl);
			B2BCommon.Login(b2bPage, userName, "1q2w3e4r");
			b2bPage.HomePage_productsLink.click();
			Thread.sleep(5000);
			b2bPage.HomePage_Destop.click();
			int updatenumber= b2bPage.customizeButtons.size();
			Assert.assertEquals(number, updatenumber);
			//hot update bundle mtm
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCSearchB2BUnit(unit);
			HMCCommon.hotUpdate(driver, hmcPage, BundleMTM);
			HMCCommon.cleanRadis(hmcPage, BundleMTM);
			hmcPage.Home_closeSession.click();		
			//check the total customize button after hot update bundle mtm
			driver.get(b2bLoginUrl);
			B2BCommon.Login(b2bPage, userName, "1q2w3e4r");
			b2bPage.HomePage_productsLink.click();
			Thread.sleep(5000);
			b2bPage.HomePage_Destop.click();
			updatenumber= b2bPage.customizeButtons.size();
			Assert.assertEquals(number, updatenumber);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void HMCSearchB2BUnit(String Unit) {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + Unit);
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(Unit);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
		hmcPage.B2BUnit_siteAttribute.click();
	}

}
