package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23036Test;

public class NA23036 {

	@DataProvider(name = "23036")
	public static Object[][] Data23036() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"}, 
		},PropsUtils.getTargetStore("NA-23036"));
	}

	@Factory(dataProvider = "23036")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];

		tests[0] = new NA23036Test(store);

		return tests;
	}

}
