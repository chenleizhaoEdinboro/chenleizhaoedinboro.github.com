package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA9729Test;

public class NA9729 {
	@DataProvider(name = "9729")
	public static Object[][] Data9729() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-9729"));
	}

	@Factory(dataProvider = "9729")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA9729Test(store);

		return tests;
	}
}
