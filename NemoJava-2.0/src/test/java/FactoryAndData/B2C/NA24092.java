package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA24092Test;

public class NA24092 {

	@DataProvider(name = "24092")
	public static Object[][] Data24092() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }
			},PropsUtils.getTargetStore("NA-24092"));
	}

	@Factory(dataProvider = "24092")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA24092Test(store);

		return tests;
	}

}
