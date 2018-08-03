package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19852Test;
import TestScript.B2C.NA19976Test;

public class NA19976 {

	@DataProvider(name = "19976")
	public static Object[][] Data19852() {
		return Common.getFactoryData(new Object[][] { 
			{ "MX","mxweb" }
		},PropsUtils.getTargetStore("NA-19976"));
	}

	@Factory(dataProvider = "19976")
	public Object[] createTest(String store,String b2cUnit) {

		Object[] tests = new Object[1];

		tests[0] = new NA19976Test(store,b2cUnit);

		return tests;
	}

}
