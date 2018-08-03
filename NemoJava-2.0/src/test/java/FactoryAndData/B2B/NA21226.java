package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21226Test;

public class NA21226 {

	@DataProvider(name = "21226")
	public static Object[][] Data21226() {
		return Common.getFactoryData(new Object[][] { 
				{ "JP" }
			},PropsUtils.getTargetStore("NA-21226"));
	}

	@Factory(dataProvider = "21226")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21226Test(store);

		return tests;
	}

}
