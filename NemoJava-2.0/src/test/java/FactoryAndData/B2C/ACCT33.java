package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT33Test;

public class ACCT33 {

	@DataProvider(name = "ACCT33")
	public static Object[][] DataACCT33() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" },
				{ "USEPP" },
				{ "CA_AFFINITY" },
				{ "US_OUTLET" },
				{ "US_BPCTO" },
				{ "HK" },
				{ "JP" },
				{ "GB" }
		},PropsUtils.getTargetStore("ACCT33"));
	}

	@Factory(dataProvider = "ACCT33")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT33Test(store);

		return tests;
	}

}
