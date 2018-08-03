package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13165Test;

public class NA13165 {
	@DataProvider(name = "13165")
	public static Object[][] Data13165() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-13165"));
	}

	@Factory(dataProvider = "13165")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13165Test(store);

		return tests;
	}
}
