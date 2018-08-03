package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE337Test;

public class SHOPE337 {

	@DataProvider(name = "SHOPE337")
	public static Object[][] Data337() {
		return Common.getFactoryData(new Object[][] { { "US", "RR00000001", "Subscription Test", "Subscription Test", "20JES1X200" },

		}, PropsUtils.getTargetStore("SHOPE-337"));
	}

	@Factory(dataProvider = "SHOPE337")
	public Object[] createTest(String store, String subscription, String sectionTitle, String groupTitle, String testProduct) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE337Test(store, subscription, sectionTitle, groupTitle, testProduct);

		return tests;
	}

}
