package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17380Test;

public class NA17380 {
	@DataProvider(name = "17380")
	public static Object[][] Data17380() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","80XE0004AU"},
			{ "US","ZA1H0006US"},
			{ "US","80VV00CMUS"},
			{ "NZ","80XE002WAU"},
			{ "USEPP","20HDCTO1WWENUS2"},
			{ "CA","80VV00CMUS"}
			},
				PropsUtils.getTargetStore("NA-17380"));
	}

	@Factory(dataProvider = "17380")
	public Object[] createTest(String store,String partnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA17380Test(store,partnumber);

		return tests;
	}
}
