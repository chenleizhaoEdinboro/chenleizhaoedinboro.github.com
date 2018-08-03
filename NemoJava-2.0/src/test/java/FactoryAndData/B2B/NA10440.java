package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA10440Test;

public class NA10440 {
	@DataProvider(name = "10440")
	public static Object[][] Data10440() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-10440"));
	}

	@Factory(dataProvider = "10440")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA10440Test(store);

		return tests;
	}
}
