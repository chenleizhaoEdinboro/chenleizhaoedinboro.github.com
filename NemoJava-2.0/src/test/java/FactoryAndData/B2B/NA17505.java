package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17505Test;

public class NA17505 {
	@DataProvider(name = "17505")
	public static Object[][] Data17505() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"},
			{ "AU"}
		},PropsUtils.getTargetStore("NA-17505"));
	}

	@Factory(dataProvider = "17505")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17505Test(store);

		return tests;
	}
}
