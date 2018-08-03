package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17127Test;

public class NA17127 {

	@DataProvider(name = "17127")
	public static Object[][] Data17127() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"}, 
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-17127"));
	}

	@Factory(dataProvider = "17127")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17127Test(store);

		return tests;
	}

}
