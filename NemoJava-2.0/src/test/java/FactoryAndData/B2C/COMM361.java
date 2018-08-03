package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM361Test;

public class COMM361 {
	@DataProvider(name = "361")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM361"));
		
	}

	@Factory(dataProvider = "361")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM361Test( store);

		return tests;
	}
  
}
