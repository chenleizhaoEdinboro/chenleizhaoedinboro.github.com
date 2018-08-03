package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.CONTENT409Test;

public class CONTENT409 {
	@DataProvider(name = "CONTENT409")
	public static Object[][] Data409() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("CONTENT-409"));
	}

	@Factory(dataProvider = "CONTENT409")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT409Test(store);

		return tests;
	}
}
