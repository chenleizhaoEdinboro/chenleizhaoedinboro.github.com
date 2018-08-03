package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOPE348Test;

public class SHOPE348 {

	@DataProvider(name = "SHOPE348")
	public static Object[][] Data348() {
		return Common.getFactoryData(new Object[][] { 
			{"US"},
			{"CA"},
			{"AU"},
			{"NZ"},
			
			},
				PropsUtils.getTargetStore("SHOPE-348"));
	}

	@Factory(dataProvider = "SHOPE348")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE348Test(store);

		return tests;
	}

}
