package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21734Test;

public class NA21734 {
	@DataProvider(name = "21734")
	public static Object[][] Data21734() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU"},
				},
				PropsUtils.getTargetStore("NA-21734"));
	}

	@Factory(dataProvider = "21734")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21734Test(store);

		return tests;
	}
}
