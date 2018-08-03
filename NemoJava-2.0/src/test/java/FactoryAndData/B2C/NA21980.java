package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21980Test;


public class NA21980 {
	@DataProvider(name = "21980")
	public static Object[][] Data21980() {
		return Common.getFactoryData(new Object[][] {				
				{"JP"},
				{"AU"},
				{"US"},
				{"USEPP"},
				{"US_BPCTO"},
				{"CA_AFFINITY"},
		},PropsUtils.getTargetStore("NA-21980"));
	}

	@Factory(dataProvider = "21980")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21980Test(store);

		return tests;
	}

}