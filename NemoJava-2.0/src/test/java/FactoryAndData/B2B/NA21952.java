package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21952Test;

public class NA21952 {
	@DataProvider(name = "21952")
	public static Object[][] Data21952() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"},
			{ "AU"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-21952"));
	}

	@Factory(dataProvider = "21952")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21952Test(store);

		return tests;
	}
}
