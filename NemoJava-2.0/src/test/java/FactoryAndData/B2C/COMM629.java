package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM629Test;

public class COMM629 {
	@DataProvider(name = "629")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
			},PropsUtils.getTargetStore("COMM629"));
		
	}

	@Factory(dataProvider = "629")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM629Test( store);

		return tests;
	}
  
}
