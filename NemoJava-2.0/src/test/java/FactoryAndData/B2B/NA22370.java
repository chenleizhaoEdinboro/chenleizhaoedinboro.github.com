package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA22370Test;

public class NA22370 {

	@DataProvider(name = "22370")
	public static Object[][] Data22370() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "US" },
			{ "JP" }

		},PropsUtils.getTargetStore("NA-22370"));
	}

	@Factory(dataProvider = "22370")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22370Test(store);

		return tests;
	}

}
