package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM360Test;

public class COMM360 {
	@DataProvider(name = "360")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM360"));
		
	}

	@Factory(dataProvider = "360")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM360Test( store);

		return tests;
	}
  
}
