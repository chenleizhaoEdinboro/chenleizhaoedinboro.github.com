package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16658Test;

public class NA16658 {

	@DataProvider(name = "16658")
	public static Object[][] Data16658() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" }, 
			{ "US" } },
				PropsUtils.getTargetStore("NA-16658"));
	}

	@Factory(dataProvider = "16658")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16658Test(store);

		return tests;
	}

}
