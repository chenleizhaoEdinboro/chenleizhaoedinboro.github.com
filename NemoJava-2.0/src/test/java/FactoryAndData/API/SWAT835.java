package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.SWAT835Test;

public class SWAT835 {
	@DataProvider(name = "SWAT835")
	public static Object[][] Data835() {
		return Common.getFactoryData(new Object[][] {

				{ "US", "CTO", "20KNCTO1WWENUS0", "United States", "US web store unit", "380", "599.99", 569.99f },
				{ "AU", "MTM", "81A5003AAU", "Australia", "AU Public unit", "360.00", "439.00", 409.00f },
				{ "NZ", "CTO", "20HBCTO1WWENNZ4", "New Zealand", "NZ Public unit", "1380.00", "3100.00", 3070.00f },
				{ "JP", "CTO", "20JHCTO1WWJAJP0", "Japan", "JP public store unit", "81500", "9600", 128970f },
				{ "HK", "MTM", "80XL006MHH", "Hong Kong S.A.R. of China", "HK public store unit", "4100.00", "5600.00",
						5570.00f },
				{ "GB", "MTM", "81AC000CUK", "United Kingdom", "gbweb", "600.00", "880.00", 850.00f },

		}, PropsUtils.getTargetStore("SWAT835"));
	}

	@Factory(dataProvider = "SWAT835")
	public Object[] createTest(String store, String productType, String ctoPro, String country, String unit,
			String floorP, String webP, float salesP) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT835Test(store, productType, ctoPro, country, unit, floorP, webP, salesP);

		return tests;
	}
}
