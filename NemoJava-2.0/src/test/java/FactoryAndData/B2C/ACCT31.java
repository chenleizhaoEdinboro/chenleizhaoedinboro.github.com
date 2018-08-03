package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT31Test;

public class ACCT31 {

	@DataProvider(name = "ACCT31")
	public static Object[][] DataACCT31() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
			 	{ "NZ" }, 
				{ "US" },
				{ "USEPP" },
				{ "CA" },
				{ "CA_AFFINITY" },
				{ "US_OUTLET" },
				{ "HK" },
				{ "HKZF" },
				{ "SG" },
				{ "JP" },
				{ "BR" }
		},PropsUtils.getTargetStore("ACCT31"));
	}

	@Factory(dataProvider = "ACCT31")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT31Test(store);

		return tests;
	}

}
