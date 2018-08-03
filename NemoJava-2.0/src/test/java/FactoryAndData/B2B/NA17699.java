package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17699Test;

public class NA17699 {
	@DataProvider(name = "17699")
	public static Object[][] Data17699() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				{ "US" },
				{ "JP" }
		},PropsUtils.getTargetStore("NA-17699"));
	}

	@Factory(dataProvider = "17699")
	public Object[] CreateTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA17699Test(store);
		return tests;
	}
}
