package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13181Test;

public class NA13181 {
	@DataProvider(name = "13181")
	public static Object[][] Data13181() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-13181"));
	}

	@Factory(dataProvider = "13181")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13181Test(store);

		return tests;
	}
}
