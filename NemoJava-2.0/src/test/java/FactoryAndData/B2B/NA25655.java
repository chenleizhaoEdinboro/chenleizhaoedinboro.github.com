package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25655Test;


public class NA25655 {

	@DataProvider(name = "25655")
	public static Object[][] Data25655() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-25655"));
	}

	@Factory(dataProvider = "25655")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25655Test(store);

		return tests;
	}

}
