package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27159Test;

public class NA27159 {

	@DataProvider(name = "27159")
	public static Object[][] Data27159() {
		return Common.getFactoryData(new Object[][] { 
			{ "CA" }
		},PropsUtils.getTargetStore("NA-27159"));
	}

	@Factory(dataProvider = "27159")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27159Test(store);

		return tests;
	}

}
