package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26458Test;

public class NA26458 {

	@DataProvider(name = "26458")
	public static Object[][] Data26458() {
		return Common.getFactoryData(new Object[][] { 
			{ "HKZF" },
			{ "JP" },
			{ "US" }
			},PropsUtils.getTargetStore("NA-26458"));
	}

	@Factory(dataProvider = "26458")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26458Test(store);

		return tests;
	}

}
