package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13158Test;

public class NA13158 {
	@DataProvider(name = "13158")
	public static Object[][] Data13158() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-13158"));
	}

	@Factory(dataProvider = "13158")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13158Test(store);

		return tests;
	}
}
