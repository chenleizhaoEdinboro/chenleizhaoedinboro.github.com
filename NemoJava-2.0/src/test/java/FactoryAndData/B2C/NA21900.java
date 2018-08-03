package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21900Test;


public class NA21900 {
	@DataProvider(name = "21900")
	public static Object[][] Data21900() {
		return Common.getFactoryData(new Object[][] {				
				{ "AU" }				
		},PropsUtils.getTargetStore("NA-21900"));
	}

	@Factory(dataProvider = "21900")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21900Test(store);

		return tests;
	}

}
