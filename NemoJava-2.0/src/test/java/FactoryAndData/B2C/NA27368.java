package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27368Test;

public class NA27368 {
	@DataProvider(name = "27368")
	public static Object[][] Data27286() {
		return Common.getFactoryData(new Object[][] { 
				{ "CA" }
			},PropsUtils.getTargetStore("NA-27368"));
		
	}

	@Factory(dataProvider = "27368")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27368Test(store);

		return tests;
	}

}
