package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18233Test;

public class NA18233 {

	@DataProvider(name = "NA18233")
	public static Object[][] Data18233() {
		return Common.getFactoryData(new Object[][] {
				{ "AU" } 
				},PropsUtils.getTargetStore("NA-18233"));
	}

	@Factory(dataProvider = "NA18233")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18233Test(store);

		return tests;
	}

}