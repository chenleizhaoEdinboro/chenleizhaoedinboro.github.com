package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27312Test;

public class NA27312 {

	@DataProvider(name = "27312")
	public static Object[][] Data27133() {
		return Common.getFactoryData(new Object[][] { 
			{ "IN" }, 
			},
			PropsUtils.getTargetStore("NA-27312"));
	}

	@Factory(dataProvider = "27312")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27312Test(store);

		return tests;
	}

}
