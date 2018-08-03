package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23668Test;

public class NA23668 {

	@DataProvider(name = "23668")
	public static Object[][] Data23668() {
		return Common.getFactoryData(new Object[][] { 
//			{ "AU" }, 
//			{ "NZ" }, 
//			{ "US" },
//			{ "USEPP" },
//			{ "CA" },
//			{ "CA_AFFINITY" },
//			{ "US_OUTLET" },
//			{ "HK" },
//			{ "HKZF" },
//			{ "SG" },
//			{ "JP" },
//			{ "GB" },
//			{ "IE" },
			{ "CO" }
		},PropsUtils.getTargetStore("NA-23668"));
	}

	@Factory(dataProvider = "23668")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23668Test(store);

		return tests;
	}

}
