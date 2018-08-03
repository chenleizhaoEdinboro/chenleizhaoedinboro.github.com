package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18036Test;

public class NA18036 {
	@DataProvider(name = "18036")
	public static Object[][] Data18036() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				{ "US" },
				{ "JP" }
		},PropsUtils.getTargetStore("NA-18036"));
	}

	@Factory(dataProvider = "18036")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18036Test(store);
		return tests;
	}
}
