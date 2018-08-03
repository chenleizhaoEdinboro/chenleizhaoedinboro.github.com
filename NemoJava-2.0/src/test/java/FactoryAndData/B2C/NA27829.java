package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27829Test;

public class NA27829 {

	@DataProvider(name = "27829")
	public static Object[][] Data27829() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				{ "NZ" },
				{ "US" },
				{ "CA" },
				{ "JP" },
		},PropsUtils.getTargetStore("NA-27829"));
	}

	@Factory(dataProvider = "27829")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27829Test(store);

		return tests;
	}
}
