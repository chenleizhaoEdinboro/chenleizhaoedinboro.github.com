package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM481Test;

public class COMM481 {
	@DataProvider(name = "481")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM481"));
		
	}

	@Factory(dataProvider = "481")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM481Test( store);

		return tests;
	}
  
}
