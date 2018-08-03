package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM482Test;

public class COMM482 {
	@DataProvider(name = "482")
	public static Object[][] Data482() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("COMM-482"));
	}

	@Factory(dataProvider = "482")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM482Test(store);

		return tests;
	}
}
