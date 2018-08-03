package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17669Test;

public class NA17669 {
	@DataProvider(name = "17669")
	public static Object[][] Data17669() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" },
				{ "USEPP" },
				{ "CA_AFFINITY" },
				{ "US_OUTLET" },
				{ "US_BPCTO" },
				{ "HK" },
				{ "JP" },
				{ "GB" }
			},
				PropsUtils.getTargetStore("NA-17669"));
	}

	@Factory(dataProvider = "17669")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17669Test(store);

		return tests;
	}
}
