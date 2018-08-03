package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28156Test;

public class NA28156 {

	@DataProvider(name = "28156")
	public static Object[][] Data15487() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP" },
		},PropsUtils.getTargetStore("NA-28156"));
	}

	@Factory(dataProvider = "28156")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28156Test(store);

		return tests;
	}

}
