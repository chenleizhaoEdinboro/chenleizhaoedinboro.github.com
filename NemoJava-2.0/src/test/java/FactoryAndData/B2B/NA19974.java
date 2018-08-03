package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19974Test;

public class NA19974 {

	@DataProvider(name = "19974")
	public static Object[][] Data19974() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }

		},PropsUtils.getTargetStore("NA-19974"));
	}

	@Factory(dataProvider = "19974")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19974Test(store);

		return tests;
	}

}
