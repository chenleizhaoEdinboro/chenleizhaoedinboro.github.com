package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28214Test;

public class NA28214 {
	@DataProvider(name = "28214")
	public static Object[][] Data28214() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-28214"));
	}

	@Factory(dataProvider = "28214")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28214Test(store);

		return tests;
	}
}
