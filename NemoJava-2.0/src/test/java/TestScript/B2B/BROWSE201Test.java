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

public class BROWSE201Test extends SuperTestClass {
	String unit;
	String b2bLoginUrl;
	String b2bHomeUrl;
	String BundleMTM;
	String userName;
	HMCPage hmcPage;
	B2BPage b2bPage;
	String date = Common.getDateTimeString();

	public BROWSE201Test(String Store, String unit, String BundleMTM,
			String userName) {
		this.Store = Store;
		this.testName = "BROWSE-201";
		this.unit = unit;
		this.BundleMTM = BundleMTM;
		this.userName = userName;
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {
			"browsegroup", "product", "p2", "b2b" })
	public void BROWSE201(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2bPage = new B2BPage(driver);
			boolean flag;
			String NormalCTO="";
			if (Store.equals("AU")) {

				b2bLoginUrl = testData.B2B.getLoginUrl()
						.replace("adobe_global", unit)
						.replace("1213654197", unit);
				b2bHomeUrl = testData.B2B.getHomePageUrl()
						.replace("adobe_global", unit)
						.replace("1213654197", unit);
				// HMC Configuration
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				HMCSearchB2BUnit(hmcPage,unit);
				hmcPage.B2BUnit_siteAttribute.click();
				hmcPage.B2BUNIT_CustomiseForCM_Yes.click();
				hmcPage.B2BUNIT_CustomiseBuy_Yes.click();
				hmcPage.SaveButton.click();
				HMCCommon.cleanRadis(hmcPage, BundleMTM);
				hmcPage.Home_closeSession.click();
				driver.get(b2bLoginUrl);
				B2BCommon.Login(b2bPage, userName, "1q2w3e4r");
				b2bPage.HomePage_productsLink.click();
				Thread.sleep(5000);
				b2bPage.HomePage_Destop.click();
				b2bPage.productPage_agreementsAndContract.click();
				b2bPage.productPage_raidoContractButton.click();
				System.out.println(Common.checkElementDisplays(driver,
						b2bPage.customize, 3));
				Assert.assertTrue(Common.checkElementDisplays(driver,
						b2bPage.customize, 3),
						"set customizeforCM yes and actual button is yes");
				driver.get(b2bHomeUrl + "/p/" + BundleMTM);
				Assert.assertTrue(Common.checkElementDisplays(driver,
						b2bPage.PDP_CustomizeForCM, 3),
						"set customizeforCM yes and actual button is yes");
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				HMCSearchB2BUnit(hmcPage,unit);
				hmcPage.B2BUnit_siteAttribute.click();
				hmcPage.B2BUNIT_CustomiseForCM_No.click();
				hmcPage.B2BUNIT_CustomiseBuy_Yes.click();
				hmcPage.SaveButton.click();
				HMCCommon.cleanRadis(hmcPage, BundleMTM);
				hmcPage.Home_closeSession.click();
				driver.get(b2bLoginUrl);
				B2BCommon.Login(b2bPage, userName, "1q2w3e4r");
				b2bPage.HomePage_productsLink.click();
				Thread.sleep(5000);
				b2bPage.HomePage_Destop.click();
				b2bPage.productPage_agreementsAndContract.click();
				b2bPage.productPage_raidoContractButton.click();
				System.out.println(Common.checkElementDisplays(driver,
						b2bPage.customize, 3));
				Assert.assertFalse(Common.checkElementDisplays(driver,
						b2bPage.customize, 3),
						"set customizeforCM no and actual button is no");
				driver.get(b2bHomeUrl + "/p/" + BundleMTM);
				System.out.println(Common.checkElementDisplays(driver,
						b2bPage.PDP_CustomizeForCM, 3));
				Assert.assertFalse(Common.checkElementDisplays(driver,
						b2bPage.PDP_CustomizeForCM, 3),
						"set customizeforCM no and actual button is no");
				

			} else if (Store.equals("US")) {
				driver.get(testData.B2B.getLoginUrl());
				B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), "1q2w3e4r");
				b2bPage.HomePage_productsLink.click();
				Thread.sleep(5000);
				b2bPage.HomePage_Laptop.click();
				b2bPage.productPage_agreementsAndContract.click();
				try {
					b2bPage.productPage_radioAgreementButton.click();
					flag = true;
				} catch (Exception e) {
					flag = false;
				}
				if (flag) {
					b2bPage.customize.click();
					NormalCTO = driver.getCurrentUrl().split("/p/")[1];
				} else {
					Assert.assertTrue(false, "There is no product on US B2B");
				}
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.searchB2BUnit(hmcPage, testData);
				hmcPage.B2BUnit_siteAttribute.click();
				hmcPage.B2BUNIT_CustomiseForCM_Yes.click();
				hmcPage.B2BUNIT_CustomiseBuy_Yes.click();
				hmcPage.SaveButton.click();
				HMCCommon.cleanRadis(hmcPage, NormalCTO);
				hmcPage.Home_closeSession.click();
				driver.get(testData.B2B.getLoginUrl());
				B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), "1q2w3e4r");
				b2bPage.HomePage_productsLink.click();
				Thread.sleep(5000);
				b2bPage.HomePage_Laptop.click();
				b2bPage.productPage_agreementsAndContract.click();
				b2bPage.productPage_radioAgreementButton.click();
				Assert.assertTrue(Common.checkElementDisplays(driver,
						b2bPage.customize, 3),
						"set customizeforCM yes and normal button is yes");
				driver.get(testData.HMC.getHomePageUrl());
				HMCCommon.Login(hmcPage, testData);
				HMCCommon.searchB2BUnit(hmcPage, testData);
				hmcPage.B2BUnit_siteAttribute.click();
				hmcPage.B2BUNIT_CustomiseForCM_No.click();
				hmcPage.B2BUNIT_CustomiseBuy_Yes.click();
				hmcPage.SaveButton.click();
				HMCCommon.cleanRadis(hmcPage, NormalCTO);
				hmcPage.Home_closeSession.click();
				driver.get(testData.B2B.getLoginUrl());
				B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), "1q2w3e4r");
				b2bPage.HomePage_productsLink.click();
				Thread.sleep(5000);
				b2bPage.HomePage_Laptop.click();
				b2bPage.productPage_agreementsAndContract.click();
				b2bPage.productPage_radioAgreementButton.click();
				Assert.assertTrue(Common.checkElementDisplays(driver,
						b2bPage.customize, 3),
						"set customizeforCM no and normal button is yes");

			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void HMCSearchB2BUnit(HMCPage hmcPage,String Unit) {
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
