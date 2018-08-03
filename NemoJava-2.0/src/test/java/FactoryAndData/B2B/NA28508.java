package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28508Test;

public class NA28508 {

	@DataProvider(name = "28508")
	public static Object[][] Data28508() {
		return Common.getFactoryData(new Object[][] { { "US", "United States" } }, PropsUtils.getTargetStore("NA-28508"));
	}

	@Factory(dataProvider = "28508")
	public Object[] createTest(String store, String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA28508Test(store, country);

		return tests;
	}

}
