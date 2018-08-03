package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM367Test;

public class COMM367 {
	@DataProvider(name = "367")
	public static Object[][] Data367() {
		return Common.getFactoryData(new Object[][] { 
			{ "MY"}},
				PropsUtils.getTargetStore("COMM-367"));
	}

	@Factory(dataProvider = "367")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM367Test(store);

		return tests;
	}
}
