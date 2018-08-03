package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16452Test;

public class NA16452 {

	@DataProvider(name = "16452")
	public static Object[][] Data16452() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "BR" },
			{ "NZ" }, 
			{ "US" },
			{ "USEPP" },
			{ "CA" },
			{ "CA_AFFINITY" },
			{ "US_BPCTO" },
			{ "US_OUTLET" },
			{ "HK" },
			{ "HKZF" },
			{ "SG" },
			{ "JP" },
			{ "GB" },
			{ "IE" },
			{ "TW" },
			{ "US_SMB" },
			{ "CO" }
		},PropsUtils.getTargetStore("NA-16452"));
	}

	@Factory(dataProvider = "16452")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16452Test(store);

		return tests;
	}

}
