package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21914Test;

public class NA21914 {

	@DataProvider(name = "21914")
	public static Object[][] Data21914() {
		return Common.getFactoryData(new Object[][] { 
				{"AU"},
				{"US"},
				{"USEPP"},
//				{"US_OUTLET"},
				{"US_BPCTO"},
				{"CA_AFFINITY"},
				{"JP"},
				{"HK"},
				{"GB"}
		},PropsUtils.getTargetStore("NA-21914"));
	}

	@Factory(dataProvider = "21914")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21914Test(store);

		return tests;
	}

}