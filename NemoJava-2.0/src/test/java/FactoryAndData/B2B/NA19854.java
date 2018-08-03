package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19854Test;

public class NA19854 {

	@DataProvider(name = "19854")
	public static Object[][] Data19854() {
		return Common.getFactoryData(new Object[][] {
			{ "US" },
			{ "AU" }
		}, PropsUtils.getTargetStore("NA-19854"));
	}

	@Factory(dataProvider = "19854")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19854Test(store);

		return tests;
	}

}
