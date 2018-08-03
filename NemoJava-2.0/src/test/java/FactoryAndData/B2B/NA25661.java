package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25661Test;

public class NA25661{

	@DataProvider(name = "25661")
	public static Object[][] Data25661() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-25661"));
	}
	@Factory(dataProvider = "25661")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25661Test(store);

		return tests;
	}
}
