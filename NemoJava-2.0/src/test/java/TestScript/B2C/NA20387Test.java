package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA20387Test extends SuperTestClass {
	private String productID;
	private B2CPage b2cPage;
	private String productCTO;
	private String productMTM;
	
	
	public NA20387Test(String store,String product,String productCTO,String productMTM) {
		this.Store = store;
		this.testName = "NA-20387";
		this.productID = product;
		this.productCTO = productCTO;
		this.productMTM = productMTM;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA20387(ITestContext ctx) {

		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl());
			Common.sleep(3000);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			//=========== Step:1 MTM&CTO products under models tab ===========//
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+productID);
			Common.sleep(3000);
			Assert.assertTrue(b2cPage.Product_viewModel.getText().toLowerCase().contains("view or customize"));
			Dailylog.logInfoDB(1, "MTM&CTO products under models tab ", Store, testName);
			
			//=========== Step:2 Only CTO model under models tab ===========//
			Common.sleep(2000);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+productCTO);
			Common.sleep(3000);
			Assert.assertTrue(b2cPage.Product_viewModel.getText().toLowerCase().contains("view or customize"));
			Dailylog.logInfoDB(2, "Only CTO model under models tab ", Store, testName);
			
			//=========== Step:3 Only MTM model under models tab ===========//
			Common.sleep(2000);
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+productMTM);
			Common.sleep(3000);
			Assert.assertTrue(b2cPage.Product_viewModel.getText().toLowerCase().contains("view models"));
			Dailylog.logInfoDB(3, "Only MTM model under models tab ", Store, testName);
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
	
}
