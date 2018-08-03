package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17457Test;

public class NA17457 {
	@DataProvider(name = "17457")
	public static Object[][] Data17457() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "US" },
			{ "USEPP" },
			{ "CA" },
			{ "CA_AFFINITY" },
			{ "US_OUTLET" },
			{ "US_BPCTO" },
			{ "HK" },
			{ "HKZF" },
			{ "JP" },
			{ "GB" },
		},PropsUtils.getTargetStore("NA-17457"));
	}

	@Factory(dataProvider = "17457")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17457Test(store);

		return tests;
	}
}
