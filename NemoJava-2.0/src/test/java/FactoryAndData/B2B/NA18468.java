package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18468Test;

public class NA18468 {
	@DataProvider(name = "18468")
	public static Object[][] Data18468() {
		return Common.getFactoryData(new Object[][] { 
				{ "US" },
				{ "JP" },
				{ "AU" }
		},PropsUtils.getTargetStore("NA-18468"));
	}

	@Factory(dataProvider = "18468")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18468Test(store);
		return tests;
	}
}
