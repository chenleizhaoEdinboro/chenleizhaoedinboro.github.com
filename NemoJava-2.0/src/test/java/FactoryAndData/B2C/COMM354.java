package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM354Test;

public class COMM354 {
	@DataProvider(name = "354")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
//				{"US"},
			},PropsUtils.getTargetStore("COMM354"));
		
	}

	@Factory(dataProvider = "354")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM354Test( store);

		return tests;
	}
  
}
