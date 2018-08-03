package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22978Test;

public class NA22978 {

	@DataProvider(name = "22978")
	public static Object[][] Data22978() {
		return  Common.getFactoryData(new Object[][] { 
				{"AU"},
				{"US"},
				{"JP"}
		},PropsUtils.getTargetStore("NA-22978"));
	}

	@Factory(dataProvider = "22978")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22978Test(store);

		return tests;
	}
}
