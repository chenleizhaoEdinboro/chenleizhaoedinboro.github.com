package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16968Test;

public class NA16968 {

	@DataProvider(name = "16968")
	public static Object[][] Data16968() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "NZ" }
			},
				PropsUtils.getTargetStore("NA-16968"));
	}

	@Factory(dataProvider = "16968")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16968Test(store);

		return tests;
	}

}
