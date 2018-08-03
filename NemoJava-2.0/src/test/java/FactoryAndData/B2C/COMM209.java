package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM209Test;

public class COMM209 {
	@DataProvider(name = "209")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM209"));
		
	}

	@Factory(dataProvider = "209")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM209Test( store);

		return tests;
	}
  
}
