package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17687Test;

public class NA17687 {

	@DataProvider(name = "17687")
	public static Object[][] Data17687() {

		return Common.getFactoryData(new Object[][] {
				{"AU" }
		},PropsUtils.getTargetStore("NA-17687"));

	}

	@Factory(dataProvider = "17687")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17687Test(store);

		return tests;
	}

}
