package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26087Test;

public class NA26087 {

	@DataProvider(name = "26087")
	public static Object[][] Data26087() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "USEPP"},
			{ "US_BPCTO"},
			{ "CA_AFFINITY"},
			{ "JP"},
			{ "HK"}
		},PropsUtils.getTargetStore("NA-26087"));
	}

	@Factory(dataProvider = "26087")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26087Test(store);

		return tests;
	}

}
