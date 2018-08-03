package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13493Test;

public class NA13493{

	@DataProvider(name = "13493")
	public static Object[][] Data13493() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-13493"));
	}
	@Factory(dataProvider = "13493")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA13493Test(store);

		return tests;
	}
}
