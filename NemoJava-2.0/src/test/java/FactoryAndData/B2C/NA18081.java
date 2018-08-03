package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18081Test;

public class NA18081 {
	@DataProvider(name = "18081")
	public static Object[][] Data18081() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" },
				{ "USEPP" },
				{ "CA_AFFINITY" },
				{ "HK" },
				{ "JP" },
				{ "US_OUTLET" }
		},PropsUtils.getTargetStore("NA-18081"));
	}

	@Factory(dataProvider = "18081")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18081Test(store);
		return tests;
	}
}
