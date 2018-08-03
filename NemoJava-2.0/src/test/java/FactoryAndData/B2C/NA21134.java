package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21134Test;

public class NA21134 {

	@DataProvider(name = "21134")
	public static Object[][] Data21134() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("NA-21134"));
	}

	@Factory(dataProvider = "21134")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21134Test(store);

		return tests;
	}

}
