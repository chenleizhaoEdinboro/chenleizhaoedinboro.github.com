package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21832Test;

public class NA21832 {

	@DataProvider(name = "21832")
	public static Object[][] Data21832() {
		return Common.getFactoryData( new Object[][] { 
			{ "CA"}
		},PropsUtils.getTargetStore("NA-21832"));
	}

	@Factory(dataProvider = "21832")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21832Test(store);

		return tests;
	}

}
