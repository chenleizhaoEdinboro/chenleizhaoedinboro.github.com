package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28038Test;

public class NA28038 {
	@DataProvider(name = "28038")
	public static Object[][] Data28038() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" },
			{ "NZ" }
		},PropsUtils.getTargetStore("NA-28038"));
	}

	@Factory(dataProvider = "28038")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28038Test(store);

		return tests;
	}
}
