package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE338Test;

public class SHOPE338 {

	@DataProvider(name = "SHOPE338")
	public static Object[][] Data338() {
		return Common.getFactoryData(new Object[][] {
				// { "US", "RR00000001", "Subscription Test" },
				{ "US", "RR00000001", "Subscription Test", "Subscription Test" },

		}, PropsUtils.getTargetStore("SHOPE-338"));
	}

	@Factory(dataProvider = "SHOPE338")
	public Object[] createTest(String store, String subscription, String product, String sectionTitle) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE338Test(store, subscription, product, sectionTitle);

		return tests;
	}

}
