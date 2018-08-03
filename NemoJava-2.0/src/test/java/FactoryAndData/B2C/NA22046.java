package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22046Test;

public class NA22046 {

	@DataProvider(name = "22046")
	public static Object[][] Data22046() {
		return Common.getFactoryData(new Object[][] { 
			{ "TW" }
		},PropsUtils.getTargetStore("NA-22046"));
	}

	@Factory(dataProvider = "22046")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22046Test(store);

		return tests;
	}

}
