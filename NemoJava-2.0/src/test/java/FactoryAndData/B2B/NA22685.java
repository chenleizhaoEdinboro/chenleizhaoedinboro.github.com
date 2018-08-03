package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA22685Test;

public class NA22685 {
	@DataProvider(name = "22685")
	public static Object[][] Data22685() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-22685"));
	}

	@Factory(dataProvider = "22685")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22685Test(store);

		return tests;
	}
}
