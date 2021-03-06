package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28125Test;

public class NA28125 {
	@DataProvider(name = "28125")
	public static Object[][] Data28205() {
		return Common.getFactoryData(new Object[][] { 
				{"US"}
			},PropsUtils.getTargetStore("NA-28125"));
		
	}

	@Factory(dataProvider = "28125")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28125Test(store);

		return tests;
	}
  
}
