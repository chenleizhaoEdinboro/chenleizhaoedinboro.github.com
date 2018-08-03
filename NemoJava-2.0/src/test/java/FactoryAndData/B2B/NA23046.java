package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA23046Test;

public class NA23046 {
	@DataProvider(name = "23046")
	public static Object[][] Data23046() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-23046"));
	}

	@Factory(dataProvider = "23046")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23046Test(store);

		return tests;
	}
}
