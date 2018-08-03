package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA27290Test;

public class NA27290 {

	@DataProvider(name = "27290")
	public static Object[][] Data15487() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "US" },
			{ "JP" }
		},PropsUtils.getTargetStore("NA-27290"));
	}

	@Factory(dataProvider = "27290")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27290Test(store);

		return tests;
	}

}
