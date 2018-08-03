package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17384Test;

public class NA17384 {
	@DataProvider(name = "17384")
	public static Object[][] Data17384() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","80VV004WAU"},
			{ "US","80XE002WAU"},
			{ "NZ","80XE002WAU"},
			{ "USEPP","80XE002WAU"},
			{ "CA","80VV00CMUS"}
			},
				PropsUtils.getTargetStore("NA-17384"));
	}

	@Factory(dataProvider = "17384")
	public Object[] createTest(String store,String partnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA17384Test(store,partnumber);

		return tests;
	}
}
