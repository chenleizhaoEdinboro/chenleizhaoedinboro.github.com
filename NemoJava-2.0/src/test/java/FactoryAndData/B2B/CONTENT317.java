package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.CONTENT317Test;

public class CONTENT317 {
	@DataProvider(name = "CONTENT317")
	public static Object[][] Data317() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
		},PropsUtils.getTargetStore("CONTENT-317"));
	}

	@Factory(dataProvider = "CONTENT317")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT317Test(store);

		return tests;
	}
}
