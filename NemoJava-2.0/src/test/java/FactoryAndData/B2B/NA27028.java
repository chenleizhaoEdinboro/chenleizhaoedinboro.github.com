package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA27028Test;

public class NA27028 {
	@DataProvider(name = "27028")
	public static Object[][] Data27028() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }
		},PropsUtils.getTargetStore("NA-27028"));
	}

	@Factory(dataProvider = "27028")
	public Object[] CreateTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA27028Test(store);
		return tests;
	}
}
