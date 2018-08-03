package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28925Test;

public class NA28925 {
	@DataProvider(name = "28925")
	public static Object[][] Data28925() {
		return Common.getFactoryData(new Object[][] { { "JP", "Japan" } }, PropsUtils.getTargetStore("NA-28925"));
	}

	@Factory(dataProvider = "28925")
	public Object[] createTest(String store, String country1) {

		Object[] tests = new Object[1];

		tests[0] = new NA28925Test(store, country1);

		return tests;
	}
}
