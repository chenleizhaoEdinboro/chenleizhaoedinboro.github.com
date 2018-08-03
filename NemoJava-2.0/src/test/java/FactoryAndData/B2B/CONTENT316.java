package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.CONTENT316Test;

public class CONTENT316 {
	@DataProvider(name = "CONTENT316")
	public static Object[][] Data316() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("CONTENT-316"));
	}

	@Factory(dataProvider = "CONTENT316")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT316Test(store);

		return tests;
	}
}
