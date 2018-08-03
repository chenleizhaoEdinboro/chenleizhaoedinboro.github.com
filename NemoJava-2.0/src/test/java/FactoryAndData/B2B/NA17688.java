package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17688Test;

public class NA17688 {

	@DataProvider(name = "17688")
	public static Object[][] Data17688() {

		return Common.getFactoryData(new Object[][] {
				{"AU"},
				{"US"}
		},PropsUtils.getTargetStore("NA-17688"));

	}

	@Factory(dataProvider = "17688")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17688Test(store);

		return tests;
	}

}
