package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA27231Test;

public class NA27231{

	@DataProvider(name = "NA27231")
	public static Object[][] Data25661() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-27231"));
	}
	@Factory(dataProvider = "NA27231")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27231Test(store);

		return tests;
	}
}
