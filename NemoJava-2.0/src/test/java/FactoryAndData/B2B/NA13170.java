package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13170Test;

public class NA13170 {
	@DataProvider(name = "13170")
	public static Object[][] Data13170() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}
		},PropsUtils.getTargetStore("NA-13170"));
	}

	@Factory(dataProvider = "13170")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13170Test(store);

		return tests;
	}
}
