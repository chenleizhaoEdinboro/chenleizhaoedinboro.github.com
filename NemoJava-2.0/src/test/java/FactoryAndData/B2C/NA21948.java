package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21948Test;

public class NA21948 {

	@DataProvider(name = "21948")
	public static Object[][] Data21948() {
		return Common.getFactoryData( new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-21948"));
	}

	@Factory(dataProvider = "21948")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21948Test(store);

		return tests;
	}

}
