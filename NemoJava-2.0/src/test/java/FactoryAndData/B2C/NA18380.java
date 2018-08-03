package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18380Test;

public class NA18380 {
	@DataProvider(name = "18380")
	public static Object[][] Data18380() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","88GMY700794"},
			{ "US","88GMY700794"},
			{ "NZ","88GMY700794"},
			{ "USEPP","88GMY700794"},
			{ "CA","88GMY700794"}
			},
				PropsUtils.getTargetStore("NA-18380"));
	}

	@Factory(dataProvider = "18380")
	public Object[] createTest(String store,String partnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA18380Test(store,partnumber);

		return tests;
	}
}
