package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM636Test;

public class COMM636 {
	@DataProvider(name = "636")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
			},PropsUtils.getTargetStore("COMM636"));
		
	}

	@Factory(dataProvider = "636")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM636Test( store);

		return tests;
	}
  
}
