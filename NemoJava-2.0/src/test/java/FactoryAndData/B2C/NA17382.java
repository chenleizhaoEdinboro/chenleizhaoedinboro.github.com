package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17382Test;

public class NA17382 {
	@DataProvider(name = "17382")
	public static Object[][] Data17382() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","80XE0004AU"},
			{ "US","80VV00CMUS"},
			{ "NZ","80XE002WAU"},
			{ "USEPP","20HDCTO1WWENUS2"},
			{ "CA","80VV00CMUS"}
			},
				PropsUtils.getTargetStore("NA-17382"));
	}

	@Factory(dataProvider = "17382")
	public Object[] createTest(String store,String partnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA17382Test(store,partnumber);

		return tests;
	}
}
