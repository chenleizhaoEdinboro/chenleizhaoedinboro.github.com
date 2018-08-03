package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23893Test;

public class NA23893 {

	@DataProvider(name = "23893")
	public static Object[][] Data21876() {
		return Common.getFactoryData( new Object[][] { 
				{ "AU"},
				{ "US"},
				{ "USEPP"},
				{ "US_BPCTO"},
				{ "CA_AFFINITY"},
				{ "JP"},
				{ "GB"},
				{ "HK"}
		},PropsUtils.getTargetStore("NA-23893"));
	}

	@Factory(dataProvider = "23893")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];

		tests[0] = new NA23893Test(store);

		return tests;
	}

}
