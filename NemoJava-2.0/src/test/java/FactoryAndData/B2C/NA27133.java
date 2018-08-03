package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestScript.B2C.NA27133Test;
import TestData.PropsUtils;

public class NA27133 {

	@DataProvider(name = "27133")
	public static Object[][] Data27133() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			},
			PropsUtils.getTargetStore("NA-27133"));
	}

	@Factory(dataProvider = "27133")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27133Test(store);

		return tests;
	}

}
