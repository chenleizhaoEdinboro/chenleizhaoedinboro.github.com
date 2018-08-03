package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17830Test;

public class NA17830 {

	@DataProvider(name = "17830")
	public static Object[][] Data17830() {
		return Common.getFactoryData(new Object[][] { 
			{ "HKZF" }
			},PropsUtils.getTargetStore("NA-17830"));
	}

	@Factory(dataProvider = "17830")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17830Test(store);

		return tests;
	}

}
