package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21297Test;

public class NA21297 {

	@DataProvider(name = "21297")
	public static Object[][] Data21297() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" },
			{ "AU" },
			{ "JP" }
		},PropsUtils.getTargetStore("NA-21297"));
	}

	@Factory(dataProvider = "21297")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21297Test(store);

		return tests;
	}

}
