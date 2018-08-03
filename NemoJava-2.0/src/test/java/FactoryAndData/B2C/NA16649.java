package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16649Test;

public class NA16649 {

	@DataProvider(name = "16649")
	public static Object[][] Data16649() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("NA-16649"));
	}

	@Factory(dataProvider = "16649")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16649Test(store);

		return tests;
	}

}
