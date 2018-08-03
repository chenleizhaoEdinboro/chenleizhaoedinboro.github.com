package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA27225Test;

public class NA27225 {

	@DataProvider(name = "27225")
	public static Object[][] Data27225() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}, 
			{ "JP"}, 
			{ "US"} 
			},
				PropsUtils.getTargetStore("NA-27225"));
	}

	@Factory(dataProvider = "27225")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27225Test(store);

		return tests;
	}

}
