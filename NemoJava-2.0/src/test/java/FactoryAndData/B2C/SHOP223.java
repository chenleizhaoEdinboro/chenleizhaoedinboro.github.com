package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP223Test;

public class SHOP223 {
	@DataProvider(name = "223")
	public static Object[][] Data223() {
		return Common.getFactoryData(new Object[][] { 
				{ "HK","4X40E77324"},
				{ "HKZF","4X40E77324" }, 
			 	{ "SG","4X40E77324" }, 
				{ "TW","4X40E77324" }
			},PropsUtils.getTargetStore("SHOPE-223"));
		
	}

	@Factory(dataProvider = "223")
	public Object[] createTest(String store,String ProductNo) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP223Test(store, ProductNo);

		return tests;
	}

}
