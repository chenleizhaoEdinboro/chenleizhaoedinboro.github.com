package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18055Test;

public class NA18055 {

	@DataProvider(name = "18055")
	public static Object[][] Data18055() {
		return Common.getFactoryData(new Object[][] { 				
				{ "US" },			
				{ "CA_AFFINITY" },
				{ "US_BPCTO" },				
				{ "HK" },				
				{ "JP" },
		},PropsUtils.getTargetStore("NA-18055"));
	}

	@Factory(dataProvider = "18055")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18055Test(store);

		return tests;
	}
}
