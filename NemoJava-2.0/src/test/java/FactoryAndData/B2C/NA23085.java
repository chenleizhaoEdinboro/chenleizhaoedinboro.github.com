package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23085Test;

public class NA23085 {

	@DataProvider(name = "23085")
	public static Object[][] Data23085() {
		return Common.getFactoryData(new Object[][] { 
			{ "MX" }
			},PropsUtils.getTargetStore("NA-23085"));
	}

	@Factory(dataProvider = "23085")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23085Test(store);

		return tests;
	}

}
