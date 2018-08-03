package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23914Test;

public class NA23914 {

	@DataProvider(name = "23914")
	public static Object[][] Data23914() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }
			},PropsUtils.getTargetStore("NA-23914"));
	}

	@Factory(dataProvider = "23914")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23914Test(store);

		return tests;
	}

}
