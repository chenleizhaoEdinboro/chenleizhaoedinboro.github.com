package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25939Test;

public class NA25939 {
	@DataProvider(name = "NA25939")
	public static Object[][] Data25939() {
		return Common.getFactoryData(new Object[][] {
				{ "US"},
						},PropsUtils.getTargetStore("NA-25939"));
	}

	@Factory(dataProvider = "NA25939")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25939Test(store);

		return tests;
	}

}
