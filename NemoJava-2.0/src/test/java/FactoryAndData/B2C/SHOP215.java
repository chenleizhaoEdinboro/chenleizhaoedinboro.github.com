package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP215Test;

public class SHOP215 {
	@DataProvider(name = "215")
	public static Object[][] Data215() {
		return Common.getFactoryData(new Object[][] { { "US_SMB", "RR00000003", "20KNCTO1WWENUS0", "Subscription Test" },

		}, PropsUtils.getTargetStore("SHOPE-215"));

	}

	@Factory(dataProvider = "215")
	public Object[] createTest(String store, String subscription, String product, String sectionTitle) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP215Test(store, subscription, product, sectionTitle);

		return tests;
	}

}
