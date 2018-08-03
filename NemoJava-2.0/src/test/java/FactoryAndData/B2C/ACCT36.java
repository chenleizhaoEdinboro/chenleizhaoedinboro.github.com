package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT36Test;

public class ACCT36 {

	@DataProvider(name = "ACCT36")
	public static Object[][] DataACCT34() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }
		},PropsUtils.getTargetStore("ACCT36"));
	}

	@Factory(dataProvider = "ACCT36")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT36Test(store);

		return tests;
	}

}
