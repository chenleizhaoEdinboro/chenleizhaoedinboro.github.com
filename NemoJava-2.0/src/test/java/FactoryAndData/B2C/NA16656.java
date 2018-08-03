package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16656Test;

public class NA16656 {

	@DataProvider(name = "16656")
	public static Object[][] Data16656() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "US" },
			{ "USEPP" },
			{ "HK" },
			{ "US_OUTLET" },
			{ "CA_AFFINITY" },
			{ "JP" },
			{ "US_BPCTO" },
			{ "GB" }
			},PropsUtils.getTargetStore("NA-16656"));
	}

	@Factory(dataProvider = "16656")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16656Test(store);

		return tests;
	}

}
