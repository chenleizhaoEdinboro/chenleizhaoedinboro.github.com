package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21942Test;

public class NA21942 {
	@DataProvider(name = "21942")
	public static Object[][] Data21942() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-21942"));
	}

	@Factory(dataProvider = "21942")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21942Test(store);

		return tests;
	}
}
