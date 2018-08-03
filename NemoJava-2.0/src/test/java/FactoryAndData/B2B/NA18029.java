package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18029Test;

public class NA18029 {
	@DataProvider(name = "18029")
	public static Object[][] Data18029() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" }
		},PropsUtils.getTargetStore("NA-18029"));
	}

	@Factory(dataProvider = "18029")
	public Object[] CreateTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18029Test(store);
		return tests;
	}
}
