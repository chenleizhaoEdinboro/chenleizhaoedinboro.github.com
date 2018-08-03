package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23022Test;

public class NA23022 {

	@DataProvider(name = "23022")
	public static Object[][] Data23022() {
		return Common.getFactoryData(new Object[][] { 
			{ "GB" }
		},PropsUtils.getTargetStore("NA-23022"));
	}

	@Factory(dataProvider = "23022")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23022Test(store);

		return tests;
	}

}