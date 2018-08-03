package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26125Test;

public class NA26125 {

	@DataProvider(name = "26125")
	public static Object[][] Data26125() {
		return Common.getFactoryData(new Object[][] { 
				{ "CA", "80SX0074LM"},
				{ "US", "80SX0074LM"}
			},PropsUtils.getTargetStore("NA-26125"));
		
	}

	@Factory(dataProvider = "26125")
	public Object[] createTest(String store, String productNO) {

		Object[] tests = new Object[1];

		tests[0] = new NA26125Test(store,productNO);

		return tests;
	}

}

