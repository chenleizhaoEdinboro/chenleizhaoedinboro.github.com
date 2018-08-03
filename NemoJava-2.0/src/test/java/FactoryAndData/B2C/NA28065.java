package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28065Test;

public class NA28065 {
	@DataProvider(name = "28065")
	public static Object[][] Data28065() {
		return Common.getFactoryData(new Object[][] { 
				{ "US_OUTLET"},
				},
				PropsUtils.getTargetStore("NA-28065"));
	}

	@Factory(dataProvider = "28065")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28065Test(store);

		return tests;
	}
}
