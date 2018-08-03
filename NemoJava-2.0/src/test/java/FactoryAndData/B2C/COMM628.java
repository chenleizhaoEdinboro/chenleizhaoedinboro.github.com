package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM628Test;

public class COMM628 {
	@DataProvider(name = "628")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
			},PropsUtils.getTargetStore("COMM628"));
		
	}

	@Factory(dataProvider = "628")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM628Test( store);

		return tests;
	}
  
}
