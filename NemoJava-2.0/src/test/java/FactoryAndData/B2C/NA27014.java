package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27014Test;

public class NA27014 {

	@DataProvider(name = "27014")
	public static Object[][] Data27014() {
		return Common.getFactoryData(new Object[][] { 
			{ "BR","brweb" }
		},PropsUtils.getTargetStore("NA-27014"));
	}

	@Factory(dataProvider = "27014")
	public Object[] createTest(String store,String b2cUnit) {

		Object[] tests = new Object[1];

		tests[0] = new NA27014Test(store,b2cUnit);

		return tests;
	}

}
