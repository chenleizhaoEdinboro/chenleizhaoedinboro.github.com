package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16863Test;

public class NA16863 {
	@DataProvider(name = "16863")
	public static Object[][] Data16863() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU"},
				{ "US"},
				{ "JP"},
				{ "IE"}
				},
				PropsUtils.getTargetStore("NA-16863"));
	}

	@Factory(dataProvider = "16863")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16863Test(store);

		return tests;
	}
}
