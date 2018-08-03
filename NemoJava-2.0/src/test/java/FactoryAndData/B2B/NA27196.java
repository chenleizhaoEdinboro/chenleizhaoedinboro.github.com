package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA27196Test;

public class NA27196{

	@DataProvider(name = "NA27196")
	public static Object[][] Data25661() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-27196"));
	}
	@Factory(dataProvider = "NA27196")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27196Test(store);

		return tests;
	}
}
