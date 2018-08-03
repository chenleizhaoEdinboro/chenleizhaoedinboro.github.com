package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25599Test;

public class NA25599 {

	@DataProvider(name = "25599")
	public static Object[][] Data25599() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }, 
		},PropsUtils.getTargetStore("NA-25599"));
	}

	@Factory(dataProvider = "25599")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25599Test(store);

		return tests;
	}

}
