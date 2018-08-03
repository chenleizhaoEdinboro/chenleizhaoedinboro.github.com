package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28210Test;

public class NA28210 {

	@DataProvider(name = "28210")
	public static Object[][] Data28210() {
		return Common.getFactoryData(new Object[][] { { "JP", "Japan" } }, PropsUtils.getTargetStore("NA-28210"));
	}

	@Factory(dataProvider = "28210")
	public Object[] createTest(String store, String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA28210Test(store, country);

		return tests;
	}

}
