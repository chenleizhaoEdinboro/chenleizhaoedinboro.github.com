package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21868Test;

public class NA21868 {

	@DataProvider(name = "21868")
	public static Object[][] Data21868() {
		return Common.getFactoryData( new Object[][] { 
//				{ "HK" },
//				{ "AU" },
//				{ "US" },
//				{ "USEPP" },
//				{ "US_OUTLET" },
//				{ "US_BPCTO" },
//				{ "CA_AFFINITY" },
//				{ "JP" },
//				{ "GB" }
				{ "TW" }
		},PropsUtils.getTargetStore("NA-21868"));
	}

	@Factory(dataProvider = "21868")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21868Test(store);

		return tests;
	}

}
