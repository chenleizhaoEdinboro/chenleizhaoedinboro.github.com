package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17410Test;

public class NA17410 {
	@DataProvider(name = "17410")
	public static Object[][] Data17410() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				{ "US" },
				{ "JP" }
		},PropsUtils.getTargetStore("NA-17410"));
	}

	@Factory(dataProvider = "17410")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA17410Test(store);
		return tests;
	}
}
