package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17938Test;

public class NA17938 {
	@DataProvider(name = "17938")
	public static Object[][] Data17938() {
		return Common.getFactoryData(new Object[][] { 
				{ "JP" }
		},PropsUtils.getTargetStore("NA-17938"));
	}

	@Factory(dataProvider = "17938")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA17938Test(store);
		return tests;
	}
}
