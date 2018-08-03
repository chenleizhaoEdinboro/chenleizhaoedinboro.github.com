package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16415Test;

public class NA16415 {
	@DataProvider(name = "16415")
	public static Object[][] Data16389() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "US" }
		},PropsUtils.getTargetStore("NA-16415"));
	}

	@Factory(dataProvider = "16415")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16415Test(store);

		return tests;
	}
}
