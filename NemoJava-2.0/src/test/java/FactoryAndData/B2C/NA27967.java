package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27967Test;

public class NA27967 {

	@DataProvider(name = "NA27967")
	public static Object[][] Data27967() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP", "Japan", "JP public store unit" } },
				PropsUtils.getTargetStore("NA-27967"));
	}

	@Factory(dataProvider = "NA27967")
	public Object[] createTest(String store, String country, String unit) {

		Object[] tests = new Object[1];

		tests[0] = new NA27967Test(store, country, unit);

		return tests;
	}

}
