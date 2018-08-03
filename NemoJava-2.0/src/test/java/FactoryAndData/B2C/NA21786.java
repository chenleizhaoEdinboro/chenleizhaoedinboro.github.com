package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21786Test;

public class NA21786 {

	@DataProvider(name = "21786")
	public static Object[][] Data21786() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "USEPP"},
			{ "US_BPCTO"},
			{ "CA_AFFINITY"},
			{ "JP"},
			{ "HK"}
		},PropsUtils.getTargetStore("NA-21786"));
	}

	@Factory(dataProvider = "21786")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21786Test(store);

		return tests;
	}

}
