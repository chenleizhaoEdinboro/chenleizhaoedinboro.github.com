package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19686Test;

public class NA19686 {

	@DataProvider(name = "19686")
	public static Object[][] Data19686() {
		return Common.getFactoryData(new Object[][] { { "HKZF" } },
				PropsUtils.getTargetStore("NA-19686"));
	}

	@Factory(dataProvider = "19686")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19686Test(store);

		return tests;
	}

}
