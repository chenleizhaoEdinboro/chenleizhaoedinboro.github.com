package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA14919Test;

public class NA14919 {

	@DataProvider(name = "14919")
	public static Object[][] Data14919() {
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
			},PropsUtils.getTargetStore("NA-14919"));
	}

	@Factory(dataProvider = "14919")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA14919Test(store);

		return tests;
	}

}
