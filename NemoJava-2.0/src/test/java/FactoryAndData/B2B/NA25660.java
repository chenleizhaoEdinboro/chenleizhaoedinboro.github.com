package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25660Test;

public class NA25660{

	@DataProvider(name = "25660")
	public static Object[][] Data25658() {
		return Common.getFactoryData(new Object[][] {
			{ "AU" }
		}, PropsUtils.getTargetStore("NA-25660"));
	}
	@Factory(dataProvider = "25660")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25660Test(store);

		return tests;
	}
}
