package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM167Test;

public class COMM167 {
	@DataProvider(name = "167")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM167"));
		
	}

	@Factory(dataProvider = "167")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM167Test( store);

		return tests;
	}
  
}
