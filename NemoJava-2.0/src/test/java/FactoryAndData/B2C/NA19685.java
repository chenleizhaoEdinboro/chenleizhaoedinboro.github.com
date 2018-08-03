package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19685Test;

public class NA19685 {

	@DataProvider(name = "19685")
	public static Object[][] Data19685() {
		return Common.getFactoryData(new Object[][] { 
			{ "USEPP" }
			},PropsUtils.getTargetStore("NA-19685"));
	}

	@Factory(dataProvider = "19685")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19685Test(store);

		return tests;
	}

}
