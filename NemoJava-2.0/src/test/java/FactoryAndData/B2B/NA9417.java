package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA9417Test;

public class NA9417 {
	@DataProvider(name = "9417")
	public static Object[][] Data9417() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-9417"));
	}

	@Factory(dataProvider = "9417")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA9417Test(store);

		return tests;
	}
}
