package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21941Test;

public class NA21941 {
	@DataProvider(name = "21941")
	public static Object[][] Data21941() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" },
				{ "USEPP" },
				{ "CA_AFFINITY" },
				{ "US_BPCTO" },
				{ "HK" },
				{ "JP" },
				{ "GB" }  
		},PropsUtils.getTargetStore("NA-21941"));
	}

	@Factory(dataProvider = "21941")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA21941Test(store);
		return tests;
	}
}
