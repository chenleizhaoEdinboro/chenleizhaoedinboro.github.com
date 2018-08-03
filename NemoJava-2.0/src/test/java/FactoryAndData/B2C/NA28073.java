package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28073Test;

public class NA28073 {
	@DataProvider(name = "28073")
	public static Object[][] Data28073() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" },
			{ "NZ" }
		},PropsUtils.getTargetStore("NA-28073"));
	}

	@Factory(dataProvider = "28073")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28073Test(store);

		return tests;
	}
}
