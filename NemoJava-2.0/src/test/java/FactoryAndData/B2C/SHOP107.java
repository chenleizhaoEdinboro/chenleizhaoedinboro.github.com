package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP107Test;

public class SHOP107 {
	@DataProvider(name = "107")
	public static Object[][] Data107() {
		return Common.getFactoryData(new Object[][] { { "US_SMB", "RR00000003" },

		}, PropsUtils.getTargetStore("SHOPE-107"));

	}

	@Factory(dataProvider = "107")
	public Object[] createTest(String store, String subscription) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP107Test(store, subscription);

		return tests;
	}

}
