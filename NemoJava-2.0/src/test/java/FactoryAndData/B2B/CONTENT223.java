package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.CONTENT223Test;

public class CONTENT223 {
	@DataProvider(name = "CONTENT223")
	public static Object[][] Data223() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("CONTENT-223"));
	}

	@Factory(dataProvider = "CONTENT223")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT223Test(store);

		return tests;
	}
}
