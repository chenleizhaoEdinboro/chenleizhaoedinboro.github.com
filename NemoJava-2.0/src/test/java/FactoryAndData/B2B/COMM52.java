package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM52Test;

public class COMM52 {
	@DataProvider(name = "52")
	public static Object[][] Data52() {
		return Common.getFactoryData(new Object[][] { 
			{"US"}
		},PropsUtils.getTargetStore("COMM-52"));
	}

	@Factory(dataProvider = "52")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM52Test(store);

		return tests;
	}
}
