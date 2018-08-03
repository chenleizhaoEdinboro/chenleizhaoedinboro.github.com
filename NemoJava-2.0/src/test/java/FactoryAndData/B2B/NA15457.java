package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15457Test;

public class NA15457 {

	@DataProvider(name = "15457")
	public static Object[][] Data15487() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" },
		},PropsUtils.getTargetStore("NA-15457"));
	}

	@Factory(dataProvider = "15457")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15457Test(store);

		return tests;
	}

}
