package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM491Test;

public class COMM491 {
	@DataProvider(name = "491")
	public static Object[][] Data491() {
		return Common.getFactoryData(new Object[][] { 
			{"US"}
		},PropsUtils.getTargetStore("COMM-491"));
	}

	@Factory(dataProvider = "491")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM491Test(store);

		return tests;
	}
}
