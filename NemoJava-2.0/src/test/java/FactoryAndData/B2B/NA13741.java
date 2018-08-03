package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13741Test;


public class NA13741 {

	@DataProvider(name = "13741")
	public static Object[][] Data13741() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-13741"));
	}

	@Factory(dataProvider = "13741")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13741Test(store);

		return tests;
	}

}
