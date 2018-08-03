package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18480Test;

public class NA18480 {
	@DataProvider(name = "18480")
	public static Object[][] Data18480() {
		return Common.getFactoryData(new Object[][] { 
				{ "JP" }
		},PropsUtils.getTargetStore("NA-18480"));
	}

	@Factory(dataProvider = "18480")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18480Test(store);
		return tests;
	}
}
