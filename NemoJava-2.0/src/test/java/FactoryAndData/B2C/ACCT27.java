package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT27Test;

public class ACCT27 {

	@DataProvider(name = "ACCT27")
	public static Object[][] DataACCT27() {
		return Common.getFactoryData(new Object[][] { 
			{ "IN" }
			},PropsUtils.getTargetStore("ACCT27"));
	}

	@Factory(dataProvider = "ACCT27")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT27Test(store);

		return tests;
	}

}
