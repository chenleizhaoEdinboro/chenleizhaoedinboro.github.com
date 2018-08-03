package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA10264Test;

public class NA10264 {
	@DataProvider(name = "10264")
	public static Object[][] Data10264() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-10264"));
	}

	@Factory(dataProvider = "10264")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA10264Test(store);

		return tests;
	}
}
