package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT32Test;

public class ACCT32 {

	@DataProvider(name = "ACCT32")
	public static Object[][] DataACCT32() {
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
				{ "GB" },
				{ "IE" },
				{ "CO" }
		},PropsUtils.getTargetStore("ACCT32"));
	}

	@Factory(dataProvider = "ACCT32")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT32Test(store);

		return tests;
	}

}
