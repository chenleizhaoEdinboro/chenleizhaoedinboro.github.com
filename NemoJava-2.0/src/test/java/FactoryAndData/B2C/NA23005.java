package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23005Test;

public class NA23005 {

	@DataProvider(name = "23005")
	public static Object[][] Data23005() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" },
			{ "USEPP" },
			{ "US_BPCTO" },
			},PropsUtils.getTargetStore("NA-23005"));
	}

	@Factory(dataProvider = "23005")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23005Test(store);

		return tests;
	}

}
