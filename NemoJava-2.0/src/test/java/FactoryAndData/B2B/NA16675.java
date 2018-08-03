package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16675Test;

public class NA16675 {

	@DataProvider(name = "16675")
	public static Object[][] Data16868() {
		return Common.getFactoryData(new Object[][] {
			{ "AU" },
			{ "JP" },
			{ "US" }
		},PropsUtils.getTargetStore("NA-16675"));
	}

	@Factory(dataProvider = "16675")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16675Test(store);

		return tests;
	}

}

