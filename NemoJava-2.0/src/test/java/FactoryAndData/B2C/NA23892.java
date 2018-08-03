package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23892Test;

public class NA23892 {

	@DataProvider(name = "23892")
	public static Object[][] Data21876() {
		return Common.getFactoryData( new Object[][] { 
				{ "AU"},
				{ "US"},
				{ "USEPP"},
				{ "US_BPCTO"},
				{ "CA_AFFINITY"},
				{ "JP"},
				{ "HK"}
		},PropsUtils.getTargetStore("NA-23892"));
	}

	@Factory(dataProvider = "23892")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];

		tests[0] = new NA23892Test(store);

		return tests;
	}

}
