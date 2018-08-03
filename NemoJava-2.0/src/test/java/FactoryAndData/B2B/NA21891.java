package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21891Test;

public class NA21891 {
	@DataProvider(name = "21891")
	public static Object[][] Data21891() {
		return Common.getFactoryData(new Object[][] { 
				{"AU"},
				{"US"},
				{"JP"}
		},PropsUtils.getTargetStore("NA-21891"));
	}

	@Factory(dataProvider = "21891")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21891Test(store);

		return tests;
	}
}
