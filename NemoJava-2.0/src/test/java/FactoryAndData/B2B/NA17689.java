package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17689Test;

public class NA17689 {

	@DataProvider(name = "17689")
	public static Object[][] Data17689() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "US" },
			{ "JP" }
		},PropsUtils.getTargetStore("NA-17689"));
	}

	@Factory(dataProvider = "17689")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17689Test(store);

		return tests;
	}

}
