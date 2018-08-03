package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15411Test;

public class NA15411 {

	@DataProvider(name = "15411")
	public static Object[][] Data15411() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" }, 
			{ "US" } },
				PropsUtils.getTargetStore("NA-15411"));
	}

	@Factory(dataProvider = "15411")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15411Test(store);

		return tests;
	}

}
