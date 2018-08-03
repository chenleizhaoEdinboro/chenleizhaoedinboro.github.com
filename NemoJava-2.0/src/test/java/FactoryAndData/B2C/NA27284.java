package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27284Test;

public class NA27284 {
	@DataProvider(name = "27284")
	public static Object[][] Data27284() {
		return Common.getFactoryData(new Object[][] { 
				{ "MY","4X40E77324"},
				{ "AU","4X40E77324" }, 
			 	{ "NZ","4X40E77324" }, 
				{ "US","4X40E77324" },
				{ "KR","4X40E77324" },
				{ "CA","4X40E77324" },
				{ "CO","4X40E77324" },
				{ "US_OUTLET","20HMCTR1WW-PC0LCAUJ" },
				{ "HK","4X40E77324" },
				{ "JPEPP","4X40E77329" },
				{ "SG","4X40E77329" },
				{ "JP","4X40E77324" },
				{ "BR","4X40E77324" },
				{ "MX","4X40E77324" },
				{ "TW","4X40E77324" }
			},PropsUtils.getTargetStore("NA-27284"));
		
	}

	@Factory(dataProvider = "27284")
	public Object[] createTest(String store,String ProductNo) {

		Object[] tests = new Object[1];

		tests[0] = new NA27284Test(store,ProductNo);

		return tests;
	}

}
