package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17702Test;

public class NA17702 {
	@DataProvider(name = "17702")
	public static Object[][] Data17702() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "NZ" }
		},PropsUtils.getTargetStore("NA-17702"));
	}

	@Factory(dataProvider = "17702")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA17702Test(store);
		return tests;
	}
}
