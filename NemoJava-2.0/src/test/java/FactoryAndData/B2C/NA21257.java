package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21257Test;

public class NA21257 {

	@DataProvider(name = "21257")
	public static Object[][] Data21257() {
		return Common.getFactoryData(new Object[][] { 
			{ "NZ" },
			{ "CA" },
			{ "JP" }
		},PropsUtils.getTargetStore("NA-21257"));
	}

	@Factory(dataProvider = "21257")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21257Test(store);

		return tests;
	}

}
