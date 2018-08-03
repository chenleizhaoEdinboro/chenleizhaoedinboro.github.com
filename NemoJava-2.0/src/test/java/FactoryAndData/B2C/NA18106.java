package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16656Test;
import TestScript.B2C.NA18106Test;

public class NA18106 {

	@DataProvider(name = "18106")
	public static Object[][] Data16656() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "US" },
			
			{ "JP" },
			{ "MX" },
			{ "GB" }
			},PropsUtils.getTargetStore("NA-18106"));
	}

	@Factory(dataProvider = "18106")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18106Test(store);

		return tests;
	}

}
