package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM175Test;

public class COMM175 {
	@DataProvider(name = "175")
	public static Object[][] Data175() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM175"));
		
	}

	@Factory(dataProvider = "175")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM175Test(store);

		return tests;
	}
  
}
