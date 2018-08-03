package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15503Test;

public class NA15503 {

	@DataProvider(name = "15503")
	public static Object[][] Data15503() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "US" }
		},PropsUtils.getTargetStore("NA-15503"));
	}

	@Factory(dataProvider = "15503")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15503Test(store);

		return tests;
	}

}
