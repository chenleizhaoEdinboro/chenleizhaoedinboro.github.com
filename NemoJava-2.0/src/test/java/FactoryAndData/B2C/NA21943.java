package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21943Test;

public class NA21943 {

	@DataProvider(name = "21943")
	public static Object[][] Data21943() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-21943"));
	}

	@Factory(dataProvider = "21943")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21943Test(store);

		return tests;
	}

}
