package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17650Test;

public class NA17650 {

	@DataProvider(name = "17650")
	public static Object[][] Data17650() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP" }
			},PropsUtils.getTargetStore("NA-17650"));
	}

	@Factory(dataProvider = "17650")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17650Test(store);

		return tests;
	}

}
