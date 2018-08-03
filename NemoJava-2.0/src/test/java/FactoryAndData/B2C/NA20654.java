package FactoryAndData.B2C;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20654Test;

public class NA20654 {

	@DataProvider(name = "20654")
	public static Object[][] Data20654() {
		return Common.getFactoryData(new Object[][] { 
			{ "CA" }, 
			{ "CA_AFFINITY"},
			{ "US"},
			{ "US_BPCTO"}, 
			{ "USEPP"},
			{ "US_OUTLET"}
			
		},PropsUtils.getTargetStore("NA-20654"));
	}

	@Factory(dataProvider = "20654")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA20654Test(store );

		return tests;
	}

}

