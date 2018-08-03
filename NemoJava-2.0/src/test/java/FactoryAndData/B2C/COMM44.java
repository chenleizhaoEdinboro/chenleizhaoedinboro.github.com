package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE61Test;
import TestScript.B2C.COMM44Test;

public class COMM44 {
	@DataProvider(name = "44")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
			},PropsUtils.getTargetStore("COMM44"));
		
	}

	@Factory(dataProvider = "44")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM44Test(store);

		return tests;
	}
  
}
