package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA27732Test;

public class NA27732 {

	@DataProvider(name = "27732")
	public static Object[][] Data27732() {
		return Common.getFactoryData(new Object[][] { { "JP", "Japan" } }, PropsUtils.getTargetStore("NA-27732"));
	}

	@Factory(dataProvider = "27732")
	public Object[] createTest(String store, String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA27732Test(store, country);

		return tests;
	}

}
