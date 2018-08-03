package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA23913Test;

public class NA23913 {
	@DataProvider(name = "23913")
	public static Object[][] Data23913() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"},
			{ "AU"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-23913"));
	}

	@Factory(dataProvider = "23913")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23913Test(store);

		return tests;
	}
}
