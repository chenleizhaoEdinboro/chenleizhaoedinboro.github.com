package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18598Test;

public class NA18598 {

	@DataProvider(name = "18598")
	public static Object[][] Data18598() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
			
		},PropsUtils.getTargetStore("NA-18598"));
	}

	@Factory(dataProvider = "18598")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18598Test(store);

		return tests;
	}

}
