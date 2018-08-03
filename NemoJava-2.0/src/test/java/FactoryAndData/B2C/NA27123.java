package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27123Test;

public class NA27123 {

	@DataProvider(name = "27123")
	public static Object[][] Data27133() {
		return Common.getFactoryData(new Object[][] { 
			{ "IN" }, 
			},
			PropsUtils.getTargetStore("NA-27123"));
	}

	@Factory(dataProvider = "27123")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27123Test(store);

		return tests;
	}

}
