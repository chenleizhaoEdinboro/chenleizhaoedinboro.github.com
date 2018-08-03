package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25413Test;

public class NA25413 {

	@DataProvider(name = "25413")
	public static Object[][] Data25413() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }, 
			{ "JP_SMB" },
		},PropsUtils.getTargetStore("NA-25413"));
	}

	@Factory(dataProvider = "25413")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25413Test(store);

		return tests;
	}

}
