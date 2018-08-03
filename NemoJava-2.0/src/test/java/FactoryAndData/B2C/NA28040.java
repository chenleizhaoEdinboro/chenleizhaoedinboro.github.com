package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28040Test;

public class NA28040 {
	@DataProvider(name = "28040")
	public static Object[][] Data28040() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" },
			{ "US" },
			{ "NZ" }
		},PropsUtils.getTargetStore("NA-28040"));
	}

	@Factory(dataProvider = "28040")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28040Test(store);

		return tests;
	}
}
