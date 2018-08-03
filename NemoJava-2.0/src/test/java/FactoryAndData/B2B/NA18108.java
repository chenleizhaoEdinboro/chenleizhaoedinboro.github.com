package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18108Test;

public class NA18108 {
	@DataProvider(name = "18108")
	public static Object[][] Data18108() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-18108"));
	}

	@Factory(dataProvider = "18108")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18108Test(store); 

		return tests;
	}
}
