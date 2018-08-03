package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15456Test;

public class NA15456 {

	@DataProvider(name = "15456")
	public static Object[][] Data15487() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" },
		},PropsUtils.getTargetStore("NA-15456"));
	}

	@Factory(dataProvider = "15456")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15456Test(store);

		return tests;
	}

}
