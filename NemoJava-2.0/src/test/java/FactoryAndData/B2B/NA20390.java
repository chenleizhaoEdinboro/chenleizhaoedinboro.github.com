package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA20390Test;

public class NA20390 {
	@DataProvider(name = "20390")
	public static Object[][] Data20390() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-20390"));
	}

	@Factory(dataProvider = "20390")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA20390Test(store);

		return tests;
	}
}
