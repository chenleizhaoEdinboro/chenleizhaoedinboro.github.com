package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE244Test;

public class BROWSE244 {
	@DataProvider(name = "244")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
			},PropsUtils.getTargetStore("BROWSE-244"));
		
	}

	@Factory(dataProvider = "244")
	public Object[] createTest(String store ) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE244Test(store);

		return tests;
	}
}
