package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25597Test;

public class NA25597 {

	@DataProvider(name = "25597")
	public static Object[][] Data25597() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"},
			{ "US"},
			{ "USEPP"},
			{ "US_BPCTO"},
			{ "CA_AFFINITY"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-25597"));
	}

	@Factory(dataProvider = "25597")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25597Test(store);

		return tests;
	}

}
