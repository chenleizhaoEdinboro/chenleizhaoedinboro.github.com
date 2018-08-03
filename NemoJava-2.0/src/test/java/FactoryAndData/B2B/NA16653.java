package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16653Test;

public class NA16653 {
	@DataProvider(name = "16653")
	public static Object[][] Data16653() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}
		},PropsUtils.getTargetStore("NA-16653"));
	}

	@Factory(dataProvider = "16653")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16653Test(store);

		return tests;
	}
}
