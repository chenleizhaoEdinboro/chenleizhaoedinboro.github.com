package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21818Test;

public class NA21818 {

	@DataProvider(name = "21818")
	public static Object[][] Data21818() {
		return Common.getFactoryData( new Object[][] { 
			{ "CA_AFFINITY"},
			{ "HK"},
			{ "AU"},
			{ "US"},
			{ "USEPP"},
			{ "US_BPCTO"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-21818"));
	}

	@Factory(dataProvider = "21818")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21818Test(store);

		return tests;
	}

}
