package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13519Test;


public class NA13519 {

	@DataProvider(name = "13519")
	public static Object[][] Data13741() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("13519"));
	}

	@Factory(dataProvider = "13519")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13519Test(store);

		return tests;
	}

}
