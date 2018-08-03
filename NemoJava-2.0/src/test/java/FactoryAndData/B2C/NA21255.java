package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21255Test;

public class NA21255 {

	@DataProvider(name = "21255")
	public static Object[][] Data21255() {
		return Common.getFactoryData(new Object[][] {
				{ "AU" },
				{ "US" }
				}, PropsUtils.getTargetStore("NA-21255"));
	}

	@Factory(dataProvider = "21255")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21255Test(store);

		return tests;
	}

}
