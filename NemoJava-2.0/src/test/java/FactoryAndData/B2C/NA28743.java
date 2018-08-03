package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28743Test;

public class NA28743 {

	@DataProvider(name = "NA28743")
	public static Object[][] Data28743() {
		return Common.getFactoryData(new Object[][] {
				{"US"},
				{"AU"},
				{"JP"},
				{"GB"},
				{"BR"}
				},PropsUtils.getTargetStore("NA-28743"));
	}

	@Factory(dataProvider = "NA28743")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28743Test(store);

		return tests;
	}

}
