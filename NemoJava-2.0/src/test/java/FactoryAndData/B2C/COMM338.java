package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM338Test;

public class COMM338 {
	@DataProvider(name = "338")
	public static Object[][] Data338() {
		return Common.getFactoryData(new Object[][] { 
				{ "IN" }
			},PropsUtils.getTargetStore("COMM-338"));
		
	}

	@Factory(dataProvider = "338")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM338Test(store);

		return tests;
	}

}
