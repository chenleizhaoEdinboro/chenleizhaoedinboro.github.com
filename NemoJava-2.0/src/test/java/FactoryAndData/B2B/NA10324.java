package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA10324Test;

public class NA10324 {
	@DataProvider(name = "10324")
	public static Object[][] Data10324() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-10324"));
	}

	@Factory(dataProvider = "10324")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA10324Test(store);

		return tests;
	}
}
