package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18973Test;

public class NA18973 {
	@DataProvider(name = "18973")
	public static Object[][] Data18973() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "US" },
			{ "JP" }
		},PropsUtils.getTargetStore("NA-18973"));
	}

	@Factory(dataProvider = "18973")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18973Test(store);

		return tests;
	}
}
