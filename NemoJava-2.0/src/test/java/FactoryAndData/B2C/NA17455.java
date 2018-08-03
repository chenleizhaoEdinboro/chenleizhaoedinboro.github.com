package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestScript.B2C.NA17455Test;
import TestData.PropsUtils;

public class NA17455 {

	@DataProvider(name = "17455")
	public static Object[][] Data17455() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "US" },
			{ "JP" }
			},
			PropsUtils.getTargetStore("NA-17455"));
	}

	@Factory(dataProvider = "17455")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17455Test(store);

		return tests;
	}

}
