package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18253Test;

public class NA18253 {

	@DataProvider(name = "18253")
	public static Object[][] Data18253() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP" }
		},PropsUtils.getTargetStore("NA-18253"));
	}

	@Factory(dataProvider = "18253")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18253Test(store);

		return tests;
	}

}
