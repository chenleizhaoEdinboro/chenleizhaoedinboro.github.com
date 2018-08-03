package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM207Test;

public class COMM207 {
	@DataProvider(name = "207")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM207"));
		
	}

	@Factory(dataProvider = "207")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM207Test( store);

		return tests;
	}
  
}
