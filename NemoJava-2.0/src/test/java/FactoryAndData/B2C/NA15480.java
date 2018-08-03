package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA15480Test;

public class NA15480 {

	@DataProvider(name = "15480")
	public static Object[][] Data15480() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
//			{ "NZ" },
//			{ "US" },
//		    { "USEPP" },
//			{ "CA" },
//			{ "CA_AFFINITY" },
//			{ "US_OUTLET" },
//			{ "HK" },
//			{ "HKZF" },
//			{ "SG" },
//			{ "JP" },
//			{ "GB" },
//			{ "IE" },
//			{ "CO" }
		},PropsUtils.getTargetStore("NA-15480"));
	}

	@Factory(dataProvider = "15480")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15480Test(store);

		return tests;
	}

}
