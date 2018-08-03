package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17401Test;

public class NA17401 {
	@DataProvider(name = "17401")
	public static Object[][] Data17401() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" },
			{ "US" }
		},PropsUtils.getTargetStore("NA-17401"));
	}

	@Factory(dataProvider = "17401")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17401Test(store);

		return tests;
	}
}
