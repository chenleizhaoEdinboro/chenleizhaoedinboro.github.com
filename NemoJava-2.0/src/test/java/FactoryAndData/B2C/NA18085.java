package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18085Test;

public class NA18085 {

	@DataProvider(name = "18085")
	public static Object[][] Data23022() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP" }
		},PropsUtils.getTargetStore("NA-18085"));
	}

	@Factory(dataProvider = "18085")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18085Test(store);

		return tests;
	}

}