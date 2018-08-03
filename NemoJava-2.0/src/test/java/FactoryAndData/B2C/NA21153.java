package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21153Test;

public class NA21153 {

	@DataProvider(name = "21153")
	public static Object[][] Data21153() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "US" },
			{ "USEPP" },
			{ "CA_AFFINITY" },
			{ "US_BPCTO" },
			{ "US_OUTLET" },
			{ "HK" },
			{ "JP" },
		},PropsUtils.getTargetStore("NA-21153"));
	}

	@Factory(dataProvider = "21153")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21153Test(store);

		return tests;
	}

}
