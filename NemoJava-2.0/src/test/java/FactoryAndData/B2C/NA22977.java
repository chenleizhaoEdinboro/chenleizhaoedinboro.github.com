package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22977Test;

public class NA22977 {

	@DataProvider(name = "22977")
	public static Object[][] Data22977() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("NA-22977"));
	}

	@Factory(dataProvider = "22977")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22977Test(store);

		return tests;
	}

}