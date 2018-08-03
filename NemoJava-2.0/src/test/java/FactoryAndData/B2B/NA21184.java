package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21184Test;

public class NA21184 {

	@DataProvider(name = "21184")
	public static Object[][] Data21184() {
		return Common.getFactoryData ( new Object[][] {
			{ "AU" } 
		}, PropsUtils.getTargetStore("NA-21184"));
	}

	@Factory(dataProvider = "21184")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21184Test(store);

		return tests;
	}

}
