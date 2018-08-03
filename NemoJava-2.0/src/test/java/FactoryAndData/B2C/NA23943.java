package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23943Test;

public class NA23943 {

	@DataProvider(name = "23943")
	public static Object[][] Data21876() {
		return Common.getFactoryData( new Object[][] { 
				{ "US_SMB"}
		},PropsUtils.getTargetStore("NA-23943"));
	}

	@Factory(dataProvider = "23943")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];

		tests[0] = new NA23943Test(store);

		return tests;
	}

}
