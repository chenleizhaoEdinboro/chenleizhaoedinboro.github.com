package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16411Test;

public class NA16411 {
	@DataProvider(name = "16411")
	public static Object[][] Data16411() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-16411"));
	}

	@Factory(dataProvider = "16411")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16411Test(store);

		return tests;
	}
}
