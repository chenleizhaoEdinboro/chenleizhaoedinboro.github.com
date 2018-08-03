package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17528Test;

public class NA17528 {
	@DataProvider(name = "17528")
	public static Object[][] Data17528() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU","NZ" }, 
				{ "US","CA" },
				{ "USEPP","AU" },
				{ "CA_AFFINITY","NZ" },
				{ "US_OUTLET","HK" },
				{ "US_BPCTO","NZ" },
				{ "HK","CA" },
				{ "JP","AU" },
				{ "GB","CA" }},
				PropsUtils.getTargetStore("NA-17528"));
	}

	@Factory(dataProvider = "17528")
	public Object[] createTest(String store,String differentStore) {

		Object[] tests = new Object[1];

		tests[0] = new NA17528Test(store,differentStore);

		return tests;
	}
}
