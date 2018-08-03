package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17280Test;

public class NA17280 {

	@DataProvider(name = "17280")
	public static Object[][] Data17280() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" }, 
			{ "US" } 
			},
				PropsUtils.getTargetStore("NA-17280"));
	}

	@Factory(dataProvider = "17280")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17280Test(store);

		return tests;
	}

}
