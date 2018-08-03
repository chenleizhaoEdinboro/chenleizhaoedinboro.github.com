package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27834Test;

public class NA27834 {

	@DataProvider(name = "27834")
	public static Object[][] Data27834() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },				
				{ "US" },
				{ "JP" },
		},PropsUtils.getTargetStore("NA-27834"));
	}

	@Factory(dataProvider = "27834")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27834Test(store);

		return tests;
	}
}
