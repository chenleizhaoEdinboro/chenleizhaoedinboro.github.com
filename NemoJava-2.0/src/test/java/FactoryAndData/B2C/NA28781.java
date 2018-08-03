package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28781Test;

public class NA28781 {

	@DataProvider(name = "NA28781")
	public static Object[][] Data28781() {
		return Common.getFactoryData(new Object[][] {
				{ "AU"},
				{ "US"},
				{ "JP"},
				{ "GB"},
				{ "BR"}
				},PropsUtils.getTargetStore("NA-28781"));
	}

	@Factory(dataProvider = "NA28781")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28781Test(store);

		return tests;
	}

}
