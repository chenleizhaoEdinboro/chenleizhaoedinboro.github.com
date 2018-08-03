package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM54Test;

public class COMM54 {
	@DataProvider(name = "54")
	public static Object[][] Data54() {
		return Common.getFactoryData(new Object[][] { 
			{"US"}
		},PropsUtils.getTargetStore("COMM-54"));
	}

	@Factory(dataProvider = "54")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM54Test(store);

		return tests;
	}
}
