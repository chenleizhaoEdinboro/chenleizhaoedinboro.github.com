package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25040Test;
import TestScript.B2C.NA20177Test;

public class NA25040 {

	@DataProvider(name = "25040")
	public static Object[][] Data25040() {
		return Common.getFactoryData(new Object[][] { 
			{"AU"},
			{"CA"},
			{"HK"},
			{"US"}
		},PropsUtils.getTargetStore("NA-25040"));
	}

	@Factory(dataProvider = "25040")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25040Test(store);

		return tests;
	}

}
