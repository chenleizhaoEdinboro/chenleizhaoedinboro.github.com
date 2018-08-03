package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25269Test;

public class NA25269 {

	@DataProvider(name = "25269")
	public static Object[][] Data21876() {
		return Common.getFactoryData( new Object[][] { 
				{ "US_SMB"}
		},PropsUtils.getTargetStore("NA-25269"));
	}

	@Factory(dataProvider = "25269")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];

		tests[0] = new NA25269Test(store);

		return tests;
	}

}
