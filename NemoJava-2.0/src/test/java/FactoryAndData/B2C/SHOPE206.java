package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOPE206Test;

public class SHOPE206 {
	@DataProvider(name = "SHOPE206")
	public static Object[][] SHOPE206() {
		return Common.getFactoryData(new Object[][] {
				{ "JP"},
						},PropsUtils.getTargetStore("SHOPE-206"));
	}

	@Factory(dataProvider = "SHOPE206")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE206Test(store);

		return tests;
	}

}