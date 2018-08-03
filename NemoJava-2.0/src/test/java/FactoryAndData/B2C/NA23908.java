package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23908Test;

public class NA23908 {

	@DataProvider(name = "23908")
	public static Object[][] Data23908() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }, 
			{ "JP_SMB" },
		},PropsUtils.getTargetStore("NA-23908"));
	}

	@Factory(dataProvider = "23908")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23908Test(store);

		return tests;
	}

}
