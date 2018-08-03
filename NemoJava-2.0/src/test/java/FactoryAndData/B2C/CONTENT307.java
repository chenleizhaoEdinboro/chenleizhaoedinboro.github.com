package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT307Test;

public class CONTENT307 {
	@DataProvider(name = "CONTENT307")
	public static Object[][] Data307() {
		return Common.getFactoryData(new Object[][] { 
				{"JP_SMB"},
				{"US_SMB"},
			},PropsUtils.getTargetStore("CONTENT-307"));
		
	}

	@Factory(dataProvider = "CONTENT307")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT307Test(store);

		return tests;
	}
  
}
