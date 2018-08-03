package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19760Test;

public class NA19760 {
	@DataProvider(name = "NA19760")
	public static Object[][] Data25939() {
		return Common.getFactoryData(new Object[][] {
				{ "US_OUTLET"},
						},PropsUtils.getTargetStore("NA19760"));
	}

	@Factory(dataProvider = "NA19760")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19760Test(store);

		return tests;
	}

}
