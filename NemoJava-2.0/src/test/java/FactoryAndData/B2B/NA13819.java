package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13819Test;

public class NA13819 {
	@DataProvider(name = "13819")
	public static Object[][] Data13819() {
		return Common.getFactoryData(new Object[][] { { "AU", "1213654197", "adobe_global", "adobe_global", "0B47090", "20HGS0CB0P" } }, PropsUtils.getTargetStore("NA-13819"));
	}

	@Factory(dataProvider = "13819")
	public Object[] createTest(String store, String defaultUnit, String defaultDMU, String AccessLevel, String product1, String product2) {

		Object[] tests = new Object[1];

		tests[0] = new NA13819Test(store, defaultUnit, defaultDMU, AccessLevel, product1, product2);

		return tests;
	}
}
