package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM488Test;

public class COMM488 {
	@DataProvider(name = "488")
	public static Object[][] Data488() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("COMM-488"));
	}

	@Factory(dataProvider = "488")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM488Test(store);

		return tests;
	}
}
