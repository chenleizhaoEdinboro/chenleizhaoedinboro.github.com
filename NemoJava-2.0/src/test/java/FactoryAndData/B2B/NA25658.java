package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25658Test;

public class NA25658 {

	@DataProvider(name = "26568")
	public static Object[][] Data26568() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-26568"));
	}
	@Factory(dataProvider = "26568")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25658Test(store);

		return tests;
	}
}
