package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18360Test;

public class NA18360 {
	@DataProvider(name = "18360")
	public static Object[][] Data18360() {
		return Common.getFactoryData(new Object[][] { 
				{ "US" }
		},PropsUtils.getTargetStore("NA-18360"));
	}

	@Factory(dataProvider = "18360")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18360Test(store);
		return tests;
	}
}
