package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25656Test;


public class NA25656 {

	@DataProvider(name = "25656")
	public static Object[][] Data25656() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-25656"));
	}

	@Factory(dataProvider = "25656")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25656Test(store);

		return tests;
	}

}
