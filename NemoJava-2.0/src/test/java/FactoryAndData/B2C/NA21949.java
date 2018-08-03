package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21949Test;

public class NA21949 {
	@DataProvider(name = "21949")
	public static Object[][] Data21949() {
		return Common.getFactoryData(new Object[][] { 
				{ "US" }
		},PropsUtils.getTargetStore("NA-21949"));
	}

	@Factory(dataProvider = "21949")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA21949Test(store);
		return tests;
	}
}
