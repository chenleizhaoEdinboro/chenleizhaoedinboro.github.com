package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18960Test;

public class NA18960 {
	@DataProvider(name = "18960")
	public static Object[][] Data18480() {
		return Common.getFactoryData(new Object[][] { 
				{ "US" }
		},PropsUtils.getTargetStore("NA-18960"));
	}

	@Factory(dataProvider = "18960")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18960Test(store);
		return tests;
	}
}