package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18405Test;

public class NA18405 {
	@DataProvider(name = "18405")
	public static Object[][] Data18405() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }
		},PropsUtils.getTargetStore("NA-18405"));
	}

	@Factory(dataProvider = "18405")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18405Test(store);
		return tests;
	}
}
