package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17639Test;

public class NA17639 {
	@DataProvider(name = "17639")
	public static Object[][] Data17639() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU"},
				{ "US" },
				{ "CA_AFFINITY" },
				{ "JP" },
				},
				PropsUtils.getTargetStore("NA-17639"));
	}

	@Factory(dataProvider = "17639")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17639Test(store);

		return tests;
	}
}
