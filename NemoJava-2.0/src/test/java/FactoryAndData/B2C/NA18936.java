package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18936Test;

public class NA18936 {
	@DataProvider(name = "18936")
	public static Object[][] Data18936() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" },
				{ "USEPP" },
				{ "CA_AFFINITY" },
				{ "US_BPCTO" },
				{ "US_OUTLET" },
				{ "HK" },
				{ "JP" },
				{ "GB" }
		},PropsUtils.getTargetStore("NA-18936"));
	}

	@Factory(dataProvider = "18936")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18936Test(store);
		return tests;
	}
}
