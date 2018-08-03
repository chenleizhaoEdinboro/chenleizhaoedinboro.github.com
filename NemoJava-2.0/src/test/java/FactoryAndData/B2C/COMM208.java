package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM208Test;

public class COMM208 {
	@DataProvider(name = "208")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM208"));
		
	}

	@Factory(dataProvider = "208")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM208Test( store);

		return tests;
	}
  
}
