package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA24295Test;

public class NA24295 {
	@DataProvider(name = "NA24295")
	public static Object[][] Data24295() {
		return Common.getFactoryData(new Object[][] {
				{ "US"},
						},PropsUtils.getTargetStore("NA-24295"));
	}

	@Factory(dataProvider = "NA24295")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA24295Test(store);

		return tests;
	}

}