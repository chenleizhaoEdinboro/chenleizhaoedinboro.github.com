package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13518Test;


public class NA13518 {

	@DataProvider(name = "13518")
	public static Object[][] Data13741() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-13518"));
	}

	@Factory(dataProvider = "13518")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13518Test(store);

		return tests;
	}

}
