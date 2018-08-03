package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM483Test;

public class COMM483 {
	@DataProvider(name = "483")
	public static Object[][] Data483() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("COMM-483"));
	}

	@Factory(dataProvider = "483")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM483Test(store);

		return tests;
	}
}
