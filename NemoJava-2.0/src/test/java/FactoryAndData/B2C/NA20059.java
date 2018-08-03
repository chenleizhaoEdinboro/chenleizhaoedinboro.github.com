package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20059Test;

public class NA20059 {

	@DataProvider(name = "20059")
	public static Object[][] Data20059() {
		return Common.getFactoryData(new Object[][] { 
			{ "MX" }
		},PropsUtils.getTargetStore("NA-20059"));
	}

	@Factory(dataProvider = "20059")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA20059Test(store);

		return tests;
	}

}
