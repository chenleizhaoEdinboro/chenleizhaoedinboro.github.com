package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19762Test;

public class NA19762 {
	@DataProvider(name = "19762")
	public static Object[][] Data19762() {
		return Common.getFactoryData(new Object[][] {
				{ "AU"},
						},PropsUtils.getTargetStore("NA-19762"));
	}

	@Factory(dataProvider = "19762")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19762Test(store);

		return tests;
	}

}
