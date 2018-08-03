package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA20000Test;

public class NA20000 {

	@DataProvider(name = "20000")
	public static Object[][] Data20000() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}, 
			{ "JP"},
			{ "US"}
		},PropsUtils.getTargetStore("NA-20000"));
	}

	@Factory(dataProvider = "20000")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA20000Test(store);

		return tests;
	}

}
