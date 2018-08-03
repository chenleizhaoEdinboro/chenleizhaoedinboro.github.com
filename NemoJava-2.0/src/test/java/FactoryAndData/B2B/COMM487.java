package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM487Test;

public class COMM487 {
	@DataProvider(name = "487")
	public static Object[][] Data487() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("COMM-487"));
	}

	@Factory(dataProvider = "487")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM487Test(store);

		return tests;
	}
}
