package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21905Test;

public class NA21905 {
	@DataProvider(name = "21905")
	public static Object[][] Data21905() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				{ "US" }
		},PropsUtils.getTargetStore("NA-21905"));
	}

	@Factory(dataProvider = "21905")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA21905Test(store);
		return tests;
	}
}
