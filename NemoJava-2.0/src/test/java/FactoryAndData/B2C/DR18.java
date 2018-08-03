package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.DR18Test;

public class DR18 {

	@DataProvider(name = "DR-18")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
			{ "FR", "20HRCTO1WWFRFR0","80X7004WFR","TEST_AUTO"  },
     		//{ "GB", "/laptops/thinkpad/x-series/ThinkPad-X1-Carbon-5th-Generation", "20HRCTO1WWENGB0","80X80006UK","autotest80X80006UK" },
			{ "IE", "20HRCTO1WWENIE0","80U20055UK","TEST_AUTO" } 
		}, PropsUtils.getTargetStore("DR-18"));
	}

	@Factory(dataProvider = "DR-18")
	public Object[] createTest(String store, String ctoProduct, String mtmPro,String eCoupon ) {

		Object[] tests = new Object[1];

		tests[0] = new DR18Test(store, ctoProduct, mtmPro,eCoupon);

		return tests;
	}

}