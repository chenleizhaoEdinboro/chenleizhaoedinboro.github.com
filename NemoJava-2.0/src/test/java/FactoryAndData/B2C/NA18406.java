package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18406Test;

public class NA18406 {

	@DataProvider(name = "NA18406")
	public static Object[][] Ddata15492() {
		return Common.getFactoryData(new Object[][] { { "JP" } }, PropsUtils.getTargetStore("NA-18406"));
	}

	@Factory(dataProvider = "NA18406")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18406Test(store);

		return tests;
	}

}