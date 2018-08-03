package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16672Test;

public class NA16672 {

	@DataProvider(name = "16672")
	public static Object[][] Data16672() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "NZ" }, 
			{ "US" },
			{ "USEPP" },
			{ "CA" },
			{ "CA_AFFINITY" },
			{ "US_OUTLET" },
			{ "US_BPCTO" },
			{ "HK" },
			{ "HKZF" },
			{ "SG" },
			{ "JP" },
			{ "GB" },
			{ "IE" },
			
//			{ "BR" },
			{ "CO" }
			
		},PropsUtils.getTargetStore("NA-16672"));
	}

	@Factory(dataProvider = "16672")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16672Test(store);

		return tests;
	}

}
