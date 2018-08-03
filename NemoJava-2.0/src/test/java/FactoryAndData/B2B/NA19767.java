package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19767Test;

public class NA19767 {

	@DataProvider(name = "19767")
	public static Object[][] Data19767() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-19767"));
	}

	@Factory(dataProvider = "19767")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19767Test(store);

		return tests;
	}

}
