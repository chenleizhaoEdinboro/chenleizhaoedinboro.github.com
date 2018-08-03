package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15487Test;

public class NA15487 {

	@DataProvider(name = "15487")
	public static Object[][] Data15487() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" },
			{ "US" },
			{ "JP" }
		},PropsUtils.getTargetStore("NA-15487"));
	}

	@Factory(dataProvider = "15487")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15487Test(store);

		return tests;
	}

}
