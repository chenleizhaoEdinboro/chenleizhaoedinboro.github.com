package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.CONTENT319Test;

public class CONTENT319 {
	@DataProvider(name = "CONTENT319")
	public static Object[][] Data319() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("CONTENT-319"));
	}

	@Factory(dataProvider = "CONTENT319")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT319Test(store);

		return tests;
	}
}
