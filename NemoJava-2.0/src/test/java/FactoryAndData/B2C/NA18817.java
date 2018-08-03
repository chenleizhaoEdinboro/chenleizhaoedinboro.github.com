package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18817Test;


public class NA18817 {
	@DataProvider(name = "18817")
	public static Object[][] Data18817() {
		return Common.getFactoryData(new Object[][] {				
				{ "JP" }				
		},PropsUtils.getTargetStore("NA-18817"));
	}

	@Factory(dataProvider = "18817")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18817Test(store);

		return tests;
	}

}
