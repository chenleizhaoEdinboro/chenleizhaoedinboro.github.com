package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27286Test;

public class NA27286 {
	@DataProvider(name = "27286")
	public static Object[][] Data27286() {
		return Common.getFactoryData(new Object[][] { 
				{ "MY","4X40E77324"},
				{ "AU","4X40E77324" }, 
			 	{ "NZ","4X40E77324" }, 
				{ "US","4X40E77324" },
				{ "CA","4X40E77324" },
				{ "JP","4X40E77324" }
			},PropsUtils.getTargetStore("NA-27286"));
		
	}

	@Factory(dataProvider = "27286")
	public Object[] createTest(String store,String ProductNo) {

		Object[] tests = new Object[1];

		tests[0] = new NA27286Test(store, ProductNo);

		return tests;
	}

}
