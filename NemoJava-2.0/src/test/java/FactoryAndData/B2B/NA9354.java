package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA9354Test;

public class NA9354 {
	@DataProvider(name = "9354")
	public static Object[][] Data9354() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-9354"));
	}

	@Factory(dataProvider = "9354")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA9354Test(store);

		return tests;
	}
}
