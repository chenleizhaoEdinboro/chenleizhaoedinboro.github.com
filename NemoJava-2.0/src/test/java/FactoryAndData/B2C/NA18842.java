package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18842Test;

public class NA18842 {
	@DataProvider(name = "18842")
	public static Object[][] Data18842() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","about-lenovo-2014-cssv2"},
			{ "AU","about-lenovo-2014-js-2"},
			{ "NZ","about-lenovo-2014-cssv2"},
			{ "NZ","about-lenovo-2014-js-2"}
		},
				PropsUtils.getTargetStore("NA-18842"));
	}

	@Factory(dataProvider = "18842")
	public Object[] createTest(String store,String cssAndjs) {

		Object[] tests = new Object[1];

		tests[0] = new NA18842Test(store,cssAndjs);

		return tests;
	}
}
