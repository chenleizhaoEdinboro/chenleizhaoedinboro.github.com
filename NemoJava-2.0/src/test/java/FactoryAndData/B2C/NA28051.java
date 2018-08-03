package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28051Test;

public class NA28051 {

	@DataProvider(name = "NA28051")
	public static Object[][] Data28051() {
		return Common.getFactoryData(new Object[][] { { "US", "United States", "US web store unit" }}, PropsUtils.getTargetStore("NA-28051"));
	}

	@Factory(dataProvider = "NA28051")
	public Object[] createTest(String store, String country, String unit) {

		Object[] tests = new Object[1];

		tests[0] = new NA28051Test(store, country, unit);

		return tests;
	}

}
