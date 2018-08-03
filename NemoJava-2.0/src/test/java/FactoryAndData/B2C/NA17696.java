package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17696Test;

public class NA17696 {

	@DataProvider(name = "17696")
	public static Object[][] Data17696() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_BPCTO" },
			}, PropsUtils.getTargetStore("NA-17696"));
	}

	@Factory(dataProvider = "17696")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17696Test(store);

		return tests;
	}

}

