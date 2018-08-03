package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18342Test;

public class NA18342 {
	@DataProvider(name = "18342")
	public static Object[][] Data18342() {
		return Common.getFactoryData(new Object[][] { 
			
			{ "US"}
			},
				PropsUtils.getTargetStore("NA-18342"));
	}

	@Factory(dataProvider = "18342")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18342Test(store);

		return tests;
	}
}
