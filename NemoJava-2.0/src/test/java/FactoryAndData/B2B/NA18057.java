package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18057Test;

public class NA18057 {
	@DataProvider(name = "18057")
	public static Object[][] Data18057() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				{ "US" },
				{ "JP" }
		},PropsUtils.getTargetStore("NA-18057"));
	}

	@Factory(dataProvider = "18057")
	public Object[] CreateTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18057Test(store);
		return tests;
	}
}
