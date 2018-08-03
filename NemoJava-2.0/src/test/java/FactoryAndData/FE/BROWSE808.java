package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.BROWSE808Test;


public class BROWSE808 {
	
	@DataProvider(name = "test")
	public static Object[][] Data808() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("BROWSE808"));
	}
	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		
		tests[0] = new BROWSE808Test(store);
		return tests;

	}
}
