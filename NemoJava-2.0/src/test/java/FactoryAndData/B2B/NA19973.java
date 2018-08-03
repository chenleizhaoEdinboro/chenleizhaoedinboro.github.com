package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19973Test;

public class NA19973 {

	@DataProvider(name = "19973")
	public static Object[][] Data19973() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }

		},PropsUtils.getTargetStore("NA-19973"));
	}

	@Factory(dataProvider = "19973")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19973Test(store);

		return tests;
	}

}
