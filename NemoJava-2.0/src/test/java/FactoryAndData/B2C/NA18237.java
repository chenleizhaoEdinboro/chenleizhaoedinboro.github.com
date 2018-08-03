package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18237Test;

public class NA18237 {

	@DataProvider(name = "NA18237")
	public static Object[][] Data18237() {
		return Common.getFactoryData(new Object[][] {
			{ "AU"},
			{ "US"},
			{ "BR"},
			{ "HK"},
			{ "JP"}
				},PropsUtils.getTargetStore("NA-18237"));
	}

	@Factory(dataProvider = "NA18237")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18237Test(store);

		return tests;
	}

}