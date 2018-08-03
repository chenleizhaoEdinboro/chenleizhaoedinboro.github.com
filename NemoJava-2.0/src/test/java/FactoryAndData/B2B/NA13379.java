package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13379Test;

public class NA13379 {
	@DataProvider(name = "13379")
	public static Object[][] Data13379() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}
		},PropsUtils.getTargetStore("NA-13379"));
	}

	@Factory(dataProvider = "13379")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13379Test(store);

		return tests;
	}
}
