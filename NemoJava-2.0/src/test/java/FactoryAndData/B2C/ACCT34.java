package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT34Test;

public class ACCT34 {

	@DataProvider(name = "ACCT34")
	public static Object[][] DataACCT34() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }
		},PropsUtils.getTargetStore("ACCT34"));
	}

	@Factory(dataProvider = "ACCT34")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT34Test(store);

		return tests;
	}

}
