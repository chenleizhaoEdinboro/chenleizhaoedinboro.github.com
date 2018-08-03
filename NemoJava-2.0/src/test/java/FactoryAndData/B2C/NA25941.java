package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25941Test;

public class NA25941 {
	@DataProvider(name = "NA25941")
	public static Object[][] Data25941() {
		return Common.getFactoryData(new Object[][] {
				{ "CA"},
						},PropsUtils.getTargetStore("NA-25941"));
	}

	@Factory(dataProvider = "NA25941")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25941Test(store);

		return tests;
	}

}
