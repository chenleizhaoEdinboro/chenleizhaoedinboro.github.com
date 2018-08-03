package FactoryAndData.B2C;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20315Test;

public class NA20315 {

	@DataProvider(name = "20315")
	public static Object[][] Data20315() {
		return Common.getFactoryData(new Object[][] {  
				{ "US"},
				{ "US_BPCTO"},
				{ "USEPP"}
		},PropsUtils.getTargetStore("NA-20315"));
	}

	@Factory(dataProvider = "20315")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA20315Test(store);

		return tests;
	}

}
