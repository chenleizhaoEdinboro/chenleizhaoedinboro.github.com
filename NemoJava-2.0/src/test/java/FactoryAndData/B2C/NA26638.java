package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26638Test;

public class NA26638 {

	@DataProvider(name = "26638")
	public static Object[][] Data26638() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "CA" },
			{ "US" },
			{ "JP" }
			},PropsUtils.getTargetStore("NA-26638"));
	}

	@Factory(dataProvider = "26638")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26638Test(store);

		return tests;
	}

}
