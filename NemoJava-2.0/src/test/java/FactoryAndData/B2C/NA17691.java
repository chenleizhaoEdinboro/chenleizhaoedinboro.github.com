package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17691Test;

public class NA17691 {
	@DataProvider(name = "17691")
	public static Object[][] Data16863() {
		return Common.getFactoryData(new Object[][] { 
				{ "GB"},
				},
				PropsUtils.getTargetStore("NA-17691"));
	}

	@Factory(dataProvider = "17691")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17691Test(store);

		return tests;
	}
}
